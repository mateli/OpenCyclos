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
package nl.strohalm.cyclos.controls.ads;

import java.util.Map;

import nl.strohalm.cyclos.controls.BaseBindingForm;
import nl.strohalm.cyclos.utils.binding.MapBean;

import org.apache.struts.upload.FormFile;

/**
 * Form for ad details
 * @author luis
 */
public class AdForm extends BaseBindingForm {

    private static final long serialVersionUID = 6972762377763607796L;
    private long              id;
    private long              memberId;
    private FormFile          picture;
    private String            pictureCaption;

    public AdForm() {
        setAd("publicationPeriod", new MapBean("begin", "end"));
        setAd("customValues", new MapBean(true, "field", "value"));
    }

    public Map<String, Object> getAd() {
        return values;
    }

    public Object getAd(final String key) {
        return values.get(key);
    }

    public long getId() {
        return id;
    }

    public long getMemberId() {
        return memberId;
    }

    public FormFile getPicture() {
        return picture;
    }

    public String getPictureCaption() {
        return pictureCaption;
    }

    public void setAd(final Map<String, Object> map) {
        values = map;
    }

    public void setAd(final String key, final Object value) {
        values.put(key, value);
    }

    public void setId(final long memberId) {
        id = memberId;
    }

    public void setMemberId(final long memberId) {
        this.memberId = memberId;
    }

    public void setPicture(final FormFile picture) {
        this.picture = picture;
    }

    public void setPictureCaption(final String pictureCaption) {
        this.pictureCaption = pictureCaption;
    }
}
