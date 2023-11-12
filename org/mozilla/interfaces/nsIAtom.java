/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIAtom
extends nsISupports {
    public static final String NS_IATOM_IID = "{3d1b15b0-93b4-11d1-895b-006008911b81}";

    public String toString();

    public String toUTF8String();

    public boolean _equals(String var1);

    public boolean equalsUTF8(String var1);
}

