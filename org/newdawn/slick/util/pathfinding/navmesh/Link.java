/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util.pathfinding.navmesh;

import org.newdawn.slick.util.pathfinding.navmesh.Space;

public class Link {
    private float px;
    private float py;
    private Space target;

    public Link(float f, float f2, Space space) {
        this.px = f;
        this.py = f2;
        this.target = space;
    }

    public float distance2(float f, float f2) {
        float f3 = f - this.px;
        float f4 = f2 - this.py;
        return f3 * f3 + f4 * f4;
    }

    public float getX() {
        return this.px;
    }

    public float getY() {
        return this.py;
    }

    public Space getTarget() {
        return this.target;
    }
}

