/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.particles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ConfigurableEmitterFactory;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.particles.l;
import org.newdawn.slick.particles.ll;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParticleIO {
    public static ParticleSystem loadConfiguredSystem(String string, Color color) throws IOException {
        return ParticleIO.loadConfiguredSystem(ResourceLoader.getResourceAsStream(string), null, null, color);
    }

    public static ParticleSystem loadConfiguredSystem(String string) throws IOException {
        return ParticleIO.loadConfiguredSystem(ResourceLoader.getResourceAsStream(string), null, null, null);
    }

    public static ParticleSystem loadConfiguredSystem(File file) throws IOException {
        return ParticleIO.loadConfiguredSystem(new FileInputStream(file), null, null, null);
    }

    public static ParticleSystem loadConfiguredSystem(InputStream inputStream, Color color) throws IOException {
        return ParticleIO.loadConfiguredSystem(inputStream, null, null, color);
    }

    public static ParticleSystem loadConfiguredSystem(InputStream inputStream) throws IOException {
        return ParticleIO.loadConfiguredSystem(inputStream, null, null, null);
    }

    public static ParticleSystem loadConfiguredSystem(String string, ConfigurableEmitterFactory configurableEmitterFactory) throws IOException {
        return ParticleIO.loadConfiguredSystem(ResourceLoader.getResourceAsStream(string), configurableEmitterFactory, null, null);
    }

    public static ParticleSystem loadConfiguredSystem(File file, ConfigurableEmitterFactory configurableEmitterFactory) throws IOException {
        return ParticleIO.loadConfiguredSystem(new FileInputStream(file), configurableEmitterFactory, null, null);
    }

    public static ParticleSystem loadConfiguredSystem(InputStream inputStream, ConfigurableEmitterFactory configurableEmitterFactory) throws IOException {
        return ParticleIO.loadConfiguredSystem(inputStream, configurableEmitterFactory, null, null);
    }

    public static ParticleSystem loadConfiguredSystem(InputStream inputStream, ConfigurableEmitterFactory configurableEmitterFactory, ParticleSystem particleSystem, Color color) throws IOException {
        if (configurableEmitterFactory == null) {
            configurableEmitterFactory = new ll();
        }
        try {
            boolean bl;
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Element element = document.getDocumentElement();
            if (!element.getNodeName().equals("system")) {
                throw new IOException("Not a particle system file");
            }
            if (particleSystem == null) {
                particleSystem = new ParticleSystem("org/newdawn/slick/data/particle.tga", 2000, color);
            }
            if (bl = "true".equals(element.getAttribute("additive"))) {
                particleSystem.setBlendingMode(1);
            } else {
                particleSystem.setBlendingMode(2);
            }
            boolean bl2 = "true".equals(element.getAttribute("points"));
            particleSystem.setUsePoints(bl2);
            NodeList nodeList = element.getElementsByTagName("emitter");
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Element element2 = (Element)nodeList.item(i);
                ConfigurableEmitter configurableEmitter = configurableEmitterFactory.createEmitter("new");
                ParticleIO.elementToEmitter(element2, configurableEmitter);
                particleSystem.addEmitter(configurableEmitter);
            }
            particleSystem.setRemoveCompletedEmitters(false);
            return particleSystem;
        }
        catch (IOException iOException) {
            Log.error(iOException);
            throw iOException;
        }
        catch (Exception exception) {
            Log.error(exception);
            throw new IOException("Unable to load particle system config");
        }
    }

    public static void saveConfiguredSystem(File file, ParticleSystem particleSystem) throws IOException {
        ParticleIO.saveConfiguredSystem(new FileOutputStream(file), particleSystem);
    }

    public static void saveConfiguredSystem(OutputStream outputStream, ParticleSystem particleSystem) throws IOException {
        try {
            Object object;
            Object object2;
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element element = document.createElement("system");
            element.setAttribute("additive", "" + (particleSystem.getBlendingMode() == 1));
            element.setAttribute("points", "" + particleSystem.usePoints());
            document.appendChild(element);
            for (int i = 0; i < particleSystem.getEmitterCount(); ++i) {
                object2 = particleSystem.getEmitter(i);
                if (!(object2 instanceof ConfigurableEmitter)) {
                    throw new RuntimeException("Only ConfigurableEmitter instances can be stored");
                }
                object = ParticleIO.emitterToElement(document, (ConfigurableEmitter)object2);
                element.appendChild((Node)object);
            }
            StreamResult streamResult = new StreamResult(new OutputStreamWriter(outputStream, "utf-8"));
            object2 = new DOMSource(document);
            object = TransformerFactory.newInstance();
            Transformer transformer = ((TransformerFactory)object).newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.transform((Source)object2, streamResult);
        }
        catch (Exception exception) {
            Log.error(exception);
            throw new IOException("Unable to save configured particle system");
        }
    }

    public static ConfigurableEmitter loadEmitter(String string) throws IOException {
        return ParticleIO.loadEmitter(ResourceLoader.getResourceAsStream(string), null);
    }

    public static ConfigurableEmitter loadEmitter(File file) throws IOException {
        return ParticleIO.loadEmitter(new FileInputStream(file), null);
    }

    public static ConfigurableEmitter loadEmitter(InputStream inputStream) throws IOException {
        return ParticleIO.loadEmitter(inputStream, null);
    }

    public static ConfigurableEmitter loadEmitter(String string, ConfigurableEmitterFactory configurableEmitterFactory) throws IOException {
        return ParticleIO.loadEmitter(ResourceLoader.getResourceAsStream(string), configurableEmitterFactory);
    }

    public static ConfigurableEmitter loadEmitter(File file, ConfigurableEmitterFactory configurableEmitterFactory) throws IOException {
        return ParticleIO.loadEmitter(new FileInputStream(file), configurableEmitterFactory);
    }

    public static ConfigurableEmitter loadEmitter(InputStream inputStream, ConfigurableEmitterFactory configurableEmitterFactory) throws IOException {
        if (configurableEmitterFactory == null) {
            configurableEmitterFactory = new l();
        }
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            if (!document.getDocumentElement().getNodeName().equals("emitter")) {
                throw new IOException("Not a particle emitter file");
            }
            ConfigurableEmitter configurableEmitter = configurableEmitterFactory.createEmitter("new");
            ParticleIO.elementToEmitter(document.getDocumentElement(), configurableEmitter);
            return configurableEmitter;
        }
        catch (IOException iOException) {
            Log.error(iOException);
            throw iOException;
        }
        catch (Exception exception) {
            Log.error(exception);
            throw new IOException("Unable to load emitter");
        }
    }

    public static void saveEmitter(File file, ConfigurableEmitter configurableEmitter) throws IOException {
        ParticleIO.saveEmitter(new FileOutputStream(file), configurableEmitter);
    }

    public static void saveEmitter(OutputStream outputStream, ConfigurableEmitter configurableEmitter) throws IOException {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            document.appendChild(ParticleIO.emitterToElement(document, configurableEmitter));
            StreamResult streamResult = new StreamResult(new OutputStreamWriter(outputStream, "utf-8"));
            DOMSource dOMSource = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.transform(dOMSource, streamResult);
        }
        catch (Exception exception) {
            Log.error(exception);
            throw new IOException("Failed to save emitter");
        }
    }

    private static Element getFirstNamedElement(Element element, String string) {
        NodeList nodeList = element.getElementsByTagName(string);
        if (nodeList.getLength() == 0) {
            return null;
        }
        return (Element)nodeList.item(0);
    }

    private static void elementToEmitter(Element element, ConfigurableEmitter configurableEmitter) {
        String string;
        String string2;
        configurableEmitter.name = element.getAttribute("name");
        configurableEmitter.setImageName(element.getAttribute("imageName"));
        String string3 = element.getAttribute("renderType");
        configurableEmitter.usePoints = 1;
        if (string3.equals("quads")) {
            configurableEmitter.usePoints = 3;
        }
        if (string3.equals("points")) {
            configurableEmitter.usePoints = 2;
        }
        if ((string2 = element.getAttribute("useOriented")) != null) {
            configurableEmitter.useOriented = "true".equals(string2);
        }
        if ((string = element.getAttribute("useAdditive")) != null) {
            configurableEmitter.useAdditive = "true".equals(string);
        }
        ParticleIO.parseRangeElement(ParticleIO.getFirstNamedElement(element, "spawnInterval"), configurableEmitter.spawnInterval);
        ParticleIO.parseRangeElement(ParticleIO.getFirstNamedElement(element, "spawnCount"), configurableEmitter.spawnCount);
        ParticleIO.parseRangeElement(ParticleIO.getFirstNamedElement(element, "initialLife"), configurableEmitter.initialLife);
        ParticleIO.parseRangeElement(ParticleIO.getFirstNamedElement(element, "initialSize"), configurableEmitter.initialSize);
        ParticleIO.parseRangeElement(ParticleIO.getFirstNamedElement(element, "xOffset"), configurableEmitter.xOffset);
        ParticleIO.parseRangeElement(ParticleIO.getFirstNamedElement(element, "yOffset"), configurableEmitter.yOffset);
        ParticleIO.parseRangeElement(ParticleIO.getFirstNamedElement(element, "initialDistance"), configurableEmitter.initialDistance);
        ParticleIO.parseRangeElement(ParticleIO.getFirstNamedElement(element, "speed"), configurableEmitter.speed);
        ParticleIO.parseRangeElement(ParticleIO.getFirstNamedElement(element, "length"), configurableEmitter.length);
        ParticleIO.parseRangeElement(ParticleIO.getFirstNamedElement(element, "emitCount"), configurableEmitter.emitCount);
        ParticleIO.parseValueElement(ParticleIO.getFirstNamedElement(element, "spread"), configurableEmitter.spread);
        ParticleIO.parseValueElement(ParticleIO.getFirstNamedElement(element, "angularOffset"), configurableEmitter.angularOffset);
        ParticleIO.parseValueElement(ParticleIO.getFirstNamedElement(element, "growthFactor"), configurableEmitter.growthFactor);
        ParticleIO.parseValueElement(ParticleIO.getFirstNamedElement(element, "gravityFactor"), configurableEmitter.gravityFactor);
        ParticleIO.parseValueElement(ParticleIO.getFirstNamedElement(element, "windFactor"), configurableEmitter.windFactor);
        ParticleIO.parseValueElement(ParticleIO.getFirstNamedElement(element, "startAlpha"), configurableEmitter.startAlpha);
        ParticleIO.parseValueElement(ParticleIO.getFirstNamedElement(element, "endAlpha"), configurableEmitter.endAlpha);
        ParticleIO.parseValueElement(ParticleIO.getFirstNamedElement(element, "alpha"), configurableEmitter.alpha);
        ParticleIO.parseValueElement(ParticleIO.getFirstNamedElement(element, "size"), configurableEmitter.size);
        ParticleIO.parseValueElement(ParticleIO.getFirstNamedElement(element, "velocity"), configurableEmitter.velocity);
        ParticleIO.parseValueElement(ParticleIO.getFirstNamedElement(element, "scaleY"), configurableEmitter.scaleY);
        Element element2 = ParticleIO.getFirstNamedElement(element, "color");
        NodeList nodeList = element2.getElementsByTagName("step");
        configurableEmitter.colors.clear();
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Element element3 = (Element)nodeList.item(i);
            float f = Float.parseFloat(element3.getAttribute("offset"));
            float f2 = Float.parseFloat(element3.getAttribute("r"));
            float f3 = Float.parseFloat(element3.getAttribute("g"));
            float f4 = Float.parseFloat(element3.getAttribute("b"));
            configurableEmitter.addColorPoint(f, new Color(f2, f3, f4, 1.0f));
        }
        configurableEmitter.replay();
    }

    private static Element emitterToElement(Document document, ConfigurableEmitter configurableEmitter) {
        Element element = document.createElement("emitter");
        element.setAttribute("name", configurableEmitter.name);
        element.setAttribute("imageName", configurableEmitter.imageName == null ? "" : configurableEmitter.imageName);
        element.setAttribute("useOriented", configurableEmitter.useOriented ? "true" : "false");
        element.setAttribute("useAdditive", configurableEmitter.useAdditive ? "true" : "false");
        if (configurableEmitter.usePoints == 1) {
            element.setAttribute("renderType", "inherit");
        }
        if (configurableEmitter.usePoints == 2) {
            element.setAttribute("renderType", "points");
        }
        if (configurableEmitter.usePoints == 3) {
            element.setAttribute("renderType", "quads");
        }
        element.appendChild(ParticleIO.createRangeElement(document, "spawnInterval", configurableEmitter.spawnInterval));
        element.appendChild(ParticleIO.createRangeElement(document, "spawnCount", configurableEmitter.spawnCount));
        element.appendChild(ParticleIO.createRangeElement(document, "initialLife", configurableEmitter.initialLife));
        element.appendChild(ParticleIO.createRangeElement(document, "initialSize", configurableEmitter.initialSize));
        element.appendChild(ParticleIO.createRangeElement(document, "xOffset", configurableEmitter.xOffset));
        element.appendChild(ParticleIO.createRangeElement(document, "yOffset", configurableEmitter.yOffset));
        element.appendChild(ParticleIO.createRangeElement(document, "initialDistance", configurableEmitter.initialDistance));
        element.appendChild(ParticleIO.createRangeElement(document, "speed", configurableEmitter.speed));
        element.appendChild(ParticleIO.createRangeElement(document, "length", configurableEmitter.length));
        element.appendChild(ParticleIO.createRangeElement(document, "emitCount", configurableEmitter.emitCount));
        element.appendChild(ParticleIO.createValueElement(document, "spread", configurableEmitter.spread));
        element.appendChild(ParticleIO.createValueElement(document, "angularOffset", configurableEmitter.angularOffset));
        element.appendChild(ParticleIO.createValueElement(document, "growthFactor", configurableEmitter.growthFactor));
        element.appendChild(ParticleIO.createValueElement(document, "gravityFactor", configurableEmitter.gravityFactor));
        element.appendChild(ParticleIO.createValueElement(document, "windFactor", configurableEmitter.windFactor));
        element.appendChild(ParticleIO.createValueElement(document, "startAlpha", configurableEmitter.startAlpha));
        element.appendChild(ParticleIO.createValueElement(document, "endAlpha", configurableEmitter.endAlpha));
        element.appendChild(ParticleIO.createValueElement(document, "alpha", configurableEmitter.alpha));
        element.appendChild(ParticleIO.createValueElement(document, "size", configurableEmitter.size));
        element.appendChild(ParticleIO.createValueElement(document, "velocity", configurableEmitter.velocity));
        element.appendChild(ParticleIO.createValueElement(document, "scaleY", configurableEmitter.scaleY));
        Element element2 = document.createElement("color");
        ArrayList arrayList = configurableEmitter.colors;
        for (int i = 0; i < arrayList.size(); ++i) {
            ConfigurableEmitter.ColorRecord colorRecord = (ConfigurableEmitter.ColorRecord)arrayList.get(i);
            Element element3 = document.createElement("step");
            element3.setAttribute("offset", "" + colorRecord.pos);
            element3.setAttribute("r", "" + colorRecord.col.r);
            element3.setAttribute("g", "" + colorRecord.col.g);
            element3.setAttribute("b", "" + colorRecord.col.b);
            element2.appendChild(element3);
        }
        element.appendChild(element2);
        return element;
    }

    private static Element createRangeElement(Document document, String string, ConfigurableEmitter.Range range) {
        Element element = document.createElement(string);
        element.setAttribute("min", "" + range.getMin());
        element.setAttribute("max", "" + range.getMax());
        element.setAttribute("enabled", "" + range.isEnabled());
        return element;
    }

    private static Element createValueElement(Document document, String string, ConfigurableEmitter.Value value) {
        Element element = document.createElement(string);
        if (value instanceof ConfigurableEmitter.SimpleValue) {
            element.setAttribute("type", "simple");
            element.setAttribute("value", "" + value.getValue(0.0f));
        } else if (value instanceof ConfigurableEmitter.RandomValue) {
            element.setAttribute("type", "random");
            element.setAttribute("value", "" + ((ConfigurableEmitter.RandomValue)value).getValue());
        } else if (value instanceof ConfigurableEmitter.LinearInterpolator) {
            element.setAttribute("type", "linear");
            element.setAttribute("min", "" + ((ConfigurableEmitter.LinearInterpolator)value).getMin());
            element.setAttribute("max", "" + ((ConfigurableEmitter.LinearInterpolator)value).getMax());
            element.setAttribute("active", "" + ((ConfigurableEmitter.LinearInterpolator)value).isActive());
            ArrayList arrayList = ((ConfigurableEmitter.LinearInterpolator)value).getCurve();
            for (int i = 0; i < arrayList.size(); ++i) {
                Vector2f vector2f = (Vector2f)arrayList.get(i);
                Element element2 = document.createElement("point");
                element2.setAttribute("x", "" + vector2f.x);
                element2.setAttribute("y", "" + vector2f.y);
                element.appendChild(element2);
            }
        } else {
            Log.warn("unkown value type ignored: " + value.getClass());
        }
        return element;
    }

    private static void parseRangeElement(Element element, ConfigurableEmitter.Range range) {
        if (element == null) {
            return;
        }
        range.setMin(Float.parseFloat(element.getAttribute("min")));
        range.setMax(Float.parseFloat(element.getAttribute("max")));
        range.setEnabled("true".equals(element.getAttribute("enabled")));
    }

    private static void parseValueElement(Element element, ConfigurableEmitter.Value value) {
        if (element == null) {
            return;
        }
        String string = element.getAttribute("type");
        String string2 = element.getAttribute("value");
        if (string == null || string.length() == 0) {
            if (value instanceof ConfigurableEmitter.SimpleValue) {
                ((ConfigurableEmitter.SimpleValue)value).setValue(Float.parseFloat(string2));
            } else if (value instanceof ConfigurableEmitter.RandomValue) {
                ((ConfigurableEmitter.RandomValue)value).setValue(Float.parseFloat(string2));
            } else {
                Log.warn("problems reading element, skipping: " + element);
            }
        } else if (string.equals("simple")) {
            ((ConfigurableEmitter.SimpleValue)value).setValue(Float.parseFloat(string2));
        } else if (string.equals("random")) {
            ((ConfigurableEmitter.RandomValue)value).setValue(Float.parseFloat(string2));
        } else if (string.equals("linear")) {
            String string3 = element.getAttribute("min");
            String string4 = element.getAttribute("max");
            String string5 = element.getAttribute("active");
            NodeList nodeList = element.getElementsByTagName("point");
            ArrayList<Vector2f> arrayList = new ArrayList<Vector2f>();
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Element element2 = (Element)nodeList.item(i);
                float f = Float.parseFloat(element2.getAttribute("x"));
                float f2 = Float.parseFloat(element2.getAttribute("y"));
                arrayList.add(new Vector2f(f, f2));
            }
            ((ConfigurableEmitter.LinearInterpolator)value).setCurve(arrayList);
            ((ConfigurableEmitter.LinearInterpolator)value).setMin(Integer.parseInt(string3));
            ((ConfigurableEmitter.LinearInterpolator)value).setMax(Integer.parseInt(string4));
            ((ConfigurableEmitter.LinearInterpolator)value).setActive("true".equals(string5));
        } else {
            Log.warn("unkown type detected: " + string);
        }
    }
}

