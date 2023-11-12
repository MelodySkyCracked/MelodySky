/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIArray;
import org.mozilla.interfaces.nsIFeedElementBase;
import org.mozilla.interfaces.nsIFeedTextConstruct;
import org.mozilla.interfaces.nsIURI;
import org.mozilla.interfaces.nsIWritablePropertyBag2;

public interface nsIFeedContainer
extends nsIFeedElementBase {
    public static final String NS_IFEEDCONTAINER_IID = "{577a1b4c-b3d4-4c76-9cf8-753e6606114f}";

    public String getId();

    public void setId(String var1);

    public nsIWritablePropertyBag2 getFields();

    public void setFields(nsIWritablePropertyBag2 var1);

    public nsIFeedTextConstruct getTitle();

    public void setTitle(nsIFeedTextConstruct var1);

    public nsIURI getLink();

    public void setLink(nsIURI var1);

    public nsIArray getLinks();

    public void setLinks(nsIArray var1);

    public nsIArray getCategories();

    public void setCategories(nsIArray var1);

    public nsIFeedTextConstruct getRights();

    public void setRights(nsIFeedTextConstruct var1);

    public nsIArray getAuthors();

    public void setAuthors(nsIArray var1);

    public nsIArray getContributors();

    public void setContributors(nsIArray var1);

    public String getUpdated();

    public void setUpdated(String var1);

    public void normalize();
}

