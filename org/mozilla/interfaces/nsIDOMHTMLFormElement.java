/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLCollection;
import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLFormElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLFORMELEMENT_IID = "{a6cf908f-15b3-11d2-932e-00805f8add32}";

    public nsIDOMHTMLCollection getElements();

    public int getLength();

    public String getName();

    public void setName(String var1);

    public String getAcceptCharset();

    public void setAcceptCharset(String var1);

    public String getAction();

    public void setAction(String var1);

    public String getEnctype();

    public void setEnctype(String var1);

    public String getMethod();

    public void setMethod(String var1);

    public String getTarget();

    public void setTarget(String var1);

    public void submit();

    public void reset();
}

