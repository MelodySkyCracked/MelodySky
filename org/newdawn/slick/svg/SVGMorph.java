/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg;

import java.util.ArrayList;
import org.newdawn.slick.geom.MorphShape;
import org.newdawn.slick.svg.Diagram;
import org.newdawn.slick.svg.Figure;

public class SVGMorph
extends Diagram {
    private ArrayList figures = new ArrayList();

    public SVGMorph(Diagram diagram) {
        super(diagram.getWidth(), diagram.getHeight());
        for (int i = 0; i < diagram.getFigureCount(); ++i) {
            Figure figure = diagram.getFigure(i);
            Figure figure2 = new Figure(figure.getType(), new MorphShape(figure.getShape()), figure.getData(), figure.getTransform());
            this.figures.add(figure2);
        }
    }

    public void addStep(Diagram diagram) {
        if (diagram.getFigureCount() != this.figures.size()) {
            throw new RuntimeException("Mismatched diagrams, missing ids");
        }
        block0: for (int i = 0; i < diagram.getFigureCount(); ++i) {
            Figure figure = diagram.getFigure(i);
            String string = figure.getData().getMetaData();
            for (int j = 0; j < this.figures.size(); ++j) {
                Figure figure2 = (Figure)this.figures.get(j);
                if (!figure2.getData().getMetaData().equals(string)) continue;
                MorphShape morphShape = (MorphShape)figure2.getShape();
                morphShape.addShape(figure.getShape());
                continue block0;
            }
        }
    }

    public void setExternalDiagram(Diagram diagram) {
        block0: for (int i = 0; i < this.figures.size(); ++i) {
            Figure figure = (Figure)this.figures.get(i);
            for (int j = 0; j < diagram.getFigureCount(); ++j) {
                Figure figure2 = diagram.getFigure(j);
                if (!figure2.getData().getMetaData().equals(figure.getData().getMetaData())) continue;
                MorphShape morphShape = (MorphShape)figure.getShape();
                morphShape.setExternalFrame(figure2.getShape());
                continue block0;
            }
        }
    }

    public void updateMorphTime(float f) {
        for (int i = 0; i < this.figures.size(); ++i) {
            Figure figure = (Figure)this.figures.get(i);
            MorphShape morphShape = (MorphShape)figure.getShape();
            morphShape.updateMorphTime(f);
        }
    }

    public void setMorphTime(float f) {
        for (int i = 0; i < this.figures.size(); ++i) {
            Figure figure = (Figure)this.figures.get(i);
            MorphShape morphShape = (MorphShape)figure.getShape();
            morphShape.setMorphTime(f);
        }
    }

    @Override
    public int getFigureCount() {
        return this.figures.size();
    }

    @Override
    public Figure getFigure(int n) {
        return (Figure)this.figures.get(n);
    }
}

