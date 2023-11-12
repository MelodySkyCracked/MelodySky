/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.particles;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.lI;
import org.newdawn.slick.util.Log;

public class ParticleSystem {
    protected SGL GL = Renderer.get();
    public static final int BLEND_ADDITIVE = 1;
    public static final int BLEND_COMBINE = 2;
    private static final int DEFAULT_PARTICLES = 100;
    private ArrayList removeMe = new ArrayList();
    protected HashMap particlesByEmitter = new HashMap();
    protected int maxParticlesPerEmitter;
    protected ArrayList emitters = new ArrayList();
    protected Particle dummy;
    private int blendingMode = 2;
    private int pCount;
    private boolean usePoints;
    private float x;
    private float y;
    private boolean removeCompletedEmitters = true;
    private Image sprite;
    private boolean visible = true;
    private String defaultImageName;
    private Color mask;

    public static void setRelativePath(String string) {
        ConfigurableEmitter.setRelativePath(string);
    }

    public ParticleSystem(Image image) {
        this(image, 100);
    }

    public ParticleSystem(String string) {
        this(string, 100);
    }

    public void reset() {
        for (ParticlePool particlePool : this.particlesByEmitter.values()) {
            particlePool.reset(this);
        }
        for (int i = 0; i < this.emitters.size(); ++i) {
            ParticleEmitter particleEmitter = (ParticleEmitter)this.emitters.get(i);
            particleEmitter.resetState();
        }
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean bl) {
        this.visible = bl;
    }

    public void setRemoveCompletedEmitters(boolean bl) {
        this.removeCompletedEmitters = bl;
    }

    public void setUsePoints(boolean bl) {
        this.usePoints = bl;
    }

    public boolean usePoints() {
        return this.usePoints;
    }

    public ParticleSystem(String string, int n) {
        this(string, n, null);
    }

    public ParticleSystem(String string, int n, Color color) {
        this.maxParticlesPerEmitter = n;
        this.mask = color;
        this.setDefaultImageName(string);
        this.dummy = this.createParticle(this);
    }

    public ParticleSystem(Image image, int n) {
        this.maxParticlesPerEmitter = n;
        this.sprite = image;
        this.dummy = this.createParticle(this);
    }

    public void setDefaultImageName(String string) {
        this.defaultImageName = string;
        this.sprite = null;
    }

    public int getBlendingMode() {
        return this.blendingMode;
    }

    protected Particle createParticle(ParticleSystem particleSystem) {
        return new Particle(particleSystem);
    }

    public void setBlendingMode(int n) {
        this.blendingMode = n;
    }

    public int getEmitterCount() {
        return this.emitters.size();
    }

    public ParticleEmitter getEmitter(int n) {
        return (ParticleEmitter)this.emitters.get(n);
    }

    public void addEmitter(ParticleEmitter particleEmitter) {
        this.emitters.add(particleEmitter);
        ParticlePool particlePool = new ParticlePool(this, this, this.maxParticlesPerEmitter);
        this.particlesByEmitter.put(particleEmitter, particlePool);
    }

    public void removeEmitter(ParticleEmitter particleEmitter) {
        this.emitters.remove(particleEmitter);
        this.particlesByEmitter.remove(particleEmitter);
    }

    public void removeAllEmitters() {
        for (int i = 0; i < this.emitters.size(); ++i) {
            this.removeEmitter((ParticleEmitter)this.emitters.get(i));
            --i;
        }
    }

    public float getPositionX() {
        return this.x;
    }

    public float getPositionY() {
        return this.y;
    }

    public void setPosition(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public void render() {
        this.render(this.x, this.y);
    }

    public void render(float f, float f2) {
        if (this.sprite == null && this.defaultImageName != null) {
            this.loadSystemParticleImage();
        }
        if (!this.visible) {
            return;
        }
        this.GL.glTranslatef(f, f2, 0.0f);
        if (this.blendingMode == 1) {
            this.GL.glBlendFunc(770, 1);
        }
        if (this.usePoints()) {
            this.GL.glEnable(2832);
            TextureImpl.bindNone();
        }
        for (int i = 0; i < this.emitters.size(); ++i) {
            ParticleEmitter particleEmitter = (ParticleEmitter)this.emitters.get(i);
            if (!particleEmitter.isEnabled()) continue;
            if (particleEmitter.useAdditive()) {
                this.GL.glBlendFunc(770, 1);
            }
            ParticlePool particlePool = (ParticlePool)this.particlesByEmitter.get(particleEmitter);
            Image image = particleEmitter.getImage();
            if (image == null) {
                image = this.sprite;
            }
            if (!particleEmitter.isOriented() && !particleEmitter.usePoints(this)) {
                image.startUse();
            }
            for (int j = 0; j < particlePool.particles.length; ++j) {
                if (!particlePool.particles[j].inUse()) continue;
                particlePool.particles[j].render();
            }
            if (!particleEmitter.isOriented() && !particleEmitter.usePoints(this)) {
                image.endUse();
            }
            if (!particleEmitter.useAdditive()) continue;
            this.GL.glBlendFunc(770, 771);
        }
        if (this.usePoints()) {
            this.GL.glDisable(2832);
        }
        if (this.blendingMode == 1) {
            this.GL.glBlendFunc(770, 771);
        }
        Color.white.bind();
        this.GL.glTranslatef(-f, -f2, 0.0f);
    }

    private void loadSystemParticleImage() {
        AccessController.doPrivileged(new lI(this));
    }

    public void update(int n) {
        if (this.sprite == null && this.defaultImageName != null) {
            this.loadSystemParticleImage();
        }
        this.removeMe.clear();
        ArrayList arrayList = new ArrayList(this.emitters);
        for (int i = 0; i < arrayList.size(); ++i) {
            ParticleEmitter particleEmitter = (ParticleEmitter)arrayList.get(i);
            if (!particleEmitter.isEnabled()) continue;
            particleEmitter.update(this, n);
            if (!this.removeCompletedEmitters || !particleEmitter.completed()) continue;
            this.removeMe.add(particleEmitter);
            this.particlesByEmitter.remove(particleEmitter);
        }
        this.emitters.removeAll(this.removeMe);
        this.pCount = 0;
        if (!this.particlesByEmitter.isEmpty()) {
            for (ParticleEmitter particleEmitter : this.particlesByEmitter.keySet()) {
                if (!particleEmitter.isEnabled()) continue;
                ParticlePool particlePool = (ParticlePool)this.particlesByEmitter.get(particleEmitter);
                for (int i = 0; i < particlePool.particles.length; ++i) {
                    if (!(particlePool.particles[i].life > 0.0f)) continue;
                    particlePool.particles[i].update(n);
                    ++this.pCount;
                }
            }
        }
    }

    public int getParticleCount() {
        return this.pCount;
    }

    public Particle getNewParticle(ParticleEmitter particleEmitter, float f) {
        ParticlePool particlePool = (ParticlePool)this.particlesByEmitter.get(particleEmitter);
        ArrayList arrayList = particlePool.available;
        if (arrayList.size() > 0) {
            Particle particle = (Particle)arrayList.remove(arrayList.size() - 1);
            particle.init(particleEmitter, f);
            particle.setImage(this.sprite);
            return particle;
        }
        Log.warn("Ran out of particles (increase the limit)!");
        return this.dummy;
    }

    public void release(Particle particle) {
        if (particle != this.dummy) {
            ParticlePool particlePool = (ParticlePool)this.particlesByEmitter.get(particle.getEmitter());
            particlePool.available.add(particle);
        }
    }

    public void releaseAll(ParticleEmitter particleEmitter) {
        if (!this.particlesByEmitter.isEmpty()) {
            for (ParticlePool particlePool : this.particlesByEmitter.values()) {
                for (int i = 0; i < particlePool.particles.length; ++i) {
                    if (!particlePool.particles[i].inUse() || particlePool.particles[i].getEmitter() != particleEmitter) continue;
                    particlePool.particles[i].setLife(-1.0f);
                    this.release(particlePool.particles[i]);
                }
            }
        }
    }

    public void moveAll(ParticleEmitter particleEmitter, float f, float f2) {
        ParticlePool particlePool = (ParticlePool)this.particlesByEmitter.get(particleEmitter);
        for (int i = 0; i < particlePool.particles.length; ++i) {
            if (!particlePool.particles[i].inUse()) continue;
            particlePool.particles[i].move(f, f2);
        }
    }

    public ParticleSystem duplicate() throws SlickException {
        for (int i = 0; i < this.emitters.size(); ++i) {
            if (this.emitters.get(i) instanceof ConfigurableEmitter) continue;
            throw new SlickException("Only systems contianing configurable emitters can be duplicated");
        }
        ParticleSystem particleSystem = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ParticleIO.saveConfiguredSystem(byteArrayOutputStream, this);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            particleSystem = ParticleIO.loadConfiguredSystem(byteArrayInputStream);
        }
        catch (IOException iOException) {
            Log.error("Failed to duplicate particle system");
            throw new SlickException("Unable to duplicated particle system", iOException);
        }
        return particleSystem;
    }

    static Color access$000(ParticleSystem particleSystem) {
        return particleSystem.mask;
    }

    static Image access$102(ParticleSystem particleSystem, Image image) {
        particleSystem.sprite = image;
        return particleSystem.sprite;
    }

    static String access$200(ParticleSystem particleSystem) {
        return particleSystem.defaultImageName;
    }

    static String access$202(ParticleSystem particleSystem, String string) {
        particleSystem.defaultImageName = string;
        return particleSystem.defaultImageName;
    }

    private class ParticlePool {
        public Particle[] particles;
        public ArrayList available;
        final ParticleSystem this$0;

        public ParticlePool(ParticleSystem particleSystem, ParticleSystem particleSystem2, int n) {
            this.this$0 = particleSystem;
            this.particles = new Particle[n];
            this.available = new ArrayList();
            for (int i = 0; i < this.particles.length; ++i) {
                this.particles[i] = particleSystem.createParticle(particleSystem2);
            }
            this.reset(particleSystem2);
        }

        public void reset(ParticleSystem particleSystem) {
            this.available.clear();
            for (int i = 0; i < this.particles.length; ++i) {
                this.available.add(this.particles[i]);
            }
        }
    }
}

