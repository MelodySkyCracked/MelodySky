/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.graphics;

import java.io.Serializable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;

public final class Rectangle
implements Serializable {
    public int x;
    public int y;
    public int width;
    public int height;
    static final long serialVersionUID = 3256439218279428914L;

    public Rectangle(int n, int n2, int n3, int n4) {
        this.x = n;
        this.y = n2;
        this.width = n3;
        this.height = n4;
    }

    public void add(Rectangle rectangle) {
        if (rectangle == null) {
            SWT.error(4);
        }
        int n = this.x < rectangle.x ? this.x : rectangle.x;
        int n2 = this.y < rectangle.y ? this.y : rectangle.y;
        int n3 = this.x + this.width;
        int n4 = rectangle.x + rectangle.width;
        int n5 = n3 > n4 ? n3 : n4;
        n3 = this.y + this.height;
        n4 = rectangle.y + rectangle.height;
        int n6 = n3 > n4 ? n3 : n4;
        this.x = n;
        this.y = n2;
        this.width = n5 - n;
        this.height = n6 - n2;
    }

    public boolean contains(int n, int n2) {
        return n >= this.x && n2 >= this.y && n < this.x + this.width && n2 < this.y + this.height;
    }

    public boolean contains(Point point) {
        if (point == null) {
            SWT.error(4);
        }
        return this.contains(point.x, point.y);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Rectangle)) {
            return false;
        }
        Rectangle rectangle = (Rectangle)object;
        return rectangle.x == this.x && rectangle.y == this.y && rectangle.width == this.width && rectangle.height == this.height;
    }

    public int hashCode() {
        return this.x ^ this.y ^ this.width ^ this.height;
    }

    public void intersect(Rectangle rectangle) {
        if (rectangle == null) {
            SWT.error(4);
        }
        if (this == rectangle) {
            return;
        }
        int n = this.x > rectangle.x ? this.x : rectangle.x;
        int n2 = this.y > rectangle.y ? this.y : rectangle.y;
        int n3 = this.x + this.width;
        int n4 = rectangle.x + rectangle.width;
        int n5 = n3 < n4 ? n3 : n4;
        n3 = this.y + this.height;
        n4 = rectangle.y + rectangle.height;
        int n6 = n3 < n4 ? n3 : n4;
        this.x = n5 < n ? 0 : n;
        this.y = n6 < n2 ? 0 : n2;
        this.width = n5 < n ? 0 : n5 - n;
        this.height = n6 < n2 ? 0 : n6 - n2;
    }

    public Rectangle intersection(Rectangle rectangle) {
        if (rectangle == null) {
            SWT.error(4);
        }
        if (this == rectangle) {
            return new Rectangle(this.x, this.y, this.width, this.height);
        }
        int n = this.x > rectangle.x ? this.x : rectangle.x;
        int n2 = this.y > rectangle.y ? this.y : rectangle.y;
        int n3 = this.x + this.width;
        int n4 = rectangle.x + rectangle.width;
        int n5 = n3 < n4 ? n3 : n4;
        n3 = this.y + this.height;
        n4 = rectangle.y + rectangle.height;
        int n6 = n3 < n4 ? n3 : n4;
        return new Rectangle(n5 < n ? 0 : n, n6 < n2 ? 0 : n2, n5 < n ? 0 : n5 - n, n6 < n2 ? 0 : n6 - n2);
    }

    /*
     * Exception decompiling
     */
    public boolean intersects(Rectangle var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl18 : ICONST_1 - null : trying to set 0 previously set to 3
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
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

    public boolean isEmpty() {
        return this.width <= 0 || this.height <= 0;
    }

    public String toString() {
        return "Rectangle {" + this.x + ", " + this.y + ", " + this.width + ", " + this.height;
    }

    public Rectangle union(Rectangle rectangle) {
        if (rectangle == null) {
            SWT.error(4);
        }
        int n = this.x < rectangle.x ? this.x : rectangle.x;
        int n2 = this.y < rectangle.y ? this.y : rectangle.y;
        int n3 = this.x + this.width;
        int n4 = rectangle.x + rectangle.width;
        int n5 = n3 > n4 ? n3 : n4;
        n3 = this.y + this.height;
        n4 = rectangle.y + rectangle.height;
        int n6 = n3 > n4 ? n3 : n4;
        return new Rectangle(n, n2, n5 - n, n6 - n2);
    }
}

