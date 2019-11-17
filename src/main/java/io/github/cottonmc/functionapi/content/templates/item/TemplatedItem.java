package io.github.cottonmc.functionapi.content.templates.item;

import io.github.cottonmc.functionapi.api.content.ItemTemplate;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TemplatedItem extends Item {
    private final ItemTemplate template;

    public TemplatedItem(ItemTemplate template,Settings settings) {
        super(settings);
        this.template = template;
    }

    @Override
    public int getMaxUseTime(ItemStack itemStack_1) {
        return template.getMaxUseTime();
    }

}
