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
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.lang.StringUtils;

/**
 * Handle setup arguments. This is not done in Setup class to avoid it's runtime dependency with Apache Commons CLI
 * @author luis
 */
public class Arguments {

    static Options buildOptions(final Locale locale) {
        final ResourceBundle bundle = Setup.getResourceBundle(locale);
        final Options options = new Options();

        // help
        OptionBuilder.withLongOpt("help");
        OptionBuilder.hasArg(false);
        OptionBuilder.withDescription(bundle.getString("arg.help"));
        options.addOption(OptionBuilder.create('?'));

        // force
        OptionBuilder.withLongOpt("force");
        OptionBuilder.hasArg(false);
        OptionBuilder.withDescription(bundle.getString("arg.force"));
        options.addOption(OptionBuilder.create('f'));

        // create database
        OptionBuilder.withLongOpt("database");
        OptionBuilder.hasArg(false);
        OptionBuilder.withDescription(bundle.getString("arg.create-data-base"));
        options.addOption(OptionBuilder.create('d'));

        // export script
        OptionBuilder.withLongOpt("script");
        OptionBuilder.hasOptionalArg();
        OptionBuilder.withArgName("file");
        OptionBuilder.withDescription(bundle.getString("arg.export-script"));
        options.addOption(OptionBuilder.create('s'));

        // create basic data
        OptionBuilder.withLongOpt("basic-data");
        OptionBuilder.hasArg(false);
        OptionBuilder.withDescription(bundle.getString("arg.create-basic-data"));
        options.addOption(OptionBuilder.create('b'));

        // create initial data
        OptionBuilder.withLongOpt("initial-data");
        OptionBuilder.hasArg(false);
        OptionBuilder.withDescription(bundle.getString("arg.create-initial-data"));
        options.addOption(OptionBuilder.create('i'));

        // create sms data
        OptionBuilder.withLongOpt("sms-data");
        OptionBuilder.hasArg(false);
        OptionBuilder.withDescription(bundle.getString("arg.create-sms-data"));
        options.addOption(OptionBuilder.create('m'));

        return options;
    }

    static Setup buildSetupFromArguments(final Locale locale, final String... args) {
        CommandLine commandLine = null;
        final Options options = buildOptions(locale);
        try {
            commandLine = new PosixParser().parse(options, args);
        } catch (final ParseException e) {
            printHelp(options);
        }

        final Setup setup = new Setup();
        setup.setCreateDataBase(commandLine.hasOption('d'));
        final boolean createScript = commandLine.hasOption('s');
        String exportTo = StringUtils.trimToNull(commandLine.getOptionValue('s'));
        if (createScript && exportTo == null) {
            exportTo = "cyclos.ddl";
        }
        setup.setExportScriptTo(exportTo == null ? null : new File(exportTo));
        setup.setCreateInitialData(commandLine.hasOption('i'));
        setup.setCreateBasicData(commandLine.hasOption('b'));
        setup.setCreateSmsData(commandLine.hasOption('m'));

        // Check if help should be printed
        if (!setup.isValid() || commandLine.hasOption('?')) {
            printHelp(options);
        }
        setup.setForce(commandLine.hasOption('f'));

        return setup;
    }

    static void printHelp(final Options options) {
        final HelpFormatter formatter = new HelpFormatter();
        formatter.defaultSyntaxPrefix = "";
        formatter.defaultLongOptPrefix = " --";
        final PrintWriter printWriter = new PrintWriter(Setup.getOut());
        formatter.printHelp(printWriter, HelpFormatter.DEFAULT_WIDTH, Setup.getResourceBundle().getString("help.header") + ":\n", "", options, HelpFormatter.DEFAULT_LEFT_PAD, HelpFormatter.DEFAULT_DESC_PAD, "", false);
        printWriter.flush();
        System.exit(1);
    }

}
