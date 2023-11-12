/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAccessible;
import org.mozilla.interfaces.nsIAccessibleRetrieval;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIAccessibilityService
extends nsIAccessibleRetrieval {
    public static final String NS_IACCESSIBILITYSERVICE_IID = "{0e80f152-d676-4fba-8862-9dc4eb761442}";

    public nsIAccessible createOuterDocAccessible(nsIDOMNode var1);

    public nsIAccessible createRootAccessible(nsISupports var1, nsISupports var2);

    public nsIAccessible createHTML4ButtonAccessible(nsISupports var1);

    public nsIAccessible createXULAlertAccessible(nsIDOMNode var1);

    public nsIAccessible createHTMLAreaAccessible(nsISupports var1, nsIDOMNode var2, nsIAccessible var3);

    public nsIAccessible createHTMLBlockAccessible(nsISupports var1);

    public nsIAccessible createHTMLButtonAccessible(nsISupports var1);

    public nsIAccessible createHTMLButtonAccessibleXBL(nsIDOMNode var1);

    public nsIAccessible createHTMLAccessibleByMarkup(nsISupports var1, nsISupports var2, nsIDOMNode var3, String var4);

    public nsIAccessible createHTMLLIAccessible(nsISupports var1, nsISupports var2, String var3);

    public nsIAccessible createHTMLCheckboxAccessible(nsISupports var1);

    public nsIAccessible createHTMLCheckboxAccessibleXBL(nsIDOMNode var1);

    public nsIAccessible createHTMLComboboxAccessible(nsIDOMNode var1, nsISupports var2);

    public nsIAccessible createHTMLGenericAccessible(nsISupports var1);

    public nsIAccessible createHTMLGroupboxAccessible(nsISupports var1);

    public nsIAccessible createHTMLHRAccessible(nsISupports var1);

    public nsIAccessible createHTMLImageAccessible(nsISupports var1);

    public nsIAccessible createHTMLLabelAccessible(nsISupports var1);

    public nsIAccessible createHTMLListboxAccessible(nsIDOMNode var1, nsISupports var2);

    public nsIAccessible createHTMLObjectFrameAccessible(nsISupports var1);

    public nsIAccessible createHTMLRadioButtonAccessible(nsISupports var1);

    public nsIAccessible createHTMLRadioButtonAccessibleXBL(nsIDOMNode var1);

    public nsIAccessible createHTMLSelectOptionAccessible(nsIDOMNode var1, nsIAccessible var2, nsISupports var3);

    public nsIAccessible createHTMLTableAccessible(nsISupports var1);

    public nsIAccessible createHTMLTableCellAccessible(nsISupports var1);

    public nsIAccessible createHTMLTableCaptionAccessible(nsIDOMNode var1);

    public nsIAccessible createHTMLTableHeadAccessible(nsIDOMNode var1);

    public nsIAccessible createHTMLTextAccessible(nsISupports var1);

    public nsIAccessible createHTMLTextFieldAccessible(nsISupports var1);

    public nsIAccessible createXULButtonAccessible(nsIDOMNode var1);

    public nsIAccessible createXULCheckboxAccessible(nsIDOMNode var1);

    public nsIAccessible createXULColorPickerAccessible(nsIDOMNode var1);

    public nsIAccessible createXULColorPickerTileAccessible(nsIDOMNode var1);

    public nsIAccessible createXULComboboxAccessible(nsIDOMNode var1);

    public nsIAccessible createXULDropmarkerAccessible(nsIDOMNode var1);

    public nsIAccessible createXULGroupboxAccessible(nsIDOMNode var1);

    public nsIAccessible createXULImageAccessible(nsIDOMNode var1);

    public nsIAccessible createXULLinkAccessible(nsIDOMNode var1);

    public nsIAccessible createXULListboxAccessible(nsIDOMNode var1);

    public nsIAccessible createXULListitemAccessible(nsIDOMNode var1);

    public nsIAccessible createXULMenubarAccessible(nsIDOMNode var1);

    public nsIAccessible createXULMenuitemAccessible(nsIDOMNode var1);

    public nsIAccessible createXULMenupopupAccessible(nsIDOMNode var1);

    public nsIAccessible createXULMenuSeparatorAccessible(nsIDOMNode var1);

    public nsIAccessible createXULProgressMeterAccessible(nsIDOMNode var1);

    public nsIAccessible createXULStatusBarAccessible(nsIDOMNode var1);

    public nsIAccessible createXULRadioButtonAccessible(nsIDOMNode var1);

    public nsIAccessible createXULRadioGroupAccessible(nsIDOMNode var1);

    public nsIAccessible createXULSelectOptionAccessible(nsIDOMNode var1);

    public nsIAccessible createXULSelectListAccessible(nsIDOMNode var1);

    public nsIAccessible createXULTabAccessible(nsIDOMNode var1);

    public nsIAccessible createXULTabBoxAccessible(nsIDOMNode var1);

    public nsIAccessible createXULTabPanelsAccessible(nsIDOMNode var1);

    public nsIAccessible createXULTabsAccessible(nsIDOMNode var1);

    public nsIAccessible createXULTextAccessible(nsIDOMNode var1);

    public nsIAccessible createXULTextBoxAccessible(nsIDOMNode var1);

    public nsIAccessible createXULTreeAccessible(nsIDOMNode var1);

    public nsIAccessible createXULTreeColumnsAccessible(nsIDOMNode var1);

    public nsIAccessible createXULTreeColumnitemAccessible(nsIDOMNode var1);

    public nsIAccessible createXULToolbarAccessible(nsIDOMNode var1);

    public nsIAccessible createXULToolbarSeparatorAccessible(nsIDOMNode var1);

    public nsIAccessible createXULTooltipAccessible(nsIDOMNode var1);
}

