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
import org.newdawn.slick.font.effects.llI;

public class OutlineWobbleEffect
extends OutlineEffect {
    private float detail = 1.0f;
    private float amplitude = 1.0f;

    public OutlineWobbleEffect() {
        this.setStroke(new WobbleStroke(this, null));
    }

    public float getDetail() {
        return this.detail;
    }

    public void setDetail(float f) {
        this.detail = f;
    }

    public float getAmplitude() {
        return this.amplitude;
    }

    public void setAmplitude(float f) {
        this.amplitude = f;
    }

    public OutlineWobbleEffect(int n, Color color) {
        super(n, color);
    }

    @Override
    public String toString() {
        return "Outline (Wobble)";
    }

    @Override
    public List getValues() {
        List list = super.getValues();
        list.remove(2);
        list.add(EffectUtil.floatValue("Detail", this.detail, 1.0f, 50.0f, "This setting controls how detailed the outline will be. Smaller numbers cause the outline to have more detail."));
        list.add(EffectUtil.floatValue("Amplitude", this.amplitude, 0.5f, 50.0f, "This setting controls the amplitude of the outline."));
        return list;
    }

    @Override
    public void setValues(List list) {
        super.setValues(list);
        for (ConfigurableEffect.Value value : list) {
            if (value.getName().equals("Detail")) {
                this.detail = ((Float)value.getObject()).floatValue();
                continue;
            }
            if (!value.getName().equals("Amplitude")) continue;
            this.amplitude = ((Float)value.getObject()).floatValue();
        }
    }

    static float access$100(OutlineWobbleEffect outlineWobbleEffect) {
        return outlineWobbleEffect.detail;
    }

    static float access$200(OutlineWobbleEffect outlineWobbleEffect) {
        return outlineWobbleEffect.amplitude;
    }

    private class WobbleStroke
    implements Stroke {
        private static final float FLATNESS = 1.0f;
        final OutlineWobbleEffect this$0;

        private WobbleStroke(OutlineWobbleEffect outlineWobbleEffect) {
            this.this$0 = outlineWobbleEffect;
        }

        @Override
        public Shape createStrokedShape(Shape shape) {
            GeneralPath generalPath = new GeneralPath();
            shape = new BasicStroke(this.this$0.getWidth(), 2, this.this$0.getJoin()).createStrokedShape(shape);
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
            while (!flatteningPathIterator.isDone()) {
                n = flatteningPathIterator.currentSegment(fArray);
                switch (n) {
                    case 0: {
                        f = f3 = this.randomize(fArray[0]);
                        f2 = f4 = this.randomize(fArray[1]);
                        generalPath.moveTo(f, f2);
                        f7 = 0.0f;
                        break;
                    }
                    case 4: {
                        fArray[0] = f;
                        fArray[1] = f2;
                    }
                    case 1: {
                        f5 = this.randomize(fArray[0]);
                        f6 = this.randomize(fArray[1]);
                        float f8 = f5 - f3;
                        float f9 = f6 - f4;
                        float f10 = (float)Math.sqrt(f8 * f8 + f9 * f9);
                        if (f10 >= f7) {
                            float f11 = 1.0f / f10;
                            while (f10 >= f7) {
                                float f12 = f3 + f7 * f8 * f11;
                                float f13 = f4 + f7 * f9 * f11;
                                generalPath.lineTo(this.randomize(f12), this.randomize(f13));
                                f7 += OutlineWobbleEffect.access$100(this.this$0);
                            }
                        }
                        f7 -= f10;
                        f3 = f5;
                        f4 = f6;
                    }
                }
                flatteningPathIterator.next();
            }
            return generalPath;
        }

        private float randomize(float f) {
            return f + (float)Math.random() * OutlineWobbleEffect.access$200(this.this$0) * 2.0f - 1.0f;
        }

        WobbleStroke(OutlineWobbleEffect outlineWobbleEffect, llI llI2) {
            this(outlineWobbleEffect);
        }
    }
}

