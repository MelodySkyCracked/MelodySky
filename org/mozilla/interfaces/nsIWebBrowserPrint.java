/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIPrintSettings;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWebProgressListener;

public interface nsIWebBrowserPrint
extends nsISupports {
    public static final String NS_IWEBBROWSERPRINT_IID = "{9a7ca4b0-fbba-11d4-a869-00105a183419}";
    public static final short PRINTPREVIEW_GOTO_PAGENUM = 0;
    public static final short PRINTPREVIEW_PREV_PAGE = 1;
    public static final short PRINTPREVIEW_NEXT_PAGE = 2;
    public static final short PRINTPREVIEW_HOME = 3;
    public static final short PRINTPREVIEW_END = 4;

    public nsIPrintSettings getGlobalPrintSettings();

    public nsIPrintSettings getCurrentPrintSettings();

    public nsIDOMWindow getCurrentChildDOMWindow();

    public boolean getDoingPrint();

    public boolean getDoingPrintPreview();

    public boolean getIsFramesetDocument();

    public boolean getIsFramesetFrameSelected();

    public boolean getIsIFrameSelected();

    public boolean getIsRangeSelection();

    public int getPrintPreviewNumPages();

    public void print(nsIPrintSettings var1, nsIWebProgressListener var2);

    public void printPreview(nsIPrintSettings var1, nsIDOMWindow var2, nsIWebProgressListener var3);

    public void printPreviewNavigate(short var1, int var2);

    public void cancel();

    public String[] enumerateDocumentNames(long[] var1);

    public void exitPrintPreview();
}

