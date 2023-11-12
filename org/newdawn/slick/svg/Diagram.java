/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg;

import java.util.ArrayList;
import java.util.HashMap;
import org.newdawn.slick.svg.Figure;
import org.newdawn.slick.svg.Gradient;
import org.newdawn.slick.svg.InkscapeLoader;

public class Diagram {
    private ArrayList figures = new ArrayList();
    private HashMap patterns = new HashMap();
    private HashMap gradients = new HashMap();
    private HashMap figureMap = new HashMap();
    private float width;
    private float height;

    public Diagram(float f, float f2) {
        this.width = f;
        this.height = f2;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public void addPatternDef(String string, String string2) {
        this.patterns.put(string, string2);
    }

    public void addGradient(String string, Gradient gradient) {
        this.gradients.put(string, gradient);
    }

    public String getPatternDef(String string) {
        return (String)this.patterns.get(string);
    }

    public Gradient getGradient(String string) {
        return (Gradient)this.gradients.get(string);
    }

    public String[] getPatternDefNames() {
        return this.patterns.keySet().toArray(new String[0]);
    }

    public Figure getFigureByID(String string) {
        return (Figure)this.figureMap.get(string);
    }

    public void addFigure(Figure figure) {
        this.figures.add(figure);
        this.figureMap.put(figure.getData().getAttribute("id"), figure);
        String string = figure.getData().getAsReference("fill");
        Gradient gradient = this.getGradient(string);
        if (gradient != null && gradient.isRadial()) {
            for (int i = 0; i < InkscapeLoader.RADIAL_TRIANGULATION_LEVEL; ++i) {
                figure.getShape().increaseTriangulation();
            }
        }
    }

    public int getFigureCount() {
        return this.figures.size();
    }

    public Figure getFigure(int n) {
        return (Figure)this.figures.get(n);
    }

    public void removeFigure(Figure figure) {
        this.figures.remove(figure);
        this.figureMap.remove(figure.getData().getAttribute("id"));
    }
}

