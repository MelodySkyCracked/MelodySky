/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIException;
import org.mozilla.interfaces.nsISupports;

public interface nsIExceptionProvider
extends nsISupports {
    public static final String NS_IEXCEPTIONPROVIDER_IID = "{0577744c-c1d2-47f2-8bcc-ce7a9e5a88fc}";

    public nsIException getException(long var1, nsIException var3);
}

