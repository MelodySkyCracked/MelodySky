/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.tests.AnimationTest;
import org.newdawn.slick.tests.AntiAliasTest;
import org.newdawn.slick.tests.BigImageTest;
import org.newdawn.slick.tests.ClipTest;
import org.newdawn.slick.tests.DuplicateEmitterTest;
import org.newdawn.slick.tests.FlashTest;
import org.newdawn.slick.tests.FontPerformanceTest;
import org.newdawn.slick.tests.FontTest;
import org.newdawn.slick.tests.GeomTest;
import org.newdawn.slick.tests.GradientTest;
import org.newdawn.slick.tests.GraphicsTest;
import org.newdawn.slick.tests.I;
import org.newdawn.slick.tests.ImageBufferTest;
import org.newdawn.slick.tests.ImageReadTest;
import org.newdawn.slick.tests.ImageTest;
import org.newdawn.slick.tests.KeyRepeatTest;
import org.newdawn.slick.tests.MusicListenerTest;
import org.newdawn.slick.tests.PackedSheetTest;
import org.newdawn.slick.tests.PedigreeTest;
import org.newdawn.slick.tests.PureFontTest;
import org.newdawn.slick.tests.ShapeTest;
import org.newdawn.slick.tests.SoundTest;
import org.newdawn.slick.tests.SpriteSheetFontTest;
import org.newdawn.slick.tests.TransparentColorTest;
import org.newdawn.slick.util.Log;

public class TestBox
extends BasicGame {
    private ArrayList games = new ArrayList();
    private BasicGame currentGame;
    private int index;
    private AppGameContainer container;

    public TestBox() {
        super("Test Box");
    }

    public void addGame(Class clazz) {
        this.games.add(clazz);
    }

    private void nextGame() {
        if (this.index == -1) {
            return;
        }
        ++this.index;
        if (this.index >= this.games.size()) {
            this.index = 0;
        }
        this.startGame();
    }

    private void startGame() {
        try {
            this.currentGame = (BasicGame)((Class)this.games.get(this.index)).newInstance();
            this.container.getGraphics().setBackground(Color.black);
            this.currentGame.init(this.container);
            this.currentGame.render(this.container, this.container.getGraphics());
        }
        catch (Exception exception) {
            Log.error(exception);
        }
        this.container.setTitle(this.currentGame.getTitle());
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        if (this.games.size() == 0) {
            this.currentGame = new I(this, "NULL");
            this.currentGame.init(gameContainer);
            this.index = -1;
        } else {
            this.index = 0;
            this.container = (AppGameContainer)gameContainer;
            this.startGame();
        }
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
        this.currentGame.update(gameContainer, n);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        SlickCallable.enterSafeBlock();
        this.currentGame.render(gameContainer, graphics);
        SlickCallable.leaveSafeBlock();
    }

    @Override
    public void controllerButtonPressed(int n, int n2) {
        this.currentGame.controllerButtonPressed(n, n2);
    }

    @Override
    public void controllerButtonReleased(int n, int n2) {
        this.currentGame.controllerButtonReleased(n, n2);
    }

    @Override
    public void controllerDownPressed(int n) {
        this.currentGame.controllerDownPressed(n);
    }

    @Override
    public void controllerDownReleased(int n) {
        this.currentGame.controllerDownReleased(n);
    }

    @Override
    public void controllerLeftPressed(int n) {
        this.currentGame.controllerLeftPressed(n);
    }

    @Override
    public void controllerLeftReleased(int n) {
        this.currentGame.controllerLeftReleased(n);
    }

    @Override
    public void controllerRightPressed(int n) {
        this.currentGame.controllerRightPressed(n);
    }

    @Override
    public void controllerRightReleased(int n) {
        this.currentGame.controllerRightReleased(n);
    }

    @Override
    public void controllerUpPressed(int n) {
        this.currentGame.controllerUpPressed(n);
    }

    @Override
    public void controllerUpReleased(int n) {
        this.currentGame.controllerUpReleased(n);
    }

    @Override
    public void keyPressed(int n, char c) {
        this.currentGame.keyPressed(n, c);
        if (n == 28) {
            this.nextGame();
        }
    }

    @Override
    public void keyReleased(int n, char c) {
        this.currentGame.keyReleased(n, c);
    }

    @Override
    public void mouseMoved(int n, int n2, int n3, int n4) {
        this.currentGame.mouseMoved(n, n2, n3, n4);
    }

    @Override
    public void mousePressed(int n, int n2, int n3) {
        this.currentGame.mousePressed(n, n2, n3);
    }

    @Override
    public void mouseReleased(int n, int n2, int n3) {
        this.currentGame.mouseReleased(n, n2, n3);
    }

    @Override
    public void mouseWheelMoved(int n) {
        this.currentGame.mouseWheelMoved(n);
    }

    public static void main(String[] stringArray) {
        try {
            TestBox testBox = new TestBox();
            testBox.addGame(AnimationTest.class);
            testBox.addGame(AntiAliasTest.class);
            testBox.addGame(BigImageTest.class);
            testBox.addGame(ClipTest.class);
            testBox.addGame(DuplicateEmitterTest.class);
            testBox.addGame(FlashTest.class);
            testBox.addGame(FontPerformanceTest.class);
            testBox.addGame(FontTest.class);
            testBox.addGame(GeomTest.class);
            testBox.addGame(GradientTest.class);
            testBox.addGame(GraphicsTest.class);
            testBox.addGame(ImageBufferTest.class);
            testBox.addGame(ImageReadTest.class);
            testBox.addGame(ImageTest.class);
            testBox.addGame(KeyRepeatTest.class);
            testBox.addGame(MusicListenerTest.class);
            testBox.addGame(PackedSheetTest.class);
            testBox.addGame(PedigreeTest.class);
            testBox.addGame(PureFontTest.class);
            testBox.addGame(ShapeTest.class);
            testBox.addGame(SoundTest.class);
            testBox.addGame(SpriteSheetFontTest.class);
            testBox.addGame(TransparentColorTest.class);
            AppGameContainer appGameContainer = new AppGameContainer(testBox);
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

