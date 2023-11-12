/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DragSourceEffect;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.BITMAP;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.SHDRAGIMAGE;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Tree;

public class TreeDragSourceEffect
extends DragSourceEffect {
    Image dragSourceImage = null;

    public TreeDragSourceEffect(Tree tree) {
        super(tree);
    }

    @Override
    public void dragFinished(DragSourceEvent dragSourceEvent) {
        if (this.dragSourceImage != null) {
            this.dragSourceImage.dispose();
        }
        this.dragSourceImage = null;
    }

    @Override
    public void dragStart(DragSourceEvent dragSourceEvent) {
        dragSourceEvent.image = this.getDragSourceImage(dragSourceEvent);
    }

    Image getDragSourceImage(DragSourceEvent dragSourceEvent) {
        if (this.dragSourceImage != null) {
            this.dragSourceImage.dispose();
        }
        this.dragSourceImage = null;
        SHDRAGIMAGE sHDRAGIMAGE = new SHDRAGIMAGE();
        int n = OS.RegisterWindowMessage(new TCHAR(0, "ShellGetDragImage", true));
        if (OS.SendMessage(this.control.handle, n, 0L, sHDRAGIMAGE) != 0L) {
            dragSourceEvent.offsetX = (this.control.getStyle() & 0x8000000) != 0 ? sHDRAGIMAGE.sizeDragImage.cx - sHDRAGIMAGE.ptOffset.x : sHDRAGIMAGE.ptOffset.x;
            dragSourceEvent.offsetY = sHDRAGIMAGE.ptOffset.y;
            long l2 = sHDRAGIMAGE.hbmpDragImage;
            if (l2 != 0L) {
                Object object;
                BITMAP bITMAP = new BITMAP();
                OS.GetObject(l2, BITMAP.sizeof, bITMAP);
                int n2 = bITMAP.bmWidth;
                int n3 = bITMAP.bmHeight;
                long l3 = OS.GetDC(0L);
                long l4 = OS.CreateCompatibleDC(l3);
                long l5 = OS.SelectObject(l4, l2);
                long l6 = OS.CreateCompatibleDC(l3);
                BITMAPINFOHEADER bITMAPINFOHEADER = new BITMAPINFOHEADER();
                bITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
                bITMAPINFOHEADER.biWidth = n2;
                bITMAPINFOHEADER.biHeight = -n3;
                bITMAPINFOHEADER.biPlanes = 1;
                bITMAPINFOHEADER.biBitCount = (short)32;
                bITMAPINFOHEADER.biCompression = 0;
                byte[] byArray = new byte[BITMAPINFOHEADER.sizeof];
                OS.MoveMemory(byArray, bITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
                long[] lArray = new long[]{0L};
                long l7 = OS.CreateDIBSection(0L, byArray, 0, lArray, 0L, 0);
                if (l7 == 0L) {
                    SWT.error(2);
                }
                long l8 = OS.SelectObject(l6, l7);
                BITMAP bITMAP2 = new BITMAP();
                OS.GetObject(l7, BITMAP.sizeof, bITMAP2);
                int n4 = bITMAP2.bmWidthBytes * bITMAP2.bmHeight;
                OS.BitBlt(l6, 0, 0, n2, n3, l4, 0, 0, 0xCC0020);
                byte[] byArray2 = new byte[n4];
                OS.MoveMemory(byArray2, bITMAP2.bmBits, n4);
                PaletteData paletteData = new PaletteData(65280, 0xFF0000, -16777216);
                ImageData imageData = new ImageData(n2, n3, bITMAP.bmBitsPixel, paletteData, bITMAP.bmWidthBytes, byArray2);
                if (sHDRAGIMAGE.crColorKey == -1) {
                    object = new byte[n2 * n3];
                    int n5 = bITMAP2.bmWidthBytes - n2 * 4;
                    int n6 = 0;
                    int n7 = 3;
                    for (int i = 0; i < n3; ++i) {
                        for (int j = 0; j < n2; ++j) {
                            object[n6++] = byArray2[n7];
                            n7 += 4;
                        }
                        n7 += n5;
                    }
                    imageData.alphaData = object;
                } else {
                    imageData.transparentPixel = sHDRAGIMAGE.crColorKey << 8;
                }
                object = this.control.getDisplay();
                this.dragSourceImage = new Image((Device)object, new DPIUtil.AutoScaleImageDataProvider((Device)object, imageData, DPIUtil.getDeviceZoom()));
                OS.SelectObject(l6, l8);
                OS.DeleteDC(l6);
                OS.DeleteObject(l7);
                OS.SelectObject(l4, l5);
                OS.DeleteDC(l4);
                OS.ReleaseDC(0L, l3);
                OS.DeleteObject(l2);
                return this.dragSourceImage;
            }
        }
        return null;
    }
}

