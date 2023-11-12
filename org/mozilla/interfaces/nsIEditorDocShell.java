/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEditor;
import org.mozilla.interfaces.nsISupports;

public interface nsIEditorDocShell
extends nsISupports {
    public static final String NS_IEDITORDOCSHELL_IID = "{3bdb8f01-f141-11d4-a73c-fba4aba8a3fc}";

    public nsIEditor getEditor();

    public void setEditor(nsIEditor var1);

    public boolean getEditable();

    public boolean getHasEditingSession();

    public void makeEditable(boolean var1);
}

