/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.tree.AnnotationNode;

public class TypeAnnotationNode
extends AnnotationNode {
    public int typeRef;
    public TypePath typePath;

    public TypeAnnotationNode(int n, TypePath typePath, String string) {
        this(327680, n, typePath, string);
        if (this.getClass() != TypeAnnotationNode.class) {
            throw new IllegalStateException();
        }
    }

    public TypeAnnotationNode(int n, int n2, TypePath typePath, String string) {
        super(n, string);
        this.typeRef = n2;
        this.typePath = typePath;
    }
}

