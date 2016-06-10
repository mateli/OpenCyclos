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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Validates if an address is in the given whitelist. The wildcards '*' (any sequence) and '?' (single character) can be used, for example: 200.1.1.*
 * or ???.strohalm.org
 * @author luis
 */
public class WhitelistValidator {

    private static boolean isHost(final String string) {
        try {
            return !Character.isDigit(string.charAt(0));
        } catch (final Exception e) {
            return false;
        }
    }

    private final List<String> allowed = new ArrayList<String>();

    /**
     * A new-line separated list of IP address or host names
     * @param data
     */
    public WhitelistValidator(final String data) {
        parse(data);
    }

    public List<String> getAllowedAddrs() {
        final List<String> list = new ArrayList<String>();
        for (final String string : allowed) {
            if (!isHost(string)) {
                list.add(string);
            }
        }
        return list;
    }

    public List<String> getAllowedHosts() {
        final List<String> list = new ArrayList<String>();
        for (final String string : allowed) {
            if (isHost(string)) {
                list.add(string);
            }
        }
        return list;
    }

    /**
     * Check if the given address is allowed on the whitelist
     */
    public boolean isAllowed(String address) {
        if (allowed.isEmpty()) {
            return true;
        }
        address = StringUtils.trimToNull(address);
        if (address == null) {
            return false;
        }
        for (final String test : allowed) {
            final String regExp = StringUtils.replace(StringUtils.replace(StringUtils.replace(test, ".", "\\."), "*", ".*"), "?", ".");
            if (address.matches(regExp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the given request is allowed on the whitelist
     */
    public boolean isAllowed(final String host, final String addr) {
        final boolean allowed = isAllowed(addr);
        if (allowed) {
            return true;
        }
        if (ObjectUtils.equals(host, addr)) {
            // Try to resolve the host name to one of the known hosts
            for (final String current : getAllowedHosts()) {
                try {
                    // Check if one of the addresses on the whitelisted hosts is the request address
                    final InetAddress[] addresses = InetAddress.getAllByName(current);
                    for (final InetAddress address : addresses) {
                        if (address.getHostAddress().equals(addr)) {
                            return true;
                        }
                    }
                } catch (final UnknownHostException e) {
                    // Go on
                }
            }
            return false;
        } else {
            return isAllowed(host);
        }
    }

    private void parse(final String data) {
        final String[] lines = StringUtils.split(StringUtils.trimToEmpty(data), "\n");
        for (String line : lines) {
            line = StringUtils.trimToEmpty(line);
            if (line.length() == 0 || line.charAt(0) == '#') {
                continue;
            }
            allowed.add(line);
        }
    }
}
