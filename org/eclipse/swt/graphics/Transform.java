/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.gdip.Gdip;

public class Transform
extends Resource {
    public long handle;

    public Transform(Device device) {
        this(device, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
    }

    public Transform(Device device, float[] fArray) {
        this(device, Transform.checkTransform(fArray)[0], fArray[1], fArray[2], fArray[3], fArray[4], fArray[5]);
    }

    public Transform(Device device, float f, float f2, float f3, float f4, float f5, float f6) {
        super(device);
        this.device.checkGDIP();
        this.handle = Gdip.Matrix_new(f, f2, f3, f4, DPIUtil.autoScaleUp((Drawable)this.device, f5), DPIUtil.autoScaleUp((Drawable)this.device, f6));
        if (this.handle == 0L) {
            SWT.error(2);
        }
        this.init();
    }

    static float[] checkTransform(float[] fArray) {
        if (fArray == null) {
            SWT.error(4);
        }
        if (fArray.length < 6) {
            SWT.error(5);
        }
        return fArray;
    }

    @Override
    void destroy() {
        Gdip.Matrix_delete(this.handle);
        this.handle = 0L;
    }

    public void getElements(float[] fArray) {
        if (this == false) {
            SWT.error(44);
        }
        if (fArray == null) {
            SWT.error(4);
        }
        if (fArray.length < 6) {
            SWT.error(5);
        }
        Gdip.Matrix_GetElements(this.handle, fArray);
        Device device = this.getDevice();
        fArray[4] = DPIUtil.autoScaleDown((Drawable)device, fArray[4]);
        fArray[5] = DPIUtil.autoScaleDown((Drawable)device, fArray[5]);
    }

    public void identity() {
        if (this == false) {
            SWT.error(44);
        }
        Gdip.Matrix_SetElements(this.handle, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
    }

    public void invert() {
        if (this == false) {
            SWT.error(44);
        }
        if (Gdip.Matrix_Invert(this.handle) != 0) {
            SWT.error(10);
        }
    }

    public boolean isIdentity() {
        if (this == false) {
            SWT.error(44);
        }
        return Gdip.Matrix_IsIdentity(this.handle);
    }

    public void multiply(Transform transform) {
        if (this == false) {
            SWT.error(44);
        }
        if (transform == null) {
            SWT.error(4);
        }
        if (transform == false) {
            SWT.error(5);
        }
        Gdip.Matrix_Multiply(this.handle, transform.handle, 0);
    }

    public void rotate(float f) {
        if (this == false) {
            SWT.error(44);
        }
        Gdip.Matrix_Rotate(this.handle, f, 0);
    }

    public void scale(float f, float f2) {
        if (this == false) {
            SWT.error(44);
        }
        Gdip.Matrix_Scale(this.handle, f, f2, 0);
    }

    public void setElements(float f, float f2, float f3, float f4, float f5, float f6) {
        if (this == false) {
            SWT.error(44);
        }
        Device device = this.getDevice();
        Gdip.Matrix_SetElements(this.handle, f, f2, f3, f4, DPIUtil.autoScaleUp((Drawable)device, f5), DPIUtil.autoScaleUp((Drawable)device, f6));
    }

    public void shear(float f, float f2) {
        if (this == false) {
            SWT.error(44);
        }
        Gdip.Matrix_Shear(this.handle, f, f2, 0);
    }

    public void transform(float[] fArray) {
        int n;
        if (this == false) {
            SWT.error(44);
        }
        if (fArray == null) {
            SWT.error(4);
        }
        int n2 = fArray.length;
        Device device = this.getDevice();
        for (n = 0; n < n2; ++n) {
            fArray[n] = DPIUtil.autoScaleUp((Drawable)device, fArray[n]);
        }
        Gdip.Matrix_TransformPoints(this.handle, fArray, n2 / 2);
        for (n = 0; n < n2; ++n) {
            fArray[n] = DPIUtil.autoScaleDown((Drawable)device, fArray[n]);
        }
    }

    public void translate(float f, float f2) {
        if (this == false) {
            SWT.error(44);
        }
        Device device = this.getDevice();
        Gdip.Matrix_Translate(this.handle, DPIUtil.autoScaleUp((Drawable)device, f), DPIUtil.autoScaleUp((Drawable)device, f2), 0);
    }

    public String toString() {
        if (this == false) {
            return "Transform {*DISPOSED*}";
        }
        float[] fArray = new float[6];
        this.getElements(fArray);
        return "Transform {" + fArray[0] + "," + fArray[1] + "," + fArray[2] + "," + fArray[3] + "," + fArray[4] + "," + fArray[5];
    }
}

