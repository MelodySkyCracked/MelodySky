/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests.states;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tests.states.I;

public class TestState1
extends BasicGameState {
    public static final int ID = 1;
    private Font font;
    private StateBasedGame game;

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.game = stateBasedGame;
        this.font = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        graphics.setFont(this.font);
        graphics.setColor(Color.white);
        graphics.drawString("State Based Game Test", 100.0f, 100.0f);
        graphics.drawString("Numbers 1-3 will switch between states.", 150.0f, 300.0f);
        graphics.setColor(Color.red);
        graphics.drawString("This is State 1", 200.0f, 50.0f);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int n) {
    }

    @Override
    public void keyReleased(int n, char c) {
        if (n == 3) {
            GameState gameState = this.game.getState(2);
            long l2 = System.currentTimeMillis();
            I i = new I(this, gameState, l2);
            this.game.enterState(2, i, new EmptyTransition());
        }
        if (n == 4) {
            this.game.enterState(3, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
    }
}

