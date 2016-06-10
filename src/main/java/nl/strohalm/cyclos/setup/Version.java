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

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * Represents a version on the upgrade descriptor.
 * @author luis
 */
public class Version implements Serializable {
    private static final long                   serialVersionUID = -823920658138712571L;
    private final String                        label;
    private String                              description;
    private Map<String, List<String>>           statementsByDataBase;
    private Map<String, List<Class<Migration>>> migrationsByDataBase;
    private List<String>                        newHelps;
    private List<String>                        removedHelps;
    private List<String>                        newStaticFiles;
    private List<String>                        removedStaticFiles;
    private List<String>                        newTranslationKeys;
    private List<String>                        removedTranslationKeys;
    private List<String>                        newSetupKeys;
    private List<String>                        removedSetupKeys;
    private List<String>                        newLibraries;
    private List<String>                        removedLibraries;
    private List<String>                        newCssClasses;
    private List<String>                        removedCssClasses;
    private List<String>                        bugFixes;
    private List<String>                        enhancements;

    public Version(final String label) {
        if (StringUtils.isEmpty(label)) {
            throw new IllegalArgumentException("Empty label");
        }
        this.label = label;
    }

    public void addMigration(String database, final Class<Migration> clazz) {
        if (clazz == null) {
            return;
        }
        if (!Migration.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException("Invalid migration class: " + clazz.getName());
        }
        database = database.toLowerCase();
        if (migrationsByDataBase == null) {
            migrationsByDataBase = new HashMap<String, List<Class<Migration>>>();
        }
        List<Class<Migration>> migrations = migrationsByDataBase.get(database);
        if (migrations == null) {
            migrations = new ArrayList<Class<Migration>>();
            migrationsByDataBase.put(database, migrations);
        }
        migrations.add(clazz);
    }

    public void addStatements(String database, final List<String> statements) {
        if (statements == null || statements.isEmpty()) {
            return;
        }
        database = database.toLowerCase();
        if (statementsByDataBase == null) {
            statementsByDataBase = new HashMap<String, List<String>>();
        }
        List<String> currentStatements = statementsByDataBase.get(database);
        if (currentStatements == null) {
            currentStatements = new ArrayList<String>();
            statementsByDataBase.put(database, currentStatements);
        }
        currentStatements.addAll(statements);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Version)) {
            return false;
        }
        final Version v = (Version) obj;
        return label.equals(v.label);
    }

    public List<String> getBugFixes() {
        return bugFixes;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getEnhancements() {
        return enhancements;
    }

    public String getLabel() {
        return label;
    }

    public List<Class<Migration>> getMigrations(String database) {
        database = StringUtils.trimToNull(database);
        if (migrationsByDataBase == null && database != null) {
            return null;
        }
        for (final Map.Entry<String, List<Class<Migration>>> entry : migrationsByDataBase.entrySet()) {
            if (database.equalsIgnoreCase(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Map<String, List<Class<Migration>>> getMigrationsByDataBase() {
        return migrationsByDataBase;
    }

    public List<String> getNewCssClasses() {
        return newCssClasses;
    }

    public List<String> getNewHelps() {
        return newHelps;
    }

    public List<String> getNewLibraries() {
        return newLibraries;
    }

    public List<String> getNewSetupKeys() {
        return newSetupKeys;
    }

    public List<String> getNewStaticFiles() {
        return newStaticFiles;
    }

    public List<String> getNewTranslationKeys() {
        return newTranslationKeys;
    }

    public List<String> getRemovedCssClasses() {
        return removedCssClasses;
    }

    public List<String> getRemovedHelps() {
        return removedHelps;
    }

    public List<String> getRemovedLibraries() {
        return removedLibraries;
    }

    public List<String> getRemovedSetupKeys() {
        return removedSetupKeys;
    }

    public List<String> getRemovedStaticFiles() {
        return removedStaticFiles;
    }

    public List<String> getRemovedTranslationKeys() {
        return removedTranslationKeys;
    }

    public List<String> getStatements(String database) {
        database = StringUtils.trimToNull(database);
        if (statementsByDataBase == null && database != null) {
            return null;
        }
        for (final Map.Entry<String, List<String>> entry : statementsByDataBase.entrySet()) {
            if (database.equalsIgnoreCase(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Map<String, List<String>> getStatementsByDataBase() {
        return statementsByDataBase;
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }

    public boolean sameAs(String label) {
        label = StringUtils.trimToNull(label);
        if (label == null) {
            return false;
        }
        return this.label.equalsIgnoreCase(label);
    }

    public void setBugFixes(final List<String> bugFixes) {
        this.bugFixes = bugFixes;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setEnhancements(final List<String> enhancements) {
        this.enhancements = enhancements;
    }

    public void setMigrationsByDataBase(final Map<String, List<Class<Migration>>> migrationsByDataBase) {
        this.migrationsByDataBase = migrationsByDataBase;
    }

    public void setNewCssClasses(final List<String> newCssClasses) {
        this.newCssClasses = newCssClasses;
    }

    public void setNewHelps(final List<String> newHelps) {
        this.newHelps = newHelps;
    }

    public void setNewLibraries(final List<String> newLibraries) {
        this.newLibraries = newLibraries;
    }

    public void setNewSetupKeys(final List<String> newSetupKeys) {
        this.newSetupKeys = newSetupKeys;
    }

    public void setNewStaticFiles(final List<String> newStaticFiles) {
        this.newStaticFiles = newStaticFiles;
    }

    public void setNewTranslationKeys(final List<String> newTranslationKeys) {
        this.newTranslationKeys = newTranslationKeys;
    }

    public void setRemovedCssClasses(final List<String> removedCssClasses) {
        this.removedCssClasses = removedCssClasses;
    }

    public void setRemovedHelps(final List<String> removedHelps) {
        this.removedHelps = removedHelps;
    }

    public void setRemovedLibraries(final List<String> removedLibraries) {
        this.removedLibraries = removedLibraries;
    }

    public void setRemovedSetupKeys(final List<String> removedSetupKeys) {
        this.removedSetupKeys = removedSetupKeys;
    }

    public void setRemovedStaticFiles(final List<String> removedStaticFiles) {
        this.removedStaticFiles = removedStaticFiles;
    }

    public void setRemovedTranslationKeys(final List<String> removedTranslationKeys) {
        this.removedTranslationKeys = removedTranslationKeys;
    }

    public void setStatementsByDataBase(final Map<String, List<String>> statementsByDataBase) {
        this.statementsByDataBase = statementsByDataBase;
    }

    @Override
    public String toString() {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        final String header = String.format("Version %s", label);
        pw.println(header);
        pw.println(StringUtils.repeat("-", header.length()));
        if (StringUtils.isNotEmpty(description)) {
            pw.println(description);
        }
        pw.println();
        appendList(pw, enhancements, "New / modified functions", " * ", null);
        appendList(pw, bugFixes, "Bug fixes", " * ", null);
        appendList(pw, newLibraries, "New library dependencies", " * ", null);
        appendList(pw, removedLibraries, "Removed library dependencies", " * ", null);
        appendList(pw, newHelps, "New help files", " * ", null);
        appendList(pw, removedHelps, "Removed help files", " * ", null);
        appendList(pw, newStaticFiles, "New static files", " * ", null);
        appendList(pw, removedStaticFiles, "Removed static files", " * ", null);
        appendList(pw, newCssClasses, "New CSS classes", " * ", null);
        appendList(pw, removedCssClasses, "Removed CSS classes", " * ", null);
        appendList(pw, newTranslationKeys, "New application translation keys", " * ", null);
        appendList(pw, removedTranslationKeys, "Removed application translation keys", " * ", null);
        appendList(pw, newSetupKeys, "New setup translation keys", " * ", null);
        appendList(pw, removedSetupKeys, "Removed setup translation keys", " * ", null);
        pw.close();
        return sw.toString();
    }

    private void appendList(final PrintWriter out, final List<String> list, final String header, final String prefix, final String suffix) {
        if (list == null || list.isEmpty()) {
            return;
        }
        out.println(header);
        for (final String item : list) {
            final StringBuilder sb = new StringBuilder();
            if (StringUtils.isNotEmpty(prefix)) {
                sb.append(prefix);
            }
            sb.append(item);
            if (StringUtils.isNotEmpty(suffix)) {
                sb.append(suffix);
            }
            out.println(sb);
        }
        out.println();
    }
}
