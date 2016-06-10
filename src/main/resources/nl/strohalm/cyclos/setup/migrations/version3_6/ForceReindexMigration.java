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
package nl.strohalm.cyclos.setup.migrations.version3_6;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import nl.strohalm.cyclos.setup.TraceableMigration;
import nl.strohalm.cyclos.utils.JDBCWrapper;
import nl.strohalm.cyclos.utils.lucene.IndexHandler;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A migration which removes the indexes directory, forcing a reindex
 * 
 * @author luis
 */
public class ForceReindexMigration implements TraceableMigration {
    private static final Log LOG = LogFactory.getLog(ForceReindexMigration.class);

    public int execute(final JDBCWrapper jdbc) throws SQLException {
        final File indexRoot = IndexHandler.resolveIndexRoot();
        if (indexRoot.exists()) {
            try {
                FileUtils.deleteDirectory(indexRoot);
                LOG.info("The index root directory (" + indexRoot + ") was removed successfully");
            } catch (final IOException e) {
                // Ok, ignore
                LOG.warn("Error removing the index root directory (" + indexRoot + "). Error: " + e.getMessage());
            }
        }
        return 0;
    }

}
