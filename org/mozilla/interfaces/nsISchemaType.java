/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISchemaComponent;

public interface nsISchemaType
extends nsISchemaComponent {
    public static final String NS_ISCHEMATYPE_IID = "{3c14a022-6f4e-11d5-9b46-000064657374}";
    public static final int SCHEMA_TYPE_SIMPLE = 1;
    public static final int SCHEMA_TYPE_COMPLEX = 2;
    public static final int SCHEMA_TYPE_PLACEHOLDER = 3;

    public String getName();

    public int getSchemaType();
}

