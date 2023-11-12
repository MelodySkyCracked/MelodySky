/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIRDFCompositeDataSource;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIXULBuilderListener;

public interface nsIXULTemplateBuilder
extends nsISupports {
    public static final String NS_IXULTEMPLATEBUILDER_IID = "{9da147a7-5854-49e3-a397-22ecdd93e96d}";

    public nsIDOMElement getRoot();

    public nsIRDFCompositeDataSource getDatabase();

    public void rebuild();

    public void refresh();

    public void addListener(nsIXULBuilderListener var1);

    public void removeListener(nsIXULBuilderListener var1);
}

