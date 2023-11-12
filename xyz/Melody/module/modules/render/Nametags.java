/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.render;

import java.awt.Color;
import java.util.regex.Pattern;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.value.Option;
import xyz.Melody.GUI.Animate.AnimationUtil;
import xyz.Melody.System.Managers.Client.FriendManager;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.Utils.timer.TimerUtil;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;
import xyz.Melody.module.balance.AntiBot;
import xyz.Melody.module.modules.others.NickHider;

public class Nametags
extends Module {
    private float renderHpWidth;
    private static String lol;
    private final TimerUtil animationStopwatch = new TimerUtil();
    public Option mcplayer = new Option("ShowYou", true);
    private static Nametags INSTANCE;

    public Nametags() {
        super("Nametags", new String[]{"tags"}, ModuleType.Render);
        this.setColor(new Color(29, 187, 102).getRGB());
        this.addValues(this.mcplayer);
        this.setModInfo("Name Tag.");
    }

    public static Nametags getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = (Nametags)Client.instance.getModuleManager().getModuleByClass(Nametags.class);
        }
        return INSTANCE;
    }

    @SubscribeEvent
    public void onRender(RenderLivingEvent.Specials.Pre pre) {
        if (!(pre.entity instanceof EntityPlayer)) {
            return;
        }
        pre.setCanceled(true);
    }

    @EventHandler
    public void onRender(EventRender3D eventRender3D) {
        Pattern pattern = Pattern.compile("(?i)\ufffd\ufffd[0-9A-FK-OR]");
        for (EntityPlayer entityPlayer : this.mc.field_71441_e.field_73010_i) {
            if (NickHider.getINSTANCE().isEnabled() && NickHider.getINSTANCE().nicked.containsKey(entityPlayer.func_110124_au()) || !((Boolean)this.mcplayer.getValue() != false ? entityPlayer.func_70089_S() : entityPlayer.func_70089_S() && entityPlayer != this.mc.field_71439_g)) continue;
            double d = entityPlayer.field_70142_S + (entityPlayer.field_70165_t - entityPlayer.field_70142_S) * (double)eventRender3D.getPartialTicks() - this.mc.func_175598_ae().field_78730_l;
            double d2 = entityPlayer.field_70137_T + (entityPlayer.field_70163_u - entityPlayer.field_70137_T) * (double)eventRender3D.getPartialTicks() - this.mc.func_175598_ae().field_78731_m;
            double d3 = entityPlayer.field_70136_U + (entityPlayer.field_70161_v - entityPlayer.field_70136_U) * (double)eventRender3D.getPartialTicks() - this.mc.func_175598_ae().field_78728_n;
            this.renderNameTag(entityPlayer, pattern.matcher(entityPlayer.func_145748_c_().func_150260_c()).replaceAll(""), d, d2, d3);
        }
    }

    private void renderNameTag(EntityPlayer entityPlayer, String string, double d, double d2, double d3) {
        if (!entityPlayer.func_82150_aj()) {
            if (entityPlayer.func_70005_c_().toUpperCase().contains("CRYPT DREADLORD") || entityPlayer.func_70005_c_().toUpperCase().contains("DECOY") || entityPlayer.func_70005_c_().toUpperCase().contains("ZOMBIE COMMANDER") || entityPlayer.func_70005_c_().toUpperCase().contains("SKELETOR PRIME") || entityPlayer.func_70005_c_().toUpperCase().contains("CRYPT SOULEATER") || ((AntiBot)Client.instance.getModuleManager().getModuleByClass(AntiBot.class)).isEntityBot((Entity)entityPlayer)) {
                return;
            }
            float f = this.mc.field_71439_g.func_70032_d((Entity)entityPlayer) / 10.0f;
            if (f < 1.2f) {
                f = 1.2f;
            }
            d2 += entityPlayer.func_70093_af() ? 0.5 : 0.7;
            float f2 = f * 1.8f;
            f2 /= 100.0f;
            String string2 = "";
            if (FriendManager.isFriend(entityPlayer.func_70005_c_())) {
                string2 = EnumChatFormatting.DARK_PURPLE + "[Friend]";
            }
            lol = string2 + EnumChatFormatting.WHITE + string;
            GlStateManager.func_179094_E();
            GlStateManager.func_179117_G();
            GlStateManager.func_179140_f();
            GlStateManager.func_179137_b((double)d, (double)(d2 + (double)1.4f), (double)d3);
            GlStateManager.func_179114_b((float)(-this.mc.func_175598_ae().field_78735_i), (float)0.0f, (float)2.0f, (float)0.0f);
            GlStateManager.func_179114_b((float)this.mc.func_175598_ae().field_78732_j, (float)2.0f, (float)0.0f, (float)0.0f);
            GlStateManager.func_179152_a((float)(-f2), (float)(-f2), (float)f2);
            GlStateManager.func_179132_a((boolean)false);
            GlStateManager.func_179097_i();
            float f3 = (float)(-this.mc.field_71466_p.func_78256_a(lol) / 2) - 4.6f;
            float f4 = f3 - 2.0f * f3;
            float f5 = entityPlayer.func_110143_aJ();
            float f6 = f5 / entityPlayer.func_110138_aP();
            f6 = (float)MathHelper.func_151237_a((double)f6, (double)0.0, (double)1.0);
            float f7 = f3 - 2.0f * f3 * f6;
            if (this.animationStopwatch.hasReached(5.0)) {
                this.renderHpWidth = (float)AnimationUtil.animate(f7, this.renderHpWidth, 0.053f);
                this.animationStopwatch.reset();
            }
            float f8 = -17.0f;
            GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
            GlStateManager.func_179090_x();
            RenderUtil.drawFastRoundedRect(f3, f8, f4, -0.1f, 1.0f, new Color(25, 25, 25, 101).getRGB());
            RenderUtil.drawFastRoundedRect(f3, -2.0f, f4, -0.1f, 0.0f, new Color(152, 171, 195).getRGB());
            RenderUtil.drawFastRoundedRect(f3, -2.0f, this.renderHpWidth, -0.1f, 0.0f, new Color(123, 104, 238).getRGB());
            GlStateManager.func_179098_w();
            this.mc.field_71466_p.func_78276_b(lol, (int)(f3 + 4.0f), -13, -1);
            GlStateManager.func_179117_G();
            this.renderHead(entityPlayer, -19.0f, -55.0f);
            GlStateManager.func_179145_e();
            GlStateManager.func_179126_j();
            GlStateManager.func_179132_a((boolean)true);
            GlStateManager.func_179121_F();
            GlStateManager.func_179117_G();
        }
    }

    private void renderHead(EntityPlayer entityPlayer, float f, float f2) {
        ResourceLocation resourceLocation = ((AbstractClientPlayer)entityPlayer).func_110306_p();
        this.mc.func_110434_K().func_110577_a(resourceLocation);
        Gui.func_152125_a((int)((int)(f + 3.0f)), (int)((int)((double)f2 + 3.5)), (float)8.0f, (float)8.0f, (int)8, (int)8, (int)32, (int)32, (float)64.0f, (float)64.0f);
    }
}

