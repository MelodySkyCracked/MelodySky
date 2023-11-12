/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.xpcom;

import org.mozilla.xpcom.VersionComparator;

public class GREVersionRange {
    private String lower;
    private boolean lowerInclusive;
    private String upper;
    private boolean upperInclusive;

    public GREVersionRange(String string, boolean bl, String string2, boolean bl2) {
        this.lower = string;
        this.lowerInclusive = bl;
        this.upper = string2;
        this.upperInclusive = bl2;
    }

    public boolean check(String string) {
        VersionComparator versionComparator = new VersionComparator();
        int n = versionComparator.compare(string, this.lower);
        if (n < 0) {
            return false;
        }
        if (n == 0 && !this.lowerInclusive) {
            return false;
        }
        int n2 = versionComparator.compare(string, this.upper);
        return n2 <= 0 && (n2 != 0 || this.upperInclusive);
    }
}

