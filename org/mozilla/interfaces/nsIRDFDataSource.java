/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRDFNode;
import org.mozilla.interfaces.nsIRDFObserver;
import org.mozilla.interfaces.nsIRDFResource;
import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsISupportsArray;

public interface nsIRDFDataSource
extends nsISupports {
    public static final String NS_IRDFDATASOURCE_IID = "{0f78da58-8321-11d2-8eac-00805f29f370}";

    public String getURI();

    public nsIRDFResource getSource(nsIRDFResource var1, nsIRDFNode var2, boolean var3);

    public nsISimpleEnumerator getSources(nsIRDFResource var1, nsIRDFNode var2, boolean var3);

    public nsIRDFNode getTarget(nsIRDFResource var1, nsIRDFResource var2, boolean var3);

    public nsISimpleEnumerator getTargets(nsIRDFResource var1, nsIRDFResource var2, boolean var3);

    public void _assert(nsIRDFResource var1, nsIRDFResource var2, nsIRDFNode var3, boolean var4);

    public void unassert(nsIRDFResource var1, nsIRDFResource var2, nsIRDFNode var3);

    public void change(nsIRDFResource var1, nsIRDFResource var2, nsIRDFNode var3, nsIRDFNode var4);

    public void move(nsIRDFResource var1, nsIRDFResource var2, nsIRDFResource var3, nsIRDFNode var4);

    public boolean hasAssertion(nsIRDFResource var1, nsIRDFResource var2, nsIRDFNode var3, boolean var4);

    public void addObserver(nsIRDFObserver var1);

    public void removeObserver(nsIRDFObserver var1);

    public nsISimpleEnumerator arcLabelsIn(nsIRDFNode var1);

    public nsISimpleEnumerator arcLabelsOut(nsIRDFResource var1);

    public nsISimpleEnumerator getAllResources();

    public boolean isCommandEnabled(nsISupportsArray var1, nsIRDFResource var2, nsISupportsArray var3);

    public void doCommand(nsISupportsArray var1, nsIRDFResource var2, nsISupportsArray var3);

    public nsISimpleEnumerator getAllCmds(nsIRDFResource var1);

    public boolean hasArcIn(nsIRDFNode var1, nsIRDFResource var2);

    public boolean hasArcOut(nsIRDFResource var1, nsIRDFResource var2);

    public void beginUpdateBatch();

    public void endUpdateBatch();
}

