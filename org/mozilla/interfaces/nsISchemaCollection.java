/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISchema;
import org.mozilla.interfaces.nsISchemaAttribute;
import org.mozilla.interfaces.nsISchemaElement;
import org.mozilla.interfaces.nsISchemaType;
import org.mozilla.interfaces.nsISupports;

public interface nsISchemaCollection
extends nsISupports {
    public static final String NS_ISCHEMACOLLECTION_IID = "{427c5511-941b-48c0-9abc-8ec9ea5d964b}";

    public nsISchema getSchema(String var1);

    public nsISchemaElement getElement(String var1, String var2);

    public nsISchemaAttribute getAttribute(String var1, String var2);

    public nsISchemaType getType(String var1, String var2);
}

