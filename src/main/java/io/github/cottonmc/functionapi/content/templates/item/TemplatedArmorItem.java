package io.github.cottonmc.functionapi.content.templates.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TemplatedArmorItem extends ArmorItem {

    public TemplatedArmorItem(ArmorMaterial armorMaterial_1, EquipmentSlot equipmentSlot_1, Settings item$Settings_1) {
        super(armorMaterial_1, equipmentSlot_1, item$Settings_1);
    }

}
