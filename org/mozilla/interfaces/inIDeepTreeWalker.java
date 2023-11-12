/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMTreeWalker;

public interface inIDeepTreeWalker
extends nsIDOMTreeWalker {
    public static final String INIDEEPTREEWALKER_IID = "{91fca0e9-99d6-406b-9d78-4c96f11e9ee4}";

    public boolean getShowAnonymousContent();

    public void setShowAnonymousContent(boolean var1);

    public boolean getShowSubDocuments();

    public void setShowSubDocuments(boolean var1);

    public void init(nsIDOMNode var1, long var2);
}

