/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package org.spongepowered.tools.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.transformer.MixinTransformer;
import org.spongepowered.asm.mixin.transformer.debug.IHotSwap;
import org.spongepowered.asm.mixin.transformer.throwables.MixinReloadException;
import org.spongepowered.tools.agent.MixinAgentClassLoader;

public class MixinAgent
implements IHotSwap {
    public static final byte[] ERROR_BYTECODE = new byte[]{1};
    static final MixinAgentClassLoader classLoader = new MixinAgentClassLoader();
    static final Logger logger = LogManager.getLogger((String)"mixin.agent");
    static Instrumentation instrumentation = null;
    private static List agents = new ArrayList();
    final MixinTransformer classTransformer;

    public MixinAgent(MixinTransformer mixinTransformer) {
        this.classTransformer = mixinTransformer;
        agents.add(this);
        if (instrumentation != null) {
            this.initTransformer();
        }
    }

    private void initTransformer() {
        instrumentation.addTransformer(new Transformer(this), true);
    }

    @Override
    public void registerMixinClass(String string) {
        classLoader.addMixinClass(string);
    }

    @Override
    public void registerTargetClass(String string, byte[] byArray) {
        classLoader.addTargetClass(string, byArray);
    }

    public static void init(Instrumentation instrumentation) {
        MixinAgent.instrumentation = instrumentation;
        if (!MixinAgent.instrumentation.isRedefineClassesSupported()) {
            logger.error("The instrumentation doesn't support re-definition of classes");
        }
        for (MixinAgent mixinAgent : agents) {
            mixinAgent.initTransformer();
        }
    }

    public static void premain(String string, Instrumentation instrumentation) {
        System.setProperty("mixin.hotSwap", "true");
        MixinAgent.init(instrumentation);
    }

    public static void agentmain(String string, Instrumentation instrumentation) {
        MixinAgent.init(instrumentation);
    }

    class Transformer
    implements ClassFileTransformer {
        final MixinAgent this$0;

        Transformer(MixinAgent mixinAgent) {
            this.this$0 = mixinAgent;
        }

        /*
         * Exception decompiling
         */
        public byte[] transform(ClassLoader var1, String var2, Class var3, ProtectionDomain var4, byte[] var5) throws IllegalClassFormatException {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl21 : GETSTATIC - null : trying to set 0 previously set to 1
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
             *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
             *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
             *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
             *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:923)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1035)
             *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
             *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
             *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
             *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
             *     at org.benf.cfr.reader.Main.main(Main.java:54)
             */
            throw new IllegalStateException("Decompilation failed");
        }

        private List reloadMixin(String string, byte[] byArray) {
            logger.info("Redefining mixin {}", new Object[]{string});
            try {
                return this.this$0.classTransformer.reload(string.replace('/', '.'), byArray);
            }
            catch (MixinReloadException mixinReloadException) {
                logger.error("Mixin {} cannot be reloaded, needs a restart to be applied: {} ", new Object[]{mixinReloadException.getMixinInfo(), mixinReloadException.getMessage()});
            }
            catch (Throwable throwable) {
                logger.error("Error while finding targets for mixin " + string, throwable);
            }
            return null;
        }
    }
}

