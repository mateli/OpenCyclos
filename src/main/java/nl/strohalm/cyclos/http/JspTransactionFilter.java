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
package nl.strohalm.cyclos.http;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.struts.CyclosRequestProcessor;
import nl.strohalm.cyclos.struts.CyclosRequestProcessor.ExecutionResult;
import nl.strohalm.cyclos.utils.ActionHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * A filter used to make sure that JSP execution is done within a read-only database transaction
 * 
 * @author luis
 */
public class JspTransactionFilter extends OncePerRequestFilter {

    private static final Log    LOG = LogFactory.getLog(JspTransactionFilter.class);

    private ActionHelper        actionHelper;
    private TransactionTemplate transactionTemplate;

    @Inject
    public void setActionHelper(final ActionHelper actionHelper) {
        this.actionHelper = actionHelper;
    }

    @Inject
    public void setTransactionManager(final PlatformTransactionManager transactionManager) {
        transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.setReadOnly(true);
    }

    @Override
    protected void execute(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final ExecutionResult result = (ExecutionResult) request.getAttribute(CyclosRequestProcessor.EXECUTION_RESULT_KEY);
        if (result != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(request.getRequestURI() + ": Not opening a new readonly transaction for JSP because CyclosRequestProcessor is managing transactions");
            }
            // The transaction is already being managed by CyclosRequestProcessor. Don't do anything here
            chain.doFilter(request, response);
        } else {
            // We need a readonly transaction for this JSP
            request.setAttribute(CyclosRequestProcessor.NO_TRANSACTION_KEY, true);
            try {
                if (LOG.isDebugEnabled()) {
                    LOG.debug(request.getRequestURI() + ": Opening a new readonly transaction for JSP");
                }
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(final TransactionStatus status) {
                        try {
                            chain.doFilter(request, response);
                        } catch (final Exception e) {
                            actionHelper.generateLog(request, config.getServletContext(), e);
                            throw new RuntimeException(e);
                        }
                    }
                });
            } catch (final RuntimeException e) {
                ActionHelper.throwException(e.getCause());
            }
        }
    }
}
