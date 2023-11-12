/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;

public class MouseOverArea
extends AbstractComponent {
    private static final int NORMAL = 1;
    private static final int MOUSE_DOWN = 2;
    private static final int MOUSE_OVER = 3;
    private Image normalImage;
    private Image mouseOverImage;
    private Image mouseDownImage;
    private Color normalColor = Color.white;
    private Color mouseOverColor = Color.white;
    private Color mouseDownColor = Color.white;
    private Sound mouseOverSound;
    private Sound mouseDownSound;
    private Shape area;
    private Image currentImage;
    private Color currentColor;
    private boolean over;
    private boolean mouseDown;
    private int state = 1;
    private boolean mouseUp;

    public MouseOverArea(GUIContext gUIContext, Image image, int n, int n2, ComponentListener componentListener) {
        this(gUIContext, image, n, n2, image.getWidth(), image.getHeight());
        this.addListener(componentListener);
    }

    public MouseOverArea(GUIContext gUIContext, Image image, int n, int n2) {
        this(gUIContext, image, n, n2, image.getWidth(), image.getHeight());
    }

    public MouseOverArea(GUIContext gUIContext, Image image, int n, int n2, int n3, int n4, ComponentListener componentListener) {
        this(gUIContext, image, n, n2, n3, n4);
        this.addListener(componentListener);
    }

    public MouseOverArea(GUIContext gUIContext, Image image, int n, int n2, int n3, int n4) {
        this(gUIContext, image, new Rectangle(n, n2, n3, n4));
    }

    public MouseOverArea(GUIContext gUIContext, Image image, Shape shape) {
        super(gUIContext);
        this.area = shape;
        this.normalImage = image;
        this.currentImage = image;
        this.mouseOverImage = image;
        this.mouseDownImage = image;
        this.currentColor = this.normalColor;
        this.state = 1;
        Input input = gUIContext.getInput();
        this.over = this.area.contains(input.getMouseX(), input.getMouseY());
        this.mouseDown = input.isMouseButtonDown(0);
        this.updateImage();
    }

    public void setLocation(float f, float f2) {
        if (this.area != null) {
            this.area.setX(f);
            this.area.setY(f2);
        }
    }

    public void setX(float f) {
        this.area.setX(f);
    }

    public void setY(float f) {
        this.area.setY(f);
    }

    @Override
    public int getX() {
        return (int)this.area.getX();
    }

    @Override
    public int getY() {
        return (int)this.area.getY();
    }

    public void setNormalColor(Color color) {
        this.normalColor = color;
    }

    public void setMouseOverColor(Color color) {
        this.mouseOverColor = color;
    }

    public void setMouseDownColor(Color color) {
        this.mouseDownColor = color;
    }

    public void setNormalImage(Image image) {
        this.normalImage = image;
    }

    public void setMouseOverImage(Image image) {
        this.mouseOverImage = image;
    }

    public void setMouseDownImage(Image image) {
        this.mouseDownImage = image;
    }

    @Override
    public void render(GUIContext gUIContext, Graphics graphics) {
        if (this.currentImage != null) {
            int n = (int)(this.area.getX() + (float)((this.getWidth() - this.currentImage.getWidth()) / 2));
            int n2 = (int)(this.area.getY() + (float)((this.getHeight() - this.currentImage.getHeight()) / 2));
            this.currentImage.draw((float)n, (float)n2, this.currentColor);
        } else {
            graphics.setColor(this.currentColor);
            graphics.fill(this.area);
        }
        this.updateImage();
    }

    private void updateImage() {
        if (!this.over) {
            this.currentImage = this.normalImage;
            this.currentColor = this.normalColor;
            this.state = 1;
            this.mouseUp = false;
        } else {
            if (this.mouseDown) {
                if (this.state != 2 && this.mouseUp) {
                    if (this.mouseDownSound != null) {
                        this.mouseDownSound.play();
                    }
                    this.currentImage = this.mouseDownImage;
                    this.currentColor = this.mouseDownColor;
                    this.state = 2;
                    this.notifyListeners();
                    this.mouseUp = false;
                }
                return;
            }
            this.mouseUp = true;
            if (this.state != 3) {
                if (this.mouseOverSound != null) {
                    this.mouseOverSound.play();
                }
                this.currentImage = this.mouseOverImage;
                this.currentColor = this.mouseOverColor;
                this.state = 3;
            }
        }
        this.mouseDown = false;
        this.state = 1;
    }

    public void setMouseOverSound(Sound sound) {
        this.mouseOverSound = sound;
    }

    public void setMouseDownSound(Sound sound) {
        this.mouseDownSound = sound;
    }

    @Override
    public void mouseMoved(int n, int n2, int n3, int n4) {
        this.over = this.area.contains(n3, n4);
    }

    @Override
    public void mouseDragged(int n, int n2, int n3, int n4) {
        this.mouseMoved(n, n2, n3, n4);
    }

    @Override
    public void mousePressed(int n, int n2, int n3) {
        this.over = this.area.contains(n2, n3);
        if (n == 0) {
            this.mouseDown = true;
        }
    }

    @Override
    public void mouseReleased(int n, int n2, int n3) {
        this.over = this.area.contains(n2, n3);
        if (n == 0) {
            this.mouseDown = false;
        }
    }

    @Override
    public int getHeight() {
        return (int)(this.area.getMaxY() - this.area.getY());
    }

    @Override
    public int getWidth() {
        return (int)(this.area.getMaxX() - this.area.getX());
    }

    public boolean isMouseOver() {
        return this.over;
    }

    @Override
    public void setLocation(int n, int n2) {
        this.setLocation((float)n, (float)n2);
    }
}

