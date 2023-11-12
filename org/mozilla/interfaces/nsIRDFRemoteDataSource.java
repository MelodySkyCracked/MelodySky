/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIRDFRemoteDataSource
extends nsISupports {
    public static final String NS_IRDFREMOTEDATASOURCE_IID = "{1d297320-27f7-11d3-be01-000064657374}";

    public boolean getLoaded();

    public void init(String var1);

    public void refresh(boolean var1);

    public void flush();

    public void flushTo(String var1);
}

