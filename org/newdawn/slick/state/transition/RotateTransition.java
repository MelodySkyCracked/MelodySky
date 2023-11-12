/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.state.transition;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

public class RotateTransition
implements Transition {
    private GameState prev;
    private float ang;
    private boolean finish;
    private float scale = 1.0f;
    private Color background;

    public RotateTransition() {
    }

    public RotateTransition(Color color) {
        this.background = color;
    }

    @Override
    public void init(GameState gameState, GameState gameState2) {
        this.prev = gameState2;
    }

    @Override
    public boolean isComplete() {
        return this.finish;
    }

    @Override
    public void postRender(StateBasedGame stateBasedGame, GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.translate(gameContainer.getWidth() / 2, gameContainer.getHeight() / 2);
        graphics.scale(this.scale, this.scale);
        graphics.rotate(0.0f, 0.0f, this.ang);
        graphics.translate(-gameContainer.getWidth() / 2, -gameContainer.getHeight() / 2);
        if (this.background != null) {
            Color color = graphics.getColor();
            graphics.setColor(this.background);
            graphics.fillRect(0.0f, 0.0f, gameContainer.getWidth(), gameContainer.getHeight());
            graphics.setColor(color);
        }
        this.prev.render(gameContainer, stateBasedGame, graphics);
        graphics.translate(gameContainer.getWidth() / 2, gameContainer.getHeight() / 2);
        graphics.rotate(0.0f, 0.0f, -this.ang);
        graphics.scale(1.0f / this.scale, 1.0f / this.scale);
        graphics.translate(-gameContainer.getWidth() / 2, -gameContainer.getHeight() / 2);
    }

    @Override
    public void preRender(StateBasedGame stateBasedGame, GameContainer gameContainer, Graphics graphics) throws SlickException {
    }

    @Override
    public void update(StateBasedGame stateBasedGame, GameContainer gameContainer, int n) throws SlickException {
        this.ang += (float)n * 0.5f;
        if (this.ang > 500.0f) {
            this.finish = true;
        }
        this.scale -= (float)n * 0.001f;
        if (this.scale < 0.0f) {
            this.scale = 0.0f;
        }
    }
}

