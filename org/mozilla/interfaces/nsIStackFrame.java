/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIStackFrame
extends nsISupports {
    public static final String NS_ISTACKFRAME_IID = "{91d82105-7c62-4f8b-9779-154277c0ee90}";

    public long getLanguage();

    public String getLanguageName();

    public String getFilename();

    public String getName();

    public int getLineNumber();

    public String getSourceLine();

    public nsIStackFrame getCaller();

    public String toString();
}

