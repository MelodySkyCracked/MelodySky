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
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class PedigreeTest
extends BasicGame {
    private Image image;
    private GameContainer container;
    private ParticleSystem trail;
    private ParticleSystem fire;
    private float rx;
    private float ry = 900.0f;

    public PedigreeTest() {
        super("Pedigree Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.container = gameContainer;
        try {
            this.fire = ParticleIO.loadConfiguredSystem("testdata/system.xml");
            this.trail = ParticleIO.loadConfiguredSystem("testdata/smoketrail.xml");
        }
        catch (IOException iOException) {
            throw new SlickException("Failed to load particle systems", iOException);
        }
        this.image = new Image("testdata/rocket.png");
        this.spawnRocket();
    }

    private void spawnRocket() {
        this.ry = 700.0f;
        this.rx = (float)(Math.random() * 600.0 + 100.0);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        ((ConfigurableEmitter)this.trail.getEmitter(0)).setPosition(this.rx + 14.0f, this.ry + 35.0f);
        this.trail.render();
        this.image.draw((int)this.rx, (int)this.ry);
        graphics.translate(400.0f, 300.0f);
        this.fire.render();
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
        this.fire.update(n);
        this.trail.update(n);
        this.ry -= (float)n * 0.25f;
        if (this.ry < -100.0f) {
            this.spawnRocket();
        }
    }

    @Override
    public void mousePressed(int n, int n2, int n3) {
        super.mousePressed(n, n2, n3);
        for (int i = 0; i < this.fire.getEmitterCount(); ++i) {
            ((ConfigurableEmitter)this.fire.getEmitter(i)).setPosition(n2 - 400, n3 - 300, true);
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new PedigreeTest());
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

