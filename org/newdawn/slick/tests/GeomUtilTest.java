/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.GeomUtil;
import org.newdawn.slick.geom.GeomUtilListener;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

public class GeomUtilTest
extends BasicGame
implements GeomUtilListener {
    private Shape source;
    private Shape cut;
    private Shape[] result;
    private ArrayList points = new ArrayList();
    private ArrayList marks = new ArrayList();
    private ArrayList exclude = new ArrayList();
    private boolean dynamic;
    private GeomUtil util = new GeomUtil();
    private int xp;
    private int yp;
    private Circle circle;
    private Shape rect;
    private Polygon star;
    private boolean union;

    public GeomUtilTest() {
        super("GeomUtilTest");
    }

    public void init() {
        Polygon polygon = new Polygon();
        polygon.addPoint(100.0f, 100.0f);
        polygon.addPoint(150.0f, 80.0f);
        polygon.addPoint(210.0f, 120.0f);
        polygon.addPoint(340.0f, 150.0f);
        polygon.addPoint(150.0f, 200.0f);
        polygon.addPoint(120.0f, 250.0f);
        this.source = polygon;
        this.circle = new Circle(0.0f, 0.0f, 50.0f);
        this.rect = new Rectangle(-100.0f, -40.0f, 200.0f, 80.0f);
        this.star = new Polygon();
        float f = 40.0f;
        for (int i = 0; i < 360; i += 30) {
            f = f == 40.0f ? 60.0f : 40.0f;
            double d = Math.cos(Math.toRadians(i)) * (double)f;
            double d2 = Math.sin(Math.toRadians(i)) * (double)f;
            this.star.addPoint((float)d, (float)d2);
        }
        this.cut = this.circle;
        this.cut.setLocation(203.0f, 78.0f);
        this.xp = (int)this.cut.getCenterX();
        this.yp = (int)this.cut.getCenterY();
        this.makeBoolean();
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.util.setListener(this);
        this.init();
        gameContainer.setVSync(true);
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
        if (gameContainer.getInput().isKeyPressed(57)) {
            boolean bl = this.dynamic = !this.dynamic;
        }
        if (gameContainer.getInput().isKeyPressed(28)) {
            this.union = !this.union;
            this.makeBoolean();
        }
        if (gameContainer.getInput().isKeyPressed(2)) {
            this.cut = this.circle;
            this.circle.setCenterX(this.xp);
            this.circle.setCenterY(this.yp);
            this.makeBoolean();
        }
        if (gameContainer.getInput().isKeyPressed(3)) {
            this.cut = this.rect;
            this.rect.setCenterX(this.xp);
            this.rect.setCenterY(this.yp);
            this.makeBoolean();
        }
        if (gameContainer.getInput().isKeyPressed(4)) {
            this.cut = this.star;
            this.star.setCenterX(this.xp);
            this.star.setCenterY(this.yp);
            this.makeBoolean();
        }
        if (this.dynamic) {
            this.xp = gameContainer.getInput().getMouseX();
            this.yp = gameContainer.getInput().getMouseY();
            this.makeBoolean();
        }
    }

    private void makeBoolean() {
        this.marks.clear();
        this.points.clear();
        this.exclude.clear();
        this.cut.setCenterX(this.xp);
        this.cut.setCenterY(this.yp);
        this.result = this.union ? this.util.union(this.source, this.cut) : this.util.subtract(this.source, this.cut);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        Vector2f vector2f;
        int n;
        graphics.drawString("Space - toggle movement of cutting shape", 530.0f, 10.0f);
        graphics.drawString("1,2,3 - select cutting shape", 530.0f, 30.0f);
        graphics.drawString("Mouse wheel - rotate shape", 530.0f, 50.0f);
        graphics.drawString("Enter - toggle union/subtract", 530.0f, 70.0f);
        graphics.drawString("MODE: " + (this.union ? "Union" : "Cut"), 530.0f, 200.0f);
        graphics.setColor(Color.green);
        graphics.draw(this.source);
        graphics.setColor(Color.red);
        graphics.draw(this.cut);
        graphics.setColor(Color.white);
        for (n = 0; n < this.exclude.size(); ++n) {
            vector2f = (Vector2f)this.exclude.get(n);
            graphics.drawOval(vector2f.x - 3.0f, vector2f.y - 3.0f, 7.0f, 7.0f);
        }
        graphics.setColor(Color.yellow);
        for (n = 0; n < this.points.size(); ++n) {
            vector2f = (Vector2f)this.points.get(n);
            graphics.fillOval(vector2f.x - 1.0f, vector2f.y - 1.0f, 3.0f, 3.0f);
        }
        graphics.setColor(Color.white);
        for (n = 0; n < this.marks.size(); ++n) {
            vector2f = (Vector2f)this.marks.get(n);
            graphics.fillOval(vector2f.x - 1.0f, vector2f.y - 1.0f, 3.0f, 3.0f);
        }
        graphics.translate(0.0f, 300.0f);
        graphics.setColor(Color.white);
        if (this.result != null) {
            for (n = 0; n < this.result.length; ++n) {
                graphics.draw(this.result[n]);
            }
            graphics.drawString("Polys:" + this.result.length, 10.0f, 100.0f);
            graphics.drawString("X:" + this.xp, 10.0f, 120.0f);
            graphics.drawString("Y:" + this.yp, 10.0f, 130.0f);
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new GeomUtilTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    @Override
    public void pointExcluded(float f, float f2) {
        this.exclude.add(new Vector2f(f, f2));
    }

    @Override
    public void pointIntersected(float f, float f2) {
        this.marks.add(new Vector2f(f, f2));
    }

    @Override
    public void pointUsed(float f, float f2) {
        this.points.add(new Vector2f(f, f2));
    }

    @Override
    public void mouseWheelMoved(int n) {
        if (this.dynamic) {
            this.cut = n < 0 ? this.cut.transform(Transform.createRotateTransform((float)Math.toRadians(10.0), this.cut.getCenterX(), this.cut.getCenterY())) : this.cut.transform(Transform.createRotateTransform((float)Math.toRadians(-10.0), this.cut.getCenterX(), this.cut.getCenterY()));
        }
    }
}

