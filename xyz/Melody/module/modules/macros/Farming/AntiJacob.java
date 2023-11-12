/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.util.StringUtils
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.macros.Farming;

import java.util.ArrayList;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender2D;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Mode;
import xyz.Melody.GUI.Font.FontLoaders;
import xyz.Melody.System.Managers.Skyblock.Area.Areas;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.game.PlayerListUtils;
import xyz.Melody.Utils.pathfinding.PathPerformer;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.modules.macros.Farming.CropNuker;
import xyz.Melody.module.modules.macros.Farming.LegitCropNuker;
import xyz.Melody.module.modules.others.AutoWalk;

public class AntiJacob
extends Module {
    public boolean contestStarted = false;
    private ArrayList mods = new ArrayList();
    private Mode mode = new Mode("Crop", crops.values(), crops.Wheat);
    private int ticks = 0;

    public AntiJacob() {
        super("AntiJacobContest", ModuleType.Macros);
        this.addValues(this.mode);
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (!Client.instance.sbArea.isIn(Areas.Garden)) {
            return;
        }
        if (this.ticks < 20) {
            ++this.ticks;
            return;
        }
        this.ticks = 0;
        if (PlayerListUtils.tabContains("Jacob's Contest:") && !this.contestStarted && PlayerListUtils.tabContains("Starts In: NOW!")) {
            String string = ((Enum)this.mode.getValue()).name().toLowerCase();
            if (string.equals("wheat") && PlayerListUtils.tabContains("Wheat")) {
                this.disableAll();
            } else if (string.equals("carrot") && PlayerListUtils.tabContains("Carrot")) {
                this.disableAll();
            } else if (string.equals("potato") && PlayerListUtils.tabContains("Potato")) {
                this.disableAll();
            } else if (string.equals("pumpkin") && PlayerListUtils.tabContains("Pumpkin")) {
                this.disableAll();
            } else if (string.equals("melon") && PlayerListUtils.tabContains("Melon")) {
                this.disableAll();
            } else if (string.equals("mushroom") && PlayerListUtils.tabContains("Mushroom")) {
                this.disableAll();
            } else if (string.equals("cactus") && PlayerListUtils.tabContains("Cactus")) {
                this.disableAll();
            } else if (string.equals("sugar_cane") && PlayerListUtils.tabContains("Sugar Cane")) {
                this.disableAll();
            } else if (string.equals("nether_wart") && PlayerListUtils.tabContains("Nether Wart")) {
                this.disableAll();
            } else if (string.equals("cocoa_beans") && PlayerListUtils.tabContains("Cocoa Beans")) {
                this.disableAll();
            }
        }
    }

    @SubscribeEvent(receiveCanceled=true)
    public void onChat(ClientChatReceivedEvent clientChatReceivedEvent) {
        String string = StringUtils.func_76338_a((String)clientChatReceivedEvent.message.func_150260_c());
        if (string.equals("[CHAT] [NPC] Jacob: The Farming Contest is over!")) {
            this.reEnableAll();
        }
    }

    @EventHandler
    private void on2D(EventRender2D eventRender2D) {
        if (!this.contestStarted) {
            return;
        }
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        FontLoaders.CNMD35.drawCenteredString("Jacob's Contest is being held.", scaledResolution.func_78326_a() / 2, scaledResolution.func_78328_b() / 2, Colors.RED.c);
    }

    private void reEnableAll() {
        for (Module module : this.mods) {
            module.setEnabled(true);
            if (!(module instanceof AutoWalk)) continue;
            PathPerformer.instance.revive();
        }
        this.mods.clear();
    }

    private void disableAll() {
        this.contestStarted = true;
        if (this.getModule(AutoWalk.class).isEnabled()) {
            this.mods.add(this.getModule(AutoWalk.class));
        }
        if (this.getModule(CropNuker.class).isEnabled()) {
            this.mods.add(this.getModule(CropNuker.class));
        }
        if (this.getModule(LegitCropNuker.class).isEnabled()) {
            this.mods.add(this.getModule(LegitCropNuker.class));
        }
        for (Module module : this.mods) {
            module.setEnabled(false);
        }
    }

    private Module getModule(Class clazz) {
        return Client.instance.getModuleManager().getModuleByClass(clazz);
    }

    static enum crops {
        Wheat,
        Carrot,
        Potato,
        Pumpkin,
        Melon,
        Mushroom,
        Cactus,
        Sugar_Cane,
        Nether_Wart,
        Cocoa_Beans;

    }
}

