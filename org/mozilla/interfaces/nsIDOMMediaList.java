/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMMediaList
extends nsISupports {
    public static final String NS_IDOMMEDIALIST_IID = "{9b0c2ed7-111c-4824-adf9-ef0da6dad371}";

    public String getMediaText();

    public void setMediaText(String var1);

    public long getLength();

    public String item(long var1);

    public void deleteMedium(String var1);

    public void appendMedium(String var1);
}

