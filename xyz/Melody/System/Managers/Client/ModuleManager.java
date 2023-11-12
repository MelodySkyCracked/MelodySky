/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.GlStateManager
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.System.Managers.Client;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import xyz.Melody.Client;
import xyz.Melody.Event.EventBus;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.misc.EventKey;
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.System.Managers.Manager;
import xyz.Melody.Utils.render.gl.GLUtils;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.balance.AimAssist;
import xyz.Melody.module.balance.AntiBot;
import xyz.Melody.module.balance.AntiVelocity;
import xyz.Melody.module.balance.Aura;
import xyz.Melody.module.balance.AutoArmor;
import xyz.Melody.module.balance.AutoClicker;
import xyz.Melody.module.balance.AutoHead;
import xyz.Melody.module.balance.ChestStealer;
import xyz.Melody.module.balance.InvCleaner;
import xyz.Melody.module.balance.NoHitDelay;
import xyz.Melody.module.balance.NoSlowDown;
import xyz.Melody.module.balance.Reach;
import xyz.Melody.module.modules.Fishing.AutoBaits;
import xyz.Melody.module.modules.Fishing.AutoFish;
import xyz.Melody.module.modules.Fishing.AutoSellInv;
import xyz.Melody.module.modules.Fishing.SlugFishing;
import xyz.Melody.module.modules.QOL.AimDragonCrystals;
import xyz.Melody.module.modules.QOL.AutoEnchantTable;
import xyz.Melody.module.modules.QOL.ChestSellValue;
import xyz.Melody.module.modules.QOL.CombindBooks;
import xyz.Melody.module.modules.QOL.DamageFormat;
import xyz.Melody.module.modules.QOL.DivanTreasure;
import xyz.Melody.module.modules.QOL.Dungeons.Alerts;
import xyz.Melody.module.modules.QOL.Dungeons.AutoCloseChest;
import xyz.Melody.module.modules.QOL.Dungeons.AutoSalvage;
import xyz.Melody.module.modules.QOL.Dungeons.AutoSell;
import xyz.Melody.module.modules.QOL.Dungeons.AutoTerminals;
import xyz.Melody.module.modules.QOL.Dungeons.CrystalGetter;
import xyz.Melody.module.modules.QOL.Dungeons.Devices.AutoArrowAlign;
import xyz.Melody.module.modules.QOL.Dungeons.Devices.AutoShootTheTarget;
import xyz.Melody.module.modules.QOL.Dungeons.Devices.AutoSimonSays;
import xyz.Melody.module.modules.QOL.Dungeons.DungeonChestProfit;
import xyz.Melody.module.modules.QOL.Dungeons.LeverAura;
import xyz.Melody.module.modules.QOL.Dungeons.LividFinder;
import xyz.Melody.module.modules.QOL.Dungeons.SayMimicKilled;
import xyz.Melody.module.modules.QOL.Dungeons.SecretAura;
import xyz.Melody.module.modules.QOL.Dungeons.StonkLessStonk;
import xyz.Melody.module.modules.QOL.FrozenTreasureESP;
import xyz.Melody.module.modules.QOL.GemStoneESP;
import xyz.Melody.module.modules.QOL.GhostBlock;
import xyz.Melody.module.modules.QOL.HideSummonings;
import xyz.Melody.module.modules.QOL.InvisEntity;
import xyz.Melody.module.modules.QOL.JerryChineHelper;
import xyz.Melody.module.modules.QOL.MainWorld.AttributeFinder;
import xyz.Melody.module.modules.QOL.MainWorld.AutoJerryBox;
import xyz.Melody.module.modules.QOL.MainWorld.MelodyPlayer;
import xyz.Melody.module.modules.QOL.MobTracker;
import xyz.Melody.module.modules.QOL.Nether.AshfangHelper;
import xyz.Melody.module.modules.QOL.Nether.SulphurESP;
import xyz.Melody.module.modules.QOL.NoNBTUpdate;
import xyz.Melody.module.modules.QOL.RenewCHPass;
import xyz.Melody.module.modules.QOL.SapphireMiningPit;
import xyz.Melody.module.modules.QOL.Slayer.BlazeDagger;
import xyz.Melody.module.modules.QOL.SoundsHider;
import xyz.Melody.module.modules.QOL.Sprint;
import xyz.Melody.module.modules.QOL.Swappings.AOTS;
import xyz.Melody.module.modules.QOL.Swappings.AutoZombieSword;
import xyz.Melody.module.modules.QOL.Swappings.EndStoneSword;
import xyz.Melody.module.modules.QOL.Swappings.ItemSwitcher;
import xyz.Melody.module.modules.QOL.Swappings.SoulWhip;
import xyz.Melody.module.modules.QOL.Swappings.StrengthStack;
import xyz.Melody.module.modules.QOL.Swappings.WitherImpact;
import xyz.Melody.module.modules.QOL.TerminatorClicker;
import xyz.Melody.module.modules.QOL.WormFishingESP;
import xyz.Melody.module.modules.macros.ActionMacro;
import xyz.Melody.module.modules.macros.AntiLimbo;
import xyz.Melody.module.modules.macros.AutoReturnRealm;
import xyz.Melody.module.modules.macros.Farming.AntiJacob;
import xyz.Melody.module.modules.macros.Farming.BanwaveProtector;
import xyz.Melody.module.modules.macros.Farming.CropNuker;
import xyz.Melody.module.modules.macros.Farming.ForagingMacro;
import xyz.Melody.module.modules.macros.Farming.GlowingMushroomNuker;
import xyz.Melody.module.modules.macros.Farming.LegitCropNuker;
import xyz.Melody.module.modules.macros.Mining.AutoRuby;
import xyz.Melody.module.modules.macros.Mining.CustomNuker;
import xyz.Melody.module.modules.macros.Mining.GemstoneNuker;
import xyz.Melody.module.modules.macros.Mining.GoldNuker;
import xyz.Melody.module.modules.macros.Mining.HardStoneNuker;
import xyz.Melody.module.modules.macros.Mining.IceNuker;
import xyz.Melody.module.modules.macros.Mining.MithrilNuker;
import xyz.Melody.module.modules.macros.Mining.MyceliumNuker;
import xyz.Melody.module.modules.macros.Mining.ObsidianNuker;
import xyz.Melody.module.modules.macros.Mining.PinglessMining;
import xyz.Melody.module.modules.macros.Mining.RouteHelper;
import xyz.Melody.module.modules.macros.Mining.SackClicker;
import xyz.Melody.module.modules.macros.Mining.SandNuker;
import xyz.Melody.module.modules.macros.NiggerHelper;
import xyz.Melody.module.modules.macros.PowderChestMacro;
import xyz.Melody.module.modules.others.AntiAFK;
import xyz.Melody.module.modules.others.AntiLobbyCommand;
import xyz.Melody.module.modules.others.AutoGG;
import xyz.Melody.module.modules.others.AutoWalk;
import xyz.Melody.module.modules.others.ClientCommands;
import xyz.Melody.module.modules.others.DianaHelper;
import xyz.Melody.module.modules.others.FreeCam;
import xyz.Melody.module.modules.others.InternetSurfing;
import xyz.Melody.module.modules.others.LbinData;
import xyz.Melody.module.modules.others.NickHider;
import xyz.Melody.module.modules.others.PlayerList;
import xyz.Melody.module.modules.others.TransferBack;
import xyz.Melody.module.modules.others.UnGrabMouse;
import xyz.Melody.module.modules.render.BlockOverlay;
import xyz.Melody.module.modules.render.CHMobESP;
import xyz.Melody.module.modules.render.Cam;
import xyz.Melody.module.modules.render.ClickGui;
import xyz.Melody.module.modules.render.FairySoulESP;
import xyz.Melody.module.modules.render.FullBright;
import xyz.Melody.module.modules.render.HUD;
import xyz.Melody.module.modules.render.HideImplosionParticle;
import xyz.Melody.module.modules.render.MotionBlur;
import xyz.Melody.module.modules.render.Nametags;
import xyz.Melody.module.modules.render.NoArmorRender;
import xyz.Melody.module.modules.render.OldAnimations;
import xyz.Melody.module.modules.render.Tracers;
import xyz.Melody.module.modules.render.XRay;

public class ModuleManager
implements Manager {
    public static HashMap waittingHook = new HashMap();
    public static List modules = new ArrayList();
    public static boolean loaded;

    @Override
    public void init() {
        modules.add(new NickHider());
        modules.add(new SecretAura());
        modules.add(new AutoReturnRealm());
        modules.add(new XRay());
        modules.add(new SackClicker());
        modules.add(new ChestSellValue());
        modules.add(new RouteHelper());
        modules.add(new BanwaveProtector());
        modules.add(new AntiJacob());
        modules.add(new AntiLimbo());
        modules.add(new AutoJerryBox());
        modules.add(new StrengthStack());
        modules.add(new AutoWalk());
        modules.add(new FairySoulESP());
        modules.add(new UnGrabMouse());
        modules.add(new LegitCropNuker());
        modules.add(new NiggerHelper());
        modules.add(new DianaHelper());
        modules.add(new TransferBack());
        modules.add(new AttributeFinder());
        modules.add(new CustomNuker());
        modules.add(new ObsidianNuker());
        modules.add(new DivanTreasure());
        modules.add(new PinglessMining());
        modules.add(new CHMobESP());
        modules.add(new JerryChineHelper());
        modules.add(new AutoSellInv());
        modules.add(new InvCleaner());
        modules.add(new ChestStealer());
        modules.add(new AutoArmor());
        modules.add(new AutoRuby());
        modules.add(new InternetSurfing());
        modules.add(new AutoBaits());
        modules.add(new MyceliumNuker());
        modules.add(new SandNuker());
        modules.add(new SulphurESP());
        modules.add(new GlowingMushroomNuker());
        modules.add(new SapphireMiningPit());
        modules.add(new AntiAFK());
        modules.add(new BlockOverlay());
        modules.add(new NoSlowDown());
        modules.add(new AimAssist());
        modules.add(new AimDragonCrystals());
        modules.add(new IceNuker());
        modules.add(new FrozenTreasureESP());
        modules.add(new HardStoneNuker());
        modules.add(new PlayerList());
        modules.add(new MotionBlur());
        modules.add(new GemStoneESP());
        modules.add(new SlugFishing());
        modules.add(new CropNuker());
        modules.add(new FreeCam());
        modules.add(new OldAnimations());
        modules.add(new AshfangHelper());
        modules.add(new BlazeDagger());
        modules.add(new HideImplosionParticle());
        modules.add(new CombindBooks());
        modules.add(new NoHitDelay());
        modules.add(new SoulWhip());
        modules.add(new Reach());
        modules.add(new DamageFormat());
        modules.add(new AutoArrowAlign());
        modules.add(new AutoShootTheTarget());
        modules.add(new AutoSimonSays());
        modules.add(new DungeonChestProfit());
        modules.add(new LbinData());
        modules.add(new SoundsHider());
        modules.add(new TerminatorClicker());
        modules.add(new ForagingMacro());
        modules.add(new SayMimicKilled());
        modules.add(new StonkLessStonk());
        modules.add(new AntiLobbyCommand());
        modules.add(new AutoSalvage());
        modules.add(new AutoSell());
        modules.add(new AutoEnchantTable());
        modules.add(new AutoCloseChest());
        modules.add(new HideSummonings());
        modules.add(new WormFishingESP());
        modules.add(new RenewCHPass());
        modules.add(new Tracers());
        modules.add(new LividFinder());
        modules.add(new LeverAura());
        modules.add(new AutoGG());
        modules.add(new GemstoneNuker());
        modules.add(new ActionMacro());
        modules.add(new PowderChestMacro());
        modules.add(new NoArmorRender());
        modules.add(new NoNBTUpdate());
        modules.add(new ClientCommands());
        modules.add(new Alerts());
        modules.add(new AutoHead());
        modules.add(new AntiBot());
        modules.add(new Aura());
        modules.add(new GoldNuker());
        modules.add(new MithrilNuker());
        modules.add(new AutoTerminals());
        modules.add(new AOTS());
        modules.add(new EndStoneSword());
        modules.add(new AutoFish());
        modules.add(new AutoZombieSword());
        modules.add(new WitherImpact());
        modules.add(new CrystalGetter());
        modules.add(new Nametags());
        modules.add(new ItemSwitcher());
        modules.add(new ClickGui());
        modules.add(new MelodyPlayer());
        modules.add(new AutoClicker());
        modules.add(new MobTracker());
        modules.add(new GhostBlock());
        modules.add(new InvisEntity());
        modules.add(new HUD());
        modules.add(new Cam());
        modules.add(new Sprint());
        modules.add(new AntiVelocity());
        modules.add(new FullBright());
        Client.instance.readConfig();
        for (Module module : modules) {
            module.makeCommand();
        }
        EventBus.getInstance().register(this);
        loaded = true;
    }

    public static List getModules() {
        return modules;
    }

    public Module getModuleByClass(Class clazz) {
        for (Module module : modules) {
            if (module.getClass() != clazz) continue;
            return module;
        }
        return null;
    }

    public static Module getModuleByName(String string) {
        for (Module module : modules) {
            if (!module.getName().equalsIgnoreCase(string)) continue;
            return module;
        }
        return null;
    }

    public Module getAlias(String string) {
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(string)) {
                return module;
            }
            for (String string2 : module.getAlias()) {
                if (!string2.equalsIgnoreCase(string)) continue;
                return module;
            }
        }
        return null;
    }

    public List getModulesInType(ModuleType moduleType) {
        ArrayList<Module> arrayList = new ArrayList<Module>();
        for (Module module : modules) {
            if (module.getType() != moduleType || module.getName().equals("ClickGui")) continue;
            arrayList.add(module);
        }
        return arrayList;
    }

    @EventHandler
    private void onKeyPress(EventKey eventKey) {
        for (Module module : modules) {
            if (module.getKey() != eventKey.getKey()) continue;
            module.setEnabled(!module.isEnabled());
        }
    }

    @EventHandler
    private void onGLHack(EventRender3D eventRender3D) {
        GlStateManager.func_179111_a((int)2982, (FloatBuffer)((FloatBuffer)GLUtils.MODELVIEW.clear()));
        GlStateManager.func_179111_a((int)2983, (FloatBuffer)((FloatBuffer)GLUtils.PROJECTION.clear()));
        this.glGetInteger(2978, (IntBuffer)GLUtils.VIEWPORT.clear());
    }

    @EventHandler
    private void onHook(EventRender2D eventRender2D) {
        if (!waittingHook.isEmpty()) {
            Client.instance.logger.info("Processing Module Hooks.");
            try {
                for (Map.Entry entry : waittingHook.entrySet()) {
                    if (((Boolean)entry.getValue()).booleanValue()) {
                        ((Module)entry.getKey()).onEnable();
                        continue;
                    }
                    ((Module)entry.getKey()).onDisable();
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            waittingHook.clear();
            Client.instance.logger.info("Module Hooks Proccessed.");
        }
    }

    public void glGetInteger(int n, IntBuffer intBuffer) {
        GL11.glGetInteger((int)n, (IntBuffer)intBuffer);
    }
}

