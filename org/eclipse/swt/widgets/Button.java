/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.BidiUtil;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.ImageList;
import org.eclipse.swt.internal.win32.BITMAP;
import org.eclipse.swt.internal.win32.BUTTON_IMAGELIST;
import org.eclipse.swt.internal.win32.DRAWITEMSTRUCT;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SIZE;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TypedListener;

public class Button
extends Control {
    String text = "";
    String message = "";
    Image image;
    Image disabledImage;
    ImageList imageList;
    boolean ignoreMouse;
    boolean grayed;
    boolean useDarkModeExplorerTheme;
    static final int MARGIN = 4;
    static final int CHECK_WIDTH;
    static final int CHECK_HEIGHT;
    static final int ICON_WIDTH = 128;
    static final int ICON_HEIGHT = 128;
    static boolean COMMAND_LINK;
    static final char[] STRING_WITH_ZERO_CHAR;
    static final long ButtonProc;
    static final TCHAR ButtonClass;

    public Button(Composite composite, int n) {
        super(composite, Button.checkStyle(n));
    }

    void _setImage(Image image) {
        if ((this.style & 0x400000) != 0) {
            return;
        }
        if (this.imageList != null) {
            this.imageList.dispose();
        }
        this.imageList = null;
        if (image != null) {
            int n;
            this.imageList = new ImageList(this.style & 0x4000000);
            if (OS.IsWindowEnabled(this.handle)) {
                this.imageList.add(image);
            } else {
                if (this.disabledImage != null) {
                    this.disabledImage.dispose();
                }
                this.disabledImage = new Image((Device)this.display, image, 1);
                this.imageList.add(this.disabledImage);
            }
            BUTTON_IMAGELIST bUTTON_IMAGELIST = new BUTTON_IMAGELIST();
            bUTTON_IMAGELIST.himl = this.imageList.getHandle();
            int n2 = n = OS.GetWindowLong(this.handle, -16);
            n2 &= 0xFFFFFCFF;
            if ((this.style & 0x4000) != 0) {
                n2 |= 0x100;
            }
            if ((this.style & 0x1000000) != 0) {
                n2 |= 0x300;
            }
            if ((this.style & 0x20000) != 0) {
                n2 |= 0x200;
            }
            if (this.text.length() == 0) {
                if ((this.style & 0x4000) != 0) {
                    bUTTON_IMAGELIST.uAlign = 0;
                }
                if ((this.style & 0x1000000) != 0) {
                    bUTTON_IMAGELIST.uAlign = 4;
                }
                if ((this.style & 0x20000) != 0) {
                    bUTTON_IMAGELIST.uAlign = 1;
                }
            } else {
                bUTTON_IMAGELIST.uAlign = 0;
                bUTTON_IMAGELIST.margin_left = this.computeLeftMargin();
                bUTTON_IMAGELIST.margin_right = 4;
                n2 &= 0xFFFFFCFF;
                n2 |= 0x100;
            }
            if (n2 != n) {
                OS.SetWindowLong(this.handle, -16, n2);
                OS.InvalidateRect(this.handle, null, true);
            }
            OS.SendMessage(this.handle, 5634, 0L, bUTTON_IMAGELIST);
        } else {
            OS.SendMessage(this.handle, 5634, 0L, 0L);
        }
        OS.InvalidateRect(this.handle, null, true);
    }

    void _setText(String string) {
        Object object;
        int n;
        int n2 = n = OS.GetWindowLong(this.handle, -16);
        n2 &= 0xFFFFFCFF;
        if ((this.style & 0x4000) != 0) {
            n2 |= 0x100;
        }
        if ((this.style & 0x1000000) != 0) {
            n2 |= 0x300;
        }
        if ((this.style & 0x20000) != 0) {
            n2 |= 0x200;
        }
        if (this.imageList != null) {
            object = new BUTTON_IMAGELIST();
            ((BUTTON_IMAGELIST)object).himl = this.imageList.getHandle();
            if (string.length() == 0) {
                if ((this.style & 0x4000) != 0) {
                    ((BUTTON_IMAGELIST)object).uAlign = 0;
                }
                if ((this.style & 0x1000000) != 0) {
                    ((BUTTON_IMAGELIST)object).uAlign = 4;
                }
                if ((this.style & 0x20000) != 0) {
                    ((BUTTON_IMAGELIST)object).uAlign = 1;
                }
            } else {
                ((BUTTON_IMAGELIST)object).uAlign = 0;
                ((BUTTON_IMAGELIST)object).margin_left = this.computeLeftMargin();
                ((BUTTON_IMAGELIST)object).margin_right = 4;
                n2 &= 0xFFFFFCFF;
                n2 |= 0x100;
            }
            OS.SendMessage(this.handle, 5634, 0L, (BUTTON_IMAGELIST)object);
        }
        if (n2 != n) {
            OS.SetWindowLong(this.handle, -16, n2);
            OS.InvalidateRect(this.handle, null, true);
        }
        if ((this.style & 0x4000000) != 0 && !OS.IsAppThemed()) {
            string = OS.IsWindowEnabled(this.handle) ? string : string + " ";
        }
        object = new TCHAR(this.getCodePage(), string, true);
        OS.SetWindowText(this.handle, (TCHAR)object);
        if ((this.state & 0x400000) != 0) {
            this.updateTextDirection(0x6000000);
        }
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

    @Override
    long callWindowProc(long l2, int n, long l3, long l4) {
        if (this.handle == 0L) {
            return 0L;
        }
        return OS.CallWindowProc(ButtonProc, l2, n, l3, l4);
    }

    static int checkStyle(int n) {
        if (((n = Button.checkBits(n, 8, 4, 32, 16, 2, COMMAND_LINK ? 0x400000 : 0)) & 0xA) != 0) {
            return Button.checkBits(n, 0x1000000, 16384, 131072, 0, 0, 0);
        }
        if ((n & 0x30) != 0) {
            return Button.checkBits(n, 16384, 131072, 0x1000000, 0, 0, 0);
        }
        if ((n & 4) != 0) {
            return Button.checkBits(n |= 0x80000, 128, 1024, 16384, 131072, 0, 0);
        }
        return n;
    }

    void click() {
        this.ignoreMouse = true;
        OS.SendMessage(this.handle, 245, 0L, 0L);
        this.ignoreMouse = false;
    }

    int computeLeftMargin() {
        if ((this.style & 0xA) == 0) {
            return 4;
        }
        int n = 0;
        if (this.image != null && this.text.length() != 0) {
            Rectangle rectangle = this.image.getBoundsInPixels();
            n += rectangle.width + 8;
            long l2 = 0L;
            long l3 = OS.GetDC(this.handle);
            long l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
            if (l4 != 0L) {
                l2 = OS.SelectObject(l3, l4);
            }
            char[] cArray = this.text.toCharArray();
            RECT rECT = new RECT();
            int n2 = 1056;
            OS.DrawText(l3, cArray, cArray.length, rECT, n2);
            n += rECT.right - rECT.left;
            if (l4 != 0L) {
                OS.SelectObject(l3, l2);
            }
            OS.ReleaseDC(this.handle, l3);
            OS.GetClientRect(this.handle, rECT);
            n = Math.max(4, (rECT.right - rECT.left - n) / 2);
        }
        return n;
    }

    @Override
    Point computeSizeInPixels(int n, int n2, boolean bl) {
        this.checkWidget();
        int n3 = 0;
        int n4 = 0;
        int n5 = this.getBorderWidthInPixels();
        if ((this.style & 4) != 0) {
            if ((this.style & 0x480) != 0) {
                n3 += OS.GetSystemMetrics(2);
                n4 += OS.GetSystemMetrics(20);
            } else {
                n3 += OS.GetSystemMetrics(21);
                n4 += OS.GetSystemMetrics(3);
            }
        } else if ((this.style & 0x400000) != 0) {
            SIZE sIZE = new SIZE();
            if (n != -1) {
                sIZE.cx = n;
                OS.SendMessage(this.handle, 5633, 0L, sIZE);
                n3 = sIZE.cx;
                n4 = sIZE.cy;
            } else {
                OS.SendMessage(this.handle, 5633, 0L, sIZE);
                n3 = sIZE.cy;
                n4 = sIZE.cy;
                sIZE.cy = 0;
                while (sIZE.cy != n4) {
                    sIZE.cx = n3++;
                    sIZE.cy = 0;
                    OS.SendMessage(this.handle, 5633, 0L, sIZE);
                }
            }
        } else {
            int n6 = 0;
            boolean bl2 = this.image != null;
            boolean bl3 = true;
            if (bl2 && this.image != null) {
                Rectangle rectangle = this.image.getBoundsInPixels();
                n3 = rectangle.width;
                if (bl3 && this.text.length() != 0) {
                    n3 += 8;
                }
                n4 = rectangle.height;
                n6 = 8;
            }
            if (bl3) {
                long l2 = 0L;
                long l3 = OS.GetDC(this.handle);
                long l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
                if (l4 != 0L) {
                    l2 = OS.SelectObject(l3, l4);
                }
                TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
                OS.GetTextMetrics(l3, tEXTMETRIC);
                int n7 = this.text.length();
                if (n7 == 0) {
                    n4 = Math.max(n4, tEXTMETRIC.tmHeight);
                } else {
                    n6 = Math.max(8, tEXTMETRIC.tmAveCharWidth);
                    char[] cArray = this.text.toCharArray();
                    RECT rECT = new RECT();
                    int n8 = 1056;
                    if ((this.style & 0x40) != 0 && n != -1) {
                        n8 = 1040;
                        rECT.right = n - n3 - 2 * n5;
                        int n9 = rECT.right = this != false ? (rECT.right = rECT.right - (CHECK_WIDTH + 3)) : (rECT.right = rECT.right - 6);
                        if (!OS.IsAppThemed()) {
                            rECT.right -= 2;
                            if (this != false) {
                                rECT.right -= 2;
                            }
                        }
                    }
                    OS.DrawText(l3, cArray, cArray.length, rECT, n8);
                    n3 += rECT.right - rECT.left;
                    n4 = Math.max(n4, rECT.bottom - rECT.top);
                }
                if (l4 != 0L) {
                    OS.SelectObject(l3, l2);
                }
                OS.ReleaseDC(this.handle, l3);
            }
            if (this != false) {
                n3 += CHECK_WIDTH + n6;
                n4 = Math.max(n4, CHECK_HEIGHT + 3);
            }
            if ((this.style & 0xA) != 0) {
                n3 += 12;
                n4 += 10;
            }
        }
        if (n != -1) {
            n3 = n;
        }
        if (n2 != -1) {
            n4 = n2;
        }
        return new Point(n3 += n5 * 2, n4 += n5 * 2);
    }

    @Override
    void createHandle() {
        this.parent.state |= 0x100000;
        super.createHandle();
        this.parent.state &= 0xFFEFFFFF;
        if (OS.IsAppThemed()) {
            if ((this.style & 0xA) == 0) {
                this.state |= 0x100;
            }
            if ((this.style & 0x10) != 0) {
                this.state |= 0x200;
            }
            this.useDarkModeExplorerTheme = this.display.useDarkModeExplorerTheme;
            this.maybeEnableDarkSystemTheme();
        }
    }

    @Override
    int defaultBackground() {
        if ((this.style & 0xA) != 0) {
            return OS.GetSysColor(15);
        }
        return super.defaultBackground();
    }

    @Override
    int defaultForeground() {
        return OS.GetSysColor(18);
    }

    @Override
    void enableWidget(boolean bl) {
        super.enableWidget(bl);
        if ((this.style & 0x4000000) != 0 && !OS.IsAppThemed()) {
            int n = OS.GetWindowLong(this.handle, -16);
            boolean bl2 = (n & 0xC0) != 0;
            boolean bl3 = bl2;
            if (!bl2) {
                String string = bl ? this.text : this.text + " ";
                TCHAR tCHAR = new TCHAR(this.getCodePage(), string, true);
                OS.SetWindowText(this.handle, tCHAR);
            }
        }
        this.updateImageList();
    }

    public int getAlignment() {
        this.checkWidget();
        if ((this.style & 4) != 0) {
            if ((this.style & 0x80) != 0) {
                return 128;
            }
            if ((this.style & 0x400) != 0) {
                return 1024;
            }
            if ((this.style & 0x4000) != 0) {
                return 16384;
            }
            if ((this.style & 0x20000) != 0) {
                return 131072;
            }
            return 128;
        }
        if ((this.style & 0x4000) != 0) {
            return 16384;
        }
        if ((this.style & 0x1000000) != 0) {
            return 0x1000000;
        }
        if ((this.style & 0x20000) != 0) {
            return 131072;
        }
        return 16384;
    }

    public boolean getGrayed() {
        this.checkWidget();
        if ((this.style & 0x20) == 0) {
            return false;
        }
        return this.grayed;
    }

    public Image getImage() {
        this.checkWidget();
        return this.image;
    }

    String getMessage() {
        this.checkWidget();
        return this.message;
    }

    @Override
    String getNameText() {
        return this.getText();
    }

    public String getText() {
        this.checkWidget();
        if ((this.style & 4) != 0) {
            return "";
        }
        return this.text;
    }

    boolean isTabItem() {
        if ((this.style & 8) != 0) {
            return this.isTabGroup();
        }
        return super.isTabItem();
    }

    @Override
    boolean mnemonicHit(char c) {
        if ((this.style & 0x10) == 0 && this != false) {
            return false;
        }
        this.click();
        return true;
    }

    @Override
    boolean mnemonicMatch(char c) {
        char c2 = this.findMnemonic(this.getText());
        if (c2 == '\u0000') {
            return false;
        }
        return Character.toUpperCase(c) == Character.toUpperCase(c2);
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        if (this.imageList != null) {
            this.imageList.dispose();
        }
        this.imageList = null;
        if (this.disabledImage != null) {
            this.disabledImage.dispose();
        }
        this.disabledImage = null;
        this.text = null;
        this.image = null;
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

    @Override
    int resolveTextDirection() {
        return (this.style & 4) != 0 ? 0 : BidiUtil.resolveTextDirection(this.text);
    }

    void selectRadio() {
        for (Control control : this.parent._getChildren()) {
            if (this == control) continue;
            control.setRadioSelection(false);
        }
        this.setSelection(true);
    }

    public void setAlignment(int n) {
        int n2;
        this.checkWidget();
        if ((this.style & 4) != 0) {
            if ((this.style & 0x24480) == 0) {
                return;
            }
            this.style &= 0xFFFDBB7F;
            this.style |= n & 0x24480;
            OS.InvalidateRect(this.handle, null, true);
            return;
        }
        if ((n & 0x1024000) == 0) {
            return;
        }
        this.style &= 0xFEFDBFFF;
        this.style |= n & 0x1024000;
        int n3 = n2 = OS.GetWindowLong(this.handle, -16);
        n3 &= 0xFFFFFCFF;
        if ((this.style & 0x4000) != 0) {
            n3 |= 0x100;
        }
        if ((this.style & 0x1000000) != 0) {
            n3 |= 0x300;
        }
        if ((this.style & 0x20000) != 0) {
            n3 |= 0x200;
        }
        if (this.imageList != null) {
            BUTTON_IMAGELIST bUTTON_IMAGELIST = new BUTTON_IMAGELIST();
            bUTTON_IMAGELIST.himl = this.imageList.getHandle();
            if (this.text.length() == 0) {
                if ((this.style & 0x4000) != 0) {
                    bUTTON_IMAGELIST.uAlign = 0;
                }
                if ((this.style & 0x1000000) != 0) {
                    bUTTON_IMAGELIST.uAlign = 4;
                }
                if ((this.style & 0x20000) != 0) {
                    bUTTON_IMAGELIST.uAlign = 1;
                }
            } else {
                bUTTON_IMAGELIST.uAlign = 0;
                bUTTON_IMAGELIST.margin_left = this.computeLeftMargin();
                bUTTON_IMAGELIST.margin_right = 4;
                n3 &= 0xFFFFFCFF;
                n3 |= 0x100;
            }
            OS.SendMessage(this.handle, 5634, 0L, bUTTON_IMAGELIST);
        }
        if (n3 != n2) {
            OS.SetWindowLong(this.handle, -16, n3);
            OS.InvalidateRect(this.handle, null, true);
        }
    }

    @Override
    public void setBackground(Color color) {
        super.setBackground(color);
    }

    void setDefault(boolean bl) {
        if ((this.style & 8) == 0) {
            return;
        }
        long l2 = this.menuShell().handle;
        int n = OS.GetWindowLong(this.handle, -16);
        if (bl) {
            n |= 1;
            OS.SendMessage(l2, 1025, this.handle, 0L);
        } else {
            n &= 0xFFFFFFFE;
            OS.SendMessage(l2, 1025, 0L, 0L);
        }
        OS.SendMessage(this.handle, 244, (long)n, 1L);
    }

    public void setImage(Image image) {
        this.checkWidget();
        if (image != null && image.isDisposed()) {
            this.error(5);
        }
        if ((this.style & 4) != 0) {
            return;
        }
        this.image = image;
        this._setImage(image);
    }

    public void setGrayed(boolean bl) {
        this.checkWidget();
        if ((this.style & 0x20) == 0) {
            return;
        }
        this.grayed = bl;
        long l2 = OS.SendMessage(this.handle, 240, 0L, 0L);
        if (bl) {
            if (l2 == 1L) {
                this.updateSelection(2);
            }
        } else if (l2 == 2L) {
            this.updateSelection(1);
        }
    }

    void setMessage(String string) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        this.message = string;
        if ((this.style & 0x400000) != 0) {
            int n = string.length();
            char[] cArray = new char[n + 1];
            string.getChars(0, n, cArray, 0);
            OS.SendMessage(this.handle, 5641, 0L, cArray);
        }
    }

    @Override
    boolean setRadioFocus(boolean bl) {
        if ((this.style & 0x10) == 0 || this == false) {
            return false;
        }
        return bl ? this.setTabItemFocus() : this.setFocus();
    }

    @Override
    boolean setRadioSelection(boolean bl) {
        if ((this.style & 0x10) == 0) {
            return false;
        }
        if (this.getSelection() != bl) {
            this.setSelection(bl);
            this.sendSelectionEvent(13);
        }
        return true;
    }

    public void setSelection(boolean bl) {
        this.checkWidget();
        if ((this.style & 0x32) == 0) {
            return;
        }
        int n = bl ? 1 : 0;
        int n2 = n;
        if ((this.style & 0x20) != 0 && bl && this.grayed) {
            n = 2;
        }
        this.updateSelection(n);
    }

    public void setText(String string) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        if ((this.style & 4) != 0) {
            return;
        }
        this.text = string;
        this._setText(string);
    }

    @Override
    boolean updateTextDirection(int n) {
        return super.updateTextDirection(n);
    }

    void updateImageList() {
        if (this.imageList != null) {
            BUTTON_IMAGELIST bUTTON_IMAGELIST = new BUTTON_IMAGELIST();
            OS.SendMessage(this.handle, 5635, 0L, bUTTON_IMAGELIST);
            if (this.imageList != null) {
                this.imageList.dispose();
            }
            this.imageList = new ImageList(this.style & 0x4000000);
            if (OS.IsWindowEnabled(this.handle)) {
                this.imageList.add(this.image);
            } else {
                if (this.disabledImage != null) {
                    this.disabledImage.dispose();
                }
                this.disabledImage = new Image((Device)this.display, this.image, 1);
                this.imageList.add(this.disabledImage);
            }
            bUTTON_IMAGELIST.himl = this.imageList.getHandle();
            OS.SendMessage(this.handle, 5634, 0L, bUTTON_IMAGELIST);
            OS.InvalidateRect(this.handle, null, true);
        }
    }

    @Override
    void updateOrientation() {
        super.updateOrientation();
        this.updateImageList();
    }

    void updateSelection(int n) {
        if ((long)n != OS.SendMessage(this.handle, 240, 0L, 0L)) {
            int n2 = OS.GetWindowLong(this.handle, -16);
            if ((this.style & 0x20) != 0) {
                if (n == 2) {
                    n2 &= 0xFFFFFFFD;
                    n2 |= 5;
                } else {
                    n2 |= 2;
                    n2 &= 0xFFFFFFFA;
                }
                if (n2 != OS.GetWindowLong(this.handle, -16)) {
                    OS.SetWindowLong(this.handle, -16, n2);
                }
            }
            OS.SendMessage(this.handle, 241, (long)n, 0L);
            if (n2 != OS.GetWindowLong(this.handle, -16)) {
                OS.SetWindowLong(this.handle, -16, n2);
            }
        }
    }

    @Override
    int widgetStyle() {
        int n = super.widgetStyle();
        if ((this.style & 0x800000) != 0) {
            n |= 0x8000;
        }
        if ((this.style & 4) != 0) {
            return n | 0xB;
        }
        if ((this.style & 0x4000) != 0) {
            n |= 0x100;
        }
        if ((this.style & 0x1000000) != 0) {
            n |= 0x300;
        }
        if ((this.style & 0x20000) != 0) {
            n |= 0x200;
        }
        if ((this.style & 0x40) != 0) {
            n |= 0x2000;
        }
        if ((this.style & 8) != 0) {
            return n | 0 | 0x10000;
        }
        if ((this.style & 0x20) != 0) {
            return n | 2 | 0x10000;
        }
        if ((this.style & 0x10) != 0) {
            return n | 4;
        }
        if ((this.style & 2) != 0) {
            return n | 0x1000 | 2 | 0x10000;
        }
        if ((this.style & 0x400000) != 0) {
            return n | 0xE | 0x10000;
        }
        return n | 0 | 0x10000;
    }

    @Override
    TCHAR windowClass() {
        return ButtonClass;
    }

    @Override
    long windowProc() {
        return ButtonProc;
    }

    @Override
    LRESULT wmColorChild(long l2, long l3) {
        if (this != false) {
            return super.wmColorChild(l2, l3);
        }
        return this.parent.wmColorChild(l2, l3);
    }

    @Override
    LRESULT WM_GETDLGCODE(long l2, long l3) {
        LRESULT lRESULT = super.WM_GETDLGCODE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if ((this.style & 4) != 0) {
            return new LRESULT(256L);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_GETOBJECT(long l2, long l3) {
        if ((this.style & 0x10) != 0 && this.accessible == null) {
            this.accessible = this.new_Accessible(this);
        }
        return super.WM_GETOBJECT(l2, l3);
    }

    @Override
    LRESULT WM_KILLFOCUS(long l2, long l3) {
        LRESULT lRESULT = super.WM_KILLFOCUS(l2, l3);
        if ((this.style & 8) != 0 && this == false) {
            this.menuShell().setDefaultButton(null, false);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_LBUTTONDOWN(long l2, long l3) {
        if (this.ignoreMouse) {
            return null;
        }
        return super.WM_LBUTTONDOWN(l2, l3);
    }

    @Override
    LRESULT WM_LBUTTONUP(long l2, long l3) {
        if (this.ignoreMouse) {
            return null;
        }
        return super.WM_LBUTTONUP(l2, l3);
    }

    @Override
    LRESULT WM_SETFOCUS(long l2, long l3) {
        int n = 0;
        if ((this.style & 0x10) != 0) {
            n = OS.GetWindowLong(this.handle, -16);
        }
        LRESULT lRESULT = super.WM_SETFOCUS(l2, l3);
        if ((this.style & 0x10) != 0) {
            OS.SetWindowLong(this.handle, -16, n);
        }
        if ((this.style & 8) != 0) {
            this.menuShell().setDefaultButton(this, false);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SIZE(long l2, long l3) {
        LRESULT lRESULT = super.WM_SIZE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if ((this.style & 0xA) != 0 && this.imageList != null && this.text.length() != 0) {
            BUTTON_IMAGELIST bUTTON_IMAGELIST = new BUTTON_IMAGELIST();
            OS.SendMessage(this.handle, 5635, 0L, bUTTON_IMAGELIST);
            bUTTON_IMAGELIST.uAlign = 0;
            bUTTON_IMAGELIST.margin_left = this.computeLeftMargin();
            bUTTON_IMAGELIST.margin_right = 4;
            OS.SendMessage(this.handle, 5634, 0L, bUTTON_IMAGELIST);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SYSCOLORCHANGE(long l2, long l3) {
        LRESULT lRESULT = super.WM_SYSCOLORCHANGE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        return lRESULT;
    }

    /*
     * Exception decompiling
     */
    @Override
    LRESULT WM_UPDATEUISTATE(long var1, long var3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl87 : IF_ICMPEQ - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
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

    @Override
    LRESULT wmCommandChild(long l2, long l3) {
        int n = OS.HIWORD(l2);
        switch (n) {
            case 0: 
            case 5: {
                if ((this.style & 0x22) != 0) {
                    this.setSelection(this == false);
                } else if ((this.style & 0x10) != 0) {
                    if ((this.parent.getStyle() & 0x400000) != 0) {
                        this.setSelection(this == false);
                    } else {
                        this.selectRadio();
                    }
                }
                this.sendSelectionEvent(13);
            }
        }
        return super.wmCommandChild(l2, l3);
    }

    private int getCheckboxTextOffset(long l2) {
        int n = 0;
        SIZE sIZE = new SIZE();
        if (OS.IsAppThemed()) {
            OS.GetThemePartSize(this.display.hButtonTheme(), l2, 3, 1, null, 1, sIZE);
            n += sIZE.cx;
        } else {
            n += DPIUtil.autoScaleUpUsingNativeDPI(13);
        }
        OS.GetTextExtentPoint32(l2, STRING_WITH_ZERO_CHAR, 1, sIZE);
        return n += sIZE.cx / 2;
    }

    /*
     * Exception decompiling
     */
    @Override
    LRESULT wmNotifyChild(NMHDR var1, long var2, long var4) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl16 : IF_ICMPEQ - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
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

    static int getThemeStateId(int n, boolean bl, boolean bl2) {
        int n2 = n & 0x24480;
        if ((n & 0x8000000) != 0) {
            if (n2 == 16384) {
                n2 = 131072;
            } else if (n2 == 131072) {
                n2 = 16384;
            }
        }
        boolean bl3 = false;
        if (OS.WIN32_BUILD >= 22000 && !bl && bl2) {
            bl3 = true;
        }
        if (bl3) {
            switch (n2) {
                case 128: {
                    return 2;
                }
                case 1024: {
                    return 6;
                }
                case 16384: {
                    return 10;
                }
                case 131072: {
                    return 14;
                }
            }
        }
        if (bl) {
            switch (n2) {
                case 128: {
                    return 3;
                }
                case 1024: {
                    return 7;
                }
                case 16384: {
                    return 11;
                }
                case 131072: {
                    return 15;
                }
            }
        }
        if (!bl2) {
            switch (n2) {
                case 128: {
                    return 4;
                }
                case 1024: {
                    return 8;
                }
                case 16384: {
                    return 12;
                }
                case 131072: {
                    return 16;
                }
            }
        }
        switch (n2) {
            case 128: {
                return 1;
            }
            case 1024: {
                return 5;
            }
            case 16384: {
                return 9;
            }
            case 131072: {
                return 13;
            }
        }
        return 9;
    }

    @Override
    LRESULT wmDrawChild(long l2, long l3) {
        if ((this.style & 4) == 0) {
            return super.wmDrawChild(l2, l3);
        }
        DRAWITEMSTRUCT dRAWITEMSTRUCT = new DRAWITEMSTRUCT();
        OS.MoveMemory(dRAWITEMSTRUCT, l3, DRAWITEMSTRUCT.sizeof);
        RECT rECT = new RECT();
        OS.SetRect(rECT, dRAWITEMSTRUCT.left, dRAWITEMSTRUCT.top, dRAWITEMSTRUCT.right, dRAWITEMSTRUCT.bottom);
        if (OS.IsAppThemed()) {
            boolean bl = (dRAWITEMSTRUCT.itemState & 1) != 0;
            boolean bl2 = this.getEnabled();
            int n = Button.getThemeStateId(this.style, bl, bl2);
            OS.DrawThemeBackground(this.display.hScrollBarTheme(), dRAWITEMSTRUCT.hDC, 1, n, rECT, null);
        } else {
            int n = 2;
            switch (this.style & 0x24480) {
                case 128: {
                    n = 0;
                    break;
                }
                case 1024: {
                    n = 1;
                    break;
                }
                case 16384: {
                    n = 2;
                    break;
                }
                case 131072: {
                    n = 3;
                }
            }
            if (!this.getEnabled()) {
                n |= 0x100;
            }
            if ((this.style & 0x800000) == 0x800000) {
                n |= 0x4000;
            }
            if ((dRAWITEMSTRUCT.itemState & 1) != 0) {
                n |= 0x200;
            }
            OS.DrawFrameControl(dRAWITEMSTRUCT.hDC, rECT, 3, n);
        }
        return null;
    }

    static {
        Object object;
        COMMAND_LINK = false;
        STRING_WITH_ZERO_CHAR = new char[]{'0'};
        ButtonClass = new TCHAR(0, "BUTTON", true);
        long l2 = OS.LoadBitmap(0L, 32759L);
        if (l2 == 0L) {
            CHECK_WIDTH = OS.GetSystemMetrics(2);
            CHECK_HEIGHT = OS.GetSystemMetrics(20);
        } else {
            object = new BITMAP();
            OS.GetObject(l2, BITMAP.sizeof, (BITMAP)object);
            OS.DeleteObject(l2);
            CHECK_WIDTH = ((BITMAP)object).bmWidth / 4;
            CHECK_HEIGHT = ((BITMAP)object).bmHeight / 3;
        }
        object = new WNDCLASS();
        OS.GetClassInfo(0L, ButtonClass, (WNDCLASS)object);
        ButtonProc = ((WNDCLASS)object).lpfnWndProc;
    }
}

