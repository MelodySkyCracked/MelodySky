/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;

public class SpriteSheet
extends Image {
    private int tw;
    private int th;
    private int margin = 0;
    private Image[][] subImages;
    private int spacing;
    private Image target;

    public SpriteSheet(URL uRL, int n, int n2) throws SlickException, IOException {
        this(new Image(uRL.openStream(), uRL.toString(), false), n, n2);
    }

    public SpriteSheet(Image image, int n, int n2) {
        super(image);
        this.target = image;
        this.tw = n;
        this.th = n2;
        this.initImpl();
    }

    public SpriteSheet(Image image, int n, int n2, int n3, int n4) {
        super(image);
        this.target = image;
        this.tw = n;
        this.th = n2;
        this.spacing = n3;
        this.margin = n4;
        this.initImpl();
    }

    public SpriteSheet(Image image, int n, int n2, int n3) {
        this(image, n, n2, n3, 0);
    }

    public SpriteSheet(String string, int n, int n2, int n3) throws SlickException {
        this(string, n, n2, null, n3);
    }

    public SpriteSheet(String string, int n, int n2) throws SlickException {
        this(string, n, n2, null);
    }

    public SpriteSheet(String string, int n, int n2, Color color) throws SlickException {
        this(string, n, n2, color, 0);
    }

    public SpriteSheet(String string, int n, int n2, Color color, int n3) throws SlickException {
        super(string, false, 2, color);
        this.target = this;
        this.tw = n;
        this.th = n2;
        this.spacing = n3;
    }

    public SpriteSheet(String string, InputStream inputStream, int n, int n2) throws SlickException {
        super(inputStream, string, false);
        this.target = this;
        this.tw = n;
        this.th = n2;
    }

    @Override
    protected void initImpl() {
        if (this.subImages != null) {
            return;
        }
        int n = (this.getWidth() - this.margin * 2 - this.tw) / (this.tw + this.spacing) + 1;
        int n2 = (this.getHeight() - this.margin * 2 - this.th) / (this.th + this.spacing) + 1;
        if ((this.getHeight() - this.th) % (this.th + this.spacing) != 0) {
            ++n2;
        }
        this.subImages = new Image[n][n2];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                this.subImages[i][j] = this.getSprite(i, j);
            }
        }
    }

    public Image getSubImage(int n, int n2) {
        this.init();
        if (n < 0 || n >= this.subImages.length) {
            throw new RuntimeException("SubImage out of sheet bounds: " + n + "," + n2);
        }
        if (n2 < 0 || n2 >= this.subImages[0].length) {
            throw new RuntimeException("SubImage out of sheet bounds: " + n + "," + n2);
        }
        return this.subImages[n][n2];
    }

    public Image getSprite(int n, int n2) {
        this.target.init();
        this.initImpl();
        if (n < 0 || n >= this.subImages.length) {
            throw new RuntimeException("SubImage out of sheet bounds: " + n + "," + n2);
        }
        if (n2 < 0 || n2 >= this.subImages[0].length) {
            throw new RuntimeException("SubImage out of sheet bounds: " + n + "," + n2);
        }
        return this.target.getSubImage(n * (this.tw + this.spacing) + this.margin, n2 * (this.th + this.spacing) + this.margin, this.tw, this.th);
    }

    public int getHorizontalCount() {
        this.target.init();
        this.initImpl();
        return this.subImages.length;
    }

    public int getVerticalCount() {
        this.target.init();
        this.initImpl();
        return this.subImages[0].length;
    }

    public void renderInUse(int n, int n2, int n3, int n4) {
        this.subImages[n3][n4].drawEmbedded(n, n2, this.tw, this.th);
    }

    @Override
    public void endUse() {
        if (this.target == this) {
            super.endUse();
            return;
        }
        this.target.endUse();
    }

    @Override
    public void startUse() {
        if (this.target == this) {
            super.startUse();
            return;
        }
        this.target.startUse();
    }

    @Override
    public void setTexture(Texture texture) {
        if (this.target == this) {
            super.setTexture(texture);
            return;
        }
        this.target.setTexture(texture);
    }
}

