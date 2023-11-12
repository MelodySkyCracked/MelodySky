/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public abstract class BasicGameState
implements GameState {
    @Override
    public void inputStarted() {
    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void setInput(Input input) {
    }

    @Override
    public void inputEnded() {
    }

    @Override
    public abstract int getID();

    @Override
    public void controllerButtonPressed(int n, int n2) {
    }

    @Override
    public void controllerButtonReleased(int n, int n2) {
    }

    @Override
    public void controllerDownPressed(int n) {
    }

    @Override
    public void controllerDownReleased(int n) {
    }

    @Override
    public void controllerLeftPressed(int n) {
    }

    @Override
    public void controllerLeftReleased(int n) {
    }

    @Override
    public void controllerRightPressed(int n) {
    }

    @Override
    public void controllerRightReleased(int n) {
    }

    @Override
    public void controllerUpPressed(int n) {
    }

    @Override
    public void controllerUpReleased(int n) {
    }

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
    public void mouseReleased(int n, int n2, int n3) {
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
    }

    @Override
    public void mouseWheelMoved(int n) {
    }
}

