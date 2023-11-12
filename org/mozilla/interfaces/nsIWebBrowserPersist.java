/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICancelable;
import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;
import org.mozilla.interfaces.nsIWebProgressListener;

public interface nsIWebBrowserPersist
extends nsICancelable {
    public static final String NS_IWEBBROWSERPERSIST_IID = "{dd4e0a6a-210f-419a-ad85-40e8543b9465}";
    public static final long PERSIST_FLAGS_NONE = 0L;
    public static final long PERSIST_FLAGS_FROM_CACHE = 1L;
    public static final long PERSIST_FLAGS_BYPASS_CACHE = 2L;
    public static final long PERSIST_FLAGS_IGNORE_REDIRECTED_DATA = 4L;
    public static final long PERSIST_FLAGS_IGNORE_IFRAMES = 8L;
    public static final long PERSIST_FLAGS_NO_CONVERSION = 16L;
    public static final long PERSIST_FLAGS_REPLACE_EXISTING_FILES = 32L;
    public static final long PERSIST_FLAGS_NO_BASE_TAG_MODIFICATIONS = 64L;
    public static final long PERSIST_FLAGS_FIXUP_ORIGINAL_DOM = 128L;
    public static final long PERSIST_FLAGS_FIXUP_LINKS_TO_DESTINATION = 256L;
    public static final long PERSIST_FLAGS_DONT_FIXUP_LINKS = 512L;
    public static final long PERSIST_FLAGS_SERIALIZE_OUTPUT = 1024L;
    public static final long PERSIST_FLAGS_DONT_CHANGE_FILENAMES = 2048L;
    public static final long PERSIST_FLAGS_FAIL_ON_BROKEN_LINKS = 4096L;
    public static final long PERSIST_FLAGS_CLEANUP_ON_FAILURE = 8192L;
    public static final long PERSIST_FLAGS_AUTODETECT_APPLY_CONVERSION = 16384L;
    public static final long PERSIST_STATE_READY = 1L;
    public static final long PERSIST_STATE_SAVING = 2L;
    public static final long PERSIST_STATE_FINISHED = 3L;
    public static final long ENCODE_FLAGS_SELECTION_ONLY = 1L;
    public static final long ENCODE_FLAGS_FORMATTED = 2L;
    public static final long ENCODE_FLAGS_RAW = 4L;
    public static final long ENCODE_FLAGS_BODY_ONLY = 8L;
    public static final long ENCODE_FLAGS_PREFORMATTED = 16L;
    public static final long ENCODE_FLAGS_WRAP = 32L;
    public static final long ENCODE_FLAGS_FORMAT_FLOWED = 64L;
    public static final long ENCODE_FLAGS_ABSOLUTE_LINKS = 128L;
    public static final long ENCODE_FLAGS_ENCODE_W3C_ENTITIES = 256L;
    public static final long ENCODE_FLAGS_CR_LINEBREAKS = 512L;
    public static final long ENCODE_FLAGS_LF_LINEBREAKS = 1024L;
    public static final long ENCODE_FLAGS_NOSCRIPT_CONTENT = 2048L;
    public static final long ENCODE_FLAGS_NOFRAMES_CONTENT = 4096L;
    public static final long ENCODE_FLAGS_ENCODE_BASIC_ENTITIES = 8192L;
    public static final long ENCODE_FLAGS_ENCODE_LATIN1_ENTITIES = 16384L;
    public static final long ENCODE_FLAGS_ENCODE_HTML_ENTITIES = 32768L;

    public long getPersistFlags();

    public void setPersistFlags(long var1);

    public long getCurrentState();

    public long getResult();

    public nsIWebProgressListener getProgressListener();

    public void setProgressListener(nsIWebProgressListener var1);

    public void saveURI(nsIURI var1, nsISupports var2, nsIURI var3, nsIInputStream var4, String var5, nsISupports var6);

    public void saveChannel(nsIChannel var1, nsISupports var2);

    public void saveDocument(nsIDOMDocument var1, nsISupports var2, nsISupports var3, String var4, long var5, long var7);

    public void cancelSave();
}

