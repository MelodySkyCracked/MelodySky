/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import java.util.ArrayList;
import java.util.Arrays;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.internal.BidiUtil;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.Compatibility;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.gdip.Gdip;
import org.eclipse.swt.internal.gdip.Rect;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IMLangFontLink2;
import org.eclipse.swt.internal.win32.EMR;
import org.eclipse.swt.internal.win32.EMREXTCREATEFONTINDIRECTW;
import org.eclipse.swt.internal.win32.LOGBRUSH;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.OUTLINETEXTMETRIC;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SCRIPT_ANALYSIS;
import org.eclipse.swt.internal.win32.SCRIPT_CONTROL;
import org.eclipse.swt.internal.win32.SCRIPT_FONTPROPERTIES;
import org.eclipse.swt.internal.win32.SCRIPT_ITEM;
import org.eclipse.swt.internal.win32.SCRIPT_LOGATTR;
import org.eclipse.swt.internal.win32.SCRIPT_PROPERTIES;
import org.eclipse.swt.internal.win32.SCRIPT_STATE;
import org.eclipse.swt.internal.win32.TEXTMETRIC;

public final class TextLayout
extends Resource {
    Font font;
    String text;
    String segmentsText;
    int lineSpacingInPoints;
    int ascentInPixels;
    int descentInPixels;
    int alignment;
    int wrapWidth;
    int orientation;
    int textDirection;
    int indent;
    int wrapIndent;
    boolean justify;
    int[] tabs;
    int[] segments;
    char[] segmentsChars;
    StyleItem[] styles;
    int stylesCount;
    StyleItem[] allRuns;
    StyleItem[][] runs;
    int[] lineOffset;
    int[] lineY;
    int[] lineWidth;
    IMLangFontLink2 mLangFontLink2;
    int verticalIndentInPoints;
    static final char LTR_MARK = '\u200e';
    static final char RTL_MARK = '\u200f';
    static final int SCRIPT_VISATTR_SIZEOF = 2;
    static final int GOFFSET_SIZEOF = 8;
    static final int MERGE_MAX = 512;
    static final int TOO_MANY_RUNS = 1024;
    static final int MAX_RUN_LENGTH = 32000;
    static final int MAX_SEARCH_RUN_BREAK = 1000;
    static final int UNDERLINE_IME_DOT = 65536;
    static final int UNDERLINE_IME_DASH = 131072;
    static final int UNDERLINE_IME_THICK = 196608;

    public TextLayout(Device device) {
        super(device);
        int n = -1;
        this.descentInPixels = -1;
        this.ascentInPixels = -1;
        this.wrapWidth = -1;
        this.lineSpacingInPoints = 0;
        this.verticalIndentInPoints = 0;
        this.orientation = 0x2000000;
        this.textDirection = 0x2000000;
        StyleItem[] styleItemArray = new StyleItem[2];
        this.styles = styleItemArray;
        styleItemArray[0] = new StyleItem(this);
        this.styles[1] = new StyleItem(this);
        this.stylesCount = 2;
        this.text = "";
        long[] lArray = new long[]{0L};
        OS.OleInitialize(0L);
        if (COM.CoCreateInstance(COM.CLSID_CMultiLanguage, 0L, 1, COM.IID_IMLangFontLink2, lArray) == 0) {
            this.mLangFontLink2 = new IMLangFontLink2(lArray[0]);
        }
        this.init();
    }

    RECT addClipRect(StyleItem styleItem, RECT rECT, RECT rECT2, int n, int n2) {
        if (rECT2 != null) {
            boolean bl;
            if (rECT == null) {
                rECT = new RECT();
                OS.SetRect(rECT, -1, rECT2.top, -1, rECT2.bottom);
            }
            boolean bl2 = bl = (this.orientation & 0x4000000) != 0;
            if (styleItem.start <= n && n <= styleItem.start + styleItem.length) {
                if (styleItem.analysis.fRTL ^ bl) {
                    rECT.right = rECT2.left;
                } else {
                    rECT.left = rECT2.left;
                }
            }
            if (styleItem.start <= n2 && n2 <= styleItem.start + styleItem.length) {
                if (styleItem.analysis.fRTL ^ bl) {
                    rECT.left = rECT2.right;
                } else {
                    rECT.right = rECT2.right;
                }
            }
        }
        return rECT;
    }

    void breakRun(StyleItem styleItem) {
        if (styleItem.psla != 0L) {
            return;
        }
        char[] cArray = new char[styleItem.length];
        this.segmentsText.getChars(styleItem.start, styleItem.start + styleItem.length, cArray, 0);
        long l2 = OS.GetProcessHeap();
        styleItem.pslaAllocSize = SCRIPT_LOGATTR.sizeof * cArray.length;
        styleItem.psla = OS.HeapAlloc(l2, 8, SCRIPT_LOGATTR.sizeof * cArray.length);
        if (styleItem.psla == 0L) {
            SWT.error(2);
        }
        OS.ScriptBreak(cArray, cArray.length, styleItem.analysis, styleItem.psla);
    }

    void checkLayout() {
        if (this == null) {
            SWT.error(44);
        }
    }

    void computeRuns(GC gC) {
        Object object;
        StyleItem[] styleItemArray;
        int n;
        int n2;
        int n3;
        int n4;
        Object object2;
        if (this.runs != null) {
            return;
        }
        long l2 = gC != null ? gC.handle : this.device.internal_new_GC(null);
        long l3 = OS.CreateCompatibleDC(l2);
        this.allRuns = this.itemize();
        for (int i = 0; i < this.allRuns.length - 1; ++i) {
            object2 = this.allRuns[i];
            OS.SelectObject(l3, this.getItemFont((StyleItem)object2));
            this.shape(l3, (StyleItem)object2);
        }
        SCRIPT_LOGATTR sCRIPT_LOGATTR = new SCRIPT_LOGATTR();
        object2 = new SCRIPT_PROPERTIES();
        int n5 = this.indent;
        int n6 = 0;
        int n7 = 1;
        for (n4 = 0; n4 < this.allRuns.length - 1; ++n4) {
            int n8;
            StyleItem[] styleItemArray2 = this.allRuns[n4];
            if (this.tabs != null && styleItemArray2.tab) {
                n3 = this.tabs.length;
                for (n2 = 0; n2 < n3; ++n2) {
                    if (this.tabs[n2] <= n5) continue;
                    styleItemArray2.width = this.tabs[n2] - n5;
                    break;
                }
                if (n2 == n3) {
                    int n9 = n = n3 > 1 ? this.tabs[n3 - 1] - this.tabs[n3 - 2] : this.tabs[0];
                    if (n > 0) {
                        for (n8 = this.tabs[n3 - 1]; n8 <= n5; n8 += n) {
                        }
                        styleItemArray2.width = n8 - n5;
                    }
                }
                if ((n8 = styleItemArray2.length) > 1) {
                    n = n2 + n8 - 1;
                    if (n < n3) {
                        styleItemArray = styleItemArray2;
                        styleItemArray.width += this.tabs[n] - this.tabs[n2];
                    } else {
                        if (n2 < n3) {
                            styleItemArray = styleItemArray2;
                            styleItemArray.width += this.tabs[n3 - 1] - this.tabs[n2];
                            n8 -= n3 - 1 - n2;
                        }
                        int n10 = n3 > 1 ? this.tabs[n3 - 1] - this.tabs[n3 - 2] : this.tabs[0];
                        object = styleItemArray2;
                        ((StyleItem)object).width += n10 * (n8 - 1);
                    }
                }
            }
            if (this.wrapWidth != -1 && n5 + styleItemArray2.width > this.wrapWidth && !styleItemArray2.tab && !styleItemArray2.lineBreak) {
                StyleItem[] styleItemArray3;
                boolean bl;
                n3 = 0;
                int[] nArray = new int[styleItemArray2.length];
                if (styleItemArray2.style != null && styleItemArray2.style.metrics != null) {
                    nArray[0] = styleItemArray2.width;
                } else {
                    OS.ScriptGetLogicalWidths(styleItemArray2.analysis, styleItemArray2.length, styleItemArray2.glyphCount, styleItemArray2.advances, styleItemArray2.clusters, styleItemArray2.visAttrs, nArray);
                }
                n8 = 0;
                n = this.wrapWidth - n5;
                while (n8 + nArray[n3] < n) {
                    n8 += nArray[n3++];
                }
                n8 = n3;
                n = n4;
                while (n4 >= n6) {
                    this.breakRun((StyleItem)styleItemArray2);
                    while (n3 >= 0) {
                        OS.MoveMemory(sCRIPT_LOGATTR, styleItemArray2.psla + (long)(n3 * SCRIPT_LOGATTR.sizeof), SCRIPT_LOGATTR.sizeof);
                        if (sCRIPT_LOGATTR.fSoftBreak || sCRIPT_LOGATTR.fWhiteSpace) break;
                        --n3;
                    }
                    if (n3 == 0 && n4 != n6 && !styleItemArray2.tab && sCRIPT_LOGATTR.fSoftBreak && !sCRIPT_LOGATTR.fWhiteSpace) {
                        OS.MoveMemory((SCRIPT_PROPERTIES)object2, this.device.scripts[styleItemArray2.analysis.eScript], SCRIPT_PROPERTIES.sizeof);
                        short s = ((SCRIPT_PROPERTIES)object2).langid;
                        object = this.allRuns[n4 - 1];
                        OS.MoveMemory((SCRIPT_PROPERTIES)object2, this.device.scripts[((StyleItem)object).analysis.eScript], SCRIPT_PROPERTIES.sizeof);
                        if (((SCRIPT_PROPERTIES)object2).langid == s || s == 0 || ((SCRIPT_PROPERTIES)object2).langid == 0) {
                            this.breakRun((StyleItem)object);
                            OS.MoveMemory(sCRIPT_LOGATTR, ((StyleItem)object).psla + (long)((((StyleItem)object).length - 1) * SCRIPT_LOGATTR.sizeof), SCRIPT_LOGATTR.sizeof);
                            if (!sCRIPT_LOGATTR.fWhiteSpace) {
                                n3 = -1;
                            }
                        }
                    }
                    if (n3 >= 0 || n4 == n6) break;
                    styleItemArray2 = this.allRuns[--n4];
                    n3 = styleItemArray2.length - 1;
                }
                boolean bl2 = bl = n3 == 0 && n4 != n6 && !styleItemArray2.tab;
                if (bl) {
                    this.breakRun((StyleItem)styleItemArray2);
                    OS.MoveMemory(sCRIPT_LOGATTR, styleItemArray2.psla + (long)(n3 * SCRIPT_LOGATTR.sizeof), SCRIPT_LOGATTR.sizeof);
                    boolean bl3 = bl = !sCRIPT_LOGATTR.fWhiteSpace;
                }
                if (bl) {
                    styleItemArray2 = this.allRuns[--n4];
                    n3 = styleItemArray2.length;
                } else if (n3 <= 0 && n4 == n6) {
                    if (n8 == 0 && n > n6) {
                        n4 = n - 1;
                        styleItemArray2 = this.allRuns[n4];
                        n3 = styleItemArray2.length;
                    } else {
                        n4 = n;
                        styleItemArray2 = this.allRuns[n4];
                        n3 = Math.max(1, n8);
                    }
                }
                this.breakRun((StyleItem)styleItemArray2);
                while (n3 < styleItemArray2.length) {
                    OS.MoveMemory(sCRIPT_LOGATTR, styleItemArray2.psla + (long)(n3 * SCRIPT_LOGATTR.sizeof), SCRIPT_LOGATTR.sizeof);
                    if (!sCRIPT_LOGATTR.fWhiteSpace) break;
                    ++n3;
                }
                if (0 < n3 && n3 < styleItemArray2.length) {
                    object = new StyleItem(this);
                    ((StyleItem)object).start = styleItemArray2.start + n3;
                    ((StyleItem)object).length = styleItemArray2.length - n3;
                    ((StyleItem)object).style = styleItemArray2.style;
                    ((StyleItem)object).analysis = this.cloneScriptAnalysis(styleItemArray2.analysis);
                    styleItemArray2.free();
                    styleItemArray2.length = n3;
                    OS.SelectObject(l3, this.getItemFont((StyleItem)styleItemArray2));
                    styleItemArray2.analysis.fNoGlyphIndex = false;
                    this.shape(l3, (StyleItem)styleItemArray2);
                    OS.SelectObject(l3, this.getItemFont((StyleItem)object));
                    ((StyleItem)object).analysis.fNoGlyphIndex = false;
                    this.shape(l3, (StyleItem)object);
                    styleItemArray3 = new StyleItem[this.allRuns.length + 1];
                    System.arraycopy(this.allRuns, 0, styleItemArray3, 0, n4 + 1);
                    System.arraycopy(this.allRuns, n4 + 1, styleItemArray3, n4 + 2, this.allRuns.length - n4 - 1);
                    this.allRuns = styleItemArray3;
                    styleItemArray3[n4 + 1] = object;
                }
                if (n4 != this.allRuns.length - 2) {
                    object = styleItemArray2;
                    styleItemArray3 = styleItemArray2;
                    boolean bl4 = true;
                    styleItemArray3.lineBreak = true;
                    ((StyleItem)object).softBreak = true;
                }
            }
            n5 += styleItemArray2.width;
            if (!styleItemArray2.lineBreak) continue;
            n6 = n4 + 1;
            n5 = styleItemArray2.softBreak ? this.wrapIndent : this.indent;
            ++n7;
        }
        n5 = 0;
        this.runs = new StyleItem[n7][];
        this.lineOffset = new int[n7 + 1];
        this.lineY = new int[n7 + 1];
        this.lineWidth = new int[n7];
        n4 = 0;
        int n11 = 0;
        n3 = Math.max(0, DPIUtil.autoScaleDown((Drawable)this.getDevice(), this.ascentInPixels));
        n2 = Math.max(0, DPIUtil.autoScaleDown((Drawable)this.getDevice(), this.descentInPixels));
        StyleItem[] styleItemArray4 = new StyleItem[this.allRuns.length];
        for (n = 0; n < this.allRuns.length; ++n) {
            StyleItem[] styleItemArray5;
            styleItemArray = this.allRuns[n];
            styleItemArray4[n4++] = styleItemArray;
            n5 += styleItemArray.width;
            n3 = Math.max(n3, styleItemArray.ascentInPoints);
            n2 = Math.max(n2, styleItemArray.descentInPoints);
            if (!styleItemArray.lineBreak && n != this.allRuns.length - 1) continue;
            if (!(n4 != 1 || n != this.allRuns.length - 1 && styleItemArray.softBreak)) {
                object = new TEXTMETRIC();
                OS.SelectObject(l3, this.getItemFont((StyleItem)styleItemArray));
                OS.GetTextMetrics(l3, (TEXTMETRIC)object);
                styleItemArray.ascentInPoints = DPIUtil.autoScaleDown((Drawable)this.getDevice(), ((TEXTMETRIC)object).tmAscent);
                styleItemArray.descentInPoints = DPIUtil.autoScaleDown((Drawable)this.getDevice(), ((TEXTMETRIC)object).tmDescent);
                n3 = Math.max(n3, styleItemArray.ascentInPoints);
                n2 = Math.max(n2, styleItemArray.descentInPoints);
            }
            this.runs[n11] = new StyleItem[n4];
            System.arraycopy(styleItemArray4, 0, this.runs[n11], 0, n4);
            if (this.justify && this.wrapWidth != -1 && styleItemArray.softBreak && n5 > 0) {
                int n12 = this.wrapIndent;
                if (n11 == 0) {
                    n12 = this.indent;
                } else {
                    StyleItem[] styleItemArray6 = this.runs[n11 - 1];
                    styleItemArray5 = styleItemArray6[styleItemArray6.length - 1];
                    if (styleItemArray5.lineBreak && !styleItemArray5.softBreak) {
                        n12 = this.indent;
                    }
                }
                n5 += n12;
                long l4 = OS.GetProcessHeap();
                int n13 = 0;
                for (StyleItem styleItem : this.runs[n11]) {
                    int n14 = styleItem.width * this.wrapWidth / n5;
                    if (n14 != styleItem.width) {
                        styleItem.justify = OS.HeapAlloc(l4, 8, styleItem.glyphCount * 4);
                        if (styleItem.justify == 0L) {
                            SWT.error(2);
                        }
                        OS.ScriptJustify(styleItem.visAttrs, styleItem.advances, styleItem.glyphCount, n14 - styleItem.width, 2, styleItem.justify);
                        styleItem.width = n14;
                    }
                    n13 += styleItem.width;
                }
                n5 = n13;
            }
            this.lineWidth[n11] = n5;
            object = this.runs[n11][n4 - 1];
            int n15 = ((StyleItem)object).start + ((StyleItem)object).length;
            this.runs[n11] = this.reorder(this.runs[n11], n == this.allRuns.length - 1);
            object = this.runs[n11][n4 - 1];
            if (styleItemArray.softBreak && styleItemArray != object) {
                styleItemArray5 = styleItemArray;
                StyleItem[] styleItemArray7 = styleItemArray;
                boolean bl = false;
                styleItemArray7.lineBreak = false;
                styleItemArray5.softBreak = false;
                Object object3 = object;
                Object object4 = object;
                boolean bl5 = true;
                ((StyleItem)object4).lineBreak = true;
                ((StyleItem)object3).softBreak = true;
            }
            n5 = this.getLineIndent(n11);
            for (StyleItem styleItem : this.runs[n11]) {
                styleItem.x = n5;
                n5 += styleItem.width;
            }
            this.lineY[++n11] = this.lineY[n11 - 1] + n3 + n2 + this.lineSpacingInPoints;
            this.lineOffset[n11] = n15;
            n4 = 0;
            n5 = 0;
            n3 = Math.max(0, DPIUtil.autoScaleDown((Drawable)this.getDevice(), this.ascentInPixels));
            n2 = Math.max(0, DPIUtil.autoScaleDown((Drawable)this.getDevice(), this.descentInPixels));
        }
        if (l3 != 0L) {
            OS.DeleteDC(l3);
        }
        if (gC == null) {
            this.device.internal_dispose_GC(l2, null);
        }
    }

    @Override
    void destroy() {
        this.freeRuns();
        this.font = null;
        this.text = null;
        this.segmentsText = null;
        this.tabs = null;
        this.styles = null;
        this.runs = null;
        this.lineOffset = null;
        this.lineY = null;
        this.lineWidth = null;
        this.segments = null;
        this.segmentsChars = null;
        if (this.mLangFontLink2 != null) {
            this.mLangFontLink2.Release();
            this.mLangFontLink2 = null;
        }
        OS.OleUninitialize();
    }

    SCRIPT_ANALYSIS cloneScriptAnalysis(SCRIPT_ANALYSIS sCRIPT_ANALYSIS) {
        SCRIPT_ANALYSIS sCRIPT_ANALYSIS2 = new SCRIPT_ANALYSIS();
        sCRIPT_ANALYSIS2.eScript = sCRIPT_ANALYSIS.eScript;
        sCRIPT_ANALYSIS2.fRTL = sCRIPT_ANALYSIS.fRTL;
        sCRIPT_ANALYSIS2.fLayoutRTL = sCRIPT_ANALYSIS.fLayoutRTL;
        sCRIPT_ANALYSIS2.fLinkBefore = sCRIPT_ANALYSIS.fLinkBefore;
        sCRIPT_ANALYSIS2.fLinkAfter = sCRIPT_ANALYSIS.fLinkAfter;
        sCRIPT_ANALYSIS2.fLogicalOrder = sCRIPT_ANALYSIS.fLogicalOrder;
        sCRIPT_ANALYSIS2.fNoGlyphIndex = sCRIPT_ANALYSIS.fNoGlyphIndex;
        sCRIPT_ANALYSIS2.s = new SCRIPT_STATE();
        sCRIPT_ANALYSIS2.s.uBidiLevel = sCRIPT_ANALYSIS.s.uBidiLevel;
        sCRIPT_ANALYSIS2.s.fOverrideDirection = sCRIPT_ANALYSIS.s.fOverrideDirection;
        sCRIPT_ANALYSIS2.s.fInhibitSymSwap = sCRIPT_ANALYSIS.s.fInhibitSymSwap;
        sCRIPT_ANALYSIS2.s.fCharShape = sCRIPT_ANALYSIS.s.fCharShape;
        sCRIPT_ANALYSIS2.s.fDigitSubstitute = sCRIPT_ANALYSIS.s.fDigitSubstitute;
        sCRIPT_ANALYSIS2.s.fInhibitLigate = sCRIPT_ANALYSIS.s.fInhibitLigate;
        sCRIPT_ANALYSIS2.s.fDisplayZWG = sCRIPT_ANALYSIS.s.fDisplayZWG;
        sCRIPT_ANALYSIS2.s.fArabicNumContext = sCRIPT_ANALYSIS.s.fArabicNumContext;
        sCRIPT_ANALYSIS2.s.fGcpClusters = sCRIPT_ANALYSIS.s.fGcpClusters;
        sCRIPT_ANALYSIS2.s.fReserved = sCRIPT_ANALYSIS.s.fReserved;
        sCRIPT_ANALYSIS2.s.fEngineReserved = sCRIPT_ANALYSIS.s.fEngineReserved;
        return sCRIPT_ANALYSIS2;
    }

    int[] computePolyline(int n, int n2, int n3, int n4) {
        int n5;
        int n6 = n4 - n2;
        int n7 = 2 * n6;
        int n8 = Compatibility.ceil(n3 - n, n7);
        if (n8 == 0 && n3 - n > 2) {
            n8 = 1;
        }
        if ((n5 = (2 * n8 + 1) * 2) < 0) {
            return new int[0];
        }
        int[] nArray = new int[n5];
        for (int i = 0; i < n8; ++i) {
            int n9 = 4 * i;
            nArray[n9] = n + n7 * i;
            nArray[n9 + 1] = n4;
            nArray[n9 + 2] = nArray[n9] + n7 / 2;
            nArray[n9 + 3] = n2;
        }
        nArray[n5 - 2] = n + n7 * n8;
        nArray[n5 - 1] = n4;
        return nArray;
    }

    long createGdipBrush(int n, int n2) {
        int n3 = (n2 & 0xFF) << 24 | n >> 16 & 0xFF | n & 0xFF00 | (n & 0xFF) << 16;
        return Gdip.SolidBrush_new(n3);
    }

    long createGdipBrush(Color color, int n) {
        return this.createGdipBrush(color.handle, n);
    }

    public void draw(GC gC, int n, int n2) {
        this.checkLayout();
        this.drawInPixels(gC, DPIUtil.autoScaleUp((Drawable)this.getDevice(), n), DPIUtil.autoScaleUp((Drawable)this.getDevice(), n2));
    }

    void drawInPixels(GC gC, int n, int n2) {
        this.drawInPixels(gC, n, n2, -1, -1, null, null);
    }

    public void draw(GC gC, int n, int n2, int n3, int n4, Color color, Color color2) {
        this.checkLayout();
        this.drawInPixels(gC, DPIUtil.autoScaleUp((Drawable)this.getDevice(), n), DPIUtil.autoScaleUp((Drawable)this.getDevice(), n2), n3, n4, color, color2);
    }

    void drawInPixels(GC gC, int n, int n2, int n3, int n4, Color color, Color color2) {
        this.drawInPixels(gC, n, n2, n3, n4, color, color2, 0);
    }

    public void draw(GC gC, int n, int n2, int n3, int n4, Color color, Color color2, int n5) {
        this.checkLayout();
        this.drawInPixels(gC, DPIUtil.autoScaleUp((Drawable)this.getDevice(), n), DPIUtil.autoScaleUp((Drawable)this.getDevice(), n2), n3, n4, color, color2, n5);
    }

    /*
     * WARNING - void declaration
     */
    void drawInPixels(GC gC, int n, int n2, int n3, int n4, Color color, Color color2, int n5) {
        int n6;
        int n7;
        this.computeRuns(gC);
        if (gC == null) {
            SWT.error(4);
        }
        if (gC.isDisposed()) {
            SWT.error(5);
        }
        if (color != null && color.isDisposed()) {
            SWT.error(5);
        }
        if (color2 != null && color2.isDisposed()) {
            SWT.error(5);
        }
        if ((n7 = this.text.length()) == 0 && n5 == 0) {
            return;
        }
        n2 += this.getScaledVerticalIndent();
        long l2 = gC.handle;
        Rectangle rectangle = gC.getClippingInPixels();
        GCData gCData = gC.data;
        long l3 = gCData.gdipGraphics;
        int n8 = gCData.foreground;
        int n9 = OS.GetSysColor(26);
        int n10 = gCData.alpha;
        boolean bl = l3 != 0L;
        long l4 = 0L;
        long l5 = 0L;
        int n11 = 0;
        if (bl) {
            gC.checkGC(1);
            l4 = gC.getFgBrush();
        } else {
            n11 = OS.SaveDC(l2);
            if ((gCData.style & 0x8000000) != 0) {
                OS.SetLayout(l2, OS.GetLayout(l2) | 1);
            }
        }
        boolean bl2 = n3 <= n4 && n3 != -1 && n4 != -1;
        long l6 = 0L;
        long l7 = 0L;
        long l8 = 0L;
        long l9 = 0L;
        long l10 = 0L;
        int n12 = 0;
        if (bl2 || (n5 & 0x100000) != 0 && (n5 & 0x30000) != 0) {
            int n13 = color != null ? color.handle : OS.GetSysColor(14);
            int n14 = n6 = color2 != null ? color2.handle : OS.GetSysColor(13);
            if (bl) {
                l6 = this.createGdipBrush(n6, n10);
                l7 = this.createGdipBrush(n13, n10);
            } else {
                l10 = OS.CreateSolidBrush(n6);
                n12 = n13;
            }
            if (bl2) {
                n3 = this.translateOffset(Math.min(Math.max(0, n3), n7 - 1));
                n4 = this.translateOffset(Math.min(Math.max(0, n4), n7 - 1));
            }
        }
        RECT rECT = new RECT();
        OS.SetBkMode(l2, 1);
        block0: for (n6 = 0; n6 < this.runs.length; ++n6) {
            void var46_42;
            int n15;
            StyleItem[] styleItemArray2;
            int n16;
            int n17 = n + this.getLineIndent(n6);
            int n18 = n2 + DPIUtil.autoScaleUp((Drawable)this.getDevice(), this.lineY[n6]);
            StyleItem[] styleItemArray = this.runs[n6];
            int n19 = DPIUtil.autoScaleUp((Drawable)this.getDevice(), this.lineY[n6 + 1] - this.lineY[n6] - this.lineSpacingInPoints);
            if ((n5 & 0x30000) != 0 && (bl2 || (n5 & 0x100000) != 0)) {
                n16 = 0;
                if (n6 == this.runs.length - 1 && (n5 & 0x100000) != 0) {
                    n16 = 1;
                } else {
                    styleItemArray2 = styleItemArray[styleItemArray.length - 1];
                    if (styleItemArray2.lineBreak && !styleItemArray2.softBreak) {
                        if (n3 <= styleItemArray2.start && styleItemArray2.start <= n4) {
                            n16 = 1;
                        }
                    } else {
                        n15 = styleItemArray2.start + styleItemArray2.length - 1;
                        if (n3 <= n15 && n15 < n4 && (n5 & 0x10000) != 0) {
                            n16 = 1;
                        }
                    }
                }
                if (n16 != 0) {
                    int n20 = (n5 & 0x10000) != 0 ? 0x6FFFFFF : n19 / 3;
                    if (bl) {
                        Gdip.Graphics_FillRectangle(l3, l6, n17 + this.lineWidth[n6], n18, n20, n19);
                    } else {
                        OS.SelectObject(l2, l10);
                        OS.PatBlt(l2, n17 + this.lineWidth[n6], n18, n20, n19, 15728673);
                    }
                }
            }
            if (n17 > rectangle.x + rectangle.width || n17 + this.lineWidth[n6] < rectangle.x) continue;
            n16 = n17;
            styleItemArray2 = styleItemArray;
            n15 = styleItemArray2.length;
            boolean bl3 = false;
            while (var46_42 < n15) {
                StyleItem styleItem = styleItemArray2[var46_42];
                if (styleItem.length != 0) {
                    if (n17 > rectangle.x + rectangle.width) break;
                    if (n17 + styleItem.width >= rectangle.x && (!styleItem.lineBreak || styleItem.softBreak)) {
                        OS.SetRect(rECT, n17, n18, n17 + styleItem.width, n18 + n19);
                        if (bl) {
                            this.drawRunBackgroundGDIP(styleItem, l3, rECT, n3, n4, n10, l6, bl2);
                        } else {
                            this.drawRunBackground(styleItem, l2, rECT, n3, n4, l10, bl2);
                        }
                    }
                    n17 += styleItem.width;
                }
                ++var46_42;
            }
            int n21 = Math.max(0, this.ascentInPixels);
            n15 = 0;
            for (StyleItem styleItem : styleItemArray) {
                n21 = Math.max(n21, DPIUtil.autoScaleUp((Drawable)this.getDevice(), styleItem.ascentInPoints));
                n15 = Math.min(n15, styleItem.underlinePos);
            }
            Object var46_44 = null;
            Object var47_51 = null;
            RECT rECT2 = null;
            Object var49_59 = null;
            n17 = n16;
            for (int i = 0; i < styleItemArray.length; ++i) {
                boolean bl4;
                StyleItem styleItem = styleItemArray[i];
                TextStyle textStyle = styleItem.style;
                boolean bl5 = bl4 = textStyle != null && (textStyle.underline || textStyle.strikeout || textStyle.borderStyle != 0);
                if (styleItem.length == 0) continue;
                if (n17 > rectangle.x + rectangle.width) continue block0;
                if (n17 + styleItem.width >= rectangle.x) {
                    boolean bl6;
                    boolean bl7 = bl6 = styleItem.tab && !bl4;
                    if (!(bl6 || styleItem.lineBreak && !styleItem.softBreak || textStyle != null && textStyle.metrics != null)) {
                        void object4;
                        void var47_47;
                        OS.SetRect(rECT, n17, n18, n17 + styleItem.width, n18 + n19);
                        if (bl) {
                            void var49_62;
                            long l11;
                            long l12 = this.getItemFont(styleItem);
                            if (l12 != l9) {
                                l9 = l12;
                                if (l8 != 0L) {
                                    Gdip.Font_delete(l8);
                                }
                                l11 = OS.SelectObject(l2, l12);
                                l8 = Gdip.Font_new(l2, l12);
                                OS.SelectObject(l2, l11);
                                if (l8 == 0L) {
                                    SWT.error(2);
                                }
                                if (!Gdip.Font_IsAvailable(l8)) {
                                    Gdip.Font_delete(l8);
                                    l8 = 0L;
                                }
                            }
                            l11 = l4;
                            if (textStyle != null && textStyle.underline && textStyle.underlineStyle == 4) {
                                if (l5 == 0L) {
                                    l5 = this.createGdipBrush(n9, n10);
                                }
                                l11 = l5;
                            }
                            if (l8 != 0L && !styleItem.analysis.fNoGlyphIndex) {
                                RECT rECT3 = this.drawRunTextGDIP(l3, styleItem, rECT, l8, n21, l11, l7, n3, n4, n10);
                            } else {
                                int n20 = textStyle != null && textStyle.underline && textStyle.underlineStyle == 4 ? n9 : n8;
                                RECT rECT4 = this.drawRunTextGDIPRaster(l3, styleItem, rECT, n21, n20, n12, n3, n4);
                            }
                            RECT rECT5 = this.drawUnderlineGDIP(l3, n, n18 + n21, n15, n18 + n19, styleItemArray, i, l11, l7, (RECT)var47_47, (RECT)var49_62, n3, n4, n10, rectangle);
                            rECT2 = this.drawStrikeoutGDIP(l3, n, n18 + n21, styleItemArray, i, l11, l7, rECT2, (RECT)var49_62, n3, n4, n10, rectangle);
                            RECT rECT6 = this.drawBorderGDIP(l3, n, n18, n19, styleItemArray, i, l11, l7, (RECT)object4, (RECT)var49_62, n3, n4, n10, rectangle);
                        } else {
                            int n22 = textStyle != null && textStyle.underline && textStyle.underlineStyle == 4 ? n9 : n8;
                            RECT rECT7 = this.drawRunText(l2, styleItem, rECT, n21, n22, n12, n3, n4);
                            RECT rECT8 = this.drawUnderline(l2, n, n18 + n21, n15, n18 + n19, styleItemArray, i, n22, n12, (RECT)var47_47, rECT7, n3, n4, rectangle);
                            rECT2 = this.drawStrikeout(l2, n, n18 + n21, styleItemArray, i, n22, n12, rECT2, rECT7, n3, n4, rectangle);
                            RECT rECT9 = this.drawBorder(l2, n, n18, n19, styleItemArray, i, n22, n12, (RECT)object4, rECT7, n3, n4, rectangle);
                        }
                    }
                }
                n17 += styleItem.width;
            }
        }
        if (l6 != 0L) {
            Gdip.SolidBrush_delete(l6);
        }
        if (l7 != 0L) {
            Gdip.SolidBrush_delete(l7);
        }
        if (l5 != 0L) {
            Gdip.SolidBrush_delete(l5);
        }
        if (l8 != 0L) {
            Gdip.Font_delete(l8);
        }
        if (n11 != 0) {
            OS.RestoreDC(l2, n11);
        }
        if (l10 != 0L) {
            OS.DeleteObject(l10);
        }
    }

    RECT drawBorder(long l2, int n, int n2, int n3, StyleItem[] styleItemArray, int n4, int n5, int n6, RECT rECT, RECT rECT2, int n7, int n8, Rectangle rectangle) {
        boolean bl;
        StyleItem styleItem = styleItemArray[n4];
        TextStyle textStyle = styleItem.style;
        if (textStyle == null) {
            return null;
        }
        if (textStyle.borderStyle == 0) {
            return null;
        }
        rECT = this.addClipRect(styleItem, rECT, rECT2, n7, n8);
        boolean bl2 = bl = rectangle != null && n + styleItem.x + styleItem.width > rectangle.x + rectangle.width;
        if (n4 + 1 >= styleItemArray.length || bl || styleItemArray[n4 + 1].lineBreak || !textStyle.isAdherentBorder(styleItemArray[n4 + 1].style)) {
            int n9;
            boolean bl3;
            int n10;
            int n11 = styleItem.x;
            int n12 = styleItem.start;
            int n13 = styleItem.start + styleItem.length - 1;
            for (n10 = n4; n10 > 0 && textStyle.isAdherentBorder(styleItemArray[n10 - 1].style); --n10) {
                n11 = styleItemArray[n10 - 1].x;
                n12 = Math.min(n12, styleItemArray[n10 - 1].start);
                n13 = Math.max(n13, styleItemArray[n10 - 1].start + styleItemArray[n10 - 1].length - 1);
            }
            n10 = n7 <= n8 && n7 != -1 && n8 != -1 ? 1 : 0;
            boolean bl4 = bl3 = n10 != 0 && n7 <= n12 && n13 <= n8;
            if (textStyle.borderColor != null) {
                n5 = textStyle.borderColor.handle;
                rECT = null;
            } else if (bl3) {
                n5 = n6;
                rECT = null;
            } else if (textStyle.foreground != null) {
                n5 = textStyle.foreground.handle;
            }
            boolean bl5 = true;
            int n14 = 1;
            int n15 = 0;
            switch (textStyle.borderStyle) {
                case 2: {
                    n15 = 1;
                    n14 = 4;
                    break;
                }
                case 4: {
                    n15 = 2;
                    n14 = 2;
                }
            }
            long l3 = OS.SelectObject(l2, OS.GetStockObject(5));
            LOGBRUSH lOGBRUSH = new LOGBRUSH();
            lOGBRUSH.lbStyle = 0;
            lOGBRUSH.lbColor = n5;
            long l4 = OS.ExtCreatePen(n15 | 0x10000, 1, lOGBRUSH, 0, null);
            long l5 = OS.SelectObject(l2, l4);
            RECT rECT3 = new RECT();
            OS.SetRect(rECT3, n + n11, n2, n + styleItem.x + styleItem.width, n2 + n3);
            if (rectangle != null) {
                if (rECT3.left < rectangle.x) {
                    n9 = rECT3.left % n14;
                    rECT3.left = rectangle.x / n14 * n14 + n9 - n14;
                }
                if (rECT3.right > rectangle.x + rectangle.width) {
                    n9 = rECT3.right % n14;
                    rECT3.right = (rectangle.x + rectangle.width) / n14 * n14 + n9 + n14;
                }
            }
            OS.Rectangle(l2, rECT3.left, rECT3.top, rECT3.right, rECT3.bottom);
            OS.SelectObject(l2, l5);
            OS.DeleteObject(l4);
            if (rECT != null) {
                n9 = OS.SaveDC(l2);
                if (rECT.left == -1) {
                    rECT.left = 0;
                }
                if (rECT.right == -1) {
                    rECT.right = 524287;
                }
                OS.IntersectClipRect(l2, rECT.left, rECT.top, rECT.right, rECT.bottom);
                lOGBRUSH.lbColor = n6;
                long l6 = OS.ExtCreatePen(n15 | 0x10000, 1, lOGBRUSH, 0, null);
                l5 = OS.SelectObject(l2, l6);
                OS.Rectangle(l2, rECT3.left, rECT3.top, rECT3.right, rECT3.bottom);
                OS.RestoreDC(l2, n9);
                OS.SelectObject(l2, l5);
                OS.DeleteObject(l6);
            }
            OS.SelectObject(l2, l3);
            return null;
        }
        return rECT;
    }

    RECT drawBorderGDIP(long l2, int n, int n2, int n3, StyleItem[] styleItemArray, int n4, long l3, long l4, RECT rECT, RECT rECT2, int n5, int n6, int n7, Rectangle rectangle) {
        boolean bl;
        StyleItem styleItem = styleItemArray[n4];
        TextStyle textStyle = styleItem.style;
        if (textStyle == null) {
            return null;
        }
        if (textStyle.borderStyle == 0) {
            return null;
        }
        rECT = this.addClipRect(styleItem, rECT, rECT2, n5, n6);
        boolean bl2 = bl = rectangle != null && n + styleItem.x + styleItem.width > rectangle.x + rectangle.width;
        if (n4 + 1 >= styleItemArray.length || bl || styleItemArray[n4 + 1].lineBreak || !textStyle.isAdherentBorder(styleItemArray[n4 + 1].style)) {
            int n8;
            int n9 = styleItem.x;
            int n10 = styleItem.start;
            int n11 = styleItem.start + styleItem.length - 1;
            for (n8 = n4; n8 > 0 && textStyle.isAdherentBorder(styleItemArray[n8 - 1].style); --n8) {
                n9 = styleItemArray[n8 - 1].x;
                n10 = Math.min(n10, styleItemArray[n8 - 1].start);
                n11 = Math.max(n11, styleItemArray[n8 - 1].start + styleItemArray[n8 - 1].length - 1);
            }
            n8 = n5 <= n6 && n5 != -1 && n6 != -1 ? 1 : 0;
            boolean bl3 = n8 != 0 && n5 <= n10 && n11 <= n6;
            long l5 = l3;
            if (textStyle.borderColor != null) {
                l5 = this.createGdipBrush(textStyle.borderColor, n7);
                rECT = null;
            } else if (bl3) {
                l5 = l4;
                rECT = null;
            } else if (textStyle.foreground != null) {
                l5 = this.createGdipBrush(textStyle.foreground, n7);
            }
            boolean bl4 = true;
            int n12 = 0;
            switch (textStyle.borderStyle) {
                case 2: {
                    n12 = 1;
                    break;
                }
                case 4: {
                    n12 = 2;
                }
            }
            long l6 = Gdip.Pen_new(l5, 1.0f);
            Gdip.Pen_SetDashStyle(l6, n12);
            Gdip.Graphics_SetPixelOffsetMode(l2, 3);
            int n13 = Gdip.Graphics_GetSmoothingMode(l2);
            Gdip.Graphics_SetSmoothingMode(l2, 3);
            if (rECT != null) {
                int n14 = Gdip.Graphics_Save(l2);
                if (rECT.left == -1) {
                    rECT.left = 0;
                }
                if (rECT.right == -1) {
                    rECT.right = 524287;
                }
                Rect rect = new Rect();
                rect.X = rECT.left;
                rect.Y = rECT.top;
                rect.Width = rECT.right - rECT.left;
                rect.Height = rECT.bottom - rECT.top;
                Gdip.Graphics_SetClip(l2, rect, 4);
                Gdip.Graphics_DrawRectangle(l2, l6, n + n9, n2, styleItem.x + styleItem.width - n9 - 1, n3 - 1);
                Gdip.Graphics_Restore(l2, n14);
                n14 = Gdip.Graphics_Save(l2);
                Gdip.Graphics_SetClip(l2, rect, 1);
                long l7 = Gdip.Pen_new(l4, 1.0f);
                Gdip.Pen_SetDashStyle(l7, n12);
                Gdip.Graphics_DrawRectangle(l2, l7, n + n9, n2, styleItem.x + styleItem.width - n9 - 1, n3 - 1);
                Gdip.Pen_delete(l7);
                Gdip.Graphics_Restore(l2, n14);
            } else {
                Gdip.Graphics_DrawRectangle(l2, l6, n + n9, n2, styleItem.x + styleItem.width - n9 - 1, n3 - 1);
            }
            Gdip.Graphics_SetPixelOffsetMode(l2, 4);
            Gdip.Graphics_SetSmoothingMode(l2, n13);
            Gdip.Pen_delete(l6);
            if (l5 != l4 && l5 != l3) {
                Gdip.SolidBrush_delete(l5);
            }
            return null;
        }
        return rECT;
    }

    void drawRunBackground(StyleItem styleItem, long l2, RECT rECT, int n, int n2, long l3, boolean bl) {
        boolean bl2;
        int n3 = styleItem.start + styleItem.length - 1;
        boolean bl3 = bl2 = bl && n <= styleItem.start && n2 >= n3;
        if (bl2) {
            OS.SelectObject(l2, l3);
            OS.PatBlt(l2, rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top, 15728673);
        } else {
            int n4;
            if (styleItem.style != null && styleItem.style.background != null) {
                n4 = styleItem.style.background.handle;
                long l4 = OS.CreateSolidBrush(n4);
                long l5 = OS.SelectObject(l2, l4);
                OS.PatBlt(l2, rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top, 15728673);
                OS.SelectObject(l2, l5);
                OS.DeleteObject(l4);
            }
            int n5 = n4 = bl && n <= n3 && styleItem.start <= n2 ? 1 : 0;
            if (n4 != 0) {
                this.getPartialSelection(styleItem, n, n2, rECT);
                OS.SelectObject(l2, l3);
                OS.PatBlt(l2, rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top, 15728673);
            }
        }
    }

    void drawRunBackgroundGDIP(StyleItem styleItem, long l2, RECT rECT, int n, int n2, int n3, long l3, boolean bl) {
        boolean bl2;
        int n4 = styleItem.start + styleItem.length - 1;
        boolean bl3 = bl2 = bl && n <= styleItem.start && n2 >= n4;
        if (bl2) {
            Gdip.Graphics_FillRectangle(l2, l3, rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top);
        } else {
            boolean bl4;
            if (styleItem.style != null && styleItem.style.background != null) {
                long l4 = this.createGdipBrush(styleItem.style.background, n3);
                Gdip.Graphics_FillRectangle(l2, l4, rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top);
                Gdip.SolidBrush_delete(l4);
            }
            boolean bl5 = bl4 = bl && n <= n4 && styleItem.start <= n2;
            if (bl4) {
                this.getPartialSelection(styleItem, n, n2, rECT);
                if (rECT.left > rECT.right) {
                    int n5 = rECT.left;
                    rECT.left = rECT.right;
                    rECT.right = n5;
                }
                Gdip.Graphics_FillRectangle(l2, l3, rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top);
            }
        }
    }

    RECT drawRunText(long l2, StyleItem styleItem, RECT rECT, int n, int n2, int n3, int n4, int n5) {
        int n6 = styleItem.start + styleItem.length - 1;
        boolean bl = n4 <= n5 && n4 != -1 && n5 != -1;
        boolean bl2 = bl && n4 <= styleItem.start && n5 >= n6;
        boolean bl3 = bl && !bl2 && n4 <= n6 && styleItem.start <= n5;
        int n7 = (this.orientation & 0x4000000) != 0 ? -1 : 0;
        int n8 = rECT.left + n7;
        int n9 = rECT.top + (n - DPIUtil.autoScaleUp((Drawable)this.getDevice(), styleItem.ascentInPoints));
        long l3 = this.getItemFont(styleItem);
        OS.SelectObject(l2, l3);
        if (bl2) {
            n2 = n3;
        } else if (styleItem.style != null && styleItem.style.foreground != null) {
            n2 = styleItem.style.foreground.handle;
        }
        OS.SetTextColor(l2, n2);
        OS.ScriptTextOut(l2, styleItem.psc, n8, n9, 0, null, styleItem.analysis, 0L, 0, styleItem.glyphs, styleItem.glyphCount, styleItem.advances, styleItem.justify, styleItem.goffsets);
        if (bl3) {
            this.getPartialSelection(styleItem, n4, n5, rECT);
            OS.SetTextColor(l2, n3);
            OS.ScriptTextOut(l2, styleItem.psc, n8, n9, 4, rECT, styleItem.analysis, 0L, 0, styleItem.glyphs, styleItem.glyphCount, styleItem.advances, styleItem.justify, styleItem.goffsets);
        }
        return bl2 || bl3 ? rECT : null;
    }

    RECT drawRunTextGDIP(long l2, StyleItem styleItem, RECT rECT, long l3, int n, long l4, long l5, int n2, int n3, int n4) {
        boolean bl;
        int n5 = styleItem.start + styleItem.length - 1;
        boolean bl2 = n2 <= n3 && n2 != -1 && n3 != -1;
        boolean bl3 = bl2 && n2 <= styleItem.start && n3 >= n5;
        boolean bl4 = bl2 && !bl3 && n2 <= n5 && styleItem.start <= n3;
        int n6 = rECT.top + n;
        if (styleItem.style != null && styleItem.style.rise != 0) {
            n6 -= DPIUtil.autoScaleUp((Drawable)this.getDevice(), styleItem.style.rise);
        }
        int n7 = rECT.left;
        long l6 = l4;
        if (bl3) {
            l6 = l5;
        } else if (styleItem.style != null && styleItem.style.foreground != null) {
            l6 = this.createGdipBrush(styleItem.style.foreground, n4);
        }
        int n8 = 0;
        Rect rect = null;
        if (bl4) {
            rect = new Rect();
            this.getPartialSelection(styleItem, n2, n3, rECT);
            rect.X = rECT.left;
            rect.Y = rECT.top;
            rect.Width = rECT.right - rECT.left;
            rect.Height = rECT.bottom - rECT.top;
            n8 = Gdip.Graphics_Save(l2);
            Gdip.Graphics_SetClip(l2, rect, 4);
        }
        int n9 = 0;
        boolean bl5 = bl = (this.orientation & 0x4000000) != 0;
        if (bl) {
            switch (Gdip.Brush_GetType(l6)) {
                case 4: {
                    Gdip.LinearGradientBrush_ScaleTransform(l6, -1.0f, 1.0f, 0);
                    Gdip.LinearGradientBrush_TranslateTransform(l6, -2 * n7 - styleItem.width, 0.0f, 0);
                    break;
                }
                case 2: {
                    Gdip.TextureBrush_ScaleTransform(l6, -1.0f, 1.0f, 0);
                    Gdip.TextureBrush_TranslateTransform(l6, -2 * n7 - styleItem.width, 0.0f, 0);
                }
            }
            n9 = Gdip.Graphics_Save(l2);
            Gdip.Graphics_ScaleTransform(l2, -1.0f, 1.0f, 0);
            Gdip.Graphics_TranslateTransform(l2, -2 * n7 - styleItem.width, 0.0f, 0);
        }
        int[] nArray = new int[styleItem.glyphCount];
        float[] fArray = new float[styleItem.glyphCount * 2];
        C.memmove(nArray, styleItem.justify != 0L ? styleItem.justify : styleItem.advances, (long)(styleItem.glyphCount * 4));
        int n10 = n7;
        int n11 = 0;
        for (int i = 0; i < nArray.length; ++i) {
            fArray[n11++] = n10;
            fArray[n11++] = n6;
            n10 += nArray[i];
        }
        Gdip.Graphics_DrawDriverString(l2, styleItem.glyphs, styleItem.glyphCount, l3, l6, fArray, 0, 0L);
        if (bl4) {
            if (bl) {
                Gdip.Graphics_Restore(l2, n9);
            }
            Gdip.Graphics_Restore(l2, n8);
            n8 = Gdip.Graphics_Save(l2);
            Gdip.Graphics_SetClip(l2, rect, 1);
            if (bl) {
                n9 = Gdip.Graphics_Save(l2);
                Gdip.Graphics_ScaleTransform(l2, -1.0f, 1.0f, 0);
                Gdip.Graphics_TranslateTransform(l2, -2 * n7 - styleItem.width, 0.0f, 0);
            }
            Gdip.Graphics_DrawDriverString(l2, styleItem.glyphs, styleItem.glyphCount, l3, l5, fArray, 0, 0L);
            Gdip.Graphics_Restore(l2, n8);
        }
        if (bl) {
            switch (Gdip.Brush_GetType(l6)) {
                case 4: {
                    Gdip.LinearGradientBrush_ResetTransform(l6);
                    break;
                }
                case 2: {
                    Gdip.TextureBrush_ResetTransform(l6);
                }
            }
            Gdip.Graphics_Restore(l2, n9);
        }
        if (l6 != l5 && l6 != l4) {
            Gdip.SolidBrush_delete(l6);
        }
        return bl3 || bl4 ? rECT : null;
    }

    RECT drawRunTextGDIPRaster(long l2, StyleItem styleItem, RECT rECT, int n, int n2, int n3, int n4, int n5) {
        long l3 = 0L;
        Gdip.Graphics_SetPixelOffsetMode(l2, 3);
        long l4 = Gdip.Region_new();
        if (l4 == 0L) {
            SWT.error(2);
        }
        Gdip.Graphics_GetClip(l2, l4);
        if (!Gdip.Region_IsInfinite(l4, l2)) {
            l3 = Gdip.Region_GetHRGN(l4, l2);
        }
        Gdip.Region_delete(l4);
        Gdip.Graphics_SetPixelOffsetMode(l2, 4);
        float[] fArray = null;
        long l5 = Gdip.Matrix_new(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
        if (l5 == 0L) {
            SWT.error(2);
        }
        Gdip.Graphics_GetTransform(l2, l5);
        if (!Gdip.Matrix_IsIdentity(l5)) {
            fArray = new float[6];
            Gdip.Matrix_GetElements(l5, fArray);
        }
        Gdip.Matrix_delete(l5);
        long l6 = Gdip.Graphics_GetHDC(l2);
        int n6 = OS.SaveDC(l6);
        if (fArray != null) {
            OS.SetGraphicsMode(l6, 2);
            OS.SetWorldTransform(l6, fArray);
        }
        if (l3 != 0L) {
            OS.SelectClipRgn(l6, l3);
            OS.DeleteObject(l3);
        }
        if ((this.orientation & 0x4000000) != 0) {
            OS.SetLayout(l6, OS.GetLayout(l6) | 1);
        }
        OS.SetBkMode(l6, 1);
        RECT rECT2 = this.drawRunText(l6, styleItem, rECT, n, n2, n3, n4, n5);
        OS.RestoreDC(l6, n6);
        Gdip.Graphics_ReleaseHDC(l2, l6);
        return rECT2;
    }

    RECT drawStrikeout(long l2, int n, int n2, StyleItem[] styleItemArray, int n3, int n4, int n5, RECT rECT, RECT rECT2, int n6, int n7, Rectangle rectangle) {
        boolean bl;
        StyleItem styleItem = styleItemArray[n3];
        TextStyle textStyle = styleItem.style;
        if (textStyle == null) {
            return null;
        }
        if (!textStyle.strikeout) {
            return null;
        }
        rECT = this.addClipRect(styleItem, rECT, rECT2, n6, n7);
        boolean bl2 = bl = rectangle != null && n + styleItem.x + styleItem.width > rectangle.x + rectangle.width;
        if (n3 + 1 >= styleItemArray.length || bl || styleItemArray[n3 + 1].lineBreak || !textStyle.isAdherentStrikeout(styleItemArray[n3 + 1].style)) {
            boolean bl3;
            int n8;
            int n9 = styleItem.x;
            int n10 = styleItem.start;
            int n11 = styleItem.start + styleItem.length - 1;
            for (n8 = n3; n8 > 0 && textStyle.isAdherentStrikeout(styleItemArray[n8 - 1].style); --n8) {
                n9 = styleItemArray[n8 - 1].x;
                n10 = Math.min(n10, styleItemArray[n8 - 1].start);
                n11 = Math.max(n11, styleItemArray[n8 - 1].start + styleItemArray[n8 - 1].length - 1);
            }
            n8 = n6 <= n7 && n6 != -1 && n7 != -1 ? 1 : 0;
            boolean bl4 = bl3 = n8 != 0 && n6 <= n10 && n11 <= n7;
            if (textStyle.strikeoutColor != null) {
                n4 = textStyle.strikeoutColor.handle;
                rECT = null;
            } else if (bl3) {
                n4 = n5;
                rECT = null;
            } else if (textStyle.foreground != null) {
                n4 = textStyle.foreground.handle;
            }
            RECT rECT3 = new RECT();
            int n12 = DPIUtil.autoScaleUp((Drawable)this.getDevice(), textStyle.rise);
            OS.SetRect(rECT3, n + n9, n2 - styleItem.strikeoutPos - n12, n + styleItem.x + styleItem.width, n2 - styleItem.strikeoutPos + styleItem.strikeoutThickness - n12);
            long l3 = OS.CreateSolidBrush(n4);
            OS.FillRect(l2, rECT3, l3);
            OS.DeleteObject(l3);
            if (rECT != null) {
                long l4 = OS.CreateSolidBrush(n5);
                if (rECT.left == -1) {
                    rECT.left = 0;
                }
                if (rECT.right == -1) {
                    rECT.right = 524287;
                }
                OS.SetRect(rECT, Math.max(rECT3.left, rECT.left), rECT3.top, Math.min(rECT3.right, rECT.right), rECT3.bottom);
                OS.FillRect(l2, rECT, l4);
                OS.DeleteObject(l4);
            }
            return null;
        }
        return rECT;
    }

    RECT drawStrikeoutGDIP(long l2, int n, int n2, StyleItem[] styleItemArray, int n3, long l3, long l4, RECT rECT, RECT rECT2, int n4, int n5, int n6, Rectangle rectangle) {
        boolean bl;
        StyleItem styleItem = styleItemArray[n3];
        TextStyle textStyle = styleItem.style;
        if (textStyle == null) {
            return null;
        }
        if (!textStyle.strikeout) {
            return null;
        }
        rECT = this.addClipRect(styleItem, rECT, rECT2, n4, n5);
        boolean bl2 = bl = rectangle != null && n + styleItem.x + styleItem.width > rectangle.x + rectangle.width;
        if (n3 + 1 >= styleItemArray.length || bl || styleItemArray[n3 + 1].lineBreak || !textStyle.isAdherentStrikeout(styleItemArray[n3 + 1].style)) {
            int n7;
            int n8 = styleItem.x;
            int n9 = styleItem.start;
            int n10 = styleItem.start + styleItem.length - 1;
            for (n7 = n3; n7 > 0 && textStyle.isAdherentStrikeout(styleItemArray[n7 - 1].style); --n7) {
                n8 = styleItemArray[n7 - 1].x;
                n9 = Math.min(n9, styleItemArray[n7 - 1].start);
                n10 = Math.max(n10, styleItemArray[n7 - 1].start + styleItemArray[n7 - 1].length - 1);
            }
            n7 = n4 <= n5 && n4 != -1 && n5 != -1 ? 1 : 0;
            boolean bl3 = n7 != 0 && n4 <= n9 && n10 <= n5;
            long l5 = l3;
            if (textStyle.strikeoutColor != null) {
                l5 = this.createGdipBrush(textStyle.strikeoutColor, n6);
                rECT = null;
            } else if (bl3) {
                l5 = l4;
                rECT = null;
            } else if (textStyle.foreground != null) {
                l5 = this.createGdipBrush(textStyle.foreground, n6);
            }
            int n11 = DPIUtil.autoScaleUp((Drawable)this.getDevice(), textStyle.rise);
            if (rECT != null) {
                int n12 = Gdip.Graphics_Save(l2);
                if (rECT.left == -1) {
                    rECT.left = 0;
                }
                if (rECT.right == -1) {
                    rECT.right = 524287;
                }
                Rect rect = new Rect();
                rect.X = rECT.left;
                rect.Y = rECT.top;
                rect.Width = rECT.right - rECT.left;
                rect.Height = rECT.bottom - rECT.top;
                Gdip.Graphics_SetClip(l2, rect, 4);
                Gdip.Graphics_FillRectangle(l2, l5, n + n8, n2 - styleItem.strikeoutPos - n11, styleItem.x + styleItem.width - n8, styleItem.strikeoutThickness);
                Gdip.Graphics_Restore(l2, n12);
                n12 = Gdip.Graphics_Save(l2);
                Gdip.Graphics_SetClip(l2, rect, 1);
                Gdip.Graphics_FillRectangle(l2, l4, n + n8, n2 - styleItem.strikeoutPos - n11, styleItem.x + styleItem.width - n8, styleItem.strikeoutThickness);
                Gdip.Graphics_Restore(l2, n12);
            } else {
                Gdip.Graphics_FillRectangle(l2, l5, n + n8, n2 - styleItem.strikeoutPos - n11, styleItem.x + styleItem.width - n8, styleItem.strikeoutThickness);
            }
            if (l5 != l4 && l5 != l3) {
                Gdip.SolidBrush_delete(l5);
            }
            return null;
        }
        return rECT;
    }

    RECT drawUnderline(long l2, int n, int n2, int n3, int n4, StyleItem[] styleItemArray, int n5, int n6, int n7, RECT rECT, RECT rECT2, int n8, int n9, Rectangle rectangle) {
        boolean bl;
        StyleItem styleItem = styleItemArray[n5];
        TextStyle textStyle = styleItem.style;
        if (textStyle == null) {
            return null;
        }
        if (!textStyle.underline) {
            return null;
        }
        rECT = this.addClipRect(styleItem, rECT, rECT2, n8, n9);
        boolean bl2 = bl = rectangle != null && n + styleItem.x + styleItem.width > rectangle.x + rectangle.width;
        if (n5 + 1 >= styleItemArray.length || bl || styleItemArray[n5 + 1].lineBreak || !textStyle.isAdherentUnderline(styleItemArray[n5 + 1].style)) {
            boolean bl3;
            int n10;
            int n11 = styleItem.x;
            int n12 = styleItem.start;
            int n13 = styleItem.start + styleItem.length - 1;
            for (n10 = n5; n10 > 0 && textStyle.isAdherentUnderline(styleItemArray[n10 - 1].style); --n10) {
                n11 = styleItemArray[n10 - 1].x;
                n12 = Math.min(n12, styleItemArray[n10 - 1].start);
                n13 = Math.max(n13, styleItemArray[n10 - 1].start + styleItemArray[n10 - 1].length - 1);
            }
            n10 = n8 <= n9 && n8 != -1 && n9 != -1 ? 1 : 0;
            boolean bl4 = bl3 = n10 != 0 && n8 <= n12 && n13 <= n9;
            if (textStyle.underlineColor != null) {
                n6 = textStyle.underlineColor.handle;
                rECT = null;
            } else if (bl3) {
                n6 = n7;
                rECT = null;
            } else if (textStyle.foreground != null) {
                n6 = textStyle.foreground.handle;
            }
            RECT rECT3 = new RECT();
            int n14 = DPIUtil.autoScaleUp((Drawable)this.getDevice(), textStyle.rise);
            OS.SetRect(rECT3, n + n11, n2 - n3 - n14, n + styleItem.x + styleItem.width, n2 - n3 + styleItem.underlineThickness - n14);
            if (rECT != null) {
                if (rECT.left == -1) {
                    rECT.left = 0;
                }
                if (rECT.right == -1) {
                    rECT.right = 524287;
                }
                OS.SetRect(rECT, Math.max(rECT3.left, rECT.left), rECT3.top, Math.min(rECT3.right, rECT.right), rECT3.bottom);
            }
            switch (textStyle.underlineStyle) {
                case 2: 
                case 3: {
                    boolean bl5 = true;
                    int n15 = 2;
                    int n16 = Math.min(rECT3.top - 1, n4 - 2 - 1);
                    int[] nArray = this.computePolyline(rECT3.left, n16, rECT3.right, n16 + 2);
                    long l3 = OS.CreatePen(0, 1, n6);
                    long l4 = OS.SelectObject(l2, l3);
                    int n17 = OS.SaveDC(l2);
                    OS.IntersectClipRect(l2, rECT3.left, n16, rECT3.right + 1, n16 + 2 + 1);
                    OS.Polyline(l2, nArray, nArray.length / 2);
                    int n18 = nArray.length;
                    if (n18 >= 2) {
                        OS.SetPixel(l2, nArray[n18 - 2], nArray[n18 - 1], n6);
                    }
                    OS.SelectObject(l2, l4);
                    OS.DeleteObject(l3);
                    OS.RestoreDC(l2, n17);
                    if (rECT == null) break;
                    l3 = OS.CreatePen(0, 1, n7);
                    l4 = OS.SelectObject(l2, l3);
                    n17 = OS.SaveDC(l2);
                    OS.IntersectClipRect(l2, rECT.left, n16, rECT.right + 1, n16 + 2 + 1);
                    OS.Polyline(l2, nArray, nArray.length / 2);
                    if (n18 >= 2) {
                        OS.SetPixel(l2, nArray[n18 - 2], nArray[n18 - 1], n7);
                    }
                    OS.SelectObject(l2, l4);
                    OS.DeleteObject(l3);
                    OS.RestoreDC(l2, n17);
                    break;
                }
                case 0: 
                case 1: 
                case 4: 
                case 196608: {
                    int n19;
                    if (textStyle.underlineStyle == 196608) {
                        RECT rECT4 = rECT3;
                        rECT4.top -= styleItem.underlineThickness;
                        if (rECT != null) {
                            RECT rECT5 = rECT;
                            rECT5.top -= styleItem.underlineThickness;
                        }
                    }
                    int n20 = n19 = textStyle.underlineStyle == 1 ? rECT3.bottom + styleItem.underlineThickness * 2 : rECT3.bottom;
                    if (n19 > n4) {
                        OS.OffsetRect(rECT3, 0, n4 - n19);
                        if (rECT != null) {
                            OS.OffsetRect(rECT, 0, n4 - n19);
                        }
                    }
                    long l5 = OS.CreateSolidBrush(n6);
                    OS.FillRect(l2, rECT3, l5);
                    if (textStyle.underlineStyle == 1) {
                        OS.SetRect(rECT3, rECT3.left, rECT3.top + styleItem.underlineThickness * 2, rECT3.right, rECT3.bottom + styleItem.underlineThickness * 2);
                        OS.FillRect(l2, rECT3, l5);
                    }
                    OS.DeleteObject(l5);
                    if (rECT == null) break;
                    long l6 = OS.CreateSolidBrush(n7);
                    OS.FillRect(l2, rECT, l6);
                    if (textStyle.underlineStyle == 1) {
                        OS.SetRect(rECT, rECT.left, rECT3.top, rECT.right, rECT3.bottom);
                        OS.FillRect(l2, rECT, l6);
                    }
                    OS.DeleteObject(l6);
                    break;
                }
                case 65536: 
                case 131072: {
                    int n21 = textStyle.underlineStyle == 131072 ? 1 : 2;
                    long l7 = OS.CreatePen(n21, 1, n6);
                    long l8 = OS.SelectObject(l2, l7);
                    int n22 = DPIUtil.autoScaleUp((Drawable)this.getDevice(), styleItem.descentInPoints);
                    OS.SetRect(rECT3, rECT3.left, n2 + n22, rECT3.right, n2 + n22 + styleItem.underlineThickness);
                    OS.MoveToEx(l2, rECT3.left, rECT3.top, 0L);
                    OS.LineTo(l2, rECT3.right, rECT3.top);
                    OS.SelectObject(l2, l8);
                    OS.DeleteObject(l7);
                    if (rECT == null) break;
                    l7 = OS.CreatePen(n21, 1, n7);
                    l8 = OS.SelectObject(l2, l7);
                    OS.SetRect(rECT, rECT.left, rECT3.top, rECT.right, rECT3.bottom);
                    OS.MoveToEx(l2, rECT.left, rECT.top, 0L);
                    OS.LineTo(l2, rECT.right, rECT.top);
                    OS.SelectObject(l2, l8);
                    OS.DeleteObject(l7);
                    break;
                }
            }
            return null;
        }
        return rECT;
    }

    RECT drawUnderlineGDIP(long l2, int n, int n2, int n3, int n4, StyleItem[] styleItemArray, int n5, long l3, long l4, RECT rECT, RECT rECT2, int n6, int n7, int n8, Rectangle rectangle) {
        boolean bl;
        StyleItem styleItem = styleItemArray[n5];
        TextStyle textStyle = styleItem.style;
        if (textStyle == null) {
            return null;
        }
        if (!textStyle.underline) {
            return null;
        }
        rECT = this.addClipRect(styleItem, rECT, rECT2, n6, n7);
        boolean bl2 = bl = rectangle != null && n + styleItem.x + styleItem.width > rectangle.x + rectangle.width;
        if (n5 + 1 >= styleItemArray.length || bl || styleItemArray[n5 + 1].lineBreak || !textStyle.isAdherentUnderline(styleItemArray[n5 + 1].style)) {
            int n9;
            int n10 = styleItem.x;
            int n11 = styleItem.start;
            int n12 = styleItem.start + styleItem.length - 1;
            for (n9 = n5; n9 > 0 && textStyle.isAdherentUnderline(styleItemArray[n9 - 1].style); --n9) {
                n10 = styleItemArray[n9 - 1].x;
                n11 = Math.min(n11, styleItemArray[n9 - 1].start);
                n12 = Math.max(n12, styleItemArray[n9 - 1].start + styleItemArray[n9 - 1].length - 1);
            }
            n9 = n6 <= n7 && n6 != -1 && n7 != -1 ? 1 : 0;
            boolean bl3 = n9 != 0 && n6 <= n11 && n12 <= n7;
            long l5 = l3;
            if (textStyle.underlineColor != null) {
                l5 = this.createGdipBrush(textStyle.underlineColor, n8);
                rECT = null;
            } else if (bl3) {
                l5 = l4;
                rECT = null;
            } else if (textStyle.foreground != null) {
                l5 = this.createGdipBrush(textStyle.foreground, n8);
            }
            RECT rECT3 = new RECT();
            int n13 = DPIUtil.autoScaleUp((Drawable)this.getDevice(), textStyle.rise);
            OS.SetRect(rECT3, n + n10, n2 - n3 - n13, n + styleItem.x + styleItem.width, n2 - n3 + styleItem.underlineThickness - n13);
            Rect rect = null;
            if (rECT != null) {
                if (rECT.left == -1) {
                    rECT.left = 0;
                }
                if (rECT.right == -1) {
                    rECT.right = 524287;
                }
                OS.SetRect(rECT, Math.max(rECT3.left, rECT.left), rECT3.top, Math.min(rECT3.right, rECT.right), rECT3.bottom);
                rect = new Rect();
                rect.X = rECT.left;
                rect.Y = rECT.top;
                rect.Width = rECT.right - rECT.left;
                rect.Height = rECT.bottom - rECT.top;
            }
            int n14 = 0;
            Gdip.Graphics_SetPixelOffsetMode(l2, 3);
            int n15 = Gdip.Graphics_GetSmoothingMode(l2);
            Gdip.Graphics_SetSmoothingMode(l2, 3);
            switch (textStyle.underlineStyle) {
                case 2: 
                case 3: {
                    boolean bl4 = true;
                    int n16 = 2;
                    int n17 = Math.min(rECT3.top - 1, n4 - 2 - 1);
                    int[] nArray = this.computePolyline(rECT3.left, n17, rECT3.right, n17 + 2);
                    long l6 = Gdip.Pen_new(l5, 1.0f);
                    n14 = Gdip.Graphics_Save(l2);
                    if (rect != null) {
                        Gdip.Graphics_SetClip(l2, rect, 4);
                    } else {
                        Rect rect2 = new Rect();
                        rect2.X = rECT3.left;
                        rect2.Y = n17;
                        rect2.Width = rECT3.right - rECT3.left;
                        rect2.Height = 3;
                        Gdip.Graphics_SetClip(l2, rect2, 1);
                    }
                    Gdip.Graphics_DrawLines(l2, l6, nArray, nArray.length / 2);
                    if (rect != null) {
                        long l7 = Gdip.Pen_new(l4, 1.0f);
                        Gdip.Graphics_Restore(l2, n14);
                        n14 = Gdip.Graphics_Save(l2);
                        Gdip.Graphics_SetClip(l2, rect, 1);
                        Gdip.Graphics_DrawLines(l2, l7, nArray, nArray.length / 2);
                        Gdip.Pen_delete(l7);
                    }
                    Gdip.Graphics_Restore(l2, n14);
                    Gdip.Pen_delete(l6);
                    if (n14 == 0) break;
                    Gdip.Graphics_Restore(l2, n14);
                    break;
                }
                case 0: 
                case 1: 
                case 4: 
                case 196608: {
                    int n18;
                    if (textStyle.underlineStyle == 196608) {
                        RECT rECT4 = rECT3;
                        rECT4.top -= styleItem.underlineThickness;
                    }
                    int n19 = n18 = textStyle.underlineStyle == 1 ? rECT3.bottom + styleItem.underlineThickness * 2 : rECT3.bottom;
                    if (n18 > n4) {
                        OS.OffsetRect(rECT3, 0, n4 - n18);
                    }
                    if (rect != null) {
                        rect.Y = rECT3.top;
                        if (textStyle.underlineStyle == 196608) {
                            rect.Height = styleItem.underlineThickness * 2;
                        }
                        if (textStyle.underlineStyle == 1) {
                            rect.Height = styleItem.underlineThickness * 3;
                        }
                        n14 = Gdip.Graphics_Save(l2);
                        Gdip.Graphics_SetClip(l2, rect, 4);
                    }
                    Gdip.Graphics_FillRectangle(l2, l5, rECT3.left, rECT3.top, rECT3.right - rECT3.left, rECT3.bottom - rECT3.top);
                    if (textStyle.underlineStyle == 1) {
                        Gdip.Graphics_FillRectangle(l2, l5, rECT3.left, rECT3.top + styleItem.underlineThickness * 2, rECT3.right - rECT3.left, rECT3.bottom - rECT3.top);
                    }
                    if (rect == null) break;
                    Gdip.Graphics_Restore(l2, n14);
                    n14 = Gdip.Graphics_Save(l2);
                    Gdip.Graphics_SetClip(l2, rect, 1);
                    Gdip.Graphics_FillRectangle(l2, l4, rECT3.left, rECT3.top, rECT3.right - rECT3.left, rECT3.bottom - rECT3.top);
                    if (textStyle.underlineStyle == 1) {
                        Gdip.Graphics_FillRectangle(l2, l4, rECT3.left, rECT3.top + styleItem.underlineThickness * 2, rECT3.right - rECT3.left, rECT3.bottom - rECT3.top);
                    }
                    Gdip.Graphics_Restore(l2, n14);
                    break;
                }
                case 65536: 
                case 131072: {
                    long l8 = Gdip.Pen_new(l5, 1.0f);
                    int n20 = textStyle.underlineStyle == 65536 ? 2 : 1;
                    Gdip.Pen_SetDashStyle(l8, n20);
                    if (rect != null) {
                        n14 = Gdip.Graphics_Save(l2);
                        Gdip.Graphics_SetClip(l2, rect, 4);
                    }
                    int n21 = DPIUtil.autoScaleUp((Drawable)this.getDevice(), styleItem.descentInPoints);
                    Gdip.Graphics_DrawLine(l2, l8, rECT3.left, n2 + n21, styleItem.width - styleItem.length, n2 + n21);
                    if (rect != null) {
                        Gdip.Graphics_Restore(l2, n14);
                        n14 = Gdip.Graphics_Save(l2);
                        Gdip.Graphics_SetClip(l2, rect, 1);
                        long l9 = Gdip.Pen_new(l5, 1.0f);
                        Gdip.Pen_SetDashStyle(l9, n20);
                        Gdip.Graphics_DrawLine(l2, l9, rECT3.left, n2 + n21, styleItem.width - styleItem.length, n2 + n21);
                        Gdip.Graphics_Restore(l2, n14);
                        Gdip.Pen_delete(l9);
                    }
                    Gdip.Pen_delete(l8);
                    break;
                }
            }
            if (l5 != l4 && l5 != l3) {
                Gdip.SolidBrush_delete(l5);
            }
            Gdip.Graphics_SetPixelOffsetMode(l2, 4);
            Gdip.Graphics_SetSmoothingMode(l2, n15);
            return null;
        }
        return rECT;
    }

    void freeRuns() {
        if (this.allRuns == null) {
            return;
        }
        for (StyleItem styleItem : this.allRuns) {
            styleItem.free();
        }
        this.allRuns = null;
        this.runs = null;
        this.segmentsText = null;
    }

    public int getAlignment() {
        this.checkLayout();
        return this.alignment;
    }

    public int getAscent() {
        this.checkLayout();
        return DPIUtil.autoScaleDown((Drawable)this.getDevice(), this.ascentInPixels);
    }

    public Rectangle getBounds() {
        this.checkLayout();
        this.computeRuns(null);
        int n = 0;
        if (this.wrapWidth != -1) {
            n = this.wrapWidth;
        } else {
            for (int i = 0; i < this.runs.length; ++i) {
                n = Math.max(n, this.lineWidth[i] + this.getLineIndent(i));
            }
        }
        return new Rectangle(0, 0, DPIUtil.autoScaleDown((Drawable)this.getDevice(), n), this.lineY[this.lineY.length - 1] + this.getScaledVerticalIndent());
    }

    public Rectangle getBounds(int n, int n2) {
        this.checkLayout();
        return DPIUtil.autoScaleDown((Drawable)this.getDevice(), this.getBoundsInPixels(n, n2));
    }

    Rectangle getBoundsInPixels(int n, int n2) {
        this.computeRuns(null);
        int n3 = this.text.length();
        if (n3 == 0) {
            return new Rectangle(0, 0, 0, 0);
        }
        if (n > n2) {
            return new Rectangle(0, 0, 0, 0);
        }
        n = Math.min(Math.max(0, n), n3 - 1);
        n2 = Math.min(Math.max(0, n2), n3 - 1);
        n = this.translateOffset(n);
        n2 = this.translateOffset(n2);
        n3 = this.segmentsText.length();
        char c = this.segmentsText.charAt(n);
        if ('\udc00' <= c && c <= '\udfff' && n - 1 >= 0 && '\ud800' <= (c = this.segmentsText.charAt(n - 1)) && c <= '\udbff') {
            --n;
        }
        if ('\ud800' <= (c = this.segmentsText.charAt(n2)) && c <= '\udbff' && n2 + 1 < n3 && '\udc00' <= (c = this.segmentsText.charAt(n2 + 1)) && c <= '\udfff') {
            ++n2;
        }
        int n4 = Integer.MAX_VALUE;
        int n5 = 0;
        int n6 = Integer.MAX_VALUE;
        int n7 = 0;
        boolean bl = (this.orientation & 0x4000000) != 0;
        for (int i = 0; i < this.allRuns.length - 1; ++i) {
            GlyphMetrics glyphMetrics;
            int n8;
            StyleItem styleItem = this.allRuns[i];
            int n9 = styleItem.start + styleItem.length;
            if (n9 <= n) continue;
            if (styleItem.start > n2) break;
            int n10 = styleItem.x;
            int n11 = styleItem.x + styleItem.width;
            if (styleItem.start <= n && n < n9) {
                n8 = 0;
                if (styleItem.style != null && styleItem.style.metrics != null) {
                    glyphMetrics = styleItem.style.metrics;
                    n8 = glyphMetrics.getWidthInPixels() * (n - styleItem.start);
                } else if (!styleItem.tab) {
                    int n12 = this.ScriptCPtoX(n - styleItem.start, false, styleItem);
                    int n13 = n8 = bl ? styleItem.width - n12 : n12;
                }
                if (styleItem.analysis.fRTL ^ bl) {
                    n11 = styleItem.x + n8;
                } else {
                    n10 = styleItem.x + n8;
                }
            }
            if (styleItem.start <= n2 && n2 < n9) {
                n8 = styleItem.width;
                if (styleItem.style != null && styleItem.style.metrics != null) {
                    glyphMetrics = styleItem.style.metrics;
                    n8 = glyphMetrics.getWidthInPixels() * (n2 - styleItem.start + 1);
                } else if (!styleItem.tab) {
                    int n14 = this.ScriptCPtoX(n2 - styleItem.start, true, styleItem);
                    int n15 = n8 = bl ? styleItem.width - n14 : n14;
                }
                if (styleItem.analysis.fRTL ^ bl) {
                    n10 = styleItem.x + n8;
                } else {
                    n11 = styleItem.x + n8;
                }
            }
            for (n8 = 0; n8 < this.runs.length && this.lineOffset[n8 + 1] <= styleItem.start; ++n8) {
            }
            n4 = Math.min(n4, n10);
            n5 = Math.max(n5, n11);
            n6 = Math.min(n6, DPIUtil.autoScaleUp((Drawable)this.getDevice(), this.lineY[n8]));
            n7 = Math.max(n7, DPIUtil.autoScaleUp((Drawable)this.getDevice(), this.lineY[n8 + 1] - this.lineSpacingInPoints));
        }
        return new Rectangle(n4, n6, n5 - n4, n7 - n6 + this.getScaledVerticalIndent());
    }

    public int getDescent() {
        this.checkLayout();
        return DPIUtil.autoScaleDown((Drawable)this.getDevice(), this.descentInPixels);
    }

    public Font getFont() {
        this.checkLayout();
        return this.font;
    }

    public int getIndent() {
        this.checkLayout();
        return DPIUtil.autoScaleDown((Drawable)this.getDevice(), this.getIndentInPixels());
    }

    int getIndentInPixels() {
        return this.indent;
    }

    public boolean getJustify() {
        this.checkLayout();
        return this.justify;
    }

    long getItemFont(StyleItem styleItem) {
        if (styleItem.fallbackFont != 0L) {
            return styleItem.fallbackFont;
        }
        if (styleItem.style != null && styleItem.style.font != null) {
            return styleItem.style.font.handle;
        }
        if (this.font != null) {
            return this.font.handle;
        }
        return this.device.systemFont.handle;
    }

    public int getLevel(int n) {
        this.checkLayout();
        this.computeRuns(null);
        int n2 = this.text.length();
        if (0 > n || n > n2) {
            SWT.error(6);
        }
        n = this.translateOffset(n);
        for (int i = 1; i < this.allRuns.length; ++i) {
            if (this.allRuns[i].start <= n) continue;
            return this.allRuns[i - 1].analysis.s.uBidiLevel;
        }
        return (this.resolveTextDirection() & 0x4000000) != 0 ? 1 : 0;
    }

    public Rectangle getLineBounds(int n) {
        this.checkLayout();
        return DPIUtil.autoScaleDown((Drawable)this.getDevice(), this.getLineBoundsInPixels(n));
    }

    Rectangle getLineBoundsInPixels(int n) {
        this.computeRuns(null);
        if (0 > n || n >= this.runs.length) {
            SWT.error(6);
        }
        int n2 = this.getLineIndent(n);
        int n3 = DPIUtil.autoScaleUp((Drawable)this.getDevice(), this.lineY[n]);
        int n4 = this.lineWidth[n];
        int n5 = DPIUtil.autoScaleUp((Drawable)this.getDevice(), this.lineY[n + 1] - this.lineY[n] - this.lineSpacingInPoints);
        return new Rectangle(n2, n3, n4, n5);
    }

    public int getLineCount() {
        this.checkLayout();
        this.computeRuns(null);
        return this.runs.length;
    }

    int getLineIndent(int n) {
        StyleItem[] styleItemArray;
        int n2 = this.wrapIndent;
        if (n == 0) {
            n2 = this.indent;
        } else {
            StyleItem[] styleItemArray2 = this.runs[n - 1];
            styleItemArray = styleItemArray2[styleItemArray2.length - 1];
            if (styleItemArray.lineBreak && !styleItemArray.softBreak) {
                n2 = this.indent;
            }
        }
        if (this.wrapWidth != -1) {
            boolean bl = true;
            if (this.justify) {
                styleItemArray = this.runs[n];
                if (styleItemArray[styleItemArray.length - 1].softBreak) {
                    bl = false;
                }
            }
            if (bl) {
                int n3 = this.lineWidth[n] + n2;
                switch (this.alignment) {
                    case 0x1000000: {
                        n2 += (this.wrapWidth - n3) / 2;
                        break;
                    }
                    case 131072: {
                        n2 += this.wrapWidth - n3;
                    }
                }
            }
        }
        return n2;
    }

    public int getLineIndex(int n) {
        this.checkLayout();
        this.computeRuns(null);
        int n2 = this.text.length();
        if (0 > n || n > n2) {
            SWT.error(6);
        }
        n = this.translateOffset(n);
        for (int i = 0; i < this.runs.length; ++i) {
            if (this.lineOffset[i + 1] <= n) continue;
            return i;
        }
        return this.runs.length - 1;
    }

    public FontMetrics getLineMetrics(int n) {
        this.checkLayout();
        this.computeRuns(null);
        if (0 > n || n >= this.runs.length) {
            SWT.error(6);
        }
        long l2 = this.device.internal_new_GC(null);
        long l3 = OS.CreateCompatibleDC(l2);
        TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
        OS.SelectObject(l3, this.font != null ? this.font.handle : this.device.systemFont.handle);
        OS.GetTextMetrics(l3, tEXTMETRIC);
        OS.DeleteDC(l3);
        this.device.internal_dispose_GC(l2, null);
        int n2 = DPIUtil.autoScaleDown((Drawable)this.getDevice(), Math.max(tEXTMETRIC.tmAscent, this.ascentInPixels));
        int n3 = DPIUtil.autoScaleDown((Drawable)this.getDevice(), Math.max(tEXTMETRIC.tmDescent, this.descentInPixels));
        int n4 = DPIUtil.autoScaleDown((Drawable)this.getDevice(), tEXTMETRIC.tmInternalLeading);
        if (this.text.length() != 0) {
            for (StyleItem styleItem : this.runs[n]) {
                if (styleItem.ascentInPoints > n2) {
                    n2 = styleItem.ascentInPoints;
                    n4 = styleItem.leadingInPoints;
                }
                n3 = Math.max(n3, styleItem.descentInPoints);
            }
        }
        tEXTMETRIC.tmAscent = DPIUtil.autoScaleUp((Drawable)this.getDevice(), n2);
        tEXTMETRIC.tmDescent = DPIUtil.autoScaleUp((Drawable)this.getDevice(), n3);
        tEXTMETRIC.tmHeight = DPIUtil.autoScaleUp((Drawable)this.getDevice(), n2 + n3);
        tEXTMETRIC.tmInternalLeading = DPIUtil.autoScaleUp((Drawable)this.getDevice(), n4);
        tEXTMETRIC.tmAveCharWidth = 0;
        return FontMetrics.win32_new(tEXTMETRIC);
    }

    public int[] getLineOffsets() {
        this.checkLayout();
        this.computeRuns(null);
        int[] nArray = new int[this.lineOffset.length];
        for (int i = 0; i < nArray.length; ++i) {
            nArray[i] = this.untranslateOffset(this.lineOffset[i]);
        }
        return nArray;
    }

    public Point getLocation(int n, boolean bl) {
        this.checkLayout();
        return DPIUtil.autoScaleDown((Drawable)this.getDevice(), this.getLocationInPixels(n, bl));
    }

    Point getLocationInPixels(int n, boolean bl) {
        int n2;
        this.computeRuns(null);
        int n3 = this.text.length();
        if (0 > n || n > n3) {
            SWT.error(6);
        }
        n3 = this.segmentsText.length();
        n = this.translateOffset(n);
        for (n2 = 0; n2 < this.runs.length && this.lineOffset[n2 + 1] <= n; ++n2) {
        }
        n2 = Math.min(n2, this.runs.length - 1);
        if (n == n3) {
            return new Point(this.getLineIndent(n2) + this.lineWidth[n2], DPIUtil.autoScaleUp((Drawable)this.getDevice(), this.lineY[n2]));
        }
        char c = this.segmentsText.charAt(n);
        if (bl) {
            if ('\ud800' <= c && c <= '\udbff' && n + 1 < n3 && '\udc00' <= (c = this.segmentsText.charAt(n + 1)) && c <= '\udfff') {
                ++n;
            }
        } else if ('\udc00' <= c && c <= '\udfff' && n - 1 >= 0 && '\ud800' <= (c = this.segmentsText.charAt(n - 1)) && c <= '\udbff') {
            --n;
        }
        int n4 = -1;
        int n5 = this.allRuns.length;
        while (n5 - n4 > 1) {
            int n6 = (n5 + n4) / 2;
            StyleItem styleItem = this.allRuns[n6];
            if (styleItem.start > n) {
                n5 = n6;
                continue;
            }
            if (styleItem.start + styleItem.length > n) {
                int n7;
                if (styleItem.style != null && styleItem.style.metrics != null) {
                    GlyphMetrics glyphMetrics = styleItem.style.metrics;
                    n7 = glyphMetrics.getWidthInPixels() * (n - styleItem.start + (bl ? 1 : 0));
                } else if (styleItem.tab) {
                    n7 = bl || n == n3 ? styleItem.width : 0;
                } else {
                    int n8 = n - styleItem.start;
                    int n9 = this.ScriptCPtoX(n8, bl, styleItem);
                    n7 = (this.orientation & 0x4000000) != 0 ? styleItem.width - n9 : n9;
                }
                return new Point(styleItem.x + n7, DPIUtil.autoScaleUp((Drawable)this.getDevice(), this.lineY[n2]) + this.getScaledVerticalIndent());
            }
            n4 = n6;
        }
        return new Point(0, 0);
    }

    private int ScriptCPtoX(int n, boolean bl, StyleItem styleItem) {
        int[] nArray = new int[]{0};
        long l2 = styleItem.justify != 0L ? styleItem.justify : styleItem.advances;
        OS.ScriptCPtoX(n, bl, styleItem.length, styleItem.glyphCount, styleItem.clusters, styleItem.visAttrs, l2, styleItem.analysis, nArray);
        return nArray[0];
    }

    public int getNextOffset(int n, int n2) {
        this.checkLayout();
        return this._getOffset(n, n2, true);
    }

    int _getOffset(int n, int n2, boolean bl) {
        int n3;
        this.computeRuns(null);
        int n4 = this.text.length();
        if (0 > n || n > n4) {
            SWT.error(6, null, " [offset value: " + n);
        }
        if (bl && n == n4) {
            return n4;
        }
        if (!bl && n == 0) {
            return 0;
        }
        int n5 = n3 = bl ? 1 : -1;
        if ((n2 & 1) != 0) {
            return n + n3;
        }
        n4 = this.segmentsText.length();
        n = this.translateOffset(n);
        SCRIPT_LOGATTR sCRIPT_LOGATTR = new SCRIPT_LOGATTR();
        SCRIPT_PROPERTIES sCRIPT_PROPERTIES = new SCRIPT_PROPERTIES();
        int n6 = bl ? 0 : this.allRuns.length - 1;
        n = this.validadeOffset(n, n3);
        do {
            boolean bl2;
            StyleItem styleItem = this.allRuns[n6];
            if (styleItem.start > n || n >= styleItem.start + styleItem.length) continue;
            if (styleItem.lineBreak && !styleItem.softBreak) {
                return this.untranslateOffset(styleItem.start);
            }
            if (styleItem.tab) {
                return this.untranslateOffset(styleItem.start);
            }
            OS.MoveMemory(sCRIPT_PROPERTIES, this.device.scripts[styleItem.analysis.eScript], SCRIPT_PROPERTIES.sizeof);
            boolean bl3 = bl2 = sCRIPT_PROPERTIES.fNeedsCaretInfo || sCRIPT_PROPERTIES.fNeedsWordBreaking;
            if (bl2) {
                this.breakRun(styleItem);
            }
            while (styleItem.start <= n && n < styleItem.start + styleItem.length) {
                if (bl2) {
                    OS.MoveMemory(sCRIPT_LOGATTR, styleItem.psla + (long)((n - styleItem.start) * SCRIPT_LOGATTR.sizeof), SCRIPT_LOGATTR.sizeof);
                }
                switch (n2) {
                    case 2: {
                        if (sCRIPT_PROPERTIES.fNeedsCaretInfo && (sCRIPT_LOGATTR.fInvalid || !sCRIPT_LOGATTR.fCharStop)) break;
                        char c2 = this.segmentsText.charAt(n);
                        if ('\udc00' <= c2 && c2 <= '\udfff' && n > 0 && '\ud800' <= (c2 = this.segmentsText.charAt(n - 1)) && c2 <= '\udbff') {
                            n += n3;
                        }
                        return this.untranslateOffset(n);
                    }
                    case 4: 
                    case 16: {
                        char c;
                        char c2;
                        if (!(sCRIPT_PROPERTIES.fNeedsWordBreaking ? !sCRIPT_LOGATTR.fInvalid && sCRIPT_LOGATTR.fWordStop : n > 0 && ((c2 = (char)(Character.isLetterOrDigit(this.segmentsText.charAt(n)) ? 1 : 0)) != (c = Character.isLetterOrDigit(this.segmentsText.charAt(n - 1))) || c2 == '\u0000') && !Character.isWhitespace(this.segmentsText.charAt(n)))) break;
                        return this.untranslateOffset(n);
                    }
                    case 8: {
                        if (n <= 0) break;
                        char c2 = (char)(Character.isLetterOrDigit(this.segmentsText.charAt(n)) ? 1 : 0);
                        char c = Character.isLetterOrDigit(this.segmentsText.charAt(n - 1));
                        if (c2 != '\u0000' || c == '\u0000') break;
                        return this.untranslateOffset(n);
                    }
                }
                n = this.validadeOffset(n, n3);
            }
        } while (0 <= (n6 += n3) && n6 < this.allRuns.length - 1 && 0 <= n && n < n4);
        return bl ? this.text.length() : 0;
    }

    public int getOffset(Point point, int[] nArray) {
        this.checkLayout();
        if (point == null) {
            SWT.error(4);
        }
        return this.getOffsetInPixels(DPIUtil.autoScaleUp((Drawable)this.getDevice(), point), nArray);
    }

    int getOffsetInPixels(Point point, int[] nArray) {
        return this.getOffsetInPixels(point.x, point.y, nArray);
    }

    public int getOffset(int n, int n2, int[] nArray) {
        this.checkLayout();
        return this.getOffsetInPixels(DPIUtil.autoScaleUp((Drawable)this.getDevice(), n), DPIUtil.autoScaleUp((Drawable)this.getDevice(), n2), nArray);
    }

    int getOffsetInPixels(int n, int n2, int[] nArray) {
        int n3;
        this.computeRuns(null);
        if (nArray != null && nArray.length < 1) {
            SWT.error(5);
        }
        int n4 = this.runs.length;
        for (n3 = 0; n3 < n4 && DPIUtil.autoScaleUp((Drawable)this.getDevice(), this.lineY[n3 + 1]) <= n2; ++n3) {
        }
        n3 = Math.min(n3, this.runs.length - 1);
        StyleItem[] styleItemArray = this.runs[n3];
        int n5 = this.getLineIndent(n3);
        if (n >= n5 + this.lineWidth[n3]) {
            n = n5 + this.lineWidth[n3] - 1;
        }
        if (n < n5) {
            n = n5;
        }
        int n6 = -1;
        int n7 = styleItemArray.length;
        while (n7 - n6 > 1) {
            char c;
            GlyphMetrics glyphMetrics;
            int n8 = (n7 + n6) / 2;
            StyleItem styleItem = styleItemArray[n8];
            if (styleItem.x > n) {
                n7 = n8;
                continue;
            }
            if (styleItem.x + styleItem.width <= n) {
                n6 = n8;
                continue;
            }
            if (styleItem.lineBreak && !styleItem.softBreak) {
                return this.untranslateOffset(styleItem.start);
            }
            int n9 = n - styleItem.x;
            if (styleItem.style != null && styleItem.style.metrics != null && (glyphMetrics = styleItem.style.metrics).getWidthInPixels() > 0) {
                if (nArray != null) {
                    nArray[0] = n9 % glyphMetrics.getWidthInPixels() >= glyphMetrics.getWidthInPixels() / 2 ? 1 : 0;
                }
                return this.untranslateOffset(styleItem.start + n9 / glyphMetrics.getWidthInPixels());
            }
            if (styleItem.tab) {
                if (nArray != null) {
                    nArray[0] = n >= styleItem.x + styleItem.width / 2 ? 1 : 0;
                }
                return this.untranslateOffset(styleItem.start);
            }
            int n10 = styleItem.length;
            int n11 = styleItem.glyphCount;
            int[] nArray2 = new int[]{0};
            int[] nArray3 = new int[]{0};
            if ((this.orientation & 0x4000000) != 0) {
                n9 = styleItem.width - n9;
            }
            long l2 = styleItem.justify != 0L ? styleItem.justify : styleItem.advances;
            OS.ScriptXtoCP(n9, n10, n11, styleItem.clusters, styleItem.visAttrs, l2, styleItem.analysis, nArray2, nArray3);
            int n12 = styleItem.start + nArray2[0];
            int n13 = this.segmentsText.length();
            char c2 = c = n12 < n13 ? this.segmentsText.charAt(n12) : (char)'\u0000';
            if ('\ud800' <= c && c <= '\udbff' && nArray3[0] <= 1) {
                if (n12 + 1 < n13 && '\udc00' <= (c = this.segmentsText.charAt(n12 + 1)) && c <= '\udfff' && nArray != null) {
                    nArray[0] = 0;
                }
            } else if ('\udc00' <= c && c <= '\udfff' && nArray3[0] <= 1) {
                if (n12 - 1 >= 0 && '\ud800' <= (c = this.segmentsText.charAt(n12 - 1)) && c <= '\udbff') {
                    --n12;
                    if (nArray != null) {
                        nArray[0] = 2;
                    }
                }
            } else if (nArray != null) {
                nArray[0] = nArray3[0];
            }
            return this.untranslateOffset(n12);
        }
        if (nArray != null) {
            nArray[0] = 0;
        }
        if (styleItemArray.length == 1) {
            StyleItem styleItem = styleItemArray[0];
            if (styleItem.lineBreak && !styleItem.softBreak) {
                return this.untranslateOffset(styleItem.start);
            }
        }
        return this.untranslateOffset(this.lineOffset[n3 + 1]);
    }

    public int getOrientation() {
        this.checkLayout();
        return this.orientation;
    }

    void getPartialSelection(StyleItem styleItem, int n, int n2, RECT rECT) {
        int n3 = styleItem.start + styleItem.length - 1;
        int n4 = Math.max(n, styleItem.start) - styleItem.start;
        int n5 = Math.min(n2, n3) - styleItem.start;
        int n6 = rECT.left;
        int n7 = this.ScriptCPtoX(n4, false, styleItem);
        int n8 = (this.orientation & 0x4000000) != 0 ? styleItem.width - n7 : n7;
        rECT.left = n6 + n8;
        n7 = this.ScriptCPtoX(n5, true, styleItem);
        n8 = (this.orientation & 0x4000000) != 0 ? styleItem.width - n7 : n7;
        rECT.right = n6 + n8;
    }

    public int getPreviousOffset(int n, int n2) {
        this.checkLayout();
        return this._getOffset(n, n2, false);
    }

    public int[] getRanges() {
        this.checkLayout();
        int[] nArray = new int[this.stylesCount * 2];
        int n = 0;
        for (int i = 0; i < this.stylesCount - 1; ++i) {
            if (this.styles[i].style == null) continue;
            nArray[n++] = this.styles[i].start;
            nArray[n++] = this.styles[i + 1].start - 1;
        }
        if (n != nArray.length) {
            int[] nArray2 = new int[n];
            System.arraycopy(nArray, 0, nArray2, 0, n);
            nArray = nArray2;
        }
        return nArray;
    }

    public int[] getSegments() {
        this.checkLayout();
        return this.segments;
    }

    public char[] getSegmentsChars() {
        this.checkLayout();
        return this.segmentsChars;
    }

    String getSegmentsText() {
        int n;
        int n2;
        int n3 = this.text.length();
        if (n3 == 0) {
            return this.text;
        }
        if (this.segments == null) {
            return this.text;
        }
        int n4 = this.segments.length;
        if (n4 == 0) {
            return this.text;
        }
        if (this.segmentsChars == null) {
            if (n4 == 1) {
                return this.text;
            }
            if (n4 == 2 && this.segments[0] == 0 && this.segments[1] == n3) {
                return this.text;
            }
        }
        char[] cArray = new char[n3];
        this.text.getChars(0, n3, cArray, 0);
        char[] cArray2 = new char[n3 + n4];
        int n5 = 0;
        int n6 = 0;
        int n7 = n2 = (this.resolveTextDirection() & 0x4000000) != 0 ? 8207 : 8206;
        while (n5 < n3) {
            if (n6 < n4 && n5 == this.segments[n6]) {
                n = this.segmentsChars != null && this.segmentsChars.length > n6 ? this.segmentsChars[n6] : n2;
                cArray2[n5 + n6++] = n;
                continue;
            }
            cArray2[n5 + n6] = cArray[n5++];
        }
        while (n6 < n4) {
            this.segments[n6] = n5;
            n = this.segmentsChars != null && this.segmentsChars.length > n6 ? this.segmentsChars[n6] : n2;
            cArray2[n5 + n6++] = n;
        }
        return new String(cArray2, 0, cArray2.length);
    }

    public int getSpacing() {
        this.checkLayout();
        return this.lineSpacingInPoints;
    }

    public int getVerticalIndent() {
        this.checkLayout();
        return this.verticalIndentInPoints;
    }

    private int getScaledVerticalIndent() {
        if (this.verticalIndentInPoints == 0) {
            return this.verticalIndentInPoints;
        }
        return DPIUtil.autoScaleUp((Drawable)this.getDevice(), this.verticalIndentInPoints);
    }

    public TextStyle getStyle(int n) {
        this.checkLayout();
        int n2 = this.text.length();
        if (0 > n || n >= n2) {
            SWT.error(6);
        }
        for (int i = 1; i < this.stylesCount; ++i) {
            if (this.styles[i].start <= n) continue;
            return this.styles[i - 1].style;
        }
        return null;
    }

    public TextStyle[] getStyles() {
        this.checkLayout();
        TextStyle[] textStyleArray = new TextStyle[this.stylesCount];
        int n = 0;
        for (int i = 0; i < this.stylesCount; ++i) {
            if (this.styles[i].style == null) continue;
            textStyleArray[n++] = this.styles[i].style;
        }
        if (n != textStyleArray.length) {
            TextStyle[] textStyleArray2 = new TextStyle[n];
            System.arraycopy(textStyleArray, 0, textStyleArray2, 0, n);
            textStyleArray = textStyleArray2;
        }
        return textStyleArray;
    }

    public int[] getTabs() {
        this.checkLayout();
        return DPIUtil.autoScaleDown((Drawable)this.getDevice(), this.getTabsInPixels());
    }

    int[] getTabsInPixels() {
        return this.tabs;
    }

    public String getText() {
        this.checkLayout();
        return this.text;
    }

    public int getTextDirection() {
        this.checkLayout();
        return this.resolveTextDirection();
    }

    public int getWidth() {
        this.checkLayout();
        return DPIUtil.autoScaleDown((Drawable)this.getDevice(), this.getWidthInPixels());
    }

    int getWidthInPixels() {
        return this.wrapWidth;
    }

    public int getWrapIndent() {
        this.checkLayout();
        return DPIUtil.autoScaleDown((Drawable)this.getDevice(), this.getWrapIndentInPixels());
    }

    int getWrapIndentInPixels() {
        return this.wrapIndent;
    }

    StyleItem[] itemize() {
        this.segmentsText = this.getSegmentsText();
        int n = this.segmentsText.length();
        SCRIPT_CONTROL sCRIPT_CONTROL = new SCRIPT_CONTROL();
        SCRIPT_STATE sCRIPT_STATE = new SCRIPT_STATE();
        int n2 = n + 1;
        if ((this.resolveTextDirection() & 0x4000000) != 0) {
            sCRIPT_STATE.uBidiLevel = 1;
            sCRIPT_STATE.fArabicNumContext = true;
        }
        OS.ScriptApplyDigitSubstitution(0L, sCRIPT_CONTROL, sCRIPT_STATE);
        long l2 = OS.GetProcessHeap();
        long l3 = OS.HeapAlloc(l2, 8, (1 + n2) * SCRIPT_ITEM.sizeof);
        if (l3 == 0L) {
            SWT.error(2);
        }
        int[] nArray = new int[]{0};
        char[] cArray = new char[n];
        this.segmentsText.getChars(0, n, cArray, 0);
        sCRIPT_CONTROL.fMergeNeutralItems = true;
        if (BidiUtil.resolveTextDirection(this.text) != 0x4000000) {
            int n3 = -2;
            int n4 = -2;
            for (int i = 0; i < n; ++i) {
                char c = cArray[i];
                if (c >= ' ' && c <= '~' && !Character.isAlphabetic(c)) {
                    n3 = i;
                } else {
                    if (c <= '\u00ff') continue;
                    n4 = i;
                }
                if (Math.abs(n3 - n4) != 1) continue;
                cArray[n3] = 65;
            }
        }
        OS.ScriptItemize(cArray, n, n2, sCRIPT_CONTROL, sCRIPT_STATE, l3, nArray);
        StyleItem[] styleItemArray = this.merge(l3, nArray[0]);
        OS.HeapFree(l2, 0, l3);
        return styleItemArray;
    }

    StyleItem[] merge(long l2, int n) {
        StyleItem styleItem;
        if (this.styles.length > this.stylesCount) {
            StyleItem[] styleItemArray = new StyleItem[this.stylesCount];
            System.arraycopy(this.styles, 0, styleItemArray, 0, this.stylesCount);
            this.styles = styleItemArray;
        }
        int n2 = this.segmentsText.length();
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        ArrayList<StyleItem> arrayList = new ArrayList<StyleItem>(n + this.stylesCount + (n2 + 32000 - 1) / 32000);
        SCRIPT_ITEM sCRIPT_ITEM = new SCRIPT_ITEM();
        int n6 = -1;
        int n7 = 0;
        boolean bl = false;
        boolean bl2 = n > 1024;
        SCRIPT_PROPERTIES sCRIPT_PROPERTIES = new SCRIPT_PROPERTIES();
        while (n3 < n2) {
            int n8;
            int n9;
            int n10;
            styleItem = new StyleItem(this);
            styleItem.start = n3;
            styleItem.style = this.styles[n5].style;
            arrayList.add(styleItem);
            OS.MoveMemory(sCRIPT_ITEM, l2 + (long)(n4 * SCRIPT_ITEM.sizeof), SCRIPT_ITEM.sizeof);
            styleItem.analysis = sCRIPT_ITEM.a;
            sCRIPT_ITEM.a = new SCRIPT_ANALYSIS();
            if (bl) {
                styleItem.analysis.fLinkBefore = true;
                bl = false;
            }
            char c = this.segmentsText.charAt(n3);
            switch (c) {
                case '\n': 
                case '\r': {
                    styleItem.lineBreak = true;
                    break;
                }
                case '\t': {
                    styleItem.tab = true;
                }
            }
            if (n6 == -1) {
                n7 = n4 + 1;
                OS.MoveMemory(sCRIPT_ITEM, l2 + (long)(n7 * SCRIPT_ITEM.sizeof), SCRIPT_ITEM.sizeof);
                n6 = sCRIPT_ITEM.iCharPos;
                if (n7 < n && c == '\r' && this.segmentsText.charAt(n6) == '\n') {
                    n7 = n4 + 2;
                    OS.MoveMemory(sCRIPT_ITEM, l2 + (long)(n7 * SCRIPT_ITEM.sizeof), SCRIPT_ITEM.sizeof);
                    n6 = sCRIPT_ITEM.iCharPos;
                }
                if (n7 < n && bl2 && !styleItem.lineBreak) {
                    OS.MoveMemory(sCRIPT_PROPERTIES, this.device.scripts[styleItem.analysis.eScript], SCRIPT_PROPERTIES.sizeof);
                    if (!sCRIPT_PROPERTIES.fComplex || styleItem.tab) {
                        for (n10 = 0; n10 < 512 && n7 != n && (n9 = (int)this.segmentsText.charAt(n6)) != 10 && n9 != 13 && n9 == 9 == styleItem.tab; ++n10) {
                            OS.MoveMemory(sCRIPT_PROPERTIES, this.device.scripts[sCRIPT_ITEM.a.eScript], SCRIPT_PROPERTIES.sizeof);
                            if (!styleItem.tab && sCRIPT_PROPERTIES.fComplex) break;
                            OS.MoveMemory(sCRIPT_ITEM, l2 + (long)(++n7 * SCRIPT_ITEM.sizeof), SCRIPT_ITEM.sizeof);
                            n6 = sCRIPT_ITEM.iCharPos;
                        }
                    }
                }
            }
            n10 = 1;
            n9 = this.translateOffset(this.styles[n5 + 1].start);
            if (n9 <= n6 && (n8 = n9 - n3) < 32000) {
                n10 = 0;
                ++n5;
                n3 = n9;
                if (n3 < n6 && 0 < n3 && n3 < n2) {
                    char c2 = this.segmentsText.charAt(n3 - 1);
                    char c3 = this.segmentsText.charAt(n3);
                    if (Character.isLetter(c2) && Character.isLetter(c3)) {
                        styleItem.analysis.fLinkAfter = true;
                        bl = true;
                    }
                }
            }
            n8 = n6 - n3;
            if (n10 != 0 && n8 > 32000) {
                n3 += this.splitLongRun(styleItem);
            } else if (n6 <= n9) {
                n4 = n7;
                n3 = n6;
                n6 = -1;
            }
            styleItem.length = n3 - styleItem.start;
        }
        styleItem = new StyleItem(this);
        styleItem.start = n2;
        OS.MoveMemory(sCRIPT_ITEM, l2 + (long)(n * SCRIPT_ITEM.sizeof), SCRIPT_ITEM.sizeof);
        styleItem.analysis = sCRIPT_ITEM.a;
        arrayList.add(styleItem);
        return (StyleItem[])arrayList.toArray();
    }

    int splitLongRun(StyleItem styleItem) {
        styleItem.length = 32000;
        this.breakRun(styleItem);
        SCRIPT_LOGATTR sCRIPT_LOGATTR = new SCRIPT_LOGATTR();
        int n = 32000;
        for (int i = 31999; i >= 31000; --i) {
            int n2 = i * SCRIPT_LOGATTR.sizeof;
            if (n2 + SCRIPT_LOGATTR.sizeof > styleItem.pslaAllocSize) {
                throw new IndexOutOfBoundsException();
            }
            OS.MoveMemory(sCRIPT_LOGATTR, styleItem.psla + (long)n2, SCRIPT_LOGATTR.sizeof);
            if (!sCRIPT_LOGATTR.fSoftBreak && !sCRIPT_LOGATTR.fWhiteSpace && !sCRIPT_LOGATTR.fWordStop) continue;
            n = i;
            break;
        }
        if (Character.isHighSurrogate(this.segmentsText.charAt(styleItem.start + n - 1))) {
            --n;
        }
        return n;
    }

    int resolveTextDirection() {
        return this.textDirection == 0x6000000 ? BidiUtil.resolveTextDirection(this.text) : this.textDirection;
    }

    StyleItem[] reorder(StyleItem[] styleItemArray, boolean bl) {
        int n;
        int n2 = styleItemArray.length;
        if (n2 <= 1) {
            return styleItemArray;
        }
        byte[] byArray = new byte[n2];
        for (int i = 0; i < n2; ++i) {
            byArray[i] = (byte)(styleItemArray[i].analysis.s.uBidiLevel & 0x1F);
        }
        StyleItem styleItem = styleItemArray[n2 - 1];
        if (styleItem.lineBreak && !styleItem.softBreak) {
            byArray[n2 - 1] = 0;
        }
        int[] nArray = new int[n2];
        OS.ScriptLayout(n2, byArray, null, nArray);
        StyleItem[] styleItemArray2 = new StyleItem[n2];
        for (n = 0; n < n2; ++n) {
            styleItemArray2[nArray[n]] = styleItemArray[n];
        }
        if ((this.orientation & 0x4000000) != 0) {
            if (bl) {
                --n2;
            }
            for (n = 0; n < n2 / 2; ++n) {
                StyleItem styleItem2 = styleItemArray2[n];
                styleItemArray2[n] = styleItemArray2[n2 - n - 1];
                styleItemArray2[n2 - n - 1] = styleItem2;
            }
        }
        return styleItemArray2;
    }

    public void setAlignment(int n) {
        this.checkLayout();
        int n2 = 16924672;
        if ((n &= 0x1024000) == 0) {
            return;
        }
        if ((n & 0x4000) != 0) {
            n = 16384;
        }
        if ((n & 0x20000) != 0) {
            n = 131072;
        }
        if (this.alignment == n) {
            return;
        }
        this.freeRuns();
        this.alignment = n;
    }

    public void setAscent(int n) {
        this.checkLayout();
        if (n < -1) {
            SWT.error(5);
        }
        if (this.ascentInPixels == (n = DPIUtil.autoScaleUp((Drawable)this.getDevice(), n))) {
            return;
        }
        this.freeRuns();
        this.ascentInPixels = n;
    }

    public void setDescent(int n) {
        this.checkLayout();
        if (n < -1) {
            SWT.error(5);
        }
        if (this.descentInPixels == (n = DPIUtil.autoScaleUp((Drawable)this.getDevice(), n))) {
            return;
        }
        this.freeRuns();
        this.descentInPixels = n;
    }

    public void setFont(Font font) {
        Font font2;
        this.checkLayout();
        if (font != null && font.isDisposed()) {
            SWT.error(5);
        }
        if ((font2 = this.font) == font) {
            return;
        }
        this.font = font;
        if (font2 != null && font2.equals(font)) {
            return;
        }
        this.freeRuns();
    }

    public void setIndent(int n) {
        this.checkLayout();
        this.setIndentInPixels(DPIUtil.autoScaleUp((Drawable)this.getDevice(), n));
    }

    void setIndentInPixels(int n) {
        if (n < 0) {
            return;
        }
        if (this.indent == n) {
            return;
        }
        this.freeRuns();
        this.indent = n;
    }

    public void setJustify(boolean bl) {
        this.checkLayout();
        if (this.justify == bl) {
            return;
        }
        this.freeRuns();
        this.justify = bl;
    }

    public void setOrientation(int n) {
        int n2;
        this.checkLayout();
        int n3 = 0x6000000;
        if ((n &= 0x6000000) == 0) {
            return;
        }
        if ((n & 0x2000000) != 0) {
            n = 0x2000000;
        }
        if (this.orientation == n) {
            return;
        }
        this.orientation = n2 = n;
        this.textDirection = n2;
        this.freeRuns();
    }

    public void setSegments(int[] nArray) {
        this.checkLayout();
        if (this.segments == null && nArray == null) {
            return;
        }
        if (this.segments != null && nArray != null && this.segments.length == nArray.length) {
            int n;
            for (n = 0; n < nArray.length && this.segments[n] == nArray[n]; ++n) {
            }
            if (n == nArray.length) {
                return;
            }
        }
        this.freeRuns();
        this.segments = nArray;
    }

    public void setSegmentsChars(char[] cArray) {
        this.checkLayout();
        if (this.segmentsChars == null && cArray == null) {
            return;
        }
        if (this.segmentsChars != null && cArray != null && this.segmentsChars.length == cArray.length) {
            int n;
            for (n = 0; n < cArray.length && this.segmentsChars[n] == cArray[n]; ++n) {
            }
            if (n == cArray.length) {
                return;
            }
        }
        this.freeRuns();
        this.segmentsChars = cArray;
    }

    public void setSpacing(int n) {
        this.checkLayout();
        if (n < 0) {
            SWT.error(5);
        }
        if (this.lineSpacingInPoints == n) {
            return;
        }
        this.freeRuns();
        this.lineSpacingInPoints = n;
    }

    public void setVerticalIndent(int n) {
        this.checkLayout();
        if (n < 0) {
            SWT.error(5);
        }
        if (this.verticalIndentInPoints == n) {
            return;
        }
        this.verticalIndentInPoints = n;
    }

    public void setStyle(TextStyle textStyle, int n, int n2) {
        int n3;
        int n4;
        int n5;
        this.checkLayout();
        int n6 = this.text.length();
        if (n6 == 0) {
            return;
        }
        if (n > n2) {
            return;
        }
        n = Math.min(Math.max(0, n), n6 - 1);
        n2 = Math.min(Math.max(0, n2), n6 - 1);
        int n7 = -1;
        int n8 = this.stylesCount;
        while (n8 - n7 > 1) {
            int n9 = (n8 + n7) / 2;
            if (this.styles[n9 + 1].start > n) {
                n8 = n9;
                continue;
            }
            n7 = n9;
        }
        if (0 <= n8 && n8 < this.stylesCount) {
            StyleItem styleItem = this.styles[n8];
            if (styleItem.start == n && this.styles[n8 + 1].start - 1 == n2 && (textStyle == null ? styleItem.style == null : textStyle.equals(styleItem.style))) {
                return;
            }
        }
        this.freeRuns();
        int n10 = n5 = n8;
        while (n5 < this.stylesCount && this.styles[n5 + 1].start <= n2) {
            ++n5;
        }
        if (n10 == n5) {
            n4 = this.styles[n10].start;
            n3 = this.styles[n5 + 1].start - 1;
            if (n4 == n && n3 == n2) {
                this.styles[n10].style = textStyle;
                return;
            }
            if (n4 != n && n3 != n2) {
                int n11 = this.stylesCount + 2;
                if (n11 > this.styles.length) {
                    int n12 = Math.min(n11 + 1024, Math.max(64, n11 * 2));
                    StyleItem[] styleItemArray = new StyleItem[n12];
                    System.arraycopy(this.styles, 0, styleItemArray, 0, this.stylesCount);
                    this.styles = styleItemArray;
                }
                System.arraycopy(this.styles, n5 + 1, this.styles, n5 + 3, this.stylesCount - n5 - 1);
                StyleItem styleItem = new StyleItem(this);
                styleItem.start = n;
                styleItem.style = textStyle;
                this.styles[n10 + 1] = styleItem;
                styleItem = new StyleItem(this);
                styleItem.start = n2 + 1;
                styleItem.style = this.styles[n10].style;
                this.styles[n10 + 2] = styleItem;
                this.stylesCount = n11;
                return;
            }
        }
        if (n == this.styles[n10].start) {
            --n10;
        }
        if (n2 == this.styles[n5 + 1].start - 1) {
            ++n5;
        }
        if ((n4 = this.stylesCount + 1 - (n5 - n10 - 1)) > this.styles.length) {
            n3 = Math.min(n4 + 1024, Math.max(64, n4 * 2));
            StyleItem[] styleItemArray = new StyleItem[n3];
            System.arraycopy(this.styles, 0, styleItemArray, 0, this.stylesCount);
            this.styles = styleItemArray;
        }
        System.arraycopy(this.styles, n5, this.styles, n10 + 2, this.stylesCount - n5);
        StyleItem styleItem = new StyleItem(this);
        styleItem.start = n;
        styleItem.style = textStyle;
        this.styles[n10 + 1] = styleItem;
        this.styles[n10 + 2].start = n2 + 1;
        this.stylesCount = n4;
    }

    public void setTabs(int[] nArray) {
        this.checkLayout();
        if (this.tabs == null && nArray == null) {
            return;
        }
        this.setTabsInPixels(DPIUtil.autoScaleUp((Drawable)this.getDevice(), nArray));
    }

    void setTabsInPixels(int[] nArray) {
        if (Arrays.equals(this.tabs, nArray)) {
            return;
        }
        this.freeRuns();
        this.tabs = nArray;
    }

    public void setText(String string) {
        this.checkLayout();
        if (string == null) {
            SWT.error(4);
        }
        if (string.equals(this.text)) {
            return;
        }
        this.freeRuns();
        this.text = string;
        StyleItem[] styleItemArray = new StyleItem[2];
        this.styles = styleItemArray;
        styleItemArray[0] = new StyleItem(this);
        this.styles[1] = new StyleItem(this);
        this.styles[1].start = string.length();
        this.stylesCount = 2;
    }

    public void setTextDirection(int n) {
        this.checkLayout();
        int n2 = 0x6000000;
        if ((n &= 0x6000000) == 0) {
            return;
        }
        if (n != 0x6000000) {
            if ((n & 0x2000000) != 0) {
                n = 0x2000000;
            }
            if (this.textDirection == n) {
                return;
            }
        }
        this.textDirection = n;
        this.freeRuns();
    }

    public void setWidth(int n) {
        this.checkLayout();
        this.setWidthInPixels(n != -1 ? DPIUtil.autoScaleUp((Drawable)this.getDevice(), n) : n);
    }

    void setWidthInPixels(int n) {
        if (n < -1 || n == 0) {
            SWT.error(5);
        }
        if (this.wrapWidth == n) {
            return;
        }
        this.freeRuns();
        this.wrapWidth = n;
    }

    public void setWrapIndent(int n) {
        this.checkLayout();
        this.setWrapIndentInPixels(DPIUtil.autoScaleUp((Drawable)this.getDevice(), n));
    }

    void setWrapIndentInPixels(int n) {
        if (n < 0) {
            return;
        }
        if (this.wrapIndent == n) {
            return;
        }
        this.freeRuns();
        this.wrapIndent = n;
    }

    boolean shape(long l2, StyleItem styleItem, char[] cArray, int[] nArray, int n, SCRIPT_PROPERTIES sCRIPT_PROPERTIES) {
        short[] sArray;
        boolean bl;
        boolean bl2 = bl = !sCRIPT_PROPERTIES.fComplex && !styleItem.analysis.fNoGlyphIndex;
        if (bl && OS.ScriptGetCMap(l2, styleItem.psc, cArray, cArray.length, 0, sArray = new short[cArray.length]) != 0) {
            if (styleItem.psc != 0L) {
                OS.ScriptFreeCache(styleItem.psc);
                nArray[0] = 0;
                OS.MoveMemory(styleItem.psc, new long[1], C.PTR_SIZEOF);
            }
            return false;
        }
        int n2 = OS.ScriptShape(l2, styleItem.psc, cArray, cArray.length, n, styleItem.analysis, styleItem.glyphs, styleItem.clusters, styleItem.visAttrs, nArray);
        if (n2 == 0) {
            int n3;
            styleItem.glyphCount = nArray[0];
            if (bl) {
                return true;
            }
            if (styleItem.analysis.fNoGlyphIndex) {
                return true;
            }
            SCRIPT_FONTPROPERTIES sCRIPT_FONTPROPERTIES = new SCRIPT_FONTPROPERTIES();
            sCRIPT_FONTPROPERTIES.cBytes = SCRIPT_FONTPROPERTIES.sizeof;
            OS.ScriptGetFontProperties(l2, styleItem.psc, sCRIPT_FONTPROPERTIES);
            short[] sArray2 = new short[nArray[0]];
            OS.MoveMemory(sArray2, styleItem.glyphs, sArray2.length * 2);
            for (n3 = 0; n3 < sArray2.length && sArray2[n3] != sCRIPT_FONTPROPERTIES.wgDefault; ++n3) {
            }
            if (n3 == sArray2.length) {
                return true;
            }
        }
        if (styleItem.psc != 0L) {
            OS.ScriptFreeCache(styleItem.psc);
            nArray[0] = 0;
            OS.MoveMemory(styleItem.psc, new long[1], C.PTR_SIZEOF);
        }
        styleItem.glyphCount = 0;
        return false;
    }

    long createMetafileWithChars(long l2, long l3, char[] cArray, int n) {
        long l4 = OS.GetProcessHeap();
        int n2 = n * 2;
        long l5 = OS.HeapAlloc(l4, 8, n2);
        OS.MoveMemory(l5, cArray, n2);
        long l6 = OS.HeapAlloc(l4, 8, OS.SCRIPT_STRING_ANALYSIS_sizeof());
        long l7 = OS.CreateEnhMetaFile(l2, null, null, null);
        long l8 = OS.SelectObject(l7, l3);
        int n3 = 6304;
        if (OS.ScriptStringAnalyse(l7, l5, n, 0, -1, 6304, 0, null, null, 0L, 0L, 0L, l6) == 0) {
            OS.ScriptStringOut(l6, 0, 0, 0, null, 0, 0, false);
            OS.ScriptStringFree(l6);
        }
        OS.HeapFree(l4, 0, l5);
        OS.HeapFree(l4, 0, l6);
        OS.SelectObject(l7, l8);
        return OS.CloseEnhMetaFile(l7);
    }

    void shape(long l2, StyleItem styleItem) {
        if (styleItem.lineBreak) {
            return;
        }
        if (styleItem.glyphs != 0L) {
            return;
        }
        int[] nArray = new int[]{0};
        char[] cArray = new char[styleItem.length];
        this.segmentsText.getChars(styleItem.start, styleItem.start + styleItem.length, cArray, 0);
        int n = cArray.length * 3 / 2 + 16;
        long l3 = OS.GetProcessHeap();
        styleItem.glyphs = OS.HeapAlloc(l3, 8, n * 2);
        if (styleItem.glyphs == 0L) {
            SWT.error(2);
        }
        styleItem.clusters = OS.HeapAlloc(l3, 8, n * 2);
        if (styleItem.clusters == 0L) {
            SWT.error(2);
        }
        styleItem.visAttrs = OS.HeapAlloc(l3, 8, n * 2);
        if (styleItem.visAttrs == 0L) {
            SWT.error(2);
        }
        styleItem.psc = OS.HeapAlloc(l3, 8, C.PTR_SIZEOF);
        if (styleItem.psc == 0L) {
            SWT.error(2);
        }
        short s = styleItem.analysis.eScript;
        SCRIPT_PROPERTIES sCRIPT_PROPERTIES = new SCRIPT_PROPERTIES();
        OS.MoveMemory(sCRIPT_PROPERTIES, this.device.scripts[s], SCRIPT_PROPERTIES.sizeof);
        boolean bl = this.shape(l2, styleItem, cArray, nArray, n, sCRIPT_PROPERTIES);
        if (!bl && sCRIPT_PROPERTIES.fPrivateUseArea) {
            styleItem.analysis.fNoGlyphIndex = true;
            bl = this.shape(l2, styleItem, cArray, nArray, n, sCRIPT_PROPERTIES);
        }
        if (!bl) {
            Object object;
            Object object2;
            Object object3;
            int n2;
            long l4 = OS.GetCurrentObject(l2, 6);
            long l5 = 0L;
            char[] cArray2 = new char[Math.min(cArray.length, 2)];
            SCRIPT_LOGATTR sCRIPT_LOGATTR = new SCRIPT_LOGATTR();
            this.breakRun(styleItem);
            int n3 = 0;
            for (n2 = 0; n2 < cArray.length; ++n2) {
                OS.MoveMemory(sCRIPT_LOGATTR, styleItem.psla + (long)(n2 * SCRIPT_LOGATTR.sizeof), SCRIPT_LOGATTR.sizeof);
                if (sCRIPT_LOGATTR.fWhiteSpace) continue;
                cArray2[n3++] = cArray[n2];
                if (n3 == cArray2.length) break;
            }
            if (n3 > 0) {
                long l6 = this.createMetafileWithChars(l2, l4, cArray2, n3);
                object3 = new EMREXTCREATEFONTINDIRECTW();
                class MetaFileEnumProc {
                    final EMREXTCREATEFONTINDIRECTW val$emr;
                    final TextLayout this$0;

                    MetaFileEnumProc(TextLayout textLayout, EMREXTCREATEFONTINDIRECTW eMREXTCREATEFONTINDIRECTW) {
                        this.this$0 = textLayout;
                        this.val$emr = eMREXTCREATEFONTINDIRECTW;
                    }

                    long metaFileEnumProc(long l2, long l3, long l4, long l5, long l6) {
                        OS.MoveMemory(this.val$emr.emr, l4, EMR.sizeof);
                        switch (this.val$emr.emr.iType) {
                            case 82: {
                                OS.MoveMemory(this.val$emr, l4, EMREXTCREATEFONTINDIRECTW.sizeof);
                                break;
                            }
                            case 84: {
                                return 0L;
                            }
                        }
                        return 1L;
                    }
                }
                object2 = new MetaFileEnumProc(this, (EMREXTCREATEFONTINDIRECTW)object3);
                boolean bl2 = false;
                Callback callback = new Callback(object2, "metaFileEnumProc", 5);
                OS.EnumEnhMetaFile(0L, l6, callback.getAddress(), 0L, null);
                OS.DeleteEnhMetaFile(l6);
                callback.dispose();
                l5 = OS.CreateFontIndirect(((EMREXTCREATEFONTINDIRECTW)object3).elfw.elfLogFont);
            } else {
                for (n2 = 0; n2 < this.allRuns.length - 1; ++n2) {
                    LOGFONT lOGFONT;
                    long l7;
                    if (this.allRuns[n2] != styleItem) continue;
                    if (n2 > 0) {
                        object = this.allRuns[n2 - 1];
                        if (((StyleItem)object).analysis.eScript == styleItem.analysis.eScript) {
                            l7 = this.getItemFont((StyleItem)object);
                            lOGFONT = new LOGFONT();
                            OS.GetObject(l7, LOGFONT.sizeof, lOGFONT);
                            l5 = OS.CreateFontIndirect(lOGFONT);
                        }
                    }
                    if (l5 != 0L || n2 + 1 >= this.allRuns.length - 1) break;
                    object = this.allRuns[n2 + 1];
                    if (((StyleItem)object).analysis.eScript != styleItem.analysis.eScript) break;
                    OS.SelectObject(l2, this.getItemFont((StyleItem)object));
                    this.shape(l2, (StyleItem)object);
                    l7 = this.getItemFont((StyleItem)object);
                    lOGFONT = new LOGFONT();
                    OS.GetObject(l7, LOGFONT.sizeof, lOGFONT);
                    l5 = OS.CreateFontIndirect(lOGFONT);
                    break;
                }
            }
            if (l5 != 0L) {
                OS.SelectObject(l2, l5);
                bl = this.shape(l2, styleItem, cArray, nArray, n, sCRIPT_PROPERTIES);
                if (bl) {
                    styleItem.fallbackFont = l5;
                }
            }
            if (!bl && !sCRIPT_PROPERTIES.fComplex) {
                styleItem.analysis.fNoGlyphIndex = true;
                bl = this.shape(l2, styleItem, cArray, nArray, n, sCRIPT_PROPERTIES);
                if (bl) {
                    styleItem.fallbackFont = l5;
                } else {
                    styleItem.analysis.fNoGlyphIndex = false;
                }
            }
            if (!bl && this.mLangFontLink2 != null) {
                long[] lArray = new long[]{0L};
                object = new int[]{0};
                object3 = new int[]{0};
                this.mLangFontLink2.GetStrCodePages(cArray, cArray.length, 0, (int[])object, (int[])object3);
                if (this.mLangFontLink2.MapFont(l2, (int)object[0], cArray[0], lArray) == 0) {
                    object2 = new LOGFONT();
                    OS.GetObject(lArray[0], LOGFONT.sizeof, (LOGFONT)object2);
                    this.mLangFontLink2.ReleaseFont(lArray[0]);
                    long l8 = OS.CreateFontIndirect((LOGFONT)object2);
                    long l9 = OS.SelectObject(l2, l8);
                    bl = this.shape(l2, styleItem, cArray, nArray, n, sCRIPT_PROPERTIES);
                    if (bl) {
                        styleItem.fallbackFont = l8;
                    } else {
                        OS.SelectObject(l2, l9);
                        OS.DeleteObject(l8);
                    }
                }
            }
            if (!bl) {
                OS.SelectObject(l2, l4);
            }
            if (l5 != 0L && l5 != styleItem.fallbackFont) {
                OS.DeleteObject(l5);
            }
        }
        if (!bl) {
            OS.ScriptShape(l2, styleItem.psc, cArray, cArray.length, n, styleItem.analysis, styleItem.glyphs, styleItem.clusters, styleItem.visAttrs, nArray);
            styleItem.glyphCount = nArray[0];
        }
        int[] nArray2 = new int[3];
        styleItem.advances = OS.HeapAlloc(l3, 8, styleItem.glyphCount * 4);
        if (styleItem.advances == 0L) {
            SWT.error(2);
        }
        styleItem.goffsets = OS.HeapAlloc(l3, 8, styleItem.glyphCount * 8);
        if (styleItem.goffsets == 0L) {
            SWT.error(2);
        }
        OS.ScriptPlace(l2, styleItem.psc, styleItem.glyphs, styleItem.glyphCount, styleItem.visAttrs, styleItem.analysis, styleItem.advances, styleItem.goffsets, nArray2);
        styleItem.width = nArray2[0] + nArray2[1] + nArray2[2];
        TextStyle textStyle = styleItem.style;
        if (textStyle != null) {
            OUTLINETEXTMETRIC oUTLINETEXTMETRIC = null;
            if ((textStyle.underline || textStyle.strikeout) && OS.GetOutlineTextMetrics(l2, OUTLINETEXTMETRIC.sizeof, oUTLINETEXTMETRIC = new OUTLINETEXTMETRIC()) == 0) {
                oUTLINETEXTMETRIC = null;
            }
            if (textStyle.metrics != null) {
                GlyphMetrics glyphMetrics = textStyle.metrics;
                styleItem.width = glyphMetrics.getWidthInPixels() * Math.max(1, styleItem.glyphCount);
                styleItem.ascentInPoints = glyphMetrics.ascent;
                styleItem.descentInPoints = glyphMetrics.descent;
                styleItem.leadingInPoints = 0;
            } else {
                TEXTMETRIC tEXTMETRIC = null;
                if (oUTLINETEXTMETRIC != null) {
                    tEXTMETRIC = oUTLINETEXTMETRIC.otmTextMetrics;
                } else {
                    tEXTMETRIC = new TEXTMETRIC();
                    OS.GetTextMetrics(l2, tEXTMETRIC);
                }
                styleItem.ascentInPoints = DPIUtil.autoScaleDown((Drawable)this.getDevice(), tEXTMETRIC.tmAscent);
                styleItem.descentInPoints = DPIUtil.autoScaleDown((Drawable)this.getDevice(), tEXTMETRIC.tmDescent);
                styleItem.leadingInPoints = DPIUtil.autoScaleDown((Drawable)this.getDevice(), tEXTMETRIC.tmInternalLeading);
            }
            if (oUTLINETEXTMETRIC != null) {
                styleItem.underlinePos = oUTLINETEXTMETRIC.otmsUnderscorePosition;
                styleItem.underlineThickness = Math.max(1, oUTLINETEXTMETRIC.otmsUnderscoreSize);
                styleItem.strikeoutPos = oUTLINETEXTMETRIC.otmsStrikeoutPosition;
                styleItem.strikeoutThickness = Math.max(1, oUTLINETEXTMETRIC.otmsStrikeoutSize);
            } else {
                styleItem.underlinePos = 1;
                styleItem.underlineThickness = 1;
                styleItem.strikeoutPos = DPIUtil.autoScaleUp((Drawable)this.getDevice(), styleItem.ascentInPoints) / 2;
                styleItem.strikeoutThickness = 1;
            }
            styleItem.ascentInPoints += textStyle.rise;
            styleItem.descentInPoints -= textStyle.rise;
        } else {
            TEXTMETRIC tEXTMETRIC = new TEXTMETRIC();
            OS.GetTextMetrics(l2, tEXTMETRIC);
            styleItem.ascentInPoints = DPIUtil.autoScaleDown((Drawable)this.getDevice(), tEXTMETRIC.tmAscent);
            styleItem.descentInPoints = DPIUtil.autoScaleDown((Drawable)this.getDevice(), tEXTMETRIC.tmDescent);
            styleItem.leadingInPoints = DPIUtil.autoScaleDown((Drawable)this.getDevice(), tEXTMETRIC.tmInternalLeading);
        }
    }

    int validadeOffset(int n, int n2) {
        n = this.untranslateOffset(n);
        return this.translateOffset(n + n2);
    }

    public String toString() {
        if (this == null) {
            return "TextLayout {*DISPOSED*}";
        }
        return "TextLayout {}";
    }

    int translateOffset(int n) {
        int n2 = this.text.length();
        if (n2 == 0) {
            return n;
        }
        if (this.segments == null) {
            return n;
        }
        int n3 = this.segments.length;
        if (n3 == 0) {
            return n;
        }
        if (this.segmentsChars == null) {
            if (n3 == 1) {
                return n;
            }
            if (n3 == 2 && this.segments[0] == 0 && this.segments[1] == n2) {
                return n;
            }
        }
        for (int i = 0; i < n3 && n - i >= this.segments[i]; ++i) {
            ++n;
        }
        return n;
    }

    int untranslateOffset(int n) {
        int n2 = this.text.length();
        if (n2 == 0) {
            return n;
        }
        if (this.segments == null) {
            return n;
        }
        int n3 = this.segments.length;
        if (n3 == 0) {
            return n;
        }
        if (this.segmentsChars == null) {
            if (n3 == 1) {
                return n;
            }
            if (n3 == 2 && this.segments[0] == 0 && this.segments[1] == n2) {
                return n;
            }
        }
        for (int i = 0; i < n3 && n > this.segments[i]; --n, ++i) {
        }
        return n;
    }

    public void setDefaultTabWidth(int n) {
    }

    class StyleItem {
        TextStyle style;
        int start;
        int length;
        boolean lineBreak;
        boolean softBreak;
        boolean tab;
        SCRIPT_ANALYSIS analysis;
        long psc;
        long glyphs;
        int glyphCount;
        long clusters;
        long visAttrs;
        long advances;
        long goffsets;
        int width;
        int ascentInPoints;
        int descentInPoints;
        int leadingInPoints;
        int x;
        int underlinePos;
        int underlineThickness;
        int strikeoutPos;
        int strikeoutThickness;
        long justify;
        int pslaAllocSize;
        long psla;
        long fallbackFont;
        final TextLayout this$0;

        StyleItem(TextLayout textLayout) {
            this.this$0 = textLayout;
            this.psc = 0L;
        }

        void free() {
            long l2 = OS.GetProcessHeap();
            if (this.psc != 0L) {
                OS.ScriptFreeCache(this.psc);
                OS.HeapFree(l2, 0, this.psc);
                this.psc = 0L;
            }
            if (this.glyphs != 0L) {
                OS.HeapFree(l2, 0, this.glyphs);
                this.glyphs = 0L;
                this.glyphCount = 0;
            }
            if (this.clusters != 0L) {
                OS.HeapFree(l2, 0, this.clusters);
                this.clusters = 0L;
            }
            if (this.visAttrs != 0L) {
                OS.HeapFree(l2, 0, this.visAttrs);
                this.visAttrs = 0L;
            }
            if (this.advances != 0L) {
                OS.HeapFree(l2, 0, this.advances);
                this.advances = 0L;
            }
            if (this.goffsets != 0L) {
                OS.HeapFree(l2, 0, this.goffsets);
                this.goffsets = 0L;
            }
            if (this.justify != 0L) {
                OS.HeapFree(l2, 0, this.justify);
                this.justify = 0L;
            }
            if (this.psla != 0L) {
                OS.HeapFree(l2, 0, this.psla);
                this.psla = 0L;
            }
            if (this.fallbackFont != 0L) {
                OS.DeleteObject(this.fallbackFont);
                this.fallbackFont = 0L;
            }
            boolean bl = false;
            this.x = 0;
            this.descentInPoints = 0;
            this.ascentInPoints = 0;
            this.width = 0;
            boolean bl2 = false;
            this.softBreak = false;
            this.lineBreak = false;
        }

        public String toString() {
            return "StyleItem {" + this.start + ", " + this.style;
        }
    }
}

