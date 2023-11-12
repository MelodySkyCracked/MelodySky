/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.BlockStainedGlass
 *  net.minecraft.block.BlockStainedGlassPane
 *  net.minecraft.block.properties.IProperty
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.EnumDyeColor
 *  net.minecraftforge.event.world.WorldEvent$Load
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package xyz.Melody.module.modules.QOL;

import java.awt.Color;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.Melody.Client;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.rendering.EventRender3D;
import xyz.Melody.Event.events.world.BlockChangeEvent;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.System.Managers.Skyblock.Area.Areas;
import xyz.Melody.module.Module;
import xyz.Melody.module.ModuleType;

public class GemStoneESP
extends Module {
    private Numbers radius = new Numbers("Radius", 100.0, 1.0, 500.0, 1.0);
    private Option jade = new Option("Jade", true);
    private Option amber = new Option("Amber", true);
    private Option topaz = new Option("Topaz", true);
    private Option sapphire = new Option("Sapphire", true);
    private Option amethyst = new Option("Amethyst", true);
    private Option jasper = new Option("Jasper", true);
    private Option ruby = new Option("Ruby", true);
    private Option opal = new Option("Opal", true);
    private Option pane = new Option("GlassPane", false);
    private ConcurrentHashMap gemstones = new ConcurrentHashMap();
    private Thread thread;

    public GemStoneESP() {
        super("GemStoneESP", new String[]{"tags"}, ModuleType.Render);
        this.addValues(this.radius, this.jade, this.amber, this.topaz, this.sapphire, this.amethyst, this.jasper, this.ruby, this.opal, this.pane);
        this.setModInfo("Just Gemstone ESP.");
    }

    @Override
    public void onEnable() {
        this.gemstones.clear();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventHandler
    public void onTick(EventTick eventTick) {
        if (!Client.instance.sbArea.isIn(Areas.Crystal_Hollows)) {
            this.gemstones.clear();
            return;
        }
        if (!(this.thread != null && this.thread.isAlive() || this.mc.field_71439_g == null || this.mc.field_71441_e == null)) {
            this.thread = new Thread(this::lambda$onTick$0, "Gemstone Scanner");
            this.thread.start();
        }
    }

    @EventHandler
    public void onBlockChange(BlockChangeEvent blockChangeEvent) {
        if (blockChangeEvent.getNewBlock().func_177230_c() == Blocks.field_150350_a) {
            this.gemstones.remove(blockChangeEvent.getPosition());
        }
    }

    /*
     * Exception decompiling
     */
    @EventHandler
    public void onRenderWorld(EventRender3D var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl12 : ALOAD_2 - null : trying to set 1 previously set to 0
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

    public Gemstone getGemstone(IBlockState iBlockState) {
        if (iBlockState.func_177230_c() != Blocks.field_150399_cn && iBlockState.func_177230_c() != Blocks.field_150397_co) {
            return null;
        }
        if (((Boolean)this.pane.getValue()).booleanValue() && iBlockState.func_177230_c() != Blocks.field_150397_co) {
            return null;
        }
        EnumDyeColor enumDyeColor = (EnumDyeColor)this.firstNotNull((EnumDyeColor)iBlockState.func_177229_b((IProperty)BlockStainedGlass.field_176547_a), (EnumDyeColor)iBlockState.func_177229_b((IProperty)BlockStainedGlassPane.field_176245_a));
        if (enumDyeColor == Gemstone.RUBY.dyeColor) {
            return Gemstone.RUBY;
        }
        if (enumDyeColor == Gemstone.AMETHYST.dyeColor) {
            return Gemstone.AMETHYST;
        }
        if (enumDyeColor == Gemstone.JADE.dyeColor) {
            return Gemstone.JADE;
        }
        if (enumDyeColor == Gemstone.SAPPHIRE.dyeColor) {
            return Gemstone.SAPPHIRE;
        }
        if (enumDyeColor == Gemstone.AMBER.dyeColor) {
            return Gemstone.AMBER;
        }
        if (enumDyeColor == Gemstone.TOPAZ.dyeColor) {
            return Gemstone.TOPAZ;
        }
        if (enumDyeColor == Gemstone.JASPER.dyeColor) {
            return Gemstone.JASPER;
        }
        if (enumDyeColor == Gemstone.OPAL.dyeColor) {
            return Gemstone.OPAL;
        }
        return null;
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load load) {
        this.gemstones.clear();
    }

    public Object firstNotNull(Object ... objectArray) {
        for (Object object : objectArray) {
            if (object == null) continue;
            return object;
        }
        return null;
    }

    private void lambda$onTick$0() {
        while (this.isEnabled()) {
            try {
                if (this == false) continue;
                break;
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    static enum Gemstone {
        RUBY(new Color(188, 3, 29), EnumDyeColor.RED),
        AMETHYST(new Color(137, 0, 201), EnumDyeColor.PURPLE),
        JADE(new Color(157, 249, 32), EnumDyeColor.LIME),
        SAPPHIRE(new Color(60, 121, 224), EnumDyeColor.LIGHT_BLUE),
        AMBER(new Color(237, 139, 35), EnumDyeColor.ORANGE),
        TOPAZ(new Color(249, 215, 36), EnumDyeColor.YELLOW),
        JASPER(new Color(214, 15, 150), EnumDyeColor.MAGENTA),
        OPAL(new Color(245, 245, 240), EnumDyeColor.WHITE);

        public Color color;
        public EnumDyeColor dyeColor;

        /*
         * WARNING - void declaration
         */
        private Gemstone() {
            void var4_2;
            void var3_1;
            this.color = var3_1;
            this.dyeColor = var4_2;
        }
    }
}

