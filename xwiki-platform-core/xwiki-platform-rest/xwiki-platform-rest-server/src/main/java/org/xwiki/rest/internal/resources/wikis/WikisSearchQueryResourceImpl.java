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
package org.xwiki.rest.internal.resources.wikis;

import org.xwiki.component.annotation.Component;
import org.xwiki.rest.XWikiRestException;
import org.xwiki.rest.internal.Utils;
import org.xwiki.rest.internal.resources.BaseSearchResult;
import org.xwiki.rest.model.jaxb.SearchResults;
import org.xwiki.rest.resources.wikis.WikisSearchQueryResource;

@Component("org.xwiki.rest.internal.resources.wikis.WikisSearchQueryResourceImpl")
public class WikisSearchQueryResourceImpl extends BaseSearchResult implements WikisSearchQueryResource
{
    @Override
    public SearchResults search(String query, Integer number, Integer start, Boolean distinct, String searchWikis,
        String orderField, String order, Boolean withPrettyNames, String className) throws XWikiRestException
    {
        try {
            SearchResults searchResults = objectFactory.createSearchResults();
            searchResults.setTemplate(String.format("%s?%s", uriInfo.getBaseUri().toString(),
                MULTIWIKI_QUERY_TEMPLATE_INFO));

            searchResults.getSearchResults().addAll(
                searchQuery(
                    query,
                    QueryType.LUCENE.toString(),
                    getXWikiContext().getDatabase(),
                    searchWikis,
                    Utils.getXWiki(componentManager).getRightService()
                        .hasProgrammingRights(Utils.getXWikiContext(componentManager)), orderField, order, distinct,
                    number, start, withPrettyNames, className));

            return searchResults;
        } catch (Exception e) {
            throw new XWikiRestException(e);
        }
    }
}
