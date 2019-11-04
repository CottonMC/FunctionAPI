package io.github.cottonmc.functionapi.content.templates.impl.item;

import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class TemplatedShield extends Item {
    private final ToolMaterial toolMaterial;

    public TemplatedShield(ToolMaterial toolMaterial, Settings item$Settings_1) {
        super(item$Settings_1);
        this.toolMaterial = toolMaterial;
        this.addPropertyGetter(new Identifier("blocking"), (itemStack_1, world_1, livingEntity_1) -> livingEntity_1 != null && livingEntity_1.isUsingItem() && livingEntity_1.getActiveItem() == itemStack_1 ? 1.0F : 0.0F);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    }

    public UseAction getUseAction(ItemStack itemStack_1) {
        return UseAction.BLOCK;
    }

    public int getMaxUseTime(ItemStack itemStack_1) {
        return 72000;
    }

    public TypedActionResult<ItemStack> use(World world_1, PlayerEntity playerEntity_1, Hand hand_1) {
        ItemStack itemStack_1 = playerEntity_1.getStackInHand(hand_1);
        playerEntity_1.setCurrentHand(hand_1);
        return new TypedActionResult(ActionResult.SUCCESS, itemStack_1);
    }

    public boolean canRepair(ItemStack itemStack_1, ItemStack itemStack_2) {
        return toolMaterial.getRepairIngredient().method_8093(itemStack_2) || super.canRepair(itemStack_1, itemStack_2);
    }
}
