/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolderRenderer;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

class CTabFolderLayout
extends Layout {
    CTabFolderLayout() {
    }

    @Override
    protected Point computeSize(Composite composite, int n, int n2, boolean bl) {
        int n3;
        int n4;
        Object[] objectArray;
        Object object;
        int n5;
        int n6;
        int n7;
        CTabFolder cTabFolder = (CTabFolder)composite;
        CTabItem[] cTabItemArray = cTabFolder.items;
        CTabFolderRenderer cTabFolderRenderer = cTabFolder.renderer;
        int n8 = 0;
        int n9 = cTabFolder.selectedIndex;
        if (n9 == -1) {
            n9 = 0;
        }
        GC gC = new GC(cTabFolder);
        for (n7 = 0; n7 < cTabItemArray.length; ++n7) {
            if (cTabFolder.single) {
                n8 = Math.max(n8, cTabFolderRenderer.computeSize((int)n7, (int)2, (GC)gC, (int)-1, (int)-1).x);
                continue;
            }
            n6 = 0;
            if (n7 == n9) {
                n6 |= 2;
            }
            n8 += cTabFolderRenderer.computeSize((int)n7, (int)n6, (GC)gC, (int)-1, (int)-1).x;
        }
        n7 = 0;
        n6 = 0;
        boolean bl2 = false;
        boolean bl3 = false;
        if (n == -1) {
            for (n5 = 0; n5 < cTabFolder.controls.length; ++n5) {
                object = cTabFolder.controls[n5];
                if (object.isDisposed() || !object.getVisible()) continue;
                if ((cTabFolder.controlAlignments[n5] & 0x4000) != 0) {
                    bl2 = true;
                } else {
                    bl3 = true;
                }
                n7 += ((Control)object).computeSize((int)-1, (int)-1).x;
            }
        } else {
            Point point = new Point(n, n2);
            object = new boolean[][]{null};
            objectArray = cTabFolder.computeControlBounds(point, (boolean[][])object);
            n4 = Integer.MAX_VALUE;
            n3 = 0;
            for (int i = 0; i < objectArray.length; ++i) {
                if (object[0][i] != false) {
                    n4 = Math.min(n4, ((Rectangle)objectArray[i]).y);
                    n3 = Math.max(n3, ((Rectangle)objectArray[i]).y + ((Rectangle)objectArray[i]).height);
                    n6 = n3 - n4;
                    continue;
                }
                if ((cTabFolder.controlAlignments[i] & 0x4000) != 0) {
                    bl2 = true;
                } else {
                    bl3 = true;
                }
                n7 += ((Rectangle)objectArray[i]).width;
            }
        }
        if (bl2) {
            n7 += 6;
        }
        if (bl3) {
            n7 += 6;
        }
        n8 += n7;
        gC.dispose();
        n5 = 0;
        int n10 = 0;
        objectArray = cTabItemArray;
        n4 = objectArray.length;
        for (n3 = 0; n3 < n4; ++n3) {
            Object object2 = objectArray[n3];
            Control control = ((CTabItem)object2).control;
            if (control == null || control.isDisposed()) continue;
            Point point = control.computeSize(n, n2, bl);
            n5 = Math.max(n5, point.x);
            n10 = Math.max(n10, point.y);
        }
        int n11 = Math.max(n8, n5 + cTabFolder.marginWidth);
        int n12 = n4 = cTabFolder.minimized ? 0 : n10 + n6;
        if (n11 == 0) {
            n11 = 64;
        }
        if (n4 == 0) {
            n4 = 64;
        }
        if (n != -1) {
            n11 = n;
        }
        if (n2 != -1) {
            n4 = n2;
        }
        return new Point(n11, n4);
    }

    @Override
    protected boolean flushCache(Control control) {
        return true;
    }

    @Override
    protected void layout(Composite composite, boolean bl) {
        Control control;
        CTabFolder cTabFolder = (CTabFolder)composite;
        if (cTabFolder.selectedIndex != -1 && (control = cTabFolder.items[cTabFolder.selectedIndex].control) != null && !control.isDisposed()) {
            control.setBounds(cTabFolder.getClientArea());
        }
    }
}

