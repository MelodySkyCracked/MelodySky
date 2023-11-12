/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIControllers;
import org.mozilla.interfaces.nsIDOMBarProp;
import org.mozilla.interfaces.nsIDOMCrypto;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMHistory;
import org.mozilla.interfaces.nsIDOMLocation;
import org.mozilla.interfaces.nsIDOMNavigator;
import org.mozilla.interfaces.nsIDOMPkcs11;
import org.mozilla.interfaces.nsIDOMScreen;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIDOMWindow2;

public interface nsIDOMWindowInternal
extends nsIDOMWindow2 {
    public static final String NS_IDOMWINDOWINTERNAL_IID = "{f914492c-0138-4123-a634-6ef8e3f126f8}";

    public nsIDOMWindowInternal getWindow();

    public nsIDOMWindowInternal getSelf();

    public nsIDOMNavigator getNavigator();

    public nsIDOMScreen getScreen();

    public nsIDOMHistory getHistory();

    public nsIDOMWindow getContent();

    public nsIDOMBarProp getMenubar();

    public nsIDOMBarProp getToolbar();

    public nsIDOMBarProp getLocationbar();

    public nsIDOMBarProp getPersonalbar();

    public nsIDOMBarProp getStatusbar();

    public nsIDOMBarProp getDirectories();

    public boolean getClosed();

    public nsIDOMCrypto getCrypto();

    public nsIDOMPkcs11 getPkcs11();

    public nsIControllers getControllers();

    public nsIDOMWindowInternal getOpener();

    public void setOpener(nsIDOMWindowInternal var1);

    public String getStatus();

    public void setStatus(String var1);

    public String getDefaultStatus();

    public void setDefaultStatus(String var1);

    public nsIDOMLocation getLocation();

    public int getInnerWidth();

    public void setInnerWidth(int var1);

    public int getInnerHeight();

    public void setInnerHeight(int var1);

    public int getOuterWidth();

    public void setOuterWidth(int var1);

    public int getOuterHeight();

    public void setOuterHeight(int var1);

    public int getScreenX();

    public void setScreenX(int var1);

    public int getScreenY();

    public void setScreenY(int var1);

    public int getPageXOffset();

    public int getPageYOffset();

    public int getScrollMaxX();

    public int getScrollMaxY();

    public long getLength();

    public boolean getFullScreen();

    public void setFullScreen(boolean var1);

    public void alert(String var1);

    public boolean confirm(String var1);

    public String prompt(String var1, String var2, String var3, long var4);

    public void focus();

    public void blur();

    public void back();

    public void forward();

    public void home();

    public void stop();

    public void print();

    public void moveTo(int var1, int var2);

    public void moveBy(int var1, int var2);

    public void resizeTo(int var1, int var2);

    public void resizeBy(int var1, int var2);

    public void scroll(int var1, int var2);

    public void close();

    public void updateCommands(String var1);

    public String atob(String var1);

    public String btoa(String var1);

    public nsIDOMElement getFrameElement();
}

