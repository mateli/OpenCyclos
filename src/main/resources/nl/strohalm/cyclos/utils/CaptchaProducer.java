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

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import nl.strohalm.cyclos.CyclosConfiguration;
import nl.strohalm.cyclos.entities.customization.images.Image;
import nl.strohalm.cyclos.entities.customization.images.Image.Nature;
import nl.strohalm.cyclos.utils.customizedfile.CustomizedFileHandler;
import nl.strohalm.cyclos.utils.customizedfile.ImageChangeListener;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.text.WordRenderer;
import com.google.code.kaptcha.util.Config;

/**
 * Custom captcha producer which is based in a background image
 * @author luis
 */
public class CaptchaProducer implements Producer, ServletContextAware, InitializingBean {

    private BufferedImage         background;
    private WordRenderer          wordRenderer;
    private GimpyEngine           gimpyEngine;
    private Config                config;
    private ServletContext        servletContext;
    private CustomizedFileHandler customizedFileHandler;

    @Override
    public void afterPropertiesSet() throws Exception {
        config = new Config(CyclosConfiguration.getCyclosProperties());

        wordRenderer = config.getWordRendererImpl();
        gimpyEngine = config.getObscurificatorImpl();

        customizedFileHandler.addImageChangeListener(new ImageChangeListener() {
            @Override
            public void onImageChanged(final Image image) {
                if (image.getNature() == Nature.SYSTEM && image.getName().contains("captchaBackground")) {
                    // Whenever the captcha background has changed, set the cached image to null, so that it's reloaded again
                    background = null;
                }
            }
        });
    }

    @Override
    public BufferedImage createImage(final String text) {
        final BufferedImage background = readBackground();
        BufferedImage image = wordRenderer.renderWord(text, background.getWidth(), background.getHeight());
        image = gimpyEngine.getDistortedImage(image);
        return combine(image, background);
    }

    @Override
    public String createText() {
        return config.getTextProducerImpl().getText();
    }

    public void setCustomizedFileHandler(final CustomizedFileHandler customizedFileHandler) {
        this.customizedFileHandler = customizedFileHandler;
    }

    @Override
    public void setServletContext(final ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * Draws the image over the background
     */
    private BufferedImage combine(final BufferedImage image, final BufferedImage background) {
        final int width = background.getWidth();
        final int height = background.getHeight();

        // Create the new combined image
        final BufferedImage imageWithBackground = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D graph = (Graphics2D) imageWithBackground.getGraphics();
        final RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.add(new RenderingHints(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY));
        hints.add(new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY));
        hints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        graph.setRenderingHints(hints);

        graph.fill(new Rectangle2D.Double(0, 0, width, height));

        // draw the image over the background
        graph.drawImage(background, 0, 0, null);
        graph.drawImage(image, 0, 0, null);

        return imageWithBackground;
    }

    private BufferedImage readBackground() {
        if (background == null) {
            try {
                final URL backgroundUrl = servletContext.getResource("/pages/images/captchaBackground.jpg");
                background = ImageIO.read(backgroundUrl);
            } catch (final Exception e) {
                throw new IllegalStateException("Could not read captcha background image");
            }
        }
        return background;
    }

}
