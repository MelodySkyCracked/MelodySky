/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.gui;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.util.InputAdapter;

public abstract class AbstractComponent
extends InputAdapter {
    private static AbstractComponent currentFocus = null;
    protected GUIContext container;
    protected Set listeners;
    private boolean focus = false;
    protected Input input;

    public AbstractComponent(GUIContext gUIContext) {
        this.container = gUIContext;
        this.listeners = new HashSet();
        this.input = gUIContext.getInput();
        this.input.addPrimaryListener(this);
        this.setLocation(0, 0);
    }

    public void addListener(ComponentListener componentListener) {
        this.listeners.add(componentListener);
    }

    public void removeListener(ComponentListener componentListener) {
        this.listeners.remove(componentListener);
    }

    protected void notifyListeners() {
        Iterator iterator = this.listeners.iterator();
        while (iterator.hasNext()) {
            ((ComponentListener)iterator.next()).componentActivated(this);
        }
    }

    public abstract void render(GUIContext var1, Graphics var2) throws SlickException;

    public abstract void setLocation(int var1, int var2);

    public abstract int getX();

    public abstract int getY();

    public abstract int getWidth();

    public abstract int getHeight();

    public void setFocus(boolean bl) {
        if (bl) {
            if (currentFocus != null) {
                currentFocus.setFocus(false);
            }
            currentFocus = this;
        } else if (currentFocus == this) {
            currentFocus = null;
        }
        this.focus = bl;
    }

    public boolean hasFocus() {
        return this.focus;
    }

    protected void consumeEvent() {
        this.input.consumeEvent();
    }

    @Override
    public void mouseReleased(int n, int n2, int n3) {
        this.setFocus(Rectangle.contains(n2, n3, this.getX(), this.getY(), this.getWidth(), this.getHeight()));
    }
}

