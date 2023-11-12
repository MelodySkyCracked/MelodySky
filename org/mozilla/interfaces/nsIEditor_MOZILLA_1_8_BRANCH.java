/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEditor;
import org.mozilla.interfaces.nsIInlineSpellChecker;

public interface nsIEditor_MOZILLA_1_8_BRANCH
extends nsIEditor {
    public static final String NS_IEDITOR_MOZILLA_1_8_BRANCH_IID = "{60fbf998-e021-4f81-bdf0-749cc651e221}";

    public nsIInlineSpellChecker getInlineSpellCheckerOptionally(boolean var1);

    public void syncRealTimeSpell();

    public void setSpellcheckUserOverride(boolean var1);
}

