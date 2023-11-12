/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIEditor;
import org.mozilla.interfaces.nsISupports;

public interface nsIEditingSession
extends nsISupports {
    public static final String NS_IEDITINGSESSION_IID = "{d39fd2b4-3978-45d2-a4be-ba448171b61b}";
    public static final int eEditorOK = 0;
    public static final int eEditorCreationInProgress = 1;
    public static final int eEditorErrorCantEditMimeType = 2;
    public static final int eEditorErrorFileNotFound = 3;
    public static final int eEditorErrorCantEditFramesets = 8;
    public static final int eEditorErrorUnknown = 9;

    public long getEditorStatus();

    public void makeWindowEditable(nsIDOMWindow var1, String var2, boolean var3);

    public boolean windowIsEditable(nsIDOMWindow var1);

    public nsIEditor getEditorForWindow(nsIDOMWindow var1);

    public void setupEditorOnWindow(nsIDOMWindow var1);

    public void tearDownEditorOnWindow(nsIDOMWindow var1);

    public void setEditorOnControllers(nsIDOMWindow var1, nsIEditor var2);
}

