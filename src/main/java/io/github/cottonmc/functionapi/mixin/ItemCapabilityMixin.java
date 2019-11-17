package io.github.cottonmc.functionapi.mixin;

import com.google.common.collect.Multimap;
import io.github.cottonmc.functionapi.api.script.ScriptedObject;
import io.github.cottonmc.functionapi.content.templates.item.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {TemplatedArmorItem.class, TemplatedBow.class, TemplatedCrossBow.class, TemplatedShield.class,TemplatedTool.class})
public abstract class ItemCapabilityMixin extends Item {
    public ItemCapabilityMixin(Settings item$Settings_1) {
        super(item$Settings_1);
    }


    @Override
    public Multimap<String, EntityAttributeModifier> getModifiers(EquipmentSlot equipmentSlot_1) {

        return super.getModifiers(equipmentSlot_1);
    }

}
