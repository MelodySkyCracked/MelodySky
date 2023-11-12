/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ComparisonChain
 *  com.google.common.collect.Ordering
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiPlayerTabOverlay
 *  net.minecraft.client.network.NetworkPlayerInfo
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.util.StringUtils
 *  net.minecraft.world.WorldSettings$GameType
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package xyz.Melody.Utils.game;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.StringUtils;
import net.minecraft.world.WorldSettings;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.Melody.Utils.game.I;

public final class PlayerListUtils {
    private static final Minecraft mc = Minecraft.func_71410_x();
    private static final Ordering playerOrdering = Ordering.from((Comparator)new PlayerComparator(null));
    public static final Ordering playerInfoOrdering2 = new I();

    public static ArrayList getNames() {
        GuiPlayerTabOverlay guiPlayerTabOverlay = PlayerListUtils.getTabList();
        ArrayList<String> arrayList = new ArrayList<String>();
        if (PlayerListUtils.mc.field_71439_g == null || guiPlayerTabOverlay == null) {
            return arrayList;
        }
        Collection collection = PlayerListUtils.mc.field_71439_g.field_71174_a.func_175106_d();
        List list = playerOrdering.sortedCopy((Iterable)collection);
        for (NetworkPlayerInfo networkPlayerInfo : list) {
            arrayList.add(StringUtils.func_76338_a((String)guiPlayerTabOverlay.func_175243_a(networkPlayerInfo)));
        }
        return arrayList;
    }

    public static GuiPlayerTabOverlay getTabList() {
        return PlayerListUtils.mc.field_71456_v.func_175181_h();
    }

    public static List getTabEntries() {
        if (Minecraft.func_71410_x().field_71439_g == null) {
            return Collections.emptyList();
        }
        return playerInfoOrdering2.sortedCopy((Iterable)Minecraft.func_71410_x().field_71439_g.field_71174_a.func_175106_d());
    }

    public static List getTabListListStr() {
        return PlayerListUtils.getTabEntries().stream().map(PlayerListUtils::lambda$getTabListListStr$0).collect(Collectors.toList());
    }

    public static boolean tabContains(String string) {
        List list = playerOrdering.sortedCopy((Iterable)Minecraft.func_71410_x().field_71439_g.field_71174_a.func_175106_d());
        for (NetworkPlayerInfo networkPlayerInfo : list) {
            String string2 = StringUtils.func_76338_a((String)Minecraft.func_71410_x().field_71456_v.func_175181_h().func_175243_a(networkPlayerInfo));
            if (!string2.contains(string)) continue;
            return true;
        }
        return false;
    }

    public static String copyContainsLine(String string) {
        List list = playerOrdering.sortedCopy((Iterable)Minecraft.func_71410_x().field_71439_g.field_71174_a.func_175106_d());
        for (NetworkPlayerInfo networkPlayerInfo : list) {
            String string2 = Minecraft.func_71410_x().field_71456_v.func_175181_h().func_175243_a(networkPlayerInfo);
            if (!string2.contains(string)) continue;
            return string2;
        }
        return null;
    }

    public static boolean isInTablist(EntityPlayer entityPlayer) {
        if (mc.func_71356_B()) {
            return true;
        }
        for (Object e : mc.func_147114_u().func_175106_d()) {
            NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)e;
            if (!networkPlayerInfo.func_178845_a().getName().equalsIgnoreCase(entityPlayer.func_70005_c_())) continue;
            return true;
        }
        return false;
    }

    private static String lambda$getTabListListStr$0(NetworkPlayerInfo networkPlayerInfo) {
        return Minecraft.func_71410_x().field_71456_v.func_175181_h().func_175243_a(networkPlayerInfo);
    }

    @SideOnly(value=Side.CLIENT)
    static class PlayerComparator
    implements Comparator {
        private PlayerComparator() {
        }

        public int compare(NetworkPlayerInfo networkPlayerInfo, NetworkPlayerInfo networkPlayerInfo2) {
            ScorePlayerTeam scorePlayerTeam = networkPlayerInfo.func_178850_i();
            ScorePlayerTeam scorePlayerTeam2 = networkPlayerInfo2.func_178850_i();
            return ComparisonChain.start().compareTrueFirst(networkPlayerInfo.func_178848_b() != WorldSettings.GameType.SPECTATOR, networkPlayerInfo2.func_178848_b() != WorldSettings.GameType.SPECTATOR).compare((Comparable)((Object)(scorePlayerTeam != null ? scorePlayerTeam.func_96661_b() : "")), (Comparable)((Object)(scorePlayerTeam2 != null ? scorePlayerTeam2.func_96661_b() : ""))).compare((Comparable)((Object)networkPlayerInfo.func_178845_a().getName()), (Comparable)((Object)networkPlayerInfo2.func_178845_a().getName())).result();
        }

        public int compare(Object object, Object object2) {
            return this.compare((NetworkPlayerInfo)object, (NetworkPlayerInfo)object2);
        }

        PlayerComparator(I i) {
            this();
        }
    }
}

