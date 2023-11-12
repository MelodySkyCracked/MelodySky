/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.xpcom;

import java.io.File;
import org.mozilla.interfaces.nsIComponentManager;
import org.mozilla.interfaces.nsIComponentRegistrar;
import org.mozilla.interfaces.nsILocalFile;
import org.mozilla.interfaces.nsIServiceManager;
import org.mozilla.xpcom.IAppFileLocProvider;
import org.mozilla.xpcom.XPCOMException;

public interface IXPCOM {
    public nsIServiceManager initXPCOM(File var1, IAppFileLocProvider var2) throws XPCOMException;

    public void shutdownXPCOM(nsIServiceManager var1) throws XPCOMException;

    public nsIServiceManager getServiceManager() throws XPCOMException;

    public nsIComponentManager getComponentManager() throws XPCOMException;

    public nsIComponentRegistrar getComponentRegistrar() throws XPCOMException;

    public nsILocalFile newLocalFile(String var1, boolean var2) throws XPCOMException;
}

