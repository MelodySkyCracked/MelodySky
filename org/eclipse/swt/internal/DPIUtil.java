/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageDataProvider;
import org.eclipse.swt.graphics.ImageFileNameProvider;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class DPIUtil {
    private static final int DPI_ZOOM_100 = 96;
    private static int deviceZoom = 100;
    private static int nativeDeviceZoom = 100;
    private static AutoScaleMethod autoScaleMethodSetting = AutoScaleMethod.AUTO;
    private static AutoScaleMethod autoScaleMethod = AutoScaleMethod.NEAREST;
    private static String autoScaleValue;
    private static boolean useCairoAutoScale;
    private static final String SWT_AUTOSCALE = "swt.autoScale";
    private static final String SWT_AUTOSCALE_METHOD = "swt.autoScale.method";

    public static ImageData autoScaleDown(Device device, ImageData imageData) {
        if (deviceZoom == 100 || imageData == null || device != null && !device.isAutoScalable()) {
            return imageData;
        }
        float f = 1.0f / DPIUtil.getScalingFactor();
        return DPIUtil.autoScaleImageData(device, imageData, f);
    }

    public static int[] autoScaleDown(int[] nArray) {
        if (deviceZoom == 100 || nArray == null) {
            return nArray;
        }
        float f = DPIUtil.getScalingFactor();
        int[] nArray2 = new int[nArray.length];
        for (int i = 0; i < nArray.length; ++i) {
            nArray2[i] = Math.round((float)nArray[i] / f);
        }
        return nArray2;
    }

    public static int[] autoScaleDown(Drawable drawable, int[] nArray) {
        if (drawable != null && !drawable.isAutoScalable()) {
            return nArray;
        }
        return DPIUtil.autoScaleDown(nArray);
    }

    public static float[] autoScaleDown(float[] fArray) {
        if (deviceZoom == 100 || fArray == null) {
            return fArray;
        }
        float f = DPIUtil.getScalingFactor();
        float[] fArray2 = new float[fArray.length];
        for (int i = 0; i < fArray2.length; ++i) {
            fArray2[i] = fArray[i] / f;
        }
        return fArray2;
    }

    public static float[] autoScaleDown(Drawable drawable, float[] fArray) {
        if (drawable != null && !drawable.isAutoScalable()) {
            return fArray;
        }
        return DPIUtil.autoScaleDown(fArray);
    }

    public static int autoScaleDown(int n) {
        if (deviceZoom == 100 || n == -1) {
            return n;
        }
        float f = DPIUtil.getScalingFactor();
        return Math.round((float)n / f);
    }

    public static int autoScaleDown(Drawable drawable, int n) {
        if (drawable != null && !drawable.isAutoScalable()) {
            return n;
        }
        return DPIUtil.autoScaleDown(n);
    }

    public static float autoScaleDown(float f) {
        if (deviceZoom == 100 || f == -1.0f) {
            return f;
        }
        float f2 = DPIUtil.getScalingFactor();
        return f / f2;
    }

    public static float autoScaleDown(Drawable drawable, float f) {
        if (drawable != null && !drawable.isAutoScalable()) {
            return f;
        }
        return DPIUtil.autoScaleDown(f);
    }

    public static Point autoScaleDown(Point point) {
        if (deviceZoom == 100 || point == null) {
            return point;
        }
        float f = DPIUtil.getScalingFactor();
        Point point2 = new Point(0, 0);
        point2.x = Math.round((float)point.x / f);
        point2.y = Math.round((float)point.y / f);
        return point2;
    }

    public static Point autoScaleDown(Drawable drawable, Point point) {
        if (drawable != null && !drawable.isAutoScalable()) {
            return point;
        }
        return DPIUtil.autoScaleDown(point);
    }

    public static Rectangle autoScaleDown(Rectangle rectangle) {
        if (deviceZoom == 100 || rectangle == null) {
            return rectangle;
        }
        Rectangle rectangle2 = new Rectangle(0, 0, 0, 0);
        Point point = DPIUtil.autoScaleDown(new Point(rectangle.x, rectangle.y));
        Point point2 = DPIUtil.autoScaleDown(new Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height));
        rectangle2.x = point.x;
        rectangle2.y = point.y;
        rectangle2.width = point2.x - point.x;
        rectangle2.height = point2.y - point.y;
        return rectangle2;
    }

    public static Rectangle autoScaleDown(Drawable drawable, Rectangle rectangle) {
        if (drawable != null && !drawable.isAutoScalable()) {
            return rectangle;
        }
        return DPIUtil.autoScaleDown(rectangle);
    }

    public static ImageData autoScaleImageData(Device device, ImageData imageData, int n, int n2) {
        if (imageData == null || n == n2 || device != null && !device.isAutoScalable()) {
            return imageData;
        }
        float f = (float)n / (float)n2;
        return DPIUtil.autoScaleImageData(device, imageData, f);
    }

    private static ImageData autoScaleImageData(Device device, ImageData imageData, float f) {
        int n = imageData.width;
        int n2 = imageData.height;
        int n3 = Math.round((float)n * f);
        int n4 = Math.round((float)n2 * f);
        switch (autoScaleMethod) {
            case SMOOTH: {
                Image image = new Image(device, imageData);
                ImageData imageData2 = new ImageData(n3, n4, 24, new PaletteData(255, 65280, 0xFF0000));
                imageData2.alphaData = new byte[n3 * n4];
                Image image2 = new Image(device, imageData);
                GC gC = new GC(image2);
                gC.setAntialias(1);
                gC.drawImage(image, 0, 0, DPIUtil.autoScaleDown(n), DPIUtil.autoScaleDown(n2), 0, 0, Math.round(DPIUtil.autoScaleDown((float)n * f)), Math.round(DPIUtil.autoScaleDown((float)n2 * f)));
                gC.dispose();
                image.dispose();
                ImageData imageData3 = image2.getImageData(DPIUtil.getDeviceZoom());
                image2.dispose();
                return imageData3;
            }
        }
        return imageData.scaledTo(n3, n4);
    }

    public static Rectangle autoScaleBounds(Rectangle rectangle, int n, int n2) {
        if (deviceZoom == 100 || rectangle == null || n == n2) {
            return rectangle;
        }
        float f = (float)n / (float)n2;
        Rectangle rectangle2 = new Rectangle(0, 0, 0, 0);
        rectangle2.x = Math.round((float)rectangle.x * f);
        rectangle2.y = Math.round((float)rectangle.y * f);
        rectangle2.width = Math.round((float)rectangle.width * f);
        rectangle2.height = Math.round((float)rectangle.height * f);
        return rectangle2;
    }

    public static ImageData autoScaleUp(Device device, ImageData imageData) {
        if (deviceZoom == 100 || imageData == null || device != null && !device.isAutoScalable()) {
            return imageData;
        }
        float f = (float)deviceZoom / 100.0f;
        return DPIUtil.autoScaleImageData(device, imageData, f);
    }

    public static int[] autoScaleUp(int[] nArray) {
        if (deviceZoom == 100 || nArray == null) {
            return nArray;
        }
        float f = DPIUtil.getScalingFactor();
        int[] nArray2 = new int[nArray.length];
        for (int i = 0; i < nArray.length; ++i) {
            nArray2[i] = Math.round((float)nArray[i] * f);
        }
        return nArray2;
    }

    public static int[] autoScaleUp(Drawable drawable, int[] nArray) {
        if (drawable != null && !drawable.isAutoScalable()) {
            return nArray;
        }
        return DPIUtil.autoScaleUp(nArray);
    }

    public static int autoScaleUp(int n) {
        if (deviceZoom == 100 || n == -1) {
            return n;
        }
        float f = DPIUtil.getScalingFactor();
        return Math.round((float)n * f);
    }

    public static int autoScaleUpUsingNativeDPI(int n) {
        if (nativeDeviceZoom == 100 || n == -1) {
            return n;
        }
        float f = (float)nativeDeviceZoom / 100.0f;
        return Math.round((float)n * f);
    }

    public static int autoScaleUp(Drawable drawable, int n) {
        if (drawable != null && !drawable.isAutoScalable()) {
            return n;
        }
        return DPIUtil.autoScaleUp(n);
    }

    public static float autoScaleUp(float f) {
        if (deviceZoom == 100 || f == -1.0f) {
            return f;
        }
        float f2 = DPIUtil.getScalingFactor();
        return f * f2;
    }

    public static float autoScaleUp(Drawable drawable, float f) {
        if (drawable != null && !drawable.isAutoScalable()) {
            return f;
        }
        return DPIUtil.autoScaleUp(f);
    }

    public static Point autoScaleUp(Point point) {
        if (deviceZoom == 100 || point == null) {
            return point;
        }
        float f = DPIUtil.getScalingFactor();
        Point point2 = new Point(0, 0);
        point2.x = Math.round((float)point.x * f);
        point2.y = Math.round((float)point.y * f);
        return point2;
    }

    public static Point autoScaleUp(Drawable drawable, Point point) {
        if (drawable != null && !drawable.isAutoScalable()) {
            return point;
        }
        return DPIUtil.autoScaleUp(point);
    }

    public static Rectangle autoScaleUp(Rectangle rectangle) {
        if (deviceZoom == 100 || rectangle == null) {
            return rectangle;
        }
        Rectangle rectangle2 = new Rectangle(0, 0, 0, 0);
        Point point = DPIUtil.autoScaleUp(new Point(rectangle.x, rectangle.y));
        Point point2 = DPIUtil.autoScaleUp(new Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height));
        rectangle2.x = point.x;
        rectangle2.y = point.y;
        rectangle2.width = point2.x - point.x;
        rectangle2.height = point2.y - point.y;
        return rectangle2;
    }

    public static Rectangle autoScaleUp(Drawable drawable, Rectangle rectangle) {
        if (drawable != null && !drawable.isAutoScalable()) {
            return rectangle;
        }
        return DPIUtil.autoScaleUp(rectangle);
    }

    private static float getScalingFactor() {
        if (useCairoAutoScale) {
            return 1.0f;
        }
        return (float)deviceZoom / 100.0f;
    }

    public static int mapDPIToZoom(int n) {
        double d = (double)n * 100.0 / 96.0;
        int n2 = (int)Math.round(d);
        return n2;
    }

    public static ImageData validateAndGetImageDataAtZoom(ImageDataProvider imageDataProvider, int n, boolean[] blArray) {
        ImageData imageData;
        if (imageDataProvider == null) {
            SWT.error(4);
        }
        boolean bl = blArray[0] = (imageData = imageDataProvider.getImageData(n)) != null;
        if (n != 100 && !blArray[0]) {
            imageData = imageDataProvider.getImageData(100);
        }
        if (imageData == null) {
            SWT.error(5, null, ": ImageDataProvider [" + imageDataProvider + "] returns null ImageData at 100% zoom.");
        }
        return imageData;
    }

    public static String validateAndGetImagePathAtZoom(ImageFileNameProvider imageFileNameProvider, int n, boolean[] blArray) {
        String string;
        if (imageFileNameProvider == null) {
            SWT.error(4);
        }
        boolean bl = blArray[0] = (string = imageFileNameProvider.getImagePath(n)) != null;
        if (n != 100 && !blArray[0]) {
            string = imageFileNameProvider.getImagePath(100);
        }
        if (string == null) {
            SWT.error(5, null, ": ImageFileNameProvider [" + imageFileNameProvider + "] returns null filename at 100% zoom.");
        }
        return string;
    }

    public static int getDeviceZoom() {
        return deviceZoom;
    }

    public static void setDeviceZoom(int n) {
        nativeDeviceZoom = n;
        int n2 = deviceZoom = DPIUtil.getZoomForAutoscaleProperty(n);
        System.setProperty("org.eclipse.swt.internal.deviceZoom", Integer.toString(n2));
        if (n2 != 100 && autoScaleMethodSetting == AutoScaleMethod.AUTO) {
            autoScaleMethod = n2 / 100 * 100 == n2 || !"gtk".equals(SWT.getPlatform()) ? AutoScaleMethod.NEAREST : AutoScaleMethod.SMOOTH;
        }
    }

    public static void setUseCairoAutoScale(boolean bl) {
        useCairoAutoScale = bl;
    }

    public static boolean useCairoAutoScale() {
        return useCairoAutoScale;
    }

    public static int getZoomForAutoscaleProperty(int n) {
        int n2 = 0;
        if (autoScaleValue != null) {
            if ("false".equalsIgnoreCase(autoScaleValue)) {
                n2 = 100;
            } else if ("quarter".equalsIgnoreCase(autoScaleValue)) {
                n2 = Math.round((float)n / 25.0f) * 25;
            } else if ("exact".equalsIgnoreCase(autoScaleValue)) {
                n2 = n;
            } else {
                try {
                    int n3 = Integer.parseInt(autoScaleValue);
                    n2 = Math.max(Math.min(n3, 1600), 25);
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
        }
        if (n2 == 0) {
            n2 = Math.max((n + 25) / 100 * 100, 100);
        }
        return n2;
    }

    static {
        useCairoAutoScale = false;
        autoScaleValue = System.getProperty(SWT_AUTOSCALE);
        String string = System.getProperty(SWT_AUTOSCALE_METHOD);
        if (string != null) {
            if (AutoScaleMethod.NEAREST.name().equalsIgnoreCase(string)) {
                autoScaleMethod = autoScaleMethodSetting = AutoScaleMethod.NEAREST;
            } else if (AutoScaleMethod.SMOOTH.name().equalsIgnoreCase(string)) {
                autoScaleMethod = autoScaleMethodSetting = AutoScaleMethod.SMOOTH;
            }
        }
    }

    public static final class AutoScaleImageDataProvider
    implements ImageDataProvider {
        Device device;
        ImageData imageData;
        int currentZoom;

        public AutoScaleImageDataProvider(Device device, ImageData imageData, int n) {
            this.device = device;
            this.imageData = imageData;
            this.currentZoom = n;
        }

        @Override
        public ImageData getImageData(int n) {
            return DPIUtil.autoScaleImageData(this.device, this.imageData, n, this.currentZoom);
        }
    }

    private static enum AutoScaleMethod {
        AUTO,
        NEAREST,
        SMOOTH;

    }
}

