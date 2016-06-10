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
package nl.strohalm.cyclos.entities.accounts.external.filemapping;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import nl.strohalm.cyclos.utils.MessageResolver;
import nl.strohalm.cyclos.utils.PropertyHelper;
import nl.strohalm.cyclos.utils.conversion.Converter;
import nl.strohalm.cyclos.utils.conversion.StringTrimmerConverter;
import nl.strohalm.cyclos.utils.csv.CSVReader;
import nl.strohalm.cyclos.utils.transactionimport.IllegalTransactionFileFormatException;
import nl.strohalm.cyclos.utils.transactionimport.TransactionFileImport;
import nl.strohalm.cyclos.utils.transactionimport.TransactionImportDTO;

import org.apache.commons.lang.StringUtils;

/**
 * Imports external transactions from a CSV format
 * @author luis
 */
public class CSVImport implements TransactionFileImport {

    private static final Converter<String> DEFAULT_CONVERTER = new StringTrimmerConverter(" \t\n\r");
    private final CSVFileMapping           mapping;
    private MessageResolver                messageResolver;

    public CSVImport(final CSVFileMapping mapping, final MessageResolver messageResolver) {
        this.mapping = mapping;
        this.messageResolver = messageResolver;
    }

    @Override
    public List<TransactionImportDTO> readTransactions(final Reader in) throws IllegalTransactionFileFormatException, IOException {
        int lineNumber = 0;

        // Ignore the header lines
        for (int i = 0; i < mapping.getHeaderLines(); i++) {
            ++lineNumber;
            readLine(in);
        }

        // Read each transaction
        final List<TransactionImportDTO> transactions = new LinkedList<TransactionImportDTO>();
        List<String> line;
        while ((line = readLine(in)) != null) {
            ++lineNumber;
            if (line.isEmpty()) {
                continue;
            }
            final TransactionImportDTO transaction = new TransactionImportDTO();
            int column = -1;
            final StringBuilder comments = new StringBuilder();
            for (final FieldMapping field : mapping.getFields()) {
                ++column;
                final Converter<?> converter = resolveConverter(field);
                if (converter == null) {
                    // An ignored field
                    continue;
                }
                final String stringValue = StringUtils.trimToNull(line.get(column));
                try {
                    Object value = converter.valueOf(stringValue);
                    final String property = field.getField().getDtoProperty();

                    // If this is a member_custom_field, the value would be a map
                    if (field.getField().equals(FieldMapping.Field.MEMBER_CUSTOM_FIELD)) {
                        final Map<String, String> map = new HashMap<String, String>();
                        map.put(field.getMemberField().getInternalName(), (String) value);
                        value = map;
                    }

                    PropertyHelper.set(transaction, property, value);
                } catch (final Exception e) {
                    if (comments.length() > 0) {
                        comments.append("\n");
                    }
                    comments.append(
                            messageResolver.message(
                                    "externalTransferImport.error.importing.comments",
                                    field.getName(),
                                    column + 1,
                                    stringValue));
                }
            }
            transaction.setLineNumber(lineNumber);
            transaction.setComments(StringUtils.trimToNull(comments.toString()));
            transactions.add(transaction);
        }

        return transactions;
    }

    private List<String> readLine(final Reader in) throws IOException {
        return CSVReader.readLine(in, mapping.getStringQuote(), mapping.getColumnSeparator());
    }

    private Converter<?> resolveConverter(final FieldMapping field) {
        Converter<?> converter;
        switch (field.getField()) {
            case AMOUNT:
                converter = mapping.getNumberConverter();
                break;
            case NEGATE_AMOUNT:
                converter = mapping.getNegateAmountConverter();
                break;
            case DATE:
                converter = mapping.getDateConverter();
                break;
            case IGNORED:
                converter = null;
                break;
            default:
                converter = DEFAULT_CONVERTER;
                break;
        }
        return converter;
    }

}
