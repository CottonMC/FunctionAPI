package io.github.cottonmc.functionapi.content.templates.impl.item;

import com.google.common.collect.Multimap;
import io.github.cottonmc.functionapi.content.templates.ItemTemplate;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TemplatedItem extends Item {
    private final ItemTemplate template;

    public TemplatedItem(ItemTemplate template) {
        super(new Settings()
                .maxCount(template.maxCount())
                );
        this.template = template;
    }

    @Override
    public int getMaxUseTime(ItemStack itemStack_1) {
        return template.getMaxUseTime();
    }

    @Override
    public Multimap<String, EntityAttributeModifier> getModifiers(EquipmentSlot equipmentSlot_1) {
        Multimap<String, EntityAttributeModifier> multimap_1 = super.getModifiers(equipmentSlot_1);

        if (this.template.getAttackDamageModifier(equipmentSlot_1) > 0)
            multimap_1.put(EntityAttributes.ATTACK_DAMAGE.getId(), new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_UUID, "Tool modifier", (double) this.template.getAttackDamageModifier(equipmentSlot_1), EntityAttributeModifier.Operation.ADDITION));
        if (this.template.getAttackSpeedModifier(equipmentSlot_1) > 0)
            multimap_1.put(EntityAttributes.ATTACK_SPEED.getId(), new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_UUID, "Tool modifier", (double) this.template.getAttackSpeedModifier(equipmentSlot_1), EntityAttributeModifier.Operation.ADDITION));
        return multimap_1;
    }

}
