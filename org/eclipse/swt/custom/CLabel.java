/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.custom.llIII;
import org.eclipse.swt.custom.lll;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

public class CLabel
extends Canvas {
    private static final int GAP = 5;
    private static final int DEFAULT_MARGIN = 3;
    private static final String ELLIPSIS = "...";
    private int align = 16384;
    private int leftMargin = 3;
    private int topMargin = 3;
    private int rightMargin = 3;
    private int bottomMargin = 3;
    private String text;
    private Image image;
    private String appToolTipText;
    private boolean ignoreDispose;
    private Image backgroundImage;
    private Color[] gradientColors;
    private int[] gradientPercents;
    private boolean gradientVertical;
    private Color background;
    private static int DRAW_FLAGS = 15;

    public CLabel(Composite composite, int n) {
        super(composite, CLabel.checkStyle(n));
        if ((n & 0x1020000) == 0) {
            n |= 0x4000;
        }
        if ((n & 0x1000000) != 0) {
            this.align = 0x1000000;
        }
        if ((n & 0x20000) != 0) {
            this.align = 131072;
        }
        if ((n & 0x4000) != 0) {
            this.align = 16384;
        }
        this.addPaintListener(this::onPaint);
        this.addTraverseListener(this::lambda$new$0);
        this.addListener(12, this::onDispose);
        this.initAccessible();
    }

    private static int checkStyle(int n) {
        if ((n & 0x800) != 0) {
            n |= 4;
        }
        int n2 = 100663340;
        n &= 0x600002C;
        return n |= 0x20080000;
    }

    @Override
    public Point computeSize(int n, int n2, boolean bl) {
        Point point;
        this.checkWidget();
        Point point2 = this.getTotalSize(this.image, this.text);
        if (n == -1) {
            point = point2;
            point.x += this.leftMargin + this.rightMargin;
        } else {
            point2.x = n;
        }
        if (n2 == -1) {
            point = point2;
            point.y += this.topMargin + this.bottomMargin;
        } else {
            point2.y = n2;
        }
        return point2;
    }

    private void drawBevelRect(GC gC, int n, int n2, int n3, int n4, Color color, Color color2) {
        gC.setForeground(color2);
        gC.drawLine(n + n3, n2, n + n3, n2 + n4);
        gC.drawLine(n, n2 + n4, n + n3, n2 + n4);
        gC.setForeground(color);
        gC.drawLine(n, n2, n + n3 - 1, n2);
        gC.drawLine(n, n2, n, n2 + n4 - 1);
    }

    char _findMnemonic(String string) {
        if (string == null) {
            return '\u0000';
        }
        int n = 0;
        int n2 = string.length();
        while (true) {
            if (n < n2 && string.charAt(n) != '&') {
                ++n;
                continue;
            }
            if (++n >= n2) {
                return '\u0000';
            }
            if (string.charAt(n) != '&') {
                return Character.toLowerCase(string.charAt(n));
            }
            if (++n >= n2) break;
        }
        return '\u0000';
    }

    public int getAlignment() {
        return this.align;
    }

    public int getBottomMargin() {
        return this.bottomMargin;
    }

    public Image getImage() {
        return this.image;
    }

    public int getLeftMargin() {
        return this.leftMargin;
    }

    public int getRightMargin() {
        return this.rightMargin;
    }

    private Point getTotalSize(Image image, String string) {
        Point point;
        Point point2;
        Object object;
        Point point3 = new Point(0, 0);
        if (image != null) {
            object = image.getBounds();
            point2 = point3;
            point2.x += ((Rectangle)object).width;
            point = point3;
            point.y += ((Rectangle)object).height;
        }
        object = new GC(this);
        if (string != null && string.length() > 0) {
            point2 = ((GC)object).textExtent(string, DRAW_FLAGS);
            point = point3;
            point.x += point2.x;
            point3.y = Math.max(point3.y, point2.y);
            if (image != null) {
                Point point4 = point3;
                point4.x += 5;
            }
        } else {
            point3.y = Math.max(point3.y, ((GC)object).getFontMetrics().getHeight());
        }
        ((Resource)object).dispose();
        return point3;
    }

    @Override
    public int getStyle() {
        int n = super.getStyle();
        switch (this.align) {
            case 131072: {
                n |= 0x20000;
                break;
            }
            case 0x1000000: {
                n |= 0x1000000;
                break;
            }
            case 16384: {
                n |= 0x4000;
            }
        }
        return n;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String getToolTipText() {
        this.checkWidget();
        return this.appToolTipText;
    }

    public int getTopMargin() {
        return this.topMargin;
    }

    private void initAccessible() {
        Accessible accessible = this.getAccessible();
        accessible.addAccessibleListener(new llIII(this));
        accessible.addAccessibleControlListener(new lll(this));
    }

    void onDispose(Event event) {
        if (this.ignoreDispose) {
            this.ignoreDispose = false;
            return;
        }
        this.ignoreDispose = true;
        this.notifyListeners(event.type, event);
        event.type = 0;
        this.gradientColors = null;
        this.gradientPercents = null;
        this.backgroundImage = null;
        this.text = null;
        this.image = null;
        this.appToolTipText = null;
    }

    void onMnemonic(TraverseEvent traverseEvent) {
        char c = this._findMnemonic(this.text);
        if (c == '\u0000') {
            return;
        }
        if (Character.toLowerCase(traverseEvent.character) != c) {
            return;
        }
        for (Composite composite = this.getParent(); composite != null; composite = composite.getParent()) {
            int n;
            Control[] controlArray = composite.getChildren();
            for (n = 0; n < controlArray.length && controlArray[n] != this; ++n) {
            }
            if (++n >= controlArray.length || !controlArray[n].setFocus()) continue;
            traverseEvent.doit = true;
            traverseEvent.detail = 0;
        }
    }

    void onPaint(PaintEvent paintEvent) {
        int n;
        int n2;
        int n3;
        int n4;
        String[] stringArray;
        GC gC;
        Point point;
        Image image;
        Rectangle rectangle;
        block39: {
            Object object;
            rectangle = this.getClientArea();
            if (rectangle.width == 0 || rectangle.height == 0) {
                return;
            }
            boolean bl = false;
            String string = this.text;
            image = this.image;
            int n5 = Math.max(0, rectangle.width - (this.leftMargin + this.rightMargin));
            point = this.getTotalSize(image, string);
            if (point.x > n5) {
                image = null;
                point = this.getTotalSize(image, string);
                if (point.x > n5) {
                    bl = true;
                }
            }
            gC = paintEvent.gc;
            stringArray = this.text == null ? null : this.splitString(this.text);
            if (bl) {
                point.x = 0;
                for (n4 = 0; n4 < stringArray.length; ++n4) {
                    object = gC.textExtent(stringArray[n4], DRAW_FLAGS);
                    if (((Point)object).x > n5) {
                        stringArray[n4] = this.shortenText(gC, stringArray[n4], n5);
                        point.x = Math.max(point.x, this.getTotalSize(null, (String)stringArray[n4]).x);
                        continue;
                    }
                    point.x = Math.max(point.x, ((Point)object).x);
                }
                if (this.appToolTipText == null) {
                    super.setToolTipText(this.text);
                }
            } else {
                super.setToolTipText(this.appToolTipText);
            }
            n4 = rectangle.x + this.leftMargin;
            if (this.align == 0x1000000) {
                n4 = (rectangle.width - point.x) / 2;
            }
            if (this.align == 131072) {
                n4 = rectangle.width - this.rightMargin - point.x;
            }
            try {
                if (this.backgroundImage != null) {
                    object = this.backgroundImage.getBounds();
                    gC.setBackground(this.getBackground());
                    gC.fillRectangle(rectangle);
                    for (int i = 0; i < rectangle.width; i += ((Rectangle)object).width) {
                        for (int j = 0; j < rectangle.height; j += ((Rectangle)object).height) {
                            gC.drawImage(this.backgroundImage, i, j);
                        }
                    }
                } else if (this.gradientColors != null) {
                    object = gC.getBackground();
                    if (this.gradientColors.length == 1) {
                        if (this.gradientColors[0] != null) {
                            gC.setBackground(this.gradientColors[0]);
                        }
                        gC.fillRectangle(0, 0, rectangle.width, rectangle.height);
                    } else {
                        Color color = gC.getForeground();
                        Object object2 = this.gradientColors[0];
                        if (object2 == null) {
                            object2 = object;
                        }
                        n3 = 0;
                        for (n2 = 0; n2 < this.gradientPercents.length; ++n2) {
                            gC.setForeground((Color)object2);
                            object2 = this.gradientColors[n2 + 1];
                            if (object2 == null) {
                                object2 = object;
                            }
                            gC.setBackground((Color)object2);
                            if (this.gradientVertical) {
                                n = this.gradientPercents[n2] * rectangle.height / 100 - n3;
                                gC.fillGradientRectangle(0, n3, rectangle.width, n, true);
                                n3 += n;
                                continue;
                            }
                            n = this.gradientPercents[n2] * rectangle.width / 100 - n3;
                            gC.fillGradientRectangle(n3, 0, n, rectangle.height, false);
                            n3 += n;
                        }
                        if (this.gradientVertical && n3 < rectangle.height) {
                            gC.setBackground(this.getBackground());
                            gC.fillRectangle(0, n3, rectangle.width, rectangle.height - n3);
                        }
                        if (!this.gradientVertical && n3 < rectangle.width) {
                            gC.setBackground(this.getBackground());
                            gC.fillRectangle(n3, 0, rectangle.width - n3, rectangle.height);
                        }
                        gC.setForeground(color);
                    }
                    gC.setBackground((Color)object);
                } else if ((this.background != null || (this.getStyle() & 0x20000000) == 0) && this.background.getAlpha() > 0) {
                    gC.setBackground(this.getBackground());
                    gC.fillRectangle(rectangle);
                }
            }
            catch (SWTException sWTException) {
                if ((this.getStyle() & 0x20000000) != 0) break block39;
                gC.setBackground(this.getBackground());
                gC.fillRectangle(rectangle);
            }
        }
        int n6 = this.getStyle();
        if ((n6 & 4) != 0 || (n6 & 8) != 0) {
            this.paintBorder(gC, rectangle);
        }
        Rectangle rectangle2 = null;
        int n7 = 0;
        n3 = 0;
        n2 = 0;
        if (image != null) {
            rectangle2 = image.getBounds();
            n2 = rectangle2.height;
        }
        if (stringArray != null) {
            n7 = gC.getFontMetrics().getHeight();
            n3 = stringArray.length * n7;
        }
        n = 0;
        int n8 = 0;
        int n9 = 0;
        if (n2 > n3) {
            n = this.topMargin == 3 && this.bottomMargin == 3 ? rectangle.y + (rectangle.height - n2) / 2 : this.topMargin;
            n8 = n + n2 / 2;
            n9 = n8 - n3 / 2;
        } else {
            n9 = this.topMargin == 3 && this.bottomMargin == 3 ? rectangle.y + (rectangle.height - n3) / 2 : this.topMargin;
            n8 = n9 + n3 / 2;
            n = n8 - n2 / 2;
        }
        if (image != null) {
            gC.drawImage(image, 0, 0, rectangle2.width, n2, n4, n, rectangle2.width, n2);
            n4 += rectangle2.width + 5;
            Point object = point;
            object.x -= rectangle2.width + 5;
        }
        if (stringArray != null) {
            gC.setForeground(this.getForeground());
            for (String string : stringArray) {
                int n5 = n4;
                if (stringArray.length > 1) {
                    int n10;
                    if (this.align == 0x1000000) {
                        n10 = gC.textExtent((String)string, (int)CLabel.DRAW_FLAGS).x;
                        n5 = n4 + Math.max(0, (point.x - n10) / 2);
                    }
                    if (this.align == 131072) {
                        n10 = gC.textExtent((String)string, (int)CLabel.DRAW_FLAGS).x;
                        n5 = Math.max(n4, rectangle.x + rectangle.width - this.rightMargin - n10);
                    }
                }
                gC.drawText(string, n5, n9, DRAW_FLAGS);
                n9 += n7;
            }
        }
    }

    private void paintBorder(GC gC, Rectangle rectangle) {
        Display display = this.getDisplay();
        Color color = null;
        Color color2 = null;
        int n = this.getStyle();
        if ((n & 4) != 0) {
            color = display.getSystemColor(18);
            color2 = display.getSystemColor(20);
        }
        if ((n & 8) != 0) {
            color = display.getSystemColor(19);
            color2 = display.getSystemColor(18);
        }
        if (color != null && color2 != null) {
            gC.setLineWidth(1);
            this.drawBevelRect(gC, rectangle.x, rectangle.y, rectangle.width - 1, rectangle.height - 1, color, color2);
        }
    }

    public void setAlignment(int n) {
        this.checkWidget();
        if (n != 16384 && n != 131072 && n != 0x1000000) {
            SWT.error(5);
        }
        if (this.align != n) {
            this.align = n;
            this.redraw();
        }
    }

    @Override
    public void setBackground(Color color) {
        super.setBackground(color);
        if (this.backgroundImage == null && this.gradientColors == null && this.gradientPercents == null && (color == null ? this.background == null : color.equals(this.background))) {
            return;
        }
        this.background = color;
        this.backgroundImage = null;
        this.gradientColors = null;
        this.gradientPercents = null;
        this.redraw();
    }

    public void setBackground(Color[] colorArray, int[] nArray) {
        this.setBackground(colorArray, nArray, false);
    }

    public void setBackground(Color[] colorArray, int[] nArray, boolean bl) {
        int n;
        this.checkWidget();
        if (colorArray != null) {
            if (nArray == null || nArray.length != colorArray.length - 1) {
                SWT.error(5);
            }
            if (this.getDisplay().getDepth() < 15) {
                colorArray = new Color[]{colorArray[colorArray.length - 1]};
                nArray = new int[]{};
            }
            for (int i = 0; i < nArray.length; ++i) {
                if (nArray[i] < 0 || nArray[i] > 100) {
                    SWT.error(5);
                }
                if (i <= 0 || nArray[i] >= nArray[i - 1]) continue;
                SWT.error(5);
            }
        }
        Color color = this.getBackground();
        if (this.backgroundImage == null) {
            if (this.gradientColors != null && colorArray != null && this.gradientColors.length == colorArray.length) {
                int n2;
                n = 0;
                for (n2 = 0; n2 < this.gradientColors.length; ++n2) {
                    int n3 = n = this.gradientColors[n2] == colorArray[n2] || this.gradientColors[n2] == null && colorArray[n2] == color || this.gradientColors[n2] == color && colorArray[n2] == null ? 1 : 0;
                    if (n == 0) break;
                }
                if (n != 0) {
                    for (n2 = 0; n2 < this.gradientPercents.length; ++n2) {
                        int n4 = n = this.gradientPercents[n2] == nArray[n2] ? 1 : 0;
                        if (n == 0) break;
                    }
                }
                if (n != 0 && this.gradientVertical == bl) {
                    return;
                }
            }
        } else {
            this.backgroundImage = null;
        }
        if (colorArray == null) {
            this.gradientColors = null;
            this.gradientPercents = null;
            this.gradientVertical = false;
        } else {
            this.gradientColors = new Color[colorArray.length];
            for (n = 0; n < colorArray.length; ++n) {
                this.gradientColors[n] = colorArray[n] != null ? colorArray[n] : color;
            }
            this.gradientPercents = new int[nArray.length];
            for (n = 0; n < nArray.length; ++n) {
                this.gradientPercents[n] = nArray[n];
            }
            this.gradientVertical = bl;
        }
        this.redraw();
    }

    public void setBackground(Image image) {
        this.checkWidget();
        if (image == this.backgroundImage) {
            return;
        }
        if (image != null) {
            this.gradientColors = null;
            this.gradientPercents = null;
        }
        this.backgroundImage = image;
        this.redraw();
    }

    public void setBottomMargin(int n) {
        this.checkWidget();
        if (this.bottomMargin == n || n < 0) {
            return;
        }
        this.bottomMargin = n;
        this.redraw();
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        this.redraw();
    }

    public void setImage(Image image) {
        this.checkWidget();
        if (image != this.image) {
            this.image = image;
            this.redraw();
        }
    }

    public void setLeftMargin(int n) {
        this.checkWidget();
        if (this.leftMargin == n || n < 0) {
            return;
        }
        this.leftMargin = n;
        this.redraw();
    }

    public void setMargins(int n, int n2, int n3, int n4) {
        this.checkWidget();
        this.leftMargin = Math.max(0, n);
        this.topMargin = Math.max(0, n2);
        this.rightMargin = Math.max(0, n3);
        this.bottomMargin = Math.max(0, n4);
        this.redraw();
    }

    public void setRightMargin(int n) {
        this.checkWidget();
        if (this.rightMargin == n || n < 0) {
            return;
        }
        this.rightMargin = n;
        this.redraw();
    }

    public void setText(String string) {
        this.checkWidget();
        if (string == null) {
            string = "";
        }
        if (!string.equals(this.text)) {
            this.text = string;
            this.redraw();
        }
    }

    @Override
    public void setToolTipText(String string) {
        super.setToolTipText(string);
        this.appToolTipText = super.getToolTipText();
    }

    public void setTopMargin(int n) {
        this.checkWidget();
        if (this.topMargin == n || n < 0) {
            return;
        }
        this.topMargin = n;
        this.redraw();
    }

    protected String shortenText(GC gC, String string, int n) {
        String string2;
        int n2;
        if (string == null) {
            return null;
        }
        int n3 = gC.textExtent((String)ELLIPSIS, (int)CLabel.DRAW_FLAGS).x;
        if (n <= n3) {
            return string;
        }
        int n4 = string.length();
        int n5 = n4 / 2;
        int n6 = (n5 + (n2 = 0)) / 2 - 1;
        if (n6 <= 0) {
            return string;
        }
        TextLayout textLayout = new TextLayout(this.getDisplay());
        textLayout.setText(string);
        n6 = this.validateOffset(textLayout, n6);
        while (n2 < n6 && n6 < n5) {
            string2 = string.substring(0, n6);
            String string3 = string.substring(this.validateOffset(textLayout, n4 - n6), n4);
            int n7 = gC.textExtent((String)string2, (int)CLabel.DRAW_FLAGS).x;
            int n8 = gC.textExtent((String)string3, (int)CLabel.DRAW_FLAGS).x;
            if (n7 + n3 + n8 > n) {
                n5 = n6;
                n6 = this.validateOffset(textLayout, (n5 + n2) / 2);
                continue;
            }
            if (n7 + n3 + n8 < n) {
                n2 = n6;
                n6 = this.validateOffset(textLayout, (n5 + n2) / 2);
                continue;
            }
            n2 = n5;
        }
        string2 = n6 == 0 ? string : string.substring(0, n6) + ELLIPSIS + string.substring(this.validateOffset(textLayout, n4 - n6), n4);
        textLayout.dispose();
        return string2;
    }

    int validateOffset(TextLayout textLayout, int n) {
        int n2 = textLayout.getNextOffset(n, 2);
        if (n2 != n) {
            return textLayout.getPreviousOffset(n2, 2);
        }
        return n;
    }

    private String[] splitString(String string) {
        int n;
        String[] stringArray = new String[]{null};
        int n2 = 0;
        do {
            if ((n = string.indexOf(10, n2)) == -1) {
                stringArray[stringArray.length - 1] = string.substring(n2);
                continue;
            }
            boolean bl = n > 0 && string.charAt(n - 1) == '\r';
            stringArray[stringArray.length - 1] = string.substring(n2, n - (bl ? 1 : 0));
            n2 = n + 1;
            String[] stringArray2 = new String[stringArray.length + 1];
            System.arraycopy(stringArray, 0, stringArray2, 0, stringArray.length);
            stringArray = stringArray2;
        } while (n != -1);
        return stringArray;
    }

    private void lambda$new$0(TraverseEvent traverseEvent) {
        if (traverseEvent.detail == 128) {
            this.onMnemonic(traverseEvent);
        }
    }

    static String access$000(CLabel cLabel) {
        return cLabel.text;
    }
}

