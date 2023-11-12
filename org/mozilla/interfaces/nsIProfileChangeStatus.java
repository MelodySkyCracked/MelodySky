/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIProfileChangeStatus
extends nsISupports {
    public static final String NS_IPROFILECHANGESTATUS_IID = "{2f977d43-5485-11d4-87e2-0010a4e75ef2}";

    public void vetoChange();

    public void changeFailed();
}

