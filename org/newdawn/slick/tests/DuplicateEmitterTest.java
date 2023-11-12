/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import java.io.IOException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

public class DuplicateEmitterTest
extends BasicGame {
    private GameContainer container;
    private ParticleSystem explosionSystem;
    private ConfigurableEmitter explosionEmitter;

    public DuplicateEmitterTest() {
        super("DuplicateEmitterTest");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.container = gameContainer;
        try {
            this.explosionSystem = ParticleIO.loadConfiguredSystem("testdata/endlessexplosion.xml");
            this.explosionEmitter = (ConfigurableEmitter)this.explosionSystem.getEmitter(0);
            this.explosionEmitter.setPosition(400.0f, 100.0f);
            for (int i = 0; i < 5; ++i) {
                ConfigurableEmitter configurableEmitter = this.explosionEmitter.duplicate();
                if (configurableEmitter == null) {
                    throw new SlickException("Failed to duplicate explosionEmitter");
                }
                configurableEmitter.name = configurableEmitter.name + "_" + i;
                configurableEmitter.setPosition((i + 1) * 133, 400.0f);
                this.explosionSystem.addEmitter(configurableEmitter);
            }
        }
        catch (IOException iOException) {
            throw new SlickException("Failed to load particle systems", iOException);
        }
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
        this.explosionSystem.update(n);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        this.explosionSystem.render();
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 1) {
            this.container.exit();
        }
        if (n == 37) {
            this.explosionEmitter.wrapUp();
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new DuplicateEmitterTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

