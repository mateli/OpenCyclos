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
package nl.strohalm.cyclos.scheduling;

import java.util.List;

import nl.strohalm.cyclos.scheduling.polling.PollingTask;

import org.springframework.beans.factory.DisposableBean;

/**
 * Manages execution of {@link PollingTask}s
 * @author luis
 */
public class PollingTasksHandler implements DisposableBean {

    private List<PollingTask> pollingTasks;

    @Override
    public void destroy() throws Exception {
        if (pollingTasks != null) {
            for (PollingTask task : pollingTasks) {
                task.interrupt();
            }
        }
    }

    /**
     * Returns the {@link PollingTask} with the given type, or null if none is found
     */
    @SuppressWarnings("unchecked")
    public <PT extends PollingTask> PT get(final Class<PT> type) {
        for (PollingTask pt : pollingTasks) {
            if (type.isInstance(pt)) {
                return (PT) pt;
            }
        }
        return null;
    }

    public List<PollingTask> getPollingTasks() {
        return pollingTasks;
    }

    public void setPollingTasks(final List<PollingTask> pollingTasks) {
        this.pollingTasks = pollingTasks;
    }

    public void start() {
        if (pollingTasks != null) {
            for (PollingTask task : pollingTasks) {
                task.start();
            }
        }
    }

}
