/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.annotations.SerializedName
 *  net.minecraft.launchwrapper.Launch
 *  org.apache.logging.log4j.Level
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package org.spongepowered.asm.mixin.transformer;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.launchwrapper.Launch;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinInitialisationError;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
import org.spongepowered.asm.mixin.transformer.Config;
import org.spongepowered.asm.mixin.transformer.MixinInfo;
import org.spongepowered.asm.mixin.transformer.debug.IHotSwap;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
import org.spongepowered.asm.util.VersionNumber;

class MixinConfig
implements Comparable,
IMixinConfig {
    private static int configOrder = 0;
    private static final Set globalMixinList = new HashSet();
    private final Logger logger = LogManager.getLogger((String)"mixin");
    private final transient Map mixinMapping = new HashMap();
    private final transient Set unhandledTargets = new HashSet();
    private final transient List mixins = new ArrayList();
    private final transient Set syntheticInnerClasses = new HashSet();
    private final transient Set passThroughClasses = new HashSet();
    private transient Config handle;
    @SerializedName(value="target")
    private String selector;
    @SerializedName(value="minVersion")
    private String version;
    @SerializedName(value="compatibilityLevel")
    private String compatibility;
    @SerializedName(value="required")
    private boolean required;
    @SerializedName(value="priority")
    private int priority = 1000;
    @SerializedName(value="mixinPriority")
    private int mixinPriority = 1000;
    @SerializedName(value="package")
    private String mixinPackage;
    @SerializedName(value="mixins")
    private List mixinClasses;
    @SerializedName(value="client")
    private List mixinClassesClient;
    @SerializedName(value="server")
    private List mixinClassesServer;
    @SerializedName(value="setSourceFile")
    private boolean setSourceFile = false;
    @SerializedName(value="refmap")
    private String refMapperConfig;
    @SerializedName(value="verbose")
    private boolean verboseLogging;
    private final transient int order = configOrder++;
    private transient MixinEnvironment env;
    private transient String name;
    @SerializedName(value="plugin")
    private String pluginClassName;
    @SerializedName(value="injectors")
    private InjectorOptions injectorOptions = new InjectorOptions();
    private transient IMixinConfigPlugin plugin;
    private transient ReferenceMapper refMapper;
    private transient boolean prepared = false;

    private MixinConfig() {
    }

    private boolean onLoad(String string, MixinEnvironment mixinEnvironment) {
        this.name = string;
        this.env = this.parseSelector(this.selector, mixinEnvironment);
        this.initCompatibilityLevel();
        return this.checkVersion();
    }

    private void initCompatibilityLevel() {
        MixinEnvironment.CompatibilityLevel compatibilityLevel;
        if (this.compatibility == null) {
            return;
        }
        MixinEnvironment.CompatibilityLevel compatibilityLevel2 = MixinEnvironment.CompatibilityLevel.valueOf(this.compatibility.trim().toUpperCase());
        if (compatibilityLevel2 == (compatibilityLevel = MixinEnvironment.getCompatibilityLevel())) {
            return;
        }
        if (compatibilityLevel.isAtLeast(compatibilityLevel2) && !compatibilityLevel.canSupport(compatibilityLevel2)) {
            throw new MixinInitialisationError("Mixin config " + this.name + " requires compatibility level " + (Object)((Object)compatibilityLevel2) + " which is too old");
        }
        if (!compatibilityLevel.canElevateTo(compatibilityLevel2)) {
            throw new MixinInitialisationError("Mixin config " + this.name + " requires compatibility level " + (Object)((Object)compatibilityLevel2) + " which is prohibited by " + (Object)((Object)compatibilityLevel));
        }
        MixinEnvironment.setCompatibilityLevel(compatibilityLevel2);
    }

    private MixinEnvironment parseSelector(String string, MixinEnvironment mixinEnvironment) {
        if (string != null) {
            String[] stringArray;
            for (String string2 : stringArray = string.split("[&\\| ]")) {
                string2 = string2.trim();
                Pattern pattern = Pattern.compile("^@env(?:ironment)?\\(([A-Z]+)\\)$");
                Matcher matcher = pattern.matcher(string2);
                if (!matcher.matches()) continue;
                return MixinEnvironment.getEnvironment(MixinEnvironment.Phase.forName(matcher.group(1)));
            }
            MixinEnvironment.Phase phase = MixinEnvironment.Phase.forName(string);
            if (phase != null) {
                return MixinEnvironment.getEnvironment(phase);
            }
        }
        return mixinEnvironment;
    }

    private boolean checkVersion() throws MixinInitialisationError {
        VersionNumber versionNumber;
        VersionNumber versionNumber2 = VersionNumber.parse(this.version);
        if (versionNumber2.compareTo(versionNumber = VersionNumber.parse(this.env.getVersion())) > 0) {
            this.logger.warn("Mixin config {} requires mixin subsystem version {} but {} was found. The mixin config will not be applied.", new Object[]{this.name, versionNumber2, versionNumber});
            if (this.required) {
                throw new MixinInitialisationError("Required mixin config " + this.name + " requires mixin subsystem version " + versionNumber2);
            }
            return false;
        }
        return true;
    }

    void onSelect() {
        if (this.pluginClassName != null) {
            try {
                Class<?> clazz = Class.forName(this.pluginClassName, true, (ClassLoader)Launch.classLoader);
                this.plugin = (IMixinConfigPlugin)clazz.newInstance();
                if (this.plugin != null) {
                    this.plugin.onLoad(this.mixinPackage);
                }
            }
            catch (Throwable throwable) {
                throwable.printStackTrace();
                this.plugin = null;
            }
        }
        if (!this.mixinPackage.endsWith(".")) {
            this.mixinPackage = this.mixinPackage + ".";
        }
        if (this.refMapperConfig == null) {
            if (this.plugin != null) {
                this.refMapperConfig = this.plugin.getRefMapperConfig();
            }
            if (this.refMapperConfig == null) {
                this.refMapperConfig = "mixin.refmap.json";
            }
        }
        this.refMapper = ReferenceMapper.read(this.refMapperConfig);
        this.verboseLogging |= this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
    }

    void prepare(IHotSwap iHotSwap) {
        if (this.prepared) {
            return;
        }
        this.prepared = true;
        this.prepareMixins(this.mixinClasses, false, iHotSwap);
        switch (this.env.getSide()) {
            case CLIENT: {
                this.prepareMixins(this.mixinClassesClient, false, iHotSwap);
                break;
            }
            case SERVER: {
                this.prepareMixins(this.mixinClassesServer, false, iHotSwap);
                break;
            }
            default: {
                this.logger.warn("Mixin environment was unable to detect the current side, sided mixins will not be applied");
            }
        }
    }

    void postInitialise(IHotSwap iHotSwap) {
        Object object;
        if (this.plugin != null) {
            object = this.plugin.getMixins();
            this.prepareMixins((List)object, true, iHotSwap);
        }
        object = this.mixins.iterator();
        while (object.hasNext()) {
            MixinInfo mixinInfo = (MixinInfo)object.next();
            try {
                mixinInfo.validate();
                for (String string : mixinInfo.getSyntheticInnerClasses()) {
                    this.syntheticInnerClasses.add(string.replace('/', '.'));
                }
            }
            catch (InvalidMixinException invalidMixinException) {
                this.logger.error(invalidMixinException.getMixin() + ": " + invalidMixinException.getMessage(), (Throwable)invalidMixinException);
                this.removeMixin(mixinInfo);
                object.remove();
            }
            catch (Exception exception) {
                this.logger.error(exception.getMessage(), (Throwable)exception);
                this.removeMixin(mixinInfo);
                object.remove();
            }
        }
    }

    private void removeMixin(MixinInfo mixinInfo) {
        for (List list : this.mixinMapping.values()) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                if (mixinInfo != iterator.next()) continue;
                iterator.remove();
            }
        }
    }

    private void prepareMixins(List list, boolean bl, IHotSwap iHotSwap) {
        if (list == null) {
            return;
        }
        for (String string : list) {
            String string2 = this.mixinPackage + string;
            if (string == null || globalMixinList.contains(string2)) continue;
            MixinInfo mixinInfo = null;
            try {
                mixinInfo = new MixinInfo(this, string, true, this.plugin, bl);
                if (mixinInfo.getTargetClasses().size() <= 0) continue;
                globalMixinList.add(string2);
                for (String string3 : mixinInfo.getTargetClasses()) {
                    String string4 = string3.replace('/', '.');
                    this.mixinsFor(string4).add(mixinInfo);
                    this.unhandledTargets.add(string4);
                }
                if (iHotSwap != null) {
                    iHotSwap.registerMixinClass(mixinInfo.getClassName());
                }
                if (mixinInfo.isLoadable()) {
                    this.passThroughClasses.add(mixinInfo.getClassName());
                }
                this.mixins.add(mixinInfo);
            }
            catch (InvalidMixinException invalidMixinException) {
                if (this.required) {
                    throw invalidMixinException;
                }
                this.logger.error(invalidMixinException.getMessage(), (Throwable)invalidMixinException);
            }
            catch (Exception exception) {
                if (this.required) {
                    throw new InvalidMixinException(mixinInfo, "Error initialising mixin " + mixinInfo + " - " + exception.getClass() + ": " + exception.getMessage(), (Throwable)exception);
                }
                this.logger.error(exception.getMessage(), (Throwable)exception);
            }
        }
    }

    void postApply(String string, ClassNode classNode) {
        this.unhandledTargets.remove(string);
    }

    public Config getHandle() {
        if (this.handle == null) {
            this.handle = new Config(this);
        }
        return this.handle;
    }

    @Override
    public boolean isRequired() {
        return this.required;
    }

    @Override
    public MixinEnvironment getEnvironment() {
        return this.env;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getMixinPackage() {
        return this.mixinPackage;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    public int getDefaultMixinPriority() {
        return this.mixinPriority;
    }

    public int getDefaultRequiredInjections() {
        return this.injectorOptions.defaultRequireValue;
    }

    public String getDefaultInjectorGroup() {
        String string = this.injectorOptions.defaultGroup;
        return string != null && !string.isEmpty() ? string : "default";
    }

    public boolean select(MixinEnvironment mixinEnvironment) {
        return this.env == mixinEnvironment;
    }

    int getDeclaredMixinCount() {
        return MixinConfig.getCollectionSize(this.mixinClasses, this.mixinClassesClient, this.mixinClassesServer);
    }

    int getMixinCount() {
        return this.mixins.size();
    }

    public List getClasses() {
        return Collections.unmodifiableList(this.mixinClasses);
    }

    public boolean shouldSetSourceFile() {
        return this.setSourceFile;
    }

    public ReferenceMapper getReferenceMapper() {
        if (this.env.getOption(MixinEnvironment.Option.DISABLE_REFMAP)) {
            return ReferenceMapper.DEFAULT_MAPPER;
        }
        this.refMapper.setContext(this.env.getRefmapObfuscationContext());
        return this.refMapper;
    }

    String remapClassName(String string, String string2) {
        return this.getReferenceMapper().remap(string, string2);
    }

    @Override
    public IMixinConfigPlugin getPlugin() {
        return this.plugin;
    }

    @Override
    public Set getTargets() {
        return Collections.unmodifiableSet(this.mixinMapping.keySet());
    }

    public Set getUnhandledTargets() {
        return Collections.unmodifiableSet(this.unhandledTargets);
    }

    public Level getLoggingLevel() {
        return this.verboseLogging ? Level.INFO : Level.DEBUG;
    }

    public boolean packageMatch(String string) {
        return string.startsWith(this.mixinPackage);
    }

    public boolean canPassThrough(String string) {
        return this.syntheticInnerClasses.contains(string) || this.passThroughClasses.contains(string);
    }

    public ClassNode passThrough(String string, ClassNode classNode) {
        if (this.syntheticInnerClasses.contains(string)) {
            return this.passThroughSyntheticInner(classNode);
        }
        return classNode;
    }

    private ClassNode passThroughSyntheticInner(ClassNode classNode) {
        classNode.access |= 1;
        for (Object object : classNode.fields) {
            if ((((FieldNode)object).access & 6) != 0) continue;
            ((FieldNode)object).access |= 1;
        }
        for (Object object : classNode.methods) {
            if ((((MethodNode)object).access & 6) != 0) continue;
            ((MethodNode)object).access |= 1;
        }
        return classNode;
    }

    public boolean hasMixinsFor(String string) {
        return this.mixinMapping.containsKey(string);
    }

    public List getMixinsFor(String string) {
        return this.mixinsFor(string);
    }

    private List mixinsFor(String string) {
        ArrayList arrayList = (ArrayList)this.mixinMapping.get(string);
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.mixinMapping.put(string, arrayList);
        }
        return arrayList;
    }

    public List reloadMixin(String string, byte[] byArray) {
        for (MixinInfo mixinInfo : this.mixins) {
            if (!mixinInfo.getClassName().equals(string)) continue;
            mixinInfo.reloadMixin(byArray);
            return mixinInfo.getTargetClasses();
        }
        return Collections.emptyList();
    }

    public String toString() {
        return this.name;
    }

    public int compareTo(MixinConfig mixinConfig) {
        if (mixinConfig == null) {
            return 0;
        }
        if (mixinConfig.priority == this.priority) {
            return this.order - mixinConfig.order;
        }
        return this.priority - mixinConfig.priority;
    }

    static Config create(String string, MixinEnvironment mixinEnvironment) {
        try {
            MixinConfig mixinConfig = (MixinConfig)new Gson().fromJson((Reader)new InputStreamReader(Launch.classLoader.getResourceAsStream(string)), MixinConfig.class);
            if (mixinConfig.onLoad(string, mixinEnvironment)) {
                return mixinConfig.getHandle();
            }
            return null;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw new IllegalArgumentException(String.format("The specified resource '%s' was invalid or could not be read", string), exception);
        }
    }

    private static int getCollectionSize(Collection ... collectionArray) {
        int n = 0;
        for (Collection collection : collectionArray) {
            if (collection == null) continue;
            n += collection.size();
        }
        return n;
    }

    public int compareTo(Object object) {
        return this.compareTo((MixinConfig)object);
    }

    static class InjectorOptions {
        @SerializedName(value="defaultRequire")
        int defaultRequireValue = 0;
        @SerializedName(value="defaultGroup")
        String defaultGroup = "default";

        InjectorOptions() {
        }
    }
}

