/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEventListener;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNSEventTarget
extends nsISupports {
    public static final String NS_IDOMNSEVENTTARGET_IID = "{6cbbbf64-212f-4ef8-9ad4-7240dbb8d6ac}";

    public void addEventListener(String var1, nsIDOMEventListener var2, boolean var3, boolean var4);
}

