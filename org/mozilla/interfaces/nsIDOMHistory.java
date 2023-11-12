/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMHistory
extends nsISupports {
    public static final String NS_IDOMHISTORY_IID = "{896d1d20-b4c4-11d2-bd93-00805f8ae3f4}";

    public int getLength();

    public String getCurrent();

    public String getPrevious();

    public String getNext();

    public void back();

    public void forward();

    public String item(long var1);
}

