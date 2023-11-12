/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.dnd;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.IDataObject;
import org.eclipse.swt.internal.ole.win32.STGMEDIUM;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.DIBSECTION;
import org.eclipse.swt.internal.win32.OS;

public class ImageTransfer
extends ByteArrayTransfer {
    private static ImageTransfer _instance = new ImageTransfer();
    private static final String CF_DIB = "CF_DIB";
    private static final int CF_DIBID = 8;

    private ImageTransfer() {
    }

    public static ImageTransfer getInstance() {
        return _instance;
    }

    @Override
    public void javaToNative(Object object, TransferData transferData) {
        ImageData imageData;
        ImageTransfer imageTransfer = this;
        if (object == null || !this.isSupportedType(transferData)) {
            DND.error(2003);
        }
        if ((imageData = (ImageData)object) == null) {
            SWT.error(4);
        }
        int n = imageData.data.length;
        int n2 = imageData.height;
        int n3 = imageData.bytesPerLine;
        BITMAPINFOHEADER bITMAPINFOHEADER = new BITMAPINFOHEADER();
        bITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
        bITMAPINFOHEADER.biSizeImage = n;
        bITMAPINFOHEADER.biWidth = imageData.width;
        bITMAPINFOHEADER.biHeight = n2;
        bITMAPINFOHEADER.biPlanes = 1;
        bITMAPINFOHEADER.biBitCount = (short)imageData.depth;
        bITMAPINFOHEADER.biCompression = 0;
        int n4 = 0;
        if (bITMAPINFOHEADER.biBitCount <= 8) {
            n4 += (1 << bITMAPINFOHEADER.biBitCount) * 4;
        }
        byte[] byArray = new byte[BITMAPINFOHEADER.sizeof + n4];
        OS.MoveMemory(byArray, bITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
        RGB[] rGBArray = imageData.palette.getRGBs();
        if (rGBArray != null && n4 > 0) {
            int n5 = BITMAPINFOHEADER.sizeof;
            for (RGB rGB : rGBArray) {
                byArray[n5] = (byte)rGB.blue;
                byArray[n5 + 1] = (byte)rGB.green;
                byArray[n5 + 2] = (byte)rGB.red;
                byArray[n5 + 3] = 0;
                n5 += 4;
            }
        }
        long l2 = OS.GlobalAlloc(64, BITMAPINFOHEADER.sizeof + n4 + n);
        OS.MoveMemory(l2, byArray, byArray.length);
        long l3 = l2 + (long)BITMAPINFOHEADER.sizeof + (long)n4;
        if (n2 <= 0) {
            OS.MoveMemory(l3, imageData.data, n);
        } else {
            int n6 = 0;
            l3 += (long)(n3 * (n2 - 1));
            byte[] byArray2 = new byte[n3];
            for (int i = 0; i < n2; ++i) {
                System.arraycopy(imageData.data, n6, byArray2, 0, n3);
                OS.MoveMemory(l3, byArray2, n3);
                n6 += n3;
                l3 -= (long)n3;
            }
        }
        transferData.stgmedium = new STGMEDIUM();
        transferData.stgmedium.tymed = 1;
        transferData.stgmedium.unionField = l2;
        transferData.stgmedium.pUnkForRelease = 0L;
        transferData.result = 0;
    }

    @Override
    public Object nativeToJava(TransferData transferData) {
        Object object;
        if (!this.isSupportedType(transferData) || transferData.pIDataObject == 0L) {
            return null;
        }
        IDataObject iDataObject = new IDataObject(transferData.pIDataObject);
        iDataObject.AddRef();
        FORMATETC fORMATETC = new FORMATETC();
        fORMATETC.cfFormat = 8;
        fORMATETC.ptd = 0L;
        fORMATETC.dwAspect = 1;
        fORMATETC.lindex = -1;
        fORMATETC.tymed = 1;
        STGMEDIUM sTGMEDIUM = new STGMEDIUM();
        sTGMEDIUM.tymed = 1;
        transferData.result = this.getData(iDataObject, fORMATETC, sTGMEDIUM);
        if (transferData.result != 0) {
            return null;
        }
        long l2 = sTGMEDIUM.unionField;
        iDataObject.Release();
        long l3 = OS.GlobalLock(l2);
        if (l3 == 0L) {
            Object var9_7 = null;
            OS.GlobalFree(l2);
            return var9_7;
        }
        BITMAPINFOHEADER bITMAPINFOHEADER = new BITMAPINFOHEADER();
        OS.MoveMemory(bITMAPINFOHEADER, l3, BITMAPINFOHEADER.sizeof);
        long[] lArray = new long[]{0L};
        long l4 = OS.CreateDIBSection(0L, l3, 0, lArray, 0L, 0);
        if (l4 == 0L) {
            SWT.error(2);
        }
        long l5 = l3 + (long)bITMAPINFOHEADER.biSize;
        if (bITMAPINFOHEADER.biBitCount <= 8) {
            l5 += (long)((bITMAPINFOHEADER.biClrUsed == 0 ? 1 << bITMAPINFOHEADER.biBitCount : bITMAPINFOHEADER.biClrUsed) * 4);
        } else if (bITMAPINFOHEADER.biCompression == 3) {
            l5 += 12L;
        }
        if (bITMAPINFOHEADER.biHeight < 0) {
            OS.MoveMemory(lArray[0], l5, bITMAPINFOHEADER.biSizeImage);
        } else {
            object = new DIBSECTION();
            OS.GetObject(l4, DIBSECTION.sizeof, (DIBSECTION)object);
            int n = ((DIBSECTION)object).biHeight;
            int n2 = ((DIBSECTION)object).biSizeImage / n;
            long l6 = lArray[0];
            long l7 = l5 + (long)(n2 * (n - 1));
            for (int i = 0; i < n; ++i) {
                OS.MoveMemory(l6, l7, n2);
                l6 += (long)n2;
                l7 -= (long)n2;
            }
        }
        object = Image.win32_new(null, 0, l4);
        ImageData imageData = ((Image)object).getImageData(DPIUtil.getDeviceZoom());
        OS.DeleteObject(l4);
        ((Resource)object).dispose();
        ImageData imageData2 = imageData;
        OS.GlobalUnlock(l2);
        OS.GlobalFree(l2);
        return imageData2;
    }

    @Override
    protected int[] getTypeIds() {
        return new int[]{8};
    }

    @Override
    protected String[] getTypeNames() {
        return new String[]{CF_DIB};
    }

    @Override
    protected boolean validate(Object object) {
        return this.checkImage(object);
    }
}

