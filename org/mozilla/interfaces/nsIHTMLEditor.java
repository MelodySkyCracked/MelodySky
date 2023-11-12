/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAtom;
import org.mozilla.interfaces.nsIContentFilter;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISelection;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsISupportsArray;

public interface nsIHTMLEditor
extends nsISupports {
    public static final String NS_IHTMLEDITOR_IID = "{afc36593-5787-4420-93d9-b2c0ccbf0cad}";
    public static final short eLeft = 0;
    public static final short eCenter = 1;
    public static final short eRight = 2;
    public static final short eJustify = 3;

    public void addDefaultProperty(nsIAtom var1, String var2, String var3);

    public void removeDefaultProperty(nsIAtom var1, String var2, String var3);

    public void removeAllDefaultProperties();

    public void setCSSInlineProperty(nsIAtom var1, String var2, String var3);

    public void setInlineProperty(nsIAtom var1, String var2, String var3);

    public void getInlineProperty(nsIAtom var1, String var2, String var3, boolean[] var4, boolean[] var5, boolean[] var6);

    public String getInlinePropertyWithAttrValue(nsIAtom var1, String var2, String var3, boolean[] var4, boolean[] var5, boolean[] var6);

    public void removeAllInlineProperties();

    public void removeInlineProperty(nsIAtom var1, String var2);

    public void increaseFontSize();

    public void decreaseFontSize();

    public boolean canDrag(nsIDOMEvent var1);

    public void doDrag(nsIDOMEvent var1);

    public void insertFromDrop(nsIDOMEvent var1);

    public boolean nodeIsBlock(nsIDOMNode var1);

    public void insertHTML(String var1);

    public void pasteNoFormatting(int var1);

    public void rebuildDocumentFromSource(String var1);

    public void insertHTMLWithContext(String var1, String var2, String var3, String var4, nsIDOMDocument var5, nsIDOMNode var6, int var7, boolean var8);

    public void insertElementAtSelection(nsIDOMElement var1, boolean var2);

    public void setDocumentTitle(String var1);

    public void updateBaseURL();

    public void selectElement(nsIDOMElement var1);

    public void setCaretAfterElement(nsIDOMElement var1);

    public void setParagraphFormat(String var1);

    public String getParagraphState(boolean[] var1);

    public String getFontFaceState(boolean[] var1);

    public String getFontColorState(boolean[] var1);

    public String getBackgroundColorState(boolean[] var1);

    public String getHighlightColorState(boolean[] var1);

    public void getListState(boolean[] var1, boolean[] var2, boolean[] var3, boolean[] var4);

    public void getListItemState(boolean[] var1, boolean[] var2, boolean[] var3, boolean[] var4);

    public void getAlignment(boolean[] var1, short[] var2);

    public void getIndentState(boolean[] var1, boolean[] var2);

    public void makeOrChangeList(String var1, boolean var2, String var3);

    public void removeList(String var1);

    public void indent(String var1);

    public void align(String var1);

    public nsIDOMElement getElementOrParentByTagName(String var1, nsIDOMNode var2);

    public nsIDOMElement getSelectedElement(String var1);

    public String getHeadContentsAsHTML();

    public void replaceHeadContentsWithHTML(String var1);

    public nsIDOMElement createElementWithDefaults(String var1);

    public void insertLinkAroundSelection(nsIDOMElement var1);

    public void setBackgroundColor(String var1);

    public void setBodyAttribute(String var1, String var2);

    public void ignoreSpuriousDragEvent(boolean var1);

    public nsISupportsArray getLinkedObjects();

    public boolean getIsCSSEnabled();

    public void setIsCSSEnabled(boolean var1);

    public void addInsertionListener(nsIContentFilter var1);

    public void removeInsertionListener(nsIContentFilter var1);

    public nsIDOMElement createAnonymousElement(String var1, nsIDOMNode var2, String var3, boolean var4);

    public nsIDOMElement getSelectionContainer();

    public void checkSelectionStateForAnonymousButtons(nsISelection var1);

    public boolean isAnonymousElement(nsIDOMElement var1);

    public boolean getReturnInParagraphCreatesNewParagraph();

    public void setReturnInParagraphCreatesNewParagraph(boolean var1);
}

