/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.font.effects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.GeneralPath;
import java.util.List;
import org.newdawn.slick.font.effects.ConfigurableEffect;
import org.newdawn.slick.font.effects.EffectUtil;
import org.newdawn.slick.font.effects.OutlineEffect;
import org.newdawn.slick.font.effects.lI;

public class OutlineZigzagEffect
extends OutlineEffect {
    private float amplitude = 1.0f;
    private float wavelength = 3.0f;

    public OutlineZigzagEffect() {
        this.setStroke(new ZigzagStroke(this, null));
    }

    public float getWavelength() {
        return this.wavelength;
    }

    public void setWavelength(float f) {
        this.wavelength = f;
    }

    public float getAmplitude() {
        return this.amplitude;
    }

    public void setAmplitude(float f) {
        this.amplitude = f;
    }

    public OutlineZigzagEffect(int n, Color color) {
        super(n, color);
    }

    @Override
    public String toString() {
        return "Outline (Zigzag)";
    }

    @Override
    public List getValues() {
        List list = super.getValues();
        list.add(EffectUtil.floatValue("Wavelength", this.wavelength, 1.0f, 100.0f, "This setting controls the wavelength of the outline. The smaller the value, the more segments will be used to draw the outline."));
        list.add(EffectUtil.floatValue("Amplitude", this.amplitude, 0.5f, 50.0f, "This setting controls the amplitude of the outline. The bigger the value, the more the zigzags will vary."));
        return list;
    }

    @Override
    public void setValues(List list) {
        super.setValues(list);
        for (ConfigurableEffect.Value value : list) {
            if (value.getName().equals("Wavelength")) {
                this.wavelength = ((Float)value.getObject()).floatValue();
                continue;
            }
            if (!value.getName().equals("Amplitude")) continue;
            this.amplitude = ((Float)value.getObject()).floatValue();
        }
    }

    static float access$100(OutlineZigzagEffect outlineZigzagEffect) {
        return outlineZigzagEffect.wavelength;
    }

    static float access$200(OutlineZigzagEffect outlineZigzagEffect) {
        return outlineZigzagEffect.amplitude;
    }

    private class ZigzagStroke
    implements Stroke {
        private static final float FLATNESS = 1.0f;
        final OutlineZigzagEffect this$0;

        private ZigzagStroke(OutlineZigzagEffect outlineZigzagEffect) {
            this.this$0 = outlineZigzagEffect;
        }

        @Override
        public Shape createStrokedShape(Shape shape) {
            GeneralPath generalPath = new GeneralPath();
            FlatteningPathIterator flatteningPathIterator = new FlatteningPathIterator(shape.getPathIterator(null), 1.0);
            float[] fArray = new float[6];
            float f = 0.0f;
            float f2 = 0.0f;
            float f3 = 0.0f;
            float f4 = 0.0f;
            float f5 = 0.0f;
            float f6 = 0.0f;
            int n = 0;
            float f7 = 0.0f;
            int n2 = 0;
            while (!flatteningPathIterator.isDone()) {
                n = flatteningPathIterator.currentSegment(fArray);
                switch (n) {
                    case 0: {
                        f = f3 = fArray[0];
                        f2 = f4 = fArray[1];
                        generalPath.moveTo(f, f2);
                        f7 = OutlineZigzagEffect.access$100(this.this$0) / 2.0f;
                        break;
                    }
                    case 4: {
                        fArray[0] = f;
                        fArray[1] = f2;
                    }
                    case 1: {
                        f5 = fArray[0];
                        f6 = fArray[1];
                        float f8 = f5 - f3;
                        float f9 = f6 - f4;
                        float f10 = (float)Math.sqrt(f8 * f8 + f9 * f9);
                        if (f10 >= f7) {
                            float f11 = 1.0f / f10;
                            while (f10 >= f7) {
                                float f12 = f3 + f7 * f8 * f11;
                                float f13 = f4 + f7 * f9 * f11;
                                if (!(n2 & true)) {
                                    generalPath.lineTo(f12 + OutlineZigzagEffect.access$200(this.this$0) * f9 * f11, f13 - OutlineZigzagEffect.access$200(this.this$0) * f8 * f11);
                                } else {
                                    generalPath.lineTo(f12 - OutlineZigzagEffect.access$200(this.this$0) * f9 * f11, f13 + OutlineZigzagEffect.access$200(this.this$0) * f8 * f11);
                                }
                                f7 += OutlineZigzagEffect.access$100(this.this$0);
                                ++n2;
                            }
                        }
                        f7 -= f10;
                        f3 = f5;
                        f4 = f6;
                        if (n != 4) break;
                        generalPath.closePath();
                    }
                }
                flatteningPathIterator.next();
            }
            return new BasicStroke(this.this$0.getWidth(), 2, this.this$0.getJoin()).createStrokedShape(generalPath);
        }

        ZigzagStroke(OutlineZigzagEffect outlineZigzagEffect, lI lI2) {
            this(outlineZigzagEffect);
        }
    }
}

