/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.util;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionNumber
implements Comparable,
Serializable {
    private static final long serialVersionUID = 1L;
    public static final VersionNumber NONE = new VersionNumber();
    private static final Pattern versionNumberPattern = Pattern.compile("^(\\d{1,5})(?:\\.(\\d{1,5})(?:\\.(\\d{1,5})(?:\\.(\\d{1,5}))?)?)?(-[a-zA-Z0-9_\\-]+)?$");
    private final long value;
    private final String suffix;

    private VersionNumber() {
        this.value = 0L;
        this.suffix = "";
    }

    private VersionNumber(short[] sArray) {
        this(sArray, null);
    }

    private VersionNumber(short[] sArray, String string) {
        this.value = VersionNumber.pack(sArray);
        this.suffix = string != null ? string : "";
    }

    private VersionNumber(short s, short s2, short s3, short s4) {
        this(s, s2, s3, s4, null);
    }

    private VersionNumber(short s, short s2, short s3, short s4, String string) {
        this.value = VersionNumber.pack(s, s2, s3, s4);
        this.suffix = string != null ? string : "";
    }

    public String toString() {
        short[] sArray = VersionNumber.unpack(this.value);
        return String.format("%d.%d%3$s%4$s%5$s", sArray[0], sArray[1], (this.value & Integer.MAX_VALUE) > 0L ? String.format(".%d", sArray[2]) : "", (this.value & 0x7FFFL) > 0L ? String.format(".%d", sArray[3]) : "", this.suffix);
    }

    public int compareTo(VersionNumber versionNumber) {
        if (versionNumber == null) {
            return 1;
        }
        long l2 = this.value - versionNumber.value;
        return l2 > 0L ? 1 : (l2 < 0L ? -1 : 0);
    }

    public boolean equals(Object object) {
        if (!(object instanceof VersionNumber)) {
            return false;
        }
        return ((VersionNumber)object).value == this.value;
    }

    public int hashCode() {
        return (int)(this.value >> 32) ^ (int)(this.value & 0xFFFFFFFFFFFFFFFFL);
    }

    private static long pack(short ... sArray) {
        return (long)sArray[0] << 48 | (long)sArray[1] << 32 | (long)(sArray[2] << 16) | (long)sArray[3];
    }

    private static short[] unpack(long l2) {
        return new short[]{(short)(l2 >> 48), (short)(l2 >> 32 & 0x7FFFL), (short)(l2 >> 16 & 0x7FFFL), (short)(l2 & 0x7FFFL)};
    }

    public static final VersionNumber parse(String string) {
        return VersionNumber.parse(string, NONE);
    }

    public static VersionNumber parse(String string, String string2) {
        return VersionNumber.parse(string, VersionNumber.parse(string2));
    }

    private static VersionNumber parse(String string, VersionNumber versionNumber) {
        if (string == null) {
            return versionNumber;
        }
        Matcher matcher = versionNumberPattern.matcher(string);
        if (!matcher.matches()) {
            return versionNumber;
        }
        short[] sArray = new short[4];
        for (int i = 0; i < 4; ++i) {
            String string2 = matcher.group(i + 1);
            if (string2 == null) continue;
            int n = Integer.parseInt(string2);
            if (n > Short.MAX_VALUE) {
                throw new IllegalArgumentException("Version parts cannot exceed 32767, found " + n);
            }
            sArray[i] = (short)n;
        }
        return new VersionNumber(sArray, matcher.group(5));
    }

    public int compareTo(Object object) {
        return this.compareTo((VersionNumber)object);
    }
}

