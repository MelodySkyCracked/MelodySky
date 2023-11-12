/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIEditorIMESupport
extends nsISupports {
    public static final String NS_IEDITORIMESUPPORT_IID = "{205b3e49-aa58-499e-880b-aacab9dede01}";

    public void endComposition();

    public void forceCompositionEnd();

    public void notifyIMEOnFocus();

    public void notifyIMEOnBlur();
}

