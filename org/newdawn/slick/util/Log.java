/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util;

import java.security.AccessController;
import org.newdawn.slick.util.DefaultLogSystem;
import org.newdawn.slick.util.I;
import org.newdawn.slick.util.LogSystem;

public final class Log {
    private static boolean verbose = true;
    private static boolean forcedVerbose = false;
    private static final String forceVerboseProperty = "org.newdawn.slick.forceVerboseLog";
    private static final String forceVerbosePropertyOnValue = "true";
    private static LogSystem logSystem = new DefaultLogSystem();

    private Log() {
    }

    public static void setLogSystem(LogSystem logSystem) {
        Log.logSystem = logSystem;
    }

    public static void setVerbose(boolean bl) {
        if (forcedVerbose) {
            return;
        }
        verbose = bl;
    }

    public static void checkVerboseLogSetting() {
        try {
            AccessController.doPrivileged(new I());
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public static void setForcedVerboseOn() {
        forcedVerbose = true;
        verbose = true;
    }

    public static void error(String string, Throwable throwable) {
        logSystem.error(string, throwable);
    }

    public static void error(Throwable throwable) {
        logSystem.error(throwable);
    }

    public static void error(String string) {
        logSystem.error(string);
    }

    public static void warn(String string) {
        logSystem.warn(string);
    }

    public static void warn(String string, Throwable throwable) {
        logSystem.warn(string, throwable);
    }

    public static void info(String string) {
        if (verbose || forcedVerbose) {
            logSystem.info(string);
        }
    }

    public static void debug(String string) {
        if (verbose || forcedVerbose) {
            logSystem.debug(string);
        }
    }
}

