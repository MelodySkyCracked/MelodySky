/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.geom;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.geom.Triangulator;
import org.newdawn.slick.geom.Vector2f;

public class MannTriangulator
implements Triangulator {
    private static final double EPSILON = 1.0E-5;
    protected PointBag contour;
    protected PointBag holes;
    private PointBag nextFreePointBag;
    private Point nextFreePoint;
    private List triangles = new ArrayList();

    public MannTriangulator() {
        this.contour = this.getPointBag();
    }

    @Override
    public void addPolyPoint(float f, float f2) {
        this.addPoint(new Vector2f(f, f2));
    }

    public void reset() {
        while (this.holes != null) {
            this.holes = this.freePointBag(this.holes);
        }
        this.contour.clear();
        this.holes = null;
    }

    @Override
    public void startHole() {
        PointBag pointBag = this.getPointBag();
        pointBag.next = this.holes;
        this.holes = pointBag;
    }

    private void addPoint(Vector2f vector2f) {
        if (this.holes == null) {
            Point point = this.getPoint(vector2f);
            this.contour.add(point);
        } else {
            Point point = this.getPoint(vector2f);
            this.holes.add(point);
        }
    }

    private Vector2f[] triangulate(Vector2f[] vector2fArray) {
        Point point;
        Point point2;
        this.contour.computeAngles();
        Serializable serializable = this.holes;
        while (serializable != null) {
            ((PointBag)serializable).computeAngles();
            serializable = ((PointBag)serializable).next;
        }
        while (this.holes != null) {
            serializable = this.holes.first;
            block2: do {
                if (!(((Point)serializable).angle <= 0.0)) continue;
                Point point3 = this.contour.first;
                do {
                    if (!((Point)serializable).isInfront(point3) || !point3.isInfront((Point)serializable) || this.contour.doesIntersectSegment(((Point)serializable).pt, point3.pt)) continue;
                    PointBag pointBag = this.holes;
                    while (!pointBag.doesIntersectSegment(((Point)serializable).pt, point3.pt)) {
                        pointBag = pointBag.next;
                        if (pointBag != null) continue;
                        point2 = this.getPoint(point3.pt);
                        point3.insertAfter(point2);
                        point = this.getPoint(((Point)serializable).pt);
                        ((Point)serializable).insertBefore(point);
                        point3.next = serializable;
                        ((Point)serializable).prev = point3;
                        point.next = point2;
                        point2.prev = point;
                        point3.computeAngle();
                        ((Point)serializable).computeAngle();
                        point2.computeAngle();
                        point.computeAngle();
                        this.holes.first = null;
                        break block2;
                    }
                } while ((point3 = point3.next) != this.contour.first);
            } while ((serializable = ((Point)serializable).next) != this.holes.first);
            this.holes = this.freePointBag(this.holes);
        }
        int n = this.contour.countPoints() - 2;
        int n2 = n * 3 + 1;
        if (vector2fArray.length < n2) {
            vector2fArray = (Vector2f[])Array.newInstance(vector2fArray.getClass().getComponentType(), n2);
        }
        int n3 = 0;
        while ((point2 = this.contour.first) != null && point2.next != point2.prev) {
            Point point4;
            do {
                if (!(point2.angle > 0.0)) continue;
                point = point2.prev;
                point4 = point2.next;
                if (point4.next != point && (!point.isInfront(point4) || !point4.isInfront(point)) || this.contour.doesIntersectSegment(point.pt, point4.pt)) continue;
                vector2fArray[n3++] = point2.pt;
                vector2fArray[n3++] = point4.pt;
                vector2fArray[n3++] = point.pt;
                break;
            } while ((point2 = point2.next) != this.contour.first);
            point = point2.prev;
            point4 = point2.next;
            this.contour.first = point;
            point2.unlink();
            this.freePoint(point2);
            point4.computeAngle();
            point.computeAngle();
        }
        vector2fArray[n3] = null;
        this.contour.clear();
        return vector2fArray;
    }

    private PointBag getPointBag() {
        PointBag pointBag = this.nextFreePointBag;
        if (pointBag != null) {
            this.nextFreePointBag = pointBag.next;
            pointBag.next = null;
            return pointBag;
        }
        return new PointBag(this);
    }

    private PointBag freePointBag(PointBag pointBag) {
        PointBag pointBag2 = pointBag.next;
        pointBag.clear();
        pointBag.next = this.nextFreePointBag;
        this.nextFreePointBag = pointBag;
        return pointBag2;
    }

    private Point getPoint(Vector2f vector2f) {
        Point point = this.nextFreePoint;
        if (point != null) {
            this.nextFreePoint = point.next;
            point.next = null;
            point.prev = null;
            point.pt = vector2f;
            return point;
        }
        return new Point(vector2f);
    }

    private void freePoint(Point point) {
        point.next = this.nextFreePoint;
        this.nextFreePoint = point;
    }

    private void freePoints(Point point) {
        point.prev.next = this.nextFreePoint;
        point.prev = null;
        this.nextFreePoint = point;
    }

    @Override
    public boolean triangulate() {
        Vector2f[] vector2fArray = this.triangulate(new Vector2f[0]);
        for (int i = 0; i < vector2fArray.length && vector2fArray[i] != null; ++i) {
            this.triangles.add(vector2fArray[i]);
        }
        return true;
    }

    @Override
    public int getTriangleCount() {
        return this.triangles.size() / 3;
    }

    @Override
    public float[] getTrianglePoint(int n, int n2) {
        Vector2f vector2f = (Vector2f)this.triangles.get(n * 3 + n2);
        return new float[]{vector2f.x, vector2f.y};
    }

    static void access$000(MannTriangulator mannTriangulator, Point point) {
        mannTriangulator.freePoints(point);
    }

    protected class PointBag
    implements Serializable {
        protected Point first;
        protected PointBag next;
        final MannTriangulator this$0;

        protected PointBag(MannTriangulator mannTriangulator) {
            this.this$0 = mannTriangulator;
        }

        public void clear() {
            if (this.first != null) {
                MannTriangulator.access$000(this.this$0, this.first);
                this.first = null;
            }
        }

        public void add(Point point) {
            if (this.first != null) {
                this.first.insertBefore(point);
            } else {
                this.first = point;
                point.next = point;
                point.prev = point;
            }
        }

        public void computeAngles() {
            if (this.first == null) {
                return;
            }
            Point point = this.first;
            do {
                point.computeAngle();
            } while ((point = point.next) != this.first);
        }

        public boolean doesIntersectSegment(Vector2f vector2f, Vector2f vector2f2) {
            double d = vector2f2.x - vector2f.x;
            double d2 = vector2f2.y - vector2f.y;
            Point point = this.first;
            while (true) {
                double d3;
                double d4;
                double d5;
                Point point2 = point.next;
                if (point.pt != vector2f && point2.pt != vector2f && point.pt != vector2f2 && point2.pt != vector2f2 && Math.abs(d5 = d * (d4 = (double)(point2.pt.y - point.pt.y)) - d2 * (d3 = (double)(point2.pt.x - point.pt.x))) > 1.0E-5) {
                    double d6 = point.pt.x - vector2f.x;
                    double d7 = point.pt.y - vector2f.y;
                    double d8 = (d4 * d6 - d3 * d7) / d5;
                    double d9 = (d2 * d6 - d * d7) / d5;
                    if (d8 >= 0.0 && d8 <= 1.0 && d9 >= 0.0 && d9 <= 1.0) {
                        return true;
                    }
                }
                if (point2 == this.first) {
                    return false;
                }
                point = point2;
            }
        }

        public int countPoints() {
            if (this.first == null) {
                return 0;
            }
            int n = 0;
            Point point = this.first;
            do {
                ++n;
            } while ((point = point.next) != this.first);
            return n;
        }

        public boolean contains(Vector2f vector2f) {
            if (this.first == null) {
                return false;
            }
            if (this.first.prev.pt.equals(vector2f)) {
                return true;
            }
            return this.first.pt.equals(vector2f);
        }
    }

    private static class Point
    implements Serializable {
        protected Vector2f pt;
        protected Point prev;
        protected Point next;
        protected double nx;
        protected double ny;
        protected double angle;
        protected double dist;

        public Point(Vector2f vector2f) {
            this.pt = vector2f;
        }

        public void unlink() {
            this.prev.next = this.next;
            this.next.prev = this.prev;
            this.next = null;
            this.prev = null;
        }

        public void insertBefore(Point point) {
            this.prev.next = point;
            point.prev = this.prev;
            point.next = this;
            this.prev = point;
        }

        public void insertAfter(Point point) {
            this.next.prev = point;
            point.prev = this;
            point.next = this.next;
            this.next = point;
        }

        private double hypot(double d, double d2) {
            return Math.sqrt(d * d + d2 * d2);
        }

        public void computeAngle() {
            if (this.prev.pt.equals(this.pt)) {
                this.pt.x += 0.01f;
            }
            double d = this.pt.x - this.prev.pt.x;
            double d2 = this.pt.y - this.prev.pt.y;
            double d3 = this.hypot(d, d2);
            d /= d3;
            d2 /= d3;
            if (this.next.pt.equals(this.pt)) {
                this.pt.y += 0.01f;
            }
            double d4 = this.next.pt.x - this.pt.x;
            double d5 = this.next.pt.y - this.pt.y;
            double d6 = this.hypot(d4, d5);
            double d7 = -d2;
            double d8 = d;
            this.nx = (d7 - (d5 /= d6)) * 0.5;
            this.ny = (d8 + (d4 /= d6)) * 0.5;
            if (this.nx * this.nx + this.ny * this.ny < 1.0E-5) {
                this.nx = d;
                this.ny = d5;
                this.angle = 1.0;
                if (d * d4 + d2 * d5 > 0.0) {
                    this.nx = -d;
                    this.ny = -d2;
                }
            } else {
                this.angle = this.nx * d4 + this.ny * d5;
            }
        }

        public double getAngle(Point point) {
            double d = point.pt.x - this.pt.x;
            double d2 = point.pt.y - this.pt.y;
            double d3 = this.hypot(d, d2);
            return (this.nx * d + this.ny * d2) / d3;
        }

        public boolean isConcave() {
            return this.angle < 0.0;
        }

        public boolean isInfront(double d, double d2) {
            boolean bl = (double)(this.prev.pt.y - this.pt.y) * d + (double)(this.pt.x - this.prev.pt.x) * d2 >= 0.0;
            boolean bl2 = (double)(this.pt.y - this.next.pt.y) * d + (double)(this.next.pt.x - this.pt.x) * d2 >= 0.0;
            return this.angle < 0.0 ? bl | bl2 : bl & bl2;
        }

        public boolean isInfront(Point point) {
            return this.isInfront(point.pt.x - this.pt.x, point.pt.y - this.pt.y);
        }
    }
}

