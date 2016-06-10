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
package nl.strohalm.cyclos.services.customization;

import java.util.List;
import java.util.Locale;
import java.util.Properties;

import nl.strohalm.cyclos.entities.customization.translationMessages.TranslationMessage;
import nl.strohalm.cyclos.entities.customization.translationMessages.TranslationMessageQuery;
import nl.strohalm.cyclos.services.Service;
import nl.strohalm.cyclos.utils.validation.ValidationException;

/**
 * Service interface for message resources customization (translation file)
 * 
 * @author luis
 */
public interface TranslationMessageService extends Service {

    /**
     * Adds a listener for translations change events
     */
    void addTranslationChangeListener(TranslationChangeListener listener);

    /**
     * Load the messages as a Properties object
     */
    Properties exportAsProperties();

    /**
     * Store the properties
     */
    void importFromProperties(Properties properties, MessageImportType importType);

    /**
     * Loads the message with the given identifier
     */
    TranslationMessage load(Long id);

    /**
     * Returns the Properties from file, for the given locale
     */
    Properties readFile(Locale locale);

    /**
     * Removes the specified messages, returning the number of removed
     */
    int remove(Long... ids);

    /**
     * Save the value of a given key
     */
    TranslationMessage save(TranslationMessage translationMessage);

    /**
     * Searches for messages
     */
    List<TranslationMessage> search(TranslationMessageQuery query);

    /**
     * Validates a given message
     */
    void validate(TranslationMessage translationMessage) throws ValidationException;
}
