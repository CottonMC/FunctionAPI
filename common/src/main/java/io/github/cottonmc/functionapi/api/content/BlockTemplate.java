package io.github.cottonmc.functionapi.api.content;

import io.github.cottonmc.functionapi.api.content.enums.*;
import io.github.cottonmc.functionapi.api.content.enums.Material;
import io.github.cottonmc.functionapi.content.PlacementMapping;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

public interface BlockTemplate {

    void setHardness(int level);

    void setMiningLevel(int level);

    void setHasItem(boolean b);

    void setTicksRandomly(boolean b);

    void createIntProperty(String name, int i1);

    void setInvisible(boolean b);

    void setCollidable(boolean b);

    void setAir(boolean b);

    void setCanSpawnMobs(boolean b);

    void createBooleanProperty(String name);

    void addToStringProperty(String name, String value);

    void setRenderLayer(BlockRenderLayer translucent);

    void setTool(Tools tool);

    void makeStairs();

    void setSoundGroup(BlockSoundGroup soundGroup);

    void setMaterial(Material material);

    Material getMaterial();

    int getLightLevel();

    boolean isCollidable();

    float getHardness();

    BlockSoundGroup getSoundGroup();

    Tools getTool();

    int getMiningLevel();

    boolean ticksRandomly();

    boolean hasItem();

    boolean isStairs();

    boolean isAir();

    boolean canSpawnMobs();

    boolean isInvisible();

    void addPlacementState(PlacementMapping placementMapping);
    List<PlacementMapping> getPlacementStates();

    Map<String, Pair<BlockStatePropertyTypes,Object>> getProperties();

    BlockRenderLayer getRenderLayer();
}
