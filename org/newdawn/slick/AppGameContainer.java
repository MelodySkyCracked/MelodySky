/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.LWJGLException
 *  org.lwjgl.Sys
 *  org.lwjgl.input.Cursor
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.openal.AL
 *  org.lwjgl.opengl.Display
 *  org.lwjgl.opengl.DisplayMode
 *  org.lwjgl.opengl.Drawable
 *  org.lwjgl.opengl.PixelFormat
 */
package org.newdawn.slick;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.security.AccessController;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.lI;
import org.newdawn.slick.lIl;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.opengl.CursorLoader;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.ImageIOImageData;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.LoadableImageData;
import org.newdawn.slick.opengl.TGAImageData;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

public class AppGameContainer
extends GameContainer {
    protected DisplayMode originalDisplayMode = Display.getDisplayMode();
    protected DisplayMode targetDisplayMode;
    protected boolean updateOnlyOnVisible = true;
    protected boolean alphaSupport = false;

    public AppGameContainer(Game game) throws SlickException {
        this(game, 640, 480, false);
    }

    public AppGameContainer(Game game, int n, int n2, boolean bl) throws SlickException {
        super(game);
        this.setDisplayMode(n, n2, bl);
    }

    public boolean supportsAlphaInBackBuffer() {
        return this.alphaSupport;
    }

    public void setTitle(String string) {
        Display.setTitle((String)string);
    }

    public void setDisplayMode(int n, int n2, boolean bl) throws SlickException {
        if (this.width == n && this.height == n2 && this.isFullscreen() == bl) {
            return;
        }
        try {
            this.targetDisplayMode = null;
            if (bl) {
                DisplayMode[] displayModeArray = Display.getAvailableDisplayModes();
                int n3 = 0;
                for (int i = 0; i < displayModeArray.length; ++i) {
                    DisplayMode displayMode = displayModeArray[i];
                    if (displayMode.getWidth() != n || displayMode.getHeight() != n2) continue;
                    if (!(this.targetDisplayMode != null && displayMode.getFrequency() < n3 || this.targetDisplayMode != null && displayMode.getBitsPerPixel() <= this.targetDisplayMode.getBitsPerPixel())) {
                        this.targetDisplayMode = displayMode;
                        n3 = this.targetDisplayMode.getFrequency();
                    }
                    if (displayMode.getBitsPerPixel() != this.originalDisplayMode.getBitsPerPixel() || displayMode.getFrequency() != this.originalDisplayMode.getFrequency()) continue;
                    this.targetDisplayMode = displayMode;
                    break;
                }
            } else {
                this.targetDisplayMode = new DisplayMode(n, n2);
            }
            if (this.targetDisplayMode == null) {
                throw new SlickException("Failed to find value mode: " + n + "x" + n2 + " fs=" + bl);
            }
            this.width = n;
            this.height = n2;
            Display.setDisplayMode((DisplayMode)this.targetDisplayMode);
            Display.setFullscreen((boolean)bl);
            if (Display.isCreated()) {
                this.initGL();
                this.enterOrtho();
            }
            if (this.targetDisplayMode.getBitsPerPixel() == 16) {
                InternalTextureLoader.get().set16BitMode();
            }
        }
        catch (LWJGLException lWJGLException) {
            throw new SlickException("Unable to setup mode " + n + "x" + n2 + " fullscreen=" + bl, lWJGLException);
        }
        this.getDelta();
    }

    @Override
    public boolean isFullscreen() {
        return Display.isFullscreen();
    }

    @Override
    public void setFullscreen(boolean bl) throws SlickException {
        if (this.isFullscreen() == bl) {
            return;
        }
        if (!bl) {
            try {
                Display.setFullscreen((boolean)bl);
            }
            catch (LWJGLException lWJGLException) {
                throw new SlickException("Unable to set fullscreen=" + bl, lWJGLException);
            }
        } else {
            this.setDisplayMode(this.width, this.height, bl);
        }
        this.getDelta();
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
            Cursor cursor = CursorLoader.get().getCursor(byteBuffer, n, n2, image2.getWidth(), image.getHeight());
            Mouse.setNativeCursor((Cursor)cursor);
        }
        catch (Throwable throwable) {
            Log.error("Failed to load and apply cursor.", throwable);
            throw new SlickException("Failed to set mouse cursor", throwable);
        }
    }

    @Override
    public void reinit() throws SlickException {
        InternalTextureLoader.get().clear();
        SoundStore.get().clear();
        this.initSystem();
        this.enterOrtho();
        try {
            this.game.init(this);
        }
        catch (SlickException slickException) {
            Log.error(slickException);
            this.running = false;
        }
    }

    private void tryCreateDisplay(PixelFormat pixelFormat) throws LWJGLException {
        if (SHARED_DRAWABLE == null) {
            Display.create((PixelFormat)pixelFormat);
        } else {
            Display.create((PixelFormat)pixelFormat, (Drawable)SHARED_DRAWABLE);
        }
    }

    public void start() throws SlickException {
        this.setup();
        this.getDelta();
        while (this.running()) {
            this.gameLoop();
        }
        this.destroy();
        if (this.forceExit) {
            System.exit(0);
        }
    }

    protected void setup() throws SlickException {
        if (this.targetDisplayMode == null) {
            this.setDisplayMode(640, 480, false);
        }
        Display.setTitle((String)this.game.getTitle());
        Log.info("LWJGL Version: " + Sys.getVersion());
        Log.info("OriginalDisplayMode: " + this.originalDisplayMode);
        Log.info("TargetDisplayMode: " + this.targetDisplayMode);
        AccessController.doPrivileged(new lIl(this));
        if (!Display.isCreated()) {
            throw new SlickException("Failed to initialise the LWJGL display");
        }
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
        try {
            this.game.init(this);
        }
        catch (SlickException slickException) {
            Log.error(slickException);
            this.running = false;
        }
    }

    protected void gameLoop() throws SlickException {
        int n = this.getDelta();
        if (!Display.isVisible() && this.updateOnlyOnVisible) {
            try {
                Thread.sleep(100L);
            }
            catch (Exception exception) {}
        } else {
            try {
                this.updateAndRender(n);
            }
            catch (SlickException slickException) {
                Log.error(slickException);
                this.running = false;
                return;
            }
        }
        this.updateFPS();
        Display.update();
        if (Display.isCloseRequested() && this.game.closeRequested()) {
            this.running = false;
        }
    }

    @Override
    public void setUpdateOnlyWhenVisible(boolean bl) {
        this.updateOnlyOnVisible = bl;
    }

    @Override
    public boolean isUpdatingOnlyWhenVisible() {
        return this.updateOnlyOnVisible;
    }

    @Override
    public void setIcon(String string) throws SlickException {
        this.setIcons(new String[]{string});
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
    public boolean hasFocus() {
        return Display.isActive();
    }

    @Override
    public int getScreenHeight() {
        return this.originalDisplayMode.getHeight();
    }

    @Override
    public int getScreenWidth() {
        return this.originalDisplayMode.getWidth();
    }

    public void destroy() {
        Display.destroy();
        AL.destroy();
    }

    @Override
    public void setIcons(String[] stringArray) throws SlickException {
        ByteBuffer[] byteBufferArray = new ByteBuffer[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            LoadableImageData loadableImageData;
            boolean bl = true;
            if (stringArray[i].endsWith(".tga")) {
                loadableImageData = new TGAImageData();
            } else {
                bl = false;
                loadableImageData = new ImageIOImageData();
            }
            try {
                byteBufferArray[i] = loadableImageData.loadImage(ResourceLoader.getResourceAsStream(stringArray[i]), bl, false, null);
                continue;
            }
            catch (Exception exception) {
                Log.error(exception);
                throw new SlickException("Failed to set the icon");
            }
        }
        Display.setIcon((ByteBuffer[])byteBufferArray);
    }

    @Override
    public void setDefaultMouseCursor() {
        try {
            Mouse.setNativeCursor(null);
        }
        catch (LWJGLException lWJGLException) {
            Log.error("Failed to reset mouse cursor", lWJGLException);
        }
    }

    static void access$000(AppGameContainer appGameContainer, PixelFormat pixelFormat) throws LWJGLException {
        appGameContainer.tryCreateDisplay(pixelFormat);
    }

    static {
        AccessController.doPrivileged(new lI());
    }

    private class NullOutputStream
    extends OutputStream {
        final AppGameContainer this$0;

        private NullOutputStream(AppGameContainer appGameContainer) {
            this.this$0 = appGameContainer;
        }

        @Override
        public void write(int n) throws IOException {
        }
    }
}

