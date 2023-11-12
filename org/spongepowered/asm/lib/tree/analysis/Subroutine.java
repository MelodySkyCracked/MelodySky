/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree.analysis;

import java.util.ArrayList;
import java.util.List;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.analysis.AnalyzerException;

class Subroutine {
    LabelNode start;
    boolean[] access;
    List callers;

    private Subroutine() {
    }

    Subroutine(LabelNode labelNode, int n, JumpInsnNode jumpInsnNode) {
        this.start = labelNode;
        this.access = new boolean[n];
        this.callers = new ArrayList();
        this.callers.add(jumpInsnNode);
    }

    public Subroutine copy() {
        Subroutine subroutine = new Subroutine();
        subroutine.start = this.start;
        subroutine.access = new boolean[this.access.length];
        System.arraycopy(this.access, 0, subroutine.access, 0, this.access.length);
        subroutine.callers = new ArrayList(this.callers);
        return subroutine;
    }

    public boolean merge(Subroutine subroutine) throws AnalyzerException {
        int n;
        boolean bl = false;
        for (n = 0; n < this.access.length; ++n) {
            if (!subroutine.access[n] || this.access[n]) continue;
            this.access[n] = true;
            bl = true;
        }
        if (subroutine.start == this.start) {
            for (n = 0; n < subroutine.callers.size(); ++n) {
                JumpInsnNode jumpInsnNode = (JumpInsnNode)subroutine.callers.get(n);
                if (this.callers.contains(jumpInsnNode)) continue;
                this.callers.add(jumpInsnNode);
                bl = true;
            }
        }
        return bl;
    }
}

