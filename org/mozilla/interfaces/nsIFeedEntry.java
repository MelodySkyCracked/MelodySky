/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIArray;
import org.mozilla.interfaces.nsIFeedContainer;
import org.mozilla.interfaces.nsIFeedTextConstruct;

public interface nsIFeedEntry
extends nsIFeedContainer {
    public static final String NS_IFEEDENTRY_IID = "{31bfd5b4-8ff5-4bfd-a8cb-b3dfbd4f0a5b}";

    public nsIFeedTextConstruct getSummary();

    public void setSummary(nsIFeedTextConstruct var1);

    public String getPublished();

    public void setPublished(String var1);

    public nsIFeedTextConstruct getContent();

    public void setContent(nsIFeedTextConstruct var1);

    public nsIArray getEnclosures();

    public void setEnclosures(nsIArray var1);

    public nsIArray getMediaContent();

    public void setMediaContent(nsIArray var1);
}

