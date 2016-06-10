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
package nl.strohalm.cyclos.entities.ads;

import java.util.ArrayList;
import java.util.List;

import nl.strohalm.cyclos.utils.DataObject;

/**
 * An hierarchical VO which holds the id / name of an ad category and the number of available ads in it
 * 
 * @author luis
 */
public class AdCategoryWithCounterVO extends DataObject {

    private static final long             serialVersionUID = 3264044161757983648L;
    private Long                          id;
    private String                        name;
    private int                           level;
    private int                           count;
    private AdCategoryWithCounterVO       parent;
    private List<AdCategoryWithCounterVO> children;

    public AdCategoryWithCounterVO(final Long id, final String name, final int level, final int count, final AdCategoryWithCounterVO parent) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.count = count;
        this.parent = parent;
    }

    public void addChild(final AdCategoryWithCounterVO child) {
        if (children == null) {
            children = new ArrayList<AdCategoryWithCounterVO>();
        }
        children.add(child);
    }

    public List<AdCategoryWithCounterVO> getChildren() {
        return children;
    }

    public int getCount() {
        return count;
    }

    public Long getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public AdCategoryWithCounterVO getParent() {
        return parent;
    }

    public void setChildren(final List<AdCategoryWithCounterVO> children) {
        this.children = children;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setParent(final AdCategoryWithCounterVO parent) {
        this.parent = parent;
    }

}
