/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEventTarget;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWebNavigation;

public interface nsIDragDropHandler
extends nsISupports {
    public static final String NS_IDRAGDROPHANDLER_IID = "{4f418f58-f834-4736-a755-e0395bedca9d}";

    public void hookupTo(nsIDOMEventTarget var1, nsIWebNavigation var2);

    public void detach();
}

