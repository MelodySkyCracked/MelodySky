/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib;

final class Item {
    int index;
    int type;
    int intVal;
    long longVal;
    String strVal1;
    String strVal2;
    String strVal3;
    int hashCode;
    Item next;

    Item() {
    }

    Item(int n) {
        this.index = n;
    }

    Item(int n, Item item) {
        this.index = n;
        this.type = item.type;
        this.intVal = item.intVal;
        this.longVal = item.longVal;
        this.strVal1 = item.strVal1;
        this.strVal2 = item.strVal2;
        this.strVal3 = item.strVal3;
        this.hashCode = item.hashCode;
    }

    void set(int n) {
        this.type = 3;
        this.intVal = n;
        this.hashCode = Integer.MAX_VALUE & this.type + n;
    }

    void set(long l2) {
        this.type = 5;
        this.longVal = l2;
        this.hashCode = Integer.MAX_VALUE & this.type + (int)l2;
    }

    void set(float f) {
        this.type = 4;
        this.intVal = Float.floatToRawIntBits(f);
        this.hashCode = Integer.MAX_VALUE & this.type + (int)f;
    }

    void set(double d) {
        this.type = 6;
        this.longVal = Double.doubleToRawLongBits(d);
        this.hashCode = Integer.MAX_VALUE & this.type + (int)d;
    }

    void set(int n, String string, String string2, String string3) {
        this.type = n;
        this.strVal1 = string;
        this.strVal2 = string2;
        this.strVal3 = string3;
        switch (n) {
            case 7: {
                this.intVal = 0;
            }
            case 1: 
            case 8: 
            case 16: 
            case 30: {
                this.hashCode = Integer.MAX_VALUE & n + string.hashCode();
                return;
            }
            case 12: {
                this.hashCode = Integer.MAX_VALUE & n + string.hashCode() * string2.hashCode();
                return;
            }
        }
        this.hashCode = Integer.MAX_VALUE & n + string.hashCode() * string2.hashCode() * string3.hashCode();
    }

    void set(String string, String string2, int n) {
        this.type = 18;
        this.longVal = n;
        this.strVal1 = string;
        this.strVal2 = string2;
        this.hashCode = Integer.MAX_VALUE & 18 + n * this.strVal1.hashCode() * this.strVal2.hashCode();
    }

    void set(int n, int n2) {
        this.type = 33;
        this.intVal = n;
        this.hashCode = n2;
    }

    boolean isEqualTo(Item item) {
        switch (this.type) {
            case 1: 
            case 7: 
            case 8: 
            case 16: 
            case 30: {
                return item.strVal1.equals(this.strVal1);
            }
            case 5: 
            case 6: 
            case 32: {
                return item.longVal == this.longVal;
            }
            case 3: 
            case 4: {
                return item.intVal == this.intVal;
            }
            case 31: {
                return item.intVal == this.intVal && item.strVal1.equals(this.strVal1);
            }
            case 12: {
                return item.strVal1.equals(this.strVal1) && item.strVal2.equals(this.strVal2);
            }
            case 18: {
                return item.longVal == this.longVal && item.strVal1.equals(this.strVal1) && item.strVal2.equals(this.strVal2);
            }
        }
        return item.strVal1.equals(this.strVal1) && item.strVal2.equals(this.strVal2) && item.strVal3.equals(this.strVal3);
    }
}

