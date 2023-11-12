/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.jsdIValue;
import org.mozilla.interfaces.nsISupports;

public interface jsdIObject
extends nsISupports {
    public static final String JSDIOBJECT_IID = "{d500e8b8-1dd1-11b2-89a1-cdf55d91cbbd}";

    public String getCreatorURL();

    public long getCreatorLine();

    public String getConstructorURL();

    public long getConstructorLine();

    public jsdIValue getValue();
}

