/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick;

import java.io.IOException;
import java.util.HashMap;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.muffin.FileMuffin;
import org.newdawn.slick.muffin.Muffin;
import org.newdawn.slick.util.Log;

public class SavedState {
    private String fileName;
    private Muffin muffin;
    private HashMap numericData = new HashMap();
    private HashMap stringData = new HashMap();

    public SavedState(String string) throws SlickException {
        this.fileName = string;
        if (!this.isWebstartAvailable()) {
            this.muffin = new FileMuffin();
        }
        try {
            this.load();
        }
        catch (IOException iOException) {
            throw new SlickException("Failed to load state on startup", iOException);
        }
    }

    public double getNumber(String string) {
        return this.getNumber(string, 0.0);
    }

    public double getNumber(String string, double d) {
        Double d2 = (Double)this.numericData.get(string);
        if (d2 == null) {
            return d;
        }
        return d2;
    }

    public void setNumber(String string, double d) {
        this.numericData.put(string, new Double(d));
    }

    public String getString(String string) {
        return this.getString(string, null);
    }

    public String getString(String string, String string2) {
        String string3 = (String)this.stringData.get(string);
        if (string3 == null) {
            return string2;
        }
        return string3;
    }

    public void setString(String string, String string2) {
        this.stringData.put(string, string2);
    }

    public void save() throws IOException {
        this.muffin.saveFile(this.numericData, this.fileName + "_Number");
        this.muffin.saveFile(this.stringData, this.fileName + "_String");
    }

    public void load() throws IOException {
        this.numericData = this.muffin.loadFile(this.fileName + "_Number");
        this.stringData = this.muffin.loadFile(this.fileName + "_String");
    }

    public void clear() {
        this.numericData.clear();
        this.stringData.clear();
    }

    private boolean isWebstartAvailable() {
        try {
            Class.forName("javax.jnlp.ServiceManager");
            Log.info("Webstart detected using Muffins");
        }
        catch (Exception exception) {
            Log.info("Using Local File System");
            return false;
        }
        return true;
    }
}

