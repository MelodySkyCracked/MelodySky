/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.xpcom;

import java.io.File;
import org.mozilla.xpcom.XPCOMInitializationException;

public interface IMozilla {
    public void initialize(File var1) throws XPCOMInitializationException;

    public long getNativeHandleFromAWT(Object var1);
}

