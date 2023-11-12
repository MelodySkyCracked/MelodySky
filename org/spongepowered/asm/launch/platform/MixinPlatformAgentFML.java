/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.ITweaker
 *  net.minecraft.launchwrapper.Launch
 *  net.minecraft.launchwrapper.LaunchClassLoader
 *  org.apache.logging.log4j.Level
 */
package org.spongepowered.asm.launch.platform;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.launch.Blackboard;
import org.spongepowered.asm.launch.platform.MixinPlatformAgentAbstract;
import org.spongepowered.asm.launch.platform.MixinPlatformManager;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IRemapper;

public class MixinPlatformAgentFML
extends MixinPlatformAgentAbstract {
    private static final String LOAD_CORE_MOD_METHOD = "loadCoreMod";
    private static final String GET_REPARSEABLE_COREMODS_METHOD = "getReparseableCoremods";
    private static final String CORE_MOD_MANAGER_CLASS = "net.minecraftforge.fml.relauncher.CoreModManager";
    private static final String GET_IGNORED_MODS_METHOD = "getIgnoredMods";
    private static final String FML_REMAPPER_ADAPTER_CLASS = "org.spongepowered.asm.bridge.RemapperAdapterFML";
    private static final String MFATT_FORCELOADASMOD = "ForceLoadAsMod";
    private static final String MFATT_FMLCOREPLUGIN = "FMLCorePlugin";
    private static final String MFATT_COREMODCONTAINSMOD = "FMLCorePluginContainsFMLMod";
    private final ITweaker coreModWrapper;
    private final String fileName;
    private Class clCoreModManager;

    public MixinPlatformAgentFML(MixinPlatformManager mixinPlatformManager, URI uRI) {
        super(mixinPlatformManager, uRI);
        this.fileName = this.container.getName();
        this.coreModWrapper = this.initFMLCoreMod();
    }

    private ITweaker initFMLCoreMod() {
        try {
            this.clCoreModManager = MixinPlatformAgentFML.getCoreModManagerClass();
            if ("true".equalsIgnoreCase(this.attributes.get(MFATT_FORCELOADASMOD))) {
                MixinPlatformAgentAbstract.logger.debug("ForceLoadAsMod was specified for {}, attempting force-load", new Object[]{this.fileName});
                this.loadAsMod();
            }
            return this.injectCorePlugin();
        }
        catch (Exception exception) {
            return null;
        }
    }

    private void loadAsMod() {
        try {
            MixinPlatformAgentFML.getIgnoredMods(this.clCoreModManager).remove(this.fileName);
        }
        catch (Exception exception) {
            MixinPlatformAgentAbstract.logger.catching((Throwable)exception);
        }
        if (this.attributes.get(MFATT_COREMODCONTAINSMOD) != null) {
            this.addReparseableJar();
        }
    }

    private void addReparseableJar() {
        try {
            Method method = this.clCoreModManager.getDeclaredMethod(Blackboard.getString("mixin.launch.fml.reparseablecoremodsmethod", GET_REPARSEABLE_COREMODS_METHOD), new Class[0]);
            List list = (List)method.invoke(null, new Object[0]);
            if (!list.contains(this.fileName)) {
                MixinPlatformAgentAbstract.logger.debug("Adding {} to reparseable coremod collection", new Object[]{this.fileName});
                list.add(this.fileName);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private ITweaker injectCorePlugin() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String string = this.attributes.get(MFATT_FMLCOREPLUGIN);
        if (string == null) {
            return null;
        }
        MixinPlatformAgentAbstract.logger.debug("{} has core plugin {}. Injecting it into FML for co-initialisation:", new Object[]{this.fileName, string});
        Method method = this.clCoreModManager.getDeclaredMethod(Blackboard.getString("mixin.launch.fml.loadcoremodmethod", LOAD_CORE_MOD_METHOD), LaunchClassLoader.class, String.class, File.class);
        method.setAccessible(true);
        ITweaker iTweaker = (ITweaker)method.invoke(null, Launch.classLoader, string, this.container);
        if (iTweaker == null) {
            MixinPlatformAgentAbstract.logger.debug("Core plugin {} could not be loaded.", new Object[]{string});
            return null;
        }
        return iTweaker;
    }

    @Override
    public String getPhaseProvider() {
        return MixinPlatformAgentFML.class.getName() + "$PhaseProvider";
    }

    @Override
    public void prepare() {
    }

    @Override
    public void initPrimaryContainer() {
        if (this.clCoreModManager != null) {
            this.injectRemapper();
        }
    }

    private void injectRemapper() {
        try {
            MixinPlatformAgentAbstract.logger.debug("Creating FML remapper adapter: {}", new Object[]{FML_REMAPPER_ADAPTER_CLASS});
            Class<?> clazz = Class.forName(FML_REMAPPER_ADAPTER_CLASS, true, (ClassLoader)Launch.classLoader);
            Method method = clazz.getDeclaredMethod("create", new Class[0]);
            IRemapper iRemapper = (IRemapper)method.invoke(null, new Object[0]);
            MixinEnvironment.getDefaultEnvironment().getRemappers().add(iRemapper);
        }
        catch (Exception exception) {
            MixinPlatformAgentAbstract.logger.debug("Failed instancing FML remapper adapter, things will probably go horribly for notch-obf'd mods!");
        }
    }

    @Override
    public void injectIntoClassLoader(LaunchClassLoader launchClassLoader) {
        if (this.coreModWrapper != null) {
            if (this != false) {
                MixinPlatformAgentAbstract.logger.debug("FML agent is co-initialising coremod instance {} for {}", new Object[]{this.coreModWrapper, this.uri});
                this.coreModWrapper.injectIntoClassLoader(launchClassLoader);
            } else {
                MixinPlatformAgentAbstract.logger.debug("FML agent is skipping co-init for {} because FML already started", new Object[]{this.coreModWrapper});
            }
        }
    }

    @Override
    public String getLaunchTarget() {
        return null;
    }

    private static Class getCoreModManagerClass() throws ClassNotFoundException {
        try {
            return Class.forName(Blackboard.getString("mixin.launch.fml.coremodmanagerclass", CORE_MOD_MANAGER_CLASS));
        }
        catch (ClassNotFoundException classNotFoundException) {
            return Class.forName("cpw.mods.fml.relauncher.CoreModManager");
        }
    }

    private static List getIgnoredMods(Class clazz) throws IllegalAccessException, InvocationTargetException {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(Blackboard.getString("mixin.launch.fml.ignoredmodsmethod", GET_IGNORED_MODS_METHOD), new Class[0]);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            try {
                method = clazz.getDeclaredMethod("getLoadedCoremods", new Class[0]);
            }
            catch (NoSuchMethodException noSuchMethodException2) {
                MixinPlatformAgentAbstract.logger.catching(Level.DEBUG, (Throwable)noSuchMethodException2);
                return Collections.emptyList();
            }
        }
        return (List)method.invoke(null, new Object[0]);
    }
}

