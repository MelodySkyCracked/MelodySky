/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.util.ASMHelper;

public class InjectionNodes
extends ArrayList {
    private static final long serialVersionUID = 1L;

    public InjectionNode add(AbstractInsnNode abstractInsnNode) {
        InjectionNode injectionNode = this.get(abstractInsnNode);
        if (injectionNode == null) {
            injectionNode = new InjectionNode(abstractInsnNode);
            this.add(injectionNode);
        }
        return injectionNode;
    }

    public InjectionNode get(AbstractInsnNode abstractInsnNode) {
        for (InjectionNode injectionNode : this) {
            if (!injectionNode.matches(abstractInsnNode)) continue;
            return injectionNode;
        }
        return null;
    }

    public boolean contains(AbstractInsnNode abstractInsnNode) {
        return this.get(abstractInsnNode) != null;
    }

    public void replace(AbstractInsnNode abstractInsnNode, AbstractInsnNode abstractInsnNode2) {
        InjectionNode injectionNode = this.get(abstractInsnNode);
        if (injectionNode != null) {
            injectionNode.replace(abstractInsnNode2);
        }
    }

    public void remove(AbstractInsnNode abstractInsnNode) {
        InjectionNode injectionNode = this.get(abstractInsnNode);
        if (injectionNode != null) {
            injectionNode.remove();
        }
    }

    public static class InjectionNode
    implements Comparable {
        private final AbstractInsnNode originalTarget;
        private AbstractInsnNode currentTarget;
        private Map decorations;

        public InjectionNode(AbstractInsnNode abstractInsnNode) {
            this.currentTarget = this.originalTarget = abstractInsnNode;
        }

        public AbstractInsnNode getOriginalTarget() {
            return this.originalTarget;
        }

        public AbstractInsnNode getCurrentTarget() {
            return this.currentTarget;
        }

        public InjectionNode replace(AbstractInsnNode abstractInsnNode) {
            this.currentTarget = abstractInsnNode;
            return this;
        }

        public InjectionNode remove() {
            this.currentTarget = null;
            return this;
        }

        public boolean matches(AbstractInsnNode abstractInsnNode) {
            return this.originalTarget == abstractInsnNode || this.currentTarget == abstractInsnNode;
        }

        public boolean isReplaced() {
            return this.originalTarget != this.currentTarget;
        }

        public boolean isRemoved() {
            return this.currentTarget == null;
        }

        public InjectionNode decorate(String string, Object object) {
            if (this.decorations == null) {
                this.decorations = new HashMap();
            }
            this.decorations.put(string, object);
            return this;
        }

        public boolean hasDecoration(Object object) {
            return this.decorations != null && this.decorations.get(object) != null;
        }

        public Object getDecoration(String string) {
            return this.decorations == null ? null : this.decorations.get(string);
        }

        public int compareTo(InjectionNode injectionNode) {
            return injectionNode == null ? Integer.MAX_VALUE : this.hashCode() - injectionNode.hashCode();
        }

        public String toString() {
            return String.format("InjectionNode[%s]", ASMHelper.getNodeDescriptionForDebug(this.currentTarget).replaceAll("\\s+", " "));
        }

        public int compareTo(Object object) {
            return this.compareTo((InjectionNode)object);
        }
    }
}

