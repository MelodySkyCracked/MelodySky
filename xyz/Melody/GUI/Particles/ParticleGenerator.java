/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package xyz.Melody.GUI.Particles;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import xyz.Melody.GUI.Particles.Particle;
import xyz.Melody.GUI.Particles.RenderUtils;

public final class ParticleGenerator {
    private final List particles = new ArrayList();
    private final int amount;
    private int prevWidth;
    private int prevHeight;

    public ParticleGenerator(int n) {
        this.amount = n;
    }

    public void draw(int n, int n2) {
        if (this.particles.isEmpty() || this.prevWidth != Minecraft.func_71410_x().field_71443_c || this.prevHeight != Minecraft.func_71410_x().field_71440_d) {
            this.particles.clear();
            this.create();
        }
        this.prevWidth = Minecraft.func_71410_x().field_71443_c;
        this.prevHeight = Minecraft.func_71410_x().field_71440_d;
        for (Particle particle : this.particles) {
            boolean bl;
            particle.fall();
            particle.interpolation();
            int n3 = 60;
            boolean bl2 = bl = (float)n >= particle.x - (float)n3 && (float)n2 >= particle.y - (float)n3 && (float)n <= particle.x + (float)n3 && (float)n2 <= particle.y + (float)n3;
            if (bl) {
                this.particles.stream().filter(arg_0 -> ParticleGenerator.lambda$draw$0(particle, n3, arg_0)).forEach(arg_0 -> ParticleGenerator.lambda$draw$1(particle, arg_0));
            }
            RenderUtils.drawCircle(particle.getX(), particle.getY(), particle.size, new Color(255, 255, 255, 100).getRGB());
        }
    }

    private void create() {
        Random random = new Random();
        for (int i = 0; i < this.amount; ++i) {
            this.particles.add(new Particle(random.nextInt(Minecraft.func_71410_x().field_71443_c), random.nextInt(Minecraft.func_71410_x().field_71440_d)));
        }
    }

    private static void lambda$draw$1(Particle particle, Particle particle2) {
        particle.connect(particle2.getX(), particle2.getY());
    }

    private static boolean lambda$draw$0(Particle particle, int n, Particle particle2) {
        return particle2.getX() > particle.getX() && particle2.getX() - particle.getX() < (float)n && particle.getX() - particle2.getX() < (float)n && (particle2.getY() > particle.getY() && particle2.getY() - particle.getY() < (float)n || particle.getY() > particle2.getY() && particle.getY() - particle2.getY() < (float)n);
    }
}

