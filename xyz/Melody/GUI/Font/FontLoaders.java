/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.ResourceLocation
 */
package xyz.Melody.GUI.Font;

import java.awt.Font;
import java.io.InputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import xyz.Melody.GUI.Font.CFontRenderer;

public abstract class FontLoaders {
    public static CFontRenderer NMSL10 = new CFontRenderer(FontLoaders.getNMSL(10), true);
    public static CFontRenderer NMSL11 = new CFontRenderer(FontLoaders.getNMSL(11), true);
    public static CFontRenderer NMSL12 = new CFontRenderer(FontLoaders.getNMSL(12), true);
    public static CFontRenderer NMSL13 = new CFontRenderer(FontLoaders.getNMSL(13), true);
    public static CFontRenderer NMSL14 = new CFontRenderer(FontLoaders.getNMSL(14), true);
    public static CFontRenderer NMSL15 = new CFontRenderer(FontLoaders.getNMSL(15), true);
    public static CFontRenderer NMSL16 = new CFontRenderer(FontLoaders.getNMSL(16), true);
    public static CFontRenderer NMSL18 = new CFontRenderer(FontLoaders.getNMSL(18), true);
    public static CFontRenderer NMSL19 = new CFontRenderer(FontLoaders.getNMSL(19), true);
    public static CFontRenderer NMSL20 = new CFontRenderer(FontLoaders.getNMSL(20), true);
    public static CFontRenderer NMSL21 = new CFontRenderer(FontLoaders.getNMSL(21), true);
    public static CFontRenderer NMSL22 = new CFontRenderer(FontLoaders.getNMSL(22), true);
    public static CFontRenderer NMSL24 = new CFontRenderer(FontLoaders.getNMSL(24), true);
    public static CFontRenderer NMSL25 = new CFontRenderer(FontLoaders.getNMSL(25), true);
    public static CFontRenderer NMSL26 = new CFontRenderer(FontLoaders.getNMSL(26), true);
    public static CFontRenderer NMSL35 = new CFontRenderer(FontLoaders.getNMSL(35), true);
    public static CFontRenderer NMSL40 = new CFontRenderer(FontLoaders.getNMSL(40), true);
    public static CFontRenderer NMSL45 = new CFontRenderer(FontLoaders.getNMSL(45), true);
    public static CFontRenderer NMSL28 = new CFontRenderer(FontLoaders.getNMSL(28), true);
    public static CFontRenderer CNMD10 = new CFontRenderer(FontLoaders.getCNMD(10), true);
    public static CFontRenderer CNMD11 = new CFontRenderer(FontLoaders.getCNMD(11), true);
    public static CFontRenderer CNMD12 = new CFontRenderer(FontLoaders.getCNMD(12), true);
    public static CFontRenderer CNMD13 = new CFontRenderer(FontLoaders.getCNMD(13), true);
    public static CFontRenderer CNMD14 = new CFontRenderer(FontLoaders.getCNMD(14), true);
    public static CFontRenderer CNMD15 = new CFontRenderer(FontLoaders.getCNMD(15), true);
    public static CFontRenderer CNMD16 = new CFontRenderer(FontLoaders.getCNMD(16), true);
    public static CFontRenderer CNMD18 = new CFontRenderer(FontLoaders.getCNMD(18), true);
    public static CFontRenderer CNMD19 = new CFontRenderer(FontLoaders.getCNMD(19), true);
    public static CFontRenderer CNMD20 = new CFontRenderer(FontLoaders.getCNMD(20), true);
    public static CFontRenderer CNMD21 = new CFontRenderer(FontLoaders.getCNMD(21), true);
    public static CFontRenderer CNMD22 = new CFontRenderer(FontLoaders.getCNMD(22), true);
    public static CFontRenderer CNMD24 = new CFontRenderer(FontLoaders.getCNMD(24), true);
    public static CFontRenderer CNMD25 = new CFontRenderer(FontLoaders.getCNMD(25), true);
    public static CFontRenderer CNMD26 = new CFontRenderer(FontLoaders.getCNMD(26), true);
    public static CFontRenderer CNMD30 = new CFontRenderer(FontLoaders.getCNMD(30), true);
    public static CFontRenderer CNMD31 = new CFontRenderer(FontLoaders.getCNMD(31), true);
    public static CFontRenderer CNMD32 = new CFontRenderer(FontLoaders.getCNMD(32), true);
    public static CFontRenderer CNMD33 = new CFontRenderer(FontLoaders.getCNMD(33), true);
    public static CFontRenderer CNMD34 = new CFontRenderer(FontLoaders.getCNMD(34), true);
    public static CFontRenderer CNMD35 = new CFontRenderer(FontLoaders.getCNMD(35), true);
    public static CFontRenderer CNMD40 = new CFontRenderer(FontLoaders.getCNMD(40), true);
    public static CFontRenderer CNMD45 = new CFontRenderer(FontLoaders.getCNMD(45), true);
    public static CFontRenderer CNMD28 = new CFontRenderer(FontLoaders.getCNMD(28), true);

    private static Font getNMSL(int n) {
        Font font;
        try {
            font = new Font("Tahoma", 0, n);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, n);
        }
        return font;
    }

    private static Font getCNMD(int n) {
        Font font;
        try {
            InputStream inputStream = Minecraft.func_71410_x().func_110442_L().func_110536_a(new ResourceLocation("Melody/Fonts/CNMD.ttf")).func_110527_b();
            font = Font.createFont(0, inputStream);
            font = font.deriveFont(0, n);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, n);
        }
        return font;
    }
}

