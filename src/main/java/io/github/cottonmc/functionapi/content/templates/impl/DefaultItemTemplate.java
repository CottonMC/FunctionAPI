package io.github.cottonmc.functionapi.content.templates.impl;


import io.github.cottonmc.functionapi.content.templates.ItemTemplate;
import net.minecraft.entity.EquipmentSlot;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DefaultItemTemplate implements ItemTemplate {


    private int count = 64;
    private Type type = Type.NORMAL;
    private String materialID = "";
    private boolean isColored = false;
    private int attackDamageModifier = 0;
    private int attackSpeedModifier = 0;

    @Override
    public int maxCount() {
        return count;
    }

    @Override
    public Type getType() {
        return type;
    }


    @Override
    public boolean isColored() {
        return isColored;
    }

    @Override
    public int getAttackDamageModifier(EquipmentSlot slot) {
        return attackSpeedModifier;
    }

    @Override
    public int getAttackSpeedModifier(EquipmentSlot slot) {
        return attackSpeedModifier;
    }

    @Override
    public void setAttackDamageModifier(EquipmentSlot slot, int modifier) {
        attackDamageModifier = modifier;
    }

    @Override
    public void setAttackSpeedModifier(EquipmentSlot slot, int modifier) {
        attackSpeedModifier = modifier;
    }

    @Override
    public void setMaxCount(int amount) {
        count = amount;
    }

    @Override
    public int getMaxUseTime() {
        return 0;
    }

    @Override
    public void setMaxUseTime(int maxUseTime) {

    }

    public DefaultItemTemplate setCount(int count) {
        this.count = count;
        return this;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public DefaultItemTemplate setMaterialID(String materialID) {
        this.materialID = materialID;
        return this;
    }

    public DefaultItemTemplate setColored(boolean colored) {
        isColored = colored;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultItemTemplate that = (DefaultItemTemplate) o;
        return count == that.count &&
                isColored == that.isColored &&
                Objects.equals(type, that.type) &&
                Objects.equals(materialID, that.materialID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, type, materialID, isColored);
    }

    @Override
    public String toString() {
        return "DefaultItemTemplate{" +
                "count=" + count +
                ", type=" + type +
                ", materialID='" + materialID + '\'' +
                ", isColored=" + isColored +
                '}';
    }
}
