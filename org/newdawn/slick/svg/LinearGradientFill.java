/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg;

import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.TexCoordGenerator;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.svg.Gradient;

public class LinearGradientFill
implements TexCoordGenerator {
    private Vector2f start;
    private Vector2f end;
    private Gradient gradient;
    private Line line;
    private Shape shape;

    public LinearGradientFill(Shape shape, Transform transform, Gradient gradient) {
        this.gradient = gradient;
        float f = gradient.getX1();
        float f2 = gradient.getY1();
        float f3 = gradient.getX2();
        float f4 = gradient.getY2();
        float f5 = f4 - f2;
        float f6 = f3 - f;
        float[] fArray = new float[]{f, f2 + f5 / 2.0f};
        gradient.getTransform().transform(fArray, 0, fArray, 0, 1);
        transform.transform(fArray, 0, fArray, 0, 1);
        float[] fArray2 = new float[]{f + f6, f2 + f5 / 2.0f};
        gradient.getTransform().transform(fArray2, 0, fArray2, 0, 1);
        transform.transform(fArray2, 0, fArray2, 0, 1);
        this.start = new Vector2f(fArray[0], fArray[1]);
        this.end = new Vector2f(fArray2[0], fArray2[1]);
        this.line = new Line(this.start, this.end);
    }

    @Override
    public Vector2f getCoordFor(float f, float f2) {
        Vector2f vector2f = new Vector2f();
        this.line.getClosestPoint(new Vector2f(f, f2), vector2f);
        float f3 = vector2f.distance(this.start);
        return new Vector2f(f3 /= this.line.length(), 0.0f);
    }
}

