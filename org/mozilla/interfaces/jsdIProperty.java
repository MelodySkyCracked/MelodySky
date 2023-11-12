/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.jsdIEphemeral;
import org.mozilla.interfaces.jsdIValue;

public interface jsdIProperty
extends jsdIEphemeral {
    public static final String JSDIPROPERTY_IID = "{b8816e56-1dd1-11b2-81dc-8ba99a833d9e}";
    public static final long FLAG_ENUMERATE = 1L;
    public static final long FLAG_READONLY = 2L;
    public static final long FLAG_PERMANENT = 4L;
    public static final long FLAG_ALIAS = 8L;
    public static final long FLAG_ARGUMENT = 16L;
    public static final long FLAG_VARIABLE = 32L;
    public static final long FLAG_EXCEPTION = 64L;
    public static final long FLAG_ERROR = 128L;
    public static final long FLAG_HINTED = 2048L;

    public long getFlags();

    public jsdIValue getAlias();

    public jsdIValue getName();

    public jsdIValue getValue();

    public long getVarArgSlot();
}

