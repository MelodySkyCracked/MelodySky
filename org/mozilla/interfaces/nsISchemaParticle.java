/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISchemaComponent;

public interface nsISchemaParticle
extends nsISchemaComponent {
    public static final String NS_ISCHEMAPARTICLE_IID = "{3c14a029-6f4e-11d5-9b46-000064657374}";
    public static final int PARTICLE_TYPE_ELEMENT = 1;
    public static final int PARTICLE_TYPE_MODEL_GROUP = 2;
    public static final int PARTICLE_TYPE_ANY = 3;
    public static final long OCCURRENCE_UNBOUNDED = 0xFFFFFFFFL;

    public String getName();

    public int getParticleType();

    public long getMinOccurs();

    public long getMaxOccurs();
}

