package io.github.cottonmc.functionapi;

import io.github.cottonmc.functionapi.api.content.enums.Direction;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class Util {

    public static boolean DirectiomMatches(net.minecraft.util.math.Direction direction, Direction placementDirection) {
        if (placementDirection == Direction.ALL)
            return true;
        return placementDirection == Direction.EAST && direction == net.minecraft.util.math.Direction.EAST ||
                placementDirection == Direction.WEST && direction == net.minecraft.util.math.Direction.WEST ||
                placementDirection == Direction.DOWN && direction == net.minecraft.util.math.Direction.DOWN ||
                placementDirection == Direction.UP && direction == net.minecraft.util.math.Direction.UP ||
                placementDirection == Direction.SOUTH && direction == net.minecraft.util.math.Direction.SOUTH ||
                placementDirection == Direction.NORTH && direction == net.minecraft.util.math.Direction.NORTH;
    }

    public static EquipmentSlot getSlot(io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot equipmentSlot) {
        switch (equipmentSlot) {
            case LEGGINGS:
                return EquipmentSlot.LEGS;
            case FOOT:
                return EquipmentSlot.FEET;
            case HELMET:
                return EquipmentSlot.HEAD;
            case OFF_HAND:
                return EquipmentSlot.OFFHAND;
            case MAIN_HAND:
                return EquipmentSlot.MAINHAND;
            default:
                return EquipmentSlot.CHEST;
        }
    }

    public static io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot getSlot(EquipmentSlot equipmentSlot) {
        switch (equipmentSlot) {
            case LEGS:
                return io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot.LEGGINGS;
            case FEET:
                return io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot.FOOT;
            case HEAD:
                return io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot.HELMET;
            case OFFHAND:
                return io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot.OFF_HAND;
            case MAINHAND:
                return io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot.MAIN_HAND;
            default:
                return io.github.cottonmc.functionapi.api.content.enums.EquipmentSlot.CHESTPLATE;
        }
    }

    public static SoundEvent getSoundEvent(io.github.cottonmc.functionapi.api.content.enums.EquipSoundEvent equipSoundEvent) {
        switch (equipSoundEvent) {
            case GOLD:
                return SoundEvents.ITEM_ARMOR_EQUIP_GOLD;
            case IRON:
                return SoundEvents.ITEM_ARMOR_EQUIP_IRON;
            case CHAIN:
                return SoundEvents.ITEM_ARMOR_EQUIP_CHAIN;
            case ELYTRA:
                return SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA;
            case TURTLE:
                return SoundEvents.ITEM_ARMOR_EQUIP_TURTLE;
            case DIAMOND:
                return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
            case LEATHER:
                return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
            default:
                return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
        }

    }

    public static net.minecraft.util.math.Direction getDirection(Direction direction) {
        switch (direction) {
            case EAST:
                return net.minecraft.util.math.Direction.EAST;
            case WEST:
                return net.minecraft.util.math.Direction.WEST;
            case DOWN:
                return net.minecraft.util.math.Direction.DOWN;
            case NORTH:
                return net.minecraft.util.math.Direction.NORTH;
            case SOUTH:
                return net.minecraft.util.math.Direction.SOUTH;
            default:
                return net.minecraft.util.math.Direction.UP;
        }
    }
}
