/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.accessibility;

import org.eclipse.swt.accessibility.AccessibleTextEvent;
import org.eclipse.swt.accessibility.AccessibleTextListener;

public interface AccessibleTextExtendedListener
extends AccessibleTextListener {
    public void addSelection(AccessibleTextEvent var1);

    public void getCharacterCount(AccessibleTextEvent var1);

    public void getHyperlinkCount(AccessibleTextEvent var1);

    public void getHyperlink(AccessibleTextEvent var1);

    public void getHyperlinkIndex(AccessibleTextEvent var1);

    public void getOffsetAtPoint(AccessibleTextEvent var1);

    public void getRanges(AccessibleTextEvent var1);

    public void getSelection(AccessibleTextEvent var1);

    public void getSelectionCount(AccessibleTextEvent var1);

    public void getText(AccessibleTextEvent var1);

    public void getTextBounds(AccessibleTextEvent var1);

    public void getVisibleRanges(AccessibleTextEvent var1);

    public void removeSelection(AccessibleTextEvent var1);

    public void scrollText(AccessibleTextEvent var1);

    public void setCaretOffset(AccessibleTextEvent var1);

    public void setSelection(AccessibleTextEvent var1);
}

