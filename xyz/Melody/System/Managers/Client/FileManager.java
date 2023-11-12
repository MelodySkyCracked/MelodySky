/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package xyz.Melody.System.Managers.Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import xyz.Melody.Client;

public final class FileManager {
    private static File mcDataDir = Minecraft.func_71410_x().field_71412_D;
    private static File dir = new File(mcDataDir, "Melody");

    public static File getClientDir() {
        return dir;
    }

    public static File getConfigFile(String string) {
        File file = new File(dir, String.format("%s.txt", string));
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
        return file;
    }

    public static void init() {
        if (!dir.exists()) {
            Client.firstLaunch = true;
            Client.instance.logger.info("[MelodySky] [CONSOLE] Detected First Launch.");
            dir.mkdir();
        }
    }

    public static String getStringFronArray(List list) {
        String string = "";
        for (String string2 : list) {
            string = string + string2;
        }
        return string;
    }

    public static List read(String string) {
        FileInputStream fileInputStream;
        ArrayList<String> arrayList;
        block10: {
            File file;
            arrayList = new ArrayList<String>();
            if (!dir.exists()) {
                dir.mkdir();
            }
            if (!(file = new File(dir, string)).exists()) {
                file.createNewFile();
            }
            fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader((InputStream)fileInputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String string2 = "";
            while ((string2 = bufferedReader.readLine()) != null) {
                arrayList.add(string2);
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (fileInputStream == null) break block10;
            fileInputStream.close();
            ArrayList<String> arrayList2 = arrayList;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            return arrayList2;
        }
        try {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        return arrayList;
    }

    public static void save(String string, String string2, boolean bl) {
        try {
            File file = new File(dir, string);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, bl);
            fileWriter.write(string2);
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static List read(File file) {
        return FileManager.read(file.getAbsolutePath());
    }
}

