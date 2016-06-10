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
package nl.strohalm.cyclos.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains helper methods for handling cascading style sheets
 * @author luis
 */
public class CSSHelper {

    /**
     * A pattern to match a url(file.extension). There may be spaces or the file may be enclosed in single or double quotes
     */
    private static final Pattern URL = Pattern.compile("url\\s*\\([\\s'\"]*([\\w\\.\\-]+)[\\s'\"]*\\)", Pattern.CASE_INSENSITIVE);

    /**
     * Returns a list with all URLs referenced by the given css code
     */
    public static List<String> resolveURLs(final String css) {
        final Matcher matcher = URL.matcher(css);
        final List<String> urls = new ArrayList<String>();
        while (matcher.find()) {
            final String url = matcher.group(1);
            urls.add(url);
        }
        return urls;
    }
}
