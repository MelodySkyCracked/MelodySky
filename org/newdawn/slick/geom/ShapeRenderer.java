/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import org.newdawn.slick.Image;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.I;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.TexCoordGenerator;
import org.newdawn.slick.geom.Triangulator;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.l;
import org.newdawn.slick.geom.lI;
import org.newdawn.slick.geom.lII;
import org.newdawn.slick.geom.lIl;
import org.newdawn.slick.geom.ll;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.LineStripRenderer;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

public final class ShapeRenderer {
    private static SGL GL = Renderer.get();
    private static LineStripRenderer LSR = Renderer.getLineStripRenderer();

    public static final void draw(Shape shape) {
        Texture texture = TextureImpl.getLastBind();
        TextureImpl.bindNone();
        float[] fArray = shape.getPoints();
        LSR.start();
        for (int i = 0; i < fArray.length; i += 2) {
            LSR.vertex(fArray[i], fArray[i + 1]);
        }
        if (shape.closed()) {
            LSR.vertex(fArray[0], fArray[1]);
        }
        LSR.end();
        if (texture == null) {
            TextureImpl.bindNone();
        } else {
            texture.bind();
        }
    }

    public static final void draw(Shape shape, ShapeFill shapeFill) {
        float[] fArray = shape.getPoints();
        Texture texture = TextureImpl.getLastBind();
        TextureImpl.bindNone();
        float[] fArray2 = shape.getCenter();
        GL.glBegin(3);
        for (int i = 0; i < fArray.length; i += 2) {
            shapeFill.colorAt(shape, fArray[i], fArray[i + 1]).bind();
            Vector2f vector2f = shapeFill.getOffsetAt(shape, fArray[i], fArray[i + 1]);
            GL.glVertex2f(fArray[i] + vector2f.x, fArray[i + 1] + vector2f.y);
        }
        if (shape.closed()) {
            shapeFill.colorAt(shape, fArray[0], fArray[1]).bind();
            Vector2f vector2f = shapeFill.getOffsetAt(shape, fArray[0], fArray[1]);
            GL.glVertex2f(fArray[0] + vector2f.x, fArray[1] + vector2f.y);
        }
        GL.glEnd();
        if (texture == null) {
            TextureImpl.bindNone();
        } else {
            texture.bind();
        }
    }

    public static final void fill(Shape shape) {
        if (shape == null) {
            return;
        }
        Texture texture = TextureImpl.getLastBind();
        TextureImpl.bindNone();
        ShapeRenderer.fill(shape, new I());
        if (texture == null) {
            TextureImpl.bindNone();
        } else {
            texture.bind();
        }
    }

    private static final void fill(Shape shape, PointCallback pointCallback) {
        Triangulator triangulator = shape.getTriangles();
        GL.glBegin(4);
        for (int i = 0; i < triangulator.getTriangleCount(); ++i) {
            for (int j = 0; j < 3; ++j) {
                float[] fArray = triangulator.getTrianglePoint(i, j);
                float[] fArray2 = pointCallback.preRenderPoint(shape, fArray[0], fArray[1]);
                if (fArray2 == null) {
                    GL.glVertex2f(fArray[0], fArray[1]);
                    continue;
                }
                GL.glVertex2f(fArray2[0], fArray2[1]);
            }
        }
        GL.glEnd();
    }

    public static final void texture(Shape shape, Image image) {
        ShapeRenderer.texture(shape, image, 0.01f, 0.01f);
    }

    public static final void textureFit(Shape shape, Image image) {
        ShapeRenderer.textureFit(shape, image, 1.0f, 1.0f);
    }

    public static final void texture(Shape shape, Image image, float f, float f2) {
        if (shape == null) {
            return;
        }
        Texture texture = TextureImpl.getLastBind();
        image.getTexture().bind();
        ShapeRenderer.fill(shape, new ll(f, f2, image));
        float[] fArray = shape.getPoints();
        if (texture == null) {
            TextureImpl.bindNone();
        } else {
            texture.bind();
        }
    }

    public static final void textureFit(Shape shape, Image image, float f, float f2) {
        if (shape == null) {
            return;
        }
        float[] fArray = shape.getPoints();
        Texture texture = TextureImpl.getLastBind();
        image.getTexture().bind();
        float f3 = shape.getX();
        float f4 = shape.getY();
        float f5 = shape.getMaxX() - f3;
        float f6 = shape.getMaxY() - f4;
        ShapeRenderer.fill(shape, new lIl(f, f2, image));
        if (texture == null) {
            TextureImpl.bindNone();
        } else {
            texture.bind();
        }
    }

    public static final void fill(Shape shape, ShapeFill shapeFill) {
        if (shape == null) {
            return;
        }
        Texture texture = TextureImpl.getLastBind();
        TextureImpl.bindNone();
        float[] fArray = shape.getCenter();
        ShapeRenderer.fill(shape, new lII(shapeFill));
        if (texture == null) {
            TextureImpl.bindNone();
        } else {
            texture.bind();
        }
    }

    public static final void texture(Shape shape, Image image, float f, float f2, ShapeFill shapeFill) {
        if (shape == null) {
            return;
        }
        Texture texture = TextureImpl.getLastBind();
        image.getTexture().bind();
        float[] fArray = shape.getCenter();
        ShapeRenderer.fill(shape, new lI(shapeFill, fArray, f, f2, image));
        if (texture == null) {
            TextureImpl.bindNone();
        } else {
            texture.bind();
        }
    }

    public static final void texture(Shape shape, Image image, TexCoordGenerator texCoordGenerator) {
        Texture texture = TextureImpl.getLastBind();
        image.getTexture().bind();
        float[] fArray = shape.getCenter();
        ShapeRenderer.fill(shape, new l(texCoordGenerator));
        if (texture == null) {
            TextureImpl.bindNone();
        } else {
            texture.bind();
        }
    }

    static SGL access$000() {
        return GL;
    }

    private static interface PointCallback {
        public float[] preRenderPoint(Shape var1, float var2, float var3);
    }
}

