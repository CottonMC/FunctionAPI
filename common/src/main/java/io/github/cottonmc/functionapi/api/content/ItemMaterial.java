package io.github.cottonmc.functionapi.api.content;

import io.github.cottonmc.functionapi.api.content.enums.EquipSoundEvent;
import io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;

public interface ItemMaterial {
    int getDurability(EquipmentSlot var1);

    int getProtectionAmount(EquipmentSlot var1);

    int getEnchantability();

    EquipSoundEvent getEquipSound();

    FunctionAPIIdentifier getRepairIngredient();

    String getName();

    float getToughness();

    float getMiningSpeed();

    float getAttackDamage();

    int getMiningLevel();


    void setDurability(EquipmentSlot var1,int durability);

    void setProtectionAmount(EquipmentSlot var1,int protectionAmount);

    void setEnchantability(int enchantability);

    void setEquipSound(EquipSoundEvent equipsound);

    void setRepairIngredient(FunctionAPIIdentifier repairIngredient);

    void setName(String name);

    void setToughness(float toughness);

    void setMiningSpeed(float miningSpeed);

    void setAttackDamage(float attackDamage);

    void setMiningLevel(int miningLevel);
}