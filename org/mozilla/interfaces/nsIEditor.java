/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAtom;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDocumentStateListener;
import org.mozilla.interfaces.nsIEditActionListener;
import org.mozilla.interfaces.nsIEditorObserver;
import org.mozilla.interfaces.nsIInlineSpellChecker;
import org.mozilla.interfaces.nsIOutputStream;
import org.mozilla.interfaces.nsISelection;
import org.mozilla.interfaces.nsISelectionController;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITransaction;
import org.mozilla.interfaces.nsITransactionManager;

public interface nsIEditor
extends nsISupports {
    public static final String NS_IEDITOR_IID = "{d4882ffb-e927-408b-96be-d4391b456fa9}";
    public static final short eNone = 0;
    public static final short eNext = 1;
    public static final short ePrevious = 2;
    public static final short eNextWord = 3;
    public static final short ePreviousWord = 4;
    public static final short eToBeginningOfLine = 5;
    public static final short eToEndOfLine = 6;

    public nsISelection getSelection();

    public void setAttributeOrEquivalent(nsIDOMElement var1, String var2, String var3, boolean var4);

    public void removeAttributeOrEquivalent(nsIDOMElement var1, String var2, boolean var3);

    public void postCreate();

    public void preDestroy();

    public long getFlags();

    public void setFlags(long var1);

    public String getContentsMIMEType();

    public void setContentsMIMEType(String var1);

    public boolean getIsDocumentEditable();

    public nsIDOMDocument getDocument();

    public nsIDOMElement getRootElement();

    public nsISelectionController getSelectionController();

    public void deleteSelection(short var1);

    public boolean getDocumentIsEmpty();

    public boolean getDocumentModified();

    public String getDocumentCharacterSet();

    public void setDocumentCharacterSet(String var1);

    public void resetModificationCount();

    public int getModificationCount();

    public void incrementModificationCount(int var1);

    public nsITransactionManager getTransactionManager();

    public void doTransaction(nsITransaction var1);

    public void enableUndo(boolean var1);

    public void undo(long var1);

    public void canUndo(boolean[] var1, boolean[] var2);

    public void redo(long var1);

    public void canRedo(boolean[] var1, boolean[] var2);

    public void beginTransaction();

    public void endTransaction();

    public void beginPlaceHolderTransaction(nsIAtom var1);

    public void endPlaceHolderTransaction();

    public boolean shouldTxnSetSelection();

    public void setShouldTxnSetSelection(boolean var1);

    public nsIInlineSpellChecker getInlineSpellChecker();

    public void cut();

    public boolean canCut();

    public void copy();

    public boolean canCopy();

    public void paste(int var1);

    public boolean canPaste(int var1);

    public void selectAll();

    public void beginningOfDocument();

    public void endOfDocument();

    public boolean canDrag(nsIDOMEvent var1);

    public void doDrag(nsIDOMEvent var1);

    public void insertFromDrop(nsIDOMEvent var1);

    public void setAttribute(nsIDOMElement var1, String var2, String var3);

    public boolean getAttributeValue(nsIDOMElement var1, String var2, String[] var3);

    public void removeAttribute(nsIDOMElement var1, String var2);

    public void cloneAttribute(String var1, nsIDOMNode var2, nsIDOMNode var3);

    public void cloneAttributes(nsIDOMNode var1, nsIDOMNode var2);

    public nsIDOMNode createNode(String var1, nsIDOMNode var2, int var3);

    public void insertNode(nsIDOMNode var1, nsIDOMNode var2, int var3);

    public void splitNode(nsIDOMNode var1, int var2, nsIDOMNode[] var3);

    public void joinNodes(nsIDOMNode var1, nsIDOMNode var2, nsIDOMNode var3);

    public void deleteNode(nsIDOMNode var1);

    public void markNodeDirty(nsIDOMNode var1);

    public void switchTextDirection();

    public String outputToString(String var1, long var2);

    public void outputToStream(nsIOutputStream var1, String var2, String var3, long var4);

    public void addEditorObserver(nsIEditorObserver var1);

    public void removeEditorObserver(nsIEditorObserver var1);

    public void addEditActionListener(nsIEditActionListener var1);

    public void removeEditActionListener(nsIEditActionListener var1);

    public void addDocumentStateListener(nsIDocumentStateListener var1);

    public void removeDocumentStateListener(nsIDocumentStateListener var1);

    public void dumpContentTree();

    public void debugDumpContent();

    public void debugUnitTests(int[] var1, int[] var2);
}

