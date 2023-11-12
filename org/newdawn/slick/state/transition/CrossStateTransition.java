/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.state.transition;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

public abstract class CrossStateTransition
implements Transition {
    private GameState secondState;

    public CrossStateTransition(GameState gameState) {
        this.secondState = gameState;
    }

    @Override
    public abstract boolean isComplete();

    @Override
    public void postRender(StateBasedGame stateBasedGame, GameContainer gameContainer, Graphics graphics) throws SlickException {
        this.preRenderSecondState(stateBasedGame, gameContainer, graphics);
        this.secondState.render(gameContainer, stateBasedGame, graphics);
        this.postRenderSecondState(stateBasedGame, gameContainer, graphics);
    }

    @Override
    public void preRender(StateBasedGame stateBasedGame, GameContainer gameContainer, Graphics graphics) throws SlickException {
        this.preRenderFirstState(stateBasedGame, gameContainer, graphics);
    }

    @Override
    public void update(StateBasedGame stateBasedGame, GameContainer gameContainer, int n) throws SlickException {
    }

    public void preRenderFirstState(StateBasedGame stateBasedGame, GameContainer gameContainer, Graphics graphics) throws SlickException {
    }

    public void preRenderSecondState(StateBasedGame stateBasedGame, GameContainer gameContainer, Graphics graphics) throws SlickException {
    }

    public void postRenderSecondState(StateBasedGame stateBasedGame, GameContainer gameContainer, Graphics graphics) throws SlickException {
    }
}

