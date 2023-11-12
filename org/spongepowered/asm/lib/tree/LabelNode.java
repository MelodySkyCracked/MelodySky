/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree;

import java.util.Map;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;

public class LabelNode
extends AbstractInsnNode {
    private Label label;

    public LabelNode() {
        super(-1);
    }

    public LabelNode(Label label) {
        super(-1);
        this.label = label;
    }

    public int getType() {
        return 8;
    }

    public Label getLabel() {
        if (this.label == null) {
            this.label = new Label();
        }
        return this.label;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitLabel(this.getLabel());
    }

    public AbstractInsnNode clone(Map map) {
        return (AbstractInsnNode)map.get(this);
    }

    public void resetLabel() {
        this.label = null;
    }
}

