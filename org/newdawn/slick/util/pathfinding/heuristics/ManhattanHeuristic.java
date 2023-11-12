/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util.pathfinding.heuristics;

import org.newdawn.slick.util.pathfinding.AStarHeuristic;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class ManhattanHeuristic
implements AStarHeuristic {
    private int minimumCost;

    public ManhattanHeuristic(int n) {
        this.minimumCost = n;
    }

    @Override
    public float getCost(TileBasedMap tileBasedMap, Mover mover, int n, int n2, int n3, int n4) {
        return this.minimumCost * (Math.abs(n - n3) + Math.abs(n2 - n4));
    }
}

