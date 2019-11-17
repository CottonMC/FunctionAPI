package io.github.cottonmc.functionapi.content;

import io.github.cottonmc.functionapi.api.content.BlockTemplate;
import io.github.cottonmc.functionapi.api.content.ItemMaterial;
import io.github.cottonmc.functionapi.api.content.ItemTemplate;
import io.github.cottonmc.functionapi.api.content.enums.Tools;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.content.templates.GeneratedToolMaterial;
import io.github.cottonmc.functionapi.content.templates.TemplatedBlock;
import io.github.cottonmc.functionapi.content.templates.item.*;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Registration extends RegistrationTemplate {

    @Override
    protected void registerBlock(BlockTemplate blockTemplate, ItemTemplate itemTemplate, FunctionAPIIdentifier identifier) {
        net.minecraft.util.Identifier mcIdentifier = new net.minecraft.util.Identifier(identifier.getNamespace(), identifier.getPath());

        FabricBlockSettings fabricBlockSettings =
                FabricBlockSettings
                        .of(getMaterialFrom(blockTemplate.getMaterial()))
                        .lightLevel(blockTemplate.getLightLevel())
                        .collidable(blockTemplate.isCollidable())
                        .hardness(blockTemplate.getHardness())
                        .sounds(getSoundGroupFrom(blockTemplate.getSoundGroup()))
                        .breakByTool(getTool(blockTemplate.getTool()), blockTemplate.getMiningLevel());

        if (blockTemplate.ticksRandomly()) {
            fabricBlockSettings = fabricBlockSettings.ticksRandomly();
        }

        Block.Settings settings = fabricBlockSettings.build();

        TemplatedBlock.currentTemplate = blockTemplate;
        Block block = new TemplatedBlock(blockTemplate, settings);

        block = Registry.register(Registry.BLOCK, mcIdentifier, block);

        if (blockTemplate.hasItem()) {
            Registry.register(Registry.ITEM, mcIdentifier, new BlockItem(block, new Item.Settings().maxCount(itemTemplate.maxCount()).group(ItemGroup.BUILDING_BLOCKS)));
        }
    }

    @Override
    protected void registerItem(ItemTemplate template, ItemMaterial itemMaterial, FunctionAPIIdentifier identifier) {
        Identifier mcIdentifier = new Identifier(identifier.getNamespace(), identifier.getPath());

        Item item;
        Item.Settings settings = new Item.Settings()
                .maxCount(template.maxCount());
        switch (template.getType()) {
            case BOW:
                item = new TemplatedBow(new GeneratedToolMaterial(itemMaterial), template, settings);
                break;
            case CROSSBOW:
                item = new TemplatedCrossBow(new GeneratedToolMaterial(itemMaterial), template, settings);
                break;
            case TOOL:
                item = new TemplatedTool(new GeneratedToolMaterial(itemMaterial), template, settings);
                break;
            case AXE:
                item = new TemplatedTool(new GeneratedToolMaterial(itemMaterial), template, settings);
                ToolTagHelper.INSTANCE.addTag(item,FabricToolTags.AXES);
                break;

            case PICKAXE:
                item = new TemplatedTool(new GeneratedToolMaterial(itemMaterial), template, settings);
                ToolTagHelper.INSTANCE.addTag(item,FabricToolTags.PICKAXES);
                break;

            case SHOVEL:
                item = new TemplatedTool(new GeneratedToolMaterial(itemMaterial), template, settings);
                ToolTagHelper.INSTANCE.addTag(item,FabricToolTags.SHOVELS);
                break;

            case SHIELD:
                item = new TemplatedShield(new GeneratedToolMaterial(itemMaterial), settings);
                break;
            default:
                item = new TemplatedItem(template, settings);
        }

        Registry.register(Registry.ITEM, mcIdentifier, item);
    }

    private Material getMaterialFrom(io.github.cottonmc.functionapi.api.content.enums.Material material) {
        switch (material) {
            case ANVIL:
                return Material.ANVIL;
            case BAMBOO:
                return Material.BAMBOO;
            case BAMBOO_SAPLING:
                return Material.BAMBOO_SAPLING;
            case BARRIER:
                return Material.BARRIER;
            case BUBBLE_COLUMN:
                return Material.BUBBLE_COLUMN;
            case CACTUS:
                return Material.CACTUS;
            case CAKE:
                return Material.CAKE;
            case CARPET:
                return Material.CARPET;
            case CLAY:
                return Material.CLAY;
            case EARTH:
                return Material.EARTH;
            case EGG:
                return Material.EGG;
            case FIRE:
                return Material.FIRE;
            case GLASS:
                return Material.GLASS;
            case ICE:
                return Material.ICE;
            case LAVA:
                return Material.LAVA;
            case LEAVES:
                return Material.LEAVES;
            case METAL:
                return Material.METAL;
            case ORGANIC:
                return Material.ORGANIC;
            case PACKED_ICE:
                return Material.PACKED_ICE;
            case PISTON:
                return Material.PISTON;
            case PLANT:
                return Material.PLANT;
            case PORTAL:
                return Material.PORTAL;
            case PUMPKIN:
                return Material.PUMPKIN;
            case REDSTONE_LAMP:
                return Material.REDSTONE_LAMP;
            case REPLACEABLE_PLANT:
                return Material.REPLACEABLE_PLANT;
            case SAND:
                return Material.SAND;
            case SEAGRASS:
                return Material.SEAGRASS;
            case SHULKER_BOX:
                return Material.SHULKER_BOX;
            case SNOW:
                return Material.SNOW;
            case SNOW_BLOCK:
                return Material.SNOW_BLOCK;
            case SPONGE:
                return Material.SPONGE;
            case STONE:
                return Material.STONE;
            case STRUCTURE_VOID:
                return Material.STRUCTURE_VOID;
            case UNDERWATER_PLANT:
                return Material.UNDERWATER_PLANT;
            case WATER:
                return Material.WATER;
            case WOOD:
                return Material.WOOD;
            case WOOL:
                return Material.WOOL;
            default:
                return Material.AIR;
        }
    }

    private BlockSoundGroup getSoundGroupFrom(io.github.cottonmc.functionapi.api.content.enums.BlockSoundGroup blockSoundGroup) {
        switch (blockSoundGroup) {
            case WOOD:
                return BlockSoundGroup.WOOD;
            case GRASS:
                return BlockSoundGroup.GRASS;
            case METAL:
                return BlockSoundGroup.METAL;
            case GLASS:
                return BlockSoundGroup.GLASS;
            case WOOL:
                return BlockSoundGroup.WOOL;
            case SAND:
                return BlockSoundGroup.SAND;
            case SNOW:
                return BlockSoundGroup.SNOW;
            case LADDER:
                return BlockSoundGroup.LADDER;
            case ANVIL:
                return BlockSoundGroup.ANVIL;
            case SLIME:
                return BlockSoundGroup.SLIME;
            case WET_GRASS:
                return BlockSoundGroup.WET_GRASS;
            case CORAL:
                return BlockSoundGroup.CORAL;
            case BAMBOO:
                return BlockSoundGroup.BAMBOO;
            case BAMBOO_SAPLING:
                return BlockSoundGroup.BAMBOO_SAPLING;
            case SCAFFOLDING:
                return BlockSoundGroup.SCAFFOLDING;
            case SWEET_BERRY_BUSH:
                return BlockSoundGroup.SWEET_BERRY_BUSH;
            case CROP:
                return BlockSoundGroup.CROP;
            case STEM:
                return BlockSoundGroup.STEM;
            case NETHER_WART:
                return BlockSoundGroup.NETHER_WART;
            case LANTERN:
                return BlockSoundGroup.LANTERN;
            default:
                return BlockSoundGroup.STONE;
        }
    }

    private Tag<Item> getTool(Tools tool) {
        switch (tool) {
            case AXES:
                return FabricToolTags.AXES;
            case HOES:
                return FabricToolTags.HOES;
            case SHOVELS:
                return FabricToolTags.SHOVELS;
            case SWORDS:
                return FabricToolTags.SWORDS;
            default:
                return FabricToolTags.PICKAXES;
        }
    }


}