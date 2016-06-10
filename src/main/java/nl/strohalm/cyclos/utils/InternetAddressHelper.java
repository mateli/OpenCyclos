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

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * Helper methods for internet addresses
 * 
 * @author luis
 */
public class InternetAddressHelper {
    /**
     * The possible types for an internet address
     * @author luis
     */
    public static enum AddressType {
        HOSTNAME, SIMPLE_IP, IP_RANGE
    }

    private static final Pattern HOSTNAME_PART = Pattern.compile("[a-z|A-Z|0-9|-]{1,63}");

    /**
     * Given an ip range in form 10.10.10.30-40 return an array with 2 strings: 10.10.10.30 and 10.10.10.40. The first will always be the lowest
     * address, so that 10.10.10.40-30 would also return 10.10.10.30 and 10.10.10.40
     */
    public static String[] getRangeBoundaries(final String address) {
        if (!isIpRange(address)) {
            throw new IllegalArgumentException("Was expecting an IP range, but received \"" + address + "\"");
        }
        final String[] parts = StringUtils.split(address, '.');
        final String[] address1 = new String[4];
        final String[] address2 = new String[4];
        for (int i = 0; i < 3; i++) {
            address1[i] = parts[i];
            address2[i] = parts[i];
        }
        final String range = parts[3];
        final String[] subParts = StringUtils.split(range, '-');
        address1[3] = subParts[0];
        address2[3] = subParts[1];
        final int int1 = Integer.parseInt(subParts[0]);
        final int int2 = Integer.parseInt(subParts[1]);
        if (int1 <= int2) {
            return new String[] { StringUtils.join(address1, '.'), StringUtils.join(address2, '.') };
        } else {
            return new String[] { StringUtils.join(address2, '.'), StringUtils.join(address1, '.') };
        }
    }

    /**
     * Checks whether the given address is a valid hostname
     */
    public static boolean isHostname(final String address) {
        if (StringUtils.isEmpty(address)) {
            return false;
        }
        if (address.length() > 255) {
            // RFC mandates the max length is 255
            return false;
        }

        final String[] parts = StringUtils.split(address, '.');
        boolean allNumeric = true;
        for (final String part : parts) {
            // RFC mandates each part must be between 1 and 63 characters long
            if (!HOSTNAME_PART.matcher(part).matches()) {
                return false;
            }
            if (!StringUtils.isNumeric(part)) {
                allNumeric = false;
            }
        }
        // It can't be only numbers
        return !allNumeric;
    }

    /**
     * Checks whether the given address is a valid ip address range, in the form: A.B.C.D-E
     */
    public static boolean isIpRange(final String address) {
        if (StringUtils.isEmpty(address)) {
            return false;
        }
        if (StringUtils.containsOnly(address, "0123456789.-")) {
            final String[] parts = StringUtils.split(address, '.');
            if (parts.length != 4) {
                return false;
            }
            for (int i = 0; i < parts.length; i++) {
                final String part = parts[i];
                if (i == 3) {
                    // The last part is the only one that can contain a dash as separator
                    final String[] subParts = StringUtils.split(part, '-');
                    if (subParts.length != 2) {
                        return false;
                    }
                    final String begin = subParts[0];
                    final String end = subParts[1];
                    return isValidIpPart(begin) && isValidIpPart(end);
                } else {
                    if (!isValidIpPart(part)) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the given address is a valid simple ip address
     */
    public static boolean isSimpleIp(final String address) {
        if (StringUtils.isEmpty(address)) {
            return false;
        }
        if (StringUtils.containsOnly(address, "0123456789.")) {
            final String[] parts = StringUtils.split(address, '.');
            if (parts.length != 4) {
                return false;
            }
            for (final String part : parts) {
                if (!isValidIpPart(part)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Pads an address, filling with zeroes on the left. Example: 1.2.30.100 becomes 001.002.030.100
     */
    public static String padAddress(final String address) {
        if (!isSimpleIp(address)) {
            throw new IllegalArgumentException("Was expecting an IP address, but received \"" + address + "\"");
        }

        final String[] parts = StringUtils.split(address, '.');
        for (int i = 0; i < parts.length; i++) {
            parts[i] = StringUtils.leftPad(parts[i], 3, '0');
        }
        return StringUtils.join(parts, '.');
    }

    /**
     * Returns the address type, or null when invalid
     */
    public static AddressType resolveAddressType(final String address) {
        if (isSimpleIp(address)) {
            return AddressType.SIMPLE_IP;
        } else if (isIpRange(address)) {
            return AddressType.IP_RANGE;
        } else if (isHostname(address)) {
            return AddressType.HOSTNAME;
        } else {
            return null;
        }
    }

    private static boolean isValidIpPart(final String part) {
        try {
            final int value = Integer.parseInt(part);
            return value >= 0x00 && value <= 0xff;
        } catch (final Exception e) {
            return false;
        }
    }

}
