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

import java.sql.ResultSet;
import java.sql.SQLException;

import nl.strohalm.cyclos.setup.TraceableMigration;
import nl.strohalm.cyclos.utils.JDBCWrapper;

/**
 * A migration class which will change the data types of the columns image and thumbnails of the images table to mediumblob.
 * 
 * @author jcomas
 */
public class ChangeImagesColumnsMigration implements TraceableMigration {

    public int execute(final JDBCWrapper jdbc) throws SQLException {
        final ResultSet imageResult = jdbc.query("show columns from images where Field = 'image'");
        final ResultSet thumbnailResult = jdbc.query("show columns from images where Field = 'thumbnail'");
        try {
            imageResult.next();
            final String imageColumnType = imageResult.getString("Type");
            final Boolean imageNullable = imageResult.getBoolean("Null");

            final boolean shouldAlterImageColumn = !imageColumnType.equals("mediumblob") || imageNullable;

            thumbnailResult.next();
            final String thumbnailColumnType = thumbnailResult.getString("Type");
            final Boolean thumbnailNullable = thumbnailResult.getBoolean("Null");

            final boolean shouldAlterThumbnailColumn = !thumbnailColumnType.equals("mediumblob") || !thumbnailNullable;

            int modifiedRows = 0;
            if (shouldAlterImageColumn && shouldAlterThumbnailColumn) {
                modifiedRows = jdbc.execute("alter table images modify column image mediumblob not null, modify column thumbnail mediumblob default null");
            } else if (shouldAlterImageColumn) {
                modifiedRows = jdbc.execute("alter table images modify column image mediumblob not null");
            } else if (shouldAlterThumbnailColumn) {
                modifiedRows = jdbc.execute("alter table images modify column thumbnail mediumblob default null");
            }
            return modifiedRows;

        } finally {
            JDBCWrapper.closeQuietly(imageResult);
            JDBCWrapper.closeQuietly(thumbnailResult);
        }
    }

}
