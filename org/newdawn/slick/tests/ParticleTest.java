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
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.effects.FireEmitter;

public class ParticleTest
extends BasicGame {
    private ParticleSystem system;
    private int mode = 2;

    public ParticleTest() {
        super("Particle Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        Image image = new Image("testdata/particle.tga", true);
        this.system = new ParticleSystem(image);
        this.system.addEmitter(new FireEmitter(400, 300, 45.0f));
        this.system.addEmitter(new FireEmitter(200, 300, 60.0f));
        this.system.addEmitter(new FireEmitter(600, 300, 30.0f));
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        for (int i = 0; i < 100; ++i) {
            graphics.translate(1.0f, 1.0f);
            this.system.render();
        }
        graphics.resetTransform();
        graphics.drawString("Press space to toggle blending mode", 200.0f, 500.0f);
        graphics.drawString("Particle Count: " + this.system.getParticleCount() * 100, 200.0f, 520.0f);
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
        this.system.update(n);
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 1) {
            System.exit(0);
        }
        if (n == 57) {
            this.mode = 1 == this.mode ? 2 : 1;
            this.system.setBlendingMode(this.mode);
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new ParticleTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

