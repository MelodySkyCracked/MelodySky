/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRDFDataSource;
import org.mozilla.interfaces.nsIRDFDate;
import org.mozilla.interfaces.nsIRDFInt;
import org.mozilla.interfaces.nsIRDFLiteral;
import org.mozilla.interfaces.nsIRDFResource;
import org.mozilla.interfaces.nsISupports;

public interface nsIRDFService
extends nsISupports {
    public static final String NS_IRDFSERVICE_IID = "{bfd05261-834c-11d2-8eac-00805f29f370}";

    public nsIRDFResource getResource(String var1);

    public nsIRDFResource getUnicodeResource(String var1);

    public nsIRDFResource getAnonymousResource();

    public nsIRDFLiteral getLiteral(String var1);

    public nsIRDFDate getDateLiteral(long var1);

    public nsIRDFInt getIntLiteral(int var1);

    public boolean isAnonymousResource(nsIRDFResource var1);

    public void registerResource(nsIRDFResource var1, boolean var2);

    public void unregisterResource(nsIRDFResource var1);

    public void registerDataSource(nsIRDFDataSource var1, boolean var2);

    public void unregisterDataSource(nsIRDFDataSource var1);

    public nsIRDFDataSource getDataSource(String var1);

    public nsIRDFDataSource getDataSourceBlocking(String var1);
}

