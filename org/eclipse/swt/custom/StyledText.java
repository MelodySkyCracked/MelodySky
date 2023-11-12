/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleAttributeAdapter;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleEditableTextListener;
import org.eclipse.swt.accessibility.AccessibleTextExtendedAdapter;
import org.eclipse.swt.custom.BidiSegmentListener;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.DefaultContent;
import org.eclipse.swt.custom.ExtendedModifyListener;
import org.eclipse.swt.custom.LineBackgroundListener;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.MouseNavigator;
import org.eclipse.swt.custom.MovementListener;
import org.eclipse.swt.custom.PaintObjectListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledTextContent;
import org.eclipse.swt.custom.StyledTextDropTargetEffect;
import org.eclipse.swt.custom.StyledTextEvent;
import org.eclipse.swt.custom.StyledTextLineSpacingProvider;
import org.eclipse.swt.custom.StyledTextListener;
import org.eclipse.swt.custom.StyledTextPrintOptions;
import org.eclipse.swt.custom.StyledTextRenderer;
import org.eclipse.swt.custom.TextChangeListener;
import org.eclipse.swt.custom.TextChangedEvent;
import org.eclipse.swt.custom.TextChangingEvent;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.custom.lIIII;
import org.eclipse.swt.custom.lIIIl;
import org.eclipse.swt.custom.lIIIll;
import org.eclipse.swt.custom.lIIlI;
import org.eclipse.swt.custom.lIIlIl;
import org.eclipse.swt.custom.lIIllI;
import org.eclipse.swt.custom.lIlI;
import org.eclipse.swt.custom.llIlI;
import org.eclipse.swt.custom.lllI;
import org.eclipse.swt.custom.llll;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.internal.BidiUtil;
import org.eclipse.swt.internal.Compatibility;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Caret;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.IME;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.TypedListener;

public class StyledText
extends Canvas {
    static final char TAB = '\t';
    static final String PlatformLineDelimiter = System.lineSeparator();
    static final int BIDI_CARET_WIDTH = 3;
    static final int DEFAULT_WIDTH = 64;
    static final int DEFAULT_HEIGHT = 64;
    static final int V_SCROLL_RATE = 50;
    static final int H_SCROLL_RATE = 10;
    static final int PREVIOUS_OFFSET_TRAILING = 0;
    static final int OFFSET_LEADING = 1;
    static final String STYLEDTEXT_KEY = "org.eclipse.swt.internal.cocoa.styledtext";
    static final Comparator SELECTION_COMPARATOR = Comparator.comparingInt(StyledText::getX).thenComparingInt(StyledText::lambda$static$55);
    Color selectionBackground;
    Color selectionForeground;
    StyledTextContent content;
    StyledTextRenderer renderer;
    Listener listener;
    TextChangeListener textChangeListener;
    int verticalScrollOffset = 0;
    int horizontalScrollOffset = 0;
    boolean alwaysShowScroll = true;
    int ignoreResize = 0;
    int topIndex = 0;
    int topIndexY;
    int clientAreaHeight = 0;
    int clientAreaWidth = 0;
    int tabLength = 4;
    int[] tabs;
    int leftMargin;
    int topMargin;
    int rightMargin;
    int bottomMargin;
    Color marginColor;
    int columnX;
    Caret[] carets;
    int[] caretOffsets = new int[]{0};
    int caretAlignment;
    Point[] selection = new Point[]{new Point(0, 0)};
    int[] selectionAnchors = new int[]{0};
    Point clipboardSelection;
    Point doubleClickSelection;
    boolean editable = true;
    boolean wordWrap = false;
    boolean visualWrap = false;
    boolean hasStyleWithVariableHeight = false;
    boolean hasVerticalIndent = false;
    boolean doubleClickEnabled = true;
    boolean overwrite = false;
    int textLimit = -1;
    Map keyActionMap = new HashMap();
    Color background = null;
    Color foreground = null;
    boolean customBackground;
    boolean customForeground;
    boolean enabled = true;
    boolean insideSetEnableCall;
    Clipboard clipboard;
    int clickCount;
    int autoScrollDirection = 0;
    int autoScrollDistance = 0;
    int lastTextChangeStart;
    int lastTextChangeNewLineCount;
    int lastTextChangeNewCharCount;
    int lastTextChangeReplaceLineCount;
    int lastTextChangeReplaceCharCount;
    int lastCharCount = 0;
    int lastLineBottom;
    boolean bidiColoring = false;
    Image leftCaretBitmap = null;
    Image rightCaretBitmap = null;
    int caretDirection = 0;
    int caretWidth = 0;
    Caret defaultCaret = null;
    boolean updateCaretDirection = true;
    boolean dragDetect = true;
    IME ime;
    Cursor cursor;
    int alignment;
    boolean justify;
    int indent;
    int wrapIndent;
    int lineSpacing;
    int alignmentMargin;
    int newOrientation = 0;
    int accCaretOffset;
    Accessible acc;
    AccessibleControlAdapter accControlAdapter;
    AccessibleAttributeAdapter accAttributeAdapter;
    AccessibleEditableTextListener accEditableTextListener;
    AccessibleTextExtendedAdapter accTextExtendedAdapter;
    AccessibleAdapter accAdapter;
    MouseNavigator mouseNavigator;
    boolean middleClickPressed;
    boolean blockSelection;
    int blockXAnchor = -1;
    int blockYAnchor = -1;
    int blockXLocation = -1;
    int blockYLocation = -1;
    static final boolean IS_MAC;
    static final boolean IS_GTK;

    private static int getX(Point point) {
        return point.x;
    }

    public StyledText(Composite composite, int n) {
        super(composite, StyledText.checkStyle(n));
        int n2;
        super.setForeground(this.getForeground());
        super.setDragDetect(false);
        Display display = this.getDisplay();
        if ((n & 8) != 0) {
            this.setEditable(false);
        }
        this.rightMargin = n2 = this.isBidiCaret() ? 2 : 0;
        this.leftMargin = n2;
        if ((n & 4) != 0 && (n & 0x800) != 0) {
            int n3 = 2;
            this.bottomMargin = 2;
            this.rightMargin = 2;
            this.topMargin = 2;
            this.leftMargin = 2;
        }
        this.alignment = n & 0x1024000;
        if (this.alignment == 0) {
            this.alignment = 16384;
        }
        this.clipboard = new Clipboard(display);
        this.installDefaultContent();
        this.renderer = new StyledTextRenderer(this.getDisplay(), this);
        this.renderer.setContent(this.content);
        this.renderer.setFont(this.getFont(), this.tabLength);
        this.ime = new IME(this, 0);
        this.defaultCaret = new Caret(this, 0);
        if ((n & 0x40) != 0) {
            this.setWordWrap(true);
        }
        if (this.isBidiCaret()) {
            this.createCaretBitmaps();
            Runnable runnable = this::lambda$new$1;
            BidiUtil.addLanguageListener(this, runnable);
        }
        this.setCaret(this.defaultCaret);
        this.calculateScrollBars();
        this.createKeyBindings();
        super.setCursor(display.getSystemCursor(19));
        this.installListeners();
        this.initializeAccessible();
        this.setData("DEFAULT_DROP_TARGET_EFFECT", new StyledTextDropTargetEffect(this));
        if (IS_MAC) {
            this.setData(STYLEDTEXT_KEY);
        }
    }

    public void addExtendedModifyListener(ExtendedModifyListener extendedModifyListener) {
        this.checkWidget();
        if (extendedModifyListener == null) {
            SWT.error(4);
        }
        StyledTextListener styledTextListener = new StyledTextListener(extendedModifyListener);
        this.addListener(3000, styledTextListener);
    }

    public void addBidiSegmentListener(BidiSegmentListener bidiSegmentListener) {
        this.checkWidget();
        if (bidiSegmentListener == null) {
            SWT.error(4);
        }
        this.addListener(3007, new StyledTextListener(bidiSegmentListener));
        this.resetCache(0, this.content.getLineCount());
        this.setCaretLocations();
        super.redraw();
    }

    public void addCaretListener(CaretListener caretListener) {
        this.checkWidget();
        if (caretListener == null) {
            SWT.error(4);
        }
        this.addListener(3011, new StyledTextListener(caretListener));
    }

    public void addLineBackgroundListener(LineBackgroundListener lineBackgroundListener) {
        this.checkWidget();
        if (lineBackgroundListener == null) {
            SWT.error(4);
        }
        if (!this.isListening(3001)) {
            this.renderer.clearLineBackground(0, this.content.getLineCount());
        }
        this.addListener(3001, new StyledTextListener(lineBackgroundListener));
    }

    public void addLineStyleListener(LineStyleListener lineStyleListener) {
        this.checkWidget();
        if (lineStyleListener == null) {
            SWT.error(4);
        }
        if (!this.isListening(3002)) {
            this.setStyleRanges(0, 0, null, null, true);
            this.renderer.clearLineStyle(0, this.content.getLineCount());
        }
        this.addListener(3002, new StyledTextListener(lineStyleListener));
        this.setCaretLocations();
    }

    public void addModifyListener(ModifyListener modifyListener) {
        this.checkWidget();
        if (modifyListener == null) {
            SWT.error(4);
        }
        this.addListener(24, new TypedListener(modifyListener));
    }

    public void addPaintObjectListener(PaintObjectListener paintObjectListener) {
        this.checkWidget();
        if (paintObjectListener == null) {
            SWT.error(4);
        }
        this.addListener(3008, new StyledTextListener(paintObjectListener));
    }

    public void addSelectionListener(SelectionListener selectionListener) {
        this.checkWidget();
        if (selectionListener == null) {
            SWT.error(4);
        }
        this.addListener(13, new TypedListener(selectionListener));
    }

    public void addVerifyKeyListener(VerifyKeyListener verifyKeyListener) {
        this.checkWidget();
        if (verifyKeyListener == null) {
            SWT.error(4);
        }
        this.addListener(3005, new StyledTextListener(verifyKeyListener));
    }

    public void addVerifyListener(VerifyListener verifyListener) {
        this.checkWidget();
        if (verifyListener == null) {
            SWT.error(4);
        }
        this.addListener(25, new TypedListener(verifyListener));
    }

    public void addWordMovementListener(MovementListener movementListener) {
        this.checkWidget();
        if (movementListener == null) {
            SWT.error(4);
        }
        this.addListener(3009, new StyledTextListener(movementListener));
        this.addListener(3010, new StyledTextListener(movementListener));
    }

    public void append(String string) {
        this.checkWidget();
        if (string == null) {
            SWT.error(4);
        }
        int n = Math.max(this.getCharCount(), 0);
        this.replaceTextRange(n, 0, string);
    }

    void calculateScrollBars() {
        ScrollBar scrollBar = this.getHorizontalBar();
        ScrollBar scrollBar2 = this.getVerticalBar();
        this.setScrollBars(true);
        if (scrollBar2 != null) {
            scrollBar2.setIncrement(this.getVerticalIncrement());
        }
        if (scrollBar != null) {
            scrollBar.setIncrement(this.getHorizontalIncrement());
        }
    }

    void calculateTopIndex(int n) {
        int n2;
        int n3 = n;
        int n4 = this.topIndex;
        int n5 = this.topIndexY;
        if (this == false) {
            n2 = this.getVerticalIncrement();
            if (n2 == 0) {
                return;
            }
            this.topIndex = Compatibility.ceil(this.getVerticalScrollOffset(), n2);
            if (this.topIndex >= 0) {
                if (this.clientAreaHeight > 0) {
                    int n6 = this.getVerticalScrollOffset() + this.clientAreaHeight;
                    this.topIndexY = this.getLinePixel(this.topIndex);
                    int n7 = this.topIndex * n2;
                    int n8 = n6 - n7;
                    if (n8 < n2) {
                        this.topIndex = this.getVerticalScrollOffset() / n2;
                    }
                } else if (this.topIndex >= this.content.getLineCount()) {
                    this.topIndex = this.content.getLineCount() - 1;
                }
            }
        } else if (n >= 0) {
            n -= this.topIndexY;
            n2 = this.topIndex;
            int n9 = this.content.getLineCount();
            while (n2 < n9 && n > 0) {
                n -= this.renderer.getCachedLineHeight(n2++);
            }
            if (n2 < n9 && -n + this.renderer.getCachedLineHeight(n2) <= this.clientAreaHeight - this.topMargin - this.bottomMargin) {
                this.topIndex = n2;
                this.topIndexY = -n;
            } else {
                this.topIndex = n2 - 1;
                this.topIndexY = -this.renderer.getCachedLineHeight(this.topIndex) - n;
            }
        } else {
            int n10;
            n -= this.topIndexY;
            for (n2 = this.topIndex; n2 > 0 && n + (n10 = this.renderer.getCachedLineHeight(n2 - 1)) <= 0; --n2) {
                n += n10;
            }
            if (n2 == 0 || -n + this.renderer.getCachedLineHeight(n2) <= this.clientAreaHeight - this.topMargin - this.bottomMargin) {
                this.topIndex = n2;
                this.topIndexY = -n;
            } else {
                this.topIndex = n2 - 1;
                this.topIndexY = -this.renderer.getCachedLineHeight(this.topIndex) - n;
            }
        }
        if (this.topIndex < 0) {
            System.err.println("StyledText: topIndex was " + this.topIndex + ", isFixedLineHeight() = " + this.isFixedLineHeight() + ", delta = " + n + ", content.getLineCount() = " + this.content.getLineCount() + ", clientAreaHeight = " + this.clientAreaHeight + ", oldTopIndex = " + n4 + ", oldTopIndexY = " + n5 + ", getVerticalScrollOffset = " + this.getVerticalScrollOffset() + ", oldDelta = " + n3 + ", getVerticalIncrement() = " + this.getVerticalIncrement());
            this.topIndex = 0;
        }
        if (this.topIndex != n4 || n5 != this.topIndexY) {
            n2 = this.renderer.getWidth();
            this.renderer.calculateClientArea();
            if (n2 != this.renderer.getWidth()) {
                this.setScrollBars(false);
            }
        }
    }

    static int checkStyle(int n) {
        if ((n & 4) != 0) {
            n &= 0xFFFFFCBD;
        } else if (((n |= 2) & 0x40) != 0) {
            n &= 0xFFFFFEFF;
        }
        return (n |= 0x20140000) & 0xFEFFFFFF;
    }

    void claimBottomFreeSpace() {
        if (this.ime.getCompositionOffset() != -1) {
            return;
        }
        if (this == false) {
            int n = Math.max(0, this.renderer.getHeight() - this.clientAreaHeight);
            if (n < this.getVerticalScrollOffset()) {
                this.scrollVertical(n - this.getVerticalScrollOffset(), true);
            }
        } else {
            int n = this.getPartialBottomIndex();
            int n2 = this.getLinePixel(n + 1);
            if (this.clientAreaHeight > n2) {
                this.scrollVertical(-this.getAvailableHeightAbove(this.clientAreaHeight - n2), true);
            }
        }
    }

    void claimRightFreeSpace() {
        int n = Math.max(0, this.renderer.getWidth() - this.clientAreaWidth);
        if (n < this.horizontalScrollOffset) {
            this.scrollHorizontal(n - this.horizontalScrollOffset, true);
        }
    }

    void clearBlockSelection(boolean bl, boolean bl2) {
        if (bl) {
            this.resetSelection();
        }
        int n = -1;
        this.blockYAnchor = -1;
        this.blockXAnchor = -1;
        int n2 = -1;
        this.blockYLocation = -1;
        this.blockXLocation = -1;
        this.caretDirection = 0;
        this.updateCaretVisibility();
        super.redraw();
        if (bl2) {
            this.sendSelectionEvent();
        }
    }

    void clearSelection(boolean bl) {
        int n = this.selection[0].x;
        int n2 = this.selection[0].y;
        this.resetSelection();
        if (n2 - n > 0) {
            int n3 = this.content.getCharCount();
            int n4 = Math.min(n, n3);
            int n5 = Math.min(n2, n3);
            if (n5 - n4 > 0) {
                this.internalRedrawRange(n4, n5 - n4);
            }
            if (bl) {
                this.sendSelectionEvent();
            }
        }
    }

    @Override
    public Point computeSize(int n, int n2, boolean bl) {
        int n3;
        this.checkWidget();
        int n4 = (this.getStyle() & 4) != 0 ? 1 : this.content.getLineCount();
        int n5 = 0;
        int n6 = 0;
        if (n == -1 || n2 == -1) {
            Display display = this.getDisplay();
            n3 = display.getClientArea().height;
            for (int i = 0; i < n4; ++i) {
                TextLayout textLayout = this.renderer.getTextLayout(i);
                int n7 = textLayout.getWidth();
                if (this.wordWrap) {
                    textLayout.setWidth(n == 0 ? 1 : (n == -1 ? -1 : Math.max(1, n - this.leftMargin - this.rightMargin)));
                }
                Rectangle rectangle = textLayout.getBounds();
                n5 = Math.max(n5, rectangle.width);
                textLayout.setWidth(n7);
                this.renderer.disposeTextLayout(textLayout);
                if (this == false && (n6 += rectangle.height) > n3) break;
            }
            if (this == false) {
                n6 = n4 * this.renderer.getLineHeight();
            }
        }
        if (n5 == 0) {
            n5 = 64;
        }
        if (n6 == 0) {
            n6 = 64;
        }
        if (n != -1) {
            n5 = n;
        }
        if (n2 != -1) {
            n6 = n2;
        }
        int n8 = this.getLeftMargin() + this.rightMargin + this.getCaretWidth();
        n3 = this.topMargin + this.bottomMargin;
        Rectangle rectangle = this.computeTrim(0, 0, n5 + n8, n6 + n3);
        return new Point(rectangle.width, rectangle.height);
    }

    public void copy() {
        this.checkWidget();
        this.copySelection(1);
    }

    public void copy(int n) {
        this.checkWidget();
        this.copySelection(n);
    }

    public int getAlignment() {
        this.checkWidget();
        return this.alignment;
    }

    public boolean getAlwaysShowScrollBars() {
        this.checkWidget();
        return this.alwaysShowScroll;
    }

    int getAvailableHeightAbove(int n) {
        int n2 = this.verticalScrollOffset;
        if (n2 == -1) {
            int n3 = this.topIndex - 1;
            n2 = -this.topIndexY;
            if (this.topIndexY > 0) {
                n2 += this.renderer.getLineHeight(n3--);
            }
            while (n > n2 && n3 >= 0) {
                n2 += this.renderer.getLineHeight(n3--);
            }
        }
        return Math.min(n, n2);
    }

    int getAvailableHeightBellow(int n) {
        int n2 = this.getPartialBottomIndex();
        int n3 = this.getLinePixel(n2);
        int n4 = this.renderer.getLineHeight(n2);
        int n5 = 0;
        int n6 = this.clientAreaHeight - this.topMargin - this.bottomMargin;
        if (n3 + n4 > n6) {
            n5 = n4 - (n6 - n3);
        }
        int n7 = n2 + 1;
        int n8 = this.content.getLineCount();
        while (n > n5 && n7 < n8) {
            n5 += this.renderer.getLineHeight(n7++);
        }
        return Math.min(n, n5);
    }

    public Color getMarginColor() {
        this.checkWidget();
        return this.marginColor != null ? this.marginColor : this.getBackground();
    }

    String getModelDelimitedText(String string) {
        int n = string.length();
        if (n == 0) {
            return string;
        }
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        StringBuilder stringBuilder = new StringBuilder(n);
        String string2 = this.getLineDelimiter();
        while (n4 < n) {
            if (n2 != -1) {
                n2 = string.indexOf(13, n4);
            }
            if (n3 != -1) {
                n3 = string.indexOf(10, n4);
            }
            if (n3 == -1 && n2 == -1) break;
            if (n2 < n3 && n2 != -1 || n3 == -1) {
                stringBuilder.append(string.substring(n4, n2));
                n4 = n3 == n2 + 1 ? n3 + 1 : n2 + 1;
            } else {
                stringBuilder.append(string.substring(n4, n3));
                n4 = n3 + 1;
            }
            if (this != false) break;
            stringBuilder.append(string2);
        }
        if (n4 < n && (this == false || stringBuilder.length() == 0)) {
            stringBuilder.append(string.substring(n4));
        }
        return stringBuilder.toString();
    }

    void createKeyBindings() {
        int n = this != false ? 0x1000003 : 0x1000004;
        int n2 = this != false ? 0x1000004 : 0x1000003;
        this.setKeyBinding(0x1000001, 0x1000001);
        this.setKeyBinding(0x1000002, 0x1000002);
        if (IS_MAC) {
            this.setKeyBinding(n2 | SWT.MOD1, 0x1000007);
            this.setKeyBinding(n | SWT.MOD1, 0x1000008);
            this.setKeyBinding(0x1000007, 17039367);
            this.setKeyBinding(0x1000008, 17039368);
            this.setKeyBinding(0x1000001 | SWT.MOD1, 17039367);
            this.setKeyBinding(0x1000002 | SWT.MOD1, 17039368);
            this.setKeyBinding(n | SWT.MOD3, 0x1040004);
            this.setKeyBinding(n2 | SWT.MOD3, 17039363);
        } else {
            this.setKeyBinding(0x1000007, 0x1000007);
            this.setKeyBinding(0x1000008, 0x1000008);
            this.setKeyBinding(0x1000007 | SWT.MOD1, 17039367);
            this.setKeyBinding(0x1000008 | SWT.MOD1, 17039368);
            this.setKeyBinding(n | SWT.MOD1, 0x1040004);
            this.setKeyBinding(n2 | SWT.MOD1, 17039363);
        }
        this.setKeyBinding(0x1000005, 0x1000005);
        this.setKeyBinding(0x1000006, 0x1000006);
        this.setKeyBinding(0x1000005 | SWT.MOD1, 17039365);
        this.setKeyBinding(0x1000006 | SWT.MOD1, 17039366);
        this.setKeyBinding(n, 0x1000004);
        this.setKeyBinding(n2, 0x1000003);
        this.setKeyBinding(0x1000001 | SWT.MOD2, 0x1020001);
        this.setKeyBinding(0x1000002 | SWT.MOD2, 0x1020002);
        if (IS_MAC) {
            this.setKeyBinding(n2 | SWT.MOD1 | SWT.MOD2, 16908295);
            this.setKeyBinding(n | SWT.MOD1 | SWT.MOD2, 16908296);
            this.setKeyBinding(0x1000007 | SWT.MOD2, 17170439);
            this.setKeyBinding(0x1000008 | SWT.MOD2, 17170440);
            this.setKeyBinding(0x1000001 | SWT.MOD1 | SWT.MOD2, 17170439);
            this.setKeyBinding(0x1000002 | SWT.MOD1 | SWT.MOD2, 17170440);
            this.setKeyBinding(n | SWT.MOD2 | SWT.MOD3, 17170436);
            this.setKeyBinding(n2 | SWT.MOD2 | SWT.MOD3, 17170435);
        } else {
            this.setKeyBinding(0x1000007 | SWT.MOD2, 16908295);
            this.setKeyBinding(0x1000008 | SWT.MOD2, 16908296);
            this.setKeyBinding(0x1000007 | SWT.MOD1 | SWT.MOD2, 17170439);
            this.setKeyBinding(0x1000008 | SWT.MOD1 | SWT.MOD2, 17170440);
            this.setKeyBinding(n | SWT.MOD1 | SWT.MOD2, 17170436);
            this.setKeyBinding(n2 | SWT.MOD1 | SWT.MOD2, 17170435);
        }
        this.setKeyBinding(0x1000005 | SWT.MOD2, 16908293);
        this.setKeyBinding(0x1000006 | SWT.MOD2, 16908294);
        this.setKeyBinding(0x1000005 | SWT.MOD1 | SWT.MOD2, 17170437);
        this.setKeyBinding(0x1000006 | SWT.MOD1 | SWT.MOD2, 0x1060006);
        this.setKeyBinding(n | SWT.MOD2, 16908292);
        this.setKeyBinding(n2 | SWT.MOD2, 16908291);
        this.setKeyBinding(0x58 | SWT.MOD1, 131199);
        this.setKeyBinding(0x43 | SWT.MOD1, 17039369);
        this.setKeyBinding(0x56 | SWT.MOD1, 16908297);
        if (IS_MAC) {
            this.setKeyBinding(0x7F | SWT.MOD2, 127);
            this.setKeyBinding(8 | SWT.MOD3, 262152);
            this.setKeyBinding(0x7F | SWT.MOD3, 262271);
        } else {
            this.setKeyBinding(0x7F | SWT.MOD2, 131199);
            this.setKeyBinding(0x1000009 | SWT.MOD1, 17039369);
            this.setKeyBinding(0x1000009 | SWT.MOD2, 16908297);
        }
        this.setKeyBinding(8 | SWT.MOD2, 8);
        this.setKeyBinding(8, 8);
        this.setKeyBinding(127, 127);
        this.setKeyBinding(8 | SWT.MOD1, 262152);
        this.setKeyBinding(0x7F | SWT.MOD1, 262271);
        this.setKeyBinding(0x1000009, 0x1000009);
    }

    void createCaretBitmaps() {
        int n = 3;
        Display display = this.getDisplay();
        if (this.leftCaretBitmap != null) {
            if (this.defaultCaret != null && this.leftCaretBitmap.equals(this.defaultCaret.getImage())) {
                this.defaultCaret.setImage(null);
            }
            this.leftCaretBitmap.dispose();
        }
        int n2 = this.renderer.getLineHeight();
        this.leftCaretBitmap = new Image((Device)display, 3, n2);
        GC gC = new GC(this.leftCaretBitmap);
        gC.setBackground(display.getSystemColor(2));
        gC.fillRectangle(0, 0, 3, n2);
        gC.setForeground(display.getSystemColor(1));
        gC.drawLine(0, 0, 0, n2);
        gC.drawLine(0, 0, 2, 0);
        gC.drawLine(0, 1, 1, 1);
        gC.dispose();
        if (this.rightCaretBitmap != null) {
            if (this.defaultCaret != null && this.rightCaretBitmap.equals(this.defaultCaret.getImage())) {
                this.defaultCaret.setImage(null);
            }
            this.rightCaretBitmap.dispose();
        }
        this.rightCaretBitmap = new Image((Device)display, 3, n2);
        gC = new GC(this.rightCaretBitmap);
        gC.setBackground(display.getSystemColor(2));
        gC.fillRectangle(0, 0, 3, n2);
        gC.setForeground(display.getSystemColor(1));
        gC.drawLine(2, 0, 2, n2);
        gC.drawLine(0, 0, 2, 0);
        gC.drawLine(2, 1, 1, 1);
        gC.dispose();
    }

    public void cut() {
        this.checkWidget();
        if (this != true) {
            if (this.blockSelection && this.blockXLocation != -1) {
                this.insertBlockSelectionText('\u0000', 0);
            } else {
                this.doDelete();
            }
        }
    }

    void doAutoScroll(Event event) {
        int n = this.getFirstCaretLine();
        if (event.y > this.clientAreaHeight - this.bottomMargin && n != this.content.getLineCount() - 1) {
            this.doAutoScroll(1024, event.y - (this.clientAreaHeight - this.bottomMargin));
        } else if (event.y < this.topMargin && n != 0) {
            this.doAutoScroll(128, this.topMargin - event.y);
        } else if (event.x < this.leftMargin && !this.wordWrap) {
            this.doAutoScroll(0x1000003, this.leftMargin - event.x);
        } else if (event.x > this.clientAreaWidth - this.rightMargin && !this.wordWrap) {
            this.doAutoScroll(0x1000004, event.x - (this.clientAreaWidth - this.rightMargin));
        } else {
            this.endAutoScroll();
        }
    }

    void doAutoScroll(int n, int n2) {
        this.autoScrollDistance = n2;
        if (this.autoScrollDirection == n) {
            return;
        }
        Runnable runnable = null;
        Display display = this.getDisplay();
        if (n == 128) {
            runnable = new lIlI(this, display);
            this.autoScrollDirection = n;
            display.timerExec(50, runnable);
        } else if (n == 1024) {
            runnable = new lIIIll(this, display);
            this.autoScrollDirection = n;
            display.timerExec(50, runnable);
        } else if (n == 0x1000004) {
            runnable = new lIIIl(this, display);
            this.autoScrollDirection = n;
            display.timerExec(10, runnable);
        } else if (n == 0x1000003) {
            runnable = new lIIllI(this, display);
            this.autoScrollDirection = n;
            display.timerExec(10, runnable);
        }
    }

    void doBackspace() {
        Event event = new Event();
        event.text = "";
        if (Arrays.stream(this.selection).anyMatch(StyledText::lambda$doBackspace$2)) {
            for (int i = this.selection.length - 1; i >= 0; --i) {
                Point point = this.selection[i];
                event.start = point.x;
                event.end = point.y;
                this.sendKeyEvent(event);
            }
        } else {
            for (int i = this.caretOffsets.length - 1; i >= 0; --i) {
                int n = this.caretOffsets[i];
                if (n <= 0) continue;
                int n2 = this.content.getLineAtOffset(n);
                int n3 = this.content.getOffsetAtLine(n2);
                if (n == n3) {
                    n3 = this.content.getOffsetAtLine(n2 - 1);
                    event.start = n3 + this.content.getLine(n2 - 1).length();
                    event.end = n;
                } else {
                    boolean bl = false;
                    String string = this.content.getLine(n2);
                    char c = string.charAt(n - n3 - 1);
                    if ('\udc00' <= c && c <= '\udfff' && n - n3 - 2 >= 0) {
                        c = string.charAt(n - n3 - 2);
                        bl = '\ud800' <= c && c <= '\udbff';
                    }
                    TextLayout textLayout = this.renderer.getTextLayout(n2);
                    int n4 = textLayout.getPreviousOffset(n - n3, bl ? 2 : 1);
                    this.renderer.disposeTextLayout(textLayout);
                    event.start = n4 + n3;
                    event.end = n;
                }
                this.sendKeyEvent(event);
            }
        }
    }

    void doBlockColumn(boolean bl) {
        int n;
        int n2;
        int[] nArray;
        int n3;
        int n4;
        int n5;
        if (this.blockXLocation == -1) {
            this.setBlockSelectionOffset(this.caretOffsets[0], false);
        }
        if ((n5 = this.getOffsetAtPoint(n4 = this.blockXLocation - this.horizontalScrollOffset, n3 = this.blockYLocation - this.getVerticalScrollOffset(), nArray = new int[]{0}, true)) != -1) {
            n2 = this.content.getLineAtOffset(n5 += nArray[0]);
            n = bl ? this.getClusterNext(n5, n2) : this.getClusterPrevious(n5, n2);
            int n6 = n5 = n != n5 ? n : -1;
        }
        if (n5 != -1) {
            this.setBlockSelectionOffset(n5, true);
            this.showCaret();
        } else {
            n2 = bl ? this.renderer.averageCharWidth : -this.renderer.averageCharWidth;
            n = Math.max(this.clientAreaWidth - this.rightMargin - this.leftMargin, this.renderer.getWidth());
            n4 = Math.max(0, Math.min(this.blockXLocation + n2, n)) - this.horizontalScrollOffset;
            this.setBlockSelectionLocation(n4, n3, true);
            Rectangle rectangle = new Rectangle(n4, n3, 0, 0);
            this.showLocation(rectangle, true);
        }
    }

    void doBlockContentStartEnd(boolean bl) {
        if (this.blockXLocation == -1) {
            this.setBlockSelectionOffset(this.caretOffsets[0], false);
        }
        int n = bl ? this.content.getCharCount() : 0;
        this.setBlockSelectionOffset(n, true);
        this.showCaret();
    }

    void doBlockWord(boolean bl) {
        Object object;
        int n;
        int n2;
        int[] nArray;
        int n3;
        int n4;
        int n5;
        if (this.blockXLocation == -1) {
            this.setBlockSelectionOffset(this.caretOffsets[0], false);
        }
        if ((n5 = this.getOffsetAtPoint(n4 = this.blockXLocation - this.horizontalScrollOffset, n3 = this.blockYLocation - this.getVerticalScrollOffset(), nArray = new int[]{0}, true)) != -1) {
            n2 = this.content.getLineAtOffset(n5 += nArray[0]);
            n = this.content.getOffsetAtLine(n2);
            object = this.content.getLine(n2);
            int n6 = ((String)object).length();
            int n7 = n5;
            if (bl) {
                if (n5 < n + n6) {
                    n7 = this.getWordNext(n5, 4);
                }
            } else if (n5 > n) {
                n7 = this.getWordPrevious(n5, 4);
            }
            int n8 = n5 = n7 != n5 ? n7 : -1;
        }
        if (n5 != -1) {
            this.setBlockSelectionOffset(n5, true);
            this.showCaret();
        } else {
            n2 = (bl ? this.renderer.averageCharWidth : -this.renderer.averageCharWidth) * 6;
            n = Math.max(this.clientAreaWidth - this.rightMargin - this.leftMargin, this.renderer.getWidth());
            n4 = Math.max(0, Math.min(this.blockXLocation + n2, n)) - this.horizontalScrollOffset;
            this.setBlockSelectionLocation(n4, n3, true);
            object = new Rectangle(n4, n3, 0, 0);
            this.showLocation((Rectangle)object, true);
        }
    }

    void doBlockLineVertical(boolean bl) {
        if (this.blockXLocation == -1) {
            this.setBlockSelectionOffset(this.caretOffsets[0], false);
        }
        int n = this.blockYLocation - this.getVerticalScrollOffset();
        int n2 = this.getLineIndex(n);
        if (bl) {
            if (n2 > 0) {
                n = this.getLinePixel(n2 - 1);
                this.setBlockSelectionLocation(this.blockXLocation - this.horizontalScrollOffset, n, true);
                if (n < this.topMargin) {
                    this.scrollVertical(n - this.topMargin, true);
                }
            }
        } else {
            int n3 = this.content.getLineCount();
            if (n2 + 1 < n3) {
                n = this.getLinePixel(n2 + 2) - 1;
                this.setBlockSelectionLocation(this.blockXLocation - this.horizontalScrollOffset, n, true);
                int n4 = this.clientAreaHeight - this.bottomMargin;
                if (n > n4) {
                    this.scrollVertical(n - n4, true);
                }
            }
        }
    }

    void doBlockLineHorizontal(boolean bl) {
        int n;
        if (this.blockXLocation == -1) {
            this.setBlockSelectionOffset(this.caretOffsets[0], false);
        }
        int n2 = this.blockXLocation - this.horizontalScrollOffset;
        int n3 = this.blockYLocation - this.getVerticalScrollOffset();
        int n4 = this.getLineIndex(n3);
        int n5 = this.content.getOffsetAtLine(n4);
        String string = this.content.getLine(n4);
        int n6 = string.length();
        int[] nArray = new int[]{0};
        int n7 = this.getOffsetAtPoint(n2, n3, nArray, true);
        if (n7 != -1) {
            n7 = n = n7 + nArray[0];
            if (bl) {
                if (n7 < n5 + n6) {
                    n = n5 + n6;
                }
            } else if (n7 > n5) {
                n = n5;
            }
            n7 = n != n7 ? n : -1;
        } else if (!bl) {
            n7 = n5 + n6;
        }
        if (n7 != -1) {
            this.setBlockSelectionOffset(n7, true);
            this.showCaret();
        } else {
            n = Math.max(this.clientAreaWidth - this.rightMargin - this.leftMargin, this.renderer.getWidth());
            n2 = (bl ? n : 0) - this.horizontalScrollOffset;
            this.setBlockSelectionLocation(n2, n3, true);
            Rectangle rectangle = new Rectangle(n2, n3, 0, 0);
            this.showLocation(rectangle, true);
        }
    }

    void doBlockSelection(boolean bl) {
        if (this.caretOffsets[0] > this.selectionAnchors[0]) {
            this.selection[0].x = this.selectionAnchors[0];
            this.selection[0].y = this.caretOffsets[0];
        } else {
            this.selection[0].x = this.caretOffsets[0];
            this.selection[0].y = this.selectionAnchors[0];
        }
        this.updateCaretVisibility();
        this.setCaretLocations();
        super.redraw();
        if (bl) {
            this.sendSelectionEvent();
        }
        this.sendAccessibleTextCaretMoved();
    }

    void doContent(char c) {
        if (this.blockSelection && this.blockXLocation != -1) {
            this.insertBlockSelectionText(c, 0);
            return;
        }
        for (int i = this.selection.length - 1; i >= 0; --i) {
            Point point = this.selection[i];
            Event event = new Event();
            event.start = point.x;
            event.end = point.y;
            if (c == '\r' || c == '\n') {
                if (this != false) {
                    event.text = this.getLineDelimiter();
                }
            } else if (point.x == point.y && this.overwrite && c != '\t') {
                String string;
                int n = this.content.getLineAtOffset(event.end);
                int n2 = this.content.getOffsetAtLine(n);
                if (event.end < n2 + (string = this.content.getLine(n)).length()) {
                    Event event2 = event;
                    ++event2.end;
                }
                event.text = new String(new char[]{c});
            } else {
                event.text = new String(new char[]{c});
            }
            if (event.text == null) continue;
            if (this.textLimit > 0 && this.content.getCharCount() - (event.end - event.start) >= this.textLimit) {
                return;
            }
            this.sendKeyEvent(event);
        }
    }

    void doContentEnd() {
        if (this != false) {
            this.doLineEnd();
        } else {
            int n = this.content.getCharCount();
            this.setCaretOffsets(new int[]{n}, -1);
            this.showCaret();
        }
    }

    void doContentStart() {
        this.setCaretOffsets(new int[]{0}, -1);
        this.showCaret();
    }

    void doCursorPrevious() {
        if (Arrays.stream(this.selection).anyMatch(StyledText::lambda$doCursorPrevious$3)) {
            this.setCaretOffsets(Arrays.stream(this.selection).mapToInt(StyledText::lambda$doCursorPrevious$4).toArray(), 1);
            this.showCaret();
        } else {
            this.doSelectionCursorPrevious();
        }
    }

    void doCursorNext() {
        if (Arrays.stream(this.selection).anyMatch(StyledText::lambda$doCursorNext$5)) {
            this.setCaretOffsets(Arrays.stream(this.selection).mapToInt(StyledText::lambda$doCursorNext$6).toArray(), 0);
            this.showCaret();
        } else {
            this.doSelectionCursorNext();
        }
    }

    void doDelete() {
        Event event = new Event();
        event.text = "";
        Point point2 = null;
        if (Arrays.stream(this.selection).anyMatch(StyledText::lambda$doDelete$7)) {
            for (Point point2 : this.selection) {
                event.start = point2.x;
                event.end = point2.y;
                this.sendKeyEvent(event);
            }
        } else {
            for (int i = this.caretOffsets.length - 1; i >= 0; --i) {
                int n;
                int n2 = this.caretOffsets[i];
                if (n2 >= this.content.getCharCount()) continue;
                int n3 = this.content.getLineAtOffset(n2);
                int n4 = this.content.getOffsetAtLine(n3);
                if (n2 == n4 + (n = this.content.getLine(n3).length())) {
                    event.start = n2;
                    event.end = this.content.getOffsetAtLine(n3 + 1);
                } else {
                    event.start = n2;
                    event.end = this.getClusterNext(n2, n3);
                }
                this.sendKeyEvent(event);
            }
        }
    }

    void doDeleteWordNext() {
        if (Arrays.stream(this.selection).anyMatch(StyledText::lambda$doDeleteWordNext$8)) {
            this.doDelete();
        } else {
            for (int i = this.caretOffsets.length - 1; i >= 0; --i) {
                int n = this.caretOffsets[i];
                Event event = new Event();
                event.text = "";
                event.start = n;
                event.end = this.getWordNext(n, 4);
                this.sendKeyEvent(event);
            }
        }
    }

    void doDeleteWordPrevious() {
        if (Arrays.stream(this.selection).anyMatch(StyledText::lambda$doDeleteWordPrevious$9)) {
            this.doBackspace();
        } else {
            for (int i = this.caretOffsets.length - 1; i >= 0; --i) {
                int n = this.caretOffsets[i];
                Event event = new Event();
                event.text = "";
                event.start = this.getWordPrevious(n, 4);
                event.end = n;
                this.sendKeyEvent(event);
            }
        }
    }

    void doLineDown(boolean bl) {
        int n;
        int n2;
        int n3;
        int n4;
        int[] nArray = new int[this.caretOffsets.length];
        int n5 = this.content.getLineCount();
        int[] nArray2 = new int[]{0};
        for (n4 = 0; n4 < this.caretOffsets.length; ++n4) {
            n3 = this.caretOffsets[n4];
            n2 = this.content.getLineAtOffset(n3);
            n = this.caretOffsets.length == 1 ? this.columnX : this.getPointAtOffset((int)n3).x;
            int n6 = 0;
            boolean bl2 = false;
            if (this == false) {
                int n7;
                int n8 = this.content.getOffsetAtLine(n2);
                int n9 = n3 - n8;
                TextLayout textLayout = this.renderer.getTextLayout(n2);
                int n10 = this.getVisualLineIndex(textLayout, n9);
                if (n10 == (n7 = textLayout.getLineCount()) - 1) {
                    bl2 = n2 == n5 - 1;
                    ++n2;
                } else {
                    n6 = textLayout.getLineBounds((int)(n10 + 1)).y;
                    ++n6;
                }
                this.renderer.disposeTextLayout(textLayout);
            } else {
                bl2 = n2 == n5 - 1;
                ++n2;
            }
            nArray[n4] = bl2 ? this.content.getCharCount() : this.getOffsetAtPoint(n, n6, n2, nArray2);
        }
        n4 = this.content.getLineAtOffset(nArray[nArray.length - 1]) == n5 - 1 ? 1 : 0;
        this.setCaretOffsets(nArray, n4 != 0 ? -1 : nArray2[0]);
        n3 = this.columnX;
        n2 = this.horizontalScrollOffset;
        if (bl) {
            this.setMouseWordSelectionAnchor();
            this.doSelection(0x1000004);
        }
        this.showCaret();
        n = n2 - this.horizontalScrollOffset;
        this.columnX = n3 + n;
    }

    void doLineEnd() {
        int[] nArray = new int[this.caretOffsets.length];
        for (int i = 0; i < this.caretOffsets.length; ++i) {
            int n;
            int n2 = this.caretOffsets[i];
            int n3 = this.content.getLineAtOffset(n2);
            int n4 = this.content.getOffsetAtLine(n3);
            if (this == false) {
                TextLayout textLayout = this.renderer.getTextLayout(n3);
                int n5 = n2 - n4;
                int n6 = this.getVisualLineIndex(textLayout, n5);
                int[] nArray2 = textLayout.getLineOffsets();
                n = n4 + nArray2[n6 + 1];
                this.renderer.disposeTextLayout(textLayout);
            } else {
                int n7 = this.content.getLine(n3).length();
                n = n4 + n7;
            }
            nArray[i] = n;
        }
        this.setCaretOffsets(nArray, 0);
        this.showCaret();
    }

    void doLineStart() {
        int[] nArray = new int[this.caretOffsets.length];
        for (int i = 0; i < this.caretOffsets.length; ++i) {
            int n = this.caretOffsets[i];
            int n2 = this.content.getLineAtOffset(n);
            int n3 = this.content.getOffsetAtLine(n2);
            if (this == false) {
                TextLayout textLayout = this.renderer.getTextLayout(n2);
                int n4 = n - n3;
                int n5 = this.getVisualLineIndex(textLayout, n4);
                int[] nArray2 = textLayout.getLineOffsets();
                n3 += nArray2[n5];
                this.renderer.disposeTextLayout(textLayout);
            }
            nArray[i] = n3;
        }
        this.setCaretOffsets(nArray, 1);
        this.showCaret();
    }

    void doLineUp(boolean bl) {
        int n;
        int n2;
        int n3;
        int[] nArray = new int[this.caretOffsets.length];
        int[] nArray2 = new int[]{0};
        for (n3 = 0; n3 < this.caretOffsets.length; ++n3) {
            n2 = this.caretOffsets[n3];
            n = this.content.getLineAtOffset(n2);
            int n4 = this.caretOffsets.length == 1 ? this.columnX : this.getPointAtOffset((int)n2).x;
            int n5 = 0;
            boolean bl2 = false;
            if (this == false) {
                int n6 = this.content.getOffsetAtLine(n);
                int n7 = n2 - n6;
                TextLayout textLayout = this.renderer.getTextLayout(n);
                int n8 = this.getVisualLineIndex(textLayout, n7);
                if (n8 == 0) {
                    boolean bl3 = bl2 = n == 0;
                    if (!bl2) {
                        n5 = this.renderer.getLineHeight(--n) - 1;
                        --n5;
                    }
                } else {
                    n5 = textLayout.getLineBounds((int)(n8 - 1)).y;
                    ++n5;
                }
                this.renderer.disposeTextLayout(textLayout);
            } else {
                bl2 = n == 0;
                --n;
            }
            nArray[n3] = bl2 ? 0 : this.getOffsetAtPoint(n4, n5, n, nArray2);
        }
        this.setCaretOffsets(nArray, nArray[0] == 0 ? -1 : nArray2[0]);
        n3 = this.columnX;
        n2 = this.horizontalScrollOffset;
        if (bl) {
            this.setMouseWordSelectionAnchor();
        }
        this.showCaret();
        if (bl) {
            this.doSelection(0x1000003);
        }
        n = n2 - this.horizontalScrollOffset;
        this.columnX = n3 + n;
    }

    void doMouseLinkCursor() {
        Display display = this.getDisplay();
        Point point = display.getCursorLocation();
        point = display.map(null, (Control)this, point);
        this.doMouseLinkCursor(point.x, point.y);
    }

    void doMouseLinkCursor(int n, int n2) {
        int n3 = this.getOffsetAtPoint(n, n2, null, true);
        Display display = this.getDisplay();
        Cursor cursor = this.cursor;
        if (this.renderer.hasLink(n3)) {
            cursor = display.getSystemCursor(21);
        } else if (this.cursor == null) {
            int n4 = this.blockSelection ? 2 : 19;
            cursor = display.getSystemCursor(n4);
        }
        if (cursor != this.getCursor()) {
            super.setCursor(cursor);
        }
    }

    void doMouseLocationChange(int n, int n2, boolean bl) {
        boolean bl2;
        int[] nArray;
        int n3;
        int n4 = this.getLineIndex(n2);
        this.updateCaretDirection = true;
        if (this.blockSelection) {
            n = Math.max(this.leftMargin, Math.min(n, this.clientAreaWidth - this.rightMargin));
            n2 = Math.max(this.topMargin, Math.min(n2, this.clientAreaHeight - this.bottomMargin));
            if (this.doubleClickEnabled && this.clickCount > 1) {
                boolean bl3;
                boolean bl4 = bl3 = (this.clickCount & 1) == 0;
                if (bl3) {
                    Point point = this.getPointAtOffset(this.doubleClickSelection.x);
                    int[] nArray2 = new int[]{0};
                    int n5 = this.getOffsetAtPoint(n, n2, nArray2, true);
                    if (n5 != -1) {
                        if (n > point.x) {
                            n5 = this.getWordNext(n5 + nArray2[0], 8);
                            this.setBlockSelectionOffset(this.doubleClickSelection.x, n5, true);
                        } else {
                            n5 = this.getWordPrevious(n5 + nArray2[0], 16);
                            this.setBlockSelectionOffset(this.doubleClickSelection.y, n5, true);
                        }
                    } else if (n > point.x) {
                        this.setBlockSelectionLocation(point.x, point.y, n, n2, true);
                    } else {
                        Point point2 = this.getPointAtOffset(this.doubleClickSelection.y);
                        this.setBlockSelectionLocation(point2.x, point2.y, n, n2, true);
                    }
                } else {
                    this.setBlockSelectionLocation(this.blockXLocation, n2, true);
                }
                return;
            }
            if (bl) {
                if (this.blockXLocation == -1) {
                    this.setBlockSelectionOffset(this.caretOffsets[0], false);
                }
            } else {
                this.clearBlockSelection(true, false);
            }
            if ((n3 = this.getOffsetAtPoint(n, n2, nArray = new int[]{0}, true)) == -1) {
                if (this == false && this.renderer.fixedPitch) {
                    int n6 = this.renderer.averageCharWidth;
                    n = (n + n6 / 2 - this.leftMargin + this.horizontalScrollOffset) / n6 * n6 + this.leftMargin - this.horizontalScrollOffset;
                }
                this.setBlockSelectionLocation(n, n2, true);
                return;
            }
            if (bl) {
                this.setBlockSelectionOffset(n3 + nArray[0], true);
                return;
            }
        }
        if (n4 < 0 || this != false && n4 > 0) {
            return;
        }
        nArray = new int[]{0};
        n3 = this.getOffsetAtPoint(n, n2, nArray);
        int n7 = nArray[0];
        if (this.doubleClickEnabled && this.clickCount > 1) {
            n3 = this.doMouseWordSelect(n, n3, n4);
        }
        int n8 = this.content.getLineAtOffset(n3);
        boolean bl5 = 0 <= n2 && n2 < this.clientAreaHeight || n8 == 0 || n8 == this.content.getLineCount() - 1;
        boolean bl6 = bl2 = 0 <= n && n < this.clientAreaWidth || this.wordWrap || n8 != this.content.getLineAtOffset(this.caretOffsets[0]);
        if (bl5 && bl2 && (n3 != this.caretOffsets[0] || n7 != this.caretAlignment)) {
            this.setCaretOffsets(new int[]{n3}, n7);
            if (bl) {
                this.doMouseSelection();
            }
            this.showCaret();
        }
        if (!bl) {
            this.setCaretOffsets(new int[]{n3}, n7);
            this.clearSelection(true);
        }
    }

    void doMouseSelection() {
        if (this.caretOffsets[0] <= this.selection[0].x || this.caretOffsets[0] > this.selection[0].x && this.caretOffsets[0] < this.selection[0].y && this.selectionAnchors[0] == this.selection[0].x) {
            this.doSelection(0x1000003);
        } else {
            this.doSelection(0x1000004);
        }
    }

    int doMouseWordSelect(int n, int n2, int n3) {
        if (n2 < this.selectionAnchors[0] && this.selectionAnchors[0] == this.selection[0].x) {
            this.selectionAnchors[0] = this.doubleClickSelection.y;
        } else if (n2 > this.selectionAnchors[0] && this.selectionAnchors[0] == this.selection[0].y) {
            this.selectionAnchors[0] = this.doubleClickSelection.x;
        }
        if (0 <= n && n < this.clientAreaWidth) {
            boolean bl;
            boolean bl2 = bl = (this.clickCount & 1) == 0;
            if (this.caretOffsets[0] == this.selection[0].x) {
                n2 = bl ? this.getWordPrevious(n2, 16) : this.content.getOffsetAtLine(n3);
            } else if (bl) {
                n2 = this.getWordNext(n2, 8);
            } else {
                int n4 = this.content.getCharCount();
                if (n3 + 1 < this.content.getLineCount()) {
                    n4 = this.content.getOffsetAtLine(n3 + 1);
                }
                n2 = n4;
            }
        }
        return n2;
    }

    void doPageDown(boolean bl, int n) {
        int n2;
        if (this != false) {
            return;
        }
        int n3 = this.columnX;
        int n4 = this.horizontalScrollOffset;
        if (this == false) {
            n2 = this.content.getLineCount();
            int n5 = this.getFirstCaretLine();
            if (n5 < n2 - 1) {
                int n6 = this.renderer.getLineHeight();
                int n7 = (n == -1 ? this.clientAreaHeight : n) / n6;
                int n8 = Math.min(n2 - n5 - 1, n7);
                n8 = Math.max(1, n8);
                int[] nArray = new int[]{0};
                int n9 = this.getOffsetAtPoint(this.columnX, this.getLinePixel(n5 + n8), nArray);
                this.setCaretOffsets(new int[]{n9}, nArray[0]);
                if (bl) {
                    this.doSelection(0x1000004);
                }
                int n10 = n2 * this.getVerticalIncrement();
                int n11 = this.clientAreaHeight;
                int n12 = this.getVerticalScrollOffset();
                int n13 = n12 + n8 * this.getVerticalIncrement();
                if (n13 + n11 > n10) {
                    n13 = n10 - n11;
                }
                if (n13 > n12) {
                    this.scrollVertical(n13 - n12, true);
                }
            }
        } else {
            Object object;
            int n142;
            int n15;
            int n16;
            n2 = this.content.getLineCount();
            if (n == -1) {
                n16 = this.getPartialBottomIndex();
                n15 = this.getLinePixel(n16);
                int n17 = this.renderer.getLineHeight(n16);
                n = n15;
                if (n15 + n17 <= this.clientAreaHeight) {
                    n += n17;
                } else if (this == false) {
                    TextLayout textLayout = this.renderer.getTextLayout(n16);
                    n142 = this.clientAreaHeight - n15;
                    for (int i = 0; i < textLayout.getLineCount(); ++i) {
                        object = textLayout.getLineBounds(i);
                        if (!((Rectangle)object).contains(((Rectangle)object).x, n142)) continue;
                        n += ((Rectangle)object).y;
                        break;
                    }
                    this.renderer.disposeTextLayout(textLayout);
                }
            } else {
                n16 = this.getLineIndex(n);
                n15 = this.getLinePixel(n16);
                if (this == false) {
                    TextLayout textLayout = this.renderer.getTextLayout(n16);
                    int n18 = n - n15;
                    for (n142 = 0; n142 < textLayout.getLineCount(); ++n142) {
                        Rectangle rectangle = textLayout.getLineBounds(n142);
                        if (!rectangle.contains(rectangle.x, n18)) continue;
                        n = n15 + rectangle.y + rectangle.height;
                        break;
                    }
                    this.renderer.disposeTextLayout(textLayout);
                } else {
                    n = n15 + this.renderer.getLineHeight(n16);
                }
            }
            n16 = n;
            if (this == false) {
                for (int n142 : this.caretOffsets) {
                    int n19 = this.content.getLineAtOffset(n142);
                    object = this.renderer.getTextLayout(n19);
                    int n20 = n142 - this.content.getOffsetAtLine(n19);
                    int n21 = this.getVisualLineIndex((TextLayout)object, n20);
                    n16 += ((TextLayout)object).getLineBounds((int)n21).y;
                    this.renderer.disposeTextLayout((TextLayout)object);
                }
            }
            int n22 = this.getFirstCaretLine();
            int n23 = this.renderer.getLineHeight(n22);
            while (n16 - n23 >= 0 && n22 < n2 - 1) {
                n16 -= n23;
                n23 = this.renderer.getLineHeight(++n22);
            }
            int[] nArray = new int[]{0};
            n142 = this.getOffsetAtPoint(this.columnX, n16, n22, nArray);
            this.setCaretOffsets(new int[]{n142}, nArray[0]);
            if (bl) {
                this.doSelection(0x1000004);
            }
            n = this.getAvailableHeightBellow(n);
            this.scrollVertical(n, true);
            if (n == 0) {
                this.setCaretLocations();
            }
        }
        this.showCaret();
        n2 = n4 - this.horizontalScrollOffset;
        this.columnX = n3 + n2;
    }

    void doPageEnd() {
        if (this != false) {
            this.doLineEnd();
        } else if (this.caretOffsets.length == 1) {
            int n;
            if (this == false) {
                int n2;
                int n3 = this.getPartialBottomIndex();
                TextLayout textLayout = this.renderer.getTextLayout(n3);
                int n4 = this.clientAreaHeight - this.bottomMargin - this.getLinePixel(n3);
                for (n2 = textLayout.getLineCount() - 1; n2 >= 0; --n2) {
                    Rectangle rectangle = textLayout.getLineBounds(n2);
                    if (n4 >= rectangle.y + rectangle.height) break;
                }
                n = n2 == -1 && n3 > 0 ? this.content.getOffsetAtLine(n3 - 1) + this.content.getLine(n3 - 1).length() : this.content.getOffsetAtLine(n3) + Math.max(0, textLayout.getLineOffsets()[n2 + 1] - 1);
                this.renderer.disposeTextLayout(textLayout);
            } else {
                int n5 = this.getBottomIndex();
                n = this.content.getOffsetAtLine(n5) + this.content.getLine(n5).length();
            }
            if (this.caretOffsets[0] < n) {
                this.setCaretOffsets(new int[]{n}, 1);
                this.showCaret();
            }
        }
    }

    void doPageStart() {
        int n;
        if (this == false) {
            int n2;
            int n3;
            int n4;
            if (this.topIndexY > 0) {
                n4 = this.topIndex - 1;
                n3 = this.renderer.getLineHeight(n4) - this.topIndexY;
            } else {
                n4 = this.topIndex;
                n3 = -this.topIndexY;
            }
            TextLayout textLayout = this.renderer.getTextLayout(n4);
            int n5 = textLayout.getLineCount();
            for (n2 = 0; n2 < n5; ++n2) {
                Rectangle rectangle = textLayout.getLineBounds(n2);
                if (n3 <= rectangle.y) break;
            }
            n = n2 == n5 ? this.content.getOffsetAtLine(n4 + 1) : this.content.getOffsetAtLine(n4) + textLayout.getLineOffsets()[n2];
            this.renderer.disposeTextLayout(textLayout);
        } else {
            n = this.content.getOffsetAtLine(this.topIndex);
        }
        if (this.caretOffsets[0] > n) {
            this.setCaretOffsets(new int[]{n}, 1);
            this.showCaret();
        }
    }

    void doPageUp(boolean bl, int n) {
        int n2;
        if (this != false) {
            return;
        }
        int n3 = this.horizontalScrollOffset;
        int n4 = this.columnX;
        if (this == false) {
            n2 = this.getFirstCaretLine();
            if (n2 > 0) {
                int n5;
                int n6;
                int n7 = this.renderer.getLineHeight();
                int n8 = (n == -1 ? this.clientAreaHeight : n) / n7;
                int n9 = Math.max(1, Math.min(n2, n8));
                int[] nArray = new int[]{0};
                int n10 = this.getOffsetAtPoint(this.columnX, this.getLinePixel(n2 -= n9), nArray);
                this.setCaretOffsets(new int[]{n10}, nArray[0]);
                if (bl) {
                    this.doSelection(0x1000003);
                }
                if ((n6 = Math.max(0, (n5 = this.getVerticalScrollOffset()) - n9 * this.getVerticalIncrement())) < n5) {
                    this.scrollVertical(n6 - n5, true);
                }
            }
        } else {
            Rectangle rectangle;
            int n112;
            int n12;
            if (n == -1) {
                if (this.topIndexY == 0) {
                    n = this.clientAreaHeight;
                } else {
                    int n13;
                    if (this.topIndex > 0) {
                        n2 = this.topIndex - 1;
                        n12 = this.renderer.getLineHeight(n2);
                        n = this.clientAreaHeight - this.topIndexY;
                        n13 = n12 - this.topIndexY;
                    } else {
                        n2 = this.topIndex;
                        n12 = this.renderer.getLineHeight(n2);
                        n = this.clientAreaHeight - (n12 + this.topIndexY);
                        n13 = -this.topIndexY;
                    }
                    if (this == false) {
                        TextLayout textLayout = this.renderer.getTextLayout(n2);
                        for (n112 = 0; n112 < textLayout.getLineCount(); ++n112) {
                            rectangle = textLayout.getLineBounds(n112);
                            if (!rectangle.contains(rectangle.x, n13)) continue;
                            n += n12 - (rectangle.y + rectangle.height);
                            break;
                        }
                        this.renderer.disposeTextLayout(textLayout);
                    }
                }
            } else {
                n2 = this.getLineIndex(this.clientAreaHeight - n);
                n12 = this.getLinePixel(n2);
                if (this == false) {
                    TextLayout textLayout = this.renderer.getTextLayout(n2);
                    int n14 = n12;
                    for (n112 = 0; n112 < textLayout.getLineCount(); ++n112) {
                        rectangle = textLayout.getLineBounds(n112);
                        if (!rectangle.contains(rectangle.x, n14)) continue;
                        n = this.clientAreaHeight - (n12 + rectangle.y);
                        break;
                    }
                    this.renderer.disposeTextLayout(textLayout);
                } else {
                    n = this.clientAreaHeight - n12;
                }
            }
            n2 = n;
            if (this == false) {
                for (int n112 : this.caretOffsets) {
                    int n15 = this.content.getLineAtOffset(n112);
                    TextLayout textLayout = this.renderer.getTextLayout(n15);
                    int n16 = n112 - this.content.getOffsetAtLine(n15);
                    int n17 = this.getVisualLineIndex(textLayout, n16);
                    n2 += textLayout.getBounds().height - textLayout.getLineBounds((int)n17).y;
                    this.renderer.disposeTextLayout(textLayout);
                }
            }
            int n18 = this.getFirstCaretLine();
            int n19 = this.renderer.getLineHeight(n18);
            while (n2 - n19 >= 0 && n18 > 0) {
                n2 -= n19;
                n19 = this.renderer.getLineHeight(--n18);
            }
            int n20 = this.renderer.getLineHeight(n18);
            int[] nArray = new int[]{0};
            int n21 = this.getOffsetAtPoint(this.columnX, n20 - n2, n18, nArray);
            this.setCaretOffsets(new int[]{n21}, nArray[0]);
            if (bl) {
                this.doSelection(0x1000003);
            }
            n = this.getAvailableHeightAbove(n);
            this.scrollVertical(-n, true);
            if (n == 0) {
                this.setCaretLocations();
            }
        }
        this.showCaret();
        n2 = n3 - this.horizontalScrollOffset;
        this.columnX = n4 + n2;
    }

    void doSelection(int n) {
        int n2;
        if (this.caretOffsets.length != this.selection.length) {
            return;
        }
        if (this.selectionAnchors.length != this.selection.length) {
            this.selectionAnchors = new int[this.selection.length];
            Arrays.fill(this.selectionAnchors, -1);
        }
        boolean bl = false;
        Point point = null;
        Point[] pointArray = (Point[])Arrays.stream(this.selection).map(StyledText::lambda$doSelection$10).toArray(StyledText::lambda$doSelection$11);
        boolean[] blArray = new boolean[pointArray.length];
        for (int i = 0; i < this.caretOffsets.length; ++i) {
            int n3;
            int n4;
            n2 = this.caretOffsets[i];
            Point point2 = pointArray[i];
            int n5 = this.selectionAnchors[i];
            if (n5 == -1) {
                int n6;
                int[] nArray = this.selectionAnchors;
                n4 = i;
                nArray[n4] = n6 = point2.x;
                n5 = n6;
            }
            int n7 = -1;
            n4 = -1;
            if (n == 0x1000003) {
                if (n2 < point2.x) {
                    blArray[i] = true;
                    n4 = point2.x;
                    Point point3 = point2;
                    point3.x = n3 = n2;
                    n7 = n3;
                    if (point2.y != n5) {
                        n4 = point2.y;
                        point2.y = n5;
                    }
                } else if (n5 == point2.x && n2 < point2.y) {
                    n4 = point2.y;
                    Point point4 = point2;
                    point4.y = n3 = n2;
                    n7 = n3;
                }
            } else if (n2 > point2.y) {
                n7 = point2.y;
                Point point5 = point2;
                point5.y = n3 = n2;
                n4 = n3;
                if (point2.x != n5) {
                    n7 = point2.x;
                    point2.x = n5;
                }
            } else if (n5 == point2.y && n2 > point2.x) {
                blArray[i] = true;
                n7 = point2.x;
                Point point6 = point2;
                point6.x = n3 = n2;
                n4 = n3;
            }
            if (n7 == -1 || n4 == -1) continue;
            this.internalRedrawRange(n7, n4 - n7);
            bl = true;
        }
        if (bl) {
            int[] nArray = new int[pointArray.length * 2];
            for (n2 = 0; n2 < pointArray.length; ++n2) {
                point = pointArray[n2];
                if (blArray[n2]) {
                    nArray[2 * n2] = point.y;
                    nArray[2 * n2 + 1] = point.x - point.y;
                    continue;
                }
                nArray[2 * n2] = point.x;
                nArray[2 * n2 + 1] = point.y - point.x;
            }
            this.setSelection(nArray, false, this.blockSelection);
            this.sendSelectionEvent();
        }
        this.sendAccessibleTextCaretMoved();
    }

    void doSelectionCursorNext() {
        int[] nArray = Arrays.copyOf(this.caretOffsets, this.caretOffsets.length);
        int n = Integer.MIN_VALUE;
        for (int i = 0; i < this.caretOffsets.length; ++i) {
            int n2 = this.caretOffsets[i];
            int n3 = this.content.getLineAtOffset(n2);
            int n4 = this.content.getOffsetAtLine(n3);
            int n5 = n2 - n4;
            if (n5 < this.content.getLine(n3).length()) {
                TextLayout textLayout = this.renderer.getTextLayout(n3);
                n5 = textLayout.getNextOffset(n5, 2);
                int n6 = textLayout.getLineOffsets()[textLayout.getLineIndex(n5)];
                this.renderer.disposeTextLayout(textLayout);
                int n7 = n5 + n4;
                n = n5 == n6 ? 1 : 0;
                nArray[i] = n7;
                continue;
            }
            if (n3 >= this.content.getLineCount() - 1 || this == false) continue;
            int n8 = this.content.getOffsetAtLine(++n3);
            n = 0;
            nArray[i] = n8;
        }
        if (n > Integer.MIN_VALUE) {
            this.setCaretOffsets(nArray, n);
            this.showCaret();
        }
    }

    void doSelectionCursorPrevious() {
        int[] nArray = Arrays.copyOf(this.caretOffsets, this.caretOffsets.length);
        for (int i = 0; i < this.caretOffsets.length; ++i) {
            int n = this.caretOffsets[i];
            int n2 = this.content.getLineAtOffset(n);
            int n3 = this.content.getOffsetAtLine(n2);
            int n4 = n - n3;
            if (n4 > 0) {
                nArray[i] = this.getClusterPrevious(n, n2);
                continue;
            }
            if (n2 <= 0) continue;
            n3 = this.content.getOffsetAtLine(--n2);
            nArray[i] = n3 + this.content.getLine(n2).length();
        }
        if (!Arrays.equals(this.caretOffsets, nArray)) {
            this.setCaretOffsets(nArray, 1);
            this.showCaret();
        }
    }

    void doSelectionLineDown() {
        int n;
        this.columnX = n = this.getPointAtOffset((int)this.caretOffsets[0]).x;
        int n2 = n;
        this.doLineDown(true);
        this.columnX = n2;
    }

    void doSelectionLineUp() {
        int n;
        this.columnX = n = this.getPointAtOffset((int)this.caretOffsets[0]).x;
        int n2 = n;
        this.doLineUp(true);
        this.columnX = n2;
    }

    void doSelectionPageDown(int n) {
        int n2;
        this.columnX = n2 = this.getPointAtOffset((int)this.caretOffsets[0]).x;
        int n3 = n2;
        this.doPageDown(true, n);
        this.columnX = n3;
    }

    void doSelectionPageUp(int n) {
        int n2;
        if (this.caretOffsets.length > 1) {
            return;
        }
        this.columnX = n2 = this.getPointAtOffset((int)this.caretOffsets[0]).x;
        int n3 = n2;
        this.doPageUp(true, n);
        this.columnX = n3;
    }

    void doSelectionWordNext() {
        int[] nArray = Arrays.stream(this.caretOffsets).map(this::lambda$doSelectionWordNext$12).toArray();
        if (this != false) {
            this.setCaretOffsets(nArray, 1);
            this.showCaret();
        } else {
            int[] nArray2;
            int[] nArray3 = Arrays.stream(this.caretOffsets).map(this::lambda$doSelectionWordNext$13).toArray();
            if (Arrays.equals(nArray3, nArray2 = Arrays.stream(nArray).map(this::lambda$doSelectionWordNext$14).toArray())) {
                this.setCaretOffsets(nArray, 1);
                this.showCaret();
            }
        }
    }

    void doSelectionWordPrevious() {
        this.setCaretOffsets(Arrays.stream(this.caretOffsets).map(this::lambda$doSelectionWordPrevious$15).toArray(), 1);
        this.showCaret();
    }

    void doVisualPrevious() {
        this.setCaretOffsets(Arrays.stream(this.caretOffsets).map(this::lambda$doVisualPrevious$16).toArray(), -1);
        this.showCaret();
    }

    void doVisualNext() {
        this.setCaretOffsets(Arrays.stream(this.caretOffsets).map(this::lambda$doVisualNext$17).toArray(), -1);
        this.showCaret();
    }

    void doWordNext() {
        if (Arrays.stream(this.selection).anyMatch(StyledText::lambda$doWordNext$18)) {
            this.setCaretOffsets(Arrays.stream(this.selection).mapToInt(StyledText::lambda$doWordNext$19).toArray(), -1);
            this.showCaret();
        } else {
            this.doSelectionWordNext();
        }
    }

    void doWordPrevious() {
        if (Arrays.stream(this.selection).anyMatch(StyledText::lambda$doWordPrevious$20)) {
            this.setCaretOffsets(Arrays.stream(this.selection).mapToInt(StyledText::lambda$doWordPrevious$21).toArray(), -1);
            this.showCaret();
        } else {
            this.doSelectionWordPrevious();
        }
    }

    void endAutoScroll() {
        this.autoScrollDirection = 0;
    }

    @Override
    public Color getBackground() {
        this.checkWidget();
        if (this.background == null) {
            return this.getDisplay().getSystemColor(25);
        }
        return this.background;
    }

    public int getBaseline() {
        this.checkWidget();
        return this.renderer.getBaseline();
    }

    public int getBaseline(int n) {
        this.checkWidget();
        if (0 > n || n > this.content.getCharCount()) {
            SWT.error(6);
        }
        if (this == false) {
            return this.renderer.getBaseline();
        }
        int n2 = this.content.getLineAtOffset(n);
        int n3 = this.content.getOffsetAtLine(n2);
        TextLayout textLayout = this.renderer.getTextLayout(n2);
        int n4 = textLayout.getLineIndex(Math.min(n - n3, textLayout.getText().length()));
        FontMetrics fontMetrics = textLayout.getLineMetrics(n4);
        this.renderer.disposeTextLayout(textLayout);
        return fontMetrics.getAscent() + fontMetrics.getLeading();
    }

    @Deprecated
    public boolean getBidiColoring() {
        this.checkWidget();
        return this.bidiColoring;
    }

    public boolean getBlockSelection() {
        this.checkWidget();
        return this.blockSelection;
    }

    Rectangle getBlockSelectionPosition() {
        int n;
        int n2;
        int n3;
        int n4 = this.getLineIndex(this.blockYAnchor - this.getVerticalScrollOffset());
        if (n4 > (n3 = this.getLineIndex(this.blockYLocation - this.getVerticalScrollOffset()))) {
            n2 = n4;
            n4 = n3;
            n3 = n2;
        }
        if ((n2 = this.blockXAnchor) > (n = this.blockXLocation)) {
            n2 = this.blockXLocation;
            n = this.blockXAnchor;
        }
        return new Rectangle(n2 - this.horizontalScrollOffset, n4, n - this.horizontalScrollOffset, n3);
    }

    public Rectangle getBlockSelectionBounds() {
        Serializable serializable;
        Serializable serializable2;
        Rectangle rectangle;
        if (this.blockSelection && this.blockXLocation != -1) {
            rectangle = this.getBlockSelectionRectangle();
        } else {
            serializable2 = this.getPointAtOffset(this.selection[0].x);
            serializable = this.getPointAtOffset(this.selection[0].y);
            int n = this.getLineHeight(this.selection[0].y);
            rectangle = new Rectangle(serializable2.x, serializable2.y, serializable.x - serializable2.x, serializable.y + n - serializable2.y);
            if (this.selection[0].x == this.selection[0].y) {
                rectangle.width = this.getCaretWidth();
            }
        }
        serializable2 = rectangle;
        ((Rectangle)serializable2).x += this.horizontalScrollOffset;
        serializable = rectangle;
        ((Rectangle)serializable).y += this.getVerticalScrollOffset();
        return rectangle;
    }

    Rectangle getBlockSelectionRectangle() {
        Rectangle rectangle = this.getBlockSelectionPosition();
        rectangle.y = this.getLinePixel(rectangle.y);
        rectangle.width -= rectangle.x;
        rectangle.height = this.getLinePixel(rectangle.height + 1) - rectangle.y;
        return rectangle;
    }

    String getBlockSelectionText(String string) {
        Rectangle rectangle = this.getBlockSelectionPosition();
        int n = rectangle.y;
        int n2 = rectangle.height;
        int n3 = rectangle.x;
        int n4 = rectangle.width;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = n; i <= n2; ++i) {
            int n5;
            int n6 = this.getOffsetAtPoint(n3, 0, i, null);
            if (n6 > (n5 = this.getOffsetAtPoint(n4, 0, i, null))) {
                int n7 = n6;
                n6 = n5;
                n5 = n7;
            }
            String string2 = this.content.getTextRange(n6, n5 - n6);
            stringBuilder.append(string2);
            if (i >= n2) continue;
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    int getBottomIndex() {
        int n;
        if (this == false) {
            int n2 = 1;
            int n3 = this.renderer.getLineHeight();
            if (n3 != 0) {
                int n4 = this.topIndex * n3 - this.getVerticalScrollOffset();
                n2 = (this.clientAreaHeight - n4) / n3;
            }
            n = Math.min(this.content.getLineCount() - 1, this.topIndex + Math.max(0, n2 - 1));
        } else {
            int n5;
            int n6;
            int n7 = this.clientAreaHeight - this.bottomMargin;
            n = this.getLineIndex(n7);
            if (n > 0 && (n6 = this.getLinePixel(n)) + (n5 = this.renderer.getLineHeight(n)) > n7 && this.getLinePixel(n - 1) >= this.topMargin) {
                --n;
            }
        }
        return n;
    }

    public int getBottomMargin() {
        this.checkWidget();
        return this.bottomMargin;
    }

    Rectangle getBoundsAtOffset(int n) {
        int n2;
        Rectangle rectangle;
        Object object;
        int n3 = this.content.getLineAtOffset(n);
        int n4 = this.content.getOffsetAtLine(n3);
        String string = this.content.getLine(n3);
        if (string.length() != 0) {
            object = this.renderer.getTextLayout(n3);
            int n5 = Math.min(((TextLayout)object).getText().length(), Math.max(0, n - n4));
            rectangle = ((TextLayout)object).getBounds(n5, n5);
            if (this.getListeners(3007).length > 0 && this.caretAlignment == 0 && n5 != 0) {
                n5 = ((TextLayout)object).getPreviousOffset(n5, 2);
                Point point = ((TextLayout)object).getLocation(n5, true);
                rectangle = new Rectangle(point.x, point.y, 0, rectangle.height);
            }
            this.renderer.disposeTextLayout((TextLayout)object);
        } else {
            rectangle = new Rectangle(0, 0, 0, this.renderer.getLineHeight());
        }
        if (Arrays.binarySearch(this.caretOffsets, n) >= 0 && this == false && n == (n2 = n4 + string.length())) {
            Rectangle rectangle2 = rectangle;
            rectangle2.width += this.getCaretWidth();
        }
        object = rectangle;
        ((Rectangle)object).x += this.leftMargin - this.horizontalScrollOffset;
        Rectangle rectangle3 = rectangle;
        rectangle3.y += this.getLinePixel(n3);
        return rectangle;
    }

    public int getCaretOffset() {
        this.checkWidget();
        return this.caretOffsets[0];
    }

    int getCaretWidth() {
        Caret caret = this.getCaret();
        if (caret == null) {
            return 0;
        }
        return caret.getSize().x;
    }

    Object getClipboardContent(int n) {
        TextTransfer textTransfer = TextTransfer.getInstance();
        return this.clipboard.getContents(textTransfer, n);
    }

    int getClusterNext(int n, int n2) {
        int n3 = this.content.getOffsetAtLine(n2);
        TextLayout textLayout = this.renderer.getTextLayout(n2);
        n -= n3;
        n = textLayout.getNextOffset(n, 2);
        this.renderer.disposeTextLayout(textLayout);
        return n += n3;
    }

    int getClusterPrevious(int n, int n2) {
        int n3 = this.content.getOffsetAtLine(n2);
        TextLayout textLayout = this.renderer.getTextLayout(n2);
        n -= n3;
        n = textLayout.getPreviousOffset(n, 2);
        this.renderer.disposeTextLayout(textLayout);
        return n += n3;
    }

    public StyledTextContent getContent() {
        this.checkWidget();
        return this.content;
    }

    @Override
    public boolean getDragDetect() {
        this.checkWidget();
        return this.dragDetect;
    }

    public boolean getDoubleClickEnabled() {
        this.checkWidget();
        return this.doubleClickEnabled;
    }

    public boolean getEditable() {
        this.checkWidget();
        return this.editable;
    }

    @Override
    public Color getForeground() {
        this.checkWidget();
        if (this.foreground == null) {
            return this.getDisplay().getSystemColor(24);
        }
        return this.foreground;
    }

    int getHorizontalIncrement() {
        return this.renderer.averageCharWidth;
    }

    public int getHorizontalIndex() {
        this.checkWidget();
        return this.horizontalScrollOffset / this.getHorizontalIncrement();
    }

    public int getHorizontalPixel() {
        this.checkWidget();
        return this.horizontalScrollOffset;
    }

    public int getIndent() {
        this.checkWidget();
        return this.indent;
    }

    public boolean getJustify() {
        this.checkWidget();
        return this.justify;
    }

    public int getKeyBinding(int n) {
        this.checkWidget();
        Integer n2 = (Integer)this.keyActionMap.get(n);
        return n2 == null ? 0 : n2;
    }

    public int getCharCount() {
        this.checkWidget();
        return this.content.getCharCount();
    }

    public String getLine(int n) {
        this.checkWidget();
        if (n < 0 || n > 0 && n >= this.content.getLineCount()) {
            SWT.error(6);
        }
        return this.content.getLine(n);
    }

    public int getLineAlignment(int n) {
        this.checkWidget();
        if (n < 0 || n > this.content.getLineCount()) {
            SWT.error(5);
        }
        return this.renderer.getLineAlignment(n, this.alignment);
    }

    public int getLineAtOffset(int n) {
        this.checkWidget();
        if (n < 0 || n > this.getCharCount()) {
            SWT.error(6);
        }
        return this.content.getLineAtOffset(n);
    }

    public Color getLineBackground(int n) {
        this.checkWidget();
        if (n < 0 || n > this.content.getLineCount()) {
            SWT.error(5);
        }
        return this.isListening(3001) ? null : this.renderer.getLineBackground(n, null);
    }

    public Bullet getLineBullet(int n) {
        this.checkWidget();
        if (n < 0 || n > this.content.getLineCount()) {
            SWT.error(5);
        }
        return this.isListening(3002) ? null : this.renderer.getLineBullet(n, null);
    }

    StyledTextEvent getLineBackgroundData(int n, String string) {
        return this.sendLineEvent(3001, n, string);
    }

    public int getLineCount() {
        this.checkWidget();
        return this.content.getLineCount();
    }

    int getLineCountWhole() {
        if (this == false) {
            int n = this.renderer.getLineHeight();
            return n != 0 ? this.clientAreaHeight / n : 1;
        }
        return this.getBottomIndex() - this.topIndex + 1;
    }

    public String getLineDelimiter() {
        this.checkWidget();
        return this.content.getLineDelimiter();
    }

    public int getLineHeight() {
        this.checkWidget();
        return this.renderer.getLineHeight();
    }

    public int getLineHeight(int n) {
        this.checkWidget();
        if (0 > n || n > this.content.getCharCount()) {
            SWT.error(6);
        }
        if (this == false) {
            return this.renderer.getLineHeight();
        }
        int n2 = this.content.getLineAtOffset(n);
        int n3 = this.content.getOffsetAtLine(n2);
        TextLayout textLayout = this.renderer.getTextLayout(n2);
        int n4 = textLayout.getLineIndex(Math.min(n - n3, textLayout.getText().length()));
        int n5 = textLayout.getLineBounds((int)n4).height;
        this.renderer.disposeTextLayout(textLayout);
        return n5;
    }

    public int getLineIndent(int n) {
        this.checkWidget();
        if (n < 0 || n > this.content.getLineCount()) {
            SWT.error(5);
        }
        return this.isListening(3002) ? 0 : this.renderer.getLineIndent(n, this.indent);
    }

    public int getLineVerticalIndent(int n) {
        this.checkWidget();
        if (n < 0 || n >= this.content.getLineCount()) {
            SWT.error(5);
        }
        return this.isListening(3002) ? 0 : this.renderer.getLineVerticalIndent(n);
    }

    public boolean getLineJustify(int n) {
        this.checkWidget();
        if (n < 0 || n > this.content.getLineCount()) {
            SWT.error(5);
        }
        return !this.isListening(3002) && this.renderer.getLineJustify(n, this.justify);
    }

    public int getLineSpacing() {
        this.checkWidget();
        return this.lineSpacing;
    }

    StyledTextEvent getLineStyleData(int n, String string) {
        return this.sendLineEvent(3002, n, string);
    }

    public int getLinePixel(int n) {
        this.checkWidget();
        int n2 = this.content.getLineCount();
        n = Math.max(0, Math.min(n2, n));
        if (this == false) {
            int n3 = this.renderer.getLineHeight();
            return n * n3 - this.getVerticalScrollOffset() + this.topMargin;
        }
        if (n == this.topIndex) {
            return this.topIndexY + this.topMargin;
        }
        int n4 = this.topIndexY;
        if (n > this.topIndex) {
            for (int i = this.topIndex; i < n; ++i) {
                n4 += this.renderer.getLineHeight(i);
            }
        } else {
            for (int i = this.topIndex - 1; i >= n; --i) {
                n4 -= this.renderer.getLineHeight(i);
            }
        }
        return n4 + this.topMargin;
    }

    public int getLineIndex(int n) {
        this.checkWidget();
        n -= this.topMargin;
        if (this == false) {
            int n2 = this.renderer.getLineHeight();
            int n3 = (n + this.getVerticalScrollOffset()) / n2;
            int n4 = this.content.getLineCount();
            n3 = Math.max(0, Math.min(n4 - 1, n3));
            return n3;
        }
        if (n == this.topIndexY) {
            return this.topIndex;
        }
        int n5 = this.topIndex;
        if (n < this.topIndexY) {
            while (n < this.topIndexY && n5 > 0) {
                n += this.renderer.getLineHeight(--n5);
            }
        } else {
            int n6 = this.content.getLineCount();
            int n7 = this.renderer.getLineHeight(n5);
            while (n - n7 >= this.topIndexY && n5 < n6 - 1) {
                n -= n7;
                n7 = this.renderer.getLineHeight(++n5);
            }
        }
        return n5;
    }

    public int[] getLineTabStops(int n) {
        this.checkWidget();
        if (n < 0 || n > this.content.getLineCount()) {
            SWT.error(5);
        }
        if (this.isListening(3002)) {
            return null;
        }
        int[] nArray = this.renderer.getLineTabStops(n, null);
        if (nArray == null) {
            nArray = this.tabs;
        }
        if (nArray == null) {
            return new int[]{this.renderer.tabWidth};
        }
        int[] nArray2 = new int[nArray.length];
        System.arraycopy(nArray, 0, nArray2, 0, nArray.length);
        return nArray2;
    }

    public int getLineWrapIndent(int n) {
        this.checkWidget();
        if (n < 0 || n > this.content.getLineCount()) {
            SWT.error(5);
        }
        return this.isListening(3002) ? 0 : this.renderer.getLineWrapIndent(n, this.wrapIndent);
    }

    public int getLeftMargin() {
        this.checkWidget();
        return this.leftMargin - this.alignmentMargin;
    }

    public Point getLocationAtOffset(int n) {
        this.checkWidget();
        if (n < 0 || n > this.getCharCount()) {
            SWT.error(6);
        }
        return this.getPointAtOffset(n);
    }

    public boolean getMouseNavigatorEnabled() {
        this.checkWidget();
        return this.mouseNavigator != null;
    }

    public int getOffsetAtLine(int n) {
        this.checkWidget();
        if (n < 0 || n > 0 && n >= this.content.getLineCount()) {
            SWT.error(6);
        }
        return this.content.getOffsetAtLine(n);
    }

    @Deprecated
    public int getOffsetAtLocation(Point point) {
        int[] nArray;
        int n;
        this.checkWidget();
        if (point == null) {
            SWT.error(4);
        }
        if ((n = this.getOffsetAtPoint(point.x, point.y, nArray = new int[]{0}, true)) == -1) {
            SWT.error(5);
        }
        return n + nArray[0];
    }

    public int getOffsetAtPoint(Point point) {
        int[] nArray;
        int n;
        this.checkWidget();
        if (point == null) {
            SWT.error(4);
        }
        return (n = this.getOffsetAtPoint(point.x, point.y, nArray = new int[]{0}, true)) != -1 ? n + nArray[0] : -1;
    }

    int getOffsetAtPoint(int n, int n2, int[] nArray) {
        int n3 = this.getLineIndex(n2);
        return this.getOffsetAtPoint(n, n2 -= this.getLinePixel(n3), n3, nArray);
    }

    int getOffsetAtPoint(int n, int n2, int n3, int[] nArray) {
        TextLayout textLayout = this.renderer.getTextLayout(n3);
        int[] nArray2 = new int[]{0};
        int n4 = textLayout.getOffset(n += this.horizontalScrollOffset - this.leftMargin, n2, nArray2);
        if (nArray != null) {
            nArray[0] = 1;
        }
        if (nArray2[0] != 0) {
            int n5 = textLayout.getLineIndex(n4 + nArray2[0]);
            int n6 = textLayout.getLineOffsets()[n5];
            if (n4 + nArray2[0] == n6) {
                n4 += nArray2[0];
                if (nArray != null) {
                    nArray[0] = 0;
                }
            } else {
                int n7;
                String string = this.content.getLine(n3);
                int n8 = 0;
                if (nArray != null) {
                    for (n7 = n4; n7 > 0 && Character.isDigit(string.charAt(n7)); --n7) {
                    }
                    n8 = n7 == 0 && Character.isDigit(string.charAt(n7)) ? (this != false ? 1 : 0) : textLayout.getLevel(n7) & 1;
                }
                n4 += nArray2[0];
                if (nArray != null) {
                    n7 = textLayout.getLevel(n4) & 1;
                    nArray[0] = n8 != n7 ? 0 : 1;
                }
            }
        }
        this.renderer.disposeTextLayout(textLayout);
        return n4 + this.content.getOffsetAtLine(n3);
    }

    int getOffsetAtPoint(int n, int n2, int[] nArray, boolean bl) {
        if (bl && n2 + this.getVerticalScrollOffset() < 0 || n + this.horizontalScrollOffset < 0) {
            return -1;
        }
        int n3 = this.getPartialBottomIndex();
        int n4 = this.getLinePixel(n3 + 1);
        if (bl && n2 > n4) {
            return -1;
        }
        int n5 = this.getLineIndex(n2);
        int n6 = this.content.getOffsetAtLine(n5);
        TextLayout textLayout = this.renderer.getTextLayout(n5);
        int n7 = textLayout.getOffset(n += this.horizontalScrollOffset - this.leftMargin, n2 -= this.getLinePixel(n5), nArray);
        Rectangle rectangle = textLayout.getLineBounds(textLayout.getLineIndex(n7));
        this.renderer.disposeTextLayout(textLayout);
        if (bl && (rectangle.x > n || n > rectangle.x + rectangle.width)) {
            return -1;
        }
        return n7 + n6;
    }

    @Override
    public int getOrientation() {
        return super.getOrientation();
    }

    int getPartialBottomIndex() {
        if (this == false) {
            int n = this.renderer.getLineHeight();
            int n2 = Compatibility.ceil(this.clientAreaHeight, n);
            return Math.max(0, Math.min(this.content.getLineCount(), this.topIndex + n2) - 1);
        }
        return this.getLineIndex(this.clientAreaHeight - this.bottomMargin);
    }

    int getPartialTopIndex() {
        if (this == false) {
            int n = this.renderer.getLineHeight();
            return this.getVerticalScrollOffset() / n;
        }
        return this.topIndexY <= 0 ? this.topIndex : this.topIndex - 1;
    }

    String getPlatformDelimitedText(TextWriter textWriter) {
        int n = textWriter.getStart() + textWriter.getCharCount();
        int n2 = this.content.getLineAtOffset(textWriter.getStart());
        int n3 = this.content.getLineAtOffset(n);
        String string = this.content.getLine(n3);
        int n4 = this.content.getOffsetAtLine(n3);
        for (int i = n2; i <= n3; ++i) {
            textWriter.writeLine(this.content.getLine(i), this.content.getOffsetAtLine(i));
            if (i >= n3) continue;
            textWriter.writeLineDelimiter(PlatformLineDelimiter);
        }
        if (n > n4 + string.length()) {
            textWriter.writeLineDelimiter(PlatformLineDelimiter);
        }
        textWriter.close();
        return textWriter.toString();
    }

    public int[] getRanges() {
        int[] nArray;
        this.checkWidget();
        if (!this.isListening(3002) && (nArray = this.renderer.getRanges(0, this.content.getCharCount())) != null) {
            return nArray;
        }
        return new int[0];
    }

    public int[] getRanges(int n, int n2) {
        int[] nArray;
        this.checkWidget();
        int n3 = this.getCharCount();
        int n4 = n + n2;
        if (n > n4 || n < 0 || n4 > n3) {
            SWT.error(6);
        }
        if (!this.isListening(3002) && (nArray = this.renderer.getRanges(n, n2)) != null) {
            return nArray;
        }
        return new int[0];
    }

    public int getRightMargin() {
        this.checkWidget();
        return this.rightMargin;
    }

    public Point getSelection() {
        this.checkWidget();
        return new Point(this.selection[0].x, this.selection[0].y);
    }

    public Point getSelectionRange() {
        this.checkWidget();
        return new Point(this.selection[0].x, this.selection[0].y - this.selection[0].x);
    }

    public int[] getSelectionRanges() {
        this.checkWidget();
        if (this.blockSelection && this.blockXLocation != -1) {
            Rectangle rectangle = this.getBlockSelectionPosition();
            int n = rectangle.y;
            int n2 = rectangle.height;
            int n3 = rectangle.x;
            int n4 = rectangle.width;
            int[] nArray = new int[(n2 - n + 1) * 2];
            int n5 = 0;
            for (int i = n; i <= n2; ++i) {
                int n6;
                int n7 = this.getOffsetAtPoint(n3, 0, i, null);
                if (n7 > (n6 = this.getOffsetAtPoint(n4, 0, i, null))) {
                    int n8 = n7;
                    n7 = n6;
                    n6 = n8;
                }
                nArray[n5++] = n7;
                nArray[n5++] = n6 - n7;
            }
            return nArray;
        }
        int[] nArray = new int[2 * this.selection.length];
        int n = 0;
        for (Point point : this.selection) {
            nArray[n++] = point.x;
            nArray[n++] = point.y - point.x;
        }
        return nArray;
    }

    public Color getSelectionBackground() {
        this.checkWidget();
        if (this.selectionBackground == null) {
            return this.getDisplay().getSystemColor(26);
        }
        return this.selectionBackground;
    }

    public int getSelectionCount() {
        this.checkWidget();
        if (this.blockSelection && this.blockXLocation != -1) {
            return this.getBlockSelectionText(this.content.getLineDelimiter()).length();
        }
        return Arrays.stream(this.selection).collect(Collectors.summingInt(StyledText::lambda$getSelectionCount$22));
    }

    public Color getSelectionForeground() {
        this.checkWidget();
        if (this.selectionForeground == null) {
            return this.getDisplay().getSystemColor(27);
        }
        return this.selectionForeground;
    }

    public String getSelectionText() {
        this.checkWidget();
        if (this.blockSelection && this.blockXLocation != -1) {
            return this.getBlockSelectionText(this.content.getLineDelimiter());
        }
        return Arrays.stream(this.selection).map(this::lambda$getSelectionText$23).collect(Collectors.joining());
    }

    StyledTextEvent getBidiSegments(int n, String string) {
        char[] cArray;
        if (!this.isListening(3007)) {
            if (!this.bidiColoring) {
                return null;
            }
            StyledTextEvent styledTextEvent = new StyledTextEvent(this.content);
            styledTextEvent.segments = this.getBidiSegmentsCompatibility(string, n);
            return styledTextEvent;
        }
        StyledTextEvent styledTextEvent = this.sendLineEvent(3007, n, string);
        if (styledTextEvent == null || styledTextEvent.segments == null || styledTextEvent.segments.length == 0) {
            return null;
        }
        int[] nArray = styledTextEvent.segments;
        int n2 = string.length();
        if (nArray[0] > n2) {
            SWT.error(5);
        }
        boolean bl = (cArray = styledTextEvent.segmentsChars) != null;
        for (int i = 1; i < nArray.length; ++i) {
            if (!(!bl ? nArray[i] <= nArray[i - 1] : nArray[i] < nArray[i - 1]) && nArray[i] <= n2) continue;
            SWT.error(5);
        }
        if (bl && !this.visualWrap) {
            for (char c : cArray) {
                if (c != '\n' && c != '\r') continue;
                this.visualWrap = true;
                break;
            }
        }
        return styledTextEvent;
    }

    int[] getBidiSegmentsCompatibility(String string, int n) {
        int n2;
        int n3 = string.length();
        StyleRange[] styleRangeArray = null;
        StyledTextEvent styledTextEvent = this.getLineStyleData(n, string);
        styleRangeArray = styledTextEvent != null ? styledTextEvent.styles : this.renderer.getStyleRanges(n, n3, true);
        if (styleRangeArray == null || styleRangeArray.length == 0) {
            return new int[]{0, n3};
        }
        int n4 = 1;
        for (n2 = 0; n2 < styleRangeArray.length && styleRangeArray[n2].start == 0 && styleRangeArray[n2].length == n3; ++n2) {
        }
        int[] nArray = new int[(styleRangeArray.length - n2) * 2 + 2];
        for (int i = n2; i < styleRangeArray.length; ++i) {
            StyleRange styleRange = styleRangeArray[i];
            int n5 = Math.max(styleRange.start - n, 0);
            int n6 = Math.max(styleRange.start + styleRange.length - n, n5);
            n6 = Math.min(n6, string.length());
            if (i > 0 && n4 > 1 && (n5 >= nArray[n4 - 2] && n5 <= nArray[n4 - 1] || n6 >= nArray[n4 - 2] && n6 <= nArray[n4 - 1]) && styleRange.similarTo(styleRangeArray[i - 1])) {
                nArray[n4 - 2] = Math.min(nArray[n4 - 2], n5);
                nArray[n4 - 1] = Math.max(nArray[n4 - 1], n6);
                continue;
            }
            if (n5 > nArray[n4 - 1]) {
                nArray[n4] = n5;
                ++n4;
            }
            nArray[n4] = n6;
            ++n4;
        }
        if (n3 > nArray[n4 - 1]) {
            nArray[n4] = n3;
            ++n4;
        }
        if (n4 == nArray.length) {
            return nArray;
        }
        int[] nArray2 = new int[n4];
        System.arraycopy(nArray, 0, nArray2, 0, n4);
        return nArray2;
    }

    public StyleRange getStyleRangeAtOffset(int n) {
        StyleRange[] styleRangeArray;
        this.checkWidget();
        if (n < 0 || n >= this.getCharCount()) {
            SWT.error(5);
        }
        if (!this.isListening(3002) && (styleRangeArray = this.renderer.getStyleRanges(n, 1, true)) != null) {
            return styleRangeArray[0];
        }
        return null;
    }

    public StyleRange[] getStyleRanges() {
        this.checkWidget();
        return this.getStyleRanges(0, this.content.getCharCount(), true);
    }

    public StyleRange[] getStyleRanges(boolean bl) {
        this.checkWidget();
        return this.getStyleRanges(0, this.content.getCharCount(), bl);
    }

    public StyleRange[] getStyleRanges(int n, int n2) {
        this.checkWidget();
        return this.getStyleRanges(n, n2, true);
    }

    public StyleRange[] getStyleRanges(int n, int n2, boolean bl) {
        StyleRange[] styleRangeArray;
        this.checkWidget();
        int n3 = this.getCharCount();
        int n4 = n + n2;
        if (n > n4 || n < 0 || n4 > n3) {
            SWT.error(6);
        }
        if (!this.isListening(3002) && (styleRangeArray = this.renderer.getStyleRanges(n, n2, bl)) != null) {
            return styleRangeArray;
        }
        return new StyleRange[0];
    }

    public int getTabs() {
        this.checkWidget();
        return this.tabLength;
    }

    public int[] getTabStops() {
        this.checkWidget();
        if (this.tabs == null) {
            return new int[]{this.renderer.tabWidth};
        }
        int[] nArray = new int[this.tabs.length];
        System.arraycopy(this.tabs, 0, nArray, 0, this.tabs.length);
        return nArray;
    }

    public String getText() {
        this.checkWidget();
        return this.content.getTextRange(0, this.getCharCount());
    }

    public String getText(int n, int n2) {
        this.checkWidget();
        int n3 = this.getCharCount();
        if (n < 0 || n >= n3 || n2 < 0 || n2 >= n3 || n > n2) {
            SWT.error(6);
        }
        return this.content.getTextRange(n, n2 - n + 1);
    }

    public Rectangle getTextBounds(int n, int n2) {
        Rectangle rectangle;
        this.checkWidget();
        int n3 = this.getCharCount();
        if (n < 0 || n >= n3 || n2 < 0 || n2 >= n3 || n > n2) {
            SWT.error(6);
        }
        int n4 = this.content.getLineAtOffset(n);
        int n5 = this.content.getLineAtOffset(n2);
        int n6 = this.getLinePixel(n4);
        int n7 = 0;
        int n8 = Integer.MAX_VALUE;
        int n9 = 0;
        for (int i = n4; i <= n5; ++i) {
            int n10 = this.content.getOffsetAtLine(i);
            TextLayout textLayout = this.renderer.getTextLayout(i);
            int n11 = textLayout.getText().length();
            if (n11 > 0) {
                Rectangle rectangle2;
                if (i == n4) {
                    rectangle2 = i == n5 ? textLayout.getBounds(n - n10, n2 - n10) : textLayout.getBounds(n - n10, n11);
                    n6 += rectangle2.y;
                } else {
                    rectangle2 = i == n5 ? textLayout.getBounds(0, n2 - n10) : textLayout.getBounds();
                }
                n8 = Math.min(n8, rectangle2.x);
                n9 = Math.max(n9, rectangle2.x + rectangle2.width);
                n7 += rectangle2.height;
            } else {
                n7 += this.renderer.getLineHeight();
            }
            this.renderer.disposeTextLayout(textLayout);
        }
        Rectangle rectangle3 = rectangle = new Rectangle(n8, n6, n9 - n8, n7);
        rectangle.x += this.leftMargin - this.horizontalScrollOffset;
        return rectangle3;
    }

    public String getTextRange(int n, int n2) {
        this.checkWidget();
        int n3 = this.getCharCount();
        int n4 = n + n2;
        if (n > n4 || n < 0 || n4 > n3) {
            SWT.error(6);
        }
        return this.content.getTextRange(n, n2);
    }

    public int getTextLimit() {
        this.checkWidget();
        return this.textLimit;
    }

    public int getTopIndex() {
        this.checkWidget();
        return this.topIndex;
    }

    public int getTopMargin() {
        this.checkWidget();
        return this.topMargin;
    }

    public int getTopPixel() {
        this.checkWidget();
        return this.getVerticalScrollOffset();
    }

    int getVerticalIncrement() {
        return this.renderer.getLineHeight();
    }

    int getVerticalScrollOffset() {
        if (this.verticalScrollOffset == -1) {
            this.renderer.calculate(0, this.topIndex);
            int n = 0;
            for (int i = 0; i < this.topIndex; ++i) {
                n += this.renderer.getCachedLineHeight(i);
            }
            this.verticalScrollOffset = n -= this.topIndexY;
        }
        return this.verticalScrollOffset;
    }

    int getVisualLineIndex(TextLayout textLayout, int n) {
        int n2 = textLayout.getLineIndex(n);
        int[] nArray = textLayout.getLineOffsets();
        Caret caret = this.getCaret();
        if (caret != null && n2 != 0 && n == nArray[n2]) {
            int n3 = textLayout.getLineBounds((int)n2).y;
            int n4 = caret.getLocation().y - this.getLinePixel(this.getFirstCaretLine());
            if (n3 > n4) {
                --n2;
            }
            this.caretAlignment = 1;
        }
        return n2;
    }

    int getCaretDirection() {
        if (!this.isBidiCaret()) {
            return -1;
        }
        if (this.ime.getCompositionOffset() != -1) {
            return -1;
        }
        if (!this.updateCaretDirection && this.caretDirection != 0) {
            return this.caretDirection;
        }
        this.updateCaretDirection = false;
        int n = this.getFirstCaretLine();
        int n2 = this.content.getOffsetAtLine(n);
        String string = this.content.getLine(n);
        int n3 = this.caretOffsets[0] - n2;
        int n4 = string.length();
        if (n4 == 0) {
            return this != false ? 131072 : 16384;
        }
        if (this.caretAlignment == 0 && n3 > 0) {
            --n3;
        }
        if (n3 == n4 && n3 > 0) {
            --n3;
        }
        while (n3 > 0 && Character.isDigit(string.charAt(n3))) {
            --n3;
        }
        if (n3 == 0 && Character.isDigit(string.charAt(n3))) {
            return this != false ? 131072 : 16384;
        }
        TextLayout textLayout = this.renderer.getTextLayout(n);
        int n5 = textLayout.getLevel(n3);
        this.renderer.disposeTextLayout(textLayout);
        return (n5 & 1) != 0 ? 131072 : 16384;
    }

    int getFirstCaretLine() {
        return this.content.getLineAtOffset(this.caretOffsets[0]);
    }

    int getWrapWidth() {
        if (this.wordWrap && this != false) {
            int n = this.clientAreaWidth - this.leftMargin - this.rightMargin;
            return n > 0 ? n : 1;
        }
        return -1;
    }

    int getWordNext(int n, int n2) {
        return this.getWordNext(n, n2, false);
    }

    int getWordNext(int n, int n2, boolean bl) {
        String string;
        int n3;
        int n4;
        if (n >= this.getCharCount()) {
            n4 = n;
            int n5 = this.content.getLineCount() - 1;
            n3 = this.content.getOffsetAtLine(n5);
            string = this.content.getLine(n5);
        } else {
            int n6;
            int n7 = this.content.getLineAtOffset(n);
            n3 = this.content.getOffsetAtLine(n7);
            if (n >= n3 + (n6 = (string = this.content.getLine(n7)).length())) {
                n4 = this.content.getOffsetAtLine(n7 + 1);
            } else {
                TextLayout textLayout = this.renderer.getTextLayout(n7);
                n4 = n3 + textLayout.getNextOffset(n - n3, n2);
                this.renderer.disposeTextLayout(textLayout);
            }
        }
        if (bl) {
            return n4;
        }
        return this.sendWordBoundaryEvent(3009, n2, n, n4, string, n3);
    }

    int getWordPrevious(int n, int n2) {
        return this.getWordPrevious(n, n2, false);
    }

    int getWordPrevious(int n, int n2, boolean bl) {
        String string;
        int n3;
        int n4;
        if (n <= 0) {
            n4 = 0;
            int n5 = this.content.getLineAtOffset(n4);
            n3 = this.content.getOffsetAtLine(n5);
            string = this.content.getLine(n5);
        } else {
            int n6 = this.content.getLineAtOffset(n);
            n3 = this.content.getOffsetAtLine(n6);
            string = this.content.getLine(n6);
            if (n == n3) {
                String string2 = this.content.getLine(n6 - 1);
                int n7 = this.content.getOffsetAtLine(n6 - 1);
                n4 = n7 + string2.length();
            } else {
                int n8 = Math.min(n - n3, string.length());
                TextLayout textLayout = this.renderer.getTextLayout(n6);
                n4 = n3 + textLayout.getPreviousOffset(n8, n2);
                this.renderer.disposeTextLayout(textLayout);
            }
        }
        if (bl) {
            return n4;
        }
        return this.sendWordBoundaryEvent(3010, n2, n, n4, string, n3);
    }

    public boolean getWordWrap() {
        this.checkWidget();
        return this.wordWrap;
    }

    public int getWrapIndent() {
        this.checkWidget();
        return this.wrapIndent;
    }

    Point getPointAtOffset(int n) {
        Object object;
        Point point;
        int n2 = this.content.getLineAtOffset(n);
        String string = this.content.getLine(n2);
        int n3 = this.content.getOffsetAtLine(n2);
        int n4 = Math.max(0, n - n3);
        int n5 = string.length();
        if (n2 < this.content.getLineCount() - 1) {
            int n6 = this.content.getOffsetAtLine(n2 + 1) - 1;
            if (n5 < n4 && n4 <= n6) {
                n4 = n5;
            }
        }
        TextLayout textLayout = this.renderer.getTextLayout(n2);
        if (n5 != 0 && n4 <= n5) {
            if (n4 == n5) {
                n4 = textLayout.getPreviousOffset(n4, 2);
                point = textLayout.getLocation(n4, true);
            } else {
                switch (this.caretAlignment) {
                    case 1: {
                        point = textLayout.getLocation(n4, false);
                        break;
                    }
                    default: {
                        boolean bl;
                        boolean bl2 = bl = n4 == 0;
                        if (this.wordWrap && !bl && (Arrays.binarySearch(this.caretOffsets, n) < 0 || Arrays.stream(this.selection).allMatch(StyledText::lambda$getPointAtOffset$24))) {
                            int[] nArray = textLayout.getLineOffsets();
                            object = nArray;
                            int[] nArray2 = nArray;
                            for (Object object2 : object) {
                                if (object2 != n4) continue;
                                bl = true;
                                break;
                            }
                        }
                        if (bl) {
                            point = textLayout.getLocation(n4, false);
                            break;
                        }
                        n4 = textLayout.getPreviousOffset(n4, 2);
                        point = textLayout.getLocation(n4, true);
                        break;
                    }
                }
            }
        } else {
            point = new Point(textLayout.getIndent(), textLayout.getVerticalIndent());
        }
        this.renderer.disposeTextLayout(textLayout);
        Point point2 = point;
        point2.x += this.leftMargin - this.horizontalScrollOffset;
        object = point;
        ((Point)object).y += this.getLinePixel(n2);
        return point;
    }

    public void insert(String string) {
        this.checkWidget();
        if (string == null) {
            SWT.error(4);
        }
        if (this.blockSelection) {
            this.insertBlockSelectionText(string, false);
        } else {
            Point point = this.getSelectionRange();
            this.replaceTextRange(point.x, point.y, string);
        }
    }

    int insertBlockSelectionText(String string, boolean bl) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7 = 1;
        for (int i = 0; i < string.length(); ++i) {
            n6 = string.charAt(i);
            if (n6 != 10 && n6 != 13) continue;
            ++n7;
            if (n6 != 13 || i + 1 >= string.length() || string.charAt(i + 1) != '\n') continue;
            ++i;
        }
        String[] stringArray = new String[n7];
        n6 = 0;
        n7 = 0;
        for (n5 = 0; n5 < string.length(); ++n5) {
            n4 = string.charAt(n5);
            if (n4 != 10 && n4 != 13) continue;
            stringArray[n7++] = string.substring(n6, n5);
            if (n4 == 13 && n5 + 1 < string.length() && string.charAt(n5 + 1) == '\n') {
                ++n5;
            }
            n6 = n5 + 1;
        }
        stringArray[n7++] = string.substring(n6);
        if (bl) {
            n5 = 0;
            for (String string2 : stringArray) {
                int n8 = string2.length();
                n5 = Math.max(n5, n8);
            }
            for (n4 = 0; n4 < stringArray.length; ++n4) {
                String string3 = stringArray[n4];
                n3 = string3.length();
                if (n3 >= n5) continue;
                int n9 = n5 - n3;
                StringBuilder stringBuilder = new StringBuilder(n3 + n9);
                stringBuilder.append(string3);
                for (n2 = 0; n2 < n9; ++n2) {
                    stringBuilder.append(' ');
                }
                stringArray[n4] = stringBuilder.toString();
            }
        }
        if (this.blockXLocation != -1) {
            Rectangle rectangle = this.getBlockSelectionPosition();
            n5 = rectangle.y;
            n4 = rectangle.height;
            n = rectangle.x;
            n3 = rectangle.width;
        } else {
            n4 = n5 = this.getFirstCaretLine();
            n3 = n = this.getPointAtOffset((int)this.caretOffsets[0]).x;
        }
        n6 = this.caretOffsets[0];
        int n10 = this.getFirstCaretLine();
        int n11 = 0;
        for (n2 = n5; n2 <= n4; ++n2) {
            String string4 = n11 < n7 ? stringArray[n11++] : "";
            int n12 = this.sendTextEvent(n, n3, n2, string4, bl);
            if (n2 != n10) continue;
            n6 = n12;
        }
        while (n11 < n7) {
            int n13 = this.sendTextEvent(n, n, n2, stringArray[n11++], bl);
            if (n2 == n10) {
                n6 = n13;
            }
            ++n2;
        }
        return n6;
    }

    void insertBlockSelectionText(char c, int n) {
        int n2;
        if (c == '\r' || c == '\n') {
            return;
        }
        Rectangle rectangle = this.getBlockSelectionPosition();
        int n3 = rectangle.y;
        int n4 = rectangle.height;
        int n5 = rectangle.x;
        int n6 = rectangle.width;
        int[] nArray = new int[]{0};
        int n7 = 0;
        int n8 = 0;
        String string = c != '\u0000' ? new String(new char[]{c}) : "";
        int n9 = string.length();
        for (n2 = n3; n2 <= n4; ++n2) {
            boolean bl;
            String string2 = this.content.getLine(n2);
            int n10 = this.content.getOffsetAtLine(n2);
            int n11 = n10 + string2.length();
            int n12 = this.getLinePixel(n2);
            int n13 = this.getOffsetAtPoint(n5, n12, nArray, true);
            boolean bl2 = bl = n13 == -1;
            n13 = bl ? (n5 < this.leftMargin ? n10 : n11) : (n13 += nArray[0]);
            int n14 = this.getOffsetAtPoint(n6, n12, nArray, true);
            n14 = n14 == -1 ? (n6 < this.leftMargin ? n10 : n11) : (n14 += nArray[0]);
            if (n13 > n14) {
                int n15 = n13;
                n13 = n14;
                n14 = n15;
            }
            if (n13 == n14 && !bl) {
                switch (n) {
                    case 8: {
                        if (n13 <= n10) break;
                        n13 = this.getClusterPrevious(n13, n2);
                        break;
                    }
                    case 127: {
                        if (n14 >= n11) break;
                        n14 = this.getClusterNext(n14, n2);
                    }
                }
            }
            if (bl) {
                if (string2.length() >= n8) {
                    n8 = string2.length();
                    n7 = n11 + n9;
                }
            } else {
                n7 = n13 + n9;
                n8 = this.content.getCharCount();
            }
            Event event = new Event();
            event.text = string;
            event.start = n13;
            event.end = n14;
            this.sendKeyEvent(event);
        }
        n2 = this.getPointAtOffset((int)n7).x;
        int n16 = this.getVerticalScrollOffset();
        this.setBlockSelectionLocation(n2, this.blockYAnchor - n16, n2, this.blockYLocation - n16, false);
    }

    void installDefaultContent() {
        this.textChangeListener = new lIIlI(this);
        this.content = new DefaultContent();
        this.content.addTextChangeListener(this.textChangeListener);
    }

    void installListeners() {
        ScrollBar scrollBar = this.getVerticalBar();
        ScrollBar scrollBar2 = this.getHorizontalBar();
        this.listener = this::lambda$installListeners$25;
        this.addListener(12, this.listener);
        this.addListener(1, this.listener);
        this.addListener(2, this.listener);
        this.addListener(35, this.listener);
        this.addListener(3, this.listener);
        this.addListener(4, this.listener);
        this.addListener(5, this.listener);
        this.addListener(9, this.listener);
        this.addListener(11, this.listener);
        this.addListener(31, this.listener);
        this.ime.addListener(43, this::lambda$installListeners$26);
        if (scrollBar != null) {
            scrollBar.addListener(13, this::handleVerticalScroll);
        }
        if (scrollBar2 != null) {
            scrollBar2.addListener(13, this::handleHorizontalScroll);
        }
    }

    void internalRedrawRange(int n, int n2) {
        Rectangle rectangle;
        int n3;
        Rectangle rectangle2;
        Rectangle rectangle3;
        Rectangle rectangle4;
        if (n2 <= 0) {
            return;
        }
        int n4 = n + n2;
        int n5 = this.content.getLineAtOffset(n);
        int n6 = this.content.getLineAtOffset(n4);
        int n7 = this.getPartialBottomIndex();
        int n8 = this.getPartialTopIndex();
        if (n5 > n7 || n6 < n8) {
            return;
        }
        if (n8 > n5) {
            n5 = n8;
            n = 0;
        } else {
            n -= this.content.getOffsetAtLine(n5);
        }
        if (n7 < n6) {
            n6 = n7 + 1;
            n4 = 0;
        } else {
            n4 -= this.content.getOffsetAtLine(n6);
        }
        TextLayout textLayout = this.renderer.getTextLayout(n5);
        int n9 = this.leftMargin - this.horizontalScrollOffset;
        int n10 = this.getLinePixel(n5);
        int[] nArray = textLayout.getLineOffsets();
        int n11 = textLayout.getLineIndex(Math.min(n, textLayout.getText().length()));
        if (this == false && n11 > 0 && nArray[n11] == n) {
            rectangle4 = textLayout.getLineBounds(n11 - 1);
            rectangle4.x = rectangle4.width;
            rectangle4.width = this.clientAreaWidth - this.rightMargin - rectangle4.x;
            rectangle3 = rectangle4;
            rectangle3.x += n9;
            rectangle2 = rectangle4;
            rectangle2.y += n10;
            super.redraw(rectangle4.x, rectangle4.y, rectangle4.width, rectangle4.height, false);
        }
        if (n5 == n6 && n11 == (n3 = textLayout.getLineIndex(Math.min(n4, textLayout.getText().length())))) {
            rectangle2 = rectangle3 = textLayout.getBounds(n, n4 - 1);
            rectangle3.x += n9;
            Rectangle rectangle5 = rectangle2;
            rectangle5.y += n10;
            super.redraw(rectangle2.x, rectangle2.y, rectangle2.width, rectangle2.height, false);
            this.renderer.disposeTextLayout(textLayout);
            return;
        }
        rectangle4 = textLayout.getBounds(n, nArray[n11 + 1] - 1);
        if (rectangle4.height == 0) {
            rectangle3 = textLayout.getLineBounds(n11);
            rectangle4.x = rectangle3.width;
            rectangle4.y = rectangle3.y;
            rectangle4.height = rectangle3.height;
        }
        rectangle3 = rectangle4;
        rectangle3.x += n9;
        rectangle2 = rectangle4;
        rectangle2.y += n10;
        rectangle4.width = this.clientAreaWidth - this.rightMargin - rectangle4.x;
        super.redraw(rectangle4.x, rectangle4.y, rectangle4.width, rectangle4.height, false);
        if (n5 != n6) {
            this.renderer.disposeTextLayout(textLayout);
            textLayout = this.renderer.getTextLayout(n6);
            nArray = textLayout.getLineOffsets();
        }
        int n12 = textLayout.getLineIndex(Math.min(n4, textLayout.getText().length()));
        Rectangle rectangle6 = textLayout.getBounds(nArray[n12], n4 - 1);
        if (rectangle6.height == 0) {
            rectangle = textLayout.getLineBounds(n12);
            rectangle6.y = rectangle.y;
            rectangle6.height = rectangle.height;
        }
        rectangle = rectangle6;
        rectangle.x += n9;
        Rectangle rectangle7 = rectangle6;
        rectangle7.y += this.getLinePixel(n6);
        super.redraw(rectangle6.x, rectangle6.y, rectangle6.width, rectangle6.height, false);
        this.renderer.disposeTextLayout(textLayout);
        int n13 = rectangle4.y + rectangle4.height;
        if (rectangle6.y > n13) {
            super.redraw(this.leftMargin, n13, this.clientAreaWidth - this.rightMargin - this.leftMargin, rectangle6.y - n13, false);
        }
    }

    void handleCompositionOffset(Event event) {
        int[] nArray = new int[]{0};
        event.index = this.getOffsetAtPoint(event.x, event.y, nArray, true);
        event.count = nArray[0];
    }

    void handleCompositionSelection(Event event) {
        if (event.start != event.end) {
            int n = this.getCharCount();
            event.start = Math.max(0, Math.min(event.start, n));
            event.end = Math.max(0, Math.min(event.end, n));
            if (event.text != null) {
                this.setSelection(event.start, event.end);
            } else {
                event.text = this.getTextRange(event.start, event.end - event.start);
            }
        } else {
            event.start = this.selection[0].x;
            event.end = this.selection[0].y;
            event.text = this.getSelectionText();
        }
    }

    void handleCompositionChanged(Event event) {
        String string = event.text;
        int n = event.start;
        int n2 = event.end;
        int n3 = this.content.getCharCount();
        n = Math.min(n, n3);
        n2 = Math.min(n2, n3);
        int n4 = string.length();
        if (n4 == this.ime.getCommitCount()) {
            this.content.replaceTextRange(n, n2 - n, "");
            this.setCaretOffsets(new int[]{this.ime.getCompositionOffset()}, -1);
            this.caretWidth = 0;
            this.caretDirection = 0;
        } else {
            this.content.replaceTextRange(n, n2 - n, string);
            int n5 = -1;
            if (this.ime.getWideCaret()) {
                n = this.ime.getCompositionOffset();
                for (int n6 : this.caretOffsets) {
                    int n7 = this.content.getLineAtOffset(n6);
                    int n8 = this.content.getOffsetAtLine(n7);
                    TextLayout textLayout = this.renderer.getTextLayout(n7);
                    this.caretWidth = textLayout.getBounds((int)(n - n8), (int)(n + n4 - 1 - n8)).width;
                    this.renderer.disposeTextLayout(textLayout);
                }
                n5 = 1;
            }
            this.setCaretOffsets(new int[]{this.ime.getCaretOffset()}, n5);
        }
        this.resetSelection();
        this.showCaret();
    }

    void handleDispose(Event event) {
        this.removeListener(12, this.listener);
        this.notifyListeners(12, event);
        event.type = 0;
        this.clipboard.dispose();
        if (this.renderer != null) {
            this.renderer.dispose();
            this.renderer = null;
        }
        if (this.content != null) {
            this.content.removeTextChangeListener(this.textChangeListener);
            this.content = null;
        }
        if (this.defaultCaret != null) {
            this.defaultCaret.dispose();
            this.defaultCaret = null;
        }
        if (this.leftCaretBitmap != null) {
            this.leftCaretBitmap.dispose();
            this.leftCaretBitmap = null;
        }
        if (this.rightCaretBitmap != null) {
            this.rightCaretBitmap.dispose();
            this.rightCaretBitmap = null;
        }
        if (this.carets != null) {
            for (Caret caret : this.carets) {
                if (caret == null) continue;
                caret.dispose();
            }
            this.carets = null;
        }
        if (this.isBidiCaret()) {
            BidiUtil.removeLanguageListener(this);
        }
        this.selectionBackground = null;
        this.selectionForeground = null;
        this.marginColor = null;
        this.textChangeListener = null;
        this.selection = null;
        this.doubleClickSelection = null;
        this.keyActionMap = null;
        this.background = null;
        this.foreground = null;
        this.clipboard = null;
        this.tabs = null;
    }

    void handleHorizontalScroll(Event event) {
        int n = this.getHorizontalBar().getSelection() - this.horizontalScrollOffset;
        this.scrollHorizontal(n, false);
    }

    void handleKey(Event event) {
        int n;
        int n2;
        this.caretAlignment = 0;
        if (event.keyCode != 0) {
            n2 = this.getKeyBinding(event.keyCode | event.stateMask);
        } else {
            n2 = this.getKeyBinding(event.character | event.stateMask);
            if (n2 == 0 && (event.stateMask & 0x40000) != 0 && event.character <= '\u001f') {
                n = event.character + 64;
                n2 = this.getKeyBinding(n | event.stateMask);
            }
        }
        if (n2 == 0) {
            n = 0;
            if (IS_MAC) {
                n = (event.stateMask & 0x440000) != 0 ? 1 : 0;
            } else {
                int n3 = n = event.stateMask == 65536 || event.stateMask == 262144 || event.stateMask == 196608 || event.stateMask == 393216 ? 1 : 0;
            }
            if (n == 0 && event.character > '\u001f' && event.character != '\u007f' || event.character == '\r' || event.character == '\n' || event.character == '\t') {
                this.doContent(event.character);
                this.update();
            }
        } else {
            this.invokeAction(n2);
        }
    }

    void handleKeyDown(Event event) {
        if (this.clipboardSelection == null) {
            this.clipboardSelection = new Point(this.selection[0].x, this.selection[0].y);
        }
        this.newOrientation = 0;
        event.stateMask &= SWT.MODIFIER_MASK;
        Event event2 = new Event();
        event2.character = event.character;
        event2.keyCode = event.keyCode;
        event2.keyLocation = event.keyLocation;
        event2.stateMask = event.stateMask;
        event2.doit = event.doit;
        this.notifyListeners(3005, event2);
        if (event2.doit) {
            if ((event.stateMask & SWT.MODIFIER_MASK) == 262144 && event.keyCode == 131072 && this.isBidiCaret()) {
                this.newOrientation = event.keyLocation == 16384 ? 0x2000000 : 0x4000000;
            }
            this.handleKey(event);
        }
    }

    void handleKeyUp(Event event) {
        if (this.clipboardSelection != null && (this.clipboardSelection.x != this.selection[0].x || this.clipboardSelection.y != this.selection[0].y)) {
            this.copySelection(2);
        }
        this.clipboardSelection = null;
        if (this.newOrientation != 0) {
            if (this.newOrientation != this.getOrientation()) {
                Event event2 = new Event();
                event2.doit = true;
                this.notifyListeners(44, event2);
                if (event2.doit) {
                    this.setOrientation(this.newOrientation);
                }
            }
            this.newOrientation = 0;
        }
    }

    void handleMenuDetect(Event event) {
        if (event.detail == 1) {
            Point point = this.getDisplay().map((Control)this, null, this.getPointAtOffset(this.caretOffsets[0]));
            event.x = point.x;
            event.y = point.y + this.getLineHeight(this.caretOffsets[0]);
        }
    }

    /*
     * Exception decompiling
     */
    void handleMouseDown(Event var1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl11 : ALOAD_1 - null : trying to set 0 previously set to 1
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

    void addSelection(int n, int n2) {
        int[] nArray = this.getSelectionRanges();
        nArray = Arrays.copyOf(nArray, nArray.length + 2);
        nArray[nArray.length - 2] = n;
        nArray[nArray.length - 1] = n2;
        this.setSelection(nArray, true, true);
    }

    void handleMouseMove(Event event) {
        if (this.clickCount > 0) {
            this.update();
            this.doAutoScroll(event);
            this.doMouseLocationChange(event.x, event.y, true);
        }
        if (this.renderer.hasLinks) {
            this.doMouseLinkCursor(event.x, event.y);
        }
    }

    void handleMouseUp(Event event) {
        this.middleClickPressed = false;
        this.clickCount = 0;
        this.endAutoScroll();
        if (event.button == 1) {
            this.copySelection(2);
        }
    }

    void handlePaint(Event event) {
        int n;
        if (event.width == 0 || event.height == 0) {
            return;
        }
        if (this.clientAreaWidth == 0 || this.clientAreaHeight == 0) {
            return;
        }
        int n2 = this.getLineIndex(event.y);
        int n3 = this.getLinePixel(n2);
        int n4 = event.y + event.height;
        GC gC = event.gc;
        Color color = this.getBackground();
        Color color2 = this.getForeground();
        if (n4 > 0) {
            n = this != false ? 1 : this.content.getLineCount();
            int n5 = this.leftMargin - this.horizontalScrollOffset;
            for (int i = n2; n3 < n4 && i < n; n3 += this.renderer.drawLine(i, n5, n3, gC, color, color2), ++i) {
            }
            if (n3 < n4) {
                gC.setBackground(color);
                this.drawBackground(gC, 0, n3, this.clientAreaWidth, n4 - n3);
            }
        }
        if (this.blockSelection && this.blockXLocation != -1) {
            gC.setBackground(this.getSelectionBackground());
            Rectangle rectangle = this.getBlockSelectionRectangle();
            gC.drawRectangle(rectangle.x, rectangle.y, Math.max(1, rectangle.width - 1), Math.max(1, rectangle.height - 1));
            gC.setAdvanced(true);
            if (gC.getAdvanced()) {
                gC.setAlpha(100);
                gC.fillRectangle(rectangle);
                gC.setAdvanced(false);
            }
        }
        if (this.carets != null) {
            for (n = 1; n < this.carets.length; ++n) {
                Caret caret = this.carets[n];
                if (!caret.isVisible()) continue;
                if (caret.getImage() != null) {
                    gC.drawImage(caret.getImage(), caret.getBounds().x, caret.getBounds().y);
                    continue;
                }
                gC.drawRectangle(caret.getBounds().x, caret.getBounds().y, caret.getBounds().width, this.getLineHeight(this.caretOffsets[n]));
            }
        }
        gC.setBackground(this.marginColor != null ? this.marginColor : color);
        if (this.topMargin > 0) {
            this.drawBackground(gC, 0, 0, this.clientAreaWidth, this.topMargin);
        }
        if (this.bottomMargin > 0) {
            this.drawBackground(gC, 0, this.clientAreaHeight - this.bottomMargin, this.clientAreaWidth, this.bottomMargin);
        }
        if (this.leftMargin - this.alignmentMargin > 0) {
            this.drawBackground(gC, 0, 0, this.leftMargin - this.alignmentMargin, this.clientAreaHeight);
        }
        if (this.rightMargin > 0) {
            this.drawBackground(gC, this.clientAreaWidth - this.rightMargin, 0, this.rightMargin, this.clientAreaHeight);
        }
    }

    void handleResize(Event event) {
        int n = this.clientAreaHeight;
        int n2 = this.clientAreaWidth;
        Rectangle rectangle = this.getClientArea();
        this.clientAreaHeight = rectangle.height;
        this.clientAreaWidth = rectangle.width;
        if (!this.alwaysShowScroll && this.ignoreResize != 0) {
            return;
        }
        this.redrawMargins(n, n2);
        if (this.wordWrap) {
            if (n2 != this.clientAreaWidth) {
                this.renderer.reset(0, this.content.getLineCount());
                this.verticalScrollOffset = -1;
                this.renderer.calculateIdle();
                super.redraw();
            }
            if (n != this.clientAreaHeight) {
                if (n == 0) {
                    this.topIndexY = 0;
                }
                this.setScrollBars(true);
            }
            this.setCaretLocations();
        } else {
            ScrollBar scrollBar;
            this.renderer.calculateClientArea();
            this.setScrollBars(true);
            this.claimRightFreeSpace();
            if (this.clientAreaWidth != 0 && (scrollBar = this.getHorizontalBar()) != null && scrollBar.getVisible() && this.horizontalScrollOffset != scrollBar.getSelection()) {
                scrollBar.setSelection(this.horizontalScrollOffset);
                this.horizontalScrollOffset = scrollBar.getSelection();
            }
        }
        this.updateCaretVisibility();
        this.claimBottomFreeSpace();
        this.setAlignment();
    }

    void handleTextChanged(TextChangedEvent textChangedEvent) {
        int n = this.ime.getCompositionOffset();
        if (n != -1 && this.lastTextChangeStart < n) {
            this.ime.setCompositionOffset(n + this.lastTextChangeNewCharCount - this.lastTextChangeReplaceCharCount);
        }
        int n2 = this.content.getLineAtOffset(this.lastTextChangeStart);
        this.resetCache(n2, 0);
        if (this == false && this.topIndex > n2) {
            this.topIndex = n2;
            if (this.topIndex < 0) {
                System.err.println("StyledText: topIndex was " + this.topIndex + ", lastTextChangeStart = " + this.lastTextChangeStart + ", content.getClass() = " + this.content.getClass());
                this.topIndex = 0;
            }
            this.topIndexY = 0;
            super.redraw();
        } else {
            int n3 = n2 + this.lastTextChangeNewLineCount;
            int n4 = this.getLinePixel(n2);
            int n5 = this.getLinePixel(n3 + 1);
            if (this.lastLineBottom != n5) {
                super.redraw();
            } else {
                super.redraw(0, n4, this.clientAreaWidth, n5 - n4, false);
                this.redrawLinesBullet(this.renderer.redrawLines);
            }
        }
        this.renderer.redrawLines = null;
        if (!this.blockSelection || this.blockXLocation == -1) {
            this.updateSelection(this.lastTextChangeStart, this.lastTextChangeReplaceCharCount, this.lastTextChangeNewCharCount);
        }
        if (this.lastTextChangeReplaceLineCount > 0 || this.wordWrap || this.visualWrap) {
            this.claimBottomFreeSpace();
        }
        if (this.lastTextChangeReplaceCharCount > 0) {
            this.claimRightFreeSpace();
        }
        this.sendAccessibleTextChanged(this.lastTextChangeStart, this.lastTextChangeNewCharCount, 0);
        this.lastCharCount += this.lastTextChangeNewCharCount;
        this.lastCharCount -= this.lastTextChangeReplaceCharCount;
        this.setAlignment();
    }

    void handleTextChanging(TextChangingEvent textChangingEvent) {
        int n;
        int n2;
        if (textChangingEvent.replaceCharCount < 0) {
            textChangingEvent.start += textChangingEvent.replaceCharCount;
            textChangingEvent.replaceCharCount *= -1;
        }
        this.lastTextChangeStart = textChangingEvent.start;
        this.lastTextChangeNewLineCount = textChangingEvent.newLineCount;
        this.lastTextChangeNewCharCount = textChangingEvent.newCharCount;
        this.lastTextChangeReplaceLineCount = textChangingEvent.replaceLineCount;
        this.lastTextChangeReplaceCharCount = textChangingEvent.replaceCharCount;
        int n3 = this.content.getLineAtOffset(textChangingEvent.start);
        int n4 = this.getLinePixel(n3 + textChangingEvent.replaceLineCount + 1);
        this.lastLineBottom = n2 = this.getLinePixel(n3 + 1) + textChangingEvent.newLineCount * this.renderer.getLineHeight();
        if (n4 < 0 && n2 < 0) {
            this.lastLineBottom += n4 - n2;
            this.verticalScrollOffset += n2 - n4;
            this.calculateTopIndex(n2 - n4);
            this.setScrollBars(true);
        } else {
            this.scrollText(n4, n2);
        }
        this.sendAccessibleTextChanged(this.lastTextChangeStart, 0, this.lastTextChangeReplaceCharCount);
        this.renderer.textChanging(textChangingEvent);
        int n5 = this.content.getCharCount() - textChangingEvent.replaceCharCount + textChangingEvent.newCharCount;
        for (n = 0; n < this.caretOffsets.length && this.caretOffsets[this.caretOffsets.length - 1 - n] > n5; ++n) {
        }
        if (n != 0) {
            int[] nArray = Arrays.copyOf(this.caretOffsets, this.caretOffsets.length - n + 1);
            nArray[nArray.length - 1] = n5;
            this.setCaretOffsets(nArray, -1);
        }
    }

    void handleTextSet(TextChangedEvent textChangedEvent) {
        this.reset();
        int n = this.getCharCount();
        this.sendAccessibleTextChanged(0, n, this.lastCharCount);
        this.lastCharCount = n;
        this.setAlignment();
    }

    void handleTraverse(Event event) {
        switch (event.detail) {
            case 2: 
            case 256: 
            case 512: {
                event.doit = true;
                break;
            }
            case 4: 
            case 8: 
            case 16: {
                if ((this.getStyle() & 4) != 0) {
                    event.doit = true;
                    break;
                }
                if (this.editable && (event.stateMask & SWT.MODIFIER_MASK) == 0) break;
                event.doit = true;
            }
        }
    }

    void handleVerticalScroll(Event event) {
        int n = this.getVerticalBar().getSelection() - this.getVerticalScrollOffset();
        this.scrollVertical(n, false);
    }

    void initializeAccessible() {
        this.acc = this.getAccessible();
        this.accAdapter = new lIIlIl(this);
        this.acc.addAccessibleListener(this.accAdapter);
        this.accTextExtendedAdapter = new lllI(this);
        this.acc.addAccessibleTextListener(this.accTextExtendedAdapter);
        this.accEditableTextListener = new llIlI(this);
        this.acc.addAccessibleEditableTextListener(this.accEditableTextListener);
        this.accAttributeAdapter = new llll(this);
        this.acc.addAccessibleAttributeListener(this.accAttributeAdapter);
        this.accControlAdapter = new lIIII(this);
        this.acc.addAccessibleControlListener(this.accControlAdapter);
        this.addListener(15, this::lambda$initializeAccessible$28);
    }

    @Override
    public void dispose() {
        if (!this.isDisposed()) {
            this.acc.removeAccessibleControlListener(this.accControlAdapter);
            this.acc.removeAccessibleAttributeListener(this.accAttributeAdapter);
            this.acc.removeAccessibleEditableTextListener(this.accEditableTextListener);
            this.acc.removeAccessibleTextListener(this.accTextExtendedAdapter);
            this.acc.removeAccessibleListener(this.accAdapter);
        }
        super.dispose();
    }

    String getAssociatedLabel() {
        Control[] controlArray = this.getParent().getChildren();
        for (int i = 0; i < controlArray.length; ++i) {
            if (controlArray[i] != this) continue;
            if (i <= 0) break;
            Control control = controlArray[i - 1];
            if (control instanceof Label) {
                return ((Label)control).getText();
            }
            if (!(control instanceof CLabel)) break;
            return ((CLabel)control).getText();
        }
        return null;
    }

    String stripMnemonic(String string) {
        int n = 0;
        int n2 = string.length();
        while (true) {
            if (n < n2 && string.charAt(n) != '&') {
                ++n;
                continue;
            }
            if (++n >= n2) {
                return string;
            }
            if (string.charAt(n) != '&') {
                return string.substring(0, n - 1) + string.substring(n, n2);
            }
            if (++n >= n2) break;
        }
        return string;
    }

    char _findMnemonic(String string) {
        if (string == null) {
            return '\u0000';
        }
        int n = 0;
        int n2 = string.length();
        while (true) {
            if (n < n2 && string.charAt(n) != '&') {
                ++n;
                continue;
            }
            if (++n >= n2) {
                return '\u0000';
            }
            if (string.charAt(n) != '&') {
                return Character.toLowerCase(string.charAt(n));
            }
            if (++n >= n2) break;
        }
        return '\u0000';
    }

    public void invokeAction(int n) {
        this.checkWidget();
        if (this.blockSelection && this != n) {
            return;
        }
        this.updateCaretDirection = true;
        switch (n) {
            case 0x1000001: {
                this.doLineUp(false);
                this.clearSelection(true);
                break;
            }
            case 0x1000002: {
                this.doLineDown(false);
                this.clearSelection(true);
                break;
            }
            case 0x1000007: {
                this.doLineStart();
                this.clearSelection(true);
                break;
            }
            case 0x1000008: {
                this.doLineEnd();
                this.clearSelection(true);
                break;
            }
            case 0x1000003: {
                this.doCursorPrevious();
                this.clearSelection(true);
                break;
            }
            case 0x1000004: {
                this.doCursorNext();
                this.clearSelection(true);
                break;
            }
            case 0x1000005: {
                this.doPageUp(false, -1);
                this.clearSelection(true);
                break;
            }
            case 0x1000006: {
                this.doPageDown(false, -1);
                this.clearSelection(true);
                break;
            }
            case 17039363: {
                this.doWordPrevious();
                this.clearSelection(true);
                break;
            }
            case 0x1040004: {
                this.doWordNext();
                this.clearSelection(true);
                break;
            }
            case 17039367: {
                this.doContentStart();
                this.clearSelection(true);
                break;
            }
            case 17039368: {
                this.doContentEnd();
                this.clearSelection(true);
                break;
            }
            case 17039365: {
                this.doPageStart();
                this.clearSelection(true);
                break;
            }
            case 17039366: {
                this.doPageEnd();
                this.clearSelection(true);
                break;
            }
            case 0x1020001: {
                this.doSelectionLineUp();
                break;
            }
            case 262209: {
                this.selectAll();
                break;
            }
            case 0x1020002: {
                this.doSelectionLineDown();
                break;
            }
            case 16908295: {
                this.doLineStart();
                this.doSelection(0x1000003);
                break;
            }
            case 16908296: {
                this.doLineEnd();
                this.doSelection(0x1000004);
                break;
            }
            case 16908291: {
                this.doSelectionCursorPrevious();
                this.doSelection(0x1000003);
                break;
            }
            case 16908292: {
                this.doSelectionCursorNext();
                this.doSelection(0x1000004);
                break;
            }
            case 16908293: {
                this.doSelectionPageUp(-1);
                break;
            }
            case 16908294: {
                this.doSelectionPageDown(-1);
                break;
            }
            case 17170435: {
                this.doSelectionWordPrevious();
                this.doSelection(0x1000003);
                break;
            }
            case 17170436: {
                this.doSelectionWordNext();
                this.doSelection(0x1000004);
                break;
            }
            case 17170439: {
                this.doContentStart();
                this.doSelection(0x1000003);
                break;
            }
            case 17170440: {
                this.doContentEnd();
                this.doSelection(0x1000004);
                break;
            }
            case 17170437: {
                this.doPageStart();
                this.doSelection(0x1000003);
                break;
            }
            case 0x1060006: {
                this.doPageEnd();
                this.doSelection(0x1000004);
                break;
            }
            case 131199: {
                this.cut();
                break;
            }
            case 17039369: {
                this.copy();
                break;
            }
            case 16908297: {
                this.paste();
                break;
            }
            case 8: {
                this.doBackspace();
                break;
            }
            case 127: {
                this.doDelete();
                break;
            }
            case 262152: {
                this.doDeleteWordPrevious();
                break;
            }
            case 262271: {
                this.doDeleteWordNext();
                break;
            }
            case 0x1000009: {
                this.overwrite = !this.overwrite;
                break;
            }
            case 0x100000A: {
                this.setBlockSelection(!this.blockSelection);
            }
        }
    }

    boolean isBidiCaret() {
        return BidiUtil.isBidiPlatform();
    }

    public boolean isTextSelected() {
        this.checkWidget();
        if (this.blockSelection && this.blockXLocation != -1) {
            Rectangle rectangle = this.getBlockSelectionPosition();
            return !rectangle.isEmpty();
        }
        return Arrays.stream(this.selection).anyMatch(StyledText::lambda$isTextSelected$29);
    }

    void modifyContent(Event event, boolean bl) {
        event.doit = true;
        this.notifyListeners(25, event);
        if (event.doit) {
            StyledTextEvent styledTextEvent = null;
            int n = event.end - event.start;
            if (this.isListening(3000)) {
                styledTextEvent = new StyledTextEvent(this.content);
                styledTextEvent.start = event.start;
                styledTextEvent.end = event.start + event.text.length();
                styledTextEvent.text = this.content.getTextRange(event.start, n);
            }
            if (bl && event.text.length() == 0) {
                int n2 = this.content.getLineAtOffset(event.start);
                int n3 = this.content.getOffsetAtLine(n2);
                TextLayout textLayout = this.renderer.getTextLayout(n2);
                int n4 = textLayout.getLevel(event.start - n3);
                int n5 = this.content.getLineAtOffset(event.end);
                if (n2 != n5) {
                    this.renderer.disposeTextLayout(textLayout);
                    n3 = this.content.getOffsetAtLine(n5);
                    textLayout = this.renderer.getTextLayout(n5);
                }
                int n6 = textLayout.getLevel(event.end - n3);
                this.renderer.disposeTextLayout(textLayout);
                this.caretAlignment = n4 != n6 ? 0 : 1;
            }
            this.content.replaceTextRange(event.start, n, event.text);
            if (bl && (!this.blockSelection || this.blockXLocation == -1)) {
                this.setSelection(Arrays.stream(this.selection).map(arg_0 -> StyledText.lambda$modifyContent$30(event, arg_0)).flatMapToInt(StyledText::lambda$modifyContent$31).toArray(), true, false);
                this.showCaret();
            }
            this.notifyListeners(24, event);
            if (this.isListening(3000)) {
                this.notifyListeners(3000, styledTextEvent);
            }
        }
    }

    void paintObject(GC gC, int n, int n2, int n3, int n4, StyleRange styleRange, Bullet bullet, int n5) {
        if (this.isListening(3008)) {
            StyledTextEvent styledTextEvent = new StyledTextEvent(this.content);
            styledTextEvent.gc = gC;
            styledTextEvent.x = n;
            styledTextEvent.y = n2;
            styledTextEvent.ascent = n3;
            styledTextEvent.descent = n4;
            styledTextEvent.style = styleRange;
            styledTextEvent.bullet = bullet;
            styledTextEvent.bulletIndex = n5;
            this.notifyListeners(3008, styledTextEvent);
        }
    }

    public void paste() {
        this.checkWidget();
        String string = (String)this.getClipboardContent(1);
        if (string != null && string.length() > 0) {
            int n;
            if (this.blockSelection) {
                boolean bl = this == false && this.renderer.fixedPitch;
                int n2 = this.insertBlockSelectionText(string, bl);
                this.setCaretOffsets(new int[]{n2}, -1);
                this.clearBlockSelection(true, true);
                this.setCaretLocations();
                return;
            }
            if (this.getSelectionRanges().length / 2 > 1) {
                this.insertMultiSelectionText(string);
                this.setCaretLocations();
                return;
            }
            Event event = new Event();
            event.start = this.selection[0].x;
            event.end = this.selection[0].y;
            String string2 = this.getModelDelimitedText(string);
            if (this.textLimit > 0 && (n = this.getCharCount() - (this.selection[0].y - this.selection[0].x)) + string2.length() > this.textLimit) {
                int n3 = this.textLimit - n;
                string2 = string2.substring(0, Math.max(n3, 0));
            }
            event.text = string2;
            this.sendKeyEvent(event);
        }
    }

    private void insertMultiSelectionText(String string) {
        String[] stringArray = string.split(PlatformLineDelimiter);
        int[] nArray = this.getSelectionRanges();
        for (int i = nArray.length / 2 - 1; i >= 0; --i) {
            int n = nArray[2 * i];
            int n2 = nArray[2 * i + 1];
            String string2 = stringArray.length > i ? stringArray[i] : stringArray[stringArray.length - 1];
            Event event = new Event();
            event.start = n;
            event.end = n + n2;
            event.text = string2;
            this.sendKeyEvent(event);
        }
    }

    private void pasteOnMiddleClick(Event event) {
        String string = (String)this.getClipboardContent(2);
        if (string != null && string.length() > 0) {
            this.doMouseLocationChange(event.x, event.y, false);
            Event event2 = new Event();
            event2.start = this.selection[0].x;
            event2.end = this.selection[0].y;
            event2.text = this.getModelDelimitedText(string);
            this.sendKeyEvent(event2);
        }
    }

    public void print() {
        this.checkWidget();
        Printer printer = new Printer();
        StyledTextPrintOptions styledTextPrintOptions = new StyledTextPrintOptions();
        styledTextPrintOptions.printTextForeground = true;
        styledTextPrintOptions.printTextBackground = true;
        styledTextPrintOptions.printTextFontStyle = true;
        styledTextPrintOptions.printLineBackground = true;
        new Printing(this, printer, styledTextPrintOptions).run();
        printer.dispose();
    }

    public Runnable print(Printer printer) {
        this.checkWidget();
        if (printer == null) {
            SWT.error(4);
        }
        StyledTextPrintOptions styledTextPrintOptions = new StyledTextPrintOptions();
        styledTextPrintOptions.printTextForeground = true;
        styledTextPrintOptions.printTextBackground = true;
        styledTextPrintOptions.printTextFontStyle = true;
        styledTextPrintOptions.printLineBackground = true;
        return this.print(printer, styledTextPrintOptions);
    }

    public Runnable print(Printer printer, StyledTextPrintOptions styledTextPrintOptions) {
        this.checkWidget();
        if (printer == null || styledTextPrintOptions == null) {
            SWT.error(4);
        }
        return new Printing(this, printer, styledTextPrintOptions);
    }

    @Override
    public void redraw() {
        super.redraw();
        int n = this.getPartialBottomIndex() - this.topIndex + 1;
        this.renderer.reset(this.topIndex, n);
        this.renderer.calculate(this.topIndex, n);
        this.setScrollBars(false);
        this.doMouseLinkCursor();
    }

    @Override
    public void redraw(int n, int n2, int n3, int n4, boolean bl) {
        super.redraw(n, n2, n3, n4, bl);
        if (n4 > 0) {
            int n5 = this.getLineIndex(n2);
            int n6 = this.getLineIndex(n2 + n4);
            this.resetCache(n5, n6 - n5 + 1);
            this.doMouseLinkCursor();
        }
    }

    void redrawLines(int n, int n2, boolean bl) {
        int n3 = n + n2 - 1;
        int n4 = this.getPartialBottomIndex();
        int n5 = this.getPartialTopIndex();
        if (n > n4 || n3 < n5) {
            return;
        }
        if (n < n5) {
            n = n5;
        }
        if (n3 > n4) {
            n3 = n4;
        }
        int n6 = this.getLinePixel(n);
        int n7 = this.getLinePixel(n3 + 1);
        if (bl) {
            n7 = this.clientAreaHeight - this.bottomMargin;
        }
        int n8 = this.clientAreaWidth - this.leftMargin - this.rightMargin;
        super.redraw(this.leftMargin, n6, n8, n7 - n6, true);
    }

    void redrawLinesBullet(int[] nArray) {
        if (nArray == null) {
            return;
        }
        int n = this.getPartialTopIndex();
        int n2 = this.getPartialBottomIndex();
        int[] nArray2 = nArray;
        int n3 = nArray2.length;
        for (int i = 0; i < n3; ++i) {
            int n4;
            int n5 = n4 = nArray2[i];
            if (n > n4 || n4 > n2) continue;
            int n6 = -1;
            Bullet bullet = this.renderer.getLineBullet(n4, null);
            if (bullet != null) {
                StyleRange styleRange = bullet.style;
                GlyphMetrics glyphMetrics = styleRange.metrics;
                n6 = glyphMetrics.width;
            }
            if (n6 == -1) {
                n6 = this.getClientArea().width;
            }
            int n7 = this.renderer.getLineHeight(n4);
            int n8 = this.getLinePixel(n4);
            super.redraw(0, n8, n6, n7, false);
        }
    }

    void redrawMargins(int n, int n2) {
        int n3;
        if (n2 != this.clientAreaWidth && this.rightMargin > 0) {
            n3 = (n2 < this.clientAreaWidth ? n2 : this.clientAreaWidth) - this.rightMargin;
            super.redraw(n3, 0, this.rightMargin, n, false);
        }
        if (n != this.clientAreaHeight && this.bottomMargin > 0) {
            n3 = (n < this.clientAreaHeight ? n : this.clientAreaHeight) - this.bottomMargin;
            super.redraw(0, n3, n2, this.bottomMargin, false);
        }
    }

    public void redrawRange(int n, int n2, boolean bl) {
        this.checkWidget();
        int n3 = n + n2;
        int n4 = this.content.getCharCount();
        if (n > n3 || n < 0 || n3 > n4) {
            SWT.error(6);
        }
        int n5 = this.content.getLineAtOffset(n);
        int n6 = this.content.getLineAtOffset(n3);
        this.resetCache(n5, n6 - n5 + 1);
        this.internalRedrawRange(n, n2);
        this.doMouseLinkCursor();
    }

    public void removeBidiSegmentListener(BidiSegmentListener bidiSegmentListener) {
        this.checkWidget();
        if (bidiSegmentListener == null) {
            SWT.error(4);
        }
        this.removeListener(3007, bidiSegmentListener);
        this.resetCache(0, this.content.getLineCount());
        this.setCaretLocations();
        super.redraw();
    }

    public void removeCaretListener(CaretListener caretListener) {
        this.checkWidget();
        if (caretListener == null) {
            SWT.error(4);
        }
        this.removeListener(3011, caretListener);
    }

    public void removeExtendedModifyListener(ExtendedModifyListener extendedModifyListener) {
        this.checkWidget();
        if (extendedModifyListener == null) {
            SWT.error(4);
        }
        this.removeListener(3000, extendedModifyListener);
    }

    public void removeLineBackgroundListener(LineBackgroundListener lineBackgroundListener) {
        this.checkWidget();
        if (lineBackgroundListener == null) {
            SWT.error(4);
        }
        this.removeListener(3001, lineBackgroundListener);
    }

    public void removeLineStyleListener(LineStyleListener lineStyleListener) {
        this.checkWidget();
        if (lineStyleListener == null) {
            SWT.error(4);
        }
        this.removeListener(3002, lineStyleListener);
        this.setCaretLocations();
    }

    public void removeModifyListener(ModifyListener modifyListener) {
        this.checkWidget();
        if (modifyListener == null) {
            SWT.error(4);
        }
        this.removeListener(24, modifyListener);
    }

    public void removePaintObjectListener(PaintObjectListener paintObjectListener) {
        this.checkWidget();
        if (paintObjectListener == null) {
            SWT.error(4);
        }
        this.removeListener(3008, paintObjectListener);
    }

    public void removeSelectionListener(SelectionListener selectionListener) {
        this.checkWidget();
        if (selectionListener == null) {
            SWT.error(4);
        }
        this.removeListener(13, selectionListener);
    }

    public void removeVerifyListener(VerifyListener verifyListener) {
        this.checkWidget();
        if (verifyListener == null) {
            SWT.error(4);
        }
        this.removeListener(25, verifyListener);
    }

    public void removeVerifyKeyListener(VerifyKeyListener verifyKeyListener) {
        if (verifyKeyListener == null) {
            SWT.error(4);
        }
        this.removeListener(3005, verifyKeyListener);
    }

    public void removeWordMovementListener(MovementListener movementListener) {
        this.checkWidget();
        if (movementListener == null) {
            SWT.error(4);
        }
        this.removeListener(3009, movementListener);
        this.removeListener(3010, movementListener);
    }

    public void replaceStyleRanges(int n, int n2, StyleRange[] styleRangeArray) {
        this.checkWidget();
        if (this.isListening(3002)) {
            return;
        }
        if (styleRangeArray == null) {
            SWT.error(4);
        }
        this.setStyleRanges(n, n2, null, styleRangeArray, false);
    }

    public void replaceTextRange(int n, int n2, String string) {
        this.checkWidget();
        if (string == null) {
            SWT.error(4);
        }
        int n3 = this.getCharCount();
        int n4 = n + n2;
        if (n > n4 || n < 0 || n4 > n3) {
            SWT.error(6);
        }
        Event event = new Event();
        event.start = n;
        event.end = n4;
        event.text = string;
        this.modifyContent(event, false);
    }

    void reset() {
        ScrollBar scrollBar = this.getVerticalBar();
        ScrollBar scrollBar2 = this.getHorizontalBar();
        this.setCaretOffsets(new int[]{0}, -1);
        this.topIndex = 0;
        this.topIndexY = 0;
        this.verticalScrollOffset = 0;
        this.horizontalScrollOffset = 0;
        this.resetSelection();
        this.renderer.setContent(this.content);
        if (scrollBar != null) {
            scrollBar.setSelection(0);
        }
        if (scrollBar2 != null) {
            scrollBar2.setSelection(0);
        }
        this.resetCache(0, 0);
        this.setCaretLocations();
        super.redraw();
    }

    void resetBidiData() {
        this.caretDirection = 0;
        this.resetCache(0, this.content.getLineCount());
        this.setCaretLocations();
        this.keyActionMap.clear();
        this.createKeyBindings();
        super.redraw();
    }

    void resetCache(SortedSet sortedSet) {
        if (sortedSet == null || sortedSet.isEmpty()) {
            return;
        }
        int n = this.renderer.maxWidthLineIndex;
        this.renderer.reset(sortedSet);
        this.renderer.calculateClientArea();
        if (0 <= n && n < this.content.getLineCount()) {
            this.renderer.calculate(n, 1);
        }
        this.setScrollBars(true);
        if (this == false) {
            if (this.topIndex > (Integer)sortedSet.iterator().next()) {
                this.verticalScrollOffset = -1;
            }
            this.renderer.calculateIdle();
        }
    }

    void resetCache(int n, int n2) {
        int n3 = this.renderer.maxWidthLineIndex;
        this.renderer.reset(n, n2);
        this.renderer.calculateClientArea();
        if (0 <= n3 && n3 < this.content.getLineCount()) {
            this.renderer.calculate(n3, 1);
        }
        this.setScrollBars(true);
        if (this == false) {
            if (this.topIndex > n) {
                this.verticalScrollOffset = -1;
            }
            this.renderer.calculateIdle();
        }
    }

    void resetSelection() {
        this.selection = (Point[])Arrays.stream(this.caretOffsets).mapToObj(StyledText::lambda$resetSelection$32).toArray(StyledText::lambda$resetSelection$33);
        this.selectionAnchors = Arrays.copyOf(this.caretOffsets, this.caretOffsets.length);
        this.sendAccessibleTextCaretMoved();
    }

    @Override
    public void scroll(int n, int n2, int n3, int n4, int n5, int n6, boolean bl) {
        super.scroll(n, n2, n3, n4, n5, n6, false);
        if (bl) {
            int n7 = n - n3;
            int n8 = n2 - n4;
            for (Control control : this.getChildren()) {
                Rectangle rectangle = control.getBounds();
                control.setLocation(rectangle.x + n7, rectangle.y + n8);
            }
        }
    }

    boolean scrollHorizontal(int n, boolean bl) {
        if (n == 0) {
            return false;
        }
        if (this.wordWrap) {
            return false;
        }
        ScrollBar scrollBar = this.getHorizontalBar();
        if (scrollBar != null && bl) {
            scrollBar.setSelection(this.horizontalScrollOffset + n);
        }
        int n2 = this.clientAreaHeight - this.topMargin - this.bottomMargin;
        if (n > 0) {
            int n3 = this.leftMargin + n;
            int n4 = this.clientAreaWidth - n3 - this.rightMargin;
            if (n4 > 0) {
                this.scroll(this.leftMargin, this.topMargin, n3, this.topMargin, n4, n2, true);
            }
            if (n3 > n4) {
                super.redraw(this.leftMargin + n4, this.topMargin, n - n4, n2, true);
            }
        } else {
            int n5 = this.leftMargin - n;
            int n6 = this.clientAreaWidth - n5 - this.rightMargin;
            if (n6 > 0) {
                this.scroll(n5, this.topMargin, this.leftMargin, this.topMargin, n6, n2, true);
            }
            if (n5 > n6) {
                super.redraw(this.leftMargin + n6, this.topMargin, -n - n6, n2, true);
            }
        }
        this.horizontalScrollOffset += n;
        this.setCaretLocations();
        return true;
    }

    boolean scrollVertical(int n, boolean bl) {
        if (n == 0) {
            return false;
        }
        if (this.verticalScrollOffset != -1) {
            Control[] controlArray;
            int n2;
            int n3;
            ScrollBar scrollBar = this.getVerticalBar();
            if (scrollBar != null && bl) {
                scrollBar.setSelection(this.verticalScrollOffset + n);
            }
            int n4 = 0;
            if (n > 0) {
                n3 = this.topMargin + n;
                n2 = this.clientAreaHeight - n3 - this.bottomMargin;
                if (n2 > 0) {
                    n4 = -n;
                }
            } else {
                n3 = this.topMargin - n;
                n2 = this.clientAreaHeight - n3 - this.bottomMargin;
                if (n2 > 0) {
                    n4 = -n;
                }
            }
            Control[] controlArray2 = controlArray = this.getChildren();
            for (Control control : controlArray) {
                Rectangle rectangle = control.getBounds();
                control.setLocation(rectangle.x, rectangle.y + n4);
            }
            this.verticalScrollOffset += n;
            this.calculateTopIndex(n);
            super.redraw();
        } else {
            this.calculateTopIndex(n);
            super.redraw();
        }
        this.setCaretLocations();
        return true;
    }

    void scrollText(int n, int n2) {
        if (n == n2) {
            return;
        }
        int n3 = n2 - n;
        int n4 = this.clientAreaWidth - this.leftMargin - this.rightMargin;
        int n5 = n3 > 0 ? this.clientAreaHeight - n - this.bottomMargin : this.clientAreaHeight - n2 - this.bottomMargin;
        this.scroll(this.leftMargin, n2, this.leftMargin, n, n4, n5, true);
        if (0 < n + n5 && this.topMargin > n) {
            super.redraw(this.leftMargin, n3, n4, this.topMargin, false);
        }
        if (0 < n2 + n5 && this.topMargin > n2) {
            super.redraw(this.leftMargin, 0, n4, this.topMargin, false);
        }
        if (this.clientAreaHeight - this.bottomMargin < n + n5 && this.clientAreaHeight > n) {
            super.redraw(this.leftMargin, this.clientAreaHeight - this.bottomMargin + n3, n4, this.bottomMargin, false);
        }
        if (this.clientAreaHeight - this.bottomMargin < n2 + n5 && this.clientAreaHeight > n2) {
            super.redraw(this.leftMargin, this.clientAreaHeight - this.bottomMargin, n4, this.bottomMargin, false);
        }
    }

    void sendAccessibleTextCaretMoved() {
        if (Arrays.stream(this.caretOffsets).noneMatch(this::lambda$sendAccessibleTextCaretMoved$34)) {
            this.accCaretOffset = this.caretOffsets[0];
            this.getAccessible().textCaretMoved(this.caretOffsets[0]);
        }
    }

    void sendAccessibleTextChanged(int n, int n2, int n3) {
        Accessible accessible = this.getAccessible();
        if (n3 != 0) {
            accessible.textChanged(1, n, n3);
        }
        if (n2 != 0) {
            accessible.textChanged(0, n, n2);
        }
    }

    public void selectAll() {
        this.checkWidget();
        if (this.blockSelection) {
            this.renderer.calculate(0, this.content.getLineCount());
            this.setScrollBars(false);
            int n = this.getVerticalScrollOffset();
            int n2 = this.leftMargin - this.horizontalScrollOffset;
            int n3 = this.topMargin - n;
            int n4 = this.renderer.getWidth() - this.rightMargin - this.horizontalScrollOffset;
            int n5 = this.renderer.getHeight() - this.bottomMargin - n;
            this.setBlockSelectionLocation(n2, n3, n4, n5, false);
            return;
        }
        this.setSelection(0, Math.max(this.getCharCount(), 0));
    }

    void sendKeyEvent(Event event) {
        if (this.editable) {
            this.modifyContent(event, true);
        }
    }

    StyledTextEvent sendLineEvent(int n, int n2, String string) {
        StyledTextEvent styledTextEvent = null;
        if (this.isListening(n)) {
            styledTextEvent = new StyledTextEvent(this.content);
            styledTextEvent.detail = n2;
            styledTextEvent.text = string;
            styledTextEvent.alignment = this.alignment;
            styledTextEvent.indent = this.indent;
            styledTextEvent.wrapIndent = this.wrapIndent;
            styledTextEvent.justify = this.justify;
            this.notifyListeners(n, styledTextEvent);
        }
        return styledTextEvent;
    }

    void sendSelectionEvent() {
        this.getAccessible().textSelectionChanged();
        Event event = new Event();
        event.x = this.selection[0].x;
        event.y = this.selection[this.selection.length - 1].y;
        this.notifyListeners(13, event);
    }

    int sendTextEvent(int n, int n2, int n3, String string, boolean bl) {
        int n4;
        int n5;
        int n6;
        int n7;
        Object object;
        int n8 = 0;
        StringBuilder stringBuilder = new StringBuilder();
        if (n3 < this.content.getLineCount()) {
            object = new int[]{0};
            n7 = this.getOffsetAtPoint(n, this.getLinePixel(n3), (int[])object, true);
            if (n7 == -1) {
                n6 = this.content.getOffsetAtLine(n3);
                n5 = this.content.getLine(n3).length();
                n4 = n7 = n6 + n5;
                if (bl) {
                    TextLayout textLayout = this.renderer.getTextLayout(n3);
                    n8 = textLayout.getBounds().width;
                    this.renderer.disposeTextLayout(textLayout);
                }
            } else {
                n4 = n == n2 ? (n7 += object[0]) : this.getOffsetAtPoint(n2, 0, n3, null);
                bl = false;
            }
        } else {
            n4 = n7 = this.content.getCharCount();
            stringBuilder.append(this.content.getLineDelimiter());
        }
        if (n7 > n4) {
            int n9 = n7;
            n7 = n4;
            n4 = n9;
        }
        if (bl) {
            int n10 = n - n8 + this.horizontalScrollOffset - this.leftMargin;
            n6 = n10 / this.renderer.averageCharWidth;
            for (n5 = 0; n5 < n6; ++n5) {
                stringBuilder.append(' ');
            }
        }
        stringBuilder.append(string);
        object = new Event();
        object.start = n7;
        object.end = n4;
        object.text = stringBuilder.toString();
        this.sendKeyEvent((Event)object);
        return object.start + object.text.length();
    }

    int sendWordBoundaryEvent(int n, int n2, int n3, int n4, String string, int n5) {
        if (this.isListening(n)) {
            StyledTextEvent styledTextEvent = new StyledTextEvent(this.content);
            styledTextEvent.detail = n5;
            styledTextEvent.text = string;
            styledTextEvent.count = n2;
            styledTextEvent.start = n3;
            styledTextEvent.end = n4;
            this.notifyListeners(n, styledTextEvent);
            n3 = styledTextEvent.end;
            if (n3 != n4) {
                int n6 = this.getCharCount();
                if (n3 < 0) {
                    n3 = 0;
                } else if (n3 > n6) {
                    n3 = n6;
                } else if (this > n3) {
                    SWT.error(5);
                }
            }
            return n3;
        }
        return n4;
    }

    void setAlignment() {
        if ((this.getStyle() & 4) == 0) {
            return;
        }
        int n = this.renderer.getLineAlignment(0, this.alignment);
        int n2 = 0;
        if (n != 16384) {
            this.renderer.calculate(0, 1);
            int n3 = this.renderer.getWidth() - this.alignmentMargin;
            n2 = this.clientAreaWidth - n3;
            if (n2 < 0) {
                n2 = 0;
            }
            if (n == 0x1000000) {
                n2 /= 2;
            }
        }
        if (this.alignmentMargin != n2) {
            this.leftMargin -= this.alignmentMargin;
            this.leftMargin += n2;
            this.alignmentMargin = n2;
            this.resetCache(0, 1);
            this.setCaretLocations();
            super.redraw();
        }
    }

    public void setAlignment(int n) {
        this.checkWidget();
        if ((n &= 0x1024000) == 0 || this.alignment == n) {
            return;
        }
        this.alignment = n;
        this.resetCache(0, this.content.getLineCount());
        this.setCaretLocations();
        this.setAlignment();
        super.redraw();
    }

    public void setAlwaysShowScrollBars(boolean bl) {
        this.checkWidget();
        if (bl == this.alwaysShowScroll) {
            return;
        }
        this.alwaysShowScroll = bl;
        this.setScrollBars(true);
    }

    @Override
    public void setBackground(Color color) {
        this.checkWidget();
        boolean bl = false;
        if (!this.enabled && color == null && this.background != null) {
            Color color2 = this.getDisplay().getSystemColor(38);
            if (this.background.equals(color2)) {
                return;
            }
            color = new Color(color2.getRGBA());
            bl = true;
        }
        this.customBackground = color != null && !this.insideSetEnableCall && !bl;
        this.background = color;
        super.setBackground(this.background);
        this.resetCache(0, this.content.getLineCount());
        this.setCaretLocations();
        super.redraw();
    }

    public void setBlockSelection(boolean bl) {
        int n;
        this.checkWidget();
        if ((this.getStyle() & 4) != 0) {
            return;
        }
        if (bl == this.blockSelection) {
            return;
        }
        if (this.wordWrap) {
            return;
        }
        this.blockSelection = bl;
        if (this.cursor == null) {
            Display display = this.getDisplay();
            n = bl ? 2 : 19;
            super.setCursor(display.getSystemCursor(n));
        }
        if (bl) {
            int n2 = this.selection[0].x;
            n = this.selection[0].y;
            if (n2 != n) {
                this.setBlockSelectionOffset(n2, n, false);
            }
        } else {
            this.clearBlockSelection(false, false);
        }
    }

    public void setBlockSelectionBounds(Rectangle rectangle) {
        this.checkWidget();
        if (rectangle == null) {
            SWT.error(4);
        }
        this.setBlockSelectionBounds(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void setBlockSelectionBounds(int n, int n2, int n3, int n4) {
        this.checkWidget();
        int n5 = this.getVerticalScrollOffset();
        if (!this.blockSelection) {
            int n6 = this.getOffsetAtPoint(n -= this.horizontalScrollOffset, n2 -= n5, null);
            int n7 = this.getOffsetAtPoint(n + n3 - 1, n2 + n4 - 1, null);
            this.setSelection(new int[]{n6, n7 - n6}, false, false);
            this.setCaretLocations();
            return;
        }
        int n8 = this.topMargin;
        int n9 = this.leftMargin;
        int n10 = this.renderer.getHeight() - this.bottomMargin;
        int n11 = Math.max(this.clientAreaWidth, this.renderer.getWidth()) - this.rightMargin;
        int n12 = Math.max(n9, Math.min(n11, n)) - this.horizontalScrollOffset;
        int n13 = Math.max(n8, Math.min(n10, n2)) - n5;
        int n14 = Math.max(n9, Math.min(n11, n + n3)) - this.horizontalScrollOffset;
        int n15 = Math.max(n8, Math.min(n10, n2 + n4 - 1)) - n5;
        if (this == false && this.renderer.fixedPitch) {
            int n16 = this.renderer.averageCharWidth;
            n12 = (n12 - this.leftMargin + this.horizontalScrollOffset) / n16 * n16 + this.leftMargin - this.horizontalScrollOffset;
            n14 = (n14 + n16 / 2 - this.leftMargin + this.horizontalScrollOffset) / n16 * n16 + this.leftMargin - this.horizontalScrollOffset;
        }
        this.setBlockSelectionLocation(n12, n13, n14, n15, false);
    }

    void setBlockSelectionLocation(int n, int n2, boolean bl) {
        int n3 = this.getVerticalScrollOffset();
        this.blockXLocation = n + this.horizontalScrollOffset;
        this.blockYLocation = n2 + n3;
        int[] nArray = new int[]{0};
        int n4 = this.getOffsetAtPoint(n, n2, nArray);
        this.setCaretOffsets(new int[]{n4}, nArray[0]);
        if (this.blockXAnchor == -1) {
            this.blockXAnchor = this.blockXLocation;
            this.blockYAnchor = this.blockYLocation;
            this.selectionAnchors[0] = this.caretOffsets[0];
        }
        this.doBlockSelection(bl);
    }

    void setBlockSelectionLocation(int n, int n2, int n3, int n4, boolean bl) {
        int n5 = this.getVerticalScrollOffset();
        this.blockXAnchor = n + this.horizontalScrollOffset;
        this.blockYAnchor = n2 + n5;
        this.selectionAnchors[0] = this.getOffsetAtPoint(n, n2, null);
        this.setBlockSelectionLocation(n3, n4, bl);
    }

    void setBlockSelectionOffset(int n, boolean bl) {
        Point point = this.getPointAtOffset(n);
        int n2 = this.getVerticalScrollOffset();
        this.blockXLocation = point.x + this.horizontalScrollOffset;
        this.blockYLocation = point.y + n2;
        this.setCaretOffsets(new int[]{n}, -1);
        if (this.blockXAnchor == -1) {
            this.blockXAnchor = this.blockXLocation;
            this.blockYAnchor = this.blockYLocation;
            this.selectionAnchors[0] = this.caretOffsets[0];
        }
        this.doBlockSelection(bl);
    }

    void setBlockSelectionOffset(int n, int n2, boolean bl) {
        int n3 = this.getVerticalScrollOffset();
        Point point = this.getPointAtOffset(n);
        this.blockXAnchor = point.x + this.horizontalScrollOffset;
        this.blockYAnchor = point.y + n3;
        this.selectionAnchors[0] = n;
        this.setBlockSelectionOffset(n2, bl);
    }

    @Override
    public void setCaret(Caret caret) {
        this.checkWidget();
        super.setCaret(caret);
        this.caretDirection = 0;
        if (caret != null) {
            this.setCaretLocations();
            if (this.carets != null) {
                for (int i = 1; i < this.carets.length; ++i) {
                    this.carets[i].dispose();
                }
            }
            this.carets = new Caret[]{caret};
        } else {
            this.carets = null;
        }
    }

    @Deprecated
    public void setBidiColoring(boolean bl) {
        this.checkWidget();
        this.bidiColoring = bl;
    }

    public void setBottomMargin(int n) {
        this.checkWidget();
        this.setMargins(this.getLeftMargin(), this.topMargin, this.rightMargin, n);
    }

    void setCaretLocations() {
        Point[] pointArray = (Point[])Arrays.stream(this.caretOffsets).mapToObj(this::getPointAtOffset).toArray(StyledText::lambda$setCaretLocations$35);
        this.setCaretLocations(pointArray, this.getCaretDirection());
    }

    /*
     * WARNING - void declaration
     */
    void setCaretLocations(Point[] pointArray, int n) {
        Caret caret = this.getCaret();
        if (caret != null) {
            int n2;
            boolean bl;
            if (this.carets == null || this.carets.length == 0) {
                this.carets = new Caret[]{caret};
            }
            boolean bl2 = bl = caret == this.defaultCaret;
            if (pointArray.length > this.carets.length) {
                n2 = this.carets.length;
                this.carets = Arrays.copyOf(this.carets, pointArray.length);
                for (int i = n2; i < this.carets.length; ++i) {
                    this.carets[i] = new Caret(this, caret.getStyle());
                    this.carets[i].setImage(caret.getImage());
                    this.carets[i].setFont(caret.getFont());
                    this.carets[i].setSize(caret.getSize());
                }
            } else if (pointArray.length < this.carets.length) {
                for (n2 = pointArray.length; n2 < this.carets.length; ++n2) {
                    this.carets[n2].dispose();
                }
                this.carets = Arrays.copyOf(this.carets, pointArray.length);
            }
            for (n2 = Math.min(this.caretOffsets.length, pointArray.length) - 1; n2 >= 0; --n2) {
                void n11;
                int n3;
                int n4;
                Caret caret2 = this.carets[n2];
                int n5 = this.caretOffsets[n2];
                Point point = pointArray[n2];
                StyleRange styleRange = this.content.getCharCount() > 0 ? (n5 < this.content.getCharCount() ? this.getStyleRangeAtOffset(n5) : this.getStyleRangeAtOffset(this.content.getCharCount() - 1)) : null;
                int n6 = this.content.getLineAtOffset(n5);
                int n7 = this.getLineHeight();
                int n8 = n4 = this.getOffsetAtLine(n6);
                int n9 = n3 = n8 + this.getLine(n6).length();
                if (n6 < this.getLineCount() && this.renderer.getLineHeight(n6) != this.getLineHeight()) {
                    n7 = this.getLineHeight(n5);
                    Rectangle rectangle = this.getBoundsAtOffset(n5);
                    n4 = this.getOffsetAtPoint(new Point(this.leftMargin, rectangle.y));
                    n3 = this.getOffsetAtPoint(new Point(this.leftMargin, rectangle.y + n7)) - 1;
                    if (n3 < n4) {
                        n3 = this.getCharCount();
                    }
                }
                int n10 = this.getLineHeight();
                boolean bl3 = true;
                if (n4 >= 0) {
                    for (StyleRange styleRange2 : this.getStyleRanges(n4, n3 - n4)) {
                        bl3 &= !(styleRange2.font != null && !Objects.equals(styleRange2.font, this.getFont()) || styleRange2.rise < 0 || styleRange2.metrics != null && styleRange2.metrics.descent > 0);
                    }
                }
                if (!bl3 || styleRange != null && styleRange.isVariableHeight()) {
                    if (bl) {
                        n = -1;
                        n10 = n7;
                    } else {
                        n10 = caret2.getSize().y;
                    }
                }
                if (bl3 && n10 < n7) {
                    Point point2 = point;
                    point2.y += n7 - n10;
                }
                int n12 = n;
                if (this != false) {
                    if (n12 == 16384) {
                        int n13 = 131072;
                    } else if (n12 == 131072) {
                        int n14 = 16384;
                    }
                }
                if (bl && n11 == 131072) {
                    Point point3 = point;
                    point3.x -= caret2.getSize().x - 1;
                }
                if (bl) {
                    caret2.setBounds(point.x, point.y, this.caretWidth, n10);
                } else {
                    caret2.setLocation(point);
                }
                if (n == this.caretDirection) continue;
                this.caretDirection = n;
                if (bl) {
                    if (n11 == -1) {
                        this.defaultCaret.setImage(null);
                    } else if (n11 == 16384) {
                        this.defaultCaret.setImage(this.leftCaretBitmap);
                    } else if (n11 == 131072) {
                        this.defaultCaret.setImage(this.rightCaretBitmap);
                    }
                }
                if (this.caretDirection == 16384) {
                    BidiUtil.setKeyboardLanguage(0);
                    continue;
                }
                if (this.caretDirection != 131072) continue;
                BidiUtil.setKeyboardLanguage(1);
            }
            this.updateCaretVisibility();
            super.redraw();
        }
        this.columnX = pointArray[0].x;
    }

    public void setCaretOffset(int n) {
        this.checkWidget();
        int n2 = this.getCharCount();
        if (n2 > 0 && !Arrays.equals(this.caretOffsets, new int[]{n})) {
            if (n < 0) {
                n = 0;
            } else if (n > n2) {
                n = n2;
            } else if (this > n) {
                SWT.error(5);
            }
            this.setCaretOffsets(new int[]{n}, 0);
            if (this.blockSelection) {
                this.clearBlockSelection(true, false);
            } else {
                this.clearSelection(false);
            }
        }
        this.setCaretLocations();
    }

    void setCaretOffsets(int[] nArray, int n) {
        if (nArray.length > 1) {
            nArray = Arrays.stream(nArray).distinct().sorted().toArray();
        }
        if (!Arrays.equals(this.caretOffsets, nArray)) {
            this.caretOffsets = nArray;
            if (this.isListening(3011)) {
                StyledTextEvent styledTextEvent = new StyledTextEvent(this.content);
                styledTextEvent.end = this.caretOffsets[this.caretOffsets.length - 1];
                this.notifyListeners(3011, styledTextEvent);
            }
        }
        if (n != -1) {
            this.caretAlignment = n;
        }
    }

    void setClipboardContent(int n, int n2, int n3) throws SWTError {
        Transfer[] transferArray;
        Object[] objectArray;
        if (n3 == 2 && !IS_GTK) {
            return;
        }
        TextTransfer textTransfer = TextTransfer.getInstance();
        TextWriter textWriter = new TextWriter(n, n2);
        String string = this.getPlatformDelimitedText(textWriter);
        if (n3 == 2) {
            objectArray = new Object[]{string};
            transferArray = new Transfer[]{textTransfer};
        } else {
            RTFTransfer rTFTransfer = RTFTransfer.getInstance();
            RTFWriter rTFWriter = new RTFWriter(this, n, n2);
            String string2 = this.getPlatformDelimitedText(rTFWriter);
            objectArray = new Object[]{string2, string};
            transferArray = new Transfer[]{rTFTransfer, textTransfer};
        }
        this.clipboard.setContents(objectArray, transferArray, n3);
    }

    public void setContent(StyledTextContent styledTextContent) {
        this.checkWidget();
        if (styledTextContent == null) {
            SWT.error(4);
        }
        if (this.content != null) {
            this.content.removeTextChangeListener(this.textChangeListener);
        }
        this.content = styledTextContent;
        this.content.addTextChangeListener(this.textChangeListener);
        this.reset();
    }

    @Override
    public void setCursor(Cursor cursor) {
        this.checkWidget();
        if (cursor != null && cursor.isDisposed()) {
            SWT.error(5);
        }
        if ((this.cursor = cursor) == null) {
            Display display = this.getDisplay();
            int n = this.blockSelection ? 2 : 19;
            super.setCursor(display.getSystemCursor(n));
        } else {
            super.setCursor(cursor);
        }
    }

    public void setDoubleClickEnabled(boolean bl) {
        this.checkWidget();
        this.doubleClickEnabled = bl;
    }

    @Override
    public void setDragDetect(boolean bl) {
        this.checkWidget();
        this.dragDetect = bl;
    }

    public void setEditable(boolean bl) {
        this.checkWidget();
        this.editable = bl;
    }

    @Override
    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        Display display = this.getDisplay();
        this.enabled = bl;
        this.insideSetEnableCall = true;
        if (bl && this.editable) {
            if (!this.customBackground) {
                this.setBackground(display.getSystemColor(25));
            }
            if (!this.customForeground) {
                this.setForeground(display.getSystemColor(24));
            }
        } else if (!bl) {
            if (!this.customBackground) {
                this.setBackground(display.getSystemColor(38));
            }
            if (!this.customForeground) {
                this.setForeground(display.getSystemColor(39));
            }
        } else if (!this.editable) {
            if (!this.customBackground) {
                this.setBackground(display.getSystemColor(38));
            }
            if (!this.customForeground) {
                this.setForeground(display.getSystemColor(24));
            }
        }
        this.insideSetEnableCall = false;
    }

    @Override
    public boolean setFocus() {
        boolean bl = super.setFocus();
        if (bl && this != null) {
            this.setCaretLocations();
        }
        return bl;
    }

    @Override
    public void setFont(Font font) {
        int n;
        this.checkWidget();
        int n2 = this.renderer.getLineHeight();
        super.setFont(font);
        this.renderer.setFont(this.getFont(), this.tabLength);
        if (this == false && (n = this.renderer.getLineHeight()) != n2) {
            int n3 = this.getVerticalScrollOffset() * n / n2 - this.getVerticalScrollOffset();
            this.scrollVertical(n3, true);
        }
        this.resetCache(0, this.content.getLineCount());
        this.claimBottomFreeSpace();
        this.calculateScrollBars();
        if (this.isBidiCaret()) {
            this.createCaretBitmaps();
        }
        this.caretDirection = 0;
        this.setCaretLocations();
        super.redraw();
    }

    @Override
    public void setForeground(Color color) {
        this.checkWidget();
        boolean bl = false;
        if (!this.enabled && color == null && this.foreground != null) {
            Color color2 = this.getDisplay().getSystemColor(39);
            if (this.foreground.equals(color2)) {
                return;
            }
            color = new Color(color2.getRGBA());
            bl = true;
        }
        this.customForeground = color != null && !this.insideSetEnableCall && !bl;
        this.foreground = color;
        super.setForeground(this.foreground);
        this.resetCache(0, this.content.getLineCount());
        this.setCaretLocations();
        super.redraw();
    }

    public void setHorizontalIndex(int n) {
        int n2;
        this.checkWidget();
        if (this.getCharCount() == 0) {
            return;
        }
        if (n < 0) {
            n = 0;
        }
        if (this.clientAreaWidth > 0 && (n *= this.getHorizontalIncrement()) > (n2 = this.renderer.getWidth()) - this.clientAreaWidth) {
            n = Math.max(0, n2 - this.clientAreaWidth);
        }
        this.scrollHorizontal(n - this.horizontalScrollOffset, true);
    }

    public void setHorizontalPixel(int n) {
        int n2;
        this.checkWidget();
        if (this.getCharCount() == 0) {
            return;
        }
        if (n < 0) {
            n = 0;
        }
        if (this.clientAreaWidth > 0 && n > (n2 = this.renderer.getWidth()) - this.clientAreaWidth) {
            n = Math.max(0, n2 - this.clientAreaWidth);
        }
        this.scrollHorizontal(n - this.horizontalScrollOffset, true);
    }

    public void setIndent(int n) {
        this.checkWidget();
        if (this.indent == n || n < 0) {
            return;
        }
        this.indent = n;
        this.resetCache(0, this.content.getLineCount());
        this.setCaretLocations();
        super.redraw();
    }

    public void setJustify(boolean bl) {
        this.checkWidget();
        if (this.justify == bl) {
            return;
        }
        this.justify = bl;
        this.resetCache(0, this.content.getLineCount());
        this.setCaretLocations();
        super.redraw();
    }

    public void setKeyBinding(int n, int n2) {
        this.checkWidget();
        int n3 = n & SWT.MODIFIER_MASK;
        int n4 = n & 0x100FFFF;
        char c = (char)n4;
        if (Character.isDefined(n4) && Character.isLetter(c)) {
            char c2 = Character.toUpperCase(c);
            int n5 = c2 | n3;
            if (n2 == 0) {
                this.keyActionMap.remove(n5);
            } else {
                this.keyActionMap.put(n5, n2);
            }
            c2 = Character.toLowerCase(c);
            n5 = c2 | n3;
            if (n2 == 0) {
                this.keyActionMap.remove(n5);
            } else {
                this.keyActionMap.put(n5, n2);
            }
        } else if (n2 == 0) {
            this.keyActionMap.remove(n);
        } else {
            this.keyActionMap.put(n, n2);
        }
    }

    public void setLeftMargin(int n) {
        this.checkWidget();
        this.setMargins(n, this.topMargin, this.rightMargin, this.bottomMargin);
    }

    public void setLineAlignment(int n, int n2, int n3) {
        this.checkWidget();
        if (this.isListening(3002)) {
            return;
        }
        if (n < 0 || n + n2 > this.content.getLineCount()) {
            SWT.error(5);
        }
        this.renderer.setLineAlignment(n, n2, n3);
        this.resetCache(n, n2);
        this.redrawLines(n, n2, false);
        IntStream intStream = Arrays.stream(this.caretOffsets);
        StyledTextContent styledTextContent = this.content;
        Objects.requireNonNull(styledTextContent);
        if (intStream.map(styledTextContent::getLineAtOffset).anyMatch(arg_0 -> StyledText.lambda$setLineAlignment$36(n, n2, arg_0))) {
            this.setCaretLocations();
        }
        this.setAlignment();
    }

    public void setLineBackground(int n, int n2, Color color) {
        this.checkWidget();
        if (this.isListening(3001)) {
            return;
        }
        if (n < 0 || n + n2 > this.content.getLineCount()) {
            SWT.error(5);
        }
        if (color != null) {
            this.renderer.setLineBackground(n, n2, color);
        } else {
            this.renderer.clearLineBackground(n, n2);
        }
        this.redrawLines(n, n2, false);
    }

    public void setLineBullet(int n, int n2, Bullet bullet) {
        this.checkWidget();
        if (this.isListening(3002)) {
            return;
        }
        if (n < 0 || n + n2 > this.content.getLineCount()) {
            SWT.error(5);
        }
        int n3 = this.getLinePixel(n + n2);
        this.renderer.setLineBullet(n, n2, bullet);
        this.resetCache(n, n2);
        int n4 = this.getLinePixel(n + n2);
        this.redrawLines(n, n2, n3 != n4);
        IntStream intStream = Arrays.stream(this.caretOffsets);
        StyledTextContent styledTextContent = this.content;
        Objects.requireNonNull(styledTextContent);
        if (intStream.map(styledTextContent::getLineAtOffset).anyMatch(arg_0 -> StyledText.lambda$setLineBullet$37(n, n2, arg_0))) {
            this.setCaretLocations();
        }
    }

    public void setLineIndent(int n, int n2, int n3) {
        this.checkWidget();
        if (this.isListening(3002)) {
            return;
        }
        if (n < 0 || n + n2 > this.content.getLineCount()) {
            SWT.error(5);
        }
        int n4 = this.getLinePixel(n + n2);
        this.renderer.setLineIndent(n, n2, n3);
        this.resetCache(n, n2);
        int n5 = this.getLinePixel(n + n2);
        this.redrawLines(n, n2, n4 != n5);
        IntStream intStream = Arrays.stream(this.caretOffsets);
        StyledTextContent styledTextContent = this.content;
        Objects.requireNonNull(styledTextContent);
        if (intStream.map(styledTextContent::getLineAtOffset).anyMatch(arg_0 -> StyledText.lambda$setLineIndent$38(n, n2, arg_0))) {
            this.setCaretLocations();
        }
    }

    public void setLineVerticalIndent(int n, int n2) {
        int n3;
        this.checkWidget();
        if (this.isListening(3002)) {
            return;
        }
        if (n < 0 || n >= this.content.getLineCount()) {
            SWT.error(5);
        }
        if (n2 == (n3 = this.renderer.getLineVerticalIndent(n))) {
            return;
        }
        int n4 = this.getTopPixel();
        int n5 = this.getPartialTopIndex();
        int n6 = this.getPartialBottomIndex();
        int n7 = n2 - n3;
        this.renderer.setLineVerticalIndent(n, n2);
        this.hasVerticalIndent = n2 != 0 || this.renderer.hasVerticalIndent();
        ScrollBar scrollBar = this.getVerticalBar();
        if (n < n5) {
            this.verticalScrollOffset += n7;
            if (scrollBar != null) {
                scrollBar.setSelection(this.verticalScrollOffset);
                scrollBar.setMaximum(scrollBar.getMaximum() + n7);
            }
        } else if (n > n6) {
            if (scrollBar != null) {
                scrollBar.setMaximum(scrollBar.getMaximum() + n7);
            }
        } else {
            this.resetCache(n, 1);
            if (n5 == 0 && n6 == this.content.getLineCount() - 1) {
                this.setCaretLocations();
                this.redrawLines(n, this.getBottomIndex() - n + 1, true);
            } else if (this.getFirstCaretLine() >= n5 && this.getFirstCaretLine() <= n6) {
                if (this.getFirstCaretLine() < n) {
                    this.redrawLines(n, this.getPartialBottomIndex() - n + 1, true);
                } else {
                    this.setTopPixel(n4 + n7);
                }
            } else if (n - this.getTopIndex() < this.getBottomIndex() - n) {
                this.setTopPixel(n4 + n7);
            } else {
                this.redrawLines(n, this.getPartialBottomIndex() - n + 1, true);
            }
            this.setScrollBars(true);
        }
    }

    public void setLineJustify(int n, int n2, boolean bl) {
        this.checkWidget();
        if (this.isListening(3002)) {
            return;
        }
        if (n < 0 || n + n2 > this.content.getLineCount()) {
            SWT.error(5);
        }
        this.renderer.setLineJustify(n, n2, bl);
        this.resetCache(n, n2);
        this.redrawLines(n, n2, false);
        IntStream intStream = Arrays.stream(this.caretOffsets);
        StyledTextContent styledTextContent = this.content;
        Objects.requireNonNull(styledTextContent);
        if (intStream.map(styledTextContent::getLineAtOffset).anyMatch(arg_0 -> StyledText.lambda$setLineJustify$39(n, n2, arg_0))) {
            this.setCaretLocations();
        }
    }

    public void setLineSpacing(int n) {
        this.checkWidget();
        if (this.lineSpacing == n || n < 0) {
            return;
        }
        this.lineSpacing = n;
        this.resetCache(0, this.content.getLineCount());
        this.setCaretLocations();
        super.redraw();
    }

    public void setLineSpacingProvider(StyledTextLineSpacingProvider styledTextLineSpacingProvider) {
        this.checkWidget();
        boolean bl = this.isFixedLineHeight();
        if (this.renderer.getLineSpacingProvider() == null && styledTextLineSpacingProvider == null || this.renderer.getLineSpacingProvider() != null && this.renderer.getLineSpacingProvider().equals(styledTextLineSpacingProvider)) {
            return;
        }
        this.renderer.setLineSpacingProvider(styledTextLineSpacingProvider);
        if (styledTextLineSpacingProvider == null) {
            if (!bl) {
                this.resetCache(0, this.content.getLineCount());
            }
        } else if (bl) {
            int n = -1;
            for (int i = 0; i < this.content.getLineCount(); ++i) {
                Integer n2 = styledTextLineSpacingProvider.getLineSpacing(i);
                if (n2 == null || n2 <= 0) continue;
                this.renderer.reset(i, 1);
                if (n != -1) continue;
                n = i;
            }
            if (n != -1) {
                this.resetCache(n, 0);
            }
        }
        this.setCaretLocations();
        super.redraw();
    }

    public void setLineTabStops(int n, int n2, int[] nArray) {
        Object object;
        this.checkWidget();
        if (this.isListening(3002)) {
            return;
        }
        if (n < 0 || n + n2 > this.content.getLineCount()) {
            SWT.error(5);
        }
        if (nArray != null) {
            int n3 = 0;
            object = new int[nArray.length];
            for (int i = 0; i < nArray.length; ++i) {
                if (nArray[i] < n3) {
                    SWT.error(5);
                }
                n3 = object[i] = nArray[i];
            }
            this.renderer.setLineTabStops(n, n2, (int[])object);
        } else {
            this.renderer.setLineTabStops(n, n2, null);
        }
        this.resetCache(n, n2);
        this.redrawLines(n, n2, false);
        IntStream intStream = Arrays.stream(this.caretOffsets);
        object = this.content;
        Objects.requireNonNull(object);
        if (intStream.map(((StyledTextContent)object)::getLineAtOffset).anyMatch(arg_0 -> StyledText.lambda$setLineTabStops$40(n, n2, arg_0))) {
            this.setCaretLocations();
        }
    }

    public void setLineWrapIndent(int n, int n2, int n3) {
        this.checkWidget();
        if (this.isListening(3002)) {
            return;
        }
        if (n < 0 || n + n2 > this.content.getLineCount()) {
            SWT.error(5);
        }
        int n4 = this.getLinePixel(n + n2);
        this.renderer.setLineWrapIndent(n, n2, n3);
        this.resetCache(n, n2);
        int n5 = this.getLinePixel(n + n2);
        this.redrawLines(n, n2, n4 != n5);
        IntStream intStream = Arrays.stream(this.caretOffsets);
        StyledTextContent styledTextContent = this.content;
        Objects.requireNonNull(styledTextContent);
        if (intStream.map(styledTextContent::getLineAtOffset).anyMatch(arg_0 -> StyledText.lambda$setLineWrapIndent$41(n, n2, arg_0))) {
            this.setCaretLocations();
        }
    }

    public void setMarginColor(Color color) {
        this.checkWidget();
        if (color != null && color.isDisposed()) {
            SWT.error(5);
        }
        this.marginColor = color;
        super.redraw();
    }

    public void setMargins(int n, int n2, int n3, int n4) {
        this.checkWidget();
        this.leftMargin = Math.max(0, n) + this.alignmentMargin;
        this.topMargin = Math.max(0, n2);
        this.rightMargin = Math.max(0, n3);
        this.bottomMargin = Math.max(0, n4);
        this.resetCache(0, this.content.getLineCount());
        this.setScrollBars(true);
        this.setCaretLocations();
        this.setAlignment();
        super.redraw();
    }

    public void setMouseNavigatorEnabled(boolean bl) {
        this.checkWidget();
        if (bl && this.mouseNavigator != null || !bl && this.mouseNavigator == null) {
            return;
        }
        if (bl) {
            this.mouseNavigator = new MouseNavigator(this);
        } else {
            this.mouseNavigator.dispose();
            this.mouseNavigator = null;
        }
    }

    void setMouseWordSelectionAnchor() {
        if (this.doubleClickEnabled && this.clickCount > 1) {
            if (this.caretOffsets[0] < this.doubleClickSelection.x) {
                this.selectionAnchors[0] = this.doubleClickSelection.y;
            } else if (this.caretOffsets[0] > this.doubleClickSelection.y) {
                this.selectionAnchors[0] = this.doubleClickSelection.x;
            }
        }
    }

    @Override
    public void setOrientation(int n) {
        int n2 = this.getOrientation();
        super.setOrientation(n);
        int n3 = this.getOrientation();
        if (n2 != n3) {
            this.resetBidiData();
        }
    }

    public void setRightMargin(int n) {
        this.checkWidget();
        this.setMargins(this.getLeftMargin(), this.topMargin, n, this.bottomMargin);
    }

    void setScrollBar(ScrollBar scrollBar, int n, int n2, int n3) {
        boolean bl = true;
        if (n < n2) {
            scrollBar.setMaximum(n2 - n3);
            scrollBar.setThumb(n - n3);
            scrollBar.setPageIncrement(n - n3);
            if (!this.alwaysShowScroll) {
                scrollBar.setVisible(true);
            }
        } else if (scrollBar.getThumb() != 1 || scrollBar.getMaximum() != 1) {
            scrollBar.setValues(scrollBar.getSelection(), scrollBar.getMinimum(), 1, 1, scrollBar.getIncrement(), 1);
        }
    }

    void setScrollBars(boolean bl) {
        ++this.ignoreResize;
        if (this != false || !this.alwaysShowScroll) {
            bl = true;
        }
        ScrollBar scrollBar = bl ? this.getVerticalBar() : null;
        ScrollBar scrollBar2 = this.getHorizontalBar();
        int n = this.clientAreaHeight;
        int n2 = this.clientAreaWidth;
        if (!this.alwaysShowScroll) {
            if (scrollBar != null) {
                scrollBar.setVisible(false);
            }
            if (scrollBar2 != null) {
                scrollBar2.setVisible(false);
            }
        }
        if (scrollBar != null) {
            this.setScrollBar(scrollBar, this.clientAreaHeight, this.renderer.getHeight(), this.topMargin + this.bottomMargin);
        }
        if (scrollBar2 != null && !this.wordWrap) {
            this.setScrollBar(scrollBar2, this.clientAreaWidth, this.renderer.getWidth(), this.leftMargin + this.rightMargin);
            if (!this.alwaysShowScroll && scrollBar2.getVisible() && scrollBar != null) {
                this.setScrollBar(scrollBar, this.clientAreaHeight, this.renderer.getHeight(), this.topMargin + this.bottomMargin);
                if (scrollBar.getVisible()) {
                    this.setScrollBar(scrollBar2, this.clientAreaWidth, this.renderer.getWidth(), this.leftMargin + this.rightMargin);
                }
            }
        }
        if (!this.alwaysShowScroll) {
            this.redrawMargins(n, n2);
        }
        --this.ignoreResize;
    }

    public void setSelection(int n) {
        this.setSelection(n, n);
    }

    public void setSelection(Point point) {
        this.checkWidget();
        if (point == null) {
            SWT.error(4);
        }
        this.setSelection(point.x, point.y);
    }

    public void setSelectionBackground(Color color) {
        this.checkWidget();
        if (color != null && color.isDisposed()) {
            SWT.error(5);
        }
        this.selectionBackground = color;
        this.resetCache(0, this.content.getLineCount());
        this.setCaretLocations();
        super.redraw();
    }

    public void setSelectionForeground(Color color) {
        this.checkWidget();
        if (color != null && color.isDisposed()) {
            SWT.error(5);
        }
        this.selectionForeground = color;
        this.resetCache(0, this.content.getLineCount());
        this.setCaretLocations();
        super.redraw();
    }

    public void setSelection(int n, int n2) {
        this.setSelectionRange(n, n2 - n);
        this.showSelection();
    }

    void setSelection(int[] nArray, boolean bl, boolean bl2) {
        if (nArray.length == 2 && this.selection.length == 1) {
            int n;
            int n2 = nArray[0];
            int n3 = nArray[1];
            int n4 = n2 + n3;
            if (n2 > n4) {
                n = n4;
                n4 = n2;
                n2 = n;
            }
            n = this.selectionAnchors[0];
            if (this.selection[0].x != n2 || this.selection[0].y != n4 || n3 > 0 && n != this.selection[0].x || n3 < 0 && n != this.selection[0].y) {
                if (this.blockSelection && bl2) {
                    if (n3 < 0) {
                        this.setBlockSelectionOffset(n4, n2, bl);
                    } else {
                        this.setBlockSelectionOffset(n2, n4, bl);
                    }
                } else {
                    int n5 = this.selection[0].x;
                    int n6 = this.selection[0].y - this.selection[0].x;
                    int n7 = this.content.getCharCount();
                    int n8 = Math.min(this.selection[0].x, n7);
                    int n9 = Math.min(this.selection[0].y, n7);
                    if (n3 < 0) {
                        int n10;
                        int[] nArray2 = this.selectionAnchors;
                        boolean bl3 = false;
                        Point point = this.selection[0];
                        point.y = n10 = n4;
                        n = n10;
                        nArray2[0] = n10;
                        this.selection[0].x = n2;
                        this.setCaretOffsets(new int[]{n2}, 0);
                    } else {
                        int n11;
                        int[] nArray3 = this.selectionAnchors;
                        boolean bl4 = false;
                        Point point = this.selection[0];
                        point.x = n11 = n2;
                        n = n11;
                        nArray3[0] = n11;
                        this.selection[0].y = n4;
                        this.setCaretOffsets(new int[]{n4}, 0);
                    }
                    n8 = Math.min(n8, this.selection[0].x);
                    n9 = Math.max(n9, this.selection[0].y);
                    if (n9 - n8 > 0) {
                        this.internalRedrawRange(n8, n9 - n8);
                    }
                    if (bl && (n6 != n4 - n2 || n6 != 0 && n5 != n2)) {
                        this.sendSelectionEvent();
                    }
                    this.sendAccessibleTextCaretMoved();
                }
            }
        } else if (!this.blockSelection || !bl2) {
            Point point4;
            int n;
            boolean bl5 = nArray[1] > 0;
            int n12 = this.content.getCharCount();
            Point[] pointArray = new Point[nArray.length / 2];
            for (n = 0; n < nArray.length; n += 2) {
                int n13 = nArray[n];
                int n14 = nArray[n + 1];
                int n15 = n13 + n14;
                if (n13 > n15) {
                    int n16 = n15;
                    n15 = n13;
                    n13 = n16;
                }
                pointArray[n / 2] = new Point(n13, n15);
            }
            Arrays.sort(pointArray, SELECTION_COMPARATOR);
            n = 0;
            for (Point point2 : pointArray) {
                if (n > 0) {
                    point4 = pointArray[n - 1];
                    if (point4.y >= point2.x) {
                        point4.y = Math.max(point4.y, point2.y);
                        continue;
                    }
                    pointArray[n] = point2;
                    ++n;
                    continue;
                }
                pointArray[n] = point2;
                ++n;
            }
            Point[] pointArray2 = new Point[n + this.selection.length];
            System.arraycopy(pointArray, 0, pointArray2, 0, n);
            System.arraycopy(this.selection, 0, pointArray2, n, this.selection.length);
            Arrays.sort(pointArray2, SELECTION_COMPARATOR);
            Object[] objectArray = this.selection;
            this.selection = Arrays.copyOf(pointArray, n);
            Point point3 = null;
            Point[] pointArray3 = pointArray2;
            point4 = null;
            for (Point point4 : pointArray3) {
                if (point3 == null) {
                    point3 = new Point(point4.x, point4.y);
                    continue;
                }
                if (point3.y >= point4.x - 1) {
                    point3 = new Point(point3.x, Math.max(point4.y, point3.y));
                    continue;
                }
                point3 = new Point(Math.max(0, point3.x), Math.min(n12, point3.y));
                this.internalRedrawRange(point3.x, point3.y - point3.x);
                point3 = null;
            }
            if (point3 != null) {
                point3 = new Point(Math.max(0, point3.x), Math.min(n12, point3.y));
                this.internalRedrawRange(point3.x, point3.y - point3.x);
            }
            if (!bl5) {
                this.selectionAnchors = Arrays.stream(this.selection).mapToInt(StyledText::lambda$setSelection$42).toArray();
                this.setCaretOffsets(Arrays.stream(this.selection).mapToInt(StyledText::lambda$setSelection$43).toArray(), 0);
            } else {
                this.selectionAnchors = Arrays.stream(this.selection).mapToInt(StyledText::lambda$setSelection$44).toArray();
                this.setCaretOffsets(Arrays.stream(this.selection).mapToInt(StyledText::lambda$setSelection$45).toArray(), 0);
            }
            this.setCaretLocations();
            if (bl && !Arrays.equals(objectArray, this.selection)) {
                this.sendSelectionEvent();
            }
            this.sendAccessibleTextCaretMoved();
        }
    }

    public void setSelectionRange(int n, int n2) {
        this.setSelectionRanges(new int[]{n, n2});
    }

    public void setSelectionRanges(int[] nArray) {
        this.checkWidget();
        int n = this.getCharCount();
        if (nArray.length % 2 != 0) {
            SWT.error(5);
        }
        int[] nArray2 = Arrays.copyOf(nArray, nArray.length);
        for (int i = 0; i < nArray.length; i += 2) {
            int n2;
            int n3 = nArray[i];
            int n4 = (n3 = Math.max(0, Math.min(n3, n))) + (n2 = nArray[i + 1]);
            if (n4 < 0) {
                n2 = -n3;
            } else if (n4 > n) {
                n2 = n - n3;
            }
            if (this <= n3 || this > n3 + n2) {
                SWT.error(5);
            }
            nArray2[i] = n3;
            nArray2[i + 1] = n2;
        }
        this.setSelection(nArray2, false, true);
        this.setCaretLocations();
    }

    public void setStyleRange(StyleRange styleRange) {
        this.checkWidget();
        if (this.isListening(3002)) {
            return;
        }
        if (styleRange != null) {
            if (styleRange.isUnstyled()) {
                this.setStyleRanges(styleRange.start, styleRange.length, null, null, false);
            } else {
                this.setStyleRanges(styleRange.start, 0, null, new StyleRange[]{styleRange}, false);
            }
        } else {
            this.setStyleRanges(0, 0, null, null, true);
        }
    }

    public void setStyleRanges(int n, int n2, int[] nArray, StyleRange[] styleRangeArray) {
        this.checkWidget();
        if (this.isListening(3002)) {
            return;
        }
        if (nArray == null || styleRangeArray == null) {
            this.setStyleRanges(n, n2, null, null, false);
        } else {
            this.setStyleRanges(n, n2, nArray, styleRangeArray, false);
        }
    }

    public void setStyleRanges(int[] nArray, StyleRange[] styleRangeArray) {
        this.checkWidget();
        if (this.isListening(3002)) {
            return;
        }
        if (nArray == null || styleRangeArray == null) {
            this.setStyleRanges(0, 0, null, null, true);
        } else {
            this.setStyleRanges(0, 0, nArray, styleRangeArray, true);
        }
    }

    void setStyleRanges(int n, int n2, int[] nArray, StyleRange[] styleRangeArray, boolean bl) {
        int n3;
        int n4;
        int n6;
        int n7;
        int n8;
        int n9 = this.content.getCharCount();
        if (bl) {
            n = 0;
            n2 = n9;
        }
        int[] nArray2 = this.getRanges(n, n2);
        StyleRange[] styleRangeArray2 = this.getStyleRanges(n, n2);
        int n10 = n + n2;
        boolean bl2 = this.isFixedLineHeight();
        if (n > n10 || n < 0) {
            SWT.error(6);
        }
        if (styleRangeArray != null) {
            if (n10 > n9) {
                SWT.error(6);
            }
            if (nArray != null && nArray.length != styleRangeArray.length << 1) {
                SWT.error(5);
            }
            n8 = 0;
            for (n7 = 0; n7 < styleRangeArray.length; ++n7) {
                int n5;
                if (styleRangeArray[n7] == null) {
                    SWT.error(5);
                }
                if (nArray != null) {
                    n6 = nArray[n7 << 1];
                    n5 = nArray[(n7 << 1) + 1];
                } else {
                    n6 = styleRangeArray[n7].start;
                    n5 = styleRangeArray[n7].length;
                }
                if (n5 < 0) {
                    SWT.error(5);
                }
                if (0 > n6 || n6 + n5 > n9) {
                    SWT.error(5);
                }
                if (n8 > n6) {
                    SWT.error(5);
                }
                this.hasStyleWithVariableHeight |= styleRangeArray[n7].isVariableHeight();
                n8 = n6 + n5;
            }
        }
        n8 = n;
        n7 = n10;
        if (styleRangeArray != null && styleRangeArray.length > 0) {
            if (nArray != null) {
                n8 = nArray[0];
                n7 = nArray[nArray.length - 2] + nArray[nArray.length - 1];
            } else {
                n8 = styleRangeArray[0].start;
                n7 = styleRangeArray[styleRangeArray.length - 1].start + styleRangeArray[styleRangeArray.length - 1].length;
            }
        }
        n6 = 0;
        if (this == false && !bl) {
            int object = this.content.getLineAtOffset(Math.max(n10, n7));
            n4 = this.getPartialTopIndex();
            n3 = this.getPartialBottomIndex();
            if (n4 <= object && object <= n3) {
                n6 = this.getLinePixel(object + 1);
            }
        }
        if (bl) {
            this.renderer.setStyleRanges(null, null);
        } else {
            this.renderer.updateRanges(n, n2, n2);
        }
        if (styleRangeArray != null && styleRangeArray.length > 0) {
            this.renderer.setStyleRanges(nArray, styleRangeArray);
        }
        this.hasStyleWithVariableHeight = false;
        for (StyleRange styleRange : this.getStyleRanges(false)) {
            this.hasStyleWithVariableHeight = styleRange.isVariableHeight();
            if (this.hasStyleWithVariableHeight) break;
        }
        SortedSet sortedSet = this.computeModifiedLines(nArray2, styleRangeArray2, nArray, styleRangeArray);
        this.resetCache(sortedSet);
        if (bl) {
            super.redraw();
        } else {
            n4 = this.content.getLineAtOffset(Math.min(n, n8));
            n3 = this.content.getLineAtOffset(Math.max(n10, n7));
            int n5 = this.getPartialTopIndex();
            int n11 = this.getPartialBottomIndex();
            if (n4 <= n11 && n3 >= n5) {
                int n12 = 0;
                int n13 = this.clientAreaHeight;
                if (n5 <= n4 && n4 <= n11) {
                    n12 = Math.max(0, this.getLinePixel(n4));
                }
                if (n5 <= n3 && n3 <= n11) {
                    n13 = this.getLinePixel(n3 + 1);
                }
                if (!(bl2 && this != false || n13 == n6)) {
                    n13 = this.clientAreaHeight;
                }
                super.redraw(0, n12, this.clientAreaWidth, n13 - n12, false);
            }
        }
        n4 = this.columnX;
        this.setCaretLocations();
        this.columnX = n4;
        this.doMouseLinkCursor();
    }

    /*
     * Exception decompiling
     */
    private SortedSet computeModifiedLines(int[] var1, StyleRange[] var2, int[] var3, StyleRange[] var4) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl87 : ILOAD - null : trying to set 6 previously set to 0
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

    private int[] createRanges(StyleRange[] styleRangeArray) {
        int[] nArray = new int[2 * styleRangeArray.length];
        for (int i = 0; i < styleRangeArray.length; ++i) {
            nArray[2 * i] = styleRangeArray[i].start;
            nArray[2 * i + 1] = styleRangeArray[i].length;
        }
        return nArray;
    }

    private int endRangeOffset(int[] nArray, int n) {
        if (n < 0 || 2 * n > nArray.length) {
            throw new IllegalArgumentException();
        }
        int n2 = nArray[2 * n];
        int n3 = nArray[2 * n + 1];
        return n2 + n3;
    }

    public void setStyleRanges(StyleRange[] styleRangeArray) {
        this.checkWidget();
        if (this.isListening(3002)) {
            return;
        }
        if (styleRangeArray == null) {
            SWT.error(4);
        }
        this.setStyleRanges(0, 0, null, styleRangeArray, true);
    }

    public void setTabs(int n) {
        this.checkWidget();
        this.tabLength = n;
        this.renderer.setFont(null, n);
        this.resetCache(0, this.content.getLineCount());
        this.setCaretLocations();
        super.redraw();
    }

    public void setTabStops(int[] nArray) {
        this.checkWidget();
        if (nArray != null) {
            int n = 0;
            int[] nArray2 = new int[nArray.length];
            for (int i = 0; i < nArray.length; ++i) {
                if (nArray[i] < n) {
                    SWT.error(5);
                }
                n = nArray2[i] = nArray[i];
            }
            this.tabs = nArray2;
        } else {
            this.tabs = null;
        }
        this.resetCache(0, this.content.getLineCount());
        this.setCaretLocations();
        super.redraw();
    }

    public void setText(String string) {
        this.checkWidget();
        if (string == null) {
            SWT.error(4);
        }
        Event event = new Event();
        event.start = 0;
        event.end = this.getCharCount();
        event.text = string;
        event.doit = true;
        this.notifyListeners(25, event);
        if (event.doit) {
            StyledTextEvent styledTextEvent = null;
            if (this.isListening(3000)) {
                styledTextEvent = new StyledTextEvent(this.content);
                styledTextEvent.start = event.start;
                styledTextEvent.end = event.start + event.text.length();
                styledTextEvent.text = this.content.getTextRange(event.start, event.end - event.start);
            }
            this.content.setText(event.text);
            this.notifyListeners(24, event);
            if (styledTextEvent != null) {
                this.notifyListeners(3000, styledTextEvent);
            }
        }
    }

    @Override
    public void setTextDirection(int n) {
        this.checkWidget();
        int n2 = this.getStyle();
        super.setTextDirection(n);
        if (this.isAutoDirection() || n2 != this.getStyle()) {
            this.resetBidiData();
        }
    }

    public void setTextLimit(int n) {
        this.checkWidget();
        if (n == 0) {
            SWT.error(7);
        }
        this.textLimit = n;
    }

    public void setTopIndex(int n) {
        int n2;
        this.checkWidget();
        if (this.getCharCount() == 0) {
            return;
        }
        int n3 = this.content.getLineCount();
        if (this == false) {
            int n4 = Math.max(1, Math.min(n3, this.getLineCountWhole()));
            if (n < 0) {
                n = 0;
            } else if (n > n3 - n4) {
                n = n3 - n4;
            }
            n2 = this.getLinePixel(n);
        } else {
            n2 = this.getLinePixel(n = Math.max(0, Math.min(n3 - 1, n)));
            n2 = n2 > 0 ? this.getAvailableHeightBellow(n2) : this.getAvailableHeightAbove(n2);
        }
        this.scrollVertical(n2, true);
    }

    public void setTopMargin(int n) {
        this.checkWidget();
        this.setMargins(this.getLeftMargin(), n, this.rightMargin, this.bottomMargin);
    }

    public void setTopPixel(int n) {
        this.checkWidget();
        if (this.getCharCount() == 0) {
            return;
        }
        if (n < 0) {
            n = 0;
        }
        int n2 = this.content.getLineCount();
        int n3 = this.clientAreaHeight - this.topMargin - this.bottomMargin;
        int n4 = this.getVerticalScrollOffset();
        if (this == false) {
            int n5 = Math.max(0, n2 * this.getVerticalIncrement() - n3);
            if (n > n5) {
                n = n5;
            }
            n -= n4;
        } else if ((n -= n4) > 0) {
            n = this.getAvailableHeightBellow(n);
        }
        this.scrollVertical(n, true);
    }

    public void setWordWrap(boolean bl) {
        this.checkWidget();
        if ((this.getStyle() & 4) != 0) {
            return;
        }
        if (this.wordWrap == bl) {
            return;
        }
        if (this.wordWrap && this.blockSelection) {
            this.setBlockSelection(false);
        }
        this.wordWrap = bl;
        this.resetCache(0, this.content.getLineCount());
        this.horizontalScrollOffset = 0;
        ScrollBar scrollBar = this.getHorizontalBar();
        if (scrollBar != null) {
            scrollBar.setVisible(!this.wordWrap);
        }
        this.setScrollBars(true);
        this.setCaretLocations();
        super.redraw();
    }

    public void setWrapIndent(int n) {
        this.checkWidget();
        if (this.wrapIndent == n || n < 0) {
            return;
        }
        this.wrapIndent = n;
        this.resetCache(0, this.content.getLineCount());
        this.setCaretLocations();
        super.redraw();
    }

    void showCaret() {
        Rectangle rectangle = this.getBoundsAtOffset(this.caretOffsets[0]);
        StyledText styledText = this;
        if (rectangle >= true || this.carets != null && this.caretOffsets.length != this.carets.length) {
            this.setCaretLocations();
        }
    }

    /*
     * Exception decompiling
     */
    public void showSelection() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl118 : RETURN - null : trying to set 0 previously set to 1
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

    void updateCaretVisibility() {
        Caret caret = this.getCaret();
        if (caret != null) {
            if (this.carets == null || this.carets.length == 0) {
                this.carets = new Caret[]{caret};
            }
            if (this.blockSelection && this.blockXLocation != -1) {
                Arrays.stream(this.carets).forEach(StyledText::lambda$updateCaretVisibility$46);
            } else {
                Arrays.stream(this.carets).forEach(this::lambda$updateCaretVisibility$47);
            }
        }
    }

    void updateSelection(int n, int n2, int n3) {
        if (this.selection[this.selection.length - 1].y <= n) {
            if (this == false) {
                this.setCaretLocations();
            }
            return;
        }
        Arrays.stream(this.selection).filter(arg_0 -> StyledText.lambda$updateSelection$48(n, arg_0)).filter(arg_0 -> StyledText.lambda$updateSelection$49(n, arg_0)).forEach(arg_0 -> this.lambda$updateSelection$50(n, arg_0));
        Arrays.stream(this.selection).filter(arg_0 -> StyledText.lambda$updateSelection$51(n, arg_0)).filter(arg_0 -> StyledText.lambda$updateSelection$52(n, n2, arg_0)).forEach(arg_0 -> this.lambda$updateSelection$53(n3, n2, n, arg_0));
        Point[] pointArray = this.selection;
        for (int i = 0; i < this.selection.length; ++i) {
            Point point = this.selection[i];
            if (point.y <= n) {
                pointArray[i] = point;
                continue;
            }
            if (point.x == n && point.y == n + n2) {
                pointArray[i] = new Point(n + n3, n + n3);
                continue;
            }
            if (point.y > n && point.x < n + n2) {
                pointArray[i] = new Point(n + n3, n + n3);
                continue;
            }
            int n4 = point.x + n3 - n2;
            int n5 = point.x + n3 - n2 + (point.y - point.x);
            pointArray[i] = new Point(n4 < 0 ? 0 : n4, n5 < 0 ? 0 : n5);
        }
        this.setSelection(Arrays.stream(pointArray).flatMapToInt(StyledText::lambda$updateSelection$54).toArray(), true, false);
        this.setCaretLocations();
    }

    private static int lambda$static$55(Point point) {
        return point.y;
    }

    private static IntStream lambda$updateSelection$54(Point point) {
        return IntStream.of(point.x, point.y - point.x);
    }

    private void lambda$updateSelection$53(int n, int n2, int n3, Point point) {
        int n4 = n - n2;
        int n5 = n3 + n;
        this.internalRedrawRange(n5, point.y + n4 - n5);
    }

    private static boolean lambda$updateSelection$52(int n, int n2, Point point) {
        return point.y > n + n2 && point.x < n + n2;
    }

    private static boolean lambda$updateSelection$51(int n, Point point) {
        return point.y > n;
    }

    private void lambda$updateSelection$50(int n, Point point) {
        this.internalRedrawRange(point.x, n - point.x);
    }

    private static boolean lambda$updateSelection$49(int n, Point point) {
        return point.x < n;
    }

    private static boolean lambda$updateSelection$48(int n, Point point) {
        return point.y > n;
    }

    private void lambda$updateCaretVisibility$47(Caret caret) {
        Point point = caret.getLocation();
        Point point2 = caret.getSize();
        boolean bl = this.topMargin <= point.y + point2.y && point.y <= this.clientAreaHeight - this.bottomMargin && this.leftMargin <= point.x + point2.x && point.x <= this.clientAreaWidth - this.rightMargin;
        caret.setVisible(bl);
    }

    private static void lambda$updateCaretVisibility$46(Caret caret) {
        caret.setVisible(false);
    }

    private static int lambda$setSelection$45(Point point) {
        return point.y;
    }

    private static int lambda$setSelection$44(Point point) {
        return point.x;
    }

    private static int lambda$setSelection$43(Point point) {
        return point.x;
    }

    private static int lambda$setSelection$42(Point point) {
        return point.y;
    }

    private static boolean lambda$setLineWrapIndent$41(int n, int n2, int n3) {
        return n <= n3 && n3 < n + n2;
    }

    private static boolean lambda$setLineTabStops$40(int n, int n2, int n3) {
        return n <= n3 && n3 < n + n2;
    }

    private static boolean lambda$setLineJustify$39(int n, int n2, int n3) {
        return n <= n3 && n3 < n + n2;
    }

    private static boolean lambda$setLineIndent$38(int n, int n2, int n3) {
        return n <= n3 && n3 < n + n2;
    }

    private static boolean lambda$setLineBullet$37(int n, int n2, int n3) {
        return n <= n3 && n3 < n + n2;
    }

    private static boolean lambda$setLineAlignment$36(int n, int n2, int n3) {
        return n <= n3 && n3 < n + n2;
    }

    private static Point[] lambda$setCaretLocations$35(int n) {
        return new Point[n];
    }

    private boolean lambda$sendAccessibleTextCaretMoved$34(int n) {
        return n == this.accCaretOffset;
    }

    private static Point[] lambda$resetSelection$33(int n) {
        return new Point[n];
    }

    private static Point lambda$resetSelection$32(int n) {
        return new Point(n, n);
    }

    private static IntStream lambda$modifyContent$31(Point point) {
        return IntStream.of(point.x, point.y - point.x);
    }

    private static Point lambda$modifyContent$30(Event event, Point point) {
        if (point.y < event.start || point.x > event.end) {
            return point;
        }
        return new Point(event.start + event.text.length(), event.start + event.text.length());
    }

    private static boolean lambda$isTextSelected$29(Point point) {
        return point.x != point.y;
    }

    private void lambda$initializeAccessible$28(Event event) {
        this.acc.setFocus(-1);
    }

    private void lambda$handleMouseDown$27(Event event) {
        boolean bl = this.middleClickPressed;
        this.middleClickPressed = false;
        if (bl && this.mouseNavigator != null) {
            this.mouseNavigator.onMouseDown(event);
        } else {
            this.pasteOnMiddleClick(event);
        }
    }

    private void lambda$installListeners$26(Event event) {
        if (!this.editable) {
            event.doit = false;
            event.start = 0;
            event.end = 0;
            event.text = "";
            return;
        }
        switch (event.detail) {
            case 3: {
                this.handleCompositionSelection(event);
                break;
            }
            case 1: {
                this.handleCompositionChanged(event);
                break;
            }
            case 2: {
                this.handleCompositionOffset(event);
            }
        }
    }

    private void lambda$installListeners$25(Event event) {
        switch (event.type) {
            case 12: {
                this.handleDispose(event);
                break;
            }
            case 1: {
                this.handleKeyDown(event);
                break;
            }
            case 2: {
                this.handleKeyUp(event);
                break;
            }
            case 35: {
                this.handleMenuDetect(event);
                break;
            }
            case 3: {
                this.handleMouseDown(event);
                break;
            }
            case 4: {
                this.handleMouseUp(event);
                break;
            }
            case 5: {
                this.handleMouseMove(event);
                break;
            }
            case 9: {
                this.handlePaint(event);
                break;
            }
            case 11: {
                this.handleResize(event);
                break;
            }
            case 31: {
                this.handleTraverse(event);
            }
        }
    }

    private static boolean lambda$getPointAtOffset$24(Point point) {
        return point.x == point.y;
    }

    private String lambda$getSelectionText$23(Point point) {
        return this.content.getTextRange(point.x, point.y - point.x);
    }

    private static int lambda$getSelectionCount$22(Point point) {
        return point.y - point.x;
    }

    private static int lambda$doWordPrevious$21(Point point) {
        return point.x;
    }

    private static boolean lambda$doWordPrevious$20(Point point) {
        return point.x != point.y;
    }

    private static int lambda$doWordNext$19(Point point) {
        return point.y;
    }

    private static boolean lambda$doWordNext$18(Point point) {
        return point.x != point.y;
    }

    private int lambda$doVisualNext$17(int n) {
        return this.getClusterNext(n, this.content.getLineAtOffset(n));
    }

    private int lambda$doVisualPrevious$16(int n) {
        return this.getClusterPrevious(n, this.content.getLineAtOffset(n));
    }

    private int lambda$doSelectionWordPrevious$15(int n) {
        return this.getWordPrevious(n, 4);
    }

    private int lambda$doSelectionWordNext$14(int n) {
        return this.content.getLineAtOffset(n);
    }

    private int lambda$doSelectionWordNext$13(int n) {
        return this.content.getLineAtOffset(n);
    }

    private int lambda$doSelectionWordNext$12(int n) {
        return this.getWordNext(n, 4);
    }

    private static Point[] lambda$doSelection$11(int n) {
        return new Point[n];
    }

    private static Point lambda$doSelection$10(Point point) {
        return new Point(point.x, point.y);
    }

    private static boolean lambda$doDeleteWordPrevious$9(Point point) {
        return point.x != point.y;
    }

    private static boolean lambda$doDeleteWordNext$8(Point point) {
        return point.x != point.y;
    }

    private static boolean lambda$doDelete$7(Point point) {
        return point.x != point.y;
    }

    private static int lambda$doCursorNext$6(Point point) {
        return point.y;
    }

    private static boolean lambda$doCursorNext$5(Point point) {
        return point.x != point.y;
    }

    private static int lambda$doCursorPrevious$4(Point point) {
        return point.x;
    }

    private static boolean lambda$doCursorPrevious$3(Point point) {
        return point.x != point.y;
    }

    private static boolean lambda$doBackspace$2(Point point) {
        return point.x != point.y;
    }

    private void lambda$new$1() {
        int n;
        int n2 = n = BidiUtil.getKeyboardLanguage() == 1 ? 131072 : 16384;
        if (n == this.caretDirection) {
            return;
        }
        if (this.getCaret() != this.defaultCaret) {
            return;
        }
        this.setCaretLocations((Point[])Arrays.stream(this.caretOffsets).mapToObj(this::getPointAtOffset).toArray(StyledText::lambda$null$0), n);
    }

    private static Point[] lambda$null$0(int n) {
        return new Point[n];
    }

    static {
        String string = SWT.getPlatform();
        IS_MAC = "cocoa".equals(string);
        IS_GTK = "gtk".equals(string);
    }

    static class TextWriter {
        private StringBuilder buffer;
        private int startOffset;
        private int endOffset;
        private boolean isClosed = false;

        public TextWriter(int n, int n2) {
            this.buffer = new StringBuilder(n2);
            this.startOffset = n;
            this.endOffset = n + n2;
        }

        public void close() {
            if (!this.isClosed) {
                this.isClosed = true;
            }
        }

        public int getCharCount() {
            return this.endOffset - this.startOffset;
        }

        public int getStart() {
            return this.startOffset;
        }

        public boolean isClosed() {
            return this.isClosed;
        }

        public String toString() {
            return this.buffer.toString();
        }

        void write(String string) {
            this.buffer.append(string);
        }

        void write(String string, int n) {
            if (n < 0 || n > this.buffer.length()) {
                return;
            }
            this.buffer.insert(n, string);
        }

        void write(int n) {
            this.buffer.append(n);
        }

        void write(char c) {
            this.buffer.append(c);
        }

        public void writeLine(String string, int n) {
            int n2;
            int n3;
            int n4;
            if (this.isClosed) {
                SWT.error(39);
            }
            if ((n4 = this.startOffset - n) >= (n3 = string.length())) {
                return;
            }
            int n5 = n4 > 0 ? n4 : 0;
            if (n5 < (n2 = Math.min(n3, this.endOffset - n))) {
                this.write(string.substring(n5, n2));
            }
        }

        public void writeLineDelimiter(String string) {
            if (this.isClosed) {
                SWT.error(39);
            }
            this.write(string);
        }
    }

    class RTFWriter
    extends TextWriter {
        static final int DEFAULT_FOREGROUND = 0;
        static final int DEFAULT_BACKGROUND = 1;
        List colorTable;
        List fontTable;
        final StyledText this$0;

        public RTFWriter(StyledText styledText, int n, int n2) {
            this.this$0 = styledText;
            super(n, n2);
            this.colorTable = new ArrayList();
            this.fontTable = new ArrayList();
            this.colorTable.add(styledText.getForeground());
            this.colorTable.add(styledText.getBackground());
            this.fontTable.add(styledText.getFont());
        }

        @Override
        public void close() {
            if (!this.isClosed()) {
                this.writeHeader();
                this.write("\n}}\u0000");
                super.close();
            }
        }

        int getColorIndex(Color color, int n) {
            if (color == null) {
                return n;
            }
            int n2 = this.colorTable.indexOf(color);
            if (n2 == -1) {
                n2 = this.colorTable.size();
                this.colorTable.add(color);
            }
            return n2;
        }

        int getFontIndex(Font font) {
            int n = this.fontTable.indexOf(font);
            if (n == -1) {
                n = this.fontTable.size();
                this.fontTable.add(font);
            }
            return n;
        }

        void write(String string, int n, int n2) {
            for (int i = n; i < n2; ++i) {
                char c = string.charAt(i);
                if (c > '\u007f') {
                    if (i > n) {
                        this.write(string.substring(n, i));
                    }
                    this.write("\\u");
                    this.write(Integer.toString((short)c));
                    this.write('?');
                    n = i + 1;
                    continue;
                }
                if (c != '}' && c != '{' && c != '\\') continue;
                if (i > n) {
                    this.write(string.substring(n, i));
                }
                this.write('\\');
                this.write(c);
                n = i + 1;
            }
            if (n < n2) {
                this.write(string.substring(n, n2));
            }
        }

        void writeHeader() {
            StringBuilder stringBuilder = new StringBuilder();
            FontData fontData = this.this$0.getFont().getFontData()[0];
            stringBuilder.append("{\\rtf1\\ansi");
            String string = System.getProperty("file.encoding").toLowerCase();
            if (string.startsWith("cp") || string.startsWith("ms")) {
                string = string.substring(2, string.length());
                stringBuilder.append("\\ansicpg");
                stringBuilder.append(string);
            }
            stringBuilder.append("\\uc1\\deff0{\\fonttbl{\\f0\\fnil ");
            stringBuilder.append(fontData.getName());
            stringBuilder.append(";");
            for (int i = 1; i < this.fontTable.size(); ++i) {
                stringBuilder.append("\\f");
                stringBuilder.append(i);
                stringBuilder.append(" ");
                Object object = ((Font)this.fontTable.get(i)).getFontData()[0];
                stringBuilder.append(((FontData)object).getName());
                stringBuilder.append(";");
            }
            stringBuilder.append("}}\n{\\colortbl");
            for (Object object : this.colorTable) {
                stringBuilder.append("\\red");
                stringBuilder.append(((Color)object).getRed());
                stringBuilder.append("\\green");
                stringBuilder.append(((Color)object).getGreen());
                stringBuilder.append("\\blue");
                stringBuilder.append(((Color)object).getBlue());
                stringBuilder.append(";");
            }
            stringBuilder.append("}\n{\\f0\\fs");
            stringBuilder.append(fontData.getHeight() * 2);
            stringBuilder.append(" ");
            this.write(stringBuilder.toString(), 0);
        }

        @Override
        public void writeLine(String string, int n) {
            StyleRange[] styleRangeArray;
            int[] nArray;
            boolean bl;
            int n2;
            int n3;
            if (this.isClosed()) {
                SWT.error(39);
            }
            int n4 = this.this$0.content.getLineAtOffset(n);
            StyledTextEvent styledTextEvent = this.this$0.getLineStyleData(n, string);
            if (styledTextEvent != null) {
                n3 = styledTextEvent.alignment;
                n2 = styledTextEvent.indent;
                bl = styledTextEvent.justify;
                nArray = styledTextEvent.ranges;
                styleRangeArray = styledTextEvent.styles;
            } else {
                n3 = this.this$0.renderer.getLineAlignment(n4, this.this$0.alignment);
                n2 = this.this$0.renderer.getLineIndent(n4, this.this$0.indent);
                bl = this.this$0.renderer.getLineJustify(n4, this.this$0.justify);
                nArray = this.this$0.renderer.getRanges(n, string.length());
                styleRangeArray = this.this$0.renderer.getStyleRanges(n, string.length(), false);
            }
            if (styleRangeArray == null) {
                styleRangeArray = new StyleRange[]{};
            }
            Color color = this.this$0.renderer.getLineBackground(n4, null);
            styledTextEvent = this.this$0.getLineBackgroundData(n, string);
            if (styledTextEvent != null && styledTextEvent.lineBackground != null) {
                color = styledTextEvent.lineBackground;
            }
            this.writeStyledLine(string, n, nArray, styleRangeArray, color, n2, n3, bl);
        }

        @Override
        public void writeLineDelimiter(String string) {
            if (this.isClosed()) {
                SWT.error(39);
            }
            this.write(string, 0, string.length());
            this.write("\\par ");
        }

        void writeStyledLine(String string, int n, int[] nArray, StyleRange[] styleRangeArray, Color color, int n2, int n3, boolean bl) {
            int n4 = string.length();
            int n5 = this.getStart();
            int n6 = n5 - n;
            if (n6 >= n4) {
                return;
            }
            int n7 = Math.max(0, n6);
            this.write("\\fi");
            this.write(n2);
            switch (n3) {
                case 16384: {
                    this.write("\\ql");
                    break;
                }
                case 0x1000000: {
                    this.write("\\qc");
                    break;
                }
                case 131072: {
                    this.write("\\qr");
                }
            }
            if (bl) {
                this.write("\\qj");
            }
            this.write(" ");
            if (color != null) {
                this.write("{\\chshdng0\\chcbpat");
                this.write(this.getColorIndex(color, 1));
                this.write(" ");
            }
            int n8 = n5 + super.getCharCount();
            int n9 = Math.min(n4, n8 - n);
            for (int i = 0; i < styleRangeArray.length; ++i) {
                int n10;
                int n11;
                int n12;
                StyleRange styleRange = styleRangeArray[i];
                if (nArray != null) {
                    n12 = nArray[i << 1] - n;
                    n11 = n12 + nArray[(i << 1) + 1];
                } else {
                    n12 = styleRange.start - n;
                    n11 = n12 + styleRange.length;
                }
                if (n11 < n6) continue;
                if (n12 >= n9) break;
                if (n7 < n12) {
                    this.write(string, n7, n12);
                    n7 = n12;
                }
                this.write("{\\cf");
                this.write(this.getColorIndex(styleRange.foreground, 0));
                int n13 = this.getColorIndex(styleRange.background, 1);
                if (n13 != 1) {
                    this.write("\\chshdng0\\chcbpat");
                    this.write(n13);
                }
                int n14 = styleRange.fontStyle;
                Font font = styleRange.font;
                if (font != null) {
                    n10 = this.getFontIndex(font);
                    this.write("\\f");
                    this.write(n10);
                    FontData fontData = font.getFontData()[0];
                    this.write("\\fs");
                    this.write(fontData.getHeight() * 2);
                    n14 = fontData.getStyle();
                }
                if ((n14 & 1) != 0) {
                    this.write("\\b");
                }
                if ((n14 & 2) != 0) {
                    this.write("\\i");
                }
                if (styleRange.underline) {
                    this.write("\\ul");
                }
                if (styleRange.strikeout) {
                    this.write("\\strike");
                }
                this.write(" ");
                n10 = Math.min(n11, n9);
                n10 = Math.max(n10, n7);
                this.write(string, n7, n10);
                if ((n14 & 1) != 0) {
                    this.write("\\b0");
                }
                if ((styleRange.fontStyle & 2) != 0) {
                    this.write("\\i0");
                }
                if (styleRange.underline) {
                    this.write("\\ul0");
                }
                if (styleRange.strikeout) {
                    this.write("\\strike0");
                }
                this.write("}");
                n7 = n10;
            }
            if (n7 < n9) {
                this.write(string, n7, n9);
            }
            if (color != null) {
                this.write("}");
            }
        }
    }

    static class Printing
    implements Runnable {
        static final int LEFT = 0;
        static final int CENTER = 1;
        static final int RIGHT = 2;
        Printer printer;
        StyledTextRenderer printerRenderer;
        StyledTextPrintOptions printOptions;
        Rectangle clientArea;
        FontData fontData;
        Font printerFont;
        Map resources;
        int tabLength;
        GC gc;
        int pageWidth;
        int startPage;
        int endPage;
        int scope;
        int startLine;
        int endLine;
        boolean singleLine;
        Point[] selection = new Point[]{new Point(0, 0)};
        boolean mirrored;
        int lineSpacing;
        int printMargin;

        Printing(StyledText styledText, Printer printer, StyledTextPrintOptions styledTextPrintOptions) {
            this.printer = printer;
            this.printOptions = styledTextPrintOptions;
            this.mirrored = (styledText.getStyle() & 0x8000000) != 0;
            this.singleLine = styledText.isSingleLine();
            this.startPage = 1;
            this.endPage = Integer.MAX_VALUE;
            PrinterData printerData = printer.getPrinterData();
            this.scope = printerData.scope;
            if (this.scope == 1) {
                this.startPage = printerData.startPage;
                this.endPage = printerData.endPage;
                if (this.endPage < this.startPage) {
                    int n = this.endPage;
                    this.endPage = this.startPage;
                    this.startPage = n;
                }
            } else if (this.scope == 2) {
                this.selection = Arrays.copyOf(styledText.selection, styledText.selection.length);
            }
            this.printerRenderer = new StyledTextRenderer(printer, null);
            this.printerRenderer.setContent(this.copyContent(styledText.getContent()));
            this.cacheLineData(styledText);
        }

        void cacheLineData(StyledText styledText) {
            StyleRange[] styleRangeArray;
            Object object;
            StyledTextRenderer styledTextRenderer = styledText.renderer;
            styledTextRenderer.copyInto(this.printerRenderer);
            this.fontData = styledText.getFont().getFontData()[0];
            this.tabLength = styledText.tabLength;
            int n = this.printerRenderer.lineCount;
            if (styledText.isListening(3001) || styledText.isListening(3007) || styledText.isListening(3002)) {
                object = this.printerRenderer.content;
                for (int i = 0; i < n; ++i) {
                    String string = object.getLine(i);
                    int n2 = object.getOffsetAtLine(i);
                    StyledTextEvent styledTextEvent = styledText.getLineBackgroundData(n2, string);
                    if (styledTextEvent != null && styledTextEvent.lineBackground != null) {
                        this.printerRenderer.setLineBackground(i, 1, styledTextEvent.lineBackground);
                    }
                    if ((styledTextEvent = styledText.getBidiSegments(n2, string)) != null) {
                        this.printerRenderer.setLineSegments(i, 1, styledTextEvent.segments);
                        this.printerRenderer.setLineSegmentChars(i, 1, styledTextEvent.segmentsChars);
                    }
                    if ((styledTextEvent = styledText.getLineStyleData(n2, string)) == null) continue;
                    this.printerRenderer.setLineIndent(i, 1, styledTextEvent.indent);
                    this.printerRenderer.setLineAlignment(i, 1, styledTextEvent.alignment);
                    this.printerRenderer.setLineJustify(i, 1, styledTextEvent.justify);
                    this.printerRenderer.setLineBullet(i, 1, styledTextEvent.bullet);
                    styleRangeArray = styledTextEvent.styles;
                    if (styleRangeArray == null || styleRangeArray.length <= 0) continue;
                    this.printerRenderer.setStyleRanges(styledTextEvent.ranges, styleRangeArray);
                }
            }
            object = styledText.getDisplay().getDPI();
            Point point = this.printer.getDPI();
            this.resources = new HashMap();
            for (int i = 0; i < n; ++i) {
                int n3;
                Color color = this.printerRenderer.getLineBackground(i, null);
                if (color != null) {
                    if (this.printOptions.printLineBackground) {
                        Color color2 = (Color)this.resources.get(color);
                        if (color2 == null) {
                            color2 = new Color(color.getRGB());
                            this.resources.put(color, color2);
                        }
                        this.printerRenderer.setLineBackground(i, 1, color2);
                    } else {
                        this.printerRenderer.setLineBackground(i, 1, null);
                    }
                }
                if ((n3 = this.printerRenderer.getLineIndent(i, 0)) == 0) continue;
                this.printerRenderer.setLineIndent(i, 1, n3 * point.x / ((Point)object).x);
            }
            StyleRange[] styleRangeArray2 = this.printerRenderer.styles;
            for (int i = 0; i < this.printerRenderer.styleCount; ++i) {
                Object object2;
                Resource resource;
                StyleRange styleRange = styleRangeArray2[i];
                styleRangeArray = styleRange.font;
                if (styleRange.font != null) {
                    resource = (Font)this.resources.get(styleRangeArray);
                    if (resource == null) {
                        resource = new Font((Device)this.printer, styleRangeArray.getFontData());
                        this.resources.put(styleRangeArray, resource);
                    }
                    styleRange.font = resource;
                }
                if ((resource = styleRange.foreground) != null) {
                    object2 = (Color)this.resources.get(resource);
                    if (this.printOptions.printTextForeground) {
                        if (object2 == null) {
                            object2 = new Color(((Color)resource).getRGB());
                            this.resources.put(resource, object2);
                        }
                        styleRange.foreground = object2;
                    } else {
                        styleRange.foreground = null;
                    }
                }
                if ((resource = styleRange.background) != null) {
                    object2 = (Color)this.resources.get(resource);
                    if (this.printOptions.printTextBackground) {
                        if (object2 == null) {
                            object2 = new Color(((Color)resource).getRGB());
                            this.resources.put(resource, object2);
                        }
                        styleRange.background = object2;
                    } else {
                        styleRange.background = null;
                    }
                }
                if (!this.printOptions.printTextFontStyle) {
                    styleRange.fontStyle = 0;
                }
                styleRange.rise = styleRange.rise * point.y / ((Point)object).y;
                object2 = styleRange.metrics;
                if (object2 == null) continue;
                ((GlyphMetrics)object2).ascent = ((GlyphMetrics)object2).ascent * point.y / ((Point)object).y;
                ((GlyphMetrics)object2).descent = ((GlyphMetrics)object2).descent * point.y / ((Point)object).y;
                ((GlyphMetrics)object2).width = ((GlyphMetrics)object2).width * point.x / ((Point)object).x;
            }
            this.lineSpacing = styledText.lineSpacing * point.y / ((Point)object).y;
            if (this.printOptions.printLineNumbers) {
                this.printMargin = 3 * point.x / ((Point)object).x;
            }
        }

        StyledTextContent copyContent(StyledTextContent styledTextContent) {
            DefaultContent defaultContent = new DefaultContent();
            int n = 0;
            for (int i = 0; i < styledTextContent.getLineCount(); ++i) {
                int n2 = i < styledTextContent.getLineCount() - 1 ? styledTextContent.getOffsetAtLine(i + 1) : styledTextContent.getCharCount();
                defaultContent.replaceTextRange(n, 0, styledTextContent.getTextRange(n, n2 - n));
                n = n2;
            }
            return defaultContent;
        }

        void dispose() {
            if (this.gc != null) {
                this.gc.dispose();
                this.gc = null;
            }
            if (this.resources != null) {
                for (Resource resource : this.resources.values()) {
                    resource.dispose();
                }
                this.resources = null;
            }
            if (this.printerFont != null) {
                this.printerFont.dispose();
                this.printerFont = null;
            }
            if (this.printerRenderer != null) {
                this.printerRenderer.dispose();
                this.printerRenderer = null;
            }
        }

        void init() {
            Object object;
            Rectangle rectangle = this.printer.computeTrim(0, 0, 0, 0);
            Point point = this.printer.getDPI();
            this.printerFont = new Font((Device)this.printer, this.fontData.getName(), this.fontData.getHeight(), 0);
            this.clientArea = this.printer.getClientArea();
            this.pageWidth = this.clientArea.width;
            this.clientArea.x = point.x + rectangle.x;
            this.clientArea.y = point.y + rectangle.y;
            Rectangle rectangle2 = this.clientArea;
            rectangle2.width -= this.clientArea.x + rectangle.width;
            Rectangle rectangle3 = this.clientArea;
            rectangle3.height -= this.clientArea.y + rectangle.height;
            int n = this.mirrored ? 0x4000000 : 0x2000000;
            this.gc = new GC(this.printer, n);
            this.gc.setFont(this.printerFont);
            this.printerRenderer.setFont(this.printerFont, this.tabLength);
            int n2 = this.printerRenderer.getLineHeight();
            if (this.printOptions.header != null) {
                object = this.clientArea;
                ((Rectangle)object).y += n2 * 2;
                Rectangle rectangle4 = this.clientArea;
                rectangle4.height -= n2 * 2;
            }
            if (this.printOptions.footer != null) {
                object = this.clientArea;
                ((Rectangle)object).height -= n2 * 2;
            }
            object = this.printerRenderer.content;
            this.startLine = 0;
            int n3 = this.endLine = this.singleLine ? 0 : object.getLineCount() - 1;
            if (this.scope == 1) {
                int n4 = this.clientArea.height / n2;
                this.startLine = (this.startPage - 1) * n4;
            } else if (this.scope == 2) {
                this.startLine = object.getLineAtOffset(this.selection[0].x);
                this.endLine = this.selection[0].y > 0 ? object.getLineAtOffset(this.selection[0].y) : this.startLine - 1;
            }
        }

        void print() {
            int n;
            int n2;
            Object object;
            int n3;
            Color color = this.gc.getBackground();
            Color color2 = this.gc.getForeground();
            int n4 = this.clientArea.y;
            int n5 = this.clientArea.x;
            int n6 = this.clientArea.width;
            int n7 = this.startPage;
            int n8 = this.clientArea.y + this.clientArea.height;
            int n9 = this.gc.getStyle() & 0x6000000;
            TextLayout textLayout = null;
            if (this.printOptions.printLineNumbers || this.printOptions.header != null || this.printOptions.footer != null) {
                textLayout = new TextLayout(this.printer);
                textLayout.setFont(this.printerFont);
            }
            if (this.printOptions.printLineNumbers) {
                n3 = 0;
                int n10 = this.endLine - this.startLine + 1;
                object = this.printOptions.lineLabels;
                if (object != null) {
                    for (n2 = this.startLine; n2 < Math.min(n10, ((String[])object).length); ++n2) {
                        if (object[n2] == null) continue;
                        textLayout.setText(object[n2]);
                        n = textLayout.getBounds().width;
                        n3 = Math.max(n3, n);
                    }
                } else {
                    StringBuilder stringBuilder = new StringBuilder("0");
                    while ((n10 /= 10) > 0) {
                        stringBuilder.append("0");
                    }
                    textLayout.setText(stringBuilder.toString());
                    n3 = textLayout.getBounds().width;
                }
                if ((n3 += this.printMargin) > n6) {
                    n3 = n6;
                }
                n5 += n3;
                n6 -= n3;
            }
            for (n3 = this.startLine; n3 <= this.endLine && n7 <= this.endPage; ++n3) {
                if (n4 == this.clientArea.y) {
                    this.printer.startPage();
                    this.printDecoration(n7, true, textLayout);
                }
                TextLayout textLayout2 = this.printerRenderer.getTextLayout(n3, n9, n6, this.lineSpacing);
                object = this.printerRenderer.getLineBackground(n3, color);
                n2 = n4 + textLayout2.getBounds().height;
                if (n2 <= n8) {
                    this.printLine(n5, n4, this.gc, color2, (Color)object, textLayout2, textLayout, n3);
                    n4 = n2;
                } else {
                    n = textLayout2.getLineCount();
                    while (n2 > n8 && n > 0) {
                        n2 -= textLayout2.getLineBounds((int)(--n)).height + textLayout2.getSpacing();
                    }
                    if (n == 0) {
                        this.printDecoration(n7, false, textLayout);
                        this.printer.endPage();
                        if (++n7 <= this.endPage) {
                            this.printer.startPage();
                            this.printDecoration(n7, true, textLayout);
                            n4 = this.clientArea.y;
                            this.printLine(n5, n4, this.gc, color2, (Color)object, textLayout2, textLayout, n3);
                            n4 += textLayout2.getBounds().height;
                        }
                    } else {
                        int n11 = n2 - n4;
                        this.gc.setClipping(this.clientArea.x, n4, this.clientArea.width, n11);
                        this.printLine(n5, n4, this.gc, color2, (Color)object, textLayout2, textLayout, n3);
                        this.gc.setClipping((Rectangle)null);
                        this.printDecoration(n7, false, textLayout);
                        this.printer.endPage();
                        if (++n7 <= this.endPage) {
                            this.printer.startPage();
                            this.printDecoration(n7, true, textLayout);
                            n4 = this.clientArea.y - n11;
                            int n12 = textLayout2.getBounds().height;
                            this.gc.setClipping(this.clientArea.x, this.clientArea.y, this.clientArea.width, n12 - n11);
                            this.printLine(n5, n4, this.gc, color2, (Color)object, textLayout2, textLayout, n3);
                            this.gc.setClipping((Rectangle)null);
                            n4 += n12;
                        }
                    }
                }
                this.printerRenderer.disposeTextLayout(textLayout2);
            }
            if (n7 <= this.endPage && n4 > this.clientArea.y) {
                this.printDecoration(n7, false, textLayout);
                this.printer.endPage();
            }
            if (textLayout != null) {
                textLayout.dispose();
            }
        }

        void printDecoration(int n, boolean bl, TextLayout textLayout) {
            String string;
            String string2 = string = bl ? this.printOptions.header : this.printOptions.footer;
            if (string == null) {
                return;
            }
            int n2 = 0;
            for (int i = 0; i < 3; ++i) {
                String string3;
                int n3 = string.indexOf("\t", n2);
                if (n3 == -1) {
                    string3 = string.substring(n2);
                    this.printDecorationSegment(string3, i, n, bl, textLayout);
                    break;
                }
                string3 = string.substring(n2, n3);
                this.printDecorationSegment(string3, i, n, bl, textLayout);
                n2 = n3 + 1;
            }
        }

        void printDecorationSegment(String string, int n, int n2, boolean bl, TextLayout textLayout) {
            int n3;
            int n4 = string.indexOf("<page>");
            if (n4 != -1) {
                n3 = 6;
                StringBuilder stringBuilder = new StringBuilder(string.substring(0, n4));
                stringBuilder.append(n2);
                stringBuilder.append(string.substring(n4 + n3));
                string = stringBuilder.toString();
            }
            if (string.length() > 0) {
                textLayout.setText(string);
                n3 = textLayout.getBounds().width;
                int n5 = this.printerRenderer.getLineHeight();
                int n6 = 0;
                if (n == 0) {
                    n6 = this.clientArea.x;
                } else if (n == 1) {
                    n6 = (this.pageWidth - n3) / 2;
                } else if (n == 2) {
                    n6 = this.clientArea.x + this.clientArea.width - n3;
                }
                int n7 = bl ? this.clientArea.y - n5 * 2 : this.clientArea.y + this.clientArea.height + n5;
                textLayout.draw(this.gc, n6, n7);
            }
        }

        void printLine(int n, int n2, GC gC, Color color, Color color2, TextLayout textLayout, TextLayout textLayout2, int n3) {
            Object object;
            if (color2 != null) {
                object = textLayout.getBounds();
                gC.setBackground(color2);
                gC.fillRectangle(n, n2, ((Rectangle)object).width, ((Rectangle)object).height);
            }
            if (this.printOptions.printLineNumbers) {
                object = textLayout.getLineMetrics(0);
                textLayout2.setAscent(((FontMetrics)object).getAscent() + ((FontMetrics)object).getLeading());
                textLayout2.setDescent(((FontMetrics)object).getDescent());
                String[] stringArray = this.printOptions.lineLabels;
                if (stringArray != null) {
                    if (0 <= n3 && n3 < stringArray.length && stringArray[n3] != null) {
                        textLayout2.setText(stringArray[n3]);
                    } else {
                        textLayout2.setText("");
                    }
                } else {
                    textLayout2.setText(String.valueOf(n3));
                }
                int n4 = n - this.printMargin - textLayout2.getBounds().width;
                textLayout2.draw(gC, n4, n2);
                textLayout2.setAscent(-1);
                textLayout2.setDescent(-1);
            }
            gC.setForeground(color);
            textLayout.draw(gC, n, n2);
        }

        @Override
        public void run() {
            String string = this.printOptions.jobName;
            if (string == null) {
                string = "Printing";
            }
            if (this.printer.startJob(string)) {
                this.init();
                this.print();
                this.dispose();
                this.printer.endJob();
            }
        }
    }
}

