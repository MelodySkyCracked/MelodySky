/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCharacterData;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISelection;
import org.mozilla.interfaces.nsISupports;

public interface nsIEditActionListener
extends nsISupports {
    public static final String NS_IEDITACTIONLISTENER_IID = "{b22907b1-ee93-11d2-8d50-000064657374}";

    public void willCreateNode(String var1, nsIDOMNode var2, int var3);

    public void didCreateNode(String var1, nsIDOMNode var2, nsIDOMNode var3, int var4, long var5);

    public void willInsertNode(nsIDOMNode var1, nsIDOMNode var2, int var3);

    public void didInsertNode(nsIDOMNode var1, nsIDOMNode var2, int var3, long var4);

    public void willDeleteNode(nsIDOMNode var1);

    public void didDeleteNode(nsIDOMNode var1, long var2);

    public void willSplitNode(nsIDOMNode var1, int var2);

    public void didSplitNode(nsIDOMNode var1, int var2, nsIDOMNode var3, long var4);

    public void willJoinNodes(nsIDOMNode var1, nsIDOMNode var2, nsIDOMNode var3);

    public void didJoinNodes(nsIDOMNode var1, nsIDOMNode var2, nsIDOMNode var3, long var4);

    public void willInsertText(nsIDOMCharacterData var1, int var2, String var3);

    public void didInsertText(nsIDOMCharacterData var1, int var2, String var3, long var4);

    public void willDeleteText(nsIDOMCharacterData var1, int var2, int var3);

    public void didDeleteText(nsIDOMCharacterData var1, int var2, int var3, long var4);

    public void willDeleteSelection(nsISelection var1);

    public void didDeleteSelection(nsISelection var1);
}

