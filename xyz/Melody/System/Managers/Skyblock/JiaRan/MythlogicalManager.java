/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.play.server.S2APacketParticles
 *  net.minecraft.util.BlockPos
 *  net.minecraft.util.EnumParticleTypes
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.FMLCommonHandler
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.opengl.GL11
 */
package xyz.Melody.System.Managers.Skyblock.JiaRan;

import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import xyz.Melody.Client;
import xyz.Melody.Event.EventBus;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.EventPacketRecieve;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.System.Managers.Skyblock.Area.Areas;
import xyz.Melody.Utils.Colors;
import xyz.Melody.Utils.Item.ItemUtils;
import xyz.Melody.Utils.math.Vec3f;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.modules.others.DianaHelper;

public class MythlogicalManager {
    public static MythlogicalManager instance;
    public WorldRenderer worldRenderer;
    public Tessellator tessellator;
    private TimerUtil timer = new TimerUtil();
    private int particleCounter = 0;
    private boolean paused = false;
    private float xDiff = 0.0f;
    private float yDiff = 0.0f;
    private float zDiff = 0.0f;
    private BlockPos footstepCoord = null;
    private ArrayList possibleCoords = new ArrayList();
    private ArrayList particleCoords = new ArrayList();
    private ArrayList renders = new ArrayList();

    public void init() {
        Client.instance.logger.info("[MelodySky] [CONSOL] Initializing Mythlogical Manager.");
        instance = this;
        this.tessellator = Tessellator.func_178181_a();
        this.worldRenderer = this.tessellator.func_178180_c();
        EventBus.getInstance().register(this);
        FMLCommonHandler.instance().bus().register((Object)this);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @SubscribeEvent
    public void clear(WorldEvent.Load load) {
        this.timer.reset();
        this.particleCounter = 0;
        this.paused = false;
        this.xDiff = 0.0f;
        this.yDiff = 0.0f;
        this.zDiff = 0.0f;
        this.footstepCoord = null;
        this.possibleCoords.clear();
        this.particleCoords.clear();
        this.renders.clear();
    }

    @EventHandler
    private void onTick(EventTick eventTick) {
        if (!DianaHelper.getInstance().isEnabled()) {
            return;
        }
        if (Client.instance.sbArea.getCurrentArea() != Areas.HUB) {
            return;
        }
        if (ItemUtils.getSkyBlockID(Minecraft.func_71410_x().field_71439_g.func_70694_bm()).equals("ANCESTRAL_SPADE") && Minecraft.func_71410_x().field_71474_y.field_74313_G.func_151470_d()) {
            this.particleCoords.clear();
            this.footstepCoord = null;
            this.timer.reset();
            this.paused = false;
        }
        if (this.timer.hasReached(250.0)) {
            if (this.particleCoords.size() == this.particleCounter) {
                this.paused = true;
            }
            if (this.particleCoords.size() != this.particleCounter) {
                this.possibleCoords.clear();
                this.paused = false;
            }
            this.particleCounter = this.particleCoords.size();
            this.timer.reset();
        }
        if (!this.paused) {
            this.possibleCoords.clear();
        }
        if (this.particleCoords.size() > 3) {
            Vec3f vec3f = (Vec3f)this.particleCoords.get(this.particleCoords.size() - 2);
            Vec3f vec3f2 = (Vec3f)this.particleCoords.get(this.particleCoords.size() - 1);
            this.xDiff = (float)(vec3f2.getX() - vec3f.getX());
            double d = vec3f2.getY() - vec3f.getY();
            this.yDiff = (float)(vec3f2.getY() - vec3f.getY() - d / Math.abs(d) * (double)0.05f);
            this.zDiff = (float)(vec3f2.getZ() - vec3f.getZ());
            float f = (float)((Vec3f)this.particleCoords.get(0)).getX();
            float f2 = (float)((Vec3f)this.particleCoords.get(0)).getY();
            float f3 = (float)((Vec3f)this.particleCoords.get(0)).getZ();
            for (int i = 0; i < 1145 && this.possibleCoords.size() <= 50; ++i) {
                BlockPos blockPos = new BlockPos((double)(f += this.xDiff), (double)(f2 += this.yDiff), (double)(f3 += this.zDiff));
                for (int j = -4; j < 4; ++j) {
                    BlockPos blockPos2;
                    BlockPos blockPos3 = blockPos.func_177981_b(j);
                    if (Minecraft.func_71410_x().field_71441_e.func_180495_p(blockPos3.func_177984_a()).func_177230_c() != Blocks.field_150350_a || (blockPos2 = Minecraft.func_71410_x().field_71441_e.func_180495_p(blockPos3).func_177230_c()) != Blocks.field_150349_c) continue;
                    this.possibleCoords.add(blockPos3);
                }
            }
            if (!this.possibleCoords.isEmpty()) {
                float f4 = ((BlockPos)this.possibleCoords.get(this.possibleCoords.size() - 1)).func_177958_n();
                float f5 = ((BlockPos)this.possibleCoords.get(this.possibleCoords.size() - 1)).func_177952_p();
                this.possibleCoords.removeIf(arg_0 -> MythlogicalManager.lambda$onTick$0(f4, f5, arg_0));
            }
            float f6 = 0.0f;
            float f7 = 0.0f;
            float f8 = 0.0f;
            for (BlockPos blockPos2 : this.possibleCoords) {
                f6 += (float)blockPos2.func_177958_n();
                f7 += (float)blockPos2.func_177956_o();
                f8 += (float)blockPos2.func_177952_p();
            }
            f6 /= (float)this.possibleCoords.size();
            f7 /= (float)this.possibleCoords.size();
            f8 /= (float)this.possibleCoords.size();
            if (this.possibleCoords.size() > 1) {
                if (Math.abs(f6 - (float)((BlockPos)this.possibleCoords.get(this.possibleCoords.size() - 2)).func_177958_n()) > 10.0f) {
                    f6 = ((BlockPos)this.possibleCoords.get(this.possibleCoords.size() - 2)).func_177958_n();
                }
                if (Math.abs(f8 - (float)((BlockPos)this.possibleCoords.get(this.possibleCoords.size() - 2)).func_177952_p()) > 10.0f) {
                    f8 = ((BlockPos)this.possibleCoords.get(this.possibleCoords.size() - 2)).func_177952_p();
                }
            }
            this.footstepCoord = new BlockPos((double)f6, (double)f7, (double)f8);
            if (Minecraft.func_71410_x().field_71441_e.func_180495_p(this.footstepCoord.func_177984_a()).func_177230_c() != Blocks.field_150350_a) {
                this.footstepCoord = (BlockPos)this.possibleCoords.get(this.possibleCoords.size() - 1);
            }
        }
    }

    @EventHandler
    private void onR3D(EventRender3D eventRender3D) {
        if (this.footstepCoord != null && this.footstepCoord.func_177958_n() != 0 && this.footstepCoord.func_177956_o() != 0 && this.footstepCoord.func_177952_p() != 0) {
            RenderUtil.drawSolidBlockESP(this.footstepCoord, Colors.RED.c, eventRender3D.getPartialTicks());
            double d = (double)this.footstepCoord.func_177958_n() - Minecraft.func_71410_x().func_175598_ae().field_78730_l + 0.5;
            double d2 = (double)this.footstepCoord.func_177956_o() - Minecraft.func_71410_x().func_175598_ae().field_78731_m + 0.5;
            double d3 = (double)this.footstepCoord.func_177952_p() - Minecraft.func_71410_x().func_175598_ae().field_78728_n + 0.5;
            RenderUtil.startDrawing();
            GL11.glEnable((int)2848);
            RenderUtil.setColor(Colors.BLUE.c);
            GL11.glLineWidth((float)3.0f);
            GL11.glBegin((int)1);
            GL11.glVertex3d((double)0.0, (double)Minecraft.func_71410_x().field_71439_g.func_70047_e(), (double)0.0);
            GL11.glVertex3d((double)d, (double)d2, (double)d3);
            GL11.glEnd();
            GL11.glDisable((int)2848);
            RenderUtil.stopDrawing();
        }
        for (int i = 0; i < this.particleCoords.size() - 1; ++i) {
            Object object = (Vec3f)this.particleCoords.get(i);
            Vec3f vec3f = (Vec3f)this.particleCoords.get(i + 1);
            RenderUtil.draw3DLine((Vec3f)object, vec3f, new Color(Colors.ORANGE.c), 4.0f);
        }
        for (Object object : this.renders) {
            object.render(eventRender3D.getPartialTicks());
        }
    }

    @EventHandler
    private void onPRCV(EventPacketRecieve eventPacketRecieve) {
        S2APacketParticles s2APacketParticles;
        if (!DianaHelper.getInstance().isEnabled()) {
            return;
        }
        if (eventPacketRecieve.getPacket() instanceof S2APacketParticles && (s2APacketParticles = (S2APacketParticles)eventPacketRecieve.getPacket()).func_179749_a() == EnumParticleTypes.FIREWORKS_SPARK) {
            if (Math.abs(s2APacketParticles.func_149220_d() - Minecraft.func_71410_x().field_71439_g.field_70165_t) < 5.0 && Math.abs(s2APacketParticles.func_149225_f() - Minecraft.func_71410_x().field_71439_g.field_70161_v) < 5.0 && this.particleCoords.size() <= 1) {
                this.particleCoords.add(new Vec3f(s2APacketParticles.func_149220_d(), s2APacketParticles.func_149226_e(), s2APacketParticles.func_149225_f()));
            } else if (Math.abs(s2APacketParticles.func_149220_d() - ((Vec3f)this.particleCoords.get(this.particleCoords.size() - 1)).getX()) < 3.0 && Math.abs(s2APacketParticles.func_149225_f() - ((Vec3f)this.particleCoords.get(this.particleCoords.size() - 1)).getZ()) < 3.0) {
                this.particleCoords.add(new Vec3f(s2APacketParticles.func_149220_d(), s2APacketParticles.func_149226_e(), s2APacketParticles.func_149225_f()));
            }
        }
    }

    private static boolean lambda$onTick$0(float f, float f2, BlockPos blockPos) {
        return Math.abs((float)blockPos.func_177958_n() - f) > 10.0f || Math.abs((float)blockPos.func_177952_p() - f2) > 10.0f;
    }
}

