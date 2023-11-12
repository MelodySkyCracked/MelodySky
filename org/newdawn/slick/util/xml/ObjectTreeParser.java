/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util.xml;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.util.xml.SlickXMLException;
import org.newdawn.slick.util.xml.XMLElement;
import org.newdawn.slick.util.xml.XMLElementList;
import org.newdawn.slick.util.xml.XMLParser;

public class ObjectTreeParser {
    private HashMap nameToClass = new HashMap();
    private String defaultPackage;
    private ArrayList ignored = new ArrayList();
    private String addMethod = "add";

    public ObjectTreeParser() {
    }

    public ObjectTreeParser(String string) {
        this.defaultPackage = string;
    }

    public void addElementMapping(String string, Class clazz) {
        this.nameToClass.put(string, clazz);
    }

    public void addIgnoredElement(String string) {
        this.ignored.add(string);
    }

    public void setAddMethodName(String string) {
        this.addMethod = string;
    }

    public void setDefaultPackage(String string) {
        this.defaultPackage = string;
    }

    public Object parse(String string) throws SlickXMLException {
        return this.parse(string, ResourceLoader.getResourceAsStream(string));
    }

    public Object parse(String string, InputStream inputStream) throws SlickXMLException {
        XMLParser xMLParser = new XMLParser();
        XMLElement xMLElement = xMLParser.parse(string, inputStream);
        return this.traverse(xMLElement);
    }

    public Object parseOnto(String string, Object object) throws SlickXMLException {
        return this.parseOnto(string, ResourceLoader.getResourceAsStream(string), object);
    }

    public Object parseOnto(String string, InputStream inputStream, Object object) throws SlickXMLException {
        XMLParser xMLParser = new XMLParser();
        XMLElement xMLElement = xMLParser.parse(string, inputStream);
        return this.traverse(xMLElement, object);
    }

    private Class getClassForElementName(String string) {
        Class clazz = (Class)this.nameToClass.get(string);
        if (clazz != null) {
            return clazz;
        }
        if (this.defaultPackage != null) {
            try {
                return Class.forName(this.defaultPackage + "." + string);
            }
            catch (ClassNotFoundException classNotFoundException) {
                // empty catch block
            }
        }
        return null;
    }

    private Object traverse(XMLElement xMLElement) throws SlickXMLException {
        return this.traverse(xMLElement, null);
    }

    private Object traverse(XMLElement xMLElement, Object object) throws SlickXMLException {
        String string = xMLElement.getName();
        if (this.ignored.contains(string)) {
            return null;
        }
        Class clazz = object == null ? this.getClassForElementName(string) : object.getClass();
        if (clazz == null) {
            throw new SlickXMLException("Unable to map element " + string + " to a class, define the mapping");
        }
        try {
            Object object2;
            Object object3;
            Object object4;
            Object object5;
            Object object6;
            if (object == null) {
                Method method;
                object = clazz.newInstance();
                object6 = this.getMethod(clazz, "setXMLElementName", new Class[]{String.class});
                if (object6 != null) {
                    this.invoke((Method)object6, object, new Object[]{string});
                }
                if ((method = this.getMethod(clazz, "setXMLElementContent", new Class[]{String.class})) != null) {
                    this.invoke(method, object, new Object[]{xMLElement.getContent()});
                }
            }
            object6 = xMLElement.getAttributeNames();
            for (int i = 0; i < ((String[])object6).length; ++i) {
                String string2 = "set" + object6[i];
                object5 = this.findMethod(clazz, string2);
                if (object5 == null) {
                    object4 = this.findField(clazz, object6[i]);
                    if (object4 != null) {
                        object3 = xMLElement.getAttribute(object6[i]);
                        object2 = this.typeValue((String)object3, ((Field)object4).getType());
                        this.setField((Field)object4, object, object2);
                        continue;
                    }
                    Log.info("Unable to find property on: " + clazz + " for attribute: " + object6[i]);
                    continue;
                }
                object4 = xMLElement.getAttribute(object6[i]);
                object3 = this.typeValue((String)object4, ((Method)object5).getParameterTypes()[0]);
                this.invoke((Method)object5, object, new Object[]{object3});
            }
            XMLElementList xMLElementList = xMLElement.getChildren();
            for (int i = 0; i < xMLElementList.size(); ++i) {
                object5 = xMLElementList.get(i);
                object4 = this.traverse((XMLElement)object5);
                if (object4 == null) continue;
                object3 = this.addMethod;
                object2 = this.findMethod(clazz, (String)object3, object4.getClass());
                if (object2 == null) {
                    Log.info("Unable to find method to add: " + object4 + " to " + clazz);
                    continue;
                }
                this.invoke((Method)object2, object, new Object[]{object4});
            }
            return object;
        }
        catch (InstantiationException instantiationException) {
            throw new SlickXMLException("Unable to instance " + clazz + " for element " + string + ", no zero parameter constructor?", instantiationException);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new SlickXMLException("Unable to instance " + clazz + " for element " + string + ", no zero parameter constructor?", illegalAccessException);
        }
    }

    private Object typeValue(String string, Class clazz) throws SlickXMLException {
        if (clazz == String.class) {
            return string;
        }
        try {
            clazz = this.mapPrimitive(clazz);
            return clazz.getConstructor(String.class).newInstance(string);
        }
        catch (Exception exception) {
            throw new SlickXMLException("Failed to convert: " + string + " to the expected primitive type: " + clazz, exception);
        }
    }

    private Class mapPrimitive(Class clazz) {
        if (clazz == Integer.TYPE) {
            return Integer.class;
        }
        if (clazz == Double.TYPE) {
            return Double.class;
        }
        if (clazz == Float.TYPE) {
            return Float.class;
        }
        if (clazz == Boolean.TYPE) {
            return Boolean.class;
        }
        if (clazz == Long.TYPE) {
            return Long.class;
        }
        throw new RuntimeException("Unsupported primitive: " + clazz);
    }

    private Field findField(Class clazz, String string) {
        Field[] fieldArray = clazz.getDeclaredFields();
        for (int i = 0; i < fieldArray.length; ++i) {
            if (!fieldArray[i].getName().equalsIgnoreCase(string)) continue;
            if (fieldArray[i].getType().isPrimitive()) {
                return fieldArray[i];
            }
            if (fieldArray[i].getType() != String.class) continue;
            return fieldArray[i];
        }
        return null;
    }

    private Method findMethod(Class clazz, String string) {
        Method[] methodArray = clazz.getDeclaredMethods();
        for (int i = 0; i < methodArray.length; ++i) {
            Method method;
            Class<?>[] classArray;
            if (!methodArray[i].getName().equalsIgnoreCase(string) || (classArray = (method = methodArray[i]).getParameterTypes()).length != 1) continue;
            return method;
        }
        return null;
    }

    private Method findMethod(Class clazz, String string, Class clazz2) {
        Method[] methodArray = clazz.getDeclaredMethods();
        for (int i = 0; i < methodArray.length; ++i) {
            Method method;
            Class<?>[] classArray;
            if (!methodArray[i].getName().equalsIgnoreCase(string) || (classArray = (method = methodArray[i]).getParameterTypes()).length != 1 || !method.getParameterTypes()[0].isAssignableFrom(clazz2)) continue;
            return method;
        }
        return null;
    }

    private void setField(Field field, Object object, Object object2) throws SlickXMLException {
        try {
            field.setAccessible(true);
            field.set(object, object2);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new SlickXMLException("Failed to set: " + field + " for an XML attribute, is it valid?", illegalArgumentException);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new SlickXMLException("Failed to set: " + field + " for an XML attribute, is it valid?", illegalAccessException);
        }
        field.setAccessible(false);
    }

    private void invoke(Method method, Object object, Object[] objectArray) throws SlickXMLException {
        try {
            method.setAccessible(true);
            method.invoke(object, objectArray);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new SlickXMLException("Failed to invoke: " + method + " for an XML attribute, is it valid?", illegalArgumentException);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new SlickXMLException("Failed to invoke: " + method + " for an XML attribute, is it valid?", illegalAccessException);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new SlickXMLException("Failed to invoke: " + method + " for an XML attribute, is it valid?", invocationTargetException);
        }
        method.setAccessible(false);
    }

    private Method getMethod(Class clazz, String string, Class[] classArray) {
        try {
            return clazz.getMethod(string, classArray);
        }
        catch (SecurityException securityException) {
            return null;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
    }
}

