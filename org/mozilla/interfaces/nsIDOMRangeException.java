/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMRangeException
extends nsISupports {
    public static final String NS_IDOMRANGEEXCEPTION_IID = "{0f807301-39d2-11d6-a7f2-8f504ff870dc}";
    public static final int BAD_BOUNDARYPOINTS_ERR = 1;
    public static final int INVALID_NODE_TYPE_ERR = 2;

    public int getCode();
}

