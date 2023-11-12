/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util.pathfinding.heuristics;

import org.newdawn.slick.util.pathfinding.AStarHeuristic;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class ClosestSquaredHeuristic
implements AStarHeuristic {
    @Override
    public float getCost(TileBasedMap tileBasedMap, Mover mover, int n, int n2, int n3, int n4) {
        float f = n3 - n;
        float f2 = n4 - n2;
        return f * f + f2 * f2;
    }
}

