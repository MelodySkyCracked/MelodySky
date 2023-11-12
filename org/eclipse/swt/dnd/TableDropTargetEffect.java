/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.dnd.DropTargetEffect;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.LVHITTESTINFO;
import org.eclipse.swt.internal.win32.LVINSERTMARK;
import org.eclipse.swt.internal.win32.LVITEM;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class TableDropTargetEffect
extends DropTargetEffect {
    static final int SCROLL_HYSTERESIS = 200;
    int scrollIndex = -1;
    long scrollBeginTime;
    TableItem dropHighlight;
    int iItemInsert = -1;

    public TableDropTargetEffect(Table table) {
        super(table);
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
        this.scrollBeginTime = 0L;
        this.scrollIndex = -1;
        this.dropHighlight = null;
        this.iItemInsert = -1;
    }

    @Override
    public void dragLeave(DropTargetEvent dropTargetEvent) {
        Object object;
        Table table = (Table)this.control;
        long l2 = table.handle;
        if (this.dropHighlight != null) {
            object = new LVITEM();
            ((LVITEM)object).stateMask = 8;
            OS.SendMessage(l2, 4139, -1L, (LVITEM)object);
            this.dropHighlight = null;
        }
        if (this.iItemInsert != -1) {
            object = new LVINSERTMARK();
            ((LVINSERTMARK)object).cbSize = LVINSERTMARK.sizeof;
            ((LVINSERTMARK)object).iItem = -1;
            OS.SendMessage(l2, 4262, 0L, (LVINSERTMARK)object);
            this.iItemInsert = -1;
        }
        this.scrollBeginTime = 0L;
        this.scrollIndex = -1;
    }

    @Override
    public void dragOver(DropTargetEvent dropTargetEvent) {
        Table table = (Table)this.getControl();
        int n = this.checkEffect(dropTargetEvent.feedback);
        long l2 = table.handle;
        Point point = new Point(dropTargetEvent.x, dropTargetEvent.y);
        point = DPIUtil.autoScaleUp(table.toControl(point));
        LVHITTESTINFO lVHITTESTINFO = new LVHITTESTINFO();
        lVHITTESTINFO.x = point.x;
        lVHITTESTINFO.y = point.y;
        OS.SendMessage(l2, 4114, 0L, lVHITTESTINFO);
        if ((n & 8) == 0) {
            this.scrollBeginTime = 0L;
            this.scrollIndex = -1;
        } else if (lVHITTESTINFO.iItem != -1 && this.scrollIndex == lVHITTESTINFO.iItem && this.scrollBeginTime != 0L) {
            if (System.currentTimeMillis() >= this.scrollBeginTime) {
                int n2 = Math.max(0, (int)OS.SendMessage(l2, 4135, 0L, 0L));
                int n3 = (int)OS.SendMessage(l2, 4100, 0L, 0L);
                int n4 = this.scrollIndex - 1 < n2 ? Math.max(0, this.scrollIndex - 1) : Math.min(n3 - 1, this.scrollIndex + 1);
                boolean bl = true;
                if (lVHITTESTINFO.iItem == n2) {
                    bl = lVHITTESTINFO.iItem != n4;
                } else {
                    RECT rECT = new RECT();
                    rECT.left = 0;
                    if (OS.SendMessage(l2, 4110, (long)lVHITTESTINFO.iItem, rECT) != 0L) {
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
                    OS.SendMessage(l2, 4115, (long)n4, 0L);
                    table.redraw();
                }
                this.scrollBeginTime = 0L;
                this.scrollIndex = -1;
            }
        } else {
            this.scrollBeginTime = System.currentTimeMillis() + 200L;
            this.scrollIndex = lVHITTESTINFO.iItem;
        }
        if (lVHITTESTINFO.iItem != -1 && (n & 1) != 0) {
            TableItem tableItem = table.getItem(lVHITTESTINFO.iItem);
            if (this.dropHighlight != tableItem) {
                LVITEM lVITEM = new LVITEM();
                lVITEM.stateMask = 8;
                OS.SendMessage(l2, 4139, -1L, lVITEM);
                lVITEM.state = 8;
                OS.SendMessage(l2, 4139, (long)lVHITTESTINFO.iItem, lVITEM);
                this.dropHighlight = tableItem;
            }
        } else if (this.dropHighlight != null) {
            LVITEM lVITEM = new LVITEM();
            lVITEM.stateMask = 8;
            OS.SendMessage(l2, 4139, -1L, lVITEM);
            this.dropHighlight = null;
        }
        if (lVHITTESTINFO.iItem != -1 && (n & 6) != 0) {
            LVINSERTMARK lVINSERTMARK = new LVINSERTMARK();
            lVINSERTMARK.cbSize = LVINSERTMARK.sizeof;
            lVINSERTMARK.dwFlags = (n & 4) != 0 ? 1 : 0;
            lVINSERTMARK.iItem = lVHITTESTINFO.iItem;
            if (OS.SendMessage(l2, 4262, 0L, lVINSERTMARK) != 0L) {
                this.iItemInsert = lVHITTESTINFO.iItem;
            }
        } else if (this.iItemInsert != -1) {
            LVINSERTMARK lVINSERTMARK = new LVINSERTMARK();
            lVINSERTMARK.cbSize = LVINSERTMARK.sizeof;
            lVINSERTMARK.iItem = -1;
            OS.SendMessage(l2, 4262, 0L, lVINSERTMARK);
            this.iItemInsert = -1;
        }
    }
}

