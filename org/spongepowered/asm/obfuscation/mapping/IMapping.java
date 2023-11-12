/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.obfuscation.mapping;

public interface IMapping {
    public Type getType();

    public Object move(String var1);

    public Object remap(String var1);

    public Object transform(String var1);

    public Object copy();

    public String getName();

    public String getSimpleName();

    public String getOwner();

    public String getDesc();

    public String serialise();

    public static enum Type {
        FIELD,
        METHOD,
        CLASS,
        PACKAGE;

    }
}

