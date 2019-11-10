package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.api.ScriptedObject;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = Item.class,priority = 0)
@Implements(@Interface(iface = ScriptedObject.class, prefix = "api_scripted$"))
public abstract class ItemMixin {

    private Identifier thisId = null;

    /**
     * Dynamically gets the id of this item instance.
     */
    public Identifier api_scripted$getID() {
        if (thisId == null) {
            thisId = Registry.ITEM.getId((Item) (Object) this);
        }
        return thisId;
    }

    public String api_scripted$getType() {
        return "item";
    }
}
