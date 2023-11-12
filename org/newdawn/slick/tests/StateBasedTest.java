/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tests.states.TestState1;
import org.newdawn.slick.tests.states.TestState2;
import org.newdawn.slick.tests.states.TestState3;

public class StateBasedTest
extends StateBasedGame {
    public StateBasedTest() {
        super("State Based Test");
    }

    @Override
    public void initStatesList(GameContainer gameContainer) {
        this.addState(new TestState1());
        this.addState(new TestState2());
        this.addState(new TestState3());
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new StateBasedTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

