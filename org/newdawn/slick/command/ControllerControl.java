/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.command;

import org.newdawn.slick.command.Control;

abstract class ControllerControl
implements Control {
    protected static final int BUTTON_EVENT = 0;
    protected static final int LEFT_EVENT = 1;
    protected static final int RIGHT_EVENT = 2;
    protected static final int UP_EVENT = 3;
    protected static final int DOWN_EVENT = 4;
    private int event;
    private int button;
    private int controllerNumber;

    protected ControllerControl(int n, int n2, int n3) {
        this.event = n2;
        this.button = n3;
        this.controllerNumber = n;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!(object instanceof ControllerControl)) {
            return false;
        }
        ControllerControl controllerControl = (ControllerControl)object;
        return controllerControl.controllerNumber == this.controllerNumber && controllerControl.event == this.event && controllerControl.button == this.button;
    }

    public int hashCode() {
        return this.event + this.button + this.controllerNumber;
    }
}

