/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick;

import java.awt.Canvas;
import javax.swing.SwingUtilities;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.lII;
import org.newdawn.slick.llII;
import org.newdawn.slick.util.Log;

public class CanvasGameContainer
extends Canvas {
    protected Container container;
    protected Game game;

    public CanvasGameContainer(Game game) throws SlickException {
        this(game, false);
    }

    public CanvasGameContainer(Game game, boolean bl) throws SlickException {
        this.game = game;
        this.setIgnoreRepaint(true);
        this.requestFocus();
        this.setSize(500, 500);
        this.container = new Container(this, game, bl);
        this.container.setForceExit(false);
    }

    public void start() throws SlickException {
        SwingUtilities.invokeLater(new llII(this));
    }

    private void scheduleUpdate() {
        if (!this.isVisible()) {
            return;
        }
        SwingUtilities.invokeLater(new lII(this));
    }

    public void dispose() {
    }

    static void access$000(CanvasGameContainer canvasGameContainer) {
        canvasGameContainer.scheduleUpdate();
    }

    private class Container
    extends AppGameContainer {
        final CanvasGameContainer this$0;

        public Container(CanvasGameContainer canvasGameContainer, Game game, boolean bl) throws SlickException {
            this.this$0 = canvasGameContainer;
            super(game, canvasGameContainer.getWidth(), canvasGameContainer.getHeight(), false);
            this.width = canvasGameContainer.getWidth();
            this.height = canvasGameContainer.getHeight();
            if (bl) {
                Container.enableSharedContext();
            }
        }

        @Override
        protected void updateFPS() {
            super.updateFPS();
        }

        @Override
        protected boolean running() {
            return super.running() && this.this$0.isDisplayable();
        }

        @Override
        public int getHeight() {
            return this.this$0.getHeight();
        }

        @Override
        public int getWidth() {
            return this.this$0.getWidth();
        }

        public void checkDimensions() {
            if (this.width != this.this$0.getWidth() || this.height != this.this$0.getHeight()) {
                try {
                    this.setDisplayMode(this.this$0.getWidth(), this.this$0.getHeight(), false);
                }
                catch (SlickException slickException) {
                    Log.error(slickException);
                }
            }
        }
    }
}

