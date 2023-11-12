/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.lIIIII;
import org.eclipse.swt.custom.lIIIlI;
import org.eclipse.swt.custom.lIIlII;
import org.eclipse.swt.custom.lIIll;
import org.eclipse.swt.custom.lIll;
import org.eclipse.swt.custom.llIl;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TypedListener;

public class CCombo
extends Composite {
    Text text;
    List list;
    int visibleItemCount;
    Shell popup;
    Button arrow;
    boolean hasFocus;
    Listener listener;
    Listener filter;
    Color foreground;
    Color background;
    Font font;
    Shell _shell;
    static final String PACKAGE_PREFIX = "org.eclipse.swt.custom.";

    public CCombo(Composite composite, int n) {
        int n2;
        n = CCombo.checkStyle(n);
        super(composite, n);
        this.visibleItemCount = 5;
        this._shell = super.getShell();
        this.listener = this::lambda$new$1;
        this.createText(n);
        int n3 = 1028;
        if ((n & 0x800000) != 0) {
            n3 |= 0x800000;
        }
        this.arrow = new Button(this, n3);
        this.filter = this::lambda$new$2;
        int[] nArray = new int[]{12, 15, 10, 11, 16};
        int[] nArray2 = nArray;
        for (int n4 : nArray) {
            this.addListener(n4, this.listener);
        }
        int[] nArray3 = new int[]{29, 3, 6, 7, 32, 5, 4, 37, 13, 15};
        int[] nArray4 = nArray3;
        for (int n5 : nArray3) {
            this.arrow.addListener(n5, this.listener);
        }
        this.createPopup(null, -1);
        if ((n & 0x40) == 0 && (n2 = this.list.getItemHeight()) != 0) {
            int n4;
            n4 = this.getMonitor().getClientArea().height / 3;
            this.visibleItemCount = Math.max(this.visibleItemCount, n4 / n2);
        }
        this.initAccessible();
    }

    static int checkStyle(int n) {
        int n2 = 125978632;
        return 0x80000 | n & 0x7824808;
    }

    void createText(int n) {
        String string = null;
        String string2 = null;
        Point point = null;
        int n2 = 0;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        Font font = null;
        Color color = null;
        Color color2 = null;
        Menu menu = null;
        if (this.text != null) {
            string = this.text.getText();
            string2 = this.text.getToolTipText();
            point = this.text.getSelection();
            n2 = this.text.getTextLimit();
            bl = this.text.isEnabled();
            bl3 = this.text.getEditable();
            bl2 = this.text.isFocusControl();
            font = this.text.getFont();
            color = this.text.getForeground();
            color2 = this.text.getBackground();
            menu = this.text.getMenu();
            this.text.dispose();
        }
        int n3 = 4;
        if ((n & 8) != 0) {
            n3 |= 8;
        }
        if ((n & 0x800000) != 0) {
            n3 |= 0x800000;
        }
        this.text = new Text(this, n3 |= n & 0x1024000);
        if (string != null) {
            this.text.setText(string);
            this.text.setToolTipText(string2);
            if (point != null) {
                this.text.setSelection(point);
            }
            this.text.setTextLimit(n2);
            this.text.setEnabled(bl);
            this.text.setEditable(bl3);
            if (bl2) {
                this.text.setFocus();
            }
            if (font != null && !font.isDisposed()) {
                this.text.setFont(font);
            }
            if (color != null && !color.isDisposed()) {
                this.text.setForeground(color);
            }
            if (color2 != null && !color2.isDisposed()) {
                this.text.setBackground(color2);
            }
            if (menu != null && !menu.isDisposed()) {
                this.text.setMenu(menu);
            }
            this.internalLayout(true);
        }
        int[] nArray = new int[]{14, 29, 1, 2, 35, 24, 3, 4, 8, 6, 7, 32, 5, 37, 31, 15, 25};
        int[] nArray2 = nArray;
        for (int n4 : nArray) {
            this.text.addListener(n4, this.listener);
        }
    }

    public void add(String string) {
        this.checkWidget();
        if (string == null) {
            SWT.error(4);
        }
        this.list.add(string);
    }

    public void add(String string, int n) {
        this.checkWidget();
        if (string == null) {
            SWT.error(4);
        }
        this.list.add(string, n);
    }

    public void addModifyListener(ModifyListener modifyListener) {
        this.checkWidget();
        if (modifyListener == null) {
            SWT.error(4);
        }
        TypedListener typedListener = new TypedListener(modifyListener);
        this.addListener(24, typedListener);
    }

    public void addSelectionListener(SelectionListener selectionListener) {
        this.checkWidget();
        if (selectionListener == null) {
            SWT.error(4);
        }
        TypedListener typedListener = new TypedListener(selectionListener);
        this.addListener(13, typedListener);
        this.addListener(14, typedListener);
    }

    public void addVerifyListener(VerifyListener verifyListener) {
        this.checkWidget();
        if (verifyListener == null) {
            SWT.error(4);
        }
        TypedListener typedListener = new TypedListener(verifyListener);
        this.addListener(25, typedListener);
    }

    void arrowEvent(Event event) {
        switch (event.type) {
            case 15: {
                this.handleFocus(15);
                break;
            }
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 29: 
            case 32: {
                Point point = this.getDisplay().map(this.arrow, this, event.x, event.y);
                event.x = point.x;
                event.y = point.y;
                this.notifyListeners(event.type, event);
                event.type = 0;
                break;
            }
            case 37: {
                Point point = this.getDisplay().map(this.arrow, this, event.x, event.y);
                event.x = point.x;
                event.y = point.y;
                this.notifyListeners(37, event);
                event.type = 0;
                if (this.isDisposed() || !event.doit || event.count == 0) break;
                event.doit = false;
                int n = this.getSelectionIndex();
                if (event.count > 0) {
                    this.select(Math.max(n - 1, 0));
                } else {
                    this.select(Math.min(n + 1, this.getItemCount() - 1));
                }
                if (n != this.getSelectionIndex()) {
                    Event event2 = new Event();
                    event2.time = event.time;
                    event2.stateMask = event.stateMask;
                    this.notifyListeners(13, event2);
                }
                if (!this.isDisposed()) break;
                break;
            }
            case 13: {
                this.text.setFocus();
                this.dropDown(this == false);
            }
        }
    }

    @Override
    protected void checkSubclass() {
        int n;
        String string = this.getClass().getName();
        if (!string.substring(0, (n = string.lastIndexOf(46)) + 1).equals(PACKAGE_PREFIX)) {
            SWT.error(43);
        }
    }

    public void clearSelection() {
        this.checkWidget();
        this.text.clearSelection();
        this.list.deselectAll();
    }

    void comboEvent(Event event) {
        switch (event.type) {
            case 12: {
                this.removeListener(12, this.listener);
                this.notifyListeners(12, event);
                event.type = 0;
                if (this.popup != null && !this.popup.isDisposed()) {
                    this.list.removeListener(12, this.listener);
                    this.popup.dispose();
                }
                Shell shell = this.getShell();
                shell.removeListener(27, this.listener);
                Display display = this.getDisplay();
                display.removeFilter(15, this.filter);
                this.popup = null;
                this.text = null;
                this.list = null;
                this.arrow = null;
                this._shell = null;
                break;
            }
            case 15: {
                Control control = this.getDisplay().getFocusControl();
                if (control == this.arrow || control == this.list) {
                    return;
                }
                if (this == false) {
                    this.list.setFocus();
                    break;
                }
                this.text.setFocus();
                break;
            }
            case 16: {
                this.text.clearSelection();
                break;
            }
            case 10: {
                this.dropDown(false);
                break;
            }
            case 11: {
                this.internalLayout(false);
            }
        }
    }

    @Override
    public Point computeSize(int n, int n2, boolean bl) {
        this.checkWidget();
        int n3 = 0;
        int n4 = 0;
        GC gC = new GC(this.text);
        int n5 = gC.stringExtent((String)" ").x;
        int n6 = gC.stringExtent((String)this.text.getText()).x;
        for (String n7 : this.list.getItems()) {
            n6 = Math.max(gC.stringExtent((String)n7).x, n6);
        }
        gC.dispose();
        Point point = this.text.computeSize(-1, -1, bl);
        Point point2 = this.arrow.computeSize(-1, -1, bl);
        Point point3 = this.list.computeSize(-1, -1, bl);
        int n7 = this.getBorderWidth();
        n4 = Math.max(point.y, point2.y);
        n3 = Math.max(n6 + 2 * n5 + point2.x + 2 * n7, point3.x);
        if (n != -1) {
            n3 = n;
        }
        if (n2 != -1) {
            n4 = n2;
        }
        return new Point(n3 + 2 * n7, n4 + 2 * n7);
    }

    public void copy() {
        this.checkWidget();
        this.text.copy();
    }

    void createPopup(String[] stringArray, int n) {
        this.popup = new Shell(this.getShell(), 16392);
        int n2 = this.getStyle();
        int n3 = 772;
        if ((n2 & 0x800000) != 0) {
            n3 |= 0x800000;
        }
        if ((n2 & 0x4000000) != 0) {
            n3 |= 0x4000000;
        }
        if ((n2 & 0x2000000) != 0) {
            n3 |= 0x2000000;
        }
        this.list = new List(this.popup, n3);
        if (this.font != null) {
            this.list.setFont(this.font);
        }
        if (this.foreground != null) {
            this.list.setForeground(this.foreground);
        }
        if (this.background != null) {
            this.list.setBackground(this.background);
        }
        int[] nArray = new int[]{21, 9};
        int[] nArray2 = nArray;
        for (int n4 : nArray) {
            this.popup.addListener(n4, this.listener);
        }
        int[] nArray3 = new int[]{4, 13, 31, 1, 2, 15, 16, 12};
        int[] nArray4 = nArray3;
        for (int n5 : nArray3) {
            this.list.addListener(n5, this.listener);
        }
        if (stringArray != null) {
            this.list.setItems(stringArray);
        }
        if (n != -1) {
            this.list.setSelection(n);
        }
    }

    public void cut() {
        this.checkWidget();
        this.text.cut();
    }

    public void deselect(int n) {
        this.checkWidget();
        if (0 <= n && n < this.list.getItemCount() && n == this.list.getSelectionIndex() && this.text.getText().equals(this.list.getItem(n))) {
            this.text.setText("");
            this.list.deselect(n);
        }
    }

    public void deselectAll() {
        this.checkWidget();
        this.text.setText("");
        this.list.deselectAll();
    }

    void dropDown(boolean bl) {
        ScrollBar scrollBar;
        int n;
        int n2;
        int n3;
        String[] stringArray;
        boolean bl2 = bl;
        if (this == false) {
            return;
        }
        Display display = this.getDisplay();
        if (!bl) {
            display.removeFilter(13, this.filter);
            this.popup.setVisible(false);
            if (!this.isDisposed() && this == false) {
                this.text.setFocus();
            }
            return;
        }
        if (!this.isVisible()) {
            return;
        }
        if (this.getShell() != this.popup.getParent()) {
            stringArray = this.list.getItems();
            n3 = this.list.getSelectionIndex();
            this.list.removeListener(12, this.listener);
            this.popup.dispose();
            this.popup = null;
            this.list = null;
            this.createPopup(stringArray, n3);
        }
        stringArray = this.getSize();
        n3 = this.list.getItemCount();
        n3 = n3 == 0 ? this.visibleItemCount : Math.min(this.visibleItemCount, n3);
        int n4 = this.list.getItemHeight() * n3;
        Point point = this.list.computeSize(-1, n4, false);
        Rectangle rectangle = this.getMonitor().getClientArea();
        this.list.setBounds(1, 1, Math.max(stringArray.x - 2, Math.min(point.x, rectangle.width - 2)), point.y);
        int n5 = this.list.getSelectionIndex();
        if (n5 != -1) {
            this.list.setTopIndex(n5);
        }
        Rectangle rectangle2 = this.list.getBounds();
        Rectangle rectangle3 = display.map((Control)this.getParent(), null, this.getBounds());
        int n6 = rectangle2.width + 2;
        int n7 = rectangle2.height + 2;
        int n8 = rectangle3.x;
        if (n8 + n6 > rectangle.x + rectangle.width) {
            n8 = rectangle.x + rectangle.width - n6;
        }
        if ((n2 = rectangle3.y + stringArray.y) + n7 > rectangle.y + rectangle.height) {
            int n9 = rectangle3.y - n7 < rectangle.y ? rectangle3.y - rectangle.y : n7;
            if (n9 > (n = rectangle.y + rectangle.height - n2)) {
                n7 = n9;
                n2 = rectangle3.y - n9;
            } else {
                n7 = n;
            }
            rectangle2.height = n7 - 2;
        }
        n = (scrollBar = this.list.getHorizontalBar()).isVisible() ? 0 : scrollBar.getSize().y;
        this.list.setSize(rectangle2.width, rectangle2.height - n);
        this.popup.setBounds(n8, n2, n6, n7 - n);
        this.popup.setVisible(true);
        if (this == false) {
            this.list.setFocus();
        }
        display.removeFilter(13, this.filter);
        display.addFilter(13, this.filter);
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

    public int getAlignment() {
        return this.text.getStyle() & 0x1024000;
    }

    @Override
    public Control[] getChildren() {
        this.checkWidget();
        return new Control[0];
    }

    public boolean getEditable() {
        this.checkWidget();
        return this.text.getEditable();
    }

    public String getItem(int n) {
        this.checkWidget();
        return this.list.getItem(n);
    }

    public int getItemCount() {
        this.checkWidget();
        return this.list.getItemCount();
    }

    public int getItemHeight() {
        this.checkWidget();
        return this.list.getItemHeight();
    }

    public String[] getItems() {
        this.checkWidget();
        return this.list.getItems();
    }

    public boolean getListVisible() {
        this.checkWidget();
        return this.isDropped();
    }

    @Override
    public Menu getMenu() {
        return this.text.getMenu();
    }

    public Point getSelection() {
        this.checkWidget();
        return this.text.getSelection();
    }

    public int getSelectionIndex() {
        this.checkWidget();
        return this.list.getSelectionIndex();
    }

    @Override
    public Shell getShell() {
        this.checkWidget();
        Shell shell = super.getShell();
        if (shell != this._shell) {
            if (this._shell != null && !this._shell.isDisposed()) {
                this._shell.removeListener(27, this.listener);
            }
            this._shell = shell;
        }
        return this._shell;
    }

    @Override
    public int getStyle() {
        int n = super.getStyle();
        n &= 0xFFFFFFF7;
        if (!this.text.getEditable()) {
            n |= 8;
        }
        n &= 0xFEFDBFFF;
        return n |= this.getAlignment();
    }

    public String getText() {
        this.checkWidget();
        return this.text.getText();
    }

    public int getTextHeight() {
        this.checkWidget();
        return this.text.getLineHeight();
    }

    public int getTextLimit() {
        this.checkWidget();
        return this.text.getTextLimit();
    }

    public int getVisibleItemCount() {
        this.checkWidget();
        return this.visibleItemCount;
    }

    void handleFocus(int n) {
        switch (n) {
            case 15: {
                if (this.hasFocus) {
                    return;
                }
                if (this.getEditable()) {
                    this.text.selectAll();
                }
                this.hasFocus = true;
                Shell shell = this.getShell();
                shell.removeListener(27, this.listener);
                shell.addListener(27, this.listener);
                Display display = this.getDisplay();
                display.removeFilter(15, this.filter);
                display.addFilter(15, this.filter);
                Event event = new Event();
                this.notifyListeners(15, event);
                break;
            }
            case 16: {
                if (!this.hasFocus) {
                    return;
                }
                Control control = this.getDisplay().getFocusControl();
                if (control == this.arrow || control == this.list || control == this.text) {
                    return;
                }
                this.hasFocus = false;
                Shell shell = this.getShell();
                shell.removeListener(27, this.listener);
                Display display = this.getDisplay();
                display.removeFilter(15, this.filter);
                Event event = new Event();
                this.notifyListeners(16, event);
                break;
            }
        }
    }

    void handleScroll(Event event) {
        ScrollBar scrollBar = (ScrollBar)event.widget;
        Scrollable scrollable = scrollBar.getParent();
        if (scrollable.equals(this.list)) {
            return;
        }
        CCombo cCombo = this;
        if (scrollable != null) {
            this.dropDown(false);
        }
    }

    public int indexOf(String string) {
        this.checkWidget();
        if (string == null) {
            SWT.error(4);
        }
        return this.list.indexOf(string);
    }

    public int indexOf(String string, int n) {
        this.checkWidget();
        if (string == null) {
            SWT.error(4);
        }
        return this.list.indexOf(string, n);
    }

    void initAccessible() {
        lIIIII lIIIII2 = new lIIIII(this);
        this.getAccessible().addAccessibleListener(lIIIII2);
        this.text.getAccessible().addAccessibleListener(lIIIII2);
        this.list.getAccessible().addAccessibleListener(lIIIII2);
        this.arrow.getAccessible().addAccessibleListener(new lIll(this));
        this.getAccessible().addAccessibleTextListener(new lIIlII(this));
        this.getAccessible().addAccessibleControlListener(new lIIll(this));
        this.text.getAccessible().addAccessibleControlListener(new lIIIlI(this));
        this.arrow.getAccessible().addAccessibleControlListener(new llIl(this));
    }

    void internalLayout(boolean bl) {
        if (this == false) {
            this.dropDown(false);
        }
        Rectangle rectangle = this.getClientArea();
        int n = rectangle.width;
        int n2 = rectangle.height;
        Point point = this.arrow.computeSize(-1, n2, bl);
        this.text.setBounds(0, 0, n - point.x, n2);
        this.arrow.setBounds(n - point.x, 0, point.x, point.y);
    }

    void listEvent(Event event) {
        switch (event.type) {
            case 12: {
                if (this.getShell() == this.popup.getParent()) break;
                String[] stringArray = this.list.getItems();
                int n = this.list.getSelectionIndex();
                this.popup = null;
                this.list = null;
                this.createPopup(stringArray, n);
                break;
            }
            case 15: {
                this.handleFocus(15);
                break;
            }
            case 16: {
                boolean bl;
                Point point = this.arrow.toControl(this.getDisplay().getCursorLocation());
                Point point2 = this.arrow.getSize();
                Rectangle rectangle = new Rectangle(0, 0, point2.x, point2.y);
                if (!rectangle.contains(point)) {
                    this.dropDown(false);
                    break;
                }
                boolean bl2 = bl = this.getDisplay().getActiveShell() == this.getShell();
                if (bl) break;
                this.dropDown(false);
                break;
            }
            case 4: {
                if (event.button != 1) {
                    return;
                }
                this.dropDown(false);
                break;
            }
            case 13: {
                int n = this.list.getSelectionIndex();
                if (n == -1) {
                    return;
                }
                this.text.setText(this.list.getItem(n));
                if (this.text.getEditable() && this.text.isFocusControl()) {
                    this.text.selectAll();
                }
                this.list.setSelection(n);
                Event event2 = new Event();
                event2.time = event.time;
                event2.stateMask = event.stateMask;
                event2.doit = event.doit;
                this.notifyListeners(13, event2);
                event.doit = event2.doit;
                break;
            }
            case 31: {
                switch (event.detail) {
                    case 2: 
                    case 4: 
                    case 32: 
                    case 64: {
                        event.doit = false;
                        break;
                    }
                    case 8: 
                    case 16: {
                        event.doit = this.text.traverse(event.detail);
                        event.detail = 0;
                        if (event.doit) {
                            this.dropDown(false);
                        }
                        return;
                    }
                }
                Event event3 = new Event();
                event3.time = event.time;
                event3.detail = event.detail;
                event3.doit = event.doit;
                event3.character = event.character;
                event3.keyCode = event.keyCode;
                event3.keyLocation = event.keyLocation;
                this.notifyListeners(31, event3);
                event.doit = event3.doit;
                event.detail = event3.detail;
                break;
            }
            case 2: {
                Event event4 = new Event();
                event4.time = event.time;
                event4.character = event.character;
                event4.keyCode = event.keyCode;
                event4.keyLocation = event.keyLocation;
                event4.stateMask = event.stateMask;
                this.notifyListeners(2, event4);
                event.doit = event4.doit;
                break;
            }
            case 1: {
                Event event5;
                if (event.character == '\u001b') {
                    this.dropDown(false);
                }
                if ((event.stateMask & 0x10000) != 0 && (event.keyCode == 0x1000001 || event.keyCode == 0x1000002)) {
                    this.dropDown(false);
                }
                if (event.character == '\r') {
                    this.dropDown(false);
                    event5 = new Event();
                    event5.time = event.time;
                    event5.stateMask = event.stateMask;
                    this.notifyListeners(14, event5);
                }
                if (this.isDisposed()) break;
                event5 = new Event();
                event5.time = event.time;
                event5.character = event.character;
                event5.keyCode = event.keyCode;
                event5.keyLocation = event.keyLocation;
                event5.stateMask = event.stateMask;
                this.notifyListeners(1, event5);
                event.doit = event5.doit;
                break;
            }
        }
    }

    public void paste() {
        this.checkWidget();
        this.text.paste();
    }

    void popupEvent(Event event) {
        switch (event.type) {
            case 9: {
                Rectangle rectangle = this.list.getBounds();
                Color color = this.getDisplay().getSystemColor(2);
                event.gc.setForeground(color);
                event.gc.drawRectangle(0, 0, rectangle.width + 1, rectangle.height + 1);
                break;
            }
            case 21: {
                event.doit = false;
                this.dropDown(false);
            }
        }
    }

    @Override
    public void redraw() {
        super.redraw();
        this.text.redraw();
        this.arrow.redraw();
        if (this.popup.isVisible()) {
            this.list.redraw();
        }
    }

    @Override
    public void redraw(int n, int n2, int n3, int n4, boolean bl) {
        super.redraw(n, n2, n3, n4, true);
    }

    public void remove(int n) {
        this.checkWidget();
        this.list.remove(n);
    }

    public void remove(int n, int n2) {
        this.checkWidget();
        this.list.remove(n, n2);
    }

    public void remove(String string) {
        this.checkWidget();
        if (string == null) {
            SWT.error(4);
        }
        this.list.remove(string);
    }

    public void removeAll() {
        this.checkWidget();
        this.text.setText("");
        this.list.removeAll();
    }

    public void removeModifyListener(ModifyListener modifyListener) {
        this.checkWidget();
        if (modifyListener == null) {
            SWT.error(4);
        }
        this.removeListener(24, modifyListener);
    }

    public void removeSelectionListener(SelectionListener selectionListener) {
        this.checkWidget();
        if (selectionListener == null) {
            SWT.error(4);
        }
        this.removeListener(13, selectionListener);
        this.removeListener(14, selectionListener);
    }

    public void removeVerifyListener(VerifyListener verifyListener) {
        this.checkWidget();
        if (verifyListener == null) {
            SWT.error(4);
        }
        this.removeListener(25, verifyListener);
    }

    public void select(int n) {
        this.checkWidget();
        if (n == -1) {
            this.list.deselectAll();
            this.text.setText("");
            return;
        }
        if (0 <= n && n < this.list.getItemCount() && n != this.getSelectionIndex()) {
            this.text.setText(this.list.getItem(n));
            if (this.text.getEditable() && this.text.isFocusControl()) {
                this.text.selectAll();
            }
            this.list.select(n);
            this.list.showSelection();
        }
    }

    public void setAlignment(int n) {
        this.checkWidget();
        int n2 = this.getStyle() & 0xFEFDBFFF;
        this.createText(n2 | n);
    }

    @Override
    public void setBackground(Color color) {
        super.setBackground(color);
        this.background = color;
        if (this.text != null) {
            this.text.setBackground(color);
        }
        if (this.list != null) {
            this.list.setBackground(color);
        }
        if (this.arrow != null) {
            this.arrow.setBackground(color);
        }
    }

    public void setEditable(boolean bl) {
        this.checkWidget();
        this.text.setEditable(bl);
    }

    @Override
    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        if (this.popup != null) {
            this.popup.setVisible(false);
        }
        if (this.text != null) {
            this.text.setEnabled(bl);
        }
        if (this.arrow != null) {
            this.arrow.setEnabled(bl);
        }
    }

    @Override
    public boolean setFocus() {
        this.checkWidget();
        return this.isEnabled() && this.getVisible() && (this != false || this.text.setFocus());
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        this.font = font;
        this.text.setFont(font);
        this.list.setFont(font);
        this.internalLayout(true);
    }

    @Override
    public void setForeground(Color color) {
        super.setForeground(color);
        this.foreground = color;
        if (this.text != null) {
            this.text.setForeground(color);
        }
        if (this.list != null) {
            this.list.setForeground(color);
        }
        if (this.arrow != null) {
            this.arrow.setForeground(color);
        }
    }

    public void setItem(int n, String string) {
        this.checkWidget();
        this.list.setItem(n, string);
    }

    public void setItems(String[] stringArray) {
        this.checkWidget();
        this.list.setItems(stringArray);
        if (!this.text.getEditable()) {
            this.text.setText("");
        }
    }

    @Override
    public void setLayout(Layout layout) {
        this.checkWidget();
    }

    public void setListVisible(boolean bl) {
        this.checkWidget();
        this.dropDown(bl);
    }

    @Override
    public void setMenu(Menu menu) {
        this.text.setMenu(menu);
    }

    public void setSelection(Point point) {
        this.checkWidget();
        if (point == null) {
            SWT.error(4);
        }
        this.text.setSelection(point.x, point.y);
    }

    public void setText(String string) {
        int n;
        this.checkWidget();
        if (string == null) {
            SWT.error(4);
        }
        if ((n = this.list.indexOf(string)) == -1) {
            this.list.deselectAll();
            this.text.setText(string);
            return;
        }
        this.text.setText(string);
        if (this.text.getEditable() && this.text.isFocusControl()) {
            this.text.selectAll();
        }
        this.list.setSelection(n);
        this.list.showSelection();
    }

    public void setTextLimit(int n) {
        this.checkWidget();
        this.text.setTextLimit(n);
    }

    @Override
    public void setToolTipText(String string) {
        this.checkWidget();
        super.setToolTipText(string);
        this.arrow.setToolTipText(string);
        this.text.setToolTipText(string);
    }

    @Override
    public void setVisible(boolean bl) {
        super.setVisible(bl);
        if (this.isDisposed()) {
            return;
        }
        if (this.popup == null || this.popup.isDisposed()) {
            return;
        }
        if (!bl) {
            this.popup.setVisible(false);
        }
    }

    public void setVisibleItemCount(int n) {
        this.checkWidget();
        if (n < 0) {
            return;
        }
        this.visibleItemCount = n;
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

    void textEvent(Event event) {
        switch (event.type) {
            case 15: {
                this.handleFocus(15);
                break;
            }
            case 14: {
                this.dropDown(false);
                Event event2 = new Event();
                event2.time = event.time;
                event2.stateMask = event.stateMask;
                this.notifyListeners(14, event2);
                break;
            }
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 29: 
            case 32: {
                Point point = this.getDisplay().map(this.text, this, event.x, event.y);
                event.x = point.x;
                event.y = point.y;
                this.notifyListeners(event.type, event);
                event.type = 0;
                break;
            }
            case 1: {
                Event event3 = new Event();
                event3.time = event.time;
                event3.character = event.character;
                event3.keyCode = event.keyCode;
                event3.keyLocation = event.keyLocation;
                event3.stateMask = event.stateMask;
                this.notifyListeners(1, event3);
                if (this.isDisposed() || !(event.doit = event3.doit) || event.keyCode != 0x1000001 && event.keyCode != 0x1000002) break;
                event.doit = false;
                if ((event.stateMask & 0x10000) != 0) {
                    boolean bl = this.isDropped();
                    if (this.text.getEditable() && this.text.isFocusControl()) {
                        this.text.selectAll();
                    }
                    if (!bl) {
                        this.setFocus();
                    }
                    this.dropDown(!bl);
                    break;
                }
                int n = this.getSelectionIndex();
                if (event.keyCode == 0x1000001) {
                    this.select(Math.max(n - 1, 0));
                } else {
                    this.select(Math.min(n + 1, this.getItemCount() - 1));
                }
                if (n != this.getSelectionIndex()) {
                    Event event4 = new Event();
                    event4.time = event.time;
                    event4.stateMask = event.stateMask;
                    this.notifyListeners(13, event4);
                }
                if (!this.isDisposed()) break;
                break;
            }
            case 2: {
                Event event5 = new Event();
                event5.time = event.time;
                event5.character = event.character;
                event5.keyCode = event.keyCode;
                event5.keyLocation = event.keyLocation;
                event5.stateMask = event.stateMask;
                this.notifyListeners(2, event5);
                event.doit = event5.doit;
                break;
            }
            case 35: {
                Event event6 = new Event();
                event6.time = event.time;
                event6.detail = event.detail;
                event6.x = event.x;
                event6.y = event.y;
                if (event.detail == 1) {
                    Point point = this.getDisplay().map((Control)this.text, null, this.text.getCaretLocation());
                    event6.x = point.x;
                    event6.y = point.y;
                }
                this.notifyListeners(35, event6);
                event.doit = event6.doit;
                event.x = event6.x;
                event.y = event6.y;
                break;
            }
            case 24: {
                this.list.deselectAll();
                Event event7 = new Event();
                event7.time = event.time;
                this.notifyListeners(24, event7);
                break;
            }
            case 3: {
                Point point = this.getDisplay().map(this.text, this, event.x, event.y);
                Event event8 = new Event();
                event8.button = event.button;
                event8.count = event.count;
                event8.stateMask = event.stateMask;
                event8.time = event.time;
                event8.x = point.x;
                event8.y = point.y;
                this.notifyListeners(3, event8);
                if (this.isDisposed() || !(event.doit = event8.doit)) break;
                if (event.button != 1) {
                    return;
                }
                if (this.text.getEditable()) {
                    return;
                }
                boolean bl = this.isDropped();
                if (this.text.getEditable() && this.text.isFocusControl()) {
                    this.text.selectAll();
                }
                if (!bl) {
                    this.setFocus();
                }
                this.dropDown(!bl);
                break;
            }
            case 4: {
                Point point = this.getDisplay().map(this.text, this, event.x, event.y);
                Event event9 = new Event();
                event9.button = event.button;
                event9.count = event.count;
                event9.stateMask = event.stateMask;
                event9.time = event.time;
                event9.x = point.x;
                event9.y = point.y;
                this.notifyListeners(4, event9);
                if (this.isDisposed() || !(event.doit = event9.doit)) break;
                if (event.button != 1) {
                    return;
                }
                if (this.text.getEditable()) {
                    return;
                }
                if (!this.text.getEditable() || !this.text.isFocusControl()) break;
                this.text.selectAll();
                break;
            }
            case 37: {
                this.notifyListeners(37, event);
                event.type = 0;
                if (this.isDisposed() || !event.doit || event.count == 0) break;
                event.doit = false;
                int n = this.getSelectionIndex();
                if (event.count > 0) {
                    this.select(Math.max(n - 1, 0));
                } else {
                    this.select(Math.min(n + 1, this.getItemCount() - 1));
                }
                if (n != this.getSelectionIndex()) {
                    Event event10 = new Event();
                    event10.time = event.time;
                    event10.stateMask = event.stateMask;
                    this.notifyListeners(13, event10);
                }
                if (!this.isDisposed()) break;
                break;
            }
            case 31: {
                switch (event.detail) {
                    case 32: 
                    case 64: {
                        event.doit = false;
                        break;
                    }
                    case 8: {
                        event.doit = this.traverse(8);
                        event.detail = 0;
                        return;
                    }
                }
                Event event11 = new Event();
                event11.time = event.time;
                event11.detail = event.detail;
                event11.doit = event.doit;
                event11.character = event.character;
                event11.keyCode = event.keyCode;
                event11.keyLocation = event.keyLocation;
                this.notifyListeners(31, event11);
                event.doit = event11.doit;
                event.detail = event11.detail;
                break;
            }
            case 25: {
                Event event12 = new Event();
                event12.text = event.text;
                event12.start = event.start;
                event12.end = event.end;
                event12.character = event.character;
                event12.keyCode = event.keyCode;
                event12.keyLocation = event.keyLocation;
                event12.stateMask = event.stateMask;
                this.notifyListeners(25, event12);
                event.text = event12.text;
                event.doit = event12.doit;
                break;
            }
        }
    }

    @Override
    public boolean traverse(int n) {
        if (n == 64 || n == 16) {
            return this.text.traverse(n);
        }
        return super.traverse(n);
    }

    private static boolean lambda$isFocusControl$3(Control control) {
        return control != null && !control.isDisposed() && control.isFocusControl();
    }

    private void lambda$new$2(Event event) {
        Shell shell;
        if (this.isDisposed()) {
            return;
        }
        if (event.type == 13) {
            if (event.widget instanceof ScrollBar) {
                this.handleScroll(event);
            }
            return;
        }
        if (event.widget instanceof Control && (shell = ((Control)event.widget).getShell()) == this.getShell()) {
            this.handleFocus(16);
        }
    }

    private void lambda$new$1(Event event) {
        if (this.isDisposed()) {
            return;
        }
        if (this.popup == event.widget) {
            this.popupEvent(event);
            return;
        }
        if (this.text == event.widget) {
            this.textEvent(event);
            return;
        }
        if (this.list == event.widget) {
            this.listEvent(event);
            return;
        }
        if (this.arrow == event.widget) {
            this.arrowEvent(event);
            return;
        }
        if (this == event.widget) {
            this.comboEvent(event);
            return;
        }
        if (this.getShell() == event.widget) {
            this.getDisplay().asyncExec(this::lambda$null$0);
        }
    }

    private void lambda$null$0() {
        if (!this.isDisposed()) {
            this.handleFocus(16);
        }
    }
}

