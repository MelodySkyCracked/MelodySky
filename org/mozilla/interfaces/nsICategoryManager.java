/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface nsICategoryManager
extends nsISupports {
    public static final String NS_ICATEGORYMANAGER_IID = "{3275b2cd-af6d-429a-80d7-f0c5120342ac}";

    public String getCategoryEntry(String var1, String var2);

    public String addCategoryEntry(String var1, String var2, String var3, boolean var4, boolean var5);

    public void deleteCategoryEntry(String var1, String var2, boolean var3);

    public void deleteCategory(String var1);

    public nsISimpleEnumerator enumerateCategory(String var1);

    public nsISimpleEnumerator enumerateCategories();
}

