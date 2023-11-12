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

public interface nsISOAPEncoder
extends nsISupports {
    public static final String NS_ISOAPENCODER_IID = "{fc33ffd6-1dd1-11b2-8750-fa62430a38b4}";

    public nsIDOMElement encode(nsISOAPEncoding var1, nsIVariant var2, String var3, String var4, nsISchemaType var5, nsISOAPAttachments var6, nsIDOMElement var7);
}

