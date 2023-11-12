/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.jsdIScript;
import org.mozilla.interfaces.nsISupports;

public interface jsdIScriptHook
extends nsISupports {
    public static final String JSDISCRIPTHOOK_IID = "{ae89a7e2-1dd1-11b2-8c2f-af82086291a5}";

    public void onScriptCreated(jsdIScript var1);

    public void onScriptDestroyed(jsdIScript var1);
}

