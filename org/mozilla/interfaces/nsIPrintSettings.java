/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIPrintSettings
extends nsISupports {
    public static final String NS_IPRINTSETTINGS_IID = "{f1094df6-ce0e-42c9-9847-2f663172c38d}";
    public static final long kInitSaveOddEvenPages = 1L;
    public static final long kInitSaveHeaderLeft = 2L;
    public static final long kInitSaveHeaderCenter = 4L;
    public static final long kInitSaveHeaderRight = 8L;
    public static final long kInitSaveFooterLeft = 16L;
    public static final long kInitSaveFooterCenter = 32L;
    public static final long kInitSaveFooterRight = 64L;
    public static final long kInitSaveBGColors = 128L;
    public static final long kInitSaveBGImages = 256L;
    public static final long kInitSavePaperSize = 512L;
    public static final long kInitSavePaperName = 1024L;
    public static final long kInitSavePaperSizeUnit = 2048L;
    public static final long kInitSavePaperSizeType = 4096L;
    public static final long kInitSavePaperData = 8192L;
    public static final long kInitSavePaperWidth = 16384L;
    public static final long kInitSavePaperHeight = 32768L;
    public static final long kInitSaveReversed = 65536L;
    public static final long kInitSaveInColor = 131072L;
    public static final long kInitSaveOrientation = 262144L;
    public static final long kInitSavePrintCommand = 524288L;
    public static final long kInitSavePrinterName = 0x100000L;
    public static final long kInitSavePrintToFile = 0x200000L;
    public static final long kInitSaveToFileName = 0x400000L;
    public static final long kInitSavePageDelay = 0x800000L;
    public static final long kInitSaveMargins = 0x1000000L;
    public static final long kInitSaveNativeData = 0x2000000L;
    public static final long kInitSavePlexName = 0x4000000L;
    public static final long kInitSaveShrinkToFit = 0x8000000L;
    public static final long kInitSaveScaling = 0x10000000L;
    public static final long kInitSaveColorspace = 0x20000000L;
    public static final long kInitSaveResolutionName = 0x40000000L;
    public static final long kInitSaveDownloadFonts = 0x80000000L;
    public static final long kInitSaveAll = 0xFFFFFFFFL;
    public static final int kPrintOddPages = 1;
    public static final int kPrintEvenPages = 2;
    public static final int kEnableSelectionRB = 4;
    public static final int kRangeAllPages = 0;
    public static final int kRangeSpecifiedPageRange = 1;
    public static final int kRangeSelection = 2;
    public static final int kRangeFocusFrame = 3;
    public static final int kJustLeft = 0;
    public static final int kJustCenter = 1;
    public static final int kJustRight = 2;
    public static final short kUseInternalDefault = 0;
    public static final short kUseSettingWhenPossible = 1;
    public static final short kPaperSizeNativeData = 0;
    public static final short kPaperSizeDefined = 1;
    public static final short kPaperSizeInches = 0;
    public static final short kPaperSizeMillimeters = 1;
    public static final short kPortraitOrientation = 0;
    public static final short kLandscapeOrientation = 1;
    public static final short kNoFrames = 0;
    public static final short kFramesAsIs = 1;
    public static final short kSelectedFrame = 2;
    public static final short kEachFrameSep = 3;
    public static final short kFrameEnableNone = 0;
    public static final short kFrameEnableAll = 1;
    public static final short kFrameEnableAsIsAndEach = 2;

    public void setPrintOptions(int var1, boolean var2);

    public boolean getPrintOptions(int var1);

    public int getPrintOptionsBits();

    public void getPageSizeInTwips(int[] var1, int[] var2);

    public nsIPrintSettings _clone();

    public void assign(nsIPrintSettings var1);

    public int getStartPageRange();

    public void setStartPageRange(int var1);

    public int getEndPageRange();

    public void setEndPageRange(int var1);

    public double getMarginTop();

    public void setMarginTop(double var1);

    public double getMarginLeft();

    public void setMarginLeft(double var1);

    public double getMarginBottom();

    public void setMarginBottom(double var1);

    public double getMarginRight();

    public void setMarginRight(double var1);

    public double getScaling();

    public void setScaling(double var1);

    public boolean getPrintBGColors();

    public void setPrintBGColors(boolean var1);

    public boolean getPrintBGImages();

    public void setPrintBGImages(boolean var1);

    public short getPrintRange();

    public void setPrintRange(short var1);

    public String getTitle();

    public void setTitle(String var1);

    public String getDocURL();

    public void setDocURL(String var1);

    public String getHeaderStrLeft();

    public void setHeaderStrLeft(String var1);

    public String getHeaderStrCenter();

    public void setHeaderStrCenter(String var1);

    public String getHeaderStrRight();

    public void setHeaderStrRight(String var1);

    public String getFooterStrLeft();

    public void setFooterStrLeft(String var1);

    public String getFooterStrCenter();

    public void setFooterStrCenter(String var1);

    public String getFooterStrRight();

    public void setFooterStrRight(String var1);

    public short getHowToEnableFrameUI();

    public void setHowToEnableFrameUI(short var1);

    public boolean getIsCancelled();

    public void setIsCancelled(boolean var1);

    public short getPrintFrameTypeUsage();

    public void setPrintFrameTypeUsage(short var1);

    public short getPrintFrameType();

    public void setPrintFrameType(short var1);

    public boolean getPrintSilent();

    public void setPrintSilent(boolean var1);

    public boolean getShrinkToFit();

    public void setShrinkToFit(boolean var1);

    public boolean getShowPrintProgress();

    public void setShowPrintProgress(boolean var1);

    public String getPaperName();

    public void setPaperName(String var1);

    public short getPaperSizeType();

    public void setPaperSizeType(short var1);

    public short getPaperData();

    public void setPaperData(short var1);

    public double getPaperWidth();

    public void setPaperWidth(double var1);

    public double getPaperHeight();

    public void setPaperHeight(double var1);

    public short getPaperSizeUnit();

    public void setPaperSizeUnit(short var1);

    public String getPlexName();

    public void setPlexName(String var1);

    public String getColorspace();

    public void setColorspace(String var1);

    public String getResolutionName();

    public void setResolutionName(String var1);

    public boolean getDownloadFonts();

    public void setDownloadFonts(boolean var1);

    public boolean getPrintReversed();

    public void setPrintReversed(boolean var1);

    public boolean getPrintInColor();

    public void setPrintInColor(boolean var1);

    public int getPaperSize();

    public void setPaperSize(int var1);

    public int getOrientation();

    public void setOrientation(int var1);

    public String getPrintCommand();

    public void setPrintCommand(String var1);

    public int getNumCopies();

    public void setNumCopies(int var1);

    public String getPrinterName();

    public void setPrinterName(String var1);

    public boolean getPrintToFile();

    public void setPrintToFile(boolean var1);

    public String getToFileName();

    public void setToFileName(String var1);

    public int getPrintPageDelay();

    public void setPrintPageDelay(int var1);

    public boolean getIsInitializedFromPrinter();

    public void setIsInitializedFromPrinter(boolean var1);

    public boolean getIsInitializedFromPrefs();

    public void setIsInitializedFromPrefs(boolean var1);
}

