/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIBoxObject;
import org.mozilla.interfaces.nsIControllers;
import org.mozilla.interfaces.nsIDOMCSSStyleDeclaration;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMNodeList;
import org.mozilla.interfaces.nsIRDFCompositeDataSource;
import org.mozilla.interfaces.nsIRDFResource;
import org.mozilla.interfaces.nsIXULTemplateBuilder;

public interface nsIDOMXULElement
extends nsIDOMElement {
    public static final String NS_IDOMXULELEMENT_IID = "{0574ed81-c088-11d2-96ed-00104b7b7deb}";

    public String getId();

    public void setId(String var1);

    public String getClassName();

    public void setClassName(String var1);

    public String getAlign();

    public void setAlign(String var1);

    public String getDir();

    public void setDir(String var1);

    public String getFlex();

    public void setFlex(String var1);

    public String getFlexGroup();

    public void setFlexGroup(String var1);

    public String getOrdinal();

    public void setOrdinal(String var1);

    public String getOrient();

    public void setOrient(String var1);

    public String getPack();

    public void setPack(String var1);

    public boolean getHidden();

    public void setHidden(boolean var1);

    public boolean getCollapsed();

    public void setCollapsed(boolean var1);

    public String getObserves();

    public void setObserves(String var1);

    public String getMenu();

    public void setMenu(String var1);

    public String getContextMenu();

    public void setContextMenu(String var1);

    public String getTooltip();

    public void setTooltip(String var1);

    public String getWidth();

    public void setWidth(String var1);

    public String getHeight();

    public void setHeight(String var1);

    public String getMinWidth();

    public void setMinWidth(String var1);

    public String getMinHeight();

    public void setMinHeight(String var1);

    public String getMaxWidth();

    public void setMaxWidth(String var1);

    public String getMaxHeight();

    public void setMaxHeight(String var1);

    public String getPersist();

    public void setPersist(String var1);

    public String getLeft();

    public void setLeft(String var1);

    public String getTop();

    public void setTop(String var1);

    public String getDatasources();

    public void setDatasources(String var1);

    public String getRef();

    public void setRef(String var1);

    public String getTooltipText();

    public void setTooltipText(String var1);

    public String getStatusText();

    public void setStatusText(String var1);

    public boolean getAllowEvents();

    public void setAllowEvents(boolean var1);

    public nsIDOMCSSStyleDeclaration getStyle();

    public nsIRDFCompositeDataSource getDatabase();

    public nsIXULTemplateBuilder getBuilder();

    public nsIRDFResource getResource();

    public nsIControllers getControllers();

    public nsIBoxObject getBoxObject();

    public void focus();

    public void blur();

    public void click();

    public void doCommand();

    public nsIDOMNodeList getElementsByAttribute(String var1, String var2);
}

