/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.BlockPos
 */
package xyz.Melody.Utils.configs;

import java.util.List;
import java.util.StringTokenizer;
import net.minecraft.util.BlockPos;
import xyz.Melody.System.Managers.Client.FileManager;
import xyz.Melody.module.modules.render.FairySoulESP;

public class FairySoulsConfig {
    public void read() {
        FairySoulESP fairySoulESP = FairySoulESP.getInstance();
        List list = FileManager.read("FairySouls.txt");
        for (String string : list) {
            if (string == "" || string == null) continue;
            StringTokenizer stringTokenizer = new StringTokenizer(string, "#");
            while (stringTokenizer.hasMoreTokens()) {
                String string2 = stringTokenizer.nextToken();
                if (string2 == null || string2 == "") continue;
                StringTokenizer stringTokenizer2 = new StringTokenizer(string2, ":");
                int n = Integer.parseInt(stringTokenizer2.nextToken());
                int n2 = Integer.parseInt(stringTokenizer2.nextToken());
                int n3 = Integer.parseInt(stringTokenizer2.nextToken());
                fairySoulESP.found.add(new BlockPos(n, n2, n3));
            }
        }
    }

    public void save() {
        FairySoulESP fairySoulESP = FairySoulESP.getInstance();
        String string = "";
        for (BlockPos blockPos : fairySoulESP.found) {
            int n = blockPos.func_177958_n();
            int n2 = blockPos.func_177956_o();
            int n3 = blockPos.func_177952_p();
            string = string + n + ":" + n2 + ":" + n3 + "#";
        }
        FileManager.save("FairySouls.txt", string, false);
    }
}

