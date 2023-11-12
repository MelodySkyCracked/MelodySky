/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITreeColumns;

public interface nsITreeColumn
extends nsISupports {
    public static final String NS_ITREECOLUMN_IID = "{58a8574d-15a8-4678-99a5-e1be56104093}";
    public static final short TYPE_TEXT = 1;
    public static final short TYPE_CHECKBOX = 2;
    public static final short TYPE_PROGRESSMETER = 3;

    public nsIDOMElement getElement();

    public nsITreeColumns getColumns();

    public int getX();

    public int getWidth();

    public String getId();

    public int getIndex();

    public boolean getPrimary();

    public boolean getCycler();

    public boolean getEditable();

    public short getType();

    public nsITreeColumn getNext();

    public nsITreeColumn getPrevious();

    public void invalidate();
}

