/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISOAPAttachments;
import org.mozilla.interfaces.nsISOAPDecoder;
import org.mozilla.interfaces.nsISOAPEncoder;
import org.mozilla.interfaces.nsISchemaCollection;
import org.mozilla.interfaces.nsISchemaType;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIVariant;

public interface nsISOAPEncoding
extends nsISupports {
    public static final String NS_ISOAPENCODING_IID = "{9ae49600-1dd1-11b2-877f-e62f620c5e92}";

    public String getStyleURI();

    public nsISOAPEncoding getAssociatedEncoding(String var1, boolean var2);

    public void setEncoder(String var1, nsISOAPEncoder var2);

    public nsISOAPEncoder getEncoder(String var1);

    public void setDecoder(String var1, nsISOAPDecoder var2);

    public nsISOAPDecoder getDecoder(String var1);

    public nsISOAPEncoder getDefaultEncoder();

    public void setDefaultEncoder(nsISOAPEncoder var1);

    public nsISOAPDecoder getDefaultDecoder();

    public void setDefaultDecoder(nsISOAPDecoder var1);

    public nsISchemaCollection getSchemaCollection();

    public void setSchemaCollection(nsISchemaCollection var1);

    public nsIDOMElement encode(nsIVariant var1, String var2, String var3, nsISchemaType var4, nsISOAPAttachments var5, nsIDOMElement var6);

    public nsIVariant decode(nsIDOMElement var1, nsISchemaType var2, nsISOAPAttachments var3);

    public boolean mapSchemaURI(String var1, String var2, boolean var3);

    public boolean unmapSchemaURI(String var1);

    public String getInternalSchemaURI(String var1);

    public String getExternalSchemaURI(String var1);
}

