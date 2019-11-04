package io.github.cottonmc.functionapi.content.templates.impl.block;

import io.github.cottonmc.functionapi.content.templates.BlockTemplate;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.*;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static io.github.cottonmc.functionapi.content.templates.impl.block.TemplatedBlock.currentTemplate;

/**
 * Block that takes the block template and builds up itself from it.
 */
public class TemplatedStairs extends TemplatedBlock {


    public static final DirectionProperty FACING;
    public static final EnumProperty<BlockHalf> HALF;
    public static final EnumProperty<StairShape> SHAPE;
    public static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape TOP_SHAPE;
    protected static final VoxelShape BOTTOM_SHAPE;
    protected static final VoxelShape BOTTOM_NORTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_SOUTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape TOP_NORTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape TOP_SOUTH_WEST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_NORTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape BOTTOM_SOUTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape TOP_NORTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape TOP_SOUTH_EAST_CORNER_SHAPE;
    protected static final VoxelShape[] TOP_SHAPES;
    protected static final VoxelShape[] BOTTOM_SHAPES;
    private static final int[] SHAPE_INDICES;


    public TemplatedStairs(BlockTemplate template, Settings settings) {
        super(template, settings);
    }


    public boolean hasSidedTransparency(BlockState blockState_1) {
        return true;
    }

    public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext entityContext_1) {
        return (blockState_1.get(HALF) == BlockHalf.TOP ? TOP_SHAPES : BOTTOM_SHAPES)[SHAPE_INDICES[this.getShapeIndexIndex(blockState_1)]];
    }

    private int getShapeIndexIndex(BlockState blockState_1) {
        return blockState_1.get(SHAPE).ordinal() * 4 + blockState_1.get(FACING).getHorizontal();
    }

    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
        Direction direction_1 = itemPlacementContext_1.getSide();
        BlockPos blockPos_1 = itemPlacementContext_1.getBlockPos();
        FluidState fluidState_1 = itemPlacementContext_1.getWorld().getFluidState(blockPos_1);
        BlockState blockState_1 = this.getDefaultState().with(FACING, itemPlacementContext_1.getPlayerFacing()).with(HALF, direction_1 != Direction.DOWN && (direction_1 == Direction.UP || itemPlacementContext_1.getHitPos().y - (double) blockPos_1.getY() <= 0.5D) ? BlockHalf.BOTTOM : BlockHalf.TOP).with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER);
        return blockState_1.with(SHAPE, method_10675(blockState_1, itemPlacementContext_1.getWorld(), blockPos_1));
    }

    public BlockState getStateForNeighborUpdate(BlockState blockState_1, Direction direction_1, BlockState blockState_2, IWorld iWorld_1, BlockPos blockPos_1, BlockPos blockPos_2) {
        if (blockState_1.get(WATERLOGGED)) {
            iWorld_1.getFluidTickScheduler().schedule(blockPos_1, Fluids.WATER, Fluids.WATER.getTickRate(iWorld_1));
        }

        return direction_1.getAxis().isHorizontal() ? blockState_1.with(SHAPE, method_10675(blockState_1, iWorld_1, blockPos_1)) : super.getStateForNeighborUpdate(blockState_1, direction_1, blockState_2, iWorld_1, blockPos_1, blockPos_2);
    }

    private static StairShape method_10675(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
        Direction direction_1 = blockState_1.get(FACING);
        BlockState blockState_2 = blockView_1.getBlockState(blockPos_1.offset(direction_1));
        if (isStairs(blockState_2) && blockState_1.get(HALF) == blockState_2.get(HALF)) {
            Direction direction_2 = blockState_2.get(FACING);
            if (direction_2.getAxis() != blockState_1.get(FACING).getAxis() && method_10678(blockState_1, blockView_1, blockPos_1, direction_2.getOpposite())) {
                if (direction_2 == direction_1.rotateYCounterclockwise()) {
                    return StairShape.OUTER_LEFT;
                }

                return StairShape.OUTER_RIGHT;
            }
        }

        BlockState blockState_3 = blockView_1.getBlockState(blockPos_1.offset(direction_1.getOpposite()));
        if (isStairs(blockState_3) && blockState_1.get(HALF) == blockState_3.get(HALF)) {
            Direction direction_3 = blockState_3.get(FACING);
            if (direction_3.getAxis() != blockState_1.get(FACING).getAxis() && method_10678(blockState_1, blockView_1, blockPos_1, direction_3)) {
                if (direction_3 == direction_1.rotateYCounterclockwise()) {
                    return StairShape.INNER_LEFT;
                }

                return StairShape.INNER_RIGHT;
            }
        }

        return StairShape.STRAIGHT;
    }

    private static boolean method_10678(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, Direction direction_1) {
        BlockState blockState_2 = blockView_1.getBlockState(blockPos_1.offset(direction_1));
        return !isStairs(blockState_2) || blockState_2.get(FACING) != blockState_1.get(FACING) || blockState_2.get(HALF) != blockState_1.get(HALF);
    }

    public static boolean isStairs(BlockState blockState_1) {
        return blockState_1.getBlock() instanceof StairsBlock;
    }

    public BlockState rotate(BlockState blockState_1, BlockRotation blockRotation_1) {
        return blockState_1.with(FACING, blockRotation_1.rotate(blockState_1.get(FACING)));
    }

    public BlockState mirror(BlockState blockState_1, BlockMirror blockMirror_1) {
        Direction direction_1 = blockState_1.get(FACING);
        StairShape stairShape_1 = blockState_1.get(SHAPE);
        switch (blockMirror_1) {
            case LEFT_RIGHT:
                if (direction_1.getAxis() == Direction.Axis.Z) {
                    switch (stairShape_1) {
                        case INNER_LEFT:
                            return blockState_1.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_RIGHT);
                        case INNER_RIGHT:
                            return blockState_1.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_LEFT);
                        case OUTER_LEFT:
                            return blockState_1.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return blockState_1.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_LEFT);
                        default:
                            return blockState_1.rotate(BlockRotation.CLOCKWISE_180);
                    }
                }
                break;
            case FRONT_BACK:
                if (direction_1.getAxis() == Direction.Axis.X) {
                    switch (stairShape_1) {
                        case INNER_LEFT:
                            return blockState_1.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_LEFT);
                        case INNER_RIGHT:
                            return blockState_1.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_RIGHT);
                        case OUTER_LEFT:
                            return blockState_1.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_RIGHT);
                        case OUTER_RIGHT:
                            return blockState_1.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_LEFT);
                        case STRAIGHT:
                            return blockState_1.rotate(BlockRotation.CLOCKWISE_180);
                    }
                }
        }

        return super.mirror(blockState_1, blockMirror_1);
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
        super.appendProperties(stateFactory$Builder_1.add(FACING, HALF, SHAPE, WATERLOGGED));
    }

    public FluidState getFluidState(BlockState blockState_1) {
        return blockState_1.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(blockState_1);
    }

    public boolean canPlaceAtSide(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, BlockPlacementEnvironment blockPlacementEnvironment_1) {
        return false;
    }


    static {
        FACING = HorizontalFacingBlock.FACING;
        HALF = Properties.BLOCK_HALF;
        SHAPE = Properties.STAIR_SHAPE;
        WATERLOGGED = Properties.WATERLOGGED;
        TOP_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
        BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
        BOTTOM_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 8.0D, 8.0D);
        BOTTOM_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 8.0D, 16.0D);
        TOP_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 8.0D, 16.0D, 8.0D);
        TOP_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 8.0D, 8.0D, 16.0D, 16.0D);
        BOTTOM_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 8.0D, 8.0D);
        BOTTOM_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 8.0D, 16.0D);
        TOP_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 0.0D, 16.0D, 16.0D, 8.0D);
        TOP_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0D, 8.0D, 8.0D, 16.0D, 16.0D, 16.0D);
        TOP_SHAPES = composeShapes(TOP_SHAPE, BOTTOM_NORTH_WEST_CORNER_SHAPE, BOTTOM_NORTH_EAST_CORNER_SHAPE, BOTTOM_SOUTH_WEST_CORNER_SHAPE, BOTTOM_SOUTH_EAST_CORNER_SHAPE);
        BOTTOM_SHAPES = composeShapes(BOTTOM_SHAPE, TOP_NORTH_WEST_CORNER_SHAPE, TOP_NORTH_EAST_CORNER_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE, TOP_SOUTH_EAST_CORNER_SHAPE);
        SHAPE_INDICES = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};
    }

    private static VoxelShape[] composeShapes(VoxelShape voxelShape_1, VoxelShape voxelShape_2, VoxelShape voxelShape_3, VoxelShape voxelShape_4, VoxelShape voxelShape_5) {
        return IntStream.range(0, 16).mapToObj((int_1) -> composeShape(int_1, voxelShape_1, voxelShape_2, voxelShape_3, voxelShape_4, voxelShape_5)).toArray((int_1) -> new VoxelShape[int_1]);
    }


    private static VoxelShape composeShape(int int_1, VoxelShape voxelShape_1, VoxelShape voxelShape_2, VoxelShape voxelShape_3, VoxelShape voxelShape_4, VoxelShape voxelShape_5) {
        VoxelShape voxelShape_6 = voxelShape_1;
        if ((int_1 & 1) != 0) {
            voxelShape_6 = VoxelShapes.union(voxelShape_1, voxelShape_2);
        }

        if ((int_1 & 2) != 0) {
            voxelShape_6 = VoxelShapes.union(voxelShape_6, voxelShape_3);
        }

        if ((int_1 & 4) != 0) {
            voxelShape_6 = VoxelShapes.union(voxelShape_6, voxelShape_4);
        }

        if ((int_1 & 8) != 0) {
            voxelShape_6 = VoxelShapes.union(voxelShape_6, voxelShape_5);
        }

        return voxelShape_6;
    }

}
