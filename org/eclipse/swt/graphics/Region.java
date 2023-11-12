/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;

public final class Region
extends Resource {
    public long handle;

    public Region() {
        this(null);
    }

    public Region(Device device) {
        super(device);
        this.handle = OS.CreateRectRgn(0, 0, 0, 0);
        if (this.handle == 0L) {
            SWT.error(2);
        }
        this.init();
    }

    Region(Device device, int n) {
        super(device);
        this.handle = n;
    }

    public void add(int[] nArray) {
        if (this == false) {
            SWT.error(44);
        }
        if (nArray == null) {
            SWT.error(4);
        }
        this.addInPixels(DPIUtil.autoScaleUp(nArray));
    }

    void addInPixels(int[] nArray) {
        long l2 = OS.CreatePolygonRgn(nArray, nArray.length / 2, 1);
        OS.CombineRgn(this.handle, this.handle, l2, 2);
        OS.DeleteObject(l2);
    }

    public void add(Rectangle rectangle) {
        if (this == false) {
            SWT.error(44);
        }
        if (rectangle == null) {
            SWT.error(4);
        }
        rectangle = DPIUtil.autoScaleUp(rectangle);
        this.addInPixels(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void add(int n, int n2, int n3, int n4) {
        if (this == false) {
            SWT.error(44);
        }
        this.addInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2), DPIUtil.autoScaleUp(n3), DPIUtil.autoScaleUp(n4));
    }

    void addInPixels(int n, int n2, int n3, int n4) {
        if (n3 < 0 || n4 < 0) {
            SWT.error(5);
        }
        long l2 = OS.CreateRectRgn(n, n2, n + n3, n2 + n4);
        OS.CombineRgn(this.handle, this.handle, l2, 2);
        OS.DeleteObject(l2);
    }

    public void add(Region region) {
        if (this == false) {
            SWT.error(44);
        }
        if (region == null) {
            SWT.error(4);
        }
        if (region == false) {
            SWT.error(5);
        }
        OS.CombineRgn(this.handle, this.handle, region.handle, 2);
    }

    public boolean contains(int n, int n2) {
        if (this == false) {
            SWT.error(44);
        }
        return this.containsInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2));
    }

    boolean containsInPixels(int n, int n2) {
        return OS.PtInRegion(this.handle, n, n2);
    }

    public boolean contains(Point point) {
        if (this == false) {
            SWT.error(44);
        }
        if (point == null) {
            SWT.error(4);
        }
        point = DPIUtil.autoScaleUp(point);
        return this.containsInPixels(point.x, point.y);
    }

    @Override
    void destroy() {
        OS.DeleteObject(this.handle);
        this.handle = 0L;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Region)) {
            return false;
        }
        Region region = (Region)object;
        return this.handle == region.handle;
    }

    public Rectangle getBounds() {
        if (this == false) {
            SWT.error(44);
        }
        return DPIUtil.autoScaleDown(this.getBoundsInPixels());
    }

    Rectangle getBoundsInPixels() {
        RECT rECT = new RECT();
        OS.GetRgnBox(this.handle, rECT);
        return new Rectangle(rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top);
    }

    public int hashCode() {
        return (int)this.handle;
    }

    public void intersect(Rectangle rectangle) {
        if (this == false) {
            SWT.error(44);
        }
        if (rectangle == null) {
            SWT.error(4);
        }
        rectangle = DPIUtil.autoScaleUp(rectangle);
        this.intersectInPixels(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void intersect(int n, int n2, int n3, int n4) {
        if (this == false) {
            SWT.error(44);
        }
        this.intersectInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2), DPIUtil.autoScaleUp(n3), DPIUtil.autoScaleUp(n4));
    }

    void intersectInPixels(int n, int n2, int n3, int n4) {
        if (n3 < 0 || n4 < 0) {
            SWT.error(5);
        }
        long l2 = OS.CreateRectRgn(n, n2, n + n3, n2 + n4);
        OS.CombineRgn(this.handle, this.handle, l2, 1);
        OS.DeleteObject(l2);
    }

    public void intersect(Region region) {
        if (this == false) {
            SWT.error(44);
        }
        if (region == null) {
            SWT.error(4);
        }
        if (region == false) {
            SWT.error(5);
        }
        OS.CombineRgn(this.handle, this.handle, region.handle, 1);
    }

    public boolean intersects(int n, int n2, int n3, int n4) {
        if (this == false) {
            SWT.error(44);
        }
        return this.intersectsInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2), DPIUtil.autoScaleUp(n3), DPIUtil.autoScaleUp(n4));
    }

    boolean intersectsInPixels(int n, int n2, int n3, int n4) {
        RECT rECT = new RECT();
        OS.SetRect(rECT, n, n2, n + n3, n2 + n4);
        return OS.RectInRegion(this.handle, rECT);
    }

    public boolean intersects(Rectangle rectangle) {
        if (this == false) {
            SWT.error(44);
        }
        if (rectangle == null) {
            SWT.error(4);
        }
        rectangle = DPIUtil.autoScaleUp(rectangle);
        return this.intersectsInPixels(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public boolean isEmpty() {
        RECT rECT;
        int n;
        if (this == false) {
            SWT.error(44);
        }
        return (n = OS.GetRgnBox(this.handle, rECT = new RECT())) == 1 || rECT.right - rECT.left <= 0 || rECT.bottom - rECT.top <= 0;
    }

    public void subtract(int[] nArray) {
        if (this == false) {
            SWT.error(44);
        }
        if (nArray == null) {
            SWT.error(4);
        }
        this.subtractInPixels(DPIUtil.autoScaleUp(nArray));
    }

    void subtractInPixels(int[] nArray) {
        long l2 = OS.CreatePolygonRgn(nArray, nArray.length / 2, 1);
        OS.CombineRgn(this.handle, this.handle, l2, 4);
        OS.DeleteObject(l2);
    }

    public void subtract(Rectangle rectangle) {
        if (this == false) {
            SWT.error(44);
        }
        if (rectangle == null) {
            SWT.error(4);
        }
        rectangle = DPIUtil.autoScaleUp(rectangle);
        this.subtractInPixels(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void subtract(int n, int n2, int n3, int n4) {
        if (this == false) {
            SWT.error(44);
        }
        this.subtractInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2), DPIUtil.autoScaleUp(n3), DPIUtil.autoScaleUp(n4));
    }

    void subtractInPixels(int n, int n2, int n3, int n4) {
        if (n3 < 0 || n4 < 0) {
            SWT.error(5);
        }
        long l2 = OS.CreateRectRgn(n, n2, n + n3, n2 + n4);
        OS.CombineRgn(this.handle, this.handle, l2, 4);
        OS.DeleteObject(l2);
    }

    public void subtract(Region region) {
        if (this == false) {
            SWT.error(44);
        }
        if (region == null) {
            SWT.error(4);
        }
        if (region == false) {
            SWT.error(5);
        }
        OS.CombineRgn(this.handle, this.handle, region.handle, 4);
    }

    public void translate(int n, int n2) {
        if (this == false) {
            SWT.error(44);
        }
        this.translateInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2));
    }

    void translateInPixels(int n, int n2) {
        OS.OffsetRgn(this.handle, n, n2);
    }

    public void translate(Point point) {
        if (this == false) {
            SWT.error(44);
        }
        if (point == null) {
            SWT.error(4);
        }
        point = DPIUtil.autoScaleUp(point);
        this.translateInPixels(point.x, point.y);
    }

    public String toString() {
        if (this == false) {
            return "Region {*DISPOSED*}";
        }
        return "Region {" + this.handle;
    }

    public static Region win32_new(Device device, int n) {
        return new Region(device, n);
    }
}

