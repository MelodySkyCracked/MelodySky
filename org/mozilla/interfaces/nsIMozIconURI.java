/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIURI;

public interface nsIMozIconURI
extends nsIURI {
    public static final String NS_IMOZICONURI_IID = "{1fb33f44-f522-4880-a225-4b75d09b04c6}";

    public nsIURI getIconFile();

    public void setIconFile(nsIURI var1);

    public long getImageSize();

    public void setImageSize(long var1);

    public String getStockIcon();

    public String getIconSize();

    public String getIconState();

    public String getContentType();

    public void setContentType(String var1);

    public String getFileExtension();
}

