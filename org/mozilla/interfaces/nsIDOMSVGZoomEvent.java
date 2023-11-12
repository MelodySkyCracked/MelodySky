/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGPoint;
import org.mozilla.interfaces.nsIDOMSVGRect;
import org.mozilla.interfaces.nsIDOMUIEvent;

public interface nsIDOMSVGZoomEvent
extends nsIDOMUIEvent {
    public static final String NS_IDOMSVGZOOMEVENT_IID = "{339a8c7a-552e-4cbc-8d96-8370a3939358}";

    public nsIDOMSVGRect getZoomRectScreen();

    public float getPreviousScale();

    public nsIDOMSVGPoint getPreviousTranslate();

    public float getNewScale();

    public nsIDOMSVGPoint getNewTranslate();
}

