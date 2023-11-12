/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.StringUtils
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.System.Managers.Skyblock.Dungeons;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventBus;
import xyz.Melody.System.Managers.Manager;
import xyz.Melody.System.Managers.Skyblock.Dungeons.DungeonFloors;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.game.PlayerListUtils;
import xyz.Melody.Utils.game.ScoreboardUtils;
import xyz.Melody.Utils.timer.TimerUtil;

public final class DungeonListener
implements Manager {
    private Minecraft mc = Minecraft.func_71410_x();
    public DungeonFloors floor = DungeonFloors.NULL;
    public boolean inBoss = false;
    public int totalSecrets = 0;
    public int secretsFound = 0;
    public int cryptsFound = 0;
    public boolean foundMimic = false;
    public int deaths = 0;
    public boolean activeRun = false;
    public int score = 0;
    public Pattern scorePattern = Pattern.compile("Cleared: [0-9]{1,3}% \\((?<score>[0-9]+)\\)");
    public Thread dungeonThread;
    public TimerUtil daemonTimer = new TimerUtil();
    private List entryMessages = Arrays.asList("[BOSS] Bonzo: Gratz for making it this far, but I\ufffd\ufffdm basically unbeatable.", "[BOSS] Scarf: This is where the journey ends for you, Adventurers.", "[BOSS] The Professor: I was burdened with terrible news recently...", "[BOSS] Thorn: Welcome Adventurers! I am Thorn, the Spirit! And host of the Vegan Trials!", "[BOSS] Livid: Welcome, you arrive right on time. I am Livid, the Master of Shadows.", "[BOSS] Sadan: So you made it all the way here...and you wish to defy me? Sadan?!", "[BOSS] Maxor: WELL WELL WELL LOOK WHO\ufffd\ufffdS HERE!");
    private Pattern numberPattern = Pattern.compile("[^0-9.]");

    @Override
    public void init() {
        Client.instance.logger.info("[MelodySky] [CONSOLE] Initializing Melody -> Dungeon Listener.");
        EventBus.getInstance().register(this);
    }

    @SubscribeEvent(receiveCanceled=true)
    public void onChatPacket(ClientChatReceivedEvent clientChatReceivedEvent) {
        if (this.mc.field_71441_e == null || this.mc.field_71439_g == null || !Client.inDungeons) {
            return;
        }
        String string = StringUtils.func_76338_a((String)clientChatReceivedEvent.message.func_150260_c());
        if (this.entryMessages.contains(string)) {
            this.inBoss = true;
        }
    }

    public void initDungeonThread() {
        this.dungeonThread = new Thread(this::lambda$initDungeonThread$0);
        this.dungeonThread.setDaemon(true);
        this.dungeonThread.setName("Melody -> Dungeon Thread");
        this.dungeonThread.start();
    }

    public void reset() {
        this.floor = DungeonFloors.NULL;
        this.inBoss = false;
        this.secretsFound = 0;
        this.totalSecrets = 0;
        this.cryptsFound = 0;
        this.foundMimic = false;
        this.deaths = 0;
        this.activeRun = false;
        this.score = 0;
    }

    public void updateFloor() {
        if (Minecraft.func_71410_x().field_71439_g == null || Minecraft.func_71410_x().field_71441_e == null) {
            return;
        }
        if (Client.inDungeons) {
            if (ScoreboardUtils.scoreboardContains("(F1)")) {
                this.floor = DungeonFloors.F1;
                Client.instance.dungeonManager.isMMD = false;
            } else if (ScoreboardUtils.scoreboardContains("(F2)")) {
                this.floor = DungeonFloors.F2;
                Client.instance.dungeonManager.isMMD = false;
            } else if (ScoreboardUtils.scoreboardContains("(F3)")) {
                this.floor = DungeonFloors.F3;
                Client.instance.dungeonManager.isMMD = false;
            } else if (ScoreboardUtils.scoreboardContains("(F4)")) {
                this.floor = DungeonFloors.F4;
                Client.instance.dungeonManager.isMMD = false;
            } else if (ScoreboardUtils.scoreboardContains("(F5)")) {
                this.floor = DungeonFloors.F5;
                Client.instance.dungeonManager.isMMD = false;
            } else if (ScoreboardUtils.scoreboardContains("(F6)")) {
                this.floor = DungeonFloors.F6;
                Client.instance.dungeonManager.isMMD = false;
            } else if (ScoreboardUtils.scoreboardContains("(F7)")) {
                this.floor = DungeonFloors.F7;
                Client.instance.dungeonManager.isMMD = false;
            } else if (ScoreboardUtils.scoreboardContains("(M1)")) {
                this.floor = DungeonFloors.M1;
                Client.instance.dungeonManager.isMMD = true;
            } else if (ScoreboardUtils.scoreboardContains("(M2)")) {
                this.floor = DungeonFloors.M2;
                Client.instance.dungeonManager.isMMD = true;
            } else if (ScoreboardUtils.scoreboardContains("(M3)")) {
                this.floor = DungeonFloors.M3;
                Client.instance.dungeonManager.isMMD = true;
            } else if (ScoreboardUtils.scoreboardContains("(M4)")) {
                this.floor = DungeonFloors.M4;
                Client.instance.dungeonManager.isMMD = true;
            } else if (ScoreboardUtils.scoreboardContains("(M5)")) {
                this.floor = DungeonFloors.M5;
                Client.instance.dungeonManager.isMMD = true;
            } else if (ScoreboardUtils.scoreboardContains("(M6)")) {
                this.floor = DungeonFloors.M6;
                Client.instance.dungeonManager.isMMD = true;
            } else if (ScoreboardUtils.scoreboardContains("(M7)")) {
                this.floor = DungeonFloors.M7;
                Client.instance.dungeonManager.isMMD = true;
            } else if ((ScoreboardUtils.scoreboardContains("Dragon") || ScoreboardUtils.scoreboardContains("No Alive Dragons")) && ScoreboardUtils.scoreboardContains("Time Elapsed:") && ScoreboardUtils.scoreboardContains("Cleard: ")) {
                this.floor = DungeonFloors.M7;
                Client.instance.dungeonManager.isMMD = true;
            } else {
                this.floor = DungeonFloors.NULL;
                Client.instance.dungeonManager.isMMD = false;
            }
        } else {
            this.floor = DungeonFloors.NULL;
            Client.instance.dungeonManager.isMMD = false;
        }
    }

    public void updateStats(List list) {
        try {
            double d = -1.0;
            for (String string : list) {
                String string2;
                if (string.contains("Secrets Found: ") && string.contains("%") && (d = Double.parseDouble((string2 = StringUtils.func_76338_a((String)string)).replaceAll("Secrets Found: ", "").replaceAll("%", "")) / 100.0) != -1.0 && d != 0.0) {
                    this.totalSecrets = (int)((double)this.secretsFound / d);
                }
                if (string.contains("Deaths: ")) {
                    string2 = this.removeAllExceptNumbersAndPeriods(string = StringUtils.func_76338_a((String)string));
                    if (string2.isEmpty()) continue;
                    this.deaths = Integer.parseInt(string2);
                    continue;
                }
                if (string.contains("Secrets Found: ") && !string.contains("%")) {
                    string2 = this.removeAllExceptNumbersAndPeriods(string = StringUtils.func_76338_a((String)string));
                    if (string2.isEmpty()) continue;
                    this.secretsFound = Integer.parseInt(string2);
                    continue;
                }
                if (!string.contains("Crypts: ") || (string2 = this.removeAllExceptNumbersAndPeriods(string = StringUtils.func_76338_a((String)string))).isEmpty()) continue;
                this.cryptsFound = Integer.parseInt(string2);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            Client.instance.logger.error("Exception in class DungeonListener.");
        }
    }

    public List getTabList() {
        List list = PlayerListUtils.getTabListListStr();
        if (!StringUtils.func_76338_a((String)((String)list.get(0))).contains("Party")) {
            return null;
        }
        return list;
    }

    public boolean inFloor(DungeonFloors ... dungeonFloorsArray) {
        for (DungeonFloors dungeonFloors : dungeonFloorsArray) {
            if (dungeonFloors != this.floor) continue;
            return true;
        }
        return false;
    }

    public void debug() {
        if (Client.inDungeons) {
            Helper.sendMessage("Floor: " + this.floor.name());
            Helper.sendMessage("In Boss: " + this.inBoss);
        } else {
            Helper.sendMessage("You must be in a dungeon to debug a dungeon!");
        }
    }

    public String removeAllExceptNumbersAndPeriods(String string) {
        return this.numberPattern.matcher(string).replaceAll("");
    }

    private void lambda$initDungeonThread$0() {
        while (true) {
            try {
                while (true) {
                    Matcher matcher;
                    String string;
                    if (!Client.inDungeons) {
                        this.reset();
                        Thread.sleep(5000L);
                        continue;
                    }
                    Thread.sleep(1000L);
                    if (this.mc.field_71441_e == null || this.mc.field_71439_g == null) continue;
                    this.updateFloor();
                    List list = this.getTabList();
                    if (list != null) {
                        this.updateStats(list);
                    }
                    if ((string = ScoreboardUtils.getLineThatContains("Cleared: ")) == null || !(matcher = this.scorePattern.matcher(string)).matches()) continue;
                    String string2 = matcher.group("score");
                    this.score = Integer.parseInt(string2);
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

