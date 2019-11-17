package io.github.cottonmc.functionapi.content.templates;

import io.github.cottonmc.functionapi.Util;
import io.github.cottonmc.functionapi.api.ExtendedBlockProperties;
import io.github.cottonmc.functionapi.api.content.BlockTemplate;
import io.github.cottonmc.functionapi.api.content.enums.BlockStatePropertyTypes;
import io.github.cottonmc.functionapi.api.content.enums.PlacementPosition;
import io.github.cottonmc.functionapi.content.PlacementMapping;
import io.github.cottonmc.functionapi.content.templates.state.StringBlockStateProperty;
import io.github.cottonmc.functionapi.content.templates.state.StringBlockStateValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.AbstractProperty;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static net.minecraft.block.StairsBlock.*;

/**
 * Block that takes the block template and builds up itself from it.
 */
public class TemplatedBlock extends Block implements ExtendedBlockProperties {
    private BlockTemplate template;
    private final BlockRenderLayer renderLayer;

    public static io.github.cottonmc.functionapi.api.content.BlockTemplate currentTemplate;

    public TemplatedBlock(BlockTemplate template, Settings blockSettings) {
        super(blockSettings);
        this.template = template;
        renderLayer = Util.getRenderLayerFrom(template.getRenderLayer());
    }

    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
        Map<String, Pair<BlockStatePropertyTypes, Object>> properties = currentTemplate.getProperties();

        if (!properties.isEmpty()) {
            for (Map.Entry<String, Pair<BlockStatePropertyTypes, Object>> entry : properties.entrySet()) {
                String name = entry.getKey();
                AbstractProperty property = null;

                Pair<BlockStatePropertyTypes, Object> value = entry.getValue();
                switch (value.getKey()){
                    case RANGE:
                        int stateValue = (Integer) value.getValue();
                        property = IntProperty.of(name,0,stateValue);
                        break;
                    case BOOLEAN:
                        property = BooleanProperty.of(name);
                        break;
                    default:
                        property = new StringBlockStateProperty(name);
                        List<String> values = (List<String>) value.getRight();
                        for (String s : values) {
                            ((StringBlockStateProperty)property).addValue(s);
                        }
                        break;
                }
                stateFactory$Builder_1 = stateFactory$Builder_1.add(property);
            }

        }
        if (currentTemplate.isStairs()) {
            stateFactory$Builder_1 = stateFactory$Builder_1.add(FACING, HALF, SHAPE, WATERLOGGED);
        }
        super.appendProperties(stateFactory$Builder_1);

    }

    private List<String> propertyNameList = null;

    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
        BlockState defaultState = this.getDefaultState();
        Direction direction_1 = itemPlacementContext_1.getSide();
        BlockPos blockPos_1 = itemPlacementContext_1.getBlockPos();
        FluidState fluidState_1 = itemPlacementContext_1.getWorld().getFluidState(blockPos_1);
        Direction playerFacing = itemPlacementContext_1.getPlayerFacing();
        if (template.isStairs()) {
            BlockState blockState_1 = defaultState.with(FACING, playerFacing).with(HALF, direction_1 != Direction.DOWN && (direction_1 == Direction.UP || itemPlacementContext_1.getHitPos().y - (double) blockPos_1.getY() <= 0.5D) ? BlockHalf.BOTTOM : BlockHalf.TOP).with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER);
            defaultState = blockState_1.with(SHAPE, calculateStairShape(blockState_1, itemPlacementContext_1.getWorld(), blockPos_1));
        }


        Collection<Property<?>> properties = defaultState.getProperties();
        if (propertyNameList == null) {
            propertyNameList = properties.stream().map(Property::getName).collect(Collectors.toList());
        }

        List<PlacementMapping> placementStates = template.getPlacementStates();

        List<String> setNames = new ArrayList<>();

        for (PlacementMapping placementState : placementStates) {
            String stateName = placementState.getStateName();
            if (propertyNameList.contains(stateName)) {
                if (!setNames.contains(stateName)) {
                    boolean directiomMatches = Util.DirectiomMatches(direction_1, placementState.getPlacementDirection());
                    if (directiomMatches) {
                        boolean facingMatches = Util.DirectiomMatches(playerFacing, placementState.getFacingDirection());
                        if (facingMatches) {
                            if (placementState.getPlacementPosition() == PlacementPosition.ALL || placementState.getPlacementPosition() == (direction_1 != Direction.DOWN && (direction_1 == Direction.UP || itemPlacementContext_1.getHitPos().y - (double) blockPos_1.getY() <= 0.5D) ? PlacementPosition.LOWER : PlacementPosition.UPPER)) {
                                for (Property<?> property : properties) {
                                    if (property.getName().equals(stateName)) {
                                        if (property instanceof StringBlockStateProperty) {
                                            Optional<StringBlockStateValue> value = (Optional<StringBlockStateValue>) property.getValue(placementState.getStateValue());
                                            if (value.isPresent()) {
                                                StringBlockStateValue blockStateValue = value.get();
                                                defaultState = defaultState.with((StringBlockStateProperty) property, blockStateValue);
                                                setNames.add(stateName);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

        return defaultState;
    }

    private static StairShape calculateStairShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1) {
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

    @Override
    public BlockRenderLayer getRenderLayer() {
        return renderLayer;
    }

    @Override
    public boolean hasRandomTicks(BlockState blockState_1) {
        return template.ticksRandomly();
    }

    @Override
    public boolean isAir(BlockState blockState_1) {
        return template.isAir();
    }

    @Override
    public boolean allowsSpawning(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityType<?> entityType_1) {
        return template.canSpawnMobs();
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState_1) {
        if (template.isInvisible()) {
            return BlockRenderType.INVISIBLE;
        }
        return super.getRenderType(blockState_1);
    }

    @Override
    public BlockSoundGroup getSoundGroup(BlockState blockState_1) {
        return super.getSoundGroup(blockState_1);
    }

    @Override
    public boolean isBlockStairs() {
        return template.isStairs();
    }
}
