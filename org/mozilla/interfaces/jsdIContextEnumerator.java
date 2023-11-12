/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.jsdIContext;
import org.mozilla.interfaces.nsISupports;

public interface jsdIContextEnumerator
extends nsISupports {
    public static final String JSDICONTEXTENUMERATOR_IID = "{912e342a-1dd2-11b2-b09f-cf3af38c15f0}";

    public void enumerateContext(jsdIContext var1);
}

