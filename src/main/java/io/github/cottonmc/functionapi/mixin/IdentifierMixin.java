package io.github.cottonmc.functionapi.mixin;


import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;

@Mixin(Identifier.class)
@Implements(@Interface(iface = FunctionAPIIdentifier.class,prefix = "id$"))
public abstract class IdentifierMixin //implements FunctionAPIIdentifier
{
    @Shadow @Final protected String namespace;

    @Shadow @Final protected String path;

    public String id$getNamespace() {
        return namespace;
    }

    public String id$getPath() {
        return path;
    }
}
