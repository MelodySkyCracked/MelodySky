/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick;

import java.io.IOException;
import java.io.InputStream;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.pbuffer.GraphicsFactory;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.Log;

public class Image
implements Renderable {
    public static final int TOP_LEFT = 0;
    public static final int TOP_RIGHT = 1;
    public static final int BOTTOM_RIGHT = 2;
    public static final int BOTTOM_LEFT = 3;
    protected static SGL GL = Renderer.get();
    protected static Image inUse;
    public static final int FILTER_LINEAR = 1;
    public static final int FILTER_NEAREST = 2;
    protected Texture texture;
    protected int width;
    protected int height;
    protected float textureWidth;
    protected float textureHeight;
    protected float textureOffsetX;
    protected float textureOffsetY;
    protected float angle;
    protected float alpha = 1.0f;
    protected String ref;
    protected boolean inited = false;
    protected byte[] pixelData;
    protected boolean destroyed;
    protected float centerX;
    protected float centerY;
    protected String name;
    protected Color[] corners;
    private int filter = 9729;
    private boolean flipped;
    private Color transparent;

    protected Image(Image image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.texture = image.texture;
        this.textureWidth = image.textureWidth;
        this.textureHeight = image.textureHeight;
        this.ref = image.ref;
        this.textureOffsetX = image.textureOffsetX;
        this.textureOffsetY = image.textureOffsetY;
        this.centerX = this.width / 2;
        this.centerY = this.height / 2;
        this.inited = true;
    }

    protected Image() {
    }

    public Image(Texture texture) {
        this.texture = texture;
        this.ref = texture.toString();
        this.clampTexture();
    }

    public Image(String string) throws SlickException {
        this(string, false);
    }

    public Image(String string, Color color) throws SlickException {
        this(string, false, 1, color);
    }

    public Image(String string, boolean bl) throws SlickException {
        this(string, bl, 1);
    }

    public Image(String string, boolean bl, int n) throws SlickException {
        this(string, bl, n, null);
    }

    public Image(String string, boolean bl, int n, Color color) throws SlickException {
        this.filter = n == 1 ? 9729 : 9728;
        this.transparent = color;
        this.flipped = bl;
        try {
            this.ref = string;
            int[] nArray = null;
            if (color != null) {
                nArray = new int[]{(int)(color.r * 255.0f), (int)(color.g * 255.0f), (int)(color.b * 255.0f)};
            }
            this.texture = InternalTextureLoader.get().getTexture(string, bl, this.filter, nArray);
        }
        catch (IOException iOException) {
            Log.error(iOException);
            throw new SlickException("Failed to load image from: " + string, iOException);
        }
    }

    public void setFilter(int n) {
        this.filter = n == 1 ? 9729 : 9728;
        this.texture.bind();
        GL.glTexParameteri(3553, 10241, this.filter);
        GL.glTexParameteri(3553, 10240, this.filter);
    }

    public Image(int n, int n2) throws SlickException {
        this(n, n2, 2);
    }

    public Image(int n, int n2, int n3) throws SlickException {
        this.ref = super.toString();
        this.filter = n3 == 1 ? 9729 : 9728;
        try {
            this.texture = InternalTextureLoader.get().createTexture(n, n2, this.filter);
        }
        catch (IOException iOException) {
            Log.error(iOException);
            throw new SlickException("Failed to create empty image " + n + "x" + n2);
        }
        this.init();
    }

    public Image(InputStream inputStream, String string, boolean bl) throws SlickException {
        this(inputStream, string, bl, 1);
    }

    public Image(InputStream inputStream, String string, boolean bl, int n) throws SlickException {
        this.load(inputStream, string, bl, n, null);
    }

    Image(ImageBuffer imageBuffer) {
        this(imageBuffer, 1);
        TextureImpl.bindNone();
    }

    Image(ImageBuffer imageBuffer, int n) {
        this((ImageData)imageBuffer, n);
        TextureImpl.bindNone();
    }

    public Image(ImageData imageData) {
        this(imageData, 1);
    }

    public Image(ImageData imageData, int n) {
        try {
            this.filter = n == 1 ? 9729 : 9728;
            this.texture = InternalTextureLoader.get().getTexture(imageData, this.filter);
            this.ref = this.texture.toString();
        }
        catch (IOException iOException) {
            Log.error(iOException);
        }
    }

    public int getFilter() {
        return this.filter;
    }

    public String getResourceReference() {
        return this.ref;
    }

    public void setImageColor(float f, float f2, float f3, float f4) {
        this.setColor(0, f, f2, f3, f4);
        this.setColor(1, f, f2, f3, f4);
        this.setColor(3, f, f2, f3, f4);
        this.setColor(2, f, f2, f3, f4);
    }

    public void setImageColor(float f, float f2, float f3) {
        this.setColor(0, f, f2, f3);
        this.setColor(1, f, f2, f3);
        this.setColor(3, f, f2, f3);
        this.setColor(2, f, f2, f3);
    }

    public void setColor(int n, float f, float f2, float f3, float f4) {
        if (this.corners == null) {
            this.corners = new Color[]{new Color(1.0f, 1.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f)};
        }
        this.corners[n].r = f;
        this.corners[n].g = f2;
        this.corners[n].b = f3;
        this.corners[n].a = f4;
    }

    public void setColor(int n, float f, float f2, float f3) {
        if (this.corners == null) {
            this.corners = new Color[]{new Color(1.0f, 1.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f), new Color(1.0f, 1.0f, 1.0f, 1.0f)};
        }
        this.corners[n].r = f;
        this.corners[n].g = f2;
        this.corners[n].b = f3;
    }

    public void clampTexture() {
        if (GL.canTextureMirrorClamp()) {
            GL.glTexParameteri(3553, 10242, 34627);
            GL.glTexParameteri(3553, 10243, 34627);
        } else {
            GL.glTexParameteri(3553, 10242, 10496);
            GL.glTexParameteri(3553, 10243, 10496);
        }
    }

    public void setName(String string) {
        this.name = string;
    }

    public String getName() {
        return this.name;
    }

    public Graphics getGraphics() throws SlickException {
        return GraphicsFactory.getGraphicsForImage(this);
    }

    private void load(InputStream inputStream, String string, boolean bl, int n, Color color) throws SlickException {
        this.filter = n == 1 ? 9729 : 9728;
        try {
            this.ref = string;
            int[] nArray = null;
            if (color != null) {
                nArray = new int[]{(int)(color.r * 255.0f), (int)(color.g * 255.0f), (int)(color.b * 255.0f)};
            }
            this.texture = InternalTextureLoader.get().getTexture(inputStream, string, bl, this.filter, nArray);
        }
        catch (IOException iOException) {
            Log.error(iOException);
            throw new SlickException("Failed to load image from: " + string, iOException);
        }
    }

    public void bind() {
        this.texture.bind();
    }

    protected void reinit() {
        this.inited = false;
        this.init();
    }

    protected final void init() {
        if (this.inited) {
            return;
        }
        this.inited = true;
        if (this.texture != null) {
            this.width = this.texture.getImageWidth();
            this.height = this.texture.getImageHeight();
            this.textureOffsetX = 0.0f;
            this.textureOffsetY = 0.0f;
            this.textureWidth = this.texture.getWidth();
            this.textureHeight = this.texture.getHeight();
        }
        this.initImpl();
        this.centerX = this.width / 2;
        this.centerY = this.height / 2;
    }

    protected void initImpl() {
    }

    public void draw() {
        this.draw(0.0f, 0.0f);
    }

    public void drawCentered(float f, float f2) {
        this.draw(f - (float)(this.getWidth() / 2), f2 - (float)(this.getHeight() / 2));
    }

    @Override
    public void draw(float f, float f2) {
        this.init();
        this.draw(f, f2, (float)this.width, this.height);
    }

    public void draw(float f, float f2, Color color) {
        this.init();
        this.draw(f, f2, this.width, this.height, color);
    }

    public void drawEmbedded(float f, float f2, float f3, float f4) {
        this.init();
        if (this.corners == null) {
            GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY);
            GL.glVertex3f(f, f2, 0.0f);
            GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY + this.textureHeight);
            GL.glVertex3f(f, f2 + f4, 0.0f);
            GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY + this.textureHeight);
            GL.glVertex3f(f + f3, f2 + f4, 0.0f);
            GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY);
            GL.glVertex3f(f + f3, f2, 0.0f);
        } else {
            this.corners[0].bind();
            GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY);
            GL.glVertex3f(f, f2, 0.0f);
            this.corners[3].bind();
            GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY + this.textureHeight);
            GL.glVertex3f(f, f2 + f4, 0.0f);
            this.corners[2].bind();
            GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY + this.textureHeight);
            GL.glVertex3f(f + f3, f2 + f4, 0.0f);
            this.corners[1].bind();
            GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY);
            GL.glVertex3f(f + f3, f2, 0.0f);
        }
    }

    public float getTextureOffsetX() {
        this.init();
        return this.textureOffsetX;
    }

    public float getTextureOffsetY() {
        this.init();
        return this.textureOffsetY;
    }

    public float getTextureWidth() {
        this.init();
        return this.textureWidth;
    }

    public float getTextureHeight() {
        this.init();
        return this.textureHeight;
    }

    public void draw(float f, float f2, float f3) {
        this.init();
        this.draw(f, f2, (float)this.width * f3, (float)this.height * f3, Color.white);
    }

    public void draw(float f, float f2, float f3, Color color) {
        this.init();
        this.draw(f, f2, (float)this.width * f3, (float)this.height * f3, color);
    }

    public void draw(float f, float f2, float f3, float f4) {
        this.init();
        this.draw(f, f2, f3, f4, Color.white);
    }

    public void drawSheared(float f, float f2, float f3, float f4) {
        this.drawSheared(f, f2, f3, f4, Color.white);
    }

    public void drawSheared(float f, float f2, float f3, float f4, Color color) {
        if (this.alpha != 1.0f) {
            if (color == null) {
                color = Color.white;
            }
            color = new Color(color);
            color.a *= this.alpha;
        }
        if (color != null) {
            color.bind();
        }
        this.texture.bind();
        GL.glTranslatef(f, f2, 0.0f);
        if (this.angle != 0.0f) {
            GL.glTranslatef(this.centerX, this.centerY, 0.0f);
            GL.glRotatef(this.angle, 0.0f, 0.0f, 1.0f);
            GL.glTranslatef(-this.centerX, -this.centerY, 0.0f);
        }
        GL.glBegin(7);
        this.init();
        GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY);
        GL.glVertex3f(0.0f, 0.0f, 0.0f);
        GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY + this.textureHeight);
        GL.glVertex3f(f3, this.height, 0.0f);
        GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY + this.textureHeight);
        GL.glVertex3f((float)this.width + f3, (float)this.height + f4, 0.0f);
        GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY);
        GL.glVertex3f(this.width, f4, 0.0f);
        GL.glEnd();
        if (this.angle != 0.0f) {
            GL.glTranslatef(this.centerX, this.centerY, 0.0f);
            GL.glRotatef(-this.angle, 0.0f, 0.0f, 1.0f);
            GL.glTranslatef(-this.centerX, -this.centerY, 0.0f);
        }
        GL.glTranslatef(-f, -f2, 0.0f);
    }

    public void draw(float f, float f2, float f3, float f4, Color color) {
        if (this.alpha != 1.0f) {
            if (color == null) {
                color = Color.white;
            }
            color = new Color(color);
            color.a *= this.alpha;
        }
        if (color != null) {
            color.bind();
        }
        this.texture.bind();
        GL.glTranslatef(f, f2, 0.0f);
        if (this.angle != 0.0f) {
            GL.glTranslatef(this.centerX, this.centerY, 0.0f);
            GL.glRotatef(this.angle, 0.0f, 0.0f, 1.0f);
            GL.glTranslatef(-this.centerX, -this.centerY, 0.0f);
        }
        GL.glBegin(7);
        this.drawEmbedded(0.0f, 0.0f, f3, f4);
        GL.glEnd();
        if (this.angle != 0.0f) {
            GL.glTranslatef(this.centerX, this.centerY, 0.0f);
            GL.glRotatef(-this.angle, 0.0f, 0.0f, 1.0f);
            GL.glTranslatef(-this.centerX, -this.centerY, 0.0f);
        }
        GL.glTranslatef(-f, -f2, 0.0f);
    }

    public void drawFlash(float f, float f2, float f3, float f4) {
        this.drawFlash(f, f2, f3, f4, Color.white);
    }

    public void setCenterOfRotation(float f, float f2) {
        this.centerX = f;
        this.centerY = f2;
    }

    public float getCenterOfRotationX() {
        this.init();
        return this.centerX;
    }

    public float getCenterOfRotationY() {
        this.init();
        return this.centerY;
    }

    public void drawFlash(float f, float f2, float f3, float f4, Color color) {
        this.init();
        color.bind();
        this.texture.bind();
        if (GL.canSecondaryColor()) {
            GL.glEnable(33880);
            GL.glSecondaryColor3ubEXT((byte)(color.r * 255.0f), (byte)(color.g * 255.0f), (byte)(color.b * 255.0f));
        }
        GL.glTexEnvi(8960, 8704, 8448);
        GL.glTranslatef(f, f2, 0.0f);
        if (this.angle != 0.0f) {
            GL.glTranslatef(this.centerX, this.centerY, 0.0f);
            GL.glRotatef(this.angle, 0.0f, 0.0f, 1.0f);
            GL.glTranslatef(-this.centerX, -this.centerY, 0.0f);
        }
        GL.glBegin(7);
        this.drawEmbedded(0.0f, 0.0f, f3, f4);
        GL.glEnd();
        if (this.angle != 0.0f) {
            GL.glTranslatef(this.centerX, this.centerY, 0.0f);
            GL.glRotatef(-this.angle, 0.0f, 0.0f, 1.0f);
            GL.glTranslatef(-this.centerX, -this.centerY, 0.0f);
        }
        GL.glTranslatef(-f, -f2, 0.0f);
        if (GL.canSecondaryColor()) {
            GL.glDisable(33880);
        }
    }

    public void drawFlash(float f, float f2) {
        this.drawFlash(f, f2, this.getWidth(), this.getHeight());
    }

    public void setRotation(float f) {
        this.angle = f % 360.0f;
    }

    public float getRotation() {
        return this.angle;
    }

    public float getAlpha() {
        return this.alpha;
    }

    public void setAlpha(float f) {
        this.alpha = f;
    }

    public void rotate(float f) {
        this.angle += f;
        this.angle %= 360.0f;
    }

    public Image getSubImage(int n, int n2, int n3, int n4) {
        this.init();
        float f = (float)n / (float)this.width * this.textureWidth + this.textureOffsetX;
        float f2 = (float)n2 / (float)this.height * this.textureHeight + this.textureOffsetY;
        float f3 = (float)n3 / (float)this.width * this.textureWidth;
        float f4 = (float)n4 / (float)this.height * this.textureHeight;
        Image image = new Image();
        image.inited = true;
        image.texture = this.texture;
        image.textureOffsetX = f;
        image.textureOffsetY = f2;
        image.textureWidth = f3;
        image.textureHeight = f4;
        image.width = n3;
        image.height = n4;
        image.ref = this.ref;
        image.centerX = n3 / 2;
        image.centerY = n4 / 2;
        return image;
    }

    public void draw(float f, float f2, float f3, float f4, float f5, float f6) {
        this.draw(f, f2, f + (float)this.width, f2 + (float)this.height, f3, f4, f5, f6);
    }

    public void draw(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        this.draw(f, f2, f3, f4, f5, f6, f7, f8, Color.white);
    }

    public void draw(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Color color) {
        this.init();
        if (this.alpha != 1.0f) {
            if (color == null) {
                color = Color.white;
            }
            color = new Color(color);
            color.a *= this.alpha;
        }
        color.bind();
        this.texture.bind();
        GL.glTranslatef(f, f2, 0.0f);
        if (this.angle != 0.0f) {
            GL.glTranslatef(this.centerX, this.centerY, 0.0f);
            GL.glRotatef(this.angle, 0.0f, 0.0f, 1.0f);
            GL.glTranslatef(-this.centerX, -this.centerY, 0.0f);
        }
        GL.glBegin(7);
        this.drawEmbedded(0.0f, 0.0f, f3 - f, f4 - f2, f5, f6, f7, f8);
        GL.glEnd();
        if (this.angle != 0.0f) {
            GL.glTranslatef(this.centerX, this.centerY, 0.0f);
            GL.glRotatef(-this.angle, 0.0f, 0.0f, 1.0f);
            GL.glTranslatef(-this.centerX, -this.centerY, 0.0f);
        }
        GL.glTranslatef(-f, -f2, 0.0f);
    }

    public void drawEmbedded(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        this.drawEmbedded(f, f2, f3, f4, f5, f6, f7, f8, null);
    }

    public void drawEmbedded(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, Color color) {
        if (color != null) {
            color.bind();
        }
        float f9 = f3 - f;
        float f10 = f4 - f2;
        float f11 = f7 - f5;
        float f12 = f8 - f6;
        float f13 = f5 / (float)this.width * this.textureWidth + this.textureOffsetX;
        float f14 = f6 / (float)this.height * this.textureHeight + this.textureOffsetY;
        float f15 = f11 / (float)this.width * this.textureWidth;
        float f16 = f12 / (float)this.height * this.textureHeight;
        GL.glTexCoord2f(f13, f14);
        GL.glVertex3f(f, f2, 0.0f);
        GL.glTexCoord2f(f13, f14 + f16);
        GL.glVertex3f(f, f2 + f10, 0.0f);
        GL.glTexCoord2f(f13 + f15, f14 + f16);
        GL.glVertex3f(f + f9, f2 + f10, 0.0f);
        GL.glTexCoord2f(f13 + f15, f14);
        GL.glVertex3f(f + f9, f2, 0.0f);
    }

    public void drawWarped(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        Color.white.bind();
        this.texture.bind();
        GL.glTranslatef(f, f2, 0.0f);
        if (this.angle != 0.0f) {
            GL.glTranslatef(this.centerX, this.centerY, 0.0f);
            GL.glRotatef(this.angle, 0.0f, 0.0f, 1.0f);
            GL.glTranslatef(-this.centerX, -this.centerY, 0.0f);
        }
        GL.glBegin(7);
        this.init();
        GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY);
        GL.glVertex3f(0.0f, 0.0f, 0.0f);
        GL.glTexCoord2f(this.textureOffsetX, this.textureOffsetY + this.textureHeight);
        GL.glVertex3f(f3 - f, f4 - f2, 0.0f);
        GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY + this.textureHeight);
        GL.glVertex3f(f5 - f, f6 - f2, 0.0f);
        GL.glTexCoord2f(this.textureOffsetX + this.textureWidth, this.textureOffsetY);
        GL.glVertex3f(f7 - f, f8 - f2, 0.0f);
        GL.glEnd();
        if (this.angle != 0.0f) {
            GL.glTranslatef(this.centerX, this.centerY, 0.0f);
            GL.glRotatef(-this.angle, 0.0f, 0.0f, 1.0f);
            GL.glTranslatef(-this.centerX, -this.centerY, 0.0f);
        }
        GL.glTranslatef(-f, -f2, 0.0f);
    }

    public int getWidth() {
        this.init();
        return this.width;
    }

    public int getHeight() {
        this.init();
        return this.height;
    }

    public Image copy() {
        this.init();
        return this.getSubImage(0, 0, this.width, this.height);
    }

    public Image getScaledCopy(float f) {
        this.init();
        return this.getScaledCopy((int)((float)this.width * f), (int)((float)this.height * f));
    }

    public Image getScaledCopy(int n, int n2) {
        this.init();
        Image image = this.copy();
        image.width = n;
        image.height = n2;
        image.centerX = n / 2;
        image.centerY = n2 / 2;
        return image;
    }

    public void ensureInverted() {
        if (this.textureHeight > 0.0f) {
            this.textureOffsetY += this.textureHeight;
            this.textureHeight = -this.textureHeight;
        }
    }

    public Image getFlippedCopy(boolean bl, boolean bl2) {
        this.init();
        Image image = this.copy();
        if (bl) {
            image.textureOffsetX = this.textureOffsetX + this.textureWidth;
            image.textureWidth = -this.textureWidth;
        }
        if (bl2) {
            image.textureOffsetY = this.textureOffsetY + this.textureHeight;
            image.textureHeight = -this.textureHeight;
        }
        return image;
    }

    public void endUse() {
        if (inUse != this) {
            throw new RuntimeException("The sprite sheet is not currently in use");
        }
        inUse = null;
        GL.glEnd();
    }

    public void startUse() {
        if (inUse != null) {
            throw new RuntimeException("Attempt to start use of a sprite sheet before ending use with another - see endUse()");
        }
        inUse = this;
        this.init();
        Color.white.bind();
        this.texture.bind();
        GL.glBegin(7);
    }

    public String toString() {
        this.init();
        return "[Image " + this.ref + " " + this.width + "x" + this.height + "  " + this.textureOffsetX + "," + this.textureOffsetY + "," + this.textureWidth + "," + this.textureHeight + "]";
    }

    public Texture getTexture() {
        return this.texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        this.reinit();
    }

    private int translate(byte by) {
        if (by < 0) {
            return 256 + by;
        }
        return by;
    }

    public Color getColor(int n, int n2) {
        if (this.pixelData == null) {
            this.pixelData = this.texture.getTextureData();
        }
        int n3 = (int)(this.textureOffsetX * (float)this.texture.getTextureWidth());
        int n4 = (int)(this.textureOffsetY * (float)this.texture.getTextureHeight());
        n = this.textureWidth < 0.0f ? n3 - n : n3 + n;
        n2 = this.textureHeight < 0.0f ? n4 - n2 : n4 + n2;
        int n5 = n + n2 * this.texture.getTextureWidth();
        n5 *= this.texture.hasAlpha() ? 4 : 3;
        if (this.texture.hasAlpha()) {
            return new Color(this.translate(this.pixelData[n5]), this.translate(this.pixelData[n5 + 1]), this.translate(this.pixelData[n5 + 2]), this.translate(this.pixelData[n5 + 3]));
        }
        return new Color(this.translate(this.pixelData[n5]), this.translate(this.pixelData[n5 + 1]), this.translate(this.pixelData[n5 + 2]));
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }

    public void destroy() throws SlickException {
        if (this.isDestroyed()) {
            return;
        }
        this.destroyed = true;
        this.texture.release();
        GraphicsFactory.releaseGraphicsForImage(this);
    }

    public void flushPixelData() {
        this.pixelData = null;
    }
}

