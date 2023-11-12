/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.layout;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;

public final class RowLayout
extends Layout {
    public int type = 256;
    public int marginWidth = 0;
    public int marginHeight = 0;
    public int spacing = 3;
    public boolean wrap = true;
    public boolean pack = true;
    public boolean fill = false;
    public boolean center = false;
    public boolean justify = false;
    public int marginLeft = 3;
    public int marginTop = 3;
    public int marginRight = 3;
    public int marginBottom = 3;

    public RowLayout() {
    }

    public RowLayout(int n) {
        this.type = n;
    }

    @Override
    protected Point computeSize(Composite composite, int n, int n2, boolean bl) {
        Point point;
        Point point2 = this.type == 256 ? this.layoutHorizontal(composite, false, n != -1 && this.wrap, n, bl) : (point = this.layoutVertical(composite, false, n2 != -1 && this.wrap, n2, bl));
        if (n != -1) {
            point.x = n;
        }
        if (n2 != -1) {
            point.y = n2;
        }
        return point;
    }

    Point computeSize(Control control, boolean bl) {
        int n = -1;
        int n2 = -1;
        RowData rowData = (RowData)control.getLayoutData();
        if (rowData != null) {
            n = rowData.width;
            n2 = rowData.height;
        }
        return control.computeSize(n, n2, bl);
    }

    @Override
    protected boolean flushCache(Control control) {
        return true;
    }

    String getName() {
        String string = this.getClass().getName();
        int n = string.lastIndexOf(46);
        if (n == -1) {
            return string;
        }
        return string.substring(n + 1, string.length());
    }

    @Override
    protected void layout(Composite composite, boolean bl) {
        Rectangle rectangle = composite.getClientArea();
        if (this.type == 256) {
            this.layoutHorizontal(composite, true, this.wrap, rectangle.width, bl);
        } else {
            this.layoutVertical(composite, true, this.wrap, rectangle.height, bl);
        }
    }

    Point layoutHorizontal(Composite composite, boolean bl, boolean bl2, int n, boolean bl3) {
        int n2;
        int n3;
        Object object;
        int n4;
        int n5;
        Control[] controlArray = composite.getChildren();
        int n6 = 0;
        for (n5 = 0; n5 < controlArray.length; ++n5) {
            Control control = controlArray[n5];
            RowData rowData = (RowData)control.getLayoutData();
            if (rowData != null && rowData.exclude) continue;
            controlArray[n6++] = controlArray[n5];
        }
        if (n6 == 0) {
            return new Point(this.marginLeft + this.marginWidth * 2 + this.marginRight, this.marginTop + this.marginHeight * 2 + this.marginBottom);
        }
        n5 = 0;
        int n7 = 0;
        int n8 = 0;
        if (!this.pack) {
            for (n4 = 0; n4 < n6; ++n4) {
                Control control = controlArray[n4];
                object = this.computeSize(control, bl3);
                if (n > -1 && n < ((Point)object).x && bl2) {
                    object = control.computeSize(n, control.getLayoutData() == null ? -1 : ((RowData)control.getLayoutData()).height, bl3);
                }
                n5 = Math.max(n5, ((Point)object).x);
                n7 = Math.max(n7, ((Point)object).y);
            }
            n8 = n7;
        }
        n4 = 0;
        int n9 = 0;
        if (bl) {
            object = composite.getClientArea();
            n4 = ((Rectangle)object).x;
            n9 = ((Rectangle)object).y;
        }
        object = null;
        boolean bl4 = false;
        Rectangle[] rectangleArray = null;
        if (bl && (this.justify || this.fill || this.center)) {
            rectangleArray = new Rectangle[n6];
            object = new int[n6];
        }
        int n10 = 0;
        int n11 = this.marginLeft + this.marginWidth;
        int n12 = this.marginTop + this.marginHeight;
        for (n3 = 0; n3 < n6; ++n3) {
            Control control = controlArray[n3];
            if (this.pack) {
                Point point = this.computeSize(control, bl3);
                if (n > -1 && n < point.x && bl2) {
                    point = control.computeSize(n, control.getLayoutData() == null ? -1 : ((RowData)control.getLayoutData()).height, bl3);
                }
                n5 = point.x;
                n7 = point.y;
            }
            if (bl2 && n3 != 0 && n11 + n5 > n) {
                bl4 = true;
                if (bl && (this.justify || this.fill || this.center)) {
                    object[n3 - 1] = n8;
                }
                n11 = this.marginLeft + this.marginWidth;
                n12 += this.spacing + n8;
                if (this.pack) {
                    n8 = 0;
                }
            }
            if (this.pack || this.fill || this.center) {
                n8 = Math.max(n8, n7);
            }
            if (bl) {
                int n13 = n11 + n4;
                n2 = n12 + n9;
                if (this.justify || this.fill || this.center) {
                    rectangleArray[n3] = new Rectangle(n13, n2, n5, n7);
                } else {
                    control.setBounds(n13, n2, n5, n7);
                }
            }
            n10 = Math.max(n10, n11 += this.spacing + n5);
        }
        n10 = Math.max(n4 + this.marginLeft + this.marginWidth, n10 - this.spacing);
        if (!bl4) {
            n10 += this.marginRight + this.marginWidth;
        }
        if (bl && (this.justify || this.fill || this.center)) {
            n3 = 0;
            int n14 = 0;
            if (!bl4) {
                n3 = Math.max(0, (n - n10) / (n6 + 1));
                n14 = Math.max(0, (n - n10) % (n6 + 1) / 2);
            } else if (this.fill || this.justify || this.center) {
                int n15 = 0;
                if (n6 > 0) {
                    object[n6 - 1] = n8;
                }
                for (n2 = 0; n2 < n6; ++n2) {
                    int n16;
                    if (object[n2] == false) continue;
                    int n17 = n2 - n15 + 1;
                    if (this.justify) {
                        n16 = 0;
                        for (int i = n15; i <= n2; ++i) {
                            n16 += rectangleArray[i].width + this.spacing;
                        }
                        n3 = Math.max(0, (n - n16) / (n17 + 1));
                        n14 = Math.max(0, (n - n16) % (n17 + 1) / 2);
                    }
                    for (n16 = n15; n16 <= n2; ++n16) {
                        if (this.justify) {
                            rectangleArray[n16].x += n3 * (n16 - n15 + 1) + n14;
                        }
                        if (this.fill) {
                            rectangleArray[n16].height = (int)object[n2];
                            continue;
                        }
                        if (!this.center) continue;
                        rectangleArray[n16].y += Math.max(0, (int)((object[n2] - rectangleArray[n16].height) / 2));
                    }
                    n15 = n2 + 1;
                }
            }
            for (int i = 0; i < n6; ++i) {
                if (!bl4) {
                    if (this.justify) {
                        rectangleArray[i].x += n3 * (i + 1) + n14;
                    }
                    if (this.fill) {
                        rectangleArray[i].height = n8;
                    } else if (this.center) {
                        rectangleArray[i].y += Math.max(0, (n8 - rectangleArray[i].height) / 2);
                    }
                }
                controlArray[i].setBounds(rectangleArray[i]);
            }
        }
        return new Point(n10, n12 + n8 + this.marginBottom + this.marginHeight);
    }

    Point layoutVertical(Composite composite, boolean bl, boolean bl2, int n, boolean bl3) {
        int n2;
        int n3;
        Object object;
        int n4;
        int n5;
        Control[] controlArray = composite.getChildren();
        int n6 = 0;
        for (n5 = 0; n5 < controlArray.length; ++n5) {
            Control control = controlArray[n5];
            RowData rowData = (RowData)control.getLayoutData();
            if (rowData != null && rowData.exclude) continue;
            controlArray[n6++] = controlArray[n5];
        }
        if (n6 == 0) {
            return new Point(this.marginLeft + this.marginWidth * 2 + this.marginRight, this.marginTop + this.marginHeight * 2 + this.marginBottom);
        }
        n5 = 0;
        int n7 = 0;
        int n8 = 0;
        if (!this.pack) {
            for (n4 = 0; n4 < n6; ++n4) {
                Control control = controlArray[n4];
                object = this.computeSize(control, bl3);
                if (n > -1 && n < ((Point)object).y && bl2) {
                    object = control.computeSize(control.getLayoutData() == null ? -1 : ((RowData)control.getLayoutData()).width, n, bl3);
                }
                n5 = Math.max(n5, ((Point)object).x);
                n7 = Math.max(n7, ((Point)object).y);
            }
            n8 = n5;
        }
        n4 = 0;
        int n9 = 0;
        if (bl) {
            object = composite.getClientArea();
            n4 = ((Rectangle)object).x;
            n9 = ((Rectangle)object).y;
        }
        object = null;
        boolean bl4 = false;
        Rectangle[] rectangleArray = null;
        if (bl && (this.justify || this.fill || this.center)) {
            rectangleArray = new Rectangle[n6];
            object = new int[n6];
        }
        int n10 = 0;
        int n11 = this.marginLeft + this.marginWidth;
        int n12 = this.marginTop + this.marginHeight;
        for (n3 = 0; n3 < n6; ++n3) {
            Control control = controlArray[n3];
            if (this.pack) {
                Point point = this.computeSize(control, bl3);
                if (n > -1 && n < point.y && bl2) {
                    point = control.computeSize(control.getLayoutData() == null ? -1 : ((RowData)control.getLayoutData()).width, n, bl3);
                }
                n5 = point.x;
                n7 = point.y;
            }
            if (bl2 && n3 != 0 && n12 + n7 > n) {
                bl4 = true;
                if (bl && (this.justify || this.fill || this.center)) {
                    object[n3 - 1] = n8;
                }
                n11 += this.spacing + n8;
                n12 = this.marginTop + this.marginHeight;
                if (this.pack) {
                    n8 = 0;
                }
            }
            if (this.pack || this.fill || this.center) {
                n8 = Math.max(n8, n5);
            }
            if (bl) {
                int n13 = n11 + n4;
                n2 = n12 + n9;
                if (this.justify || this.fill || this.center) {
                    rectangleArray[n3] = new Rectangle(n13, n2, n5, n7);
                } else {
                    control.setBounds(n13, n2, n5, n7);
                }
            }
            n10 = Math.max(n10, n12 += this.spacing + n7);
        }
        n10 = Math.max(n9 + this.marginTop + this.marginHeight, n10 - this.spacing);
        if (!bl4) {
            n10 += this.marginBottom + this.marginHeight;
        }
        if (bl && (this.justify || this.fill || this.center)) {
            n3 = 0;
            int n14 = 0;
            if (!bl4) {
                n3 = Math.max(0, (n - n10) / (n6 + 1));
                n14 = Math.max(0, (n - n10) % (n6 + 1) / 2);
            } else if (this.fill || this.justify || this.center) {
                int n15 = 0;
                if (n6 > 0) {
                    object[n6 - 1] = n8;
                }
                for (n2 = 0; n2 < n6; ++n2) {
                    int n16;
                    if (object[n2] == false) continue;
                    int n17 = n2 - n15 + 1;
                    if (this.justify) {
                        n16 = 0;
                        for (int i = n15; i <= n2; ++i) {
                            n16 += rectangleArray[i].height + this.spacing;
                        }
                        n3 = Math.max(0, (n - n16) / (n17 + 1));
                        n14 = Math.max(0, (n - n16) % (n17 + 1) / 2);
                    }
                    for (n16 = n15; n16 <= n2; ++n16) {
                        if (this.justify) {
                            rectangleArray[n16].y += n3 * (n16 - n15 + 1) + n14;
                        }
                        if (this.fill) {
                            rectangleArray[n16].width = (int)object[n2];
                            continue;
                        }
                        if (!this.center) continue;
                        rectangleArray[n16].x += Math.max(0, (int)((object[n2] - rectangleArray[n16].width) / 2));
                    }
                    n15 = n2 + 1;
                }
            }
            for (int i = 0; i < n6; ++i) {
                if (!bl4) {
                    if (this.justify) {
                        rectangleArray[i].y += n3 * (i + 1) + n14;
                    }
                    if (this.fill) {
                        rectangleArray[i].width = n8;
                    } else if (this.center) {
                        rectangleArray[i].x += Math.max(0, (n8 - rectangleArray[i].width) / 2);
                    }
                }
                controlArray[i].setBounds(rectangleArray[i]);
            }
        }
        return new Point(n11 + n8 + this.marginRight + this.marginWidth, n10);
    }

    public String toString() {
        String string = this.getName() + " {";
        string = string + "type=" + (this.type != 256 ? "SWT.VERTICAL" : "SWT.HORIZONTAL") + " ";
        if (this.marginWidth != 0) {
            string = string + "marginWidth=" + this.marginWidth + " ";
        }
        if (this.marginHeight != 0) {
            string = string + "marginHeight=" + this.marginHeight + " ";
        }
        if (this.marginLeft != 0) {
            string = string + "marginLeft=" + this.marginLeft + " ";
        }
        if (this.marginTop != 0) {
            string = string + "marginTop=" + this.marginTop + " ";
        }
        if (this.marginRight != 0) {
            string = string + "marginRight=" + this.marginRight + " ";
        }
        if (this.marginBottom != 0) {
            string = string + "marginBottom=" + this.marginBottom + " ";
        }
        if (this.spacing != 0) {
            string = string + "spacing=" + this.spacing + " ";
        }
        string = string + "wrap=" + this.wrap + " ";
        string = string + "pack=" + this.pack + " ";
        string = string + "fill=" + this.fill + " ";
        string = string + "justify=" + this.justify + " ";
        string = string.trim();
        string = string + "}";
        return string;
    }
}

