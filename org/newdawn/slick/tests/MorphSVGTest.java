/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.svg.Diagram;
import org.newdawn.slick.svg.InkscapeLoader;
import org.newdawn.slick.svg.SVGMorph;
import org.newdawn.slick.svg.SimpleDiagramRenderer;

public class MorphSVGTest
extends BasicGame {
    private SVGMorph morph;
    private Diagram base;
    private float time;
    private float x = -300.0f;

    public MorphSVGTest() {
        super("MorphShapeTest");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.base = InkscapeLoader.load("testdata/svg/walk1.svg");
        this.morph = new SVGMorph(this.base);
        this.morph.addStep(InkscapeLoader.load("testdata/svg/walk2.svg"));
        this.morph.addStep(InkscapeLoader.load("testdata/svg/walk3.svg"));
        this.morph.addStep(InkscapeLoader.load("testdata/svg/walk4.svg"));
        gameContainer.setVSync(true);
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
        this.morph.updateMorphTime((float)n * 0.003f);
        this.x += (float)n * 0.2f;
        if (this.x > 550.0f) {
            this.x = -450.0f;
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.translate(this.x, 0.0f);
        SimpleDiagramRenderer.render(graphics, this.morph);
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new MorphSVGTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

