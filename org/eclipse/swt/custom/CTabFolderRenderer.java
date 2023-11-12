/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;

public class CTabFolderRenderer {
    protected CTabFolder parent;
    int[] curve;
    int[] topCurveHighlightStart;
    int[] topCurveHighlightEnd;
    int curveWidth = 0;
    int curveIndent = 0;
    int lastTabHeight = -1;
    Color fillColor;
    Color selectionHighlightGradientBegin = null;
    Color[] selectionHighlightGradientColorsCache = null;
    Color selectedOuterColor = null;
    Color selectedInnerColor = null;
    Color tabAreaColor = null;
    Color lastBorderColor = null;
    private Font chevronFont = null;
    static final int[] TOP_LEFT_CORNER_HILITE = new int[]{5, 2, 4, 2, 3, 3, 2, 4, 2, 5, 1, 6};
    static final int[] TOP_LEFT_CORNER = new int[]{0, 6, 1, 5, 1, 4, 4, 1, 5, 1, 6, 0};
    static final int[] TOP_RIGHT_CORNER = new int[]{-6, 0, -5, 1, -4, 1, -1, 4, -1, 5, 0, 6};
    static final int[] BOTTOM_LEFT_CORNER = new int[]{0, -6, 1, -5, 1, -4, 4, -1, 5, -1, 6, 0};
    static final int[] BOTTOM_RIGHT_CORNER = new int[]{-6, 0, -5, -1, -4, -1, -1, -4, -1, -5, 0, -6};
    static final int[] SIMPLE_TOP_LEFT_CORNER = new int[]{0, 2, 1, 1, 2, 0};
    static final int[] SIMPLE_TOP_RIGHT_CORNER = new int[]{-2, 0, -1, 1, 0, 2};
    static final int[] SIMPLE_BOTTOM_LEFT_CORNER = new int[]{0, -2, 1, -1, 2, 0};
    static final int[] SIMPLE_BOTTOM_RIGHT_CORNER = new int[]{-2, 0, -1, -1, 0, -2};
    static final int[] SIMPLE_UNSELECTED_INNER_CORNER = new int[]{0, 0};
    static final int[] TOP_LEFT_CORNER_BORDERLESS = new int[]{0, 6, 1, 5, 1, 4, 4, 1, 5, 1, 6, 0};
    static final int[] TOP_RIGHT_CORNER_BORDERLESS = new int[]{-7, 0, -6, 1, -5, 1, -2, 4, -2, 5, -1, 6};
    static final int[] BOTTOM_LEFT_CORNER_BORDERLESS = new int[]{0, -6, 1, -6, 1, -5, 2, -4, 4, -2, 5, -1, 6, -1, 6, 0};
    static final int[] BOTTOM_RIGHT_CORNER_BORDERLESS = new int[]{-7, 0, -7, -1, -6, -1, -5, -2, -3, -4, -2, -5, -2, -6, -1, -6};
    static final int[] SIMPLE_TOP_LEFT_CORNER_BORDERLESS = new int[]{0, 2, 1, 1, 2, 0};
    static final int[] SIMPLE_TOP_RIGHT_CORNER_BORDERLESS = new int[]{-3, 0, -2, 1, -1, 2};
    static final int[] SIMPLE_BOTTOM_LEFT_CORNER_BORDERLESS = new int[]{0, -3, 1, -2, 2, -1, 3, 0};
    static final int[] SIMPLE_BOTTOM_RIGHT_CORNER_BORDERLESS = new int[]{-4, 0, -3, -1, -2, -2, -1, -3};
    static final RGB CLOSE_FILL = new RGB(240, 64, 64);
    static final int BUTTON_SIZE = 16;
    static final int BUTTON_TRIM = 1;
    static final int BUTTON_BORDER = 17;
    static final int BUTTON_FILL = 25;
    static final int BORDER1_COLOR = 18;
    static final int ITEM_TOP_MARGIN = 2;
    static final int ITEM_BOTTOM_MARGIN = 2;
    static final int ITEM_LEFT_MARGIN = 4;
    static final int ITEM_RIGHT_MARGIN = 4;
    static final int INTERNAL_SPACING = 4;
    static final int FLAGS = 11;
    static final String ELLIPSIS = "...";
    private static final String CHEVRON_ELLIPSIS = "99+";
    private static final int CHEVRON_FONT_HEIGHT = 10;
    public static final int PART_BODY = -1;
    public static final int PART_HEADER = -2;
    public static final int PART_BORDER = -3;
    public static final int PART_BACKGROUND = -4;
    public static final int PART_MAX_BUTTON = -5;
    public static final int PART_MIN_BUTTON = -6;
    public static final int PART_CHEVRON_BUTTON = -7;
    public static final int PART_CLOSE_BUTTON = -8;
    public static final int MINIMUM_SIZE = 0x1000000;

    protected CTabFolderRenderer(CTabFolder cTabFolder) {
        if (cTabFolder == null) {
            return;
        }
        if (cTabFolder.isDisposed()) {
            SWT.error(5);
        }
        this.parent = cTabFolder;
    }

    void antialias(int[] nArray, Color color, Color color2, GC gC) {
        int n;
        int n2;
        int n3;
        if (this.parent.simple) {
            return;
        }
        String string = SWT.getPlatform();
        if ("cocoa".equals(string)) {
            return;
        }
        if (this.parent.getDisplay().getDepth() < 15) {
            return;
        }
        if (color2 != null) {
            int n4 = 0;
            n3 = 1;
            n2 = this.parent.onBottom ? 0 : this.parent.getSize().y;
            int[] nArray2 = new int[nArray.length];
            for (n = 0; n < nArray.length / 2; ++n) {
                if (n3 != 0 && n4 + 3 < nArray.length) {
                    n3 = this.parent.onBottom ? (n2 <= nArray[n4 + 3] ? 1 : 0) : (n2 >= nArray[n4 + 3] ? 1 : 0);
                    n2 = nArray[n4 + 1];
                }
                nArray2[n4] = nArray[n4++] + (n3 != 0 ? -1 : 1);
                nArray2[n4] = nArray[n4++];
            }
            gC.setForeground(color2);
            gC.drawPolyline(nArray2);
        }
        if (color != null) {
            int[] nArray3 = new int[nArray.length];
            n3 = 0;
            n2 = 1;
            int n5 = this.parent.onBottom ? 0 : this.parent.getSize().y;
            for (n = 0; n < nArray.length / 2; ++n) {
                if (n2 != 0 && n3 + 3 < nArray.length) {
                    n2 = this.parent.onBottom ? (n5 <= nArray[n3 + 3] ? 1 : 0) : (n5 >= nArray[n3 + 3] ? 1 : 0);
                    n5 = nArray[n3 + 1];
                }
                nArray3[n3] = nArray[n3++] + (n2 != 0 ? 1 : -1);
                nArray3[n3] = nArray[n3++];
            }
            gC.setForeground(color);
            gC.drawPolyline(nArray3);
        }
    }

    protected Point computeSize(int n, int n2, GC gC, int n3, int n4) {
        Object object;
        int n5 = 0;
        int n6 = 0;
        switch (n) {
            case -2: {
                if (this.parent.fixedTabHeight != -1) {
                    n6 = this.parent.fixedTabHeight == 0 ? 0 : this.parent.fixedTabHeight + 1;
                    break;
                }
                object = this.parent.items;
                if (((CTabItem[])object).length == 0) {
                    n6 = gC.textExtent((String)"Default", (int)11).y + 2 + 2;
                } else {
                    for (int i = 0; i < ((CTabItem[])object).length; ++i) {
                        n6 = Math.max(n6, this.computeSize((int)i, (int)0, (GC)gC, (int)n3, (int)n4).y);
                    }
                }
                gC.dispose();
                break;
            }
            case -8: 
            case -6: 
            case -5: {
                n5 = 16;
                n6 = 16;
                break;
            }
            case -7: {
                object = gC.getFont();
                gC.setFont(this.getChevronFont(this.parent.getDisplay()));
                int n7 = 8;
                n5 = gC.textExtent((String)CHEVRON_ELLIPSIS).x + 8;
                n6 = 16;
                gC.setFont((Font)object);
                break;
            }
            default: {
                Object object2;
                if (0 > n || n >= this.parent.getItemCount()) break;
                this.updateCurves();
                object = this.parent.items[n];
                if (object.isDisposed()) {
                    return new Point(0, 0);
                }
                Image image = ((Item)object).getImage();
                if (image != null && !image.isDisposed()) {
                    object2 = image.getBounds();
                    if ((n2 & 2) != 0 || this.parent.showUnselectedImage) {
                        n5 += ((Rectangle)object2).width;
                    }
                    n6 = ((Rectangle)object2).height;
                }
                object2 = null;
                if ((n2 & 0x1000000) != 0) {
                    int n8 = this.parent.minChars;
                    Object object3 = object2 = n8 == 0 ? null : ((Item)object).getText();
                    if (object2 != null && ((String)object2).length() > n8) {
                        int n9;
                        if (this.useEllipses()) {
                            n9 = n8 < 4 ? n8 : n8 - 3;
                            object2 = ((String)object2).substring(0, n9);
                            if (n8 > 4) {
                                object2 = (String)object2 + ELLIPSIS;
                            }
                        } else {
                            n9 = n8;
                            object2 = ((String)object2).substring(0, n9);
                        }
                    }
                } else {
                    object2 = ((Item)object).getText();
                }
                if (object2 != null) {
                    if (n5 > 0) {
                        n5 += 4;
                    }
                    if (((CTabItem)object).font == null) {
                        Point point = gC.textExtent((String)object2, 11);
                        n5 += point.x;
                        n6 = Math.max(n6, point.y);
                    } else {
                        Font font = gC.getFont();
                        gC.setFont(((CTabItem)object).font);
                        Point point = gC.textExtent((String)object2, 11);
                        n5 += point.x;
                        n6 = Math.max(n6, point.y);
                        gC.setFont(font);
                    }
                }
                if (!this.parent.showClose && !((CTabItem)object).showClose || (n2 & 2) == 0 && !this.parent.showUnselectedClose) break;
                if (n5 > 0) {
                    n5 += 4;
                }
                n5 += this.computeSize((int)-8, (int)0, (GC)gC, (int)-1, (int)-1).x;
                break;
            }
        }
        object = this.computeTrim(n, n2, 0, 0, n5, n6);
        n5 = ((Rectangle)object).width;
        n6 = ((Rectangle)object).height;
        return new Point(n5, n6);
    }

    protected Rectangle computeTrim(int n, int n2, int n3, int n4, int n5, int n6) {
        int n7 = this.parent.borderVisible ? 1 : 0;
        int n8 = n7;
        int n9 = this.parent.onBottom ? n8 : 0;
        int n10 = this.parent.onBottom ? 0 : n8;
        int n11 = this.parent.tabHeight;
        switch (n) {
            case -1: {
                int n12;
                int n13 = this.parent.getStyle();
                int n14 = (n13 & 0x800000) != 0 ? 1 : 3;
                int n15 = n12 = (n13 & 0x800000) != 0 ? 0 : 2;
                if (this.parent.fixedTabHeight == 0 && (n13 & 0x800000) != 0 && (n13 & 0x800) == 0) {
                    n14 = 0;
                }
                int n16 = this.parent.marginWidth;
                int n17 = this.parent.marginHeight;
                n3 = n3 - n16 - n12 - n8;
                n5 = n5 + n8 + n7 + 2 * n16 + 2 * n12;
                if (this.parent.minimized) {
                    n4 = this.parent.onBottom ? n4 - n9 : n4 - n14 - n11 - n9;
                    n6 = n9 + n10 + n11 + n14;
                    break;
                }
                n4 = this.parent.onBottom ? n4 - n17 - n12 - n9 : n4 - n17 - n14 - n11 - n9;
                n6 = n6 + n9 + n10 + 2 * n17 + n11 + n14 + n12;
                break;
            }
            case -2: {
                break;
            }
            case -8: 
            case -7: 
            case -6: 
            case -5: {
                --n3;
                --n4;
                n5 += 2;
                n6 += 2;
                break;
            }
            case -3: {
                n3 -= n8;
                n5 = n5 + n8 + n7;
                if (!this.parent.simple) {
                    n5 += 2;
                }
                n4 -= n9;
                n6 = n6 + n9 + n10;
                break;
            }
            default: {
                if (0 > n || n >= this.parent.getItemCount()) break;
                this.updateCurves();
                n3 -= 4;
                n5 = n5 + 4 + 4;
                if (!this.parent.simple && !this.parent.single && (n2 & 2) != 0) {
                    n5 += this.curveWidth - this.curveIndent;
                }
                n4 -= 2;
                n6 = n6 + 2 + 2;
            }
        }
        return new Rectangle(n3, n4, n5, n6);
    }

    void createAntialiasColors() {
        int n;
        int n2;
        int n3;
        RGB rGB;
        RGB rGB2;
        this.disposeAntialiasColors();
        this.lastBorderColor = this.parent.getDisplay().getSystemColor(18);
        RGB rGB3 = this.lastBorderColor.getRGB();
        RGB rGB4 = this.parent.selectionBackground.getRGB();
        if (this.parent.selectionBgImage != null || this.parent.selectionGradientColors != null && this.parent.selectionGradientColors.length > 1) {
            rGB4 = null;
        }
        RGB rGB5 = this.parent.getBackground().getRGB();
        if (this.parent.gradientColors != null && this.parent.gradientColors.length > 1) {
            rGB5 = null;
        }
        if (rGB5 != null) {
            rGB2 = rGB3;
            rGB = rGB5;
            n3 = rGB2.red + 2 * (rGB.red - rGB2.red) / 3;
            n2 = rGB2.green + 2 * (rGB.green - rGB2.green) / 3;
            n = rGB2.blue + 2 * (rGB.blue - rGB2.blue) / 3;
            this.selectedOuterColor = new Color(n3, n2, n);
        }
        if (rGB4 != null) {
            rGB2 = rGB3;
            rGB = rGB4;
            n3 = rGB2.red + 2 * (rGB.red - rGB2.red) / 3;
            n2 = rGB2.green + 2 * (rGB.green - rGB2.green) / 3;
            n = rGB2.blue + 2 * (rGB.blue - rGB2.blue) / 3;
            this.selectedInnerColor = new Color(n3, n2, n);
        }
        if ((rGB5 = this.parent.getParent().getBackground().getRGB()) != null) {
            rGB2 = rGB3;
            rGB = rGB5;
            n3 = rGB2.red + 2 * (rGB.red - rGB2.red) / 3;
            n2 = rGB2.green + 2 * (rGB.green - rGB2.green) / 3;
            n = rGB2.blue + 2 * (rGB.blue - rGB2.blue) / 3;
            this.tabAreaColor = new Color(n3, n2, n);
        }
    }

    void createSelectionHighlightGradientColors(Color color) {
        this.disposeSelectionHighlightGradientColors();
        if (color == null) {
            return;
        }
        int n = this.parent.tabHeight;
        RGB rGB = color.getRGB();
        RGB rGB2 = this.parent.selectionBackground.getRGB();
        this.selectionHighlightGradientColorsCache = new Color[n];
        int n2 = n - 1;
        for (int i = 0; i < n; ++i) {
            int n3 = n2 - i;
            int n4 = i;
            int n5 = (rGB2.red * n4 + rGB.red * n3) / n2;
            int n6 = (rGB2.green * n4 + rGB.green * n3) / n2;
            int n7 = (rGB2.blue * n4 + rGB.blue * n3) / n2;
            this.selectionHighlightGradientColorsCache[i] = new Color(n5, n6, n7);
        }
    }

    protected void dispose() {
        this.disposeAntialiasColors();
        this.disposeSelectionHighlightGradientColors();
        this.fillColor = null;
        if (this.chevronFont != null) {
            this.chevronFont.dispose();
            this.chevronFont = null;
        }
    }

    void disposeAntialiasColors() {
        Object var1_1 = null;
        this.selectedOuterColor = var1_1;
        this.selectedInnerColor = var1_1;
        this.tabAreaColor = var1_1;
    }

    void disposeSelectionHighlightGradientColors() {
        this.selectionHighlightGradientColorsCache = null;
    }

    protected void draw(int n, int n2, Rectangle rectangle, GC gC) {
        switch (n) {
            case -4: {
                this.drawBackground(gC, rectangle, n2);
                break;
            }
            case -1: {
                this.drawBody(gC, rectangle, n2);
                break;
            }
            case -2: {
                this.drawTabArea(gC, rectangle, n2);
                break;
            }
            case -5: {
                this.drawMaximize(gC, rectangle, n2);
                break;
            }
            case -6: {
                this.drawMinimize(gC, rectangle, n2);
                break;
            }
            case -7: {
                this.drawChevron(gC, rectangle, n2);
                break;
            }
            default: {
                if (0 > n || n >= this.parent.getItemCount()) break;
                if (rectangle.width == 0 || rectangle.height == 0) {
                    return;
                }
                if ((n2 & 2) != 0) {
                    this.drawSelected(n, gC, rectangle, n2);
                    break;
                }
                this.drawUnselected(n, gC, rectangle, n2);
            }
        }
    }

    void drawBackground(GC gC, Rectangle rectangle, int n) {
        boolean bl = (n & 2) != 0;
        Color color = bl && this.parent.shouldHighlight() ? this.parent.selectionBackground : this.parent.getBackground();
        Image image = bl ? this.parent.selectionBgImage : null;
        Color[] colorArray = bl & this.parent.shouldHighlight() ? this.parent.selectionGradientColors : this.parent.gradientColors;
        int[] nArray = bl ? this.parent.selectionGradientPercents : this.parent.gradientPercents;
        boolean bl2 = bl ? this.parent.selectionGradientVertical : this.parent.gradientVertical;
        this.drawBackground(gC, null, rectangle.x, rectangle.y, rectangle.width, rectangle.height, color, image, colorArray, nArray, bl2);
    }

    void drawBackground(GC gC, int[] nArray, boolean bl) {
        int n;
        Color color = bl && this.parent.shouldHighlight() ? this.parent.selectionBackground : this.parent.getBackground();
        Image image = bl ? this.parent.selectionBgImage : null;
        Color[] colorArray = bl && this.parent.shouldHighlight() ? this.parent.selectionGradientColors : this.parent.gradientColors;
        int[] nArray2 = bl ? this.parent.selectionGradientPercents : this.parent.gradientPercents;
        boolean bl2 = bl ? this.parent.selectionGradientVertical : this.parent.gradientVertical;
        Point point = this.parent.getSize();
        int n2 = point.x;
        int n3 = this.parent.tabHeight + ((this.parent.getStyle() & 0x800000) != 0 ? 1 : 3);
        int n4 = 0;
        int n5 = this.parent.borderVisible ? 1 : 0;
        int n6 = this.parent.onBottom ? n5 : 0;
        int n7 = n = this.parent.onBottom ? 0 : n5;
        if (n5 > 0) {
            ++n4;
            n2 -= 2;
        }
        int n8 = this.parent.onBottom ? point.y - n - n3 : n6;
        this.drawBackground(gC, nArray, n4, n8, n2, n3, color, image, colorArray, nArray2, bl2);
    }

    void drawBackground(GC gC, int[] nArray, int n, int n2, int n3, int n4, Color color, Image image, Color[] colorArray, int[] nArray2, boolean bl) {
        Region region = null;
        Region region2 = null;
        if (nArray != null) {
            region = new Region();
            gC.getClipping(region);
            region2 = new Region();
            region2.add(nArray);
            region2.intersect(region);
            gC.setClipping(region2);
        }
        if (image != null) {
            gC.setBackground(color);
            gC.fillRectangle(n, n2, n3, n4);
            Rectangle rectangle = image.getBounds();
            gC.drawImage(image, rectangle.x, rectangle.y, rectangle.width, rectangle.height, n, n2, n3, n4);
        } else if (colorArray != null) {
            if (colorArray.length == 1) {
                Color color2 = colorArray[0] != null ? colorArray[0] : color;
                gC.setBackground(color2);
                gC.fillRectangle(n, n2, n3, n4);
            } else if (bl) {
                if (this.parent.onBottom) {
                    Color color3;
                    int n5 = 0;
                    if (nArray2[nArray2.length - 1] < 100) {
                        n5 = (100 - nArray2[nArray2.length - 1]) * n4 / 100;
                        gC.setBackground(color);
                        gC.fillRectangle(n, n2, n3, n5);
                    }
                    if ((color3 = colorArray[colorArray.length - 1]) == null) {
                        color3 = color;
                    }
                    for (int i = nArray2.length - 1; i >= 0; --i) {
                        gC.setForeground(color3);
                        color3 = colorArray[i];
                        if (color3 == null) {
                            color3 = color;
                        }
                        gC.setBackground(color3);
                        int n6 = i > 0 ? nArray2[i] - nArray2[i - 1] : nArray2[i];
                        int n7 = n6 * n4 / 100;
                        gC.fillGradientRectangle(n, n2 + n5, n3, n7, true);
                        n5 += n7;
                    }
                } else {
                    Color color4 = colorArray[0];
                    if (color4 == null) {
                        color4 = color;
                    }
                    int n8 = 0;
                    for (int i = 0; i < nArray2.length; ++i) {
                        gC.setForeground(color4);
                        color4 = colorArray[i + 1];
                        if (color4 == null) {
                            color4 = color;
                        }
                        gC.setBackground(color4);
                        int n9 = i > 0 ? nArray2[i] - nArray2[i - 1] : nArray2[i];
                        int n10 = n9 * n4 / 100;
                        gC.fillGradientRectangle(n, n2 + n8, n3, n10, true);
                        n8 += n10;
                    }
                    if (n8 < n4) {
                        gC.setBackground(color);
                        gC.fillRectangle(n, n8, n3, n4 - n8 + 1);
                    }
                }
            } else {
                n2 = 0;
                n4 = this.parent.getSize().y;
                Color color5 = colorArray[0];
                if (color5 == null) {
                    color5 = color;
                }
                int n11 = 0;
                for (int i = 0; i < nArray2.length; ++i) {
                    gC.setForeground(color5);
                    color5 = colorArray[i + 1];
                    if (color5 == null) {
                        color5 = color;
                    }
                    gC.setBackground(color5);
                    int n12 = nArray2[i] * n3 / 100 - n11;
                    gC.fillGradientRectangle(n + n11, n2, n12, n4, false);
                    n11 += n12;
                }
                if (n11 < n3) {
                    gC.setBackground(color);
                    gC.fillRectangle(n + n11, n2, n3 - n11, n4);
                }
            }
        } else if ((this.parent.getStyle() & 0x40000) != 0 || !color.equals(this.parent.getBackground())) {
            gC.setBackground(color);
            gC.fillRectangle(n, n2, n3, n4);
        }
        if (nArray != null) {
            gC.setClipping(region);
            region.dispose();
            region2.dispose();
        }
    }

    void drawBorder(GC gC, int[] nArray) {
        gC.setForeground(this.parent.getDisplay().getSystemColor(18));
        gC.drawPolyline(nArray);
    }

    void drawBody(GC gC, Rectangle rectangle, int n) {
        int n2;
        int n3;
        int n4;
        int n5;
        Point point = new Point(rectangle.width, rectangle.height);
        int n6 = this.parent.selectedIndex;
        int n7 = this.parent.tabHeight;
        int n8 = this.parent.borderVisible ? 1 : 0;
        int n9 = n8;
        int n10 = this.parent.onBottom ? n9 : 0;
        int n11 = this.parent.onBottom ? 0 : n9;
        int n12 = this.parent.getStyle();
        int n13 = (n12 & 0x800000) != 0 ? 1 : 3;
        int n14 = n5 = (n12 & 0x800000) != 0 ? 0 : 2;
        if (!this.parent.minimized) {
            int n15;
            int n16;
            n4 = point.x - n9 - n8 - 2 * n5;
            n3 = point.y - n10 - n11 - n7 - n13 - n5;
            if (n5 > 0) {
                int[] nArray = null;
                if (this.parent.onBottom) {
                    n2 = n9;
                    n16 = n10;
                    n15 = point.x - n8;
                    int n17 = point.y - n11 - n7 - n13;
                    nArray = new int[]{n2, n16, n15, n16, n15, n17, n15 - n5, n17, n15 - n5, n16 + n5, n2 + n5, n16 + n5, n2 + n5, n17, n2, n17};
                } else {
                    n2 = n9;
                    n16 = n10 + n7 + n13;
                    n15 = point.x - n8;
                    int n18 = point.y - n11;
                    nArray = new int[]{n2, n16, n2 + n5, n16, n2 + n5, n18 - n5, n15 - n5, n18 - n5, n15 - n5, n16, n15, n16, n15, n18, n2, n18};
                }
                if (n6 != -1 && this.parent.selectionGradientColors != null && this.parent.selectionGradientColors.length > 1 && !this.parent.selectionGradientVertical) {
                    this.drawBackground(gC, nArray, true);
                } else if (n6 == -1 && this.parent.gradientColors != null && this.parent.gradientColors.length > 1 && !this.parent.gradientVertical) {
                    this.drawBackground(gC, nArray, false);
                } else {
                    gC.setBackground(n6 != -1 && this.parent.shouldHighlight() ? this.parent.selectionBackground : this.parent.getBackground());
                    gC.fillPolygon(nArray);
                }
            }
            if ((this.parent.getStyle() & 0x40000) != 0) {
                gC.setBackground(this.parent.getBackground());
                int n19 = this.parent.marginWidth;
                n2 = this.parent.marginHeight;
                n16 = n9 + n19 + n5;
                n15 = this.parent.onBottom ? n10 + n5 + n2 : n10 + n7 + n13 + n2;
                gC.fillRectangle(n16 - n19, n15 - n2, n4, n3);
            }
        } else if ((this.parent.getStyle() & 0x40000) != 0 && point.y > (n4 = n10 + n7 + n13 + n11)) {
            gC.setBackground(this.parent.getParent().getBackground());
            gC.fillRectangle(0, n4, point.x, point.y - n4);
        }
        if (n9 > 0) {
            gC.setForeground(this.parent.getDisplay().getSystemColor(18));
            n4 = n9 - 1;
            n3 = point.x - n8;
            int n20 = this.parent.onBottom ? n10 - 1 : n10 + n7;
            n2 = this.parent.onBottom ? point.y - n7 - n11 - 1 : point.y - n11;
            gC.drawLine(n4, n20, n4, n2);
            gC.drawLine(n3, n20, n3, n2);
            if (this.parent.onBottom) {
                gC.drawLine(n4, n20, n3, n20);
            } else {
                gC.drawLine(n4, n2, n3, n2);
            }
        }
    }

    void drawClose(GC gC, Rectangle rectangle, int n) {
        if (rectangle.width == 0 || rectangle.height == 0) {
            return;
        }
        int n2 = rectangle.x + Math.max(1, (rectangle.width - 8) / 2);
        int n3 = rectangle.y + Math.max(1, (rectangle.height - 8) / 2);
        n3 += this.parent.onBottom ? -1 : 1;
        int n4 = gC.getLineWidth();
        Color color = gC.getForeground();
        switch (n & 0x2A) {
            case 0: {
                this.drawCloseLines(gC, n2, n3, 8, false);
                break;
            }
            case 32: {
                this.drawCloseLines(gC, n2, n3, 8, true);
                break;
            }
            case 2: {
                this.drawCloseLines(gC, n2, n3, 8, true);
                break;
            }
            case 8: {
                int[] nArray = new int[]{n2, n3, n2 + 10, n3, n2 + 10, n3 + 10, n2, n3 + 10};
                this.drawBackground(gC, nArray, false);
                break;
            }
        }
        gC.setLineWidth(n4);
        gC.setForeground(color);
    }

    private void drawCloseLines(GC gC, int n, int n2, int n3, boolean bl) {
        if (bl) {
            gC.setLineWidth(gC.getLineWidth() + 2);
            gC.setForeground(this.getFillColor());
        }
        gC.setLineCap(2);
        gC.drawLine(n, n2, n + n3, n2 + n3);
        gC.drawLine(n, n2 + n3, n + n3, n2);
    }

    void drawChevron(GC gC, Rectangle rectangle, int n) {
        if (rectangle.width == 0 || rectangle.height == 0) {
            return;
        }
        Display display = this.parent.getDisplay();
        Font font = this.getChevronFont(display);
        int n2 = font.getFontData()[0].getHeight();
        int n3 = Math.max(2, (rectangle.height - n2 - 4) / 2);
        int n4 = rectangle.x + 2;
        int n5 = rectangle.y + n3;
        int n6 = this.parent.getChevronCount();
        String string = n6 > 99 ? CHEVRON_ELLIPSIS : String.valueOf(n6);
        switch (n & 0x22) {
            case 0: {
                Color color = this.parent.single ? this.parent.getSelectionForeground() : this.parent.getForeground();
                gC.setForeground(color);
                gC.setFont(font);
                this.drawChevronContent(gC, n4, n5, string);
                break;
            }
            case 32: {
                gC.setForeground(display.getSystemColor(17));
                gC.setBackground(display.getSystemColor(25));
                gC.setFont(font);
                this.drawRoundRectangle(gC, rectangle);
                this.drawChevronContent(gC, n4, n5, string);
                break;
            }
            case 2: {
                gC.setForeground(display.getSystemColor(17));
                gC.setBackground(display.getSystemColor(25));
                gC.setFont(font);
                this.drawRoundRectangle(gC, rectangle);
                this.drawChevronContent(gC, n4 + 1, n5 + 1, string);
            }
        }
    }

    private void drawRoundRectangle(GC gC, Rectangle rectangle) {
        gC.fillRoundRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height, 6, 6);
        gC.drawRoundRectangle(rectangle.x, rectangle.y, rectangle.width - 1, rectangle.height - 1, 6, 6);
    }

    private void drawChevronContent(GC gC, int n, int n2, String string) {
        gC.drawLine(n, n2, n + 2, n2 + 2);
        gC.drawLine(n + 2, n2 + 2, n, n2 + 4);
        gC.drawLine(n + 1, n2, n + 3, n2 + 2);
        gC.drawLine(n + 3, n2 + 2, n + 1, n2 + 4);
        gC.drawLine(n + 4, n2, n + 6, n2 + 2);
        gC.drawLine(n + 6, n2 + 2, n + 4, n2 + 4);
        gC.drawLine(n + 5, n2, n + 7, n2 + 2);
        gC.drawLine(n + 7, n2 + 2, n + 5, n2 + 4);
        gC.drawString(string, n + 7, n2 + 3, true);
    }

    void drawHighlight(GC gC, Rectangle rectangle, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        if (this.parent.simple || this.parent.onBottom) {
            return;
        }
        if (this.selectionHighlightGradientBegin == null) {
            return;
        }
        Color[] colorArray = this.selectionHighlightGradientColorsCache;
        if (colorArray == null) {
            return;
        }
        int n7 = colorArray.length;
        if (n7 == 0) {
            return;
        }
        int n8 = rectangle.x;
        int n9 = rectangle.y;
        gC.setForeground(colorArray[0]);
        gC.drawLine(TOP_LEFT_CORNER_HILITE[0] + n8 + 1, 1 + n9, n2 - this.curveIndent, 1 + n9);
        int[] nArray = TOP_LEFT_CORNER_HILITE;
        int n10 = this.parent.tabHeight - this.topCurveHighlightEnd.length / 2;
        int n11 = 0;
        int n12 = 0;
        int n13 = 0;
        for (n6 = 0; n6 < nArray.length / 2; ++n6) {
            n5 = nArray[n6 * 2];
            n4 = nArray[n6 * 2 + 1];
            n11 = n5 + n8;
            n12 = n4 + n9;
            n13 = n4 - 1;
            gC.setForeground(colorArray[n13]);
            gC.drawPoint(n11, n12);
        }
        for (n6 = n13; n6 < n7; ++n6) {
            gC.setForeground(colorArray[n6]);
            gC.drawPoint(n11, 1 + n12++);
        }
        n6 = n2 - this.curveIndent;
        for (n5 = 0; n5 < this.topCurveHighlightStart.length / 2; ++n5) {
            n4 = this.topCurveHighlightStart[n5 * 2];
            n3 = this.topCurveHighlightStart[n5 * 2 + 1];
            n11 = n4 + n6;
            n12 = n3 + n9;
            n13 = n3 - 1;
            if (n13 >= n7) break;
            gC.setForeground(colorArray[n13]);
            gC.drawPoint(n11, n12);
        }
        for (n5 = n13; n5 < n13 + n10 && n5 < n7; ++n5) {
            gC.setForeground(colorArray[n5]);
            gC.drawPoint(1 + n11++, 1 + n12++);
        }
        for (n5 = 0; n5 < this.topCurveHighlightEnd.length / 2; ++n5) {
            n4 = this.topCurveHighlightEnd[n5 * 2];
            n3 = this.topCurveHighlightEnd[n5 * 2 + 1];
            n11 = n4 + n6;
            n12 = n3 + n9;
            n13 = n3 - 1;
            if (n13 >= n7) break;
            gC.setForeground(colorArray[n13]);
            gC.drawPoint(n11, n12);
        }
    }

    void drawLeftUnselectedBorder(GC gC, Rectangle rectangle, int n) {
        int n2 = rectangle.x;
        int n3 = rectangle.y;
        int n4 = rectangle.height;
        int[] nArray = null;
        if (this.parent.onBottom) {
            int[] nArray2 = this.parent.simple ? SIMPLE_UNSELECTED_INNER_CORNER : BOTTOM_LEFT_CORNER;
            nArray = new int[nArray2.length + 2];
            int n5 = 0;
            nArray[n5++] = n2;
            nArray[n5++] = n3 - 1;
            for (int i = 0; i < nArray2.length / 2; ++i) {
                nArray[n5++] = n2 + nArray2[2 * i];
                nArray[n5++] = n3 + n4 + nArray2[2 * i + 1] - 1;
            }
        } else {
            int[] nArray3 = this.parent.simple ? SIMPLE_UNSELECTED_INNER_CORNER : TOP_LEFT_CORNER;
            nArray = new int[nArray3.length + 2];
            int n6 = 0;
            nArray[n6++] = n2;
            nArray[n6++] = n3 + n4;
            for (int i = 0; i < nArray3.length / 2; ++i) {
                nArray[n6++] = n2 + nArray3[2 * i];
                nArray[n6++] = n3 + nArray3[2 * i + 1];
            }
        }
        this.drawBorder(gC, nArray);
    }

    void drawMaximize(GC gC, Rectangle rectangle, int n) {
        if (rectangle.width == 0 || rectangle.height == 0) {
            return;
        }
        int n2 = rectangle.x + (rectangle.width - 10) / 2;
        int n3 = rectangle.y + 3;
        gC.setForeground(this.parent.getForeground());
        switch (n & 0x22) {
            case 0: {
                if (!this.parent.getMaximized()) {
                    gC.drawRectangle(n2, n3, 9, 9);
                    gC.drawLine(n2, n3 + 2, n2 + 9, n3 + 2);
                    break;
                }
                gC.drawRectangle(n2, n3 + 3, 5, 4);
                gC.drawRectangle(n2 + 2, n3, 5, 4);
                gC.drawLine(n2 + 2, n3 + 1, n2 + 7, n3 + 1);
                gC.drawLine(n2, n3 + 4, n2 + 5, n3 + 4);
                break;
            }
            case 32: {
                this.drawRoundRectangle(gC, rectangle);
                if (!this.parent.getMaximized()) {
                    gC.drawRectangle(n2, n3, 9, 9);
                    gC.drawLine(n2, n3 + 2, n2 + 9, n3 + 2);
                    break;
                }
                gC.drawRectangle(n2, n3 + 3, 5, 4);
                gC.drawRectangle(n2 + 2, n3, 5, 4);
                gC.drawLine(n2 + 2, n3 + 1, n2 + 7, n3 + 1);
                gC.drawLine(n2, n3 + 4, n2 + 5, n3 + 4);
                break;
            }
            case 2: {
                this.drawRoundRectangle(gC, rectangle);
                if (!this.parent.getMaximized()) {
                    gC.drawRectangle(n2 + 1, n3 + 1, 9, 9);
                    gC.drawLine(n2 + 1, n3 + 3, n2 + 10, n3 + 3);
                    break;
                }
                gC.drawRectangle(n2 + 1, n3 + 4, 5, 4);
                gC.drawRectangle(n2 + 3, n3 + 1, 5, 4);
                gC.drawLine(n2 + 3, n3 + 2, n2 + 8, n3 + 2);
                gC.drawLine(n2 + 1, n3 + 5, n2 + 6, n3 + 5);
            }
        }
    }

    void drawMinimize(GC gC, Rectangle rectangle, int n) {
        if (rectangle.width == 0 || rectangle.height == 0) {
            return;
        }
        int n2 = rectangle.x + (rectangle.width - 10) / 2;
        int n3 = rectangle.y + 3;
        gC.setForeground(this.parent.getForeground());
        switch (n & 0x22) {
            case 0: {
                if (!this.parent.getMinimized()) {
                    gC.drawRectangle(n2, n3, 9, 3);
                    break;
                }
                gC.drawRectangle(n2, n3 + 3, 5, 4);
                gC.drawRectangle(n2 + 2, n3, 5, 4);
                gC.drawLine(n2 + 3, n3 + 1, n2 + 6, n3 + 1);
                gC.drawLine(n2 + 1, n3 + 4, n2 + 4, n3 + 4);
                break;
            }
            case 32: {
                this.drawRoundRectangle(gC, rectangle);
                if (!this.parent.getMinimized()) {
                    gC.drawRectangle(n2, n3, 9, 3);
                    break;
                }
                gC.drawRectangle(n2, n3 + 3, 5, 4);
                gC.drawRectangle(n2 + 2, n3, 5, 4);
                gC.drawLine(n2 + 3, n3 + 1, n2 + 6, n3 + 1);
                gC.drawLine(n2 + 1, n3 + 4, n2 + 4, n3 + 4);
                break;
            }
            case 2: {
                this.drawRoundRectangle(gC, rectangle);
                if (!this.parent.getMinimized()) {
                    gC.drawRectangle(n2 + 1, n3 + 1, 9, 3);
                    break;
                }
                gC.drawRectangle(n2 + 1, n3 + 4, 5, 4);
                gC.drawRectangle(n2 + 3, n3 + 1, 5, 4);
                gC.drawLine(n2 + 4, n3 + 2, n2 + 7, n3 + 2);
                gC.drawLine(n2 + 2, n3 + 5, n2 + 5, n3 + 5);
            }
        }
    }

    void drawRightUnselectedBorder(GC gC, Rectangle rectangle, int n) {
        int n2 = rectangle.x;
        int n3 = rectangle.y;
        int n4 = rectangle.width;
        int n5 = rectangle.height;
        int[] nArray = null;
        int n6 = n2 + n4 - 1;
        if (this.parent.onBottom) {
            int[] nArray2 = this.parent.simple ? SIMPLE_UNSELECTED_INNER_CORNER : BOTTOM_RIGHT_CORNER;
            nArray = new int[nArray2.length + 2];
            int n7 = 0;
            for (int i = 0; i < nArray2.length / 2; ++i) {
                nArray[n7++] = n6 + nArray2[2 * i];
                nArray[n7++] = n3 + n5 + nArray2[2 * i + 1] - 1;
            }
            nArray[n7++] = n6;
            nArray[n7++] = n3 - 1;
        } else {
            int[] nArray3 = this.parent.simple ? SIMPLE_UNSELECTED_INNER_CORNER : TOP_RIGHT_CORNER;
            nArray = new int[nArray3.length + 2];
            int n8 = 0;
            for (int i = 0; i < nArray3.length / 2; ++i) {
                nArray[n8++] = n6 + nArray3[2 * i];
                nArray[n8++] = n3 + nArray3[2 * i + 1];
            }
            nArray[n8++] = n6;
            nArray[n8++] = n3 + n5;
        }
        this.drawBorder(gC, nArray);
    }

    void drawSelected(int n, GC gC, Rectangle rectangle, int n2) {
        Object object;
        Object object2;
        int n3;
        int n4;
        CTabItem cTabItem = this.parent.items[n];
        int n5 = rectangle.x;
        int n6 = rectangle.y;
        int n7 = rectangle.height;
        int n8 = rectangle.width;
        if (!this.parent.simple && !this.parent.single) {
            n8 -= this.curveWidth - this.curveIndent;
        }
        int n9 = this.parent.borderVisible ? 1 : 0;
        int n10 = n9;
        int n11 = this.parent.onBottom ? n10 : 0;
        int n12 = this.parent.onBottom ? 0 : n10;
        Point point = this.parent.getSize();
        int n13 = Math.min(n5 + n8, this.parent.getRightItemEdge(gC));
        if ((n2 & 8) != 0) {
            int n14 = (this.parent.getStyle() & 0x800000) != 0 ? 1 : 3;
            n4 = n10;
            int n15 = this.parent.onBottom ? point.y - n12 - this.parent.tabHeight - n14 : n11 + this.parent.tabHeight + 1;
            int n16 = point.x - n10 - n9;
            n3 = n14 - 1;
            int[] nArray = new int[]{n4, n15, n4 + n16, n15, n4 + n16, n15 + n3, n4, n15 + n3};
            if (this.parent.selectionGradientColors != null && !this.parent.selectionGradientVertical) {
                this.drawBackground(gC, nArray, this.parent.shouldHighlight());
            } else {
                gC.setBackground(this.parent.shouldHighlight() ? this.parent.selectionBackground : this.parent.getBackground());
                gC.fillRectangle(n4, n15, n16, n3);
            }
            if (this.parent.single) {
                if (!cTabItem.showing) {
                    return;
                }
            } else {
                Object object3;
                boolean bl;
                int n17;
                int n18;
                Object object4;
                if (!cTabItem.showing) {
                    int n19 = Math.max(0, n10 - 1);
                    int n20 = this.parent.onBottom ? n6 - 1 : n6 + n7;
                    int n21 = point.x - n9;
                    gC.setForeground(this.parent.getDisplay().getSystemColor(18));
                    gC.drawLine(n19, n20, n21, n20);
                    return;
                }
                nArray = null;
                if (this.parent.onBottom) {
                    object4 = this.parent.simple ? SIMPLE_BOTTOM_LEFT_CORNER : BOTTOM_LEFT_CORNER;
                    object2 = this.parent.simple ? SIMPLE_BOTTOM_RIGHT_CORNER : this.curve;
                    if (n10 == 0 && n == this.parent.firstIndex) {
                        object4 = new int[]{n5, n6 + n7};
                    }
                    nArray = new int[((int[])object4).length + ((Object)object2).length + 8];
                    n18 = 0;
                    nArray[n18++] = n5;
                    nArray[n18++] = n6 - 1;
                    nArray[n18++] = n5;
                    nArray[n18++] = n6 - 1;
                    for (n17 = 0; n17 < ((int[])object4).length / 2; ++n17) {
                        nArray[n18++] = n5 + object4[2 * n17];
                        nArray[n18++] = n6 + n7 + object4[2 * n17 + 1] - 1;
                    }
                    for (n17 = 0; n17 < ((Object)object2).length / 2; ++n17) {
                        nArray[n18++] = this.parent.simple ? n13 - 1 + object2[2 * n17] : n13 - this.curveIndent + object2[2 * n17];
                        nArray[n18++] = this.parent.simple ? n6 + n7 + object2[2 * n17 + 1] - 1 : n6 + object2[2 * n17 + 1] - 2;
                    }
                    nArray[n18++] = this.parent.simple ? n13 - 1 : n13 + this.curveWidth - this.curveIndent;
                    nArray[n18++] = n6 - 1;
                    nArray[n18++] = this.parent.simple ? n13 - 1 : n13 + this.curveWidth - this.curveIndent;
                    nArray[n18++] = n6 - 1;
                } else {
                    object4 = this.parent.simple ? SIMPLE_TOP_LEFT_CORNER : TOP_LEFT_CORNER;
                    object2 = this.parent.simple ? SIMPLE_TOP_RIGHT_CORNER : this.curve;
                    if (n10 == 0 && n == this.parent.firstIndex) {
                        object4 = new int[]{n5, n6};
                    }
                    nArray = new int[((int[])object4).length + ((Object)object2).length + 8];
                    n18 = 0;
                    nArray[n18++] = n5;
                    nArray[n18++] = n6 + n7 + 1;
                    nArray[n18++] = n5;
                    nArray[n18++] = n6 + n7 + 1;
                    for (n17 = 0; n17 < ((int[])object4).length / 2; ++n17) {
                        nArray[n18++] = n5 + object4[2 * n17];
                        nArray[n18++] = n6 + object4[2 * n17 + 1];
                    }
                    for (n17 = 0; n17 < ((Object)object2).length / 2; ++n17) {
                        nArray[n18++] = this.parent.simple ? n13 - 1 + object2[2 * n17] : n13 - this.curveIndent + object2[2 * n17];
                        nArray[n18++] = n6 + object2[2 * n17 + 1];
                    }
                    nArray[n18++] = this.parent.simple ? n13 - 1 : n13 + this.curveWidth - this.curveIndent;
                    nArray[n18++] = n6 + n7 + 1;
                    nArray[n18++] = this.parent.simple ? n13 - 1 : n13 + this.curveWidth - this.curveIndent;
                    nArray[n18++] = n6 + n7 + 1;
                }
                object4 = gC.getClipping();
                object = object2 = cTabItem.getBounds();
                ++((Rectangle)object2).height;
                if (this.parent.onBottom) {
                    Object object5 = object;
                    --((Rectangle)object5).y;
                }
                if (bl = ((Rectangle)object4).intersects((Rectangle)object)) {
                    if (this.parent.selectionGradientColors != null && !this.parent.selectionGradientVertical) {
                        this.drawBackground(gC, nArray, true);
                    } else {
                        Color color = this.parent.shouldHighlight() ? this.parent.selectionBackground : this.parent.getBackground();
                        object3 = this.parent.selectionBgImage;
                        Color[] colorArray = this.parent.selectionGradientColors;
                        int[] nArray2 = this.parent.selectionGradientPercents;
                        boolean bl2 = this.parent.selectionGradientVertical;
                        n4 = n5;
                        n15 = this.parent.onBottom ? n6 - 1 : n6 + 1;
                        n16 = n8;
                        n3 = n7;
                        if (!this.parent.single && !this.parent.simple) {
                            n16 += this.curveWidth - this.curveIndent;
                        }
                        this.drawBackground(gC, nArray, n4, n15, n16, n3, color, (Image)object3, colorArray, nArray2, bl2);
                    }
                }
                this.drawHighlight(gC, rectangle, n2, n13);
                nArray[0] = Math.max(0, n10 - 1);
                if (n10 == 0 && n == this.parent.firstIndex) {
                    nArray[1] = this.parent.onBottom ? n6 + n7 - 1 : n6;
                    nArray[5] = nArray[3] = nArray[1];
                }
                nArray[nArray.length - 2] = point.x - n9 + 1;
                for (int i = 0; i < nArray.length / 2; ++i) {
                    if (nArray[2 * i + 1] != n6 + n7 + 1) continue;
                    object3 = nArray;
                    int n22 = 2 * i + 1;
                    Object object6 = object3;
                    int n23 = n22;
                    object6[n23] = object6[n23] - true;
                }
                Color color = this.parent.getDisplay().getSystemColor(18);
                if (!color.equals(this.lastBorderColor)) {
                    this.createAntialiasColors();
                }
                this.antialias(nArray, this.selectedInnerColor, this.selectedOuterColor, gC);
                gC.setForeground(color);
                gC.drawPolyline(nArray);
                if (!bl) {
                    return;
                }
            }
        }
        if ((n2 & 0x10) != 0) {
            Image image;
            Rectangle rectangle2 = this.computeTrim(n, 0, 0, 0, 0, 0);
            n4 = n5 - rectangle2.x;
            if (this.parent.single && (this.parent.showClose || cTabItem.showClose)) {
                n4 += cTabItem.closeRect.width;
            }
            if ((image = cTabItem.getImage()) != null && !image.isDisposed()) {
                Rectangle rectangle3 = image.getBounds();
                n3 = n13 - n4 - (rectangle2.width + rectangle2.x);
                if (!this.parent.single && cTabItem.closeRect.width > 0) {
                    n3 -= cTabItem.closeRect.width + 4;
                }
                if (rectangle3.width < n3) {
                    int n24 = n4;
                    int n25 = n6 + (n7 - rectangle3.height) / 2;
                    gC.drawImage(image, n24, n25 += this.parent.onBottom ? -1 : 1);
                    n4 += rectangle3.width + 4;
                }
            }
            int n26 = n13 - n4 - (rectangle2.width + rectangle2.x);
            if (!this.parent.single && cTabItem.closeRect.width > 0) {
                n26 -= cTabItem.closeRect.width + 4;
            }
            if (n26 > 0) {
                Font font = gC.getFont();
                gC.setFont(cTabItem.font == null ? this.parent.getFont() : cTabItem.font);
                if (cTabItem.shortenedText == null || cTabItem.shortenedTextWidth != n26) {
                    cTabItem.shortenedText = this.shortenText(gC, cTabItem.getText(), n26);
                    cTabItem.shortenedTextWidth = n26;
                }
                Point point2 = gC.textExtent(cTabItem.shortenedText, 11);
                int n27 = n6 + (n7 - point2.y) / 2;
                int n28 = this.parent.onBottom ? -1 : 1;
                gC.setForeground(cTabItem.selectionForeground == null ? this.parent.getSelectionForeground() : cTabItem.selectionForeground);
                gC.drawText(cTabItem.shortenedText, n4, n27 += n28, 11);
                gC.setFont(font);
                if (this.parent.isFocusControl()) {
                    object2 = gC.getForeground();
                    object = gC.getBackground();
                    Display display = this.parent.getDisplay();
                    if (this.parent.simple || this.parent.single) {
                        gC.setBackground(display.getSystemColor(2));
                        gC.setForeground(display.getSystemColor(1));
                        gC.drawFocus(n4 - 1, n27 - 1, point2.x + 2, point2.y + 2);
                    } else {
                        gC.setForeground(display.getSystemColor(17));
                        gC.drawLine(n4, n27 + point2.y + 1, n4 + point2.x + 1, n27 + point2.y + 1);
                    }
                    gC.setForeground((Color)object2);
                    gC.setBackground((Color)object);
                }
            }
            if (this.parent.showClose || cTabItem.showClose) {
                this.drawClose(gC, cTabItem.closeRect, cTabItem.closeImageState);
            }
        }
    }

    void drawTabArea(GC gC, Rectangle rectangle, int n) {
        int n2;
        int n3;
        Point point = this.parent.getSize();
        int[] nArray = null;
        Color color = this.parent.getDisplay().getSystemColor(18);
        int n4 = this.parent.tabHeight;
        int n5 = this.parent.getStyle();
        int n6 = this.parent.borderVisible ? 1 : 0;
        int n7 = n6;
        int n8 = this.parent.onBottom ? n7 : 0;
        int n9 = this.parent.onBottom ? 0 : n7;
        int n10 = this.parent.selectedIndex;
        int n11 = n3 = (n5 & 0x800000) != 0 ? 1 : 3;
        if (n4 != 0) {
            int n12;
            int n13;
            int n14;
            int[] nArray2;
            int[] nArray3;
            int n15 = Math.max(0, n7 - 1);
            int n16 = this.parent.onBottom ? point.y - n9 - n4 : n8;
            int n17 = point.x - n7 - n6 + 1;
            int n18 = n4 - 1;
            boolean bl = this.parent.simple;
            if (this.parent.onBottom) {
                int[] nArray4;
                if ((n5 & 0x800) != 0) {
                    nArray3 = bl ? SIMPLE_BOTTOM_LEFT_CORNER : BOTTOM_LEFT_CORNER;
                    nArray2 = bl ? SIMPLE_BOTTOM_RIGHT_CORNER : BOTTOM_RIGHT_CORNER;
                } else {
                    nArray3 = bl ? SIMPLE_BOTTOM_LEFT_CORNER_BORDERLESS : BOTTOM_LEFT_CORNER_BORDERLESS;
                    nArray2 = bl ? SIMPLE_BOTTOM_RIGHT_CORNER_BORDERLESS : BOTTOM_RIGHT_CORNER_BORDERLESS;
                }
                nArray = new int[nArray3.length + nArray2.length + 4];
                n14 = 0;
                nArray[n14++] = n15;
                nArray[n14++] = n16 - n3;
                for (n13 = 0; n13 < nArray3.length / 2; ++n13) {
                    nArray[n14++] = n15 + nArray3[2 * n13];
                    nArray[n14++] = n16 + n18 + nArray3[2 * n13 + 1];
                    if (n7 != 0) continue;
                    nArray4 = nArray;
                    int n19 = n12 = n14 - 1;
                    nArray4[n19] = nArray4[n19] + 1;
                }
                for (n13 = 0; n13 < nArray2.length / 2; ++n13) {
                    nArray[n14++] = n15 + n17 + nArray2[2 * n13];
                    nArray[n14++] = n16 + n18 + nArray2[2 * n13 + 1];
                    if (n7 != 0) continue;
                    nArray4 = nArray;
                    int n20 = n12 = n14 - 1;
                    nArray4[n20] = nArray4[n20] + 1;
                }
                nArray[n14++] = n15 + n17;
                nArray[n14++] = n16 - n3;
            } else {
                if ((n5 & 0x800) != 0) {
                    nArray3 = bl ? SIMPLE_TOP_LEFT_CORNER : TOP_LEFT_CORNER;
                    nArray2 = bl ? SIMPLE_TOP_RIGHT_CORNER : TOP_RIGHT_CORNER;
                } else {
                    nArray3 = bl ? SIMPLE_TOP_LEFT_CORNER_BORDERLESS : TOP_LEFT_CORNER_BORDERLESS;
                    nArray2 = bl ? SIMPLE_TOP_RIGHT_CORNER_BORDERLESS : TOP_RIGHT_CORNER_BORDERLESS;
                }
                nArray = new int[nArray3.length + nArray2.length + 4];
                n14 = 0;
                nArray[n14++] = n15;
                nArray[n14++] = n16 + n18 + n3 + 1;
                for (n13 = 0; n13 < nArray3.length / 2; ++n13) {
                    nArray[n14++] = n15 + nArray3[2 * n13];
                    nArray[n14++] = n16 + nArray3[2 * n13 + 1];
                }
                for (n13 = 0; n13 < nArray2.length / 2; ++n13) {
                    nArray[n14++] = n15 + n17 + nArray2[2 * n13];
                    nArray[n14++] = n16 + nArray2[2 * n13 + 1];
                }
                nArray[n14++] = n15 + n17;
                nArray[n14++] = n16 + n18 + n3 + 1;
            }
            boolean bl2 = this.parent.single;
            boolean bl3 = bl2 && n10 != -1;
            this.drawBackground(gC, nArray, bl3);
            Region region = new Region();
            region.add(new Rectangle(n15, n16, n17 + 1, n18 + 1));
            region.subtract(nArray);
            gC.setBackground(this.parent.getParent().getBackground());
            this.fillRegion(gC, region);
            region.dispose();
            if (n10 == -1) {
                n13 = n7;
                int n21 = this.parent.onBottom ? point.y - n9 - n4 - 1 : n8 + n4;
                n12 = point.x - n6;
                gC.setForeground(color);
                gC.drawLine(n13, n21, n12, n21);
            }
            if (n7 > 0) {
                if (!color.equals(this.lastBorderColor)) {
                    this.createAntialiasColors();
                }
                this.antialias(nArray, null, this.tabAreaColor, gC);
                gC.setForeground(color);
                gC.drawPolyline(nArray);
            }
            return;
        }
        if ((n5 & 0x800000) != 0 && (n5 & 0x800) == 0) {
            return;
        }
        int n22 = n7 - 1;
        int n23 = point.x - n6;
        int n24 = this.parent.onBottom ? point.y - n9 - n3 - 1 : n8 + n3;
        int n25 = n2 = this.parent.onBottom ? point.y - n9 : n8;
        if (n7 <= 0 || this.parent.onBottom) {
            // empty if block
        }
        nArray = new int[]{n22, n24, n22, --n2, n23, n2, n23, n24};
        if (n10 != -1 && this.parent.selectionGradientColors != null && this.parent.selectionGradientColors.length > 1 && !this.parent.selectionGradientVertical) {
            this.drawBackground(gC, nArray, true);
        } else if (n10 == -1 && this.parent.gradientColors != null && this.parent.gradientColors.length > 1 && !this.parent.gradientVertical) {
            this.drawBackground(gC, nArray, false);
        } else {
            gC.setBackground(n10 != -1 && this.parent.shouldHighlight() ? this.parent.selectionBackground : this.parent.getBackground());
            gC.fillPolygon(nArray);
        }
        if (n7 > 0) {
            gC.setForeground(color);
            gC.drawPolyline(nArray);
        }
    }

    void drawUnselected(int n, GC gC, Rectangle rectangle, int n2) {
        CTabItem cTabItem = this.parent.items[n];
        int n3 = rectangle.x;
        int n4 = rectangle.y;
        int n5 = rectangle.height;
        int n6 = rectangle.width;
        if (!cTabItem.showing) {
            return;
        }
        Rectangle rectangle2 = gC.getClipping();
        if (!rectangle2.intersects(rectangle)) {
            return;
        }
        if ((n2 & 8) != 0) {
            if (n > 0 && n < this.parent.selectedIndex) {
                this.drawLeftUnselectedBorder(gC, rectangle, n2);
            }
            if (n > this.parent.selectedIndex) {
                this.drawRightUnselectedBorder(gC, rectangle, n2);
            }
        }
        if ((n2 & 0x10) != 0) {
            int n7;
            Rectangle rectangle3 = this.computeTrim(n, 0, 0, 0, 0, 0);
            int n8 = n3 - rectangle3.x;
            Image image = cTabItem.getImage();
            if (image != null && !image.isDisposed() && this.parent.showUnselectedImage) {
                Rectangle rectangle4 = image.getBounds();
                int n9 = n3 + n6 - n8 - (rectangle3.width + rectangle3.x);
                if (this.parent.showUnselectedClose && (this.parent.showClose || cTabItem.showClose)) {
                    n9 -= cTabItem.closeRect.width + 4;
                }
                if (rectangle4.width < n9) {
                    int n10 = n8;
                    n7 = rectangle4.height;
                    int n11 = n4 + (n5 - n7) / 2;
                    int n12 = this.parent.onBottom ? -1 : 1;
                    int n13 = rectangle4.width * n7 / rectangle4.height;
                    gC.drawImage(image, rectangle4.x, rectangle4.y, rectangle4.width, rectangle4.height, n10, n11 += n12, n13, n7);
                    n8 += n13 + 4;
                }
            }
            int n14 = n3 + n6 - n8 - (rectangle3.width + rectangle3.x);
            if (this.parent.showUnselectedClose && (this.parent.showClose || cTabItem.showClose)) {
                n14 -= cTabItem.closeRect.width + 4;
            }
            if (n14 > 0) {
                Font font = gC.getFont();
                gC.setFont(cTabItem.font == null ? this.parent.getFont() : cTabItem.font);
                if (cTabItem.shortenedText == null || cTabItem.shortenedTextWidth != n14) {
                    cTabItem.shortenedText = this.shortenText(gC, cTabItem.getText(), n14);
                    cTabItem.shortenedTextWidth = n14;
                }
                Point point = gC.textExtent(cTabItem.shortenedText, 11);
                n7 = n4 + (n5 - point.y) / 2;
                int n15 = this.parent.onBottom ? -1 : 1;
                gC.setForeground(cTabItem.foreground == null ? this.parent.getForeground() : cTabItem.foreground);
                gC.drawText(cTabItem.shortenedText, n8, n7 += n15, 11);
                gC.setFont(font);
            }
            if (this.parent.showUnselectedClose && (this.parent.showClose || cTabItem.showClose)) {
                this.drawClose(gC, cTabItem.closeRect, cTabItem.closeImageState);
            }
        }
    }

    void fillRegion(GC gC, Region region) {
        Region region2 = new Region();
        gC.getClipping(region2);
        region.intersect(region2);
        gC.setClipping(region);
        gC.fillRectangle(region.getBounds());
        gC.setClipping(region2);
        region2.dispose();
    }

    Color getFillColor() {
        if (this.fillColor == null) {
            this.fillColor = new Color(CLOSE_FILL);
        }
        return this.fillColor;
    }

    private Font getChevronFont(Display display) {
        if (this.chevronFont == null) {
            Point point = display.getDPI();
            int n = 720 / point.y;
            FontData fontData = this.parent.getFont().getFontData()[0];
            fontData.setHeight(n);
            this.chevronFont = new Font((Device)display, fontData);
        }
        return this.chevronFont;
    }

    void resetChevronFont() {
        if (this.chevronFont != null) {
            this.chevronFont.dispose();
            this.chevronFont = null;
        }
    }

    void setSelectionHighlightGradientColor(Color color) {
        this.selectionHighlightGradientBegin = null;
        if (color == null) {
            return;
        }
        if (this.parent.getDisplay().getDepth() < 15) {
            return;
        }
        if (this.parent.selectionGradientColors.length < 2) {
            return;
        }
        this.selectionHighlightGradientBegin = color;
        CTabFolderRenderer cTabFolderRenderer = this;
        if (color == null) {
            this.createSelectionHighlightGradientColors(color);
        }
    }

    String shortenText(GC gC, String string, int n) {
        return this.useEllipses() ? this.shortenText(gC, string, n, ELLIPSIS) : this.shortenText(gC, string, n, "");
    }

    String shortenText(GC gC, String string, int n, String string2) {
        if (gC.textExtent((String)string, (int)11).x <= n) {
            return string;
        }
        int n2 = gC.textExtent((String)string2, (int)11).x;
        int n3 = string.length();
        TextLayout textLayout = new TextLayout(this.parent.getDisplay());
        textLayout.setText(string);
        int n4 = textLayout.getPreviousOffset(n3, 2);
        while (n4 > 0) {
            string = string.substring(0, n4);
            int n5 = gC.textExtent((String)string, (int)11).x;
            if (n5 + n2 <= n) break;
            n4 = textLayout.getPreviousOffset(n4, 2);
        }
        textLayout.dispose();
        return n4 == 0 ? string.substring(0, 1) : string + string2;
    }

    void updateCurves() {
        if (this.getClass().getName().equals("org.eclipse.e4.ui.workbench.renderers.swt.CTabRendering")) {
            return;
        }
        int n = this.parent.tabHeight;
        if (n == this.lastTabHeight) {
            return;
        }
        this.lastTabHeight = n;
        if (this.parent.onBottom) {
            int n2 = n - 12;
            this.curve = new int[]{0, 13 + n2, 0, 12 + n2, 2, 12 + n2, 3, 11 + n2, 5, 11 + n2, 6, 10 + n2, 7, 10 + n2, 9, 8 + n2, 10, 8 + n2, 11, 7 + n2, 11 + n2, 7, 12 + n2, 6, 13 + n2, 6, 15 + n2, 4, 16 + n2, 4, 17 + n2, 3, 19 + n2, 3, 20 + n2, 2, 22 + n2, 2, 23 + n2, 1};
            this.curveWidth = 26 + n2;
            this.curveIndent = this.curveWidth / 3;
        } else {
            int n3 = n - 12;
            this.curve = new int[]{0, 0, 0, 1, 2, 1, 3, 2, 5, 2, 6, 3, 7, 3, 9, 5, 10, 5, 11, 6, 11 + n3, 6 + n3, 12 + n3, 7 + n3, 13 + n3, 7 + n3, 15 + n3, 9 + n3, 16 + n3, 9 + n3, 17 + n3, 10 + n3, 19 + n3, 10 + n3, 20 + n3, 11 + n3, 22 + n3, 11 + n3, 23 + n3, 12 + n3};
            this.curveWidth = 26 + n3;
            this.curveIndent = this.curveWidth / 3;
            this.topCurveHighlightStart = new int[]{0, 2, 1, 2, 2, 2, 3, 3, 4, 3, 5, 3, 6, 4, 7, 4, 8, 5, 9, 6, 10, 6};
            this.topCurveHighlightEnd = new int[]{10 + n3, 6 + n3, 11 + n3, 7 + n3, 12 + n3, 8 + n3, 13 + n3, 8 + n3, 14 + n3, 9 + n3, 15 + n3, 10 + n3, 16 + n3, 10 + n3, 17 + n3, 11 + n3, 18 + n3, 11 + n3, 19 + n3, 11 + n3, 20 + n3, 12 + n3, 21 + n3, 12 + n3, 22 + n3, 12 + n3};
        }
    }

    boolean useEllipses() {
        return this.parent.simple;
    }
}

