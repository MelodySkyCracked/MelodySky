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
 */
package xyz.Melody.System.Commands.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import xyz.Melody.System.Commands.Command;
import xyz.Melody.Utils.Helper;
import xyz.Melody.Utils.Item.ItemUtils;

public final class ShowItemSBID
extends Command {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ShowItemSBID() {
        super("sbid", new String[]{"id"}, "", "FUCK YOU!");
    }

    @Override
    public String execute(String[] stringArray) {
        ItemStack itemStack;
        ItemStack itemStack2 = itemStack = this.mc.field_71439_g.func_70694_bm() != null ? this.mc.field_71439_g.func_70694_bm() : null;
        if (itemStack != null) {
            Object object;
            NBTTagCompound nBTTagCompound;
            String string3;
            Object object2;
            Object object32;
            NBTTagCompound nBTTagCompound2 = itemStack.func_77978_p();
            String string2 = ItemUtils.getSkyBlockID(itemStack);
            Helper.sendMessage(string2);
            for (Object object32 : nBTTagCompound2.func_150296_c()) {
                Helper.sendMessageWithoutPrefix(EnumChatFormatting.RED + "> " + (String)object32);
                object2 = nBTTagCompound2.func_74775_l((String)object32);
                for (String string3 : object2.func_150296_c()) {
                    Helper.sendMessageWithoutPrefix(EnumChatFormatting.GREEN + "   > " + string3);
                    Helper.sendMessageWithoutPrefix("      " + object2.func_74775_l(string3).func_150296_c());
                }
            }
            if (string2.equals("NotSBItem")) {
                Helper.sendMessage("Error: " + string2);
                return null;
            }
            if (string2.equals("ENCHANTED_BOOK")) {
                nBTTagCompound = nBTTagCompound2.func_74775_l("ExtraAttributes").func_74775_l("enchantments");
                object32 = nBTTagCompound.func_150296_c();
                object2 = object32.iterator();
                while (object2.hasNext()) {
                    object = (String)object2.next();
                    string3 = "ENCHANTMENT_" + ((String)object).toUpperCase() + "_" + nBTTagCompound.func_74762_e((String)object);
                    Helper.sendMessage("Attribute Enchant Info: " + string3);
                }
                Helper.sendMessage("Skyblock ID: " + string2);
                return null;
            }
            if (string2.equals("PET")) {
                if (ItemUtils.getPetInfo(itemStack) != null) {
                    nBTTagCompound = nBTTagCompound2.func_74775_l("ExtraAttributes");
                    object32 = (JsonObject)this.gson.fromJson(nBTTagCompound.func_74779_i("petInfo"), JsonObject.class);
                    object2 = "idk";
                    if (object32.has("type") && object32.has("tier")) {
                        object2 = "PET_" + object32.get("type").getAsString() + "_" + object32.get("tier").getAsString();
                    }
                    Helper.sendMessage("Attribute Pet Info: " + (String)object2);
                    Helper.sendMessage("Skyblock ID: " + string2);
                    return null;
                }
            } else if (string2.equals("RUNE")) {
                nBTTagCompound = nBTTagCompound2.func_74775_l("ExtraAttributes").func_74775_l("runes");
                object32 = nBTTagCompound.func_150296_c();
                object2 = object32.iterator();
                while (object2.hasNext()) {
                    object = (String)object2.next();
                    string3 = "RUNE_" + ((String)object).toUpperCase() + "_" + nBTTagCompound.func_74762_e((String)object);
                    Helper.sendMessage("Attribute Rune Info: " + string3);
                }
                Helper.sendMessage("Skyblock ID: " + string2);
                return null;
            }
        } else {
            Helper.sendMessage("Please Hold an Item to View it's Skyblock ID.");
        }
        return null;
    }
}

