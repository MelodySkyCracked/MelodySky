/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMMimeType;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMMimeTypeArray
extends nsISupports {
    public static final String NS_IDOMMIMETYPEARRAY_IID = "{f6134683-f28b-11d2-8360-c90899049c3c}";

    public long getLength();

    public nsIDOMMimeType item(long var1);

    public nsIDOMMimeType namedItem(String var1);
}

