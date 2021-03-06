/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.wiki.user;

import org.xwiki.stability.Unstable;

/**
 * Used to specify how user can become member of a wiki.
 *
 * @since 5.3M2
 * @version $Id$
 */
@Unstable
public enum MembershipType
{
    /**
     * Any user can join.
     */
    OPEN,
    /**
     * Any user can request to join but an admin must accept.
     */
    REQUEST,
    /**
     * Only an admin can invite a user.
     */
    INVITE
}
