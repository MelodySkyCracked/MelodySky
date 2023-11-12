/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.xpcom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

public class INIParser {
    private HashMap mSections;

    public INIParser(String string, Charset charset) throws FileNotFoundException, IOException {
        this.initFromFile(new File(string), charset);
    }

    public INIParser(String string) throws FileNotFoundException, IOException {
        this.initFromFile(new File(string), Charset.forName("UTF-8"));
    }

    public INIParser(File file, Charset charset) throws FileNotFoundException, IOException {
        this.initFromFile(file, charset);
    }

    public INIParser(File file) throws FileNotFoundException, IOException {
        this.initFromFile(file, Charset.forName("UTF-8"));
    }

    private void initFromFile(File file, Charset charset) throws FileNotFoundException, IOException {
        String string;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream)new FileInputStream(file), charset));
        this.mSections = new HashMap();
        String string2 = null;
        while ((string = bufferedReader.readLine()) != null) {
            StringTokenizer stringTokenizer;
            String string3 = string.trim();
            if (string3.length() == 0 || string3.startsWith("#") || string3.startsWith(";")) continue;
            if (string.startsWith("[")) {
                if (!string3.endsWith("]") || string3.indexOf("]") != string3.length() - 1) {
                    string2 = null;
                    continue;
                }
                string2 = string3.substring(1, string3.length() - 1);
                continue;
            }
            if (string2 == null || (stringTokenizer = new StringTokenizer(string, "=")).countTokens() != 2) continue;
            Properties properties = (Properties)this.mSections.get(string2);
            if (properties == null) {
                properties = new Properties();
                this.mSections.put(string2, properties);
            }
            properties.setProperty(stringTokenizer.nextToken(), stringTokenizer.nextToken());
        }
        bufferedReader.close();
    }

    public Iterator getSections() {
        return this.mSections.keySet().iterator();
    }

    public Iterator getKeys(String string) {
        Properties properties = (Properties)this.mSections.get(string);
        if (properties == null) {
            return null;
        }
        return new PropertiesIterator(this, properties.propertyNames());
    }

    public String getString(String string, String string2) {
        Properties properties = (Properties)this.mSections.get(string);
        if (properties == null) {
            return null;
        }
        return properties.getProperty(string2);
    }

    class PropertiesIterator
    implements Iterator {
        private Enumeration e;
        final INIParser this$0;

        public PropertiesIterator(INIParser iNIParser, Enumeration enumeration) {
            this.this$0 = iNIParser;
            this.e = enumeration;
        }

        @Override
        public boolean hasNext() {
            return this.e.hasMoreElements();
        }

        public Object next() {
            return this.e.nextElement();
        }

        @Override
        public void remove() {
        }
    }
}

