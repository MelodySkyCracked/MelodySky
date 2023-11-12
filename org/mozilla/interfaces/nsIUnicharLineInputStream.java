/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIUnicharLineInputStream
extends nsISupports {
    public static final String NS_IUNICHARLINEINPUTSTREAM_IID = "{67f42475-ba80-40f8-ac0b-649c89230184}";

    public boolean readLine(String[] var1);
}

