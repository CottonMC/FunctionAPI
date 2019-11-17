package io.github.cottonmc.functionapi.content.templates;

import io.github.cottonmc.functionapi.api.content.ItemTemplate;
import io.github.cottonmc.functionapi.api.content.enums.ItemType;

import java.util.Objects;

public class DefaultItemTemplate implements ItemTemplate {


    private int count = 64;
    private ItemType type = ItemType.NORMAL;
    private String materialID = "";
    private boolean isColored = false;
    private int attackDamageModifier = 0;
    private int attackSpeedModifier = 0;

    @Override
    public int maxCount() {
        return count;
    }

    @Override
    public ItemType getType() {
        return type;
    }


    @Override
    public boolean isColored() {
        return isColored;
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

    public void setType(ItemType type) {
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
