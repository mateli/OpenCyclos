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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Class used to read the version reader from a XML file
 * @author luis
 */
public class VersionHistoryReader {
    public static void main(final String[] args) {
        System.out.println(new VersionHistoryReader().read());
    }

    /**
     * Read the version history from classpath
     * @throws ParserConfigurationException
     */
    public VersionHistory read() {
        final Document document = readDocument();
        final VersionHistory history = new VersionHistory();
        final NodeList versions = document.getElementsByTagName("version");
        for (int i = 0; i < versions.getLength(); i++) {
            final Element version = (Element) versions.item(i);
            history.addVersion(readVersion(version));
        }
        history.onFinish();
        return history;
    }

    /**
     * Returns the first node element with the given tag name
     */
    private Element firstChild(final Element element, final String tagName) {
        final NodeList rootElements = element.getElementsByTagName(tagName);
        if (rootElements.getLength() == 0) {
            return null;
        }
        return (Element) rootElements.item(0);
    }

    /**
     * Reads the DOM Document
     */
    private Document readDocument() {
        final InputStream in = getClass().getResourceAsStream("changelog.xml");
        if (in == null) {
            throw new RuntimeException("changelog.xml");
        }
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (final ParserConfigurationException e) {
            throw new RuntimeException("Error creating the document builder", e);
        }
        try {
            return documentBuilder.parse(in);
        } catch (final Exception e) {
            throw new RuntimeException("Error parsing the changelog.xml file", e);
        }
    }

    /**
     * Reads a string list
     */
    private List<String> readItems(final Element element) {
        if (element == null) {
            return Collections.emptyList();
        }
        final NodeList items = element.getElementsByTagName("item");
        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < items.getLength(); i++) {
            final Element item = (Element) items.item(i);
            final String value = readValue(item);
            if (value != null) {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * Read the java migrations from the element
     */
    @SuppressWarnings("unchecked")
    private void readMigrations(final Version version, final Element element) {
        final NodeList migrationsList = element.getElementsByTagName("migration");
        for (int i = 0; i < migrationsList.getLength(); i++) {
            final Element migration = (Element) migrationsList.item(i);
            final String className = StringUtils.trimToNull(migration.getAttribute("class"));
            Class<Migration> clazz;
            try {
                clazz = (Class<Migration>) Class.forName(className);
            } catch (final Exception e) {
                throw new IllegalArgumentException("Illegal migration class name: " + className);
            }
            version.addMigration(migration.getAttribute("database"), clazz);
        }
    }

    /**
     * Read all database statements from the element
     */
    private void readStatements(final Version version, final Element element) {
        final NodeList statementsList = element.getElementsByTagName("statements");
        for (int i = 0; i < statementsList.getLength(); i++) {
            final Element statements = (Element) statementsList.item(i);
            version.addStatements(statements.getAttribute("database"), readItems(statements));
        }
    }

    /**
     * Reads a child node value
     */
    private String readValue(final Element element, final String tagName) {
        return readValue(firstChild(element, tagName));
    }

    /**
     * Reads a node value
     */
    private String readValue(Node node) {
        if (node instanceof Element) {
            node = node.getFirstChild();
        }
        return node == null ? null : StringUtils.trimToNull(node.getNodeValue());
    }

    /**
     * Read a version from the element
     */
    private Version readVersion(final Element element) {
        final String label = element.getAttribute("label");
        final Version version = new Version(label);
        version.setDescription(readValue(element, "description"));
        readStatements(version, element);
        readMigrations(version, element);
        version.setBugFixes(readItems(firstChild(element, "bug-fixes")));
        version.setEnhancements(readItems(firstChild(element, "enhancements")));
        version.setNewLibraries(readItems(firstChild(element, "new-libraries")));
        version.setRemovedLibraries(readItems(firstChild(element, "removed-libraries")));
        version.setNewHelps(readItems(firstChild(element, "new-helps")));
        version.setRemovedHelps(readItems(firstChild(element, "removed-helps")));
        version.setNewStaticFiles(readItems(firstChild(element, "new-static-files")));
        version.setRemovedStaticFiles(readItems(firstChild(element, "removed-static-files")));
        version.setNewTranslationKeys(readItems(firstChild(element, "new-translation-keys")));
        version.setRemovedTranslationKeys(readItems(firstChild(element, "removed-translation-keys")));
        version.setNewSetupKeys(readItems(firstChild(element, "new-setup-keys")));
        version.setRemovedSetupKeys(readItems(firstChild(element, "removed-setup-keys")));
        version.setNewCssClasses(readItems(firstChild(element, "new-css-classes")));
        version.setRemovedCssClasses(readItems(firstChild(element, "removed-css-classes")));
        return version;
    }
}
