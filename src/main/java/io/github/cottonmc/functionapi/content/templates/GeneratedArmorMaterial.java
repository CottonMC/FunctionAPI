package io.github.cottonmc.functionapi.content.templates;

import io.github.cottonmc.functionapi.Util;
import io.github.cottonmc.functionapi.api.content.ItemMaterial;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class GeneratedArmorMaterial implements ArmorMaterial {

    private final ItemMaterial itemMaterial;
    private Ingredient realRepairIngredient = null;


    public GeneratedArmorMaterial(ItemMaterial itemMaterial){
        this.itemMaterial = itemMaterial;
    }


    @Override
    public int getDurability(EquipmentSlot equipmentSlot) {
        return itemMaterial.getDurability(Util.getSlot(equipmentSlot));
    }

    @Override
    public int getProtectionAmount(EquipmentSlot equipmentSlot) {
        return itemMaterial.getProtectionAmount(Util.getSlot(equipmentSlot));
    }

    @Override
    public int getEnchantability() {
        return itemMaterial.getEnchantability();
    }

    @Override
    public SoundEvent getEquipSound() {
        return Util.getSoundEvent(itemMaterial.getEquipSound());
    }

    @Override
    public Ingredient getRepairIngredient() {
        if (realRepairIngredient == null) {
            FunctionAPIIdentifier functionAPIIdentifier = itemMaterial.getRepairIngredient();
            Tag<Item> tag = ItemTags.getContainer().getOrCreate(new Identifier(functionAPIIdentifier.getNamespace(), functionAPIIdentifier.getPath()));
            realRepairIngredient = Ingredient.fromTag(tag);
        }
        return realRepairIngredient;
    }

    @Override
    public String getName() {
        return itemMaterial.getName();
    }

    @Override
    public float getToughness() {
        return itemMaterial.getToughness();
    }
}
