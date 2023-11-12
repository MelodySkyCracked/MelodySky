/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.font;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.effects.ConfigurableEffect;
import org.newdawn.slick.util.ResourceLoader;

public class HieroSettings {
    private int fontSize = 12;
    private boolean bold = false;
    private boolean italic = false;
    private int paddingTop;
    private int paddingLeft;
    private int paddingBottom;
    private int paddingRight;
    private int paddingAdvanceX;
    private int paddingAdvanceY;
    private int glyphPageWidth = 512;
    private int glyphPageHeight = 512;
    private final List effects = new ArrayList();

    public HieroSettings() {
    }

    public HieroSettings(String string) throws SlickException {
        this(ResourceLoader.getResourceAsStream(string));
    }

    public HieroSettings(InputStream inputStream) throws SlickException {
        try {
            String string;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((string = bufferedReader.readLine()) != null) {
                if ((string = string.trim()).length() == 0) continue;
                String[] stringArray = string.split("=", 2);
                String string2 = stringArray[0].trim();
                String string3 = stringArray[1];
                if (string2.equals("font.size")) {
                    this.fontSize = Integer.parseInt(string3);
                    continue;
                }
                if (string2.equals("font.bold")) {
                    this.bold = Boolean.valueOf(string3);
                    continue;
                }
                if (string2.equals("font.italic")) {
                    this.italic = Boolean.valueOf(string3);
                    continue;
                }
                if (string2.equals("pad.top")) {
                    this.paddingTop = Integer.parseInt(string3);
                    continue;
                }
                if (string2.equals("pad.right")) {
                    this.paddingRight = Integer.parseInt(string3);
                    continue;
                }
                if (string2.equals("pad.bottom")) {
                    this.paddingBottom = Integer.parseInt(string3);
                    continue;
                }
                if (string2.equals("pad.left")) {
                    this.paddingLeft = Integer.parseInt(string3);
                    continue;
                }
                if (string2.equals("pad.advance.x")) {
                    this.paddingAdvanceX = Integer.parseInt(string3);
                    continue;
                }
                if (string2.equals("pad.advance.y")) {
                    this.paddingAdvanceY = Integer.parseInt(string3);
                    continue;
                }
                if (string2.equals("glyph.page.width")) {
                    this.glyphPageWidth = Integer.parseInt(string3);
                    continue;
                }
                if (string2.equals("glyph.page.height")) {
                    this.glyphPageHeight = Integer.parseInt(string3);
                    continue;
                }
                if (string2.equals("effect.class")) {
                    try {
                        this.effects.add(Class.forName(string3).newInstance());
                        continue;
                    }
                    catch (Exception exception) {
                        throw new SlickException("Unable to create effect instance: " + string3, exception);
                    }
                }
                if (!string2.startsWith("effect.")) continue;
                string2 = string2.substring(7);
                ConfigurableEffect configurableEffect = (ConfigurableEffect)this.effects.get(this.effects.size() - 1);
                List list = configurableEffect.getValues();
                for (ConfigurableEffect.Value value : list) {
                    if (!value.getName().equals(string2)) continue;
                    value.setString(string3);
                    break;
                }
                configurableEffect.setValues(list);
            }
            bufferedReader.close();
        }
        catch (Exception exception) {
            throw new SlickException("Unable to load Hiero font file", exception);
        }
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

    public int getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(int n) {
        this.fontSize = n;
    }

    public boolean isBold() {
        return this.bold;
    }

    public void setBold(boolean bl) {
        this.bold = bl;
    }

    public boolean isItalic() {
        return this.italic;
    }

    public void setItalic(boolean bl) {
        this.italic = bl;
    }

    public List getEffects() {
        return this.effects;
    }

    public void save(File file) throws IOException {
        PrintStream printStream = new PrintStream(new FileOutputStream(file));
        printStream.println("font.size=" + this.fontSize);
        printStream.println("font.bold=" + this.bold);
        printStream.println("font.italic=" + this.italic);
        printStream.println();
        printStream.println("pad.top=" + this.paddingTop);
        printStream.println("pad.right=" + this.paddingRight);
        printStream.println("pad.bottom=" + this.paddingBottom);
        printStream.println("pad.left=" + this.paddingLeft);
        printStream.println("pad.advance.x=" + this.paddingAdvanceX);
        printStream.println("pad.advance.y=" + this.paddingAdvanceY);
        printStream.println();
        printStream.println("glyph.page.width=" + this.glyphPageWidth);
        printStream.println("glyph.page.height=" + this.glyphPageHeight);
        printStream.println();
        for (ConfigurableEffect configurableEffect : this.effects) {
            printStream.println("effect.class=" + configurableEffect.getClass().getName());
            for (ConfigurableEffect.Value value : configurableEffect.getValues()) {
                printStream.println("effect." + value.getName() + "=" + value.getString());
            }
            printStream.println();
        }
        printStream.close();
    }
}

