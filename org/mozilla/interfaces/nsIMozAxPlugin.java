/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIVariant;

public interface nsIMozAxPlugin
extends nsISupports {
    public static final String NS_IMOZAXPLUGIN_IID = "{b30c2717-2bbf-4475-9ddf-9e26f893f32a}";

    public void invoke(String var1);

    public void invoke1(String var1, nsIVariant var2);

    public void invoke2(String var1, nsIVariant var2, nsIVariant var3);

    public void invoke3(String var1, nsIVariant var2, nsIVariant var3, nsIVariant var4);

    public void invoke4(String var1, nsIVariant var2, nsIVariant var3, nsIVariant var4, nsIVariant var5);

    public nsIVariant getProperty(String var1);

    public void setProperty(String var1, nsIVariant var2);
}

