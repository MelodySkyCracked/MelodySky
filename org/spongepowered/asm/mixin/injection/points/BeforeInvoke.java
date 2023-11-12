/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;

@InjectionPoint.AtCode(value="INVOKE")
public class BeforeInvoke
extends InjectionPoint {
    protected final MemberInfo target;
    protected final int ordinal;
    protected boolean logging = false;
    protected final Logger logger = LogManager.getLogger((String)"mixin");
    protected final String className;

    public BeforeInvoke(InjectionPointData injectionPointData) {
        super(injectionPointData);
        this.target = injectionPointData.getTarget();
        this.ordinal = injectionPointData.getOrdinal();
        this.logging = injectionPointData.get("log", false);
        this.className = this.getClass().getSimpleName();
    }

    public BeforeInvoke setLogging(boolean bl) {
        this.logging = bl;
        return this;
    }

    /*
     * Exception decompiling
     */
    @Override
    public boolean find(String var1, InsnList var2, Collection var3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl26 : ALOAD - null : trying to set 2 previously set to 0
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    protected boolean addInsn(InsnList insnList, Collection collection, AbstractInsnNode abstractInsnNode) {
        collection.add(abstractInsnNode);
        return true;
    }

    protected boolean matchesInsn(AbstractInsnNode abstractInsnNode) {
        return abstractInsnNode instanceof MethodInsnNode;
    }

    protected void inspectInsn(String string, InsnList insnList, AbstractInsnNode abstractInsnNode) {
    }
}

