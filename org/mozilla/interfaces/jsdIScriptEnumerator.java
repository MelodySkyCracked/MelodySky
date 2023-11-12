/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.jsdIScript;
import org.mozilla.interfaces.nsISupports;

public interface jsdIScriptEnumerator
extends nsISupports {
    public static final String JSDISCRIPTENUMERATOR_IID = "{4c2f706e-1dd2-11b2-9ebc-85a06e948830}";

    public void enumerateScript(jsdIScript var1);
}

