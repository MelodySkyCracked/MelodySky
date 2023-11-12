/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.jsdIFilter;
import org.mozilla.interfaces.nsISupports;

public interface jsdIFilterEnumerator
extends nsISupports {
    public static final String JSDIFILTERENUMERATOR_IID = "{54382875-ed12-4f90-9a63-1f0498d0a3f2}";

    public void enumerateFilter(jsdIFilter var1);
}

