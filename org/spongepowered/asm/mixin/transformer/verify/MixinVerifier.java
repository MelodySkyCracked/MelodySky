/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.transformer.verify;

import java.util.List;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.analysis.SimpleVerifier;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

public class MixinVerifier
extends SimpleVerifier {
    private Type currentClass;
    private Type currentSuperClass;
    private List currentClassInterfaces;
    private boolean isInterface;

    public MixinVerifier(Type type, Type type2, List list, boolean bl) {
        super(type, type2, list, bl);
        this.currentClass = type;
        this.currentSuperClass = type2;
        this.currentClassInterfaces = list;
        this.isInterface = bl;
    }

    protected boolean isInterface(Type type) {
        if (this.currentClass != null && type.equals(this.currentClass)) {
            return this.isInterface;
        }
        return ClassInfo.forType(type).isInterface();
    }

    @Override
    protected Type getSuperClass(Type type) {
        if (this.currentClass != null && type.equals(this.currentClass)) {
            return this.currentSuperClass;
        }
        ClassInfo classInfo = ClassInfo.forType(type).getSuperClass();
        return classInfo == null ? null : Type.getType("L" + classInfo.getName() + ";");
    }
}

