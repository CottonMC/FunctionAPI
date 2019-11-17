package io.github.cottonmc.functionapi.content;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.cottonmc.functionapi.api.content.*;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.script.FunctionAPIIdentifierImpl;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public abstract class RegistrationTemplate implements BiConsumer<CommandFileSource<String>, CommandDispatcher<ContentRegistrationContext>> {

    private void accept(ContentRegistrationContext contentRegistrationContext) {
        Map<FunctionAPIIdentifier, ContentRegistrationContext.ContentType> elements = contentRegistrationContext.getElements();

        if (elements.isEmpty()) {
            return;
        }

        Map<Integer, List<String>> variants = contentRegistrationContext.getVariants();

        if (variants.size() == 0) {
            elements.forEach((identifier, contentType) -> register("", contentRegistrationContext, identifier, contentType));
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

    private void register(String variantName, ContentRegistrationContext contentRegistrationContext, FunctionAPIIdentifier identifier, ContentRegistrationContext.ContentType contentType) {
        BlockTemplate blockTemplate = contentRegistrationContext.getBlockTemplate();
        ItemTemplate itemTemplate = contentRegistrationContext.getItemTemplate();

        identifier = new FunctionAPIIdentifierImpl(identifier.getNamespace(), identifier.getPath() + variantName + contentRegistrationContext.getPostfix());

        if (contentType == ContentRegistrationContext.ContentType.BLOCK) {
            if (blockTemplate.hasItem()) {
                registerBlock(blockTemplate,itemTemplate,identifier);
            }
        }
        if (contentType == ContentRegistrationContext.ContentType.ITEM) {
            registerItem(itemTemplate,contentRegistrationContext.getItemMaterial(),identifier);
        }
    }


    @Override
    public void accept(CommandFileSource<String> commandFileSource, CommandDispatcher<ContentRegistrationContext> contentRegistrationContextCommandDispatcher) {
        Map<FunctionAPIIdentifier, List<String>> commands = commandFileSource.getCommandFiles();

        ContentCommandExecutor contentRegistrationContextCommandExecutor = ContentCommandExecutor.INSTANCE;
        commands.forEach((identifier, list) -> {
            ContentRegistrationContextImpl context = new ContentRegistrationContextImpl(null);

            if (identifier.getPath().split("/").length == 1) {
                try {
                    for (String command : list) {
                        contentRegistrationContextCommandExecutor.getCommandDispatcher().execute(command, context);
                    }
                } catch (CommandSyntaxException e) {
                    System.err.println(e.getInput());
                    e.printStackTrace();
                }

                accept(context);
            }

        });
    }


    protected abstract void registerBlock(BlockTemplate blockTemplate, ItemTemplate itemTemplate, FunctionAPIIdentifier identifier);
    protected abstract void registerItem(ItemTemplate template, ItemMaterial itemMaterial, FunctionAPIIdentifier identifier);

}
