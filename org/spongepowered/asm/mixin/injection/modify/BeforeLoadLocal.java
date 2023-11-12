/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collection;
import java.util.ListIterator;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
import org.spongepowered.asm.mixin.injection.modify.ModifyVariableInjector;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.Target;

@InjectionPoint.AtCode(value="LOAD")
public class BeforeLoadLocal
extends ModifyVariableInjector.ContextualInjectionPoint {
    private final Type returnType;
    private final LocalVariableDiscriminator discriminator;
    private final int opcode;
    private final int ordinal;
    private boolean opcodeAfter;

    protected BeforeLoadLocal(InjectionPointData injectionPointData) {
        this(injectionPointData, 21, false);
    }

    protected BeforeLoadLocal(InjectionPointData injectionPointData, int n, boolean bl) {
        super(injectionPointData.getMixin());
        this.returnType = injectionPointData.getReturnType();
        this.discriminator = injectionPointData.getLocalVariableDiscriminator();
        this.opcode = injectionPointData.getOpcode(this.returnType.getOpcode(n));
        this.ordinal = injectionPointData.getOrdinal();
        this.opcodeAfter = bl;
    }

    @Override
    boolean find(Target target, Collection collection) {
        SearchState searchState = new SearchState(this.ordinal);
        ListIterator listIterator = target.method.instructions.iterator();
        while (listIterator.hasNext()) {
            int n;
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode)listIterator.next();
            if (searchState.isPendingCheck()) {
                n = this.discriminator.findLocal(this.returnType, this.discriminator.isArgsOnly(), target, abstractInsnNode);
                searchState.check(collection, abstractInsnNode, n);
                continue;
            }
            if (!(abstractInsnNode instanceof VarInsnNode) || abstractInsnNode.getOpcode() != this.opcode || this.ordinal != -1 && searchState.success()) continue;
            searchState.register((VarInsnNode)abstractInsnNode);
            if (this.opcodeAfter) {
                searchState.setPendingCheck();
                continue;
            }
            n = this.discriminator.findLocal(this.returnType, this.discriminator.isArgsOnly(), target, abstractInsnNode);
            searchState.check(collection, abstractInsnNode, n);
        }
        return searchState.success();
    }

    @Override
    public boolean find(String string, InsnList insnList, Collection collection) {
        return super.find(string, insnList, collection);
    }

    static class SearchState {
        private final int targetOrdinal;
        private int ordinal = 0;
        private boolean pendingCheck = false;
        private boolean found = false;
        private VarInsnNode varNode;

        SearchState(int n) {
            this.targetOrdinal = n;
        }

        boolean success() {
            return this.found;
        }

        boolean isPendingCheck() {
            return this.pendingCheck;
        }

        void setPendingCheck() {
            this.pendingCheck = true;
        }

        void register(VarInsnNode varInsnNode) {
            this.varNode = varInsnNode;
        }

        void check(Collection collection, AbstractInsnNode abstractInsnNode, int n) {
            this.pendingCheck = false;
            if (n != this.varNode.var) {
                return;
            }
            if (this.targetOrdinal == -1 || this.targetOrdinal == this.ordinal) {
                collection.add(abstractInsnNode);
                this.found = true;
            }
            ++this.ordinal;
            this.varNode = null;
        }
    }
}

