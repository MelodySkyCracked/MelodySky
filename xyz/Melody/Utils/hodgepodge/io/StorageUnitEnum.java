/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.hodgepodge.io;

import xyz.Melody.Utils.hodgepodge.io.I;
import xyz.Melody.Utils.hodgepodge.io.l;
import xyz.Melody.Utils.hodgepodge.io.lI;
import xyz.Melody.Utils.hodgepodge.io.lII;
import xyz.Melody.Utils.hodgepodge.io.lIII;
import xyz.Melody.Utils.hodgepodge.io.lIIl;
import xyz.Melody.Utils.hodgepodge.io.lIl;
import xyz.Melody.Utils.hodgepodge.io.ll;
import xyz.Melody.Utils.hodgepodge.io.llI;
import xyz.Melody.Utils.hodgepodge.io.lll;

public abstract class StorageUnitEnum
extends Enum {
    public static final /* enum */ StorageUnitEnum BYTE = new llI("B");
    public static final /* enum */ StorageUnitEnum KILOBYTE = new lII("KB");
    public static final /* enum */ StorageUnitEnum MEGABYTE = new lIIl("MB");
    public static final /* enum */ StorageUnitEnum GIGABYTE = new lI("GB");
    public static final /* enum */ StorageUnitEnum TERABYTE = new lIII("TB");
    public static final /* enum */ StorageUnitEnum PETABYTE = new I("PB");
    public static final /* enum */ StorageUnitEnum EXABYTE = new ll("EB");
    public static final /* enum */ StorageUnitEnum ZETTA_BYTE = new l("ZB");
    public static final /* enum */ StorageUnitEnum YOTTA_BYTE = new lll("YB");
    public static final /* enum */ StorageUnitEnum BRONTO_BYTE = new lIl("BB");
    private final String abbreviation;
    private static final StorageUnitEnum[] $VALUES = new StorageUnitEnum[]{BYTE, KILOBYTE, MEGABYTE, GIGABYTE, TERABYTE, PETABYTE, EXABYTE, ZETTA_BYTE, YOTTA_BYTE, BRONTO_BYTE};

    public static StorageUnitEnum[] values() {
        return (StorageUnitEnum[])$VALUES.clone();
    }

    public static StorageUnitEnum valueOf(String string) {
        return Enum.valueOf(StorageUnitEnum.class, string);
    }

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private StorageUnitEnum() {
        void var3_1;
        void var2_-1;
        void var1_-1;
        this.abbreviation = var3_1;
    }

    public abstract double toByte(double var1);

    public abstract double toKilobyte(double var1);

    public abstract double toMegabyte(double var1);

    public abstract double toGigabyte(double var1);

    public abstract double toTerabyte(double var1);

    public abstract double toPetabyte(double var1);

    public abstract double toExabyte(double var1);

    public abstract double toZettaByte(double var1);

    public abstract double toYottaByte(double var1);

    public abstract double toBrontoByte(double var1);

    public String getAbbreviation() {
        return this.abbreviation;
    }

    /*
     * WARNING - void declaration
     */
    StorageUnitEnum() {
        this((String)var1_-1, (int)llI2, (String)var3_2);
        void var3_2;
        void var1_-1;
    }
}

