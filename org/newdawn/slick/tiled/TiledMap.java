/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tiled;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.I;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.TileSet;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TiledMap {
    private static boolean headless;
    protected int width;
    protected int height;
    protected int tileWidth;
    protected int tileHeight;
    protected String tilesLocation;
    protected Properties props;
    protected ArrayList tileSets = new ArrayList();
    protected ArrayList layers = new ArrayList();
    protected ArrayList objectGroups = new ArrayList();
    protected static final int ORTHOGONAL = 1;
    protected static final int ISOMETRIC = 2;
    protected int orientation;
    private boolean loadTileSets = true;

    private static void setHeadless(boolean bl) {
        headless = bl;
    }

    public TiledMap(String string) throws SlickException {
        this(string, true);
    }

    public TiledMap(String string, boolean bl) throws SlickException {
        this.loadTileSets = bl;
        string = string.replace('\\', '/');
        this.load(ResourceLoader.getResourceAsStream(string), string.substring(0, string.lastIndexOf("/")));
    }

    public TiledMap(String string, String string2) throws SlickException {
        this.load(ResourceLoader.getResourceAsStream(string), string2);
    }

    public TiledMap(InputStream inputStream) throws SlickException {
        this.load(inputStream, "");
    }

    public TiledMap(InputStream inputStream, String string) throws SlickException {
        this.load(inputStream, string);
    }

    public String getTilesLocation() {
        return this.tilesLocation;
    }

    public int getLayerIndex(String string) {
        boolean bl = false;
        for (int i = 0; i < this.layers.size(); ++i) {
            Layer layer = (Layer)this.layers.get(i);
            if (!layer.name.equals(string)) continue;
            return i;
        }
        return -1;
    }

    public Image getTileImage(int n, int n2, int n3) {
        Layer layer = (Layer)this.layers.get(n3);
        int n4 = layer.data[n][n2][0];
        if (n4 >= 0 && n4 < this.tileSets.size()) {
            TileSet tileSet = (TileSet)this.tileSets.get(n4);
            int n5 = tileSet.getTileX(layer.data[n][n2][1]);
            int n6 = tileSet.getTileY(layer.data[n][n2][1]);
            return tileSet.tiles.getSprite(n5, n6);
        }
        return null;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getTileHeight() {
        return this.tileHeight;
    }

    public int getTileWidth() {
        return this.tileWidth;
    }

    public int getTileId(int n, int n2, int n3) {
        Layer layer = (Layer)this.layers.get(n3);
        return layer.getTileID(n, n2);
    }

    public void setTileId(int n, int n2, int n3, int n4) {
        Layer layer = (Layer)this.layers.get(n3);
        layer.setTileID(n, n2, n4);
    }

    public String getMapProperty(String string, String string2) {
        if (this.props == null) {
            return string2;
        }
        return this.props.getProperty(string, string2);
    }

    public String getLayerProperty(int n, String string, String string2) {
        Layer layer = (Layer)this.layers.get(n);
        if (layer == null || layer.props == null) {
            return string2;
        }
        return layer.props.getProperty(string, string2);
    }

    public String getTileProperty(int n, String string, String string2) {
        if (n == 0) {
            return string2;
        }
        TileSet tileSet = this.findTileSet(n);
        Properties properties = tileSet.getProperties(n);
        if (properties == null) {
            return string2;
        }
        return properties.getProperty(string, string2);
    }

    public void render(int n, int n2) {
        this.render(n, n2, 0, 0, this.width, this.height, false);
    }

    public void render(int n, int n2, int n3) {
        this.render(n, n2, 0, 0, this.getWidth(), this.getHeight(), n3, false);
    }

    public void render(int n, int n2, int n3, int n4, int n5, int n6) {
        this.render(n, n2, n3, n4, n5, n6, false);
    }

    public void render(int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl) {
        Layer layer = (Layer)this.layers.get(n7);
        switch (this.orientation) {
            case 1: {
                for (int i = 0; i < n6; ++i) {
                    layer.render(n, n2, n3, n4, n5, i, bl, this.tileWidth, this.tileHeight);
                }
                break;
            }
            case 2: {
                this.renderIsometricMap(n, n2, n3, n4, n5, n6, layer, bl);
                break;
            }
        }
    }

    public void render(int n, int n2, int n3, int n4, int n5, int n6, boolean bl) {
        switch (this.orientation) {
            case 1: {
                for (int i = 0; i < n6; ++i) {
                    for (int j = 0; j < this.layers.size(); ++j) {
                        Layer layer = (Layer)this.layers.get(j);
                        layer.render(n, n2, n3, n4, n5, i, bl, this.tileWidth, this.tileHeight);
                    }
                }
                break;
            }
            case 2: {
                this.renderIsometricMap(n, n2, n3, n4, n5, n6, null, bl);
                break;
            }
        }
    }

    protected void renderIsometricMap(int n, int n2, int n3, int n4, int n5, int n6, Layer layer, boolean bl) {
        ArrayList<Layer> arrayList = this.layers;
        if (layer != null) {
            arrayList = new ArrayList<Layer>();
            arrayList.add(layer);
        }
        int n7 = n5 * n6;
        int n8 = 0;
        boolean bl2 = false;
        int n9 = n;
        int n10 = n2;
        int n11 = 0;
        int n12 = 0;
        while (!bl2) {
            int n13 = n11;
            int n14 = n12;
            int n15 = n9;
            int n16 = 0;
            n16 = n6 > n5 ? (n12 < n5 - 1 ? n12 : (n5 - n13 < n6 ? n5 - n13 - 1 : n5 - 1)) : (n12 < n6 - 1 ? n12 : (n5 - n13 < n6 ? n5 - n13 - 1 : n6 - 1));
            for (int i = 0; i <= n16; ++i) {
                for (int j = 0; j < arrayList.size(); ++j) {
                    Layer layer2 = (Layer)arrayList.get(j);
                    layer2.render(n15, n10, n13, n14, 1, 0, bl, this.tileWidth, this.tileHeight);
                }
                n15 += this.tileWidth;
                ++n8;
                ++n13;
                --n14;
            }
            if (n12 < n6 - 1) {
                ++n12;
                n9 -= this.tileWidth / 2;
                n10 += this.tileHeight / 2;
            } else {
                ++n11;
                n9 += this.tileWidth / 2;
                n10 += this.tileHeight / 2;
            }
            if (n8 < n7) continue;
            bl2 = true;
        }
    }

    public int getLayerCount() {
        return this.layers.size();
    }

    private int parseInt(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException numberFormatException) {
            return 0;
        }
    }

    private void load(InputStream inputStream, String string) throws SlickException {
        this.tilesLocation = string;
        try {
            Object object;
            Object object2;
            int n;
            Object object3;
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            documentBuilder.setEntityResolver(new I(this));
            Document document = documentBuilder.parse(inputStream);
            Element element = document.getDocumentElement();
            this.orientation = element.getAttribute("orientation").equals("orthogonal") ? 1 : 2;
            this.width = this.parseInt(element.getAttribute("width"));
            this.height = this.parseInt(element.getAttribute("height"));
            this.tileWidth = this.parseInt(element.getAttribute("tilewidth"));
            this.tileHeight = this.parseInt(element.getAttribute("tileheight"));
            Element element2 = (Element)element.getElementsByTagName("properties").item(0);
            if (element2 != null && (object3 = element2.getElementsByTagName("property")) != null) {
                this.props = new Properties();
                for (n = 0; n < object3.getLength(); ++n) {
                    object2 = (Element)object3.item(n);
                    String string2 = object2.getAttribute("name");
                    object = object2.getAttribute("value");
                    this.props.setProperty(string2, (String)object);
                }
            }
            if (this.loadTileSets) {
                object3 = null;
                Object object4 = null;
                object2 = element.getElementsByTagName("tileset");
                for (int i = 0; i < object2.getLength(); ++i) {
                    object = (Element)object2.item(i);
                    object3 = new TileSet(this, (Element)object, !headless);
                    ((TileSet)object3).index = i;
                    if (object4 != null) {
                        ((TileSet)object4).setLimit(((TileSet)object3).firstGID - 1);
                    }
                    object4 = object3;
                    this.tileSets.add(object3);
                }
            }
            object3 = element.getElementsByTagName("layer");
            n = 0;
            while (n < object3.getLength()) {
                object2 = (Element)object3.item(n);
                Layer layer = new Layer(this, (Element)object2);
                layer.index = n++;
                this.layers.add(layer);
            }
            NodeList nodeList = element.getElementsByTagName("objectgroup");
            int n2 = 0;
            while (n2 < nodeList.getLength()) {
                Element element3 = (Element)nodeList.item(n2);
                object = new ObjectGroup(this, element3);
                ((ObjectGroup)object).index = n2++;
                this.objectGroups.add(object);
            }
        }
        catch (Exception exception) {
            Log.error(exception);
            throw new SlickException("Failed to parse tilemap", exception);
        }
    }

    public int getTileSetCount() {
        return this.tileSets.size();
    }

    public TileSet getTileSet(int n) {
        return (TileSet)this.tileSets.get(n);
    }

    public TileSet getTileSetByGID(int n) {
        for (int i = 0; i < this.tileSets.size(); ++i) {
            TileSet tileSet = (TileSet)this.tileSets.get(i);
            if (!tileSet.contains(n)) continue;
            return tileSet;
        }
        return null;
    }

    public TileSet findTileSet(int n) {
        for (int i = 0; i < this.tileSets.size(); ++i) {
            TileSet tileSet = (TileSet)this.tileSets.get(i);
            if (!tileSet.contains(n)) continue;
            return tileSet;
        }
        return null;
    }

    protected void renderedLine(int n, int n2, int n3) {
    }

    public int getObjectGroupCount() {
        return this.objectGroups.size();
    }

    public int getObjectCount(int n) {
        if (n >= 0 && n < this.objectGroups.size()) {
            ObjectGroup objectGroup = (ObjectGroup)this.objectGroups.get(n);
            return objectGroup.objects.size();
        }
        return -1;
    }

    public String getObjectName(int n, int n2) {
        if (n >= 0 && n < this.objectGroups.size()) {
            ObjectGroup objectGroup = (ObjectGroup)this.objectGroups.get(n);
            if (n2 >= 0 && n2 < objectGroup.objects.size()) {
                GroupObject groupObject = (GroupObject)objectGroup.objects.get(n2);
                return groupObject.name;
            }
        }
        return null;
    }

    public String getObjectType(int n, int n2) {
        if (n >= 0 && n < this.objectGroups.size()) {
            ObjectGroup objectGroup = (ObjectGroup)this.objectGroups.get(n);
            if (n2 >= 0 && n2 < objectGroup.objects.size()) {
                GroupObject groupObject = (GroupObject)objectGroup.objects.get(n2);
                return groupObject.type;
            }
        }
        return null;
    }

    public int getObjectX(int n, int n2) {
        if (n >= 0 && n < this.objectGroups.size()) {
            ObjectGroup objectGroup = (ObjectGroup)this.objectGroups.get(n);
            if (n2 >= 0 && n2 < objectGroup.objects.size()) {
                GroupObject groupObject = (GroupObject)objectGroup.objects.get(n2);
                return groupObject.x;
            }
        }
        return -1;
    }

    public int getObjectY(int n, int n2) {
        if (n >= 0 && n < this.objectGroups.size()) {
            ObjectGroup objectGroup = (ObjectGroup)this.objectGroups.get(n);
            if (n2 >= 0 && n2 < objectGroup.objects.size()) {
                GroupObject groupObject = (GroupObject)objectGroup.objects.get(n2);
                return groupObject.y;
            }
        }
        return -1;
    }

    public int getObjectWidth(int n, int n2) {
        if (n >= 0 && n < this.objectGroups.size()) {
            ObjectGroup objectGroup = (ObjectGroup)this.objectGroups.get(n);
            if (n2 >= 0 && n2 < objectGroup.objects.size()) {
                GroupObject groupObject = (GroupObject)objectGroup.objects.get(n2);
                return groupObject.width;
            }
        }
        return -1;
    }

    public int getObjectHeight(int n, int n2) {
        if (n >= 0 && n < this.objectGroups.size()) {
            ObjectGroup objectGroup = (ObjectGroup)this.objectGroups.get(n);
            if (n2 >= 0 && n2 < objectGroup.objects.size()) {
                GroupObject groupObject = (GroupObject)objectGroup.objects.get(n2);
                return groupObject.height;
            }
        }
        return -1;
    }

    public String getObjectImage(int n, int n2) {
        if (n >= 0 && n < this.objectGroups.size()) {
            ObjectGroup objectGroup = (ObjectGroup)this.objectGroups.get(n);
            if (n2 >= 0 && n2 < objectGroup.objects.size()) {
                GroupObject groupObject = (GroupObject)objectGroup.objects.get(n2);
                if (groupObject == null) {
                    return null;
                }
                return GroupObject.access$000(groupObject);
            }
        }
        return null;
    }

    public String getObjectProperty(int n, int n2, String string, String string2) {
        if (n >= 0 && n < this.objectGroups.size()) {
            ObjectGroup objectGroup = (ObjectGroup)this.objectGroups.get(n);
            if (n2 >= 0 && n2 < objectGroup.objects.size()) {
                GroupObject groupObject = (GroupObject)objectGroup.objects.get(n2);
                if (groupObject == null) {
                    return string2;
                }
                if (groupObject.props == null) {
                    return string2;
                }
                return groupObject.props.getProperty(string, string2);
            }
        }
        return string2;
    }

    protected class GroupObject {
        public int index;
        public String name;
        public String type;
        public int x;
        public int y;
        public int width;
        public int height;
        private String image;
        public Properties props;
        final TiledMap this$0;

        public GroupObject(TiledMap tiledMap, Element element) throws SlickException {
            NodeList nodeList;
            Element element2;
            this.this$0 = tiledMap;
            this.name = element.getAttribute("name");
            this.type = element.getAttribute("type");
            this.x = Integer.parseInt(element.getAttribute("x"));
            this.y = Integer.parseInt(element.getAttribute("y"));
            this.width = Integer.parseInt(element.getAttribute("width"));
            this.height = Integer.parseInt(element.getAttribute("height"));
            Element element3 = (Element)element.getElementsByTagName("image").item(0);
            if (element3 != null) {
                this.image = element3.getAttribute("source");
            }
            if ((element2 = (Element)element.getElementsByTagName("properties").item(0)) != null && (nodeList = element2.getElementsByTagName("property")) != null) {
                this.props = new Properties();
                for (int i = 0; i < nodeList.getLength(); ++i) {
                    Element element4 = (Element)nodeList.item(i);
                    String string = element4.getAttribute("name");
                    String string2 = element4.getAttribute("value");
                    this.props.setProperty(string, string2);
                }
            }
        }

        static String access$000(GroupObject groupObject) {
            return groupObject.image;
        }
    }

    protected class ObjectGroup {
        public int index;
        public String name;
        public ArrayList objects;
        public int width;
        public int height;
        public Properties props;
        final TiledMap this$0;

        public ObjectGroup(TiledMap tiledMap, Element element) throws SlickException {
            Object object;
            Element element2;
            int n;
            NodeList nodeList;
            this.this$0 = tiledMap;
            this.name = element.getAttribute("name");
            this.width = Integer.parseInt(element.getAttribute("width"));
            this.height = Integer.parseInt(element.getAttribute("height"));
            this.objects = new ArrayList();
            Element element3 = (Element)element.getElementsByTagName("properties").item(0);
            if (element3 != null && (nodeList = element3.getElementsByTagName("property")) != null) {
                this.props = new Properties();
                for (n = 0; n < nodeList.getLength(); ++n) {
                    element2 = (Element)nodeList.item(n);
                    object = element2.getAttribute("name");
                    String string = element2.getAttribute("value");
                    this.props.setProperty((String)object, string);
                }
            }
            nodeList = element.getElementsByTagName("object");
            n = 0;
            while (n < nodeList.getLength()) {
                element2 = (Element)nodeList.item(n);
                object = new GroupObject(tiledMap, element2);
                ((GroupObject)object).index = n++;
                this.objects.add(object);
            }
        }
    }
}

