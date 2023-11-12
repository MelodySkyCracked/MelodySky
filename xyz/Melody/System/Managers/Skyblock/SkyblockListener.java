/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.scoreboard.ScoreObjective
 */
package xyz.Melody.System.Managers.Skyblock;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;
import xyz.Melody.Client;
import xyz.Melody.Event.EventBus;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventDrawText;
import xyz.Melody.System.Managers.Client.ModuleManager;
import xyz.Melody.System.Managers.Manager;
import xyz.Melody.Utils.game.ScoreboardUtils;
import xyz.Melody.Utils.other.StringUtil;
import xyz.Melody.module.modules.render.HUD;

public final class SkyblockListener
implements Manager {
    private Minecraft mc = Minecraft.func_71410_x();
    private Thread clientMainThread;

    @Override
    public void init() {
        Client.instance.logger.info("[MelodySky] [CONSOLE] Initializing Melody -> Main Thread.");
        this.initMainThread();
        EventBus.getInstance().register(this);
    }

    @EventHandler
    private void onFR(EventDrawText eventDrawText) {
        if (Client.instance.getNickForCur() != null) {
            eventDrawText.setText(eventDrawText.getText().replaceAll(this.mc.func_110432_I().func_111285_a(), Client.instance.getNickForCur()));
        }
        if (ModuleManager.loaded && ((Boolean)HUD.getInstance().replaceText.getValue()).booleanValue()) {
            for (String string : Client.textMap.keySet()) {
                if (eventDrawText.getText().contains(".replacetext") || eventDrawText.getText().contains(".rt") || !eventDrawText.getText().contains(string)) continue;
                eventDrawText.setText(eventDrawText.getText().replaceAll(string, (String)Client.textMap.get(string)));
            }
        }
        if (eventDrawText.getText().contains("Wither Impact")) {
            eventDrawText.setText(eventDrawText.getText().replaceAll("Wither Impact", "Genshin Impact"));
        }
    }

    private void initMainThread() {
        this.clientMainThread = new Thread(SkyblockListener::lambda$initMainThread$0);
        this.clientMainThread.setDaemon(true);
        this.clientMainThread.setName("Melody -> Main Thread");
        this.clientMainThread.start();
    }

    private static void lambda$initMainThread$0() {
        while (true) {
            try {
                while (true) {
                    Thread.sleep(1000L);
                    if (Minecraft.func_71410_x().field_71439_g == null || Minecraft.func_71410_x().field_71441_e == null) continue;
                    ScoreObjective scoreObjective = Minecraft.func_71410_x().field_71441_e.func_96441_U().func_96539_a(1);
                    if (scoreObjective != null) {
                        Client.inSkyblock = StringUtil.removeFormatting(scoreObjective.func_96678_d()).contains("SKYBLOCK");
                    }
                    Client.inDungeons = ScoreboardUtils.scoreboardContains("The Catacombs") ? true : ScoreboardUtils.scoreboardContains("Time Elapsed:") && ScoreboardUtils.scoreboardContains("Cleared: ");
                    if (Client.instance.dungeonManager.getDungeonThread() == null) {
                        Client.instance.dungeonManager.startThread();
                    }
                    Client.instance.sbArea.updateCurrentArea();
                    if (ScoreboardUtils.scoreboardContains("Slay the boss!")) {
                        Client.instance.slayerManager.slayerBossSpawned = true;
                        continue;
                    }
                    Client.instance.slayerManager.slayerBossSpawned = false;
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
                continue;
            }
            break;
        }
    }
}

