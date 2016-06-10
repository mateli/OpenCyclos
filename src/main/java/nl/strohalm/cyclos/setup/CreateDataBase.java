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
package nl.strohalm.cyclos.setup;

import java.util.ResourceBundle;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Creates the database tables
 * @author luis
 */
public class CreateDataBase implements Runnable {

    private final Configuration  configuration;
    private final ResourceBundle bundle;

    public CreateDataBase(final Setup setup) {
        configuration = setup.getConfiguration();
        bundle = setup.getBundle();
    }

    /**
     * Create the database
     */
    public void run() {
        Setup.out.println(bundle.getString("create-database.start"));
        final SchemaExport schemaExport = new SchemaExport(configuration);
        schemaExport.create(false, true);
        Setup.out.println(bundle.getString("create-database.end"));
    }

}
