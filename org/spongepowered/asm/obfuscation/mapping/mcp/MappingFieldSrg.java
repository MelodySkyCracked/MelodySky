/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.obfuscation.mapping.mcp;

import org.spongepowered.asm.obfuscation.mapping.common.MappingField;

public class MappingFieldSrg
extends MappingField {
    private final String srg;

    public MappingFieldSrg(String string) {
        super(MappingFieldSrg.getOwnerFromSrg(string), MappingFieldSrg.getNameFromSrg(string), null);
        this.srg = string;
    }

    public MappingFieldSrg(MappingField mappingField) {
        super(mappingField.getOwner(), mappingField.getName(), null);
        this.srg = mappingField.getOwner() + "/" + mappingField.getName();
    }

    @Override
    public String serialise() {
        return this.srg;
    }

    private static String getNameFromSrg(String string) {
        if (string == null) {
            return null;
        }
        int n = string.lastIndexOf(47);
        return n > -1 ? string.substring(n + 1) : string;
    }

    private static String getOwnerFromSrg(String string) {
        if (string == null) {
            return null;
        }
        int n = string.lastIndexOf(47);
        return n > -1 ? string.substring(0, n) : null;
    }
}

