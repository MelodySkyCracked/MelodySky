/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNSHTMLImageElement
extends nsISupports {
    public static final String NS_IDOMNSHTMLIMAGEELEMENT_IID = "{a6cf90c7-15b3-11d2-932e-00805f8add32}";

    public String getLowsrc();

    public void setLowsrc(String var1);

    public boolean getComplete();

    public int getNaturalHeight();

    public int getNaturalWidth();

    public int getX();

    public int getY();
}

