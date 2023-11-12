/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.particles;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.I;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.util.FastTrig;
import org.newdawn.slick.util.Log;

public class ConfigurableEmitter
implements ParticleEmitter {
    private static String relativePath = "";
    public Range spawnInterval = new Range(this, 100.0f, 100.0f, null);
    public Range spawnCount = new Range(this, 5.0f, 5.0f, null);
    public Range initialLife = new Range(this, 1000.0f, 1000.0f, null);
    public Range initialSize = new Range(this, 10.0f, 10.0f, null);
    public Range xOffset = new Range(this, 0.0f, 0.0f, null);
    public Range yOffset = new Range(this, 0.0f, 0.0f, null);
    public RandomValue spread = new RandomValue(this, 360.0f, null);
    public SimpleValue angularOffset = new SimpleValue(this, 0.0f, null);
    public Range initialDistance = new Range(this, 0.0f, 0.0f, null);
    public Range speed = new Range(this, 50.0f, 50.0f, null);
    public SimpleValue growthFactor = new SimpleValue(this, 0.0f, null);
    public SimpleValue gravityFactor = new SimpleValue(this, 0.0f, null);
    public SimpleValue windFactor = new SimpleValue(this, 0.0f, null);
    public Range length = new Range(this, 1000.0f, 1000.0f, null);
    public ArrayList colors = new ArrayList();
    public SimpleValue startAlpha = new SimpleValue(this, 255.0f, null);
    public SimpleValue endAlpha = new SimpleValue(this, 0.0f, null);
    public LinearInterpolator alpha;
    public LinearInterpolator size;
    public LinearInterpolator velocity;
    public LinearInterpolator scaleY;
    public Range emitCount = new Range(this, 1000.0f, 1000.0f, null);
    public int usePoints = 1;
    public boolean useOriented = false;
    public boolean useAdditive = false;
    public String name;
    public String imageName = "";
    private Image image;
    private boolean updateImage;
    private boolean enabled = true;
    private float x;
    private float y;
    private int nextSpawn = 0;
    private int timeout;
    private int particleCount;
    private ParticleSystem engine;
    private int leftToEmit;
    protected boolean wrapUp = false;
    protected boolean completed = false;
    protected boolean adjust;
    protected float adjustx;
    protected float adjusty;

    public static void setRelativePath(String string) {
        if (!string.endsWith("/")) {
            string = string + "/";
        }
        relativePath = string;
    }

    public ConfigurableEmitter(String string) {
        this.name = string;
        this.leftToEmit = (int)this.emitCount.random();
        this.timeout = (int)this.length.random();
        this.colors.add(new ColorRecord(this, 0.0f, Color.white));
        this.colors.add(new ColorRecord(this, 1.0f, Color.red));
        ArrayList<Vector2f> arrayList = new ArrayList<Vector2f>();
        arrayList.add(new Vector2f(0.0f, 0.0f));
        arrayList.add(new Vector2f(1.0f, 255.0f));
        this.alpha = new LinearInterpolator(this, arrayList, 0, 255);
        arrayList = new ArrayList();
        arrayList.add(new Vector2f(0.0f, 0.0f));
        arrayList.add(new Vector2f(1.0f, 255.0f));
        this.size = new LinearInterpolator(this, arrayList, 0, 255);
        arrayList = new ArrayList();
        arrayList.add(new Vector2f(0.0f, 0.0f));
        arrayList.add(new Vector2f(1.0f, 1.0f));
        this.velocity = new LinearInterpolator(this, arrayList, 0, 1);
        arrayList = new ArrayList();
        arrayList.add(new Vector2f(0.0f, 0.0f));
        arrayList.add(new Vector2f(1.0f, 1.0f));
        this.scaleY = new LinearInterpolator(this, arrayList, 0, 1);
    }

    public void setImageName(String string) {
        if (string.length() == 0) {
            string = null;
        }
        this.imageName = string;
        if (string == null) {
            this.image = null;
        } else {
            this.updateImage = true;
        }
    }

    public String getImageName() {
        return this.imageName;
    }

    public String toString() {
        return "[" + this.name + "]";
    }

    public void setPosition(float f, float f2) {
        this.setPosition(f, f2, true);
    }

    public void setPosition(float f, float f2, boolean bl) {
        if (bl) {
            this.adjust = true;
            this.adjustx -= this.x - f;
            this.adjusty -= this.y - f2;
        }
        this.x = f;
        this.y = f2;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void setEnabled(boolean bl) {
        this.enabled = bl;
    }

    @Override
    public void update(ParticleSystem particleSystem, int n) {
        this.engine = particleSystem;
        if (!this.adjust) {
            this.adjustx = 0.0f;
            this.adjusty = 0.0f;
        } else {
            this.adjust = false;
        }
        if (this.updateImage) {
            this.updateImage = false;
            try {
                this.image = new Image(relativePath + this.imageName);
            }
            catch (SlickException slickException) {
                this.image = null;
                Log.error(slickException);
            }
        }
        if ((this.wrapUp || this.length.isEnabled() && this.timeout < 0 || this.emitCount.isEnabled() && this.leftToEmit <= 0) && this.particleCount == 0) {
            this.completed = true;
        }
        this.particleCount = 0;
        if (this.wrapUp) {
            return;
        }
        if (this.length.isEnabled()) {
            if (this.timeout < 0) {
                return;
            }
            this.timeout -= n;
        }
        if (this.emitCount.isEnabled() && this.leftToEmit <= 0) {
            return;
        }
        this.nextSpawn -= n;
        if (this.nextSpawn < 0) {
            this.nextSpawn = (int)this.spawnInterval.random();
            int n2 = (int)this.spawnCount.random();
            for (int i = 0; i < n2; ++i) {
                Particle particle = particleSystem.getNewParticle(this, this.initialLife.random());
                particle.setSize(this.initialSize.random());
                particle.setPosition(this.x + this.xOffset.random(), this.y + this.yOffset.random());
                particle.setVelocity(0.0f, 0.0f, 0.0f);
                float f = this.initialDistance.random();
                float f2 = this.speed.random();
                if (f != 0.0f || f2 != 0.0f) {
                    float f3 = this.spread.getValue(0.0f);
                    float f4 = f3 + this.angularOffset.getValue(0.0f) - this.spread.getValue() / 2.0f - 90.0f;
                    float f5 = (float)FastTrig.cos(Math.toRadians(f4)) * f;
                    float f6 = (float)FastTrig.sin(Math.toRadians(f4)) * f;
                    particle.adjustPosition(f5, f6);
                    float f7 = (float)FastTrig.cos(Math.toRadians(f4));
                    float f8 = (float)FastTrig.sin(Math.toRadians(f4));
                    particle.setVelocity(f7, f8, f2 * 0.001f);
                }
                if (this.image != null) {
                    particle.setImage(this.image);
                }
                ColorRecord colorRecord = (ColorRecord)this.colors.get(0);
                particle.setColor(colorRecord.col.r, colorRecord.col.g, colorRecord.col.b, this.startAlpha.getValue(0.0f) / 255.0f);
                particle.setUsePoint(this.usePoints);
                particle.setOriented(this.useOriented);
                if (!this.emitCount.isEnabled()) continue;
                --this.leftToEmit;
                if (this.leftToEmit <= 0) break;
            }
        }
    }

    @Override
    public void updateParticle(Particle particle, int n) {
        float f;
        ++this.particleCount;
        particle.x += this.adjustx;
        particle.y += this.adjusty;
        particle.adjustVelocity(this.windFactor.getValue(0.0f) * 5.0E-5f * (float)n, this.gravityFactor.getValue(0.0f) * 5.0E-5f * (float)n);
        float f2 = particle.getLife() / particle.getOriginalLife();
        float f3 = 1.0f - f2;
        float f4 = 0.0f;
        float f5 = 1.0f;
        Color color = null;
        Color color2 = null;
        for (int i = 0; i < this.colors.size() - 1; ++i) {
            ColorRecord colorRecord = (ColorRecord)this.colors.get(i);
            ColorRecord colorRecord2 = (ColorRecord)this.colors.get(i + 1);
            if (!(f3 >= colorRecord.pos) || !(f3 <= colorRecord2.pos)) continue;
            color = colorRecord.col;
            color2 = colorRecord2.col;
            f = colorRecord2.pos - colorRecord.pos;
            f4 = f3 - colorRecord.pos;
            f4 /= f;
            f4 = 1.0f - f4;
            f5 = 1.0f - f4;
        }
        if (color != null) {
            float f6 = color.r * f4 + color2.r * f5;
            float f7 = color.g * f4 + color2.g * f5;
            float f8 = color.b * f4 + color2.b * f5;
            f = this.alpha.isActive() ? this.alpha.getValue(f3) / 255.0f : this.startAlpha.getValue(0.0f) / 255.0f * f2 + this.endAlpha.getValue(0.0f) / 255.0f * f3;
            particle.setColor(f6, f7, f8, f);
        }
        if (this.size.isActive()) {
            float f9 = this.size.getValue(f3);
            particle.setSize(f9);
        } else {
            particle.adjustSize((float)n * this.growthFactor.getValue(0.0f) * 0.001f);
        }
        if (this.velocity.isActive()) {
            particle.setSpeed(this.velocity.getValue(f3));
        }
        if (this.scaleY.isActive()) {
            particle.setScaleY(this.scaleY.getValue(f3));
        }
    }

    public void replay() {
        this.reset();
        this.nextSpawn = 0;
        this.leftToEmit = (int)this.emitCount.random();
        this.timeout = (int)this.length.random();
    }

    public void reset() {
        this.completed = false;
        if (this.engine != null) {
            this.engine.releaseAll(this);
        }
    }

    public void replayCheck() {
        if (this == null && this.engine != null && this.engine.getParticleCount() == 0) {
            this.replay();
        }
    }

    public ConfigurableEmitter duplicate() {
        ConfigurableEmitter configurableEmitter = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ParticleIO.saveEmitter(byteArrayOutputStream, this);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            configurableEmitter = ParticleIO.loadEmitter(byteArrayInputStream);
        }
        catch (IOException iOException) {
            Log.error("Slick: ConfigurableEmitter.duplicate(): caught exception " + iOException.toString());
            return null;
        }
        return configurableEmitter;
    }

    public void addColorPoint(float f, Color color) {
        this.colors.add(new ColorRecord(this, f, color));
    }

    @Override
    public boolean useAdditive() {
        return this.useAdditive;
    }

    @Override
    public boolean isOriented() {
        return this.useOriented;
    }

    @Override
    public boolean usePoints(ParticleSystem particleSystem) {
        return this.usePoints == 1 && particleSystem.usePoints() || this.usePoints == 2;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public void wrapUp() {
        this.wrapUp = true;
    }

    @Override
    public void resetState() {
        this.wrapUp = false;
        this.replay();
    }

    public class Range {
        private float max;
        private float min;
        private boolean enabled;
        final ConfigurableEmitter this$0;

        private Range(ConfigurableEmitter configurableEmitter, float f, float f2) {
            this.this$0 = configurableEmitter;
            this.enabled = false;
            this.min = f;
            this.max = f2;
        }

        public float random() {
            return (float)((double)this.min + Math.random() * (double)(this.max - this.min));
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public void setEnabled(boolean bl) {
            this.enabled = bl;
        }

        public float getMax() {
            return this.max;
        }

        public void setMax(float f) {
            this.max = f;
        }

        public float getMin() {
            return this.min;
        }

        public void setMin(float f) {
            this.min = f;
        }

        Range(ConfigurableEmitter configurableEmitter, float f, float f2, I i) {
            this(configurableEmitter, f, f2);
        }
    }

    public class ColorRecord {
        public float pos;
        public Color col;
        final ConfigurableEmitter this$0;

        public ColorRecord(ConfigurableEmitter configurableEmitter, float f, Color color) {
            this.this$0 = configurableEmitter;
            this.pos = f;
            this.col = color;
        }
    }

    public class LinearInterpolator
    implements Value {
        private ArrayList curve;
        private boolean active;
        private int min;
        private int max;
        final ConfigurableEmitter this$0;

        public LinearInterpolator(ConfigurableEmitter configurableEmitter, ArrayList arrayList, int n, int n2) {
            this.this$0 = configurableEmitter;
            this.curve = arrayList;
            this.min = n;
            this.max = n2;
            this.active = false;
        }

        public void setCurve(ArrayList arrayList) {
            this.curve = arrayList;
        }

        public ArrayList getCurve() {
            return this.curve;
        }

        @Override
        public float getValue(float f) {
            Vector2f vector2f = (Vector2f)this.curve.get(0);
            for (int i = 1; i < this.curve.size(); ++i) {
                Vector2f vector2f2 = (Vector2f)this.curve.get(i);
                if (f >= vector2f.getX() && f <= vector2f2.getX()) {
                    float f2 = (f - vector2f.getX()) / (vector2f2.getX() - vector2f.getX());
                    float f3 = vector2f.getY() + f2 * (vector2f2.getY() - vector2f.getY());
                    return f3;
                }
                vector2f = vector2f2;
            }
            return 0.0f;
        }

        public boolean isActive() {
            return this.active;
        }

        public void setActive(boolean bl) {
            this.active = bl;
        }

        public int getMax() {
            return this.max;
        }

        public void setMax(int n) {
            this.max = n;
        }

        public int getMin() {
            return this.min;
        }

        public void setMin(int n) {
            this.min = n;
        }
    }

    public class RandomValue
    implements Value {
        private float value;
        final ConfigurableEmitter this$0;

        private RandomValue(ConfigurableEmitter configurableEmitter, float f) {
            this.this$0 = configurableEmitter;
            this.value = f;
        }

        @Override
        public float getValue(float f) {
            return (float)(Math.random() * (double)this.value);
        }

        public void setValue(float f) {
            this.value = f;
        }

        public float getValue() {
            return this.value;
        }

        RandomValue(ConfigurableEmitter configurableEmitter, float f, I i) {
            this(configurableEmitter, f);
        }
    }

    public class SimpleValue
    implements Value {
        private float value;
        private float next;
        final ConfigurableEmitter this$0;

        private SimpleValue(ConfigurableEmitter configurableEmitter, float f) {
            this.this$0 = configurableEmitter;
            this.value = f;
        }

        @Override
        public float getValue(float f) {
            return this.value;
        }

        public void setValue(float f) {
            this.value = f;
        }

        SimpleValue(ConfigurableEmitter configurableEmitter, float f, I i) {
            this(configurableEmitter, f);
        }
    }

    public static interface Value {
        public float getValue(float var1);
    }
}

