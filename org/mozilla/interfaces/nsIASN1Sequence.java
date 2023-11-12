/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIASN1Object;
import org.mozilla.interfaces.nsIMutableArray;

public interface nsIASN1Sequence
extends nsIASN1Object {
    public static final String NS_IASN1SEQUENCE_IID = "{b6b957e6-1dd1-11b2-89d7-e30624f50b00}";

    public nsIMutableArray getASN1Objects();

    public void setASN1Objects(nsIMutableArray var1);

    public boolean getIsValidContainer();

    public void setIsValidContainer(boolean var1);

    public boolean getIsExpanded();

    public void setIsExpanded(boolean var1);
}

