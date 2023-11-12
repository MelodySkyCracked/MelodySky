/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import java.io.Serializable;

public final class Point
implements Serializable {
    public int x;
    public int y;
    static final long serialVersionUID = 3257002163938146354L;

    public Point(int n, int n2) {
        this.x = n;
        this.y = n2;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Point)) {
            return false;
        }
        Point point = (Point)object;
        return point.x == this.x && point.y == this.y;
    }

    public int hashCode() {
        return this.x ^ this.y;
    }

    public String toString() {
        return "Point {" + this.x + ", " + this.y;
    }
}

