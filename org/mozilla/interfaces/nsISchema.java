/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISchemaAttribute;
import org.mozilla.interfaces.nsISchemaAttributeGroup;
import org.mozilla.interfaces.nsISchemaCollection;
import org.mozilla.interfaces.nsISchemaComponent;
import org.mozilla.interfaces.nsISchemaElement;
import org.mozilla.interfaces.nsISchemaModelGroup;
import org.mozilla.interfaces.nsISchemaType;

public interface nsISchema
extends nsISchemaComponent {
    public static final String NS_ISCHEMA_IID = "{3c14a021-6f4e-11d5-9b46-000064657374}";

    public String getSchemaNamespace();

    public long getTypeCount();

    public nsISchemaType getTypeByIndex(long var1);

    public nsISchemaType getTypeByName(String var1);

    public long getAttributeCount();

    public nsISchemaAttribute getAttributeByIndex(long var1);

    public nsISchemaAttribute getAttributeByName(String var1);

    public long getElementCount();

    public nsISchemaElement getElementByIndex(long var1);

    public nsISchemaElement getElementByName(String var1);

    public long getAttributeGroupCount();

    public nsISchemaAttributeGroup getAttributeGroupByIndex(long var1);

    public nsISchemaAttributeGroup getAttributeGroupByName(String var1);

    public long getModelGroupCount();

    public nsISchemaModelGroup getModelGroupByIndex(long var1);

    public nsISchemaModelGroup getModelGroupByName(String var1);

    public nsISchemaCollection getCollection();
}

