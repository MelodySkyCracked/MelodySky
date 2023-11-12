/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.renderer.entity.RenderPlayer
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre
 *  net.minecraftforge.fml.common.Loader
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.FMLModules;

import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.System.Managers.GaoNeng.GaoNengManager;
import xyz.Melody.module.modules.render.Cam;
import xyz.Melody.module.modules.render.Nametags;

public class RankRenderer {
    private Minecraft mc = Minecraft.func_71410_x();

    @SubscribeEvent
    public void onRankRender(RenderLivingEvent.Specials.Pre pre) {
        float f = Loader.isModLoaded((String)"xiaojiaaddons") ? -0.28f : 0.0f;
        Cam cam = Cam.getINSTANCE();
        if (pre.entity == this.mc.field_71439_g) {
            Method method;
            if (Nametags.getINSTANCE().isEnabled()) {
                return;
            }
            double d = pre.x;
            double d2 = pre.y;
            double d3 = pre.z;
            String string = this.mc.field_71439_g.func_145748_c_().func_150260_c();
            d2 += (double)((float)this.mc.field_71466_p.field_78288_b * 1.15f * 0.02666667f * 2.0f);
            if (GaoNengManager.getIfIsGaoNeng(this.mc.field_71439_g) != null) {
                try {
                    method = this.getRenderMethod((RenderPlayer)pre.renderer);
                    method.setAccessible(true);
                    method.invoke(pre.renderer, pre.entity, "\u00a7b[" + EnumChatFormatting.WHITE + GaoNengManager.getIfIsGaoNeng(this.mc.field_71439_g).getRank().replaceAll("&", "\u00a7") + "\u00a7b]", d, d2 + (double)f - 0.25, d3, 64);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else if (cam.isEnabled() && ((Boolean)cam.showRank.getValue()).booleanValue() && Client.customRank != null) {
                try {
                    method = this.getRenderMethod((RenderPlayer)pre.renderer);
                    method.setAccessible(true);
                    method.invoke(pre.renderer, pre.entity, Client.customRank, d, d2 + (double)f - 0.25, d3, 64);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            if (((Boolean)cam.name.getValue()).booleanValue()) {
                try {
                    method = this.getRenderMethod((RenderPlayer)pre.renderer);
                    method.setAccessible(true);
                    if (((Boolean)Cam.getINSTANCE().chilePlayers.getValue()).booleanValue()) {
                        method.invoke(pre.renderer, pre.entity, string, d, d2 - (double)(this.mc.field_71439_g.func_70093_af() ? 1.5f : 1.4f), d3, 64);
                    } else {
                        method.invoke(pre.renderer, pre.entity, string, d, d2 - (double)(this.mc.field_71439_g.func_70093_af() ? 0.8f : 0.55f), d3, 64);
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            return;
        }
        if (pre.entity instanceof EntityOtherPlayerMP) {
            if (GaoNengManager.getIfIsGaoNeng((EntityOtherPlayerMP)pre.entity) == null) {
                return;
            }
            if (pre.renderer instanceof RenderPlayer) {
                double d = pre.x;
                double d4 = pre.y;
                double d5 = pre.z;
                String string = "\u00a7b[" + EnumChatFormatting.WHITE + GaoNengManager.getIfIsGaoNeng((EntityOtherPlayerMP)pre.entity).getRank().replaceAll("&", "\u00a7") + "\u00a7b]";
                d4 += (double)((float)this.mc.field_71466_p.field_78288_b * 1.15f * 0.02666667f * 2.0f);
                try {
                    Method method = this.getRenderMethod((RenderPlayer)pre.renderer);
                    method.setAccessible(true);
                    method.invoke(pre.renderer, pre.entity, string, d, d4 + (double)f, d5, 64);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    private Method getRenderMethod(RenderPlayer renderPlayer) throws NoSuchMethodException {
        for (Class<?> clazz = renderPlayer.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            Method[] methodArray;
            Method[] methodArray2 = methodArray = clazz.getDeclaredMethods();
            int n = methodArray.length;
            for (int i = 0; i < n; ++i) {
                Method method = methodArray2[i];
                if (!method.getName().equals("renderLivingLabel") && !method.getName().equals("func_147906_a")) continue;
                return method;
            }
        }
        throw new NoSuchMethodException();
    }
}

