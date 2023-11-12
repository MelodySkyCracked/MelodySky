/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.geom.TexCoordGenerator;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.svg.Diagram;
import org.newdawn.slick.svg.Figure;
import org.newdawn.slick.svg.Gradient;
import org.newdawn.slick.svg.LinearGradientFill;
import org.newdawn.slick.svg.RadialGradientFill;

public class SimpleDiagramRenderer {
    protected static SGL GL = Renderer.get();
    public Diagram diagram;
    public int list = -1;

    public SimpleDiagramRenderer(Diagram diagram) {
        this.diagram = diagram;
    }

    public void render(Graphics graphics) {
        if (this.list == -1) {
            this.list = GL.glGenLists(1);
            GL.glNewList(this.list, 4864);
            SimpleDiagramRenderer.render(graphics, this.diagram);
            GL.glEndList();
        }
        GL.glCallList(this.list);
        TextureImpl.bindNone();
    }

    public static void render(Graphics graphics, Diagram diagram) {
        for (int i = 0; i < diagram.getFigureCount(); ++i) {
            Figure figure = diagram.getFigure(i);
            if (figure.getData().isFilled()) {
                String string;
                if (figure.getData().isColor("fill")) {
                    graphics.setColor(figure.getData().getAsColor("fill"));
                    graphics.fill(diagram.getFigure(i).getShape());
                    graphics.setAntiAlias(true);
                    graphics.draw(diagram.getFigure(i).getShape());
                    graphics.setAntiAlias(false);
                }
                if (diagram.getPatternDef(string = figure.getData().getAsReference("fill")) != null) {
                    System.out.println("PATTERN");
                }
                if (diagram.getGradient(string) != null) {
                    Gradient gradient = diagram.getGradient(string);
                    Shape shape = diagram.getFigure(i).getShape();
                    TexCoordGenerator texCoordGenerator = null;
                    texCoordGenerator = gradient.isRadial() ? new RadialGradientFill(shape, diagram.getFigure(i).getTransform(), gradient) : new LinearGradientFill(shape, diagram.getFigure(i).getTransform(), gradient);
                    Color.white.bind();
                    ShapeRenderer.texture(shape, gradient.getImage(), texCoordGenerator);
                }
            }
            if (!figure.getData().isStroked() || !figure.getData().isColor("stroke")) continue;
            graphics.setColor(figure.getData().getAsColor("stroke"));
            graphics.setLineWidth(figure.getData().getAsFloat("stroke-width"));
            graphics.setAntiAlias(true);
            graphics.draw(diagram.getFigure(i).getShape());
            graphics.setAntiAlias(false);
            graphics.resetLineWidth();
        }
    }
}

