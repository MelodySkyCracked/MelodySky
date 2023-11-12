/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;

public class LookupSwitchInsnNode
extends AbstractInsnNode {
    public LabelNode dflt;
    public List keys;
    public List labels;

    public LookupSwitchInsnNode(LabelNode labelNode, int[] nArray, LabelNode[] labelNodeArray) {
        super(171);
        this.dflt = labelNode;
        this.keys = new ArrayList(nArray == null ? 0 : nArray.length);
        this.labels = new ArrayList(labelNodeArray == null ? 0 : labelNodeArray.length);
        if (nArray != null) {
            for (int i = 0; i < nArray.length; ++i) {
                this.keys.add(new Integer(nArray[i]));
            }
        }
        if (labelNodeArray != null) {
            this.labels.addAll(Arrays.asList(labelNodeArray));
        }
    }

    public int getType() {
        return 12;
    }

    public void accept(MethodVisitor methodVisitor) {
        int[] nArray = new int[this.keys.size()];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = (Integer)this.keys.get(i);
        }
        Label[] labelArray = new Label[this.labels.size()];
        for (int i = 0; i < labelArray.length; ++i) {
            labelArray[i] = ((LabelNode)this.labels.get(i)).getLabel();
        }
        methodVisitor.visitLookupSwitchInsn(this.dflt.getLabel(), nArray, labelArray);
        this.acceptAnnotations(methodVisitor);
    }

    public AbstractInsnNode clone(Map map) {
        LookupSwitchInsnNode lookupSwitchInsnNode = new LookupSwitchInsnNode(LookupSwitchInsnNode.clone(this.dflt, map), null, LookupSwitchInsnNode.clone(this.labels, map));
        lookupSwitchInsnNode.keys.addAll(this.keys);
        return lookupSwitchInsnNode.cloneAnnotations(this);
    }
}

