/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.xpcom;

import java.util.StringTokenizer;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIVersionComparator;
import org.mozilla.xpcom.I;
import org.mozilla.xpcom.Mozilla;
import org.mozilla.xpcom.VersionPartTokenizer;

public class VersionComparator
implements nsIVersionComparator {
    @Override
    public nsISupports queryInterface(String string) {
        return Mozilla.queryInterface(this, string);
    }

    @Override
    public int compare(String string, String string2) {
        VersionPart versionPart;
        VersionPart versionPart2;
        int n;
        String string3 = string;
        String string4 = string2;
        do {
            versionPart2 = new VersionPart(this, null);
            versionPart = new VersionPart(this, null);
            string3 = VersionComparator.parseVersionPart(string3, versionPart2);
            string4 = VersionComparator.parseVersionPart(string4, versionPart);
        } while ((n = this.compareVersionPart(versionPart2, versionPart)) == 0 && (string3 != null || string4 != null));
        return n;
    }

    private static String parseVersionPart(String string, VersionPart versionPart) {
        if (string == null || string.length() == 0) {
            return string;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(string.trim(), ".");
        String string2 = stringTokenizer.nextToken();
        if (string2.equals("*")) {
            versionPart.numA = Integer.MAX_VALUE;
            versionPart.strB = "";
        } else {
            VersionPartTokenizer versionPartTokenizer = new VersionPartTokenizer(string2);
            try {
                versionPart.numA = Integer.parseInt(versionPartTokenizer.nextToken());
            }
            catch (NumberFormatException numberFormatException) {
                versionPart.numA = 0;
            }
            if (versionPartTokenizer.hasMoreElements()) {
                String string3 = versionPartTokenizer.nextToken();
                if (string3.charAt(0) == '+') {
                    ++versionPart.numA;
                    versionPart.strB = "pre";
                } else {
                    versionPart.strB = string3;
                    if (versionPartTokenizer.hasMoreTokens()) {
                        try {
                            versionPart.numC = Integer.parseInt(versionPartTokenizer.nextToken());
                        }
                        catch (NumberFormatException numberFormatException) {
                            versionPart.numC = 0;
                        }
                        if (versionPartTokenizer.hasMoreTokens()) {
                            versionPart.extraD = versionPartTokenizer.getRemainder();
                        }
                    }
                }
            }
        }
        if (stringTokenizer.hasMoreTokens()) {
            return string.substring(string2.length() + 1);
        }
        return null;
    }

    private int compareVersionPart(VersionPart versionPart, VersionPart versionPart2) {
        int n = this.compareInt(versionPart.numA, versionPart2.numA);
        if (n != 0) {
            return n;
        }
        int n2 = this.compareString(versionPart.strB, versionPart2.strB);
        if (n2 != 0) {
            return n2;
        }
        int n3 = this.compareInt(versionPart.numC, versionPart2.numC);
        if (n3 != 0) {
            return n3;
        }
        return this.compareString(versionPart.extraD, versionPart2.extraD);
    }

    private int compareInt(int n, int n2) {
        return n - n2;
    }

    private int compareString(String string, String string2) {
        if (string == null) {
            return string2 != null ? 1 : 0;
        }
        if (string2 == null) {
            return -1;
        }
        return string.compareTo(string2);
    }

    private class VersionPart {
        int numA;
        String strB;
        int numC;
        String extraD;
        final VersionComparator this$0;

        private VersionPart(VersionComparator versionComparator) {
            this.this$0 = versionComparator;
            this.numA = 0;
            this.numC = 0;
        }

        VersionPart(VersionComparator versionComparator, I i) {
            this(versionComparator);
        }
    }
}

