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
package nl.strohalm.cyclos.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

/**
 * A {@link HttpServletRequest} that allows reading the body at any time
 * @author luis
 */
public class StringBodyRequest extends HttpServletRequestWrapper {

    private static class DelegatingServletInputStream extends ServletInputStream {
        private InputStream in;

        private DelegatingServletInputStream(final InputStream in) {
            this.in = in;
        }

        @Override
        public int available() throws IOException {
            return in.available();
        }

        @Override
        public void close() throws IOException {
            in.close();
        }

        @Override
        public void mark(final int readlimit) {
            in.mark(readlimit);
        }

        @Override
        public boolean markSupported() {
            return in.markSupported();
        }

        @Override
        public int read() throws IOException {
            return in.read();
        }

        @Override
        public int read(final byte[] b) throws IOException {
            return in.read(b);
        }

        @Override
        public int read(final byte[] b, final int off, final int len) throws IOException {
            return in.read(b, off, len);
        }

        @Override
        public void reset() throws IOException {
            in.reset();
        }

        @Override
        public long skip(final long n) throws IOException {
            return in.skip(n);
        }

    }

    private String  body;
    private Boolean reader;

    public StringBodyRequest(final HttpServletRequest request) {
        super(request);
    }

    /**
     * Returns the request body as string
     */
    public String getBody() throws IOException {
        initBodyFromReaderIfNeeded();
        return body;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (Boolean.TRUE.equals(reader)) {
            throw new IllegalStateException("The getWriter() method was already invoked for this request");
        }
        reader = false;
        String characterEncoding = getCharacterEncoding();
        if (characterEncoding == null) {
            characterEncoding = "UTF-8";
        }
        if (body == null) {
            readBody(new InputStreamReader(super.getInputStream(), characterEncoding));
        }
        final ByteArrayInputStream in = new ByteArrayInputStream(body.getBytes(characterEncoding));
        return new DelegatingServletInputStream(in);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (Boolean.FALSE.equals(reader)) {
            throw new IllegalStateException("The getInputStream() method was already invoked for this request");
        }
        reader = true;
        initBodyFromReaderIfNeeded();
        return new BufferedReader(new StringReader(body));
    }

    private void initBodyFromReaderIfNeeded() throws IOException {
        if (body == null) {
            readBody(super.getReader());
        }
    }

    private void readBody(final Reader in) throws IOException {
        body = IOUtils.toString(in);
    }

}
