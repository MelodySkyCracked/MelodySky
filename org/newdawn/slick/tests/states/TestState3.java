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
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class TestState3
extends BasicGameState {
    public static final int ID = 3;
    private Font font;
    private String[] options = new String[]{"Start Game", "Credits", "Highscores", "Instructions", "Exit"};
    private int selected;
    private StateBasedGame game;

    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.font = new AngelCodeFont("testdata/demo2.fnt", "testdata/demo2_00.tga");
        this.game = stateBasedGame;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        graphics.setFont(this.font);
        graphics.setColor(Color.blue);
        graphics.drawString("This is State 3", 200.0f, 50.0f);
        graphics.setColor(Color.white);
        for (int i = 0; i < this.options.length; ++i) {
            graphics.drawString(this.options[i], 400 - this.font.getWidth(this.options[i]) / 2, 200 + i * 50);
            if (this.selected != i) continue;
            graphics.drawRect(200.0f, 190 + i * 50, 400.0f, 50.0f);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int n) {
    }

    @Override
    public void keyReleased(int n, char c) {
        if (n == 208) {
            ++this.selected;
            if (this.selected >= this.options.length) {
                this.selected = 0;
            }
        }
        if (n == 200) {
            --this.selected;
            if (this.selected < 0) {
                this.selected = this.options.length - 1;
            }
        }
        if (n == 2) {
            this.game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
        if (n == 3) {
            this.game.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        }
    }
}

