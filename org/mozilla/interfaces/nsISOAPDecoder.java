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

public interface nsISOAPDecoder
extends nsISupports {
    public static final String NS_ISOAPDECODER_IID = "{4c2e02ae-1dd2-11b2-b1cd-c79dea3d46db}";

    public nsIVariant decode(nsISOAPEncoding var1, nsIDOMElement var2, nsISchemaType var3, nsISOAPAttachments var4);
}

