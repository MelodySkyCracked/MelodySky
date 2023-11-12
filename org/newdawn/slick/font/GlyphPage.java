/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.font;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.Glyph;
import org.newdawn.slick.font.I;
import org.newdawn.slick.font.effects.Effect;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;

public class GlyphPage {
    private static final SGL GL = Renderer.get();
    public static final int MAX_GLYPH_SIZE = 256;
    private static ByteBuffer scratchByteBuffer = ByteBuffer.allocateDirect(262144);
    private static IntBuffer scratchIntBuffer;
    private static BufferedImage scratchImage;
    private static Graphics2D scratchGraphics;
    public static FontRenderContext renderContext;
    private final UnicodeFont unicodeFont;
    private final int pageWidth;
    private final int pageHeight;
    private final Image pageImage;
    private int pageX;
    private int pageY;
    private int rowHeight;
    private boolean orderAscending;
    private final List pageGlyphs = new ArrayList(32);

    public static Graphics2D getScratchGraphics() {
        return scratchGraphics;
    }

    public GlyphPage(UnicodeFont unicodeFont, int n, int n2) throws SlickException {
        this.unicodeFont = unicodeFont;
        this.pageWidth = n;
        this.pageHeight = n2;
        this.pageImage = new Image(n, n2);
    }

    public int loadGlyphs(List list, int n) throws SlickException {
        int n2;
        if (this.rowHeight != 0 && n == -1) {
            n2 = this.pageX;
            int n3 = this.pageY;
            int n4 = this.rowHeight;
            Iterator iterator = this.getIterator(list);
            while (iterator.hasNext()) {
                Glyph glyph = (Glyph)iterator.next();
                int n5 = glyph.getWidth();
                int n6 = glyph.getHeight();
                if (n2 + n5 >= this.pageWidth) {
                    n2 = 0;
                    n3 += n4;
                    n4 = n6;
                } else if (n6 > n4) {
                    n4 = n6;
                }
                if (n3 + n4 >= this.pageWidth) {
                    return 0;
                }
                n2 += n5;
            }
        }
        Color.white.bind();
        this.pageImage.bind();
        n2 = 0;
        Iterator iterator = this.getIterator(list);
        while (iterator.hasNext()) {
            Glyph glyph = (Glyph)iterator.next();
            int n7 = Math.min(256, glyph.getWidth());
            int n8 = Math.min(256, glyph.getHeight());
            if (this.rowHeight == 0) {
                this.rowHeight = n8;
            } else if (this.pageX + n7 >= this.pageWidth) {
                if (this.pageY + this.rowHeight + n8 >= this.pageHeight) break;
                this.pageX = 0;
                this.pageY += this.rowHeight;
                this.rowHeight = n8;
            } else if (n8 > this.rowHeight) {
                if (this.pageY + n8 >= this.pageHeight) break;
                this.rowHeight = n8;
            }
            this.renderGlyph(glyph, n7, n8);
            this.pageGlyphs.add(glyph);
            this.pageX += n7;
            iterator.remove();
            if (++n2 != n) continue;
            this.orderAscending = !this.orderAscending;
            break;
        }
        TextureImpl.bindNone();
        this.orderAscending = !this.orderAscending;
        return n2;
    }

    private void renderGlyph(Glyph glyph, int n, int n2) throws SlickException {
        scratchGraphics.setComposite(AlphaComposite.Clear);
        scratchGraphics.fillRect(0, 0, 256, 256);
        scratchGraphics.setComposite(AlphaComposite.SrcOver);
        scratchGraphics.setColor(java.awt.Color.white);
        Object object = this.unicodeFont.getEffects().iterator();
        while (object.hasNext()) {
            ((Effect)object.next()).draw(scratchImage, scratchGraphics, this.unicodeFont, glyph);
        }
        glyph.setShape(null);
        object = scratchImage.getRaster();
        int[] nArray = new int[n];
        for (int i = 0; i < n2; ++i) {
            ((Raster)object).getDataElements(0, i, n, 1, nArray);
            scratchIntBuffer.put(nArray);
        }
        GL.glTexSubImage2D(3553, 0, this.pageX, this.pageY, n, n2, 32993, 5121, scratchByteBuffer);
        scratchIntBuffer.clear();
        glyph.setImage(this.pageImage.getSubImage(this.pageX, this.pageY, n, n2));
    }

    private Iterator getIterator(List list) {
        if (this.orderAscending) {
            return list.iterator();
        }
        ListIterator listIterator = list.listIterator(list.size());
        return new I(this, listIterator);
    }

    public List getGlyphs() {
        return this.pageGlyphs;
    }

    public Image getImage() {
        return this.pageImage;
    }

    static {
        scratchByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        scratchIntBuffer = scratchByteBuffer.asIntBuffer();
        scratchImage = new BufferedImage(256, 256, 2);
        scratchGraphics = (Graphics2D)scratchImage.getGraphics();
        scratchGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        scratchGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        scratchGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        renderContext = scratchGraphics.getFontRenderContext();
    }
}

