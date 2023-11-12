/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMXPathResult
extends nsISupports {
    public static final String NS_IDOMXPATHRESULT_IID = "{75506f84-b504-11d5-a7f2-ca108ab8b6fc}";
    public static final int ANY_TYPE = 0;
    public static final int NUMBER_TYPE = 1;
    public static final int STRING_TYPE = 2;
    public static final int BOOLEAN_TYPE = 3;
    public static final int UNORDERED_NODE_ITERATOR_TYPE = 4;
    public static final int ORDERED_NODE_ITERATOR_TYPE = 5;
    public static final int UNORDERED_NODE_SNAPSHOT_TYPE = 6;
    public static final int ORDERED_NODE_SNAPSHOT_TYPE = 7;
    public static final int ANY_UNORDERED_NODE_TYPE = 8;
    public static final int FIRST_ORDERED_NODE_TYPE = 9;

    public int getResultType();

    public double getNumberValue();

    public String getStringValue();

    public boolean getBooleanValue();

    public nsIDOMNode getSingleNodeValue();

    public boolean getInvalidIteratorState();

    public long getSnapshotLength();

    public nsIDOMNode iterateNext();

    public nsIDOMNode snapshotItem(long var1);
}

