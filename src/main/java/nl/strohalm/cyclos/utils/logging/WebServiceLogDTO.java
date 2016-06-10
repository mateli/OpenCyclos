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
package nl.strohalm.cyclos.utils.logging;

import nl.strohalm.cyclos.entities.accounts.pos.Pos;
import nl.strohalm.cyclos.entities.services.ServiceClient;
import nl.strohalm.cyclos.utils.DataObject;

/**
 * Parameters used to generate logs for web services
 * 
 * @author luis
 */
public class WebServiceLogDTO extends DataObject {

    private static final long serialVersionUID = -1672691074180294948L;
    private String            remoteAddress;
    private Pos               pos;
    private ServiceClient     serviceClient;
    private String            serviceName;
    private String            methodName;
    private String            message;
    private Object            parameter;
    private Throwable         error;
    private String            errorMessage;

    public Throwable getError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getMessage() {
        return message;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object getParameter() {
        return parameter;
    }

    public Pos getPos() {
        return pos;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public ServiceClient getServiceClient() {
        return serviceClient;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setError(final Throwable error) {
        this.error = error;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setMethodName(final String methodName) {
        this.methodName = methodName;
    }

    public void setParameter(final Object parameter) {
        this.parameter = parameter;
    }

    public void setPos(final Pos pos) {
        this.pos = pos;
    }

    public void setRemoteAddress(final String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public void setServiceClient(final ServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }
}
