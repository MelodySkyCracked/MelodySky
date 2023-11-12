/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.ContainerChest
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.EnumDyeColor
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.StringUtils
 *  org.apache.commons.lang3.math.NumberUtils
 */
package xyz.Melody.module.modules.QOL.Dungeons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.misc.EventClickSlot;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class AutoTerminals
extends Module {
    private Option delayy = new Option("NoDelay", true);
    private Numbers delay = new Numbers("ClickDelay", 120.0, 1.0, 200.0, 1.0);
    private TimerUtil timer = new TimerUtil();
    private ArrayList clickQueue = new ArrayList(28);
    private long lastClickTime = 0L;
    public TerminalType currentTerminal = TerminalType.NONE;
    private int windowId = 0;
    private int windowClicks = 0;
    private TimerUtil recalculateTimer = new TimerUtil();
    private boolean recalculate = false;
    private Option pDebug = new Option("PDebug", false);
    private Option debug = new Option("Debug", false);
    private Option maze = new Option("Maze", true);
    private Option numbers = new Option("ClickInOrder", true);
    private Option ca = new Option("CorrectAll", true);
    private Option letter = new Option("Letter", true);
    private Option color = new Option("SelectColor", true);
    private Option cot = new Option("ClickOnTime", true);
    private Option sameColor = new Option("SameColor", true);
    private String letterNeeded = null;
    private int targetColorIndex = -1;
    static List colorOrder = Arrays.asList(14, 1, 4, 13, 11);
    private boolean foundColor = false;
    private int correctColor;
    private static AutoTerminals INSTANCE;

    public AutoTerminals() {
        super("AutoTerminals", new String[]{"at"}, ModuleType.Dungeons);
        this.addValues(this.debug, this.pDebug, this.delayy, this.delay, this.maze, this.numbers, this.ca, this.letter, this.color, this.cot, this.sameColor);
        this.setModInfo("Auto Do Terminals in F7/M7.");
    }

    public static AutoTerminals getInstance() {
        if (INSTANCE == null) {
            INSTANCE = (AutoTerminals)Client.instance.getModuleManager().getModuleByClass(AutoTerminals.class);
        }
        return INSTANCE;
    }

    @EventHandler
    private void onClickSlot(EventClickSlot eventClickSlot) {
        if (((Boolean)this.pDebug.getValue()).booleanValue()) {
            Helper.sendMessage("WindowID: " + eventClickSlot.getWindowID() + ", SlotNumber: " + eventClickSlot.getSlotNumber() + ", Button: " + eventClickSlot.getButton() + ", Mode: " + eventClickSlot.getMode() + ", Player: " + eventClickSlot.getPlayer());
        }
    }

    @EventHandler
    public void onGuiDraw(EventTick eventTick) {
        Container container;
        if (!Client.inDungeons) {
            return;
        }
        GuiScreen guiScreen = this.mc.field_71462_r;
        if (guiScreen instanceof GuiChest && (container = ((GuiChest)guiScreen).field_147002_h) instanceof ContainerChest) {
            if (this.currentTerminal == TerminalType.NONE) {
                String string = ((ContainerChest)container).func_85151_d().func_145748_c_().func_150260_c();
                if (string.equals("Navigate the maze!")) {
                    this.currentTerminal = TerminalType.MAZE;
                } else if (string.equals("Click in order!")) {
                    this.currentTerminal = TerminalType.NUMBERS;
                } else if (string.equals("Correct all the panes!")) {
                    this.currentTerminal = TerminalType.CORRECT_ALL;
                } else if (string.startsWith("What starts with: '")) {
                    this.currentTerminal = TerminalType.LETTER;
                } else if (string.startsWith("Select all the")) {
                    this.currentTerminal = TerminalType.COLOR;
                } else if (string.startsWith("Click the button on time!")) {
                    this.currentTerminal = TerminalType.TIMING;
                } else if (string.startsWith("Change all to same color!")) {
                    this.currentTerminal = TerminalType.CHANGEATSC;
                }
                if (((Boolean)this.debug.getValue()).booleanValue()) {
                    Helper.sendMessage("Type: " + this.currentTerminal.toString());
                }
            }
            if (this.currentTerminal != TerminalType.NONE) {
                if ((this.clickQueue.isEmpty() || this.recalculate) && this.currentTerminal != TerminalType.TIMING) {
                    if (((Boolean)this.debug.getValue()).booleanValue()) {
                        Helper.sendMessage("Calculating Clicks");
                    }
                    this.windowId = this.mc.field_71439_g.field_71070_bA.field_75152_c;
                    this.windowClicks = 0;
                    this.recalculate = this.getClicks((ContainerChest)container);
                    if (((Boolean)this.debug.getValue()).booleanValue()) {
                        Helper.sendMessage("Needed Clicks: " + this.clickQueue.size());
                    }
                }
                if (this.currentTerminal == TerminalType.TIMING && System.currentTimeMillis() - this.lastClickTime > 100L) {
                    this.handleTimingClicks((ContainerChest)container);
                }
                if (!this.clickQueue.isEmpty() && this.currentTerminal != TerminalType.TIMING && System.currentTimeMillis() - this.lastClickTime > ((Double)this.delay.getValue()).longValue()) {
                    this.clickSlot((Slot)this.clickQueue.get(0));
                }
            }
        }
    }

    @EventHandler
    public void onMaxFans(EventTick eventTick) {
        if (this.foundColor || this.currentTerminal != TerminalType.CHANGEATSC) {
            return;
        }
        EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
        if (this.mc.field_71462_r instanceof GuiChest) {
            if (entityPlayerSP == null) {
                return;
            }
            ContainerChest containerChest = (ContainerChest)entityPlayerSP.field_71070_bA;
            List list = ((GuiChest)this.mc.field_71462_r).field_147002_h.field_75151_b;
            String string = containerChest.func_85151_d().func_145748_c_().func_150260_c().trim();
            if (string.equals("Change all to same color!")) {
                int n;
                int n2 = 0;
                int n3 = 0;
                int n4 = 0;
                int n5 = 0;
                int n6 = 0;
                block7: for (n = 12; n <= 32; ++n) {
                    ItemStack itemStack = ((Slot)list.get(n)).func_75211_c();
                    if (itemStack == null || itemStack.func_77973_b() != Item.func_150898_a((Block)Blocks.field_150397_co) || itemStack.func_77952_i() == 7) continue;
                    switch (itemStack.func_77952_i()) {
                        case 1: {
                            ++n3;
                            continue block7;
                        }
                        case 4: {
                            ++n4;
                            continue block7;
                        }
                        case 11: {
                            ++n6;
                            continue block7;
                        }
                        case 13: {
                            ++n5;
                            continue block7;
                        }
                        case 14: {
                            ++n2;
                        }
                    }
                }
                n = NumberUtils.max((int[])new int[]{n2, n3, n4, n5, n6});
                this.correctColor = n == n2 ? 14 : (n == n3 ? 1 : (n == n4 ? 4 : (n == n5 ? 13 : 11)));
                this.foundColor = true;
            }
        }
    }

    @EventHandler
    public void onTick(EventTick eventTick) {
        if (!Client.inDungeons) {
            return;
        }
        if (this.mc.field_71439_g == null || this.mc.field_71441_e == null) {
            return;
        }
        if (!(this.mc.field_71462_r instanceof GuiChest)) {
            this.reset();
        }
    }

    private void reset() {
        if (((Boolean)this.debug.getValue()).booleanValue() && !this.clickQueue.isEmpty()) {
            Helper.sendMessage("reset");
        }
        this.currentTerminal = TerminalType.NONE;
        this.clickQueue.clear();
        this.foundColor = false;
        this.correctColor = 0;
        this.windowClicks = 0;
    }

    private boolean getClicks(ContainerChest containerChest) {
        List list = containerChest.field_75151_b;
        String string = containerChest.func_85151_d().func_145748_c_().func_150260_c().trim();
        switch (this.currentTerminal) {
            case MAZE: {
                if (((Boolean)this.maze.getValue()).booleanValue()) {
                    int[] nArray = new int[]{-9, -1, 1, 9};
                    boolean[] blArray = new boolean[54];
                    int n = -1;
                    for (Object object : list) {
                        ItemStack itemStack;
                        if (object.field_75224_c == this.mc.field_71439_g.field_71071_by || (itemStack = object.func_75211_c()) == null || itemStack.func_77973_b() != Item.func_150898_a((Block)Blocks.field_150397_co)) continue;
                        if (itemStack.func_77952_i() == 5) {
                            blArray[object.field_75222_d] = true;
                            continue;
                        }
                        if (itemStack.func_77952_i() != 14) continue;
                        n = object.field_75222_d;
                    }
                    for (int i = 0; i < 54; ++i) {
                        Object object;
                        if (!blArray[i]) continue;
                        object = new boolean[54];
                        int n2 = i;
                        while (n2 != n) {
                            boolean bl = false;
                            for (int n3 : nArray) {
                                ItemStack itemStack;
                                int n4 = n2 + n3;
                                if (n4 < 0 || n4 > 53 || n3 == -1 && n2 % 9 == 0 || n3 == 1 && n2 % 9 == 8) continue;
                                if (n4 == n) {
                                    return false;
                                }
                                if (object[n4] != false || (itemStack = ((Slot)list.get(n4)).func_75211_c()) == null || itemStack.func_77973_b() != Item.func_150898_a((Block)Blocks.field_150397_co) || itemStack.func_77952_i() != 0) continue;
                                this.clickQueue.add((Slot)list.get(n4));
                                n2 = n4;
                                object[n4] = (Slot)true;
                                bl = true;
                                break;
                            }
                            if (bl) continue;
                            System.out.println("Maze calculation aborted");
                            return true;
                        }
                    }
                    return true;
                }
            }
            case NUMBERS: {
                if (((Boolean)this.numbers.getValue()).booleanValue()) {
                    int n = 0;
                    Slot[] slotArray = new Slot[14];
                    for (int i = 10; i <= 25; ++i) {
                        ItemStack itemStack;
                        if (i == 17 || i == 18 || (itemStack = ((Slot)list.get(i)).func_75211_c()) == null || itemStack.func_77973_b() != Item.func_150898_a((Block)Blocks.field_150397_co) || itemStack.field_77994_a >= 15) continue;
                        if (itemStack.func_77952_i() == 14) {
                            slotArray[itemStack.field_77994_a - 1] = (Slot)list.get(i);
                            continue;
                        }
                        if (itemStack.func_77952_i() != 5 || n >= itemStack.field_77994_a) continue;
                        n = itemStack.field_77994_a;
                    }
                    this.clickQueue.addAll(Arrays.stream(slotArray).filter(Objects::nonNull).collect(Collectors.toList()));
                    if (this.clickQueue.size() == 14 - n) break;
                    return true;
                }
            }
            case CORRECT_ALL: {
                if (!((Boolean)this.ca.getValue()).booleanValue()) break;
                for (Slot slot : list) {
                    if (slot.field_75224_c == this.mc.field_71439_g.field_71071_by || slot.field_75222_d < 9 || slot.field_75222_d > 35 || slot.field_75222_d % 9 <= 1 || slot.field_75222_d % 9 >= 7) continue;
                    ItemStack itemStack = slot.func_75211_c();
                    if (itemStack == null) {
                        return true;
                    }
                    if (itemStack.func_77973_b() != Item.func_150898_a((Block)Blocks.field_150397_co) || itemStack.func_77952_i() != 14) continue;
                    this.clickQueue.add(slot);
                }
                break;
            }
            case LETTER: {
                if (((Boolean)this.letter.getValue()).booleanValue()) {
                    if (string.length() <= string.indexOf("'") + 1) break;
                    char c = string.charAt(string.indexOf("'") + 1);
                    if (this.letterNeeded != String.valueOf(c)) {
                        this.letterNeeded = String.valueOf(c);
                    }
                    for (Slot slot : list) {
                        if (slot.field_75224_c == this.mc.field_71439_g.field_71071_by) continue;
                        ItemStack itemStack = slot.func_75211_c();
                        if (itemStack == null) {
                            return true;
                        }
                        if (itemStack.func_77948_v() || slot.field_75222_d < 9 || slot.field_75222_d > 44 || slot.field_75222_d % 9 == 0 || slot.field_75222_d % 9 == 8 || !StringUtils.func_76338_a((String)itemStack.func_82833_r()).startsWith(this.letterNeeded)) continue;
                        this.clickQueue.add(slot);
                    }
                    break;
                }
            }
            case COLOR: {
                if (((Boolean)this.color.getValue()).booleanValue()) {
                    String string2 = null;
                    for (EnumDyeColor enumDyeColor : EnumDyeColor.values()) {
                        String string3 = enumDyeColor.func_176610_l().replaceAll(" ", "_").toUpperCase();
                        if (!string.contains(string3)) continue;
                        string2 = enumDyeColor.func_176762_d();
                        break;
                    }
                    if (string2 == null) break;
                    for (Slot slot : list) {
                        if (slot.field_75224_c == this.mc.field_71439_g.field_71071_by || slot.field_75222_d < 9 || slot.field_75222_d > 44 || slot.field_75222_d % 9 == 0 || slot.field_75222_d % 9 == 8) continue;
                        ItemStack itemStack = slot.func_75211_c();
                        if (itemStack == null) {
                            return true;
                        }
                        if (itemStack.func_77948_v() || !itemStack.func_77977_a().contains(string2)) continue;
                        this.clickQueue.add(slot);
                    }
                    break;
                }
            }
            case TIMING: {
                break;
            }
            case CHANGEATSC: {
                if (!((Boolean)this.sameColor.getValue()).booleanValue() || !this.foundColor) break;
                ArrayList<Slot> arrayList = new ArrayList<Slot>();
                for (int i = 12; i <= 32; ++i) {
                    int n;
                    Slot slot = (Slot)list.get(i);
                    ItemStack itemStack = slot.func_75211_c();
                    if (itemStack == null || itemStack.func_77973_b() != Item.func_150898_a((Block)Blocks.field_150397_co) || itemStack.func_77952_i() == 7 || (n = Math.abs(this.getDiff(itemStack.func_77952_i(), this.correctColor))) == 0) continue;
                    for (int j = 0; j < n; ++j) {
                        arrayList.add(slot);
                    }
                }
                this.clickQueue.addAll(arrayList);
                break;
            }
        }
        return false;
    }

    @EventHandler
    public void handleTimingClicks(ContainerChest containerChest) {
        if (!((Boolean)this.cot.getValue()).booleanValue()) {
            return;
        }
        List list = containerChest.field_75151_b;
        int n = -1;
        int n2 = -1;
        int n3 = 0;
        Slot slot = null;
        block6: for (int i = 1; i < 51; ++i) {
            ItemStack itemStack = ((Slot)list.get(i)).func_75211_c();
            if (itemStack == null) continue;
            EnumDyeColor enumDyeColor = EnumDyeColor.func_176764_b((int)itemStack.func_77952_i());
            switch (enumDyeColor) {
                case PURPLE: {
                    if (n2 != -1) continue block6;
                    n2 = i % 9;
                    if (!((Boolean)this.debug.getValue()).booleanValue()) continue block6;
                    Helper.sendMessage("PurpleSlot: " + n2);
                    continue block6;
                }
                case MAGENTA: {
                    if (n2 != -1) continue block6;
                    n2 = i % 9;
                    if (!((Boolean)this.debug.getValue()).booleanValue()) continue block6;
                    Helper.sendMessage("MagentaSlot: " + n2);
                    continue block6;
                }
                case LIME: {
                    Item item = itemStack.func_77973_b();
                    if (item == Item.func_150898_a((Block)Blocks.field_150397_co)) {
                        if (n != -1) continue block6;
                        n = i % 9;
                        if (!((Boolean)this.debug.getValue()).booleanValue()) continue block6;
                        Helper.sendMessage("GreenSlot: " + n);
                        continue block6;
                    }
                    if (item != Item.func_150898_a((Block)Blocks.field_150406_ce)) continue block6;
                    n3 = i;
                    slot = (Slot)list.get(i);
                    if (!((Boolean)this.debug.getValue()).booleanValue()) continue block6;
                    Helper.sendMessage("slotNeedClick: " + slot.field_75222_d);
                    continue block6;
                }
                case GREEN: {
                    Item item = itemStack.func_77973_b();
                    if (item == Item.func_150898_a((Block)Blocks.field_150397_co)) {
                        if (n != -1) continue block6;
                        n = i % 9;
                        if (!((Boolean)this.debug.getValue()).booleanValue()) continue block6;
                        Helper.sendMessage("GreenSlot: " + n);
                        continue block6;
                    }
                    if (item != Item.func_150898_a((Block)Blocks.field_150406_ce)) continue block6;
                    n3 = i;
                    slot = (Slot)list.get(i);
                    if (!((Boolean)this.debug.getValue()).booleanValue()) continue block6;
                    Helper.sendMessage("slotNeedClick: " + slot.field_75222_d);
                    continue block6;
                }
            }
        }
        if (((Boolean)this.debug.getValue()).booleanValue()) {
            Helper.sendMessage(n2 != -1);
            Helper.sendMessage(n3 != 0);
            Helper.sendMessage(n == n2);
            Helper.sendMessage(slot != null);
        }
        if (n2 != -1 && n3 != 0 && n == n2 && slot != null) {
            this.windowClick(this.mc.field_71439_g.field_71070_bA.field_75152_c, slot, 2, 3);
            if (((Boolean)this.debug.getValue()).booleanValue()) {
                Helper.sendMessage("ClickingSlot: " + slot.field_75222_d);
            }
            this.lastClickTime = System.currentTimeMillis();
        }
    }

    public int getDiff(int n, int n2) {
        int n3 = colorOrder.indexOf(n);
        int n4 = colorOrder.indexOf(n2);
        if (n3 == -1 || n4 == -1) {
            return 0;
        }
        if (n4 < n3) {
            return n4 - n3 + 5;
        }
        return n4 - n3;
    }

    private void clickSlot(Slot slot) {
        this.clickSlot(slot, 2, 3, true);
    }

    private void clickSlot(Slot slot, int n, int n2, boolean bl) {
        if (this.windowClicks == 0) {
            this.windowId = this.mc.field_71439_g.field_71070_bA.field_75152_c;
        }
        if (this.windowClick(this.windowId + this.windowClicks, slot, n, n2) == null) {
            this.lastClickTime = System.currentTimeMillis();
            this.timer.reset();
            if (((Boolean)this.delayy.getValue()).booleanValue()) {
                ++this.windowClicks;
                if (bl) {
                    this.clickQueue.remove(slot);
                }
            }
        }
    }

    private ItemStack windowClick(int n, Slot slot, int n2, int n3) {
        return this.mc.field_71442_b.func_78753_a(n, slot.field_75222_d, n2, n3, (EntityPlayer)this.mc.field_71439_g);
    }

    public static enum TerminalType {
        MAZE,
        NUMBERS,
        CORRECT_ALL,
        LETTER,
        COLOR,
        TIMING,
        CHANGEATSC,
        NONE;

    }
}

