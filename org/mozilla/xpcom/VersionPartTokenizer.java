/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.xpcom;

import java.util.Enumeration;

class VersionPartTokenizer
implements Enumeration {
    String part;

    public VersionPartTokenizer(String string) {
        this.part = string;
    }

    @Override
    public boolean hasMoreElements() {
        return this.part.length() != 0;
    }

    public boolean hasMoreTokens() {
        return this.part.length() != 0;
    }

    public Object nextElement() {
        int n;
        if (this.part.matches("[\\+\\-]?[0-9].*")) {
            int n2 = 0;
            if (this.part.charAt(0) == '+' || this.part.charAt(0) == '-') {
                n2 = 1;
            }
            while (n2 < this.part.length() && Character.isDigit(this.part.charAt(n2))) {
                ++n2;
            }
            String string = this.part.substring(0, n2);
            this.part = this.part.substring(n2);
            return string;
        }
        for (n = 0; n < this.part.length() && !Character.isDigit(this.part.charAt(n)); ++n) {
        }
        String string = this.part.substring(0, n);
        this.part = this.part.substring(n);
        return string;
    }

    public String nextToken() {
        return (String)this.nextElement();
    }

    public String getRemainder() {
        return this.part;
    }
}

