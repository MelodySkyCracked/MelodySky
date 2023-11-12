/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing;

import chrriis.dj.nativeswing.NativeComponentWrapper;
import chrriis.dj.nativeswing.common.UIUtils;
import chrriis.dj.nativeswing.lIlI;
import chrriis.dj.nativeswing.lIlll;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

class BackBufferManager {
    private NativeComponentWrapper nativeComponent;
    private Component paintingComponent;
    private final Object backBufferLock = new Object();
    private BufferedImage backBuffer;

    public BackBufferManager(NativeComponentWrapper nativeComponentWrapper, Component component) {
        this.nativeComponent = nativeComponentWrapper;
        this.paintingComponent = component;
    }

    public void updateBackBufferOnVisibleTranslucentAreas() {
        int n = this.paintingComponent.getWidth();
        int n2 = this.paintingComponent.getHeight();
        if (n <= 0 || n2 <= 0) {
            if (this.backBuffer != null) {
                this.backBuffer.flush();
            }
            this.backBuffer = null;
            return;
        }
        this.updateBackBuffer(this.getTranslucentOverlays());
    }

    protected Rectangle[] getTranslucentOverlays() {
        Rectangle[] rectangleArray = new Rectangle[]{new Rectangle(0, 0, this.paintingComponent.getWidth(), this.paintingComponent.getHeight())};
        rectangleArray = UIUtils.subtract(rectangleArray, UIUtils.getComponentVisibleArea(this.paintingComponent, new lIlI(this)));
        return UIUtils.subtract(rectangleArray, UIUtils.getComponentVisibleArea(this.paintingComponent, new lIlll(this)));
    }

    public void createBackBuffer() {
        this.updateBackBuffer(new Rectangle[]{new Rectangle(this.paintingComponent.getWidth(), this.paintingComponent.getHeight())});
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void updateBackBuffer(Rectangle[] rectangleArray) {
        if (rectangleArray == null || rectangleArray.length == 0) {
            return;
        }
        int n = this.paintingComponent.getWidth();
        int n2 = this.paintingComponent.getHeight();
        if (n <= 0 || n2 <= 0) {
            if (this.backBuffer != null) {
                this.backBuffer.flush();
            }
            this.backBuffer = null;
            return;
        }
        BufferedImage bufferedImage = this.backBuffer != null && this.backBuffer.getWidth() == n && this.backBuffer.getHeight() == n2 ? this.backBuffer : new BufferedImage(n, n2, 2);
        this.nativeComponent.paintNativeComponent(bufferedImage, rectangleArray);
        Object object = this.backBufferLock;
        synchronized (object) {
            if (this.backBuffer != null && this.backBuffer != bufferedImage) {
                BufferedImage bufferedImage2 = this.backBuffer;
                synchronized (bufferedImage2) {
                    Graphics graphics = bufferedImage.getGraphics();
                    graphics.drawImage(this.backBuffer, 0, 0, null);
                    graphics.dispose();
                }
                this.backBuffer.flush();
            }
            this.backBuffer = bufferedImage;
        }
        if (this.paintingComponent == this.nativeComponent.getNativeComponent()) return;
        object = UIUtils.getBounds(rectangleArray);
        this.paintingComponent.repaint(((Rectangle)object).x, ((Rectangle)object).y, ((Rectangle)object).width, ((Rectangle)object).height);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean hasBackBuffer() {
        Object object = this.backBufferLock;
        synchronized (object) {
            if (this.backBuffer == null) return false;
            return true;
        }
    }

    public void destroyBackBuffer() {
        Object object = this.backBufferLock;
        synchronized (object) {
            if (this.backBuffer != null) {
                this.backBuffer.flush();
            }
            this.backBuffer = null;
        }
    }

    public void paintBackBuffer(Graphics graphics) {
        Object object = this.backBufferLock;
        synchronized (object) {
            if (this.backBuffer != null) {
                BufferedImage bufferedImage = this.backBuffer;
                synchronized (bufferedImage) {
                    graphics.drawImage(this.backBuffer, 0, 0, this.paintingComponent);
                }
            }
        }
    }
}

