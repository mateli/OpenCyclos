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
package nl.strohalm.cyclos.themes;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Defines methods for managing themes
 * @author luis
 */
public interface ThemeHandler {

    /**
     * Exports the current settings with meta-data retrieved by the given theme object. The output stream is closed after writing all data
     */
    void export(Theme theme, OutputStream out);

    /**
     * Imports a new theme from the given file. The input stream is closed after reading all data
     */
    void importNew(String fileName, InputStream in);

    /**
     * Lists available themes
     */
    List<Theme> list();

    /**
     * Removes the theme with the given file name
     */
    void remove(String fileName);

    /**
     * Selects the theme with the given file name
     */
    void select(String fileName);

    /**
     * Checks if the given theme descriptor is valid for exporting a theme
     */
    void validateForExport(Theme theme) throws ValidationException;
}
