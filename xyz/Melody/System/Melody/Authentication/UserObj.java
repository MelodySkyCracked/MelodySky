/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.System.Melody.Authentication;

public final class UserObj {
    private String uuid;
    private String tkn;
    private String name;

    public String getName() {
        return this.name;
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getToken() {
        return this.tkn;
    }

    public UserObj(String string, String string2, String string3) {
        this.name = string;
        this.uuid = string2;
        this.tkn = string3;
    }
}

