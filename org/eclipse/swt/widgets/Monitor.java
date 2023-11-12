/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Rectangle;

public final class Monitor {
    long handle;
    int x;
    int y;
    int width;
    int height;
    int clientX;
    int clientY;
    int clientWidth;
    int clientHeight;
    int zoom;

    Monitor() {
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Monitor)) {
            return false;
        }
        Monitor monitor = (Monitor)object;
        return this.handle == monitor.handle;
    }

    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public Rectangle getClientArea() {
        return new Rectangle(this.clientX, this.clientY, this.clientWidth, this.clientHeight);
    }

    public int getZoom() {
        return this.zoom;
    }

    void setBounds(Rectangle rectangle) {
        this.x = rectangle.x;
        this.y = rectangle.y;
        this.width = rectangle.width;
        this.height = rectangle.height;
    }

    void setClientArea(Rectangle rectangle) {
        this.clientX = rectangle.x;
        this.clientY = rectangle.y;
        this.clientWidth = rectangle.width;
        this.clientHeight = rectangle.height;
    }

    public int hashCode() {
        return (int)this.handle;
    }
}

