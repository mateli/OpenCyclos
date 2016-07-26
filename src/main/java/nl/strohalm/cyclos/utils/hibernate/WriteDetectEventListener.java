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
package nl.strohalm.cyclos.utils.hibernate;

import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import org.hibernate.event.spi.PostCollectionRecreateEvent;
import org.hibernate.event.spi.PostCollectionRecreateEventListener;
import org.hibernate.event.spi.PostCollectionRemoveEvent;
import org.hibernate.event.spi.PostCollectionRemoveEventListener;
import org.hibernate.event.spi.PostCollectionUpdateEvent;
import org.hibernate.event.spi.PostCollectionUpdateEventListener;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;



/**
 * A listener for events which change the databasse
 * 
 * @author luis
 */
public class WriteDetectEventListener implements PostDeleteEventListener, PostInsertEventListener, PostUpdateEventListener, PostCollectionRecreateEventListener, PostCollectionUpdateEventListener, PostCollectionRemoveEventListener {

    private static final long serialVersionUID = 2936564522256205828L;

    @Override
    public void onPostDelete(final PostDeleteEvent event) {
        markWrite();
    }

    @Override
    public void onPostInsert(final PostInsertEvent event) {
        markWrite();
    }

    @Override
    public void onPostRecreateCollection(final PostCollectionRecreateEvent arg0) {
        markWrite();
    }

    @Override
    public void onPostRemoveCollection(final PostCollectionRemoveEvent arg0) {
        markWrite();
    }

    @Override
    public void onPostUpdate(final PostUpdateEvent event) {
        markWrite();
    }

    @Override
    public void onPostUpdateCollection(final PostCollectionUpdateEvent arg0) {
        markWrite();
    }

    private void markWrite() {
        CurrentTransactionData.setWrite();
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister ep) {
        return false;
    }
}
