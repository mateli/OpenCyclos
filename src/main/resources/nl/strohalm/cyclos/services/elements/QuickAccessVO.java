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
package nl.strohalm.cyclos.services.elements;

import nl.strohalm.cyclos.utils.DataObject;

/**
 * Represents the visible functionalities accessible through quick access
 * 
 * @author luis
 */
public class QuickAccessVO extends DataObject {

    private static final long serialVersionUID = -7785236075759875792L;
    private boolean           updateProfile;
    private boolean           searchMembers;
    private boolean           accountInformation;
    private boolean           memberPayment;
    private boolean           publishAd;
    private boolean           searchAds;
    private boolean           viewMessages;
    private boolean           viewContacts;

    public int getFunctionCount() {
        int count = 0;
        if (updateProfile) {
            count++;
        }
        if (searchMembers) {
            count++;
        }
        if (accountInformation) {
            count++;
        }
        if (memberPayment) {
            count++;
        }
        if (publishAd) {
            count++;
        }
        if (searchAds) {
            count++;
        }
        if (viewMessages) {
            count++;
        }
        if (viewContacts) {
            count++;
        }
        return count;
    }

    public boolean isAccountInformation() {
        return accountInformation;
    }

    public boolean isMemberPayment() {
        return memberPayment;
    }

    public boolean isPublishAd() {
        return publishAd;
    }

    public boolean isSearchAds() {
        return searchAds;
    }

    public boolean isSearchMembers() {
        return searchMembers;
    }

    public boolean isUpdateProfile() {
        return updateProfile;
    }

    public boolean isViewContacts() {
        return viewContacts;
    }

    public boolean isViewMessages() {
        return viewMessages;
    }

    public void setAccountInformation(final boolean accountInformation) {
        this.accountInformation = accountInformation;
    }

    public void setMemberPayment(final boolean memberPayment) {
        this.memberPayment = memberPayment;
    }

    public void setPublishAd(final boolean publishAd) {
        this.publishAd = publishAd;
    }

    public void setSearchAds(final boolean searchAds) {
        this.searchAds = searchAds;
    }

    public void setSearchMembers(final boolean searchMembers) {
        this.searchMembers = searchMembers;
    }

    public void setUpdateProfile(final boolean updateProfile) {
        this.updateProfile = updateProfile;
    }

    public void setViewContacts(final boolean viewContacts) {
        this.viewContacts = viewContacts;
    }

    public void setViewMessages(final boolean viewMessages) {
        this.viewMessages = viewMessages;
    }

}
