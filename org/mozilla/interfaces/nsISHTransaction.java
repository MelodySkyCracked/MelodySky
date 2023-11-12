/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISHEntry;
import org.mozilla.interfaces.nsISupports;

public interface nsISHTransaction
extends nsISupports {
    public static final String NS_ISHTRANSACTION_IID = "{2edf705f-d252-4971-9f09-71dd0f760dc6}";

    public nsISHEntry getSHEntry();

    public void setSHEntry(nsISHEntry var1);

    public nsISHTransaction getPrev();

    public void setPrev(nsISHTransaction var1);

    public nsISHTransaction getNext();

    public void setNext(nsISHTransaction var1);

    public boolean getPersist();

    public void setPersist(boolean var1);

    public void create(nsISHEntry var1, nsISHTransaction var2);
}

