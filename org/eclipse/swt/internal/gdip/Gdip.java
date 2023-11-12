/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.gdip;

import org.eclipse.swt.internal.Library;
import org.eclipse.swt.internal.Platform;
import org.eclipse.swt.internal.gdip.BitmapData;
import org.eclipse.swt.internal.gdip.ColorPalette;
import org.eclipse.swt.internal.gdip.GdiplusStartupInput;
import org.eclipse.swt.internal.gdip.PointF;
import org.eclipse.swt.internal.gdip.Rect;
import org.eclipse.swt.internal.gdip.RectF;

public class Gdip
extends Platform {
    public static final float FlatnessDefault = 0.25f;
    public static final int BrushTypeSolidColor = 0;
    public static final int BrushTypeHatchFill = 1;
    public static final int BrushTypeTextureFill = 2;
    public static final int BrushTypePathGradient = 3;
    public static final int BrushTypeLinearGradient = 4;
    public static final int ColorAdjustTypeBitmap = 1;
    public static final int ColorMatrixFlagsDefault = 0;
    public static final int CombineModeReplace = 0;
    public static final int CombineModeIntersect = 1;
    public static final int CombineModeUnion = 2;
    public static final int CombineModeXor = 3;
    public static final int CombineModeExclude = 4;
    public static final int CombineModeComplement = 5;
    public static final int FillModeAlternate = 0;
    public static final int FillModeWinding = 1;
    public static final int DashCapFlat = 0;
    public static final int DashCapRound = 2;
    public static final int DashCapTriangle = 3;
    public static final int DashStyleSolid = 0;
    public static final int DashStyleDash = 1;
    public static final int DashStyleDot = 2;
    public static final int DashStyleDashDot = 3;
    public static final int DashStyleDashDotDot = 4;
    public static final int DashStyleCustom = 5;
    public static final int DriverStringOptionsRealizedAdvance = 4;
    public static final int FontStyleRegular = 0;
    public static final int FontStyleBold = 1;
    public static final int FontStyleItalic = 2;
    public static final int FontStyleBoldItalic = 3;
    public static final int FontStyleUnderline = 4;
    public static final int FontStyleStrikeout = 8;
    public static final int PaletteFlagsHasAlpha = 1;
    public static final int FlushIntentionFlush = 0;
    public static final int FlushIntentionSync = 1;
    public static final int HotkeyPrefixNone = 0;
    public static final int HotkeyPrefixShow = 1;
    public static final int HotkeyPrefixHide = 2;
    public static final int LineJoinMiter = 0;
    public static final int LineJoinBevel = 1;
    public static final int LineJoinRound = 2;
    public static final int LineCapFlat = 0;
    public static final int LineCapSquare = 1;
    public static final int LineCapRound = 2;
    public static final int MatrixOrderPrepend = 0;
    public static final int MatrixOrderAppend = 1;
    public static final int QualityModeDefault = 0;
    public static final int QualityModeLow = 1;
    public static final int QualityModeHigh = 2;
    public static final int InterpolationModeDefault = 0;
    public static final int InterpolationModeLowQuality = 1;
    public static final int InterpolationModeHighQuality = 2;
    public static final int InterpolationModeBilinear = 3;
    public static final int InterpolationModeBicubic = 4;
    public static final int InterpolationModeNearestNeighbor = 5;
    public static final int InterpolationModeHighQualityBilinear = 6;
    public static final int InterpolationModeHighQualityBicubic = 7;
    public static final int PathPointTypeStart = 0;
    public static final int PathPointTypeLine = 1;
    public static final int PathPointTypeBezier = 3;
    public static final int PathPointTypePathTypeMask = 7;
    public static final int PathPointTypePathDashMode = 16;
    public static final int PathPointTypePathMarker = 32;
    public static final int PathPointTypeCloseSubpath = 128;
    public static final int PathPointTypeBezier3 = 3;
    public static final int PixelFormatIndexed = 65536;
    public static final int PixelFormatGDI = 131072;
    public static final int PixelFormatAlpha = 262144;
    public static final int PixelFormatPAlpha = 524288;
    public static final int PixelFormatExtended = 0x100000;
    public static final int PixelFormatCanonical = 0x200000;
    public static final int PixelFormat1bppIndexed = 196865;
    public static final int PixelFormat4bppIndexed = 197634;
    public static final int PixelFormat8bppIndexed = 198659;
    public static final int PixelFormat16bppGrayScale = 0x101004;
    public static final int PixelFormat16bppRGB555 = 135173;
    public static final int PixelFormat16bppRGB565 = 135174;
    public static final int PixelFormat16bppARGB1555 = 397319;
    public static final int PixelFormat24bppRGB = 137224;
    public static final int PixelFormat32bppRGB = 139273;
    public static final int PixelFormat32bppARGB = 2498570;
    public static final int PixelFormat32bppPARGB = 925707;
    public static final int PixelFormat48bppRGB = 1060876;
    public static final int PixelFormat64bppARGB = 3424269;
    public static final int PixelFormat64bppPARGB = 1851406;
    public static final int PixelFormat32bppCMYK = 8207;
    public static final int PixelFormatMax = 16;
    public static final int PixelOffsetModeNone = 3;
    public static final int PixelOffsetModeHalf = 4;
    public static final int SmoothingModeDefault = 0;
    public static final int SmoothingModeHighSpeed = 1;
    public static final int SmoothingModeHighQuality = 2;
    public static final int SmoothingModeNone = 3;
    public static final int SmoothingModeAntiAlias8x4 = 4;
    public static final int SmoothingModeAntiAlias = 4;
    public static final int SmoothingModeAntiAlias8x8 = 5;
    public static final int StringFormatFlagsDirectionRightToLeft = 1;
    public static final int StringFormatFlagsDirectionVertical = 2;
    public static final int StringFormatFlagsNoFitBlackBox = 4;
    public static final int StringFormatFlagsDisplayFormatControl = 32;
    public static final int StringFormatFlagsNoFontFallback = 1024;
    public static final int StringFormatFlagsMeasureTrailingSpaces = 2048;
    public static final int StringFormatFlagsNoWrap = 4096;
    public static final int StringFormatFlagsLineLimit = 8192;
    public static final int StringFormatFlagsNoClip = 16384;
    public static final int TextRenderingHintSystemDefault = 0;
    public static final int TextRenderingHintSingleBitPerPixelGridFit = 1;
    public static final int TextRenderingHintSingleBitPerPixel = 2;
    public static final int TextRenderingHintAntiAliasGridFit = 3;
    public static final int TextRenderingHintAntiAlias = 4;
    public static final int TextRenderingHintClearTypeGridFit = 5;
    public static final int UnitPixel = 2;
    public static final int WrapModeTile = 0;
    public static final int WrapModeTileFlipX = 1;
    public static final int WrapModeTileFlipY = 2;
    public static final int WrapModeTileFlipXY = 3;
    public static final int WrapModeClamp = 4;

    public static final native int ColorPalette_sizeof();

    public static final native int GdiplusStartupInput_sizeof();

    public static final native int GdiplusStartup(long[] var0, GdiplusStartupInput var1, long var2);

    public static final native void GdiplusShutdown(long var0);

    public static final native long Bitmap_new(long var0, long var2);

    public static final native long Bitmap_new(long var0);

    public static final native long Bitmap_new(int var0, int var1, int var2, int var3, long var4);

    public static final native long Bitmap_new(char[] var0, boolean var1);

    public static final native void Bitmap_delete(long var0);

    public static final native int Bitmap_GetHBITMAP(long var0, int var2, long[] var3);

    public static final native int Bitmap_GetHICON(long var0, long[] var2);

    public static final native long BitmapData_new();

    public static final native void BitmapData_delete(long var0);

    public static final native int Bitmap_LockBits(long var0, long var2, int var4, int var5, long var6);

    public static final native int Bitmap_UnlockBits(long var0, long var2);

    public static final native long Brush_Clone(long var0);

    public static final native int Brush_GetType(long var0);

    public static final native long PrivateFontCollection_new();

    public static final native void PrivateFontCollection_delete(long var0);

    public static final native int PrivateFontCollection_AddFontFile(long var0, char[] var2);

    public static final native long Font_new(long var0, long var2);

    public static final native long Font_new(long var0, float var2, int var3, int var4);

    public static final native long Font_new(char[] var0, float var1, int var2, int var3, long var4);

    public static final native void Font_delete(long var0);

    public static final native int Font_GetFamily(long var0, long var2);

    public static final native float Font_GetSize(long var0);

    public static final native int Font_GetStyle(long var0);

    public static final native int Font_GetLogFontW(long var0, long var2, long var4);

    public static final native boolean Font_IsAvailable(long var0);

    public static final native long FontFamily_new();

    public static final native long FontFamily_new(char[] var0, long var1);

    public static final native void FontFamily_delete(long var0);

    public static final native int FontFamily_GetFamilyName(long var0, char[] var2, char var3);

    public static final native boolean FontFamily_IsAvailable(long var0);

    public static final native long Graphics_new(long var0);

    public static final native void Graphics_delete(long var0);

    public static final native int Graphics_DrawArc(long var0, long var2, int var4, int var5, int var6, int var7, float var8, float var9);

    public static final native int Graphics_DrawDriverString(long var0, long var2, int var4, long var5, long var7, PointF var9, int var10, long var11);

    public static final native int Graphics_DrawDriverString(long var0, long var2, int var4, long var5, long var7, float[] var9, int var10, long var11);

    public static final native int Graphics_DrawEllipse(long var0, long var2, int var4, int var5, int var6, int var7);

    public static final native int Graphics_DrawImage(long var0, long var2, int var4, int var5);

    public static final native int Graphics_DrawImage(long var0, long var2, Rect var4, int var5, int var6, int var7, int var8, int var9, long var10, long var12, long var14);

    public static final native int Graphics_DrawLine(long var0, long var2, int var4, int var5, int var6, int var7);

    public static final native int Graphics_DrawLines(long var0, long var2, int[] var4, int var5);

    public static final native int Graphics_DrawPath(long var0, long var2, long var4);

    public static final native int Graphics_DrawPolygon(long var0, long var2, int[] var4, int var5);

    public static final native int Graphics_DrawRectangle(long var0, long var2, int var4, int var5, int var6, int var7);

    public static final native int Graphics_DrawString(long var0, char[] var2, int var3, long var4, PointF var6, long var7);

    public static final native int Graphics_DrawString(long var0, char[] var2, int var3, long var4, PointF var6, long var7, long var9);

    public static final native int Graphics_FillEllipse(long var0, long var2, int var4, int var5, int var6, int var7);

    public static final native int Graphics_FillPath(long var0, long var2, long var4);

    public static final native void Graphics_Flush(long var0, int var2);

    public static final native int Graphics_FillPie(long var0, long var2, int var4, int var5, int var6, int var7, float var8, float var9);

    public static final native int Graphics_FillPolygon(long var0, long var2, int[] var4, int var5, int var6);

    public static final native int Graphics_FillRectangle(long var0, long var2, int var4, int var5, int var6, int var7);

    public static final native int Graphics_GetClipBounds(long var0, RectF var2);

    public static final native int Graphics_GetClipBounds(long var0, Rect var2);

    public static final native int Graphics_GetClip(long var0, long var2);

    public static final native long Graphics_GetHDC(long var0);

    public static final native void Graphics_ReleaseHDC(long var0, long var2);

    public static final native int Graphics_GetInterpolationMode(long var0);

    public static final native int Graphics_GetSmoothingMode(long var0);

    public static final native int Graphics_GetTextRenderingHint(long var0);

    public static final native int Graphics_GetTransform(long var0, long var2);

    public static final native int Graphics_GetVisibleClipBounds(long var0, Rect var2);

    public static final native int Graphics_MeasureDriverString(long var0, long var2, int var4, long var5, float[] var7, int var8, long var9, RectF var11);

    public static final native int Graphics_MeasureString(long var0, char[] var2, int var3, long var4, PointF var6, RectF var7);

    public static final native int Graphics_MeasureString(long var0, char[] var2, int var3, long var4, PointF var6, long var7, RectF var9);

    public static final native int Graphics_ResetClip(long var0);

    public static final native int Graphics_Restore(long var0, int var2);

    public static final native int Graphics_Save(long var0);

    public static final native int Graphics_ScaleTransform(long var0, float var2, float var3, int var4);

    public static final native int Graphics_SetClip(long var0, long var2, int var4);

    public static final native int Graphics_SetClip(long var0, Rect var2, int var3);

    public static final native int Graphics_SetClipPath(long var0, long var2);

    public static final native int Graphics_SetClipPath(long var0, long var2, int var4);

    public static final native int Graphics_SetCompositingQuality(long var0, int var2);

    public static final native int Graphics_SetPageUnit(long var0, int var2);

    public static final native int Graphics_SetPixelOffsetMode(long var0, int var2);

    public static final native int Graphics_SetSmoothingMode(long var0, int var2);

    public static final native int Graphics_SetTransform(long var0, long var2);

    public static final native int Graphics_SetInterpolationMode(long var0, int var2);

    public static final native int Graphics_SetTextRenderingHint(long var0, int var2);

    public static final native int Graphics_TranslateTransform(long var0, float var2, float var3, int var4);

    public static final native long GraphicsPath_new(int var0);

    public static final native long GraphicsPath_new(int[] var0, byte[] var1, int var2, int var3);

    public static final native void GraphicsPath_delete(long var0);

    public static final native int GraphicsPath_AddArc(long var0, float var2, float var3, float var4, float var5, float var6, float var7);

    public static final native int GraphicsPath_AddBezier(long var0, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9);

    public static final native int GraphicsPath_AddLine(long var0, float var2, float var3, float var4, float var5);

    public static final native int GraphicsPath_AddPath(long var0, long var2, boolean var4);

    public static final native int GraphicsPath_AddRectangle(long var0, RectF var2);

    public static final native int GraphicsPath_AddString(long var0, char[] var2, int var3, long var4, int var6, float var7, PointF var8, long var9);

    public static final native int GraphicsPath_CloseFigure(long var0);

    public static final native long GraphicsPath_Clone(long var0);

    public static final native int GraphicsPath_Flatten(long var0, long var2, float var4);

    public static final native int GraphicsPath_GetBounds(long var0, RectF var2, long var3, long var5);

    public static final native int GraphicsPath_GetLastPoint(long var0, PointF var2);

    public static final native int GraphicsPath_GetPathPoints(long var0, float[] var2, int var3);

    public static final native int GraphicsPath_GetPathTypes(long var0, byte[] var2, int var3);

    public static final native int GraphicsPath_GetPointCount(long var0);

    public static final native boolean GraphicsPath_IsOutlineVisible(long var0, float var2, float var3, long var4, long var6);

    public static final native boolean GraphicsPath_IsVisible(long var0, float var2, float var3, long var4);

    public static final native int GraphicsPath_SetFillMode(long var0, int var2);

    public static final native int GraphicsPath_StartFigure(long var0);

    public static final native int GraphicsPath_Transform(long var0, long var2);

    public static final native long HatchBrush_new(int var0, int var1, int var2);

    public static final native void Image_delete(long var0);

    public static final native long Image_Clone(long var0);

    public static final native int Image_GetLastStatus(long var0);

    public static final native int Image_GetPixelFormat(long var0);

    public static final native int Image_GetWidth(long var0);

    public static final native int Image_GetHeight(long var0);

    public static final native int Image_GetPalette(long var0, long var2, int var4);

    public static final native int Image_GetPaletteSize(long var0);

    public static final native long ImageAttributes_new();

    public static final native void ImageAttributes_delete(long var0);

    public static final native int ImageAttributes_SetWrapMode(long var0, int var2);

    public static final native int ImageAttributes_SetColorMatrix(long var0, float[] var2, int var3, int var4);

    public static final native void HatchBrush_delete(long var0);

    public static final native long LinearGradientBrush_new(PointF var0, PointF var1, int var2, int var3);

    public static final native void LinearGradientBrush_delete(long var0);

    public static final native int LinearGradientBrush_SetInterpolationColors(long var0, int[] var2, float[] var3, int var4);

    public static final native int LinearGradientBrush_SetWrapMode(long var0, int var2);

    public static final native int LinearGradientBrush_ResetTransform(long var0);

    public static final native int LinearGradientBrush_ScaleTransform(long var0, float var2, float var3, int var4);

    public static final native int LinearGradientBrush_TranslateTransform(long var0, float var2, float var3, int var4);

    public static final native long Matrix_new(float var0, float var1, float var2, float var3, float var4, float var5);

    public static final native void Matrix_delete(long var0);

    public static final native int Matrix_GetElements(long var0, float[] var2);

    public static final native int Matrix_Invert(long var0);

    public static final native boolean Matrix_IsIdentity(long var0);

    public static final native int Matrix_Multiply(long var0, long var2, int var4);

    public static final native int Matrix_Rotate(long var0, float var2, int var3);

    public static final native int Matrix_Scale(long var0, float var2, float var3, int var4);

    public static final native int Matrix_Shear(long var0, float var2, float var3, int var4);

    public static final native int Matrix_TransformPoints(long var0, PointF var2, int var3);

    public static final native int Matrix_TransformPoints(long var0, float[] var2, int var3);

    public static final native int Matrix_TransformVectors(long var0, PointF var2, int var3);

    public static final native int Matrix_Translate(long var0, float var2, float var3, int var4);

    public static final native int Matrix_SetElements(long var0, float var2, float var3, float var4, float var5, float var6, float var7);

    public static final native void MoveMemory(ColorPalette var0, long var1, int var3);

    public static final native void MoveMemory(BitmapData var0, long var1);

    public static final native long PathGradientBrush_new(long var0);

    public static final native void PathGradientBrush_delete(long var0);

    public static final native int PathGradientBrush_SetCenterColor(long var0, int var2);

    public static final native int PathGradientBrush_SetCenterPoint(long var0, PointF var2);

    public static final native int PathGradientBrush_SetInterpolationColors(long var0, int[] var2, float[] var3, int var4);

    public static final native int PathGradientBrush_SetSurroundColors(long var0, int[] var2, int[] var3);

    public static final native int PathGradientBrush_SetGraphicsPath(long var0, long var2);

    public static final native int PathGradientBrush_SetWrapMode(long var0, int var2);

    public static final native long Pen_new(long var0, float var2);

    public static final native void Pen_delete(long var0);

    public static final native long Pen_GetBrush(long var0);

    public static final native int Pen_SetBrush(long var0, long var2);

    public static final native int Pen_SetDashOffset(long var0, float var2);

    public static final native int Pen_SetDashPattern(long var0, float[] var2, int var3);

    public static final native int Pen_SetDashStyle(long var0, int var2);

    public static final native int Pen_SetLineCap(long var0, int var2, int var3, int var4);

    public static final native int Pen_SetLineJoin(long var0, int var2);

    public static final native int Pen_SetMiterLimit(long var0, float var2);

    public static final native int Pen_SetWidth(long var0, float var2);

    public static final native long Point_new(int var0, int var1);

    public static final native void Point_delete(long var0);

    public static final native long Region_new(long var0);

    public static final native long Region_newGraphicsPath(long var0);

    public static final native long Region_new();

    public static final native void Region_delete(long var0);

    public static final native long Region_GetHRGN(long var0, long var2);

    public static final native boolean Region_IsInfinite(long var0, long var2);

    public static final native long SolidBrush_new(int var0);

    public static final native void SolidBrush_delete(long var0);

    public static final native void StringFormat_delete(long var0);

    public static final native long StringFormat_Clone(long var0);

    public static final native long StringFormat_GenericDefault();

    public static final native long StringFormat_GenericTypographic();

    public static final native int StringFormat_GetFormatFlags(long var0);

    public static final native int StringFormat_SetHotkeyPrefix(long var0, int var2);

    public static final native int StringFormat_SetFormatFlags(long var0, int var2);

    public static final native int StringFormat_SetTabStops(long var0, float var2, int var3, float[] var4);

    public static final native long TextureBrush_new(long var0, int var2, float var3, float var4, float var5, float var6);

    public static final native long TextureBrush_new(long var0, Rect var2, long var3);

    public static final native void TextureBrush_delete(long var0);

    public static final native int TextureBrush_SetTransform(long var0, long var2);

    public static final native int TextureBrush_ResetTransform(long var0);

    public static final native int TextureBrush_ScaleTransform(long var0, float var2, float var3, int var4);

    public static final native int TextureBrush_TranslateTransform(long var0, float var2, float var3, int var4);

    public static final native long TextureBrush_GetImage(long var0);

    static {
        Library.loadLibrary("swt-gdip");
    }
}

