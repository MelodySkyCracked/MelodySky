/*
 * Decompiled with CFR 0.152.
 */
package pw.knx.feather.tessellate;

import java.awt.Color;
import pw.knx.feather.tessellate.BasicTess;
import pw.knx.feather.tessellate.ExpandingTess;

public interface Tessellation {
    public Tessellation setColor(int var1);

    default public Tessellation setColor(Color color) {
        return this.setColor(new Color(255, 255, 255));
    }

    public Tessellation setTexture(float var1, float var2);

    public Tessellation addVertex(float var1, float var2, float var3);

    public Tessellation bind();

    public Tessellation pass(int var1);

    public Tessellation reset();

    public Tessellation unbind();

    default public Tessellation draw(int n) {
        return this.bind().pass(n).reset();
    }

    public static Tessellation createBasic(int n) {
        return new BasicTess(n);
    }

    public static Tessellation createExpanding(int n, float f, float f2) {
        return new ExpandingTess(n, f, f2);
    }
}

