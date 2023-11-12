/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.points.BeforeInvoke;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;

@InjectionPoint.AtCode(value="INVOKE_STRING")
public class BeforeStringInvoke
extends BeforeInvoke {
    private static final String STRING_VOID_SIG = "(Ljava/lang/String;)V";
    private final String ldcValue;
    private boolean foundLdc;

    public BeforeStringInvoke(InjectionPointData injectionPointData) {
        super(injectionPointData);
        this.ldcValue = injectionPointData.get("ldc", null);
        if (this.ldcValue == null) {
            throw new IllegalArgumentException(this.getClass().getSimpleName() + " requires named argument \"ldc\" to specify the desired target");
        }
        if (!STRING_VOID_SIG.equals(this.target.desc)) {
            throw new IllegalArgumentException(this.getClass().getSimpleName() + " requires target method with with signature " + STRING_VOID_SIG);
        }
    }

    @Override
    public boolean find(String string, InsnList insnList, Collection collection) {
        this.foundLdc = false;
        return super.find(string, insnList, collection);
    }

    @Override
    protected void inspectInsn(String string, InsnList insnList, AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode instanceof LdcInsnNode) {
            LdcInsnNode ldcInsnNode = (LdcInsnNode)abstractInsnNode;
            if (ldcInsnNode.cst instanceof String && this.ldcValue.equals(ldcInsnNode.cst)) {
                if (this.logging) {
                    this.logger.info("{} > found a matching LDC with value {}", new Object[]{this.className, ldcInsnNode.cst});
                }
                this.foundLdc = true;
                return;
            }
        }
        this.foundLdc = false;
    }

    protected boolean matchesInsn(MemberInfo memberInfo, int n) {
        if (this.logging) {
            this.logger.info("{} > > found LDC \"{}\" = {}", new Object[]{this.className, this.ldcValue, this.foundLdc});
        }
        return this.foundLdc && super.matchesInsn(memberInfo, n);
    }
}

