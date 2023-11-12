/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.svg.Diagram;

public class Gradient {
    private String name;
    private ArrayList steps = new ArrayList();
    private float x1;
    private float x2;
    private float y1;
    private float y2;
    private float r;
    private Image image;
    private boolean radial;
    private Transform transform;
    private String ref;

    public Gradient(String string, boolean bl) {
        this.name = string;
        this.radial = bl;
    }

    public boolean isRadial() {
        return this.radial;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public Transform getTransform() {
        return this.transform;
    }

    public void reference(String string) {
        this.ref = string;
    }

    public void resolve(Diagram diagram) {
        if (this.ref == null) {
            return;
        }
        Gradient gradient = diagram.getGradient(this.ref);
        for (int i = 0; i < gradient.steps.size(); ++i) {
            this.steps.add(gradient.steps.get(i));
        }
    }

    public void genImage() {
        if (this.image == null) {
            ImageBuffer imageBuffer = new ImageBuffer(128, 16);
            for (int i = 0; i < 128; ++i) {
                Color color = this.getColorAt((float)i / 128.0f);
                for (int j = 0; j < 16; ++j) {
                    imageBuffer.setRGBA(i, j, color.getRedByte(), color.getGreenByte(), color.getBlueByte(), color.getAlphaByte());
                }
            }
            this.image = imageBuffer.getImage();
        }
    }

    public Image getImage() {
        this.genImage();
        return this.image;
    }

    public void setR(float f) {
        this.r = f;
    }

    public void setX1(float f) {
        this.x1 = f;
    }

    public void setX2(float f) {
        this.x2 = f;
    }

    public void setY1(float f) {
        this.y1 = f;
    }

    public void setY2(float f) {
        this.y2 = f;
    }

    public float getR() {
        return this.r;
    }

    public float getX1() {
        return this.x1;
    }

    public float getX2() {
        return this.x2;
    }

    public float getY1() {
        return this.y1;
    }

    public float getY2() {
        return this.y2;
    }

    public void addStep(float f, Color color) {
        this.steps.add(new Step(this, f, color));
    }

    public Color getColorAt(float f) {
        if (f <= 0.0f) {
            return ((Step)this.steps.get((int)0)).col;
        }
        if (f > 1.0f) {
            return ((Step)this.steps.get((int)(this.steps.size() - 1))).col;
        }
        for (int i = 1; i < this.steps.size(); ++i) {
            Step step = (Step)this.steps.get(i - 1);
            Step step2 = (Step)this.steps.get(i);
            if (!(f <= step2.location)) continue;
            float f2 = step2.location - step.location;
            float f3 = (f -= step.location) / f2;
            Color color = new Color(1, 1, 1, 1);
            color.a = step.col.a * (1.0f - f3) + step2.col.a * f3;
            color.r = step.col.r * (1.0f - f3) + step2.col.r * f3;
            color.g = step.col.g * (1.0f - f3) + step2.col.g * f3;
            color.b = step.col.b * (1.0f - f3) + step2.col.b * f3;
            return color;
        }
        return Color.black;
    }

    private class Step {
        float location;
        Color col;
        final Gradient this$0;

        public Step(Gradient gradient, float f, Color color) {
            this.this$0 = gradient;
            this.location = f;
            this.col = color;
        }
    }
}

