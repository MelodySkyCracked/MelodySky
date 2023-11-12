/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDragSession;
import org.mozilla.interfaces.nsIScriptableRegion;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsISupportsArray;

public interface nsIDragService
extends nsISupports {
    public static final String NS_IDRAGSERVICE_IID = "{8b5314bb-db01-11d2-96ce-0060b0fb9956}";
    public static final int DRAGDROP_ACTION_NONE = 0;
    public static final int DRAGDROP_ACTION_COPY = 1;
    public static final int DRAGDROP_ACTION_MOVE = 2;
    public static final int DRAGDROP_ACTION_LINK = 4;

    public void invokeDragSession(nsIDOMNode var1, nsISupportsArray var2, nsIScriptableRegion var3, long var4);

    public nsIDragSession getCurrentSession();

    public void startDragSession();

    public void endDragSession();
}

