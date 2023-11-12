/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.command;

import org.newdawn.slick.command.Command;

public class BasicCommand
implements Command {
    public static char[] var1;
    public static String var2;
    public static String var3;
    public static String var4;
    public static String var5;
    public static String var6;
    private String name;

    public BasicCommand(String string) {
        this.name = string;
    }

    public String getName() {
        return this.name;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public boolean equals(Object object) {
        if (object instanceof BasicCommand) {
            return ((BasicCommand)object).name.equals(this.name);
        }
        return false;
    }

    public String toString() {
        return "[Command=" + this.name + "]";
    }
}

