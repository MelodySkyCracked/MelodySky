/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISchemaAttributeComponent;

public interface nsISchemaAttributeGroup
extends nsISchemaAttributeComponent {
    public static final String NS_ISCHEMAATTRIBUTEGROUP_IID = "{3c14a02f-6f4e-11d5-9b46-000064657374}";

    public long getAttributeCount();

    public nsISchemaAttributeComponent getAttributeByIndex(long var1);

    public nsISchemaAttributeComponent getAttributeByName(String var1);
}

