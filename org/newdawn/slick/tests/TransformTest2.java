/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class TransformTest2
extends BasicGame {
    private float scale = 1.0f;
    private boolean scaleUp;
    private boolean scaleDown;
    private float camX = 320.0f;
    private float camY = 240.0f;
    private boolean moveLeft;
    private boolean moveUp;
    private boolean moveRight;
    private boolean moveDown;

    public TransformTest2() {
        super("Transform Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        gameContainer.setTargetFrameRate(100);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.translate(320.0f, 240.0f);
        graphics.translate(-this.camX * this.scale, -this.camY * this.scale);
        graphics.scale(this.scale, this.scale);
        graphics.setColor(Color.red);
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                graphics.fillRect(-500 + i * 100, -500 + j * 100, 80.0f, 80.0f);
            }
        }
        graphics.setColor(new Color(1.0f, 1.0f, 1.0f, 0.5f));
        graphics.fillRect(-320.0f, -240.0f, 640.0f, 480.0f);
        graphics.setColor(Color.white);
        graphics.drawRect(-320.0f, -240.0f, 640.0f, 480.0f);
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
        if (this.scaleUp) {
            this.scale += (float)n * 0.001f;
        }
        if (this.scaleDown) {
            this.scale -= (float)n * 0.001f;
        }
        float f = (float)n * 0.4f * (1.0f / this.scale);
        if (this.moveLeft) {
            this.camX -= f;
        }
        if (this.moveUp) {
            this.camY -= f;
        }
        if (this.moveRight) {
            this.camX += f;
        }
        if (this.moveDown) {
            this.camY += f;
        }
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 1) {
            System.exit(0);
        }
        if (n == 16) {
            this.scaleUp = true;
        }
        if (n == 30) {
            this.scaleDown = true;
        }
        if (n == 203) {
            this.moveLeft = true;
        }
        if (n == 200) {
            this.moveUp = true;
        }
        if (n == 205) {
            this.moveRight = true;
        }
        if (n == 208) {
            this.moveDown = true;
        }
    }

    @Override
    public void keyReleased(int n, char c) {
        if (n == 16) {
            this.scaleUp = false;
        }
        if (n == 30) {
            this.scaleDown = false;
        }
        if (n == 203) {
            this.moveLeft = false;
        }
        if (n == 200) {
            this.moveUp = false;
        }
        if (n == 205) {
            this.moveRight = false;
        }
        if (n == 208) {
            this.moveDown = false;
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new TransformTest2());
            appGameContainer.setDisplayMode(640, 480, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

