/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.muffin;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import org.newdawn.slick.muffin.Muffin;
import org.newdawn.slick.util.Log;

public class FileMuffin
implements Muffin {
    @Override
    public void saveFile(HashMap hashMap, String string) throws IOException {
        String string2 = System.getProperty("user.home");
        File file = new File(string2);
        if (!(file = new File(file, ".java")).exists()) {
            file.mkdir();
        }
        file = new File(file, string);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(hashMap);
        objectOutputStream.close();
    }

    @Override
    public HashMap loadFile(String string) throws IOException {
        HashMap hashMap = new HashMap();
        String string2 = System.getProperty("user.home");
        File file = new File(string2);
        file = new File(file, ".java");
        if ((file = new File(file, string)).exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                hashMap = (HashMap)objectInputStream.readObject();
                objectInputStream.close();
            }
            catch (EOFException eOFException) {
            }
            catch (ClassNotFoundException classNotFoundException) {
                Log.error(classNotFoundException);
                throw new IOException("Failed to pull state from store - class not found");
            }
        }
        return hashMap;
    }
}

