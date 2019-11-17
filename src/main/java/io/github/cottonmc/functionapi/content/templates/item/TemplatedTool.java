package io.github.cottonmc.functionapi.content.templates.item;

import com.google.common.collect.Multimap;
import io.github.cottonmc.functionapi.api.content.ItemTemplate;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;

public class TemplatedTool extends ToolItem {
    private final ItemTemplate template;

    public TemplatedTool(ToolMaterial material, ItemTemplate template, Settings settings) {
        super(material,settings);
        this.template = template;
    }

}
