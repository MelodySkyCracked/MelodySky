/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import java.io.IOException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageOut;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class ImageOutTest
extends BasicGame {
    private GameContainer container;
    private ParticleSystem fire;
    private Graphics g;
    private Image copy;
    private String message;

    public ImageOutTest() {
        super("Image Out Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.container = gameContainer;
        try {
            this.fire = ParticleIO.loadConfiguredSystem("testdata/system.xml");
        }
        catch (IOException iOException) {
            throw new SlickException("Failed to load particle systems", iOException);
        }
        this.copy = new Image(400, 300);
        String[] stringArray = ImageOut.getSupportedFormats();
        this.message = "Formats supported: ";
        for (int i = 0; i < stringArray.length; ++i) {
            this.message = this.message + stringArray[i];
            if (i >= stringArray.length - 1) continue;
            this.message = this.message + ",";
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.drawString("T - TGA Snapshot", 10.0f, 50.0f);
        graphics.drawString("J - JPG Snapshot", 10.0f, 70.0f);
        graphics.drawString("P - PNG Snapshot", 10.0f, 90.0f);
        graphics.setDrawMode(Graphics.MODE_ADD);
        graphics.drawImage(this.copy, 200.0f, 300.0f);
        graphics.setDrawMode(Graphics.MODE_NORMAL);
        graphics.drawString(this.message, 10.0f, 400.0f);
        graphics.drawRect(200.0f, 0.0f, 400.0f, 300.0f);
        graphics.translate(400.0f, 250.0f);
        this.fire.render();
        this.g = graphics;
    }

    private void writeTo(String string) throws SlickException {
        this.g.copyArea(this.copy, 200, 0);
        ImageOut.write(this.copy, string);
        this.message = "Written " + string;
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
        this.fire.update(n);
        if (gameContainer.getInput().isKeyPressed(25)) {
            this.writeTo("ImageOutTest.png");
        }
        if (gameContainer.getInput().isKeyPressed(36)) {
            this.writeTo("ImageOutTest.jpg");
        }
        if (gameContainer.getInput().isKeyPressed(20)) {
            this.writeTo("ImageOutTest.tga");
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new ImageOutTest());
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
    }
}

