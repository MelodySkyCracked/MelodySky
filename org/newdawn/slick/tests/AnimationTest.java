/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class AnimationTest
extends BasicGame {
    private Animation animation;
    private Animation limited;
    private Animation manual;
    private Animation pingPong;
    private GameContainer container;
    private int start = 5000;

    public AnimationTest() {
        super("Animation Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        int n;
        this.container = gameContainer;
        SpriteSheet spriteSheet = new SpriteSheet("testdata/homeranim.png", 36, 65);
        this.animation = new Animation();
        for (n = 0; n < 8; ++n) {
            this.animation.addFrame(spriteSheet.getSprite(n, 0), 150);
        }
        this.limited = new Animation();
        for (n = 0; n < 8; ++n) {
            this.limited.addFrame(spriteSheet.getSprite(n, 0), 150);
        }
        this.limited.stopAt(7);
        this.manual = new Animation(false);
        for (n = 0; n < 8; ++n) {
            this.manual.addFrame(spriteSheet.getSprite(n, 0), 150);
        }
        this.pingPong = new Animation(spriteSheet, 0, 0, 7, 0, true, 150, true);
        this.pingPong.setPingPong(true);
        gameContainer.getGraphics().setBackground(new Color(0.4f, 0.6f, 0.6f));
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.drawString("Space to restart() animation", 100.0f, 50.0f);
        graphics.drawString("Til Limited animation: " + this.start, 100.0f, 500.0f);
        graphics.drawString("Hold 1 to move the manually animated", 100.0f, 70.0f);
        graphics.drawString("PingPong Frame:" + this.pingPong.getFrame(), 600.0f, 70.0f);
        graphics.scale(-1.0f, 1.0f);
        this.animation.draw(-100.0f, 100.0f);
        this.animation.draw(-200.0f, 100.0f, 144.0f, 260.0f);
        if (this.start < 0) {
            this.limited.draw(-400.0f, 100.0f, 144.0f, 260.0f);
        }
        this.manual.draw(-600.0f, 100.0f, 144.0f, 260.0f);
        this.pingPong.draw(-700.0f, 100.0f, 72.0f, 130.0f);
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
        if (gameContainer.getInput().isKeyDown(2)) {
            this.manual.update(n);
        }
        if (this.start >= 0) {
            this.start -= n;
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new AnimationTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 1) {
            this.container.exit();
        }
        if (n == 57) {
            this.limited.restart();
        }
    }
}

