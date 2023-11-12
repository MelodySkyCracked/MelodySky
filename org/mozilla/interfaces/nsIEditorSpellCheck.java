/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEditor;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITextServicesFilter;

public interface nsIEditorSpellCheck
extends nsISupports {
    public static final String NS_IEDITORSPELLCHECK_IID = "{6088a862-1229-11d9-941d-c035b2e390c6}";

    public void initSpellChecker(nsIEditor var1, boolean var2);

    public String getNextMisspelledWord();

    public String getSuggestedWord();

    public boolean checkCurrentWord(String var1);

    public void replaceWord(String var1, String var2, boolean var3);

    public void ignoreWordAllOccurrences(String var1);

    public void getPersonalDictionary();

    public String getPersonalDictionaryWord();

    public void addWordToDictionary(String var1);

    public void removeWordFromDictionary(String var1);

    public void getDictionaryList(String[][] var1, long[] var2);

    public String getCurrentDictionary();

    public void setCurrentDictionary(String var1);

    public void uninitSpellChecker();

    public void setFilter(nsITextServicesFilter var1);

    public boolean checkCurrentWordNoSuggest(String var1);
}

