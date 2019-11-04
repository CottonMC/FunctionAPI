package io.github.cottonmc.functionapi.content.templates.impl;

import com.google.gson.JsonObject;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.Objects;

public class DefaultToolMaterialTemplate implements ToolMaterial {

    private int durability = 100;
    private float miningSpeed =1.0f;
    private float attackDamage =1.0f;
    private int miningLevel =0;
    private int enchantability =1;
    private JsonObject repairIngredient;

    public DefaultToolMaterialTemplate setDurability(int durability) {
        this.durability = durability;
        return this;
    }

    public DefaultToolMaterialTemplate setMiningSpeed(float miningSpeed) {
        this.miningSpeed = miningSpeed;
        return this;
    }

    public DefaultToolMaterialTemplate setAttackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
        return this;
    }

    public DefaultToolMaterialTemplate setMiningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
        return this;
    }

    public DefaultToolMaterialTemplate setEnchantability(int enchantability) {
        this.enchantability = enchantability;
        return this;
    }

    public DefaultToolMaterialTemplate setRepairIngredient(JsonObject repairIngredient) {
        this.repairIngredient = repairIngredient;
        return this;
    }

    @Override
    public int getDurability() {
        return durability;
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
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.fromJson(repairIngredient);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultToolMaterialTemplate that = (DefaultToolMaterialTemplate) o;
        return durability == that.durability &&
                Float.compare(that.miningSpeed, miningSpeed) == 0 &&
                Float.compare(that.attackDamage, attackDamage) == 0 &&
                miningLevel == that.miningLevel &&
                enchantability == that.enchantability &&
                Objects.equals(repairIngredient, that.repairIngredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(durability, miningSpeed, attackDamage, miningLevel, enchantability, repairIngredient);
    }

    @Override
    public String toString() {
        return "DefaultToolMaterialTemplate{" +
                "durability=" + durability +
                ", miningSpeed=" + miningSpeed +
                ", attackDamage=" + attackDamage +
                ", miningLevel=" + miningLevel +
                ", enchantability=" + enchantability +
                ", repairIngredient=" + repairIngredient +
                '}';
    }
}
