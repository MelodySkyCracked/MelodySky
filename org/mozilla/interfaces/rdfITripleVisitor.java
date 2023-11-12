/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRDFNode;
import org.mozilla.interfaces.nsIRDFResource;
import org.mozilla.interfaces.nsISupports;

public interface rdfITripleVisitor
extends nsISupports {
    public static final String RDFITRIPLEVISITOR_IID = "{aafea151-c271-4505-9978-a100d292800c}";

    public void visit(nsIRDFNode var1, nsIRDFResource var2, nsIRDFNode var3, boolean var4);
}

