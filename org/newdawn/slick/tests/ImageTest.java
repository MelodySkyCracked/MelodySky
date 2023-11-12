/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageTest
extends BasicGame {
    private Image tga;
    private Image scaleMe;
    private Image scaled;
    private Image gif;
    private Image image;
    private Image subImage;
    private Image rotImage;
    private float rot;
    public static boolean exitMe = true;

    public ImageTest() {
        super("Image Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.image = this.tga = new Image("testdata/logo.png");
        this.rotImage = new Image("testdata/logo.png");
        this.rotImage = this.rotImage.getScaledCopy(this.rotImage.getWidth() / 2, this.rotImage.getHeight() / 2);
        this.scaleMe = new Image("testdata/logo.tga", true, 2);
        this.gif = new Image("testdata/logo.gif");
        this.gif.destroy();
        this.gif = new Image("testdata/logo.gif");
        this.scaled = this.gif.getScaledCopy(120, 120);
        this.subImage = this.image.getSubImage(200, 0, 70, 260);
        this.rot = 0.0f;
        if (exitMe) {
            gameContainer.exit();
        }
        Image image = this.tga.getSubImage(50, 50, 50, 50);
        System.out.println(image.getColor(50, 50));
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.drawRect(0.0f, 0.0f, this.image.getWidth(), this.image.getHeight());
        this.image.draw(0.0f, 0.0f);
        this.image.draw(500.0f, 0.0f, 200.0f, 100.0f);
        this.scaleMe.draw(500.0f, 100.0f, 200.0f, 100.0f);
        this.scaled.draw(400.0f, 500.0f);
        Image image = this.scaled.getFlippedCopy(true, false);
        image.draw(520.0f, 500.0f);
        Image image2 = image.getFlippedCopy(false, true);
        image2.draw(520.0f, 380.0f);
        Image image3 = image2.getFlippedCopy(true, false);
        image3.draw(400.0f, 380.0f);
        for (int i = 0; i < 3; ++i) {
            this.subImage.draw(200 + i * 30, 300.0f);
        }
        graphics.translate(500.0f, 200.0f);
        graphics.rotate(50.0f, 50.0f, this.rot);
        graphics.scale(0.3f, 0.3f);
        this.image.draw();
        graphics.resetTransform();
        this.rotImage.setRotation(this.rot);
        this.rotImage.draw(100.0f, 200.0f);
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
        this.rot += (float)n * 0.1f;
        if (this.rot > 360.0f) {
            this.rot -= 360.0f;
        }
    }

    public static void main(String[] stringArray) {
        boolean bl = false;
        try {
            exitMe = false;
            if (bl) {
                GameContainer.enableSharedContext();
                exitMe = true;
            }
            AppGameContainer appGameContainer = new AppGameContainer(new ImageTest());
            appGameContainer.setForceExit(!bl);
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
            if (bl) {
                System.out.println("Exit first instance");
                exitMe = false;
                appGameContainer = new AppGameContainer(new ImageTest());
                appGameContainer.setDisplayMode(800, 600, false);
                appGameContainer.start();
            }
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 57) {
            this.image = this.image == this.gif ? this.tga : this.gif;
        }
    }
}

