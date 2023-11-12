/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISOAPAttachments;
import org.mozilla.interfaces.nsISOAPEncoding;
import org.mozilla.interfaces.nsISchemaType;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIVariant;

public interface nsISOAPBlock
extends nsISupports {
    public static final String NS_ISOAPBLOCK_IID = "{843afaa8-1dd2-11b2-8b0d-9b5d16fe64ea}";

    public void init(nsISOAPAttachments var1, int var2);

    public String getNamespaceURI();

    public void setNamespaceURI(String var1);

    public String getName();

    public void setName(String var1);

    public nsISOAPEncoding getEncoding();

    public void setEncoding(nsISOAPEncoding var1);

    public nsISchemaType getSchemaType();

    public void setSchemaType(nsISchemaType var1);

    public nsIDOMElement getElement();

    public void setElement(nsIDOMElement var1);

    public nsIVariant getValue();

    public void setValue(nsIVariant var1);
}

