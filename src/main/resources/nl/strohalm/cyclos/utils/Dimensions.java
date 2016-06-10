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

/**
 * Encapsulates dimensions, basically for images
 * @author luis
 */
public class Dimensions extends DataObject {
    private static final long serialVersionUID = 4164814243430488870L;
    private int               width;
    private int               height;

    public Dimensions() {
    }

    public Dimensions(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isGreaterThan(final Dimensions other) {
        return width > other.width || height > other.height;
    }

    /**
     * Returns a new Dimension object, resized given the maximum possible dimensions, and keeping the aspect ratio
     */
    public Dimensions resizeKeepingRatio(final Dimensions maximum) {
        if (isGreaterThan(maximum)) {
            int newHeight = maximum.height;
            int newWidth = maximum.width;
            if (((float) height / (float) width) > ((float) newHeight / (float) newWidth)) {
                newWidth = Math.round(newHeight / ((float) height / (float) width));
            } else if (((float) width / (float) height) > ((float) newWidth / (float) newHeight)) {
                newHeight = Math.round(newWidth / ((float) width / (float) height));
            }
            if (newWidth == 0) {
                newWidth = 1;
            }
            if (newHeight == 0) {
                newHeight = 1;
            }
            return new Dimensions(newWidth, newHeight);
        }
        return (Dimensions) clone();
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return width + "x" + height;
    }
}
