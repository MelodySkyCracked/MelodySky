/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIDownload;
import org.mozilla.interfaces.nsIObserver;

public interface nsIProgressDialog
extends nsIDownload {
    public static final String NS_IPROGRESSDIALOG_IID = "{20e790a2-76c6-462d-851a-22ab6cbbe48b}";

    public void open(nsIDOMWindow var1);

    public boolean getCancelDownloadOnClose();

    public void setCancelDownloadOnClose(boolean var1);

    public nsIObserver getObserver();

    public void setObserver(nsIObserver var1);

    public nsIDOMWindow getDialog();

    public void setDialog(nsIDOMWindow var1);
}

