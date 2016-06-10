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
package nl.strohalm.cyclos.utils.conversion;

import java.io.StringReader;

import nl.strohalm.cyclos.utils.XmlHelper;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

/**
 * Used to handle HTML formatted values, removing script tags and converting paragraphs into line breaks
 * @author luis
 */
public class HtmlConverter implements Converter<String> {

    private static final long          serialVersionUID = -1184040713929519035L;
    private static final String[]      BAD_TAGS         = { "script", "style", "iframe", "form" };
    // it's initialized because this mode (with flag in true) is the most used.
    private static final HtmlConverter INSTANCE         = new HtmlConverter(true);
    // this mode is initialized on demand.
    private static HtmlConverter       INSTANCE_NBSP    = null;
    private static final int           NBSP             = 160;

    public static HtmlConverter instance() {
        return instance(true);
    }

    public static HtmlConverter instance(final boolean removeBlankspaces) {
        if (removeBlankspaces) {
            return INSTANCE;
        } else {
            if (INSTANCE_NBSP == null) {
                INSTANCE_NBSP = new HtmlConverter(false);
            }
            return INSTANCE_NBSP;
        }
    }

    private static void removeBadNodes(final Document document) {
        final NodeList elements = document.getElementsByTagName("*");
        for (int i = 0; i < elements.getLength(); i++) {
            final Element element = (Element) elements.item(i);
            if (ArrayUtils.contains(BAD_TAGS, element.getTagName())) {
                element.getParentNode().removeChild(element);
            }
            final NamedNodeMap attributes = element.getAttributes();
            for (int j = 0; j < attributes.getLength(); j++) {
                final Attr attr = (Attr) attributes.item(j);
                if (attr.getNodeName().startsWith("on")) {
                    // This is an event handler: remove it
                    element.removeAttributeNode(attr);
                }
            }
        }
    }

    private boolean removeBlankspaces;

    private HtmlConverter(final boolean removeBlankspaces) {
        this.removeBlankspaces = removeBlankspaces;
    }

    public String toString(final String string) {
        return string;
    }

    public String valueOf(final String string) {
        if (StringUtils.isBlank(string)) {
            return removeBlankspaces ? null : string;
        }

        final Tidy tidy = new Tidy(); // obtain a new Tidy instance
        tidy.setXHTML(false); // set desired config options using tidy setters
        tidy.setQuiet(true);
        tidy.setShowErrors(0);
        tidy.setShowWarnings(false);
        tidy.setIndentContent(false);
        tidy.setXmlOut(true);

        final Document document = tidy.parseDOM(new StringReader(string), null);
        removeBadNodes(document);

        final NodeList bodies = document.getElementsByTagName("body");
        if (bodies.getLength() == 0) {
            // No body element? return null
            return null;
        } else {
            // Result will contain the xml header plus the body element itself. We need to body content only
            String result = XmlHelper.toString(bodies.item(0));
            result = result.substring(result.indexOf("<body>") + "<body>".length(), result.indexOf("</body>"));
            // Remove the nbsps
            if (removeBlankspaces) {
                int begin = 0;
                while (result.charAt(begin) == NBSP) {
                    begin++;
                    if (begin == result.length()) {
                        // All the string was NBSPs
                        return null;
                    }
                }
                int end = result.length();
                while (result.charAt(end - 1) == NBSP) {
                    end--;
                }
                return StringUtils.trimToNull(result.substring(begin, end));
            } else {
                return StringUtils.trimToNull(result);
            }
        }
    }
}
