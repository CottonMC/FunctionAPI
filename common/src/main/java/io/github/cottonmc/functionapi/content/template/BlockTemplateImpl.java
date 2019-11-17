package io.github.cottonmc.functionapi.content.template;

import io.github.cottonmc.functionapi.api.content.BlockTemplate;
import io.github.cottonmc.functionapi.api.content.enums.*;
import io.github.cottonmc.functionapi.content.PlacementMapping;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class BlockTemplateImpl implements BlockTemplate {

    private int hardness = 0;
    private int miningLevel = 0;
    private boolean hasItem = true;
    private boolean ticksRandomly = false;
    private boolean invisible = false;
    private boolean collideable = true;
    private boolean isAir = false;
    private boolean canSpawnMobs = true;
    private BlockRenderLayer renderLayer = BlockRenderLayer.SOLID;
    private Tools tool = Tools.PICKAXES;
    private boolean stairs = false;
    private BlockSoundGroup soundGroup = BlockSoundGroup.STONE;
    private Material material = Material.STONE;
    private List<PlacementMapping> placementMappings = new LinkedList<>();
    private Map<String, Pair<BlockStatePropertyTypes, Object>> properties = new HashMap<>();
    @Override
    public void setHardness(int level) {
        this.hardness = level;
    }

    @Override
    public void setMiningLevel(int level) {
        miningLevel = level;
    }

    @Override
    public void setHasItem(boolean b) {
        hasItem = b;
    }

    @Override
    public void setTicksRandomly(boolean b) {
        ticksRandomly = b;
    }

    @Override
    public void createIntProperty(String name ,int i1) {
        properties.put(name,new ImmutablePair<>(BlockStatePropertyTypes.RANGE,i1));
    }

    @Override
    public void setInvisible(boolean b) {
        this.invisible = true;
    }

    @Override
    public void setCollidable(boolean b) {
        collideable = b;
    }

    @Override
    public void setAir(boolean b) {
        isAir = b;
    }

    @Override
    public void setCanSpawnMobs(boolean b) {
        canSpawnMobs = b;
    }

    @Override
    public void createBooleanProperty(String name) {
        properties.put(name,new ImmutablePair<>(BlockStatePropertyTypes.BOOLEAN,null));
    }

    @Override
    public void addToStringProperty(String name, String value) {
        Pair<BlockStatePropertyTypes, Object> property = properties.getOrDefault(name, new ImmutablePair<>(BlockStatePropertyTypes.STRING, new LinkedList<String>()));
        ((List<String>)property.getRight()).add(value);
        properties.put(name,property);
    }

    @Override
    public void setRenderLayer(BlockRenderLayer renderLayer) {
        this.renderLayer = renderLayer;
    }

    @Override
    public void setTool(Tools tool) {
        this.tool = tool;
    }

    @Override
    public void makeStairs() {
        stairs = true;
    }

    @Override
    public void setSoundGroup(BlockSoundGroup soundGroup) {
        this.soundGroup = soundGroup;

    }

    @Override
    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public int getLightLevel() {
        return 0;
    }

    @Override
    public boolean isCollidable() {
        return collideable;
    }

    @Override
    public float getHardness() {
        return hardness;
    }

    @Override
    public BlockSoundGroup getSoundGroup() {
        return soundGroup;
    }

    @Override
    public Tools getTool() {
        return tool;
    }

    @Override
    public int getMiningLevel() {
        return miningLevel;
    }

    @Override
    public boolean ticksRandomly() {
        return ticksRandomly;
    }

    @Override
    public boolean hasItem() {
        return hasItem;
    }

    @Override
    public boolean isStairs() {
        return stairs;
    }

    @Override
    public boolean isAir() {
        return isAir;
    }

    @Override
    public boolean canSpawnMobs() {
        return canSpawnMobs;
    }

    @Override
    public boolean isInvisible() {
        return invisible;
    }

    @Override
    public void addPlacementState(PlacementMapping placementMapping) {
        placementMappings.add(placementMapping);
    }

    @Override
    public List<PlacementMapping> getPlacementStates() {
        return placementMappings;
    }

    @Override
    public Map<String, Pair<BlockStatePropertyTypes, Object>> getProperties() {
        return properties;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return renderLayer;
    }

}
