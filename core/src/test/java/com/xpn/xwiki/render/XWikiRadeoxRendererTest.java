/*
 * Copyright 2007, XpertNet SARL, and individual contributors as indicated
 * by the contributors.txt.
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
package com.xpn.xwiki.render;

import org.jmock.cglib.MockObjectTestCase;
import org.jmock.Mock;
import org.jmock.core.Constraint;
import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWiki;
import com.xpn.xwiki.XWikiConfig;
import com.xpn.xwiki.web.XWikiURLFactory;
import com.xpn.xwiki.doc.XWikiDocument;

import java.net.URL;

/**
 * Unit tests for {@link com.xpn.xwiki.render.XWikiRadeoxRenderer}.
 *
 * @version $Id: $
 */
public class XWikiRadeoxRendererTest extends MockObjectTestCase
{
    private XWikiContext context;
    private XWikiRadeoxRenderer renderer;
    private Mock mockXWiki;
    private Mock mockDocument;
    private Mock mockContentDocument;
    private XWikiDocument document;
    private XWikiDocument contentDocument;

    protected void setUp()
    {
        this.renderer = new XWikiRadeoxRenderer();
        this.context = new XWikiContext();

        this.mockXWiki = mock(XWiki.class, new Class[] {XWikiConfig.class, XWikiContext.class},
            new Object[] {new XWikiConfig(), context});
        this.context.setWiki((XWiki) this.mockXWiki.proxy());

        this.mockContentDocument = mock(XWikiDocument.class);
        this.contentDocument = (XWikiDocument) this.mockContentDocument.proxy();

        this.mockDocument = mock(XWikiDocument.class);
        this.document = (XWikiDocument) this.mockDocument.proxy();
    }

    public void testRenderWithSimpleText()
    {
        String result = renderer.render("Simple content", contentDocument, document, context);

        assertEquals("Simple content", result);
    }

    /**
     * @todo this test is too complex and show that the rendering API is not right... 
     */
    public void testRenderLinkToNewPage() throws Exception
    {
        this.mockXWiki.expects(once()).method("exists").with(eq("Main.newlink"), ANYTHING).will(
            returnValue(false));
        this.mockXWiki.expects(once()).method("exists").with(eq("Main.new link"), ANYTHING).will(
            returnValue(false));
        this.mockXWiki.expects(once()).method("getEditorPreference").will(returnValue("text"));

        // This is required just to return the current space...
        Mock mockCurrentDocument = mock(XWikiDocument.class);
        mockCurrentDocument.expects(atLeastOnce()).method("getSpace").will(returnValue("Main"));
        mockCurrentDocument.expects(atLeastOnce()).method("getFullName").will(
            returnValue("Main.WebHome"));
        this.context.setDoc((XWikiDocument) mockCurrentDocument.proxy());

        Mock mockUrlFactory = mock(XWikiURLFactory.class);
        mockUrlFactory.expects(once()).method("createURL").with(new Constraint[] {
            eq("Main"), eq("new link"), eq("edit"), eq("parent=Main.WebHome"), ANYTHING, ANYTHING})
            .will(returnValue(new URL("http://server.com/Main/new link")));
        mockUrlFactory.expects(atLeastOnce()).method("getURL").will(returnValue("/Main/new link"));
        this.context.setURLFactory((XWikiURLFactory) mockUrlFactory.proxy());

        String result = renderer.render("This is a [new link]", contentDocument, document, context);

        assertEquals("This is a <a class=\"wikicreatelink\" href=\"/Main/new link\">"
            + "<span class=\"wikicreatelinktext\">new link</span>"
            + "<span class=\"wikicreatelinkqm\">?</span></a>", result);
    }
}
