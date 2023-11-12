/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.llI;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

public class AngelCodeFont
implements Font {
    private static SGL GL = Renderer.get();
    private static final int DISPLAY_LIST_CACHE_SIZE = 200;
    private static final int MAX_CHAR = 255;
    private boolean displayListCaching = true;
    private Image fontImage;
    private CharDef[] chars;
    private int lineHeight;
    private int baseDisplayListID = -1;
    private int eldestDisplayListID;
    private DisplayList eldestDisplayList;
    private final LinkedHashMap displayLists = new llI(this, 200, 1.0f, true);

    public AngelCodeFont(String string, Image image) throws SlickException {
        this.fontImage = image;
        this.parseFnt(ResourceLoader.getResourceAsStream(string));
    }

    public AngelCodeFont(String string, String string2) throws SlickException {
        this.fontImage = new Image(string2);
        this.parseFnt(ResourceLoader.getResourceAsStream(string));
    }

    public AngelCodeFont(String string, Image image, boolean bl) throws SlickException {
        this.fontImage = image;
        this.displayListCaching = bl;
        this.parseFnt(ResourceLoader.getResourceAsStream(string));
    }

    public AngelCodeFont(String string, String string2, boolean bl) throws SlickException {
        this.fontImage = new Image(string2);
        this.displayListCaching = bl;
        this.parseFnt(ResourceLoader.getResourceAsStream(string));
    }

    public AngelCodeFont(String string, InputStream inputStream, InputStream inputStream2) throws SlickException {
        this.fontImage = new Image(inputStream2, string, false);
        this.parseFnt(inputStream);
    }

    public AngelCodeFont(String string, InputStream inputStream, InputStream inputStream2, boolean bl) throws SlickException {
        this.fontImage = new Image(inputStream2, string, false);
        this.displayListCaching = bl;
        this.parseFnt(inputStream);
    }

    private void parseFnt(InputStream inputStream) throws SlickException {
        if (this.displayListCaching) {
            this.baseDisplayListID = GL.glGenLists(200);
            if (this.baseDisplayListID == 0) {
                this.displayListCaching = false;
            }
        }
        try {
            short s;
            Object string4;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String string = bufferedReader.readLine();
            String string2 = bufferedReader.readLine();
            String string3 = bufferedReader.readLine();
            HashMap<Short, ArrayList<Short>> hashMap = new HashMap<Short, ArrayList<Short>>(64);
            ArrayList<CharDef> arrayList = new ArrayList<CharDef>(255);
            int n = 0;
            boolean bl = false;
            while (!bl) {
                CharDef charDef;
                string4 = bufferedReader.readLine();
                if (string4 == null) {
                    bl = true;
                    continue;
                }
                if (!((String)string4).startsWith("chars c") && ((String)string4).startsWith("char") && (charDef = this.parseChar((String)string4)) != null) {
                    n = Math.max(n, charDef.id);
                    arrayList.add(charDef);
                }
                if (((String)string4).startsWith("kernings c") || !((String)string4).startsWith("kerning")) continue;
                StringTokenizer stringTokenizer = new StringTokenizer((String)string4, " =");
                stringTokenizer.nextToken();
                stringTokenizer.nextToken();
                s = Short.parseShort(stringTokenizer.nextToken());
                stringTokenizer.nextToken();
                int n2 = Integer.parseInt(stringTokenizer.nextToken());
                stringTokenizer.nextToken();
                int n3 = Integer.parseInt(stringTokenizer.nextToken());
                ArrayList<Short> arrayList2 = (ArrayList<Short>)hashMap.get(new Short(s));
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList<Short>();
                    hashMap.put(new Short(s), arrayList2);
                }
                arrayList2.add(new Short((short)(n3 << 8 | n2)));
            }
            this.chars = new CharDef[n + 1];
            string4 = arrayList.iterator();
            while (string4.hasNext()) {
                CharDef charDef;
                this.chars[charDef.id] = charDef = (CharDef)string4.next();
            }
            for (Map.Entry entry : hashMap.entrySet()) {
                s = (Short)entry.getKey();
                List list = (List)entry.getValue();
                short[] sArray = new short[list.size()];
                int n4 = 0;
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    sArray[n4] = (Short)iterator.next();
                    ++n4;
                }
                this.chars[s].kerning = sArray;
            }
        }
        catch (IOException iOException) {
            Log.error(iOException);
            throw new SlickException("Failed to parse font file: " + inputStream);
        }
    }

    private CharDef parseChar(String string) throws SlickException {
        CharDef charDef = new CharDef(this, null);
        StringTokenizer stringTokenizer = new StringTokenizer(string, " =");
        stringTokenizer.nextToken();
        stringTokenizer.nextToken();
        charDef.id = Short.parseShort(stringTokenizer.nextToken());
        if (charDef.id < 0) {
            return null;
        }
        if (charDef.id > 255) {
            throw new SlickException("Invalid character '" + charDef.id + "': AngelCodeFont does not support characters above " + 255);
        }
        stringTokenizer.nextToken();
        charDef.x = Short.parseShort(stringTokenizer.nextToken());
        stringTokenizer.nextToken();
        charDef.y = Short.parseShort(stringTokenizer.nextToken());
        stringTokenizer.nextToken();
        charDef.width = Short.parseShort(stringTokenizer.nextToken());
        stringTokenizer.nextToken();
        charDef.height = Short.parseShort(stringTokenizer.nextToken());
        stringTokenizer.nextToken();
        charDef.xoffset = Short.parseShort(stringTokenizer.nextToken());
        stringTokenizer.nextToken();
        charDef.yoffset = Short.parseShort(stringTokenizer.nextToken());
        stringTokenizer.nextToken();
        charDef.xadvance = Short.parseShort(stringTokenizer.nextToken());
        charDef.init();
        if (charDef.id != 32) {
            this.lineHeight = Math.max(charDef.height + charDef.yoffset, this.lineHeight);
        }
        return charDef;
    }

    @Override
    public void drawString(float f, float f2, String string) {
        this.drawString(f, f2, string, Color.white);
    }

    @Override
    public void drawString(float f, float f2, String string, Color color) {
        this.drawString(f, f2, string, color, 0, string.length() - 1);
    }

    @Override
    public void drawString(float f, float f2, String string, Color color, int n, int n2) {
        this.fontImage.bind();
        color.bind();
        GL.glTranslatef(f, f2, 0.0f);
        if (this.displayListCaching && n == 0 && n2 == string.length() - 1) {
            DisplayList displayList = (DisplayList)this.displayLists.get(string);
            if (displayList != null) {
                GL.glCallList(displayList.id);
            } else {
                displayList = new DisplayList(null);
                displayList.text = string;
                int n3 = this.displayLists.size();
                if (n3 < 200) {
                    displayList.id = this.baseDisplayListID + n3;
                } else {
                    displayList.id = this.eldestDisplayListID;
                    this.displayLists.remove(this.eldestDisplayList.text);
                }
                this.displayLists.put(string, displayList);
                GL.glNewList(displayList.id, 4865);
                this.render(string, n, n2);
                GL.glEndList();
            }
        } else {
            this.render(string, n, n2);
        }
        GL.glTranslatef(-f, -f2, 0.0f);
    }

    private void render(String string, int n, int n2) {
        GL.glBegin(7);
        int n3 = 0;
        int n4 = 0;
        CharDef charDef = null;
        char[] cArray = string.toCharArray();
        for (int i = 0; i < cArray.length; ++i) {
            CharDef charDef2;
            char c = cArray[i];
            if (c == '\n') {
                n3 = 0;
                n4 += this.getLineHeight();
                continue;
            }
            if (c >= this.chars.length || (charDef2 = this.chars[c]) == null) continue;
            if (charDef != null) {
                n3 += charDef.getKerning(c);
            }
            charDef = charDef2;
            if (i >= n && i <= n2) {
                charDef2.draw(n3, n4);
            }
            n3 += charDef2.xadvance;
        }
        GL.glEnd();
    }

    public int getYOffset(String string) {
        DisplayList displayList = null;
        if (this.displayListCaching && (displayList = (DisplayList)this.displayLists.get(string)) != null && displayList.yOffset != null) {
            return displayList.yOffset.intValue();
        }
        int n = string.indexOf(10);
        if (n == -1) {
            n = string.length();
        }
        int n2 = 10000;
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            CharDef charDef = this.chars[c];
            if (charDef == null) continue;
            n2 = Math.min(charDef.yoffset, n2);
        }
        if (displayList != null) {
            displayList.yOffset = new Short((short)n2);
        }
        return n2;
    }

    @Override
    public int getHeight(String string) {
        DisplayList displayList = null;
        if (this.displayListCaching && (displayList = (DisplayList)this.displayLists.get(string)) != null && displayList.height != null) {
            return displayList.height.intValue();
        }
        int n = 0;
        int n2 = 0;
        for (int i = 0; i < string.length(); ++i) {
            CharDef charDef;
            char c = string.charAt(i);
            if (c == '\n') {
                ++n;
                n2 = 0;
                continue;
            }
            if (c == ' ' || (charDef = this.chars[c]) == null) continue;
            n2 = Math.max(charDef.height + charDef.yoffset, n2);
        }
        n2 += n * this.getLineHeight();
        if (displayList != null) {
            displayList.height = new Short((short)n2);
        }
        return n2;
    }

    @Override
    public int getWidth(String string) {
        DisplayList displayList = null;
        if (this.displayListCaching && (displayList = (DisplayList)this.displayLists.get(string)) != null && displayList.width != null) {
            return displayList.width.intValue();
        }
        int n = 0;
        int n2 = 0;
        CharDef charDef = null;
        int n3 = string.length();
        for (int i = 0; i < n3; ++i) {
            CharDef charDef2;
            char c = string.charAt(i);
            if (c == '\n') {
                n2 = 0;
                continue;
            }
            if (c >= this.chars.length || (charDef2 = this.chars[c]) == null) continue;
            if (charDef != null) {
                n2 += charDef.getKerning(c);
            }
            charDef = charDef2;
            n2 = i < n3 - 1 ? (n2 += charDef2.xadvance) : (n2 += charDef2.width);
            n = Math.max(n, n2);
        }
        if (displayList != null) {
            displayList.width = new Short((short)n);
        }
        return n;
    }

    @Override
    public int getLineHeight() {
        return this.lineHeight;
    }

    static DisplayList access$002(AngelCodeFont angelCodeFont, DisplayList displayList) {
        angelCodeFont.eldestDisplayList = displayList;
        return angelCodeFont.eldestDisplayList;
    }

    static int access$102(AngelCodeFont angelCodeFont, int n) {
        angelCodeFont.eldestDisplayListID = n;
        return angelCodeFont.eldestDisplayListID;
    }

    static DisplayList access$000(AngelCodeFont angelCodeFont) {
        return angelCodeFont.eldestDisplayList;
    }

    static Image access$400(AngelCodeFont angelCodeFont) {
        return angelCodeFont.fontImage;
    }

    private static class DisplayList {
        int id;
        Short yOffset;
        Short width;
        Short height;
        String text;

        private DisplayList() {
        }

        DisplayList(llI llI2) {
            this();
        }
    }

    private class CharDef {
        public short id;
        public short x;
        public short y;
        public short width;
        public short height;
        public short xoffset;
        public short yoffset;
        public short xadvance;
        public Image image;
        public short dlIndex;
        public short[] kerning;
        final AngelCodeFont this$0;

        private CharDef(AngelCodeFont angelCodeFont) {
            this.this$0 = angelCodeFont;
        }

        public void init() {
            this.image = AngelCodeFont.access$400(this.this$0).getSubImage(this.x, this.y, this.width, this.height);
        }

        public String toString() {
            return "[CharDef id=" + this.id + " x=" + this.x + " y=" + this.y + "]";
        }

        public void draw(float f, float f2) {
            this.image.drawEmbedded(f + (float)this.xoffset, f2 + (float)this.yoffset, this.width, this.height);
        }

        public int getKerning(int n) {
            if (this.kerning == null) {
                return 0;
            }
            int n2 = 0;
            int n3 = this.kerning.length - 1;
            while (n2 <= n3) {
                int n4 = n2 + n3 >>> 1;
                short s = this.kerning[n4];
                int n5 = s & 0xFF;
                if (n5 < n) {
                    n2 = n4 + 1;
                    continue;
                }
                if (n5 > n) {
                    n3 = n4 - 1;
                    continue;
                }
                return s >> 8;
            }
            return 0;
        }

        CharDef(AngelCodeFont angelCodeFont, llI llI2) {
            this(angelCodeFont);
        }
    }
}

