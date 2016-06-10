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
package nl.strohalm.cyclos.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.strohalm.cyclos.annotations.Inject;
import nl.strohalm.cyclos.utils.SpringHelper;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

/**
 * Servlet used to generate a captcha image
 * @author luis
 */
public class CaptchaServlet extends HttpServlet {

    private static final long serialVersionUID = -7876504189381958457L;

    /**
     * Checks whether the given challenge is correct
     */
    public static boolean checkChallenge(final HttpServletRequest request, final String challenge) {
        final HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        try {
            return challenge.equalsIgnoreCase((String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY));
        } catch (final Exception e) {
            return false;
        }
    }

    private Producer captchaProducer;

    @Override
    public void init() throws ServletException {
        SpringHelper.injectBeans(getServletContext(), this);
    }

    @Inject
    public void setCaptchaProducer(final Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final HttpSession session = request.getSession();

        // Store the captcha challenge on session
        final String challenge = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, challenge);

        // Generate the image
        final BufferedImage image = captchaProducer.createImage(challenge);
        response.setContentType("image/png");
        ImageIO.write(image, "png", response.getOutputStream());
        response.flushBuffer();
    }

}
