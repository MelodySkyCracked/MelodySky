/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEventTarget;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNSEvent
extends nsISupports {
    public static final String NS_IDOMNSEVENT_IID = "{e565d518-4510-407f-a3d9-3b4107549c6d}";
    public static final int MOUSEDOWN = 1;
    public static final int MOUSEUP = 2;
    public static final int MOUSEOVER = 4;
    public static final int MOUSEOUT = 8;
    public static final int MOUSEMOVE = 16;
    public static final int MOUSEDRAG = 32;
    public static final int CLICK = 64;
    public static final int DBLCLICK = 128;
    public static final int KEYDOWN = 256;
    public static final int KEYUP = 512;
    public static final int KEYPRESS = 1024;
    public static final int DRAGDROP = 2048;
    public static final int FOCUS = 4096;
    public static final int BLUR = 8192;
    public static final int SELECT = 16384;
    public static final int CHANGE = 32768;
    public static final int RESET = 65536;
    public static final int SUBMIT = 131072;
    public static final int SCROLL = 262144;
    public static final int LOAD = 524288;
    public static final int UNLOAD = 0x100000;
    public static final int XFER_DONE = 0x200000;
    public static final int ABORT = 0x400000;
    public static final int ERROR = 0x800000;
    public static final int LOCATE = 0x1000000;
    public static final int MOVE = 0x2000000;
    public static final int RESIZE = 0x4000000;
    public static final int FORWARD = 0x8000000;
    public static final int HELP = 0x10000000;
    public static final int BACK = 0x20000000;
    public static final int TEXT = 0x40000000;
    public static final int ALT_MASK = 1;
    public static final int CONTROL_MASK = 2;
    public static final int SHIFT_MASK = 4;
    public static final int META_MASK = 8;

    public nsIDOMEventTarget getOriginalTarget();

    public nsIDOMEventTarget getExplicitOriginalTarget();

    public void preventBubble();

    public void preventCapture();

    public boolean getIsTrusted();
}

