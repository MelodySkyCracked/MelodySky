/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiChest
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.inventory.ContainerChest
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumChatFormatting
 */
package xyz.Melody.module.modules.QOL;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.regex.Pattern;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import xyz.Melody.Client;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.render.ColorUtils;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.modules.others.LbinData;

public class ChestSellValue
extends Module {
    private Option format = new Option("Format", false);
    private final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)\ufffd\ufffd[0-9A-FK-OR]");
    private TimerUtil refreshTimer = new TimerUtil();
    private static ChestSellValue INSTANCE;
    private static final NavigableMap suffixes;

    public ChestSellValue() {
        super("ChestSellValue", new String[]{"as"}, ModuleType.QOL);
        this.addValues(this.format);
        this.setModInfo("Container Sell Value Display.");
    }

    public static ChestSellValue getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = (ChestSellValue)Client.instance.getModuleManager().getModuleByClass(ChestSellValue.class);
        }
        return INSTANCE;
    }

    public void onRender() {
        String string;
        if (!this.isEnabled()) {
            return;
        }
        GuiScreen guiScreen = this.mc.field_71462_r;
        if (!(guiScreen instanceof GuiChest)) {
            return;
        }
        GlStateManager.func_179140_f();
        ContainerChest containerChest = (ContainerChest)((GuiChest)guiScreen).field_147002_h;
        if (Client.inDungeons || !Client.inSkyblock) {
            return;
        }
        IInventory iInventory = containerChest.func_85151_d();
        long l2 = 0L;
        int n = 0;
        HashMap<Object, Integer> hashMap = new HashMap<Object, Integer>();
        ArrayList<String> arrayList = new ArrayList<String>();
        if (this.refreshTimer.hasReached(100.0)) {
            block0: for (int i = 0; i < iInventory.func_70302_i_(); ++i) {
                ItemStack itemStack = iInventory.func_70301_a(i);
                if (itemStack == null) continue;
                long l3 = this.getPrice(itemStack) * (long)itemStack.field_77994_a;
                l2 += l3;
                if (l3 == 0L) continue;
                if (!arrayList.contains(itemStack.func_82833_r())) {
                    hashMap.put(itemStack, itemStack.field_77994_a);
                    arrayList.add(itemStack.func_82833_r());
                    continue;
                }
                for (ItemStack itemStack2 : hashMap.keySet()) {
                    if (!itemStack2.func_82833_r().equals(itemStack.func_82833_r())) continue;
                    int n2 = (Integer)hashMap.get(itemStack2);
                    hashMap.remove(itemStack2);
                    hashMap.put(itemStack2, n2 += itemStack.field_77994_a);
                    continue block0;
                }
            }
        }
        ArrayList arrayList2 = new ArrayList(hashMap.keySet());
        arrayList2.sort(Comparator.comparingDouble(arg_0 -> this.lambda$onRender$0(hashMap, arg_0)));
        for (ItemStack itemStack : arrayList2) {
            if (hashMap.get(itemStack) == null) continue;
            FontRenderer fontRenderer = this.mc.field_71466_p;
            int n3 = (Integer)hashMap.get(itemStack);
            long l4 = this.getPrice(itemStack) * (long)n3;
            string = itemStack.func_82833_r() + " \u00a7rx" + n3 + ": " + EnumChatFormatting.YELLOW + this.format(l4);
            if (n >= fontRenderer.func_78256_a(string)) continue;
            n = fontRenderer.func_78256_a(string);
        }
        int n4 = 222;
        int n5 = n4 - 108;
        int n6 = n5 + iInventory.func_70302_i_() / 9 * 18;
        int n7 = (guiScreen.field_146294_l - 40) / 2;
        int n8 = (guiScreen.field_146295_m - n6 - 18) / 2;
        FontRenderer fontRenderer = this.mc.field_71466_p;
        if (l2 == 0L) {
            return;
        }
        string = this.format(l2);
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b((float)n7, (float)n8, (float)0.0f);
        int n9 = 0;
        RenderUtil.startTop();
        fontRenderer.func_78276_b("Total: " + EnumChatFormatting.GREEN + string, 115, -20, -1);
        for (ItemStack itemStack : arrayList2) {
            int n10 = (Integer)hashMap.get(itemStack);
            long l5 = this.getPrice(itemStack) * (long)n10;
            String string2 = itemStack.func_82833_r() + " \u00a7rx" + n10 + ": " + EnumChatFormatting.YELLOW + this.format(l5);
            fontRenderer.func_78276_b(string2, 120, -10 + 10 * n9, -1);
            ++n9;
        }
        RenderUtil.endTop();
        RenderUtil.drawFastRoundedRect(110.0f, -25.0f, 120 + n + 5, -25 + (2 + n9) * 10, 5.0f, ColorUtils.addAlpha(new Color(Colors.GRAY.c), 180).getRGB());
        GlStateManager.func_179121_F();
    }

    public long getPrice(ItemStack itemStack) {
        return LbinData.getInstance().getPrice(itemStack);
    }

    public String stripColor(String string) {
        return this.STRIP_COLOR_PATTERN.matcher(string).replaceAll("");
    }

    public String cleanColor(String string) {
        return string.replaceAll("(?i)\\u00A7.", "");
    }

    public String format(long l2) {
        if (!((Boolean)this.format.getValue()).booleanValue()) {
            String string = l2 + "";
            StringBuffer stringBuffer = new StringBuffer(string);
            for (int i = string.length() - 3; i >= 0; i -= 3) {
                stringBuffer.insert(i, ",");
            }
            String string2 = stringBuffer.toString();
            if (string2.startsWith(",")) {
                string2 = string2.replaceFirst(",", "");
            }
            if (string2.startsWith("-,")) {
                string2 = string2.replaceFirst(",", "");
            }
            return string2;
        }
        if (l2 == Long.MIN_VALUE) {
            return this.format(-9223372036854775807L);
        }
        if (l2 < 0L) {
            return "-" + this.format(-l2);
        }
        if (l2 < 1000L) {
            return Long.toString(l2);
        }
        Map.Entry entry = suffixes.floorEntry(l2);
        Long l3 = entry.getKey();
        String string = (String)entry.getValue();
        long l4 = l2 / (l3 / 10L);
        boolean bl = l4 < 100L && (double)l4 / 10.0 != (double)(l4 / 10L);
        return bl ? (double)l4 / 10.0 + string : l4 / 10L + string;
    }

    private double lambda$onRender$0(HashMap hashMap, ItemStack itemStack) {
        return -(this.getPrice(itemStack) * (long)((Integer)hashMap.get(itemStack)).intValue());
    }

    static {
        suffixes = new TreeMap();
        suffixes.put(1000L, "k");
        suffixes.put(1000000L, "m");
        suffixes.put(1000000000L, "b");
    }
}

