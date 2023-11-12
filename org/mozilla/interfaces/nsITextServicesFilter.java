/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsITextServicesFilter
extends nsISupports {
    public static final String NS_ITEXTSERVICESFILTER_IID = "{5bec321f-59ac-413a-a4ad-8a8d7c50a0d0}";

    public boolean skip(nsIDOMNode var1);
}

