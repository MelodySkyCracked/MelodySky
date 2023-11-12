/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg;

import java.util.Properties;
import org.newdawn.slick.Color;

public class NonGeometricData {
    public static final String ID = "id";
    public static final String FILL = "fill";
    public static final String STROKE = "stroke";
    public static final String OPACITY = "opacity";
    public static final String STROKE_WIDTH = "stroke-width";
    public static final String STROKE_MITERLIMIT = "stroke-miterlimit";
    public static final String STROKE_DASHARRAY = "stroke-dasharray";
    public static final String STROKE_DASHOFFSET = "stroke-dashoffset";
    public static final String STROKE_OPACITY = "stroke-opacity";
    public static final String NONE = "none";
    private String metaData = "";
    private Properties props = new Properties();

    public NonGeometricData(String string) {
        this.metaData = string;
        this.addAttribute(STROKE_WIDTH, "1");
    }

    private String morphColor(String string) {
        if (string.equals("")) {
            return "#000000";
        }
        if (string.equals("white")) {
            return "#ffffff";
        }
        if (string.equals("black")) {
            return "#000000";
        }
        return string;
    }

    public void addAttribute(String string, String string2) {
        if (string2 == null) {
            string2 = "";
        }
        if (string.equals(FILL)) {
            string2 = this.morphColor(string2);
        }
        if (string.equals(STROKE_OPACITY) && string2.equals("0")) {
            this.props.setProperty(STROKE, NONE);
        }
        if (string.equals(STROKE_WIDTH)) {
            if (string2.equals("")) {
                string2 = "1";
            }
            if (string2.endsWith("px")) {
                string2 = string2.substring(0, string2.length() - 2);
            }
        }
        if (string.equals(STROKE)) {
            if (NONE.equals(this.props.getProperty(STROKE))) {
                return;
            }
            if ("".equals(this.props.getProperty(STROKE))) {
                return;
            }
            string2 = this.morphColor(string2);
        }
        this.props.setProperty(string, string2);
    }

    public boolean isColor(String string) {
        return this.getAttribute(string).startsWith("#");
    }

    public String getMetaData() {
        return this.metaData;
    }

    public String getAttribute(String string) {
        return this.props.getProperty(string);
    }

    public Color getAsColor(String string) {
        if (!this.isColor(string)) {
            throw new RuntimeException("Attribute " + string + " is not specified as a color:" + this.getAttribute(string));
        }
        int n = Integer.parseInt(this.getAttribute(string).substring(1), 16);
        return new Color(n);
    }

    public String getAsReference(String string) {
        String string2 = this.getAttribute(string);
        if (string2.length() < 7) {
            return "";
        }
        string2 = string2.substring(5, string2.length() - 1);
        return string2;
    }

    public float getAsFloat(String string) {
        String string2 = this.getAttribute(string);
        if (string2 == null) {
            return 0.0f;
        }
        try {
            return Float.parseFloat(string2);
        }
        catch (NumberFormatException numberFormatException) {
            throw new RuntimeException("Attribute " + string + " is not specified as a float:" + this.getAttribute(string));
        }
    }

    public boolean isFilled() {
        return this.isColor(FILL);
    }

    public boolean isStroked() {
        return this.isColor(STROKE) && this.getAsFloat(STROKE_WIDTH) > 0.0f;
    }
}

