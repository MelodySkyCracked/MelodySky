/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.command;

import org.newdawn.slick.command.Control;

public class MouseButtonControl
implements Control {
    private int button;

    public MouseButtonControl(int n) {
        this.button = n;
    }

    public boolean equals(Object object) {
        if (object instanceof MouseButtonControl) {
            return ((MouseButtonControl)object).button == this.button;
        }
        return false;
    }

    public int hashCode() {
        return this.button;
    }
}

