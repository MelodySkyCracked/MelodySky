/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package xyz.Melody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import xyz.Melody.Client;
import xyz.Melody.Event.value.Mode;
import xyz.Melody.Event.value.Numbers;
import xyz.Melody.Event.value.Option;
import xyz.Melody.Event.value.TextValue;
import xyz.Melody.Event.value.Value;
import xyz.Melody.GUI.ClickGui.DickGui;
import xyz.Melody.System.Managers.Client.FileManager;
import xyz.Melody.System.Managers.Client.ModuleManager;
import xyz.Melody.Utils.configs.FairySoulsConfig;
import xyz.Melody.Utils.configs.MiscConfig;
import xyz.Melody.module.Module;

public class Config {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public MiscConfig miscConfig = new MiscConfig();
    public FairySoulsConfig fairySoulsConfig = new FairySoulsConfig();

    public void read() {
        new Thread(this::lambda$read$0, "MelodySky -> Config").start();
    }

    public void save() {
        JsonObject jsonObject = new JsonObject();
        for (Module module : ModuleManager.getModules()) {
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("Enabled", Boolean.valueOf(module.isEnabled()));
            jsonObject2.addProperty("Key", (Number)module.getKey());
            for (Value value : module.getValues()) {
                jsonObject2.addProperty(value.getName(), value.getValue().toString());
            }
            jsonObject.add(module.getName(), (JsonElement)jsonObject2);
        }
        FileManager.save("Config.json", this.gson.toJson((JsonElement)jsonObject).toString(), false);
        this.miscConfig.save();
    }

    private void lambda$read$0() {
        try {
            String string = FileManager.getStringFronArray(FileManager.read("Config.json"));
            JsonObject jsonObject = (JsonObject)new JsonParser().parse(string);
            for (Module module : ModuleManager.getModules()) {
                if (module == null || !jsonObject.toString().contains(module.getName())) continue;
                JsonObject jsonObject2 = jsonObject.get(module.getName()).getAsJsonObject();
                module.preEnable(jsonObject2.get("Enabled").getAsBoolean());
                module.setKey(jsonObject2.get("Key").getAsInt());
                for (Value value : module.getValues()) {
                    if (value == null || !jsonObject2.toString().contains(value.getName())) continue;
                    if (value instanceof Numbers) {
                        ((Numbers)value).setValue(jsonObject2.get(value.getName()).getAsDouble());
                    }
                    if (value instanceof Option) {
                        ((Option)value).setValue(jsonObject2.get(value.getName()).getAsBoolean());
                    }
                    if (value instanceof Mode) {
                        ((Mode)value).setMode(jsonObject2.get(value.getName()).getAsString());
                    }
                    if (!(value instanceof TextValue)) continue;
                    ((TextValue)value).setValue(jsonObject2.get(value.getName()).getAsString());
                }
            }
            new DickGui().init();
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            Client.instance.logger.info("[MelodySky] [CONSOLE] Error Occurred While Loading Config.");
        }
        this.miscConfig.read();
        Client.instance.logger.info("[MelodySky] [CONSOLE] Config Loaded Successfully.");
    }
}

