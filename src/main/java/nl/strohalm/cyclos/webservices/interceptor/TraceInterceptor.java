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
package nl.strohalm.cyclos.webservices.interceptor;

import nl.strohalm.cyclos.utils.logging.LoggingHandler;
import nl.strohalm.cyclos.utils.logging.WebServiceLogDTO;
import nl.strohalm.cyclos.utils.transaction.CurrentTransactionData;
import nl.strohalm.cyclos.webservices.WebServiceContext;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

/**
 * Interceptor used to generate a trace log for a web service invocation
 * @author luis
 */
public class TraceInterceptor extends AbstractSoapInterceptor {
    private LoggingHandler loggingHandler;

    public TraceInterceptor() {
        super(Phase.MARSHAL);
    }

    @Override
    public void handleMessage(final SoapMessage message) throws Fault {
        // This interceptor only logs messages if there isn't a fault or the CurrentTransactionData doesn't have an error set
        // because in those cases the log will be done by the CustomFaultInterceptor or the Web Service operation implementation
        Throwable exception = (Throwable) WebServiceContext.getRequest().getAttribute("soapFault");
        if (exception == null) {
            exception = CurrentTransactionData.getError();
        }
        if (exception == null) {
            // Don't log on faults, as this will be done by CustomFaultInterceptor or
            final WebServiceLogDTO log = WebServiceContext.newLog();
            loggingHandler.traceWebService(log);
        }
    }

    public void setLoggingHandler(final LoggingHandler loggingHandler) {
        this.loggingHandler = loggingHandler;
    }

}
