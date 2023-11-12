/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonParseException
 *  net.minecraft.launchwrapper.Launch
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package org.spongepowered.asm.mixin.refmap;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.launchwrapper.Launch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ReferenceMapper
implements Serializable {
    private static final long serialVersionUID = 2L;
    public static final String DEFAULT_RESOURCE = "mixin.refmap.json";
    public static final ReferenceMapper DEFAULT_MAPPER = new ReferenceMapper(true);
    private static final Logger logger = LogManager.getLogger((String)"mixin");
    private final Map mappings = Maps.newHashMap();
    private final Map data = Maps.newHashMap();
    private final transient boolean readOnly;
    private transient String context = null;

    public ReferenceMapper() {
        this(false);
    }

    private ReferenceMapper(boolean bl) {
        this.readOnly = bl;
    }

    public String getContext() {
        return this.context;
    }

    public void setContext(String string) {
        this.context = string;
    }

    public String remap(String string, String string2) {
        return this.remapWithContext(this.context, string, string2);
    }

    public String remapWithContext(String string, String string2, String string3) {
        Map map = this.mappings;
        if (string != null && (map = (Map)this.data.get(string)) == null) {
            map = this.mappings;
        }
        return this.remap(map, string2, string3);
    }

    private String remap(Map map, String string, String string2) {
        Object object;
        Object object2;
        if (string == null) {
            object2 = map.values().iterator();
            while (object2.hasNext()) {
                object = (Map)object2.next();
                if (!object.containsKey(string2)) continue;
                return (String)object.get(string2);
            }
        }
        if ((object2 = (Map)map.get(string)) == null) {
            return string2;
        }
        object = (String)object2.get(string2);
        return object != null ? object : string2;
    }

    public String addMapping(String string, String string2, String string3, String string4) {
        HashMap<String, String> hashMap;
        if (this.readOnly || string3 == null || string4 == null || string3.equals(string4)) {
            return null;
        }
        Map map = this.mappings;
        if (string != null && (map = (Map)this.data.get(string)) == null) {
            map = Maps.newHashMap();
            this.data.put(string, map);
        }
        if ((hashMap = (HashMap<String, String>)map.get(string2)) == null) {
            hashMap = new HashMap<String, String>();
            map.put(string2, hashMap);
        }
        return hashMap.put(string3, string4);
    }

    public void write(Appendable appendable) {
        new GsonBuilder().setPrettyPrinting().create().toJson((Object)this, appendable);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ReferenceMapper read(String string) {
        InputStreamReader inputStreamReader;
        block12: {
            ReferenceMapper referenceMapper;
            inputStreamReader = null;
            try {
                InputStream inputStream = Launch.classLoader.getResourceAsStream(string);
                if (inputStream == null) break block12;
                inputStreamReader = new InputStreamReader(inputStream);
                referenceMapper = ReferenceMapper.readJson(inputStreamReader);
                if (inputStreamReader == null) return referenceMapper;
            }
            catch (JsonParseException jsonParseException) {
                logger.error("Invalid REFMAP JSON in " + string + ": " + ((Object)((Object)jsonParseException)).getClass().getName() + " " + jsonParseException.getMessage());
                if (inputStreamReader == null) return DEFAULT_MAPPER;
                try {
                    ((Reader)inputStreamReader).close();
                    return DEFAULT_MAPPER;
                }
                catch (IOException iOException) {
                    return DEFAULT_MAPPER;
                }
            }
            catch (Exception exception) {
                logger.error("Failed reading REFMAP JSON from " + string + ": " + exception.getClass().getName() + " " + exception.getMessage());
                if (inputStreamReader == null) return DEFAULT_MAPPER;
                try {
                    ((Reader)inputStreamReader).close();
                    return DEFAULT_MAPPER;
                }
                catch (IOException iOException) {
                    return DEFAULT_MAPPER;
                }
            }
            try {
                ((Reader)inputStreamReader).close();
                return referenceMapper;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            return referenceMapper;
        }
        if (inputStreamReader == null) return DEFAULT_MAPPER;
        try {
            ((Reader)inputStreamReader).close();
            return DEFAULT_MAPPER;
        }
        catch (IOException iOException) {
            return DEFAULT_MAPPER;
        }
    }

    public static ReferenceMapper read(Reader reader) {
        try {
            return ReferenceMapper.readJson(reader);
        }
        catch (Exception exception) {
            return DEFAULT_MAPPER;
        }
    }

    private static ReferenceMapper readJson(Reader reader) {
        return (ReferenceMapper)new Gson().fromJson(reader, ReferenceMapper.class);
    }
}

