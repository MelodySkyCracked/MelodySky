/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISchemaType;

public interface nsISchemaSimpleType
extends nsISchemaType {
    public static final String NS_ISCHEMASIMPLETYPE_IID = "{3c14a023-6f4e-11d5-9b46-000064657374}";
    public static final int SIMPLE_TYPE_BUILTIN = 1;
    public static final int SIMPLE_TYPE_LIST = 2;
    public static final int SIMPLE_TYPE_UNION = 3;
    public static final int SIMPLE_TYPE_RESTRICTION = 4;

    public int getSimpleType();
}

