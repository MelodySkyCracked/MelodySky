/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;

public interface nsIXTFElementWrapper
extends nsISupports {
    public static final String NS_IXTFELEMENTWRAPPER_IID = "{444d0276-3302-4d35-a74e-25c4e9c483c9}";

    public nsIDOMElement getElementNode();

    public nsIDOMElement getDocumentFrameElement();

    public long getNotificationMask();

    public void setNotificationMask(long var1);

    public void setIntrinsicState(int var1);
}

