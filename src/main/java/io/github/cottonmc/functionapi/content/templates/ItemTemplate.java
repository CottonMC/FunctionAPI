package io.github.cottonmc.functionapi.content.templates;

import net.minecraft.entity.EquipmentSlot;

/**
 * Stores enough data to create a new item.
* */
public interface ItemTemplate {

    enum Type{
        BOW,CROSSBOW,TOOL,NORMAL,SHIELD,ARMOR
    }

    int maxCount();
    Type getType();
    void setType(Type type);
    boolean isColored();
    int getAttackDamageModifier(EquipmentSlot slot);
    int getAttackSpeedModifier(EquipmentSlot slot);

    void setAttackDamageModifier(EquipmentSlot slot,int modifier);
    void setAttackSpeedModifier(EquipmentSlot slot,int modifier);

    void setMaxCount(int amount);

    int getMaxUseTime();
    void setMaxUseTime(int maxUseTime);
}
