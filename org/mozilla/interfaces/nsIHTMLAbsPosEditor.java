/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;

public interface nsIHTMLAbsPosEditor
extends nsISupports {
    public static final String NS_IHTMLABSPOSEDITOR_IID = "{91375f52-20e6-4757-9835-eb04fabe5498}";

    public boolean getSelectionContainerAbsolutelyPositioned();

    public nsIDOMElement getPositionedElement();

    public boolean getAbsolutePositioningEnabled();

    public void setAbsolutePositioningEnabled(boolean var1);

    public boolean getSnapToGridEnabled();

    public void setSnapToGridEnabled(boolean var1);

    public long getGridSize();

    public void setGridSize(long var1);

    public nsIDOMElement getAbsolutelyPositionedSelectionContainer();

    public void absolutePositionSelection(boolean var1);

    public void relativeChangeZIndex(int var1);

    public void absolutelyPositionElement(nsIDOMElement var1, boolean var2);

    public void setElementPosition(nsIDOMElement var1, int var2, int var3);

    public int getElementZIndex(nsIDOMElement var1);

    public void setElementZIndex(nsIDOMElement var1, int var2);

    public int relativeChangeElementZIndex(nsIDOMElement var1, int var2);

    public void showGrabberOnElement(nsIDOMElement var1);

    public void hideGrabber();

    public void refreshGrabber();
}

