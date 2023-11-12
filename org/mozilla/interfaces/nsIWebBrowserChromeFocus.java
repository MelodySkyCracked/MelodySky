/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIWebBrowserChromeFocus
extends nsISupports {
    public static final String NS_IWEBBROWSERCHROMEFOCUS_IID = "{d2206418-1dd1-11b2-8e55-acddcd2bcfb8}";

    public void focusNextElement();

    public void focusPrevElement();
}

