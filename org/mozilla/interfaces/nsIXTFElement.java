/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAtom;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIXTFElement
extends nsISupports {
    public static final String NS_IXTFELEMENT_IID = "{a8b607fd-24b6-4a8c-9a89-d9b24f8e2592}";
    public static final long ELEMENT_TYPE_GENERIC_ELEMENT = 0L;
    public static final long ELEMENT_TYPE_SVG_VISUAL = 1L;
    public static final long ELEMENT_TYPE_XML_VISUAL = 2L;
    public static final long ELEMENT_TYPE_XUL_VISUAL = 3L;
    public static final long ELEMENT_TYPE_BINDABLE = 4L;
    public static final long NOTIFY_WILL_CHANGE_DOCUMENT = 1L;
    public static final long NOTIFY_DOCUMENT_CHANGED = 2L;
    public static final long NOTIFY_WILL_CHANGE_PARENT = 4L;
    public static final long NOTIFY_PARENT_CHANGED = 8L;
    public static final long NOTIFY_WILL_INSERT_CHILD = 16L;
    public static final long NOTIFY_CHILD_INSERTED = 32L;
    public static final long NOTIFY_WILL_APPEND_CHILD = 64L;
    public static final long NOTIFY_CHILD_APPENDED = 128L;
    public static final long NOTIFY_WILL_REMOVE_CHILD = 256L;
    public static final long NOTIFY_CHILD_REMOVED = 512L;
    public static final long NOTIFY_WILL_SET_ATTRIBUTE = 1024L;
    public static final long NOTIFY_ATTRIBUTE_SET = 2048L;
    public static final long NOTIFY_WILL_REMOVE_ATTRIBUTE = 4096L;
    public static final long NOTIFY_ATTRIBUTE_REMOVED = 8192L;
    public static final long NOTIFY_BEGIN_ADDING_CHILDREN = 16384L;
    public static final long NOTIFY_DONE_ADDING_CHILDREN = 32768L;
    public static final long NOTIFY_HANDLE_DEFAULT = 65536L;

    public void onDestroyed();

    public long getElementType();

    public boolean getIsAttributeHandler();

    public String[] getScriptingInterfaces(long[] var1);

    public void willChangeDocument(nsIDOMDocument var1);

    public void documentChanged(nsIDOMDocument var1);

    public void willChangeParent(nsIDOMElement var1);

    public void parentChanged(nsIDOMElement var1);

    public void willInsertChild(nsIDOMNode var1, long var2);

    public void childInserted(nsIDOMNode var1, long var2);

    public void willAppendChild(nsIDOMNode var1);

    public void childAppended(nsIDOMNode var1);

    public void willRemoveChild(long var1);

    public void childRemoved(long var1);

    public void willSetAttribute(nsIAtom var1, String var2);

    public void attributeSet(nsIAtom var1, String var2);

    public void willRemoveAttribute(nsIAtom var1);

    public void attributeRemoved(nsIAtom var1);

    public void beginAddingChildren();

    public void doneAddingChildren();

    public boolean handleDefault(nsIDOMEvent var1);

    public void cloneState(nsIDOMElement var1);
}

