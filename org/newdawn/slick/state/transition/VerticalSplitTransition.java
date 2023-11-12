/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.state.transition;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

public class VerticalSplitTransition
implements Transition {
    protected static SGL GL = Renderer.get();
    private GameState prev;
    private float offset;
    private boolean finish;
    private Color background;

    public VerticalSplitTransition() {
    }

    public VerticalSplitTransition(Color color) {
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
        Color color;
        graphics.translate(0.0f, -this.offset);
        graphics.setClip(0, (int)(-this.offset), gameContainer.getWidth(), gameContainer.getHeight() / 2);
        if (this.background != null) {
            color = graphics.getColor();
            graphics.setColor(this.background);
            graphics.fillRect(0.0f, 0.0f, gameContainer.getWidth(), gameContainer.getHeight());
            graphics.setColor(color);
        }
        GL.glPushMatrix();
        this.prev.render(gameContainer, stateBasedGame, graphics);
        GL.glPopMatrix();
        graphics.clearClip();
        graphics.resetTransform();
        graphics.translate(0.0f, this.offset);
        graphics.setClip(0, (int)((float)(gameContainer.getHeight() / 2) + this.offset), gameContainer.getWidth(), gameContainer.getHeight() / 2);
        if (this.background != null) {
            color = graphics.getColor();
            graphics.setColor(this.background);
            graphics.fillRect(0.0f, 0.0f, gameContainer.getWidth(), gameContainer.getHeight());
            graphics.setColor(color);
        }
        GL.glPushMatrix();
        this.prev.render(gameContainer, stateBasedGame, graphics);
        GL.glPopMatrix();
        graphics.clearClip();
        graphics.translate(0.0f, -this.offset);
    }

    @Override
    public void preRender(StateBasedGame stateBasedGame, GameContainer gameContainer, Graphics graphics) throws SlickException {
    }

    @Override
    public void update(StateBasedGame stateBasedGame, GameContainer gameContainer, int n) throws SlickException {
        this.offset += (float)n * 1.0f;
        if (this.offset > (float)(gameContainer.getHeight() / 2)) {
            this.finish = true;
        }
    }
}

