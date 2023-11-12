/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.settings.KeyBinding
 *  net.minecraft.util.StringUtils
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.macros;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Mode;
import xyz.Melody.Event.value.Option;
import xyz.Melody.System.Managers.Skyblock.Area.Areas;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.modules.macros.Farming.CropNuker;
import xyz.Melody.module.modules.macros.Farming.LegitCropNuker;
import xyz.Melody.module.modules.others.AutoWalk;

public class AntiLimbo
extends Module {
    private boolean worldChanged = false;
    private TimerUtil worldTimer = new TimerUtil();
    private TimerUtil timer = new TimerUtil();
    private boolean wasInGarden = false;
    private stage curStage = stage.NONE;
    private Option autoStart = new Option("AutoStartMacro", false);
    private Mode mode = new Mode("Mode", macros.values(), macros.CropNuker);

    public AntiLimbo() {
        super("AntiGardenLimbo", new String[]{""}, ModuleType.Macros);
        this.addValues(this.mode, this.autoStart);
        this.setModInfo("Auto Warp Back to Garden after Limboed.");
    }

    @EventHandler
    private void tick(EventTick eventTick) {
        if (this.worldChanged && Client.inSkyblock && !Client.inDungeons && this.worldTimer.hasReached(1000.0)) {
            this.worldChanged = false;
            this.wasInGarden = Client.instance.sbArea.getCurrentArea() == Areas.Garden;
            this.worldTimer.reset();
        }
        if (this.timer.hasReached(3000.0) && this.curStage != stage.NONE) {
            if (this.curStage == stage.Limbo) {
                this.mc.field_71439_g.func_71165_d("/lobby");
                this.curStage = stage.Lobby;
            } else if (this.curStage == stage.Lobby) {
                this.mc.field_71439_g.func_71165_d("/skyblock");
                this.curStage = stage.Skyblock;
            } else if (this.curStage == stage.Skyblock) {
                if (Client.inSkyblock) {
                    this.mc.field_71439_g.func_71165_d("/warp garden");
                    this.curStage = stage.Garden;
                } else {
                    this.curStage = stage.Lobby;
                }
            } else if (this.curStage == stage.Garden) {
                this.curStage = stage.NONE;
                if (((Boolean)this.autoStart.getValue()).booleanValue()) {
                    if (this.mode.getValue() == macros.CropNuker) {
                        Client.instance.getModuleManager().getModuleByClass(CropNuker.class).setEnabled(true);
                    } else {
                        Client.instance.getModuleManager().getModuleByClass(LegitCropNuker.class).setEnabled(true);
                    }
                    AutoWalk autoWalk = (AutoWalk)Client.instance.getModuleManager().getModuleByClass(AutoWalk.class);
                    autoWalk.reset();
                    autoWalk.start(autoWalk.currentGoal);
                    autoWalk.setEnabled(true);
                    KeyBinding.func_74510_a((int)this.mc.field_71474_y.field_74311_E.func_151463_i(), (boolean)true);
                }
            }
            this.timer.reset();
        }
    }

    @SubscribeEvent(receiveCanceled=true)
    public void onChatRCV(ClientChatReceivedEvent clientChatReceivedEvent) {
        String string = StringUtils.func_76338_a((String)clientChatReceivedEvent.message.func_150260_c());
        if (string.equals("You were spawned in Limbo.")) {
            this.curStage = stage.Limbo;
        } else if (string.equals("You cannot join SkyBlock from here!")) {
            this.curStage = stage.Limbo;
        }
        if (this.curStage == stage.Skyblock && string.equals("Unknown command. Type \"/help\" for help.")) {
            this.curStage = stage.Lobby;
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load load) {
        if (!Client.inSkyblock) {
            return;
        }
        this.worldTimer.reset();
        this.timer.reset();
        this.worldChanged = true;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.timer.reset();
        this.worldChanged = false;
        this.wasInGarden = false;
        this.curStage = stage.NONE;
        super.onDisable();
    }

    static enum macros {
        CropNuker,
        LegitCropNuker;

    }

    static enum stage {
        Limbo,
        Lobby,
        Skyblock,
        Garden,
        NONE;

    }
}

