/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.state.transition;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

public class FadeOutTransition
implements Transition {
    private Color color;
    private int fadeTime;

    public FadeOutTransition() {
        this(Color.black, 500);
    }

    public FadeOutTransition(Color color) {
        this(color, 500);
    }

    public FadeOutTransition(Color color, int n) {
        this.color = new Color(color);
        this.color.a = 0.0f;
        this.fadeTime = n;
    }

    @Override
    public boolean isComplete() {
        return this.color.a >= 1.0f;
    }

    @Override
    public void postRender(StateBasedGame stateBasedGame, GameContainer gameContainer, Graphics graphics) {
        Color color = graphics.getColor();
        graphics.setColor(this.color);
        graphics.fillRect(0.0f, 0.0f, gameContainer.getWidth() * 2, gameContainer.getHeight() * 2);
        graphics.setColor(color);
    }

    @Override
    public void update(StateBasedGame stateBasedGame, GameContainer gameContainer, int n) {
        this.color.a += (float)n * (1.0f / (float)this.fadeTime);
        if (this.color.a > 1.0f) {
            this.color.a = 1.0f;
        }
    }

    @Override
    public void preRender(StateBasedGame stateBasedGame, GameContainer gameContainer, Graphics graphics) {
    }

    @Override
    public void init(GameState gameState, GameState gameState2) {
    }
}

