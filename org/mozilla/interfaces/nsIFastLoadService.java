/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFastLoadFileIO;
import org.mozilla.interfaces.nsIFastLoadReadControl;
import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIObjectInputStream;
import org.mozilla.interfaces.nsIObjectOutputStream;
import org.mozilla.interfaces.nsIOutputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsIFastLoadService
extends nsISupports {
    public static final String NS_IFASTLOADSERVICE_IID = "{759e475e-0c23-4dbf-b1b8-78c9369e3072}";
    public static final int NS_FASTLOAD_READ = 1;
    public static final int NS_FASTLOAD_WRITE = 2;

    public nsIFile newFastLoadFile(String var1);

    public nsIObjectInputStream newInputStream(nsIInputStream var1);

    public nsIObjectOutputStream newOutputStream(nsIOutputStream var1);

    public nsIObjectInputStream getInputStream();

    public void setInputStream(nsIObjectInputStream var1);

    public nsIObjectOutputStream getOutputStream();

    public void setOutputStream(nsIObjectOutputStream var1);

    public nsIFastLoadFileIO getFileIO();

    public void setFileIO(nsIFastLoadFileIO var1);

    public int getDirection();

    public void startMuxedDocument(nsISupports var1, String var2, int var3);

    public nsISupports selectMuxedDocument(nsISupports var1);

    public void endMuxedDocument(nsISupports var1);

    public void addDependency(nsIFile var1);

    public long computeChecksum(nsIFile var1, nsIFastLoadReadControl var2);

    public void cacheChecksum(nsIFile var1, nsIObjectOutputStream var2);

    public boolean hasMuxedDocument(String var1);
}

