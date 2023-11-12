/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tiled;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TileSet;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.Log;
import org.w3c.dom.Element;

public class Layer {
    private static byte[] baseCodes;
    private final TiledMap map;
    public int index;
    public String name;
    public int[][][] data;
    public int width;
    public int height;
    public Properties props;

    public Layer(TiledMap tiledMap, Element element) throws SlickException {
        Object object;
        Object object2;
        Object object3;
        Object object4;
        this.map = tiledMap;
        this.name = element.getAttribute("name");
        this.width = Integer.parseInt(element.getAttribute("width"));
        this.height = Integer.parseInt(element.getAttribute("height"));
        this.data = new int[this.width][this.height][3];
        Element element2 = (Element)element.getElementsByTagName("properties").item(0);
        if (element2 != null && (object4 = element2.getElementsByTagName("property")) != null) {
            this.props = new Properties();
            for (int i = 0; i < object4.getLength(); ++i) {
                object3 = (Element)object4.item(i);
                object2 = object3.getAttribute("name");
                object = object3.getAttribute("value");
                this.props.setProperty((String)object2, (String)object);
            }
        }
        object4 = (Element)element.getElementsByTagName("data").item(0);
        String string = object4.getAttribute("encoding");
        object3 = object4.getAttribute("compression");
        if (string.equals("base64") && ((String)object3).equals("gzip")) {
            try {
                object2 = object4.getFirstChild();
                object = object2.getNodeValue().trim().toCharArray();
                byte[] byArray = this.decodeBase64((char[])object);
                GZIPInputStream gZIPInputStream = new GZIPInputStream(new ByteArrayInputStream(byArray));
                for (int i = 0; i < this.height; ++i) {
                    for (int j = 0; j < this.width; ++j) {
                        int n = 0;
                        n |= gZIPInputStream.read();
                        n |= gZIPInputStream.read() << 8;
                        n |= gZIPInputStream.read() << 16;
                        if ((n |= gZIPInputStream.read() << 24) == 0) {
                            this.data[j][i][0] = -1;
                            this.data[j][i][1] = 0;
                            this.data[j][i][2] = 0;
                            continue;
                        }
                        TileSet tileSet = tiledMap.findTileSet(n);
                        if (tileSet != null) {
                            this.data[j][i][0] = tileSet.index;
                            this.data[j][i][1] = n - tileSet.firstGID;
                        }
                        this.data[j][i][2] = n;
                    }
                }
            }
            catch (IOException iOException) {
                Log.error(iOException);
                throw new SlickException("Unable to decode base 64 block");
            }
        } else {
            throw new SlickException("Unsupport tiled map type: " + string + "," + (String)object3 + " (only gzip base64 supported)");
        }
    }

    public int getTileID(int n, int n2) {
        return this.data[n][n2][2];
    }

    public void setTileID(int n, int n2, int n3) {
        if (n3 == 0) {
            this.data[n][n2][0] = -1;
            this.data[n][n2][1] = 0;
            this.data[n][n2][2] = 0;
        } else {
            TileSet tileSet = this.map.findTileSet(n3);
            this.data[n][n2][0] = tileSet.index;
            this.data[n][n2][1] = n3 - tileSet.firstGID;
            this.data[n][n2][2] = n3;
        }
    }

    public void render(int n, int n2, int n3, int n4, int n5, int n6, boolean bl, int n7, int n8) {
        for (int i = 0; i < this.map.getTileSetCount(); ++i) {
            TileSet tileSet = null;
            for (int j = 0; j < n5; ++j) {
                if (n3 + j < 0 || n4 + n6 < 0 || n3 + j >= this.width || n4 + n6 >= this.height || this.data[n3 + j][n4 + n6][0] != i) continue;
                if (tileSet == null) {
                    tileSet = this.map.getTileSet(i);
                    tileSet.tiles.startUse();
                }
                int n9 = tileSet.getTileX(this.data[n3 + j][n4 + n6][1]);
                int n10 = tileSet.getTileY(this.data[n3 + j][n4 + n6][1]);
                int n11 = tileSet.tileHeight - n8;
                tileSet.tiles.renderInUse(n + j * n7, n2 + n6 * n8 - n11, n9, n10);
            }
            if (bl) {
                if (tileSet != null) {
                    tileSet.tiles.endUse();
                    tileSet = null;
                }
                this.map.renderedLine(n6, n6 + n4, this.index);
            }
            if (tileSet == null) continue;
            tileSet.tiles.endUse();
        }
    }

    private byte[] decodeBase64(char[] cArray) {
        int n;
        int n2 = cArray.length;
        for (n = 0; n < cArray.length; ++n) {
            if (cArray[n] <= '\u00ff' && baseCodes[cArray[n]] >= 0) continue;
            --n2;
        }
        n = n2 / 4 * 3;
        if (n2 % 4 == 3) {
            n += 2;
        }
        if (n2 % 4 == 2) {
            ++n;
        }
        byte[] byArray = new byte[n];
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        for (int i = 0; i < cArray.length; ++i) {
            int n6;
            int n7 = n6 = cArray[i] > '\u00ff' ? -1 : baseCodes[cArray[i]];
            if (n6 < 0) continue;
            n4 <<= 6;
            n4 |= n6;
            if ((n3 += 6) < 8) continue;
            byArray[n5++] = (byte)(n4 >> (n3 -= 8) & 0xFF);
        }
        if (n5 != byArray.length) {
            throw new RuntimeException("Data length appears to be wrong (wrote " + n5 + " should be " + byArray.length + ")");
        }
        return byArray;
    }

    static {
        int n;
        baseCodes = new byte[256];
        for (n = 0; n < 256; ++n) {
            Layer.baseCodes[n] = -1;
        }
        for (n = 65; n <= 90; ++n) {
            Layer.baseCodes[n] = (byte)(n - 65);
        }
        for (n = 97; n <= 122; ++n) {
            Layer.baseCodes[n] = (byte)(26 + n - 97);
        }
        for (n = 48; n <= 57; ++n) {
            Layer.baseCodes[n] = (byte)(52 + n - 48);
        }
        Layer.baseCodes[43] = 62;
        Layer.baseCodes[47] = 63;
    }
}

