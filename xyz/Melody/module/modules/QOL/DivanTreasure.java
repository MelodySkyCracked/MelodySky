/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonParser
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.client.entity.EntityPlayerSP
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.StringUtils
 *  net.minecraft.util.Vec3
 *  net.minecraftforge.client.event.ClientChatReceivedEvent
 *  net.minecraftforge.event.world.WorldEvent$Unload
 *  net.minecraftforge.fml.common.eventhandler.EventPriority
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.QOL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Objects;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.MultiThreads;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class DivanTreasure
extends Module {
    private static BlockPos anchor;
    private static final HashSet relativeChestCoords;
    private static final HashSet absoluteChestCoords;
    private static BlockPos ignoreBlockPos;
    private static final HashSet predictedChestLocations;
    private static Vec3 lastPos;
    private static long lastScan;
    private static boolean lobbyInitialized;

    public DivanTreasure() {
        super("DivanHelper", new String[]{"divantreasure", "divanhelper"}, ModuleType.QOL);
        this.setModInfo("Calculate Chest Position in Divan's Mine.");
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse((Reader)new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("assets/minecraft/Melody/divan_treasure_coords.json")))));
        JsonArray jsonArray = (JsonArray)jsonElement;
        for (JsonElement jsonElement2 : jsonArray) {
            relativeChestCoords.add(new BlockPos(jsonElement2.getAsJsonArray().get(0).getAsInt(), jsonElement2.getAsJsonArray().get(1).getAsInt(), jsonElement2.getAsJsonArray().get(2).getAsInt()));
        }
    }

    @SubscribeEvent(priority=EventPriority.LOW, receiveCanceled=true)
    public void onGuiChat(ClientChatReceivedEvent clientChatReceivedEvent) {
        if (this.mc.field_71441_e == null || this.mc.field_71439_g == null) {
            return;
        }
        String string = StringUtils.func_76338_a((String)clientChatReceivedEvent.message.func_150260_c());
        if (string.contains("TREASURE")) {
            if (!lobbyInitialized && System.currentTimeMillis() - lastScan > 3000L) {
                lastScan = System.currentTimeMillis();
                MultiThreads.runAsync(this::scanChunks);
                if (anchor != null) {
                    absoluteChestCoords.clear();
                    for (BlockPos blockPos : relativeChestCoords) {
                        BlockPos blockPos2 = new BlockPos(anchor.func_177958_n() - blockPos.func_177958_n(), anchor.func_177956_o() - blockPos.func_177956_o() + 1, anchor.func_177952_p() - blockPos.func_177952_p());
                        absoluteChestCoords.add(blockPos2);
                    }
                    lobbyInitialized = true;
                } else {
                    return;
                }
            }
            EntityPlayerSP entityPlayerSP = this.mc.field_71439_g;
            if (lastPos == null || entityPlayerSP.field_70165_t != DivanTreasure.lastPos.field_72450_a || entityPlayerSP.field_70163_u != DivanTreasure.lastPos.field_72448_b || entityPlayerSP.field_70161_v != DivanTreasure.lastPos.field_72449_c) {
                lastPos = entityPlayerSP.func_174791_d();
                return;
            }
            double d = Double.parseDouble(string.split("TREASURE: ")[1].split("m")[0].replaceAll("(?!\\.)\\D", ""));
            for (BlockPos blockPos : absoluteChestCoords) {
                double d2 = Math.sqrt(Math.pow(entityPlayerSP.field_70165_t - (double)blockPos.func_177958_n(), 2.0) + Math.pow(entityPlayerSP.field_70163_u - (double)blockPos.func_177956_o(), 2.0) + Math.pow(entityPlayerSP.field_70161_v - (double)blockPos.func_177952_p(), 2.0));
                if ((double)Math.round(d2 * 10.0) / 10.0 != d) continue;
                if (blockPos.func_177982_a(0, -1, 0).equals((Object)ignoreBlockPos)) {
                    ignoreBlockPos = null;
                    return;
                }
                if (!predictedChestLocations.contains(blockPos.func_177982_a(0, -1, 0))) {
                    this.mc.field_71439_g.func_85030_a("random.orb", 1.0f, 0.5f);
                }
                predictedChestLocations.clear();
                predictedChestLocations.add(blockPos.func_177982_a(0, -1, 0));
            }
        }
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent clientChatReceivedEvent) {
        if (this.mc.field_71441_e == null || this.mc.field_71439_g == null) {
            return;
        }
        if (clientChatReceivedEvent.type != 0) {
            return;
        }
        String string = StringUtils.func_76338_a((String)clientChatReceivedEvent.message.func_150260_c());
        if (string.startsWith("You found") && string.endsWith("Metal Detector!")) {
            if (predictedChestLocations.iterator().hasNext()) {
                ignoreBlockPos = (BlockPos)predictedChestLocations.iterator().next();
            }
            predictedChestLocations.clear();
        }
    }

    @EventHandler
    public void onRender(EventRender3D eventRender3D) {
        if (this.mc.field_71441_e == null || this.mc.field_71439_g == null) {
            return;
        }
        predictedChestLocations.forEach(arg_0 -> DivanTreasure.lambda$onRender$0(eventRender3D, arg_0));
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload unload) {
        anchor = null;
        ignoreBlockPos = null;
        lastScan = 0L;
        lastPos = null;
        predictedChestLocations.clear();
    }

    private void scanChunks() {
        int n = (int)this.mc.field_71439_g.field_70165_t;
        int n2 = (int)this.mc.field_71439_g.field_70163_u;
        int n3 = (int)this.mc.field_71439_g.field_70161_v;
        for (int i = n - 50; i < n + 50; ++i) {
            for (int j = n2 + 35; j > n2; --j) {
                for (int k = n3 - 50; k < n3 + 50; ++k) {
                    if (this.getBlockState(new BlockPos(i, j, k)).func_177230_c() != Blocks.field_150370_cb || this.getBlockState(new BlockPos(i, j + 13, k)).func_177230_c() != Blocks.field_180401_cv) continue;
                    anchor = this.verifyAnchor(i, j + 13, k);
                    return;
                }
            }
        }
    }

    private BlockPos verifyAnchor(int n, int n2, int n3) {
        boolean bl = true;
        if (this.getBlockState(new BlockPos(n, n2, n3)).func_177230_c() != Blocks.field_180401_cv) {
            return new BlockPos(n, n2, n3);
        }
        while (bl) {
            bl = false;
            if (this.getBlockState(new BlockPos(n + 1, n2, n3)).func_177230_c() == Blocks.field_180401_cv) {
                ++n;
                bl = true;
            }
            if (this.getBlockState(new BlockPos(n, n2 - 1, n3)).func_177230_c() == Blocks.field_180401_cv) {
                --n2;
                bl = true;
            }
            if (this.getBlockState(new BlockPos(n, n2, n3 + 1)).func_177230_c() != Blocks.field_180401_cv) continue;
            ++n3;
            bl = true;
        }
        return new BlockPos(n, n2, n3);
    }

    private IBlockState getBlockState(BlockPos blockPos) {
        return this.mc.field_71441_e.func_180495_p(blockPos);
    }

    private static void lambda$onRender$0(EventRender3D eventRender3D, BlockPos blockPos) {
        RenderUtil.drawSolidBlockESP(blockPos, Color.GREEN.getRGB(), eventRender3D.getPartialTicks());
        RenderUtil.renderBeacon(blockPos, new Color(20, 20, 20, 255), eventRender3D.getPartialTicks());
        RenderUtil.trace(blockPos, Colors.BLUE.c);
    }

    static {
        relativeChestCoords = new HashSet();
        absoluteChestCoords = new HashSet();
        predictedChestLocations = new HashSet();
        lastScan = 0L;
        lobbyInitialized = false;
    }
}

