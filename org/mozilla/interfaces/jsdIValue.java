/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.jsdIEphemeral;
import org.mozilla.interfaces.jsdIObject;
import org.mozilla.interfaces.jsdIProperty;

public interface jsdIValue
extends jsdIEphemeral {
    public static final String JSDIVALUE_IID = "{b7964304-1dd1-11b2-ba20-cf4205772e9d}";
    public static final long TYPE_BOOLEAN = 0L;
    public static final long TYPE_DOUBLE = 1L;
    public static final long TYPE_INT = 2L;
    public static final long TYPE_FUNCTION = 3L;
    public static final long TYPE_NULL = 4L;
    public static final long TYPE_OBJECT = 5L;
    public static final long TYPE_STRING = 6L;
    public static final long TYPE_VOID = 7L;

    public boolean getIsNative();

    public boolean getIsNumber();

    public boolean getIsPrimitive();

    public long getJsType();

    public jsdIValue getJsPrototype();

    public jsdIValue getJsParent();

    public String getJsClassName();

    public jsdIValue getJsConstructor();

    public String getJsFunctionName();

    public boolean getBooleanValue();

    public double getDoubleValue();

    public int getIntValue();

    public jsdIObject getObjectValue();

    public String getStringValue();

    public int getPropertyCount();

    public void getProperties(jsdIProperty[][] var1, long[] var2);

    public jsdIProperty getProperty(String var1);

    public void refresh();

    public void getWrappedValue();
}

