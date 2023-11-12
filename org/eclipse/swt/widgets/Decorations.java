/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import java.io.Serializable;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.ACCEL;
import org.eclipse.swt.internal.win32.CREATESTRUCT;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.MENUITEMINFO;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.STARTUPINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WINDOWPLACEMENT;
import org.eclipse.swt.internal.win32.WINDOWPOS;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

public class Decorations
extends Canvas {
    Image image;
    Image smallImage;
    Image largeImage;
    Image[] images;
    Menu menuBar;
    Menu[] menus;
    Control savedFocus;
    Button defaultButton;
    Button saveDefault;
    int swFlags;
    int nAccel;
    long hAccel;
    boolean moved;
    boolean resized;
    boolean opened;
    int oldX = Integer.MIN_VALUE;
    int oldY = Integer.MIN_VALUE;
    int oldWidth = Integer.MIN_VALUE;
    int oldHeight = Integer.MIN_VALUE;
    RECT maxRect = new RECT();

    Decorations() {
    }

    public Decorations(Composite composite, int n) {
        super(composite, Decorations.checkStyle(n));
    }

    void _setMaximized(boolean bl) {
        int n = this.swFlags = bl ? 3 : 9;
        if (!OS.IsWindowVisible(this.handle)) {
            return;
        }
        if (bl == OS.IsZoomed(this.handle)) {
            return;
        }
        OS.ShowWindow(this.handle, this.swFlags);
        OS.UpdateWindow(this.handle);
    }

    void _setMinimized(boolean bl) {
        int n = this.swFlags = bl ? 7 : 9;
        if (!OS.IsWindowVisible(this.handle)) {
            return;
        }
        if (bl == OS.IsIconic(this.handle)) {
            return;
        }
        int n2 = this.swFlags;
        if (n2 == 7 && this.handle == OS.GetActiveWindow()) {
            n2 = 6;
        }
        OS.ShowWindow(this.handle, n2);
        OS.UpdateWindow(this.handle);
    }

    void addMenu(Menu menu) {
        if (this.menus == null) {
            this.menus = new Menu[4];
        }
        for (int i = 0; i < this.menus.length; ++i) {
            if (this.menus[i] != null) continue;
            this.menus[i] = menu;
            return;
        }
        Menu[] menuArray = new Menu[this.menus.length + 4];
        menuArray[this.menus.length] = menu;
        System.arraycopy(this.menus, 0, menuArray, 0, this.menus.length);
        this.menus = menuArray;
    }

    void bringToTop() {
        OS.BringWindowToTop(this.handle);
    }

    static int checkStyle(int n) {
        if ((n & 8) != 0) {
            n &= 0xFFFFF30F;
        } else if ((n & 0x800000) != 0) {
            n |= 0x20;
        }
        if ((n & 0x4C0) != 0) {
            n |= 0x20;
        }
        if ((n & 0x480) != 0) {
            n |= 0x40;
        }
        if ((n & 0x40) != 0) {
            n |= 0x20;
        }
        return n;
    }

    @Override
    void checkBorder() {
    }

    void checkComposited(Composite composite) {
    }

    @Override
    void checkOpened() {
        if (!this.opened) {
            this.resized = false;
        }
    }

    @Override
    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    @Override
    long callWindowProc(long l2, int n, long l3, long l4) {
        if (this.handle == 0L) {
            return 0L;
        }
        return OS.DefMDIChildProc(l2, n, l3, l4);
    }

    void closeWidget() {
        Event event = new Event();
        this.sendEvent(21, event);
        if (event.doit && !this.isDisposed()) {
            this.dispose();
        }
    }

    int compare(ImageData imageData, ImageData imageData2, int n, int n2, int n3) {
        int n4;
        int n5;
        int n6 = Math.abs(imageData.width - n);
        if (n6 != (n5 = Math.abs(imageData2.width - n))) {
            return n6 < n5 ? -1 : 1;
        }
        int n7 = imageData.getTransparencyType();
        if (n7 == (n4 = imageData2.getTransparencyType())) {
            if (imageData.depth == imageData2.depth) {
                return 0;
            }
            return imageData.depth > imageData2.depth && imageData.depth <= n3 ? -1 : 1;
        }
        if (n7 == 1) {
            return -1;
        }
        if (n4 == 1) {
            return 1;
        }
        if (n7 == 2) {
            return -1;
        }
        if (n4 == 2) {
            return 1;
        }
        if (n7 == 4) {
            return -1;
        }
        if (n4 == 4) {
            return 1;
        }
        return 0;
    }

    @Override
    Widget computeTabGroup() {
        return this;
    }

    @Override
    Control computeTabRoot() {
        return this;
    }

    @Override
    Rectangle computeTrimInPixels(int n, int n2, int n3, int n4) {
        RECT rECT;
        this.checkWidget();
        RECT rECT2 = new RECT();
        OS.SetRect(rECT2, n, n2, n + n3, n2 + n4);
        int n5 = OS.GetWindowLong(this.handle, -16);
        int n6 = OS.GetWindowLong(this.handle, -20);
        boolean bl = OS.GetMenu(this.handle) != 0L;
        OS.AdjustWindowRectEx(rECT2, n5, bl, n6);
        if (this.horizontalBar != null) {
            rECT = rECT2;
            rECT.bottom += OS.GetSystemMetrics(3);
        }
        if (this.verticalBar != null) {
            rECT = rECT2;
            rECT.right += OS.GetSystemMetrics(2);
        }
        if (bl) {
            rECT = new RECT();
            OS.SetRect(rECT, 0, 0, rECT2.right - rECT2.left, rECT2.bottom - rECT2.top);
            OS.SendMessage(this.handle, 131, 0L, rECT);
            while (rECT.bottom - rECT.top < n4 && rECT.bottom - rECT.top != 0) {
                RECT rECT3 = rECT2;
                rECT3.top -= OS.GetSystemMetrics(15) - OS.GetSystemMetrics(6);
                OS.SetRect(rECT, 0, 0, rECT2.right - rECT2.left, rECT2.bottom - rECT2.top);
                OS.SendMessage(this.handle, 131, 0L, rECT);
            }
        }
        return new Rectangle(rECT2.left, rECT2.top, rECT2.right - rECT2.left, rECT2.bottom - rECT2.top);
    }

    void createAccelerators() {
        boolean bl = false;
        this.nAccel = 0;
        this.hAccel = 0L;
        MenuItem[] menuItemArray = this.display.items;
        if (this.menuBar == null || menuItemArray == null) {
            return;
        }
        ACCEL aCCEL = new ACCEL();
        byte[] byArray = new byte[ACCEL.sizeof];
        byte[] byArray2 = new byte[menuItemArray.length * ACCEL.sizeof];
        for (MenuItem menuItem : menuItemArray) {
            Menu menu;
            if (menuItem == null || menuItem.accelerator == 0) continue;
            if (menu.parent != this) continue;
            for (menu = menuItem.parent; menu != null && menu != this.menuBar; menu = menu.getParentMenu()) {
            }
            if (menu != this.menuBar || !menuItem.fillAccel(aCCEL)) continue;
            OS.MoveMemory(byArray, aCCEL, ACCEL.sizeof);
            System.arraycopy(byArray, 0, byArray2, this.nAccel * ACCEL.sizeof, ACCEL.sizeof);
            ++this.nAccel;
        }
        if (this.nAccel != 0) {
            this.hAccel = OS.CreateAcceleratorTable(byArray2, this.nAccel);
        }
    }

    @Override
    void createHandle() {
        super.createHandle();
        if (this.parent != null || (this.style & 4) != 0) {
            this.setParent();
            this.setSystemMenu();
        }
    }

    @Override
    void createWidget() {
        super.createWidget();
        this.swFlags = 4;
        this.hAccel = -1L;
    }

    void destroyAccelerators() {
        if (this.hAccel != 0L && this.hAccel != -1L) {
            OS.DestroyAcceleratorTable(this.hAccel);
        }
        this.hAccel = -1L;
    }

    @Override
    public void dispose() {
        if (this.isDisposed()) {
            return;
        }
        if (!this.isValidThread()) {
            this.error(22);
        }
        if (!(this instanceof Shell)) {
            if (this < true) {
                Shell shell = this.getShell();
                shell.setFocus();
            }
            this.setVisible(false);
        }
        super.dispose();
    }

    Menu findMenu(long l2) {
        if (this.menus == null) {
            return null;
        }
        for (Menu menu : this.menus) {
            if (menu == null || l2 != menu.handle) continue;
            return menu;
        }
        return null;
    }

    void fixDecorations(Decorations decorations, Control control, Menu[] menuArray) {
        if (this == decorations) {
            return;
        }
        if (control == this.savedFocus) {
            this.savedFocus = null;
        }
        if (control == this.defaultButton) {
            this.defaultButton = null;
        }
        if (control == this.saveDefault) {
            this.saveDefault = null;
        }
        if (menuArray == null) {
            return;
        }
        Menu menu = control.menu;
        if (menu != null) {
            for (int i = 0; i < menuArray.length; ++i) {
                if (menuArray[i] != menu) continue;
                control.setMenu(null);
                return;
            }
            menu.fixMenus(decorations);
            this.destroyAccelerators();
            decorations.destroyAccelerators();
        }
    }

    @Override
    Rectangle getBoundsInPixels() {
        this.checkWidget();
        if (!OS.IsIconic(this.handle)) {
            return super.getBoundsInPixels();
        }
        WINDOWPLACEMENT wINDOWPLACEMENT = new WINDOWPLACEMENT();
        wINDOWPLACEMENT.length = WINDOWPLACEMENT.sizeof;
        OS.GetWindowPlacement(this.handle, wINDOWPLACEMENT);
        if ((wINDOWPLACEMENT.flags & 2) != 0) {
            int n = this.maxRect.right - this.maxRect.left;
            int n2 = this.maxRect.bottom - this.maxRect.top;
            return new Rectangle(this.maxRect.left, this.maxRect.top, n, n2);
        }
        int n = wINDOWPLACEMENT.right - wINDOWPLACEMENT.left;
        int n3 = wINDOWPLACEMENT.bottom - wINDOWPLACEMENT.top;
        return new Rectangle(wINDOWPLACEMENT.left, wINDOWPLACEMENT.top, n, n3);
    }

    @Override
    Rectangle getClientAreaInPixels() {
        this.checkWidget();
        if (!OS.IsIconic(this.handle)) {
            return super.getClientAreaInPixels();
        }
        WINDOWPLACEMENT wINDOWPLACEMENT = new WINDOWPLACEMENT();
        wINDOWPLACEMENT.length = WINDOWPLACEMENT.sizeof;
        OS.GetWindowPlacement(this.handle, wINDOWPLACEMENT);
        if ((wINDOWPLACEMENT.flags & 2) != 0) {
            return new Rectangle(0, 0, this.oldWidth, this.oldHeight);
        }
        int n = wINDOWPLACEMENT.right - wINDOWPLACEMENT.left;
        int n2 = wINDOWPLACEMENT.bottom - wINDOWPLACEMENT.top;
        if (this.horizontalBar != null) {
            n -= OS.GetSystemMetrics(3);
        }
        if (this.verticalBar != null) {
            n2 -= OS.GetSystemMetrics(2);
        }
        RECT rECT = new RECT();
        int n3 = OS.GetWindowLong(this.handle, -16);
        int n4 = OS.GetWindowLong(this.handle, -20);
        boolean bl = OS.GetMenu(this.handle) != 0L;
        OS.AdjustWindowRectEx(rECT, n3, bl, n4);
        n = Math.max(0, n - (rECT.right - rECT.left));
        n2 = Math.max(0, n2 - (rECT.bottom - rECT.top));
        return new Rectangle(0, 0, n, n2);
    }

    public Button getDefaultButton() {
        this.checkWidget();
        if (this.defaultButton != null && this.defaultButton.isDisposed()) {
            return null;
        }
        return this.defaultButton;
    }

    public Image getImage() {
        this.checkWidget();
        return this.image;
    }

    public Image[] getImages() {
        this.checkWidget();
        if (this.images == null) {
            return new Image[0];
        }
        Image[] imageArray = new Image[this.images.length];
        System.arraycopy(this.images, 0, imageArray, 0, this.images.length);
        return imageArray;
    }

    @Override
    Point getLocationInPixels() {
        this.checkWidget();
        if (!OS.IsIconic(this.handle)) {
            return super.getLocationInPixels();
        }
        WINDOWPLACEMENT wINDOWPLACEMENT = new WINDOWPLACEMENT();
        wINDOWPLACEMENT.length = WINDOWPLACEMENT.sizeof;
        OS.GetWindowPlacement(this.handle, wINDOWPLACEMENT);
        if ((wINDOWPLACEMENT.flags & 2) != 0) {
            return new Point(this.maxRect.left, this.maxRect.top);
        }
        return new Point(wINDOWPLACEMENT.left, wINDOWPLACEMENT.top);
    }

    public boolean getMaximized() {
        this.checkWidget();
        if (OS.IsWindowVisible(this.handle)) {
            return OS.IsZoomed(this.handle);
        }
        return this.swFlags == 3;
    }

    public Menu getMenuBar() {
        this.checkWidget();
        return this.menuBar;
    }

    public boolean getMinimized() {
        this.checkWidget();
        if (OS.IsWindowVisible(this.handle)) {
            return OS.IsIconic(this.handle);
        }
        return this.swFlags == 7;
    }

    @Override
    String getNameText() {
        return this.getText();
    }

    @Override
    Point getSizeInPixels() {
        this.checkWidget();
        if (!OS.IsIconic(this.handle)) {
            return super.getSizeInPixels();
        }
        WINDOWPLACEMENT wINDOWPLACEMENT = new WINDOWPLACEMENT();
        wINDOWPLACEMENT.length = WINDOWPLACEMENT.sizeof;
        OS.GetWindowPlacement(this.handle, wINDOWPLACEMENT);
        if ((wINDOWPLACEMENT.flags & 2) != 0) {
            int n = this.maxRect.right - this.maxRect.left;
            int n2 = this.maxRect.bottom - this.maxRect.top;
            return new Point(n, n2);
        }
        int n = wINDOWPLACEMENT.right - wINDOWPLACEMENT.left;
        int n3 = wINDOWPLACEMENT.bottom - wINDOWPLACEMENT.top;
        return new Point(n, n3);
    }

    public String getText() {
        this.checkWidget();
        int n = OS.GetWindowTextLength(this.handle);
        if (n == 0) {
            return "";
        }
        char[] cArray = new char[n + 1];
        OS.GetWindowText(this.handle, cArray, n + 1);
        return new String(cArray, 0, n);
    }

    @Override
    public boolean isReparentable() {
        this.checkWidget();
        return false;
    }

    boolean isTabGroup() {
        return true;
    }

    boolean isTabItem() {
        return false;
    }

    @Override
    Decorations menuShell() {
        return this;
    }

    @Override
    void releaseChildren(boolean bl) {
        if (this.menuBar != null) {
            this.menuBar.release(false);
            this.menuBar = null;
        }
        super.releaseChildren(bl);
        if (this.menus != null) {
            for (Menu menu : this.menus) {
                if (menu == null || menu.isDisposed()) continue;
                menu.dispose();
            }
            this.menus = null;
        }
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        if (this.smallImage != null) {
            this.smallImage.dispose();
        }
        if (this.largeImage != null) {
            this.largeImage.dispose();
        }
        Object var1_1 = null;
        this.image = var1_1;
        this.largeImage = var1_1;
        this.smallImage = var1_1;
        this.images = null;
        this.savedFocus = null;
        Object var2_2 = null;
        this.saveDefault = var2_2;
        this.defaultButton = var2_2;
        if (this.hAccel != 0L && this.hAccel != -1L) {
            OS.DestroyAcceleratorTable(this.hAccel);
        }
        this.hAccel = -1L;
    }

    void removeMenu(Menu menu) {
        if (this.menus == null) {
            return;
        }
        for (int i = 0; i < this.menus.length; ++i) {
            if (this.menus[i] != menu) continue;
            this.menus[i] = null;
            return;
        }
    }

    @Override
    void reskinChildren(int n) {
        if (this.menuBar != null) {
            this.menuBar.reskin(n);
        }
        if (this.menus != null) {
            for (Menu menu : this.menus) {
                if (menu == null) continue;
                menu.reskin(n);
            }
        }
        super.reskinChildren(n);
    }

    void saveFocus() {
        Control control = this.display._getFocusControl();
        if (control != null && control != this && this == control.menuShell()) {
            this.setSavedFocus(control);
        }
    }

    @Override
    void setBoundsInPixels(int n, int n2, int n3, int n4, int n5, boolean bl) {
        this.swFlags = 4;
        if (OS.IsIconic(this.handle)) {
            this.setPlacement(n, n2, n3, n4, n5);
            return;
        }
        this.forceResize();
        RECT rECT = new RECT();
        OS.GetWindowRect(this.handle, rECT);
        boolean bl2 = true;
        if ((2 & n5) == 0) {
            boolean bl3 = bl2 = rECT.left == n && rECT.top == n2;
            if (!bl2) {
                this.moved = true;
            }
        }
        boolean bl4 = true;
        if ((1 & n5) == 0) {
            boolean bl5 = bl4 = rECT.right - rECT.left == n3 && rECT.bottom - rECT.top == n4;
            if (!bl4) {
                this.resized = true;
            }
        }
        if (!OS.IsZoomed(this.handle)) {
            super.setBoundsInPixels(n, n2, n3, n4, n5, bl);
            return;
        }
        if (bl2 && bl4) {
            return;
        }
        this.setPlacement(n, n2, n3, n4, n5);
        this._setMaximized(false);
    }

    public void setDefaultButton(Button button) {
        this.checkWidget();
        if (button != null) {
            if (button.isDisposed()) {
                this.error(5);
            }
            if (button.menuShell() != this) {
                this.error(32);
            }
        }
        this.setDefaultButton(button, true);
    }

    void setDefaultButton(Button button, boolean bl) {
        if (button == null) {
            if (this.defaultButton == this.saveDefault) {
                if (bl) {
                    this.saveDefault = null;
                }
                return;
            }
        } else {
            if ((button.style & 8) == 0) {
                return;
            }
            if (button == this.defaultButton) {
                if (bl) {
                    this.saveDefault = this.defaultButton;
                }
                return;
            }
        }
        if (this.defaultButton != null && !this.defaultButton.isDisposed()) {
            this.defaultButton.setDefault(false);
        }
        if ((this.defaultButton = button) == null) {
            this.defaultButton = this.saveDefault;
        }
        if (this.defaultButton != null && !this.defaultButton.isDisposed()) {
            this.defaultButton.setDefault(true);
        }
        if (bl) {
            this.saveDefault = this.defaultButton;
        }
        if (this.saveDefault != null && this.saveDefault.isDisposed()) {
            this.saveDefault = null;
        }
    }

    public void setImage(Image image) {
        this.checkWidget();
        if (image != null && image.isDisposed()) {
            this.error(5);
        }
        this.image = image;
        this.setImages(this.image, null);
    }

    void setImages(Image image, Image[] imageArray) {
        int n;
        if (this.smallImage != null) {
            this.smallImage.dispose();
        }
        if (this.largeImage != null) {
            this.largeImage.dispose();
        }
        Object var3_3 = null;
        this.largeImage = var3_3;
        this.smallImage = var3_3;
        long l2 = 0L;
        long l3 = 0L;
        Image image2 = null;
        Image image3 = null;
        if (image != null) {
            image3 = image;
            image2 = image;
        } else if (imageArray != null && imageArray.length > 0) {
            n = this.display.getIconDepth();
            ImageData[] imageDataArray = null;
            if (imageArray.length > 1) {
                Image[] imageArray2 = new Image[imageArray.length];
                System.arraycopy(imageArray, 0, imageArray2, 0, imageArray.length);
                imageDataArray = new ImageData[imageArray.length];
                for (int i = 0; i < imageDataArray.length; ++i) {
                    imageDataArray[i] = imageArray[i].getImageData(DPIUtil.getDeviceZoom());
                }
                imageArray = imageArray2;
                this.sort(imageArray, imageDataArray, OS.GetSystemMetrics(49), OS.GetSystemMetrics(50), n);
            }
            image2 = imageArray[0];
            if (imageArray.length > 1) {
                this.sort(imageArray, imageDataArray, OS.GetSystemMetrics(11), OS.GetSystemMetrics(12), n);
            }
            image3 = imageArray[0];
        }
        if (image2 != null) {
            switch (image2.type) {
                case 0: {
                    this.smallImage = Display.createIcon(image2);
                    l2 = this.smallImage.handle;
                    break;
                }
                case 1: {
                    l2 = image2.handle;
                }
            }
        }
        OS.SendMessage(this.handle, 128, 0L, l2);
        if (image3 != null) {
            switch (image3.type) {
                case 0: {
                    this.largeImage = Display.createIcon(image3);
                    l3 = this.largeImage.handle;
                    break;
                }
                case 1: {
                    l3 = image3.handle;
                }
            }
        }
        OS.SendMessage(this.handle, 128, 1L, l3);
        if (l2 == 0L && l3 == 0L && (this.style & 0x800) != 0) {
            n = 1025;
            OS.RedrawWindow(this.handle, null, 0L, 1025);
        }
    }

    public void setImages(Image[] imageArray) {
        this.checkWidget();
        if (imageArray == null) {
            this.error(5);
        }
        for (Image image : imageArray) {
            if (image != null && !image.isDisposed()) continue;
            this.error(5);
        }
        this.images = imageArray;
        this.setImages(null, imageArray);
    }

    public void setMaximized(boolean bl) {
        this.checkWidget();
        Display.lpStartupInfo = null;
        this._setMaximized(bl);
    }

    public void setMenuBar(Menu menu) {
        this.checkWidget();
        if (this.menuBar == menu) {
            return;
        }
        if (menu != null) {
            if (menu.isDisposed()) {
                this.error(5);
            }
            if ((menu.style & 2) == 0) {
                this.error(33);
            }
            if (menu.parent != this) {
                this.error(32);
            }
        }
        if (menu != null) {
            this.display.removeBar(menu);
        }
        this.menuBar = menu;
        long l2 = this.menuBar != null ? this.menuBar.handle : 0L;
        OS.SetMenu(this.handle, l2);
        this.destroyAccelerators();
    }

    public void setMinimized(boolean bl) {
        this.checkWidget();
        Display.lpStartupInfo = null;
        this._setMinimized(bl);
    }

    @Override
    public void setOrientation(int n) {
        super.setOrientation(n);
        if (this.menus != null) {
            for (Menu menu : this.menus) {
                if (menu == null || menu.isDisposed() || (menu.getStyle() & 8) == 0) continue;
                menu._setOrientation(menu.getOrientation());
            }
        }
    }

    void setParent() {
        long l2 = this.parent.handle;
        this.display.lockActiveWindow = true;
        OS.SetParent(this.handle, l2);
        if (!OS.IsWindowVisible(l2)) {
            OS.ShowWindow(this.handle, 8);
        }
        int n = OS.GetWindowLong(this.handle, -16);
        OS.SetWindowLong(this.handle, -16, (n &= 0xBFFFFFFF) | Integer.MIN_VALUE);
        OS.SetWindowLongPtr(this.handle, -12, 0L);
        int n2 = 19;
        OS.SetWindowPos(this.handle, 1L, 0, 0, 0, 0, 19);
        this.display.lockActiveWindow = false;
    }

    void setPlacement(int n, int n2, int n3, int n4, int n5) {
        WINDOWPLACEMENT wINDOWPLACEMENT = new WINDOWPLACEMENT();
        wINDOWPLACEMENT.length = WINDOWPLACEMENT.sizeof;
        OS.GetWindowPlacement(this.handle, wINDOWPLACEMENT);
        wINDOWPLACEMENT.showCmd = 8;
        if (OS.IsIconic(this.handle)) {
            wINDOWPLACEMENT.showCmd = 7;
        } else if (OS.IsZoomed(this.handle)) {
            wINDOWPLACEMENT.showCmd = 3;
        }
        boolean bl = true;
        if ((n5 & 2) == 0) {
            bl = wINDOWPLACEMENT.left != n || wINDOWPLACEMENT.top != n2;
            wINDOWPLACEMENT.right = n + (wINDOWPLACEMENT.right - wINDOWPLACEMENT.left);
            wINDOWPLACEMENT.bottom = n2 + (wINDOWPLACEMENT.bottom - wINDOWPLACEMENT.top);
            wINDOWPLACEMENT.left = n;
            wINDOWPLACEMENT.top = n2;
        }
        boolean bl2 = true;
        if ((n5 & 1) == 0) {
            bl2 = wINDOWPLACEMENT.right - wINDOWPLACEMENT.left != n3 || wINDOWPLACEMENT.bottom - wINDOWPLACEMENT.top != n4;
            wINDOWPLACEMENT.right = wINDOWPLACEMENT.left + n3;
            wINDOWPLACEMENT.bottom = wINDOWPLACEMENT.top + n4;
        }
        OS.SetWindowPlacement(this.handle, wINDOWPLACEMENT);
        if (OS.IsIconic(this.handle)) {
            Serializable serializable;
            if (bl) {
                this.moved = true;
                serializable = this.getLocationInPixels();
                this.oldX = serializable.x;
                this.oldY = serializable.y;
                this.sendEvent(10);
                if (this.isDisposed()) {
                    return;
                }
            }
            if (bl2) {
                this.resized = true;
                serializable = this.getClientAreaInPixels();
                this.oldWidth = ((Rectangle)serializable).width;
                this.oldHeight = ((Rectangle)serializable).height;
                this.sendEvent(11);
                if (this.isDisposed()) {
                    return;
                }
                if (this.layout != null) {
                    this.markLayout(false, false);
                    this.updateLayout(true, false);
                }
            }
        }
    }

    void setSavedFocus(Control control) {
        this.savedFocus = control;
    }

    void setSystemMenu() {
        long l2 = OS.GetSystemMenu(this.handle, false);
        if (l2 == 0L) {
            return;
        }
        int n = OS.GetMenuItemCount(l2);
        if ((this.style & 0x10) == 0) {
            OS.DeleteMenu(l2, 61440, 0);
        }
        if ((this.style & 0x80) == 0) {
            OS.DeleteMenu(l2, 61472, 0);
        }
        if ((this.style & 0x400) == 0) {
            OS.DeleteMenu(l2, 61488, 0);
        }
        if ((this.style & 0x480) == 0) {
            OS.DeleteMenu(l2, 61728, 0);
        }
        int n2 = OS.GetMenuItemCount(l2);
        if ((this.style & 0x40) == 0 || n2 != n) {
            int n3;
            OS.DeleteMenu(l2, 61744, 0);
            MENUITEMINFO mENUITEMINFO = new MENUITEMINFO();
            mENUITEMINFO.cbSize = MENUITEMINFO.sizeof;
            mENUITEMINFO.fMask = 2;
            for (n3 = 0; !(n3 >= n2 || OS.GetMenuItemInfo(l2, n3, true, mENUITEMINFO) && mENUITEMINFO.wID == 61536); ++n3) {
            }
            if (n3 != n2) {
                OS.DeleteMenu(l2, n3 - 1, 1024);
                if ((this.style & 0x40) == 0) {
                    OS.DeleteMenu(l2, 61536, 0);
                }
            }
        }
    }

    public void setText(String string) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        TCHAR tCHAR = new TCHAR(0, string, true);
        if ((this.state & 0x4000) != 0) {
            long l2 = OS.GetProcessHeap();
            int n = tCHAR.length() * 2;
            long l3 = OS.HeapAlloc(l2, 8, n);
            OS.MoveMemory(l3, tCHAR, n);
            OS.DefWindowProc(this.handle, 12, 0L, l3);
            if (l3 != 0L) {
                OS.HeapFree(l2, 0, l3);
            }
        } else {
            OS.SetWindowText(this.handle, tCHAR);
        }
        if ((this.state & 0x400000) != 0) {
            this.updateTextDirection(0x6000000);
        }
    }

    @Override
    public void setVisible(boolean bl) {
        this.checkWidget();
        if (!this.getDrawing() ? (this.state & 0x10) == 0 == bl : bl == OS.IsWindowVisible(this.handle)) {
            return;
        }
        if (bl) {
            this.sendEvent(22);
            if (this.isDisposed()) {
                return;
            }
            if (!this.getDrawing()) {
                this.state &= 0xFFFFFFEF;
            } else {
                Serializable serializable;
                STARTUPINFO sTARTUPINFO;
                if (this.menuBar != null) {
                    this.display.removeBar(this.menuBar);
                    OS.DrawMenuBar(this.handle);
                }
                if ((sTARTUPINFO = Display.lpStartupInfo) != null && (sTARTUPINFO.dwFlags & 1) != 0) {
                    OS.ShowWindow(this.handle, sTARTUPINFO.wShowWindow);
                } else {
                    OS.ShowWindow(this.handle, this.swFlags);
                }
                if (this.isDisposed()) {
                    return;
                }
                this.opened = true;
                if (!this.moved) {
                    this.moved = true;
                    serializable = this.getLocationInPixels();
                    this.oldX = serializable.x;
                    this.oldY = serializable.y;
                }
                if (!this.resized) {
                    this.resized = true;
                    serializable = this.getClientAreaInPixels();
                    this.oldWidth = ((Rectangle)serializable).width;
                    this.oldHeight = ((Rectangle)serializable).height;
                }
                if (OS.IsAppThemed() || !OS.IsHungAppWindow(this.handle)) {
                    OS.UpdateWindow(this.handle);
                }
            }
        } else {
            this.swFlags = OS.IsIconic(this.handle) ? 7 : (OS.IsZoomed(this.handle) ? 3 : 4);
            if (!this.getDrawing()) {
                this.state |= 0x10;
            } else {
                OS.ShowWindow(this.handle, 0);
            }
            if (this.isDisposed()) {
                return;
            }
            this.sendEvent(23);
        }
    }

    void sort(Image[] imageArray, ImageData[] imageDataArray, int n, int n2, int n3) {
        int n4 = imageArray.length;
        if (n4 <= 1) {
            return;
        }
        for (int i = n4 / 2; i > 0; i /= 2) {
            for (int j = i; j < n4; ++j) {
                for (int k = j - i; k >= 0; k -= i) {
                    if (this.compare(imageDataArray[k], imageDataArray[k + i], n, n2, n3) < 0) continue;
                    Image image = imageArray[k];
                    imageArray[k] = imageArray[k + i];
                    imageArray[k + i] = image;
                    ImageData imageData = imageDataArray[k];
                    imageDataArray[k] = imageDataArray[k + i];
                    imageDataArray[k + i] = imageData;
                }
            }
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    boolean translateAccelerator(MSG var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl24 : ICONST_1 - null : trying to set 1 previously set to 2
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

    @Override
    boolean traverseItem(boolean bl) {
        return false;
    }

    @Override
    boolean traverseReturn() {
        if (this.defaultButton == null || this.defaultButton.isDisposed()) {
            return false;
        }
        if (!this.defaultButton.isVisible() || !this.defaultButton.isEnabled()) {
            return false;
        }
        this.defaultButton.click();
        return true;
    }

    @Override
    CREATESTRUCT widgetCreateStruct() {
        return new CREATESTRUCT();
    }

    @Override
    int widgetExtStyle() {
        int n = super.widgetExtStyle() | 0x40;
        n &= 0xFFFFFDFF;
        if ((this.style & 8) != 0) {
            return n;
        }
        if ((this.style & 0x10) != 0) {
            return n;
        }
        if ((this.style & 0x800) != 0) {
            n |= 1;
        }
        return n;
    }

    @Override
    long widgetParent() {
        Shell shell = this.getShell();
        return shell.hwndMDIClient();
    }

    @Override
    int widgetStyle() {
        int n = super.widgetStyle() & 0xEFFEFFFF;
        n &= 0xFF7FFFFF;
        if ((this.style & 8) != 0) {
            if (this.parent == null) {
                n |= 0xA0000;
            }
            return n;
        }
        if ((this.style & 0x20) != 0) {
            n |= 0xC00000;
        }
        if ((this.style & 0x80) != 0) {
            n |= 0x20000;
        }
        if ((this.style & 0x400) != 0) {
            n |= 0x10000;
        }
        if ((this.style & 0x10) != 0) {
            n |= 0x40000;
        } else if ((this.style & 0x800) == 0) {
            n |= 0x800000;
        }
        if ((this.style & 0x40) != 0) {
            n |= 0x80000;
        }
        return n;
    }

    @Override
    long windowProc(long l2, int n, long l3, long l4) {
        switch (n) {
            case 32768: 
            case 32769: {
                if (this.hAccel == -1L) {
                    this.createAccelerators();
                }
                return n == 32768 ? (long)this.nAccel : this.hAccel;
            }
        }
        return super.windowProc(l2, n, l3, l4);
    }

    @Override
    LRESULT WM_ACTIVATE(long l2, long l3) {
        int n;
        char[] cArray;
        Object object;
        LRESULT lRESULT = super.WM_ACTIVATE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (OS.GetParent(l3) == this.handle && ((String)(object = new String(cArray = new char[128], 0, n = OS.GetClassName(l3, cArray, cArray.length)))).equals("SunAwtWindow")) {
            return LRESULT.ZERO;
        }
        int n2 = OS.LOWORD(l2);
        if (n2 != 0) {
            if (OS.HIWORD(l2) != 0) {
                return lRESULT;
            }
            Control control = this.display.findControl(l3);
            if ((control == null || control instanceof Shell) && this instanceof Shell) {
                object = new Event();
                ((Event)object).detail = n2 == 2 ? 3 : 0;
                this.sendEvent(26, (Event)object);
                if (this.isDisposed()) {
                    return LRESULT.ZERO;
                }
            }
            if (this != false) {
                return LRESULT.ZERO;
            }
        } else {
            Control control;
            Display display = this.display;
            boolean bl = display.isXMouseActive();
            if (bl) {
                display.lockActiveWindow = true;
            }
            if (((control = display.findControl(l3)) == null || control instanceof Shell) && this instanceof Shell) {
                this.sendEvent(27);
                if (!this.isDisposed()) {
                    Shell shell = this.getShell();
                    shell.setActiveControl(null);
                }
            }
            if (bl) {
                display.lockActiveWindow = false;
            }
            if (this.isDisposed()) {
                return LRESULT.ZERO;
            }
            this.saveFocus();
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_CLOSE(long l2, long l3) {
        LRESULT lRESULT = super.WM_CLOSE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (this.isEnabled() && this.isActive()) {
            this.closeWidget();
        }
        return LRESULT.ZERO;
    }

    @Override
    LRESULT WM_KILLFOCUS(long l2, long l3) {
        LRESULT lRESULT = super.WM_KILLFOCUS(l2, l3);
        this.saveFocus();
        return lRESULT;
    }

    @Override
    LRESULT WM_MOVE(long l2, long l3) {
        if (this.moved) {
            Point point = this.getLocationInPixels();
            if (point.x == this.oldX && point.y == this.oldY) {
                return null;
            }
            this.oldX = point.x;
            this.oldY = point.y;
        }
        return super.WM_MOVE(l2, l3);
    }

    @Override
    LRESULT WM_NCACTIVATE(long l2, long l3) {
        LRESULT lRESULT = super.WM_NCACTIVATE(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (l2 == 0L) {
            if (this.display.lockActiveWindow) {
                return LRESULT.ZERO;
            }
            Control control = this.display.findControl(l3);
            if (control != null) {
                Shell shell = this.getShell();
                Decorations decorations = control.menuShell();
                if (decorations.getShell() == shell) {
                    if (this instanceof Shell) {
                        return LRESULT.ONE;
                    }
                    if (this.display.ignoreRestoreFocus && this.display.lastHittest != 1) {
                        lRESULT = LRESULT.ONE;
                    }
                }
            }
        }
        if (!(this instanceof Shell)) {
            long l4 = this.getShell().handle;
            OS.SendMessage(l4, 134, l2, l3);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_QUERYOPEN(long l2, long l3) {
        LRESULT lRESULT = super.WM_QUERYOPEN(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        this.sendEvent(20);
        return lRESULT;
    }

    @Override
    LRESULT WM_SETFOCUS(long l2, long l3) {
        LRESULT lRESULT = super.WM_SETFOCUS(l2, l3);
        if (this.isDisposed()) {
            return lRESULT;
        }
        if (this.savedFocus != this) {
            this.restoreFocus();
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SIZE(long l2, long l3) {
        LRESULT lRESULT = null;
        boolean bl = true;
        if (this.resized) {
            int n = 0;
            int n2 = 0;
            switch ((int)l2) {
                case 2: {
                    OS.GetWindowRect(this.handle, this.maxRect);
                }
                case 0: {
                    n = OS.LOWORD(l3);
                    n2 = OS.HIWORD(l3);
                    break;
                }
                case 1: {
                    Rectangle rectangle = this.getClientAreaInPixels();
                    n = rectangle.width;
                    n2 = rectangle.height;
                    break;
                }
            }
            boolean bl2 = bl = n != this.oldWidth || n2 != this.oldHeight;
            if (bl) {
                this.oldWidth = n;
                this.oldHeight = n2;
            }
        }
        if (bl) {
            lRESULT = super.WM_SIZE(l2, l3);
            if (this.isDisposed()) {
                return lRESULT;
            }
        }
        if (l2 == 1L) {
            this.sendEvent(19);
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SYSCOMMAND(long l2, long l3) {
        LRESULT lRESULT = super.WM_SYSCOMMAND(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (!(this instanceof Shell)) {
            int n = (int)l2 & 0xFFF0;
            switch (n) {
                case 61536: {
                    OS.PostMessage(this.handle, 16, 0L, 0L);
                    return LRESULT.ZERO;
                }
                case 61504: {
                    this.traverseDecorations(true);
                    return LRESULT.ZERO;
                }
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_WINDOWPOSCHANGING(long l2, long l3) {
        LRESULT lRESULT = super.WM_WINDOWPOSCHANGING(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (this.display.lockActiveWindow) {
            WINDOWPOS wINDOWPOS = new WINDOWPOS();
            OS.MoveMemory(wINDOWPOS, l3, WINDOWPOS.sizeof);
            WINDOWPOS wINDOWPOS2 = wINDOWPOS;
            wINDOWPOS2.flags |= 4;
            OS.MoveMemory(l3, wINDOWPOS, WINDOWPOS.sizeof);
        }
        return lRESULT;
    }
}

