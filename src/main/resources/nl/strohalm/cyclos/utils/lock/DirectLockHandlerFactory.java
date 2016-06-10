/*
    This file is part of Cyclos (www.cyclos.org).
    A project of the Social Trade Organisation (www.socialtrade.org).

    Cyclos is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Cyclos is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Cyclos; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 */
package nl.strohalm.cyclos.utils.lock;

import nl.strohalm.cyclos.entities.accounts.Account;
import nl.strohalm.cyclos.entities.exceptions.LockingException;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.entities.sms.MemberSmsStatusLock;
import nl.strohalm.cyclos.utils.EntityHelper;
import nl.strohalm.cyclos.utils.ExceptionHelper;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.utils.transaction.TransactionRollbackListener;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.JDBCException;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Returns {@link LockHandler}s which use the current transaction to perform locks. Suitable for most cases, except replicated databases (like MySQL
 * master-master replication).
 * 
 * @author luis
 */
public class DirectLockHandlerFactory extends BaseLockHandlerFactory {
    /**
     * Locks records in the current transaction
     * @author luis
     */
    private class DirectLockHandler implements LockHandler {

        @Override
        public void lock(final Account... accounts) throws LockingException {
            if (ArrayUtils.isEmpty(accounts)) {
                return;
            }
            Long[] ids = EntityHelper.toIds(accounts);
            Session session = SessionFactoryUtils.getSession(sessionFactory, true);
            try {
                session
                        .createQuery("select l.id from AccountLock l where l.id in (:ids)")
                        .setLockOptions(new LockOptions(LockMode.PESSIMISTIC_WRITE))
                        .setParameterList("ids", ids)
                        .list();
            } catch (JDBCException e) {
                handleException(e);
            }
        }

        @Override
        public void lockSmsStatus(final Member member) throws LockingException {
            if (member == null) {
                return;
            }
            final Session session = SessionFactoryUtils.getSession(sessionFactory, true);
            try {
                Long id = (Long) session
                        .createQuery("select m.id from MemberSmsStatusLock m where m.id = :id")
                        .setLockOptions(new LockOptions(LockMode.PESSIMISTIC_WRITE))
                        .setParameter("id", member.getId())
                        .uniqueResult();

                // If the id didn't exist, insert it and throw a LockingException, so the next attempt will succeed
                if (id == null) {
                    CurrentTransactionData.addTransactionRollbackListener(new TransactionRollbackListener() {
                        @Override
                        public void onTransactionRollback() {
                            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                                @Override
                                protected void doInTransactionWithoutResult(final TransactionStatus status) {
                                    MemberSmsStatusLock lock = new MemberSmsStatusLock();
                                    lock.setId(member.getId());
                                    Session session = SessionFactoryUtils.getSession(sessionFactory, true);
                                    session.save(lock);
                                }
                            });
                        }
                    });
                    throw new LockingException("First time this member sms status. Please, retry.");
                }
            } catch (JDBCException e) {
                handleException(e);
            }
        }

        @Override
        public void release() {
            // Nothing to do, as when the current transaction ends, all locks will be released
        }

        private void handleException(final JDBCException e) {
            if (ExceptionHelper.isLockingException(e)) {
                throw new LockingException(e);
            }
            throw e;
        }
    }

    private SessionFactory      sessionFactory;
    private TransactionTemplate transactionTemplate;
    private final LockHandler   lockHandler = new DirectLockHandler();

    @Override
    public LockHandler getLockHandler() {
        return lockHandler;
    }

    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setTransactionTemplate(final TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

}
