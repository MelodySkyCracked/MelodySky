/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.state.transition;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;
import org.newdawn.slick.util.MaskUtil;

public class BlobbyTransition
implements Transition {
    protected static SGL GL = Renderer.get();
    private GameState prev;
    private boolean finish;
    private Color background;
    private ArrayList blobs = new ArrayList();
    private int timer = 1000;
    private int blobCount = 10;

    public BlobbyTransition() {
    }

    public BlobbyTransition(Color color) {
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
        MaskUtil.resetMask();
    }

    @Override
    public void preRender(StateBasedGame stateBasedGame, GameContainer gameContainer, Graphics graphics) throws SlickException {
        this.prev.render(gameContainer, stateBasedGame, graphics);
        MaskUtil.defineMask();
        for (int i = 0; i < this.blobs.size(); ++i) {
            ((Blob)this.blobs.get(i)).render(graphics);
        }
        MaskUtil.finishDefineMask();
        MaskUtil.drawOnMask();
        if (this.background != null) {
            Color color = graphics.getColor();
            graphics.setColor(this.background);
            graphics.fillRect(0.0f, 0.0f, gameContainer.getWidth(), gameContainer.getHeight());
            graphics.setColor(color);
        }
    }

    @Override
    public void update(StateBasedGame stateBasedGame, GameContainer gameContainer, int n) throws SlickException {
        int n2;
        if (this.blobs.size() == 0) {
            for (n2 = 0; n2 < this.blobCount; ++n2) {
                this.blobs.add(new Blob(this, gameContainer));
            }
        }
        for (n2 = 0; n2 < this.blobs.size(); ++n2) {
            ((Blob)this.blobs.get(n2)).update(n);
        }
        this.timer -= n;
        if (this.timer < 0) {
            this.finish = true;
        }
    }

    private class Blob {
        private float x;
        private float y;
        private float growSpeed;
        private float rad;
        final BlobbyTransition this$0;

        public Blob(BlobbyTransition blobbyTransition, GameContainer gameContainer) {
            this.this$0 = blobbyTransition;
            this.x = (float)(Math.random() * (double)gameContainer.getWidth());
            this.y = (float)(Math.random() * (double)gameContainer.getWidth());
            this.growSpeed = (float)(1.0 + Math.random() * 1.0);
        }

        public void update(int n) {
            this.rad += this.growSpeed * (float)n * 0.4f;
        }

        public void render(Graphics graphics) {
            graphics.fillOval(this.x - this.rad, this.y - this.rad, this.rad * 2.0f, this.rad * 2.0f);
        }
    }
}

