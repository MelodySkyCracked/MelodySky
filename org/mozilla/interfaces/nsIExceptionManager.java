/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIException;
import org.mozilla.interfaces.nsISupports;

public interface nsIExceptionManager
extends nsISupports {
    public static final String NS_IEXCEPTIONMANAGER_IID = "{efc9d00b-231c-4feb-852c-ac017266a415}";

    public void setCurrentException(nsIException var1);

    public nsIException getCurrentException();

    public nsIException getExceptionFromProvider(long var1, nsIException var3);
}

