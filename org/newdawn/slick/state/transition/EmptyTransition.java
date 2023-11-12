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

public class EmptyTransition
implements Transition {
    @Override
    public boolean isComplete() {
        return true;
    }

    @Override
    public void postRender(StateBasedGame stateBasedGame, GameContainer gameContainer, Graphics graphics) throws SlickException {
    }

    @Override
    public void preRender(StateBasedGame stateBasedGame, GameContainer gameContainer, Graphics graphics) throws SlickException {
    }

    @Override
    public void update(StateBasedGame stateBasedGame, GameContainer gameContainer, int n) throws SlickException {
    }

    @Override
    public void init(GameState gameState, GameState gameState2) {
    }
}

