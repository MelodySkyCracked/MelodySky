/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Event.events.misc;

import xyz.Melody.Event.Event;

public class EventChat
extends Event {
    private String message;

    public EventChat(String string) {
        this.message = string;
        this.setType((byte)0);
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String string) {
        this.message = string;
    }
}

