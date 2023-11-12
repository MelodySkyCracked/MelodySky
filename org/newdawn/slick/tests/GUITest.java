/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.tests.ll;
import org.newdawn.slick.tests.lll;
import org.newdawn.slick.util.Log;

public class GUITest
extends BasicGame
implements ComponentListener {
    private Image image;
    private MouseOverArea[] areas = new MouseOverArea[4];
    private GameContainer container;
    private String message = "Demo Menu System with stock images";
    private TextField field;
    private TextField field2;
    private Image background;
    private Font font;
    private AppGameContainer app;

    public GUITest() {
        super("GUI Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        if (gameContainer instanceof AppGameContainer) {
            this.app = (AppGameContainer)gameContainer;
            this.app.setIcon("testdata/icon.tga");
        }
        this.font = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
        this.field = new TextField(gameContainer, this.font, 150, 20, 500, 35, new lll(this));
        this.field2 = new TextField(gameContainer, this.font, 150, 70, 500, 35, new ll(this));
        this.field2.setBorderColor(Color.red);
        this.container = gameContainer;
        this.image = new Image("testdata/logo.tga");
        this.background = new Image("testdata/dungeontiles.gif");
        gameContainer.setMouseCursor("testdata/cursor.tga", 0, 0);
        for (int i = 0; i < 4; ++i) {
            this.areas[i] = new MouseOverArea(gameContainer, this.image, 300, 100 + i * 100, 200, 90, this);
            this.areas[i].setNormalColor(new Color(1.0f, 1.0f, 1.0f, 0.8f));
            this.areas[i].setMouseOverColor(new Color(1.0f, 1.0f, 1.0f, 0.9f));
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        this.background.draw(0.0f, 0.0f, 800.0f, 500.0f);
        for (int i = 0; i < 4; ++i) {
            this.areas[i].render(gameContainer, graphics);
        }
        this.field.render(gameContainer, graphics);
        this.field2.render(gameContainer, graphics);
        graphics.setFont(this.font);
        graphics.drawString(this.message, 200.0f, 550.0f);
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 1) {
            System.exit(0);
        }
        if (n == 60) {
            this.app.setDefaultMouseCursor();
        }
        if (n == 59 && this.app != null) {
            try {
                this.app.setDisplayMode(640, 480, false);
            }
            catch (SlickException slickException) {
                Log.error(slickException);
            }
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new GUITest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        System.out.println("ACTIVL : " + abstractComponent);
        for (int i = 0; i < 4; ++i) {
            if (abstractComponent != this.areas[i]) continue;
            this.message = "Option " + (i + 1) + " pressed!";
        }
        if (abstractComponent == this.field2) {
            // empty if block
        }
    }

    static String access$002(GUITest gUITest, String string) {
        gUITest.message = string;
        return gUITest.message;
    }

    static TextField access$100(GUITest gUITest) {
        return gUITest.field;
    }

    static TextField access$200(GUITest gUITest) {
        return gUITest.field2;
    }
}

