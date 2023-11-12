/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick;

import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.font.GlyphVector;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.Glyph;
import org.newdawn.slick.font.GlyphPage;
import org.newdawn.slick.font.HieroSettings;
import org.newdawn.slick.l;
import org.newdawn.slick.ll;
import org.newdawn.slick.llIl;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.ResourceLoader;

public class UnicodeFont
implements Font {
    private static final int DISPLAY_LIST_CACHE_SIZE = 200;
    private static final int MAX_GLYPH_CODE = 0x10FFFF;
    private static final int PAGE_SIZE = 512;
    private static final int PAGES = 2175;
    private static final SGL GL = Renderer.get();
    private static final DisplayList EMPTY_DISPLAY_LIST = new DisplayList();
    private static final Comparator heightComparator = new ll();
    private java.awt.Font font;
    private String ttfFileRef;
    private int ascent;
    private int descent;
    private int leading;
    private int spaceWidth;
    private final Glyph[][] glyphs = new Glyph[2175][];
    private final List glyphPages = new ArrayList();
    private final List queuedGlyphs = new ArrayList(256);
    private final List effects = new ArrayList();
    private int paddingTop;
    private int paddingLeft;
    private int paddingBottom;
    private int paddingRight;
    private int paddingAdvanceX;
    private int paddingAdvanceY;
    private Glyph missingGlyph;
    private int glyphPageWidth = 512;
    private int glyphPageHeight = 512;
    private boolean displayListCaching = true;
    private int baseDisplayListID = -1;
    private int eldestDisplayListID;
    private DisplayList eldestDisplayList;
    private final LinkedHashMap displayLists = new llIl(this, 200, 1.0f, true);

    private static java.awt.Font createFont(String string) throws SlickException {
        try {
            return java.awt.Font.createFont(0, ResourceLoader.getResourceAsStream(string));
        }
        catch (FontFormatException fontFormatException) {
            throw new SlickException("Invalid font: " + string, fontFormatException);
        }
        catch (IOException iOException) {
            throw new SlickException("Error reading font: " + string, iOException);
        }
    }

    public UnicodeFont(String string, String string2) throws SlickException {
        this(string, new HieroSettings(string2));
    }

    public UnicodeFont(String string, HieroSettings hieroSettings) throws SlickException {
        this.ttfFileRef = string;
        java.awt.Font font = UnicodeFont.createFont(string);
        this.initializeFont(font, hieroSettings.getFontSize(), hieroSettings.isBold(), hieroSettings.isItalic());
        this.loadSettings(hieroSettings);
    }

    public UnicodeFont(String string, int n, boolean bl, boolean bl2) throws SlickException {
        this.ttfFileRef = string;
        this.initializeFont(UnicodeFont.createFont(string), n, bl, bl2);
    }

    public UnicodeFont(java.awt.Font font, String string) throws SlickException {
        this(font, new HieroSettings(string));
    }

    public UnicodeFont(java.awt.Font font, HieroSettings hieroSettings) {
        this.initializeFont(font, hieroSettings.getFontSize(), hieroSettings.isBold(), hieroSettings.isItalic());
        this.loadSettings(hieroSettings);
    }

    public UnicodeFont(java.awt.Font font) {
        this.initializeFont(font, font.getSize(), font.isBold(), font.isItalic());
    }

    public UnicodeFont(java.awt.Font font, int n, boolean bl, boolean bl2) {
        this.initializeFont(font, n, bl, bl2);
    }

    private void initializeFont(java.awt.Font font, int n, boolean bl, boolean bl2) {
        Map<TextAttribute, ?> map = font.getAttributes();
        map.put(TextAttribute.SIZE, new Float(n));
        map.put(TextAttribute.WEIGHT, bl ? TextAttribute.WEIGHT_BOLD : TextAttribute.WEIGHT_REGULAR);
        map.put(TextAttribute.POSTURE, bl2 ? TextAttribute.POSTURE_OBLIQUE : TextAttribute.POSTURE_REGULAR);
        try {
            map.put((TextAttribute)TextAttribute.class.getDeclaredField("KERNING").get(null), TextAttribute.class.getDeclaredField("KERNING_ON").get(null));
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.font = font.deriveFont(map);
        FontMetrics fontMetrics = GlyphPage.getScratchGraphics().getFontMetrics(this.font);
        this.ascent = fontMetrics.getAscent();
        this.descent = fontMetrics.getDescent();
        this.leading = fontMetrics.getLeading();
        char[] cArray = " ".toCharArray();
        GlyphVector glyphVector = this.font.layoutGlyphVector(GlyphPage.renderContext, cArray, 0, cArray.length, 0);
        this.spaceWidth = glyphVector.getGlyphLogicalBounds((int)0).getBounds().width;
    }

    private void loadSettings(HieroSettings hieroSettings) {
        this.paddingTop = hieroSettings.getPaddingTop();
        this.paddingLeft = hieroSettings.getPaddingLeft();
        this.paddingBottom = hieroSettings.getPaddingBottom();
        this.paddingRight = hieroSettings.getPaddingRight();
        this.paddingAdvanceX = hieroSettings.getPaddingAdvanceX();
        this.paddingAdvanceY = hieroSettings.getPaddingAdvanceY();
        this.glyphPageWidth = hieroSettings.getGlyphPageWidth();
        this.glyphPageHeight = hieroSettings.getGlyphPageHeight();
        this.effects.addAll(hieroSettings.getEffects());
    }

    public void addGlyphs(int n, int n2) {
        for (int i = n; i <= n2; ++i) {
            this.addGlyphs(new String(Character.toChars(i)));
        }
    }

    public void addGlyphs(String string) {
        if (string == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        char[] cArray = string.toCharArray();
        GlyphVector glyphVector = this.font.layoutGlyphVector(GlyphPage.renderContext, cArray, 0, cArray.length, 0);
        int n = glyphVector.getNumGlyphs();
        for (int i = 0; i < n; ++i) {
            int n2 = string.codePointAt(glyphVector.getGlyphCharIndex(i));
            Rectangle rectangle = this.getGlyphBounds(glyphVector, i, n2);
            this.getGlyph(glyphVector.getGlyphCode(i), n2, rectangle, glyphVector, i);
        }
    }

    public void addAsciiGlyphs() {
        this.addGlyphs(32, 255);
    }

    public void addNeheGlyphs() {
        this.addGlyphs(32, 128);
    }

    public boolean loadGlyphs() throws SlickException {
        return this.loadGlyphs(-1);
    }

    public boolean loadGlyphs(int n) throws SlickException {
        if (this.queuedGlyphs.isEmpty()) {
            return false;
        }
        if (this.effects.isEmpty()) {
            throw new IllegalStateException("The UnicodeFont must have at least one effect before any glyphs can be loaded.");
        }
        Object object = this.queuedGlyphs.iterator();
        while (object.hasNext()) {
            Object object2 = (Glyph)object.next();
            int n2 = ((Glyph)object2).getCodePoint();
            if (((Glyph)object2).getWidth() == 0 || n2 == 32) {
                object.remove();
                continue;
            }
            if (!((Glyph)object2).isMissing()) continue;
            if (this.missingGlyph != null) {
                if (object2 == this.missingGlyph) continue;
                object.remove();
                continue;
            }
            this.missingGlyph = object2;
        }
        Collections.sort(this.queuedGlyphs, heightComparator);
        for (Object object2 : this.glyphPages) {
            if ((n -= ((GlyphPage)object2).loadGlyphs(this.queuedGlyphs, n)) != 0 && !this.queuedGlyphs.isEmpty()) continue;
            return true;
        }
        while (!this.queuedGlyphs.isEmpty()) {
            object = new GlyphPage(this, this.glyphPageWidth, this.glyphPageHeight);
            this.glyphPages.add(object);
            if ((n -= ((GlyphPage)object).loadGlyphs(this.queuedGlyphs, n)) != 0) continue;
            return true;
        }
        return true;
    }

    public void clearGlyphs() {
        for (int i = 0; i < 2175; ++i) {
            this.glyphs[i] = null;
        }
        for (GlyphPage glyphPage : this.glyphPages) {
            try {
                glyphPage.getImage().destroy();
            }
            catch (SlickException slickException) {}
        }
        this.glyphPages.clear();
        if (this.baseDisplayListID != -1) {
            GL.glDeleteLists(this.baseDisplayListID, this.displayLists.size());
            this.baseDisplayListID = -1;
        }
        this.queuedGlyphs.clear();
        this.missingGlyph = null;
    }

    public void destroy() {
        this.clearGlyphs();
    }

    /*
     * Enabled aggressive block sorting
     */
    public DisplayList drawDisplayList(float f, float f2, String string, Color color, int n, int n2) {
        if (string == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        if (string.length() == 0) {
            return EMPTY_DISPLAY_LIST;
        }
        if (color == null) {
            throw new IllegalArgumentException("color cannot be null.");
        }
        f -= (float)this.paddingLeft;
        f2 -= (float)this.paddingTop;
        String string2 = string.substring(n, n2);
        color.bind();
        TextureImpl.bindNone();
        DisplayList displayList = null;
        if (this.displayListCaching && this.queuedGlyphs.isEmpty()) {
            if (this.baseDisplayListID == -1) {
                this.baseDisplayListID = GL.glGenLists(200);
                if (this.baseDisplayListID == 0) {
                    this.baseDisplayListID = -1;
                    this.displayListCaching = false;
                    return new DisplayList();
                }
            }
            if ((displayList = (DisplayList)this.displayLists.get(string2)) != null) {
                if (!displayList.invalid) {
                    GL.glTranslatef(f, f2, 0.0f);
                    GL.glCallList(displayList.id);
                    GL.glTranslatef(-f, -f2, 0.0f);
                    return displayList;
                }
                displayList.invalid = false;
            } else if (displayList == null) {
                displayList = new DisplayList();
                int n3 = this.displayLists.size();
                this.displayLists.put(string2, displayList);
                displayList.id = n3 < 200 ? this.baseDisplayListID + n3 : this.eldestDisplayListID;
            }
            this.displayLists.put(string2, displayList);
        }
        GL.glTranslatef(f, f2, 0.0f);
        if (displayList != null) {
            GL.glNewList(displayList.id, 4865);
        }
        char[] cArray = string.substring(0, n2).toCharArray();
        GlyphVector glyphVector = this.font.layoutGlyphVector(GlyphPage.renderContext, cArray, 0, cArray.length, 0);
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = this.ascent;
        boolean bl = false;
        Texture texture = null;
        int n9 = glyphVector.getNumGlyphs();
        for (int i = 0; i < n9; ++i) {
            Image image;
            int n10 = glyphVector.getGlyphCharIndex(i);
            if (n10 < n) continue;
            if (n10 > n2) break;
            int n11 = string.codePointAt(n10);
            Rectangle rectangle = this.getGlyphBounds(glyphVector, i, n11);
            Glyph glyph = this.getGlyph(glyphVector.getGlyphCode(i), n11, rectangle, glyphVector, i);
            if (bl && n11 != 10) {
                n7 = -rectangle.x;
                bl = false;
            }
            if ((image = glyph.getImage()) == null && this.missingGlyph != null && glyph.isMissing()) {
                image = this.missingGlyph.getImage();
            }
            if (image != null) {
                Texture texture2 = image.getTexture();
                if (texture != null && texture != texture2) {
                    GL.glEnd();
                    texture = null;
                }
                if (texture == null) {
                    texture2.bind();
                    GL.glBegin(7);
                    texture = texture2;
                }
                image.drawEmbedded(rectangle.x + n7, rectangle.y + n8, image.getWidth(), image.getHeight());
            }
            if (i >= 0) {
                n7 += this.paddingRight + this.paddingLeft + this.paddingAdvanceX;
            }
            n4 = Math.max(n4, rectangle.x + n7 + rectangle.width);
            n5 = Math.max(n5, this.ascent + rectangle.y + rectangle.height);
            if (n11 != 10) continue;
            bl = true;
            n8 += this.getLineHeight();
            ++n6;
            n5 = 0;
        }
        if (texture != null) {
            GL.glEnd();
        }
        if (displayList != null) {
            GL.glEndList();
            if (!this.queuedGlyphs.isEmpty()) {
                displayList.invalid = true;
            }
        }
        GL.glTranslatef(-f, -f2, 0.0f);
        if (displayList == null) {
            displayList = new DisplayList();
        }
        displayList.width = (short)n4;
        displayList.height = (short)(n6 * this.getLineHeight() + n5);
        return displayList;
    }

    @Override
    public void drawString(float f, float f2, String string, Color color, int n, int n2) {
        this.drawDisplayList(f, f2, string, color, n, n2);
    }

    @Override
    public void drawString(float f, float f2, String string) {
        this.drawString(f, f2, string, Color.white);
    }

    @Override
    public void drawString(float f, float f2, String string, Color color) {
        this.drawString(f, f2, string, color, 0, string.length());
    }

    private Glyph getGlyph(int n, int n2, Rectangle rectangle, GlyphVector glyphVector, int n3) {
        if (n < 0 || n >= 0x10FFFF) {
            return new l(this, n2, rectangle, glyphVector, n3, this);
        }
        int n4 = n / 512;
        int n5 = n & 0x1FF;
        Glyph glyph = null;
        Glyph[] glyphArray = this.glyphs[n4];
        if (glyphArray != null) {
            glyph = glyphArray[n5];
            if (glyph != null) {
                return glyph;
            }
        } else {
            this.glyphs[n4] = new Glyph[512];
            glyphArray = this.glyphs[n4];
        }
        glyph = glyphArray[n5] = new Glyph(n2, rectangle, glyphVector, n3, this);
        this.queuedGlyphs.add(glyph);
        return glyph;
    }

    private Rectangle getGlyphBounds(GlyphVector glyphVector, int n, int n2) {
        Rectangle rectangle = glyphVector.getGlyphPixelBounds(n, GlyphPage.renderContext, 0.0f, 0.0f);
        if (n2 == 32) {
            rectangle.width = this.spaceWidth;
        }
        return rectangle;
    }

    public int getSpaceWidth() {
        return this.spaceWidth;
    }

    @Override
    public int getWidth(String string) {
        Object object;
        if (string == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        if (string.length() == 0) {
            return 0;
        }
        if (this.displayListCaching && (object = (Object)((DisplayList)this.displayLists.get(string))) != null) {
            return object.width;
        }
        object = string.toCharArray();
        GlyphVector glyphVector = this.font.layoutGlyphVector(GlyphPage.renderContext, (char[])object, 0, ((char[])object).length, 0);
        int n = 0;
        int n2 = 0;
        boolean bl = false;
        int n3 = glyphVector.getNumGlyphs();
        for (int i = 0; i < n3; ++i) {
            int n4 = glyphVector.getGlyphCharIndex(i);
            int n5 = string.codePointAt(n4);
            Rectangle rectangle = this.getGlyphBounds(glyphVector, i, n5);
            if (bl && n5 != 10) {
                n2 = -rectangle.x;
            }
            if (i > 0) {
                n2 += this.paddingLeft + this.paddingRight + this.paddingAdvanceX;
            }
            n = Math.max(n, rectangle.x + n2 + rectangle.width);
            if (n5 != 10) continue;
            bl = true;
        }
        return n;
    }

    @Override
    public int getHeight(String string) {
        Object object;
        if (string == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        if (string.length() == 0) {
            return 0;
        }
        if (this.displayListCaching && (object = (Object)((DisplayList)this.displayLists.get(string))) != null) {
            return object.height;
        }
        object = string.toCharArray();
        GlyphVector glyphVector = this.font.layoutGlyphVector(GlyphPage.renderContext, (char[])object, 0, ((char[])object).length, 0);
        int n = 0;
        int n2 = 0;
        int n3 = glyphVector.getNumGlyphs();
        for (int i = 0; i < n3; ++i) {
            int n4 = glyphVector.getGlyphCharIndex(i);
            int n5 = string.codePointAt(n4);
            if (n5 == 32) continue;
            Rectangle rectangle = this.getGlyphBounds(glyphVector, i, n5);
            n2 = Math.max(n2, this.ascent + rectangle.y + rectangle.height);
            if (n5 != 10) continue;
            ++n;
            n2 = 0;
        }
        return n * this.getLineHeight() + n2;
    }

    public int getYOffset(String string) {
        if (string == null) {
            throw new IllegalArgumentException("text cannot be null.");
        }
        DisplayList displayList = null;
        if (this.displayListCaching && (displayList = (DisplayList)this.displayLists.get(string)) != null && displayList.yOffset != null) {
            return displayList.yOffset.intValue();
        }
        int n = string.indexOf(10);
        if (n != -1) {
            string = string.substring(0, n);
        }
        char[] cArray = string.toCharArray();
        GlyphVector glyphVector = this.font.layoutGlyphVector(GlyphPage.renderContext, cArray, 0, cArray.length, 0);
        int n2 = this.ascent + glyphVector.getPixelBounds(null, (float)0.0f, (float)0.0f).y;
        if (displayList != null) {
            displayList.yOffset = new Short((short)n2);
        }
        return n2;
    }

    public java.awt.Font getFont() {
        return this.font;
    }

    public int getPaddingTop() {
        return this.paddingTop;
    }

    public void setPaddingTop(int n) {
        this.paddingTop = n;
    }

    public int getPaddingLeft() {
        return this.paddingLeft;
    }

    public void setPaddingLeft(int n) {
        this.paddingLeft = n;
    }

    public int getPaddingBottom() {
        return this.paddingBottom;
    }

    public void setPaddingBottom(int n) {
        this.paddingBottom = n;
    }

    public int getPaddingRight() {
        return this.paddingRight;
    }

    public void setPaddingRight(int n) {
        this.paddingRight = n;
    }

    public int getPaddingAdvanceX() {
        return this.paddingAdvanceX;
    }

    public void setPaddingAdvanceX(int n) {
        this.paddingAdvanceX = n;
    }

    public int getPaddingAdvanceY() {
        return this.paddingAdvanceY;
    }

    public void setPaddingAdvanceY(int n) {
        this.paddingAdvanceY = n;
    }

    @Override
    public int getLineHeight() {
        return this.descent + this.ascent + this.leading + this.paddingTop + this.paddingBottom + this.paddingAdvanceY;
    }

    public int getAscent() {
        return this.ascent;
    }

    public int getDescent() {
        return this.descent;
    }

    public int getLeading() {
        return this.leading;
    }

    public int getGlyphPageWidth() {
        return this.glyphPageWidth;
    }

    public void setGlyphPageWidth(int n) {
        this.glyphPageWidth = n;
    }

    public int getGlyphPageHeight() {
        return this.glyphPageHeight;
    }

    public void setGlyphPageHeight(int n) {
        this.glyphPageHeight = n;
    }

    public List getGlyphPages() {
        return this.glyphPages;
    }

    public List getEffects() {
        return this.effects;
    }

    public boolean isCaching() {
        return this.displayListCaching;
    }

    public void setDisplayListCaching(boolean bl) {
        this.displayListCaching = bl;
    }

    public String getFontFile() {
        if (this.ttfFileRef == null) {
            try {
                Object object = Class.forName("sun.font.FontManager").getDeclaredMethod("getFont2D", java.awt.Font.class).invoke(null, this.font);
                Field field = Class.forName("sun.font.PhysicalFont").getDeclaredField("platName");
                field.setAccessible(true);
                this.ttfFileRef = (String)field.get(object);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            if (this.ttfFileRef == null) {
                this.ttfFileRef = "";
            }
        }
        if (this.ttfFileRef.length() == 0) {
            return null;
        }
        return this.ttfFileRef;
    }

    static int access$002(UnicodeFont unicodeFont, int n) {
        unicodeFont.eldestDisplayListID = n;
        return unicodeFont.eldestDisplayListID;
    }

    public static class DisplayList {
        boolean invalid;
        int id;
        Short yOffset;
        public short width;
        public short height;
        public Object userData;

        DisplayList() {
        }
    }
}

