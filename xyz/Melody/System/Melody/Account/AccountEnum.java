/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.System.Melody.Account;

public enum AccountEnum {
    OFFLINE("OFFLINE"),
    XBLTOKEN("XBOXLIVE"),
    MICROSOFT("MICROSOFT"),
    ORIGINAL("ORIGINAL");

    private final String writeName;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private AccountEnum() {
        void var3_1;
        this.writeName = var3_1;
    }

    public String getWriteName() {
        return this.writeName;
    }

    public static AccountEnum parse(String string) {
        for (AccountEnum accountEnum : AccountEnum.values()) {
            if (!accountEnum.writeName.equals(string)) continue;
            return accountEnum;
        }
        return null;
    }
}

