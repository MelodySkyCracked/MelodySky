/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Event.value;

import xyz.Melody.Event.value.Value;

public class TextValue
extends Value {
    public Object text;

    public TextValue(String string, Object object) {
        super(string, string);
        this.text = object;
    }

    @Override
    public Object getValue() {
        return this.text;
    }

    @Override
    public void setValue(Object object) {
        this.text = object;
    }
}

