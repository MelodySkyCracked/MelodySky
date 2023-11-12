/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.IClassTransformer
 */
package org.spongepowered.asm.transformers;

import net.minecraft.launchwrapper.IClassTransformer;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.transformer.MixinClassWriter;

public abstract class TreeTransformer
implements IClassTransformer {
    private ClassReader classReader;
    private ClassNode classNode;

    protected final ClassNode readClass(byte[] byArray) {
        return this.readClass(byArray, true);
    }

    protected final ClassNode readClass(byte[] byArray, boolean bl) {
        ClassReader classReader = new ClassReader(byArray);
        if (bl) {
            this.classReader = classReader;
        }
        ClassNode classNode = new ClassNode();
        classReader.accept(classNode, 8);
        return classNode;
    }

    protected final byte[] writeClass(ClassNode classNode) {
        if (this.classReader != null && this.classNode == classNode) {
            this.classNode = null;
            MixinClassWriter mixinClassWriter = new MixinClassWriter(this.classReader, 3);
            this.classReader = null;
            classNode.accept(mixinClassWriter);
            return mixinClassWriter.toByteArray();
        }
        this.classNode = null;
        MixinClassWriter mixinClassWriter = new MixinClassWriter(3);
        classNode.accept(mixinClassWriter);
        return mixinClassWriter.toByteArray();
    }
}

