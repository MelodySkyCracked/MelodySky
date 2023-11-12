/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.ImageList;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TBBUTTONINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class ToolItem
extends Item {
    ToolBar parent;
    Control control;
    String toolTipText;
    Image disabledImage;
    Image hotImage;
    Image disabledImage2;
    int id;
    short cx;
    int foreground = -1;
    int background = -1;

    public ToolItem(ToolBar toolBar, int n) {
        super(toolBar, ToolItem.checkStyle(n));
        this.parent = toolBar;
        this.parent.createItem(this, toolBar.getItemCount());
    }

    public ToolItem(ToolBar toolBar, int n, int n2) {
        super(toolBar, ToolItem.checkStyle(n));
        this.parent = toolBar;
        this.parent.createItem(this, n2);
    }

    public void addSelectionListener(SelectionListener selectionListener) {
        this.checkWidget();
        if (selectionListener == null) {
            this.error(4);
        }
        TypedListener typedListener = new TypedListener(selectionListener);
        this.addListener(13, typedListener);
        this.addListener(14, typedListener);
    }

    static int checkStyle(int n) {
        return Widget.checkBits(n, 8, 32, 16, 2, 4, 0);
    }

    @Override
    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    void click(boolean bl) {
        long l2 = this.parent.handle;
        if (OS.GetKeyState(1) < 0) {
            return;
        }
        int n = (int)OS.SendMessage(l2, 1049, (long)this.id, 0L);
        RECT rECT = new RECT();
        OS.SendMessage(l2, 1053, (long)n, rECT);
        int n2 = (int)OS.SendMessage(l2, 1095, 0L, 0L);
        int n3 = rECT.top + (rECT.bottom - rECT.top) / 2;
        long l3 = OS.MAKELPARAM(bl ? rECT.right - 1 : rECT.left, n3);
        this.parent.ignoreMouse = true;
        OS.SendMessage(l2, 513, 0L, l3);
        OS.SendMessage(l2, 514, 0L, l3);
        this.parent.ignoreMouse = false;
        if (n2 != -1) {
            OS.SendMessage(l2, 1096, (long)n2, 0L);
        }
    }

    Widget[] computeTabList() {
        if (this != null && this != false) {
            if ((this.style & 2) == 0) {
                return new Widget[]{this};
            }
            if (this.control != null) {
                return this.control.computeTabList();
            }
        }
        return new Widget[0];
    }

    @Override
    void destroyWidget() {
        this.parent.destroyItem(this);
        this.releaseHandle();
    }

    public Rectangle getBounds() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getBoundsInPixels());
    }

    Rectangle getBoundsInPixels() {
        long l2 = this.parent.handle;
        int n = (int)OS.SendMessage(l2, 1049, (long)this.id, 0L);
        RECT rECT = new RECT();
        OS.SendMessage(l2, 1053, (long)n, rECT);
        int n2 = rECT.right - rECT.left;
        int n3 = rECT.bottom - rECT.top;
        return new Rectangle(rECT.left, rECT.top, n2, n3);
    }

    public Control getControl() {
        this.checkWidget();
        return this.control;
    }

    public Image getDisabledImage() {
        this.checkWidget();
        return this.disabledImage;
    }

    public Color getBackground() {
        this.checkWidget();
        return Color.win32_new(this.display, this.parent.getBackgroundPixel(this));
    }

    public Color getForeground() {
        this.checkWidget();
        return Color.win32_new(this.display, this.parent.getForegroundPixel(this));
    }

    public Image getHotImage() {
        this.checkWidget();
        return this.hotImage;
    }

    @Override
    public Image getImage() {
        return super.getImage();
    }

    public ToolBar getParent() {
        this.checkWidget();
        return this.parent;
    }

    public boolean getSelection() {
        this.checkWidget();
        if ((this.style & 0x30) == 0) {
            return false;
        }
        long l2 = this.parent.handle;
        long l3 = OS.SendMessage(l2, 1042, (long)this.id, 0L);
        return (l3 & 1L) != 0L;
    }

    public String getToolTipText() {
        this.checkWidget();
        return this.toolTipText;
    }

    public int getWidth() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getWidthInPixels());
    }

    int getWidthInPixels() {
        long l2 = this.parent.handle;
        int n = (int)OS.SendMessage(l2, 1049, (long)this.id, 0L);
        RECT rECT = new RECT();
        OS.SendMessage(l2, 1053, (long)n, rECT);
        return rECT.right - rECT.left;
    }

    public boolean isEnabled() {
        this.checkWidget();
        return this != false && this.parent.isEnabled();
    }

    void redraw() {
        RECT rECT = new RECT();
        if (OS.SendMessage(this.parent.handle, 1075, (long)this.id, rECT) != 0L) {
            OS.InvalidateRect(this.parent.handle, rECT, true);
        }
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        this.releaseImages();
        this.control = null;
        this.toolTipText = null;
        Object var1_1 = null;
        this.hotImage = var1_1;
        this.disabledImage = var1_1;
        if (this.disabledImage2 != null) {
            this.disabledImage2.dispose();
        }
        this.disabledImage2 = null;
    }

    @Override
    void releaseHandle() {
        super.releaseHandle();
        this.parent = null;
        this.id = -1;
    }

    void releaseImages() {
        TBBUTTONINFO tBBUTTONINFO = new TBBUTTONINFO();
        tBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
        tBBUTTONINFO.dwMask = 9;
        long l2 = this.parent.handle;
        OS.SendMessage(l2, 1087, (long)this.id, tBBUTTONINFO);
        if ((tBBUTTONINFO.fsStyle & 1) == 0 && tBBUTTONINFO.iImage != -2) {
            ImageList imageList = this.parent.getImageList();
            ImageList imageList2 = this.parent.getHotImageList();
            ImageList imageList3 = this.parent.getDisabledImageList();
            if (imageList != null) {
                imageList.put(tBBUTTONINFO.iImage, null);
            }
            if (imageList2 != null) {
                imageList2.put(tBBUTTONINFO.iImage, null);
            }
            if (imageList3 != null) {
                imageList3.put(tBBUTTONINFO.iImage, null);
            }
        }
    }

    public void removeSelectionListener(SelectionListener selectionListener) {
        this.checkWidget();
        if (selectionListener == null) {
            this.error(4);
        }
        if (this.eventTable == null) {
            return;
        }
        this.eventTable.unhook(13, selectionListener);
        this.eventTable.unhook(14, selectionListener);
    }

    void resizeControl() {
        if (this.control != null && !this.control.isDisposed()) {
            Rectangle rectangle = this.getBounds();
            this.control.setSize(rectangle.width, rectangle.height);
            Rectangle rectangle2 = this.control.getBounds();
            rectangle2.x = rectangle.x + (rectangle.width - rectangle2.width) / 2;
            rectangle2.y = rectangle.y + (rectangle.height - rectangle2.height) / 2;
            this.control.setLocation(rectangle2.x, rectangle2.y);
        }
    }

    /*
     * Exception decompiling
     */
    void selectRadio() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl22 : ILOAD_3 - null : trying to set 1 previously set to 0
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public void setBackground(Color color) {
        int n;
        this.checkWidget();
        if (color != null && color.isDisposed()) {
            this.error(5);
        }
        ToolBar toolBar = this.parent;
        toolBar.state |= 0x1000000;
        int n2 = n = color != null ? color.handle : -1;
        if (n == this.background) {
            return;
        }
        this.background = n;
        this.redraw();
    }

    public void setControl(Control control) {
        this.checkWidget();
        if (control != null) {
            if (control.isDisposed()) {
                this.error(5);
            }
            if (control.parent != this.parent) {
                this.error(32);
            }
        }
        if ((this.style & 2) == 0) {
            return;
        }
        this.parent.layout(true);
        this.control = control;
        if ((this.parent.style & 0x240) != 0) {
            boolean bl = false;
            long l2 = this.parent.handle;
            TBBUTTONINFO tBBUTTONINFO = new TBBUTTONINFO();
            tBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
            tBBUTTONINFO.dwMask = 12;
            OS.SendMessage(l2, 1087, (long)this.id, tBBUTTONINFO);
            if (control == null) {
                if ((tBBUTTONINFO.fsStyle & 1) == 0) {
                    bl = true;
                    TBBUTTONINFO tBBUTTONINFO2 = tBBUTTONINFO;
                    tBBUTTONINFO2.fsStyle = (byte)(tBBUTTONINFO2.fsStyle & 0xFFFFFFBF);
                    TBBUTTONINFO tBBUTTONINFO3 = tBBUTTONINFO;
                    tBBUTTONINFO3.fsStyle = (byte)(tBBUTTONINFO3.fsStyle | 1);
                    if ((this.state & 8) != 0) {
                        TBBUTTONINFO tBBUTTONINFO4 = tBBUTTONINFO;
                        tBBUTTONINFO4.fsState = (byte)(tBBUTTONINFO4.fsState & 0xFFFFFFFB);
                    } else {
                        TBBUTTONINFO tBBUTTONINFO5 = tBBUTTONINFO;
                        tBBUTTONINFO5.fsState = (byte)(tBBUTTONINFO5.fsState | 4);
                    }
                }
            } else if ((tBBUTTONINFO.fsStyle & 1) != 0) {
                bl = true;
                TBBUTTONINFO tBBUTTONINFO6 = tBBUTTONINFO;
                tBBUTTONINFO6.fsStyle = (byte)(tBBUTTONINFO6.fsStyle & 0xFFFFFFFE);
                TBBUTTONINFO tBBUTTONINFO7 = tBBUTTONINFO;
                tBBUTTONINFO7.fsStyle = (byte)(tBBUTTONINFO7.fsStyle | 0x40);
                TBBUTTONINFO tBBUTTONINFO8 = tBBUTTONINFO;
                tBBUTTONINFO8.fsState = (byte)(tBBUTTONINFO8.fsState & 0xFFFFFFFB);
                TBBUTTONINFO tBBUTTONINFO9 = tBBUTTONINFO;
                tBBUTTONINFO9.dwMask |= 1;
                tBBUTTONINFO.iImage = -2;
            }
            if (bl) {
                OS.SendMessage(l2, 1088, (long)this.id, tBBUTTONINFO);
                if (OS.SendMessage(l2, 1064, 0L, 0L) > 1L) {
                    OS.InvalidateRect(l2, null, true);
                }
            }
        }
        this.resizeControl();
    }

    public void setEnabled(boolean bl) {
        this.checkWidget();
        long l2 = this.parent.handle;
        int n = (int)OS.SendMessage(l2, 1042, (long)this.id, 0L);
        if ((n & 4) != 0 == bl) {
            return;
        }
        if (bl) {
            n |= 4;
            this.state &= 0xFFFFFFF7;
        } else {
            n &= 0xFFFFFFFB;
            this.state |= 8;
        }
        OS.SendMessage(l2, 1041, (long)this.id, n);
        if ((this.style & 2) == 0 && this.image != null) {
            this.updateImages(bl && this.parent.getEnabled());
        }
        if (!bl && this.parent.lastFocusId == this.id) {
            this.parent.lastFocusId = -1;
        }
    }

    public void setDisabledImage(Image image) {
        this.checkWidget();
        if (this.disabledImage == image) {
            return;
        }
        if ((this.style & 2) != 0) {
            return;
        }
        if (image != null && image.isDisposed()) {
            this.error(5);
        }
        this.parent.layout(this.isImageSizeChanged(this.disabledImage, image));
        this.disabledImage = image;
        this.updateImages(this != false && this.parent.getEnabled());
    }

    public void setForeground(Color color) {
        int n;
        this.checkWidget();
        if (color != null && color.isDisposed()) {
            this.error(5);
        }
        ToolBar toolBar = this.parent;
        toolBar.state |= 0x1000000;
        int n2 = n = color != null ? color.handle : -1;
        if (n == this.foreground) {
            return;
        }
        this.foreground = n;
        this.redraw();
    }

    public void setHotImage(Image image) {
        this.checkWidget();
        if (this.hotImage == image) {
            return;
        }
        if ((this.style & 2) != 0) {
            return;
        }
        if (image != null && image.isDisposed()) {
            this.error(5);
        }
        this.parent.layout(this.isImageSizeChanged(this.hotImage, image));
        this.hotImage = image;
        this.updateImages(this != false && this.parent.getEnabled());
    }

    @Override
    public void setImage(Image image) {
        this.checkWidget();
        if (this.image == image) {
            return;
        }
        if ((this.style & 2) != 0) {
            return;
        }
        if (image != null && image.isDisposed()) {
            this.error(5);
        }
        this.parent.layout(this.isImageSizeChanged(this.image, image));
        super.setImage(image);
        this.updateImages(this != false && this.parent.getEnabled());
    }

    boolean isImageSizeChanged(Image image, Image image2) {
        boolean bl = true;
        if (image != null && !image.isDisposed() && image2 != null && !image2.isDisposed()) {
            bl = !image.getBounds().equals(image2.getBounds());
        }
        return bl;
    }

    public void setSelection(boolean bl) {
        this.checkWidget();
        if ((this.style & 0x30) == 0) {
            return;
        }
        long l2 = this.parent.handle;
        int n = (int)OS.SendMessage(l2, 1042, (long)this.id, 0L);
        if ((n & 1) != 0 == bl) {
            return;
        }
        n = bl ? (n |= 1) : (n &= 0xFFFFFFFE);
        OS.SendMessage(l2, 1041, (long)this.id, n);
        if (!((this.style & 0x30) == 0 || this != false && this.parent.getEnabled())) {
            this.updateImages(false);
        }
    }

    @Override
    boolean setTabItemFocus() {
        if (this.parent.setTabItemFocus()) {
            long l2 = this.parent.handle;
            int n = (int)OS.SendMessage(l2, 1049, (long)this.id, 0L);
            OS.SendMessage(l2, 1096, (long)n, 0L);
            return true;
        }
        return false;
    }

    void _setText(String string) {
        long l2 = this.parent.handle;
        TBBUTTONINFO tBBUTTONINFO = new TBBUTTONINFO();
        tBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
        tBBUTTONINFO.dwMask = 10;
        tBBUTTONINFO.fsStyle = (byte)(this.widgetStyle() | 0x10);
        long l3 = OS.GetProcessHeap();
        long l4 = 0L;
        if (string.length() != 0) {
            int n;
            TBBUTTONINFO tBBUTTONINFO2 = tBBUTTONINFO;
            tBBUTTONINFO2.fsStyle = (byte)(tBBUTTONINFO2.fsStyle | 0x40);
            TCHAR tCHAR = (this.style & Integer.MIN_VALUE) != 0 ? (((n = OS.GetWindowLong(l2, -20)) & 0x400000) != 0 ? new TCHAR(this.parent.getCodePage(), "\u202a" + string, true) : new TCHAR(this.parent.getCodePage(), "\u202b" + string, true)) : new TCHAR(this.parent.getCodePage(), string, true);
            n = tCHAR.length() * 2;
            l4 = OS.HeapAlloc(l3, 8, n);
            OS.MoveMemory(l4, tCHAR, n);
            tBBUTTONINFO.pszText = l4;
        }
        OS.SendMessage(l2, 1088, (long)this.id, tBBUTTONINFO);
        if (l4 != 0L) {
            OS.HeapFree(l3, 0, l4);
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public void setText(String var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl37 : ALOAD_0 - null : trying to set 0 previously set to 1
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public void setToolTipText(String string) {
        this.checkWidget();
        this.toolTipText = string;
    }

    public void setWidth(int n) {
        this.checkWidget();
        this.setWidthInPixels(DPIUtil.autoScaleUp(n));
    }

    void setWidthInPixels(int n) {
        short s;
        if ((this.style & 2) == 0) {
            return;
        }
        if (n < 0) {
            return;
        }
        long l2 = this.parent.handle;
        TBBUTTONINFO tBBUTTONINFO = new TBBUTTONINFO();
        tBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
        tBBUTTONINFO.dwMask = 64;
        TBBUTTONINFO tBBUTTONINFO2 = tBBUTTONINFO;
        this.cx = s = (short)n;
        tBBUTTONINFO2.cx = s;
        OS.SendMessage(l2, 1088, (long)this.id, tBBUTTONINFO);
        this.parent.layoutItems();
    }

    void updateImages(boolean bl) {
        Object object;
        if ((this.style & 2) != 0) {
            return;
        }
        long l2 = this.parent.handle;
        TBBUTTONINFO tBBUTTONINFO = new TBBUTTONINFO();
        tBBUTTONINFO.cbSize = TBBUTTONINFO.sizeof;
        tBBUTTONINFO.dwMask = 1;
        OS.SendMessage(l2, 1087, (long)this.id, tBBUTTONINFO);
        if (tBBUTTONINFO.iImage == -2 && this.image == null) {
            return;
        }
        ImageList imageList = this.parent.getImageList();
        ImageList imageList2 = this.parent.getHotImageList();
        ImageList imageList3 = this.parent.getDisabledImageList();
        if (tBBUTTONINFO.iImage == -2) {
            Image image;
            object = this.image.getBoundsInPixels();
            int n = this.parent.style & 0x4000000;
            if (imageList == null) {
                imageList = this.display.getImageListToolBar(n, ((Rectangle)object).width, ((Rectangle)object).height);
            }
            if (imageList3 == null) {
                imageList3 = this.display.getImageListToolBarDisabled(n, ((Rectangle)object).width, ((Rectangle)object).height);
            }
            if (imageList2 == null) {
                imageList2 = this.display.getImageListToolBarHot(n, ((Rectangle)object).width, ((Rectangle)object).height);
            }
            Image image2 = this.disabledImage;
            if (this.disabledImage == null) {
                if (this.disabledImage2 != null) {
                    this.disabledImage2.dispose();
                }
                this.disabledImage2 = null;
                image2 = this.image;
                if (!bl) {
                    this.disabledImage2 = image = new Image((Device)this.display, this.image, 1);
                    image2 = image;
                }
            }
            image = this.image;
            Image image3 = this.hotImage;
            if ((this.style & 0x30) != 0 && !bl) {
                image3 = image = image2;
            }
            tBBUTTONINFO.iImage = imageList.add(image);
            imageList3.add(image2);
            imageList2.add(image3 != null ? image3 : image);
            this.parent.setImageList(imageList);
            this.parent.setDisabledImageList(imageList3);
            this.parent.setHotImageList(imageList2);
        } else {
            Object object2;
            object = null;
            if (imageList3 != null) {
                if (this.image != null) {
                    if (this.disabledImage2 != null) {
                        this.disabledImage2.dispose();
                    }
                    this.disabledImage2 = null;
                    object = this.disabledImage;
                    if (this.disabledImage == null) {
                        object = this.image;
                        if (!bl) {
                            this.disabledImage2 = object2 = new Image((Device)this.display, this.image, 1);
                            object = object2;
                        }
                    }
                }
                imageList3.put(tBBUTTONINFO.iImage, (Image)object);
            }
            object2 = this.image;
            Object object3 = this.hotImage;
            if ((this.style & 0x30) != 0 && !bl) {
                object3 = object2 = object;
            }
            if (imageList != null) {
                imageList.put(tBBUTTONINFO.iImage, (Image)object2);
            }
            if (imageList2 != null) {
                imageList2.put(tBBUTTONINFO.iImage, (Image)(object3 != null ? object3 : object2));
            }
            if (this.image == null) {
                tBBUTTONINFO.iImage = -2;
            }
        }
        object = tBBUTTONINFO;
        ((TBBUTTONINFO)object).dwMask |= 0x40;
        tBBUTTONINFO.cx = 0;
        OS.SendMessage(l2, 1088, (long)this.id, tBBUTTONINFO);
        long l3 = OS.SendMessage(l2, 49, 0L, 0L);
        OS.SendMessage(l2, 48, l3, 0L);
        this.parent.layoutItems();
    }

    int widgetStyle() {
        if ((this.style & 4) != 0) {
            return 8;
        }
        if ((this.style & 8) != 0) {
            return 0;
        }
        if ((this.style & 0x20) != 0) {
            return 2;
        }
        if ((this.style & 0x10) != 0) {
            return 2;
        }
        if ((this.style & 2) != 0) {
            return 1;
        }
        return 0;
    }

    LRESULT wmCommandChild(long l2, long l3) {
        if ((this.style & 0x10) != 0 && (this.parent.getStyle() & 0x400000) == 0) {
            this.selectRadio();
        }
        this.sendSelectionEvent(13);
        return null;
    }
}

