/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.TexCoordGenerator;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.svg.Gradient;

public class RadialGradientFill
implements TexCoordGenerator {
    private Vector2f centre;
    private float radius;
    private Gradient gradient;
    private Shape shape;

    public RadialGradientFill(Shape shape, Transform transform, Gradient gradient) {
        this.gradient = gradient;
        this.radius = gradient.getR();
        float f = gradient.getX1();
        float f2 = gradient.getY1();
        float[] fArray = new float[]{f, f2};
        gradient.getTransform().transform(fArray, 0, fArray, 0, 1);
        transform.transform(fArray, 0, fArray, 0, 1);
        float[] fArray2 = new float[]{f, f2 - this.radius};
        gradient.getTransform().transform(fArray2, 0, fArray2, 0, 1);
        transform.transform(fArray2, 0, fArray2, 0, 1);
        this.centre = new Vector2f(fArray[0], fArray[1]);
        Vector2f vector2f = new Vector2f(fArray2[0], fArray2[1]);
        this.radius = vector2f.distance(this.centre);
    }

    @Override
    public Vector2f getCoordFor(float f, float f2) {
        float f3 = this.centre.distance(new Vector2f(f, f2));
        if ((f3 /= this.radius) > 0.99f) {
            f3 = 0.99f;
        }
        return new Vector2f(f3, 0.0f);
    }
}

