/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 */
package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.base.Strings;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mirror.MemberHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MethodHandle
extends MemberHandle {
    private final ExecutableElement element;

    public MethodHandle(TypeElement typeElement, ExecutableElement executableElement) {
        this(TypeUtils.getInternalName(typeElement), executableElement);
    }

    public MethodHandle(String string, ExecutableElement executableElement) {
        this(string, TypeUtils.getName(executableElement), TypeUtils.getDescriptor(executableElement));
    }

    protected MethodHandle(String string, String string2, String string3) {
        this(string, null, string2, string3);
    }

    private MethodHandle(String string, ExecutableElement executableElement, String string2, String string3) {
        super(string, string2, string3);
        this.element = executableElement;
    }

    public boolean isImaginary() {
        return this.element == null;
    }

    public ExecutableElement getElement() {
        return this.element;
    }

    @Override
    public MappingMethod asMapping(boolean bl) {
        return new MappingMethod(bl ? this.getOwner() : null, this.getName(), this.getDesc());
    }

    public String toString() {
        String string = this.getOwner() != null ? "L" + this.getOwner() + ";" : "";
        String string2 = Strings.nullToEmpty((String)this.getName());
        String string3 = Strings.nullToEmpty((String)this.getDesc());
        return String.format("%s%s%s", string, string2, string3);
    }

    @Override
    public IMapping asMapping(boolean bl) {
        return this.asMapping(bl);
    }
}

