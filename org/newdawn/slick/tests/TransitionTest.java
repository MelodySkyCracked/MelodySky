/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.BlobbyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;
import org.newdawn.slick.state.transition.RotateTransition;
import org.newdawn.slick.state.transition.SelectTransition;
import org.newdawn.slick.state.transition.Transition;
import org.newdawn.slick.state.transition.VerticalSplitTransition;
import org.newdawn.slick.util.Log;

public class TransitionTest
extends StateBasedGame {
    private Class[][] transitions = new Class[][]{{null, VerticalSplitTransition.class}, {FadeOutTransition.class, FadeInTransition.class}, {null, RotateTransition.class}, {null, HorizontalSplitTransition.class}, {null, BlobbyTransition.class}, {null, SelectTransition.class}};
    private int index;

    public TransitionTest() {
        super("Transition Test - Hit Space To Transition");
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new ImageState(this, 0, "testdata/wallpaper/paper1.png", 1));
        this.addState(new ImageState(this, 1, "testdata/wallpaper/paper2.png", 2));
        this.addState(new ImageState(this, 2, "testdata/bigimage.tga", 0));
    }

    public Transition[] getNextTransitionPair() {
        Transition[] transitionArray = new Transition[2];
        try {
            if (this.transitions[this.index][0] != null) {
                transitionArray[0] = (Transition)this.transitions[this.index][0].newInstance();
            }
            if (this.transitions[this.index][1] != null) {
                transitionArray[1] = (Transition)this.transitions[this.index][1].newInstance();
            }
        }
        catch (Throwable throwable) {
            Log.error(throwable);
        }
        ++this.index;
        if (this.index >= this.transitions.length) {
            this.index = 0;
        }
        return transitionArray;
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new TransitionTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    private class ImageState
    extends BasicGameState {
        private int id;
        private int next;
        private String ref;
        private Image image;
        final TransitionTest this$0;

        public ImageState(TransitionTest transitionTest, int n, String string, int n2) {
            this.this$0 = transitionTest;
            this.ref = string;
            this.id = n;
            this.next = n2;
        }

        @Override
        public int getID() {
            return this.id;
        }

        @Override
        public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
            this.image = new Image(this.ref);
        }

        @Override
        public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
            this.image.draw(0.0f, 0.0f, 800.0f, 600.0f);
            graphics.setColor(Color.red);
            graphics.fillRect(-50.0f, 200.0f, 50.0f, 50.0f);
        }

        @Override
        public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int n) throws SlickException {
            if (gameContainer.getInput().isKeyPressed(57)) {
                Transition[] transitionArray = this.this$0.getNextTransitionPair();
                stateBasedGame.enterState(this.next, transitionArray[0], transitionArray[1]);
            }
        }
    }
}

