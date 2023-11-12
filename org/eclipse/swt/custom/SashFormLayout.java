/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.SashFormData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Sash;

class SashFormLayout
extends Layout {
    SashFormLayout() {
    }

    @Override
    protected Point computeSize(Composite composite, int n, int n2, boolean bl) {
        int n3;
        SashForm sashForm = (SashForm)composite;
        Control[] controlArray = sashForm.getControls(true);
        int n4 = 0;
        int n5 = 0;
        if (controlArray.length == 0) {
            if (n != -1) {
                n4 = n;
            }
            if (n2 != -1) {
                n5 = n2;
            }
            return new Point(n4, n5);
        }
        boolean bl2 = sashForm.getOrientation() == 512;
        int n6 = 0;
        int n7 = 0;
        for (int i = 0; i < controlArray.length; ++i) {
            Point point;
            if (bl2) {
                point = controlArray[i].computeSize(n, -1, bl);
                if (point.y > n7) {
                    n6 = i;
                    n7 = point.y;
                }
                n4 = Math.max(n4, point.x);
                continue;
            }
            point = controlArray[i].computeSize(-1, n2, bl);
            if (point.x > n7) {
                n6 = i;
                n7 = point.x;
            }
            n5 = Math.max(n5, point.y);
        }
        long[] lArray = new long[controlArray.length];
        long l2 = 0L;
        for (n3 = 0; n3 < controlArray.length; ++n3) {
            Object object = controlArray[n3].getLayoutData();
            if (object instanceof SashFormData) {
                lArray[n3] = ((SashFormData)object).weight;
            } else {
                object = new SashFormData();
                controlArray[n3].setLayoutData(object);
                SashFormData sashFormData = (SashFormData)object;
                long[] lArray2 = lArray;
                int n8 = n3;
                long l3 = 13108L;
                lArray2[n8] = 13108L;
                sashFormData.weight = 13108L;
            }
            l2 += lArray[n3];
        }
        if (lArray[n6] > 0L) {
            int n9 = n3 = sashForm.sashes.length > 0 ? sashForm.SASH_WIDTH + sashForm.sashes[0].getBorderWidth() * 2 : sashForm.SASH_WIDTH;
            if (bl2) {
                n5 += (int)(l2 * (long)n7 / lArray[n6]) + (controlArray.length - 1) * n3;
            } else {
                n4 += (int)(l2 * (long)n7 / lArray[n6]) + (controlArray.length - 1) * n3;
            }
        }
        n4 += sashForm.getBorderWidth() * 2;
        n5 += sashForm.getBorderWidth() * 2;
        if (n != -1) {
            n4 = n;
        }
        if (n2 != -1) {
            n5 = n2;
        }
        return new Point(n4, n5);
    }

    @Override
    protected boolean flushCache(Control control) {
        return true;
    }

    @Override
    protected void layout(Composite composite, boolean bl) {
        int n;
        int n2;
        Sash[] sashArray;
        SashForm sashForm = (SashForm)composite;
        Rectangle rectangle = sashForm.getClientArea();
        if (rectangle.width <= 1 || rectangle.height <= 1) {
            return;
        }
        Control[] controlArray = sashForm.getControls(true);
        if (sashForm.controls.length == 0 && controlArray.length == 0) {
            return;
        }
        Control[] controlArray2 = sashForm.controls = controlArray;
        if (sashForm.maxControl != null && !sashForm.maxControl.isDisposed()) {
            for (Control control : controlArray2) {
                if (control != sashForm.maxControl) {
                    control.setBounds(-200, -200, 0, 0);
                    continue;
                }
                control.setBounds(rectangle);
            }
            return;
        }
        if (sashForm.sashes.length < controlArray2.length - 1) {
            sashArray = new Sash[controlArray2.length - 1];
            System.arraycopy(sashForm.sashes, 0, sashArray, 0, sashForm.sashes.length);
            for (n2 = sashForm.sashes.length; n2 < sashArray.length; ++n2) {
                sashArray[n2] = sashForm.createSash();
            }
            sashForm.sashes = sashArray;
        }
        if (sashForm.sashes.length > controlArray2.length - 1) {
            if (controlArray2.length == 0) {
                for (Sash sash : sashForm.sashes) {
                    sash.dispose();
                }
                sashForm.sashes = new Sash[0];
            } else {
                sashArray = new Sash[controlArray2.length - 1];
                System.arraycopy(sashForm.sashes, 0, sashArray, 0, sashArray.length);
                for (n2 = controlArray2.length - 1; n2 < sashForm.sashes.length; ++n2) {
                    sashForm.sashes[n2].dispose();
                }
                sashForm.sashes = sashArray;
            }
        }
        if (controlArray2.length == 0) {
            return;
        }
        sashArray = sashForm.sashes;
        long[] lArray = new long[controlArray2.length];
        long l2 = 0L;
        for (n = 0; n < controlArray2.length; ++n) {
            Object object = controlArray2[n].getLayoutData();
            if (object instanceof SashFormData) {
                lArray[n] = ((SashFormData)object).weight;
            } else {
                object = new SashFormData();
                controlArray2[n].setLayoutData(object);
                SashFormData sashFormData = (SashFormData)object;
                long[] lArray2 = lArray;
                int n3 = n;
                long l3 = 13108L;
                lArray2[n3] = 13108L;
                sashFormData.weight = 13108L;
            }
            l2 += lArray[n];
        }
        int n4 = n = sashArray.length > 0 ? sashForm.SASH_WIDTH + sashArray[0].getBorderWidth() * 2 : sashForm.SASH_WIDTH;
        if (sashForm.getOrientation() == 256) {
            int n5 = (int)(lArray[0] * (long)(rectangle.width - sashArray.length * n) / l2);
            int n6 = rectangle.x;
            controlArray2[0].setBounds(n6, rectangle.y, n5, rectangle.height);
            n6 += n5;
            for (int i = 1; i < controlArray2.length - 1; ++i) {
                sashArray[i - 1].setBounds(n6, rectangle.y, n, rectangle.height);
                n5 = (int)(lArray[i] * (long)(rectangle.width - sashArray.length * n) / l2);
                controlArray2[i].setBounds(n6 += n, rectangle.y, n5, rectangle.height);
                n6 += n5;
            }
            if (controlArray2.length > 1) {
                sashArray[sashArray.length - 1].setBounds(n6, rectangle.y, n, rectangle.height);
                n5 = rectangle.width - (n6 += n);
                controlArray2[controlArray2.length - 1].setBounds(n6, rectangle.y, n5, rectangle.height);
            }
        } else {
            int n7 = (int)(lArray[0] * (long)(rectangle.height - sashArray.length * n) / l2);
            int n8 = rectangle.y;
            controlArray2[0].setBounds(rectangle.x, n8, rectangle.width, n7);
            n8 += n7;
            for (int i = 1; i < controlArray2.length - 1; ++i) {
                sashArray[i - 1].setBounds(rectangle.x, n8, rectangle.width, n);
                n7 = (int)(lArray[i] * (long)(rectangle.height - sashArray.length * n) / l2);
                controlArray2[i].setBounds(rectangle.x, n8 += n, rectangle.width, n7);
                n8 += n7;
            }
            if (controlArray2.length > 1) {
                sashArray[sashArray.length - 1].setBounds(rectangle.x, n8, rectangle.width, n);
                n7 = rectangle.height - (n8 += n);
                controlArray2[controlArray2.length - 1].setBounds(rectangle.x, n8, rectangle.width, n7);
            }
        }
    }
}

