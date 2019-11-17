package io.github.cottonmc.functionapi.content.template;

import io.github.cottonmc.functionapi.api.content.ItemTemplate;
import io.github.cottonmc.functionapi.api.content.enums.ItemType;

public class ItemTemplateImpl implements ItemTemplate {

    private int maxCount = 64;
    private ItemType type = ItemType.NORMAL;

    @Override
    public int maxCount() {
        return maxCount;
    }

    @Override
    public ItemType getType() {
        return type;
    }

    @Override
    public void setType(ItemType type) {
        this.type = type;
    }

    @Override
    public boolean isColored() {
        return false;
    }

    @Override
    public void setMaxCount(int amount) {
        maxCount = amount;
    }

    @Override
    public int getMaxUseTime() {
        return 0;
    }

    @Override
    public void setMaxUseTime(int maxUseTime) {

    }
}
