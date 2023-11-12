/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package org.spongepowered.tools.agent;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.mixin.MixinEnvironment;

class MixinAgentClassLoader
extends ClassLoader {
    private static final Logger logger = LogManager.getLogger((String)"mixin.agent");
    private Map mixins = new HashMap();
    private Map targets = new HashMap();

    MixinAgentClassLoader() {
    }

    void addMixinClass(String string) {
        logger.debug("Mixin class {} added to class loader", new Object[]{string});
        try {
            byte[] byArray = this.materialise(string);
            Class<?> clazz = this.defineClass(string, byArray, 0, byArray.length);
            clazz.newInstance();
            this.mixins.put(clazz, byArray);
        }
        catch (Throwable throwable) {
            logger.catching(throwable);
        }
    }

    void addTargetClass(String string, byte[] byArray) {
        this.targets.put(string, byArray);
    }

    byte[] getFakeMixinBytecode(Class clazz) {
        return (byte[])this.mixins.get(clazz);
    }

    byte[] getOriginalTargetBytecode(String string) {
        return (byte[])this.targets.get(string);
    }

    private byte[] materialise(String string) {
        ClassWriter classWriter = new ClassWriter(3);
        classWriter.visit(MixinEnvironment.getCompatibilityLevel().classVersion(), 1, string.replace('.', '/'), null, Type.getInternalName(Object.class), null);
        MethodVisitor methodVisitor = classWriter.visitMethod(1, "<init>", "()V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(25, 0);
        methodVisitor.visitMethodInsn(183, Type.getInternalName(Object.class), "<init>", "()V", false);
        methodVisitor.visitInsn(177);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();
        classWriter.visitEnd();
        return classWriter.toByteArray();
    }
}

