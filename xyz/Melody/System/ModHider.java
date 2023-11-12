/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  net.minecraftforge.common.config.Configuration
 *  net.minecraftforge.common.config.Property
 *  net.minecraftforge.fml.common.Loader
 *  net.minecraftforge.fml.common.ModContainer
 */
package xyz.Melody.System;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import xyz.Melody.System.Managers.Client.FileManager;

public class ModHider {
    public static Configuration modsConfig;
    public static final List wellKnownMods;
    private static final HashMap modProperties;

    public static void initModHider() {
        File file = new File(FileManager.getClientDir(), "HideMods.txt");
        modsConfig = new Configuration(file);
        modsConfig.load();
        for (ModContainer modContainer : Loader.instance().getActiveModList()) {
            modProperties.put(modContainer.getModId(), modsConfig.get("general", modContainer.getName(), wellKnownMods.contains(modContainer.getModId()), modContainer.getModId()));
        }
    }

    public static boolean isWhitelisted(String string) {
        return ((Property)modProperties.get(string)).getBoolean();
    }

    static {
        wellKnownMods = Lists.newArrayList((Object[])new String[]{"FML", "Forge", "mcp"});
        modProperties = new HashMap();
    }
}

