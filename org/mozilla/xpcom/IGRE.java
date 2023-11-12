/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.xpcom;

import java.io.File;
import org.mozilla.xpcom.IAppFileLocProvider;
import org.mozilla.xpcom.ProfileLock;
import org.mozilla.xpcom.XPCOMException;

public interface IGRE {
    public void initEmbedding(File var1, File var2, IAppFileLocProvider var3) throws XPCOMException;

    public void termEmbedding();

    public ProfileLock lockProfileDirectory(File var1) throws XPCOMException;

    public void notifyProfile();
}

