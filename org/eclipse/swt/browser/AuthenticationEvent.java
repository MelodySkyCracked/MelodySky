/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.browser;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Widget;

public class AuthenticationEvent
extends TypedEvent {
    public String location;
    public String user;
    public String password;
    public boolean doit = true;
    static final long serialVersionUID = -8322331206780057921L;

    public AuthenticationEvent(Widget widget) {
        super(widget);
    }

    @Override
    public String toString() {
        String string = super.toString();
        return string.substring(0, string.length() - 1) + " name=" + this.user + " password=" + this.password + " location=" + this.location;
    }
}

