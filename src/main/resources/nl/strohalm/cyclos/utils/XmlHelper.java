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

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlHelper {

    /**
     * Removes all child nodes from the given element
     */
    public static void clear(final Element element) {
        if (element == null) {
            return;
        }
        Node node;
        while ((node = element.getFirstChild()) != null) {
            element.removeChild(node);
        }
    }

    /**
     * Returns a list of children (class Element) with the given tag name
     */
    public static List<Element> getChilden(final Element element, final String tagName) {
        final List<Element> children = new ArrayList<Element>();
        final NodeList nodes = element.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            final Node item = nodes.item(i);
            if (!(item instanceof Element)) {
                continue;
            }
            final Element elem = (Element) item;
            if (tagName.equals(elem.getTagName())) {
                children.add(elem);
            }
        }
        return children;
    }

    /**
     * Reads the DOM Document from an input stream
     */
    public static Document readDocument(final InputStream in) {
        return readDocument(new InputSource(in));
    }

    /**
     * Reads the DOM Document from a XML string
     */
    public static Document readDocument(final String xml) {
        return readDocument(new InputSource(new StringReader(xml)));
    }

    /**
     * Returns the content of the given node as string
     */
    public static String toString(final Node node) {
        final StringWriter out = new StringWriter();
        write(node, new StreamResult(out));
        return out.toString();
    }

    /**
     * Writes the content of the given node into the given stream
     */
    public static void writeNode(final Node node, final OutputStream out) {
        write(node, new StreamResult(out));
    }

    private static Document readDocument(final InputSource inputSource) {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (final ParserConfigurationException e) {
            throw new RuntimeException("Error creating the document builder", e);
        }
        try {
            return documentBuilder.parse(inputSource);
        } catch (final Exception e) {
            throw new RuntimeException("Error parsing xml file", e);
        }
    }

    private static void write(final Node node, final Result result) {
        try {
            // Prepare the DOM document for writing
            final Source source = new DOMSource(node);

            // Write the DOM document to the file
            final Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);
        } catch (final TransformerConfigurationException e) {
        } catch (final TransformerException e) {
        }

    }

}
