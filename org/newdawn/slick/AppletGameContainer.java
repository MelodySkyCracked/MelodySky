/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.LWJGLException
 *  org.lwjgl.input.Cursor
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.PixelFormat
 */
package org.newdawn.slick;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.lIlI;
import org.newdawn.slick.lll;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.CursorLoader;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.util.Log;

public class AppletGameContainer
extends Applet {
    protected ContainerPanel canvas;
    protected Container container;
    protected Canvas displayParent;
    protected Thread gameThread;
    protected boolean alphaSupport = true;

    @Override
    public void destroy() {
        if (this.displayParent != null) {
            this.remove(this.displayParent);
        }
        super.destroy();
        Log.info("Clear up");
    }

    private void destroyLWJGL() {
        this.container.stopApplet();
        try {
            this.gameThread.join();
        }
        catch (InterruptedException interruptedException) {
            Log.error(interruptedException);
        }
    }

    @Override
    public void start() {
    }

    public void startLWJGL() {
        if (this.gameThread != null) {
            return;
        }
        this.gameThread = new lll(this);
        this.gameThread.start();
    }

    @Override
    public void stop() {
    }

    @Override
    public void init() {
        this.removeAll();
        this.setLayout(new BorderLayout());
        this.setIgnoreRepaint(true);
        try {
            Game game = (Game)Class.forName(this.getParameter("game")).newInstance();
            this.container = new Container(this, game);
            this.canvas = new ContainerPanel(this, this.container);
            this.displayParent = new lIlI(this);
            this.displayParent.setSize(this.getWidth(), this.getHeight());
            this.add(this.displayParent);
            this.displayParent.setFocusable(true);
            this.displayParent.requestFocus();
            this.displayParent.setIgnoreRepaint(true);
            this.setVisible(true);
        }
        catch (Exception exception) {
            Log.error(exception);
            throw new RuntimeException("Unable to create game container");
        }
    }

    static void access$000(AppletGameContainer appletGameContainer) {
        appletGameContainer.destroyLWJGL();
    }

    public class ConsolePanel
    extends Panel {
        TextArea textArea;
        final AppletGameContainer this$0;

        public ConsolePanel(AppletGameContainer appletGameContainer, Exception exception) {
            this.this$0 = appletGameContainer;
            this.textArea = new TextArea();
            this.setLayout(new BorderLayout());
            this.setBackground(Color.black);
            this.setForeground(Color.white);
            Font font = new Font("Arial", 1, 14);
            Label label = new Label("SLICK CONSOLE", 1);
            label.setFont(font);
            this.add((Component)label, "First");
            StringWriter stringWriter = new StringWriter();
            exception.printStackTrace(new PrintWriter(stringWriter));
            this.textArea.setText(stringWriter.toString());
            this.textArea.setEditable(false);
            this.add((Component)this.textArea, "Center");
            this.add((Component)new Panel(), "Before");
            this.add((Component)new Panel(), "After");
            Panel panel = new Panel();
            panel.setLayout(new GridLayout(0, 1));
            Label label2 = new Label("An error occured while running the applet.", 1);
            Label label3 = new Label("Plese contact support to resolve this issue.", 1);
            label2.setFont(font);
            label3.setFont(font);
            panel.add(label2);
            panel.add(label3);
            this.add((Component)panel, "Last");
        }
    }

    public class Container
    extends GameContainer {
        final AppletGameContainer this$0;

        public Container(AppletGameContainer appletGameContainer, Game game) {
            this.this$0 = appletGameContainer;
            super(game);
            this.width = appletGameContainer.getWidth();
            this.height = appletGameContainer.getHeight();
        }

        public void initApplet() throws SlickException {
            this.initSystem();
            this.enterOrtho();
            try {
                this.getInput().initControllers();
            }
            catch (SlickException slickException) {
                Log.info("Controllers not available");
            }
            catch (Throwable throwable) {
                Log.info("Controllers not available");
            }
            this.game.init(this);
            this.getDelta();
        }

        public boolean isRunning() {
            return this.running;
        }

        public void stopApplet() {
            this.running = false;
        }

        @Override
        public int getScreenHeight() {
            return 0;
        }

        @Override
        public int getScreenWidth() {
            return 0;
        }

        public boolean supportsAlphaInBackBuffer() {
            return this.this$0.alphaSupport;
        }

        @Override
        public boolean hasFocus() {
            return true;
        }

        public Applet getApplet() {
            return this.this$0;
        }

        @Override
        public void setIcon(String string) throws SlickException {
        }

        @Override
        public void setMouseGrabbed(boolean bl) {
            Mouse.setGrabbed((boolean)bl);
        }

        @Override
        public boolean isMouseGrabbed() {
            return Mouse.isGrabbed();
        }

        @Override
        public void setMouseCursor(String string, int n, int n2) throws SlickException {
            try {
                Cursor cursor = CursorLoader.get().getCursor(string, n, n2);
                Mouse.setNativeCursor((Cursor)cursor);
            }
            catch (Throwable throwable) {
                Log.error("Failed to load and apply cursor.", throwable);
                throw new SlickException("Failed to set mouse cursor", throwable);
            }
        }

        private int get2Fold(int n) {
            int n2;
            for (n2 = 2; n2 < n; n2 *= 2) {
            }
            return n2;
        }

        @Override
        public void setMouseCursor(Image image, int n, int n2) throws SlickException {
            try {
                Image image2 = new Image(this.get2Fold(image.getWidth()), this.get2Fold(image.getHeight()));
                Graphics graphics = image2.getGraphics();
                ByteBuffer byteBuffer = BufferUtils.createByteBuffer((int)(image2.getWidth() * image2.getHeight() * 4));
                graphics.drawImage(image.getFlippedCopy(false, true), 0.0f, 0.0f);
                graphics.flush();
                graphics.getArea(0, 0, image2.getWidth(), image2.getHeight(), byteBuffer);
                Cursor cursor = CursorLoader.get().getCursor(byteBuffer, n, n2, image2.getWidth(), image2.getHeight());
                Mouse.setNativeCursor((Cursor)cursor);
            }
            catch (Throwable throwable) {
                Log.error("Failed to load and apply cursor.", throwable);
                throw new SlickException("Failed to set mouse cursor", throwable);
            }
        }

        @Override
        public void setIcons(String[] stringArray) throws SlickException {
        }

        @Override
        public void setMouseCursor(ImageData imageData, int n, int n2) throws SlickException {
            try {
                Cursor cursor = CursorLoader.get().getCursor(imageData, n, n2);
                Mouse.setNativeCursor((Cursor)cursor);
            }
            catch (Throwable throwable) {
                Log.error("Failed to load and apply cursor.", throwable);
                throw new SlickException("Failed to set mouse cursor", throwable);
            }
        }

        @Override
        public void setMouseCursor(Cursor cursor, int n, int n2) throws SlickException {
            try {
                Mouse.setNativeCursor((Cursor)cursor);
            }
            catch (Throwable throwable) {
                Log.error("Failed to load and apply cursor.", throwable);
                throw new SlickException("Failed to set mouse cursor", throwable);
            }
        }

        @Override
        public void setDefaultMouseCursor() {
        }

        @Override
        public boolean isFullscreen() {
            return Display.isFullscreen();
        }

        @Override
        public void setFullscreen(boolean bl) throws SlickException {
            if (bl == this.isFullscreen()) {
                return;
            }
            try {
                if (bl) {
                    int n;
                    int n2;
                    int n3;
                    float f = (float)this.width / (float)this.height;
                    int n4 = Display.getDisplayMode().getWidth();
                    float f2 = (float)n4 / (float)(n3 = Display.getDisplayMode().getHeight());
                    if (f >= f2) {
                        n2 = n4;
                        n = (int)((float)this.height / ((float)this.width / (float)n4));
                    } else {
                        n2 = (int)((float)this.width / ((float)this.height / (float)n3));
                        n = n3;
                    }
                    int n5 = (n4 - n2) / 2;
                    int n6 = (n3 - n) / 2;
                    GL11.glViewport((int)n5, (int)n6, (int)n2, (int)n);
                    this.enterOrtho();
                    this.getInput().setOffset((float)(-n5) * (float)this.width / (float)n2, (float)(-n6) * (float)this.height / (float)n);
                    this.getInput().setScale((float)this.width / (float)n2, (float)this.height / (float)n);
                    this.width = n4;
                    this.height = n3;
                    Display.setFullscreen((boolean)true);
                } else {
                    this.getInput().setOffset(0.0f, 0.0f);
                    this.getInput().setScale(1.0f, 1.0f);
                    this.width = this.this$0.getWidth();
                    this.height = this.this$0.getHeight();
                    GL11.glViewport((int)0, (int)0, (int)this.width, (int)this.height);
                    this.enterOrtho();
                    Display.setFullscreen((boolean)false);
                }
            }
            catch (LWJGLException lWJGLException) {
                Log.error(lWJGLException);
            }
        }

        public void runloop() throws Exception {
            while (this.running) {
                int n = this.getDelta();
                this.updateAndRender(n);
                this.updateFPS();
                Display.update();
            }
            Display.destroy();
        }
    }

    public class ContainerPanel {
        private Container container;
        final AppletGameContainer this$0;

        public ContainerPanel(AppletGameContainer appletGameContainer, Container container) {
            this.this$0 = appletGameContainer;
            this.container = container;
        }

        private void createDisplay() throws Exception {
            try {
                Display.create((PixelFormat)new PixelFormat(8, 8, GameContainer.stencil ? 8 : 0));
                this.this$0.alphaSupport = true;
            }
            catch (Exception exception) {
                this.this$0.alphaSupport = false;
                Display.destroy();
                Display.create();
            }
        }

        public void start() throws Exception {
            Display.setParent((Canvas)this.this$0.displayParent);
            Display.setVSyncEnabled((boolean)true);
            try {
                this.createDisplay();
            }
            catch (LWJGLException lWJGLException) {
                lWJGLException.printStackTrace();
                Thread.sleep(1000L);
                this.createDisplay();
            }
            this.initGL();
            this.this$0.displayParent.requestFocus();
            this.container.runloop();
        }

        protected void initGL() {
            try {
                InternalTextureLoader.get().clear();
                SoundStore.get().clear();
                this.container.initApplet();
            }
            catch (Exception exception) {
                Log.error(exception);
                this.container.stopApplet();
            }
        }
    }
}

