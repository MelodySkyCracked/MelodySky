/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import java.util.ArrayList;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class FontPerformanceTest
extends BasicGame {
    private AngelCodeFont font;
    private String text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin bibendum. Aliquam ac sapien a elit congue iaculis. Quisque et justo quis mi mattis euismod. Donec elementum, mi quis aliquet varius, nisi leo volutpat magna, quis ultricies eros augue at risus. Integer non magna at lorem sodales molestie. Integer diam nulla, ornare sit amet, mattis quis, euismod et, mauris. Proin eget tellus non nisl mattis laoreet. Nunc at nunc id elit pretium tempor. Duis vulputate, nibh eget rhoncus eleifend, tellus lectus sollicitudin mi, rhoncus tincidunt nisi massa vitae ipsum. Praesent tellus diam, luctus ut, eleifend nec, auctor et, orci. Praesent eu elit. Pellentesque ante orci, volutpat placerat, ornare eget, cursus sit amet, eros. Duis pede sapien, euismod a, volutpat pellentesque, convallis eu, mauris. Nunc eros. Ut eu risus et felis laoreet viverra. Curabitur a metus.";
    private ArrayList lines = new ArrayList();
    private boolean visible = true;

    public FontPerformanceTest() {
        super("Font Performance Test");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.font = new AngelCodeFont("testdata/perffont.fnt", "testdata/perffont.png");
        for (int i = 0; i < 2; ++i) {
            int n = 90;
            for (int j = 0; j < this.text.length(); j += n) {
                if (j + n > this.text.length()) {
                    n = this.text.length() - j;
                }
                this.lines.add(this.text.substring(j, j + n));
            }
            this.lines.add("");
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) {
        graphics.setFont(this.font);
        if (this.visible) {
            for (int i = 0; i < this.lines.size(); ++i) {
                this.font.drawString(10.0f, 50 + i * 20, (String)this.lines.get(i), i > 10 ? Color.red : Color.green);
            }
        }
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }

    @Override
    public void keyPressed(int n, char c) {
        if (n == 1) {
            System.exit(0);
        }
        if (n == 57) {
            this.visible = !this.visible;
        }
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new FontPerformanceTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }
}

