/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.layout;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Scrollable;

public final class GridLayout
extends Layout {
    public int numColumns = 1;
    public boolean makeColumnsEqualWidth = false;
    public int marginWidth = 5;
    public int marginHeight = 5;
    public int marginLeft = 0;
    public int marginTop = 0;
    public int marginRight = 0;
    public int marginBottom = 0;
    public int horizontalSpacing = 5;
    public int verticalSpacing = 5;

    public GridLayout() {
    }

    public GridLayout(int n, boolean bl) {
        this.numColumns = n;
        this.makeColumnsEqualWidth = bl;
    }

    @Override
    protected Point computeSize(Composite composite, int n, int n2, boolean bl) {
        Point point = this.layout(composite, false, 0, 0, n, n2, bl);
        if (n != -1) {
            point.x = n;
        }
        if (n2 != -1) {
            point.y = n2;
        }
        return point;
    }

    @Override
    protected boolean flushCache(Control control) {
        Object object = control.getLayoutData();
        if (object != null) {
            ((GridData)object).flushCache();
        }
        return true;
    }

    GridData getData(Control[][] controlArray, int n, int n2, int n3, int n4, boolean bl) {
        Control control = controlArray[n][n2];
        if (control != null) {
            int n5;
            GridData gridData = (GridData)control.getLayoutData();
            int n6 = Math.max(1, Math.min(gridData.horizontalSpan, n4));
            int n7 = Math.max(1, gridData.verticalSpan);
            int n8 = bl ? n + n7 - 1 : n - n7 + 1;
            int n9 = n5 = bl ? n2 + n6 - 1 : n2 - n6 + 1;
            if (0 <= n8 && n8 < n3 && 0 <= n5 && n5 < n4 && control == controlArray[n8][n5]) {
                return gridData;
            }
        }
        return null;
    }

    @Override
    protected void layout(Composite composite, boolean bl) {
        Rectangle rectangle = composite.getClientArea();
        this.layout(composite, true, rectangle.x, rectangle.y, rectangle.width, rectangle.height, bl);
    }

    Point layout(Composite composite, boolean bl, int n, int n2, int n3, int n4, boolean bl2) {
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        int n11;
        int n12;
        int n13;
        int n14;
        int n15;
        int n16;
        int n17;
        int n18;
        int n19;
        int n20;
        int n21;
        int n22;
        int n23;
        int n24;
        int n25;
        int n26;
        int n27;
        int n28;
        int n29;
        Object object;
        int n30;
        int n31;
        GridData gridData;
        Control control;
        int n32;
        if (this.numColumns < 1) {
            return new Point(this.marginLeft + this.marginWidth * 2 + this.marginRight, this.marginTop + this.marginHeight * 2 + this.marginBottom);
        }
        Control[] controlArray = composite.getChildren();
        int n33 = 0;
        for (n32 = 0; n32 < controlArray.length; ++n32) {
            control = controlArray[n32];
            gridData = (GridData)control.getLayoutData();
            if (gridData != null && gridData.exclude) continue;
            controlArray[n33++] = controlArray[n32];
        }
        if (n33 == 0) {
            return new Point(this.marginLeft + this.marginWidth * 2 + this.marginRight, this.marginTop + this.marginHeight * 2 + this.marginBottom);
        }
        for (n32 = 0; n32 < n33; ++n32) {
            control = controlArray[n32];
            gridData = (GridData)control.getLayoutData();
            if (gridData == null) {
                gridData = new GridData();
                control.setLayoutData(gridData);
            }
            if (bl2) {
                gridData.flushCache();
            }
            gridData.computeSize(control, gridData.widthHint, gridData.heightHint, bl2);
            if (gridData.grabExcessHorizontalSpace && gridData.minimumWidth > 0 && gridData.cacheWidth < gridData.minimumWidth) {
                n31 = 0;
                if (control instanceof Scrollable) {
                    Rectangle rectangle = ((Scrollable)control).computeTrim(0, 0, 0, 0);
                    n31 = rectangle.width;
                } else {
                    n31 = control.getBorderWidth() * 2;
                }
                gridData.cacheHeight = -1;
                gridData.cacheWidth = -1;
                gridData.computeSize(control, Math.max(0, gridData.minimumWidth - n31), gridData.heightHint, false);
            }
            if (!gridData.grabExcessVerticalSpace || gridData.minimumHeight <= 0) continue;
            gridData.cacheHeight = Math.max(gridData.cacheHeight, gridData.minimumHeight);
        }
        int n34 = 0;
        n31 = 0;
        int n35 = 0;
        int n36 = this.numColumns;
        Control[][] controlArray2 = new Control[4][n36];
        for (n30 = 0; n30 < n33; ++n30) {
            Control control2 = controlArray[n30];
            object = (GridData)control2.getLayoutData();
            int n37 = Math.max(1, Math.min(((GridData)object).horizontalSpan, n36));
            int n38 = Math.max(1, ((GridData)object).verticalSpan);
            while (true) {
                if ((n29 = n34 + n38) >= controlArray2.length) {
                    Control[][] controlArray3 = new Control[n29 + 4][n36];
                    System.arraycopy(controlArray2, 0, controlArray3, 0, controlArray2.length);
                    controlArray2 = controlArray3;
                }
                if (controlArray2[n34] == null) {
                    controlArray2[n34] = new Control[n36];
                }
                while (n31 < n36 && controlArray2[n34][n31] != null) {
                    ++n31;
                }
                int n39 = n31 + n37;
                if (n39 <= n36) {
                    for (n28 = n31; n28 < n39 && controlArray2[n34][n28] == null; ++n28) {
                    }
                    if (n28 == n39) break;
                    n31 = n28;
                }
                if (n31 + n37 < n36) continue;
                n31 = 0;
                ++n34;
            }
            for (n27 = 0; n27 < n38; ++n27) {
                if (controlArray2[n34 + n27] == null) {
                    controlArray2[n34 + n27] = new Control[n36];
                }
                for (n29 = 0; n29 < n37; ++n29) {
                    controlArray2[n34 + n27][n31 + n29] = control2;
                }
            }
            n35 = Math.max(n35, n34 + n38);
            n31 += n37;
        }
        n30 = n3 - this.horizontalSpacing * (n36 - 1) - (this.marginLeft + this.marginWidth * 2 + this.marginRight);
        int n40 = 0;
        object = new int[n36];
        int[] nArray = new int[n36];
        boolean[] blArray = new boolean[n36];
        for (n27 = 0; n27 < n36; ++n27) {
            GridData gridData2;
            for (n26 = 0; n26 < n35; ++n26) {
                gridData2 = this.getData(controlArray2, n26, n27, n35, n36, true);
                if (gridData2 == null || (n29 = Math.max(1, Math.min(gridData2.horizontalSpan, n36))) != 1) continue;
                n28 = gridData2.cacheWidth + gridData2.horizontalIndent;
                object[n27] = Math.max((int)object[n27], n28);
                if (gridData2.grabExcessHorizontalSpace) {
                    if (!blArray[n27]) {
                        ++n40;
                    }
                    blArray[n27] = true;
                }
                if (gridData2.grabExcessHorizontalSpace && gridData2.minimumWidth == 0) continue;
                n28 = !gridData2.grabExcessHorizontalSpace || gridData2.minimumWidth == -1 ? gridData2.cacheWidth : gridData2.minimumWidth;
                nArray[n27] = Math.max(nArray[n27], n28 += gridData2.horizontalIndent);
            }
            for (n26 = 0; n26 < n35; ++n26) {
                int n41;
                gridData2 = this.getData(controlArray2, n26, n27, n35, n36, false);
                if (gridData2 == null || (n29 = Math.max(1, Math.min(gridData2.horizontalSpan, n36))) <= 1) continue;
                n25 = 0;
                n24 = 0;
                n23 = 0;
                for (n41 = 0; n41 < n29; ++n41) {
                    n25 += object[n27 - n41];
                    n24 += nArray[n27 - n41];
                    if (!blArray[n27 - n41]) continue;
                    ++n23;
                }
                if (gridData2.grabExcessHorizontalSpace && n23 == 0) {
                    ++n40;
                    blArray[n27] = true;
                }
                if ((n22 = gridData2.cacheWidth + gridData2.horizontalIndent - n25 - (n29 - 1) * this.horizontalSpacing) > 0) {
                    if (this.makeColumnsEqualWidth) {
                        n41 = (n22 + n25) / n29;
                        n21 = (n22 + n25) % n29;
                        n20 = -1;
                        for (n19 = 0; n19 < n29; ++n19) {
                            n20 = n27 - n19;
                            object[n20] = Math.max(n41, (int)object[n27 - n19]);
                        }
                        if (n20 > -1) {
                            n18 = n20;
                            object[n18] = object[n18] + n21;
                        }
                    } else if (n23 == 0) {
                        n41 = n27;
                        object[n41] = object[n41] + n22;
                    } else {
                        n28 = n22 / n23;
                        n21 = n22 % n23;
                        n20 = -1;
                        for (n19 = 0; n19 < n29; ++n19) {
                            if (!blArray[n27 - n19]) continue;
                            n20 = n27 - n19;
                            object[n20] = object[n20] + n28;
                        }
                        if (n20 > -1) {
                            n41 = n20;
                            object[n41] = object[n41] + n21;
                        }
                    }
                }
                if (gridData2.grabExcessHorizontalSpace && gridData2.minimumWidth == 0) continue;
                int n42 = n22 = !gridData2.grabExcessHorizontalSpace || gridData2.minimumWidth == -1 ? gridData2.cacheWidth : gridData2.minimumWidth;
                if ((n22 += gridData2.horizontalIndent - n24 - (n29 - 1) * this.horizontalSpacing) <= 0) continue;
                if (n23 == 0) {
                    n41 = n27;
                    nArray[n41] = nArray[n41] + n22;
                    continue;
                }
                n28 = n22 / n23;
                n21 = n22 % n23;
                n20 = -1;
                for (n19 = 0; n19 < n29; ++n19) {
                    if (!blArray[n27 - n19]) continue;
                    n20 = n27 - n19;
                    nArray[n20] = nArray[n20] + n28;
                }
                if (n20 <= -1) continue;
                n41 = n20;
                nArray[n41] = nArray[n41] + n21;
            }
        }
        if (this.makeColumnsEqualWidth) {
            int n43 = 0;
            n28 = 0;
            for (n29 = 0; n29 < n36; ++n29) {
                n43 = Math.max(n43, nArray[n29]);
                n28 = Math.max(n28, (int)object[n29]);
            }
            n28 = n3 == -1 || n40 == 0 ? n28 : Math.max(n43, n30 / n36);
            for (n29 = 0; n29 < n36; ++n29) {
                blArray[n29] = n40 > 0;
                object[n29] = n28;
            }
        } else if (n3 != -1 && n40 > 0) {
            n29 = 0;
            for (n26 = 0; n26 < n36; ++n26) {
                n29 += object[n26];
            }
            int n44 = n40;
            n28 = (n30 - n29) / n44;
            n21 = (n30 - n29) % n44;
            n22 = -1;
            while (n29 != n30) {
                for (n25 = 0; n25 < n36; ++n25) {
                    if (!blArray[n25]) continue;
                    if (object[n25] + n28 > nArray[n25]) {
                        n22 = n25;
                        object[n22] = object[n25] + n28;
                        continue;
                    }
                    object[n25] = nArray[n25];
                    blArray[n25] = false;
                    --n44;
                }
                if (n22 > -1) {
                    n24 = n22;
                    object[n24] = object[n24] + n21;
                }
                for (n25 = 0; n25 < n36; ++n25) {
                    for (n17 = 0; n17 < n35; ++n17) {
                        GridData gridData3 = this.getData(controlArray2, n17, n25, n35, n36, false);
                        if (gridData3 == null || (n23 = Math.max(1, Math.min(gridData3.horizontalSpan, n36))) <= 1 || gridData3.grabExcessHorizontalSpace && gridData3.minimumWidth == 0) continue;
                        n18 = 0;
                        n16 = 0;
                        for (n19 = 0; n19 < n23; ++n19) {
                            n18 += object[n25 - n19];
                            if (!blArray[n25 - n19]) continue;
                            ++n16;
                        }
                        int n45 = n24 = !gridData3.grabExcessHorizontalSpace || gridData3.minimumWidth == -1 ? gridData3.cacheWidth : gridData3.minimumWidth;
                        if ((n24 += gridData3.horizontalIndent - n18 - (n23 - 1) * this.horizontalSpacing) <= 0) continue;
                        if (n16 == 0) {
                            n15 = n25;
                            object[n15] = object[n15] + n24;
                            continue;
                        }
                        n15 = n24 / n16;
                        n14 = n24 % n16;
                        n13 = -1;
                        for (n12 = 0; n12 < n23; ++n12) {
                            if (!blArray[n25 - n12]) continue;
                            n13 = n25 - n12;
                            object[n13] = object[n13] + n15;
                        }
                        if (n13 <= -1) continue;
                        n11 = n13;
                        object[n11] = object[n11] + n14;
                    }
                }
                if (n44 != 0) {
                    n29 = 0;
                    for (n24 = 0; n24 < n36; ++n24) {
                        n29 += object[n24];
                    }
                    n28 = (n30 - n29) / n44;
                    n21 = (n30 - n29) % n44;
                    n22 = -1;
                    continue;
                }
                break;
            }
        }
        GridData[] gridDataArray = null;
        int n46 = 0;
        if (n3 != -1) {
            for (n28 = 0; n28 < n36; ++n28) {
                for (n21 = 0; n21 < n35; ++n21) {
                    int n47;
                    GridData gridData4 = this.getData(controlArray2, n21, n28, n35, n36, false);
                    if (gridData4 == null || gridData4.heightHint != -1) continue;
                    Control control3 = controlArray2[n21][n28];
                    n24 = Math.max(1, Math.min(gridData4.horizontalSpan, n36));
                    n23 = 0;
                    for (n47 = 0; n47 < n24; ++n47) {
                        n23 += object[n28 - n47];
                    }
                    if (((n23 += (n24 - 1) * this.horizontalSpacing - gridData4.horizontalIndent) == gridData4.cacheWidth || gridData4.horizontalAlignment != 4) && gridData4.cacheWidth <= n23) continue;
                    n47 = 0;
                    if (control3 instanceof Scrollable) {
                        Rectangle rectangle = ((Scrollable)control3).computeTrim(0, 0, 0, 0);
                        n47 = rectangle.width;
                    } else {
                        n47 = control3.getBorderWidth() * 2;
                    }
                    gridData4.cacheHeight = -1;
                    gridData4.cacheWidth = -1;
                    gridData4.computeSize(control3, Math.max(0, n23 - n47), gridData4.heightHint, false);
                    if (gridData4.grabExcessVerticalSpace && gridData4.minimumHeight > 0) {
                        gridData4.cacheHeight = Math.max(gridData4.cacheHeight, gridData4.minimumHeight);
                    }
                    if (gridDataArray == null) {
                        gridDataArray = new GridData[n33];
                    }
                    gridDataArray[n46++] = gridData4;
                }
            }
        }
        n28 = n4 - this.verticalSpacing * (n35 - 1) - (this.marginTop + this.marginHeight * 2 + this.marginBottom);
        n40 = 0;
        int[] nArray2 = new int[n35];
        int[] nArray3 = new int[n35];
        boolean[] blArray2 = new boolean[n35];
        for (n17 = 0; n17 < n35; ++n17) {
            int n48;
            GridData gridData5;
            int n49;
            for (n49 = 0; n49 < n36; ++n49) {
                gridData5 = this.getData(controlArray2, n17, n49, n35, n36, true);
                if (gridData5 == null || (n24 = Math.max(1, Math.min(gridData5.verticalSpan, n35))) != 1) continue;
                n48 = gridData5.cacheHeight + gridData5.verticalIndent;
                nArray2[n17] = Math.max(nArray2[n17], n48);
                if (gridData5.grabExcessVerticalSpace) {
                    if (!blArray2[n17]) {
                        ++n40;
                    }
                    blArray2[n17] = true;
                }
                if (gridData5.grabExcessVerticalSpace && gridData5.minimumHeight == 0) continue;
                n48 = !gridData5.grabExcessVerticalSpace || gridData5.minimumHeight == -1 ? gridData5.cacheHeight : gridData5.minimumHeight;
                nArray3[n17] = Math.max(nArray3[n17], n48 += gridData5.verticalIndent);
            }
            for (n49 = 0; n49 < n36; ++n49) {
                gridData5 = this.getData(controlArray2, n17, n49, n35, n36, false);
                if (gridData5 == null || (n24 = Math.max(1, Math.min(gridData5.verticalSpan, n35))) <= 1) continue;
                n13 = 0;
                n11 = 0;
                n10 = 0;
                for (n9 = 0; n9 < n24; ++n9) {
                    n13 += nArray2[n17 - n9];
                    n11 += nArray3[n17 - n9];
                    if (!blArray2[n17 - n9]) continue;
                    ++n10;
                }
                if (gridData5.grabExcessVerticalSpace && n10 == 0) {
                    ++n40;
                    blArray2[n17] = true;
                }
                if ((n14 = gridData5.cacheHeight + gridData5.verticalIndent - n13 - (n24 - 1) * this.verticalSpacing) > 0) {
                    if (n10 == 0) {
                        n9 = n17;
                        nArray2[n9] = nArray2[n9] + n14;
                    } else {
                        n15 = n14 / n10;
                        n16 = n14 % n10;
                        n48 = -1;
                        for (n8 = 0; n8 < n24; ++n8) {
                            if (!blArray2[n17 - n8]) continue;
                            n48 = n17 - n8;
                            nArray2[n48] = nArray2[n48] + n15;
                        }
                        if (n48 > -1) {
                            n9 = n48;
                            nArray2[n9] = nArray2[n9] + n16;
                        }
                    }
                }
                if (gridData5.grabExcessVerticalSpace && gridData5.minimumHeight == 0) continue;
                int n50 = n14 = !gridData5.grabExcessVerticalSpace || gridData5.minimumHeight == -1 ? gridData5.cacheHeight : gridData5.minimumHeight;
                if ((n14 += gridData5.verticalIndent - n11 - (n24 - 1) * this.verticalSpacing) <= 0) continue;
                if (n10 == 0) {
                    n9 = n17;
                    nArray3[n9] = nArray3[n9] + n14;
                    continue;
                }
                n15 = n14 / n10;
                n16 = n14 % n10;
                n48 = -1;
                for (n8 = 0; n8 < n24; ++n8) {
                    if (!blArray2[n17 - n8]) continue;
                    n48 = n17 - n8;
                    nArray3[n48] = nArray3[n48] + n15;
                }
                if (n48 <= -1) continue;
                n9 = n48;
                nArray3[n9] = nArray3[n9] + n16;
            }
        }
        if (n4 != -1 && n40 > 0) {
            int n51;
            n24 = 0;
            for (n51 = 0; n51 < n35; ++n51) {
                n24 += nArray2[n51];
            }
            n51 = n40;
            int n52 = (n28 - n24) / n51;
            int n53 = (n28 - n24) % n51;
            n20 = -1;
            while (n24 != n28) {
                for (n16 = 0; n16 < n35; ++n16) {
                    if (!blArray2[n16]) continue;
                    if (nArray2[n16] + n52 > nArray3[n16]) {
                        n20 = n16;
                        nArray2[n20] = nArray2[n16] + n52;
                        continue;
                    }
                    nArray2[n16] = nArray3[n16];
                    blArray2[n16] = false;
                    --n51;
                }
                if (n20 > -1) {
                    n15 = n20;
                    nArray2[n15] = nArray2[n15] + n53;
                }
                for (n16 = 0; n16 < n35; ++n16) {
                    for (n15 = 0; n15 < n36; ++n15) {
                        int n54;
                        GridData gridData6 = this.getData(controlArray2, n16, n15, n35, n36, false);
                        if (gridData6 == null || (n13 = Math.max(1, Math.min(gridData6.verticalSpan, n35))) <= 1 || gridData6.grabExcessVerticalSpace && gridData6.minimumHeight == 0) continue;
                        n10 = 0;
                        n9 = 0;
                        for (n8 = 0; n8 < n13; ++n8) {
                            n10 += nArray2[n16 - n8];
                            if (!blArray2[n16 - n8]) continue;
                            ++n9;
                        }
                        int n55 = n14 = !gridData6.grabExcessVerticalSpace || gridData6.minimumHeight == -1 ? gridData6.cacheHeight : gridData6.minimumHeight;
                        if ((n14 += gridData6.verticalIndent - n10 - (n13 - 1) * this.verticalSpacing) <= 0) continue;
                        if (n9 == 0) {
                            n7 = n16;
                            nArray2[n7] = nArray2[n7] + n14;
                            continue;
                        }
                        n7 = n14 / n9;
                        n6 = n14 % n9;
                        int n56 = -1;
                        for (n54 = 0; n54 < n13; ++n54) {
                            if (!blArray2[n16 - n54]) continue;
                            n56 = n16 - n54;
                            nArray2[n56] = nArray2[n56] + n7;
                        }
                        if (n56 <= -1) continue;
                        n54 = n56;
                        nArray2[n54] = nArray2[n54] + n6;
                    }
                }
                if (n51 == 0) break;
                n24 = 0;
                for (n16 = 0; n16 < n35; ++n16) {
                    n24 += nArray2[n16];
                }
                n52 = (n28 - n24) / n51;
                n53 = (n28 - n24) % n51;
                n20 = -1;
            }
        }
        if (bl) {
            n24 = n2 + this.marginTop + this.marginHeight;
            for (int i = 0; i < n35; ++i) {
                int n57 = n + this.marginLeft + this.marginWidth;
                for (int j = 0; j < n36; ++j) {
                    GridData gridData7 = this.getData(controlArray2, i, j, n35, n36, true);
                    if (gridData7 != null) {
                        n15 = Math.max(1, Math.min(gridData7.horizontalSpan, n36));
                        n14 = Math.max(1, gridData7.verticalSpan);
                        n13 = 0;
                        int n58 = 0;
                        for (n12 = 0; n12 < n15; ++n12) {
                            n13 += object[j + n12];
                        }
                        for (n12 = 0; n12 < n14; ++n12) {
                            n58 += nArray2[i + n12];
                        }
                        n10 = n57 + gridData7.horizontalIndent;
                        n9 = Math.min(gridData7.cacheWidth, n13 += this.horizontalSpacing * (n15 - 1));
                        switch (gridData7.horizontalAlignment) {
                            case 2: 
                            case 0x1000000: {
                                n10 += Math.max(0, (n13 - gridData7.horizontalIndent - n9) / 2);
                                break;
                            }
                            case 3: 
                            case 131072: 
                            case 0x1000008: {
                                n10 += Math.max(0, n13 - gridData7.horizontalIndent - n9);
                                break;
                            }
                            case 4: {
                                n9 = n13 - gridData7.horizontalIndent;
                            }
                        }
                        n7 = n24 + gridData7.verticalIndent;
                        n6 = Math.min(gridData7.cacheHeight, n58 += this.verticalSpacing * (n14 - 1));
                        switch (gridData7.verticalAlignment) {
                            case 2: 
                            case 0x1000000: {
                                n7 += Math.max(0, (n58 - gridData7.verticalIndent - n6) / 2);
                                break;
                            }
                            case 3: 
                            case 1024: 
                            case 0x1000008: {
                                n7 += Math.max(0, n58 - gridData7.verticalIndent - n6);
                                break;
                            }
                            case 4: {
                                n6 = n58 - gridData7.verticalIndent;
                            }
                        }
                        Control control4 = controlArray2[i][j];
                        if (control4 != null) {
                            control4.setBounds(n10, n7, n9, n6);
                        }
                    }
                    n57 += object[j] + this.horizontalSpacing;
                }
                n24 += nArray2[i] + this.verticalSpacing;
            }
        }
        for (n17 = 0; n17 < n46; ++n17) {
            gridDataArray[n17].cacheHeight = -1;
            gridDataArray[n17].cacheWidth = -1;
        }
        n24 = 0;
        int n59 = 0;
        for (n5 = 0; n5 < n36; ++n5) {
            n24 += object[n5];
        }
        for (n5 = 0; n5 < n35; ++n5) {
            n59 += nArray2[n5];
        }
        return new Point(n24 += this.horizontalSpacing * (n36 - 1) + this.marginLeft + this.marginWidth * 2 + this.marginRight, n59 += this.verticalSpacing * (n35 - 1) + this.marginTop + this.marginHeight * 2 + this.marginBottom);
    }

    String getName() {
        String string = this.getClass().getName();
        int n = string.lastIndexOf(46);
        if (n == -1) {
            return string;
        }
        return string.substring(n + 1, string.length());
    }

    public String toString() {
        String string = this.getName() + " {";
        if (this.numColumns != 1) {
            string = string + "numColumns=" + this.numColumns + " ";
        }
        if (this.makeColumnsEqualWidth) {
            string = string + "makeColumnsEqualWidth=" + this.makeColumnsEqualWidth + " ";
        }
        if (this.marginWidth != 0) {
            string = string + "marginWidth=" + this.marginWidth + " ";
        }
        if (this.marginHeight != 0) {
            string = string + "marginHeight=" + this.marginHeight + " ";
        }
        if (this.marginLeft != 0) {
            string = string + "marginLeft=" + this.marginLeft + " ";
        }
        if (this.marginRight != 0) {
            string = string + "marginRight=" + this.marginRight + " ";
        }
        if (this.marginTop != 0) {
            string = string + "marginTop=" + this.marginTop + " ";
        }
        if (this.marginBottom != 0) {
            string = string + "marginBottom=" + this.marginBottom + " ";
        }
        if (this.horizontalSpacing != 0) {
            string = string + "horizontalSpacing=" + this.horizontalSpacing + " ";
        }
        if (this.verticalSpacing != 0) {
            string = string + "verticalSpacing=" + this.verticalSpacing + " ";
        }
        string = string.trim();
        string = string + "}";
        return string;
    }
}

