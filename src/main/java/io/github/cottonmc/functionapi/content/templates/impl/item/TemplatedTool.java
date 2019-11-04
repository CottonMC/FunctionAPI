package io.github.cottonmc.functionapi.content.templates.impl.item;

import com.google.common.collect.Multimap;
import io.github.cottonmc.functionapi.content.templates.ItemTemplate;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;

import java.util.Collections;

public class TemplatedTool extends ToolItem {
    private final ItemTemplate template;

    public TemplatedTool(ToolMaterial material, ItemTemplate template) {
        super(material,new Settings().maxCount(template.maxCount()));
        this.template = template;
    }

    @Override
    public Multimap<String, EntityAttributeModifier> getModifiers(EquipmentSlot equipmentSlot_1) {
        Multimap<String, EntityAttributeModifier> multimap_1 = super.getModifiers(equipmentSlot_1);
        if (equipmentSlot_1 == EquipmentSlot.MAINHAND) {
            multimap_1.put(EntityAttributes.ATTACK_DAMAGE.getId(), new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_UUID, "Tool modifier", (double)this.template.getAttackDamageModifier(equipmentSlot_1), EntityAttributeModifier.Operation.ADDITION));
            multimap_1.put(EntityAttributes.ATTACK_SPEED.getId(), new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_UUID, "Tool modifier", (double)this.template.getAttackSpeedModifier(equipmentSlot_1), EntityAttributeModifier.Operation.ADDITION));
        }

        return multimap_1;
    }
}
