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
package nl.strohalm.cyclos.services.accounts.cards;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import nl.strohalm.cyclos.dao.accounts.cards.CardDAO;
import nl.strohalm.cyclos.dao.accounts.cards.CardLogDAO;
import nl.strohalm.cyclos.entities.Relationship;
import nl.strohalm.cyclos.entities.accounts.cards.Card;
import nl.strohalm.cyclos.entities.accounts.cards.Card.Status;
import nl.strohalm.cyclos.entities.accounts.cards.CardLog;
import nl.strohalm.cyclos.entities.accounts.cards.CardQuery;
import nl.strohalm.cyclos.entities.accounts.cards.CardType;
import nl.strohalm.cyclos.entities.accounts.cards.CardType.CardSecurityCode;
import nl.strohalm.cyclos.entities.groups.BasicGroupSettings.PasswordPolicy;
import nl.strohalm.cyclos.entities.members.Element;
import nl.strohalm.cyclos.entities.members.FullTextMemberQuery;
import nl.strohalm.cyclos.entities.members.Member;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;
import nl.strohalm.cyclos.services.InitializingService;
import nl.strohalm.cyclos.services.access.AccessServiceLocal;
import nl.strohalm.cyclos.services.elements.BulkMemberActionResultVO;
import nl.strohalm.cyclos.services.elements.ElementServiceLocal;
import nl.strohalm.cyclos.services.fetch.FetchServiceLocal;
import nl.strohalm.cyclos.utils.CacheCleaner;
import nl.strohalm.cyclos.utils.DateHelper;
import nl.strohalm.cyclos.utils.HashHandler;
import nl.strohalm.cyclos.utils.RangeConstraint;
import nl.strohalm.cyclos.utils.RelationshipHelper;
import nl.strohalm.cyclos.utils.access.LoggedUser;
import nl.strohalm.cyclos.utils.validation.LengthValidation;
import nl.strohalm.cyclos.utils.validation.ValidationError;
import nl.strohalm.cyclos.utils.validation.ValidationException;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

/**
 * Card Service Implementation
 * @author rodrigo
 */
public class CardServiceImpl implements CardServiceLocal, InitializingService {

    private FetchServiceLocal   fetchService;
    private CardDAO             cardDao;
    private CardLogDAO          cardLogDao;
    private ElementServiceLocal elementService;
    private AccessServiceLocal  accessService;

    private static final char   NUMERIC_CONSTANT = '#';

    private HashHandler         hashHandler;

    @Override
    public Card activateCard(Card card, String cardCode) {
        if (card != null && card.getId() > 0) {
            final List<Card> activeCards = cardDao.searchActiveCards(card.getOwner().getId(), card.getId());
            if (activeCards != null) {
                for (final Card activeCard : activeCards) {
                    cancelCard(activeCard);
                }
            }
            card = fetchService.fetch(card, Card.Relationships.CARD_TYPE, RelationshipHelper.nested(Card.Relationships.OWNER, Element.Relationships.USER));
            if (card.getCardType().getCardSecurityCode() == CardType.CardSecurityCode.MANUAL) {
                validateCardSecurityCode(card, cardCode);
                if (!card.getCardType().isShowCardSecurityCode()) {
                    cardCode = hashHandler.hash(card.getOwner().getUser().getSalt(), cardCode);
                }
                card.setCardSecurityCode(cardCode);
            }
            card.setStatus(Card.Status.ACTIVE);
            card.setActivationDate(Calendar.getInstance());
            cardDao.update(card, true);
            generateLog(card);
        }
        return card;
    }

    @Override
    public Card blockCard(final Card card) {
        if (card != null && card.getId() > 0) {
            card.setStatus(Card.Status.BLOCKED);
            cardDao.update(card, true);
            generateLog(card);
        }

        return card;
    }

    @Override
    @SuppressWarnings("unchecked")
    public BulkMemberActionResultVO bulkGenerateNewCard(final FullTextMemberQuery query, final boolean generateForPending, final boolean generateForActive) {

        int changed = 0;
        int unchanged = 0;
        boolean generateNewCard = true;
        // force the result type to ITERATOR to avoid load all members in memory
        query.setIterateAll();
        final List<Member> members = (List<Member>) elementService.fullTextSearch(query);
        final CacheCleaner cacheCleaner = new CacheCleaner(fetchService);
        for (Member member : members) {
            member = fetchService.fetch(member, Element.Relationships.GROUP);
            if (member.getMemberGroup().getCardType() != null) {
                final Card card = cardDao.getLastCard(member.getId());
                if (card != null) {
                    if (card.getStatus().equals(Card.Status.PENDING) && !generateForPending) {
                        generateNewCard = false;
                    }
                    if (card.getStatus().equals(Card.Status.ACTIVE) && !generateForActive) {
                        generateNewCard = false;
                    }
                }
            } else {
                generateNewCard = false;
            }

            if (generateNewCard) {
                generateNewCard(member);
                changed++;
            } else {
                unchanged++;
            }

            generateNewCard = true;

            cacheCleaner.clearCache();
        }
        return new BulkMemberActionResultVO(changed, unchanged);
    }

    @Override
    public void cancelAllMemberCards(final Member member) {
        cardDao.cancelAllMemberCards(member);
    }

    @Override
    public Card cancelCard(final Card card) {
        if (card != null && card.getId() > 0) {
            card.setStatus(Card.Status.CANCELED);
            cardDao.update(card, true);
            generateLog(card);
        }
        return card;
    }

    @Override
    public Card changeCardCode(Card card, String code) {
        card = fetchService.fetch(card, RelationshipHelper.nested(Card.Relationships.OWNER, Element.Relationships.USER), Card.Relationships.CARD_TYPE);
        if (card.getCardType().getCardSecurityCode() != CardSecurityCode.MANUAL) {
            throw new PermissionDeniedException();
        }
        validateCardSecurityCode(card, code);
        if (!card.getCardType().isShowCardSecurityCode()) {
            code = hashHandler.hash(code, card.getOwner().getUser().getSalt());
        }
        card.setCardSecurityCode(code);
        cardDao.update(card);
        return card;
    }

    @Override
    public Card generateNewCard(final Member member) {

        final Card lastCard = cardDao.getLastCard(member.getId());
        if (lastCard != null && lastCard.getStatus() == Card.Status.PENDING) {
            cancelCard(lastCard);
        }
        final Card newCard = buildNewCard(member);
        cardDao.insert(newCard, false);
        generateLog(newCard);
        return newCard;
    }

    @Override
    public Card getActiveCard(final Member member) {
        for (final Card card : getMemberCards(member.getId())) {
            if (card.getStatus() == Status.ACTIVE) {
                return card;
            }
        }
        return null;
    }

    public CardDAO getCardDao() {
        return cardDao;
    }

    public CardLogDAO getCardLogDao() {
        return cardLogDao;
    }

    @Override
    public void initializeService() {
        processCards(Calendar.getInstance());
    }

    @Override
    public Card load(final long cardId, final Relationship... fetch) {
        return cardDao.load(cardId, fetch);
    }

    @Override
    public Card loadByNumber(final BigInteger number, final Relationship... fetch) {
        return cardDao.loadByNumber(number, fetch);
    }

    @Override
    public List<Card> processCards(final Calendar time) {
        return expireCards(time);
    }

    @Override
    public List<Card> search(final CardQuery query) {
        return cardDao.search(query);
    }

    public void setAccessServiceLocal(final AccessServiceLocal accessService) {
        this.accessService = accessService;
    }

    public void setCardDao(final CardDAO cardDao) {
        this.cardDao = cardDao;
    }

    public void setCardLogDao(final CardLogDAO cardLogDao) {
        this.cardLogDao = cardLogDao;
    }

    public void setElementServiceLocal(final ElementServiceLocal elementService) {
        this.elementService = elementService;
    }

    public void setFetchServiceLocal(final FetchServiceLocal fetchService) {
        this.fetchService = fetchService;
    }

    public void setHashHandler(final HashHandler hashHandler) {
        this.hashHandler = hashHandler;
    }

    @Override
    public Card unblockCard(final Card card) {
        if (card != null && card.getId() > 0) {
            card.setStatus(Card.Status.ACTIVE);
            cardDao.update(card, true);
            generateLog(card);
        }
        return card;
    }

    @Override
    public void unblockSecurityCode(final Card card) {
        accessService.unblockCardSecurityCode(card.getCardNumber());
    }

    private BigInteger buildCardNumber(final String cardFormatNumber) {

        BigInteger generatedNumber;
        boolean exists = false;
        do {
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cardFormatNumber.length(); i++) {
                final char c = cardFormatNumber.charAt(i);
                if (Character.isDigit(c)) {
                    sb.append(c);
                } else if (c == NUMERIC_CONSTANT) {
                    final int next = i == 0 ? RandomUtils.nextInt(9) + 1 : RandomUtils.nextInt(10); // never generates zero for the first digit
                    sb.append(next);
                }
            }
            generatedNumber = new BigInteger(sb.toString());
            exists = cardDao.existsNumber(generatedNumber);
        } while (exists);

        return generatedNumber;
    }

    private String buildCardSecurityCode(final Integer length) {
        return RandomStringUtils.randomNumeric(length);
    }

    private Card buildNewCard(final Member member) {

        final Calendar now = Calendar.getInstance();

        final Card newCard = new Card();
        final CardType cardType = member.getMemberGroup().getCardType();
        newCard.setCardType(cardType);
        newCard.setOwner(member);
        newCard.setCreationDate(now);
        newCard.setStatus(Status.PENDING);

        final Calendar expirationDate = (Calendar) now.clone();
        expirationDate.add(cardType.getDefaultExpiration().getField().getValue(), cardType.getDefaultExpiration().getNumber());
        if (cardType.isIgnoreDayInExpirationDate()) {
            expirationDate.set(Calendar.DAY_OF_MONTH, expirationDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        newCard.setExpirationDate(expirationDate);
        newCard.setCardNumber(buildCardNumber(cardType.getCardFormatNumber()));

        if (cardType.getCardSecurityCode() == CardType.CardSecurityCode.AUTOMATIC) {
            newCard.setCardSecurityCode(buildCardSecurityCode(cardType.getCardSecurityCodeLength().getMax()));
        }

        return newCard;
    }

    /**
     * Verify if has any cards that expires on given time. If has, card status is changed to EXPIRED
     * @param time
     * @return
     */
    private List<Card> expireCards(final Calendar taskTime) {

        final List<Card> cards = cardDao.getCardsToExpire(DateHelper.truncate(taskTime));
        for (final Card card : cards) {
            card.setStatus(Status.EXPIRED);
            cardDao.update(card);
            generateLog(card);
        }
        return cards;
    }

    private void generateLog(final Card card) {
        final CardLog cardLog = new CardLog();
        if (LoggedUser.hasUser()) {
            cardLog.setBy(LoggedUser.element());
        }
        cardLog.setCard(card);
        cardLog.setDate(Calendar.getInstance());
        cardLog.setStatus(card.getStatus());

        cardLogDao.insert(cardLog);

    }

    private List<Card> getMemberCards(final long memberId) {
        return cardDao.getMemberCards(memberId);
    }

    private void validateCardSecurityCode(final Card card, final String code) throws ValidationException {
        final CardType cardType = card.getCardType();
        if (cardType.getCardSecurityCode() != CardSecurityCode.MANUAL) {
            throw new ValidationException();
        }

        final RangeConstraint length = cardType.getCardSecurityCodeLength();
        final ValidationError lengthResult = new LengthValidation(length).validate(card, "securityCode", code);
        if (lengthResult != null) {
            throw new ValidationException("code1", "cardType.cardSecurityCode", lengthResult);
        }

        final PasswordPolicy passwordPolicy = card.getOwner().getGroup().getBasicSettings().getPasswordPolicy();
        final boolean avoidObvious = passwordPolicy != null && passwordPolicy != PasswordPolicy.NONE;
        if (avoidObvious && accessService.isObviousCredential(card.getOwner(), code)) {
            throw new ValidationException("card.changeSecurityCode.error.obvious");
        }
    }

}
