/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMRange;
import org.mozilla.interfaces.nsISupports;

public interface nsIFind
extends nsISupports {
    public static final String NS_IFIND_IID = "{75125d55-37ee-4575-b9b5-f33bfa68c2a1}";

    public boolean getFindBackwards();

    public void setFindBackwards(boolean var1);

    public boolean getCaseSensitive();

    public void setCaseSensitive(boolean var1);

    public nsISupports getWordBreaker();

    public void setWordBreaker(nsISupports var1);

    public nsIDOMRange find(String var1, nsIDOMRange var2, nsIDOMRange var3, nsIDOMRange var4);
}

