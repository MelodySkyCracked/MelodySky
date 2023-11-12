/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIRDFResource;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIXULTreeBuilderObserver;

public interface nsIXULTreeBuilder
extends nsISupports {
    public static final String NS_IXULTREEBUILDER_IID = "{06b31b15-ebf5-4e74-a0e2-6bc0a18a3969}";

    public nsIRDFResource getResourceAtIndex(int var1);

    public int getIndexOfResource(nsIRDFResource var1);

    public void addObserver(nsIXULTreeBuilderObserver var1);

    public void removeObserver(nsIXULTreeBuilderObserver var1);

    public void sort(nsIDOMElement var1);
}

