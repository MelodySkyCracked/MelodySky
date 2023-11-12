/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIArray;
import org.mozilla.interfaces.nsIDOMCSSStyleRule;
import org.mozilla.interfaces.nsIDOMCharacterData;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsISupportsArray;

public interface inIDOMUtils
extends nsISupports {
    public static final String INIDOMUTILS_IID = "{78fd16c2-bdfb-4b1d-8738-d536d0a8f430}";

    public nsISupportsArray getCSSStyleRules(nsIDOMElement var1);

    public long getRuleLine(nsIDOMCSSStyleRule var1);

    public boolean isIgnorableWhitespace(nsIDOMCharacterData var1);

    public nsIDOMNode getParentForNode(nsIDOMNode var1, boolean var2);

    public nsIArray getBindingURLs(nsIDOMElement var1);

    public int getContentState(nsIDOMElement var1);

    public void setContentState(nsIDOMElement var1, int var2);
}

