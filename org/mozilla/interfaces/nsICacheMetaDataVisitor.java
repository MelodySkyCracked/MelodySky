/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsICacheMetaDataVisitor
extends nsISupports {
    public static final String NS_ICACHEMETADATAVISITOR_IID = "{22f9a49c-3cf8-4c23-8006-54efb11ac562}";

    public boolean visitMetaDataElement(String var1, String var2);
}

