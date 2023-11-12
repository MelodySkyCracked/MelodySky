/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IDispatch;

public class IAccessible
extends IDispatch {
    public IAccessible(long l2) {
        super(l2);
    }

    public int get_accParent(long l2) {
        return COM.VtblCall(7, this.address, l2);
    }

    public int get_accChildCount(long l2) {
        return COM.VtblCall(8, this.address, l2);
    }

    public int get_accChild(long l2, long l3) {
        return COM.VtblCall(9, this.address, l2, l3);
    }

    public int get_accName(long l2, long l3) {
        return COM.VtblCall(10, this.address, l2, l3);
    }

    public int get_accValue(long l2, long l3) {
        return COM.VtblCall(11, this.address, l2, l3);
    }

    public int get_accDescription(long l2, long l3) {
        return COM.VtblCall(12, this.address, l2, l3);
    }

    public int get_accRole(long l2, long l3) {
        return COM.VtblCall(13, this.address, l2, l3);
    }

    public int get_accState(long l2, long l3) {
        return COM.VtblCall(14, this.address, l2, l3);
    }

    public int get_accHelp(long l2, long l3) {
        return COM.VtblCall(15, this.address, l2, l3);
    }

    public int get_accHelpTopic(long l2, long l3, long l4) {
        return COM.VtblCall(16, this.address, l2, l3, l4);
    }

    public int get_accKeyboardShortcut(long l2, long l3) {
        return COM.VtblCall(17, this.address, l2, l3);
    }

    public int get_accFocus(long l2) {
        return COM.VtblCall(18, this.address, l2);
    }

    public int get_accSelection(long l2) {
        return COM.VtblCall(19, this.address, l2);
    }

    public int get_accDefaultAction(long l2, long l3) {
        return COM.VtblCall(20, this.address, l2, l3);
    }

    public int accSelect(int n, long l2) {
        return COM.VtblCall(21, this.address, n, l2);
    }

    public int accLocation(long l2, long l3, long l4, long l5, long l6) {
        return COM.VtblCall(22, this.address, l2, l3, l4, l5, l6);
    }

    public int accNavigate(int n, long l2, long l3) {
        return COM.VtblCall(23, this.address, n, l2, l3);
    }

    public int accHitTest(int n, int n2, long l2) {
        return COM.VtblCall(24, this.address, n, (long)n2, l2);
    }

    public int accDoDefaultAction(long l2) {
        return COM.VtblCall(25, this.address, l2);
    }

    public int put_accName(long l2, long l3) {
        return COM.VtblCall(26, this.address, l2, l3);
    }

    public int put_accValue(long l2, long l3) {
        return COM.VtblCall(27, this.address, l2, l3);
    }
}

