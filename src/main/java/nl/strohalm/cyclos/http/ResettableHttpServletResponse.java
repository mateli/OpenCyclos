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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * An {@link HttpServletResponse} which wraps another response, keeping track of changes. Any changes can be applied or reset.
 * @author luis
 */
@SuppressWarnings("deprecation")
public class ResettableHttpServletResponse implements HttpServletResponse, Resettable {

    private interface ResponseOperation {
        void apply();
    }

    private HttpServletResponse     wrapped;
    private List<ResponseOperation> operations;
    private Locale                  locale;
    private int                     bufferSize;
    private String                  contentType;
    private String                  charset;
    private PrintWriter             writer;
    private OutputStream            outputStream;
    private ServletOutputStream     servletOutputStream;
    private File                    contents;
    private Set<String>             headerNames;
    private Integer                 status;
    private boolean                 stateApplied;

    public ResettableHttpServletResponse(final HttpServletResponse response) {
        wrapped = response;
        operations = new ArrayList<ResponseOperation>();
        headerNames = new HashSet<String>();
        resetState();
    }

    @Override
    public void addCookie(final Cookie cookie) {
        operations.add(new ResponseOperation() {
            @Override
            public void apply() {
                wrapped.addCookie(cookie);
            }
        });
    }

    @Override
    public void addDateHeader(final String name, final long date) {
        operations.add(new ResponseOperation() {
            @Override
            public void apply() {
                wrapped.addDateHeader(name, date);
            }
        });
    }

    @Override
    public void addHeader(final String name, final String value) {
        operations.add(new ResponseOperation() {
            @Override
            public void apply() {
                wrapped.addHeader(name, value);
            }
        });
    }

    @Override
    public void addIntHeader(final String name, final int value) {
        operations.add(new ResponseOperation() {
            @Override
            public void apply() {
                wrapped.addIntHeader(name, value);
            }
        });
    }

    @Override
    public void applyState() {
        try {
            // Apply all operations
            for (final ResponseOperation operation : operations) {
                operation.apply();
            }
            // Copy the response contents, if any
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    IOUtils.copy(new FileInputStream(contents), wrapped.getOutputStream());
                } catch (final Exception e) {
                    throw new NestableRuntimeException(e);
                }
            } else if (writer != null) {
                try {
                    writer.flush();
                    IOUtils.copy(new FileReader(contents), wrapped.getWriter());
                } catch (final Exception e) {
                    throw new NestableRuntimeException(e);
                }
            }
            stateApplied = true;
        } finally {
            reset();
        }
    }

    @Override
    public boolean containsHeader(final String name) {
        return headerNames.contains(name);
    }

    @Override
    public String encodeRedirectUrl(final String url) {
        return wrapped.encodeRedirectUrl(url);
    }

    @Override
    public String encodeRedirectURL(final String url) {
        return wrapped.encodeRedirectURL(url);
    }

    @Override
    public String encodeUrl(final String url) {
        return wrapped.encodeUrl(url);
    }

    @Override
    public String encodeURL(final String url) {
        return wrapped.encodeURL(url);
    }

    @Override
    public void flushBuffer() throws IOException {
        // No-op, as nothing has been done in the real response
    }

    @Override
    public int getBufferSize() {
        return bufferSize;
    }

    @Override
    public String getCharacterEncoding() {
        return charset;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException("getWriter() was already invoked in this response");
        }
        if (servletOutputStream == null) {
            contents = File.createTempFile("cyclos_", "_response");
            outputStream = new FileOutputStream(contents);
            servletOutputStream = new ServletOutputStream() {
                @Override
                public void close() throws IOException {
                    outputStream.close();
                }

                @Override
                public void flush() throws IOException {
                    outputStream.flush();
                }

                @Override
                public void write(final byte[] b) throws IOException {
                    outputStream.write(b);
                }

                @Override
                public void write(final byte[] b, final int off, final int len) throws IOException {
                    outputStream.write(b, off, len);
                }

                @Override
                public void write(final int b) throws IOException {
                    outputStream.write(b);
                }
            };
        }
        return servletOutputStream;
    }

    public Integer getStatus() {
        return status;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (outputStream != null) {
            throw new IllegalStateException("getOutputStream() was already invoked");
        }
        if (writer == null) {
            contents = File.createTempFile("cyclos_", "_response");
            writer = new PrintWriter(contents);
        }
        return writer;
    }

    @Override
    public boolean isCommitted() {
        if (!stateApplied) {
            return false;
        }
        return wrapped.isCommitted();
    }

    @Override
    public void reset() {
        resetState();
    }

    @Override
    public void resetBuffer() {
        IOUtils.closeQuietly(outputStream);
        IOUtils.closeQuietly(writer);
        outputStream = null;
        servletOutputStream = null;
        writer = null;
        if (contents != null) {
            contents.delete();
            contents = null;
        }
    }

    @Override
    public void resetState() {
        status = null;
        operations.clear();
        headerNames.clear();
        charset = wrapped.getCharacterEncoding();
        bufferSize = wrapped.getBufferSize();
        contentType = wrapped.getContentType();
        locale = wrapped.getLocale();
        resetBuffer();
    }

    @Override
    public void sendError(final int sc) {
        status = sc;
        operations.add(new ResponseOperation() {
            @Override
            public void apply() {
                try {
                    wrapped.sendError(sc);
                } catch (final IOException e) {
                    throw new NestableRuntimeException(e);
                }
            }
        });
    }

    @Override
    public void sendError(final int sc, final String msg) throws IOException {
        status = sc;
        operations.add(new ResponseOperation() {
            @Override
            public void apply() {
                try {
                    wrapped.sendError(sc, msg);
                } catch (final IOException e) {
                    throw new NestableRuntimeException(e);
                }
            }
        });
    }

    @Override
    public void sendRedirect(final String location) throws IOException {
        operations.add(new ResponseOperation() {
            @Override
            public void apply() {
                try {
                    wrapped.sendRedirect(location);
                } catch (final IOException e) {
                    throw new NestableRuntimeException(e);
                }
            }
        });
    }

    @Override
    public void setBufferSize(final int bufferSize) {
        this.bufferSize = bufferSize;
        operations.add(new ResponseOperation() {
            @Override
            public void apply() {
                wrapped.setBufferSize(bufferSize);
            }
        });
    }

    @Override
    public void setCharacterEncoding(final String charset) {
        this.charset = charset;
        operations.add(new ResponseOperation() {
            @Override
            public void apply() {
                wrapped.setCharacterEncoding(charset);
            }
        });
    }

    @Override
    public void setContentLength(final int len) {
        operations.add(new ResponseOperation() {
            @Override
            public void apply() {
                wrapped.setContentLength(len);
            }
        });
    }

    @Override
    public void setContentType(final String contentType) {
        this.contentType = contentType;
        operations.add(new ResponseOperation() {
            @Override
            public void apply() {
                wrapped.setContentType(contentType);
            }
        });
    }

    @Override
    public void setDateHeader(final String name, final long date) {
        operations.add(new ResponseOperation() {
            @Override
            public void apply() {
                wrapped.setDateHeader(name, date);
            }
        });
    }

    @Override
    public void setHeader(final String name, final String value) {
        operations.add(new ResponseOperation() {
            @Override
            public void apply() {
                wrapped.setHeader(name, value);
            }
        });
    }

    @Override
    public void setIntHeader(final String name, final int value) {
        operations.add(new ResponseOperation() {
            @Override
            public void apply() {
                wrapped.setIntHeader(name, value);
            }
        });
    }

    @Override
    public void setLocale(final Locale locale) {
        this.locale = locale;
        operations.add(new ResponseOperation() {
            @Override
            public void apply() {
                wrapped.setLocale(locale);
            }
        });
    }

    @Override
    public void setStatus(final int sc) {
        status = sc;
        operations.add(new ResponseOperation() {
            @Override
            public void apply() {
                wrapped.setStatus(sc);
            }
        });
    }

    @Override
    public void setStatus(final int sc, final String sm) {
        status = sc;
        operations.add(new ResponseOperation() {
            @Override
            public void apply() {
                wrapped.setStatus(sc, sm);
            }
        });
    }

}
