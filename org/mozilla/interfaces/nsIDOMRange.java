/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocumentFragment;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMRange
extends nsISupports {
    public static final String NS_IDOMRANGE_IID = "{a6cf90ce-15b3-11d2-932e-00805f8add32}";
    public static final int START_TO_START = 0;
    public static final int START_TO_END = 1;
    public static final int END_TO_END = 2;
    public static final int END_TO_START = 3;

    public nsIDOMNode getStartContainer();

    public int getStartOffset();

    public nsIDOMNode getEndContainer();

    public int getEndOffset();

    public boolean getCollapsed();

    public nsIDOMNode getCommonAncestorContainer();

    public void setStart(nsIDOMNode var1, int var2);

    public void setEnd(nsIDOMNode var1, int var2);

    public void setStartBefore(nsIDOMNode var1);

    public void setStartAfter(nsIDOMNode var1);

    public void setEndBefore(nsIDOMNode var1);

    public void setEndAfter(nsIDOMNode var1);

    public void collapse(boolean var1);

    public void selectNode(nsIDOMNode var1);

    public void selectNodeContents(nsIDOMNode var1);

    public short compareBoundaryPoints(int var1, nsIDOMRange var2);

    public void deleteContents();

    public nsIDOMDocumentFragment extractContents();

    public nsIDOMDocumentFragment cloneContents();

    public void insertNode(nsIDOMNode var1);

    public void surroundContents(nsIDOMNode var1);

    public nsIDOMRange cloneRange();

    public String toString();

    public void detach();
}

