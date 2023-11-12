/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonObject
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraftforge.event.entity.player.ItemTooltipEvent
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.others;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.System.Managers.Skyblock.Auctions.AhBzManager;
import xyz.Melody.Utils.Item.ItemUtils;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class LbinData
extends Module {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public Option sbonly = new Option("Skyblock Only", true);
    public Option hbin = new Option("HBin", false);
    public Option fmt = new Option("Format", false);
    public Numbers delay = new Numbers("Delay(Min)", 1.0, 0.5, 10.0, 0.5);
    public static String colorPrefix = "6";
    private TimerUtil timer = new TimerUtil();
    private static LbinData INSTANCE;
    private static final NavigableMap suffixes;

    public LbinData() {
        super("FetchLBinData", new String[]{"lbin"}, ModuleType.Others);
        this.addValues(this.delay, this.sbonly, this.fmt, this.hbin);
        this.setModInfo("Show Auction or Bazaar Data as ToolTip.");
    }

    public static LbinData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = (LbinData)Client.instance.getModuleManager().getModuleByClass(LbinData.class);
        }
        return INSTANCE;
    }

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent itemTooltipEvent) {
        ItemStack itemStack = itemTooltipEvent.itemStack;
        if (Client.inSkyblock || !((Boolean)this.sbonly.getValue()).booleanValue()) {
            NBTTagCompound nBTTagCompound = itemStack.func_77978_p();
            String string = ItemUtils.getSkyBlockID(itemStack);
            if (string.equals("NotSBItem")) {
                return;
            }
            AhBzManager.AuctionData auctionData = this.getAuctionDataFor(itemStack);
            if (string.equals("ENCHANTED_BOOK")) {
                NBTTagCompound nBTTagCompound2 = nBTTagCompound.func_74775_l("ExtraAttributes").func_74775_l("enchantments");
                Set set = nBTTagCompound2.func_150296_c();
                int n = 0;
                for (String string2 : set) {
                    if (auctionData == null || ++n >= 10) continue;
                    if (auctionData.getPrices().size() == 0) {
                        itemTooltipEvent.toolTip.add("\u00a7" + colorPrefix + "Bazaar Sell\u00a77: " + (auctionData.getSellPrice() == -1L ? "\u00a78N/A" : "\u00a7e" + this.format(auctionData.getSellPrice())));
                        itemTooltipEvent.toolTip.add("\u00a7" + colorPrefix + "Bazaar Buy\u00a77: " + (auctionData.getBuyPrice() == -1L ? "\u00a78N/A" : "\u00a7e" + this.format(auctionData.getBuyPrice())));
                        continue;
                    }
                    if (auctionData.getSellPrice() != -1L) continue;
                    itemTooltipEvent.toolTip.add("\u00a7" + colorPrefix + "Lowest Bin\u00a77: " + (auctionData.getPrices().size() != 0 ? "\u00a7e" + this.format((Long)auctionData.getPrices().first()) : "\u00a78N/A"));
                    if (!((Boolean)this.hbin.getValue()).booleanValue()) continue;
                    itemTooltipEvent.toolTip.add("\u00a7" + colorPrefix + "Highest Bin\u00a77: " + (auctionData.getPrices().size() != 0 ? "\u00a7e" + this.format((Long)auctionData.getPrices().last()) : "\u00a78N/A"));
                }
                if (n >= 10) {
                    itemTooltipEvent.toolTip.add("\u00a77" + (n - 10) + " more enchants... ");
                }
            } else if (string.equals("PET")) {
                if (ItemUtils.getPetInfo(itemStack) != null) {
                    NBTTagCompound nBTTagCompound3 = nBTTagCompound.func_74775_l("ExtraAttributes");
                    JsonObject jsonObject = (JsonObject)this.gson.fromJson(nBTTagCompound3.func_74779_i("petInfo"), JsonObject.class);
                    String string3 = "idk";
                    if (jsonObject != null) {
                        if (jsonObject.has("type") && jsonObject.has("tier")) {
                            string3 = "PET_" + jsonObject.get("type").getAsString() + "_" + jsonObject.get("tier").getAsString();
                        }
                        if (string3 != "idk" && auctionData != null && auctionData.getSellPrice() == -1L) {
                            itemTooltipEvent.toolTip.add("\u00a7" + colorPrefix + "Lowest Bin\u00a77: " + (auctionData.getPrices().size() != 0 ? "\u00a7e" + this.format((Long)auctionData.getPrices().first()) : "\u00a78N/A"));
                            if (((Boolean)this.hbin.getValue()).booleanValue()) {
                                itemTooltipEvent.toolTip.add("\u00a7" + colorPrefix + "Highest Bin\u00a77: " + (auctionData.getPrices().size() != 0 ? "\u00a7e" + this.format((Long)auctionData.getPrices().last()) : "\u00a78N/A"));
                            }
                        }
                    }
                }
            } else if (string.equals("RUNE")) {
                NBTTagCompound nBTTagCompound4 = nBTTagCompound.func_74775_l("ExtraAttributes").func_74775_l("runes");
                Set set = nBTTagCompound4.func_150296_c();
                for (String string4 : set) {
                    if (auctionData == null || auctionData.getSellPrice() != -1L) continue;
                    itemTooltipEvent.toolTip.add("\u00a7" + colorPrefix + "Lowest Bin\u00a77: " + (auctionData.getPrices().size() != 0 ? "\u00a7e" + this.format((Long)auctionData.getPrices().first()) : "\u00a78N/A"));
                    if (!((Boolean)this.hbin.getValue()).booleanValue()) continue;
                    itemTooltipEvent.toolTip.add("\u00a7" + colorPrefix + "Highest Bin\u00a77: " + (auctionData.getPrices().size() != 0 ? "\u00a7e" + this.format((Long)auctionData.getPrices().last()) : "\u00a78N/A"));
                }
            } else if (string.equals("ATTRIBUTE_SHARD")) {
                NBTTagCompound nBTTagCompound5 = nBTTagCompound.func_74775_l("ExtraAttributes").func_74775_l("attributes");
                Set set = nBTTagCompound5.func_150296_c();
                for (String string5 : set) {
                    if (auctionData == null || auctionData.getSellPrice() != -1L) continue;
                    itemTooltipEvent.toolTip.add("\u00a7" + colorPrefix + "Lowest Bin\u00a77: " + (auctionData.getPrices().size() != 0 ? "\u00a7e" + this.format((Long)auctionData.getPrices().first()) : "\u00a78N/A"));
                    if (!((Boolean)this.hbin.getValue()).booleanValue()) continue;
                    itemTooltipEvent.toolTip.add("\u00a7" + colorPrefix + "Highest Bin\u00a77: " + (auctionData.getPrices().size() != 0 ? "\u00a7e" + this.format((Long)auctionData.getPrices().last()) : "\u00a78N/A"));
                }
            } else if (string.equals("POTION")) {
                NBTTagCompound nBTTagCompound6 = nBTTagCompound.func_74775_l("ExtraAttributes");
                if (nBTTagCompound6.func_74764_b("potion") && nBTTagCompound6.func_74764_b("potion_level") && auctionData != null && auctionData.getSellPrice() == -1L) {
                    itemTooltipEvent.toolTip.add("\u00a7" + colorPrefix + "Lowest Bin\u00a77: " + (auctionData.getPrices().size() != 0 ? "\u00a7e" + this.format((Long)auctionData.getPrices().first()) : "\u00a78N/A"));
                    if (((Boolean)this.hbin.getValue()).booleanValue()) {
                        itemTooltipEvent.toolTip.add("\u00a7" + colorPrefix + "Highest Bin\u00a77: " + (auctionData.getPrices().size() != 0 ? "\u00a7e" + this.format((Long)auctionData.getPrices().last()) : "\u00a78N/A"));
                    }
                }
            } else {
                itemTooltipEvent.toolTip.add("\u00a7f");
                if (auctionData != null) {
                    if (auctionData.getSellPrice() == -1L) {
                        ItemStack itemStack2 = itemTooltipEvent.itemStack;
                        int n = itemStack2.field_77994_a;
                        long l2 = (Long)auctionData.getPrices().first();
                        long l3 = l2 * (long)n;
                        String string6 = l3 == l2 ? "" : EnumChatFormatting.GRAY + " (" + this.format(l2) + " each)";
                        itemTooltipEvent.toolTip.add("\u00a7" + colorPrefix + "Lowest Bin\u00a77: " + (auctionData.getPrices().size() != 0 ? "\u00a7e" + this.format(l3) + string6 : "\u00a78N/A"));
                        if (((Boolean)this.hbin.getValue()).booleanValue()) {
                            itemTooltipEvent.toolTip.add("\u00a7" + colorPrefix + "Highest Bin\u00a77: " + (auctionData.getPrices().size() != 0 ? "\u00a7e" + this.format((Long)auctionData.getPrices().last()) : "\u00a78N/A"));
                        }
                    } else if (auctionData.getPrices().size() == 0) {
                        long l4;
                        ItemStack itemStack3 = itemTooltipEvent.itemStack;
                        int n = itemStack3.field_77994_a;
                        long l5 = auctionData.getSellPrice();
                        String string7 = l5 == (l4 = l5 * (long)n) ? "" : EnumChatFormatting.GRAY + " (" + this.format(l5) + " each)";
                        itemTooltipEvent.toolTip.add("\u00a7" + colorPrefix + "Bazaar Sell\u00a77: " + (l5 == -1L ? "\u00a78N/A" : "\u00a7e" + this.format(l4) + string7));
                        itemTooltipEvent.toolTip.add("\u00a7" + colorPrefix + "Bazaar Buy\u00a77: " + (auctionData.getBuyPrice() == -1L ? "\u00a78N/A" : "\u00a7e" + this.format(auctionData.getBuyPrice())));
                    }
                }
            }
        }
    }

    public long getPrice(ItemStack itemStack) {
        AhBzManager.AuctionData auctionData = LbinData.getInstance().getAuctionDataFor(itemStack);
        if (auctionData != null) {
            if (auctionData.getPrices().size() == 0) {
                return auctionData.getSellPrice();
            }
            if (auctionData.getSellPrice() == -1L) {
                return (Long)auctionData.getPrices().first();
            }
        }
        return 0L;
    }

    public AhBzManager.AuctionData getAuctionDataFor(ItemStack itemStack) {
        NBTTagCompound nBTTagCompound;
        NBTTagCompound nBTTagCompound2 = itemStack.func_77978_p();
        String string = ItemUtils.getSkyBlockID(itemStack);
        if (string.equals("ENCHANTED_BOOK")) {
            NBTTagCompound nBTTagCompound3 = nBTTagCompound2.func_74775_l("ExtraAttributes").func_74775_l("enchantments");
            Set set = nBTTagCompound3.func_150296_c();
            Iterator iterator = set.iterator();
            if (iterator.hasNext()) {
                String string2 = (String)iterator.next();
                String string3 = "ENCHANTMENT_" + string2.toUpperCase() + "_" + nBTTagCompound3.func_74762_e(string2);
                return (AhBzManager.AuctionData)AhBzManager.auctions.get(string3);
            }
        } else if (string.equals("PET")) {
            if (ItemUtils.getPetInfo(itemStack) != null) {
                NBTTagCompound nBTTagCompound4 = nBTTagCompound2.func_74775_l("ExtraAttributes");
                JsonObject jsonObject = (JsonObject)this.gson.fromJson(nBTTagCompound4.func_74779_i("petInfo"), JsonObject.class);
                String string4 = "idk";
                if (jsonObject != null) {
                    if (jsonObject.has("type") && jsonObject.has("tier")) {
                        string4 = "PET_" + jsonObject.get("type").getAsString() + "_" + jsonObject.get("tier").getAsString();
                    }
                    if (string4 != "idk") {
                        return (AhBzManager.AuctionData)AhBzManager.auctions.get(string4);
                    }
                }
            }
        } else if (string.equals("RUNE")) {
            NBTTagCompound nBTTagCompound5 = nBTTagCompound2.func_74775_l("ExtraAttributes").func_74775_l("runes");
            Set set = nBTTagCompound5.func_150296_c();
            Iterator iterator = set.iterator();
            if (iterator.hasNext()) {
                String string5 = (String)iterator.next();
                String string6 = "RUNE_" + string5.toUpperCase() + "_" + nBTTagCompound5.func_74762_e(string5);
                return (AhBzManager.AuctionData)AhBzManager.auctions.get(string6);
            }
        } else if (string.equals("ATTRIBUTE_SHARD")) {
            NBTTagCompound nBTTagCompound6 = nBTTagCompound2.func_74775_l("ExtraAttributes").func_74775_l("attributes");
            Set set = nBTTagCompound6.func_150296_c();
            Iterator iterator = set.iterator();
            if (iterator.hasNext()) {
                String string7 = (String)iterator.next();
                String string8 = "ATTRIBUTE_SHARD_" + string7.toUpperCase() + "_" + nBTTagCompound6.func_74762_e(string7);
                return (AhBzManager.AuctionData)AhBzManager.auctions.get(string8);
            }
        } else if (string.equals("POTION") && (nBTTagCompound = nBTTagCompound2.func_74775_l("ExtraAttributes")).func_74764_b("potion") && nBTTagCompound.func_74764_b("potion_level")) {
            String string9 = nBTTagCompound.func_74764_b("enhanced") ? "ENHANCED" : "NOTENHANCED";
            String string10 = nBTTagCompound.func_74764_b("extended") ? "EXTENDED" : "UNEXTENDED";
            String string11 = nBTTagCompound.func_74764_b("splash") ? "SPLASH" : "DRINKABLE";
            String string12 = "POTION_" + nBTTagCompound.func_74779_i("potion").toUpperCase() + "_" + nBTTagCompound.func_74762_e("potion_level") + "_" + string9 + "_" + string10 + "_" + string11;
            return (AhBzManager.AuctionData)AhBzManager.auctions.get(string12);
        }
        return (AhBzManager.AuctionData)AhBzManager.auctions.get(string);
    }

    public String format(long l2) {
        if (!((Boolean)this.fmt.getValue()).booleanValue()) {
            String string = l2 + "";
            StringBuffer stringBuffer = new StringBuffer(string);
            for (int i = string.length() - 3; i >= 0; i -= 3) {
                stringBuffer.insert(i, ",");
            }
            String string2 = stringBuffer.toString();
            if (string2.startsWith(",")) {
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

    static {
        suffixes = new TreeMap();
        suffixes.put(1000L, "k");
        suffixes.put(1000000L, "m");
        suffixes.put(1000000000L, "b");
    }
}

