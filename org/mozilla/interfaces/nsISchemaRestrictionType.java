/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISchemaFacet;
import org.mozilla.interfaces.nsISchemaSimpleType;

public interface nsISchemaRestrictionType
extends nsISchemaSimpleType {
    public static final String NS_ISCHEMARESTRICTIONTYPE_IID = "{3c14a027-6f4e-11d5-9b46-000064657374}";

    public nsISchemaSimpleType getBaseType();

    public long getFacetCount();

    public nsISchemaFacet getFacet(long var1);
}

