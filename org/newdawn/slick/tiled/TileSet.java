/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tiled;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TileSet {
    private final TiledMap map;
    public int index;
    public String name;
    public int firstGID;
    public int lastGID = Integer.MAX_VALUE;
    public int tileWidth;
    public int tileHeight;
    public SpriteSheet tiles;
    public int tilesAcross;
    public int tilesDown;
    private HashMap props = new HashMap();
    protected int tileSpacing = 0;
    protected int tileMargin = 0;

    public TileSet(TiledMap tiledMap, Element element, boolean bl) throws SlickException {
        Object object;
        Object object2;
        Object object3;
        Object object4;
        this.map = tiledMap;
        this.name = element.getAttribute("name");
        this.firstGID = Integer.parseInt(element.getAttribute("firstgid"));
        String string = element.getAttribute("source");
        if (string != null && !string.equals("")) {
            try {
                object4 = ResourceLoader.getResourceAsStream(tiledMap.getTilesLocation() + "/" + string);
                object3 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                object2 = ((DocumentBuilder)object3).parse((InputStream)object4);
                object = object2.getDocumentElement();
                element = object;
            }
            catch (Exception exception) {
                Log.error(exception);
                throw new SlickException("Unable to load or parse sourced tileset: " + this.map.tilesLocation + "/" + string);
            }
        }
        object4 = element.getAttribute("tilewidth");
        object3 = element.getAttribute("tileheight");
        if (((String)object4).length() == 0 || ((String)object3).length() == 0) {
            throw new SlickException("TiledMap requires that the map be created with tilesets that use a single image.  Check the WiKi for more complete information.");
        }
        this.tileWidth = Integer.parseInt((String)object4);
        this.tileHeight = Integer.parseInt((String)object3);
        object2 = element.getAttribute("spacing");
        if (object2 != null && !((String)object2).equals("")) {
            this.tileSpacing = Integer.parseInt((String)object2);
        }
        if ((object = element.getAttribute("margin")) != null && !((String)object).equals("")) {
            this.tileMargin = Integer.parseInt((String)object);
        }
        NodeList nodeList = element.getElementsByTagName("image");
        Element element2 = (Element)nodeList.item(0);
        String string2 = element2.getAttribute("source");
        Color color = null;
        String string3 = element2.getAttribute("trans");
        if (string3 != null && string3.length() > 0) {
            int n = Integer.parseInt(string3, 16);
            color = new Color(n);
        }
        if (bl) {
            Image image = new Image(tiledMap.getTilesLocation() + "/" + string2, false, 2, color);
            this.setTileSetImage(image);
        }
        NodeList nodeList2 = element.getElementsByTagName("tile");
        for (int i = 0; i < nodeList2.getLength(); ++i) {
            Element element3 = (Element)nodeList2.item(i);
            int n = Integer.parseInt(element3.getAttribute("id"));
            n += this.firstGID;
            Properties properties = new Properties();
            Element element4 = (Element)element3.getElementsByTagName("properties").item(0);
            NodeList nodeList3 = element4.getElementsByTagName("property");
            for (int j = 0; j < nodeList3.getLength(); ++j) {
                Element element5 = (Element)nodeList3.item(j);
                String string4 = element5.getAttribute("name");
                String string5 = element5.getAttribute("value");
                properties.setProperty(string4, string5);
            }
            this.props.put(new Integer(n), properties);
        }
    }

    public int getTileWidth() {
        return this.tileWidth;
    }

    public int getTileHeight() {
        return this.tileHeight;
    }

    public int getTileSpacing() {
        return this.tileSpacing;
    }

    public int getTileMargin() {
        return this.tileMargin;
    }

    public void setTileSetImage(Image image) {
        this.tiles = new SpriteSheet(image, this.tileWidth, this.tileHeight, this.tileSpacing, this.tileMargin);
        this.tilesAcross = this.tiles.getHorizontalCount();
        this.tilesDown = this.tiles.getVerticalCount();
        if (this.tilesAcross <= 0) {
            this.tilesAcross = 1;
        }
        if (this.tilesDown <= 0) {
            this.tilesDown = 1;
        }
        this.lastGID = this.tilesAcross * this.tilesDown + this.firstGID - 1;
    }

    public Properties getProperties(int n) {
        return (Properties)this.props.get(new Integer(n));
    }

    public int getTileX(int n) {
        return n % this.tilesAcross;
    }

    public int getTileY(int n) {
        return n / this.tilesAcross;
    }

    public void setLimit(int n) {
        this.lastGID = n;
    }

    public boolean contains(int n) {
        return n >= this.firstGID && n <= this.lastGID;
    }
}

