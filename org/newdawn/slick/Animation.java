/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.Sys
 */
package org.newdawn.slick;

import java.util.ArrayList;
import org.lwjgl.Sys;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.Log;

public class Animation
implements Renderable {
    private ArrayList frames = new ArrayList();
    private int currentFrame = -1;
    private long nextChange = 0L;
    private boolean stopped = false;
    private long timeLeft;
    private float speed = 1.0f;
    private int stopAt = -2;
    private long lastUpdate;
    private boolean firstUpdate = true;
    private boolean autoUpdate = true;
    private int direction = 1;
    private boolean pingPong;
    private boolean loop = true;
    private SpriteSheet spriteSheet = null;

    public Animation() {
        this(true);
    }

    public Animation(Image[] imageArray, int n) {
        this(imageArray, n, true);
    }

    public Animation(Image[] imageArray, int[] nArray) {
        this(imageArray, nArray, true);
    }

    public Animation(boolean bl) {
        this.currentFrame = 0;
        this.autoUpdate = bl;
    }

    public Animation(Image[] imageArray, int n, boolean bl) {
        for (int i = 0; i < imageArray.length; ++i) {
            this.addFrame(imageArray[i], n);
        }
        this.currentFrame = 0;
        this.autoUpdate = bl;
    }

    public Animation(Image[] imageArray, int[] nArray, boolean bl) {
        this.autoUpdate = bl;
        if (imageArray.length != nArray.length) {
            throw new RuntimeException("There must be one duration per frame");
        }
        for (int i = 0; i < imageArray.length; ++i) {
            this.addFrame(imageArray[i], nArray[i]);
        }
        this.currentFrame = 0;
    }

    public Animation(SpriteSheet spriteSheet, int n) {
        this(spriteSheet, 0, 0, spriteSheet.getHorizontalCount() - 1, spriteSheet.getVerticalCount() - 1, true, n, true);
    }

    public Animation(SpriteSheet spriteSheet, int n, int n2, int n3, int n4, boolean bl, int n5, boolean bl2) {
        this.autoUpdate = bl2;
        if (!bl) {
            for (int i = n; i <= n3; ++i) {
                for (int j = n2; j <= n4; ++j) {
                    this.addFrame(spriteSheet.getSprite(i, j), n5);
                }
            }
        } else {
            for (int i = n2; i <= n4; ++i) {
                for (int j = n; j <= n3; ++j) {
                    this.addFrame(spriteSheet.getSprite(j, i), n5);
                }
            }
        }
    }

    public Animation(SpriteSheet spriteSheet, int[] nArray, int[] nArray2) {
        this.spriteSheet = spriteSheet;
        int n = -1;
        int n2 = -1;
        for (int i = 0; i < nArray.length / 2; ++i) {
            n = nArray[i * 2];
            n2 = nArray[i * 2 + 1];
            this.addFrame(nArray2[i], n, n2);
        }
    }

    public void addFrame(int n, int n2, int n3) {
        if (n == 0) {
            Log.error("Invalid duration: " + n);
            throw new RuntimeException("Invalid duration: " + n);
        }
        if (this.frames.isEmpty()) {
            this.nextChange = (int)((float)n / this.speed);
        }
        this.frames.add(new Frame(this, n, n2, n3));
        this.currentFrame = 0;
    }

    public void setAutoUpdate(boolean bl) {
        this.autoUpdate = bl;
    }

    public void setPingPong(boolean bl) {
        this.pingPong = bl;
    }

    public boolean isStopped() {
        return this.stopped;
    }

    public void setSpeed(float f) {
        if (f > 0.0f) {
            this.nextChange = (long)((float)this.nextChange * this.speed / f);
            this.speed = f;
        }
    }

    public float getSpeed() {
        return this.speed;
    }

    public void stop() {
        if (this.frames.size() == 0) {
            return;
        }
        this.timeLeft = this.nextChange;
        this.stopped = true;
    }

    public void start() {
        if (!this.stopped) {
            return;
        }
        if (this.frames.size() == 0) {
            return;
        }
        this.stopped = false;
        this.nextChange = this.timeLeft;
    }

    public void restart() {
        if (this.frames.size() == 0) {
            return;
        }
        this.stopped = false;
        this.currentFrame = 0;
        this.nextChange = (int)((float)((Frame)this.frames.get((int)0)).duration / this.speed);
        this.firstUpdate = true;
        this.lastUpdate = 0L;
    }

    public void addFrame(Image image, int n) {
        if (n == 0) {
            Log.error("Invalid duration: " + n);
            throw new RuntimeException("Invalid duration: " + n);
        }
        if (this.frames.isEmpty()) {
            this.nextChange = (int)((float)n / this.speed);
        }
        this.frames.add(new Frame(this, image, n));
        this.currentFrame = 0;
    }

    public void draw() {
        this.draw(0.0f, 0.0f);
    }

    @Override
    public void draw(float f, float f2) {
        this.draw(f, f2, this.getWidth(), this.getHeight());
    }

    public void draw(float f, float f2, Color color) {
        this.draw(f, f2, this.getWidth(), this.getHeight(), color);
    }

    public void draw(float f, float f2, float f3, float f4) {
        this.draw(f, f2, f3, f4, Color.white);
    }

    public void draw(float f, float f2, float f3, float f4, Color color) {
        if (this.frames.size() == 0) {
            return;
        }
        if (this.autoUpdate) {
            long l2 = this.getTime();
            long l3 = l2 - this.lastUpdate;
            if (this.firstUpdate) {
                l3 = 0L;
                this.firstUpdate = false;
            }
            this.lastUpdate = l2;
            this.nextFrame(l3);
        }
        Frame frame = (Frame)this.frames.get(this.currentFrame);
        frame.image.draw(f, f2, f3, f4, color);
    }

    public void renderInUse(int n, int n2) {
        if (this.frames.size() == 0) {
            return;
        }
        if (this.autoUpdate) {
            long l2 = this.getTime();
            long l3 = l2 - this.lastUpdate;
            if (this.firstUpdate) {
                l3 = 0L;
                this.firstUpdate = false;
            }
            this.lastUpdate = l2;
            this.nextFrame(l3);
        }
        Frame frame = (Frame)this.frames.get(this.currentFrame);
        this.spriteSheet.renderInUse(n, n2, frame.x, frame.y);
    }

    public int getWidth() {
        return ((Frame)this.frames.get((int)this.currentFrame)).image.getWidth();
    }

    public int getHeight() {
        return ((Frame)this.frames.get((int)this.currentFrame)).image.getHeight();
    }

    public void drawFlash(float f, float f2, float f3, float f4) {
        this.drawFlash(f, f2, f3, f4, Color.white);
    }

    public void drawFlash(float f, float f2, float f3, float f4, Color color) {
        if (this.frames.size() == 0) {
            return;
        }
        if (this.autoUpdate) {
            long l2 = this.getTime();
            long l3 = l2 - this.lastUpdate;
            if (this.firstUpdate) {
                l3 = 0L;
                this.firstUpdate = false;
            }
            this.lastUpdate = l2;
            this.nextFrame(l3);
        }
        Frame frame = (Frame)this.frames.get(this.currentFrame);
        frame.image.drawFlash(f, f2, f3, f4, color);
    }

    public void updateNoDraw() {
        if (this.autoUpdate) {
            long l2 = this.getTime();
            long l3 = l2 - this.lastUpdate;
            if (this.firstUpdate) {
                l3 = 0L;
                this.firstUpdate = false;
            }
            this.lastUpdate = l2;
            this.nextFrame(l3);
        }
    }

    public void update(long l2) {
        this.nextFrame(l2);
    }

    public int getFrame() {
        return this.currentFrame;
    }

    public void setCurrentFrame(int n) {
        this.currentFrame = n;
    }

    public Image getImage(int n) {
        Frame frame = (Frame)this.frames.get(n);
        return frame.image;
    }

    public int getFrameCount() {
        return this.frames.size();
    }

    public Image getCurrentFrame() {
        Frame frame = (Frame)this.frames.get(this.currentFrame);
        return frame.image;
    }

    private void nextFrame(long l2) {
        if (this.stopped) {
            return;
        }
        if (this.frames.size() == 0) {
            return;
        }
        this.nextChange -= l2;
        while (this.nextChange < 0L && !this.stopped) {
            if (this.currentFrame == this.stopAt) {
                this.stopped = true;
                break;
            }
            if (this.currentFrame == this.frames.size() - 1 && !this.loop && !this.pingPong) {
                this.stopped = true;
                break;
            }
            this.currentFrame = (this.currentFrame + this.direction) % this.frames.size();
            if (this.pingPong) {
                if (this.currentFrame <= 0) {
                    this.currentFrame = 0;
                    this.direction = 1;
                    if (!this.loop) {
                        this.stopped = true;
                        break;
                    }
                } else if (this.currentFrame >= this.frames.size() - 1) {
                    this.currentFrame = this.frames.size() - 1;
                    this.direction = -1;
                }
            }
            int n = (int)((float)((Frame)this.frames.get((int)this.currentFrame)).duration / this.speed);
            this.nextChange += (long)n;
        }
    }

    public void setLooping(boolean bl) {
        this.loop = bl;
    }

    private long getTime() {
        return Sys.getTime() * 1000L / Sys.getTimerResolution();
    }

    public void stopAt(int n) {
        this.stopAt = n;
    }

    public int getDuration(int n) {
        return ((Frame)this.frames.get((int)n)).duration;
    }

    public void setDuration(int n, int n2) {
        ((Frame)this.frames.get((int)n)).duration = n2;
    }

    public int[] getDurations() {
        int[] nArray = new int[this.frames.size()];
        for (int i = 0; i < this.frames.size(); ++i) {
            nArray[i] = this.getDuration(i);
        }
        return nArray;
    }

    public String toString() {
        String string = "[Animation (" + this.frames.size() + ") ";
        for (int i = 0; i < this.frames.size(); ++i) {
            Frame frame = (Frame)this.frames.get(i);
            string = string + frame.duration + ",";
        }
        string = string + "]";
        return string;
    }

    public Animation copy() {
        Animation animation = new Animation();
        animation.spriteSheet = this.spriteSheet;
        animation.frames = this.frames;
        animation.autoUpdate = this.autoUpdate;
        animation.direction = this.direction;
        animation.loop = this.loop;
        animation.pingPong = this.pingPong;
        animation.speed = this.speed;
        return animation;
    }

    static SpriteSheet access$000(Animation animation) {
        return animation.spriteSheet;
    }

    private class Frame {
        public Image image;
        public int duration;
        public int x;
        public int y;
        final Animation this$0;

        public Frame(Animation animation, Image image, int n) {
            this.this$0 = animation;
            this.x = -1;
            this.y = -1;
            this.image = image;
            this.duration = n;
        }

        public Frame(Animation animation, int n, int n2, int n3) {
            this.this$0 = animation;
            this.x = -1;
            this.y = -1;
            this.image = Animation.access$000(animation).getSubImage(n2, n3);
            this.duration = n;
            this.x = n2;
            this.y = n3;
        }
    }
}

