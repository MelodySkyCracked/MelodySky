/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.command;

import org.newdawn.slick.command.ControllerControl;

public class ControllerDirectionControl
extends ControllerControl {
    public static final Direction LEFT = new Direction(1);
    public static final Direction UP = new Direction(3);
    public static final Direction DOWN = new Direction(4);
    public static final Direction RIGHT = new Direction(2);

    public ControllerDirectionControl(int n, Direction direction) {
        super(n, Direction.access$000(direction), 0);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object);
    }

    private static class Direction {
        private int event;

        public Direction(int n) {
            this.event = n;
        }

        static int access$000(Direction direction) {
            return direction.event;
        }
    }
}

