/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.dnd.DropTargetEffect;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TVHITTESTINFO;
import org.eclipse.swt.internal.win32.TVITEM;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class TreeDropTargetEffect
extends DropTargetEffect {
    static final int SCROLL_HYSTERESIS = 200;
    static final int EXPAND_HYSTERESIS = 1000;
    long dropIndex;
    long scrollIndex;
    long scrollBeginTime;
    long expandIndex;
    long expandBeginTime;
    TreeItem insertItem;
    boolean insertBefore;

    public TreeDropTargetEffect(Tree tree) {
        super(tree);
    }

    int checkEffect(int n) {
        if ((n & 1) != 0) {
            n = n & 0xFFFFFFFB & 0xFFFFFFFD;
        }
        if ((n & 2) != 0) {
            n &= 0xFFFFFFFB;
        }
        return n;
    }

    @Override
    public void dragEnter(DropTargetEvent dropTargetEvent) {
        this.dropIndex = -1L;
        this.insertItem = null;
        this.expandBeginTime = 0L;
        this.expandIndex = -1L;
        this.scrollBeginTime = 0L;
        this.scrollIndex = -1L;
    }

    @Override
    public void dragLeave(DropTargetEvent dropTargetEvent) {
        Tree tree = (Tree)this.control;
        long l2 = tree.handle;
        if (this.dropIndex != -1L) {
            TVITEM tVITEM = new TVITEM();
            tVITEM.hItem = this.dropIndex;
            tVITEM.mask = 8;
            tVITEM.stateMask = 8;
            tVITEM.state = 0;
            OS.SendMessage(l2, 4415, 0L, tVITEM);
            this.dropIndex = -1L;
        }
        if (this.insertItem != null) {
            tree.setInsertMark(null, false);
            this.insertItem = null;
        }
        this.expandBeginTime = 0L;
        this.expandIndex = -1L;
        this.scrollBeginTime = 0L;
        this.scrollIndex = -1L;
    }

    @Override
    public void dragOver(DropTargetEvent dropTargetEvent) {
        Object object;
        Tree tree = (Tree)this.getControl();
        int n = this.checkEffect(dropTargetEvent.feedback);
        long l2 = tree.handle;
        Point point = new Point(dropTargetEvent.x, dropTargetEvent.y);
        point = DPIUtil.autoScaleUp(tree.toControl(point));
        TVHITTESTINFO tVHITTESTINFO = new TVHITTESTINFO();
        tVHITTESTINFO.x = point.x;
        tVHITTESTINFO.y = point.y;
        OS.SendMessage(l2, 4369, 0L, tVHITTESTINFO);
        long l3 = tVHITTESTINFO.hItem;
        if ((n & 8) == 0) {
            this.scrollBeginTime = 0L;
            this.scrollIndex = -1L;
        } else if (l3 != -1L && this.scrollIndex == l3 && this.scrollBeginTime != 0L) {
            if (System.currentTimeMillis() >= this.scrollBeginTime) {
                long l4 = OS.SendMessage(l2, 4362, 5L, 0L);
                long l5 = OS.SendMessage(l2, 4362, l3 == l4 ? 7L : 6L, l3);
                boolean bl = true;
                if (l3 == l4) {
                    bl = l5 != 0L;
                } else {
                    RECT rECT = new RECT();
                    if (OS.TreeView_GetItemRect(l2, l5, rECT, true)) {
                        RECT rECT2 = new RECT();
                        OS.GetClientRect(l2, rECT2);
                        POINT pOINT = new POINT();
                        pOINT.x = rECT.left;
                        pOINT.y = rECT.top;
                        if (OS.PtInRect(rECT2, pOINT)) {
                            pOINT.y = rECT.bottom;
                            if (OS.PtInRect(rECT2, pOINT)) {
                                bl = false;
                            }
                        }
                    }
                }
                if (bl) {
                    OS.SendMessage(l2, 4372, 0L, l5);
                    tree.redraw();
                }
                this.scrollBeginTime = 0L;
                this.scrollIndex = -1L;
            }
        } else {
            this.scrollBeginTime = System.currentTimeMillis() + 200L;
            this.scrollIndex = l3;
        }
        if ((n & 0x10) == 0) {
            this.expandBeginTime = 0L;
            this.expandIndex = -1L;
        } else if (l3 != -1L && this.expandIndex == l3 && this.expandBeginTime != 0L) {
            if (System.currentTimeMillis() >= this.expandBeginTime) {
                TreeItem treeItem;
                if (OS.SendMessage(l2, 4362, 4L, l3) != 0L && (treeItem = (TreeItem)tree.getDisplay().findWidget(tree.handle, l3)) != null && !treeItem.getExpanded()) {
                    treeItem.setExpanded(true);
                    tree.redraw();
                    object = new Event();
                    ((Event)object).item = treeItem;
                    tree.notifyListeners(17, (Event)object);
                }
                this.expandBeginTime = 0L;
                this.expandIndex = -1L;
            }
        } else {
            this.expandBeginTime = System.currentTimeMillis() + 1000L;
            this.expandIndex = l3;
        }
        if (this.dropIndex != -1L && (this.dropIndex != l3 || (n & 1) == 0)) {
            TVITEM tVITEM = new TVITEM();
            tVITEM.hItem = this.dropIndex;
            tVITEM.mask = 8;
            tVITEM.stateMask = 8;
            tVITEM.state = 0;
            OS.SendMessage(l2, 4415, 0L, tVITEM);
            this.dropIndex = -1L;
        }
        if (l3 != -1L && l3 != this.dropIndex && (n & 1) != 0) {
            TVITEM tVITEM = new TVITEM();
            tVITEM.hItem = l3;
            tVITEM.mask = 8;
            tVITEM.stateMask = 8;
            tVITEM.state = 8;
            OS.SendMessage(l2, 4415, 0L, tVITEM);
            this.dropIndex = l3;
        }
        if ((n & 2) != 0 || (n & 4) != 0) {
            boolean bl = (n & 2) != 0;
            object = (TreeItem)tree.getDisplay().findWidget(tree.handle, l3);
            if (object != null) {
                if (object != this.insertItem || bl != this.insertBefore) {
                    tree.setInsertMark((TreeItem)object, bl);
                }
                this.insertItem = object;
                this.insertBefore = bl;
            } else {
                if (this.insertItem != null) {
                    tree.setInsertMark(null, false);
                }
                this.insertItem = null;
            }
        } else {
            if (this.insertItem != null) {
                tree.setInsertMark(null, false);
            }
            this.insertItem = null;
        }
    }
}

