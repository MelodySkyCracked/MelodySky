/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISchemaElement;
import org.mozilla.interfaces.nsISchemaParticle;

public interface nsISchemaModelGroup
extends nsISchemaParticle {
    public static final String NS_ISCHEMAMODELGROUP_IID = "{3c14a02a-6f4e-11d5-9b46-000064657374}";
    public static final int COMPOSITOR_ALL = 1;
    public static final int COMPOSITOR_SEQUENCE = 2;
    public static final int COMPOSITOR_CHOICE = 3;

    public int getCompositor();

    public long getParticleCount();

    public nsISchemaParticle getParticle(long var1);

    public nsISchemaElement getElementByName(String var1);
}

