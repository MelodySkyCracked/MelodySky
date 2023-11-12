/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal;

import java.lang.reflect.Type;
import java.util.function.Function;
import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.C;

public class Callback {
    Object object;
    String method;
    String signature;
    int argCount;
    long address;
    long errorResult;
    boolean isStatic;
    boolean isArrayBased;
    static final boolean is32Bit = C.PTR_SIZEOF == 4;
    static final String PTR_SIGNATURE = is32Bit ? "I" : "J";
    static final String SIGNATURE_0 = Callback.getSignature(0);
    static final String SIGNATURE_1 = Callback.getSignature(1);
    static final String SIGNATURE_2 = Callback.getSignature(2);
    static final String SIGNATURE_3 = Callback.getSignature(3);
    static final String SIGNATURE_4 = Callback.getSignature(4);
    static final String SIGNATURE_N = "([" + PTR_SIGNATURE + ")" + PTR_SIGNATURE;

    public Callback(Object object, String string, int n) {
        this(object, string, n, false);
    }

    public Callback(Object object, String string, int n, boolean bl) {
        this(object, string, n, bl, 0L);
    }

    public Callback(Object object, String string, int n, boolean bl, long l2) {
        this.object = object;
        this.method = string;
        this.argCount = n;
        this.isStatic = object instanceof Class;
        this.isArrayBased = bl;
        this.errorResult = l2;
        if (bl) {
            this.signature = SIGNATURE_N;
        } else {
            switch (n) {
                case 0: {
                    this.signature = SIGNATURE_0;
                    break;
                }
                case 1: {
                    this.signature = SIGNATURE_1;
                    break;
                }
                case 2: {
                    this.signature = SIGNATURE_2;
                    break;
                }
                case 3: {
                    this.signature = SIGNATURE_3;
                    break;
                }
                case 4: {
                    this.signature = SIGNATURE_4;
                    break;
                }
                default: {
                    this.signature = Callback.getSignature(n);
                }
            }
        }
        this.address = Callback.bind(this, object, string, this.signature, n, this.isStatic, bl, l2);
        if (this.address == 0L) {
            SWT.error(3);
        }
    }

    public Callback(Object object, String string, Type type, Type[] typeArray) {
        this.object = object;
        this.method = string;
        this.argCount = typeArray != null ? typeArray.length : 0;
        this.isStatic = object instanceof Class;
        this.isArrayBased = false;
        this.errorResult = 0L;
        Function<Type, String> function = Callback::lambda$new$0;
        StringBuilder stringBuilder = new StringBuilder("(");
        if (this.argCount > 0) {
            for (Type type2 : typeArray) {
                if (type2.equals(Void.TYPE)) {
                    SWT.error(5, null, "void is not a valid argument");
                }
                stringBuilder.append(function.apply(type2));
            }
        }
        stringBuilder.append(")");
        stringBuilder.append(function.apply(type));
        this.signature = stringBuilder.toString();
        if (is32Bit) {
            this.signature = this.signature.replace("J", "I");
        }
        this.address = Callback.bind(this, this.object, this.method, this.signature, this.argCount, this.isStatic, this.isArrayBased, this.errorResult);
        if (this.address == 0L) {
            SWT.error(3);
        }
    }

    static synchronized native long bind(Callback var0, Object var1, String var2, String var3, int var4, boolean var5, boolean var6, long var7);

    public void dispose() {
        if (this.object == null) {
            return;
        }
        Callback.unbind(this);
        Object var1_1 = null;
        this.signature = var1_1;
        this.method = var1_1;
        this.object = var1_1;
        this.address = 0L;
    }

    public long getAddress() {
        return this.address;
    }

    public static native String getPlatform();

    public static native int getEntryCount();

    static String getSignature(int n) {
        String string = "(";
        for (int i = 0; i < n; ++i) {
            string = string + PTR_SIGNATURE;
        }
        string = string + ")" + PTR_SIGNATURE;
        return string;
    }

    public static final synchronized native void setEnabled(boolean var0);

    public static final synchronized native boolean getEnabled();

    @Deprecated
    static final void ignoreCallbacks(boolean bl) {
        Callback.setEnabled(!bl);
    }

    public static final synchronized native void reset();

    static final synchronized native void unbind(Callback var0);

    private static String lambda$new$0(Type type) {
        if (Integer.TYPE.equals(type)) {
            return "I";
        }
        if (Long.TYPE.equals(type)) {
            return "J";
        }
        if (Void.TYPE.equals(type)) {
            return "V";
        }
        if (Byte.TYPE.equals(type)) {
            return "B";
        }
        if (Character.TYPE.equals(type)) {
            return "C";
        }
        if (Double.TYPE.equals(type)) {
            return "D";
        }
        if (Float.TYPE.equals(type)) {
            return "F";
        }
        if (Short.TYPE.equals(type)) {
            return "S";
        }
        if (Boolean.TYPE.equals(type)) {
            return "Z";
        }
        SWT.error(5, null, type.toString() + "Not supported");
        return null;
    }
}

