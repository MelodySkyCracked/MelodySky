/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISchemaParticle;
import org.mozilla.interfaces.nsISchemaType;

public interface nsISchemaElement
extends nsISchemaParticle {
    public static final String NS_ISCHEMAELEMENT_IID = "{3c14a02c-6f4e-11d5-9b46-000064657374}";

    public nsISchemaType getType();

    public String getDefaultValue();

    public String getFixedValue();

    public boolean getNillable();

    public boolean getAbstract();
}

