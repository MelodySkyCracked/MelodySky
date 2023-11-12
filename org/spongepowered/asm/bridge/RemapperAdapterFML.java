/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.objectweb.asm.commons.Remapper
 */
package org.spongepowered.asm.bridge;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.objectweb.asm.commons.Remapper;
import org.spongepowered.asm.bridge.RemapperAdapter;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

public class RemapperAdapterFML
extends RemapperAdapter {
    private final Method mdUnmap;

    private RemapperAdapterFML(Remapper remapper, Method method) {
        super(remapper);
        this.logger.info("Initialised Mixin FML Remapper Adapter with {}", new Object[]{remapper});
        this.mdUnmap = method;
    }

    @Override
    public String unmap(String string) {
        try {
            return this.mdUnmap.invoke(this.remapper, string).toString();
        }
        catch (Exception exception) {
            return string;
        }
    }

    public static IRemapper create() {
        try {
            Class clazz = RemapperAdapterFML.getFMLDeobfuscatingRemapper();
            Field field = clazz.getDeclaredField("INSTANCE");
            Method method = clazz.getDeclaredMethod("unmap", String.class);
            Remapper remapper = (Remapper)field.get(null);
            return new RemapperAdapterFML(remapper, method);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private static Class getFMLDeobfuscatingRemapper() throws ClassNotFoundException {
        try {
            return Class.forName("net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper");
        }
        catch (ClassNotFoundException classNotFoundException) {
            return Class.forName("cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper");
        }
    }
}

