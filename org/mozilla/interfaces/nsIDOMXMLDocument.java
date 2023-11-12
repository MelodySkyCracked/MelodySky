/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMRange;
import org.mozilla.interfaces.nsIXPointerResult;

public interface nsIDOMXMLDocument
extends nsIDOMDocument {
    public static final String NS_IDOMXMLDOCUMENT_IID = "{8816d003-e7c8-4065-8827-829b8d07b6e0}";

    public boolean getAsync();

    public void setAsync(boolean var1);

    public boolean load(String var1);

    public nsIDOMRange evaluateFIXptr(String var1);

    public nsIXPointerResult evaluateXPointer(String var1);
}

