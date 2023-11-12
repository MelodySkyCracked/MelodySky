/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISchemaAttributeComponent;
import org.mozilla.interfaces.nsISchemaModelGroup;
import org.mozilla.interfaces.nsISchemaSimpleType;
import org.mozilla.interfaces.nsISchemaType;

public interface nsISchemaComplexType
extends nsISchemaType {
    public static final String NS_ISCHEMACOMPLEXTYPE_IID = "{3c14a028-6f4e-11d5-9b46-000064657374}";
    public static final int CONTENT_MODEL_EMPTY = 1;
    public static final int CONTENT_MODEL_SIMPLE = 2;
    public static final int CONTENT_MODEL_ELEMENT_ONLY = 3;
    public static final int CONTENT_MODEL_MIXED = 4;
    public static final int DERIVATION_EXTENSION_SIMPLE = 1;
    public static final int DERIVATION_RESTRICTION_SIMPLE = 2;
    public static final int DERIVATION_EXTENSION_COMPLEX = 3;
    public static final int DERIVATION_RESTRICTION_COMPLEX = 4;
    public static final int DERIVATION_SELF_CONTAINED = 5;

    public int getContentModel();

    public int getDerivation();

    public nsISchemaType getBaseType();

    public nsISchemaSimpleType getSimpleBaseType();

    public nsISchemaModelGroup getModelGroup();

    public long getAttributeCount();

    public nsISchemaAttributeComponent getAttributeByIndex(long var1);

    public nsISchemaAttributeComponent getAttributeByName(String var1);

    public boolean getAbstract();

    public boolean getIsArray();

    public nsISchemaType getArrayType();

    public long getArrayDimension();
}

