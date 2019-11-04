package io.github.cottonmc.functionapi.content.templates.impl.block;

import io.github.cottonmc.functionapi.content.templates.BlockTemplate;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.AbstractProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.List;
import java.util.function.Supplier;

import static io.github.cottonmc.functionapi.content.templates.impl.block.TemplatedBlock.currentTemplate;

/**
 * Block that takes the block template and builds up itself from it.
 * */
public class TemplatedSlab extends SlabBlock {
    private final BlockTemplate template;

    public TemplatedSlab(BlockTemplate template, Settings settings) {
        super(settings);

        this.template = template;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return template.getRenderLayer();
    }

    @Override
    public boolean hasRandomTicks(BlockState blockState_1) {
        return template.ticksRandomly();
    }

    @Override
    public boolean allowsSpawning(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityType<?> entityType_1) {
        return template.canSpawnMobs();
    }


    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
        List<AbstractProperty> properties = currentTemplate.getProperties();
        if(properties.isEmpty()) {
            super.appendProperties(stateFactory$Builder_1);
        }else{
            for (AbstractProperty property : properties) {
                stateFactory$Builder_1 = stateFactory$Builder_1.add(property);
            }
            super.appendProperties(stateFactory$Builder_1);
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState_1) {
        if(template.isInvisible()){
            return BlockRenderType.INVISIBLE;
        }
        return super.getRenderType(blockState_1);
    }
}
