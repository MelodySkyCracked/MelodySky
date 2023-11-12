/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.state.transition;

import java.util.ArrayList;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

public class CombinedTransition
implements Transition {
    private ArrayList transitions = new ArrayList();

    public void addTransition(Transition transition) {
        this.transitions.add(transition);
    }

    @Override
    public boolean isComplete() {
        for (int i = 0; i < this.transitions.size(); ++i) {
            if (((Transition)this.transitions.get(i)).isComplete()) continue;
            return false;
        }
        return true;
    }

    @Override
    public void postRender(StateBasedGame stateBasedGame, GameContainer gameContainer, Graphics graphics) throws SlickException {
        for (int i = this.transitions.size() - 1; i >= 0; --i) {
            ((Transition)this.transitions.get(i)).postRender(stateBasedGame, gameContainer, graphics);
        }
    }

    @Override
    public void preRender(StateBasedGame stateBasedGame, GameContainer gameContainer, Graphics graphics) throws SlickException {
        for (int i = 0; i < this.transitions.size(); ++i) {
            ((Transition)this.transitions.get(i)).postRender(stateBasedGame, gameContainer, graphics);
        }
    }

    @Override
    public void update(StateBasedGame stateBasedGame, GameContainer gameContainer, int n) throws SlickException {
        for (int i = 0; i < this.transitions.size(); ++i) {
            Transition transition = (Transition)this.transitions.get(i);
            if (transition.isComplete()) continue;
            transition.update(stateBasedGame, gameContainer, n);
        }
    }

    @Override
    public void init(GameState gameState, GameState gameState2) {
        for (int i = this.transitions.size() - 1; i >= 0; --i) {
            ((Transition)this.transitions.get(i)).init(gameState, gameState2);
        }
    }
}

