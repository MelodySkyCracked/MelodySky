/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib;

import org.spongepowered.asm.lib.Label;

class Handler {
    Label start;
    Label end;
    Label handler;
    String desc;
    int type;
    Handler next;

    Handler() {
    }

    static Handler remove(Handler handler, Label label, Label label2) {
        int n;
        if (handler == null) {
            return null;
        }
        handler.next = Handler.remove(handler.next, label, label2);
        int n2 = handler.start.position;
        int n3 = handler.end.position;
        int n4 = label.position;
        int n5 = n = label2 == null ? Integer.MAX_VALUE : label2.position;
        if (n4 < n3 && n > n2) {
            if (n4 <= n2) {
                if (n >= n3) {
                    handler = handler.next;
                } else {
                    handler.start = label2;
                }
            } else if (n >= n3) {
                handler.end = label;
            } else {
                Handler handler2 = new Handler();
                handler2.start = label2;
                handler2.end = handler.end;
                handler2.handler = handler.handler;
                handler2.desc = handler.desc;
                handler2.type = handler.type;
                handler2.next = handler.next;
                handler.end = label;
                handler.next = handler2;
            }
        }
        return handler;
    }
}

