/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMRange;
import org.mozilla.interfaces.nsIEditorSpellCheck;
import org.mozilla.interfaces.nsISelection;
import org.mozilla.interfaces.nsISupports;

public interface nsIInlineSpellChecker
extends nsISupports {
    public static final String NS_IINLINESPELLCHECKER_IID = "{f5d1ec9e-4d30-11d8-8053-da0cc7df1f20}";

    public nsIEditorSpellCheck getSpellChecker();

    public boolean getEnableRealTimeSpell();

    public void setEnableRealTimeSpell(boolean var1);

    public void spellCheckAfterEditorChange(int var1, nsISelection var2, nsIDOMNode var3, int var4, nsIDOMNode var5, int var6, nsIDOMNode var7, int var8);

    public void spellCheckRange(nsIDOMRange var1);

    public nsIDOMRange getMispelledWord(nsIDOMNode var1, int var2);

    public void replaceWord(nsIDOMNode var1, int var2, String var3);

    public void addWordToDictionary(String var1);

    public void ignoreWord(String var1);

    public void ignoreWords(String[] var1, long var2);
}

