/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.opengl;

import java.io.IOException;
import java.util.ArrayList;

public class CompositeIOException
extends IOException {
    private ArrayList exceptions = new ArrayList();

    public void addException(Exception exception) {
        this.exceptions.add(exception);
    }

    @Override
    public String getMessage() {
        String string = "Composite Exception: \n";
        for (int i = 0; i < this.exceptions.size(); ++i) {
            string = string + "\t" + ((IOException)this.exceptions.get(i)).getMessage() + "\n";
        }
        return string;
    }
}

