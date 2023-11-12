/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.WorldRenderer
 *  net.minecraft.client.renderer.vertex.VertexFormat
 */
package xyz.Melody.injection.mixins.XRay;

import java.awt.Color;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import xyz.Melody.module.modules.render.XRay;

@Mixin(value={WorldRenderer.class})
public class MixinWorldRenderer {
    @Shadow
    private int field_178997_d;
    @Shadow
    private VertexFormat field_179011_q;
    @Shadow
    public IntBuffer field_178999_b;
    @Shadow
    private boolean field_78939_q;

    @Shadow
    public int func_78909_a(int n) {
        return 0;
    }

    @Overwrite
    public void func_178978_a(float f, float f2, float f3, int n) {
        int n2 = this.func_78909_a(n);
        int n3 = -1;
        if (!this.field_78939_q) {
            n3 = this.field_178999_b.get(n2);
            if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                int n4 = (int)((float)(n3 & 0xFF) * f);
                int n5 = (int)((float)(n3 >> 8 & 0xFF) * f2);
                int n6 = (int)((float)(n3 >> 16 & 0xFF) * f3);
                n3 &= 0xFF000000;
                n3 = n3 | n6 << 16 | n5 << 8 | n4;
                if (XRay.getINSTANCE().isEnabled()) {
                    n3 = this.setXRayAlpha(n3);
                }
            } else {
                int n7 = (int)((float)(n3 >> 24 & 0xFF) * f);
                int n8 = (int)((float)(n3 >> 16 & 0xFF) * f2);
                int n9 = (int)((float)(n3 >> 8 & 0xFF) * f3);
                n3 &= 0xFF;
                n3 = n3 | n7 << 24 | n8 << 16 | n9 << 8;
                if (XRay.getINSTANCE().isEnabled()) {
                    n3 = this.setXRayAlpha(n3);
                }
            }
        }
        this.field_178999_b.put(n2, n3);
    }

    public int setXRayAlpha(int n) {
        Color color = new Color(n);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), 140).getRGB();
    }
}

