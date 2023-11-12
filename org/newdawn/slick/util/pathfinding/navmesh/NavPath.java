/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util.pathfinding.navmesh;

import java.util.ArrayList;
import org.newdawn.slick.util.pathfinding.navmesh.Link;

public class NavPath {
    private ArrayList links = new ArrayList();

    public void push(Link link) {
        this.links.add(link);
    }

    public int length() {
        return this.links.size();
    }

    public float getX(int n) {
        return ((Link)this.links.get(n)).getX();
    }

    public float getY(int n) {
        return ((Link)this.links.get(n)).getY();
    }

    public String toString() {
        return "[Path length=" + this.length() + "]";
    }

    public void remove(int n) {
        this.links.remove(n);
    }
}

