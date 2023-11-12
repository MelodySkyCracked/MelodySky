/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;

public interface nsIProcess
extends nsISupports {
    public static final String NS_IPROCESS_IID = "{9da0b650-d07e-4617-a18a-250035572ac8}";

    public void init(nsIFile var1);

    public void initWithPid(long var1);

    public void kill();

    public long run(boolean var1, String[] var2, long var3);

    public nsIFile getLocation();

    public long getPid();

    public String getProcessName();

    public long getProcessSignature();

    public int getExitValue();
}

