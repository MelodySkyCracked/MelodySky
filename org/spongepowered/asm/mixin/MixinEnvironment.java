/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Sets
 *  javax.annotation.Resource
 *  net.minecraft.launchwrapper.IClassNameTransformer
 *  net.minecraft.launchwrapper.IClassTransformer
 *  net.minecraft.launchwrapper.ITweaker
 *  net.minecraft.launchwrapper.Launch
 *  net.minecraft.launchwrapper.LaunchClassLoader
 *  org.apache.logging.log4j.Level
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.apache.logging.log4j.core.Appender
 *  org.apache.logging.log4j.core.Filter
 *  org.apache.logging.log4j.core.Layout
 *  org.apache.logging.log4j.core.LogEvent
 *  org.apache.logging.log4j.core.Logger
 *  org.apache.logging.log4j.core.appender.AbstractAppender
 *  org.apache.logging.log4j.core.helpers.Booleans
 */
package org.spongepowered.asm.mixin;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import net.minecraft.launchwrapper.IClassNameTransformer;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.helpers.Booleans;
import org.spongepowered.asm.launch.Blackboard;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.I;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.extensibility.IEnvironmentTokenProvider;
import org.spongepowered.asm.mixin.l;
import org.spongepowered.asm.mixin.lI;
import org.spongepowered.asm.mixin.lII;
import org.spongepowered.asm.mixin.lIII;
import org.spongepowered.asm.mixin.lIIl;
import org.spongepowered.asm.mixin.lIl;
import org.spongepowered.asm.mixin.lIlI;
import org.spongepowered.asm.mixin.ll;
import org.spongepowered.asm.mixin.llI;
import org.spongepowered.asm.mixin.lll;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.MixinTransformer;
import org.spongepowered.asm.obfuscation.RemapperChain;
import org.spongepowered.asm.util.ITokenProvider;
import org.spongepowered.asm.util.PrettyPrinter;

public final class MixinEnvironment
implements ITokenProvider {
    private static final Set excludeTransformers = Sets.newHashSet((Object[])new String[]{"net.minecraftforge.fml.common.asm.transformers.EventSubscriptionTransformer", "cpw.mods.fml.common.asm.transformers.EventSubscriptionTransformer", "net.minecraftforge.fml.common.asm.transformers.TerminalTransformer", "cpw.mods.fml.common.asm.transformers.TerminalTransformer"});
    private static MixinEnvironment currentEnvironment;
    private static Phase currentPhase;
    private static CompatibilityLevel compatibility;
    private static boolean showHeader;
    private static final Logger logger;
    private final Phase phase;
    private final String configsKey;
    private final boolean[] options;
    private final Set tokenProviderClasses = new HashSet();
    private final List tokenProviders = new ArrayList();
    private final Map internalTokens = new HashMap();
    private final RemapperChain remappers = new RemapperChain();
    private Side side;
    private List transformers;
    private IClassNameTransformer nameTransformer;
    private String obfuscationContext = null;

    MixinEnvironment(Phase phase) {
        this.phase = phase;
        this.configsKey = "mixin.configs." + this.phase.name.toLowerCase();
        String string = this.getVersion();
        if (string == null || !"0.6.5".equals(string)) {
            throw new MixinException("Environment conflict, mismatched versions or you didn't call MixinBootstrap.init()");
        }
        if (this.getClass().getClassLoader() != Launch.class.getClassLoader()) {
            throw new MixinException("Attempted to init the mixin environment in the wrong classloader");
        }
        this.options = new boolean[Option.values().length];
        for (Option option : Option.values()) {
            this.options[option.ordinal()] = option.getBooleanValue();
        }
        if (showHeader) {
            showHeader = false;
            this.printHeader(string);
        }
    }

    private void printHeader(Object object) {
        Side side = this.getSide();
        String string = this.getCodeSource();
        logger.info("SpongePowered MIXIN Subsystem Version={} Source={} Env={}", new Object[]{object, string, side});
        if (this.getOption(Option.DEBUG_VERBOSE)) {
            PrettyPrinter prettyPrinter = new PrettyPrinter(32);
            prettyPrinter.add("SpongePowered MIXIN (Verbose debugging enabled)").centre().hr();
            prettyPrinter.kv("Code source", string);
            prettyPrinter.kv("Internal Version", object);
            prettyPrinter.kv("Java 8 Supported", CompatibilityLevel.JAVA_8.isSupported()).hr();
            for (Option option : Option.values()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < option.depth; ++i) {
                    stringBuilder.append("- ");
                }
                prettyPrinter.kv(option.property, "%s<%s>", new Object[]{stringBuilder, option});
            }
            prettyPrinter.hr().kv("Detected Side", (Object)side);
            prettyPrinter.print(System.err);
        }
    }

    private String getCodeSource() {
        try {
            return this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
        }
        catch (Throwable throwable) {
            return "Unknown";
        }
    }

    public Phase getPhase() {
        return this.phase;
    }

    @Deprecated
    public List getMixinConfigs() {
        ArrayList arrayList = (ArrayList)Blackboard.get(this.configsKey);
        if (arrayList == null) {
            arrayList = new ArrayList();
            Launch.blackboard.put(this.configsKey, arrayList);
        }
        return arrayList;
    }

    @Deprecated
    public MixinEnvironment addConfiguration(String string) {
        logger.warn("MixinEnvironment::addConfiguration is deprecated and will be removed. Use Mixins::addConfiguration instead!");
        Mixins.addConfiguration(string, this);
        return this;
    }

    void registerConfig(String string) {
        List list = this.getMixinConfigs();
        if (!list.contains(string)) {
            list.add(string);
        }
    }

    @Deprecated
    public MixinEnvironment registerErrorHandlerClass(String string) {
        Mixins.registerErrorHandlerClass(string);
        return this;
    }

    public MixinEnvironment registerTokenProviderClass(String string) {
        if (!this.tokenProviderClasses.contains(string)) {
            try {
                Class<?> clazz = Class.forName(string, true, (ClassLoader)Launch.classLoader);
                IEnvironmentTokenProvider iEnvironmentTokenProvider = (IEnvironmentTokenProvider)clazz.newInstance();
                this.registerTokenProvider(iEnvironmentTokenProvider);
            }
            catch (Throwable throwable) {
                logger.error("Error instantiating " + string, throwable);
            }
        }
        return this;
    }

    public MixinEnvironment registerTokenProvider(IEnvironmentTokenProvider iEnvironmentTokenProvider) {
        if (iEnvironmentTokenProvider != null && !this.tokenProviderClasses.contains(iEnvironmentTokenProvider.getClass().getName())) {
            String string = iEnvironmentTokenProvider.getClass().getName();
            TokenProviderWrapper tokenProviderWrapper = new TokenProviderWrapper(iEnvironmentTokenProvider, this);
            logger.info("Adding new token provider {} to {}", new Object[]{string, this});
            this.tokenProviders.add(tokenProviderWrapper);
            this.tokenProviderClasses.add(string);
            Collections.sort(this.tokenProviders);
        }
        return this;
    }

    @Override
    public Integer getToken(String string) {
        string = string.toUpperCase();
        for (TokenProviderWrapper tokenProviderWrapper : this.tokenProviders) {
            Integer n = tokenProviderWrapper.getToken(string);
            if (n == null) continue;
            return n;
        }
        return (Integer)this.internalTokens.get(string);
    }

    @Deprecated
    public Set getErrorHandlerClasses() {
        return Mixins.getErrorHandlerClasses();
    }

    public Object getActiveTransformer() {
        return Blackboard.get("mixin.transformer");
    }

    public void setActiveTransformer(IClassTransformer iClassTransformer) {
        if (iClassTransformer != null) {
            Blackboard.put("mixin.transformer", iClassTransformer);
        }
    }

    public MixinEnvironment setSide(Side side) {
        if (side != null && this.getSide() == Side.UNKNOWN && side != Side.UNKNOWN) {
            this.side = side;
        }
        return this;
    }

    public Side getSide() {
        if (this.side == null) {
            for (Side side : Side.values()) {
                if (!side.detect()) continue;
                this.side = side;
                break;
            }
        }
        return this.side != null ? this.side : Side.UNKNOWN;
    }

    public String getVersion() {
        return (String)Blackboard.get("mixin.initialised");
    }

    public boolean getOption(Option option) {
        return this.options[option.ordinal()];
    }

    public void setOption(Option option, boolean bl) {
        this.options[option.ordinal()] = bl;
    }

    public String getOptionValue(Option option) {
        return option.getStringValue();
    }

    public void setObfuscationContext(String string) {
        this.obfuscationContext = string;
    }

    public String getObfuscationContext() {
        return this.obfuscationContext;
    }

    public String getRefmapObfuscationContext() {
        String string = Option.OBFUSCATION_TYPE.getStringValue();
        if (string != null) {
            return string;
        }
        return this.obfuscationContext;
    }

    public RemapperChain getRemappers() {
        return this.remappers;
    }

    public void audit() {
        Object object = this.getActiveTransformer();
        if (object instanceof MixinTransformer) {
            MixinTransformer mixinTransformer = (MixinTransformer)object;
            mixinTransformer.audit();
        }
    }

    public List getTransformers() {
        if (this.transformers == null) {
            this.buildTransformerDelegationList();
        }
        return Collections.unmodifiableList(this.transformers);
    }

    public void addTransformerExclusion(String string) {
        excludeTransformers.add(string);
        this.transformers = null;
    }

    public String unmap(String string) {
        if (this.transformers == null) {
            this.buildTransformerDelegationList();
        }
        if (this.nameTransformer != null) {
            return this.nameTransformer.unmapClassName(string);
        }
        return string;
    }

    private void buildTransformerDelegationList() {
        logger.debug("Rebuilding transformer delegation list:");
        this.transformers = new ArrayList();
        for (IClassTransformer iClassTransformer : Launch.classLoader.getTransformers()) {
            boolean bl;
            String string = iClassTransformer.getClass().getName();
            boolean bl2 = true;
            for (String string2 : excludeTransformers) {
                if (!string.contains(string2)) continue;
                bl2 = false;
                break;
            }
            boolean bl3 = bl = iClassTransformer.getClass().getAnnotation(Resource.class) != null;
            if (bl2 && !bl && !string.contains(MixinTransformer.class.getName())) {
                logger.debug("  Adding:    {}", new Object[]{string});
                this.transformers.add(iClassTransformer);
                continue;
            }
            logger.debug("  Excluding: {}", new Object[]{string});
        }
        logger.debug("Transformer delegation list created with {} entries", new Object[]{this.transformers.size()});
        for (IClassTransformer iClassTransformer : Launch.classLoader.getTransformers()) {
            if (!(iClassTransformer instanceof IClassNameTransformer)) continue;
            logger.debug("Found name transformer: {}", new Object[]{iClassTransformer.getClass().getName()});
            this.nameTransformer = (IClassNameTransformer)iClassTransformer;
        }
    }

    public String toString() {
        return String.format("%s[%s]", this.getClass().getSimpleName(), this.phase);
    }

    private static Phase getCurrentPhase() {
        if (currentPhase == Phase.NOT_INITIALISED) {
            MixinEnvironment.init(Phase.PREINIT);
        }
        return currentPhase;
    }

    public static void init(Phase phase) {
        if (currentPhase == Phase.NOT_INITIALISED) {
            currentPhase = phase;
            MixinEnvironment.getEnvironment(phase);
            MixinLogger mixinLogger = new MixinLogger();
        }
    }

    public static MixinEnvironment getEnvironment(Phase phase) {
        if (phase == null) {
            return Phase.DEFAULT.getEnvironment();
        }
        return phase.getEnvironment();
    }

    public static MixinEnvironment getDefaultEnvironment() {
        return MixinEnvironment.getEnvironment(Phase.DEFAULT);
    }

    public static MixinEnvironment getCurrentEnvironment() {
        if (currentEnvironment == null) {
            currentEnvironment = MixinEnvironment.getEnvironment(MixinEnvironment.getCurrentPhase());
        }
        return currentEnvironment;
    }

    public static CompatibilityLevel getCompatibilityLevel() {
        return compatibility;
    }

    @Deprecated
    public static void setCompatibilityLevel(CompatibilityLevel compatibilityLevel) throws IllegalArgumentException {
        StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
        if (!"org.spongepowered.asm.mixin.transformer.MixinConfig".equals(stackTraceElementArray[2].getClassName())) {
            logger.warn("MixinEnvironment::setCompatibilityLevel is deprecated and will be removed. Set level via config instead!");
        }
        if (compatibilityLevel != compatibility && compatibilityLevel.isAtLeast(compatibility)) {
            if (!compatibilityLevel.isSupported()) {
                throw new IllegalArgumentException("The requested compatibility level " + (Object)((Object)compatibilityLevel) + " could not be set. Level is not supported");
            }
            compatibility = compatibilityLevel;
            logger.info("Compatibility level set to {}", new Object[]{compatibilityLevel});
        }
    }

    static void gotoPhase(Phase phase) {
        if (phase == null || phase.ordinal < 0) {
            throw new IllegalArgumentException("Cannot go to the specified phase, phase is null or invalid");
        }
        if (phase.ordinal > MixinEnvironment.getCurrentPhase().ordinal) {
            MixinBootstrap.addProxy();
        }
        if (phase == Phase.DEFAULT) {
            org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger)LogManager.getLogger((String)"FML");
            logger.removeAppender((Appender)MixinLogger.appender);
        }
        currentPhase = phase;
        currentEnvironment = MixinEnvironment.getEnvironment(MixinEnvironment.getCurrentPhase());
    }

    static {
        currentPhase = Phase.NOT_INITIALISED;
        compatibility = (CompatibilityLevel)Option.DEFAULT_COMPATIBILITY_LEVEL.getEnumValue(CompatibilityLevel.JAVA_6);
        showHeader = true;
        logger = LogManager.getLogger((String)"mixin");
    }

    static class MixinLogger {
        static MixinAppender appender = new MixinAppender("MixinLogger", null, null);

        public MixinLogger() {
            org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger)LogManager.getLogger((String)"FML");
            appender.start();
            logger.addAppender((Appender)appender);
        }

        static class MixinAppender
        extends AbstractAppender {
            protected MixinAppender(String string, Filter filter, Layout layout) {
                super(string, filter, layout);
            }

            public void append(LogEvent logEvent) {
                if (logEvent.getLevel() == Level.DEBUG && "Validating minecraft".equals(logEvent.getMessage().getFormat())) {
                    MixinEnvironment.gotoPhase(Phase.INIT);
                }
            }
        }
    }

    static class TokenProviderWrapper
    implements Comparable {
        private static int nextOrder = 0;
        private final int priority;
        private final int order;
        private final IEnvironmentTokenProvider provider;
        private final MixinEnvironment environment;

        public TokenProviderWrapper(IEnvironmentTokenProvider iEnvironmentTokenProvider, MixinEnvironment mixinEnvironment) {
            this.provider = iEnvironmentTokenProvider;
            this.environment = mixinEnvironment;
            this.order = nextOrder++;
            this.priority = iEnvironmentTokenProvider.getPriority();
        }

        public int compareTo(TokenProviderWrapper tokenProviderWrapper) {
            if (tokenProviderWrapper == null) {
                return 0;
            }
            if (tokenProviderWrapper.priority == this.priority) {
                return tokenProviderWrapper.order - this.order;
            }
            return tokenProviderWrapper.priority - this.priority;
        }

        public IEnvironmentTokenProvider getProvider() {
            return this.provider;
        }

        Integer getToken(String string) {
            return this.provider.getToken(string, this.environment);
        }

        public int compareTo(Object object) {
            return this.compareTo((TokenProviderWrapper)object);
        }
    }

    public static class EnvironmentStateTweaker
    implements ITweaker {
        public void acceptOptions(List list, File file, File file2, String string) {
        }

        public void injectIntoClassLoader(LaunchClassLoader launchClassLoader) {
            MixinBootstrap.getPlatform().injectIntoClassLoader(launchClassLoader);
        }

        public String getLaunchTarget() {
            return "";
        }

        public String[] getLaunchArguments() {
            MixinEnvironment.gotoPhase(Phase.DEFAULT);
            return new String[0];
        }
    }

    public static class CompatibilityLevel
    extends Enum {
        public static final /* enum */ CompatibilityLevel JAVA_6 = new CompatibilityLevel("JAVA_6", 0, 6, 50, false);
        public static final /* enum */ CompatibilityLevel JAVA_7 = new ll(7, 51, false);
        public static final /* enum */ CompatibilityLevel JAVA_8 = new I(8, 52, true);
        private final int ver;
        private final int classVersion;
        private final boolean supportsMethodsInInterfaces;
        private CompatibilityLevel maxCompatibleLevel;
        private static final CompatibilityLevel[] $VALUES = new CompatibilityLevel[]{JAVA_6, JAVA_7, JAVA_8};

        public static CompatibilityLevel[] values() {
            return (CompatibilityLevel[])$VALUES.clone();
        }

        public static CompatibilityLevel valueOf(String string) {
            return Enum.valueOf(CompatibilityLevel.class, string);
        }

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        private CompatibilityLevel(boolean bl) {
            void var5_3;
            void var4_2;
            void var2_-1;
            void var1_-1;
            this.ver = bl ? 1 : 0;
            this.classVersion = var4_2;
            this.supportsMethodsInInterfaces = var5_3;
        }

        private void setMaxCompatibleLevel(CompatibilityLevel compatibilityLevel) {
            this.maxCompatibleLevel = compatibilityLevel;
        }

        boolean isSupported() {
            return true;
        }

        public int classVersion() {
            return this.classVersion;
        }

        public boolean supportsMethodsInInterfaces() {
            return this.supportsMethodsInInterfaces;
        }

        public boolean isAtLeast(CompatibilityLevel compatibilityLevel) {
            return this.ver >= compatibilityLevel.ver;
        }

        public boolean canElevateTo(CompatibilityLevel compatibilityLevel) {
            if (compatibilityLevel == null || this.maxCompatibleLevel == null) {
                return true;
            }
            return compatibilityLevel.ver <= this.maxCompatibleLevel.ver;
        }

        public boolean canSupport(CompatibilityLevel compatibilityLevel) {
            if (compatibilityLevel == null) {
                return true;
            }
            return compatibilityLevel.canElevateTo(this);
        }

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        CompatibilityLevel(boolean bl, l l2) {
            this((String)var1_-1, n2, bl ? 1 : 0, (int)l2, (boolean)var5_4);
            void var5_4;
            void var1_-1;
        }
    }

    public static class Option
    extends Enum {
        public static final /* enum */ Option DEBUG_ALL = new Option("DEBUG_ALL", 0, "debug");
        public static final /* enum */ Option DEBUG_EXPORT = new Option("DEBUG_EXPORT", 1, DEBUG_ALL, "export");
        public static final /* enum */ Option DEBUG_EXPORT_FILTER = new Option("DEBUG_EXPORT_FILTER", 2, DEBUG_EXPORT, "filter", false);
        public static final /* enum */ Option DEBUG_EXPORT_DECOMPILE = new lIlI(DEBUG_EXPORT, "decompile");
        public static final /* enum */ Option DEBUG_EXPORT_DECOMPILE_THREADED = new lIl(DEBUG_EXPORT_DECOMPILE, "async");
        public static final /* enum */ Option DEBUG_VERIFY = new Option("DEBUG_VERIFY", 5, DEBUG_ALL, "verify");
        public static final /* enum */ Option DEBUG_VERBOSE = new Option("DEBUG_VERBOSE", 6, DEBUG_ALL, "verbose");
        public static final /* enum */ Option DEBUG_INJECTORS = new Option("DEBUG_INJECTORS", 7, DEBUG_ALL, "countInjections");
        public static final /* enum */ Option DEBUG_STRICT = new lll(DEBUG_ALL, "strict");
        public static final /* enum */ Option DEBUG_UNIQUE = new Option("DEBUG_UNIQUE", 9, DEBUG_STRICT, "unique");
        public static final /* enum */ Option DEBUG_TARGETS = new Option("DEBUG_TARGETS", 10, DEBUG_STRICT, "targets");
        public static final /* enum */ Option DUMP_TARGET_ON_FAILURE = new Option("DUMP_TARGET_ON_FAILURE", 11, "dumpTargetOnFailure");
        public static final /* enum */ Option CHECK_ALL = new Option("CHECK_ALL", 12, "checks");
        public static final /* enum */ Option CHECK_IMPLEMENTS = new Option("CHECK_IMPLEMENTS", 13, CHECK_ALL, "interfaces");
        public static final /* enum */ Option CHECK_IMPLEMENTS_STRICT = new lII(CHECK_IMPLEMENTS, "strict");
        public static final /* enum */ Option IGNORE_CONSTRAINTS = new Option("IGNORE_CONSTRAINTS", 15, "ignoreConstraints");
        public static final /* enum */ Option HOT_SWAP = new Option("HOT_SWAP", 16, "hotSwap");
        public static final /* enum */ Option ENVIRONMENT = new lIIl("env");
        public static final /* enum */ Option OBFUSCATION_TYPE = new Option("OBFUSCATION_TYPE", 18, ENVIRONMENT, "obf");
        public static final /* enum */ Option DISABLE_REFMAP = new Option("DISABLE_REFMAP", 19, ENVIRONMENT, "disableRefMap");
        public static final /* enum */ Option DEFAULT_COMPATIBILITY_LEVEL = new Option("DEFAULT_COMPATIBILITY_LEVEL", 20, ENVIRONMENT, "compatLevel");
        public static final /* enum */ Option INITIALISER_INJECTION_MODE = new Option("INITIALISER_INJECTION_MODE", 21, "initialiserInjectionMode", "default");
        private static final String PREFIX = "mixin";
        final Option parent;
        final String property;
        final String defaultValue;
        final boolean flag;
        final int depth;
        private static final Option[] $VALUES = new Option[]{DEBUG_ALL, DEBUG_EXPORT, DEBUG_EXPORT_FILTER, DEBUG_EXPORT_DECOMPILE, DEBUG_EXPORT_DECOMPILE_THREADED, DEBUG_VERIFY, DEBUG_VERBOSE, DEBUG_INJECTORS, DEBUG_STRICT, DEBUG_UNIQUE, DEBUG_TARGETS, DUMP_TARGET_ON_FAILURE, CHECK_ALL, CHECK_IMPLEMENTS, CHECK_IMPLEMENTS_STRICT, IGNORE_CONSTRAINTS, HOT_SWAP, ENVIRONMENT, OBFUSCATION_TYPE, DISABLE_REFMAP, DEFAULT_COMPATIBILITY_LEVEL, INITIALISER_INJECTION_MODE};

        public static Option[] values() {
            return (Option[])$VALUES.clone();
        }

        public static Option valueOf(String string) {
            return Enum.valueOf(Option.class, string);
        }

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        private Option() {
            this((String)var1_-1, (int)var2_-1, null, (String)var3_1, true);
            void var3_1;
            void var2_-1;
            void var1_-1;
        }

        /*
         * WARNING - void declaration
         */
        private Option() {
            this((String)var1_-1, (int)var2_-1, null, (String)var3_1, (boolean)var4_2);
            void var4_2;
            void var3_1;
            void var2_-1;
            void var1_-1;
        }

        /*
         * WARNING - void declaration
         */
        private Option() {
            this((String)var1_-1, (int)var2_-1, null, (String)var3_1, false, (String)var4_2);
            void var4_2;
            void var3_1;
            void var2_-1;
            void var1_-1;
        }

        /*
         * WARNING - void declaration
         */
        private Option() {
            this((String)var1_-1, (int)var2_-1, (Option)var3_1, (String)var4_2, true);
            void var4_2;
            void var3_1;
            void var2_-1;
            void var1_-1;
        }

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        private Option(boolean bl) {
            this((String)var1_-1, (int)var2_-1, (Option)bl, (String)var4_2, (boolean)var5_3, null);
            void var5_3;
            void var4_2;
            void var2_-1;
            void var1_-1;
        }

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        private Option(String string) {
            this((String)var1_-1, (int)var2_-1, (Option)((Object)string), (String)var4_2, false, (String)var5_3);
            void var5_3;
            void var4_2;
            void var2_-1;
            void var1_-1;
        }

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        private Option(boolean object, String string) {
            void var5_3;
            void var6_4;
            void var2_-1;
            void var1_-1;
            this.parent = (Option)((Object)object);
            this.property = (object != null ? object.property : PREFIX) + "." + string;
            this.defaultValue = var6_4;
            this.flag = var5_3;
            int n = 0;
            while (object != null) {
                object = object.parent;
                ++n;
            }
            this.depth = n;
        }

        Option getParent() {
            return this.parent;
        }

        String getProperty() {
            return this.property;
        }

        public String toString() {
            return this.flag ? String.valueOf(this.getBooleanValue()) : this.getStringValue();
        }

        protected boolean getLocalBooleanValue() {
            return Booleans.parseBoolean((String)System.getProperty(this.property), (boolean)false);
        }

        String getStringValue() {
            return this.parent == null || this.parent == false ? System.getProperty(this.property, this.defaultValue) : this.defaultValue;
        }

        Enum getEnumValue(Enum enum_) {
            String string = System.getProperty(this.property, enum_.name());
            try {
                return Enum.valueOf(enum_.getClass(), string.toUpperCase());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return enum_;
            }
        }

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        Option(l l2) {
            this((String)var1_-1, (int)string, (Option)((Object)l2), (String)var4_3);
            void var4_3;
            void var1_-1;
        }

        /*
         * WARNING - void declaration
         */
        Option() {
            this((String)var1_-1, (int)l2, (String)var3_2);
            void var3_2;
            void var1_-1;
        }
    }

    public static abstract class Side
    extends Enum {
        public static final /* enum */ Side UNKNOWN = new lI();
        public static final /* enum */ Side CLIENT = new lIII();
        public static final /* enum */ Side SERVER = new llI();
        private static final Side[] $VALUES = new Side[]{UNKNOWN, CLIENT, SERVER};

        public static Side[] values() {
            return (Side[])$VALUES.clone();
        }

        public static Side valueOf(String string) {
            return Enum.valueOf(Side.class, string);
        }

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        private Side() {
            void var2_-1;
            void var1_-1;
        }

        protected abstract boolean detect();

        protected final String getSideName() {
            for (ITweaker iTweaker : (List)Blackboard.get("Tweaks")) {
                if (iTweaker.getClass().getName().endsWith(".common.launcher.FMLServerTweaker")) {
                    return "SERVER";
                }
                if (!iTweaker.getClass().getName().endsWith(".common.launcher.FMLTweaker")) continue;
                return "CLIENT";
            }
            Object object = this.getSideName("net.minecraftforge.fml.relauncher.FMLLaunchHandler", "side");
            if (object != null) {
                return object;
            }
            object = this.getSideName("cpw.mods.fml.relauncher.FMLLaunchHandler", "side");
            if (object != null) {
                return object;
            }
            object = this.getSideName("com.mumfrey.liteloader.launch.LiteLoaderTweaker", "getEnvironmentType");
            if (object != null) {
                return object;
            }
            return "UNKNOWN";
        }

        private String getSideName(String string, String string2) {
            try {
                Class<?> clazz = Class.forName(string, false, (ClassLoader)Launch.classLoader);
                Method method = clazz.getDeclaredMethod(string2, new Class[0]);
                return ((Enum)method.invoke(null, new Object[0])).name();
            }
            catch (Exception exception) {
                return null;
            }
        }

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        Side() {
            this((String)var1_-1, (int)var2_1);
            void var2_1;
            void var1_-1;
        }
    }

    public static class Phase {
        static final Phase NOT_INITIALISED = new Phase(-1, "NOT_INITIALISED");
        public static final Phase PREINIT = new Phase(0, "PREINIT");
        public static final Phase INIT = new Phase(1, "INIT");
        public static final Phase DEFAULT = new Phase(2, "DEFAULT");
        static final List phases = ImmutableList.of((Object)PREINIT, (Object)INIT, (Object)DEFAULT);
        final int ordinal;
        final String name;
        private MixinEnvironment environment;

        private Phase(int n, String string) {
            this.ordinal = n;
            this.name = string;
        }

        public String toString() {
            return this.name;
        }

        public static Phase forName(String string) {
            for (Phase phase : phases) {
                if (!phase.name.equals(string)) continue;
                return phase;
            }
            return null;
        }

        MixinEnvironment getEnvironment() {
            if (this.ordinal < 0) {
                throw new IllegalArgumentException("Cannot access the NOT_INITIALISED environment");
            }
            if (this.environment == null) {
                this.environment = new MixinEnvironment(this);
            }
            return this.environment;
        }
    }
}

