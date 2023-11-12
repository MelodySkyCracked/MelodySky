/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.accessibility.lIll;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.win32.OS;

class Relation {
    Accessible accessible;
    COMObject objIAccessibleRelation;
    int refCount;
    int type;
    Accessible[] targets;
    static final String[] relationTypeString = new String[]{"controlledBy", "controllerFor", "describedBy", "descriptionFor", "embeddedBy", "embeds", "flowsFrom", "flowsTo", "labelFor", "labelledBy", "memberOf", "nodeChildOf", "parentWindowOf", "popupFor", "subwindowOf"};
    static final String[] localizedRelationTypeString = new String[]{SWT.getMessage("SWT_Controlled_By"), SWT.getMessage("SWT_Controller_For"), SWT.getMessage("SWT_Described_By"), SWT.getMessage("SWT_Description_For"), SWT.getMessage("SWT_Embedded_By"), SWT.getMessage("SWT_Embeds"), SWT.getMessage("SWT_Flows_From"), SWT.getMessage("SWT_Flows_To"), SWT.getMessage("SWT_Label_For"), SWT.getMessage("SWT_Labelled_By"), SWT.getMessage("SWT_Member_Of"), SWT.getMessage("SWT_Node_Child_Of"), SWT.getMessage("SWT_Parent_Window_Of"), SWT.getMessage("SWT_Popup_For"), SWT.getMessage("SWT_Subwindow_Of")};

    Relation(Accessible accessible, int n) {
        this.accessible = accessible;
        this.type = n;
        this.targets = new Accessible[0];
        this.AddRef();
    }

    long getAddress() {
        if (this.objIAccessibleRelation == null) {
            this.createIAccessibleRelation();
        }
        return this.objIAccessibleRelation.getAddress();
    }

    void createIAccessibleRelation() {
        this.objIAccessibleRelation = new lIll(this, new int[]{2, 0, 0, 1, 1, 1, 2, 3});
    }

    int QueryInterface(long l2, long l3) {
        GUID gUID = new GUID();
        COM.MoveMemory(gUID, l2, GUID.sizeof);
        if (COM.IsEqualGUID(gUID, COM.IIDIUnknown) || COM.IsEqualGUID(gUID, COM.IIDIAccessibleRelation)) {
            OS.MoveMemory(l3, new long[]{this.getAddress()}, C.PTR_SIZEOF);
            this.AddRef();
            return 0;
        }
        return -2147467262;
    }

    int AddRef() {
        return ++this.refCount;
    }

    int Release() {
        --this.refCount;
        if (this.refCount == 0) {
            if (this.objIAccessibleRelation != null) {
                this.objIAccessibleRelation.dispose();
            }
            this.objIAccessibleRelation = null;
        }
        return this.refCount;
    }

    int get_relationType(long l2) {
        this.setString(l2, relationTypeString[this.type]);
        return 0;
    }

    int get_localizedRelationType(long l2) {
        this.setString(l2, localizedRelationTypeString[this.type]);
        return 0;
    }

    int get_nTargets(long l2) {
        OS.MoveMemory(l2, new int[]{this.targets.length}, 4);
        return 0;
    }

    int get_target(int n, long l2) {
        if (n < 0 || n >= this.targets.length) {
            return -2147024809;
        }
        Accessible accessible = this.targets[n];
        accessible.AddRef();
        OS.MoveMemory(l2, new long[]{accessible.getAddress()}, C.PTR_SIZEOF);
        return 0;
    }

    int get_targets(int n, long l2, long l3) {
        int n2 = Math.min(this.targets.length, n);
        for (int i = 0; i < n2; ++i) {
            Accessible accessible = this.targets[i];
            accessible.AddRef();
            OS.MoveMemory(l2 + (long)(i * C.PTR_SIZEOF), new long[]{accessible.getAddress()}, C.PTR_SIZEOF);
        }
        OS.MoveMemory(l3, new int[]{n2}, 4);
        return 0;
    }

    void addTarget(Accessible accessible) {
        if (this < accessible) {
            return;
        }
        Accessible[] accessibleArray = new Accessible[this.targets.length + 1];
        System.arraycopy(this.targets, 0, accessibleArray, 0, this.targets.length);
        accessibleArray[this.targets.length] = accessible;
        this.targets = accessibleArray;
    }

    void removeTarget(Accessible accessible) {
        if (this < accessible) {
            return;
        }
        Accessible[] accessibleArray = new Accessible[this.targets.length - 1];
        int n = 0;
        for (Accessible accessible2 : this.targets) {
            if (accessible2 == accessible) continue;
            accessibleArray[n++] = accessible2;
        }
        this.targets = accessibleArray;
    }

    boolean hasTargets() {
        return this.targets.length > 0;
    }

    void setString(long l2, String string) {
        char[] cArray = string.toCharArray();
        long l3 = COM.SysAllocString(cArray);
        OS.MoveMemory(l2, new long[]{l3}, C.PTR_SIZEOF);
    }
}

