package io.github.cottonmc.functionapi.content.templates.impl;

import com.google.gson.JsonObject;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.Objects;

public class DefaultArmormaterialTemplate implements ArmorMaterial {

    private int durability = 100;
    private int protection =0;
    private int enchantability =1;
    private JsonObject repairIngredient;
    private String name;
    private float toughness =0.0f;

    public DefaultArmormaterialTemplate setDurability(int durability) {
        this.durability = durability;
        return this;
    }

    public DefaultArmormaterialTemplate setProtection(int protection) {
        this.protection = protection;
        return this;
    }

    public DefaultArmormaterialTemplate setEnchantability(int enchantability) {
        this.enchantability = enchantability;
        return this;
    }

    public DefaultArmormaterialTemplate setRepairIngredient(JsonObject repairIngredient) {
        this.repairIngredient = repairIngredient;
        return this;
    }

    public DefaultArmormaterialTemplate setName(String name) {
        this.name = name;
        return this;
    }

    public DefaultArmormaterialTemplate setToughness(float toughness) {
        this.toughness = toughness;
        return this;
    }

    @Override
    public int getDurability(EquipmentSlot equipmentSlot) {
        return durability;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot equipmentSlot) {
        return protection;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.fromJson(repairIngredient);
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
    public String toString() {
        return "DefaultArmormaterialTemplate{" +
                "durability=" + durability +
                ", protection=" + protection +
                ", enchantability=" + enchantability +
                ", repairIngredient=" + repairIngredient +
                ", name='" + name + '\'' +
                ", toughness=" + toughness +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultArmormaterialTemplate that = (DefaultArmormaterialTemplate) o;
        return durability == that.durability &&
                protection == that.protection &&
                enchantability == that.enchantability &&
                Float.compare(that.toughness, toughness) == 0 &&
                Objects.equals(repairIngredient, that.repairIngredient) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(durability, protection, enchantability, repairIngredient, name, toughness);
    }
}
