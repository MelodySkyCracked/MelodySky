/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.IClassTransformer
 *  net.minecraft.launchwrapper.Launch
 *  org.apache.commons.io.FileUtils
 *  org.apache.logging.log4j.Level
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package org.spongepowered.asm.mixin.transformer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;
import java.util.regex.Pattern;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinErrorHandler;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.throwables.ClassAlreadyLoadedException;
import org.spongepowered.asm.mixin.throwables.MixinApplyError;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.throwables.MixinPrepareError;
import org.spongepowered.asm.mixin.transformer.Config;
import org.spongepowered.asm.mixin.transformer.IMixinTransformerModule;
import org.spongepowered.asm.mixin.transformer.MixinConfig;
import org.spongepowered.asm.mixin.transformer.MixinTransformerModuleCheckClass;
import org.spongepowered.asm.mixin.transformer.MixinTransformerModuleInterfaceChecker;
import org.spongepowered.asm.mixin.transformer.TargetClassContext;
import org.spongepowered.asm.mixin.transformer.TreeInfo;
import org.spongepowered.asm.mixin.transformer.debug.IDecompiler;
import org.spongepowered.asm.mixin.transformer.debug.IHotSwap;
import org.spongepowered.asm.mixin.transformer.l;
import org.spongepowered.asm.mixin.transformer.lI;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.transformers.TreeTransformer;
import org.spongepowered.asm.util.PrettyPrinter;

public class MixinTransformer
extends TreeTransformer {
    static final File DEBUG_OUTPUT = new File(".mixin.out");
    private final Logger logger = LogManager.getLogger((String)"mixin");
    private final List configs = new ArrayList();
    private final List pendingConfigs = new ArrayList();
    private final List modules = new ArrayList();
    private MixinEnvironment currentEnvironment;
    private final ReEntranceState lock = new ReEntranceState(this, 1);
    private final String sessionId = UUID.randomUUID().toString();
    private Level verboseLoggingLevel = Level.DEBUG;
    private boolean errorState = false;
    private final File classExportDir = new File(DEBUG_OUTPUT, "class");
    private final IDecompiler decompiler;
    private final IHotSwap hotSwapper;

    MixinTransformer() {
        MixinEnvironment mixinEnvironment = MixinEnvironment.getCurrentEnvironment();
        Object object = mixinEnvironment.getActiveTransformer();
        if (object instanceof IClassTransformer) {
            throw new MixinException("Terminating MixinTransformer instance " + this);
        }
        mixinEnvironment.setActiveTransformer(this);
        TreeInfo.setLock(this.lock);
        this.decompiler = this.initDecompiler(new File(DEBUG_OUTPUT, "java"));
        this.hotSwapper = this.initHotSwapper();
        try {
            FileUtils.deleteDirectory((File)this.classExportDir);
        }
        catch (IOException iOException) {
            this.logger.warn("Error cleaning class output directory: {}", new Object[]{iOException.getMessage()});
        }
    }

    private IDecompiler initDecompiler(File file) {
        MixinEnvironment mixinEnvironment = MixinEnvironment.getCurrentEnvironment();
        if (!mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE)) {
            return null;
        }
        try {
            boolean bl = mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE_THREADED);
            this.logger.info("Attempting to load Fernflower decompiler{}", new Object[]{bl ? " (Threaded mode)" : ""});
            Class<?> clazz = Class.forName("org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler" + (bl ? "Async" : ""));
            Constructor<?> constructor = clazz.getDeclaredConstructor(File.class);
            IDecompiler iDecompiler = (IDecompiler)constructor.newInstance(file);
            this.logger.info("Fernflower decompiler was successfully initialised, exported classes will be decompiled{}", new Object[]{bl ? " in a separate thread" : ""});
            return iDecompiler;
        }
        catch (Throwable throwable) {
            this.logger.info("Fernflower could not be loaded, exported classes will not be decompiled. {}: {}", new Object[]{throwable.getClass().getSimpleName(), throwable.getMessage()});
            return null;
        }
    }

    private IHotSwap initHotSwapper() {
        if (!MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.HOT_SWAP)) {
            return null;
        }
        try {
            this.logger.info("Attempting to load Hot-Swap agent");
            Class<?> clazz = Class.forName("org.spongepowered.tools.agent.MixinAgent");
            Constructor<?> constructor = clazz.getDeclaredConstructor(MixinTransformer.class);
            return (IHotSwap)constructor.newInstance(this);
        }
        catch (Throwable throwable) {
            this.logger.info("Hot-swap agent could not be loaded, hot swapping of mixins won't work. {}: {}", new Object[]{throwable.getClass().getSimpleName(), throwable.getMessage()});
            return null;
        }
    }

    public void audit() {
        HashSet hashSet = new HashSet();
        for (Iterator iterator : this.configs) {
            hashSet.addAll(((MixinConfig)((Object)iterator)).getUnhandledTargets());
        }
        Logger logger = LogManager.getLogger((String)"mixin/audit");
        for (Object object : hashSet) {
            try {
                logger.info("Force-loading class {}", new Object[]{object});
                Class.forName((String)object, true, (ClassLoader)Launch.classLoader);
            }
            catch (ClassNotFoundException classNotFoundException) {
                logger.error("Could not force-load " + (String)object, (Throwable)classNotFoundException);
            }
        }
        for (Object object : this.configs) {
            for (String string : ((MixinConfig)object).getUnhandledTargets()) {
                ClassAlreadyLoadedException classAlreadyLoadedException = new ClassAlreadyLoadedException(string + " was already classloaded");
                logger.error("Could not force-load " + string, (Throwable)classAlreadyLoadedException);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public synchronized byte[] transform(String string, String string2, byte[] byArray) {
        Object object;
        if (byArray == null || string2 == null || this.errorState) {
            return byArray;
        }
        boolean bl = this.lock.push().check();
        MixinEnvironment mixinEnvironment = MixinEnvironment.getCurrentEnvironment();
        if (this.currentEnvironment != mixinEnvironment && !bl) {
            try {
                this.select(mixinEnvironment);
            }
            catch (Exception exception) {
                this.lock.pop();
                throw new MixinException(exception);
            }
        }
        try {
            TreeSet treeSet = null;
            boolean bl2 = false;
            for (Object object2 : this.configs) {
                if (((MixinConfig)object2).packageMatch(string2)) {
                    if (((MixinConfig)object2).canPassThrough(string2)) {
                        byte[] byArray2 = this.passThrough((MixinConfig)object2, string, string2, byArray);
                        this.lock.pop();
                        return byArray2;
                    } else {
                        bl2 = true;
                        continue;
                    }
                }
                if (!((MixinConfig)object2).hasMixinsFor(string2)) continue;
                if (treeSet == null) {
                    treeSet = new TreeSet();
                }
                treeSet.addAll(((MixinConfig)object2).getMixinsFor(string2));
            }
            if (bl2) {
                throw new NoClassDefFoundError(String.format("%s is a mixin class and cannot be referenced directly", string2));
            }
            if (treeSet != null) {
                if (bl) {
                    this.logger.warn("Re-entrance detected, this will cause serious problems.", (Throwable)new MixinException());
                    throw new MixinApplyError("Re-entrance error.");
                }
                if (this.hotSwapper != null) {
                    this.hotSwapper.registerTargetClass(string2, byArray);
                }
                try {
                    Object object2;
                    object = this.readClass(byArray, true);
                    object2 = new TargetClassContext(this.sessionId, string2, (ClassNode)object, treeSet);
                    byArray = this.applyMixins((TargetClassContext)object2);
                }
                catch (InvalidMixinException invalidMixinException) {
                    this.dumpClassOnFailure(string2, byArray, mixinEnvironment);
                    this.handleMixinApplyError(string2, invalidMixinException, mixinEnvironment);
                }
            }
            object = byArray;
            this.lock.pop();
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            this.dumpClassOnFailure(string2, byArray, mixinEnvironment);
            throw new MixinTransformerError("An unexpected critical error was encountered", throwable);
        }
        return object;
    }

    public List reload(String string, byte[] byArray) {
        if (this.lock.getDepth() > 0) {
            throw new MixinApplyError("Cannot reload mixin if re-entrant lock entered");
        }
        ArrayList arrayList = new ArrayList();
        for (MixinConfig mixinConfig : this.configs) {
            arrayList.addAll(mixinConfig.reloadMixin(string, byArray));
        }
        return arrayList;
    }

    private void select(MixinEnvironment mixinEnvironment) {
        this.verboseLoggingLevel = mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_VERBOSE) ? Level.INFO : Level.DEBUG;
        this.logger.log(this.verboseLoggingLevel, "Preparing mixins for {}", new Object[]{mixinEnvironment});
        long l2 = System.currentTimeMillis();
        this.selectConfigs(mixinEnvironment);
        this.selectModules(mixinEnvironment);
        int n = this.prepareConfigs(mixinEnvironment);
        this.currentEnvironment = mixinEnvironment;
        double d = (double)(System.currentTimeMillis() - l2) * 0.001;
        if (d > 0.25) {
            String string = new DecimalFormat("###0.000").format(d);
            String string2 = new DecimalFormat("###0.0").format(d / (double)n * 1000.0);
            this.logger.log(this.verboseLoggingLevel, "Prepared {} mixins in {} sec ({} msec avg.)", new Object[]{n, string, string2});
        }
    }

    private void selectConfigs(MixinEnvironment mixinEnvironment) {
        Iterator iterator = Mixins.getConfigs().iterator();
        while (iterator.hasNext()) {
            Config config = (Config)iterator.next();
            try {
                MixinConfig mixinConfig = config.get();
                if (!mixinConfig.select(mixinEnvironment)) continue;
                iterator.remove();
                this.logger.log(this.verboseLoggingLevel, "Selecting config {}", new Object[]{mixinConfig});
                mixinConfig.onSelect();
                this.pendingConfigs.add(mixinConfig);
            }
            catch (Exception exception) {
                this.logger.warn(String.format("Failed to select mixin config: %s", config), (Throwable)exception);
            }
        }
        Collections.sort(this.pendingConfigs);
    }

    private void selectModules(MixinEnvironment mixinEnvironment) {
        this.modules.clear();
        if (mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
            this.modules.add(new MixinTransformerModuleCheckClass());
        }
        if (mixinEnvironment.getOption(MixinEnvironment.Option.CHECK_IMPLEMENTS)) {
            this.modules.add(new MixinTransformerModuleInterfaceChecker());
        }
    }

    private int prepareConfigs(MixinEnvironment mixinEnvironment) {
        int n = 0;
        for (MixinConfig mixinConfig : this.pendingConfigs) {
            try {
                this.logger.log(this.verboseLoggingLevel, "Preparing {} ({})", new Object[]{mixinConfig, mixinConfig.getDeclaredMixinCount()});
                mixinConfig.prepare(this.hotSwapper);
                n += mixinConfig.getMixinCount();
            }
            catch (InvalidMixinException invalidMixinException) {
                this.handleMixinPrepareError(mixinConfig, invalidMixinException, mixinEnvironment);
            }
            catch (Exception exception) {
                this.logger.error("Error encountered whilst initialising mixin config '" + mixinConfig.getName() + "': " + exception.getMessage(), (Throwable)exception);
            }
        }
        for (MixinConfig mixinConfig : this.pendingConfigs) {
            IMixinConfigPlugin iMixinConfigPlugin = mixinConfig.getPlugin();
            if (iMixinConfigPlugin == null) continue;
            HashSet hashSet = new HashSet();
            for (MixinConfig mixinConfig2 : this.pendingConfigs) {
                if (mixinConfig2.equals(mixinConfig)) continue;
                hashSet.addAll(mixinConfig2.getTargets());
            }
            iMixinConfigPlugin.acceptTargets(mixinConfig.getTargets(), Collections.unmodifiableSet(hashSet));
        }
        for (MixinConfig mixinConfig : this.pendingConfigs) {
            try {
                mixinConfig.postInitialise(this.hotSwapper);
            }
            catch (InvalidMixinException invalidMixinException) {
                this.handleMixinPrepareError(mixinConfig, invalidMixinException, mixinEnvironment);
            }
            catch (Exception exception) {
                this.logger.error("Error encountered during mixin config postInit step'" + mixinConfig.getName() + "': " + exception.getMessage(), (Throwable)exception);
            }
        }
        this.configs.addAll(this.pendingConfigs);
        Collections.sort(this.configs);
        this.pendingConfigs.clear();
        return n;
    }

    private byte[] passThrough(MixinConfig mixinConfig, String string, String string2, byte[] byArray) {
        ClassNode classNode = mixinConfig.passThrough(string2, this.readClass(byArray, true));
        return this.writeClass(string2, classNode, false);
    }

    private byte[] applyMixins(TargetClassContext targetClassContext) {
        block2: {
            this.preApply(targetClassContext);
            this.apply(targetClassContext);
            try {
                this.postApply(targetClassContext);
            }
            catch (MixinTransformerModuleCheckClass.ValidationFailedException validationFailedException) {
                this.logger.info(validationFailedException.getMessage());
                if (!targetClassContext.isExportForced() && !MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_EXPORT)) break block2;
                this.writeClass(targetClassContext);
            }
        }
        return this.writeClass(targetClassContext);
    }

    private void preApply(TargetClassContext targetClassContext) {
        for (IMixinTransformerModule iMixinTransformerModule : this.modules) {
            iMixinTransformerModule.preApply(targetClassContext);
        }
    }

    private void apply(TargetClassContext targetClassContext) {
        targetClassContext.applyMixins();
    }

    private void postApply(TargetClassContext targetClassContext) {
        for (IMixinTransformerModule iMixinTransformerModule : this.modules) {
            iMixinTransformerModule.postApply(targetClassContext);
        }
    }

    private void handleMixinPrepareError(MixinConfig mixinConfig, InvalidMixinException invalidMixinException, MixinEnvironment mixinEnvironment) throws MixinPrepareError {
        this.handleMixinError(mixinConfig.getName(), invalidMixinException, mixinEnvironment, ErrorPhase.PREPARE);
    }

    private void handleMixinApplyError(String string, InvalidMixinException invalidMixinException, MixinEnvironment mixinEnvironment) throws MixinApplyError {
        this.handleMixinError(string, invalidMixinException, mixinEnvironment, ErrorPhase.APPLY);
    }

    private void handleMixinError(String string, InvalidMixinException invalidMixinException, MixinEnvironment mixinEnvironment, ErrorPhase errorPhase) throws Error {
        IMixinErrorHandler.ErrorAction errorAction;
        this.errorState = true;
        IMixinInfo iMixinInfo = invalidMixinException.getMixin();
        if (iMixinInfo == null) {
            this.logger.error("InvalidMixinException has no mixin!", (Throwable)invalidMixinException);
            throw invalidMixinException;
        }
        IMixinConfig iMixinConfig = iMixinInfo.getConfig();
        MixinEnvironment.Phase phase = iMixinInfo.getPhase();
        IMixinErrorHandler.ErrorAction errorAction2 = errorAction = iMixinConfig.isRequired() ? IMixinErrorHandler.ErrorAction.ERROR : IMixinErrorHandler.ErrorAction.WARN;
        if (mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
            new PrettyPrinter().add("Invalid Mixin").centre().hr('-').kvWidth(10).kv("Action", errorPhase.name()).kv("Mixin", iMixinInfo.getClassName()).kv("Config", iMixinConfig.getName()).kv("Phase", phase).hr('-').add("    %s", invalidMixinException.getClass().getName()).hr('-').addWrapped("    %s", invalidMixinException.getMessage()).hr('-').add(invalidMixinException.getStackTrace(), 8).trace(errorAction.logLevel);
        }
        for (IMixinErrorHandler iMixinErrorHandler : this.getErrorHandlers(iMixinInfo.getPhase())) {
            IMixinErrorHandler.ErrorAction errorAction3 = errorPhase.onError(iMixinErrorHandler, string, invalidMixinException, iMixinInfo, errorAction);
            if (errorAction3 == null) continue;
            errorAction = errorAction3;
        }
        this.logger.log(errorAction.logLevel, errorPhase.getLogMessage(string, invalidMixinException, iMixinInfo), (Throwable)invalidMixinException);
        this.errorState = false;
        if (errorAction == IMixinErrorHandler.ErrorAction.ERROR) {
            throw new MixinApplyError(errorPhase.getErrorMessage(iMixinInfo, iMixinConfig, phase), invalidMixinException);
        }
    }

    private List getErrorHandlers(MixinEnvironment.Phase phase) {
        ArrayList<IMixinErrorHandler> arrayList = new ArrayList<IMixinErrorHandler>();
        for (String string : Mixins.getErrorHandlerClasses()) {
            try {
                this.logger.info("Instancing error handler class {}", new Object[]{string});
                Class<?> clazz = Class.forName(string, true, (ClassLoader)Launch.classLoader);
                IMixinErrorHandler iMixinErrorHandler = (IMixinErrorHandler)clazz.newInstance();
                if (iMixinErrorHandler == null) continue;
                arrayList.add(iMixinErrorHandler);
            }
            catch (Throwable throwable) {}
        }
        return arrayList;
    }

    private String prepareFilter(String string) {
        string = "^\\Q" + string.replace("**", "\u0081").replace("*", "\u0082").replace("?", "\u0083") + "\\E$";
        return string.replace("\u0081", "\\E.*\\Q").replace("\u0082", "\\E[^\\.]+\\Q").replace("\u0083", "\\E.\\Q").replace("\\Q\\E", "");
    }

    private boolean applyFilter(String string, String string2) {
        return Pattern.compile(this.prepareFilter(string), 2).matcher(string2).matches();
    }

    private byte[] writeClass(TargetClassContext targetClassContext) {
        return this.writeClass(targetClassContext.getClassName(), targetClassContext.getClassNode(), targetClassContext.isExportForced());
    }

    private byte[] writeClass(String string, ClassNode classNode, boolean bl) {
        byte[] byArray = this.writeClass(classNode);
        MixinEnvironment mixinEnvironment = MixinEnvironment.getCurrentEnvironment();
        if (bl || mixinEnvironment.getOption(MixinEnvironment.Option.DEBUG_EXPORT)) {
            String string2 = mixinEnvironment.getOptionValue(MixinEnvironment.Option.DEBUG_EXPORT_FILTER);
            if (bl || string2 == null || this.applyFilter(string2, string)) {
                File file = this.dumpClass(string.replace('.', '/'), byArray);
                if (this.decompiler != null) {
                    this.decompiler.decompile(file);
                }
            }
        }
        return byArray;
    }

    private void dumpClassOnFailure(String string, byte[] byArray, MixinEnvironment mixinEnvironment) {
        if (mixinEnvironment.getOption(MixinEnvironment.Option.DUMP_TARGET_ON_FAILURE)) {
            this.dumpClass(string.replace('.', '/') + ".target", byArray);
        }
    }

    private File dumpClass(String string, byte[] byArray) {
        File file = new File(this.classExportDir, string + ".class");
        try {
            FileUtils.writeByteArrayToFile((File)file, (byte[])byArray);
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return file;
    }

    class ReEntranceState {
        private final int maxDepth;
        private int depth;
        private boolean semaphore;
        final MixinTransformer this$0;

        public ReEntranceState(MixinTransformer mixinTransformer, int n) {
            this.this$0 = mixinTransformer;
            this.depth = 0;
            this.semaphore = false;
            this.maxDepth = n;
        }

        public int getMaxDepth() {
            return this.maxDepth;
        }

        public int getDepth() {
            return this.depth;
        }

        ReEntranceState push() {
            ++this.depth;
            this.checkAndSet();
            return this;
        }

        ReEntranceState pop() {
            if (this.depth == 0) {
                throw new IllegalStateException("ReEntranceState pop() with zero depth");
            }
            --this.depth;
            return this;
        }

        boolean check() {
            return this.depth > this.maxDepth;
        }

        boolean checkAndSet() {
            return this.semaphore |= this.check();
        }

        ReEntranceState set() {
            this.semaphore = true;
            return this;
        }

        boolean isSet() {
            return this.semaphore;
        }

        ReEntranceState clear() {
            this.semaphore = false;
            return this;
        }
    }

    public static class Proxy
    implements IClassTransformer {
        private static List proxies = new ArrayList();
        private static MixinTransformer transformer = new MixinTransformer();
        private boolean isActive = true;

        public Proxy() {
            for (Proxy proxy : proxies) {
                proxy.isActive = false;
            }
            proxies.add(this);
            LogManager.getLogger((String)"mixin").debug("Adding new mixin transformer proxy #{}", new Object[]{proxies.size()});
        }

        public byte[] transform(String string, String string2, byte[] byArray) {
            if (this.isActive) {
                return transformer.transform(string, string2, byArray);
            }
            return byArray;
        }
    }

    static abstract class ErrorPhase
    extends Enum {
        public static final /* enum */ ErrorPhase PREPARE = new l();
        public static final /* enum */ ErrorPhase APPLY = new lI();
        private final String text = this.name().toLowerCase();
        private static final ErrorPhase[] $VALUES = new ErrorPhase[]{PREPARE, APPLY};

        public static ErrorPhase[] values() {
            return (ErrorPhase[])$VALUES.clone();
        }

        public static ErrorPhase valueOf(String string) {
            return Enum.valueOf(ErrorPhase.class, string);
        }

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        private ErrorPhase() {
            void var2_-1;
            void var1_-1;
        }

        abstract IMixinErrorHandler.ErrorAction onError(IMixinErrorHandler var1, String var2, InvalidMixinException var3, IMixinInfo var4, IMixinErrorHandler.ErrorAction var5);

        protected abstract String getContext(IMixinInfo var1, String var2);

        public String getLogMessage(String string, InvalidMixinException invalidMixinException, IMixinInfo iMixinInfo) {
            return String.format("Mixin %s failed %s: %s %s", this.text, this.getContext(iMixinInfo, string), invalidMixinException.getClass().getName(), invalidMixinException.getMessage());
        }

        public String getErrorMessage(IMixinInfo iMixinInfo, IMixinConfig iMixinConfig, MixinEnvironment.Phase phase) {
            return String.format("Mixin [%s] from phase [%s] in config [%s] FAILED during %s", iMixinInfo, phase, iMixinConfig, this.name());
        }

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        ErrorPhase() {
            this((String)var1_-1, (int)var2_1);
            void var2_1;
            void var1_-1;
        }
    }
}

