/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIArray;
import org.mozilla.interfaces.nsIFeedContainer;
import org.mozilla.interfaces.nsIFeedGenerator;
import org.mozilla.interfaces.nsIFeedTextConstruct;
import org.mozilla.interfaces.nsIWritablePropertyBag2;

public interface nsIFeed
extends nsIFeedContainer {
    public static final String NS_IFEED_IID = "{3b8aae33-80e2-4efa-99c8-a6c5b99f76ea}";
    public static final long TYPE_FEED = 0L;
    public static final long TYPE_AUDIO = 1L;
    public static final long TYPE_IMAGE = 2L;
    public static final long TYPE_VIDEO = 4L;

    public nsIFeedTextConstruct getSubtitle();

    public void setSubtitle(nsIFeedTextConstruct var1);

    public long getType();

    public int getEnclosureCount();

    public void setEnclosureCount(int var1);

    public nsIArray getItems();

    public void setItems(nsIArray var1);

    public nsIWritablePropertyBag2 getCloud();

    public void setCloud(nsIWritablePropertyBag2 var1);

    public nsIFeedGenerator getGenerator();

    public void setGenerator(nsIFeedGenerator var1);

    public nsIWritablePropertyBag2 getImage();

    public void setImage(nsIWritablePropertyBag2 var1);

    public nsIWritablePropertyBag2 getTextInput();

    public void setTextInput(nsIWritablePropertyBag2 var1);

    public nsIArray getSkipDays();

    public void setSkipDays(nsIArray var1);

    public nsIArray getSkipHours();

    public void setSkipHours(nsIArray var1);
}

