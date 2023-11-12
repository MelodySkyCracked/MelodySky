/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util.pathfinding.navmesh;

import java.util.ArrayList;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import org.newdawn.slick.util.pathfinding.navmesh.NavMesh;
import org.newdawn.slick.util.pathfinding.navmesh.Space;

public class NavMeshBuilder
implements PathFindingContext {
    private int sx;
    private int sy;
    private float smallestSpace = 0.2f;
    private boolean tileBased;

    public NavMesh build(TileBasedMap tileBasedMap) {
        return this.build(tileBasedMap, true);
    }

    public NavMesh build(TileBasedMap tileBasedMap, boolean bl) {
        this.tileBased = bl;
        ArrayList<Space> arrayList = new ArrayList<Space>();
        if (bl) {
            for (int i = 0; i < tileBasedMap.getWidthInTiles(); ++i) {
                for (int j = 0; j < tileBasedMap.getHeightInTiles(); ++j) {
                    if (tileBasedMap.blocked(this, i, j)) continue;
                    arrayList.add(new Space(i, j, 1.0f, 1.0f));
                }
            }
        } else {
            Space space = new Space(0.0f, 0.0f, tileBasedMap.getWidthInTiles(), tileBasedMap.getHeightInTiles());
            this.subsection(tileBasedMap, space, arrayList);
        }
        while (this < arrayList) {
        }
        this.linkSpaces(arrayList);
        return new NavMesh(arrayList);
    }

    private void linkSpaces(ArrayList arrayList) {
        for (int i = 0; i < arrayList.size(); ++i) {
            Space space = (Space)arrayList.get(i);
            for (int j = i + 1; j < arrayList.size(); ++j) {
                Space space2 = (Space)arrayList.get(j);
                if (!space.hasJoinedEdge(space2)) continue;
                space.link(space2);
                space2.link(space);
            }
        }
    }

    private void subsection(TileBasedMap tileBasedMap, Space space, ArrayList arrayList) {
        NavMeshBuilder navMeshBuilder = this;
        TileBasedMap tileBasedMap2 = tileBasedMap;
        if (space != false) {
            float f = space.getWidth() / 2.0f;
            float f2 = space.getHeight() / 2.0f;
            if (f < this.smallestSpace && f2 < this.smallestSpace) {
                return;
            }
            this.subsection(tileBasedMap, new Space(space.getX(), space.getY(), f, f2), arrayList);
            this.subsection(tileBasedMap, new Space(space.getX(), space.getY() + f2, f, f2), arrayList);
            this.subsection(tileBasedMap, new Space(space.getX() + f, space.getY(), f, f2), arrayList);
            this.subsection(tileBasedMap, new Space(space.getX() + f, space.getY() + f2, f, f2), arrayList);
        } else {
            arrayList.add(space);
        }
    }

    @Override
    public Mover getMover() {
        return null;
    }

    @Override
    public int getSearchDistance() {
        return 0;
    }

    @Override
    public int getSourceX() {
        return this.sx;
    }

    @Override
    public int getSourceY() {
        return this.sy;
    }
}

