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
package nl.strohalm.cyclos.services.infotexts;

import java.util.List;

import nl.strohalm.cyclos.dao.infotexts.InfoTextDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.exceptions.EntityNotFoundException;
import nl.strohalm.cyclos.entities.infotexts.InfoText;
import nl.strohalm.cyclos.entities.infotexts.InfoTextQuery;
import nl.strohalm.cyclos.utils.MessageResolver;
import nl.strohalm.cyclos.utils.validation.PeriodValidation;
import nl.strohalm.cyclos.utils.validation.Validator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

public class InfoTextServiceImpl implements InfoTextServiceLocal {

    private InfoTextDAO     infoTextDao;
    private MessageResolver messageResolver;

    public String getInfoTextSubject(String alias) {
        alias = StringUtils.trimToEmpty(alias);
        final InfoTextQuery query = new InfoTextQuery();
        query.setOnlyActive(true);
        query.setAlias(alias);
        final List<InfoText> result = infoTextDao.search(query);
        final int size = result.size();
        if (size == 0) {
            if (StringUtils.isEmpty(alias)) {
                return messageResolver.message(INFO_TEXT_EMPTY_PROPERTY);
            } else {
                return messageResolver.message(INFO_TEXT_NOT_MATCH_PROPERTY, alias);
            }
        } else if (size == 1) {
            return result.get(0).getSubject();
        } else { // size > 1
            final int index = RandomUtils.nextInt(result.size());
            return result.get(index).getSubject();
        }
    }

    public InfoText load(final long id, final Relationship... fetch) {
        return infoTextDao.load(id, fetch);
    }

    public InfoText loadByAliasForWebServices(String alias) {
        alias = StringUtils.trimToEmpty(alias);
        final InfoTextQuery query = new InfoTextQuery();
        query.setOnlyActive(true);
        query.setAlias(alias);
        final List<InfoText> result = infoTextDao.search(query);
        final int size = result.size();
        if (size == 0) {
            throw new EntityNotFoundException(InfoText.class);
        } else if (size == 1) {
            return result.get(0);
        } else { // size > 1
            final int index = RandomUtils.nextInt(result.size());
            return result.get(index);
        }
    }

    public InfoText loadForWebServices(final long id) {
        return load(id);
    }

    public int remove(final Long... ids) {
        return infoTextDao.delete(ids);
    }

    public InfoText save(final InfoText infoText) {
        validate(infoText);
        if (infoText.isTransient()) {
            return infoTextDao.insert(infoText);
        } else {
            return infoTextDao.update(infoText);
        }
    }

    public List<InfoText> search(final InfoTextQuery query) {
        return infoTextDao.search(query);
    }

    public List<InfoText> searchForWebServices(final InfoTextQuery query) {
        return search(query);
    }

    public void setInfoTextDao(final InfoTextDAO infoTextDao) {
        this.infoTextDao = infoTextDao;
    }

    public void setMessageResolver(final MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public void validate(final InfoText infoText) {
        getValidator().validate(infoText);

    }

    private Validator getValidator() {
        final Validator validator = new Validator();
        validator.property("subject").required();
        validator.property("aliases").required();
        validator.property("validity").add(new PeriodValidation(PeriodValidation.ValidationType.VALIDATE_RANGE));
        return validator;
    }

}
