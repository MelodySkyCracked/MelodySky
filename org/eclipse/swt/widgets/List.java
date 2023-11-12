/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.BidiUtil;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.LRESULT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SCROLLINFO;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.WNDCLASS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.TypedListener;
import org.eclipse.swt.widgets.Widget;

public class List
extends Scrollable {
    static final int INSET = 3;
    static final long ListProc;
    static final TCHAR ListClass;
    boolean addedUCC = false;

    public List(Composite composite, int n) {
        super(composite, List.checkStyle(n));
    }

    public void add(String string) {
        TCHAR tCHAR;
        int n;
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        if ((n = (int)OS.SendMessage(this.handle, 384, 0L, tCHAR = new TCHAR(this.getCodePage(), string, true))) == -1) {
            this.error(14);
        }
        if (n == -2) {
            this.error(14);
        }
        if ((this.style & 0x100) != 0) {
            this.setScrollWidth(tCHAR.chars, true);
        }
    }

    public void add(String string, int n) {
        TCHAR tCHAR;
        int n2;
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        if (n == -1) {
            this.error(6);
        }
        if ((n2 = (int)OS.SendMessage(this.handle, 385, (long)n, tCHAR = new TCHAR(this.getCodePage(), string, true))) == -2) {
            this.error(14);
        }
        if (n2 == -1) {
            int n3 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
            if (0 <= n && n <= n3) {
                this.error(14);
            } else {
                this.error(6);
            }
        }
        if ((this.style & 0x100) != 0) {
            this.setScrollWidth(tCHAR.chars, true);
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
        boolean bl = false;
        switch (n) {
            case 276: 
            case 277: {
                boolean bl2 = bl = this.findImageControl() != null && this.getDrawing() && OS.IsWindowVisible(this.handle);
                if (!bl) break;
                OS.DefWindowProc(this.handle, 11, 0L, 0L);
            }
        }
        long l5 = OS.CallWindowProc(ListProc, l2, n, l3, l4);
        switch (n) {
            case 276: 
            case 277: {
                if (!bl) break;
                OS.DefWindowProc(this.handle, 11, 1L, 0L);
                OS.InvalidateRect(this.handle, null, true);
            }
        }
        return l5;
    }

    static int checkStyle(int n) {
        return Widget.checkBits(n, 4, 2, 0, 0, 0, 0);
    }

    @Override
    Point computeSizeInPixels(int n, int n2, boolean bl) {
        int n3;
        this.checkWidget();
        int n4 = 0;
        int n5 = 0;
        if (n == -1) {
            if ((this.style & 0x100) != 0) {
                n4 = (int)OS.SendMessage(this.handle, 403, 0L, 0L);
                n4 -= 3;
            } else {
                n3 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
                long l2 = 0L;
                long l3 = OS.GetDC(this.handle);
                long l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
                if (l4 != 0L) {
                    l2 = OS.SelectObject(l3, l4);
                }
                RECT rECT = new RECT();
                int n6 = 3104;
                char[] cArray = new char[65];
                for (int i = 0; i < n3; ++i) {
                    int n7;
                    int n8 = (int)OS.SendMessage(this.handle, 394, (long)i, 0L);
                    if (n8 == -1) continue;
                    if (n8 + 1 > cArray.length) {
                        cArray = new char[n8 + 1];
                    }
                    if ((n7 = (int)OS.SendMessage(this.handle, 393, (long)i, cArray)) == -1) continue;
                    OS.DrawText(l3, cArray, n8, rECT, 3104);
                    n4 = Math.max(n4, rECT.right - rECT.left);
                }
                if (l4 != 0L) {
                    OS.SelectObject(l3, l2);
                }
                OS.ReleaseDC(this.handle, l3);
            }
        }
        if (n2 == -1) {
            n3 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
            int n9 = (int)OS.SendMessage(this.handle, 417, 0L, 0L);
            n5 = n3 * n9;
        }
        if (n4 == 0) {
            n4 = 64;
        }
        if (n5 == 0) {
            n5 = 64;
        }
        if (n != -1) {
            n4 = n;
        }
        if (n2 != -1) {
            n5 = n2;
        }
        n3 = this.getBorderWidthInPixels();
        n4 += n3 * 2 + 3;
        n5 += n3 * 2;
        if ((this.style & 0x200) != 0) {
            n4 += OS.GetSystemMetrics(2);
        }
        if ((this.style & 0x100) != 0) {
            n5 += OS.GetSystemMetrics(3);
        }
        return new Point(n4, n5);
    }

    @Override
    int defaultBackground() {
        return OS.GetSysColor(5);
    }

    public void deselect(int[] nArray) {
        this.checkWidget();
        if (nArray == null) {
            this.error(4);
        }
        if (nArray.length == 0) {
            return;
        }
        if ((this.style & 4) == 0) {
            for (int n : nArray) {
                if (n == -1) continue;
                OS.SendMessage(this.handle, 389, 0L, n);
            }
            return;
        }
        int n = (int)OS.SendMessage(this.handle, 392, 0L, 0L);
        if (n == -1) {
            return;
        }
        for (int n2 : nArray) {
            if (n != n2) continue;
            OS.SendMessage(this.handle, 390, -1L, 0L);
            return;
        }
    }

    public void deselect(int n) {
        this.checkWidget();
        if (n == -1) {
            return;
        }
        if ((this.style & 4) == 0) {
            OS.SendMessage(this.handle, 389, 0L, n);
            return;
        }
        int n2 = (int)OS.SendMessage(this.handle, 392, 0L, 0L);
        if (n2 == -1) {
            return;
        }
        if (n2 == n) {
            OS.SendMessage(this.handle, 390, -1L, 0L);
        }
    }

    public void deselect(int n, int n2) {
        this.checkWidget();
        if (n > n2) {
            return;
        }
        if ((this.style & 4) != 0) {
            int n3 = (int)OS.SendMessage(this.handle, 392, 0L, 0L);
            if (n3 == -1) {
                return;
            }
            if (n <= n3 && n3 <= n2) {
                OS.SendMessage(this.handle, 390, -1L, 0L);
            }
        } else {
            int n4 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
            if (n < 0 && n2 < 0) {
                return;
            }
            if (n >= n4 && n2 >= n4) {
                return;
            }
            n = Math.min(n4 - 1, Math.max(0, n));
            n2 = Math.min(n4 - 1, Math.max(0, n2));
            OS.SendMessage(this.handle, 387, (long)n2, n);
        }
    }

    public void deselectAll() {
        this.checkWidget();
        if ((this.style & 4) != 0) {
            OS.SendMessage(this.handle, 390, -1L, 0L);
        } else {
            OS.SendMessage(this.handle, 389, 0L, -1L);
        }
    }

    public int getFocusIndex() {
        int n;
        this.checkWidget();
        int n2 = (int)OS.SendMessage(this.handle, 415, 0L, 0L);
        if (n2 == 0 && (n = (int)OS.SendMessage(this.handle, 395, 0L, 0L)) == 0) {
            return -1;
        }
        return n2;
    }

    public String getItem(int n) {
        char[] cArray;
        int n2;
        this.checkWidget();
        int n3 = (int)OS.SendMessage(this.handle, 394, (long)n, 0L);
        if (n3 != -1 && (n2 = (int)OS.SendMessage(this.handle, 393, (long)n, cArray = new char[n3 + 1])) != -1) {
            return (this.state & 0x400000) != 0 ? new String(cArray, 1, n3 - 1) : new String(cArray, 0, n3);
        }
        int n4 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
        if (0 <= n && n < n4) {
            this.error(8);
        }
        this.error(6);
        return "";
    }

    public int getItemCount() {
        this.checkWidget();
        int n = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
        if (n == -1) {
            this.error(36);
        }
        return n;
    }

    public int getItemHeight() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getItemHeightInPixels());
    }

    int getItemHeightInPixels() {
        int n = (int)OS.SendMessage(this.handle, 417, 0L, 0L);
        if (n == -1) {
            this.error(11);
        }
        return n;
    }

    public String[] getItems() {
        this.checkWidget();
        int n = this.getItemCount();
        String[] stringArray = new String[n];
        for (int i = 0; i < n; ++i) {
            stringArray[i] = this.getItem(i);
        }
        return stringArray;
    }

    public String[] getSelection() {
        this.checkWidget();
        int[] nArray = this.getSelectionIndices();
        String[] stringArray = new String[nArray.length];
        for (int i = 0; i < nArray.length; ++i) {
            stringArray[i] = this.getItem(nArray[i]);
        }
        return stringArray;
    }

    public int getSelectionCount() {
        this.checkWidget();
        if ((this.style & 4) == 0) {
            int n = (int)OS.SendMessage(this.handle, 400, 0L, 0L);
            if (n == -1) {
                this.error(36);
            }
            return n;
        }
        int n = (int)OS.SendMessage(this.handle, 392, 0L, 0L);
        if (n == -1) {
            return 0;
        }
        return 1;
    }

    public int getSelectionIndex() {
        this.checkWidget();
        if ((this.style & 4) != 0) {
            return (int)OS.SendMessage(this.handle, 392, 0L, 0L);
        }
        int n = (int)OS.SendMessage(this.handle, 400, 0L, 0L);
        if (n == -1) {
            this.error(9);
        }
        if (n == 0) {
            return -1;
        }
        int n2 = (int)OS.SendMessage(this.handle, 415, 0L, 0L);
        int n3 = (int)OS.SendMessage(this.handle, 391, (long)n2, 0L);
        if (n3 == -1) {
            this.error(9);
        }
        if (n3 != 0) {
            return n2;
        }
        int[] nArray = new int[]{0};
        n3 = (int)OS.SendMessage(this.handle, 401, 1L, nArray);
        if (n3 != 1) {
            this.error(9);
        }
        return nArray[0];
    }

    public int[] getSelectionIndices() {
        this.checkWidget();
        if ((this.style & 4) == 0) {
            int[] nArray;
            int n;
            int n2 = (int)OS.SendMessage(this.handle, 400, 0L, 0L);
            if (n2 == -1) {
                this.error(9);
            }
            if ((n = (int)OS.SendMessage(this.handle, 401, (long)n2, nArray = new int[n2])) != n2) {
                this.error(9);
            }
            return nArray;
        }
        int n = (int)OS.SendMessage(this.handle, 392, 0L, 0L);
        if (n == -1) {
            return new int[0];
        }
        return new int[]{n};
    }

    public int getTopIndex() {
        this.checkWidget();
        return (int)OS.SendMessage(this.handle, 398, 0L, 0L);
    }

    public int indexOf(String string) {
        return this.indexOf(string, 0);
    }

    public int indexOf(String string, int n) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        if (string.length() == 0) {
            int n2 = this.getItemCount();
            for (int i = n; i < n2; ++i) {
                if (!string.equals(this.getItem(i))) continue;
                return i;
            }
            return -1;
        }
        int n3 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
        if (0 > n || n >= n3) {
            return -1;
        }
        int n4 = n - 1;
        TCHAR tCHAR = new TCHAR(this.getCodePage(), string, true);
        do {
            int n5;
            if ((n4 = (int)OS.SendMessage(this.handle, 418, (long)(n5 = n4), tCHAR)) != -1 && n4 > n5) continue;
            return -1;
        } while (!string.equals(this.getItem(n4)));
        return n4;
    }

    boolean isUseWsBorder() {
        return super.isUseWsBorder() || this.display != null && this.display.useWsBorderList;
    }

    public void remove(int[] nArray) {
        int n;
        this.checkWidget();
        if (nArray == null) {
            this.error(4);
        }
        if (nArray.length == 0) {
            return;
        }
        int[] nArray2 = new int[nArray.length];
        System.arraycopy(nArray, 0, nArray2, 0, nArray.length);
        this.sort(nArray2);
        int n2 = nArray2[nArray2.length - 1];
        int n3 = nArray2[0];
        int n4 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
        if (0 > n2 || n2 > n3 || n3 >= n4) {
            this.error(6);
        }
        int n5 = (int)OS.SendMessage(this.handle, 398, 0L, 0L);
        RECT rECT = null;
        long l2 = 0L;
        long l3 = 0L;
        long l4 = 0L;
        int n6 = 0;
        if ((this.style & 0x100) != 0) {
            rECT = new RECT();
            l2 = OS.GetDC(this.handle);
            l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
            if (l4 != 0L) {
                l3 = OS.SelectObject(l2, l4);
            }
        }
        int n7 = 0;
        int n8 = -1;
        for (n = 0; n < nArray2.length; ++n) {
            int n9;
            int n10 = nArray2[n];
            if (n10 == n8) continue;
            char[] cArray = null;
            int n11 = 0;
            if ((this.style & 0x100) != 0 && ((n11 = (int)OS.SendMessage(this.handle, 394, (long)n10, 0L)) == -1 || (n9 = (int)OS.SendMessage(this.handle, 393, (long)n10, cArray = new char[n11 + 1])) == -1) || (n9 = (int)OS.SendMessage(this.handle, 386, (long)n10, 0L)) == -1) break;
            if ((this.style & 0x100) != 0) {
                int n12 = 3104;
                OS.DrawText(l2, cArray, n11, rECT, 3104);
                n6 = Math.max(n6, rECT.right - rECT.left);
            }
            if (n10 < n5) {
                ++n7;
            }
            n8 = n10;
        }
        if ((this.style & 0x100) != 0) {
            if (l4 != 0L) {
                OS.SelectObject(l2, l3);
            }
            OS.ReleaseDC(this.handle, l2);
            this.setScrollWidth(n6, false);
        }
        if (n7 > 0) {
            n5 -= n7;
        }
        OS.SendMessage(this.handle, 407, (long)n5, 0L);
        if (n < nArray2.length) {
            this.error(15);
        }
    }

    public void remove(int n) {
        int n2;
        int n3;
        int n4;
        this.checkWidget();
        char[] cArray = null;
        if ((this.style & 0x100) != 0) {
            n4 = (int)OS.SendMessage(this.handle, 394, (long)n, 0L);
            if (n4 == -1) {
                n3 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
                if (0 <= n && n < n3) {
                    this.error(15);
                }
                this.error(6);
            }
            if ((n3 = (int)OS.SendMessage(this.handle, 393, (long)n, cArray = new char[n4 + 1])) == -1) {
                n2 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
                if (0 <= n && n < n2) {
                    this.error(15);
                }
                this.error(6);
            }
        }
        n4 = (int)OS.SendMessage(this.handle, 398, 0L, 0L);
        n3 = (int)OS.SendMessage(this.handle, 386, (long)n, 0L);
        if (n3 == -1) {
            n2 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
            if (0 <= n && n < n2) {
                this.error(15);
            }
            this.error(6);
        }
        if ((this.style & 0x100) != 0) {
            this.setScrollWidth(cArray, false);
        }
        if (n < n4) {
            --n4;
        }
        OS.SendMessage(this.handle, 407, (long)n4, 0L);
    }

    public void remove(int n, int n2) {
        int n3;
        this.checkWidget();
        if (n > n2) {
            return;
        }
        int n4 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
        if (0 > n || n > n2 || n2 >= n4) {
            this.error(6);
        }
        if (n == 0 && n2 == n4 - 1) {
            this.removeAll();
            return;
        }
        int n5 = (int)OS.SendMessage(this.handle, 398, 0L, 0L);
        RECT rECT = null;
        long l2 = 0L;
        long l3 = 0L;
        long l4 = 0L;
        int n6 = 0;
        if ((this.style & 0x100) != 0) {
            rECT = new RECT();
            l2 = OS.GetDC(this.handle);
            l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
            if (l4 != 0L) {
                l3 = OS.SelectObject(l2, l4);
            }
        }
        int n7 = 3104;
        for (n3 = n; n3 <= n2; ++n3) {
            int n8;
            char[] cArray = null;
            int n9 = 0;
            if ((this.style & 0x100) != 0 && ((n9 = (int)OS.SendMessage(this.handle, 394, (long)n, 0L)) == -1 || (n8 = (int)OS.SendMessage(this.handle, 393, (long)n, cArray = new char[n9 + 1])) == -1) || (n8 = (int)OS.SendMessage(this.handle, 386, (long)n, 0L)) == -1) break;
            if ((this.style & 0x100) == 0) continue;
            OS.DrawText(l2, cArray, n9, rECT, 3104);
            n6 = Math.max(n6, rECT.right - rECT.left);
        }
        if ((this.style & 0x100) != 0) {
            if (l4 != 0L) {
                OS.SelectObject(l2, l3);
            }
            OS.ReleaseDC(this.handle, l2);
            this.setScrollWidth(n6, false);
        }
        if (n2 < n5) {
            n5 -= n2 - n + 1;
        }
        OS.SendMessage(this.handle, 407, (long)n5, 0L);
        if (n3 <= n2) {
            this.error(15);
        }
    }

    public void remove(String string) {
        int n;
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        if ((n = this.indexOf(string, 0)) == -1) {
            this.error(5);
        }
        this.remove(n);
    }

    public void removeAll() {
        this.checkWidget();
        OS.SendMessage(this.handle, 388, 0L, 0L);
        if ((this.style & 0x100) != 0) {
            OS.SendMessage(this.handle, 404, 0L, 0L);
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

    public void select(int[] nArray) {
        int n;
        this.checkWidget();
        if (nArray == null) {
            this.error(4);
        }
        if ((n = nArray.length) == 0 || (this.style & 4) != 0 && n > 1) {
            return;
        }
        this.select(nArray, false);
    }

    void select(int[] nArray, boolean bl) {
        for (int i = 0; i < nArray.length; ++i) {
            int n = nArray[i];
            if (n == -1) continue;
            this.select(n, false);
        }
        if (bl) {
            this.showSelection();
        }
    }

    public void select(int n) {
        this.checkWidget();
        this.select(n, false);
    }

    void select(int n, boolean bl) {
        boolean bl2;
        if (n < 0) {
            return;
        }
        int n2 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
        if (n >= n2) {
            return;
        }
        if (bl) {
            if ((this.style & 4) != 0) {
                OS.SendMessage(this.handle, 390, (long)n, 0L);
            } else {
                OS.SendMessage(this.handle, 389, 1L, n);
            }
            return;
        }
        int n3 = (int)OS.SendMessage(this.handle, 398, 0L, 0L);
        RECT rECT = new RECT();
        RECT rECT2 = null;
        OS.SendMessage(this.handle, 408, (long)n, rECT);
        boolean bl3 = bl2 = this.getDrawing() && OS.IsWindowVisible(this.handle);
        if (bl2) {
            OS.UpdateWindow(this.handle);
            OS.SendMessage(this.handle, 11, 0L, 0L);
        }
        int n4 = -1;
        if ((this.style & 4) != 0) {
            int n5 = (int)OS.SendMessage(this.handle, 392, 0L, 0L);
            if (n5 != -1) {
                rECT2 = new RECT();
                OS.SendMessage(this.handle, 408, (long)n5, rECT2);
            }
            OS.SendMessage(this.handle, 390, (long)n, 0L);
        } else {
            n4 = (int)OS.SendMessage(this.handle, 415, 0L, 0L);
            OS.SendMessage(this.handle, 389, 1L, n);
        }
        if ((this.style & 2) != 0 && n4 != -1) {
            OS.SendMessage(this.handle, 414, (long)n4, 0L);
        }
        OS.SendMessage(this.handle, 407, (long)n3, 0L);
        if (bl2) {
            OS.SendMessage(this.handle, 11, 1L, 0L);
            OS.ValidateRect(this.handle, null);
            OS.InvalidateRect(this.handle, rECT, true);
            if (rECT2 != null) {
                OS.InvalidateRect(this.handle, rECT2, true);
            }
        }
    }

    public void select(int n, int n2) {
        this.checkWidget();
        if (n2 < 0 || n > n2 || (this.style & 4) != 0 && n != n2) {
            return;
        }
        int n3 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
        if (n3 == 0 || n >= n3) {
            return;
        }
        n = Math.max(0, n);
        n2 = Math.min(n2, n3 - 1);
        if ((this.style & 4) != 0) {
            this.select(n, false);
        } else {
            this.select(n, n2, false);
        }
    }

    void select(int n, int n2, boolean bl) {
        if (n == n2) {
            this.select(n, bl);
            return;
        }
        OS.SendMessage(this.handle, 387, (long)n, n2);
        if (bl) {
            this.showSelection();
        }
    }

    public void selectAll() {
        this.checkWidget();
        if ((this.style & 4) != 0) {
            return;
        }
        OS.SendMessage(this.handle, 389, 1L, -1L);
    }

    void setFocusIndex(int n) {
        int n2 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
        if (0 > n || n >= n2) {
            return;
        }
        OS.SendMessage(this.handle, 414, (long)n, 0L);
    }

    @Override
    public void setFont(Font font) {
        this.checkWidget();
        super.setFont(font);
        if ((this.style & 0x100) != 0) {
            this.setScrollWidth();
        }
    }

    public void setItem(int n, String string) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        int n2 = this.getTopIndex();
        boolean bl = this.isSelected(n);
        this.remove(n);
        this.add(string, n);
        if (bl) {
            this.select(n, false);
        }
        this.setTopIndex(n2);
    }

    public void setItems(String ... stringArray) {
        String string;
        TCHAR tCHAR;
        int n;
        int n2;
        Object object;
        int n3;
        this.checkWidget();
        if (stringArray == null) {
            this.error(4);
        }
        String[] stringArray2 = stringArray;
        int n4 = stringArray2.length;
        for (n3 = 0; n3 < n4; ++n3) {
            object = stringArray2[n3];
            if (object != null) continue;
            this.error(5);
        }
        long l2 = OS.GetWindowLongPtr(this.handle, -4);
        OS.SetWindowLongPtr(this.handle, -4, ListProc);
        int n5 = n3 = this.getDrawing() && OS.IsWindowVisible(this.handle) ? 1 : 0;
        if (n3 != 0) {
            OS.SendMessage(this.handle, 11, 0L, 0L);
        }
        object = null;
        long l3 = 0L;
        long l4 = 0L;
        long l5 = 0L;
        int n6 = 0;
        if ((this.style & 0x100) != 0) {
            object = new RECT();
            l3 = OS.GetDC(this.handle);
            l5 = OS.SendMessage(this.handle, 49, 0L, 0L);
            if (l5 != 0L) {
                l4 = OS.SelectObject(l3, l5);
            }
            OS.SendMessage(this.handle, 404, 0L, 0L);
        }
        int n7 = stringArray.length;
        OS.SendMessage(this.handle, 388, 0L, 0L);
        OS.SendMessage(this.handle, 424, (long)n7, n7 * 32);
        int n8 = this.getCodePage();
        for (n2 = 0; n2 < n7 && (n = (int)OS.SendMessage(this.handle, 384, 0L, tCHAR = new TCHAR(n8, string = stringArray[n2], true))) != -1 && n != -2; ++n2) {
            if ((this.style & 0x100) == 0) continue;
            int n9 = 3104;
            OS.DrawText(l3, tCHAR, -1, (RECT)object, 3104);
            n6 = Math.max(n6, ((RECT)object).right - ((RECT)object).left);
        }
        if ((this.style & 0x100) != 0) {
            if (l5 != 0L) {
                OS.SelectObject(l3, l4);
            }
            OS.ReleaseDC(this.handle, l3);
            OS.SendMessage(this.handle, 404, (long)(n6 + 3), 0L);
        }
        if (n3 != 0) {
            OS.SendMessage(this.handle, 11, 1L, 0L);
        }
        OS.SetWindowLongPtr(this.handle, -4, l2);
        if (n2 < stringArray.length) {
            this.error(14);
        }
    }

    void setScrollWidth() {
        int n = 0;
        RECT rECT = new RECT();
        long l2 = 0L;
        long l3 = OS.GetDC(this.handle);
        long l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
        if (l4 != 0L) {
            l2 = OS.SelectObject(l3, l4);
        }
        int n2 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
        int n3 = 3104;
        for (int i = 0; i < n2; ++i) {
            char[] cArray;
            int n4;
            int n5 = (int)OS.SendMessage(this.handle, 394, (long)i, 0L);
            if (n5 == -1 || (n4 = (int)OS.SendMessage(this.handle, 393, (long)i, cArray = new char[n5 + 1])) == -1) continue;
            OS.DrawText(l3, cArray, n5, rECT, 3104);
            n = Math.max(n, rECT.right - rECT.left);
        }
        if (l4 != 0L) {
            OS.SelectObject(l3, l2);
        }
        OS.ReleaseDC(this.handle, l3);
        OS.SendMessage(this.handle, 404, (long)(n + 3), 0L);
    }

    void setScrollWidth(char[] cArray, boolean bl) {
        RECT rECT = new RECT();
        long l2 = 0L;
        long l3 = OS.GetDC(this.handle);
        long l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
        if (l4 != 0L) {
            l2 = OS.SelectObject(l3, l4);
        }
        int n = 3104;
        OS.DrawText(l3, cArray, -1, rECT, 3104);
        if (l4 != 0L) {
            OS.SelectObject(l3, l2);
        }
        OS.ReleaseDC(this.handle, l3);
        this.setScrollWidth(rECT.right - rECT.left, bl);
    }

    void setScrollWidth(int n, boolean bl) {
        n += 3;
        int n2 = (int)OS.SendMessage(this.handle, 403, 0L, 0L);
        if (bl) {
            if (n <= n2) {
                return;
            }
            OS.SendMessage(this.handle, 404, (long)n, 0L);
        } else {
            if (n < n2) {
                return;
            }
            this.setScrollWidth();
        }
    }

    public void setSelection(int[] nArray) {
        int n;
        this.checkWidget();
        if (nArray == null) {
            this.error(4);
        }
        this.deselectAll();
        int n2 = nArray.length;
        if (n2 == 0 || (this.style & 4) != 0 && n2 > 1) {
            return;
        }
        this.select(nArray, true);
        if ((this.style & 2) != 0 && (n = nArray[0]) >= 0) {
            this.setFocusIndex(n);
        }
    }

    /*
     * Exception decompiling
     */
    public void setSelection(String[] var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl42 : ALOAD_0 - null : trying to set 1 previously set to 0
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

    public void setSelection(int n) {
        this.checkWidget();
        this.deselectAll();
        this.select(n, true);
        if ((this.style & 2) != 0 && n >= 0) {
            this.setFocusIndex(n);
        }
    }

    public void setSelection(int n, int n2) {
        this.checkWidget();
        this.deselectAll();
        if (n2 < 0 || n > n2 || (this.style & 4) != 0 && n != n2) {
            return;
        }
        int n3 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
        if (n3 == 0 || n >= n3) {
            return;
        }
        n = Math.max(0, n);
        n2 = Math.min(n2, n3 - 1);
        if ((this.style & 4) != 0) {
            this.select(n, true);
        } else {
            this.select(n, n2, true);
            this.setFocusIndex(n);
        }
    }

    public void setTopIndex(int n) {
        this.checkWidget();
        int n2 = (int)OS.SendMessage(this.handle, 407, (long)n, 0L);
        if (n2 == -1) {
            int n3 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
            n = Math.min(n3 - 1, Math.max(0, n));
            OS.SendMessage(this.handle, 407, (long)n, 0L);
        }
    }

    public void showSelection() {
        int n;
        int n2;
        this.checkWidget();
        if ((this.style & 4) != 0) {
            n2 = (int)OS.SendMessage(this.handle, 392, 0L, 0L);
        } else {
            int[] nArray = new int[]{0};
            n = (int)OS.SendMessage(this.handle, 401, 1L, nArray);
            n2 = nArray[0];
            if (n != 1) {
                n2 = -1;
            }
        }
        if (n2 == -1) {
            return;
        }
        int n3 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
        if (n3 == 0) {
            return;
        }
        n = (int)OS.SendMessage(this.handle, 417, 0L, 0L);
        this.forceResize();
        RECT rECT = new RECT();
        OS.GetClientRect(this.handle, rECT);
        int n4 = (int)OS.SendMessage(this.handle, 398, 0L, 0L);
        int n5 = Math.max(rECT.bottom / n, 1);
        int n6 = Math.min(n4 + n5, n3) - 1;
        if (n4 <= n2 && n2 <= n6) {
            return;
        }
        int n7 = Math.min(Math.max(n2 - n5 / 2, 0), n3 - 1);
        OS.SendMessage(this.handle, 407, (long)n7, 0L);
    }

    @Override
    void updateMenuLocation(Event event) {
        Object object;
        Rectangle rectangle = this.getClientAreaInPixels();
        int n = rectangle.x;
        int n2 = rectangle.y;
        int n3 = this.getFocusIndex();
        if (n3 != -1) {
            object = new RECT();
            long l2 = 0L;
            long l3 = OS.GetDC(this.handle);
            long l4 = OS.SendMessage(this.handle, 49, 0L, 0L);
            if (l4 != 0L) {
                l2 = OS.SelectObject(l3, l4);
            }
            int n4 = 3104;
            char[] cArray = new char[65];
            int n5 = (int)OS.SendMessage(this.handle, 394, (long)n3, 0L);
            if (n5 != -1) {
                int n6;
                if (n5 + 1 > cArray.length) {
                    cArray = new char[n5 + 1];
                }
                if ((n6 = (int)OS.SendMessage(this.handle, 393, (long)n3, cArray)) != -1) {
                    OS.DrawText(l3, cArray, n5, (RECT)object, 3104);
                }
            }
            if (l4 != 0L) {
                OS.SelectObject(l3, l2);
            }
            OS.ReleaseDC(this.handle, l3);
            n = Math.max(n, ((RECT)object).right / 2);
            n = Math.min(n, rectangle.x + rectangle.width);
            OS.SendMessage(this.handle, 408, (long)n3, (RECT)object);
            n2 = Math.max(n2, ((RECT)object).bottom);
            n2 = Math.min(n2, rectangle.y + rectangle.height);
        }
        object = this.toDisplayInPixels(n, n2);
        event.setLocationInPixels(((Point)object).x, ((Point)object).y);
    }

    @Override
    boolean updateTextDirection(int n) {
        int n2;
        if (n == 0x6000000) {
            if ((this.state & 0x400000) != 0) {
                return false;
            }
            this.state |= 0x400000;
        } else {
            this.state &= 0xFFBFFFFF;
            if (!this.addedUCC) {
                return super.updateTextDirection(n);
            }
        }
        int n3 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
        if (n3 == -1) {
            return false;
        }
        int n4 = (int)OS.SendMessage(this.handle, 392, 0L, 0L);
        this.addedUCC = false;
        while (n3-- > 0 && (n2 = (int)OS.SendMessage(this.handle, 394, (long)n3, 0L)) != -1) {
            if (n2 == 0) continue;
            char[] cArray = new char[n2 + 1];
            if (OS.SendMessage(this.handle, 393, (long)n3, cArray) == -1L || OS.SendMessage(this.handle, 386, (long)n3, 0L) == -1L) break;
            if ((this.state & 0x400000) == 0) {
                System.arraycopy(cArray, 1, cArray, 0, n2);
            }
            if (OS.SendMessage(this.handle, 385, (long)n3, cArray) != -1L) continue;
            break;
        }
        if (n4 != -1) {
            OS.SendMessage(this.handle, 390, (long)n4, 0L);
        }
        return n == 0x6000000 || super.updateTextDirection(n);
    }

    @Override
    int widgetStyle() {
        int n = super.widgetStyle() | 1 | 0x100;
        if ((this.style & 4) != 0) {
            return n;
        }
        if ((this.style & 2) == 0) {
            return n;
        }
        if ((this.style & 0x40) != 0) {
            return n | 8;
        }
        return n | 0x800;
    }

    @Override
    TCHAR windowClass() {
        return ListClass;
    }

    @Override
    long windowProc() {
        return ListProc;
    }

    @Override
    long windowProc(long l2, int n, long l3, long l4) {
        if (this.handle != 0L && l4 != 0L && (this.state & 0x400000) != 0) {
            switch (n) {
                case 384: 
                case 385: 
                case 418: {
                    int n2 = OS.wcslen(l4);
                    int n3 = this.getCodePage();
                    TCHAR tCHAR = new TCHAR(n3, n2);
                    OS.MoveMemory(tCHAR, l4, tCHAR.length() * 2);
                    String string = tCHAR.toString(0, n2);
                    int n4 = BidiUtil.resolveTextDirection(string);
                    if (n4 == 0) {
                        n4 = (this.style & 0x4000000) != 0 ? 0x4000000 : 0x2000000;
                    }
                    string = (n4 == 0x4000000 ? 8235 : 8234) + string;
                    tCHAR = new TCHAR(n3, string, true);
                    long l5 = OS.GetProcessHeap();
                    n2 = tCHAR.length() * 2;
                    long l6 = OS.HeapAlloc(l5, 8, n2);
                    OS.MoveMemory(l6, tCHAR, n2);
                    long l7 = super.windowProc(l2, n, l3, l6);
                    OS.HeapFree(l5, 0, l6);
                    this.addedUCC = true;
                    return l7;
                }
            }
        }
        return super.windowProc(l2, n, l3, l4);
    }

    @Override
    LRESULT WM_CHAR(long l2, long l3) {
        int n;
        LRESULT lRESULT = super.WM_CHAR(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (OS.GetKeyState(17) < 0 && OS.GetKeyState(16) >= 0 && ((n = OS.GetWindowLong(this.handle, -16)) & 0x800) != 0) {
            switch ((int)l2) {
                case 32: {
                    int n2 = (int)OS.SendMessage(this.handle, 415, 0L, 0L);
                    int n3 = (int)OS.SendMessage(this.handle, 391, (long)n2, 0L);
                    if (n3 == -1) break;
                    OS.SendMessage(this.handle, 389, n3 == 0 ? 1L : 0L, n2);
                    OS.SendMessage(this.handle, 61852, (long)n2, 0L);
                    this.sendSelectionEvent(13);
                    return LRESULT.ZERO;
                }
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_KEYDOWN(long l2, long l3) {
        int n;
        LRESULT lRESULT = super.WM_KEYDOWN(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        if (OS.GetKeyState(17) < 0 && OS.GetKeyState(16) >= 0 && ((n = OS.GetWindowLong(this.handle, -16)) & 0x800) != 0) {
            int n2;
            int n3 = -1;
            switch ((int)l2) {
                case 32: {
                    return LRESULT.ZERO;
                }
                case 38: 
                case 40: {
                    n2 = (int)OS.SendMessage(this.handle, 415, 0L, 0L);
                    n3 = Math.max(0, n2 + ((int)l2 == 38 ? -1 : 1));
                    break;
                }
                case 33: {
                    n2 = (int)OS.SendMessage(this.handle, 398, 0L, 0L);
                    int n4 = (int)OS.SendMessage(this.handle, 415, 0L, 0L);
                    if (n4 != n2) {
                        n3 = n2;
                        break;
                    }
                    this.forceResize();
                    RECT rECT = new RECT();
                    OS.GetClientRect(this.handle, rECT);
                    int n5 = (int)OS.SendMessage(this.handle, 417, 0L, 0L);
                    int n6 = Math.max(2, rECT.bottom / n5);
                    n3 = Math.max(0, n2 - (n6 - 1));
                    break;
                }
                case 34: {
                    n2 = (int)OS.SendMessage(this.handle, 398, 0L, 0L);
                    int n4 = (int)OS.SendMessage(this.handle, 415, 0L, 0L);
                    this.forceResize();
                    RECT rECT = new RECT();
                    OS.GetClientRect(this.handle, rECT);
                    int n7 = (int)OS.SendMessage(this.handle, 417, 0L, 0L);
                    int n8 = Math.max(2, rECT.bottom / n7);
                    int n9 = n2 + n8 - 1;
                    n3 = n4 != n9 ? n9 : n9 + n8 - 1;
                    int n10 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
                    if (n10 == -1) break;
                    n3 = Math.min(n10 - 1, n3);
                    break;
                }
                case 36: {
                    n3 = 0;
                    break;
                }
                case 35: {
                    n2 = (int)OS.SendMessage(this.handle, 395, 0L, 0L);
                    if (n2 == -1) break;
                    n3 = n2 - 1;
                    break;
                }
            }
            if (n3 != -1) {
                n2 = (int)OS.SendMessage(this.handle, 297, 0L, 0L);
                if ((n2 & 1) != 0) {
                    OS.SendMessage(this.handle, 295, 3L, 0L);
                    RECT rECT = new RECT();
                    int n11 = (int)OS.SendMessage(this.handle, 415, 0L, 0L);
                    OS.SendMessage(this.handle, 408, (long)n11, rECT);
                    OS.InvalidateRect(this.handle, rECT, true);
                }
                OS.SendMessage(this.handle, 414, (long)n3, 0L);
                return LRESULT.ZERO;
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT WM_SETREDRAW(long l2, long l3) {
        LRESULT lRESULT = super.WM_SETREDRAW(l2, l3);
        if (lRESULT != null) {
            return lRESULT;
        }
        OS.DefWindowProc(this.handle, 11, l2, l3);
        return lRESULT;
    }

    @Override
    LRESULT WM_SIZE(long l2, long l3) {
        int n = (int)OS.SendMessage(this.handle, 398, 0L, 0L);
        LRESULT lRESULT = super.WM_SIZE(l2, l3);
        if (!this.isDisposed()) {
            int n2;
            SCROLLINFO sCROLLINFO = new SCROLLINFO();
            sCROLLINFO.cbSize = SCROLLINFO.sizeof;
            sCROLLINFO.fMask = 4;
            if (OS.GetScrollInfo(this.handle, 0, sCROLLINFO) && sCROLLINFO.nPos != 0) {
                OS.InvalidateRect(this.handle, null, true);
            }
            if (n != (n2 = (int)OS.SendMessage(this.handle, 398, 0L, 0L))) {
                OS.InvalidateRect(this.handle, null, true);
            }
        }
        return lRESULT;
    }

    @Override
    LRESULT wmCommandChild(long l2, long l3) {
        int n = OS.HIWORD(l2);
        switch (n) {
            case 1: {
                this.sendSelectionEvent(13);
                break;
            }
            case 2: {
                this.sendSelectionEvent(14);
            }
        }
        return super.wmCommandChild(l2, l3);
    }

    static {
        ListClass = new TCHAR(0, "LISTBOX", true);
        WNDCLASS wNDCLASS = new WNDCLASS();
        OS.GetClassInfo(0L, ListClass, wNDCLASS);
        ListProc = wNDCLASS.lpfnWndProc;
    }
}

