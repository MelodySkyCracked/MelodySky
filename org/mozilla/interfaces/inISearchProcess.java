/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.inISearchObserver;
import org.mozilla.interfaces.nsISupports;

public interface inISearchProcess
extends nsISupports {
    public static final String INISEARCHPROCESS_IID = "{d5fa765b-2448-4686-b7c1-5ff13acb0fc9}";

    public boolean getIsActive();

    public int getResultCount();

    public boolean getHoldResults();

    public void setHoldResults(boolean var1);

    public void searchSync();

    public void searchAsync(inISearchObserver var1);

    public void searchStop();

    public boolean searchStep();

    public String getStringResultAt(int var1);

    public int getIntResultAt(int var1);

    public long getUIntResultAt(int var1);
}

