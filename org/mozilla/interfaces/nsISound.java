/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURL;

public interface nsISound
extends nsISupports {
    public static final String NS_ISOUND_IID = "{b148eed1-236d-11d3-b35c-00a0cc3c1cde}";

    public void play(nsIURL var1);

    public void playSystemSound(String var1);

    public void beep();

    public void init();
}

