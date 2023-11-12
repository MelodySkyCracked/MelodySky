/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  net.minecraft.client.Minecraft
 *  net.minecraft.scoreboard.Score
 *  net.minecraft.scoreboard.ScoreObjective
 *  net.minecraft.scoreboard.ScorePlayerTeam
 *  net.minecraft.scoreboard.Scoreboard
 *  net.minecraft.scoreboard.Team
 *  net.minecraft.util.StringUtils
 */
package xyz.Melody.Utils.game;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.StringUtils;
import xyz.Melody.Utils.other.StringUtil;

public final class ScoreboardUtils {
    public static final String cleanSB(String string) {
        char[] cArray = StringUtils.func_76338_a((String)string).toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : cArray) {
            if (c <= '\u0014' || c >= '\u007f') continue;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static final List getScoreboard() {
        ArrayList<String> arrayList = new ArrayList<String>();
        if (Minecraft.func_71410_x().field_71441_e == null) {
            return arrayList;
        }
        Scoreboard scoreboard = Minecraft.func_71410_x().field_71441_e.func_96441_U();
        if (scoreboard == null) {
            return arrayList;
        }
        ScoreObjective scoreObjective = scoreboard.func_96539_a(1);
        if (scoreObjective == null) {
            return arrayList;
        }
        ArrayList arrayList2 = scoreboard.func_96534_i(scoreObjective);
        ArrayList arrayList3 = arrayList2.stream().filter(ScoreboardUtils::lambda$getScoreboard$0).collect(Collectors.toList());
        arrayList2 = arrayList3.size() > 15 ? Lists.newArrayList((Iterable)Iterables.skip(arrayList3, (int)(arrayList2.size() - 15))) : arrayList3;
        for (Score score : arrayList2) {
            ScorePlayerTeam scorePlayerTeam = scoreboard.func_96509_i(score.func_96653_e());
            arrayList.add(ScorePlayerTeam.func_96667_a((Team)scorePlayerTeam, (String)score.func_96653_e()));
        }
        return arrayList;
    }

    public static final int getLinesNumber() {
        return ScoreboardUtils.getScoreboard().size();
    }

    public static final boolean scoreboardContains(String string) {
        boolean bl = false;
        List list = ScoreboardUtils.getScoreboard();
        for (String string2 : list) {
            string2 = ScoreboardUtils.cleanSB(string2);
            if (!(string2 = StringUtil.removeFormatting(string2)).contains(string)) continue;
            bl = true;
            break;
        }
        return bl;
    }

    public static final boolean scoreboardLowerContains(String string) {
        boolean bl = false;
        List list = ScoreboardUtils.getScoreboard();
        for (String string2 : list) {
            string2 = ScoreboardUtils.cleanSB(string2).toLowerCase();
            if (!(string2 = StringUtil.removeFormatting(string2)).contains(string)) continue;
            bl = true;
            break;
        }
        return bl;
    }

    public static final String getLineThatContains(String string) {
        String string2 = null;
        List list = ScoreboardUtils.getScoreboard();
        for (String string3 : list) {
            if (!(string3 = ScoreboardUtils.cleanSB(string3)).contains(string)) continue;
            string2 = string3;
            break;
        }
        return string2;
    }

    private static boolean lambda$getScoreboard$0(Score score) {
        return score != null && score.func_96653_e() != null && !score.func_96653_e().startsWith("#");
    }
}

