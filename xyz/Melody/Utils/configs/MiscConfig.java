/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package xyz.Melody.Utils.configs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import xyz.Melody.Client;
import xyz.Melody.System.Managers.Client.FileManager;
import xyz.Melody.System.Managers.Client.FriendManager;
import xyz.Melody.module.FMLModules.Utils.CTObject;

public class MiscConfig {
    public void read() {
        new Thread(MiscConfig::lambda$read$0, "MelodySky -> MiscConfig").start();
    }

    public void save() {
        Object object;
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        JsonObject jsonObject3 = new JsonObject();
        JsonObject jsonObject4 = new JsonObject();
        JsonObject jsonObject5 = new JsonObject();
        for (Object object2 : Client.chatTriggerMap) {
            object = new JsonObject();
            object.addProperty("Msg", ((CTObject)object2).getMessage());
            object.addProperty("Strip", Boolean.valueOf(((CTObject)object2).isStrip()));
            jsonObject2.add(((CTObject)object2).getTarget(), (JsonElement)object);
        }
        for (Object object2 : Client.textMap.keySet()) {
            object = (String)Client.textMap.get(object2);
            jsonObject3.addProperty((String)object2, (String)object);
        }
        for (Object object2 : Client.nickMap.keySet()) {
            object = (String)Client.nickMap.get(object2);
            jsonObject4.addProperty((String)object2, (String)object);
        }
        for (Object object2 : FriendManager.getFriends().keySet()) {
            object = (String)FriendManager.getFriends().get(object2);
            jsonObject5.addProperty((String)object2, (String)object);
        }
        jsonObject.add("CT", (JsonElement)jsonObject2);
        jsonObject.add("TR", (JsonElement)jsonObject3);
        jsonObject.add("NICK", (JsonElement)jsonObject4);
        jsonObject.add("FR", (JsonElement)jsonObject5);
        FileManager.save("MiscConfigs.json", Client.instance.gson.toJson((JsonElement)jsonObject).toString(), false);
    }

    private static void lambda$read$0() {
        try {
            Iterator iterator;
            Object object;
            Object object22;
            String string = FileManager.getStringFronArray(FileManager.read("MiscConfigs.json"));
            JsonObject jsonObject = (JsonObject)new JsonParser().parse(string);
            JsonObject jsonObject2 = jsonObject.get("CT").getAsJsonObject();
            JsonObject jsonObject3 = jsonObject.get("TR").getAsJsonObject();
            JsonObject jsonObject4 = jsonObject.get("NICK").getAsJsonObject();
            JsonObject jsonObject5 = jsonObject.get("FR").getAsJsonObject();
            Set set = jsonObject2.entrySet();
            for (Object object22 : set) {
                boolean bl;
                object = (String)object22.getKey();
                CTObject cTObject = new CTObject((String)object, (String)((Object)(iterator = ((JsonElement)object22.getValue()).getAsJsonObject().get("Msg").getAsString())), bl = ((JsonElement)object22.getValue()).getAsJsonObject().get("Strip").getAsBoolean());
                if (Client.chatTriggerMap.contains(cTObject)) continue;
                Client.chatTriggerMap.add(cTObject);
            }
            Set set2 = jsonObject3.entrySet();
            object22 = set2.iterator();
            while (object22.hasNext()) {
                object = (Map.Entry)object22.next();
                Client.textMap.put(object.getKey(), ((JsonElement)object.getValue()).getAsString());
            }
            object22 = jsonObject4.entrySet();
            object = object22.iterator();
            while (object.hasNext()) {
                iterator = (Map.Entry)object.next();
                Client.nickMap.put(iterator.getKey(), ((JsonElement)iterator.getValue()).getAsString());
            }
            object = jsonObject5.entrySet();
            iterator = object.iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator.next();
                FriendManager.getFriends().put(entry.getKey(), ((JsonElement)entry.getValue()).getAsString());
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

