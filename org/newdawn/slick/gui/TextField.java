/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.Sys
 */
package org.newdawn.slick.gui;

import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;

public class TextField
extends AbstractComponent {
    private static final int INITIAL_KEY_REPEAT_INTERVAL = 400;
    private static final int KEY_REPEAT_INTERVAL = 50;
    private int width;
    private int height;
    protected int x;
    protected int y;
    private int maxCharacter = 10000;
    private String value = "";
    private Font font;
    private Color border = Color.white;
    private Color text = Color.white;
    private Color background = new Color(0.0f, 0.0f, 0.0f, 0.5f);
    private int cursorPos;
    private boolean visibleCursor = true;
    private int lastKey = -1;
    private char lastChar = '\u0000';
    private long repeatTimer;
    private String oldText;
    private int oldCursorPos;
    private boolean consume = true;

    public TextField(GUIContext gUIContext, Font font, int n, int n2, int n3, int n4, ComponentListener componentListener) {
        this(gUIContext, font, n, n2, n3, n4);
        this.addListener(componentListener);
    }

    public TextField(GUIContext gUIContext, Font font, int n, int n2, int n3, int n4) {
        super(gUIContext);
        this.font = font;
        this.setLocation(n, n2);
        this.width = n3;
        this.height = n4;
    }

    public void setConsumeEvents(boolean bl) {
        this.consume = bl;
    }

    public void deactivate() {
        this.setFocus(false);
    }

    @Override
    public void setLocation(int n, int n2) {
        this.x = n;
        this.y = n2;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    public void setBackgroundColor(Color color) {
        this.background = color;
    }

    public void setBorderColor(Color color) {
        this.border = color;
    }

    public void setTextColor(Color color) {
        this.text = color;
    }

    @Override
    public void render(GUIContext gUIContext, Graphics graphics) {
        if (this.lastKey != -1) {
            if (this.input.isKeyDown(this.lastKey)) {
                if (this.repeatTimer < System.currentTimeMillis()) {
                    this.repeatTimer = System.currentTimeMillis() + 50L;
                    this.keyPressed(this.lastKey, this.lastChar);
                }
            } else {
                this.lastKey = -1;
            }
        }
        Rectangle rectangle = graphics.getClip();
        graphics.setWorldClip(this.x, this.y, this.width, this.height);
        Color color = graphics.getColor();
        if (this.background != null) {
            graphics.setColor(this.background.multiply(color));
            graphics.fillRect(this.x, this.y, this.width, this.height);
        }
        graphics.setColor(this.text.multiply(color));
        Font font = graphics.getFont();
        int n = this.font.getWidth(this.value.substring(0, this.cursorPos));
        int n2 = 0;
        if (n > this.width) {
            n2 = this.width - n - this.font.getWidth("_");
        }
        graphics.translate(n2 + 2, 0.0f);
        graphics.setFont(this.font);
        graphics.drawString(this.value, this.x + 1, this.y + 1);
        if (this.hasFocus() && this.visibleCursor) {
            graphics.drawString("_", this.x + 1 + n + 2, this.y + 1);
        }
        graphics.translate(-n2 - 2, 0.0f);
        if (this.border != null) {
            graphics.setColor(this.border.multiply(color));
            graphics.drawRect(this.x, this.y, this.width, this.height);
        }
        graphics.setColor(color);
        graphics.setFont(font);
        graphics.clearWorldClip();
        graphics.setClip(rectangle);
    }

    public String getText() {
        return this.value;
    }

    public void setText(String string) {
        this.value = string;
        if (this.cursorPos > string.length()) {
            this.cursorPos = string.length();
        }
    }

    public void setCursorPos(int n) {
        this.cursorPos = n;
        if (this.cursorPos > this.value.length()) {
            this.cursorPos = this.value.length();
        }
    }

    public void setCursorVisible(boolean bl) {
        this.visibleCursor = bl;
    }

    public void setMaxLength(int n) {
        this.maxCharacter = n;
        if (this.value.length() > this.maxCharacter) {
            this.value = this.value.substring(0, this.maxCharacter);
        }
    }

    protected void doPaste(String string) {
        this.recordOldPosition();
        for (int i = 0; i < string.length(); ++i) {
            this.keyPressed(-1, string.charAt(i));
        }
    }

    protected void recordOldPosition() {
        this.oldText = this.getText();
        this.oldCursorPos = this.cursorPos;
    }

    protected void doUndo(int n, String string) {
        if (string != null) {
            this.setText(string);
            this.setCursorPos(n);
        }
    }

    @Override
    public void keyPressed(int n, char c) {
        if (this.hasFocus()) {
            if (n != -1) {
                if (n == 47 && (this.input.isKeyDown(29) || this.input.isKeyDown(157))) {
                    String string = Sys.getClipboard();
                    if (string != null) {
                        this.doPaste(string);
                    }
                    return;
                }
                if (n == 44 && (this.input.isKeyDown(29) || this.input.isKeyDown(157))) {
                    if (this.oldText != null) {
                        this.doUndo(this.oldCursorPos, this.oldText);
                    }
                    return;
                }
                if (this.input.isKeyDown(29) || this.input.isKeyDown(157)) {
                    return;
                }
                if (this.input.isKeyDown(56) || this.input.isKeyDown(184)) {
                    return;
                }
            }
            if (this.lastKey != n) {
                this.lastKey = n;
                this.repeatTimer = System.currentTimeMillis() + 400L;
            } else {
                this.repeatTimer = System.currentTimeMillis() + 50L;
            }
            this.lastChar = c;
            if (n == 203) {
                if (this.cursorPos > 0) {
                    --this.cursorPos;
                }
                if (this.consume) {
                    this.container.getInput().consumeEvent();
                }
            } else if (n == 205) {
                if (this.cursorPos < this.value.length()) {
                    ++this.cursorPos;
                }
                if (this.consume) {
                    this.container.getInput().consumeEvent();
                }
            } else if (n == 14) {
                if (this.cursorPos > 0 && this.value.length() > 0) {
                    this.value = this.cursorPos < this.value.length() ? this.value.substring(0, this.cursorPos - 1) + this.value.substring(this.cursorPos) : this.value.substring(0, this.cursorPos - 1);
                    --this.cursorPos;
                }
                if (this.consume) {
                    this.container.getInput().consumeEvent();
                }
            } else if (n == 211) {
                if (this.value.length() > this.cursorPos) {
                    this.value = this.value.substring(0, this.cursorPos) + this.value.substring(this.cursorPos + 1);
                }
                if (this.consume) {
                    this.container.getInput().consumeEvent();
                }
            } else if (c < '\u007f' && c > '\u001f' && this.value.length() < this.maxCharacter) {
                this.value = this.cursorPos < this.value.length() ? this.value.substring(0, this.cursorPos) + c + this.value.substring(this.cursorPos) : this.value.substring(0, this.cursorPos) + c;
                ++this.cursorPos;
                if (this.consume) {
                    this.container.getInput().consumeEvent();
                }
            } else if (n == 28) {
                this.notifyListeners();
                if (this.consume) {
                    this.container.getInput().consumeEvent();
                }
            }
        }
    }

    @Override
    public void setFocus(boolean bl) {
        this.lastKey = -1;
        super.setFocus(bl);
    }
}

