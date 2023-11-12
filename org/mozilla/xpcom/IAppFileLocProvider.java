/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.xpcom;

import java.io.File;

public interface IAppFileLocProvider {
    public File getFile(String var1, boolean[] var2);

    public File[] getFiles(String var1);
}

