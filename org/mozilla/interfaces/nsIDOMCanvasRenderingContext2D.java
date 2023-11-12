/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCanvasGradient;
import org.mozilla.interfaces.nsIDOMCanvasPattern;
import org.mozilla.interfaces.nsIDOMHTMLCanvasElement;
import org.mozilla.interfaces.nsIDOMHTMLElement;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIVariant;

public interface nsIDOMCanvasRenderingContext2D
extends nsISupports {
    public static final String NS_IDOMCANVASRENDERINGCONTEXT2D_IID = "{ab27f42d-e1e1-4ef6-9c83-059a81da479b}";

    public nsIDOMHTMLCanvasElement getCanvas();

    public void save();

    public void restore();

    public void scale(float var1, float var2);

    public void rotate(float var1);

    public void translate(float var1, float var2);

    public float getGlobalAlpha();

    public void setGlobalAlpha(float var1);

    public String getGlobalCompositeOperation();

    public void setGlobalCompositeOperation(String var1);

    public nsIVariant getStrokeStyle();

    public void setStrokeStyle(nsIVariant var1);

    public nsIVariant getFillStyle();

    public void setFillStyle(nsIVariant var1);

    public nsIDOMCanvasGradient createLinearGradient(float var1, float var2, float var3, float var4);

    public nsIDOMCanvasGradient createRadialGradient(float var1, float var2, float var3, float var4, float var5, float var6);

    public nsIDOMCanvasPattern createPattern(nsIDOMHTMLElement var1, String var2);

    public float getLineWidth();

    public void setLineWidth(float var1);

    public String getLineCap();

    public void setLineCap(String var1);

    public String getLineJoin();

    public void setLineJoin(String var1);

    public float getMiterLimit();

    public void setMiterLimit(float var1);

    public float getShadowOffsetX();

    public void setShadowOffsetX(float var1);

    public float getShadowOffsetY();

    public void setShadowOffsetY(float var1);

    public float getShadowBlur();

    public void setShadowBlur(float var1);

    public String getShadowColor();

    public void setShadowColor(String var1);

    public void clearRect(float var1, float var2, float var3, float var4);

    public void fillRect(float var1, float var2, float var3, float var4);

    public void strokeRect(float var1, float var2, float var3, float var4);

    public void beginPath();

    public void closePath();

    public void moveTo(float var1, float var2);

    public void lineTo(float var1, float var2);

    public void quadraticCurveTo(float var1, float var2, float var3, float var4);

    public void bezierCurveTo(float var1, float var2, float var3, float var4, float var5, float var6);

    public void arcTo(float var1, float var2, float var3, float var4, float var5);

    public void arc(float var1, float var2, float var3, float var4, float var5, boolean var6);

    public void rect(float var1, float var2, float var3, float var4);

    public void fill();

    public void stroke();

    public void clip();

    public void drawImage();

    public boolean isPointInPath(float var1, float var2);

    public void getImageData();

    public void putImageData();

    public void drawWindow(nsIDOMWindow var1, int var2, int var3, int var4, int var5, String var6);
}

