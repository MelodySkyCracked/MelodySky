/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import java.io.IOException;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Bootstrap;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;
import org.newdawn.slick.util.pathfinding.navmesh.Link;
import org.newdawn.slick.util.pathfinding.navmesh.NavMesh;
import org.newdawn.slick.util.pathfinding.navmesh.NavMeshBuilder;
import org.newdawn.slick.util.pathfinding.navmesh.NavPath;
import org.newdawn.slick.util.pathfinding.navmesh.Space;

public class NavMeshTest
extends BasicGame
implements PathFindingContext {
    private NavMesh navMesh;
    private NavMeshBuilder builder;
    private boolean showSpaces = true;
    private boolean showLinks = true;
    private NavPath path;
    private float sx;
    private float sy;
    private float ex;
    private float ey;
    private DataMap dataMap;

    public NavMeshTest() {
        super("Nav-mesh Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        gameContainer.setShowFPS(false);
        try {
            this.dataMap = new DataMap(this, "testdata/map.dat");
        }
        catch (IOException iOException) {
            throw new SlickException("Failed to load map data", iOException);
        }
        this.builder = new NavMeshBuilder();
        this.navMesh = this.builder.build(this.dataMap);
        System.out.println("Navmesh shapes: " + this.navMesh.getSpaceCount());
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
        if (gameContainer.getInput().isKeyPressed(2)) {
            boolean bl = this.showLinks = !this.showLinks;
        }
        if (gameContainer.getInput().isKeyPressed(3)) {
            this.showSpaces = !this.showSpaces;
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        int n;
        graphics.translate(50.0f, 50.0f);
        for (n = 0; n < 50; ++n) {
            for (int i = 0; i < 50; ++i) {
                if (!this.dataMap.blocked(this, n, i)) continue;
                graphics.setColor(Color.gray);
                graphics.fillRect(n * 10 + 1, i * 10 + 1, 8.0f, 8.0f);
            }
        }
        if (this.showSpaces) {
            for (n = 0; n < this.navMesh.getSpaceCount(); ++n) {
                Space space = this.navMesh.getSpace(n);
                if (this.builder.clear(this.dataMap, space)) {
                    graphics.setColor(new Color(1.0f, 1.0f, 0.0f, 0.5f));
                    graphics.fillRect(space.getX() * 10.0f, space.getY() * 10.0f, space.getWidth() * 10.0f, space.getHeight() * 10.0f);
                }
                graphics.setColor(Color.yellow);
                graphics.drawRect(space.getX() * 10.0f, space.getY() * 10.0f, space.getWidth() * 10.0f, space.getHeight() * 10.0f);
                if (!this.showLinks) continue;
                int n2 = space.getLinkCount();
                for (int i = 0; i < n2; ++i) {
                    Link link = space.getLink(i);
                    graphics.setColor(Color.red);
                    graphics.fillRect(link.getX() * 10.0f - 2.0f, link.getY() * 10.0f - 2.0f, 5.0f, 5.0f);
                }
            }
        }
        if (this.path != null) {
            graphics.setColor(Color.white);
            for (n = 0; n < this.path.length() - 1; ++n) {
                graphics.drawLine(this.path.getX(n) * 10.0f, this.path.getY(n) * 10.0f, this.path.getX(n + 1) * 10.0f, this.path.getY(n + 1) * 10.0f);
            }
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
        return 0;
    }

    @Override
    public int getSourceY() {
        return 0;
    }

    @Override
    public void mousePressed(int n, int n2, int n3) {
        float f = (float)(n2 - 50) / 10.0f;
        float f2 = (float)(n3 - 50) / 10.0f;
        if (n == 0) {
            this.sx = f;
            this.sy = f2;
        } else {
            this.ex = f;
            this.ey = f2;
        }
        this.path = this.navMesh.findPath(this.sx, this.sy, this.ex, this.ey, true);
    }

    public static void main(String[] stringArray) {
        Bootstrap.runAsApplication(new NavMeshTest(), 600, 600, false);
    }

    private class DataMap
    implements TileBasedMap {
        private byte[] map;
        final NavMeshTest this$0;

        public DataMap(NavMeshTest navMeshTest, String string) throws IOException {
            this.this$0 = navMeshTest;
            this.map = new byte[2500];
            ResourceLoader.getResourceAsStream(string).read(this.map);
        }

        @Override
        public boolean blocked(PathFindingContext pathFindingContext, int n, int n2) {
            if (n < 0 || n2 < 0 || n >= 50 || n2 >= 50) {
                return false;
            }
            return this.map[n + n2 * 50] != 0;
        }

        @Override
        public float getCost(PathFindingContext pathFindingContext, int n, int n2) {
            return 1.0f;
        }

        @Override
        public int getHeightInTiles() {
            return 50;
        }

        @Override
        public int getWidthInTiles() {
            return 50;
        }

        @Override
        public void pathFinderVisited(int n, int n2) {
        }
    }
}

