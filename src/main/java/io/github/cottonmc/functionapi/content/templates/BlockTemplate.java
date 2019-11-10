package io.github.cottonmc.functionapi.content.templates;

import io.github.cottonmc.functionapi.content.templates.state.StringBlockStateProperty;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.AbstractProperty;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.Tag;

import java.util.*;

public class BlockTemplate {

    public enum Type {
        STAIRS, FENCE, SLAB, NORMAL,PILLAR,DIRECTIONAL
    }
    

    private Material material = Material.STONE;
    private BlockRenderLayer renderLayer = BlockRenderLayer.SOLID;
    private boolean canSpawnMobs = true;
    private boolean isAir = false;
    private int lightLevel = 0;
    private boolean isInvisible = false;
    private float hardness = 1.0f;
    private boolean isCollidable = true;
    private boolean hasItem = true;
    private boolean ticksRandomly = false;



    private boolean ticks = false;
    private boolean isColored = false;
    private Type type = Type.NORMAL;
    private BlockSoundGroup soundGroup = BlockSoundGroup.STONE;
    private Tag<Item> tool = FabricToolTags.PICKAXES;
    private int miningLevel = 0;

    private Map<String,AbstractProperty> stateProperties= new HashMap<>();


    public BlockTemplate setTicksRandomly(boolean ticksRandomly) {
        this.ticksRandomly = ticksRandomly;
        return this;
    }

    
    public List<AbstractProperty> getProperties() {
        return Collections.list(Collections.enumeration(stateProperties.values()));
    }

    
    public void addToStringProperty(String name,String value) {
        if(!stateProperties.containsKey(name)){
            stateProperties.put(name,new StringBlockStateProperty(name, value));
        }else{
            ((StringBlockStateProperty)stateProperties.get(name)).addValue(value);
        }
    }

    public void createIntProperty(String name,int min,int max){
        stateProperties.put(name,IntProperty.of(name,min,max));
    }
    public void createBooleanProperty(String name){
        stateProperties.put(name, BooleanProperty.of(name));
    }

    
    public Material getMaterial() {
        return material;
    }

    
    public BlockRenderLayer getRenderLayer() {
        return renderLayer;
    }

    
    public boolean canSpawnMobs() {
        return canSpawnMobs;
    }

    
    public boolean isAir() {
        return isAir;
    }

    
    public int getLightLevel() {
        return lightLevel;
    }

    
    public Type getType() {
        return type;
    }

    
    public BlockTemplate setType(Type type) {
        this.type = type;
        return this;
    }

    public boolean isColored() {
        return isColored;
    }

    
    public boolean isInvisible() {
        return isInvisible;
    }

    
    public float getHardness() {
        return hardness;
    }

    
    public boolean ticksRandomly() {
        return ticksRandomly;
    }

    
    public boolean isCollidable() {
        return isCollidable;
    }

    
    public boolean hasItem() {
        return hasItem;
    }

    
    public BlockSoundGroup getSoundGroup() {
        return soundGroup;
    }

    
    public BlockTemplate setSoundGroup(BlockSoundGroup soundGroup) {
        this.soundGroup = soundGroup;
        return this;
    }

    public BlockTemplate setHasItem(boolean hasItem) {
        this.hasItem = hasItem;
        return this;
    }

    

    public BlockTemplate setCollidable(boolean collidable) {
        isCollidable = collidable;
        return this;
    }

    

    public BlockTemplate setHardness(float hardness) {
        this.hardness = hardness;
        return this;
    }

    

    public BlockTemplate setInvisible(boolean invisible) {
        isInvisible = invisible;
        return this;
    }

    public BlockTemplate setLightLevel(int lightLevel) {
        this.lightLevel = lightLevel;
        return this;
    }

    

    public BlockTemplate setAir(boolean air) {
        isAir = air;
        return this;
    }

    

    public BlockTemplate setCanSpawnMobs(boolean canSpawnMobs) {
        this.canSpawnMobs = canSpawnMobs;
        return this;
    }

    

    public BlockTemplate setMaterial(Material material) {
        this.material = material;
        return this;
    }

    

    public BlockTemplate setRenderLayer(BlockRenderLayer renderLayer) {
        this.renderLayer = renderLayer;
        return this;
    }

    

    public BlockTemplate setColored(boolean colored) {
        isColored = colored;
        return this;
    }

    
    public BlockTemplate setTool(Tag<Item> tool) {
        this.tool = tool;
        return this;
    }

    
    public Tag<Item> getTool() {
        return tool;
    }

    
    public BlockTemplate setMiningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
        return this;
    }

    public boolean isTicks() {
        return ticks;
    }

    public BlockTemplate setTicks(boolean ticks) {
        this.ticks = ticks;
        return this;
    }
    
    public int getMiningLevel() {
        return miningLevel;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockTemplate that = (BlockTemplate) o;
        return canSpawnMobs == that.canSpawnMobs &&
                isAir == that.isAir &&
                lightLevel == that.lightLevel &&
                isInvisible == that.isInvisible &&
                Float.compare(that.hardness, hardness) == 0 &&
                isCollidable == that.isCollidable &&
                hasItem == that.hasItem &&
                ticksRandomly == that.ticksRandomly &&
                ticks == that.ticks &&
                isColored == that.isColored &&
                miningLevel == that.miningLevel &&
                Objects.equals(material, that.material) &&
                renderLayer == that.renderLayer &&
                type == that.type &&
                Objects.equals(soundGroup, that.soundGroup) &&
                Objects.equals(tool, that.tool) &&
                Objects.equals(stateProperties, that.stateProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(material, renderLayer, canSpawnMobs, isAir, lightLevel, isInvisible, hardness, isCollidable, hasItem, ticksRandomly, ticks, isColored, type, soundGroup, tool, miningLevel, stateProperties);
    }

    @Override
    public String toString() {
        return "BlockTemplate{" +
                "material=" + material +
                ", renderLayer=" + renderLayer +
                ", canSpawnMobs=" + canSpawnMobs +
                ", isAir=" + isAir +
                ", lightLevel=" + lightLevel +
                ", isInvisible=" + isInvisible +
                ", hardness=" + hardness +
                ", isCollidable=" + isCollidable +
                ", hasItem=" + hasItem +
                ", ticksRandomly=" + ticksRandomly +
                ", ticks=" + ticks +
                ", isColored=" + isColored +
                ", type=" + type +
                ", soundGroup=" + soundGroup +
                ", tool=" + tool +
                ", miningLevel=" + miningLevel +
                ", stateProperties=" + stateProperties +
                '}';
    }
}

