/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMMimeType;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMPlugin
extends nsISupports {
    public static final String NS_IDOMPLUGIN_IID = "{f6134681-f28b-11d2-8360-c90899049c3c}";

    public String getDescription();

    public String getFilename();

    public String getName();

    public long getLength();

    public nsIDOMMimeType item(long var1);

    public nsIDOMMimeType namedItem(String var1);
}

