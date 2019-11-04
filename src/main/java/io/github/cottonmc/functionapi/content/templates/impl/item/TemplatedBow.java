package io.github.cottonmc.functionapi.content.templates.impl.item;

import io.github.cottonmc.functionapi.content.templates.ItemTemplate;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class TemplatedBow extends BowItem {
    private final ToolMaterial material;

    public TemplatedBow(ToolMaterial material, ItemTemplate template, Settings item$Settings_1) {
        super(item$Settings_1.maxDamage(material.getDurability()));

        this.material = material;
    }

    @Override
    public int getEnchantability() {
        return material.getEnchantability();
    }

    //we CAN repair it with the given material.
    @Override
    public boolean canRepair(ItemStack itemStack_1, ItemStack itemStack_2) {
        return material.getRepairIngredient().method_8093(itemStack_2)|| super.canRepair(itemStack_1, itemStack_2);
    }
}
