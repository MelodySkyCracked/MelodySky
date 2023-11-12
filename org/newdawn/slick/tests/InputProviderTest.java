/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.ControllerButtonControl;
import org.newdawn.slick.command.ControllerDirectionControl;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.command.MouseButtonControl;

public class InputProviderTest
extends BasicGame
implements InputProviderListener {
    private Command attack = new BasicCommand("attack");
    private Command jump = new BasicCommand("jump");
    private Command run = new BasicCommand("run");
    private InputProvider provider;
    private String message = "";

    public InputProviderTest() {
        super("InputProvider Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.provider = new InputProvider(gameContainer.getInput());
        this.provider.addListener(this);
        this.provider.bindCommand(new KeyControl(203), this.run);
        this.provider.bindCommand(new KeyControl(30), this.run);
        this.provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.LEFT), this.run);
        this.provider.bindCommand(new KeyControl(200), this.jump);
        this.provider.bindCommand(new KeyControl(17), this.jump);
        this.provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.UP), this.jump);
        this.provider.bindCommand(new KeyControl(57), this.attack);
        this.provider.bindCommand(new MouseButtonControl(0), this.attack);
        this.provider.bindCommand(new ControllerButtonControl(0, 1), this.attack);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.drawString("Press A, W, Left, Up, space, mouse button 1,and gamepad controls", 10.0f, 50.0f);
        graphics.drawString(this.message, 100.0f, 150.0f);
    }

    @Override
    public void update(GameContainer gameContainer, int n) {
    }

    @Override
    public void controlPressed(Command command) {
        this.message = "Pressed: " + command;
    }

    @Override
    public void controlReleased(Command command) {
        this.message = "Released: " + command;
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new InputProviderTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

