/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.common;

public interface Filter {
    public Acceptance accept(Object var1);

    public static enum Acceptance {
        YES,
        NO,
        TEST_CHILDREN;

    }
}

