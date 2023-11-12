/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.particles.effects;

import org.newdawn.slick.Image;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;

public class FireEmitter
implements ParticleEmitter {
    private int x;
    private int y;
    private int interval = 50;
    private int timer;
    private float size = 40.0f;

    public FireEmitter() {
    }

    public FireEmitter(int n, int n2) {
        this.x = n;
        this.y = n2;
    }

    public FireEmitter(int n, int n2, float f) {
        this.x = n;
        this.y = n2;
        this.size = f;
    }

    @Override
    public void update(ParticleSystem particleSystem, int n) {
        this.timer -= n;
        if (this.timer <= 0) {
            this.timer = this.interval;
            Particle particle = particleSystem.getNewParticle(this, 1000.0f);
            particle.setColor(1.0f, 1.0f, 1.0f, 0.5f);
            particle.setPosition(this.x, this.y);
            particle.setSize(this.size);
            float f = (float)((double)-0.02f + Math.random() * (double)0.04f);
            float f2 = (float)(-(Math.random() * (double)0.15f));
            particle.setVelocity(f, f2, 1.1f);
        }
    }

    @Override
    public void updateParticle(Particle particle, int n) {
        if (particle.getLife() > 600.0f) {
            particle.adjustSize(0.07f * (float)n);
        } else {
            particle.adjustSize(-0.04f * (float)n * (this.size / 40.0f));
        }
        float f = 0.002f * (float)n;
        particle.adjustColor(0.0f, -f / 2.0f, -f * 2.0f, -f / 4.0f);
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void setEnabled(boolean bl) {
    }

    @Override
    public boolean completed() {
        return false;
    }

    @Override
    public boolean useAdditive() {
        return false;
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public boolean usePoints(ParticleSystem particleSystem) {
        return false;
    }

    @Override
    public boolean isOriented() {
        return false;
    }

    @Override
    public void wrapUp() {
    }

    @Override
    public void resetState() {
    }
}

