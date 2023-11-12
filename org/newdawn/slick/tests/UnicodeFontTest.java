/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import java.io.IOException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class UnicodeFontTest
extends BasicGame {
    private UnicodeFont unicodeFont;

    public UnicodeFontTest() {
        super("Font Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        gameContainer.setShowFPS(false);
        this.unicodeFont = new UnicodeFont("c:/windows/fonts/arial.ttf", 48, false, false);
        this.unicodeFont.getEffects().add(new ColorEffect(java.awt.Color.white));
        gameContainer.getGraphics().setBackground(Color.darkGray);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.setColor(Color.white);
        String string = "This is UnicodeFont!\nIt rockz. Kerning: T,";
        this.unicodeFont.drawString(10.0f, 33.0f, string);
        graphics.setColor(Color.red);
        graphics.drawRect(10.0f, 33.0f, this.unicodeFont.getWidth(string), this.unicodeFont.getLineHeight());
        graphics.setColor(Color.blue);
        int n = this.unicodeFont.getYOffset(string);
        graphics.drawRect(10.0f, 33 + n, this.unicodeFont.getWidth(string), this.unicodeFont.getHeight(string) - n);
        this.unicodeFont.addGlyphs("~!@!#!#$%___--");
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
        this.unicodeFont.loadGlyphs(1);
    }

    public static void main(String[] stringArray) throws SlickException, IOException {
        Input.disableControllers();
        AppGameContainer appGameContainer = new AppGameContainer(new UnicodeFontTest());
        appGameContainer.setDisplayMode(512, 600, false);
        appGameContainer.setTargetFrameRate(20);
        appGameContainer.start();
    }
}

