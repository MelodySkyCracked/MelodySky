/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package pw.knx.feather.tessellate;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import pw.knx.feather.tessellate.Tessellation;

public class BasicTess
implements Tessellation {
    int index;
    int[] raw = new int[n *= 6];
    ByteBuffer buffer;
    FloatBuffer fBuffer;
    IntBuffer iBuffer;
    private int colors;
    private float texU;
    private float texV;
    private boolean color;
    private boolean texture;

    BasicTess(int n) {
        this.buffer = ByteBuffer.allocateDirect(n * 4).order(ByteOrder.nativeOrder());
        this.fBuffer = this.buffer.asFloatBuffer();
        this.iBuffer = this.buffer.asIntBuffer();
    }

    @Override
    public Tessellation setColor(int n) {
        this.color = true;
        this.colors = n;
        return this;
    }

    @Override
    public Tessellation setTexture(float f, float f2) {
        this.texture = true;
        this.texU = f;
        this.texV = f2;
        return this;
    }

    @Override
    public Tessellation addVertex(float f, float f2, float f3) {
        int n = this.index * 6;
        this.raw[n] = Float.floatToRawIntBits(f);
        this.raw[n + 1] = Float.floatToRawIntBits(f2);
        this.raw[n + 2] = Float.floatToRawIntBits(f3);
        this.raw[n + 3] = this.colors;
        this.raw[n + 4] = Float.floatToRawIntBits(this.texU);
        this.raw[n + 5] = Float.floatToRawIntBits(this.texV);
        ++this.index;
        return this;
    }

    @Override
    public Tessellation bind() {
        int n = this.index * 6;
        this.iBuffer.put(this.raw, 0, n);
        this.buffer.position(0);
        this.buffer.limit(n * 4);
        if (this.color) {
            this.buffer.position(12);
            GL11.glColorPointer((int)4, (boolean)true, (int)24, (ByteBuffer)this.buffer);
        }
        if (this.texture) {
            this.fBuffer.position(4);
            GL11.glTexCoordPointer((int)2, (int)24, (FloatBuffer)this.fBuffer);
        }
        this.fBuffer.position(0);
        GL11.glVertexPointer((int)3, (int)24, (FloatBuffer)this.fBuffer);
        return this;
    }

    @Override
    public Tessellation pass(int n) {
        GL11.glDrawArrays((int)n, (int)0, (int)this.index);
        return this;
    }

    @Override
    public Tessellation unbind() {
        this.iBuffer.position(0);
        return this;
    }

    @Override
    public Tessellation reset() {
        this.iBuffer.clear();
        this.index = 0;
        this.color = false;
        this.texture = false;
        return this;
    }
}

