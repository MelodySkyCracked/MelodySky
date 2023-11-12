/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledCompositeLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

public class ScrolledComposite
extends Composite {
    Control content;
    Listener contentListener;
    Listener filter;
    int minHeight = 0;
    int minWidth = 0;
    boolean expandHorizontal = false;
    boolean expandVertical = false;
    boolean alwaysShowScroll = false;
    boolean showFocusedControl = false;
    boolean showNextFocusedControl = true;

    public ScrolledComposite(Composite composite, int n) {
        super(composite, ScrolledComposite.checkStyle(n));
        ScrollBar scrollBar;
        super.setLayout(new ScrolledCompositeLayout());
        ScrollBar scrollBar2 = this.getHorizontalBar();
        if (scrollBar2 != null) {
            scrollBar2.setVisible(false);
            scrollBar2.addListener(13, this::lambda$new$0);
        }
        if ((scrollBar = this.getVerticalBar()) != null) {
            scrollBar.setVisible(false);
            scrollBar.addListener(13, this::lambda$new$1);
        }
        this.contentListener = this::lambda$new$2;
        this.filter = this::lambda$new$3;
        this.addDisposeListener(this::lambda$new$4);
    }

    static int checkStyle(int n) {
        int n2 = 0x6000B00;
        return n & 0x6000B00;
    }

    public boolean getAlwaysShowScrollBars() {
        return this.alwaysShowScroll;
    }

    public boolean getExpandHorizontal() {
        this.checkWidget();
        return this.expandHorizontal;
    }

    public boolean getExpandVertical() {
        this.checkWidget();
        return this.expandVertical;
    }

    public int getMinWidth() {
        this.checkWidget();
        return this.minWidth;
    }

    public int getMinHeight() {
        this.checkWidget();
        return this.minHeight;
    }

    public Control getContent() {
        return this.content;
    }

    public boolean getShowFocusedControl() {
        this.checkWidget();
        return this.showFocusedControl;
    }

    void hScroll() {
        if (this.content == null) {
            return;
        }
        Point point = this.content.getLocation();
        ScrollBar scrollBar = this.getHorizontalBar();
        int n = scrollBar.getSelection();
        this.content.setLocation(-n, point.y);
    }

    boolean needHScroll(Rectangle rectangle, boolean bl) {
        ScrollBar scrollBar = this.getHorizontalBar();
        if (scrollBar == null) {
            return false;
        }
        Rectangle rectangle2 = this.getBounds();
        int n = this.getBorderWidth();
        Rectangle rectangle3 = rectangle2;
        rectangle3.width -= 2 * n;
        ScrollBar scrollBar2 = this.getVerticalBar();
        if (bl && scrollBar2 != null) {
            Rectangle rectangle4 = rectangle2;
            rectangle4.width -= scrollBar2.getSize().x;
        }
        return !this.expandHorizontal && rectangle.width > rectangle2.width || this.expandHorizontal && this.minWidth > rectangle2.width;
    }

    boolean needVScroll(Rectangle rectangle, boolean bl) {
        ScrollBar scrollBar = this.getVerticalBar();
        if (scrollBar == null) {
            return false;
        }
        Rectangle rectangle2 = this.getBounds();
        int n = this.getBorderWidth();
        Rectangle rectangle3 = rectangle2;
        rectangle3.height -= 2 * n;
        ScrollBar scrollBar2 = this.getHorizontalBar();
        if (bl && scrollBar2 != null) {
            Rectangle rectangle4 = rectangle2;
            rectangle4.height -= scrollBar2.getSize().y;
        }
        return !this.expandVertical && rectangle.height > rectangle2.height || this.expandVertical && this.minHeight > rectangle2.height;
    }

    public Point getOrigin() {
        this.checkWidget();
        if (this.content == null) {
            return new Point(0, 0);
        }
        Point point = this.content.getLocation();
        return new Point(-point.x, -point.y);
    }

    public void setOrigin(Point point) {
        this.setOrigin(point.x, point.y);
    }

    public void setOrigin(int n, int n2) {
        this.checkWidget();
        if (this.content == null) {
            return;
        }
        ScrollBar scrollBar = this.getHorizontalBar();
        if (scrollBar != null) {
            scrollBar.setSelection(n);
            n = -scrollBar.getSelection();
        } else {
            n = 0;
        }
        ScrollBar scrollBar2 = this.getVerticalBar();
        if (scrollBar2 != null) {
            scrollBar2.setSelection(n2);
            n2 = -scrollBar2.getSelection();
        } else {
            n2 = 0;
        }
        this.content.setLocation(n, n2);
    }

    public void setAlwaysShowScrollBars(boolean bl) {
        ScrollBar scrollBar;
        this.checkWidget();
        if (bl == this.alwaysShowScroll) {
            return;
        }
        this.alwaysShowScroll = bl;
        ScrollBar scrollBar2 = this.getHorizontalBar();
        if (scrollBar2 != null && this.alwaysShowScroll) {
            scrollBar2.setVisible(true);
        }
        if ((scrollBar = this.getVerticalBar()) != null && this.alwaysShowScroll) {
            scrollBar.setVisible(true);
        }
        this.layout(false);
    }

    public void setContent(Control control) {
        this.checkWidget();
        if (this.content != null && !this.content.isDisposed()) {
            this.content.removeListener(11, this.contentListener);
            this.content.setBounds(new Rectangle(-200, -200, 0, 0));
        }
        this.content = control;
        ScrollBar scrollBar = this.getVerticalBar();
        ScrollBar scrollBar2 = this.getHorizontalBar();
        if (this.content != null) {
            if (scrollBar != null) {
                scrollBar.setMaximum(0);
                scrollBar.setThumb(0);
                scrollBar.setSelection(0);
            }
            if (scrollBar2 != null) {
                scrollBar2.setMaximum(0);
                scrollBar2.setThumb(0);
                scrollBar2.setSelection(0);
            }
            control.setLocation(0, 0);
            this.layout(false);
            this.content.addListener(11, this.contentListener);
        } else {
            if (scrollBar2 != null) {
                scrollBar2.setVisible(this.alwaysShowScroll);
            }
            if (scrollBar != null) {
                scrollBar.setVisible(this.alwaysShowScroll);
            }
        }
    }

    public void setExpandHorizontal(boolean bl) {
        this.checkWidget();
        if (bl == this.expandHorizontal) {
            return;
        }
        this.expandHorizontal = bl;
        this.layout(false);
    }

    public void setExpandVertical(boolean bl) {
        this.checkWidget();
        if (bl == this.expandVertical) {
            return;
        }
        this.expandVertical = bl;
        this.layout(false);
    }

    @Override
    public void setLayout(Layout layout) {
        this.checkWidget();
    }

    public void setMinHeight(int n) {
        this.setMinSize(this.minWidth, n);
    }

    public void setMinSize(Point point) {
        if (point == null) {
            this.setMinSize(0, 0);
        } else {
            this.setMinSize(point.x, point.y);
        }
    }

    public void setMinSize(int n, int n2) {
        this.checkWidget();
        if (n == this.minWidth && n2 == this.minHeight) {
            return;
        }
        this.minWidth = Math.max(0, n);
        this.minHeight = Math.max(0, n2);
        this.layout(false);
    }

    public void setMinWidth(int n) {
        this.setMinSize(n, this.minHeight);
    }

    public void setShowFocusedControl(boolean bl) {
        this.checkWidget();
        if (this.showFocusedControl == bl) {
            return;
        }
        Display display = this.getDisplay();
        display.removeFilter(15, this.filter);
        display.removeFilter(16, this.filter);
        this.showFocusedControl = bl;
        if (!this.showFocusedControl) {
            return;
        }
        display.addFilter(15, this.filter);
        display.addFilter(16, this.filter);
        Control control = display.getFocusControl();
        ScrolledComposite scrolledComposite = this;
        if (control != null) {
            this.showControl(control);
        }
    }

    public void showControl(Control control) {
        this.checkWidget();
        if (control == null) {
            SWT.error(4);
        }
        if (control.isDisposed()) {
            SWT.error(5);
        }
        ScrolledComposite scrolledComposite = this;
        if (control != null) {
            SWT.error(5);
        }
        Rectangle rectangle = this.getDisplay().map((Control)control.getParent(), (Control)this, control.getBounds());
        Rectangle rectangle2 = this.getClientArea();
        Point point = this.getOrigin();
        if (rectangle.x < 0) {
            point.x = Math.max(0, point.x + rectangle.x);
        } else if (rectangle2.width < rectangle.x + rectangle.width) {
            point.x = Math.max(0, point.x + rectangle.x + Math.min(rectangle.width, rectangle2.width) - rectangle2.width);
        }
        if (rectangle.y < 0) {
            point.y = Math.max(0, point.y + rectangle.y);
        } else if (rectangle2.height < rectangle.y + rectangle.height) {
            point.y = Math.max(0, point.y + rectangle.y + Math.min(rectangle.height, rectangle2.height) - rectangle2.height);
        }
        this.setOrigin(point);
    }

    void vScroll() {
        if (this.content == null) {
            return;
        }
        Point point = this.content.getLocation();
        ScrollBar scrollBar = this.getVerticalBar();
        int n = scrollBar.getSelection();
        this.content.setLocation(point.x, -n);
    }

    private void lambda$new$4(DisposeEvent disposeEvent) {
        this.getDisplay().removeFilter(15, this.filter);
        this.getDisplay().removeFilter(16, this.filter);
    }

    /*
     * Exception decompiling
     */
    private void lambda$new$3(Event var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl45 : RETURN - null : trying to set 1 previously set to 0
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

    private void lambda$new$2(Event event) {
        if (event.type != 11) {
            return;
        }
        this.layout(false);
    }

    private void lambda$new$1(Event event) {
        this.vScroll();
    }

    private void lambda$new$0(Event event) {
        this.hScroll();
    }
}

