/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

public class PackedSpriteSheet {
    private Image image;
    private String basePath;
    private HashMap sections = new HashMap();
    private int filter = 2;

    public PackedSpriteSheet(String string) throws SlickException {
        this(string, null);
    }

    public PackedSpriteSheet(String string, Color color) throws SlickException {
        string = string.replace('\\', '/');
        this.basePath = string.substring(0, string.lastIndexOf("/") + 1);
        this.loadDefinition(string, color);
    }

    public PackedSpriteSheet(String string, int n) throws SlickException {
        this(string, n, null);
    }

    public PackedSpriteSheet(String string, int n, Color color) throws SlickException {
        this.filter = n;
        string = string.replace('\\', '/');
        this.basePath = string.substring(0, string.lastIndexOf("/") + 1);
        this.loadDefinition(string, color);
    }

    public Image getFullImage() {
        return this.image;
    }

    public Image getSprite(String string) {
        Section section = (Section)this.sections.get(string);
        if (section == null) {
            throw new RuntimeException("Unknown sprite from packed sheet: " + string);
        }
        return this.image.getSubImage(section.x, section.y, section.width, section.height);
    }

    public SpriteSheet getSpriteSheet(String string) {
        Image image = this.getSprite(string);
        Section section = (Section)this.sections.get(string);
        return new SpriteSheet(image, section.width / section.tilesx, section.height / section.tilesy);
    }

    private void loadDefinition(String string, Color color) throws SlickException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ResourceLoader.getResourceAsStream(string)));
        try {
            this.image = new Image(this.basePath + bufferedReader.readLine(), false, this.filter, color);
            while (bufferedReader.ready() && bufferedReader.readLine() != null) {
                Section section = new Section(this, bufferedReader);
                this.sections.put(section.name, section);
                if (bufferedReader.readLine() != null) continue;
                break;
            }
        }
        catch (Exception exception) {
            Log.error(exception);
            throw new SlickException("Failed to process definitions file - invalid format?", exception);
        }
    }

    private class Section {
        public int x;
        public int y;
        public int width;
        public int height;
        public int tilesx;
        public int tilesy;
        public String name;
        final PackedSpriteSheet this$0;

        public Section(PackedSpriteSheet packedSpriteSheet, BufferedReader bufferedReader) throws IOException {
            this.this$0 = packedSpriteSheet;
            this.name = bufferedReader.readLine().trim();
            this.x = Integer.parseInt(bufferedReader.readLine().trim());
            this.y = Integer.parseInt(bufferedReader.readLine().trim());
            this.width = Integer.parseInt(bufferedReader.readLine().trim());
            this.height = Integer.parseInt(bufferedReader.readLine().trim());
            this.tilesx = Integer.parseInt(bufferedReader.readLine().trim());
            this.tilesy = Integer.parseInt(bufferedReader.readLine().trim());
            bufferedReader.readLine().trim();
            bufferedReader.readLine().trim();
            this.tilesx = Math.max(1, this.tilesx);
            this.tilesy = Math.max(1, this.tilesy);
        }
    }
}

