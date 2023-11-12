/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.rdfITripleVisitor;

public interface rdfIDataSource
extends nsISupports {
    public static final String RDFIDATASOURCE_IID = "{ebce86bd-1568-4a34-a808-9ccf9cde8087}";

    public void visitAllSubjects(rdfITripleVisitor var1);

    public void visitAllTriples(rdfITripleVisitor var1);
}

