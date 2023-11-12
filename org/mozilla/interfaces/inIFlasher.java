/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;

public interface inIFlasher
extends nsISupports {
    public static final String INIFLASHER_IID = "{7b4a099f-6f6e-4565-977b-fb622adbff49}";

    public String getColor();

    public void setColor(String var1);

    public boolean getInvert();

    public void setInvert(boolean var1);

    public int getThickness();

    public void setThickness(int var1);

    public void drawElementOutline(nsIDOMElement var1);

    public void repaintElement(nsIDOMElement var1);

    public void scrollElementIntoView(nsIDOMElement var1);
}

