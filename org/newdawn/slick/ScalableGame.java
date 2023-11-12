/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick;

import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.SlickCallable;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

public class ScalableGame
implements Game {
    private static SGL GL = Renderer.get();
    private float normalWidth;
    private float normalHeight;
    private Game held;
    private boolean maintainAspect;
    private int targetWidth;
    private int targetHeight;
    private GameContainer container;

    public ScalableGame(Game game, int n, int n2) {
        this(game, n, n2, false);
    }

    public ScalableGame(Game game, int n, int n2, boolean bl) {
        this.held = game;
        this.normalWidth = n;
        this.normalHeight = n2;
        this.maintainAspect = bl;
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.container = gameContainer;
        this.recalculateScale();
        this.held.init(gameContainer);
    }

    public void recalculateScale() throws SlickException {
        int n;
        int n2;
        this.targetWidth = this.container.getWidth();
        this.targetHeight = this.container.getHeight();
        if (this.maintainAspect) {
            n2 = (double)(this.normalWidth / this.normalHeight) > 1.6 ? 1 : 0;
            n = (double)((float)this.targetWidth / (float)this.targetHeight) > 1.6 ? 1 : 0;
            float f = (float)this.targetWidth / this.normalWidth;
            float f2 = (float)this.targetHeight / this.normalHeight;
            if ((n2 & n) != 0) {
                float f3 = f < f2 ? f : f2;
                this.targetWidth = (int)(this.normalWidth * f3);
                this.targetHeight = (int)(this.normalHeight * f3);
            } else if ((n2 & (n == 0 ? 1 : 0)) != 0) {
                this.targetWidth = (int)(this.normalWidth * f);
                this.targetHeight = (int)(this.normalHeight * f);
            } else if (((n2 == 0 ? 1 : 0) & n) != 0) {
                this.targetWidth = (int)(this.normalWidth * f2);
                this.targetHeight = (int)(this.normalHeight * f2);
            } else {
                float f4 = f < f2 ? f : f2;
                this.targetWidth = (int)(this.normalWidth * f4);
                this.targetHeight = (int)(this.normalHeight * f4);
            }
        }
        if (this.held instanceof InputListener) {
            this.container.getInput().addListener((InputListener)((Object)this.held));
        }
        this.container.getInput().setScale(this.normalWidth / (float)this.targetWidth, this.normalHeight / (float)this.targetHeight);
        n2 = 0;
        n = 0;
        if (this.targetHeight < this.container.getHeight()) {
            n2 = (this.container.getHeight() - this.targetHeight) / 2;
        }
        if (this.targetWidth < this.container.getWidth()) {
            n = (this.container.getWidth() - this.targetWidth) / 2;
        }
        this.container.getInput().setOffset((float)(-n) / ((float)this.targetWidth / this.normalWidth), (float)(-n2) / ((float)this.targetHeight / this.normalHeight));
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
        if (this.targetHeight != gameContainer.getHeight() || this.targetWidth != gameContainer.getWidth()) {
            this.recalculateScale();
        }
        this.held.update(gameContainer, n);
    }

    @Override
    public final void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        int n = 0;
        int n2 = 0;
        if (this.targetHeight < gameContainer.getHeight()) {
            n = (gameContainer.getHeight() - this.targetHeight) / 2;
        }
        if (this.targetWidth < gameContainer.getWidth()) {
            n2 = (gameContainer.getWidth() - this.targetWidth) / 2;
        }
        SlickCallable.enterSafeBlock();
        graphics.setClip(n2, n, this.targetWidth, this.targetHeight);
        GL.glTranslatef(n2, n, 0.0f);
        graphics.scale((float)this.targetWidth / this.normalWidth, (float)this.targetHeight / this.normalHeight);
        GL.glPushMatrix();
        this.held.render(gameContainer, graphics);
        GL.glPopMatrix();
        graphics.clearClip();
        SlickCallable.leaveSafeBlock();
        this.renderOverlay(gameContainer, graphics);
    }

    protected void renderOverlay(GameContainer gameContainer, Graphics graphics) {
    }

    @Override
    public boolean closeRequested() {
        return this.held.closeRequested();
    }

    @Override
    public String getTitle() {
        return this.held.getTitle();
    }
}

