package io.github.cottonmc.functionapi;

import io.github.cottonmc.functionapi.api.content.enums.PlacementDirection;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Direction;
import org.apache.http.Header;

public class Util {

    public static boolean DirectiomMatches(Direction direction, PlacementDirection placementDirection){
        if(placementDirection == PlacementDirection.ALL)
            return true;
        return placementDirection == PlacementDirection.EAST && direction == Direction.EAST ||
                placementDirection == PlacementDirection.WEST && direction == Direction.WEST ||
                placementDirection == PlacementDirection.DOWN && direction == Direction.DOWN ||
                placementDirection == PlacementDirection.UP && direction == Direction.UP ||
                placementDirection == PlacementDirection.SOUTH && direction == Direction.SOUTH ||
                placementDirection == PlacementDirection.NORTH && direction == Direction.NORTH;
    }

    public static BlockRenderLayer getRenderLayerFrom(io.github.cottonmc.functionapi.api.content.enums.BlockRenderLayer renderLayer){
        switch (renderLayer){
            case TRANSLUCENT:
                return BlockRenderLayer.TRANSLUCENT;
            case CUTOUT:
                return BlockRenderLayer.CUTOUT;
            case CUTOUT_MIPPED:
                return BlockRenderLayer.CUTOUT_MIPPED;
            default: return BlockRenderLayer.SOLID;
        }
    }

    public static EquipmentSlot getSlot(io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot equipmentSlot){
        switch (equipmentSlot){
            case LEGGINGS:return EquipmentSlot.LEGS;
            case FOOT:return EquipmentSlot.FEET;
            case HELMET:return EquipmentSlot.HEAD;
            case OFF_HAND:return EquipmentSlot.OFFHAND;
            case MAIN_HAND:return EquipmentSlot.MAINHAND;
            default:return EquipmentSlot.CHEST;
        }
    }

    public static io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot getSlot(EquipmentSlot equipmentSlot){
        switch (equipmentSlot){
            case LEGS:return io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot.LEGGINGS;
            case FEET:return io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot.FOOT;
            case HEAD:return io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot.HELMET;
            case OFFHAND:return io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot.OFF_HAND;
            case MAINHAND:return io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot.MAIN_HAND;
            default:return io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot.CHESTPLATE;
        }
    }

    public static SoundEvent getSoundEvent(io.github.cottonmc.functionapi.api.content.enums.EquipSoundEvent equipSoundEvent){
        switch (equipSoundEvent){
            case GOLD:return SoundEvents.ITEM_ARMOR_EQUIP_GOLD;
            case IRON:return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
            case CHAIN:return SoundEvents.ITEM_ARMOR_EQUIP_CHAIN;
            case ELYTRA:return SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA;
            case TURTLE:return SoundEvents.ITEM_ARMOR_EQUIP_TURTLE;
            case DIAMOND:return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
            case LEATHER:return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
            default:
                return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
        }

    }
}
