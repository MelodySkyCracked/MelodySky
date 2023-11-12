/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

class SashFormData {
    long weight;

    SashFormData() {
    }

    String getName() {
        String string = this.getClass().getName();
        int n = string.lastIndexOf(46);
        if (n == -1) {
            return string;
        }
        return string.substring(n + 1, string.length());
    }

    public String toString() {
        return this.getName() + " {weight=" + this.weight;
    }
}

