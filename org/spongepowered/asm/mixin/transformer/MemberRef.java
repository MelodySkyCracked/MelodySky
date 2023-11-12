/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.util.ASMHelper;

public abstract class MemberRef {
    public abstract boolean isField();

    public abstract int getOpcode();

    public abstract String getOwner();

    public abstract void setOwner(String var1);

    public abstract String getName();

    public abstract String getDesc();

    public abstract void setDesc(String var1);

    public String toString() {
        return ASMHelper.getOpcodeName(this.getOpcode()) + " for " + this.getOwner() + "." + this.getName() + (this.isField() ? ":" : "") + this.getDesc();
    }

    public boolean equals(Object object) {
        if (!(object instanceof MemberRef)) {
            return false;
        }
        MemberRef memberRef = (MemberRef)object;
        return this.getOpcode() == memberRef.getOpcode() && this.getOwner().equals(memberRef.getOwner()) && this.getName().equals(memberRef.getName()) && this.getDesc().equals(memberRef.getDesc());
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public static final class Handle
    extends MemberRef {
        private org.spongepowered.asm.lib.Handle handle;

        public Handle(org.spongepowered.asm.lib.Handle handle) {
            this.handle = handle;
        }

        public org.spongepowered.asm.lib.Handle getMethodHandle() {
            return this.handle;
        }

        @Override
        public boolean isField() {
            switch (this.handle.getTag()) {
                case 5: 
                case 6: 
                case 7: 
                case 8: 
                case 9: {
                    return false;
                }
                case 1: 
                case 2: 
                case 3: 
                case 4: {
                    return true;
                }
            }
            throw new MixinTransformerError("Invalid tag " + this.handle.getTag() + " for method handle " + this.handle + ".");
        }

        @Override
        public int getOpcode() {
            switch (this.handle.getTag()) {
                case 5: {
                    return 182;
                }
                case 6: {
                    return 184;
                }
                case 9: {
                    return 185;
                }
                case 7: 
                case 8: {
                    return 183;
                }
                case 1: {
                    return 180;
                }
                case 2: {
                    return 178;
                }
                case 3: {
                    return 181;
                }
                case 4: {
                    return 179;
                }
            }
            throw new MixinTransformerError("Invalid tag " + this.handle.getTag() + " for method handle " + this.handle + ".");
        }

        @Override
        public String getOwner() {
            return this.handle.getOwner();
        }

        @Override
        public void setOwner(String string) {
            this.handle = new org.spongepowered.asm.lib.Handle(this.handle.getTag(), string, this.handle.getName(), this.handle.getDesc());
        }

        @Override
        public String getName() {
            return this.handle.getName();
        }

        @Override
        public String getDesc() {
            return this.handle.getDesc();
        }

        @Override
        public void setDesc(String string) {
            this.handle = new org.spongepowered.asm.lib.Handle(this.handle.getTag(), this.handle.getOwner(), this.handle.getName(), string);
        }
    }

    public static final class Field
    extends MemberRef {
        public final FieldInsnNode insn;

        public Field(FieldInsnNode fieldInsnNode) {
            this.insn = fieldInsnNode;
        }

        @Override
        public boolean isField() {
            return true;
        }

        @Override
        public int getOpcode() {
            return this.insn.getOpcode();
        }

        @Override
        public String getOwner() {
            return this.insn.owner;
        }

        @Override
        public void setOwner(String string) {
            this.insn.owner = string;
        }

        @Override
        public String getName() {
            return this.insn.name;
        }

        @Override
        public String getDesc() {
            return this.insn.desc;
        }

        @Override
        public void setDesc(String string) {
            this.insn.desc = string;
        }
    }

    public static final class Method
    extends MemberRef {
        public final MethodInsnNode insn;

        public Method(MethodInsnNode methodInsnNode) {
            this.insn = methodInsnNode;
        }

        @Override
        public boolean isField() {
            return false;
        }

        @Override
        public int getOpcode() {
            return this.insn.getOpcode();
        }

        @Override
        public String getOwner() {
            return this.insn.owner;
        }

        @Override
        public void setOwner(String string) {
            this.insn.owner = string;
        }

        @Override
        public String getName() {
            return this.insn.name;
        }

        @Override
        public String getDesc() {
            return this.insn.desc;
        }

        @Override
        public void setDesc(String string) {
            this.insn.desc = string;
        }
    }
}

