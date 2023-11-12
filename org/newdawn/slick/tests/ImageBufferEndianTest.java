/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests;

import java.nio.ByteOrder;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.ImageBuffer;
import org.newdawn.slick.SlickException;

public class ImageBufferEndianTest
extends BasicGame {
    private ImageBuffer redImageBuffer;
    private ImageBuffer blueImageBuffer;
    private Image fromRed;
    private Image fromBlue;
    private String endian;

    public ImageBufferEndianTest() {
        super("ImageBuffer Endian Test");
    }

    public static void main(String[] stringArray) {
        try {
            AppGameContainer appGameContainer = new AppGameContainer(new ImageBufferEndianTest());
            appGameContainer.setDisplayMode(800, 600, false);
            appGameContainer.start();
        }
        catch (SlickException slickException) {
            slickException.printStackTrace();
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        graphics.drawString("Endianness is " + this.endian, 10.0f, 100.0f);
        graphics.drawString("Image below should be red", 10.0f, 200.0f);
        graphics.drawImage(this.fromRed, 10.0f, 220.0f);
        graphics.drawString("Image below should be blue", 410.0f, 200.0f);
        graphics.drawImage(this.fromBlue, 410.0f, 220.0f);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.endian = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN ? "Big endian" : (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? "Little endian" : "no idea");
        this.redImageBuffer = new ImageBuffer(100, 100);
        this.fillImageBufferWithColor(this.redImageBuffer, Color.red, 100, 100);
        this.blueImageBuffer = new ImageBuffer(100, 100);
        this.fillImageBufferWithColor(this.blueImageBuffer, Color.blue, 100, 100);
        this.fromRed = this.redImageBuffer.getImage();
        this.fromBlue = this.blueImageBuffer.getImage();
    }

    private void fillImageBufferWithColor(ImageBuffer imageBuffer, Color color, int n, int n2) {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                imageBuffer.setRGBA(i, j, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            }
        }
    }

    @Override
    public void update(GameContainer gameContainer, int n) throws SlickException {
    }
}

