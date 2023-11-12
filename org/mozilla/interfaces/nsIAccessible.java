/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIAccessible
extends nsISupports {
    public static final String NS_IACCESSIBLE_IID = "{db717db4-37e9-42f1-a3b0-2579dd7c3814}";
    public static final long STATE_UNAVAILABLE = 1L;
    public static final long STATE_SELECTED = 2L;
    public static final long STATE_FOCUSED = 4L;
    public static final long STATE_PRESSED = 8L;
    public static final long STATE_CHECKED = 16L;
    public static final long STATE_MIXED = 32L;
    public static final long STATE_READONLY = 64L;
    public static final long STATE_HOTTRACKED = 128L;
    public static final long STATE_DEFAULT = 256L;
    public static final long STATE_EXPANDED = 512L;
    public static final long STATE_COLLAPSED = 1024L;
    public static final long STATE_BUSY = 2048L;
    public static final long STATE_FLOATING = 4096L;
    public static final long STATE_MARQUEED = 8192L;
    public static final long STATE_ANIMATED = 16384L;
    public static final long STATE_INVISIBLE = 32768L;
    public static final long STATE_OFFSCREEN = 65536L;
    public static final long STATE_SIZEABLE = 131072L;
    public static final long STATE_MOVEABLE = 262144L;
    public static final long STATE_SELFVOICING = 524288L;
    public static final long STATE_FOCUSABLE = 0x100000L;
    public static final long STATE_SELECTABLE = 0x200000L;
    public static final long STATE_LINKED = 0x400000L;
    public static final long STATE_TRAVERSED = 0x800000L;
    public static final long STATE_MULTISELECTABLE = 0x1000000L;
    public static final long STATE_EXTSELECTABLE = 0x2000000L;
    public static final long STATE_ALERT_LOW = 0x4000000L;
    public static final long STATE_ALERT_MEDIUM = 0x8000000L;
    public static final long STATE_ALERT_HIGH = 0x10000000L;
    public static final long STATE_PROTECTED = 0x20000000L;
    public static final long STATE_HASPOPUP = 0x40000000L;
    public static final long STATE_REQUIRED = 0x4000000L;
    public static final long STATE_IMPORTANT = 0x8000000L;
    public static final long STATE_INVALID = 0x10000000L;
    public static final long STATE_CHECKABLE = 8192L;
    public static final long EXT_STATE_EDITABLE = 0x200000L;
    public static final long EXT_STATE_ACTIVE = 0x400000L;
    public static final long EXT_STATE_EXPANDABLE = 0x800000L;
    public static final long EXT_STATE_MODAL = 0x1000000L;
    public static final long EXT_STATE_MULTI_LINE = 0x2000000L;
    public static final long EXT_STATE_SENSITIVE = 0x4000000L;
    public static final long EXT_STATE_SHOWING = 0x10000000L;
    public static final long EXT_STATE_SINGLE_LINE = 0x20000000L;
    public static final long EXT_STATE_TRANSIENT = 0x40000000L;
    public static final long EXT_STATE_VERTICAL = 0x80000000L;
    public static final long RELATION_NUL = 0L;
    public static final long RELATION_CONTROLLED_BY = 1L;
    public static final long RELATION_CONTROLLER_FOR = 2L;
    public static final long RELATION_LABEL_FOR = 3L;
    public static final long RELATION_LABELLED_BY = 4L;
    public static final long RELATION_MEMBER_OF = 5L;
    public static final long RELATION_NODE_CHILD_OF = 6L;
    public static final long RELATION_FLOWS_TO = 7L;
    public static final long RELATION_FLOWS_FROM = 8L;
    public static final long RELATION_SUBWINDOW_OF = 9L;
    public static final long RELATION_EMBEDS = 10L;
    public static final long RELATION_EMBEDDED_BY = 11L;
    public static final long RELATION_POPUP_FOR = 12L;
    public static final long RELATION_PARENT_WINDOW_OF = 13L;
    public static final long RELATION_DEFAULT_BUTTON = 16384L;
    public static final long RELATION_DESCRIBED_BY = 16385L;
    public static final long RELATION_DESCRIPTION_FOR = 16386L;

    public nsIAccessible getParent();

    public nsIAccessible getNextSibling();

    public nsIAccessible getPreviousSibling();

    public nsIAccessible getFirstChild();

    public nsIAccessible getLastChild();

    public int getChildCount();

    public int getIndexInParent();

    public String getName();

    public void setName(String var1);

    public String getFinalValue();

    public String getDescription();

    public String getKeyboardShortcut();

    public String getKeyBinding();

    public long getRole();

    public long getFinalRole();

    public long getFinalState();

    public long getExtState();

    public String getHelp();

    public nsIAccessible getFocusedChild();

    public nsIAccessible getChildAtPoint(int var1, int var2);

    public nsIAccessible getChildAt(int var1);

    public nsIAccessible getAccessibleToRight();

    public nsIAccessible getAccessibleToLeft();

    public nsIAccessible getAccessibleAbove();

    public nsIAccessible getAccessibleBelow();

    public nsIAccessible getAccessibleRelated(long var1);

    public void getBounds(int[] var1, int[] var2, int[] var3, int[] var4);

    public void addSelection();

    public void removeSelection();

    public void extendSelection();

    public void takeSelection();

    public void takeFocus();

    public short getNumActions();

    public String getActionName(short var1);

    public void doAction(short var1);
}

