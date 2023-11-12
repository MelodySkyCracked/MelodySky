/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISchemaSimpleType;

public interface nsISchemaUnionType
extends nsISchemaSimpleType {
    public static final String NS_ISCHEMAUNIONTYPE_IID = "{3c14a026-6f4e-11d5-9b46-000064657374}";

    public long getUnionTypeCount();

    public nsISchemaSimpleType getUnionType(long var1);
}

