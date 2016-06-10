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

import java.io.File;
import java.util.ResourceBundle;

import org.apache.commons.lang.SystemUtils;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Exports the database table create script to a given file, and prints it to stdout
 * @author luis
 */
public class ExportScript implements Runnable {

    private final ResourceBundle bundle;
    private final Configuration  configuration;
    private File                 exportTo;

    public ExportScript(final Setup setup, final File exportTo) {
        if (exportTo == null) {
            throw new IllegalArgumentException("No file for the script");
        }
        configuration = setup.getConfiguration();
        bundle = setup.getBundle();
        this.exportTo = exportTo;
    }

    /**
     * Export the script for creating the database
     */
    public void run() {
        if (!exportTo.isAbsolute()) {
            exportTo = new File(SystemUtils.getUserDir(), exportTo.getPath());
        }
        // Resolve the file name
        if (exportTo.isDirectory()) {
            exportTo = new File(exportTo, "cyclos.ddl");
        }
        // Create the directory if needed
        final File dir = exportTo.getParentFile();
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IllegalStateException("Could not create directory: " + dir);
            }
        }
        final String fileName = exportTo.getAbsolutePath();

        Setup.out.println(bundle.getString("export-script.start"));

        final SchemaExport schemaExport = new SchemaExport(configuration);
        schemaExport.setDelimiter(";");
        schemaExport.setOutputFile(fileName);
        schemaExport.create(true, false);

        Setup.out.println(bundle.getString("export-script.end") + " " + fileName);
    }

}
