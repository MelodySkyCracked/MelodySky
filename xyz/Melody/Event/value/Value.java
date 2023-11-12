/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Event.value;

public abstract class Value {
    private String displayName;
    private String name;
    private Object value;

    public Value(String string, String string2) {
        this.displayName = string;
        this.name = string2;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object object) {
        this.value = object;
    }
}

