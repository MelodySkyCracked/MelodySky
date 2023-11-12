/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;

public class GeomAccuracyTest
extends BasicGame {
    private GameContainer container;
    private Color geomColor;
    private Color overlayColor;
    private boolean hideOverlay;
    private int colorIndex;
    private int curTest;
    private static final int NUMTESTS = 3;
    private Image magImage;

    public GeomAccuracyTest() {
        super("Geometry Accuracy Tests");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.container = gameContainer;
        this.geomColor = Color.magenta;
        this.overlayColor = Color.white;
        this.magImage = new Image(21, 21);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        String string = new String();
        switch (this.curTest) {
            case 0: {
                string = "Rectangles";
                this.rectTest(graphics);
                break;
            }
            case 1: {
                string = "Ovals";
                this.ovalTest(graphics);
                break;
            }
            case 2: {
                string = "Arcs";
                this.arcTest(graphics);
            }
        }
        graphics.setColor(Color.white);
        graphics.drawString("Press T to toggle overlay", 200.0f, 55.0f);
        graphics.drawString("Press N to switch tests", 200.0f, 35.0f);
        graphics.drawString("Press C to cycle drawing colors", 200.0f, 15.0f);
        graphics.drawString("Current Test:", 400.0f, 35.0f);
        graphics.setColor(Color.blue);
        graphics.drawString(string, 485.0f, 35.0f);
        graphics.setColor(Color.white);
        graphics.drawString("Normal:", 10.0f, 150.0f);
        graphics.drawString("Filled:", 10.0f, 300.0f);
        graphics.drawString("Drawn with Graphics context", 125.0f, 400.0f);
        graphics.drawString("Drawn using Shapes", 450.0f, 400.0f);
        graphics.copyArea(this.magImage, gameContainer.getInput().getMouseX() - 10, gameContainer.getInput().getMouseY() - 10);
        this.magImage.draw(351.0f, 451.0f, 5.0f);
        graphics.drawString("Mag Area -", 250.0f, 475.0f);
        graphics.setColor(Color.darkGray);
        graphics.drawRect(350.0f, 450.0f, 106.0f, 106.0f);
        graphics.setColor(Color.white);
        graphics.drawString("NOTE:", 500.0f, 450.0f);
        graphics.drawString("lines should be flush with edges", 525.0f, 470.0f);
        graphics.drawString("corners should be symetric", 525.0f, 490.0f);
    }

    void arcTest(Graphics graphics) {
        if (!this.hideOverlay) {
            graphics.setColor(this.overlayColor);
            graphics.drawLine(198.0f, 100.0f, 198.0f, 198.0f);
            graphics.drawLine(100.0f, 198.0f, 198.0f, 198.0f);
        }
        graphics.setColor(this.geomColor);
        graphics.drawArc(100.0f, 100.0f, 99.0f, 99.0f, 0.0f, 90.0f);
    }

    void ovalTest(Graphics graphics) {
        graphics.setColor(this.geomColor);
        graphics.drawOval(100.0f, 100.0f, 99.0f, 99.0f);
        graphics.fillOval(100.0f, 250.0f, 99.0f, 99.0f);
        Ellipse ellipse = new Ellipse(449.0f, 149.0f, 49.0f, 49.0f);
        graphics.draw(ellipse);
        ellipse = new Ellipse(449.0f, 299.0f, 49.0f, 49.0f);
        graphics.fill(ellipse);
        if (!this.hideOverlay) {
            graphics.setColor(this.overlayColor);
            graphics.drawLine(100.0f, 149.0f, 198.0f, 149.0f);
            graphics.drawLine(149.0f, 100.0f, 149.0f, 198.0f);
            graphics.drawLine(100.0f, 299.0f, 198.0f, 299.0f);
            graphics.drawLine(149.0f, 250.0f, 149.0f, 348.0f);
            graphics.drawLine(400.0f, 149.0f, 498.0f, 149.0f);
            graphics.drawLine(449.0f, 100.0f, 449.0f, 198.0f);
            graphics.drawLine(400.0f, 299.0f, 498.0f, 299.0f);
            graphics.drawLine(449.0f, 250.0f, 449.0f, 348.0f);
        }
    }

    void rectTest(Graphics graphics) {
        graphics.setColor(this.geomColor);
        graphics.drawRect(100.0f, 100.0f, 99.0f, 99.0f);
        graphics.fillRect(100.0f, 250.0f, 99.0f, 99.0f);
        graphics.drawRoundRect(250.0f, 100.0f, 99.0f, 99.0f, 10);
        graphics.fillRoundRect(250.0f, 250.0f, 99.0f, 99.0f, 10);
        Rectangle rectangle = new Rectangle(400.0f, 100.0f, 99.0f, 99.0f);
        graphics.draw(rectangle);
        rectangle = new Rectangle(400.0f, 250.0f, 99.0f, 99.0f);
        graphics.fill(rectangle);
        RoundedRectangle roundedRectangle = new RoundedRectangle(550.0f, 100.0f, 99.0f, 99.0f, 10.0f);
        graphics.draw(roundedRectangle);
        roundedRectangle = new RoundedRectangle(550.0f, 250.0f, 99.0f, 99.0f, 10.0f);
        graphics.fill(roundedRectangle);
        if (!this.hideOverlay) {
            graphics.setColor(this.overlayColor);
            graphics.drawLine(100.0f, 149.0f, 198.0f, 149.0f);
            graphics.drawLine(149.0f, 100.0f, 149.0f, 198.0f);
            graphics.drawLine(250.0f, 149.0f, 348.0f, 149.0f);
            graphics.drawLine(299.0f, 100.0f, 299.0f, 198.0f);
            graphics.drawLine(400.0f, 149.0f, 498.0f, 149.0f);
            graphics.drawLine(449.0f, 100.0f, 449.0f, 198.0f);
            graphics.drawLine(550.0f, 149.0f, 648.0f, 149.0f);
            graphics.drawLine(599.0f, 100.0f, 599.0f, 198.0f);
            graphics.drawLine(100.0f, 299.0f, 198.0f, 299.0f);
            graphics.drawLine(149.0f, 250.0f, 149.0f, 348.0f);
            graphics.drawLine(250.0f, 299.0f, 348.0f, 299.0f);
            graphics.drawLine(299.0f, 250.0f, 299.0f, 348.0f);
            graphics.drawLine(400.0f, 299.0f, 498.0f, 299.0f);
            graphics.drawLine(449.0f, 250.0f, 449.0f, 348.0f);
            graphics.drawLine(550.0f, 299.0f, 648.0f, 299.0f);
            graphics.drawLine(599.0f, 250.0f, 599.0f, 348.0f);
        }
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 1) {
            System.exit(0);
        }
        if (n == 49) {
            ++this.curTest;
            this.curTest %= 3;
        }
        if (n == 46) {
            ++this.colorIndex;
            this.colorIndex %= 4;
            this.setColors();
        }
        if (n == 20) {
            this.hideOverlay = !this.hideOverlay;
        }
    }

    private void setColors() {
        switch (this.colorIndex) {
            case 0: {
                this.overlayColor = Color.white;
                this.geomColor = Color.magenta;
                break;
            }
            case 1: {
                this.overlayColor = Color.magenta;
                this.geomColor = Color.white;
                break;
            }
            case 2: {
                this.overlayColor = Color.red;
                this.geomColor = Color.green;
                break;
            }
            case 3: {
                this.overlayColor = Color.red;
                this.geomColor = Color.white;
            }
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new GeomAccuracyTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

