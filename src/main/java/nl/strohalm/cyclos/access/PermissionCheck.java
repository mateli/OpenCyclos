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
package nl.strohalm.cyclos.access;

import nl.strohalm.cyclos.entities.Entity;
import nl.strohalm.cyclos.exceptions.PermissionDeniedException;

/**
 * Used to check for permissions according to the logged user. If a check is to accept a given user type (administrator / member / broker / operator),
 * a call to the corresponding method must be done, even if without permissions. Otherwise, for example, if the logged user is a member and the
 * {@link #member(MemberPermission...)} method was not invoked, the permission will be denied.
 * 
 * @author luis
 */
public interface PermissionCheck {

    /**
     * Adds a permission check for administrators
     */
    PermissionCheck admin(AdminPermission... permissions);

    /**
     * Adds a check to administrators that ensures ALL entities must belong to the collection represented by the given administrator permission.
     * @param permission a permission which type must be LIST.
     * @param a list of entities to check on an AND basis.
     * @throws IllegalArgumentException if the permission's type is not LIST.
     */
    PermissionCheck adminFor(AdminPermission permission, Entity... entityToCheck);

    /**
     * Adds a permission check for any user type
     */
    PermissionCheck basic(BasicPermission... permissions);

    /**
     * Adds a permission check for brokers
     */
    PermissionCheck broker(BrokerPermission... permissions);

    /**
     * Adds a check to brokers that ensures ALL entities must belong to the collection represented by the given broker permission.
     * @param permission a permission which type must be LIST.
     * @param a list of entities to check on an AND basis.
     * @throws IllegalArgumentException if the permission's type is not LIST.
     */
    PermissionCheck brokerFor(BrokerPermission permission, Entity... entityToCheck);

    /**
     * Throws a {@link PermissionDeniedException} if the result of {@link #check()} is <code>false</code>
     */
    void check() throws PermissionDeniedException;

    /**
     * Returns a boolean flag indicating whether the logged user is allowed according to the current permissions. A list of permissions is checked on
     * an OR basis: if any one of the permissions is valid, it returns true.
     */
    boolean hasPermission();

    /**
     * Adds a permission check for members
     */
    PermissionCheck member(MemberPermission... permissions);

    /**
     * Adds a check to members that ensures ALL entities must belong to the collection represented by the given member permission.
     * @param permission a permission which type must be LIST.
     * @param a list of entities to check on an AND basis.
     * @throws IllegalArgumentException if the permission's type is not LIST.
     */
    PermissionCheck memberFor(MemberPermission permission, Entity... entitiesToCheck);

    /**
     * Adds a permission check for operators with no permissions. This one exists so calls with empty args will compile. Otherwise, the compiler
     * doesn't know which method to invoke: {@link #operator(OperatorPermission...)} or {@link #operator(MemberPermission...)}
     */
    PermissionCheck operator();

    /**
     * Adds a permission check for operators, but checking his member's permissions
     */
    PermissionCheck operator(MemberPermission... permissions);

    /**
     * Adds a permission check for operators
     */
    PermissionCheck operator(OperatorPermission... permissions);

    /**
     * Adds a check to operators that ensures ALL entities must belong to the collection represented by the given member permission.
     * @param permission a permission which type must be LIST.
     * @param a list of entities to check on an AND basis.
     * @throws IllegalArgumentException if the permission's type is not LIST.
     */
    PermissionCheck operatorFor(MemberPermission permission, Entity... entityToCheck);

    /**
     * Adds a check to operators that ensures ALL entities must belong to the collection represented by the given operator permission.
     * @param permission a permission which type must be LIST.
     * @param a list of entities to check on an AND basis.
     * @throws IllegalArgumentException if the permission's type is not LIST.
     */
    PermissionCheck operatorFor(OperatorPermission permission, Entity... entityToCheck);

    /**
     * Adds a check to operators that ensures ALL entities must belong to the collection represented by the given member permission and that also has the
     * required operator permission. This method is useful when the operator permission is a boolean but the parent member permission has a
     * relationship and we want to ensure that all entities are allowed for the owner member.
     * @param permission a permission which type must be LIST.
     * @param parentPermission a permission which type must be BOOLEAN.
     * @param entities a list of entities to check on an AND basis.
     * @return
     */
    PermissionCheck operatorFor(OperatorPermission permission, MemberPermission parentPermission, Entity... entities);
}
