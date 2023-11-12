/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import java.awt.Frame;
import java.awt.GridLayout;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tests.llI;
import org.newdawn.slick.util.Log;

public class CanvasSizeTest
extends BasicGame {
    public CanvasSizeTest() {
        super("Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        System.out.println(gameContainer.getWidth() + ", " + gameContainer.getHeight());
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }

    public static void main(String[] stringArray) {
        try {
            CanvasGameContainer canvasGameContainer = new CanvasGameContainer(new CanvasSizeTest());
            canvasGameContainer.setSize(640, 480);
            Frame frame = new Frame("Test");
            frame.setLayout(new GridLayout(1, 2));
            frame.add(canvasGameContainer);
            frame.pack();
            frame.addWindowListener(new llI());
            frame.setVisible(true);
            canvasGameContainer.start();
        }
        catch (Exception exception) {
            Log.error(exception);
        }
    }
}

