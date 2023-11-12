/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRDFDataSource;
import org.mozilla.interfaces.nsIRDFNode;
import org.mozilla.interfaces.nsIRDFResource;
import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface nsIRDFContainer
extends nsISupports {
    public static final String NS_IRDFCONTAINER_IID = "{d4214e90-fb94-11d2-bdd8-00104bde6048}";

    public nsIRDFDataSource getDataSource();

    public nsIRDFResource getResource();

    public void init(nsIRDFDataSource var1, nsIRDFResource var2);

    public int getCount();

    public nsISimpleEnumerator getElements();

    public void appendElement(nsIRDFNode var1);

    public void removeElement(nsIRDFNode var1, boolean var2);

    public void insertElementAt(nsIRDFNode var1, int var2, boolean var3);

    public nsIRDFNode removeElementAt(int var1, boolean var2);

    public int indexOf(nsIRDFNode var1);
}

