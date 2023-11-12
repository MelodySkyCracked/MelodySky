/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.command;

import org.newdawn.slick.command.Control;

public class KeyControl
implements Control {
    private int keycode;

    public KeyControl(int n) {
        this.keycode = n;
    }

    public boolean equals(Object object) {
        if (object instanceof KeyControl) {
            return ((KeyControl)object).keycode == this.keycode;
        }
        return false;
    }

    public int hashCode() {
        return this.keycode;
    }
}

