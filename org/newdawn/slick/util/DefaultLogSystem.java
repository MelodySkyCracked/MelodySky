/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util;

import java.io.PrintStream;
import java.util.Date;
import org.newdawn.slick.util.LogSystem;

public class DefaultLogSystem
implements LogSystem {
    public static PrintStream out = System.out;

    @Override
    public void error(String string, Throwable throwable) {
        this.error(string);
        this.error(throwable);
    }

    @Override
    public void error(Throwable throwable) {
        out.println(new Date() + " ERROR:" + throwable.getMessage());
        throwable.printStackTrace(out);
    }

    @Override
    public void error(String string) {
        out.println(new Date() + " ERROR:" + string);
    }

    @Override
    public void warn(String string) {
        out.println(new Date() + " WARN:" + string);
    }

    @Override
    public void info(String string) {
        out.println(new Date() + " INFO:" + string);
    }

    @Override
    public void debug(String string) {
        out.println(new Date() + " DEBUG:" + string);
    }

    @Override
    public void warn(String string, Throwable throwable) {
        this.warn(string);
        throwable.printStackTrace(out);
    }
}

