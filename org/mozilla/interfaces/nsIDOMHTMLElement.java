/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;

public interface nsIDOMHTMLElement
extends nsIDOMElement {
    public static final String NS_IDOMHTMLELEMENT_IID = "{a6cf9085-15b3-11d2-932e-00805f8add32}";

    public String getId();

    public void setId(String var1);

    public String getTitle();

    public void setTitle(String var1);

    public String getLang();

    public void setLang(String var1);

    public String getDir();

    public void setDir(String var1);

    public String getClassName();

    public void setClassName(String var1);
}

