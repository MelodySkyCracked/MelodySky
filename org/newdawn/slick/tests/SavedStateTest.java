/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SavedState;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;

public class SavedStateTest
extends BasicGame
implements ComponentListener {
    private TextField name;
    private TextField age;
    private String nameValue = "none";
    private int ageValue = 0;
    private SavedState state;
    private String message = "Enter a name and age to store";
    private static AppGameContainer container;

    public SavedStateTest() {
        super("Saved State Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.state = new SavedState("testdata");
        this.nameValue = this.state.getString("name", "DefaultName");
        this.ageValue = (int)this.state.getNumber("age", 64.0);
        this.name = new TextField(gameContainer, gameContainer.getDefaultFont(), 100, 100, 300, 20, this);
        this.age = new TextField(gameContainer, gameContainer.getDefaultFont(), 100, 150, 201, 20, this);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        this.name.render(gameContainer, graphics);
        this.age.render(gameContainer, graphics);
        gameContainer.getDefaultFont().drawString(100.0f, 300.0f, "Stored Name: " + this.nameValue);
        gameContainer.getDefaultFont().drawString(100.0f, 350.0f, "Stored Age: " + this.ageValue);
        gameContainer.getDefaultFont().drawString(200.0f, 500.0f, this.message);
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 1) {
            System.exit(0);
        }
    }

    public static void main(String[] stringArray) {
        try {
            container = new AppGameContainer(new SavedStateTest());
            container.setDisplayMode(800, 600, false);
            container.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        if (abstractComponent == this.name) {
            this.nameValue = this.name.getText();
            this.state.setString("name", this.nameValue);
        }
        if (abstractComponent == this.age) {
            try {
                this.ageValue = Integer.parseInt(this.age.getText());
                this.state.setNumber("age", this.ageValue);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        try {
            this.state.save();
        }
        catch (Exception exception) {
            this.message = System.currentTimeMillis() + " : Failed to save state";
        }
    }
}

