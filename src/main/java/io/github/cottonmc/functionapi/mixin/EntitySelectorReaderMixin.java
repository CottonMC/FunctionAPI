package io.github.cottonmc.functionapi.mixin;

import net.minecraft.command.*;
import org.spongepowered.asm.mixin.*;

@Mixin(EntitySelector.class)
public class EntitySelectorReaderMixin{

    @Shadow private boolean usesAt;

    private void readAt(){

    }
}
