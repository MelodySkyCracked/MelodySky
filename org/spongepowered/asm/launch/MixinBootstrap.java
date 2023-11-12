/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.Launch
 *  net.minecraft.launchwrapper.LaunchClassLoader
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package org.spongepowered.asm.launch;

import java.util.List;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.Blackboard;
import org.spongepowered.asm.launch.platform.MixinPlatformManager;

public abstract class MixinBootstrap {
    public static final String VERSION = "0.6.5";
    private static final String LAUNCH_PACKAGE = "org.spongepowered.asm.launch.";
    private static final String MIXIN_PACKAGE = "org.spongepowered.asm.mixin.";
    private static final String MIXIN_UTIL_PACKAGE = "org.spongepowered.asm.util.";
    private static final String ASM_PACKAGE = "org.spongepowered.asm.lib.";
    private static final String TRANSFORMER_PROXY_CLASS = "org.spongepowered.asm.mixin.transformer.MixinTransformer$Proxy";
    private static final Logger logger = LogManager.getLogger((String)"mixin");
    private static boolean initialised = false;
    private static boolean initState = true;
    private static MixinPlatformManager platform;

    private MixinBootstrap() {
    }

    public static void addProxy() {
        Launch.classLoader.registerTransformer(TRANSFORMER_PROXY_CLASS);
    }

    public static MixinPlatformManager getPlatform() {
        if (platform == null) {
            platform = new MixinPlatformManager();
        }
        return platform;
    }

    /*
     * Exception decompiling
     */
    public static void init() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl1 : IFEQ - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
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

    /*
     * Exception decompiling
     */
    static void doInit(List var0) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl3 : IFNULL - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
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

    static void injectIntoClassLoader(LaunchClassLoader launchClassLoader) {
        MixinBootstrap.getPlatform().injectIntoClassLoader(launchClassLoader);
    }

    private static boolean checkSubsystemVersion() {
        return VERSION.equals(MixinBootstrap.getActiveSubsystemVersion());
    }

    private static Object getActiveSubsystemVersion() {
        Object object = Blackboard.get("mixin.initialised");
        return object != null ? object : "";
    }

    private static void registerSubsystem(String string) {
        Blackboard.put("mixin.initialised", string);
    }

    private static int findInStackTrace(String string, String string2) {
        StackTraceElement[] stackTraceElementArray;
        Thread thread = Thread.currentThread();
        if (!"main".equals(thread.getName())) {
            return 0;
        }
        for (StackTraceElement stackTraceElement : stackTraceElementArray = thread.getStackTrace()) {
            if (!string.equals(stackTraceElement.getClassName()) || !string2.equals(stackTraceElement.getMethodName())) continue;
            return stackTraceElement.getLineNumber();
        }
        return 0;
    }

    static {
        Launch.classLoader.addClassLoaderExclusion(ASM_PACKAGE);
        Launch.classLoader.addClassLoaderExclusion(MIXIN_PACKAGE);
        Launch.classLoader.addClassLoaderExclusion(MIXIN_UTIL_PACKAGE);
        Launch.classLoader.addClassLoaderExclusion(LAUNCH_PACKAGE);
    }
}

