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

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import nl.strohalm.cyclos.entities.Application.PasswordHash;
import nl.strohalm.cyclos.services.application.ApplicationServiceLocal;

import org.apache.commons.lang.RandomStringUtils;

/**
 * Helper class used to hash passwords and manage salts
 * @author luis
 */
public class HashHandler {

    public static final String SHA_256 = "SHA-256";
    public static final String MD5     = "MD5";

    /**
     * Apply a SHA-256 hash
     * @param string
     */
    public static String sha2(final String string) {
        return digest(SHA_256, null, string);
    }

    private static String digest(final String algorithm, final String salt, final String string) {
        if (string == null) {
            return null;
        }
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
        md.reset();
        try {
            if (salt != null) {
                md.update(salt.getBytes("UTF-8"));
            }
            return toHex(md.digest(string.getBytes("UTF-8")));
        } catch (final UnsupportedEncodingException e) {
            // Never happens as UTF-8 is always supported
            return null;
        }
    }

    private static String toHex(final byte[] bytes) {
        final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        final char[] chars = new char[bytes.length * 2];
        int j = 0;
        int k;
        for (final byte element : bytes) {
            k = element;
            chars[j++] = hexDigits[(k >>> 4) & 0x0F];
            chars[j++] = hexDigits[k & 0x0F];
        }
        return new String(chars);
    }

    private ApplicationServiceLocal applicationService;

    /**
     * Hashes the given string according to the current password hash algorithm
     */
    public String hash(final String salt, final String string) {
        switch (getPasswordHash()) {
            case SHA2_SALT:
                return digest(SHA_256, salt, string);
            case SHA2:
                return digest(SHA_256, null, string);
            case SHA2_MD5:
                return digest(SHA_256, null, digest(MD5, null, string));
        }
        return null;
    }

    /**
     * Returns a new salt, but only if the password hash is SHA2 / SALT. Otherwise, returns null
     */
    public String newSalt() {
        if (getPasswordHash() != PasswordHash.SHA2_SALT) {
            return null;
        }
        return RandomStringUtils.randomAlphanumeric(32);
    }

    public void setApplicationServiceLocal(final ApplicationServiceLocal applicationService) {
        this.applicationService = applicationService;
    }

    private PasswordHash getPasswordHash() {
        return applicationService == null ? PasswordHash.SHA2_SALT : applicationService.getPasswordHash();
    }
}
