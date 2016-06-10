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
package nl.strohalm.cyclos;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * Returns the cyclos properties
 * 
 * @author luis
 */
public class CyclosConfiguration {

    private static final String MAX_PAYMENT_REQUEST_SENDER_THREADS   = "cyclos.maxPaymentRequestSenderThreads";
    private static final String MAX_SMS_SENDER_THREADS               = "cyclos.maxSmsSenderThreads";
    private static final String MAX_MAIL_SENDER_THREADS              = "cyclos.maxMailSenderThreads";
    private static final String TRANSACTION_QUEUE_CAPACITY           = "cyclos.transaction.queueCapacity";
    private static final String TRANSACTION_MAX_POOL_SIZE            = "cyclos.transaction.maxPoolSize";
    private static final String TRANSACTION_CORE_POOL_SIZE           = "cyclos.transaction.corePoolSize";
    private static final String HIBERNATE_C3P0_MAX_POOL_SIZE         = "hibernate.c3p0.maxPoolSize";

    private static final int    HIBERNATE_C3P0_MAX_POOL_SIZE_DEFAULT = 20;

    public static final String  CYCLOS_PROPERTIES_FILE               = "/cyclos.properties";

    public static Properties getCyclosProperties() throws IOException {
        final Properties properties = new Properties();
        properties.put(MAX_MAIL_SENDER_THREADS, "5");
        properties.put(MAX_SMS_SENDER_THREADS, "50");
        properties.put(MAX_PAYMENT_REQUEST_SENDER_THREADS, "50");

        properties.load(CyclosConfiguration.class.getResourceAsStream(CYCLOS_PROPERTIES_FILE));

        final String dbMaxPoolSizeStr = properties.getProperty(HIBERNATE_C3P0_MAX_POOL_SIZE);
        final Integer dbMaxPoolSize = StringUtils.isEmpty(dbMaxPoolSizeStr) ? HIBERNATE_C3P0_MAX_POOL_SIZE_DEFAULT : Integer.parseInt(dbMaxPoolSizeStr);

        ensureProperty(TRANSACTION_CORE_POOL_SIZE, dbMaxPoolSize, properties);
        ensureProperty(TRANSACTION_MAX_POOL_SIZE, dbMaxPoolSize * 3, properties);
        ensureProperty(TRANSACTION_QUEUE_CAPACITY, dbMaxPoolSize * 5, properties);

        return properties;
    }

    private static void ensureProperty(final String property, final Integer value, final Properties properties) {
        if (!properties.containsKey(property)) {
            properties.put(property, value.toString());
        }
    }
}
