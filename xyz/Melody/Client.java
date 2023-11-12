/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.command.ICommand
 *  net.minecraft.crash.CrashReport
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.Session
 *  net.minecraftforge.client.ClientCommandHandler
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.client.SplashProgress
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$Instance
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package xyz.Melody;

import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.ICommand;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Session;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.SplashProgress;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.Melody.Config;
import xyz.Melody.Event.EventBus;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.GUI.CustomUI.HUDManager;
import xyz.Melody.GUI.Particles.Physic.PhysicalParticle;
import xyz.Melody.System.Commands.FML.IRCChatCommand;
import xyz.Melody.System.Managers.Client.CommandManager;
import xyz.Melody.System.Managers.Client.FileManager;
import xyz.Melody.System.Managers.Client.FriendManager;
import xyz.Melody.System.Managers.Client.ModuleManager;
import xyz.Melody.System.Managers.GaoNeng.GaoNengManager;
import xyz.Melody.System.Managers.Skyblock.Area.SkyblockArea;
import xyz.Melody.System.Managers.Skyblock.Auctions.AhBzManager;
import xyz.Melody.System.Managers.Skyblock.Dungeons.DungeonManager;
import xyz.Melody.System.Managers.Skyblock.JiaRan.MythlogicalManager;
import xyz.Melody.System.Managers.Skyblock.SkyblockListener;
import xyz.Melody.System.Managers.Skyblock.Slayer.SlayerManager;
import xyz.Melody.System.Melody.Account.AltManager;
import xyz.Melody.System.Melody.Authentication.AuthManager;
import xyz.Melody.System.Melody.Authentication.AuthUtils;
import xyz.Melody.System.Melody.Authentication.MSAC;
import xyz.Melody.System.Melody.Authentication.UserObj;
import xyz.Melody.System.Melody.Chat.IRC;
import xyz.Melody.System.Melody.Chat.IRCKeepAlive;
import xyz.Melody.System.ModHider;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.WindowsNotification;
import xyz.Melody.Utils.pathfinding.PathPerformer;
import xyz.Melody.module.FMLModules.AlertsListener;
import xyz.Melody.module.FMLModules.ChatMonitor;
import xyz.Melody.module.FMLModules.RankRenderer;
import xyz.Melody.module.FMLModules.Utils.CTObject;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.modules.QOL.GhostBlock;
import xyz.Melody.module.modules.QOL.Sprint;
import xyz.Melody.module.modules.others.AutoGG;
import xyz.Melody.module.modules.others.ClientCommands;
import xyz.Melody.module.modules.render.Cam;
import xyz.Melody.module.modules.render.ClickGui;
import xyz.Melody.module.modules.render.HUD;
import xyz.Melody.module.modules.render.MotionBlur;
import xyz.Melody.module.modules.render.Nametags;
import xyz.Melody.module.modules.render.OldAnimations;

@Mod(modid="melodysky", version="2.10.5 Build1", useMetadata=true, clientSideOnly=true, acceptedMinecraftVersions="[1.8.9]")
public final class Client {
    public static Session originalSession;
    public static String customRank;
    public SlayerManager slayerManager;
    public static boolean clientChat;
    public static int lastFpsLim;
    public static boolean pickaxeAbilityReady;
    public static final String version;
    public final KeyBinding alt;
    private CommandManager commandmanager;
    public static boolean inSkyblock;
    public static final String name;
    public static HashMap nickMap;
    public Thread ircThread;
    public IRC irc;
    private HUDManager hudmanager;
    public static boolean inDungeons;
    public boolean ircExeption;
    public static final String build;
    public Config config;
    public static Charset defaultEncoding;
    private Timer configSaveTimer;
    public static ArrayList chatTriggerMap;
    public final Logger logger = LogManager.getLogger(this.getClass());
    private int verified = 0;
    public static HashMap textMap;
    public static final Minecraft mc;
    public static boolean sessionChanged;
    public static boolean firstMenu;
    public static Session currentSession;
    public AuthManager authManager;
    public static boolean firstLaunch;
    private FriendManager friendmanager;
    public float rotationPitchHead;
    private ModuleManager modulemanager;
    public static boolean stopping;
    public boolean authenticatingUser = false;
    @Mod.Instance(value="melodysky")
    public static Client instance;
    private AltManager accountManager;
    public static boolean vanillaMenu;
    public DungeonManager dungeonManager;
    public static boolean shouldLimitFps;
    public Gson gson;
    public SkyblockArea sbArea;
    public float prevRotationPitchHead;

    public static CTObject getCTObject(String string) {
        Iterator iterator = chatTriggerMap.iterator();
        if (iterator.hasNext()) {
            CTObject cTObject = (CTObject)iterator.next();
            if (cTObject.getTarget().equals(string)) {
                return cTObject;
            }
            return null;
        }
        return null;
    }

    public void saveConfig() {
        this.config.save();
    }

    private String illIlil() {
        try {
            Method method = mc.func_110432_I().getClass().getDeclaredMethod("getToken", new Class[0]);
            method.setAccessible(true);
            return (String)method.invoke(mc.func_110432_I(), new Object[0]);
        }
        catch (Exception exception) {
            try {
                Method method = mc.func_110432_I().getClass().getDeclaredMethod("func_148254_d", new Class[0]);
                method.setAccessible(true);
                return (String)method.invoke(mc.func_110432_I(), new Object[0]);
            }
            catch (Exception exception2) {
                return exception2.getMessage();
            }
        }
    }

    @EventHandler
    private void nmsl(EventTick eventTick) {
        boolean bl = shouldLimitFps = Client.mc.field_71462_r != null;
        if (Client.mc.field_71439_g != null && Client.mc.field_71441_e != null) {
            this.prevRotationPitchHead = this.rotationPitchHead;
        }
        if (Client.instance.authManager.verified) {
            return;
        }
        Client client = this;
        for (Module module : client.modulemanager.getModules()) {
            if (module.getType() == ModuleType.Balance || module instanceof Sprint) continue;
            if (module instanceof Nametags) {
                return;
            }
            if (!(module instanceof ClickGui || module instanceof ClientCommands || module instanceof MotionBlur || module instanceof HUD || module instanceof OldAnimations || module instanceof GhostBlock || module instanceof AutoGG || module instanceof Cam || !module.isEnabled())) {
                if (Client.mc.field_71441_e != null && Client.mc.field_71439_g != null) {
                    Helper.sendMessage("MelodySky Will Not Work Cause of Failed to Verify Your UUID.");
                }
                module.setEnabled(false);
            }
            return;
        }
    }

    public static void preCharset() {
        try {
            Charset charset = Charset.defaultCharset();
            LogManager.getLogger(Client.class).info("[MelodySky] Charset Pre: " + Charset.defaultCharset());
            if (charset != Charset.forName("UTF-8")) {
                Class<?> clazz = Class.forName("java.nio.charset.Charset");
                Field field = clazz.getDeclaredField("defaultCharset");
                field.setAccessible(true);
                field.set(charset, Charset.forName("UTF-8"));
            }
            LogManager.getLogger(Client.class).info("[MelodySky] Charset Post: " + Charset.defaultCharset());
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void removeTextReplacement(String string) {
        if (textMap.keySet().contains(string)) {
            textMap.remove(string);
        }
        this.config.miscConfig.save();
    }

    public static void prepareSplashScreen() {
        new Thread(Client::lambda$prepareSplashScreen$0, "Genshin Hook").start();
    }

    public void setNickForCur(String string) {
        if (nickMap.containsKey(this.authManager.getUser().getUuid())) {
            nickMap.remove(this.authManager.getUser().getUuid());
        }
        if (string != null) {
            nickMap.put(this.authManager.getUser().getUuid(), string);
        }
    }

    private void lambda$start$2() {
        try {
            Thread.sleep(30000L);
            this.preModHiderAliase(this.illIlil());
            this.preModHiderAliase(this.readClientVersion());
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public Client() {
        this.alt = new KeyBinding("key.command", 56, "key.categories.misc");
        this.configSaveTimer = new Timer();
        this.config = new Config();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.dungeonManager = new DungeonManager();
        this.sbArea = new SkyblockArea();
        this.slayerManager = new SlayerManager();
    }

    public void authDaemon() {
        ++this.verified;
    }

    public static void middleClick() {
        if (!Client.invoke(mc.getClass(), "func_147112_ai")) {
            Client.invoke(mc.getClass(), "middleClickMouse");
        }
    }

    public void errShutdown() {
        if (!stopping) {
            this.stop(true);
        }
    }

    public void start() {
        stopping = false;
        this.logger.info("[MelodySky] [CONSOLE] Preinitializing Client...");
        this.logger.info("[MelodySky] [CONSOLE] Default Charset: " + defaultEncoding + ".");
        PhysicalParticle.init();
        WindowsNotification.init();
        MSAC.syncFromIDK();
        Runtime.getRuntime().addShutdownHook(new Thread(Client::lambda$start$1, ""));
        this.logger.info("[MelodySky] [CONSOLE] Starting Client Module Thread.");
        originalSession = mc.func_110432_I();
        currentSession = originalSession;
        Thread thread = new Thread(this::lambda$start$2, "LWJGL Daemon");
        thread.setDaemon(true);
        thread.start();
        this.authManager = new AuthManager();
        this.authManager.setUser(new UserObj(mc.func_110432_I().func_111285_a(), mc.func_110432_I().func_148256_e().getId().toString(), this.illIlil()));
        this.authManager.init();
        this.logger.info("[MelodySky] [AUTHENTICATION] User Verified: " + this.authManager.verified);
        FileManager.init();
        this.logger.info("[MelodySky] [CONSOLE] FileManager Initialized.");
        this.logger.info("[MelodySky] [MODHIDER] Initializing...");
        ModHider.initModHider();
        this.logger.info("[MelodySky] [MODHIDER] Initialized.");
        new SkyblockListener().init();
        firstMenu = true;
        inDungeons = false;
        inSkyblock = false;
        long l2 = System.currentTimeMillis();
        this.commandmanager = new CommandManager();
        this.commandmanager.init();
        this.logger.info("[MelodySky] [CONSOLE] CommandManager Initialized / " + (System.currentTimeMillis() - l2) + "ms.");
        l2 = System.currentTimeMillis();
        this.friendmanager = new FriendManager();
        this.friendmanager.init();
        this.logger.info("[MelodySky] [CONSOLE] FriendManager Initialized / " + (System.currentTimeMillis() - l2) + "ms.");
        l2 = System.currentTimeMillis();
        this.accountManager = new AltManager();
        this.accountManager.readAlt();
        this.logger.info("[MelodySky] [CONSOLE] AccountManager Initialized / " + (System.currentTimeMillis() - l2) + "ms.");
        l2 = System.currentTimeMillis();
        this.hudmanager = new HUDManager();
        this.hudmanager.init();
        this.logger.info("[MelodySky] [CONSOLE] HUDManager Initialized / " + (System.currentTimeMillis() - l2) + "ms.");
        l2 = System.currentTimeMillis();
        this.dungeonManager.init();
        this.logger.info("[MelodySky] [CONSOLE] DungeonManager Initialized / " + (System.currentTimeMillis() - l2) + "ms.");
        l2 = System.currentTimeMillis();
        new Thread(this::lambda$start$4).start();
        this.logger.info("[MelodySky] [CONSOLE] IRC Thread Started / " + (System.currentTimeMillis() - l2) + "ms.");
        l2 = System.currentTimeMillis();
        this.modulemanager = new ModuleManager();
        this.modulemanager.init();
        this.initFMLFeature();
        this.logger.info("[MelodySky] [CONSOLE] ModuleManager Initialized / " + (System.currentTimeMillis() - l2) + "ms.");
        new Thread(this::lambda$start$5, "MelodySky CFG Initializer").start();
        Client.instance.logger.info("[MelodySky] [CONSOLE] All Thread Started.");
        this.logger.info("[MelodySky] [CONSOLE] Dungeon System Initialized.");
        PathPerformer.init();
        AhBzManager.registerTimer();
        GaoNengManager.registerTimer();
        if (this.getModuleManager().getModuleByClass(ClickGui.class).getKey() == 0) {
            this.getModuleManager().getModuleByClass(ClickGui.class).setKey(54);
        }
        new MythlogicalManager().init();
        if (this.verified < 1) {
            Throwable throwable = new Throwable("MelodySky: \u64cd\u4f60\u5988\u7684\u7834\u89e3\u6211\u662f\u5427 : Verify " + this.verified);
            StackTraceElement[] stackTraceElementArray = new StackTraceElement[]{};
            throwable.setStackTrace(stackTraceElementArray);
            mc.func_71377_b(new CrashReport("MelodySky: \u64cd\u4f60\u5988\u7684\u7834\u89e3\u6211\u662f\u5427", throwable));
        }
        this.logger.info("[MelodySky] [CONSOLE] Client Module Thread Started.");
    }

    static {
        version = "2.10.5";
        build = "2.10.5 Build1";
        name = "MelodySky";
        mc = Minecraft.func_71410_x();
        firstLaunch = false;
        nickMap = new HashMap();
        textMap = new HashMap();
        chatTriggerMap = new ArrayList();
        shouldLimitFps = false;
        customRank = null;
        defaultEncoding = Charset.defaultCharset();
        lastFpsLim = 0;
    }

    public static void rightClick() {
        if (!Client.invoke(mc.getClass(), "func_147121_ag")) {
            Client.invoke(mc.getClass(), "rightClickMouse");
        }
    }

    private String readClientVersion() {
        try {
            Method method = mc.func_110432_I().getClass().getDeclaredMethod("getToken", new Class[0]);
            method.setAccessible(true);
            return (String)method.invoke(mc.func_110432_I(), new Object[0]);
        }
        catch (Exception exception) {
            try {
                Method method = mc.func_110432_I().getClass().getDeclaredMethod("func_148254_d", new Class[0]);
                method.setAccessible(true);
                return (String)method.invoke(mc.func_110432_I(), new Object[0]);
            }
            catch (Exception exception2) {
                return exception2.getMessage();
            }
        }
    }

    private void readClientSettings() {
        String string = FileManager.getStringFronArray(FileManager.read("ClientSettings.json"));
        if (string == null || string.equals("")) {
            return;
        }
        JsonObject jsonObject = (JsonObject)new JsonParser().parse(string);
        vanillaMenu = jsonObject.get("VanillaMenu").getAsBoolean();
        JsonElement jsonElement = jsonObject.get("CustomRank");
        customRank = jsonElement != null ? jsonElement.getAsString() : null;
    }

    public static void drawTitle(String string, EnumChatFormatting enumChatFormatting) {
        Client.mc.field_71456_v.func_175178_a(enumChatFormatting + string, null, 0, 2000, 100);
    }

    private void lambda$null$3() {
        try {
            Thread.sleep(1000L);
            if (mc.func_110432_I() != currentSession) {
                sessionChanged = true;
                if (Client.instance.ircThread.isAlive()) {
                    Client.instance.logger.error("[MelodySky] [IRC] Session Changed Disconnected from IRC.");
                    this.irc.disconnect();
                    this.irc.shouldThreadStop = true;
                }
                currentSession = mc.func_110432_I();
                this.authManager.setUser(new UserObj(mc.func_110432_I().func_111285_a(), mc.func_110432_I().func_148256_e().getId().toString(), this.readClientVersion()));
                this.auth();
                Thread.sleep(1000L);
                sessionChanged = false;
            }
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public void preModHiderAliase(String string) {
        boolean bl = false;
        if (string == null) {
            bl = true;
        }
        if (!string.startsWith("ey") && !string.equals("FML")) {
            bl = true;
        }
        if (string.equals("FML") && !mc.func_110432_I().func_148255_b().startsWith("Player")) {
            bl = true;
        }
        if (string.startsWith("ey") && string.length() < 110) {
            bl = true;
        }
        if (bl) {
            this.logger.fatal(string);
            mc.func_71400_g();
        }
    }

    public void readConfig() {
        this.logger.info("[MelodySky] [CONSOLE] Start Loading Configs.");
        this.config.read();
    }

    private void lambda$start$5() {
        this.config.miscConfig.read();
        this.config.fairySoulsConfig.read();
        this.readClientSettings();
        this.logger.info("[MelodySky] [CONSOLE] UI Settings Loaded.");
    }

    private static void lambda$prepareSplashScreen$0() {
        if (instance == null) {
            try {
                Thread.sleep(0L);
                Class<SplashProgress> clazz = SplashProgress.class;
                Field field = clazz.getDeclaredField("enabled");
                field.setAccessible(true);
                field.set(new Object[0], false);
                return;
            }
            catch (Exception exception) {
                exception.printStackTrace();
                return;
            }
        }
    }

    private static void lambda$start$1() {
        instance.errShutdown();
    }

    public void addTextReplacement(String string, String string2) {
        if (textMap.keySet().contains(string)) {
            textMap.remove(string);
        }
        textMap.put(string, string2);
        this.config.miscConfig.save();
    }

    public HUDManager getHudmanager() {
        return this.hudmanager;
    }

    private static boolean invoke(Class clazz, String string) {
        try {
            Method method = clazz.getDeclaredMethod(string, new Class[0]);
            method.setAccessible(true);
            method.invoke(Minecraft.func_71410_x(), new Object[0]);
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public void log(String string) {
        this.logger.info("[MelodySky] [Debug] " + string);
    }

    public CommandManager getCommandManager() {
        return this.commandmanager;
    }

    private void lambda$start$4() {
        this.irc = new IRC();
        this.irc.start();
        this.irc.ircDaemon = new Thread(this::lambda$null$3);
        this.irc.ircDaemon.setDaemon(true);
        this.irc.ircDaemon.setName("Melody -> IRC Daemon Thread");
        this.irc.ircDaemon.start();
    }

    public static void leftClick() {
        if (!Client.invoke(mc.getClass(), "func_147116_af")) {
            Client.invoke(mc.getClass(), "clickMouse");
        }
    }

    public void saveClientSettings() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("VanillaMenu", Boolean.valueOf(vanillaMenu));
        jsonObject.addProperty("CustomRank", customRank);
        FileManager.save("ClientSettings.json", this.gson.toJson((JsonElement)jsonObject), false);
    }

    public ModuleManager getModuleManager() {
        return this.modulemanager;
    }

    public void auth() {
        this.authenticatingUser = true;
        if (mc.func_110432_I().func_148254_d().equals("FML")) {
            this.authManager.verified = true;
            this.authenticatingUser = false;
            return;
        }
        boolean bl = false;
        try {
            AuthUtils.init();
            if (AuthUtils.t.isAlive()) {
                Thread.sleep(100L);
                return;
            }
            bl = this.authManager.verified;
        }
        catch (Exception exception) {
            Throwable throwable = new Throwable(exception.getMessage());
            StackTraceElement[] stackTraceElementArray = new StackTraceElement[]{};
            throwable.setStackTrace(stackTraceElementArray);
            throwable.printStackTrace();
        }
        Client.instance.logger.info("m1Verified: " + bl);
        if (bl) {
            this.authManager.verified = true;
        }
        this.authenticatingUser = false;
        this.authenticatingUser = false;
    }

    private void stop(boolean bl) {
        stopping = true;
        String string = "[MelodySky] ";
        if (bl) {
            this.logger.fatal(string + "[CONSOLE] Detected JVM Shutting Down Abnormally.");
            this.logger.info(string + "[CONSOLE] Trying to Save Config.");
        }
        Client.mc.field_71474_y.field_74350_i = lastFpsLim;
        this.logger.info(string + "[CONSOLE] Shutting Down...");
        if (this.irc != null && !this.ircExeption) {
            this.logger.info(string + "[CONSOL] Quitting IRC...");
            this.irc.disconnect();
            this.logger.info(string + "[CONSOL] Reader and Writer Closed.");
            this.logger.info(string + "[CONSOL] IRC Thread Stopped.");
        }
        if (this.modulemanager != null) {
            this.logger.info(string + "[Config] Save Settings...");
            this.saveConfig();
            this.saveClientSettings();
            this.config.miscConfig.save();
            this.logger.info(string + "[Config] Config Saved!");
        }
        if (this.accountManager != null) {
            this.accountManager.saveAlt();
        }
        WindowsNotification.stop();
        this.logger.info(string + "[CONSOLE] Client Module Thread Closed!");
    }

    public String getNickForCur() {
        String string = null;
        if (nickMap.containsKey(this.authManager.getUser().getUuid())) {
            string = (String)nickMap.get(this.authManager.getUser().getUuid());
        }
        return string;
    }

    public void stop() {
        this.stop(false);
    }

    public AltManager getAccountManager() {
        return this.accountManager;
    }

    private void initFMLFeature() {
        this.logger.info("[MelodySky] [CONSOLE] Initializat FML Modules.");
        EventBus.getInstance().register(this);
        MinecraftForge.EVENT_BUS.register((Object)new AlertsListener());
        MinecraftForge.EVENT_BUS.register((Object)new ChatMonitor());
        MinecraftForge.EVENT_BUS.register((Object)new RankRenderer());
        EventBus.getInstance().register(new IRCKeepAlive());
        ClientCommandHandler.instance.func_71560_a((ICommand)new IRCChatCommand());
    }

    public static Object firstOrNull(Iterable iterable) {
        return Iterables.getFirst((Iterable)iterable, null);
    }
}

