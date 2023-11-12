/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.font.effects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.Glyph;
import org.newdawn.slick.font.effects.Effect;
import org.newdawn.slick.font.effects.EffectUtil;

public class FilterEffect
implements Effect {
    private BufferedImageOp filter;

    public FilterEffect() {
    }

    public FilterEffect(BufferedImageOp bufferedImageOp) {
        this.filter = bufferedImageOp;
    }

    @Override
    public void draw(BufferedImage bufferedImage, Graphics2D graphics2D, UnicodeFont unicodeFont, Glyph glyph) {
        BufferedImage bufferedImage2 = EffectUtil.getScratchImage();
        this.filter.filter(bufferedImage, bufferedImage2);
        bufferedImage.getGraphics().drawImage(bufferedImage2, 0, 0, null);
    }

    public BufferedImageOp getFilter() {
        return this.filter;
    }

    public void setFilter(BufferedImageOp bufferedImageOp) {
        this.filter = bufferedImageOp;
    }
}

