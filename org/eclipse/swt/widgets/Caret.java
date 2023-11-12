/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.CANDIDATEFORM;
import org.eclipse.swt.internal.win32.COMPOSITIONFORM;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.IME;
import org.eclipse.swt.widgets.Widget;

public class Caret
extends Widget {
    private static Caret currentCaret;
    Canvas parent;
    int x;
    int y;
    int width;
    int height;
    boolean moved;
    boolean resized;
    boolean isVisible;
    Image image;
    Font font;
    LOGFONT oldFont;

    public Caret(Canvas canvas, int n) {
        super(canvas, n);
        this.parent = canvas;
        this.createWidget();
    }

    void createWidget() {
        this.isVisible = true;
        if (this.parent.getCaret() == null) {
            this.parent.setCaret(this);
        }
    }

    long defaultFont() {
        long l2 = this.parent.handle;
        long l3 = OS.ImmGetDefaultIMEWnd(l2);
        long l4 = 0L;
        if (l3 != 0L) {
            l4 = OS.SendMessage(l3, 49, 0L, 0L);
        }
        if (l4 == 0L) {
            l4 = OS.SendMessage(l2, 49, 0L, 0L);
        }
        if (l4 == 0L) {
            return this.parent.defaultFont();
        }
        return l4;
    }

    public Rectangle getBounds() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getBoundsInPixels());
    }

    Rectangle getBoundsInPixels() {
        int[] nArray;
        if (this.image != null) {
            Rectangle rectangle = this.image.getBoundsInPixels();
            return new Rectangle(this.x, this.y, rectangle.width, rectangle.height);
        }
        if (this.width == 0 && OS.SystemParametersInfo(8198, 0, nArray = new int[]{0}, 0)) {
            return new Rectangle(this.x, this.y, nArray[0], this.height);
        }
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public Font getFont() {
        this.checkWidget();
        if (this.font == null) {
            long l2 = this.defaultFont();
            return Font.win32_new(this.display, l2);
        }
        return this.font;
    }

    public Image getImage() {
        this.checkWidget();
        return this.image;
    }

    public Point getLocation() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getLocationInPixels());
    }

    Point getLocationInPixels() {
        return new Point(this.x, this.y);
    }

    public Canvas getParent() {
        this.checkWidget();
        return this.parent;
    }

    public Point getSize() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getSizeInPixels());
    }

    Point getSizeInPixels() {
        int[] nArray;
        if (this.image != null) {
            Rectangle rectangle = this.image.getBoundsInPixels();
            return new Point(rectangle.width, rectangle.height);
        }
        if (this.width == 0 && OS.SystemParametersInfo(8198, 0, nArray = new int[]{0}, 0)) {
            return new Point(nArray[0], this.height);
        }
        return new Point(this.width, this.height);
    }

    public boolean getVisible() {
        this.checkWidget();
        return this.isVisible;
    }

    boolean isFocusCaret() {
        return this.parent.caret == this && this == false;
    }

    public boolean isVisible() {
        this.checkWidget();
        return this.isVisible && this.parent.isVisible() && this == false;
    }

    void killFocus() {
        OS.DestroyCaret();
        this.restoreIMEFont();
    }

    void move() {
        this.moved = false;
        this.setCurrentCaret(this);
        if (!OS.SetCaretPos(this.x, this.y)) {
            return;
        }
        this.resizeIME();
    }

    void resizeIME() {
        if (!OS.IsDBLocale) {
            return;
        }
        POINT pOINT = new POINT();
        if (!OS.GetCaretPos(pOINT)) {
            return;
        }
        long l2 = this.parent.handle;
        long l3 = OS.ImmGetContext(l2);
        IME iME = this.parent.getIME();
        if (iME != null && iME.isInlineEnabled()) {
            Point point = this.getSizeInPixels();
            CANDIDATEFORM cANDIDATEFORM = new CANDIDATEFORM();
            cANDIDATEFORM.dwStyle = 128;
            cANDIDATEFORM.ptCurrentPos = pOINT;
            cANDIDATEFORM.rcArea = new RECT();
            OS.SetRect(cANDIDATEFORM.rcArea, pOINT.x, pOINT.y, pOINT.x + point.x, pOINT.y + point.y);
            OS.ImmSetCandidateWindow(l3, cANDIDATEFORM);
        } else {
            RECT rECT = new RECT();
            OS.GetClientRect(l2, rECT);
            COMPOSITIONFORM cOMPOSITIONFORM = new COMPOSITIONFORM();
            cOMPOSITIONFORM.dwStyle = 1;
            cOMPOSITIONFORM.x = pOINT.x;
            cOMPOSITIONFORM.y = pOINT.y;
            cOMPOSITIONFORM.left = rECT.left;
            cOMPOSITIONFORM.right = rECT.right;
            cOMPOSITIONFORM.top = rECT.top;
            cOMPOSITIONFORM.bottom = rECT.bottom;
            OS.ImmSetCompositionWindow(l3, cOMPOSITIONFORM);
        }
        OS.ImmReleaseContext(l2, l3);
    }

    @Override
    void releaseParent() {
        super.releaseParent();
        if (this.parent != null && this == this.parent.caret) {
            if (!this.parent.isDisposed()) {
                this.parent.setCaret(null);
            } else {
                this.parent.caret = null;
            }
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    void releaseWidget() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl4 : IF_ACMPNE - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    void resize() {
        int[] nArray;
        this.resized = false;
        long l2 = this.parent.handle;
        OS.DestroyCaret();
        long l3 = this.image != null ? this.image.handle : 0L;
        int n = this.width;
        if (this.image == null && n == 0 && OS.SystemParametersInfo(8198, 0, nArray = new int[]{0}, 0)) {
            n = nArray[0];
        }
        OS.CreateCaret(l2, l3, n, this.height);
        OS.SetCaretPos(this.x, this.y);
        OS.ShowCaret(l2);
        this.move();
    }

    void restoreIMEFont() {
        if (!OS.IsDBLocale) {
            return;
        }
        if (this.oldFont == null) {
            return;
        }
        long l2 = this.parent.handle;
        long l3 = OS.ImmGetContext(l2);
        OS.ImmSetCompositionFont(l3, this.oldFont);
        OS.ImmReleaseContext(l2, l3);
        this.oldFont = null;
    }

    public void setBounds(int n, int n2, int n3, int n4) {
        this.checkWidget();
        this.setBoundsInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2), DPIUtil.autoScaleUp(n3), DPIUtil.autoScaleUp(n4));
    }

    /*
     * Exception decompiling
     */
    void setBoundsInPixels(int var1, int var2, int var3, int var4) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl30 : IF_ACMPNE - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public void setBounds(Rectangle rectangle) {
        if (rectangle == null) {
            this.error(4);
        }
        this.setBoundsInPixels(DPIUtil.autoScaleUp(rectangle));
    }

    void setBoundsInPixels(Rectangle rectangle) {
        this.setBoundsInPixels(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    void setFocus() {
        int[] nArray;
        long l2 = this.parent.handle;
        long l3 = 0L;
        if (this.image != null) {
            l3 = this.image.handle;
        }
        int n = this.width;
        if (this.image == null && n == 0 && OS.SystemParametersInfo(8198, 0, nArray = new int[]{0}, 0)) {
            n = nArray[0];
        }
        OS.CreateCaret(l2, l3, n, this.height);
        this.move();
        this.setIMEFont();
        if (this.isVisible) {
            OS.ShowCaret(l2);
        }
    }

    public void setFont(Font font) {
        this.checkWidget();
        if (font != null && font.isDisposed()) {
            this.error(5);
        }
        this.font = font;
        if (this == false) {
            this.setIMEFont();
        }
    }

    public void setImage(Image image) {
        this.checkWidget();
        if (image != null && image.isDisposed()) {
            this.error(5);
        }
        this.image = image;
        if (this.isVisible && this == false) {
            this.resize();
        }
    }

    void setIMEFont() {
        LOGFONT lOGFONT;
        if (!OS.IsDBLocale) {
            return;
        }
        long l2 = 0L;
        if (this.font != null) {
            l2 = this.font.handle;
        }
        if (l2 == 0L) {
            l2 = this.defaultFont();
        }
        long l3 = this.parent.handle;
        long l4 = OS.ImmGetContext(l3);
        if (this.oldFont == null) {
            this.oldFont = new LOGFONT();
            if (!OS.ImmGetCompositionFont(l4, this.oldFont)) {
                this.oldFont = null;
            }
        }
        if (OS.GetObject(l2, LOGFONT.sizeof, lOGFONT = new LOGFONT()) != 0) {
            OS.ImmSetCompositionFont(l4, lOGFONT);
        }
        OS.ImmReleaseContext(l3, l4);
    }

    public void setLocation(int n, int n2) {
        this.checkWidget();
        this.setLocationInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2));
    }

    /*
     * Exception decompiling
     */
    void setLocationInPixels(int var1, int var2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl10 : IF_ACMPNE - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private void setCurrentCaret(Caret caret) {
        currentCaret = caret;
    }

    public void setLocation(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        point = DPIUtil.autoScaleUp(point);
        this.setLocationInPixels(point.x, point.y);
    }

    public void setSize(int n, int n2) {
        this.checkWidget();
        this.setSizeInPixels(DPIUtil.autoScaleUp(n), DPIUtil.autoScaleUp(n2));
    }

    /*
     * Exception decompiling
     */
    void setSizeInPixels(int var1, int var2) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl10 : IF_ACMPNE - null : Stack underflow
         *     at org.benf.cfr.reader.bytecode.analysis.stack.StackSim.getChange(StackSim.java:81)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:242)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public void setSize(Point point) {
        this.checkWidget();
        if (point == null) {
            this.error(4);
        }
        point = DPIUtil.autoScaleUp(point);
        this.setSizeInPixels(point.x, point.y);
    }

    public void setVisible(boolean bl) {
        this.checkWidget();
        if (bl == this.isVisible) {
            return;
        }
        this.isVisible = bl;
        long l2 = this.parent.handle;
        if (OS.GetFocus() != l2) {
            return;
        }
        if (!this.isVisible) {
            OS.HideCaret(l2);
        } else {
            if (this.resized) {
                this.resize();
            } else if (this.moved) {
                this.move();
            }
            OS.ShowCaret(l2);
        }
    }
}

