/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISelectionDisplay
extends nsISupports {
    public static final String NS_ISELECTIONDISPLAY_IID = "{0ddf9e1c-1dd2-11b2-a183-908a08aa75ae}";
    public static final short DISPLAY_TEXT = 1;
    public static final short DISPLAY_IMAGES = 2;
    public static final short DISPLAY_FRAMES = 4;
    public static final short DISPLAY_ALL = 7;

    public void setSelectionFlags(short var1);

    public short getSelectionFlags();
}

