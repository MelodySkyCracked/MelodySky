/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;

public class Bootstrap {
    public static void runAsApplication(Game game, int n, int n2, boolean bl) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(game, n, n2, bl);
            appGameContainer.start();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

