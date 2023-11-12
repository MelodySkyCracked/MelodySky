/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick;

import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.SlickException;

public abstract class BasicGame
implements Game,
InputListener {
    private static final int MAX_CONTROLLERS = 20;
    private static final int MAX_CONTROLLER_BUTTONS = 100;
    private String title;
    protected boolean[] controllerLeft = new boolean[20];
    protected boolean[] controllerRight = new boolean[20];
    protected boolean[] controllerUp = new boolean[20];
    protected boolean[] controllerDown = new boolean[20];
    protected boolean[][] controllerButton = new boolean[20][100];

    public BasicGame(String string) {
        this.title = string;
    }

    @Override
    public void setInput(Input input) {
    }

    @Override
    public boolean closeRequested() {
        return true;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public abstract void init(GameContainer var1) throws SlickException;

    @Override
    public void keyPressed(int n, char c) {
    }

    @Override
    public void keyReleased(int n, char c) {
    }

    @Override
    public void mouseMoved(int n, int n2, int n3, int n4) {
    }

    @Override
    public void mouseDragged(int n, int n2, int n3, int n4) {
    }

    @Override
    public void mouseClicked(int n, int n2, int n3, int n4) {
    }

    @Override
    public void mousePressed(int n, int n2, int n3) {
    }

    @Override
    public void controllerButtonPressed(int n, int n2) {
        this.controllerButton[n][n2] = true;
    }

    @Override
    public void controllerButtonReleased(int n, int n2) {
        this.controllerButton[n][n2] = false;
    }

    @Override
    public void controllerDownPressed(int n) {
        this.controllerDown[n] = true;
    }

    @Override
    public void controllerDownReleased(int n) {
        this.controllerDown[n] = false;
    }

    @Override
    public void controllerLeftPressed(int n) {
        this.controllerLeft[n] = true;
    }

    @Override
    public void controllerLeftReleased(int n) {
        this.controllerLeft[n] = false;
    }

    @Override
    public void controllerRightPressed(int n) {
        this.controllerRight[n] = true;
    }

    @Override
    public void controllerRightReleased(int n) {
        this.controllerRight[n] = false;
    }

    @Override
    public void controllerUpPressed(int n) {
        this.controllerUp[n] = true;
    }

    @Override
    public void controllerUpReleased(int n) {
        this.controllerUp[n] = false;
    }

    @Override
    public void mouseReleased(int n, int n2, int n3) {
    }

    @Override
    public abstract void update(GameContainer var1, int var2) throws SlickException;

    @Override
    public void mouseWheelMoved(int n) {
    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {
    }

    @Override
    public void inputStarted() {
    }
}

