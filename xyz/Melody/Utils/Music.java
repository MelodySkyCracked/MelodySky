/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils;

import xyz.Melody.Utils.lII;

public final class Music {
    public static synchronized void playSound(Class clazz, String string) {
        new Thread(new lII(clazz, string)).start();
    }
}

