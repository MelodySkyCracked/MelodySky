/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleActionEvent;
import org.eclipse.swt.accessibility.AccessibleActionListener;
import org.eclipse.swt.accessibility.AccessibleAttributeEvent;
import org.eclipse.swt.accessibility.AccessibleAttributeListener;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleControlListener;
import org.eclipse.swt.accessibility.AccessibleEditableTextEvent;
import org.eclipse.swt.accessibility.AccessibleEditableTextListener;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.accessibility.AccessibleHyperlinkEvent;
import org.eclipse.swt.accessibility.AccessibleHyperlinkListener;
import org.eclipse.swt.accessibility.AccessibleListener;
import org.eclipse.swt.accessibility.AccessibleTableCellEvent;
import org.eclipse.swt.accessibility.AccessibleTableCellListener;
import org.eclipse.swt.accessibility.AccessibleTableEvent;
import org.eclipse.swt.accessibility.AccessibleTableListener;
import org.eclipse.swt.accessibility.AccessibleTextAttributeEvent;
import org.eclipse.swt.accessibility.AccessibleTextEvent;
import org.eclipse.swt.accessibility.AccessibleTextExtendedListener;
import org.eclipse.swt.accessibility.AccessibleTextListener;
import org.eclipse.swt.accessibility.AccessibleValueEvent;
import org.eclipse.swt.accessibility.AccessibleValueListener;
import org.eclipse.swt.accessibility.I;
import org.eclipse.swt.accessibility.Relation;
import org.eclipse.swt.accessibility.l;
import org.eclipse.swt.accessibility.lI;
import org.eclipse.swt.accessibility.lII;
import org.eclipse.swt.accessibility.lIII;
import org.eclipse.swt.accessibility.lIIl;
import org.eclipse.swt.accessibility.lIl;
import org.eclipse.swt.accessibility.lIlI;
import org.eclipse.swt.accessibility.llII;
import org.eclipse.swt.accessibility.llIl;
import org.eclipse.swt.accessibility.lll;
import org.eclipse.swt.accessibility.llll;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IAccessible;
import org.eclipse.swt.internal.ole.win32.IEnumVARIANT;
import org.eclipse.swt.internal.ole.win32.IServiceProvider;
import org.eclipse.swt.internal.ole.win32.VARIANT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TVITEM;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

public class Accessible {
    static final int MAX_RELATION_TYPES = 15;
    static final int TABLE_MODEL_CHANGE_SIZE = 5;
    static final int TEXT_CHANGE_SIZE = 4;
    static final int SCROLL_RATE = 100;
    static final boolean DEBUG = false;
    static final String PROPERTY_USEIA2 = "org.eclipse.swt.accessibility.UseIA2";
    static boolean UseIA2 = true;
    static int UniqueID = -16;
    int refCount = 0;
    int enumIndex = 0;
    Runnable timer;
    COMObject objIAccessible;
    COMObject objIEnumVARIANT;
    COMObject objIServiceProvider;
    COMObject objIAccessibleApplication;
    COMObject objIAccessibleEditableText;
    COMObject objIAccessibleHyperlink;
    COMObject objIAccessibleHypertext;
    COMObject objIAccessibleTable2;
    COMObject objIAccessibleTableCell;
    COMObject objIAccessibleValue;
    IAccessible iaccessible;
    List accessibleListeners;
    List accessibleControlListeners;
    List accessibleTextListeners;
    List accessibleActionListeners;
    List accessibleEditableTextListeners;
    List accessibleHyperlinkListeners;
    List accessibleTableListeners;
    List accessibleTableCellListeners;
    List accessibleTextExtendedListeners;
    List accessibleValueListeners;
    List accessibleAttributeListeners;
    Relation[] relations = new Relation[15];
    Object[] variants;
    Accessible parent;
    List children = new ArrayList();
    Control control;
    int uniqueID = -1;
    int[] tableChange;
    Object[] textDeleted;
    Object[] textInserted;
    ToolItem item;

    public Accessible(Accessible accessible) {
        this.parent = Accessible.checkNull(accessible);
        this.control = accessible.control;
        accessible.children.add(this);
        this.AddRef();
    }

    @Deprecated
    protected Accessible() {
    }

    Accessible(Control control) {
        this.control = control;
        long[] lArray = new long[]{0L};
        int n = COM.CreateStdAccessibleObject(control.handle, -4, COM.IIDIAccessible, lArray);
        if (lArray[0] == 0L) {
            return;
        }
        if (n != 0) {
            OLE.error(1001, n);
        }
        this.iaccessible = new IAccessible(lArray[0]);
        this.createIAccessible();
        this.AddRef();
    }

    Accessible(Accessible accessible, long l2) {
        this(accessible);
        this.iaccessible = new IAccessible(l2);
    }

    static Accessible checkNull(Accessible accessible) {
        if (accessible == null) {
            SWT.error(4);
        }
        return accessible;
    }

    void createIAccessible() {
        this.objIAccessible = new lIIl(this, new int[]{2, 0, 0, 1, 3, 5, 8, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 2, 1, 1, 2, 2, 5, 3, 3, 1, 2, 2, 1, 2, 3, 1, 1, 3, 3, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 1});
    }

    void createIAccessibleApplication() {
        this.objIAccessibleApplication = new lIl(this, new int[]{2, 0, 0, 1, 1, 1, 1});
    }

    void createIAccessibleEditableText() {
        this.objIAccessibleEditableText = new llll(this, new int[]{2, 0, 0, 2, 2, 2, 2, 1, 3, 3});
    }

    void createIAccessibleHyperlink() {
        this.objIAccessibleHyperlink = new lI(this, new int[]{2, 0, 0, 1, 1, 2, 4, 2, 2, 2, 2, 1, 1, 1});
    }

    void createIAccessibleHypertext() {
        this.objIAccessibleHypertext = new llII(this, new int[]{2, 0, 0, 2, 4, 1, 6, 1, 4, 3, 3, 5, 5, 5, 1, 1, 3, 1, 3, 5, 1, 1, 1, 2, 2});
    }

    void createIAccessibleTable2() {
        this.objIAccessibleTable2 = new lIII(this, new int[]{2, 0, 0, 3, 1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 2, 2, 1, 1, 1, 1, 1});
    }

    void createIAccessibleTableCell() {
        this.objIAccessibleTableCell = new llIl(this, new int[]{2, 0, 0, 1, 2, 1, 1, 2, 1, 1, 5, 1});
    }

    void createIAccessibleValue() {
        this.objIAccessibleValue = new lll(this, new int[]{2, 0, 0, 1, 1, 1, 1});
    }

    void createIEnumVARIANT() {
        this.objIEnumVARIANT = new l(this, new int[]{2, 0, 0, 3, 1, 0, 1});
    }

    void createIServiceProvider() {
        this.objIServiceProvider = new lIlI(this, new int[]{2, 0, 0, 3});
    }

    public static Accessible internal_new_Accessible(Control control) {
        return new Accessible(control);
    }

    public void addAccessibleListener(AccessibleListener accessibleListener) {
        this.checkWidget();
        if (accessibleListener == null) {
            SWT.error(4);
        }
        if (this.accessibleListeners == null) {
            this.accessibleListeners = new ArrayList();
        }
        this.accessibleListeners.add(accessibleListener);
    }

    public void addAccessibleControlListener(AccessibleControlListener accessibleControlListener) {
        this.checkWidget();
        if (accessibleControlListener == null) {
            SWT.error(4);
        }
        if (this.accessibleControlListeners == null) {
            this.accessibleControlListeners = new ArrayList();
        }
        this.accessibleControlListeners.add(accessibleControlListener);
    }

    public void addAccessibleTextListener(AccessibleTextListener accessibleTextListener) {
        this.checkWidget();
        if (accessibleTextListener == null) {
            SWT.error(4);
        }
        if (accessibleTextListener instanceof AccessibleTextExtendedListener) {
            if (this.accessibleTextExtendedListeners == null) {
                this.accessibleTextExtendedListeners = new ArrayList();
            }
            this.accessibleTextExtendedListeners.add((AccessibleTextExtendedListener)accessibleTextListener);
        } else {
            if (this.accessibleTextListeners == null) {
                this.accessibleTextListeners = new ArrayList();
            }
            this.accessibleTextListeners.add(accessibleTextListener);
        }
    }

    public void addAccessibleActionListener(AccessibleActionListener accessibleActionListener) {
        this.checkWidget();
        if (accessibleActionListener == null) {
            SWT.error(4);
        }
        if (this.accessibleActionListeners == null) {
            this.accessibleActionListeners = new ArrayList();
        }
        this.accessibleActionListeners.add(accessibleActionListener);
    }

    public void addAccessibleEditableTextListener(AccessibleEditableTextListener accessibleEditableTextListener) {
        this.checkWidget();
        if (accessibleEditableTextListener == null) {
            SWT.error(4);
        }
        if (this.accessibleEditableTextListeners == null) {
            this.accessibleEditableTextListeners = new ArrayList();
        }
        this.accessibleEditableTextListeners.add(accessibleEditableTextListener);
    }

    public void addAccessibleHyperlinkListener(AccessibleHyperlinkListener accessibleHyperlinkListener) {
        this.checkWidget();
        if (accessibleHyperlinkListener == null) {
            SWT.error(4);
        }
        if (this.accessibleHyperlinkListeners == null) {
            this.accessibleHyperlinkListeners = new ArrayList();
        }
        this.accessibleHyperlinkListeners.add(accessibleHyperlinkListener);
    }

    public void addAccessibleTableListener(AccessibleTableListener accessibleTableListener) {
        this.checkWidget();
        if (accessibleTableListener == null) {
            SWT.error(4);
        }
        if (this.accessibleTableListeners == null) {
            this.accessibleTableListeners = new ArrayList();
        }
        this.accessibleTableListeners.add(accessibleTableListener);
    }

    public void addAccessibleTableCellListener(AccessibleTableCellListener accessibleTableCellListener) {
        this.checkWidget();
        if (accessibleTableCellListener == null) {
            SWT.error(4);
        }
        if (this.accessibleTableCellListeners == null) {
            this.accessibleTableCellListeners = new ArrayList();
        }
        this.accessibleTableCellListeners.add(accessibleTableCellListener);
    }

    public void addAccessibleValueListener(AccessibleValueListener accessibleValueListener) {
        this.checkWidget();
        if (accessibleValueListener == null) {
            SWT.error(4);
        }
        if (this.accessibleValueListeners == null) {
            this.accessibleValueListeners = new ArrayList();
        }
        this.accessibleValueListeners.add(accessibleValueListener);
    }

    public void addAccessibleAttributeListener(AccessibleAttributeListener accessibleAttributeListener) {
        this.checkWidget();
        if (accessibleAttributeListener == null) {
            SWT.error(4);
        }
        if (this.accessibleAttributeListeners == null) {
            this.accessibleAttributeListeners = new ArrayList();
        }
        this.accessibleAttributeListeners.add(accessibleAttributeListener);
    }

    public void addRelation(int n, Accessible accessible) {
        this.checkWidget();
        if (accessible == null) {
            SWT.error(4);
        }
        if (this.relations[n] == null) {
            this.relations[n] = new Relation(this, n);
        }
        this.relations[n].addTarget(accessible);
    }

    public void dispose() {
        if (this.parent == null) {
            return;
        }
        this.Release();
        this.parent.children.remove(this);
        this.parent = null;
    }

    long getAddress() {
        if (this.objIAccessible == null) {
            this.createIAccessible();
        }
        return this.objIAccessible.getAddress();
    }

    public Control getControl() {
        return this.control;
    }

    public void internal_dispose_Accessible() {
        if (this.iaccessible != null) {
            this.iaccessible.Release();
        }
        this.iaccessible = null;
        this.Release();
        ArrayList arrayList = new ArrayList(this.children);
        for (Accessible accessible : arrayList) {
            accessible.dispose();
        }
    }

    public long internal_WM_GETOBJECT(long l2, long l3) {
        if (this.objIAccessible == null) {
            return 0L;
        }
        if ((int)l3 == -4) {
            return COM.LresultFromObject(COM.IIDIAccessible, l2, this.objIAccessible.getAddress());
        }
        return 0L;
    }

    public void removeAccessibleListener(AccessibleListener accessibleListener) {
        this.checkWidget();
        if (accessibleListener == null) {
            SWT.error(4);
        }
        if (this.accessibleListeners != null) {
            this.accessibleListeners.remove(accessibleListener);
            if (this.accessibleListeners.isEmpty()) {
                this.accessibleListeners = null;
            }
        }
    }

    public void removeAccessibleControlListener(AccessibleControlListener accessibleControlListener) {
        this.checkWidget();
        if (accessibleControlListener == null) {
            SWT.error(4);
        }
        if (this.accessibleControlListeners != null) {
            this.accessibleControlListeners.remove(accessibleControlListener);
            if (this.accessibleControlListeners.isEmpty()) {
                this.accessibleControlListeners = null;
            }
        }
    }

    public void removeAccessibleTextListener(AccessibleTextListener accessibleTextListener) {
        this.checkWidget();
        if (accessibleTextListener == null) {
            SWT.error(4);
        }
        if (accessibleTextListener instanceof AccessibleTextExtendedListener) {
            if (this.accessibleTextExtendedListeners != null) {
                this.accessibleTextExtendedListeners.remove(accessibleTextListener);
                if (this.accessibleTextExtendedListeners.isEmpty()) {
                    this.accessibleTextExtendedListeners = null;
                }
            }
        } else if (this.accessibleTextListeners != null) {
            this.accessibleTextListeners.remove(accessibleTextListener);
            if (this.accessibleTextListeners.isEmpty()) {
                this.accessibleTextListeners = null;
            }
        }
    }

    public void removeAccessibleActionListener(AccessibleActionListener accessibleActionListener) {
        this.checkWidget();
        if (accessibleActionListener == null) {
            SWT.error(4);
        }
        if (this.accessibleActionListeners != null) {
            this.accessibleActionListeners.remove(accessibleActionListener);
            if (this.accessibleActionListeners.isEmpty()) {
                this.accessibleActionListeners = null;
            }
        }
    }

    public void removeAccessibleEditableTextListener(AccessibleEditableTextListener accessibleEditableTextListener) {
        this.checkWidget();
        if (accessibleEditableTextListener == null) {
            SWT.error(4);
        }
        if (this.accessibleEditableTextListeners != null) {
            this.accessibleEditableTextListeners.remove(accessibleEditableTextListener);
            if (this.accessibleEditableTextListeners.isEmpty()) {
                this.accessibleEditableTextListeners = null;
            }
        }
    }

    public void removeAccessibleHyperlinkListener(AccessibleHyperlinkListener accessibleHyperlinkListener) {
        this.checkWidget();
        if (accessibleHyperlinkListener == null) {
            SWT.error(4);
        }
        if (this.accessibleHyperlinkListeners != null) {
            this.accessibleHyperlinkListeners.remove(accessibleHyperlinkListener);
            if (this.accessibleHyperlinkListeners.isEmpty()) {
                this.accessibleHyperlinkListeners = null;
            }
        }
    }

    public void removeAccessibleTableListener(AccessibleTableListener accessibleTableListener) {
        this.checkWidget();
        if (accessibleTableListener == null) {
            SWT.error(4);
        }
        if (this.accessibleTableListeners != null) {
            this.accessibleTableListeners.remove(accessibleTableListener);
            if (this.accessibleTableListeners.isEmpty()) {
                this.accessibleTableListeners = null;
            }
        }
    }

    public void removeAccessibleTableCellListener(AccessibleTableCellListener accessibleTableCellListener) {
        this.checkWidget();
        if (accessibleTableCellListener == null) {
            SWT.error(4);
        }
        if (this.accessibleTableCellListeners != null) {
            this.accessibleTableCellListeners.remove(accessibleTableCellListener);
            if (this.accessibleTableCellListeners.isEmpty()) {
                this.accessibleTableCellListeners = null;
            }
        }
    }

    public void removeAccessibleValueListener(AccessibleValueListener accessibleValueListener) {
        this.checkWidget();
        if (accessibleValueListener == null) {
            SWT.error(4);
        }
        if (this.accessibleValueListeners != null) {
            this.accessibleValueListeners.remove(accessibleValueListener);
            if (this.accessibleValueListeners.isEmpty()) {
                this.accessibleValueListeners = null;
            }
        }
    }

    public void removeAccessibleAttributeListener(AccessibleAttributeListener accessibleAttributeListener) {
        this.checkWidget();
        if (accessibleAttributeListener == null) {
            SWT.error(4);
        }
        if (this.accessibleAttributeListeners != null) {
            this.accessibleAttributeListeners.remove(accessibleAttributeListener);
            if (this.accessibleAttributeListeners.isEmpty()) {
                this.accessibleAttributeListeners = null;
            }
        }
    }

    public void removeRelation(int n, Accessible accessible) {
        Relation relation;
        this.checkWidget();
        if (accessible == null) {
            SWT.error(4);
        }
        if ((relation = this.relations[n]) != null) {
            relation.removeTarget(accessible);
            if (!relation.hasTargets()) {
                this.relations[n].Release();
                this.relations[n] = null;
            }
        }
    }

    public void sendEvent(int n, Object object) {
        this.checkWidget();
        if (!this.isATRunning()) {
            return;
        }
        if (!UseIA2) {
            return;
        }
        switch (n) {
            case 518: {
                if (!(object instanceof int[]) || ((int[])object).length != 5) break;
                this.tableChange = (int[])object;
                OS.NotifyWinEvent(278, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 524: {
                if (!(object instanceof Object[]) || ((Object[])object).length != 4) break;
                Object[] objectArray = (Object[])object;
                int n2 = (Integer)objectArray[0];
                switch (n2) {
                    case 1: {
                        this.textDeleted = (Object[])object;
                        OS.NotifyWinEvent(287, this.control.handle, -4, this.eventChildID());
                        break;
                    }
                    case 0: {
                        this.textInserted = (Object[])object;
                        OS.NotifyWinEvent(286, this.control.handle, -4, this.eventChildID());
                    }
                }
                break;
            }
            case 268: {
                if (!(object instanceof Integer)) break;
                OS.NotifyWinEvent(268, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 32782: {
                OS.NotifyWinEvent(32782, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 32778: {
                OS.NotifyWinEvent(32778, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 32777: {
                OS.NotifyWinEvent(32777, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 32788: {
                OS.NotifyWinEvent(32788, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 32779: {
                OS.NotifyWinEvent(32779, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 32780: {
                OS.NotifyWinEvent(32780, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 32781: {
                OS.NotifyWinEvent(32781, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 261: {
                OS.NotifyWinEvent(261, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 262: {
                OS.NotifyWinEvent(262, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 263: {
                OS.NotifyWinEvent(263, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 273: {
                OS.NotifyWinEvent(273, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 274: {
                OS.NotifyWinEvent(274, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 256: {
                OS.NotifyWinEvent(257, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 269: {
                OS.NotifyWinEvent(269, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 264: {
                OS.NotifyWinEvent(264, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 265: {
                OS.NotifyWinEvent(265, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 266: {
                OS.NotifyWinEvent(266, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 267: {
                OS.NotifyWinEvent(267, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 271: {
                OS.NotifyWinEvent(271, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 512: {
                OS.NotifyWinEvent(272, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 515: {
                OS.NotifyWinEvent(275, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 516: {
                OS.NotifyWinEvent(276, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 517: {
                OS.NotifyWinEvent(277, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 519: {
                OS.NotifyWinEvent(279, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 520: {
                OS.NotifyWinEvent(280, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 521: {
                OS.NotifyWinEvent(281, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 522: {
                OS.NotifyWinEvent(282, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 283: {
                OS.NotifyWinEvent(283, this.control.handle, -4, this.eventChildID());
                break;
            }
            case 285: {
                OS.NotifyWinEvent(285, this.control.handle, -4, this.eventChildID());
            }
        }
    }

    public void sendEvent(int n, Object object, int n2) {
        this.checkWidget();
        if (!this.isATRunning()) {
            return;
        }
        if (!UseIA2) {
            return;
        }
        int n3 = n2 == -1 ? this.eventChildID() : this.childIDToOs(n2);
        switch (n) {
            case 32778: {
                OS.NotifyWinEvent(32778, this.control.handle, -4, n3);
                break;
            }
            case 32780: {
                OS.NotifyWinEvent(32780, this.control.handle, -4, n3);
                break;
            }
            case 32782: {
                OS.NotifyWinEvent(32782, this.control.handle, -4, n3);
                break;
            }
            case 32779: {
                OS.NotifyWinEvent(32779, this.control.handle, -4, n3);
                break;
            }
            case 32777: {
                OS.NotifyWinEvent(32777, this.control.handle, -4, n3);
                break;
            }
            case 32788: {
                OS.NotifyWinEvent(32788, this.control.handle, -4, n3);
                break;
            }
            case 32781: {
                OS.NotifyWinEvent(32781, this.control.handle, -4, n3);
            }
        }
    }

    public void selectionChanged() {
        this.checkWidget();
        if (!this.isATRunning()) {
            return;
        }
        OS.NotifyWinEvent(32777, this.control.handle, -4, this.eventChildID());
    }

    public void setFocus(int n) {
        this.checkWidget();
        if (!this.isATRunning()) {
            return;
        }
        int n2 = n == -1 ? this.eventChildID() : this.childIDToOs(n);
        OS.NotifyWinEvent(32773, this.control.handle, -4, n2);
    }

    public void textCaretMoved(int n) {
        this.checkWidget();
        if (this.timer == null) {
            this.timer = new lII(this);
        }
        this.control.getDisplay().timerExec(100, this.timer);
    }

    public void textChanged(int n, int n2, int n3) {
        this.checkWidget();
        if (!this.isATRunning()) {
            return;
        }
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        accessibleTextEvent.start = n2;
        accessibleTextEvent.end = n2 + n3;
        accessibleTextEvent.count = 0;
        accessibleTextEvent.type = 5;
        for (int i = 0; i < this.accessibleTextExtendedListenersSize(); ++i) {
            AccessibleTextExtendedListener accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(i);
            accessibleTextExtendedListener.getText(accessibleTextEvent);
        }
        if (accessibleTextEvent.result != null) {
            Object[] objectArray = new Object[]{n, n2, n2 + n3, accessibleTextEvent.result};
            this.sendEvent(524, objectArray);
            return;
        }
        OS.NotifyWinEvent(32782, this.control.handle, -4, this.eventChildID());
    }

    public void textSelectionChanged() {
        this.checkWidget();
        if (!this.isATRunning()) {
            return;
        }
        OS.NotifyWinEvent(32788, this.control.handle, -4, this.eventChildID());
    }

    int QueryInterface(long l2, long l3) {
        if (this.control != null && this.control.isDisposed()) {
            return -2147220995;
        }
        OS.MoveMemory(l3, new long[]{0L}, C.PTR_SIZEOF);
        GUID gUID = new GUID();
        COM.MoveMemory(gUID, l2, GUID.sizeof);
        if (COM.IsEqualGUID(gUID, COM.IIDIUnknown) || COM.IsEqualGUID(gUID, COM.IIDIDispatch) || COM.IsEqualGUID(gUID, COM.IIDIAccessible)) {
            if (this.objIAccessible == null) {
                this.createIAccessible();
            }
            OS.MoveMemory(l3, new long[]{this.objIAccessible.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIEnumVARIANT)) {
            if (this.objIEnumVARIANT == null) {
                this.createIEnumVARIANT();
            }
            OS.MoveMemory(l3, new long[]{this.objIEnumVARIANT.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            this.enumIndex = 0;
            return 0;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIServiceProvider)) {
            if (!UseIA2) {
                return -2147467262;
            }
            if (this.accessibleActionListenersSize() > 0 || this.accessibleAttributeListenersSize() > 0 || this.accessibleHyperlinkListenersSize() > 0 || this.accessibleTableListenersSize() > 0 || this.accessibleTableCellListenersSize() > 0 || this.accessibleTextExtendedListenersSize() > 0 || this.accessibleValueListenersSize() > 0 || this.accessibleControlListenersSize() > 0 || this.getRelationCount() > 0 || this.control instanceof Button && (this.control.getStyle() & 0x10) != 0 || this.control instanceof Composite) {
                if (this.objIServiceProvider == null) {
                    this.createIServiceProvider();
                }
                OS.MoveMemory(l3, new long[]{this.objIServiceProvider.getAddress()}, C.PTR_SIZEOF);
                this.AddRef();
                return 0;
            }
            return -2147467262;
        }
        int n = this.queryAccessible2Interfaces(gUID, l3);
        if (n != 1) {
            return n;
        }
        if (this.iaccessible != null) {
            long[] lArray = new long[]{0L};
            n = this.iaccessible.QueryInterface(gUID, lArray);
            OS.MoveMemory(l3, lArray, C.PTR_SIZEOF);
            return n;
        }
        return -2147467262;
    }

    int accessibleListenersSize() {
        return this.accessibleListeners == null ? 0 : this.accessibleListeners.size();
    }

    int accessibleControlListenersSize() {
        return this.accessibleControlListeners == null ? 0 : this.accessibleControlListeners.size();
    }

    int accessibleValueListenersSize() {
        return this.accessibleValueListeners == null ? 0 : this.accessibleValueListeners.size();
    }

    int accessibleTextExtendedListenersSize() {
        return this.accessibleTextExtendedListeners == null ? 0 : this.accessibleTextExtendedListeners.size();
    }

    int accessibleTextListenersSize() {
        return this.accessibleTextListeners == null ? 0 : this.accessibleTextListeners.size();
    }

    int accessibleTableCellListenersSize() {
        return this.accessibleTableCellListeners == null ? 0 : this.accessibleTableCellListeners.size();
    }

    int accessibleTableListenersSize() {
        return this.accessibleTableListeners == null ? 0 : this.accessibleTableListeners.size();
    }

    int accessibleHyperlinkListenersSize() {
        return this.accessibleHyperlinkListeners == null ? 0 : this.accessibleHyperlinkListeners.size();
    }

    int accessibleEditableTextListenersSize() {
        return this.accessibleEditableTextListeners == null ? 0 : this.accessibleEditableTextListeners.size();
    }

    int accessibleAttributeListenersSize() {
        return this.accessibleAttributeListeners == null ? 0 : this.accessibleAttributeListeners.size();
    }

    int accessibleActionListenersSize() {
        return this.accessibleActionListeners == null ? 0 : this.accessibleActionListeners.size();
    }

    int AddRef() {
        return ++this.refCount;
    }

    int Release() {
        --this.refCount;
        if (this.refCount == 0) {
            if (this.objIAccessible != null) {
                this.objIAccessible.dispose();
            }
            this.objIAccessible = null;
            if (this.objIEnumVARIANT != null) {
                this.objIEnumVARIANT.dispose();
            }
            this.objIEnumVARIANT = null;
            if (this.objIServiceProvider != null) {
                this.objIServiceProvider.dispose();
            }
            this.objIServiceProvider = null;
            if (this.objIAccessibleApplication != null) {
                this.objIAccessibleApplication.dispose();
            }
            this.objIAccessibleApplication = null;
            if (this.objIAccessibleEditableText != null) {
                this.objIAccessibleEditableText.dispose();
            }
            this.objIAccessibleEditableText = null;
            if (this.objIAccessibleHyperlink != null) {
                this.objIAccessibleHyperlink.dispose();
            }
            this.objIAccessibleHyperlink = null;
            if (this.objIAccessibleHypertext != null) {
                this.objIAccessibleHypertext.dispose();
            }
            this.objIAccessibleHypertext = null;
            if (this.objIAccessibleTable2 != null) {
                this.objIAccessibleTable2.dispose();
            }
            this.objIAccessibleTable2 = null;
            if (this.objIAccessibleTableCell != null) {
                this.objIAccessibleTableCell.dispose();
            }
            this.objIAccessibleTableCell = null;
            if (this.objIAccessibleValue != null) {
                this.objIAccessibleValue.dispose();
            }
            this.objIAccessibleValue = null;
            for (Relation relation : this.relations) {
                if (relation == null) continue;
                relation.Release();
            }
        }
        return this.refCount;
    }

    int QueryService(long l2, long l3, long l4) {
        long[] lArray;
        int n;
        int n2;
        OS.MoveMemory(l4, new long[]{0L}, C.PTR_SIZEOF);
        GUID gUID = new GUID();
        COM.MoveMemory(gUID, l2, GUID.sizeof);
        GUID gUID2 = new GUID();
        COM.MoveMemory(gUID2, l3, GUID.sizeof);
        if (COM.IsEqualGUID(gUID, COM.IIDIAccessible)) {
            if (COM.IsEqualGUID(gUID2, COM.IIDIUnknown) || COM.IsEqualGUID(gUID2, COM.IIDIDispatch) || COM.IsEqualGUID(gUID2, COM.IIDIAccessible)) {
                if (this.objIAccessible == null) {
                    this.createIAccessible();
                }
                OS.MoveMemory(l4, new long[]{this.objIAccessible.getAddress()}, C.PTR_SIZEOF);
                this.AddRef();
                return 0;
            }
            n2 = this.queryAccessible2Interfaces(gUID2, l4);
            if (n2 != 1) {
                return n2;
            }
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIAccessible2) && (n2 = this.queryAccessible2Interfaces(gUID2, l4)) != 1) {
            return n2;
        }
        if (this.iaccessible != null && (n = this.iaccessible.QueryInterface(COM.IIDIServiceProvider, lArray = new long[]{0L})) == 0) {
            IServiceProvider iServiceProvider = new IServiceProvider(lArray[0]);
            long[] lArray2 = new long[]{0L};
            n = iServiceProvider.QueryService(gUID, gUID2, lArray2);
            OS.MoveMemory(l4, lArray2, C.PTR_SIZEOF);
            return n;
        }
        return -2147467262;
    }

    int queryAccessible2Interfaces(GUID gUID, long l2) {
        if (this.control != null && this.control.isDisposed()) {
            return -2147220995;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIAccessible2)) {
            if (this.accessibleActionListenersSize() > 0 || this.accessibleAttributeListenersSize() > 0 || this.accessibleHyperlinkListenersSize() > 0 || this.accessibleTableListenersSize() > 0 || this.accessibleTableCellListenersSize() > 0 || this.accessibleTextExtendedListenersSize() > 0 || this.accessibleValueListenersSize() > 0 || this.accessibleControlListenersSize() > 0 || this.getRelationCount() > 0 || this.control instanceof Button && (this.control.getStyle() & 0x10) != 0 || this.control instanceof Composite) {
                if (this.objIAccessible == null) {
                    this.createIAccessible();
                }
                OS.MoveMemory(l2, new long[]{this.objIAccessible.getAddress()}, C.PTR_SIZEOF);
                this.AddRef();
                return 0;
            }
            return -2147467262;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIAccessibleAction)) {
            if (this.accessibleActionListenersSize() > 0) {
                if (this.objIAccessibleHyperlink == null) {
                    this.createIAccessibleHyperlink();
                }
                OS.MoveMemory(l2, new long[]{this.objIAccessibleHyperlink.getAddress()}, C.PTR_SIZEOF);
                this.AddRef();
                return 0;
            }
            return -2147467262;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIAccessibleApplication)) {
            if (this.objIAccessibleApplication == null) {
                this.createIAccessibleApplication();
            }
            OS.MoveMemory(l2, new long[]{this.objIAccessibleApplication.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIAccessibleComponent)) {
            return -2147467262;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIAccessibleEditableText)) {
            if (this.accessibleEditableTextListenersSize() > 0) {
                if (this.objIAccessibleEditableText == null) {
                    this.createIAccessibleEditableText();
                }
                OS.MoveMemory(l2, new long[]{this.objIAccessibleEditableText.getAddress()}, C.PTR_SIZEOF);
                this.AddRef();
                return 0;
            }
            return -2147467262;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIAccessibleHyperlink)) {
            if (this.accessibleHyperlinkListenersSize() > 0) {
                if (this.objIAccessibleHyperlink == null) {
                    this.createIAccessibleHyperlink();
                }
                OS.MoveMemory(l2, new long[]{this.objIAccessibleHyperlink.getAddress()}, C.PTR_SIZEOF);
                this.AddRef();
                return 0;
            }
            return -2147467262;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIAccessibleHypertext)) {
            if (this.accessibleTextExtendedListenersSize() > 0) {
                if (this.objIAccessibleHypertext == null) {
                    this.createIAccessibleHypertext();
                }
                OS.MoveMemory(l2, new long[]{this.objIAccessibleHypertext.getAddress()}, C.PTR_SIZEOF);
                this.AddRef();
                return 0;
            }
            return -2147467262;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIAccessibleImage)) {
            return -2147467262;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIAccessibleTable)) {
            return -2147467262;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIAccessibleTable2)) {
            if (this.accessibleTableListenersSize() > 0) {
                if (this.objIAccessibleTable2 == null) {
                    this.createIAccessibleTable2();
                }
                OS.MoveMemory(l2, new long[]{this.objIAccessibleTable2.getAddress()}, C.PTR_SIZEOF);
                this.AddRef();
                return 0;
            }
            return -2147467262;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIAccessibleTableCell)) {
            if (this.accessibleTableCellListenersSize() > 0) {
                if (this.objIAccessibleTableCell == null) {
                    this.createIAccessibleTableCell();
                }
                OS.MoveMemory(l2, new long[]{this.objIAccessibleTableCell.getAddress()}, C.PTR_SIZEOF);
                this.AddRef();
                return 0;
            }
            return -2147467262;
        }
        if (COM.IsEqualGUID(gUID, COM.IIDIAccessibleText)) {
            if (this.accessibleTextExtendedListenersSize() > 0 || this.accessibleAttributeListenersSize() > 0) {
                if (this.objIAccessibleHypertext == null) {
                    this.createIAccessibleHypertext();
                }
                OS.MoveMemory(l2, new long[]{this.objIAccessibleHypertext.getAddress()}, C.PTR_SIZEOF);
                this.AddRef();
                return 0;
            }
            return -2147467262;
        }
        if (!COM.IsEqualGUID(gUID, COM.IIDIAccessibleValue)) {
            return 1;
        }
        if (this.accessibleValueListenersSize() > 0) {
            if (this.objIAccessibleValue == null) {
                this.createIAccessibleValue();
            }
            OS.MoveMemory(l2, new long[]{this.objIAccessibleValue.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        return -2147467262;
    }

    int accDoDefaultAction(long l2) {
        if (this.accessibleActionListenersSize() > 0) {
            VARIANT vARIANT = this.getVARIANT(l2);
            if (vARIANT.vt != 3) {
                return -2147024809;
            }
            if (vARIANT.lVal == 0) {
                return this.doAction(0);
            }
        }
        int n = -2147352573;
        if (this.iaccessible != null && (n = this.iaccessible.accDoDefaultAction(l2)) == -2147024809) {
            n = -2147352573;
        }
        return n;
    }

    int accHitTest(int n, int n2, long l2) {
        int n3 = -2;
        long l3 = 0L;
        if (this.iaccessible != null) {
            int n4 = this.iaccessible.accHitTest(n, n2, l2);
            if (n4 == 0) {
                VARIANT vARIANT = this.getVARIANT(l2);
                if (vARIANT.vt == 3) {
                    n3 = vARIANT.lVal;
                } else if (vARIANT.vt == 9) {
                    l3 = vARIANT.lVal;
                }
            }
            if (this.accessibleControlListenersSize() == 0) {
                return n4;
            }
        }
        AccessibleControlEvent accessibleControlEvent = new AccessibleControlEvent(this);
        accessibleControlEvent.childID = n3 == -2 ? -2 : this.osToChildID(n3);
        accessibleControlEvent.x = n;
        accessibleControlEvent.y = n2;
        for (int i = 0; i < this.accessibleControlListenersSize(); ++i) {
            AccessibleControlListener accessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.get(i);
            accessibleControlListener.getChildAtPoint(accessibleControlEvent);
        }
        Accessible accessible = accessibleControlEvent.accessible;
        if (accessible != null) {
            accessible.AddRef();
            this.setPtrVARIANT(l2, (short)9, accessible.getAddress());
            return 0;
        }
        int n5 = accessibleControlEvent.childID;
        if (n5 != -2) {
            this.setIntVARIANT(l2, (short)3, this.childIDToOs(n5));
            return 0;
        }
        if (l3 != 0L) {
            return 0;
        }
        this.setIntVARIANT(l2, (short)0, 0);
        return 1;
    }

    int accLocation(long l2, long l3, long l4, long l5, long l6) {
        Object object;
        VARIANT vARIANT = this.getVARIANT(l6);
        if (vARIANT.vt != 3) {
            return -2147024809;
        }
        int n = 0;
        Object object2 = 0;
        int n2 = 0;
        int n3 = 0;
        if (this.iaccessible != null) {
            int n4 = this.iaccessible.accLocation(l2, l3, l4, l5, l6);
            if (n4 == -2147024809) {
                n4 = -2147352573;
            }
            if (this.accessibleControlListenersSize() == 0) {
                return n4;
            }
            if (n4 == 0) {
                int[] nArray = new int[]{0};
                object = new int[]{0};
                int[] nArray2 = new int[]{0};
                int[] nArray3 = new int[]{0};
                OS.MoveMemory(nArray, l2, 4);
                OS.MoveMemory((int[])object, l3, 4);
                OS.MoveMemory(nArray2, l4, 4);
                OS.MoveMemory(nArray3, l5, 4);
                n = nArray[0];
                object2 = object[0];
                n2 = nArray2[0];
                n3 = nArray3[0];
            }
        }
        AccessibleControlEvent accessibleControlEvent = new AccessibleControlEvent(this);
        accessibleControlEvent.childID = this.osToChildID(vARIANT.lVal);
        accessibleControlEvent.x = n;
        accessibleControlEvent.y = object2;
        accessibleControlEvent.width = n2;
        accessibleControlEvent.height = n3;
        for (int i = 0; i < this.accessibleControlListenersSize(); ++i) {
            object = (AccessibleControlListener)this.accessibleControlListeners.get(i);
            object.getLocation(accessibleControlEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleControlEvent.x}, 4);
        OS.MoveMemory(l3, new int[]{accessibleControlEvent.y}, 4);
        OS.MoveMemory(l4, new int[]{accessibleControlEvent.width}, 4);
        OS.MoveMemory(l5, new int[]{accessibleControlEvent.height}, 4);
        return 0;
    }

    int accNavigate(int n, long l2, long l3) {
        int n2 = -2147352573;
        if (this.iaccessible != null && (n2 = this.iaccessible.accNavigate(n, l2, l3)) == -2147024809) {
            n2 = -2147352573;
        }
        return n2;
    }

    int accSelect(int n, long l2) {
        int n2 = -2147352573;
        if (this.iaccessible != null && (n2 = this.iaccessible.accSelect(n, l2)) == -2147024809) {
            n2 = -2147352573;
        }
        return n2;
    }

    int get_accChild(long l2, long l3) {
        Object object;
        Object object2;
        VARIANT vARIANT = this.getVARIANT(l2);
        if (vARIANT.vt != 3) {
            return -2147024809;
        }
        if (vARIANT.lVal == 0) {
            this.AddRef();
            OS.MoveMemory(l3, new long[]{this.getAddress()}, C.PTR_SIZEOF);
            return 0;
        }
        int n = this.osToChildID(vARIANT.lVal);
        int n2 = 1;
        Accessible accessible = null;
        if (this.iaccessible != null) {
            ToolItem toolItem;
            n2 = this.iaccessible.get_accChild(l2, l3);
            if (n2 == -2147024809) {
                n2 = 1;
            }
            if (n2 == 0 && this.control instanceof ToolBar && (toolItem = ((ToolBar)(object2 = (ToolBar)this.control)).getItem(n)) != null && (toolItem.getStyle() & 4) != 0) {
                object = new long[]{0L};
                OS.MoveMemory((long[])object, l3, C.PTR_SIZEOF);
                boolean bl = false;
                for (Accessible accessible2 : this.children) {
                    if (accessible2.item != toolItem) continue;
                    accessible2.dispose();
                    accessible2.item = null;
                    bl = true;
                    break;
                }
                accessible = new Accessible(this, (long)object[0]);
                accessible.item = toolItem;
                if (!bl) {
                    toolItem.addListener(12, arg_0 -> this.lambda$get_accChild$0(toolItem, arg_0));
                }
                accessible.addAccessibleListener(new I(this, n));
            }
        }
        object2 = new AccessibleControlEvent(this);
        ((AccessibleControlEvent)object2).childID = n;
        for (int i = 0; i < this.accessibleControlListenersSize(); ++i) {
            object = (AccessibleControlListener)this.accessibleControlListeners.get(i);
            object.getChild((AccessibleControlEvent)object2);
        }
        Accessible accessible3 = ((AccessibleControlEvent)object2).accessible;
        if (accessible3 == null) {
            accessible3 = accessible;
        }
        if (accessible3 != null) {
            accessible3.AddRef();
            OS.MoveMemory(l3, new long[]{accessible3.getAddress()}, C.PTR_SIZEOF);
            return 0;
        }
        return n2;
    }

    int get_accChildCount(long l2) {
        int n = 0;
        if (this.iaccessible != null) {
            int n2 = this.iaccessible.get_accChildCount(l2);
            if (n2 == 0) {
                int[] nArray = new int[]{0};
                OS.MoveMemory(nArray, l2, 4);
                n = nArray[0];
            }
            if (this.accessibleControlListenersSize() == 0) {
                return n2;
            }
        }
        AccessibleControlEvent accessibleControlEvent = new AccessibleControlEvent(this);
        accessibleControlEvent.childID = -1;
        accessibleControlEvent.detail = n;
        for (int i = 0; i < this.accessibleControlListenersSize(); ++i) {
            AccessibleControlListener accessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.get(i);
            accessibleControlListener.getChildCount(accessibleControlEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleControlEvent.detail}, 4);
        return 0;
    }

    int get_accDefaultAction(long l2, long l3) {
        Object object;
        int n;
        Object object2;
        VARIANT vARIANT = this.getVARIANT(l2);
        if (vARIANT.vt != 3) {
            return -2147024809;
        }
        int n2 = -2147352573;
        String string = null;
        if (this.iaccessible != null) {
            n2 = this.iaccessible.get_accDefaultAction(l2, l3);
            if (n2 == -2147024809) {
                n2 = 1;
            }
            if (this.accessibleControlListenersSize() == 0) {
                return n2;
            }
            if (n2 == 0) {
                object2 = new long[]{0L};
                OS.MoveMemory((long[])object2, l3, C.PTR_SIZEOF);
                n = COM.SysStringByteLen((long)object2[0]);
                if (n > 0) {
                    object = new char[(n + 1) / 2];
                    OS.MoveMemory((char[])object, (long)object2[0], n);
                    string = new String((char[])object);
                }
            }
        }
        object2 = new AccessibleControlEvent(this);
        ((AccessibleControlEvent)object2).childID = this.osToChildID(vARIANT.lVal);
        ((AccessibleControlEvent)object2).result = string;
        for (n = 0; n < this.accessibleControlListenersSize(); ++n) {
            object = (AccessibleControlListener)this.accessibleControlListeners.get(n);
            object.getDefaultAction((AccessibleControlEvent)object2);
        }
        if ((((AccessibleControlEvent)object2).result == null || ((AccessibleControlEvent)object2).result.length() == 0) && vARIANT.lVal == 0) {
            n2 = this.get_name(0, l3);
        }
        if (((AccessibleControlEvent)object2).result == null) {
            return n2;
        }
        if (((AccessibleControlEvent)object2).result.length() == 0) {
            return 1;
        }
        this.setString(l3, ((AccessibleControlEvent)object2).result);
        return 0;
    }

    int get_accDescription(long l2, long l3) {
        Tree tree;
        int n;
        Object object;
        int n2;
        Object object2;
        VARIANT vARIANT = this.getVARIANT(l2);
        if (vARIANT.vt != 3) {
            return -2147024809;
        }
        int n3 = -2147352573;
        String string = null;
        if (this.iaccessible != null) {
            n3 = this.iaccessible.get_accDescription(l2, l3);
            if (n3 == -2147024809) {
                n3 = 1;
            }
            if (this.accessibleListenersSize() == 0 && !(this.control instanceof Tree)) {
                return n3;
            }
            if (n3 == 0) {
                object2 = new long[]{0L};
                OS.MoveMemory((long[])object2, l3, C.PTR_SIZEOF);
                n2 = COM.SysStringByteLen((long)object2[0]);
                if (n2 > 0) {
                    object = new char[(n2 + 1) / 2];
                    OS.MoveMemory((char[])object, (long)object2[0], n2);
                    string = new String((char[])object);
                }
            }
        }
        object2 = new AccessibleEvent(this);
        ((AccessibleEvent)object2).childID = this.osToChildID(vARIANT.lVal);
        ((AccessibleEvent)object2).result = string;
        if (vARIANT.lVal != 0 && this.control instanceof Tree && (n = (tree = (Tree)this.control).getColumnCount()) > 1) {
            long l4 = this.control.handle;
            long l5 = 0L;
            l5 = OS.SendMessage(l4, 4394, (long)vARIANT.lVal, 0L);
            Widget widget = tree.getDisplay().findWidget(l4, l5);
            ((AccessibleEvent)object2).result = "";
            if (widget instanceof TreeItem) {
                TreeItem treeItem = (TreeItem)widget;
                for (int i = 1; i < n; ++i) {
                    if (tree.isDisposed() || treeItem.isDisposed()) {
                        ((AccessibleEvent)object2).result = "";
                        return 0;
                    }
                    Object object3 = object2;
                    ((AccessibleEvent)object3).result = ((AccessibleEvent)object3).result + tree.getColumn(i).getText() + ": " + treeItem.getText(i);
                    if (i + 1 >= n) continue;
                    Object object4 = object2;
                    ((AccessibleEvent)object4).result = ((AccessibleEvent)object4).result + ", ";
                }
            }
        }
        for (n2 = 0; n2 < this.accessibleListenersSize(); ++n2) {
            object = (AccessibleListener)this.accessibleListeners.get(n2);
            object.getDescription((AccessibleEvent)object2);
        }
        if (((AccessibleEvent)object2).result == null) {
            return n3;
        }
        if (((AccessibleEvent)object2).result.length() == 0) {
            return 1;
        }
        this.setString(l3, ((AccessibleEvent)object2).result);
        return 0;
    }

    int get_accFocus(long l2) {
        int n = -2;
        if (this.iaccessible != null) {
            int n2 = this.iaccessible.get_accFocus(l2);
            if (n2 == 0) {
                VARIANT vARIANT = this.getVARIANT(l2);
                if (vARIANT.vt == 3) {
                    n = vARIANT.lVal;
                }
            }
            if (this.accessibleControlListenersSize() == 0) {
                return n2;
            }
        }
        AccessibleControlEvent accessibleControlEvent = new AccessibleControlEvent(this);
        accessibleControlEvent.childID = n == -2 ? -2 : this.osToChildID(n);
        for (int i = 0; i < this.accessibleControlListenersSize(); ++i) {
            AccessibleControlListener accessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.get(i);
            accessibleControlListener.getFocus(accessibleControlEvent);
        }
        Accessible accessible = accessibleControlEvent.accessible;
        if (accessible != null) {
            accessible.AddRef();
            this.setPtrVARIANT(l2, (short)9, accessible.getAddress());
            return 0;
        }
        int n3 = accessibleControlEvent.childID;
        if (n3 == -2) {
            this.setIntVARIANT(l2, (short)0, 0);
            return 1;
        }
        if (n3 == -1) {
            this.AddRef();
            this.setIntVARIANT(l2, (short)3, 0);
            return 0;
        }
        this.setIntVARIANT(l2, (short)3, this.childIDToOs(n3));
        return 0;
    }

    int get_accHelp(long l2, long l3) {
        Object object;
        int n;
        Object object2;
        VARIANT vARIANT = this.getVARIANT(l2);
        if (vARIANT.vt != 3) {
            return -2147024809;
        }
        int n2 = -2147352573;
        String string = null;
        if (this.iaccessible != null) {
            n2 = this.iaccessible.get_accHelp(l2, l3);
            if (n2 == -2147024809) {
                n2 = 1;
            }
            if (this.accessibleListenersSize() == 0) {
                return n2;
            }
            if (n2 == 0) {
                object2 = new long[]{0L};
                OS.MoveMemory((long[])object2, l3, C.PTR_SIZEOF);
                n = COM.SysStringByteLen((long)object2[0]);
                if (n > 0) {
                    object = new char[(n + 1) / 2];
                    OS.MoveMemory((char[])object, (long)object2[0], n);
                    string = new String((char[])object);
                }
            }
        }
        object2 = new AccessibleEvent(this);
        ((AccessibleEvent)object2).childID = this.osToChildID(vARIANT.lVal);
        ((AccessibleEvent)object2).result = string;
        for (n = 0; n < this.accessibleListenersSize(); ++n) {
            object = (AccessibleListener)this.accessibleListeners.get(n);
            object.getHelp((AccessibleEvent)object2);
        }
        if (((AccessibleEvent)object2).result == null) {
            return n2;
        }
        if (((AccessibleEvent)object2).result.length() == 0) {
            return 1;
        }
        this.setString(l3, ((AccessibleEvent)object2).result);
        return 0;
    }

    int get_accHelpTopic(long l2, long l3, long l4) {
        int n = -2147352573;
        if (this.iaccessible != null && (n = this.iaccessible.get_accHelpTopic(l2, l3, l4)) == -2147024809) {
            n = -2147352573;
        }
        return n;
    }

    int get_accKeyboardShortcut(long l2, long l3) {
        Object object;
        int n;
        Object object2;
        VARIANT vARIANT = this.getVARIANT(l2);
        if (vARIANT.vt != 3) {
            return -2147024809;
        }
        int n2 = -2147352573;
        String string = null;
        if (this.iaccessible != null) {
            n2 = this.iaccessible.get_accKeyboardShortcut(l2, l3);
            if (n2 == -2147024809) {
                n2 = 1;
            }
            if (this.accessibleListenersSize() == 0 && !(this.control instanceof TabFolder)) {
                return n2;
            }
            if (n2 == 0) {
                object2 = new long[]{0L};
                OS.MoveMemory((long[])object2, l3, C.PTR_SIZEOF);
                n = COM.SysStringByteLen((long)object2[0]);
                if (n > 0) {
                    object = new char[(n + 1) / 2];
                    OS.MoveMemory((char[])object, (long)object2[0], n);
                    string = new String((char[])object);
                }
            }
        }
        object2 = new AccessibleEvent(this);
        ((AccessibleEvent)object2).childID = this.osToChildID(vARIANT.lVal);
        ((AccessibleEvent)object2).result = string;
        if (vARIANT.lVal == 0 && this.control instanceof TabFolder) {
            ((AccessibleEvent)object2).result = SWT.getMessage("SWT_SwitchPage_Shortcut");
        }
        for (n = 0; n < this.accessibleListenersSize(); ++n) {
            object = (AccessibleListener)this.accessibleListeners.get(n);
            object.getKeyboardShortcut((AccessibleEvent)object2);
        }
        if (((AccessibleEvent)object2).result == null) {
            return n2;
        }
        if (((AccessibleEvent)object2).result.length() == 0) {
            return 1;
        }
        this.setString(l3, ((AccessibleEvent)object2).result);
        return 0;
    }

    int get_accName(long l2, long l3) {
        Object object;
        int n;
        Object object2;
        if (this.control != null && this.control.isDisposed()) {
            return -2147220995;
        }
        VARIANT vARIANT = this.getVARIANT(l2);
        if (vARIANT.vt != 3) {
            return -2147024809;
        }
        int n2 = 1;
        String string = null;
        if (this.iaccessible != null) {
            n2 = this.iaccessible.get_accName(l2, l3);
            if (n2 == 0) {
                object2 = new long[]{0L};
                OS.MoveMemory((long[])object2, l3, C.PTR_SIZEOF);
                n = COM.SysStringByteLen((long)object2[0]);
                if (n > 0) {
                    object = new char[(n + 1) / 2];
                    OS.MoveMemory((char[])object, (long)object2[0], n);
                    string = new String((char[])object);
                }
            }
            if (n2 == -2147024809) {
                n2 = 1;
            }
            if (this.accessibleListenersSize() == 0 && !(this.control instanceof Text)) {
                return n2;
            }
        }
        object2 = new AccessibleEvent(this);
        ((AccessibleEvent)object2).childID = this.osToChildID(vARIANT.lVal);
        ((AccessibleEvent)object2).result = string;
        if (this.control instanceof Text && (this.control.getStyle() & 0x80) != 0 && string == null) {
            ((AccessibleEvent)object2).result = ((Text)this.control).getMessage();
        }
        for (n = 0; n < this.accessibleListenersSize(); ++n) {
            object = (AccessibleListener)this.accessibleListeners.get(n);
            object.getName((AccessibleEvent)object2);
        }
        if (((AccessibleEvent)object2).result == null) {
            return n2;
        }
        if (((AccessibleEvent)object2).result.length() == 0) {
            return 1;
        }
        this.setString(l3, ((AccessibleEvent)object2).result);
        return 0;
    }

    int get_accParent(long l2) {
        int n = -2147352573;
        if (this.iaccessible != null) {
            n = this.iaccessible.get_accParent(l2);
        }
        if (this.parent != null) {
            this.parent.AddRef();
            OS.MoveMemory(l2, new long[]{this.parent.getAddress()}, C.PTR_SIZEOF);
            n = 0;
        }
        return n;
    }

    int get_accRole(long l2, long l3) {
        int n;
        if (this.control != null && this.control.isDisposed()) {
            return -2147220995;
        }
        VARIANT vARIANT = this.getVARIANT(l2);
        if (vARIANT.vt != 3) {
            return -2147024809;
        }
        int n2 = 10;
        if (this.iaccessible != null && (n = this.iaccessible.get_accRole(l2, l3)) == 0) {
            VARIANT vARIANT2 = this.getVARIANT(l3);
            if (vARIANT2.vt == 3) {
                n2 = vARIANT2.lVal;
            }
        }
        AccessibleControlEvent accessibleControlEvent = new AccessibleControlEvent(this);
        accessibleControlEvent.childID = this.osToChildID(vARIANT.lVal);
        accessibleControlEvent.detail = this.osToRole(n2);
        if ((this.control instanceof Tree || this.control instanceof Table) && vARIANT.lVal != 0 && (this.control.getStyle() & 0x20) != 0) {
            accessibleControlEvent.detail = 44;
        }
        for (int i = 0; i < this.accessibleControlListenersSize(); ++i) {
            AccessibleControlListener accessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.get(i);
            accessibleControlListener.getRole(accessibleControlEvent);
        }
        this.setIntVARIANT(l3, (short)3, this.roleToOs(accessibleControlEvent.detail));
        return 0;
    }

    int get_accSelection(long l2) {
        int n = -2;
        long l3 = 0L;
        if (this.iaccessible != null) {
            int n2 = this.iaccessible.get_accSelection(l2);
            if (this.accessibleControlListenersSize() == 0) {
                return n2;
            }
            if (n2 == 0) {
                VARIANT vARIANT = this.getVARIANT(l2);
                if (vARIANT.vt == 3) {
                    n = this.osToChildID(vARIANT.lVal);
                } else if (vARIANT.vt == 9) {
                    l3 = vARIANT.lVal;
                } else if (vARIANT.vt == 13) {
                    n = -3;
                }
            }
        }
        AccessibleControlEvent accessibleControlEvent = new AccessibleControlEvent(this);
        accessibleControlEvent.childID = n;
        for (int i = 0; i < this.accessibleControlListenersSize(); ++i) {
            AccessibleControlListener accessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.get(i);
            accessibleControlListener.getSelection(accessibleControlEvent);
        }
        Accessible accessible = accessibleControlEvent.accessible;
        if (accessible != null) {
            accessible.AddRef();
            this.setPtrVARIANT(l2, (short)9, accessible.getAddress());
            return 0;
        }
        int n3 = accessibleControlEvent.childID;
        if (n3 == -2) {
            if (l3 != 0L) {
                return 0;
            }
            this.setIntVARIANT(l2, (short)0, 0);
            return 1;
        }
        if (n3 == -3) {
            return 0;
        }
        if (n3 == -1) {
            this.AddRef();
            this.setPtrVARIANT(l2, (short)9, this.getAddress());
            return 0;
        }
        this.setIntVARIANT(l2, (short)3, this.childIDToOs(n3));
        return 0;
    }

    int get_accState(long l2, long l3) {
        int n;
        Object object;
        int n2;
        if (this.control != null && this.control.isDisposed()) {
            return -2147220995;
        }
        VARIANT vARIANT = this.getVARIANT(l2);
        if (vARIANT.vt != 3) {
            return -2147024809;
        }
        int n3 = 0;
        if (this.iaccessible != null && (n2 = this.iaccessible.get_accState(l2, l3)) == 0) {
            object = this.getVARIANT(l3);
            if (((VARIANT)object).vt == 3) {
                n3 = ((VARIANT)object).lVal;
            }
        }
        n2 = 0;
        object = new AccessibleControlEvent(this);
        ((AccessibleControlEvent)object).childID = this.osToChildID(vARIANT.lVal);
        ((AccessibleControlEvent)object).detail = this.osToState(n3);
        if (vARIANT.lVal != 0) {
            Object object2;
            if (this.control instanceof Tree && (this.control.getStyle() & 0x20) != 0) {
                boolean bl;
                long l4 = this.control.handle;
                object2 = new TVITEM();
                ((TVITEM)object2).mask = 24;
                ((TVITEM)object2).stateMask = 61440;
                ((TVITEM)object2).hItem = OS.SendMessage(l4, 4394, (long)vARIANT.lVal, 0L);
                long l5 = OS.SendMessage(l4, 4414, 0L, (TVITEM)object2);
                boolean bl2 = bl = l5 != 0L && (((TVITEM)object2).state >> 12 & 1) == 0;
                if (bl) {
                    Object object3 = object;
                    ((AccessibleControlEvent)object3).detail |= 0x10;
                }
                n2 = ((TVITEM)object2).state >> 12 > 2 ? 1 : 0;
            } else if (this.control instanceof Table && (this.control.getStyle() & 0x20) != 0) {
                Table table = (Table)this.control;
                int n4 = ((AccessibleControlEvent)object).childID;
                if (0 <= n4 && n4 < table.getItemCount()) {
                    object2 = table.getItem(n4);
                    if (((TableItem)object2).getChecked()) {
                        Object object4 = object;
                        ((AccessibleControlEvent)object4).detail |= 0x10;
                    }
                    if (((TableItem)object2).getGrayed()) {
                        n2 = 1;
                    }
                }
            }
        }
        for (n = 0; n < this.accessibleControlListenersSize(); ++n) {
            AccessibleControlListener accessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.get(n);
            accessibleControlListener.getState((AccessibleControlEvent)object);
        }
        n = this.stateToOs(((AccessibleControlEvent)object).detail);
        if ((n & 0x10) != 0 && n2 != 0) {
            n &= 0xFFFFFFEF;
            n |= 0x20;
        }
        this.setIntVARIANT(l3, (short)3, n);
        return 0;
    }

    int get_accValue(long l2, long l3) {
        Object object;
        int n;
        Object object2;
        if (this.control != null && this.control.isDisposed()) {
            return -2147220995;
        }
        VARIANT vARIANT = this.getVARIANT(l2);
        if (vARIANT.vt != 3) {
            return -2147024809;
        }
        int n2 = -2147352573;
        String string = null;
        if (this.iaccessible != null) {
            n2 = this.iaccessible.get_accValue(l2, l3);
            if (n2 == 0) {
                object2 = new long[]{0L};
                OS.MoveMemory((long[])object2, l3, C.PTR_SIZEOF);
                n = COM.SysStringByteLen((long)object2[0]);
                if (n > 0) {
                    object = new char[(n + 1) / 2];
                    OS.MoveMemory((char[])object, (long)object2[0], n);
                    string = new String((char[])object);
                }
            }
            if (n2 == -2147024809) {
                n2 = -2147352573;
            }
            if (this.accessibleControlListenersSize() == 0 && !(this.control instanceof Text)) {
                return n2;
            }
        }
        object2 = new AccessibleControlEvent(this);
        ((AccessibleControlEvent)object2).childID = this.osToChildID(vARIANT.lVal);
        ((AccessibleControlEvent)object2).result = string;
        if (this.control instanceof Text && (this.control.getStyle() & 0x80) != 0 && !this.control.isFocusControl()) {
            ((AccessibleControlEvent)object2).result = ((Text)this.control).getMessage();
        }
        for (n = 0; n < this.accessibleControlListenersSize(); ++n) {
            object = (AccessibleControlListener)this.accessibleControlListeners.get(n);
            object.getValue((AccessibleControlEvent)object2);
        }
        if (((AccessibleControlEvent)object2).result == null) {
            return n2;
        }
        this.setString(l3, ((AccessibleControlEvent)object2).result);
        return 0;
    }

    int put_accName(long l2, long l3) {
        return -2147467263;
    }

    int put_accValue(long l2, long l3) {
        VARIANT vARIANT = this.getVARIANT(l2);
        if (vARIANT.vt != 3) {
            return -2147024809;
        }
        int n = -2147352573;
        if (vARIANT.lVal == 0 && this.accessibleEditableTextListenersSize() > 0) {
            AccessibleEditableTextEvent accessibleEditableTextEvent = new AccessibleEditableTextEvent(this);
            accessibleEditableTextEvent.start = 0;
            accessibleEditableTextEvent.end = this.getCharacterCount();
            if (accessibleEditableTextEvent.end >= 0) {
                int n2 = COM.SysStringByteLen(l3);
                char[] cArray = new char[(n2 + 1) / 2];
                OS.MoveMemory(cArray, l3, n2);
                accessibleEditableTextEvent.string = new String(cArray);
                for (int i = 0; i < this.accessibleEditableTextListenersSize(); ++i) {
                    AccessibleEditableTextListener accessibleEditableTextListener = (AccessibleEditableTextListener)this.accessibleEditableTextListeners.get(i);
                    accessibleEditableTextListener.replaceText(accessibleEditableTextEvent);
                }
                if (accessibleEditableTextEvent.result != null && accessibleEditableTextEvent.result.equals("OK")) {
                    n = 0;
                }
            }
        }
        if (n != 0 && this.iaccessible != null && (n = this.iaccessible.put_accValue(l2, l3)) == -2147024809) {
            n = -2147352573;
        }
        return n;
    }

    int Next(int n, long l2, long l3) {
        int n2;
        Object[] objectArray;
        if (this.iaccessible != null && this.accessibleControlListenersSize() == 0) {
            long[] lArray = new long[]{0L};
            int n3 = this.iaccessible.QueryInterface(COM.IIDIEnumVARIANT, lArray);
            if (n3 != 0) {
                return n3;
            }
            IEnumVARIANT iEnumVARIANT = new IEnumVARIANT(lArray[0]);
            int[] nArray = new int[]{0};
            n3 = iEnumVARIANT.Next(n, l2, nArray);
            iEnumVARIANT.Release();
            OS.MoveMemory(l3, nArray, 4);
            return n3;
        }
        if (l2 == 0L) {
            return -2147024809;
        }
        if (l3 == 0L && n != 1) {
            return -2147024809;
        }
        if (this.enumIndex == 0) {
            objectArray = new AccessibleControlEvent(this);
            objectArray.childID = -1;
            for (n2 = 0; n2 < this.accessibleControlListenersSize(); ++n2) {
                AccessibleControlListener accessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.get(n2);
                accessibleControlListener.getChildren((AccessibleControlEvent)objectArray);
            }
            this.variants = objectArray.children;
        }
        objectArray = null;
        if (this.variants != null && n >= 1) {
            n2 = this.enumIndex + n - 1;
            if (n2 > this.variants.length - 1) {
                n2 = this.variants.length - 1;
            }
            if (this.enumIndex <= n2) {
                objectArray = new Object[n2 - this.enumIndex + 1];
                for (int i = 0; i < objectArray.length; ++i) {
                    Object object = this.variants[this.enumIndex];
                    objectArray[i] = object instanceof Integer ? Integer.valueOf(this.childIDToOs((Integer)object)) : object;
                    ++this.enumIndex;
                }
            }
        }
        if (objectArray != null) {
            for (n2 = 0; n2 < objectArray.length; ++n2) {
                AccessibleControlEvent accessibleControlEvent = objectArray[n2];
                if (accessibleControlEvent instanceof Integer) {
                    int n4 = (Integer)((Object)accessibleControlEvent);
                    this.setIntVARIANT(l2 + (long)(n2 * VARIANT.sizeof), (short)3, n4);
                    continue;
                }
                Accessible accessible = (Accessible)((Object)accessibleControlEvent);
                accessible.AddRef();
                this.setPtrVARIANT(l2 + (long)(n2 * VARIANT.sizeof), (short)9, accessible.getAddress());
            }
            if (l3 != 0L) {
                OS.MoveMemory(l3, new int[]{objectArray.length}, 4);
            }
            if (objectArray.length == n) {
                return 0;
            }
        } else if (l3 != 0L) {
            OS.MoveMemory(l3, new int[]{0}, 4);
        }
        return 1;
    }

    int Skip(int n) {
        if (this.iaccessible != null && this.accessibleControlListenersSize() == 0) {
            long[] lArray = new long[]{0L};
            int n2 = this.iaccessible.QueryInterface(COM.IIDIEnumVARIANT, lArray);
            if (n2 != 0) {
                return n2;
            }
            IEnumVARIANT iEnumVARIANT = new IEnumVARIANT(lArray[0]);
            n2 = iEnumVARIANT.Skip(n);
            iEnumVARIANT.Release();
            return n2;
        }
        if (n < 1) {
            return -2147024809;
        }
        this.enumIndex += n;
        if (this.enumIndex > this.variants.length - 1) {
            this.enumIndex = this.variants.length - 1;
            return 1;
        }
        return 0;
    }

    int Reset() {
        if (this.iaccessible == null || this.accessibleControlListenersSize() != 0) {
            this.enumIndex = 0;
            return 0;
        }
        long[] lArray = new long[]{0L};
        int n = this.iaccessible.QueryInterface(COM.IIDIEnumVARIANT, lArray);
        if (n != 0) {
            return n;
        }
        IEnumVARIANT iEnumVARIANT = new IEnumVARIANT(lArray[0]);
        n = iEnumVARIANT.Reset();
        iEnumVARIANT.Release();
        return n;
    }

    int Clone(long l2) {
        if (this.iaccessible != null && this.accessibleControlListenersSize() == 0) {
            long[] lArray = new long[]{0L};
            int n = this.iaccessible.QueryInterface(COM.IIDIEnumVARIANT, lArray);
            if (n != 0) {
                return n;
            }
            IEnumVARIANT iEnumVARIANT = new IEnumVARIANT(lArray[0]);
            long[] lArray2 = new long[]{0L};
            n = iEnumVARIANT.Clone(lArray2);
            iEnumVARIANT.Release();
            OS.MoveMemory(l2, lArray2, C.PTR_SIZEOF);
            return n;
        }
        if (l2 == 0L) {
            return -2147024809;
        }
        OS.MoveMemory(l2, new long[]{this.objIEnumVARIANT.getAddress()}, C.PTR_SIZEOF);
        this.AddRef();
        return 0;
    }

    int get_nRelations(long l2) {
        int n = this.getRelationCount();
        OS.MoveMemory(l2, new int[]{n}, 4);
        return 0;
    }

    int get_relation(int n, long l2) {
        int n2 = -1;
        for (int i = 0; i < 15; ++i) {
            Relation relation = this.relations[i];
            if (relation != null) {
                ++n2;
            }
            if (n2 != n) continue;
            relation.AddRef();
            OS.MoveMemory(l2, new long[]{relation.getAddress()}, C.PTR_SIZEOF);
            return 0;
        }
        return -2147024809;
    }

    int get_relations(int n, long l2, long l3) {
        int n2 = 0;
        for (int i = 0; i < 15 && n2 != n; ++i) {
            Relation relation = this.relations[i];
            if (relation == null) continue;
            relation.AddRef();
            OS.MoveMemory(l2 + (long)(n2 * C.PTR_SIZEOF), new long[]{relation.getAddress()}, C.PTR_SIZEOF);
            ++n2;
        }
        OS.MoveMemory(l3, new int[]{n2}, 4);
        return 0;
    }

    int get_role(long l2) {
        int n = this.getRole();
        if (n == 0) {
            n = this.getDefaultRole();
        }
        OS.MoveMemory(l2, new int[]{n}, 4);
        return 0;
    }

    int scrollTo(int n) {
        if (n < 4 || n > 6) {
            return -2147024809;
        }
        return -2147467263;
    }

    int scrollToPoint(int n, int n2, int n3) {
        if (n != 0) {
            return -2147024809;
        }
        return -2147467263;
    }

    int get_groupPosition(long l2, long l3, long l4) {
        int n;
        int n2;
        AccessibleAttributeEvent accessibleAttributeEvent;
        if (this.control != null && this.control.isDisposed()) {
            return -2147220995;
        }
        AccessibleAttributeEvent accessibleAttributeEvent2 = accessibleAttributeEvent = new AccessibleAttributeEvent(this);
        AccessibleAttributeEvent accessibleAttributeEvent3 = accessibleAttributeEvent;
        AccessibleAttributeEvent accessibleAttributeEvent4 = accessibleAttributeEvent;
        int n3 = -1;
        accessibleAttributeEvent3.groupIndex = -1;
        accessibleAttributeEvent2.groupCount = -1;
        accessibleAttributeEvent.groupLevel = -1;
        for (n2 = 0; n2 < this.accessibleAttributeListenersSize(); ++n2) {
            AccessibleAttributeListener accessibleAttributeListener = (AccessibleAttributeListener)this.accessibleAttributeListeners.get(n2);
            accessibleAttributeListener.getAttributes(accessibleAttributeEvent4);
        }
        n2 = accessibleAttributeEvent4.groupLevel != -1 ? accessibleAttributeEvent4.groupLevel : 0;
        int n4 = accessibleAttributeEvent4.groupCount != -1 ? accessibleAttributeEvent4.groupCount : 0;
        int n5 = n = accessibleAttributeEvent4.groupIndex != -1 ? accessibleAttributeEvent4.groupIndex : 0;
        if (n4 == 0 && n == 0 && this.control instanceof Button && (this.control.getStyle() & 0x10) != 0) {
            n = 1;
            n4 = 1;
            for (Control control : this.control.getParent().getChildren()) {
                if (!(control instanceof Button) || (control.getStyle() & 0x10) == 0) continue;
                if (control == this.control) {
                    n = n4;
                    continue;
                }
                ++n4;
            }
        }
        OS.MoveMemory(l2, new int[]{n2}, 4);
        OS.MoveMemory(l3, new int[]{n4}, 4);
        OS.MoveMemory(l4, new int[]{n}, 4);
        if (n2 == 0 && n4 == 0 && n == 0) {
            return 1;
        }
        return 0;
    }

    int get_states(long l2) {
        int n;
        AccessibleControlEvent accessibleControlEvent = new AccessibleControlEvent(this);
        accessibleControlEvent.childID = -1;
        for (n = 0; n < this.accessibleControlListenersSize(); ++n) {
            AccessibleControlListener accessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.get(n);
            accessibleControlListener.getState(accessibleControlEvent);
        }
        n = accessibleControlEvent.detail;
        int n2 = 0;
        if ((n & 0x4000000) != 0) {
            n2 |= 1;
        }
        if ((n & 0x8000000) != 0) {
            n2 |= 0x2000;
        }
        if ((n & 0x10000000) != 0) {
            n2 |= 0x200;
        }
        if ((n & 0x2000000) != 0) {
            n2 |= 0x800;
        }
        if ((n & 0x20000000) != 0) {
            n2 |= 0x40;
        }
        if ((n & 0x40000000) != 0) {
            n2 |= 0x8000;
        }
        if (this.getRole() == 42 && this.accessibleTextExtendedListenersSize() > 0) {
            n2 |= 8;
        }
        OS.MoveMemory(l2, new int[]{n2}, 4);
        return 0;
    }

    int get_extendedRole(long l2) {
        this.setString(l2, null);
        return 1;
    }

    int get_localizedExtendedRole(long l2) {
        this.setString(l2, null);
        return 1;
    }

    int get_nExtendedStates(long l2) {
        OS.MoveMemory(l2, new int[]{0}, 4);
        return 0;
    }

    int get_extendedStates(int n, long l2, long l3) {
        this.setString(l2, null);
        OS.MoveMemory(l3, new int[]{0}, 4);
        return 1;
    }

    int get_localizedExtendedStates(int n, long l2, long l3) {
        this.setString(l2, null);
        OS.MoveMemory(l3, new int[]{0}, 4);
        return 1;
    }

    int get_uniqueID(long l2) {
        if (this.uniqueID == -1) {
            this.uniqueID = UniqueID--;
        }
        OS.MoveMemory(l2, new long[]{this.uniqueID}, 4);
        return 0;
    }

    int get_windowHandle(long l2) {
        OS.MoveMemory(l2, new long[]{this.control.handle}, C.PTR_SIZEOF);
        return 0;
    }

    int get_indexInParent(long l2) {
        int n;
        AccessibleControlEvent accessibleControlEvent = new AccessibleControlEvent(this);
        accessibleControlEvent.childID = -5;
        accessibleControlEvent.detail = -1;
        for (n = 0; n < this.accessibleControlListenersSize(); ++n) {
            AccessibleControlListener accessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.get(n);
            accessibleControlListener.getChild(accessibleControlEvent);
        }
        n = accessibleControlEvent.detail;
        if (n == -1) {
            // empty if block
        }
        OS.MoveMemory(l2, new int[]{n}, 4);
        return n == -1 ? 1 : 0;
    }

    int get_locale(long l2) {
        Locale locale = Locale.getDefault();
        char[] cArray = locale.getLanguage().toCharArray();
        long l3 = COM.SysAllocString(cArray);
        OS.MoveMemory(l2, new long[]{l3}, C.PTR_SIZEOF);
        cArray = locale.getCountry().toCharArray();
        l3 = COM.SysAllocString(cArray);
        OS.MoveMemory(l2 + (long)C.PTR_SIZEOF, new long[]{l3}, C.PTR_SIZEOF);
        cArray = locale.getVariant().toCharArray();
        l3 = COM.SysAllocString(cArray);
        OS.MoveMemory(l2 + (long)(2 * C.PTR_SIZEOF), new long[]{l3}, C.PTR_SIZEOF);
        return 0;
    }

    int get_attributes(long l2) {
        AccessibleAttributeEvent accessibleAttributeEvent = new AccessibleAttributeEvent(this);
        for (int i = 0; i < this.accessibleAttributeListenersSize(); ++i) {
            AccessibleAttributeListener accessibleAttributeListener = (AccessibleAttributeListener)this.accessibleAttributeListeners.get(i);
            accessibleAttributeListener.getAttributes(accessibleAttributeEvent);
        }
        String string = "";
        string = string + "margin-left:" + accessibleAttributeEvent.leftMargin;
        string = string + "margin-top:" + accessibleAttributeEvent.topMargin;
        string = string + "margin-right:" + accessibleAttributeEvent.rightMargin;
        string = string + "margin-bottom:" + accessibleAttributeEvent.bottomMargin;
        if (accessibleAttributeEvent.tabStops != null) {
            for (Object object : (AccessibleAttributeListener)accessibleAttributeEvent.tabStops) {
                string = string + "tab-stop:position=" + (int)object;
            }
        }
        if (accessibleAttributeEvent.justify) {
            string = string + "text-align:justify;";
        }
        string = string + "text-align:" + (accessibleAttributeEvent.alignment == 16384 ? "left" : (accessibleAttributeEvent.alignment == 131072 ? "right" : "center"));
        string = string + "text-indent:" + accessibleAttributeEvent.indent;
        if (accessibleAttributeEvent.attributes != null) {
            int n = 0;
            while (n + 1 < accessibleAttributeEvent.attributes.length) {
                string = string + accessibleAttributeEvent.attributes[n] + ":" + accessibleAttributeEvent.attributes[n + 1];
                n += 2;
            }
        }
        if (this.getRole() == 42) {
            string = string + "text-model:a1;";
        }
        this.setString(l2, string);
        if (string.length() == 0) {
            return 1;
        }
        return 0;
    }

    int get_nActions(long l2) {
        AccessibleActionEvent accessibleActionEvent = new AccessibleActionEvent(this);
        for (int i = 0; i < this.accessibleActionListenersSize(); ++i) {
            AccessibleActionListener accessibleActionListener = (AccessibleActionListener)this.accessibleActionListeners.get(i);
            accessibleActionListener.getActionCount(accessibleActionEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleActionEvent.count}, 4);
        return 0;
    }

    int doAction(int n) {
        AccessibleActionEvent accessibleActionEvent = new AccessibleActionEvent(this);
        accessibleActionEvent.index = n;
        for (int i = 0; i < this.accessibleActionListenersSize(); ++i) {
            AccessibleActionListener accessibleActionListener = (AccessibleActionListener)this.accessibleActionListeners.get(i);
            accessibleActionListener.doAction(accessibleActionEvent);
        }
        if (accessibleActionEvent.result == null || !accessibleActionEvent.result.equals("OK")) {
            return -2147024809;
        }
        return 0;
    }

    int get_description(int n, long l2) {
        AccessibleActionEvent accessibleActionEvent = new AccessibleActionEvent(this);
        accessibleActionEvent.index = n;
        for (int i = 0; i < this.accessibleActionListenersSize(); ++i) {
            AccessibleActionListener accessibleActionListener = (AccessibleActionListener)this.accessibleActionListeners.get(i);
            accessibleActionListener.getDescription(accessibleActionEvent);
        }
        this.setString(l2, accessibleActionEvent.result);
        if (accessibleActionEvent.result == null || accessibleActionEvent.result.length() == 0) {
            return 1;
        }
        return 0;
    }

    int get_keyBinding(int n, int n2, long l2, long l3) {
        AccessibleActionEvent accessibleActionEvent = new AccessibleActionEvent(this);
        accessibleActionEvent.index = n;
        for (int i = 0; i < this.accessibleActionListenersSize(); ++i) {
            AccessibleActionListener accessibleActionListener = (AccessibleActionListener)this.accessibleActionListeners.get(i);
            accessibleActionListener.getKeyBinding(accessibleActionEvent);
        }
        String string = accessibleActionEvent.result;
        int n3 = 0;
        if (string != null) {
            n3 = string.length();
        }
        int n4 = 0;
        int n5 = 0;
        while (n4 < n3 && n5 != n2) {
            String string2;
            int n6 = string.indexOf(59, n4);
            if (n6 == -1) {
                n6 = n3;
            }
            if ((string2 = string.substring(n4, n6)).length() > 0) {
                this.setString(l2 + (long)(n5 * C.PTR_SIZEOF), string2);
                ++n5;
            }
            n4 = n6 + 1;
        }
        OS.MoveMemory(l3, new int[]{n5}, 4);
        if (n5 == 0) {
            this.setString(l2, null);
            return 1;
        }
        return 0;
    }

    int get_name(int n, long l2) {
        AccessibleActionEvent accessibleActionEvent = new AccessibleActionEvent(this);
        accessibleActionEvent.index = n;
        accessibleActionEvent.localized = false;
        for (int i = 0; i < this.accessibleActionListenersSize(); ++i) {
            AccessibleActionListener accessibleActionListener = (AccessibleActionListener)this.accessibleActionListeners.get(i);
            accessibleActionListener.getName(accessibleActionEvent);
        }
        if (accessibleActionEvent.result == null || accessibleActionEvent.result.length() == 0) {
            this.setString(l2, null);
            return 1;
        }
        this.setString(l2, accessibleActionEvent.result);
        return 0;
    }

    int get_localizedName(int n, long l2) {
        AccessibleActionEvent accessibleActionEvent = new AccessibleActionEvent(this);
        accessibleActionEvent.index = n;
        accessibleActionEvent.localized = true;
        for (int i = 0; i < this.accessibleActionListenersSize(); ++i) {
            AccessibleActionListener accessibleActionListener = (AccessibleActionListener)this.accessibleActionListeners.get(i);
            accessibleActionListener.getName(accessibleActionEvent);
        }
        if (accessibleActionEvent.result == null || accessibleActionEvent.result.length() == 0) {
            this.setString(l2, null);
            return 1;
        }
        this.setString(l2, accessibleActionEvent.result);
        return 0;
    }

    int get_appName(long l2) {
        String string = Display.getAppName();
        if (string == null || string.length() == 0) {
            this.setString(l2, null);
            return 1;
        }
        this.setString(l2, string);
        return 0;
    }

    int get_appVersion(long l2) {
        String string = Display.getAppVersion();
        if (string == null || string.length() == 0) {
            this.setString(l2, null);
            return 1;
        }
        this.setString(l2, string);
        return 0;
    }

    int get_toolkitName(long l2) {
        String string = "SWT";
        this.setString(l2, "SWT");
        return 0;
    }

    int get_toolkitVersion(long l2) {
        String string = "" + SWT.getVersion();
        this.setString(l2, string);
        return 0;
    }

    int copyText(int n, int n2) {
        AccessibleEditableTextEvent accessibleEditableTextEvent = new AccessibleEditableTextEvent(this);
        accessibleEditableTextEvent.start = n == -1 ? this.getCharacterCount() : n;
        accessibleEditableTextEvent.end = n2 == -1 ? this.getCharacterCount() : n2;
        for (int i = 0; i < this.accessibleEditableTextListenersSize(); ++i) {
            AccessibleEditableTextListener accessibleEditableTextListener = (AccessibleEditableTextListener)this.accessibleEditableTextListeners.get(i);
            accessibleEditableTextListener.copyText(accessibleEditableTextEvent);
        }
        if (accessibleEditableTextEvent.result == null || !accessibleEditableTextEvent.result.equals("OK")) {
            return -2147024809;
        }
        return 0;
    }

    int deleteText(int n, int n2) {
        AccessibleEditableTextEvent accessibleEditableTextEvent = new AccessibleEditableTextEvent(this);
        accessibleEditableTextEvent.start = n == -1 ? this.getCharacterCount() : n;
        accessibleEditableTextEvent.end = n2 == -1 ? this.getCharacterCount() : n2;
        accessibleEditableTextEvent.string = "";
        for (int i = 0; i < this.accessibleEditableTextListenersSize(); ++i) {
            AccessibleEditableTextListener accessibleEditableTextListener = (AccessibleEditableTextListener)this.accessibleEditableTextListeners.get(i);
            accessibleEditableTextListener.replaceText(accessibleEditableTextEvent);
        }
        if (accessibleEditableTextEvent.result == null || !accessibleEditableTextEvent.result.equals("OK")) {
            return -2147024809;
        }
        return 0;
    }

    int insertText(int n, long l2) {
        AccessibleEditableTextEvent accessibleEditableTextEvent = new AccessibleEditableTextEvent(this);
        accessibleEditableTextEvent.end = accessibleEditableTextEvent.start = n == -1 ? this.getCharacterCount() : n;
        accessibleEditableTextEvent.string = this.getString(l2);
        for (int i = 0; i < this.accessibleEditableTextListenersSize(); ++i) {
            AccessibleEditableTextListener accessibleEditableTextListener = (AccessibleEditableTextListener)this.accessibleEditableTextListeners.get(i);
            accessibleEditableTextListener.replaceText(accessibleEditableTextEvent);
        }
        if (accessibleEditableTextEvent.result == null || !accessibleEditableTextEvent.result.equals("OK")) {
            return -2147024809;
        }
        return 0;
    }

    int cutText(int n, int n2) {
        AccessibleEditableTextEvent accessibleEditableTextEvent = new AccessibleEditableTextEvent(this);
        accessibleEditableTextEvent.start = n == -1 ? this.getCharacterCount() : n;
        accessibleEditableTextEvent.end = n2 == -1 ? this.getCharacterCount() : n2;
        for (int i = 0; i < this.accessibleEditableTextListenersSize(); ++i) {
            AccessibleEditableTextListener accessibleEditableTextListener = (AccessibleEditableTextListener)this.accessibleEditableTextListeners.get(i);
            accessibleEditableTextListener.cutText(accessibleEditableTextEvent);
        }
        if (accessibleEditableTextEvent.result == null || !accessibleEditableTextEvent.result.equals("OK")) {
            return -2147024809;
        }
        return 0;
    }

    int pasteText(int n) {
        AccessibleEditableTextEvent accessibleEditableTextEvent = new AccessibleEditableTextEvent(this);
        accessibleEditableTextEvent.end = accessibleEditableTextEvent.start = n == -1 ? this.getCharacterCount() : n;
        for (int i = 0; i < this.accessibleEditableTextListenersSize(); ++i) {
            AccessibleEditableTextListener accessibleEditableTextListener = (AccessibleEditableTextListener)this.accessibleEditableTextListeners.get(i);
            accessibleEditableTextListener.pasteText(accessibleEditableTextEvent);
        }
        if (accessibleEditableTextEvent.result == null || !accessibleEditableTextEvent.result.equals("OK")) {
            return -2147024809;
        }
        return 0;
    }

    int replaceText(int n, int n2, long l2) {
        AccessibleEditableTextEvent accessibleEditableTextEvent = new AccessibleEditableTextEvent(this);
        accessibleEditableTextEvent.start = n == -1 ? this.getCharacterCount() : n;
        accessibleEditableTextEvent.end = n2 == -1 ? this.getCharacterCount() : n2;
        accessibleEditableTextEvent.string = this.getString(l2);
        for (int i = 0; i < this.accessibleEditableTextListenersSize(); ++i) {
            AccessibleEditableTextListener accessibleEditableTextListener = (AccessibleEditableTextListener)this.accessibleEditableTextListeners.get(i);
            accessibleEditableTextListener.replaceText(accessibleEditableTextEvent);
        }
        if (accessibleEditableTextEvent.result == null || !accessibleEditableTextEvent.result.equals("OK")) {
            return -2147024809;
        }
        return 0;
    }

    int setAttributes(int n, int n2, long l2) {
        AccessibleTextAttributeEvent accessibleTextAttributeEvent = new AccessibleTextAttributeEvent(this);
        String string = this.getString(l2);
        if (string != null && string.length() > 0) {
            String string2;
            accessibleTextAttributeEvent.start = n == -1 ? this.getCharacterCount() : n;
            accessibleTextAttributeEvent.end = n2 == -1 ? this.getCharacterCount() : n2;
            TextStyle textStyle = new TextStyle();
            FontData fontData = null;
            int n3 = 10;
            String[] stringArray = new String[]{};
            int n4 = 0;
            int n5 = string.indexOf(59);
            while (n5 != -1 && n5 < string.length()) {
                string2 = string.substring(n4, n5).trim();
                int n6 = string2.indexOf(58);
                if (n6 != -1 && n6 + 1 < string2.length()) {
                    String[] stringArray2 = new String[stringArray.length + 2];
                    System.arraycopy(stringArray, 0, stringArray2, 0, stringArray.length);
                    stringArray2[stringArray.length] = string2.substring(0, n6).trim();
                    stringArray2[stringArray.length + 1] = string2.substring(n6 + 1).trim();
                    stringArray = stringArray2;
                }
                n4 = n5 + 1;
                n5 = string.indexOf(59, n4);
            }
            n4 = 0;
            while (n4 + 1 < stringArray.length) {
                String string3 = stringArray[n4];
                string2 = stringArray[n4 + 1];
                if (string3.equals("text-position")) {
                    if (string2.equals("super")) {
                        textStyle.rise = n3 / 2;
                    } else if (string2.equals("sub")) {
                        textStyle.rise = -n3 / 2;
                    }
                } else if (string3.equals("text-underline-type")) {
                    textStyle.underline = true;
                    if (string2.equals("double")) {
                        textStyle.underlineStyle = 1;
                    } else if (string2.equals("single") && textStyle.underlineStyle != 3 && textStyle.underlineStyle != 2) {
                        textStyle.underlineStyle = 0;
                    }
                } else if (string3.equals("text-underline-style") && string2.equals("wave")) {
                    textStyle.underline = true;
                    textStyle.underlineStyle = 3;
                } else if (string3.equals("invalid") && string2.equals("true")) {
                    textStyle.underline = true;
                    textStyle.underlineStyle = 2;
                } else if (string3.equals("text-line-through-type")) {
                    if (string2.equals("single")) {
                        textStyle.strikeout = true;
                    }
                } else if (string3.equals("font-family")) {
                    if (fontData == null) {
                        fontData = new FontData();
                    }
                    fontData.setName(string2);
                } else if (string3.equals("font-size")) {
                    try {
                        String string4 = string2.endsWith("pt") ? string2.substring(0, string2.length() - 2) : string2;
                        n3 = Integer.parseInt(string4);
                        if (fontData == null) {
                            fontData = new FontData();
                        }
                        fontData.setHeight(n3);
                        if (textStyle.rise > 0) {
                            textStyle.rise = n3 / 2;
                        } else if (textStyle.rise < 0) {
                            textStyle.rise = -n3 / 2;
                        }
                    }
                    catch (NumberFormatException numberFormatException) {}
                } else if (string3.equals("font-style")) {
                    if (string2.equals("italic")) {
                        if (fontData == null) {
                            fontData = new FontData();
                        }
                        fontData.setStyle(fontData.getStyle() | 2);
                    }
                } else if (string3.equals("font-weight")) {
                    if (string2.equals("bold")) {
                        if (fontData == null) {
                            fontData = new FontData();
                        }
                        fontData.setStyle(fontData.getStyle() | 1);
                    } else {
                        try {
                            int n7 = Integer.parseInt(string2);
                            if (fontData == null) {
                                fontData = new FontData();
                            }
                            if (n7 > 400) {
                                fontData.setStyle(fontData.getStyle() | 1);
                            }
                        }
                        catch (NumberFormatException numberFormatException) {}
                    }
                } else if (string3.equals("color")) {
                    textStyle.foreground = this.colorFromString(string2);
                } else if (string3.equals("background-color")) {
                    textStyle.background = this.colorFromString(string2);
                }
                n4 += 2;
            }
            if (stringArray.length > 0) {
                accessibleTextAttributeEvent.attributes = stringArray;
                if (fontData != null) {
                    textStyle.font = new Font((Device)this.control.getDisplay(), fontData);
                }
                if (!textStyle.equals(new TextStyle())) {
                    accessibleTextAttributeEvent.textStyle = textStyle;
                }
            }
            for (n4 = 0; n4 < this.accessibleEditableTextListenersSize(); ++n4) {
                AccessibleEditableTextListener accessibleEditableTextListener = (AccessibleEditableTextListener)this.accessibleEditableTextListeners.get(n4);
                accessibleEditableTextListener.setTextAttributes(accessibleTextAttributeEvent);
            }
            if (textStyle.font != null) {
                textStyle.font.dispose();
            }
            if (textStyle.foreground != null) {
                textStyle.foreground.dispose();
            }
            if (textStyle.background != null) {
                textStyle.background.dispose();
            }
        }
        if (accessibleTextAttributeEvent.result == null || !accessibleTextAttributeEvent.result.equals("OK")) {
            return -2147024809;
        }
        return 0;
    }

    int get_anchor(int n, long l2) {
        AccessibleHyperlinkEvent accessibleHyperlinkEvent = new AccessibleHyperlinkEvent(this);
        accessibleHyperlinkEvent.index = n;
        for (int i = 0; i < this.accessibleHyperlinkListenersSize(); ++i) {
            AccessibleHyperlinkListener accessibleHyperlinkListener = (AccessibleHyperlinkListener)this.accessibleHyperlinkListeners.get(i);
            accessibleHyperlinkListener.getAnchor(accessibleHyperlinkEvent);
        }
        Accessible accessible = accessibleHyperlinkEvent.accessible;
        if (accessible != null) {
            accessible.AddRef();
            this.setPtrVARIANT(l2, (short)9, accessible.getAddress());
            return 0;
        }
        this.setStringVARIANT(l2, accessibleHyperlinkEvent.result);
        if (accessibleHyperlinkEvent.result == null) {
            return 1;
        }
        return 0;
    }

    int get_anchorTarget(int n, long l2) {
        AccessibleHyperlinkEvent accessibleHyperlinkEvent = new AccessibleHyperlinkEvent(this);
        accessibleHyperlinkEvent.index = n;
        for (int i = 0; i < this.accessibleHyperlinkListenersSize(); ++i) {
            AccessibleHyperlinkListener accessibleHyperlinkListener = (AccessibleHyperlinkListener)this.accessibleHyperlinkListeners.get(i);
            accessibleHyperlinkListener.getAnchorTarget(accessibleHyperlinkEvent);
        }
        Accessible accessible = accessibleHyperlinkEvent.accessible;
        if (accessible != null) {
            accessible.AddRef();
            this.setPtrVARIANT(l2, (short)9, accessible.getAddress());
            return 0;
        }
        this.setStringVARIANT(l2, accessibleHyperlinkEvent.result);
        if (accessibleHyperlinkEvent.result == null) {
            return 1;
        }
        return 0;
    }

    int get_startIndex(long l2) {
        AccessibleHyperlinkEvent accessibleHyperlinkEvent = new AccessibleHyperlinkEvent(this);
        for (int i = 0; i < this.accessibleHyperlinkListenersSize(); ++i) {
            AccessibleHyperlinkListener accessibleHyperlinkListener = (AccessibleHyperlinkListener)this.accessibleHyperlinkListeners.get(i);
            accessibleHyperlinkListener.getStartIndex(accessibleHyperlinkEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleHyperlinkEvent.index}, 4);
        return 0;
    }

    int get_endIndex(long l2) {
        AccessibleHyperlinkEvent accessibleHyperlinkEvent = new AccessibleHyperlinkEvent(this);
        for (int i = 0; i < this.accessibleHyperlinkListenersSize(); ++i) {
            AccessibleHyperlinkListener accessibleHyperlinkListener = (AccessibleHyperlinkListener)this.accessibleHyperlinkListeners.get(i);
            accessibleHyperlinkListener.getEndIndex(accessibleHyperlinkEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleHyperlinkEvent.index}, 4);
        return 0;
    }

    int get_valid(long l2) {
        return -2147467263;
    }

    int get_nHyperlinks(long l2) {
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        for (int i = 0; i < this.accessibleTextExtendedListenersSize(); ++i) {
            AccessibleTextExtendedListener accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(i);
            accessibleTextExtendedListener.getHyperlinkCount(accessibleTextEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleTextEvent.count}, 4);
        return 0;
    }

    int get_hyperlink(int n, long l2) {
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        accessibleTextEvent.index = n;
        for (int i = 0; i < this.accessibleTextExtendedListenersSize(); ++i) {
            AccessibleTextExtendedListener accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(i);
            accessibleTextExtendedListener.getHyperlink(accessibleTextEvent);
        }
        Accessible accessible = accessibleTextEvent.accessible;
        if (accessible == null) {
            this.setIntVARIANT(l2, (short)0, 0);
            return -2147024809;
        }
        accessible.AddRef();
        OS.MoveMemory(l2, new long[]{accessible.getAddress()}, C.PTR_SIZEOF);
        return 0;
    }

    int get_hyperlinkIndex(int n, long l2) {
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        accessibleTextEvent.offset = n;
        accessibleTextEvent.index = -1;
        for (int i = 0; i < this.accessibleTextExtendedListenersSize(); ++i) {
            AccessibleTextExtendedListener accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(i);
            accessibleTextExtendedListener.getHyperlinkIndex(accessibleTextEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleTextEvent.index}, 4);
        if (accessibleTextEvent.index == -1) {
            return 1;
        }
        return 0;
    }

    int get_cellAt(int n, int n2, long l2) {
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        accessibleTableEvent.row = n;
        accessibleTableEvent.column = n2;
        for (int i = 0; i < this.accessibleTableListenersSize(); ++i) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(i);
            accessibleTableListener.getCell(accessibleTableEvent);
        }
        Accessible accessible = accessibleTableEvent.accessible;
        if (accessible == null) {
            return -2147024809;
        }
        accessible.AddRef();
        OS.MoveMemory(l2, new long[]{accessible.getAddress()}, C.PTR_SIZEOF);
        return 0;
    }

    int get_caption(long l2) {
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        for (int i = 0; i < this.accessibleTableListenersSize(); ++i) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(i);
            accessibleTableListener.getCaption(accessibleTableEvent);
        }
        Accessible accessible = accessibleTableEvent.accessible;
        if (accessible == null) {
            OS.MoveMemory(l2, new long[]{0L}, C.PTR_SIZEOF);
            return 1;
        }
        accessible.AddRef();
        OS.MoveMemory(l2, new long[]{accessible.getAddress()}, C.PTR_SIZEOF);
        return 0;
    }

    int get_columnDescription(int n, long l2) {
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        accessibleTableEvent.column = n;
        for (int i = 0; i < this.accessibleTableListenersSize(); ++i) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(i);
            accessibleTableListener.getColumnDescription(accessibleTableEvent);
        }
        this.setString(l2, accessibleTableEvent.result);
        if (accessibleTableEvent.result == null) {
            return 1;
        }
        return 0;
    }

    int get_nColumns(long l2) {
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        for (int i = 0; i < this.accessibleTableListenersSize(); ++i) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(i);
            accessibleTableListener.getColumnCount(accessibleTableEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleTableEvent.count}, 4);
        return 0;
    }

    int get_nRows(long l2) {
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        for (int i = 0; i < this.accessibleTableListenersSize(); ++i) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(i);
            accessibleTableListener.getRowCount(accessibleTableEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleTableEvent.count}, 4);
        return 0;
    }

    int get_nSelectedCells(long l2) {
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        for (int i = 0; i < this.accessibleTableListenersSize(); ++i) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(i);
            accessibleTableListener.getSelectedCellCount(accessibleTableEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleTableEvent.count}, 4);
        return 0;
    }

    int get_nSelectedColumns(long l2) {
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        for (int i = 0; i < this.accessibleTableListenersSize(); ++i) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(i);
            accessibleTableListener.getSelectedColumnCount(accessibleTableEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleTableEvent.count}, 4);
        return 0;
    }

    int get_nSelectedRows(long l2) {
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        for (int i = 0; i < this.accessibleTableListenersSize(); ++i) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(i);
            accessibleTableListener.getSelectedRowCount(accessibleTableEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleTableEvent.count}, 4);
        return 0;
    }

    int get_rowDescription(int n, long l2) {
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        accessibleTableEvent.row = n;
        for (int i = 0; i < this.accessibleTableListenersSize(); ++i) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(i);
            accessibleTableListener.getRowDescription(accessibleTableEvent);
        }
        this.setString(l2, accessibleTableEvent.result);
        if (accessibleTableEvent.result == null) {
            return 1;
        }
        return 0;
    }

    int get_selectedCells(long l2, long l3) {
        int n;
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        for (n = 0; n < this.accessibleTableListenersSize(); ++n) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(n);
            accessibleTableListener.getSelectedCells(accessibleTableEvent);
        }
        if (accessibleTableEvent.accessibles == null || accessibleTableEvent.accessibles.length == 0) {
            OS.MoveMemory(l2, new long[]{0L}, C.PTR_SIZEOF);
            OS.MoveMemory(l3, new int[]{0}, 4);
            return 1;
        }
        n = accessibleTableEvent.accessibles.length;
        long l4 = OS.CoTaskMemAlloc(n * C.PTR_SIZEOF);
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            Accessible accessible = accessibleTableEvent.accessibles[i];
            if (accessible == null) continue;
            accessible.AddRef();
            OS.MoveMemory(l4 + (long)(i * C.PTR_SIZEOF), new long[]{accessible.getAddress()}, C.PTR_SIZEOF);
            ++n2;
        }
        OS.MoveMemory(l2, new long[]{l4}, C.PTR_SIZEOF);
        OS.MoveMemory(l3, new int[]{n2}, 4);
        return 0;
    }

    int get_selectedColumns(long l2, long l3) {
        int n;
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        for (n = 0; n < this.accessibleTableListenersSize(); ++n) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(n);
            accessibleTableListener.getSelectedColumns(accessibleTableEvent);
        }
        int n2 = n = accessibleTableEvent.selected == null ? 0 : accessibleTableEvent.selected.length;
        if (n == 0) {
            OS.MoveMemory(l2, new long[]{0L}, C.PTR_SIZEOF);
            OS.MoveMemory(l3, new int[]{0}, 4);
            return 1;
        }
        long l4 = OS.CoTaskMemAlloc(n * 4);
        OS.MoveMemory(l4, accessibleTableEvent.selected, n * 4);
        OS.MoveMemory(l2, new long[]{l4}, C.PTR_SIZEOF);
        OS.MoveMemory(l3, new int[]{n}, 4);
        return 0;
    }

    int get_selectedRows(long l2, long l3) {
        int n;
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        for (n = 0; n < this.accessibleTableListenersSize(); ++n) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(n);
            accessibleTableListener.getSelectedRows(accessibleTableEvent);
        }
        int n2 = n = accessibleTableEvent.selected == null ? 0 : accessibleTableEvent.selected.length;
        if (n == 0) {
            OS.MoveMemory(l2, new long[]{0L}, C.PTR_SIZEOF);
            OS.MoveMemory(l3, new int[]{0}, 4);
            return 1;
        }
        long l4 = OS.CoTaskMemAlloc(n * 4);
        OS.MoveMemory(l4, accessibleTableEvent.selected, n * 4);
        OS.MoveMemory(l2, new long[]{l4}, C.PTR_SIZEOF);
        OS.MoveMemory(l3, new int[]{n}, 4);
        return 0;
    }

    int get_summary(long l2) {
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        for (int i = 0; i < this.accessibleTableListenersSize(); ++i) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(i);
            accessibleTableListener.getSummary(accessibleTableEvent);
        }
        Accessible accessible = accessibleTableEvent.accessible;
        if (accessible == null) {
            OS.MoveMemory(l2, new long[]{0L}, C.PTR_SIZEOF);
            return 1;
        }
        accessible.AddRef();
        OS.MoveMemory(l2, new long[]{accessible.getAddress()}, C.PTR_SIZEOF);
        return 0;
    }

    int get_isColumnSelected(int n, long l2) {
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        accessibleTableEvent.column = n;
        for (int i = 0; i < this.accessibleTableListenersSize(); ++i) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(i);
            accessibleTableListener.isColumnSelected(accessibleTableEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleTableEvent.isSelected ? 1 : 0}, 4);
        return 0;
    }

    int get_isRowSelected(int n, long l2) {
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        accessibleTableEvent.row = n;
        for (int i = 0; i < this.accessibleTableListenersSize(); ++i) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(i);
            accessibleTableListener.isRowSelected(accessibleTableEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleTableEvent.isSelected ? 1 : 0}, 4);
        return 0;
    }

    int selectRow(int n) {
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        accessibleTableEvent.row = n;
        for (int i = 0; i < this.accessibleTableListenersSize(); ++i) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(i);
            accessibleTableListener.setSelectedRow(accessibleTableEvent);
        }
        if (accessibleTableEvent.result == null || !accessibleTableEvent.result.equals("OK")) {
            return -2147024809;
        }
        return 0;
    }

    int selectColumn(int n) {
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        accessibleTableEvent.column = n;
        for (int i = 0; i < this.accessibleTableListenersSize(); ++i) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(i);
            accessibleTableListener.setSelectedColumn(accessibleTableEvent);
        }
        if (accessibleTableEvent.result == null || !accessibleTableEvent.result.equals("OK")) {
            return -2147024809;
        }
        return 0;
    }

    int unselectRow(int n) {
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        accessibleTableEvent.row = n;
        for (int i = 0; i < this.accessibleTableListenersSize(); ++i) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(i);
            accessibleTableListener.deselectRow(accessibleTableEvent);
        }
        if (accessibleTableEvent.result == null || !accessibleTableEvent.result.equals("OK")) {
            return -2147024809;
        }
        return 0;
    }

    int unselectColumn(int n) {
        AccessibleTableEvent accessibleTableEvent = new AccessibleTableEvent(this);
        accessibleTableEvent.column = n;
        for (int i = 0; i < this.accessibleTableListenersSize(); ++i) {
            AccessibleTableListener accessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.get(i);
            accessibleTableListener.deselectColumn(accessibleTableEvent);
        }
        if (accessibleTableEvent.result == null || !accessibleTableEvent.result.equals("OK")) {
            return -2147024809;
        }
        return 0;
    }

    int get_modelChange(long l2) {
        if (this.tableChange == null) {
            OS.MoveMemory(l2, new long[]{0L}, C.PTR_SIZEOF);
            return 1;
        }
        OS.MoveMemory(l2, this.tableChange, this.tableChange.length * 4);
        return 0;
    }

    int get_columnExtent(long l2) {
        AccessibleTableCellEvent accessibleTableCellEvent = new AccessibleTableCellEvent(this);
        for (int i = 0; i < this.accessibleTableCellListenersSize(); ++i) {
            AccessibleTableCellListener accessibleTableCellListener = (AccessibleTableCellListener)this.accessibleTableCellListeners.get(i);
            accessibleTableCellListener.getColumnSpan(accessibleTableCellEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleTableCellEvent.count}, 4);
        return 0;
    }

    int get_columnHeaderCells(long l2, long l3) {
        int n;
        AccessibleTableCellEvent accessibleTableCellEvent = new AccessibleTableCellEvent(this);
        for (n = 0; n < this.accessibleTableCellListenersSize(); ++n) {
            AccessibleTableCellListener accessibleTableCellListener = (AccessibleTableCellListener)this.accessibleTableCellListeners.get(n);
            accessibleTableCellListener.getColumnHeaders(accessibleTableCellEvent);
        }
        if (accessibleTableCellEvent.accessibles == null || accessibleTableCellEvent.accessibles.length == 0) {
            OS.MoveMemory(l2, new long[]{0L}, C.PTR_SIZEOF);
            OS.MoveMemory(l3, new int[]{0}, 4);
            return 1;
        }
        n = accessibleTableCellEvent.accessibles.length;
        long l4 = OS.CoTaskMemAlloc(n * C.PTR_SIZEOF);
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            Accessible accessible = accessibleTableCellEvent.accessibles[i];
            if (accessible == null) continue;
            accessible.AddRef();
            OS.MoveMemory(l4 + (long)(i * C.PTR_SIZEOF), new long[]{accessible.getAddress()}, C.PTR_SIZEOF);
            ++n2;
        }
        OS.MoveMemory(l2, new long[]{l4}, C.PTR_SIZEOF);
        OS.MoveMemory(l3, new int[]{n2}, 4);
        return 0;
    }

    int get_columnIndex(long l2) {
        AccessibleTableCellEvent accessibleTableCellEvent = new AccessibleTableCellEvent(this);
        for (int i = 0; i < this.accessibleTableCellListenersSize(); ++i) {
            AccessibleTableCellListener accessibleTableCellListener = (AccessibleTableCellListener)this.accessibleTableCellListeners.get(i);
            accessibleTableCellListener.getColumnIndex(accessibleTableCellEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleTableCellEvent.index}, 4);
        return 0;
    }

    int get_rowExtent(long l2) {
        AccessibleTableCellEvent accessibleTableCellEvent = new AccessibleTableCellEvent(this);
        for (int i = 0; i < this.accessibleTableCellListenersSize(); ++i) {
            AccessibleTableCellListener accessibleTableCellListener = (AccessibleTableCellListener)this.accessibleTableCellListeners.get(i);
            accessibleTableCellListener.getRowSpan(accessibleTableCellEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleTableCellEvent.count}, 4);
        return 0;
    }

    int get_rowHeaderCells(long l2, long l3) {
        int n;
        AccessibleTableCellEvent accessibleTableCellEvent = new AccessibleTableCellEvent(this);
        for (n = 0; n < this.accessibleTableCellListenersSize(); ++n) {
            AccessibleTableCellListener accessibleTableCellListener = (AccessibleTableCellListener)this.accessibleTableCellListeners.get(n);
            accessibleTableCellListener.getRowHeaders(accessibleTableCellEvent);
        }
        if (accessibleTableCellEvent.accessibles == null || accessibleTableCellEvent.accessibles.length == 0) {
            OS.MoveMemory(l2, new long[]{0L}, C.PTR_SIZEOF);
            OS.MoveMemory(l3, new int[]{0}, 4);
            return 1;
        }
        n = accessibleTableCellEvent.accessibles.length;
        long l4 = OS.CoTaskMemAlloc(n * C.PTR_SIZEOF);
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            Accessible accessible = accessibleTableCellEvent.accessibles[i];
            if (accessible == null) continue;
            accessible.AddRef();
            OS.MoveMemory(l4 + (long)(i * C.PTR_SIZEOF), new long[]{accessible.getAddress()}, C.PTR_SIZEOF);
            ++n2;
        }
        OS.MoveMemory(l2, new long[]{l4}, C.PTR_SIZEOF);
        OS.MoveMemory(l3, new int[]{n2}, 4);
        return 0;
    }

    int get_rowIndex(long l2) {
        AccessibleTableCellEvent accessibleTableCellEvent = new AccessibleTableCellEvent(this);
        for (int i = 0; i < this.accessibleTableCellListenersSize(); ++i) {
            AccessibleTableCellListener accessibleTableCellListener = (AccessibleTableCellListener)this.accessibleTableCellListeners.get(i);
            accessibleTableCellListener.getRowIndex(accessibleTableCellEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleTableCellEvent.index}, 4);
        return 0;
    }

    int get_isSelected(long l2) {
        AccessibleTableCellEvent accessibleTableCellEvent = new AccessibleTableCellEvent(this);
        for (int i = 0; i < this.accessibleTableCellListenersSize(); ++i) {
            AccessibleTableCellListener accessibleTableCellListener = (AccessibleTableCellListener)this.accessibleTableCellListeners.get(i);
            accessibleTableCellListener.isSelected(accessibleTableCellEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleTableCellEvent.isSelected ? 1 : 0}, 4);
        return 0;
    }

    int get_rowColumnExtents(long l2, long l3, long l4, long l5, long l6) {
        return -2147352573;
    }

    int get_table(long l2) {
        AccessibleTableCellEvent accessibleTableCellEvent = new AccessibleTableCellEvent(this);
        for (int i = 0; i < this.accessibleTableCellListenersSize(); ++i) {
            AccessibleTableCellListener accessibleTableCellListener = (AccessibleTableCellListener)this.accessibleTableCellListeners.get(i);
            accessibleTableCellListener.getTable(accessibleTableCellEvent);
        }
        Accessible accessible = accessibleTableCellEvent.accessible;
        if (accessible == null) {
            OS.MoveMemory(l2, new long[]{0L}, C.PTR_SIZEOF);
            return 1;
        }
        accessible.AddRef();
        OS.MoveMemory(l2, new long[]{accessible.getAddress()}, C.PTR_SIZEOF);
        return 0;
    }

    int addSelection(int n, int n2) {
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        accessibleTextEvent.start = n == -1 ? this.getCharacterCount() : n;
        accessibleTextEvent.end = n2 == -1 ? this.getCharacterCount() : n2;
        for (int i = 0; i < this.accessibleTextExtendedListenersSize(); ++i) {
            AccessibleTextExtendedListener accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(i);
            accessibleTextExtendedListener.addSelection(accessibleTextEvent);
        }
        if (accessibleTextEvent.result == null || !accessibleTextEvent.result.equals("OK")) {
            return -2147024809;
        }
        return 0;
    }

    int get_attributes(int n, long l2, long l3, long l4) {
        Object object;
        AccessibleTextAttributeEvent accessibleTextAttributeEvent = new AccessibleTextAttributeEvent(this);
        accessibleTextAttributeEvent.offset = n == -1 ? this.getCharacterCount() : n;
        for (int i = 0; i < this.accessibleAttributeListenersSize(); ++i) {
            object = (AccessibleAttributeListener)this.accessibleAttributeListeners.get(i);
            object.getTextAttributes(accessibleTextAttributeEvent);
        }
        String string = "";
        object = accessibleTextAttributeEvent.textStyle;
        if (object != null) {
            Object object2;
            Font font;
            if (((TextStyle)object).rise != 0) {
                string = string + "text-position:";
                string = ((TextStyle)object).rise > 0 ? string + "super" : string + "sub";
            }
            if (((TextStyle)object).underline) {
                string = string + "text-underline-type:";
                switch (((TextStyle)object).underlineStyle) {
                    case 0: {
                        string = string + "single;";
                        break;
                    }
                    case 1: {
                        string = string + "double;";
                        break;
                    }
                    case 3: {
                        string = string + "single;text-underline-style:wave;";
                        break;
                    }
                    case 2: {
                        string = string + "single;text-underline-style:wave;invalid:true;";
                        break;
                    }
                    default: {
                        string = string + "none;";
                    }
                }
            }
            if (((TextStyle)object).strikeout) {
                string = string + "text-line-through-type:single;";
            }
            if ((font = ((TextStyle)object).font) != null && !font.isDisposed()) {
                object2 = font.getFontData()[0];
                string = string + "font-family:" + ((FontData)object2).getName();
                string = string + "font-size:" + ((FontData)object2).getHeight() + "pt;";
                string = string + "font-style:" + (((FontData)object2).data.lfItalic != 0 ? "italic" : "normal");
                string = string + "font-weight:" + ((FontData)object2).data.lfWeight;
            }
            if ((object2 = ((TextStyle)object).foreground) != null && !((Resource)object2).isDisposed()) {
                string = string + "color:rgb(" + ((Color)object2).getRed() + "," + ((Color)object2).getGreen() + "," + ((Color)object2).getBlue() + ");";
            }
            if ((object2 = ((TextStyle)object).background) != null && !((Resource)object2).isDisposed()) {
                string = string + "background-color:rgb(" + ((Color)object2).getRed() + "," + ((Color)object2).getGreen() + "," + ((Color)object2).getBlue() + ");";
            }
        }
        if (accessibleTextAttributeEvent.attributes != null) {
            int n2 = 0;
            while (n2 + 1 < accessibleTextAttributeEvent.attributes.length) {
                string = string + accessibleTextAttributeEvent.attributes[n2] + ":" + accessibleTextAttributeEvent.attributes[n2 + 1];
                n2 += 2;
            }
        }
        OS.MoveMemory(l2, new int[]{accessibleTextAttributeEvent.start}, 4);
        OS.MoveMemory(l3, new int[]{accessibleTextAttributeEvent.end}, 4);
        this.setString(l4, string);
        if (string.length() == 0) {
            return 1;
        }
        return 0;
    }

    int get_caretOffset(long l2) {
        int n = this.getCaretOffset();
        OS.MoveMemory(l2, new int[]{n}, 4);
        if (n == -1) {
            return 1;
        }
        return 0;
    }

    int get_characterExtents(int n, int n2, long l2, long l3, long l4, long l5) {
        int n3 = this.getCharacterCount();
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        accessibleTextEvent.start = n == -1 ? n3 : (n < 0 ? 0 : n);
        accessibleTextEvent.end = n == -1 || n >= n3 ? n3 : n + 1;
        for (int i = 0; i < this.accessibleTextExtendedListenersSize(); ++i) {
            AccessibleTextExtendedListener accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(i);
            accessibleTextExtendedListener.getTextBounds(accessibleTextEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleTextEvent.x}, 4);
        OS.MoveMemory(l3, new int[]{accessibleTextEvent.y}, 4);
        OS.MoveMemory(l4, new int[]{accessibleTextEvent.width}, 4);
        OS.MoveMemory(l5, new int[]{accessibleTextEvent.height}, 4);
        if (accessibleTextEvent.width == 0 && accessibleTextEvent.height == 0) {
            return -2147024809;
        }
        return 0;
    }

    int get_nSelections(long l2) {
        AccessibleTextListener accessibleTextListener;
        int n;
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        accessibleTextEvent.count = -1;
        for (n = 0; n < this.accessibleTextExtendedListenersSize(); ++n) {
            accessibleTextListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(n);
            accessibleTextListener.getSelectionCount(accessibleTextEvent);
        }
        if (accessibleTextEvent.count == -1) {
            accessibleTextEvent.childID = -1;
            accessibleTextEvent.offset = -1;
            accessibleTextEvent.length = 0;
            for (n = 0; n < this.accessibleTextListenersSize(); ++n) {
                accessibleTextListener = (AccessibleTextListener)this.accessibleTextListeners.get(n);
                accessibleTextListener.getSelectionRange(accessibleTextEvent);
            }
            accessibleTextEvent.count = accessibleTextEvent.offset != -1 && accessibleTextEvent.length > 0 ? 1 : 0;
        }
        OS.MoveMemory(l2, new int[]{accessibleTextEvent.count}, 4);
        return 0;
    }

    int get_offsetAtPoint(int n, int n2, int n3, long l2) {
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        accessibleTextEvent.x = n;
        accessibleTextEvent.y = n2;
        accessibleTextEvent.offset = -1;
        for (int i = 0; i < this.accessibleTextExtendedListenersSize(); ++i) {
            AccessibleTextExtendedListener accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(i);
            accessibleTextExtendedListener.getOffsetAtPoint(accessibleTextEvent);
        }
        OS.MoveMemory(l2, new int[]{accessibleTextEvent.offset}, 4);
        if (accessibleTextEvent.offset == -1) {
            return 1;
        }
        return 0;
    }

    int get_selection(int n, long l2, long l3) {
        AccessibleTextListener accessibleTextListener;
        int n2;
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        accessibleTextEvent.index = n;
        accessibleTextEvent.start = -1;
        accessibleTextEvent.end = -1;
        for (n2 = 0; n2 < this.accessibleTextExtendedListenersSize(); ++n2) {
            accessibleTextListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(n2);
            accessibleTextListener.getSelection(accessibleTextEvent);
        }
        if (accessibleTextEvent.start == -1 && n == 0) {
            accessibleTextEvent.childID = -1;
            accessibleTextEvent.offset = -1;
            accessibleTextEvent.length = 0;
            for (n2 = 0; n2 < this.accessibleTextListenersSize(); ++n2) {
                accessibleTextListener = (AccessibleTextListener)this.accessibleTextListeners.get(n2);
                accessibleTextListener.getSelectionRange(accessibleTextEvent);
            }
            accessibleTextEvent.start = accessibleTextEvent.offset;
            accessibleTextEvent.end = accessibleTextEvent.offset + accessibleTextEvent.length;
        }
        OS.MoveMemory(l2, new int[]{accessibleTextEvent.start}, 4);
        OS.MoveMemory(l3, new int[]{accessibleTextEvent.end}, 4);
        if (accessibleTextEvent.start == -1) {
            return 1;
        }
        return 0;
    }

    int get_text(int n, int n2, long l2) {
        int n3;
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        accessibleTextEvent.start = n == -1 ? this.getCharacterCount() : n;
        int n4 = accessibleTextEvent.end = n2 == -1 ? this.getCharacterCount() : n2;
        if (accessibleTextEvent.start > accessibleTextEvent.end) {
            n3 = accessibleTextEvent.start;
            accessibleTextEvent.start = accessibleTextEvent.end;
            accessibleTextEvent.end = n3;
        }
        accessibleTextEvent.count = 0;
        accessibleTextEvent.type = 5;
        for (n3 = 0; n3 < this.accessibleTextExtendedListenersSize(); ++n3) {
            AccessibleTextExtendedListener accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(n3);
            accessibleTextExtendedListener.getText(accessibleTextEvent);
        }
        if (accessibleTextEvent.result == null) {
            AccessibleControlEvent accessibleControlEvent = new AccessibleControlEvent(this);
            accessibleControlEvent.childID = -1;
            for (int i = 0; i < this.accessibleControlListenersSize(); ++i) {
                AccessibleControlListener accessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.get(i);
                accessibleControlListener.getRole(accessibleControlEvent);
                accessibleControlListener.getValue(accessibleControlEvent);
            }
            if (accessibleControlEvent.detail == 42) {
                accessibleTextEvent.result = accessibleControlEvent.result;
            }
        }
        this.setString(l2, accessibleTextEvent.result);
        if (accessibleTextEvent.result == null) {
            return -2147024809;
        }
        return 0;
    }

    int get_textBeforeOffset(int n, int n2, long l2, long l3, long l4) {
        int n3;
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        int n4 = this.getCharacterCount();
        accessibleTextEvent.end = accessibleTextEvent.start = n == -1 ? n4 : (n == -2 ? this.getCaretOffset() : n);
        accessibleTextEvent.count = -1;
        switch (n2) {
            case 0: {
                accessibleTextEvent.type = 0;
                break;
            }
            case 1: {
                accessibleTextEvent.type = 1;
                break;
            }
            case 2: {
                accessibleTextEvent.type = 2;
                break;
            }
            case 3: {
                accessibleTextEvent.type = 3;
                break;
            }
            case 4: {
                accessibleTextEvent.type = 4;
                break;
            }
            default: {
                return -2147024809;
            }
        }
        int n5 = accessibleTextEvent.start;
        int n6 = accessibleTextEvent.end;
        for (n3 = 0; n3 < this.accessibleTextExtendedListenersSize(); ++n3) {
            AccessibleTextExtendedListener accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(n3);
            accessibleTextExtendedListener.getText(accessibleTextEvent);
        }
        if (accessibleTextEvent.end < n4) {
            switch (n2) {
                case 1: 
                case 2: 
                case 3: 
                case 4: {
                    AccessibleTextExtendedListener accessibleTextExtendedListener;
                    int n7;
                    n3 = accessibleTextEvent.start;
                    accessibleTextEvent.start = n5;
                    accessibleTextEvent.end = n6;
                    accessibleTextEvent.count = 0;
                    for (n7 = 0; n7 < this.accessibleTextExtendedListenersSize(); ++n7) {
                        accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(n7);
                        accessibleTextExtendedListener.getText(accessibleTextEvent);
                    }
                    accessibleTextEvent.end = accessibleTextEvent.start;
                    accessibleTextEvent.start = n3;
                    accessibleTextEvent.type = 5;
                    accessibleTextEvent.count = 0;
                    for (n7 = 0; n7 < this.accessibleTextExtendedListenersSize(); ++n7) {
                        accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(n7);
                        accessibleTextExtendedListener.getText(accessibleTextEvent);
                    }
                    break;
                }
            }
        }
        OS.MoveMemory(l2, new int[]{accessibleTextEvent.start}, 4);
        OS.MoveMemory(l3, new int[]{accessibleTextEvent.end}, 4);
        this.setString(l4, accessibleTextEvent.result);
        if (accessibleTextEvent.result == null) {
            return 1;
        }
        return 0;
    }

    int get_textAfterOffset(int n, int n2, long l2, long l3, long l4) {
        int n3;
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        int n4 = this.getCharacterCount();
        accessibleTextEvent.end = accessibleTextEvent.start = n == -1 ? n4 : (n == -2 ? this.getCaretOffset() : n);
        accessibleTextEvent.count = 1;
        switch (n2) {
            case 0: {
                accessibleTextEvent.type = 0;
                break;
            }
            case 1: {
                accessibleTextEvent.type = 1;
                break;
            }
            case 2: {
                accessibleTextEvent.type = 2;
                break;
            }
            case 3: {
                accessibleTextEvent.type = 3;
                break;
            }
            case 4: {
                accessibleTextEvent.type = 4;
                break;
            }
            default: {
                return -2147024809;
            }
        }
        int n5 = accessibleTextEvent.start;
        int n6 = accessibleTextEvent.end;
        for (n3 = 0; n3 < this.accessibleTextExtendedListenersSize(); ++n3) {
            AccessibleTextExtendedListener accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(n3);
            accessibleTextExtendedListener.getText(accessibleTextEvent);
        }
        if (accessibleTextEvent.end < n4) {
            switch (n2) {
                case 1: 
                case 2: 
                case 3: 
                case 4: {
                    AccessibleTextExtendedListener accessibleTextExtendedListener;
                    int n7;
                    n3 = accessibleTextEvent.start;
                    accessibleTextEvent.start = n5;
                    accessibleTextEvent.end = n6;
                    accessibleTextEvent.count = 2;
                    for (n7 = 0; n7 < this.accessibleTextExtendedListenersSize(); ++n7) {
                        accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(n7);
                        accessibleTextExtendedListener.getText(accessibleTextEvent);
                    }
                    accessibleTextEvent.end = accessibleTextEvent.start;
                    accessibleTextEvent.start = n3;
                    accessibleTextEvent.type = 5;
                    accessibleTextEvent.count = 0;
                    for (n7 = 0; n7 < this.accessibleTextExtendedListenersSize(); ++n7) {
                        accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(n7);
                        accessibleTextExtendedListener.getText(accessibleTextEvent);
                    }
                    break;
                }
            }
        }
        OS.MoveMemory(l2, new int[]{accessibleTextEvent.start}, 4);
        OS.MoveMemory(l3, new int[]{accessibleTextEvent.end}, 4);
        this.setString(l4, accessibleTextEvent.result);
        if (accessibleTextEvent.result == null) {
            return 1;
        }
        return 0;
    }

    int get_textAtOffset(int n, int n2, long l2, long l3, long l4) {
        int n3;
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        int n4 = this.getCharacterCount();
        accessibleTextEvent.end = accessibleTextEvent.start = n == -1 ? n4 : (n == -2 ? this.getCaretOffset() : n);
        accessibleTextEvent.count = 0;
        switch (n2) {
            case 0: {
                accessibleTextEvent.type = 0;
                break;
            }
            case 1: {
                accessibleTextEvent.type = 1;
                break;
            }
            case 2: {
                accessibleTextEvent.type = 2;
                break;
            }
            case 3: {
                accessibleTextEvent.type = 3;
                break;
            }
            case 4: {
                accessibleTextEvent.type = 4;
                break;
            }
            case 5: {
                accessibleTextEvent.type = 5;
                accessibleTextEvent.start = 0;
                accessibleTextEvent.end = n4;
                accessibleTextEvent.count = 0;
                break;
            }
            default: {
                return -2147024809;
            }
        }
        int n5 = accessibleTextEvent.start;
        int n6 = accessibleTextEvent.end;
        for (n3 = 0; n3 < this.accessibleTextExtendedListenersSize(); ++n3) {
            AccessibleTextExtendedListener accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(n3);
            accessibleTextExtendedListener.getText(accessibleTextEvent);
        }
        if (accessibleTextEvent.end < n4) {
            switch (n2) {
                case 1: 
                case 2: 
                case 3: 
                case 4: {
                    AccessibleTextExtendedListener accessibleTextExtendedListener;
                    int n7;
                    n3 = accessibleTextEvent.start;
                    accessibleTextEvent.start = n5;
                    accessibleTextEvent.end = n6;
                    accessibleTextEvent.count = 1;
                    for (n7 = 0; n7 < this.accessibleTextExtendedListenersSize(); ++n7) {
                        accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(n7);
                        accessibleTextExtendedListener.getText(accessibleTextEvent);
                    }
                    accessibleTextEvent.end = accessibleTextEvent.start;
                    accessibleTextEvent.start = n3;
                    accessibleTextEvent.type = 5;
                    accessibleTextEvent.count = 0;
                    for (n7 = 0; n7 < this.accessibleTextExtendedListenersSize(); ++n7) {
                        accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(n7);
                        accessibleTextExtendedListener.getText(accessibleTextEvent);
                    }
                    break;
                }
            }
        }
        OS.MoveMemory(l2, new int[]{accessibleTextEvent.start}, 4);
        OS.MoveMemory(l3, new int[]{accessibleTextEvent.end}, 4);
        this.setString(l4, accessibleTextEvent.result);
        if (accessibleTextEvent.result == null) {
            return 1;
        }
        return 0;
    }

    int removeSelection(int n) {
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        accessibleTextEvent.index = n;
        for (int i = 0; i < this.accessibleTextExtendedListenersSize(); ++i) {
            AccessibleTextExtendedListener accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(i);
            accessibleTextExtendedListener.removeSelection(accessibleTextEvent);
        }
        if (accessibleTextEvent.result == null || !accessibleTextEvent.result.equals("OK")) {
            return -2147024809;
        }
        return 0;
    }

    int setCaretOffset(int n) {
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        accessibleTextEvent.offset = n == -1 ? this.getCharacterCount() : n;
        for (int i = 0; i < this.accessibleTextExtendedListenersSize(); ++i) {
            AccessibleTextExtendedListener accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(i);
            accessibleTextExtendedListener.setCaretOffset(accessibleTextEvent);
        }
        if (accessibleTextEvent.result == null || !accessibleTextEvent.result.equals("OK")) {
            return -2147024809;
        }
        return 0;
    }

    int setSelection(int n, int n2, int n3) {
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        accessibleTextEvent.index = n;
        accessibleTextEvent.start = n2 == -1 ? this.getCharacterCount() : n2;
        accessibleTextEvent.end = n3 == -1 ? this.getCharacterCount() : n3;
        for (int i = 0; i < this.accessibleTextExtendedListenersSize(); ++i) {
            AccessibleTextExtendedListener accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(i);
            accessibleTextExtendedListener.setSelection(accessibleTextEvent);
        }
        if (accessibleTextEvent.result == null || !accessibleTextEvent.result.equals("OK")) {
            return -2147024809;
        }
        return 0;
    }

    int get_nCharacters(long l2) {
        int n = this.getCharacterCount();
        OS.MoveMemory(l2, new int[]{n}, 4);
        return 0;
    }

    int scrollSubstringTo(int n, int n2, int n3) {
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        accessibleTextEvent.start = n;
        accessibleTextEvent.end = n2;
        switch (n3) {
            case 0: {
                accessibleTextEvent.type = 0;
                break;
            }
            case 1: {
                accessibleTextEvent.type = 1;
                break;
            }
            case 2: {
                accessibleTextEvent.type = 2;
                break;
            }
            case 3: {
                accessibleTextEvent.type = 3;
                break;
            }
            case 4: {
                accessibleTextEvent.type = 4;
                break;
            }
            case 5: {
                accessibleTextEvent.type = 5;
                break;
            }
            case 6: {
                accessibleTextEvent.type = 6;
            }
        }
        for (int i = 0; i < this.accessibleTextExtendedListenersSize(); ++i) {
            AccessibleTextExtendedListener accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(i);
            accessibleTextExtendedListener.scrollText(accessibleTextEvent);
        }
        if (accessibleTextEvent.result == null || !accessibleTextEvent.result.equals("OK")) {
            return -2147024809;
        }
        return 0;
    }

    int scrollSubstringToPoint(int n, int n2, int n3, int n4, int n5) {
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        accessibleTextEvent.start = n;
        accessibleTextEvent.end = n2;
        accessibleTextEvent.type = 7;
        accessibleTextEvent.x = n4;
        accessibleTextEvent.y = n5;
        for (int i = 0; i < this.accessibleTextExtendedListenersSize(); ++i) {
            AccessibleTextExtendedListener accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(i);
            accessibleTextExtendedListener.scrollText(accessibleTextEvent);
        }
        if (accessibleTextEvent.result == null || !accessibleTextEvent.result.equals("OK")) {
            return -2147024809;
        }
        return 0;
    }

    int get_newText(long l2) {
        String string = null;
        int n = 0;
        int n2 = 0;
        if (this.textInserted != null) {
            string = (String)this.textInserted[3];
            n = (Integer)this.textInserted[1];
            n2 = (Integer)this.textInserted[2];
        }
        this.setString(l2, string);
        OS.MoveMemory(l2 + (long)C.PTR_SIZEOF, new int[]{n}, 4);
        OS.MoveMemory(l2 + (long)C.PTR_SIZEOF + 4L, new int[]{n2}, 4);
        if (this.textInserted == null) {
            return 1;
        }
        return 0;
    }

    int get_oldText(long l2) {
        String string = null;
        int n = 0;
        int n2 = 0;
        if (this.textDeleted != null) {
            string = (String)this.textDeleted[3];
            n = (Integer)this.textDeleted[1];
            n2 = (Integer)this.textDeleted[2];
        }
        this.setString(l2, string);
        OS.MoveMemory(l2 + (long)C.PTR_SIZEOF, new int[]{n}, 4);
        OS.MoveMemory(l2 + (long)C.PTR_SIZEOF + 4L, new int[]{n2}, 4);
        if (this.textDeleted == null) {
            return 1;
        }
        return 0;
    }

    int get_currentValue(long l2) {
        AccessibleValueEvent accessibleValueEvent = new AccessibleValueEvent(this);
        for (int i = 0; i < this.accessibleValueListenersSize(); ++i) {
            AccessibleValueListener accessibleValueListener = (AccessibleValueListener)this.accessibleValueListeners.get(i);
            accessibleValueListener.getCurrentValue(accessibleValueEvent);
        }
        this.setNumberVARIANT(l2, accessibleValueEvent.value);
        return 0;
    }

    int setCurrentValue(long l2) {
        AccessibleValueEvent accessibleValueEvent = new AccessibleValueEvent(this);
        accessibleValueEvent.value = this.getNumberVARIANT(l2);
        for (int i = 0; i < this.accessibleValueListenersSize(); ++i) {
            AccessibleValueListener accessibleValueListener = (AccessibleValueListener)this.accessibleValueListeners.get(i);
            accessibleValueListener.setCurrentValue(accessibleValueEvent);
        }
        return 0;
    }

    int get_maximumValue(long l2) {
        AccessibleValueEvent accessibleValueEvent = new AccessibleValueEvent(this);
        for (int i = 0; i < this.accessibleValueListenersSize(); ++i) {
            AccessibleValueListener accessibleValueListener = (AccessibleValueListener)this.accessibleValueListeners.get(i);
            accessibleValueListener.getMaximumValue(accessibleValueEvent);
        }
        this.setNumberVARIANT(l2, accessibleValueEvent.value);
        return 0;
    }

    int get_minimumValue(long l2) {
        AccessibleValueEvent accessibleValueEvent = new AccessibleValueEvent(this);
        for (int i = 0; i < this.accessibleValueListenersSize(); ++i) {
            AccessibleValueListener accessibleValueListener = (AccessibleValueListener)this.accessibleValueListeners.get(i);
            accessibleValueListener.getMinimumValue(accessibleValueEvent);
        }
        this.setNumberVARIANT(l2, accessibleValueEvent.value);
        return 0;
    }

    int eventChildID() {
        if (this.parent == null) {
            return 0;
        }
        if (this.uniqueID == -1) {
            this.uniqueID = UniqueID--;
        }
        return this.uniqueID;
    }

    void checkUniqueID(int n) {
        AccessibleControlEvent accessibleControlEvent = new AccessibleControlEvent(this);
        accessibleControlEvent.childID = n;
        for (int i = 0; i < this.accessibleControlListenersSize(); ++i) {
            AccessibleControlListener accessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.get(i);
            accessibleControlListener.getChild(accessibleControlEvent);
        }
        Accessible accessible = accessibleControlEvent.accessible;
        if (accessible != null && accessible.uniqueID == -1) {
            accessible.uniqueID = n;
        }
    }

    int childIDToOs(int n) {
        if (n == -1) {
            return 0;
        }
        int n2 = n + 1;
        if (this.control instanceof Tree) {
            n2 = (int)OS.SendMessage(this.control.handle, 4395, (long)n, 0L);
        }
        this.checkUniqueID(n2);
        return n2;
    }

    int osToChildID(int n) {
        if (n == 0) {
            return -1;
        }
        if (!(this.control instanceof Tree)) {
            return n - 1;
        }
        return (int)OS.SendMessage(this.control.handle, 4394, (long)n, 0L);
    }

    int stateToOs(int n) {
        int n2 = 0;
        if ((n & 2) != 0) {
            n2 |= 2;
        }
        if ((n & 0x200000) != 0) {
            n2 |= 0x200000;
        }
        if ((n & 0x1000000) != 0) {
            n2 |= 0x1000000;
        }
        if ((n & 4) != 0) {
            n2 |= 4;
        }
        if ((n & 0x100000) != 0) {
            n2 |= 0x100000;
        }
        if ((n & 8) != 0) {
            n2 |= 8;
        }
        if ((n & 0x10) != 0) {
            n2 |= 0x10;
        }
        if ((n & 0x200) != 0) {
            n2 |= 0x200;
        }
        if ((n & 0x400) != 0) {
            n2 |= 0x400;
        }
        if ((n & 0x80) != 0) {
            n2 |= 0x80;
        }
        if ((n & 0x800) != 0) {
            n2 |= 0x800;
        }
        if ((n & 0x40) != 0) {
            n2 |= 0x40;
        }
        if ((n & 0x8000) != 0) {
            n2 |= 0x8000;
        }
        if ((n & 0x10000) != 0) {
            n2 |= 0x10000;
        }
        if ((n & 0x20000) != 0) {
            n2 |= 0x20000;
        }
        if ((n & 0x400000) != 0) {
            n2 |= 0x400000;
        }
        if ((n & 1) != 0) {
            n2 |= 1;
        }
        return n2;
    }

    int osToState(int n) {
        int n2 = 0;
        if ((n & 2) != 0) {
            n2 |= 2;
        }
        if ((n & 0x200000) != 0) {
            n2 |= 0x200000;
        }
        if ((n & 0x1000000) != 0) {
            n2 |= 0x1000000;
        }
        if ((n & 4) != 0) {
            n2 |= 4;
        }
        if ((n & 0x100000) != 0) {
            n2 |= 0x100000;
        }
        if ((n & 8) != 0) {
            n2 |= 8;
        }
        if ((n & 0x10) != 0) {
            n2 |= 0x10;
        }
        if ((n & 0x200) != 0) {
            n2 |= 0x200;
        }
        if ((n & 0x400) != 0) {
            n2 |= 0x400;
        }
        if ((n & 0x80) != 0) {
            n2 |= 0x80;
        }
        if ((n & 0x800) != 0) {
            n2 |= 0x800;
        }
        if ((n & 0x40) != 0) {
            n2 |= 0x40;
        }
        if ((n & 0x8000) != 0) {
            n2 |= 0x8000;
        }
        if ((n & 0x10000) != 0) {
            n2 |= 0x10000;
        }
        if ((n & 0x20000) != 0) {
            n2 |= 0x20000;
        }
        if ((n & 0x400000) != 0) {
            n2 |= 0x400000;
        }
        if ((n & 1) != 0) {
            n2 |= 1;
        }
        return n2;
    }

    int roleToOs(int n) {
        switch (n) {
            case 10: {
                return 10;
            }
            case 9: {
                return 9;
            }
            case 2: {
                return 2;
            }
            case 11: {
                return 11;
            }
            case 12: {
                return 12;
            }
            case 21: {
                return 21;
            }
            case 13: {
                return 13;
            }
            case 3: {
                return 3;
            }
            case 18: {
                return 18;
            }
            case 41: {
                return 41;
            }
            case 43: {
                return 43;
            }
            case 44: {
                return 44;
            }
            case 45: {
                return 45;
            }
            case 62: {
                return 62;
            }
            case 46: {
                return 46;
            }
            case 42: {
                return 42;
            }
            case 22: {
                return 22;
            }
            case 33: {
                return 33;
            }
            case 34: {
                return 34;
            }
            case 24: {
                return 24;
            }
            case 29: {
                return 29;
            }
            case 25: {
                return 25;
            }
            case 26: {
                return 26;
            }
            case 35: {
                return 35;
            }
            case 36: {
                return 36;
            }
            case 60: {
                return 60;
            }
            case 37: {
                return 37;
            }
            case 48: {
                return 48;
            }
            case 51: {
                return 51;
            }
            case 30: {
                return 30;
            }
            case 8: {
                return 8;
            }
            case 54: {
                return 54;
            }
            case 27: {
                return 27;
            }
            case 15: {
                return 15;
            }
            case 40: {
                return 40;
            }
            case 20: {
                return 20;
            }
            case 28: {
                return 28;
            }
            case 52: {
                return 52;
            }
            case 23: {
                return 23;
            }
            case 61: {
                return 61;
            }
            case 47: {
                return 47;
            }
            case 1025: {
                return 10;
            }
            case 1027: {
                return 12;
            }
            case 1073: {
                return 12;
            }
            case 1029: {
                return 47;
            }
            case 1038: {
                return 10;
            }
            case 1040: {
                return 10;
            }
            case 1043: {
                return 10;
            }
            case 1044: {
                return 10;
            }
            case 1053: {
                return 10;
            }
            case 1054: {
                return 10;
            }
            case 1060: {
                return 10;
            }
        }
        return 10;
    }

    int osToRole(int n) {
        switch (n) {
            case 10: {
                return 10;
            }
            case 9: {
                return 9;
            }
            case 2: {
                return 2;
            }
            case 11: {
                return 11;
            }
            case 12: {
                return 12;
            }
            case 21: {
                return 21;
            }
            case 13: {
                return 13;
            }
            case 3: {
                return 3;
            }
            case 18: {
                return 18;
            }
            case 41: {
                return 41;
            }
            case 43: {
                return 43;
            }
            case 44: {
                return 44;
            }
            case 45: {
                return 45;
            }
            case 62: {
                return 62;
            }
            case 46: {
                return 46;
            }
            case 42: {
                return 42;
            }
            case 22: {
                return 22;
            }
            case 33: {
                return 33;
            }
            case 34: {
                return 34;
            }
            case 24: {
                return 24;
            }
            case 29: {
                return 29;
            }
            case 25: {
                return 25;
            }
            case 26: {
                return 26;
            }
            case 35: {
                return 35;
            }
            case 36: {
                return 36;
            }
            case 60: {
                return 60;
            }
            case 37: {
                return 37;
            }
            case 48: {
                return 48;
            }
            case 51: {
                return 51;
            }
            case 30: {
                return 30;
            }
            case 8: {
                return 8;
            }
            case 54: {
                return 54;
            }
            case 27: {
                return 27;
            }
            case 15: {
                return 15;
            }
            case 40: {
                return 40;
            }
            case 20: {
                return 20;
            }
            case 28: {
                return 28;
            }
            case 52: {
                return 52;
            }
            case 23: {
                return 23;
            }
            case 61: {
                return 61;
            }
            case 47: {
                return 47;
            }
        }
        return 10;
    }

    Color colorFromString(String string) {
        try {
            int n = string.indexOf(40);
            int n2 = string.indexOf(44);
            int n3 = string.indexOf(44, n2 + 1);
            int n4 = string.indexOf(41);
            int n5 = Integer.parseInt(string.substring(n + 1, n2));
            int n6 = Integer.parseInt(string.substring(n2 + 1, n3));
            int n7 = Integer.parseInt(string.substring(n3 + 1, n4));
            return new Color(this.control.getDisplay(), n5, n6, n7);
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    int getCaretOffset() {
        AccessibleTextListener accessibleTextListener;
        int n;
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        accessibleTextEvent.offset = -1;
        for (n = 0; n < this.accessibleTextExtendedListenersSize(); ++n) {
            accessibleTextListener = (AccessibleTextListener)this.accessibleTextExtendedListeners.get(n);
            accessibleTextListener.getCaretOffset(accessibleTextEvent);
        }
        if (accessibleTextEvent.offset == -1) {
            for (n = 0; n < this.accessibleTextListenersSize(); ++n) {
                accessibleTextEvent.childID = -1;
                accessibleTextListener = (AccessibleTextListener)this.accessibleTextListeners.get(n);
                accessibleTextListener.getCaretOffset(accessibleTextEvent);
            }
        }
        return accessibleTextEvent.offset;
    }

    int getCharacterCount() {
        AccessibleTextEvent accessibleTextEvent = new AccessibleTextEvent(this);
        accessibleTextEvent.count = -1;
        for (int i = 0; i < this.accessibleTextExtendedListenersSize(); ++i) {
            AccessibleTextExtendedListener accessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.get(i);
            accessibleTextExtendedListener.getCharacterCount(accessibleTextEvent);
        }
        if (accessibleTextEvent.count == -1) {
            AccessibleControlEvent accessibleControlEvent = new AccessibleControlEvent(this);
            accessibleControlEvent.childID = -1;
            for (int i = 0; i < this.accessibleControlListenersSize(); ++i) {
                AccessibleControlListener accessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.get(i);
                accessibleControlListener.getRole(accessibleControlEvent);
                accessibleControlListener.getValue(accessibleControlEvent);
            }
            accessibleTextEvent.count = accessibleControlEvent.detail == 42 && accessibleControlEvent.result != null ? accessibleControlEvent.result.length() : 0;
        }
        return accessibleTextEvent.count;
    }

    int getRelationCount() {
        int n = 0;
        for (int i = 0; i < 15; ++i) {
            if (this.relations[i] == null) continue;
            ++n;
        }
        return n;
    }

    int getRole() {
        AccessibleControlEvent accessibleControlEvent = new AccessibleControlEvent(this);
        accessibleControlEvent.childID = -1;
        for (int i = 0; i < this.accessibleControlListenersSize(); ++i) {
            AccessibleControlListener accessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.get(i);
            accessibleControlListener.getRole(accessibleControlEvent);
        }
        return accessibleControlEvent.detail;
    }

    int getDefaultRole() {
        int n = 10;
        if (this.iaccessible != null) {
            long l2 = OS.GlobalAlloc(64, VARIANT.sizeof);
            this.setIntVARIANT(l2, (short)3, 0);
            long l3 = OS.GlobalAlloc(64, VARIANT.sizeof);
            int n2 = this.iaccessible.get_accRole(l2, l3);
            if (n2 == 0) {
                VARIANT vARIANT = this.getVARIANT(l3);
                if (vARIANT.vt == 3) {
                    n = vARIANT.lVal;
                }
            }
            OS.GlobalFree(l2);
            OS.GlobalFree(l3);
        }
        return n;
    }

    String getString(long l2) {
        long[] lArray = new long[]{0L};
        OS.MoveMemory(lArray, l2, C.PTR_SIZEOF);
        int n = COM.SysStringByteLen(lArray[0]);
        if (n == 0) {
            return "";
        }
        char[] cArray = new char[(n + 1) / 2];
        OS.MoveMemory(cArray, lArray[0], n);
        return new String(cArray);
    }

    VARIANT getVARIANT(long l2) {
        VARIANT vARIANT = new VARIANT();
        COM.MoveMemory(vARIANT, l2, VARIANT.sizeof);
        return vARIANT;
    }

    Number getNumberVARIANT(long l2) {
        VARIANT vARIANT = new VARIANT();
        COM.MoveMemory(vARIANT, l2, VARIANT.sizeof);
        if (vARIANT.vt == 20) {
            return vARIANT.lVal;
        }
        return vARIANT.lVal;
    }

    void setIntVARIANT(long l2, short s, int n) {
        if (s == 3 || s == 0) {
            OS.MoveMemory(l2, new short[]{s}, 2);
            OS.MoveMemory(l2 + 8L, new int[]{n}, 4);
        }
    }

    void setPtrVARIANT(long l2, short s, long l3) {
        if (s == 9 || s == 13) {
            OS.MoveMemory(l2, new short[]{s}, 2);
            OS.MoveMemory(l2 + 8L, new long[]{l3}, C.PTR_SIZEOF);
        }
    }

    void setNumberVARIANT(long l2, Number number) {
        if (number == null) {
            OS.MoveMemory(l2, new short[]{0}, 2);
            OS.MoveMemory(l2 + 8L, new int[]{0}, 4);
        } else if (number instanceof Double) {
            OS.MoveMemory(l2, new short[]{5}, 2);
            OS.MoveMemory(l2 + 8L, new double[]{number.doubleValue()}, 8);
        } else if (number instanceof Float) {
            OS.MoveMemory(l2, new short[]{4}, 2);
            OS.MoveMemory(l2 + 8L, new float[]{number.floatValue()}, 4);
        } else if (number instanceof Long) {
            OS.MoveMemory(l2, new short[]{20}, 2);
            OS.MoveMemory(l2 + 8L, new long[]{number.longValue()}, 8);
        } else {
            OS.MoveMemory(l2, new short[]{3}, 2);
            OS.MoveMemory(l2 + 8L, new int[]{number.intValue()}, 4);
        }
    }

    void setString(long l2, String string) {
        long l3 = 0L;
        if (string != null) {
            char[] cArray = string.toCharArray();
            l3 = COM.SysAllocString(cArray);
        }
        OS.MoveMemory(l2, new long[]{l3}, C.PTR_SIZEOF);
    }

    void setStringVARIANT(long l2, String string) {
        long l3 = 0L;
        if (string != null) {
            char[] cArray = string.toCharArray();
            l3 = COM.SysAllocString(cArray);
        }
        OS.MoveMemory(l2, new short[]{(short)(l3 == 0L ? 0 : 8)}, 2);
        OS.MoveMemory(l2 + 8L, new long[]{l3}, C.PTR_SIZEOF);
    }

    /*
     * Exception decompiling
     */
    void checkWidget() {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: While processing lbl2 : IF_ACMPNE - null : Stack underflow
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

    boolean isATRunning() {
        return true;
    }

    static void print(String string) {
    }

    String getRoleString(int n) {
        return "Unknown role (" + n;
    }

    String getStateString(int n) {
        if (n == 0) {
            return " no state bits set";
        }
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.toString();
    }

    String getIA2StatesString(int n) {
        if (n == 0) {
            return " no state bits set";
        }
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.toString();
    }

    String getEventString(int n) {
        return "Unknown event (" + n;
    }

    private String hresult(int n) {
        return " HRESULT=" + n;
    }

    boolean interesting(GUID gUID) {
        return false;
    }

    String guidString(GUID gUID) {
        return gUID.toString();
    }

    static GUID IIDFromString(String string) {
        return null;
    }

    public String toString() {
        String string = super.toString();
        return string;
    }

    private void lambda$get_accChild$0(ToolItem toolItem, Event event) {
        ArrayList arrayList = new ArrayList(this.children);
        for (Accessible accessible : arrayList) {
            if (accessible.item != toolItem) continue;
            accessible.dispose();
        }
    }

    static {
        String string = System.getProperty(PROPERTY_USEIA2);
        if (string != null && string.equalsIgnoreCase("false")) {
            UseIA2 = false;
        }
    }
}

