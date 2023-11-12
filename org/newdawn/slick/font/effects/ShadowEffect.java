/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.font.effects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.Glyph;
import org.newdawn.slick.font.effects.ConfigurableEffect;
import org.newdawn.slick.font.effects.Effect;
import org.newdawn.slick.font.effects.EffectUtil;
import org.newdawn.slick.font.effects.OutlineEffect;

public class ShadowEffect
implements ConfigurableEffect {
    public static final int NUM_KERNELS = 16;
    public static final float[][] GAUSSIAN_BLUR_KERNELS = ShadowEffect.generateGaussianBlurKernels(16);
    private Color color = Color.black;
    private float opacity = 0.6f;
    private float xDistance = 2.0f;
    private float yDistance = 2.0f;
    private int blurKernelSize = 0;
    private int blurPasses = 1;

    public ShadowEffect() {
    }

    public ShadowEffect(Color color, int n, int n2, float f) {
        this.color = color;
        this.xDistance = n;
        this.yDistance = n2;
        this.opacity = f;
    }

    @Override
    public void draw(BufferedImage bufferedImage, Graphics2D graphics2D, UnicodeFont unicodeFont, Glyph glyph) {
        graphics2D = (Graphics2D)graphics2D.create();
        graphics2D.translate(this.xDistance, this.yDistance);
        graphics2D.setColor(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), Math.round(this.opacity * 255.0f)));
        graphics2D.fill(glyph.getShape());
        for (Effect effect : unicodeFont.getEffects()) {
            if (!(effect instanceof OutlineEffect)) continue;
            Composite composite = graphics2D.getComposite();
            graphics2D.setComposite(AlphaComposite.Src);
            graphics2D.setStroke(((OutlineEffect)effect).getStroke());
            graphics2D.draw(glyph.getShape());
            graphics2D.setComposite(composite);
            break;
        }
        graphics2D.dispose();
        if (this.blurKernelSize > 1 && this.blurKernelSize < 16 && this.blurPasses > 0) {
            this.blur(bufferedImage);
        }
    }

    private void blur(BufferedImage bufferedImage) {
        float[] fArray = GAUSSIAN_BLUR_KERNELS[this.blurKernelSize - 1];
        Kernel kernel = new Kernel(fArray.length, 1, fArray);
        Kernel kernel2 = new Kernel(1, fArray.length, fArray);
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        ConvolveOp convolveOp = new ConvolveOp(kernel, 1, renderingHints);
        ConvolveOp convolveOp2 = new ConvolveOp(kernel2, 1, renderingHints);
        BufferedImage bufferedImage2 = EffectUtil.getScratchImage();
        for (int i = 0; i < this.blurPasses; ++i) {
            convolveOp.filter(bufferedImage, bufferedImage2);
            convolveOp2.filter(bufferedImage2, bufferedImage);
        }
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getXDistance() {
        return this.xDistance;
    }

    public void setXDistance(float f) {
        this.xDistance = f;
    }

    public float getYDistance() {
        return this.yDistance;
    }

    public void setYDistance(float f) {
        this.yDistance = f;
    }

    public int getBlurKernelSize() {
        return this.blurKernelSize;
    }

    public void setBlurKernelSize(int n) {
        this.blurKernelSize = n;
    }

    public int getBlurPasses() {
        return this.blurPasses;
    }

    public void setBlurPasses(int n) {
        this.blurPasses = n;
    }

    public float getOpacity() {
        return this.opacity;
    }

    public void setOpacity(float f) {
        this.opacity = f;
    }

    public String toString() {
        return "Shadow";
    }

    @Override
    public List getValues() {
        ArrayList<ConfigurableEffect.Value> arrayList = new ArrayList<ConfigurableEffect.Value>();
        arrayList.add(EffectUtil.colorValue("Color", this.color));
        arrayList.add(EffectUtil.floatValue("Opacity", this.opacity, 0.0f, 1.0f, "This setting sets the translucency of the shadow."));
        arrayList.add(EffectUtil.floatValue("X distance", this.xDistance, Float.MIN_VALUE, Float.MAX_VALUE, "This setting is the amount of pixels to offset the shadow on the x axis. The glyphs will need padding so the shadow doesn't get clipped."));
        arrayList.add(EffectUtil.floatValue("Y distance", this.yDistance, Float.MIN_VALUE, Float.MAX_VALUE, "This setting is the amount of pixels to offset the shadow on the y axis. The glyphs will need padding so the shadow doesn't get clipped."));
        ArrayList<String[]> arrayList2 = new ArrayList<String[]>();
        arrayList2.add(new String[]{"None", "0"});
        for (int i = 2; i < 16; ++i) {
            arrayList2.add(new String[]{String.valueOf(i)});
        }
        String[][] stringArray = (String[][])arrayList2.toArray((T[])new String[arrayList2.size()][]);
        arrayList.add(EffectUtil.optionValue("Blur kernel size", String.valueOf(this.blurKernelSize), stringArray, "This setting controls how many neighboring pixels are used to blur the shadow. Set to \"None\" for no blur."));
        arrayList.add(EffectUtil.intValue("Blur passes", this.blurPasses, "The setting is the number of times to apply a blur to the shadow. Set to \"0\" for no blur."));
        return arrayList;
    }

    @Override
    public void setValues(List list) {
        for (ConfigurableEffect.Value value : list) {
            if (value.getName().equals("Color")) {
                this.color = (Color)value.getObject();
                continue;
            }
            if (value.getName().equals("Opacity")) {
                this.opacity = ((Float)value.getObject()).floatValue();
                continue;
            }
            if (value.getName().equals("X distance")) {
                this.xDistance = ((Float)value.getObject()).floatValue();
                continue;
            }
            if (value.getName().equals("Y distance")) {
                this.yDistance = ((Float)value.getObject()).floatValue();
                continue;
            }
            if (value.getName().equals("Blur kernel size")) {
                this.blurKernelSize = Integer.parseInt((String)value.getObject());
                continue;
            }
            if (!value.getName().equals("Blur passes")) continue;
            this.blurPasses = (Integer)value.getObject();
        }
    }

    private static float[][] generateGaussianBlurKernels(int n) {
        float[][] fArray = ShadowEffect.generatePascalsTriangle(n);
        float[][] fArrayArray = new float[fArray.length][];
        for (int i = 0; i < fArrayArray.length; ++i) {
            float f = 0.0f;
            fArrayArray[i] = new float[fArray[i].length];
            for (int j = 0; j < fArray[i].length; ++j) {
                f += fArray[i][j];
            }
            float f2 = 1.0f / f;
            for (int j = 0; j < fArray[i].length; ++j) {
                fArrayArray[i][j] = f2 * fArray[i][j];
            }
        }
        return fArrayArray;
    }

    private static float[][] generatePascalsTriangle(int n) {
        if (n < 2) {
            n = 2;
        }
        float[][] fArrayArray = new float[n][];
        fArrayArray[0] = new float[1];
        fArrayArray[1] = new float[2];
        fArrayArray[0][0] = 1.0f;
        fArrayArray[1][0] = 1.0f;
        fArrayArray[1][1] = 1.0f;
        for (int i = 2; i < n; ++i) {
            fArrayArray[i] = new float[i + 1];
            fArrayArray[i][0] = 1.0f;
            fArrayArray[i][i] = 1.0f;
            for (int j = 1; j < fArrayArray[i].length - 1; ++j) {
                fArrayArray[i][j] = fArrayArray[i - 1][j - 1] + fArrayArray[i - 1][j];
            }
        }
        return fArrayArray;
    }
}

