/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

public class LocatedImage {
    private Image image;
    private int x;
    private int y;
    private Color filter = Color.white;
    private float width;
    private float height;

    public LocatedImage(Image image, int n, int n2) {
        this.image = image;
        this.x = n;
        this.y = n2;
        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public float getHeight() {
        return this.height;
    }

    public float getWidth() {
        return this.width;
    }

    public void setHeight(float f) {
        this.height = f;
    }

    public void setWidth(float f) {
        this.width = f;
    }

    public void setColor(Color color) {
        this.filter = color;
    }

    public Color getColor() {
        return this.filter;
    }

    public void setX(int n) {
        this.x = n;
    }

    public void setY(int n) {
        this.y = n;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void draw() {
        this.image.draw(this.x, this.y, this.width, this.height, this.filter);
    }
}

