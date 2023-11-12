/*
 * Decompiled with CFR 0.152.
 */
package pw.knx.feather.tessellate;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import pw.knx.feather.tessellate.BasicTess;
import pw.knx.feather.tessellate.Tessellation;

public class ExpandingTess
extends BasicTess {
    private final float ratio;
    private final float factor;

    ExpandingTess(int n, float f, float f2) {
        super(n);
        this.ratio = f;
        this.factor = f2;
    }

    @Override
    public Tessellation addVertex(float f, float f2, float f3) {
        int n = this.raw.length;
        if ((float)(this.index * 6) >= (float)n * this.ratio) {
            n = (int)((float)n * this.factor);
            int[] nArray = new int[n];
            System.arraycopy(this.raw, 0, nArray, 0, this.raw.length);
            this.raw = nArray;
            this.buffer = ByteBuffer.allocateDirect(n * 4).order(ByteOrder.nativeOrder());
            this.iBuffer = this.buffer.asIntBuffer();
            this.fBuffer = this.buffer.asFloatBuffer();
        }
        return super.addVertex(f, f2, f3);
    }
}

