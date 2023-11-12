/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import java.util.Arrays;

public class TCHAR {
    public char[] chars;
    public static final int sizeof = 2;

    public TCHAR(int n, int n2) {
        this.chars = new char[n2];
    }

    public TCHAR(int n, char c, boolean bl) {
        char[] cArray;
        if (bl) {
            char[] cArray2 = new char[2];
            cArray2[0] = c;
            cArray = cArray2;
            cArray2[1] = '\u0000';
        } else {
            char[] cArray3 = new char[1];
            cArray = cArray3;
            cArray3[0] = c;
        }
        this(n, cArray, false);
    }

    public TCHAR(int n, char[] cArray, boolean bl) {
        int n2 = cArray.length;
        if (bl && (n2 == 0 || n2 > 0 && cArray[n2 - 1] != '\u0000')) {
            char[] cArray2 = new char[n2 + 1];
            System.arraycopy(cArray, 0, cArray2, 0, n2);
            cArray = cArray2;
        }
        this.chars = cArray;
    }

    public TCHAR(int n, String string, boolean bl) {
        this(n, TCHAR.getChars(string, bl), false);
    }

    static char[] getChars(String string, boolean bl) {
        int n = string.length();
        char[] cArray = new char[n + (bl ? 1 : 0)];
        string.getChars(0, n, cArray, 0);
        return cArray;
    }

    public void clear() {
        Arrays.fill(this.chars, '\u0000');
    }

    public int length() {
        return this.chars.length;
    }

    public int strlen() {
        for (int i = 0; i < this.chars.length; ++i) {
            if (this.chars[i] != '\u0000') continue;
            return i;
        }
        return this.chars.length;
    }

    public int tcharAt(int n) {
        return this.chars[n];
    }

    public String toString() {
        return this.toString(0, this.length());
    }

    public String toString(int n, int n2) {
        return new String(this.chars, n, n2);
    }
}

