/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonParser
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.properties.Property
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.EnumChatFormatting
 *  net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre
 *  net.minecraftforge.event.entity.EntityJoinWorldEvent
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.others;

import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Utils.render.RenderUtil;
import xyz.Melody.module.FMLModules.Utils.NickedPlayer;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class NickHider
extends Module {
    public HashMap nicked = new HashMap();
    private Numbers appendY = new Numbers("Append Y", 0.0, -2.0, 2.0, 0.1);
    private static NickHider INSTANCE;

    public NickHider() {
        super("NickHider", new String[]{"nh", "nick", "nickhider"}, ModuleType.Others);
        this.setModInfo("Fuck Nigger Nickers.");
        this.addValues(this.appendY);
    }

    public static NickHider getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = (NickHider)Client.instance.getModuleManager().getModuleByClass(NickHider.class);
        }
        return INSTANCE;
    }

    /*
     * Exception decompiling
     */
    @SubscribeEvent
    public void onChat(EntityJoinWorldEvent var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl126 : RETURN - null : trying to set 0 previously set to 1
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @SubscribeEvent
    public void onRender(RenderLivingEvent.Specials.Pre pre) {
        if (!(pre.entity instanceof EntityPlayer)) {
            return;
        }
        if (this.nicked.keySet().contains(pre.entity.func_110124_au())) {
            pre.setCanceled(true);
        }
    }

    @EventHandler
    private void on3D(EventRender3D eventRender3D) {
        for (EntityPlayer entityPlayer : this.mc.field_71441_e.field_73010_i) {
            if (!this.nicked.keySet().contains(entityPlayer.func_110124_au())) continue;
            NickedPlayer nickedPlayer = (NickedPlayer)this.nicked.get(entityPlayer.func_110124_au());
            String string = EnumChatFormatting.GRAY + "Nick: " + EnumChatFormatting.RED + nickedPlayer.getNickedName() + EnumChatFormatting.GRAY + " | Real: " + EnumChatFormatting.AQUA + nickedPlayer.getRealName();
            RenderUtil.playerTag(entityPlayer, string, -1, eventRender3D.getPartialTicks(), ((Double)this.appendY.getValue()).floatValue());
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load load) {
        this.nicked.clear();
    }

    public String getRealName(GameProfile gameProfile) {
        AtomicReference<String> atomicReference = new AtomicReference<String>("");
        JsonParser jsonParser = new JsonParser();
        AtomicReference atomicReference2 = new AtomicReference();
        gameProfile.getProperties().entries().forEach(arg_0 -> NickHider.lambda$getRealName$0(atomicReference2, jsonParser, arg_0));
        return atomicReference.get();
    }

    private static void lambda$getRealName$0(AtomicReference atomicReference, JsonParser jsonParser, Map.Entry entry) {
        if (((String)entry.getKey()).equals("textures")) {
            try {
                atomicReference.set(jsonParser.parse(new String(Base64.getDecoder().decode(((Property)entry.getValue()).getValue()))).getAsJsonObject().get("profileName").getAsString());
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}

