/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISchemaComponent;

public interface nsISchemaAttributeComponent
extends nsISchemaComponent {
    public static final String NS_ISCHEMAATTRIBUTECOMPONENT_IID = "{3c14a02d-6f4e-11d5-9b46-000064657374}";
    public static final int COMPONENT_TYPE_ATTRIBUTE = 1;
    public static final int COMPONENT_TYPE_GROUP = 2;
    public static final int COMPONENT_TYPE_ANY = 3;

    public String getName();

    public int getComponentType();
}

