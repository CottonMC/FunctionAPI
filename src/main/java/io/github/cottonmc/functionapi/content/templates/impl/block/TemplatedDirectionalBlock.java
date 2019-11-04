package io.github.cottonmc.functionapi.content.templates.impl.block;

import io.github.cottonmc.functionapi.content.templates.BlockTemplate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.AbstractProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;

import java.util.List;

import static net.minecraft.state.property.Properties.FACING;

/**
 * Block that takes the block template and builds up itself from it.
 */
public class TemplatedDirectionalBlock extends TemplatedBlock {


    public TemplatedDirectionalBlock(BlockTemplate template, Settings blockSettings) {
        super(template, blockSettings);
        this.setDefaultState(this.stateFactory.getDefaultState().with(FACING, Direction.SOUTH));
    }

    public BlockState rotate(BlockState blockState_1, BlockRotation blockRotation_1) {
        return blockState_1.with(FACING, blockRotation_1.rotate(blockState_1.get(FACING)));
    }

    public BlockState mirror(BlockState blockState_1, BlockMirror blockMirror_1) {
        return blockState_1.rotate(blockMirror_1.getRotation(blockState_1.get(FACING)));
    }

    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
        return this.getDefaultState().with(FACING, itemPlacementContext_1.getPlayerLookDirection().getOpposite().getOpposite());
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
        super.appendProperties(stateFactory$Builder_1.add(FACING));

    }

    @Override
    public BlockSoundGroup getSoundGroup(BlockState blockState_1) {
        return super.getSoundGroup(blockState_1);
    }
}
