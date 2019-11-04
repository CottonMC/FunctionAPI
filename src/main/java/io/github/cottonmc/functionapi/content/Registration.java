package io.github.cottonmc.functionapi.content;

import com.google.common.collect.Lists;
import io.github.cottonmc.functionapi.content.templates.impl.block.*;
import io.github.cottonmc.functionapi.content.templates.BlockTemplate;
import io.github.cottonmc.functionapi.content.templates.ItemTemplate;
import io.github.cottonmc.functionapi.content.templates.impl.item.TemplatedItem;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;
import java.util.stream.Collectors;

public class Registration {

    public static void accept(ContentRegistrationContext contentRegistrationContext) {
        Map<Identifier, ContentRegistrationContext.ContentType> elements = contentRegistrationContext.getElements();

        if (elements.isEmpty()) {
            return;
        }
        Map<Integer, List<String>> variants = contentRegistrationContext.getVariants();

        if (variants.size() == 0) {
            elements.forEach((identifier, contentType) -> {
                register("", contentRegistrationContext, identifier, contentType);
            });
        } else {

            List<List<Object>> permutations = Lists.cartesianProduct(variants.values().toArray(new List[]{}));
            elements.forEach((identifier, contentType) -> {

                for (List<Object> variant : permutations) {
                    String variantName = "_" + variant.stream().map(Object::toString).collect(Collectors.joining("_"));
                    register(variantName, contentRegistrationContext, identifier, contentType);
                }
            });
        }
    }

    private static void register(String variantName, ContentRegistrationContext contentRegistrationContext, Identifier identifier, ContentRegistrationContext.ContentType contentType) {
        BlockTemplate blockTemplate = contentRegistrationContext.getBlockTemplate();
        ItemTemplate itemTemplate = contentRegistrationContext.getItemTemplate();

        if (contentType == ContentRegistrationContext.ContentType.BLOCK) {
            identifier = new Identifier(identifier.getNamespace(), identifier.getPath() + variantName + contentRegistrationContext.getPostfix());
            Block block;

            FabricBlockSettings fabricBlockSettings =
                    FabricBlockSettings
                            .of(blockTemplate.getMaterial())
                            .lightLevel(blockTemplate.getLightLevel())
                            .collidable(blockTemplate.isCollidable())
                            .hardness(blockTemplate.getHardness())
                            .sounds(blockTemplate.getSoundGroup())
                            .breakByTool(blockTemplate.getTool(), blockTemplate.getMiningLevel());

            if (blockTemplate.ticksRandomly()) {
                fabricBlockSettings = fabricBlockSettings.ticksRandomly();
            }

            Block.Settings settings = fabricBlockSettings.build();

            TemplatedBlock.currentTemplate = blockTemplate;
            switch (blockTemplate.getType()) {
                case STAIRS:
                    block = new TemplatedStairs(blockTemplate, settings);
                    break;
                case SLAB:
                    block = new TemplatedSlab(blockTemplate, settings);
                    break;
                case FENCE:
                    block = new TemplatedFence(blockTemplate, settings);
                    break;
                case PILLAR:
                    block = new TemplatedPillar(blockTemplate, settings);
                    break;
                case DIRECTIONAL:
                    block = new TemplatedDirectionalBlock(blockTemplate,settings);
                    break;
                default:
                    block = new TemplatedBlock(blockTemplate, settings);
            }

            block = Registry.register(Registry.BLOCK, identifier, block);

            if (blockTemplate.hasItem()) {
                Registry.register(Registry.ITEM, identifier, new BlockItem(block, new Item.Settings().maxCount(itemTemplate.maxCount()).group(ItemGroup.BUILDING_BLOCKS)));
            }
        }
        if (contentType == ContentRegistrationContext.ContentType.ITEM) {
            Registry.register(Registry.ITEM, identifier, new TemplatedItem(itemTemplate));
        }
    }

}