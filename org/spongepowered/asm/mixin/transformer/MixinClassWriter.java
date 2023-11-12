/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

public class MixinClassWriter
extends ClassWriter {
    private static final String JAVA_LANG_OBJECT = "java/lang/Object";

    public MixinClassWriter(int n) {
        super(n);
    }

    public MixinClassWriter(ClassReader classReader, int n) {
        super(classReader, n);
    }

    @Override
    protected String getCommonSuperClass(String string, String string2) {
        ClassInfo classInfo;
        ClassInfo classInfo2 = ClassInfo.forName(string);
        if (classInfo2.hasSuperClass(classInfo = ClassInfo.forName(string2))) {
            return string2;
        }
        if (classInfo.hasSuperClass(classInfo2)) {
            return string;
        }
        if (classInfo2.isInterface() || classInfo.isInterface()) {
            return JAVA_LANG_OBJECT;
        }
        do {
            if ((classInfo2 = classInfo2.getSuperClass()) != null) continue;
            return JAVA_LANG_OBJECT;
        } while (!classInfo.hasSuperClass(classInfo2));
        return classInfo2.getName();
    }
}

