/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Event.value;

import xyz.Melody.Event.value.Value;

public class Numbers
extends Value {
    private String name;
    public Number min;
    public Number max;
    public Number inc;
    private boolean integer;

    public Numbers(String string, Number number, Number number2, Number number3, Number number4) {
        super(string, string);
        this.setValue(number);
        this.min = number2;
        this.max = number3;
        this.inc = number4;
        this.integer = false;
    }

    public Number getMinimum() {
        return this.min;
    }

    public Number getMaximum() {
        return this.max;
    }

    public void setIncrement(Number number) {
        this.inc = number;
    }

    public Number getIncrement() {
        return this.inc;
    }

    public String getId() {
        return this.name;
    }
}

