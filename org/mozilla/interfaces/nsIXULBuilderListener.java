/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIXULTemplateBuilder;

public interface nsIXULBuilderListener
extends nsISupports {
    public static final String NS_IXULBUILDERLISTENER_IID = "{ac46be8f-c863-4c23-84a2-d0fcc8dfa9f4}";

    public void willRebuild(nsIXULTemplateBuilder var1);

    public void didRebuild(nsIXULTemplateBuilder var1);
}

