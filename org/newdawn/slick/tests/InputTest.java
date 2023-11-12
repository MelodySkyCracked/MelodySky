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
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

public class InputTest
extends BasicGame {
    private String message = "Press any key, mouse button, or drag the mouse";
    private ArrayList lines = new ArrayList();
    private boolean buttonDown;
    private float x;
    private float y;
    private Color[] cols = new Color[]{Color.red, Color.green, Color.blue, Color.white, Color.magenta, Color.cyan};
    private int index;
    private Input input;
    private int ypos;
    private AppGameContainer app;
    private boolean space;
    private boolean lshift;
    private boolean rshift;

    public InputTest() {
        super("Input Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        if (gameContainer instanceof AppGameContainer) {
            this.app = (AppGameContainer)gameContainer;
        }
        this.input = gameContainer.getInput();
        this.x = 300.0f;
        this.y = 300.0f;
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.drawString("left shift down: " + this.lshift, 100.0f, 240.0f);
        graphics.drawString("right shift down: " + this.rshift, 100.0f, 260.0f);
        graphics.drawString("space down: " + this.space, 100.0f, 280.0f);
        graphics.setColor(Color.white);
        graphics.drawString(this.message, 10.0f, 50.0f);
        graphics.drawString("" + gameContainer.getInput().getMouseY(), 10.0f, 400.0f);
        graphics.drawString("Use the primary gamepad to control the blob, and hit a gamepad button to change the color", 10.0f, 90.0f);
        for (int i = 0; i < this.lines.size(); ++i) {
            Line line = (Line)this.lines.get(i);
            line.draw(graphics);
        }
        graphics.setColor(this.cols[this.index]);
        graphics.fillOval((int)this.x, (int)this.y, 50.0f, 50.0f);
        graphics.setColor(Color.yellow);
        graphics.fillRect(50.0f, 200 + this.ypos, 40.0f, 40.0f);
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
        this.lshift = gameContainer.getInput().isKeyDown(42);
        this.rshift = gameContainer.getInput().isKeyDown(54);
        this.space = gameContainer.getInput().isKeyDown(57);
        if (this.controllerLeft[0]) {
            this.x -= (float)n * 0.1f;
        }
        if (this.controllerRight[0]) {
            this.x += (float)n * 0.1f;
        }
        if (this.controllerUp[0]) {
            this.y -= (float)n * 0.1f;
        }
        if (this.controllerDown[0]) {
            this.y += (float)n * 0.1f;
        }
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 1) {
            System.exit(0);
        }
        if (n == 59 && this.app != null) {
            try {
                this.app.setDisplayMode(600, 600, false);
                this.app.reinit();
            }
            catch (Exception exception) {
                Log.error(exception);
            }
        }
    }

    @Override
    public void keyReleased(int n, char c) {
        this.message = "You pressed key code " + n + " (character = " + c + ")";
    }

    @Override
    public void mousePressed(int n, int n2, int n3) {
        if (n == 0) {
            this.buttonDown = true;
        }
        this.message = "Mouse pressed " + n + " " + n2 + "," + n3;
    }

    @Override
    public void mouseReleased(int n, int n2, int n3) {
        if (n == 0) {
            this.buttonDown = false;
        }
        this.message = "Mouse released " + n + " " + n2 + "," + n3;
    }

    @Override
    public void mouseClicked(int n, int n2, int n3, int n4) {
        System.out.println("CLICKED:" + n2 + "," + n3 + " " + n4);
    }

    @Override
    public void mouseWheelMoved(int n) {
        this.message = "Mouse wheel moved: " + n;
        if (n < 0) {
            this.ypos -= 10;
        }
        if (n > 0) {
            this.ypos += 10;
        }
    }

    @Override
    public void mouseMoved(int n, int n2, int n3, int n4) {
        if (this.buttonDown) {
            this.lines.add(new Line(this, n, n2, n3, n4));
        }
    }

    @Override
    public void controllerButtonPressed(int n, int n2) {
        super.controllerButtonPressed(n, n2);
        ++this.index;
        this.index %= this.cols.length;
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new InputTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    private class Line {
        private int oldx;
        private int oldy;
        private int newx;
        private int newy;
        final InputTest this$0;

        public Line(InputTest inputTest, int n, int n2, int n3, int n4) {
            this.this$0 = inputTest;
            this.oldx = n;
            this.oldy = n2;
            this.newx = n3;
            this.newy = n4;
        }

        public void draw(Graphics graphics) {
            graphics.drawLine(this.oldx, this.oldy, this.newx, this.newy);
        }
    }
}

