/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Event.value;

import xyz.Melody.Event.value.Value;

public class Mode
extends Value {
    private Enum[] modes;

    public Mode(String string, Enum[] enumArray, Enum enum_) {
        super(string, string);
        this.modes = enumArray;
        this.setValue(enum_);
    }

    public Enum[] getModes() {
        return this.modes;
    }

    public String getModeAsString() {
        return ((Enum)this.getValue()).name();
    }

    public void setMode(String string) {
        for (Enum enum_ : this.modes) {
            if (!enum_.name().equalsIgnoreCase(string)) continue;
            this.setValue(enum_);
        }
    }

    public boolean isValid(String string) {
        for (Enum enum_ : this.modes) {
            if (!enum_.name().equalsIgnoreCase(string)) continue;
            return true;
        }
        return false;
    }
}

