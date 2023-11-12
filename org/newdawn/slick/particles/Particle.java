/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.particles;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.particles.ParticleEmitter;
import org.newdawn.slick.particles.ParticleSystem;

public class Particle {
    protected static SGL GL = Renderer.get();
    public static final int INHERIT_POINTS = 1;
    public static final int USE_POINTS = 2;
    public static final int USE_QUADS = 3;
    protected float x;
    protected float y;
    protected float velx;
    protected float vely;
    protected float size = 10.0f;
    protected Color color = Color.white;
    protected float life;
    protected float originalLife;
    private ParticleSystem engine;
    private ParticleEmitter emitter;
    protected Image image;
    protected int type;
    protected int usePoints = 1;
    protected boolean oriented = false;
    protected float scaleY = 1.0f;

    public Particle(ParticleSystem particleSystem) {
        this.engine = particleSystem;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public void move(float f, float f2) {
        this.x += f;
        this.y += f2;
    }

    public float getSize() {
        return this.size;
    }

    public Color getColor() {
        return this.color;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public float getOriginalLife() {
        return this.originalLife;
    }

    public float getLife() {
        return this.life;
    }

    public boolean inUse() {
        return this.life > 0.0f;
    }

    public void render() {
        if (this.engine.usePoints() && this.usePoints == 1 || this.usePoints == 2) {
            TextureImpl.bindNone();
            GL.glEnable(2832);
            GL.glPointSize(this.size / 2.0f);
            this.color.bind();
            GL.glBegin(0);
            GL.glVertex2f(this.x, this.y);
            GL.glEnd();
        } else if (this.oriented || this.scaleY != 1.0f) {
            GL.glPushMatrix();
            GL.glTranslatef(this.x, this.y, 0.0f);
            if (this.oriented) {
                float f = (float)(Math.atan2(this.y, this.x) * 180.0 / Math.PI);
                GL.glRotatef(f, 0.0f, 0.0f, 1.0f);
            }
            GL.glScalef(1.0f, this.scaleY, 1.0f);
            this.image.draw((int)(-(this.size / 2.0f)), (int)(-(this.size / 2.0f)), (int)this.size, (int)this.size, this.color);
            GL.glPopMatrix();
        } else {
            this.color.bind();
            this.image.drawEmbedded((int)(this.x - this.size / 2.0f), (int)(this.y - this.size / 2.0f), (int)this.size, (int)this.size);
        }
    }

    public void update(int n) {
        this.emitter.updateParticle(this, n);
        this.life -= (float)n;
        if (this.life > 0.0f) {
            this.x += (float)n * this.velx;
            this.y += (float)n * this.vely;
        } else {
            this.engine.release(this);
        }
    }

    public void init(ParticleEmitter particleEmitter, float f) {
        this.x = 0.0f;
        this.emitter = particleEmitter;
        this.y = 0.0f;
        this.velx = 0.0f;
        this.vely = 0.0f;
        this.size = 10.0f;
        this.type = 0;
        this.originalLife = this.life = f;
        this.oriented = false;
        this.scaleY = 1.0f;
    }

    public void setType(int n) {
        this.type = n;
    }

    public void setUsePoint(int n) {
        this.usePoints = n;
    }

    public int getType() {
        return this.type;
    }

    public void setSize(float f) {
        this.size = f;
    }

    public void adjustSize(float f) {
        this.size += f;
        this.size = Math.max(0.0f, this.size);
    }

    public void setLife(float f) {
        this.life = f;
    }

    public void adjustLife(float f) {
        this.life += f;
    }

    public void kill() {
        this.life = 1.0f;
    }

    public void setColor(float f, float f2, float f3, float f4) {
        if (this.color == Color.white) {
            this.color = new Color(f, f2, f3, f4);
        } else {
            this.color.r = f;
            this.color.g = f2;
            this.color.b = f3;
            this.color.a = f4;
        }
    }

    public void setPosition(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public void setVelocity(float f, float f2, float f3) {
        this.velx = f * f3;
        this.vely = f2 * f3;
    }

    public void setSpeed(float f) {
        float f2 = (float)Math.sqrt(this.velx * this.velx + this.vely * this.vely);
        this.velx *= f;
        this.vely *= f;
        this.velx /= f2;
        this.vely /= f2;
    }

    public void setVelocity(float f, float f2) {
        this.setVelocity(f, f2, 1.0f);
    }

    public void adjustPosition(float f, float f2) {
        this.x += f;
        this.y += f2;
    }

    public void adjustColor(float f, float f2, float f3, float f4) {
        if (this.color == Color.white) {
            this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        }
        this.color.r += f;
        this.color.g += f2;
        this.color.b += f3;
        this.color.a += f4;
    }

    public void adjustColor(int n, int n2, int n3, int n4) {
        if (this.color == Color.white) {
            this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        }
        this.color.r += (float)n / 255.0f;
        this.color.g += (float)n2 / 255.0f;
        this.color.b += (float)n3 / 255.0f;
        this.color.a += (float)n4 / 255.0f;
    }

    public void adjustVelocity(float f, float f2) {
        this.velx += f;
        this.vely += f2;
    }

    public ParticleEmitter getEmitter() {
        return this.emitter;
    }

    public String toString() {
        return super.toString() + " : " + this.life;
    }

    public boolean isOriented() {
        return this.oriented;
    }

    public void setOriented(boolean bl) {
        this.oriented = bl;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public void setScaleY(float f) {
        this.scaleY = f;
    }
}

