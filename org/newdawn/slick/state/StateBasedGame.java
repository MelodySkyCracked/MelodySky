/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.state;

import java.util.HashMap;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.I;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.Transition;

public abstract class StateBasedGame
implements Game,
InputListener {
    private HashMap states = new HashMap();
    private GameState currentState;
    private GameState nextState;
    private GameContainer container;
    private String title;
    private Transition enterTransition;
    private Transition leaveTransition;

    public StateBasedGame(String string) {
        this.title = string;
        this.currentState = new I(this);
    }

    @Override
    public void inputStarted() {
    }

    public int getStateCount() {
        return this.states.keySet().size();
    }

    public int getCurrentStateID() {
        return this.currentState.getID();
    }

    public GameState getCurrentState() {
        return this.currentState;
    }

    @Override
    public void setInput(Input input) {
    }

    public void addState(GameState gameState) {
        this.states.put(new Integer(gameState.getID()), gameState);
        if (this.currentState.getID() == -1) {
            this.currentState = gameState;
        }
    }

    public GameState getState(int n) {
        return (GameState)this.states.get(new Integer(n));
    }

    public void enterState(int n) {
        this.enterState(n, new EmptyTransition(), new EmptyTransition());
    }

    public void enterState(int n, Transition transition, Transition transition2) {
        if (transition == null) {
            transition = new EmptyTransition();
        }
        if (transition2 == null) {
            transition2 = new EmptyTransition();
        }
        this.leaveTransition = transition;
        this.enterTransition = transition2;
        this.nextState = this.getState(n);
        if (this.nextState == null) {
            throw new RuntimeException("No game state registered with the ID: " + n);
        }
        this.leaveTransition.init(this.currentState, this.nextState);
    }

    @Override
    public final void init(GameContainer gameContainer) throws SlickException {
        this.container = gameContainer;
        this.initStatesList(gameContainer);
        for (GameState gameState : this.states.values()) {
            gameState.init(gameContainer, this);
        }
        if (this.currentState != null) {
            this.currentState.enter(gameContainer, this);
        }
    }

    public abstract void initStatesList(GameContainer var1) throws SlickException;

    @Override
    public final void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        this.preRenderState(gameContainer, graphics);
        if (this.leaveTransition != null) {
            this.leaveTransition.preRender(this, gameContainer, graphics);
        } else if (this.enterTransition != null) {
            this.enterTransition.preRender(this, gameContainer, graphics);
        }
        this.currentState.render(gameContainer, this, graphics);
        if (this.leaveTransition != null) {
            this.leaveTransition.postRender(this, gameContainer, graphics);
        } else if (this.enterTransition != null) {
            this.enterTransition.postRender(this, gameContainer, graphics);
        }
        this.postRenderState(gameContainer, graphics);
    }

    protected void preRenderState(GameContainer gameContainer, Graphics graphics) throws SlickException {
    }

    protected void postRenderState(GameContainer gameContainer, Graphics graphics) throws SlickException {
    }

    @Override
    public final void update(GameContainer gameContainer, int n) throws SlickException {
        this.preUpdateState(gameContainer, n);
        if (this.leaveTransition != null) {
            this.leaveTransition.update(this, gameContainer, n);
            if (this.leaveTransition.isComplete()) {
                this.currentState.leave(gameContainer, this);
                GameState gameState = this.currentState;
                this.currentState = this.nextState;
                this.nextState = null;
                this.leaveTransition = null;
                this.currentState.enter(gameContainer, this);
                if (this.enterTransition != null) {
                    this.enterTransition.init(this.currentState, gameState);
                }
            } else {
                return;
            }
        }
        if (this.enterTransition != null) {
            this.enterTransition.update(this, gameContainer, n);
            if (this.enterTransition.isComplete()) {
                this.enterTransition = null;
            } else {
                return;
            }
        }
        this.currentState.update(gameContainer, this, n);
        this.postUpdateState(gameContainer, n);
    }

    protected void preUpdateState(GameContainer gameContainer, int n) throws SlickException {
    }

    protected void postUpdateState(GameContainer gameContainer, int n) throws SlickException {
    }

    @Override
    public boolean closeRequested() {
        return true;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public GameContainer getContainer() {
        return this.container;
    }

    @Override
    public void controllerButtonPressed(int n, int n2) {
        if (this == null) {
            return;
        }
        this.currentState.controllerButtonPressed(n, n2);
    }

    @Override
    public void controllerButtonReleased(int n, int n2) {
        if (this == null) {
            return;
        }
        this.currentState.controllerButtonReleased(n, n2);
    }

    @Override
    public void controllerDownPressed(int n) {
        if (this == null) {
            return;
        }
        this.currentState.controllerDownPressed(n);
    }

    @Override
    public void controllerDownReleased(int n) {
        if (this == null) {
            return;
        }
        this.currentState.controllerDownReleased(n);
    }

    @Override
    public void controllerLeftPressed(int n) {
        if (this == null) {
            return;
        }
        this.currentState.controllerLeftPressed(n);
    }

    @Override
    public void controllerLeftReleased(int n) {
        if (this == null) {
            return;
        }
        this.currentState.controllerLeftReleased(n);
    }

    @Override
    public void controllerRightPressed(int n) {
        if (this == null) {
            return;
        }
        this.currentState.controllerRightPressed(n);
    }

    @Override
    public void controllerRightReleased(int n) {
        if (this == null) {
            return;
        }
        this.currentState.controllerRightReleased(n);
    }

    @Override
    public void controllerUpPressed(int n) {
        if (this == null) {
            return;
        }
        this.currentState.controllerUpPressed(n);
    }

    @Override
    public void controllerUpReleased(int n) {
        if (this == null) {
            return;
        }
        this.currentState.controllerUpReleased(n);
    }

    @Override
    public void keyPressed(int n, char c) {
        if (this == null) {
            return;
        }
        this.currentState.keyPressed(n, c);
    }

    @Override
    public void keyReleased(int n, char c) {
        if (this == null) {
            return;
        }
        this.currentState.keyReleased(n, c);
    }

    @Override
    public void mouseMoved(int n, int n2, int n3, int n4) {
        if (this == null) {
            return;
        }
        this.currentState.mouseMoved(n, n2, n3, n4);
    }

    @Override
    public void mouseDragged(int n, int n2, int n3, int n4) {
        if (this == null) {
            return;
        }
        this.currentState.mouseDragged(n, n2, n3, n4);
    }

    @Override
    public void mouseClicked(int n, int n2, int n3, int n4) {
        if (this == null) {
            return;
        }
        this.currentState.mouseClicked(n, n2, n3, n4);
    }

    @Override
    public void mousePressed(int n, int n2, int n3) {
        if (this == null) {
            return;
        }
        this.currentState.mousePressed(n, n2, n3);
    }

    @Override
    public void mouseReleased(int n, int n2, int n3) {
        if (this == null) {
            return;
        }
        this.currentState.mouseReleased(n, n2, n3);
    }

    @Override
    public boolean isAcceptingInput() {
        if (this == null) {
            return false;
        }
        return this.currentState.isAcceptingInput();
    }

    @Override
    public void inputEnded() {
    }

    @Override
    public void mouseWheelMoved(int n) {
        if (this == null) {
            return;
        }
        this.currentState.mouseWheelMoved(n);
    }
}

