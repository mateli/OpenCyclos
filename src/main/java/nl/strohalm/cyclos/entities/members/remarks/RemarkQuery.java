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
package nl.strohalm.cyclos.entities.members.remarks;

import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.remarks.Remark.Nature;
import nl.strohalm.cyclos.utils.Period;
import nl.strohalm.cyclos.utils.query.QueryParameters;

/**
 * Base definitions for a remark query
 * @author Jefferson Magno
 */
public abstract class RemarkQuery extends QueryParameters {

    private static final long serialVersionUID = 4051359458150961163L;
    private Element writer;
    private Element subject;
    private Period  period;
    private String  comments;

    public String getComments() {
        return comments;
    }

    public abstract Nature getNature();

    public Period getPeriod() {
        return period;
    }

    public Element getSubject() {
        return subject;
    }

    public Element getWriter() {
        return writer;
    }

    public void setComments(final String comments) {
        this.comments = comments;
    }

    public void setPeriod(final Period period) {
        this.period = period;
    }

    public void setSubject(final Element subject) {
        this.subject = subject;
    }

    public void setWriter(final Element writer) {
        this.writer = writer;
    }

}
