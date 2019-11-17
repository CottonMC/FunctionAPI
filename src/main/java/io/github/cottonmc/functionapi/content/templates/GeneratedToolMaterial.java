package io.github.cottonmc.functionapi.content.templates;

import io.github.cottonmc.functionapi.api.content.ItemMaterial;
import io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class GeneratedToolMaterial implements ToolMaterial {

    private final ItemMaterial itemMaterial;
    private Ingredient realRepairIngredient = null;


    public GeneratedToolMaterial(ItemMaterial itemMaterial){

        this.itemMaterial = itemMaterial;
    }

    @Override
    public int getDurability() {
        return itemMaterial.getDurability(EquipmentSlot.MAIN_HAND);
    }

    @Override
    public float getMiningSpeed() {
        return itemMaterial.getMiningSpeed();
    }

    @Override
    public float getAttackDamage() {
        return itemMaterial.getAttackDamage();
    }

    @Override
    public int getMiningLevel() {
        return itemMaterial.getMiningLevel();
    }

    @Override
    public int getEnchantability() {
        return itemMaterial.getEnchantability();
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
}
