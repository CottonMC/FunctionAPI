package io.github.cottonmc.functionapi.content.template;

import io.github.cottonmc.functionapi.api.content.ItemMaterial;
import io.github.cottonmc.functionapi.api.content.enums.EquipSoundEvent;
import io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.script.FunctionAPIIdentifierImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ItemMaterialImpl implements ItemMaterial {

    private Map<EquipmentSlot, Integer> durability = new HashMap<>();
    private Map<EquipmentSlot, Integer> protection = new HashMap<>();
    private int enchantability = 1;
    private float miningSpeed = 1.0f;
    private float attackDamage = 1.0f;
    private int miningLevel = 1;
    private float toughness =0;
    private String name = String.valueOf(new Random().nextGaussian());
    private FunctionAPIIdentifier repairIngredient = new FunctionAPIIdentifierImpl("minecraft","iron_ingot");
    private EquipSoundEvent equipsound = EquipSoundEvent.GENERIC;

    @Override
    public int getDurability(EquipmentSlot var1) {
        return durability.getOrDefault(var1, 100);
    }

    @Override
    public int getProtectionAmount(EquipmentSlot var1) {
        return protection.getOrDefault(var1, 1);
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public EquipSoundEvent getEquipSound() {
        return equipsound;
    }

    @Override
    public FunctionAPIIdentifier getRepairIngredient() {
        return repairIngredient;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getMiningSpeed() {
        return miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return miningLevel;
    }

    @Override
    public void setDurability(EquipmentSlot var1, int durability) {
        this.durability.put(var1, durability);
    }

    @Override
    public void setProtectionAmount(EquipmentSlot var1, int protectionAmount) {
        this.protection.put(var1, protectionAmount);
    }

    @Override
    public void setEnchantability(int enchantability) {
        this.enchantability = enchantability;
    }

    @Override
    public void setEquipSound(EquipSoundEvent equipsound) {

        this.equipsound = equipsound;
    }

    @Override
    public void setRepairIngredient(FunctionAPIIdentifier repairIngredient) {

        this.repairIngredient = repairIngredient;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setToughness(float toughness) {
        this.toughness = toughness;
    }


    @Override
    public void setMiningSpeed(float miningSpeed) {
        this.miningSpeed = miningLevel;
    }

    @Override
    public void setAttackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
    }

    @Override
    public void setMiningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
    }
}
