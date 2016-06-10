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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import nl.strohalm.cyclos.CyclosConfiguration;
import nl.strohalm.cyclos.utils.PropertiesHelper;
import nl.strohalm.cyclos.utils.conversion.LocaleConverter;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Generate cyclos default data
 * @author luis
 */
public class Setup {

    public static final String[] SPRING_CONFIG_FILES       = { "/nl/strohalm/cyclos/spring/persistence.xml" };
    public static PrintStream    out                       = System.out;
    private static final String  RESOURCE_BUNDLE_BASE_NAME = "nl.strohalm.cyclos.setup.CyclosSetup";

    public static PrintStream getOut() {
        return Setup.out;
    }

    public static ResourceBundle getResourceBundle() {
        return getResourceBundle(Locale.getDefault());
    }

    public static ResourceBundle getResourceBundle(final Locale locale) {
        return PropertiesHelper.readBundle(RESOURCE_BUNDLE_BASE_NAME, locale);
    }

    /**
     * The external entry point
     */
    public static void main(final String... args) throws IOException {
        DataBaseConfiguration.SKIP = true;
        Locale locale = resolveLocale();
        final Setup setup = Arguments.buildSetupFromArguments(locale, args);
        setup.locale = locale;
        setup.showInitializing();
        final ApplicationContext applicationContext = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILES);
        setup.configuration = applicationContext.getBean(Configuration.class);
        setup.sessionFactory = applicationContext.getBean(SessionFactory.class);
        setup.execute();
        System.exit(0);
    }

    public static void setOut(final PrintStream out) {
        Setup.out = out;
    }

    private static Locale resolveLocale() throws IOException {
        final LocaleConverter localeConverter = LocaleConverter.instance();
        final Properties cyclosProperties = CyclosConfiguration.getCyclosProperties();
        final String defaultLocale = localeConverter.toString(Locale.getDefault());
        final String localeStr = cyclosProperties.getProperty("cyclos.embedded.locale", defaultLocale);
        return localeConverter.valueOf(localeStr);
    }

    private ResourceBundle bundle;
    private Configuration  configuration;
    private boolean        createBasicData;
    private boolean        createDataBase;
    private boolean        createSetupData;
    private boolean        createInitialData;
    private boolean        createSmsData;
    private File           exportScriptTo;
    private boolean        force;
    private Session        session;
    private SessionFactory sessionFactory;
    private Locale         locale;

    public Setup() {
    }

    public Setup(final Configuration configuration, final SessionFactory sessionFactory) {
        this.configuration = configuration;
        this.sessionFactory = sessionFactory;
    }

    public boolean execute() {
        checkBundle();

        if (!isValid()) {
            throw new IllegalStateException("Nothing to execute");
        }

        // Prompt for confirmation if not forced
        if (!force && !promptConfirm()) {
            return false;
        }

        // Configure Hibernate if necessary
        if (configuration == null || sessionFactory == null) {
            throw new IllegalStateException("Persistence not configured");
        }
        // Execute the actions
        session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            if (createDataBase) {
                new CreateDataBase(this).run();
            }
            if (exportScriptTo != null) {
                new ExportScript(this, exportScriptTo).run();
            }
            if (createSetupData) {
                new CreateBasicData(this, true).run();
            }
            if (createBasicData) {
                new CreateBasicData(this, false).run();
            }
            if (createInitialData) {
                new CreateInitialData(this).run();
            }
            if (createSmsData) {
                new CreateSmsData(this).run();
            }
            transaction.commit();
            out.println(bundle.getString("setup.end"));

            if (Boolean.getBoolean("cyclos.standalone")) {
                System.out.println(bundle.getString("setup.standalone.starting"));
            }

            return true;
        } catch (final Exception e) {
            transaction.rollback();
            e.printStackTrace(out);
            return false;
        } finally {
            try {
                session.close();
            } catch (final Exception e) {
                out.println("Error closing session: " + e);
            }
        }
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public File getExportScriptTo() {
        return exportScriptTo;
    }

    public Locale getLocale() {
        return locale;
    }

    public Session getSession() {
        return session;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public boolean isCreateBasicData() {
        return createBasicData;
    }

    public boolean isCreateDataBase() {
        return createDataBase;
    }

    public boolean isCreateInitialData() {
        return createInitialData;
    }

    public boolean isForce() {
        return force;
    }

    public boolean isValid() {
        return createDataBase || exportScriptTo != null || createBasicData || createInitialData || createSmsData;
    }

    public void setConfiguration(final Configuration configuration) {
        this.configuration = configuration;
    }

    public void setCreateBasicData(final boolean createBasicData) {
        this.createBasicData = createBasicData;
    }

    public void setCreateDataBase(final boolean createDataBase) {
        this.createDataBase = createDataBase;
    }

    public void setCreateInitialData(final boolean createInitialData) {
        this.createInitialData = createInitialData;
    }

    public void setCreateSetupData(final boolean createSetupData) {
        this.createSetupData = createSetupData;
    }

    public void setCreateSmsData(final boolean createSmsData) {
        this.createSmsData = createSmsData;
    }

    public void setExportScriptTo(final File exportScriptTo) {
        this.exportScriptTo = exportScriptTo;
    }

    public void setForce(final boolean force) {
        this.force = force;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private void checkBundle() {
        if (bundle == null) {
            bundle = getResourceBundle(locale);
        }
    }

    /**
     * Prompt for confirmation
     */
    private boolean promptConfirm() {

        final List<String> executedOperations = new ArrayList<String>();
        boolean scriptOnly = false;
        if (exportScriptTo != null) {
            scriptOnly = true;
            executedOperations.add(MessageFormat.format(bundle.getString("action.export-script"), exportScriptTo.getAbsolutePath()));
        }
        if (createDataBase) {
            scriptOnly = false;
            executedOperations.add(bundle.getString("action.create-data-base"));
        }
        if (createBasicData) {
            scriptOnly = false;
            executedOperations.add(bundle.getString("action.create-basic-data"));
        }
        if (createInitialData) {
            scriptOnly = false;
            executedOperations.add(bundle.getString("action.create-initial-data"));
        }

        if (createSmsData) {
            scriptOnly = false;
            executedOperations.add(bundle.getString("action.create-sms-data"));
        }

        if (scriptOnly) {
            return true;
        }

        final String yes = bundle.getString("confirm.yes");
        final String no = bundle.getString("confirm.no");

        out.println(bundle.getString("setup.execute") + ": \n * " + StringUtils.join(executedOperations.iterator(), "\n * "));
        out.println(bundle.getString("setup.confirm") + " (" + yes + "/" + no + ")");
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            final String line = reader.readLine();
            if (line == null || line.length() == 0 || !line.equalsIgnoreCase(yes)) {
                out.println(bundle.getString("setup.aborted"));
                return false;
            }
            return true;
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showInitializing() {
        checkBundle();
        out.println(bundle.getString("setup.initializing"));
    }
}
