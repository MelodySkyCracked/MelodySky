/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRDFContainer;
import org.mozilla.interfaces.nsIRDFDataSource;
import org.mozilla.interfaces.nsIRDFNode;
import org.mozilla.interfaces.nsIRDFResource;
import org.mozilla.interfaces.nsISupports;

public interface nsIRDFContainerUtils
extends nsISupports {
    public static final String NS_IRDFCONTAINERUTILS_IID = "{d4214e91-fb94-11d2-bdd8-00104bde6048}";

    public boolean isOrdinalProperty(nsIRDFResource var1);

    public nsIRDFResource indexToOrdinalResource(int var1);

    public int ordinalResourceToIndex(nsIRDFResource var1);

    public boolean isContainer(nsIRDFDataSource var1, nsIRDFResource var2);

    public boolean isEmpty(nsIRDFDataSource var1, nsIRDFResource var2);

    public boolean isBag(nsIRDFDataSource var1, nsIRDFResource var2);

    public boolean isSeq(nsIRDFDataSource var1, nsIRDFResource var2);

    public boolean isAlt(nsIRDFDataSource var1, nsIRDFResource var2);

    public nsIRDFContainer makeBag(nsIRDFDataSource var1, nsIRDFResource var2);

    public nsIRDFContainer makeSeq(nsIRDFDataSource var1, nsIRDFResource var2);

    public nsIRDFContainer makeAlt(nsIRDFDataSource var1, nsIRDFResource var2);

    public int indexOf(nsIRDFDataSource var1, nsIRDFResource var2, nsIRDFNode var3);
}

