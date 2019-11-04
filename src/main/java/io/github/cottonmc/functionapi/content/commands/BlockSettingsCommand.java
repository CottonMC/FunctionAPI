package io.github.cottonmc.functionapi.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github.cottonmc.functionapi.content.ContentRegistrationContext;
import io.github.cottonmc.functionapi.content.templates.BlockTemplate;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

import java.util.HashMap;

public class BlockSettingsCommand {
    public static void register(CommandDispatcher<ContentRegistrationContext> commandDispatcher) {

        commandDispatcher.register(LiteralArgumentBuilder.<ContentRegistrationContext>literal("blocksettings")
                .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("mininglevel")
                        .then(RequiredArgumentBuilder.<ContentRegistrationContext, Integer>argument("level", IntegerArgumentType.integer())
                                .executes(context -> {
                                    int level = IntegerArgumentType.getInteger(context, "level");
                                    context.getSource().getBlockTemplate().setMiningLevel(level);
                                    return 1;
                                }))
                )
                .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("hardness")
                        .then(RequiredArgumentBuilder.<ContentRegistrationContext, Integer>argument("level", IntegerArgumentType.integer())
                                .executes(context -> {
                                    int level = IntegerArgumentType.getInteger(context, "level");
                                    context.getSource().getBlockTemplate().setHardness(level);
                                    return 1;
                                }))
                )
                .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("disableitem")
                        .executes(context -> {
                            context.getSource().getBlockTemplate().setHasItem(false);
                            return 1;
                        })
                )
                .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("ticksrandomly")
                        .executes(context -> {
                            context.getSource().getBlockTemplate().setTicksRandomly(true);
                            return 1;
                        })
                )
                .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("emitsredstone")
                        .executes(context -> {
                            context.getSource().getBlockTemplate().createIntProperty("redstone_out", 0, 15);
                            return 1;
                        })
                )
                .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("hascomparatorout")
                        .executes(context -> {
                            context.getSource().getBlockTemplate().createIntProperty("comparator_out", 0, 15);
                            return 1;
                        })
                )
                .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("invisible")
                        .executes(context -> {
                            context.getSource().getBlockTemplate().setInvisible(true);
                            return 1;
                        })
                )
                .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("ethereal")
                        .executes(context -> {
                            context.getSource().getBlockTemplate().setCollidable(false);
                            return 1;
                        })
                )
                .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("air")
                        .executes(context -> {
                            context.getSource().getBlockTemplate().setAir(true);
                            return 1;
                        })
                )
                .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("nomobspawning")
                        .executes(context -> {
                            context.getSource().getBlockTemplate().setCanSpawnMobs(false);
                            return 1;
                        })
                )
                .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("rendermode")
                        .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("translucent")
                                .executes(context -> {
                                    context.getSource().getBlockTemplate().setRenderLayer(BlockRenderLayer.TRANSLUCENT);
                                    return 1;
                                }))
                        .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("cutout")
                                .executes(context -> {
                                    context.getSource().getBlockTemplate().setRenderLayer(BlockRenderLayer.CUTOUT);
                                    return 1;
                                }))
                        .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("cutoutmipped")
                                .executes(context -> {
                                    context.getSource().getBlockTemplate().setRenderLayer(BlockRenderLayer.CUTOUT_MIPPED);
                                    return 1;
                                }))
                )
                .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("tool")
                        .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("pickaxe")
                                .executes(context -> {
                                    context.getSource().getBlockTemplate().setTool(FabricToolTags.PICKAXES);
                                    return 1;
                                }))
                        .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("axe")
                                .executes(context -> {
                                    context.getSource().getBlockTemplate().setTool(FabricToolTags.AXES);
                                    return 1;
                                }))
                        .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("shovel")
                                .executes(context -> {
                                    context.getSource().getBlockTemplate().setTool(FabricToolTags.SHOVELS);
                                    return 1;
                                }))
                        .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("hoe")
                                .executes(context -> {
                                    context.getSource().getBlockTemplate().setTool(FabricToolTags.HOES);
                                    return 1;
                                }))
                        .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("sword")
                                .executes(context -> {
                                    context.getSource().getBlockTemplate().setTool(FabricToolTags.SWORDS);
                                    return 1;
                                }))

                ).then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("state")
                        .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("range")
                                .then(RequiredArgumentBuilder.<ContentRegistrationContext, String>argument("name", StringArgumentType.string())
                                        .then(RequiredArgumentBuilder.<ContentRegistrationContext, Integer>argument("min", IntegerArgumentType.integer())
                                                .then(RequiredArgumentBuilder.<ContentRegistrationContext, Integer>argument("max", IntegerArgumentType.integer())
                                                        .executes(context -> {

                                                            String name = StringArgumentType.getString(context, "name");
                                                            if (isNameIllegal(name))
                                                                return 0;
                                                            int min = IntegerArgumentType.getInteger(context, "min");
                                                            int max = IntegerArgumentType.getInteger(context, "max");

                                                            context.getSource().getBlockTemplate().createIntProperty(name, min, max);
                                                            return 1;
                                                        })
                                                )
                                        )))
                        .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("boolean")
                                .then(RequiredArgumentBuilder.<ContentRegistrationContext, String>argument("name", StringArgumentType.string())
                                        .executes(context -> {
                                            String name = StringArgumentType.getString(context, "name");
                                            if (isNameIllegal(name))
                                                return 0;
                                            context.getSource().getBlockTemplate().createBooleanProperty(name);
                                            return 1;
                                        })))
                        .then(RequiredArgumentBuilder.<ContentRegistrationContext, String>argument("name", StringArgumentType.string())
                                .then(RequiredArgumentBuilder.<ContentRegistrationContext, String>argument("value", StringArgumentType.string())
                                        .executes(context -> {
                                            String name = StringArgumentType.getString(context, "name");
                                            if (isNameIllegal(name))
                                                return 0;
                                            String value = StringArgumentType.getString(context, "value");
                                            context.getSource().getBlockTemplate().addToStringProperty(name, value);
                                            return 1;
                                        })
                                )))
                .then(materialNode())
                .then(soundNode())
                .then(typeNode())
        );
    }

    private static LiteralArgumentBuilder<ContentRegistrationContext> materialNode() {
        LiteralArgumentBuilder<ContentRegistrationContext> materialArgument = LiteralArgumentBuilder.literal("material");
        HashMap<Material, String> materialHashMap = new HashMap<>();

        materialHashMap.put(Material.AIR, "air");
        materialHashMap.put(Material.ANVIL, "anvil");
        materialHashMap.put(Material.BAMBOO, "bamboo");
        materialHashMap.put(Material.BAMBOO_SAPLING, "bamboo_sapling");
        materialHashMap.put(Material.BARRIER, "barrier");
        materialHashMap.put(Material.BUBBLE_COLUMN, "bubble_column");
        materialHashMap.put(Material.CACTUS, "cactus");
        materialHashMap.put(Material.CAKE, "cake");
        materialHashMap.put(Material.CARPET, "carpet");
        materialHashMap.put(Material.CLAY, "clay");
        materialHashMap.put(Material.COBWEB, "cobweb");
        materialHashMap.put(Material.EARTH, "earth");
        materialHashMap.put(Material.EGG, "egg");
        materialHashMap.put(Material.FIRE, "fire");
        materialHashMap.put(Material.GLASS, "glass");
        materialHashMap.put(Material.ICE, "ice");
        materialHashMap.put(Material.LAVA, "lava");
        materialHashMap.put(Material.LEAVES, "leaves");
        materialHashMap.put(Material.METAL, "metal");
        materialHashMap.put(Material.ORGANIC, "organic");
        materialHashMap.put(Material.PACKED_ICE, "packed_ice");
        materialHashMap.put(Material.PISTON, "piston");
        materialHashMap.put(Material.PLANT, "plant");
        materialHashMap.put(Material.PORTAL, "portal");
        materialHashMap.put(Material.PUMPKIN, "pumpkin");
        materialHashMap.put(Material.REDSTONE_LAMP, "redstone_lamp");
        materialHashMap.put(Material.REPLACEABLE_PLANT, "replaceable_plant");
        materialHashMap.put(Material.SAND, "sand");
        materialHashMap.put(Material.SEAGRASS, "seagrass");
        materialHashMap.put(Material.SHULKER_BOX, "shulker_box");
        materialHashMap.put(Material.SNOW, "snow");
        materialHashMap.put(Material.SNOW_BLOCK, "snow_block");
        materialHashMap.put(Material.SPONGE, "sponge");
        materialHashMap.put(Material.STONE, "stone");
        materialHashMap.put(Material.STRUCTURE_VOID, "structure_void");
        materialHashMap.put(Material.UNDERWATER_PLANT, "underwater_plant");
        materialHashMap.put(Material.WATER, "water");
        materialHashMap.put(Material.WOOD, "wood");
        materialHashMap.put(Material.WOOL, "wool");

        materialHashMap.forEach((material, s) ->
                materialArgument.then(LiteralArgumentBuilder.<ContentRegistrationContext>literal(s)
                        .executes(context -> {
                            context.getSource().getBlockTemplate().setMaterial(material);
                            return 1;
                        })
                ));

        return materialArgument;
    }

    private static LiteralArgumentBuilder<ContentRegistrationContext> soundNode() {
        LiteralArgumentBuilder<ContentRegistrationContext> materialArgument = LiteralArgumentBuilder.literal("sound");
        HashMap<BlockSoundGroup, String> soundGroupStringHashMap = new HashMap<>();

        soundGroupStringHashMap.put(BlockSoundGroup.ANVIL, "anvil");
        soundGroupStringHashMap.put(BlockSoundGroup.BAMBOO, "bamboo");
        soundGroupStringHashMap.put(BlockSoundGroup.BAMBOO_SAPLING, "bamboo_sapling");
        soundGroupStringHashMap.put(BlockSoundGroup.CORAL, "coral");
        soundGroupStringHashMap.put(BlockSoundGroup.CROP, "crop");
        soundGroupStringHashMap.put(BlockSoundGroup.GLASS, "glass");
        soundGroupStringHashMap.put(BlockSoundGroup.GRASS, "grass");
        soundGroupStringHashMap.put(BlockSoundGroup.GRAVEL, "gravel");
        soundGroupStringHashMap.put(BlockSoundGroup.LADDER, "ladder");
        soundGroupStringHashMap.put(BlockSoundGroup.LANTERN, "lantern");
        soundGroupStringHashMap.put(BlockSoundGroup.METAL, "metal");
        soundGroupStringHashMap.put(BlockSoundGroup.NETHER_WART, "nether_Wart");
        soundGroupStringHashMap.put(BlockSoundGroup.SAND, "sand");
        soundGroupStringHashMap.put(BlockSoundGroup.SCAFFOLDING, "scaffolding");
        soundGroupStringHashMap.put(BlockSoundGroup.SLIME, "slime");
        soundGroupStringHashMap.put(BlockSoundGroup.SNOW, "snow");
        soundGroupStringHashMap.put(BlockSoundGroup.STEM, "stem");
        soundGroupStringHashMap.put(BlockSoundGroup.STONE, "stone");
        soundGroupStringHashMap.put(BlockSoundGroup.SWEET_BERRY_BUSH, "sweet_berry_bush");
        soundGroupStringHashMap.put(BlockSoundGroup.WET_GRASS, "wet_grass");
        soundGroupStringHashMap.put(BlockSoundGroup.WOOD, "wood");
        soundGroupStringHashMap.put(BlockSoundGroup.WOOL, "wool");

        soundGroupStringHashMap.forEach((soundGroup, s) ->
                materialArgument.then(LiteralArgumentBuilder.<ContentRegistrationContext>literal(s)
                        .executes(context -> {
                            context.getSource().getBlockTemplate().setSoundGroup(soundGroup);
                            return 1;
                        })
                ));

        return materialArgument;
    }

    private static LiteralArgumentBuilder<ContentRegistrationContext> typeNode() {
        LiteralArgumentBuilder<ContentRegistrationContext> materialArgument = LiteralArgumentBuilder.literal("type");

        for (BlockTemplate.Type value : BlockTemplate.Type.values()) {
            materialArgument.then(LiteralArgumentBuilder.<ContentRegistrationContext>literal(value.name().toLowerCase())
                    .executes(context -> {
                        context.getSource().getBlockTemplate().setType(value);
                        return 1;
                    })
            );
        }

        return materialArgument;
    }

    private static boolean isNameIllegal(String name) {
        //reserved names that control redstone output.
        return (name.equals("redstone_out") || name.equals("comparator_out"));
    }

}
