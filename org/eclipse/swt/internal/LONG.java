/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal;

public class LONG {
    public long value;

    public LONG(long l2) {
        this.value = l2;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof LONG)) {
            return false;
        }
        LONG lONG = (LONG)object;
        return lONG.value == this.value;
    }

    public int hashCode() {
        return (int)(this.value ^ this.value >>> 32);
    }
}

