/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BlockPos
 */
package xyz.Melody.System.Commands.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import net.minecraft.util.BlockPos;
import xyz.Melody.System.Commands.Command;
import xyz.Melody.System.Managers.Client.FileManager;
import xyz.Melody.Utils.Helper;
import xyz.Melody.module.modules.others.AutoWalk;

public final class AutoWalkCMD
extends Command {
    public AutoWalkCMD() {
        super(".autowalk", new String[]{"aw", "autowalk"}, "", "sketit");
    }

    @Override
    public String execute(String[] stringArray) {
        if (stringArray.length >= 1) {
            AutoWalk autoWalk = AutoWalk.getINSTANCE();
            if (stringArray[0].toLowerCase().contains("reset")) {
                autoWalk.reset();
                autoWalk.curIndex = -1;
                Helper.sendMessage("AutoWalk: Reseted.");
            }
            if (stringArray[0].toLowerCase().contains("set")) {
                try {
                    autoWalk.curIndex = Integer.parseInt(stringArray[1]);
                    Helper.sendMessage("AutoWalk: Set Next Waypoint to " + autoWalk.curIndex + ".");
                }
                catch (Exception exception) {
                    Helper.sendMessage("AutoWalk: " + exception.getMessage());
                    exception.printStackTrace();
                }
            }
            if (stringArray[0].toLowerCase().contains("start")) {
                if (stringArray.length == 2 && stringArray[1] != null && !stringArray[1].equals("")) {
                    try {
                        autoWalk.curIndex = Integer.parseInt(stringArray[1]);
                        autoWalk.currentGoal = (BlockPos)autoWalk.waypoints.get(autoWalk.curIndex);
                        Helper.sendMessage("AutoWalk: Set Next Waypoint to " + autoWalk.curIndex + 1 + ".");
                    }
                    catch (Exception exception) {
                        Helper.sendMessage("AutoWalk: " + exception.getMessage());
                        exception.printStackTrace();
                    }
                } else {
                    autoWalk.reset();
                }
                autoWalk.start(autoWalk.currentGoal);
                Helper.sendMessage("AutoWalk: Started.");
            } else if (stringArray[0].toLowerCase().contains("stop")) {
                autoWalk.stop();
                Helper.sendMessage("AutoWalk: Stopped.");
            } else if (stringArray[0].toLowerCase().contains("remove")) {
                try {
                    autoWalk.waypoints.remove(Integer.parseInt(stringArray[1]) - 1);
                }
                catch (Exception exception) {
                    Helper.sendMessage(exception.getMessage());
                }
            } else if (stringArray[0].toLowerCase().contains("add")) {
                try {
                    int n = Integer.parseInt(stringArray[2]);
                    int n2 = Integer.parseInt(stringArray[3]);
                    int n3 = Integer.parseInt(stringArray[4]);
                    BlockPos blockPos = new BlockPos(n, n2, n3);
                    autoWalk.waypoints.add(Integer.parseInt(stringArray[1]), blockPos);
                }
                catch (Exception exception) {
                    Helper.sendMessage(exception.getMessage());
                }
            } else if (stringArray[0].toLowerCase().contains("clear")) {
                autoWalk.waypoints.clear();
                Helper.sendMessage("AutoWalk: Waypoints Cleared.");
            } else if (stringArray[0].toLowerCase().contains("save")) {
                if (stringArray[1] == null) {
                    Helper.sendMessage("Correct Useage: .aw save [configName]");
                    return null;
                }
                if (stringArray[1].contains("/") || stringArray[1].contains(":") || stringArray[1].contains("*") || stringArray[1].contains("?") || stringArray[1].contains(String.valueOf('\"')) || stringArray[1].contains("<") || stringArray[1].contains(">") || stringArray[1].contains("|")) {
                    Helper.sendMessage("Config Name can not be '/', ':', '*', '?', " + String.valueOf('\"') + ", '<', '>', '|'.");
                    return null;
                }
                String string = "";
                int n = 0;
                for (BlockPos blockPos : autoWalk.waypoints) {
                    String string2 = blockPos.func_177958_n() + ":" + blockPos.func_177956_o() + ":" + blockPos.func_177952_p() + "%";
                    string = string + string2;
                    ++n;
                }
                File file = new File(FileManager.getClientDir(), "AWWayPoints");
                if (!file.exists()) {
                    file.mkdir();
                }
                FileManager.save("AWWayPoints/" + stringArray[1] + ".txt", string, false);
                Helper.sendMessage("[AutoWalk] Saved " + n + " Waypoints.");
            } else if (stringArray[0].toLowerCase().contains("load")) {
                String[] stringArray22;
                if (stringArray[1] == null) {
                    Helper.sendMessage("Correct Useage: .aw load [configName]");
                    return null;
                }
                File file = new File(FileManager.getClientDir(), "AWWayPoints");
                if (!file.exists()) {
                    file.mkdir();
                }
                List list = FileManager.read("AWWayPoints/" + stringArray[1] + ".txt");
                String string = "";
                for (String[] stringArray22 : list) {
                    string = string + (String)stringArray22;
                }
                int n = 0;
                stringArray22 = string.split("%");
                ArrayList<BlockPos> arrayList = new ArrayList<BlockPos>();
                for (String string3 : stringArray22) {
                    StringTokenizer stringTokenizer = new StringTokenizer(string3, ":");
                    int n4 = Integer.parseInt(stringTokenizer.nextToken());
                    int n5 = Integer.parseInt(stringTokenizer.nextToken());
                    int n6 = Integer.parseInt(stringTokenizer.nextToken());
                    arrayList.add(new BlockPos(n4, n5, n6));
                    ++n;
                }
                autoWalk.waypoints.clear();
                autoWalk.waypoints.addAll(arrayList);
                Helper.sendMessage("[AutoWalk] Loaded " + n + " Waypoints.");
            } else if (stringArray[0].toLowerCase().contains("help")) {
                Helper.sendMessageWithoutPrefix("====================== AutoWalk ======================");
                Helper.sendMessageWithoutPrefix("AutoWalk:> .aw start - Start Path Proccessing.");
                Helper.sendMessageWithoutPrefix("AutoWalk:> .aw stop - Stop Path Proccessing.");
                Helper.sendMessageWithoutPrefix("AutoWalk:> .aw add [index] [x] [y] [z] - Add a Waypoint After 'index'.");
                Helper.sendMessageWithoutPrefix("AutoWalk:> .aw remove [index] - Remove a waypoint.");
                Helper.sendMessageWithoutPrefix("AutoWalk:> .aw save [name] - Save Current Waypoints as 'name'.");
                Helper.sendMessageWithoutPrefix("AutoWalk:> .aw load [name] - Load saved Waypoints as 'name'.");
                Helper.sendMessageWithoutPrefix("AutoWalk:> .aw reset - Reset Current WayPoint to 0.");
            }
        } else {
            Helper.sendMessage("useage: .autowalk start/stop");
        }
        return null;
    }
}

