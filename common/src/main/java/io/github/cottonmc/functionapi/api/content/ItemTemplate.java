package io.github.cottonmc.functionapi.api.content;


import io.github.cottonmc.functionapi.api.content.enums.ItemType;

/**
 * Stores enough data to create a new item.
* */
public interface ItemTemplate {


    int maxCount();
    ItemType getType();
    void setType(ItemType type);
    boolean isColored();


    void setMaxCount(int amount);

    int getMaxUseTime();
    void setMaxUseTime(int maxUseTime);
}
