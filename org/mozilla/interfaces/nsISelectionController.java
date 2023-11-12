/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISelection;
import org.mozilla.interfaces.nsISelectionDisplay;

public interface nsISelectionController
extends nsISelectionDisplay {
    public static final String NS_ISELECTIONCONTROLLER_IID = "{93aaa4a9-b78e-42eb-9d67-5de77ee2f54b}";
    public static final short SELECTION_NONE = 0;
    public static final short SELECTION_NORMAL = 1;
    public static final short SELECTION_SPELLCHECK = 2;
    public static final short SELECTION_IME_RAWINPUT = 4;
    public static final short SELECTION_IME_SELECTEDRAWTEXT = 8;
    public static final short SELECTION_IME_CONVERTEDTEXT = 16;
    public static final short SELECTION_IME_SELECTEDCONVERTEDTEXT = 32;
    public static final short SELECTION_ACCESSIBILITY = 64;
    public static final short NUM_SELECTIONTYPES = 8;
    public static final short SELECTION_ANCHOR_REGION = 0;
    public static final short SELECTION_FOCUS_REGION = 1;
    public static final short NUM_SELECTION_REGIONS = 2;
    public static final short SELECTION_OFF = 0;
    public static final short SELECTION_HIDDEN = 1;
    public static final short SELECTION_ON = 2;
    public static final short SELECTION_DISABLED = 3;
    public static final short SELECTION_ATTENTION = 4;

    public void setDisplaySelection(short var1);

    public short getDisplaySelection();

    public nsISelection getSelection(short var1);

    public void scrollSelectionIntoView(short var1, short var2, boolean var3);

    public void repaintSelection(short var1);

    public void setCaretEnabled(boolean var1);

    public void setCaretReadOnly(boolean var1);

    public boolean getCaretEnabled();

    public void setCaretVisibilityDuringSelection(boolean var1);

    public void characterMove(boolean var1, boolean var2);

    public void wordMove(boolean var1, boolean var2);

    public void lineMove(boolean var1, boolean var2);

    public void intraLineMove(boolean var1, boolean var2);

    public void pageMove(boolean var1, boolean var2);

    public void completeScroll(boolean var1);

    public void completeMove(boolean var1, boolean var2);

    public void scrollPage(boolean var1);

    public void scrollLine(boolean var1);

    public void scrollHorizontal(boolean var1);

    public void selectAll();

    public boolean checkVisibility(nsIDOMNode var1, short var2, short var3);
}

