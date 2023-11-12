/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.nbt.NBTTagList
 */
package xyz.Melody.Utils.Item;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import xyz.Melody.Utils.Helper;

public final class ItemUtils {
    public static Minecraft mc = Minecraft.func_71410_x();

    public static boolean useSBItem(String string, boolean bl) {
        if (string.toUpperCase() < !bl) {
            return true;
        }
        Helper.sendMessage("Missing Item in Hotbar: " + string);
        return false;
    }

    public static boolean useSBItem(String string) {
        if (string.toUpperCase() < true) {
            return true;
        }
        Helper.sendMessage("Missing Item in Hotbar: " + string);
        return false;
    }

    public static boolean hasItemInHotbar(String string) {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = ItemUtils.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (!string.equals(ItemUtils.getSkyBlockID(itemStack))) continue;
            return true;
        }
        return false;
    }

    public static NBTTagCompound getExtraAttributes(ItemStack itemStack) {
        if (!itemStack.func_77942_o()) {
            return null;
        }
        return itemStack.func_179543_a("ExtraAttributes", false);
    }

    public static String getSkyBlockID(ItemStack itemStack) {
        NBTTagCompound nBTTagCompound;
        if (itemStack != null && (nBTTagCompound = itemStack.func_179543_a("ExtraAttributes", false)) != null && nBTTagCompound.func_74764_b("id")) {
            return nBTTagCompound.func_74779_i("id");
        }
        return "NotSBItem";
    }

    public static NBTTagCompound getPetInfo(ItemStack itemStack) {
        if (ItemUtils.getSkyBlockID(itemStack) != "NotSBItem") {
            NBTTagCompound nBTTagCompound = itemStack.func_77978_p();
            NBTTagCompound nBTTagCompound2 = nBTTagCompound.func_74775_l("ExtraAttributes").func_74775_l("petInfo");
            return nBTTagCompound2;
        }
        return null;
    }

    public static String[] getLoreFromNBT(NBTTagCompound nBTTagCompound) {
        String[] stringArray = new String[]{};
        NBTTagCompound nBTTagCompound2 = nBTTagCompound.func_74775_l("display");
        if (nBTTagCompound2.func_150297_b("Lore", 9)) {
            NBTTagList nBTTagList = nBTTagCompound2.func_150295_c("Lore", 8);
            stringArray = new String[nBTTagList.func_74745_c()];
            for (int i = 0; i < nBTTagList.func_74745_c(); ++i) {
                stringArray[i] = nBTTagList.func_150307_f(i);
            }
        }
        return stringArray;
    }
}

