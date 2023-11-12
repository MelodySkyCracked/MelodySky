/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMSVGSVGElement;

public interface nsIDOMSVGDocument
extends nsIDOMDocument {
    public static final String NS_IDOMSVGDOCUMENT_IID = "{12d3b664-1dd2-11b2-a7cf-ceee7e90f396}";

    public String getTitle();

    public String getReferrer();

    public String getDomain();

    public String getURL();

    public nsIDOMSVGSVGElement getRootElement();
}

