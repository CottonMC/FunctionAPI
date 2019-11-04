package io.github.cottonmc.functionapi.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import io.github.cottonmc.functionapi.content.ContentRegistrationContext;
import io.github.cottonmc.functionapi.content.StaticCommandExecutor;
import io.github.cottonmc.functionapi.content.templates.BlockTemplate;
import io.github.cottonmc.functionapi.content.templates.ItemTemplate;
import io.github.cottonmc.functionapi.content.templates.impl.DefaultItemTemplate;
import net.minecraft.util.Identifier;

import java.util.*;

public class ContentRegistrationContextImpl implements ContentRegistrationContext {

    private final Map<Identifier, String[]> rawCommands;
    private final CommandDispatcher<ContentRegistrationContext> dispatcher;

    private final BlockTemplate blockTemplate = new BlockTemplate();
    private final ItemTemplate itemTemplate = new DefaultItemTemplate();

    private final Map<Integer,List<String>> variants = new LinkedHashMap<>();

    private String postfix = "";

    public ContentRegistrationContextImpl(Map<Identifier, String[]> rawCommands, CommandDispatcher<ContentRegistrationContext> dispatcher) {
        this.rawCommands = rawCommands;
        this.dispatcher = dispatcher;
    }

    private Map<Identifier, ContentType> elements = new HashMap<>();

    @Override
    public void addPostfix(String postfix) {
        this.postfix += "_" + postfix;
    }

    @Override
    public String getPostfix() {
        return postfix;
    }

    @Override
    public void createElement(ContentType contentType, Identifier identifier) {
        elements.put(identifier, contentType);
    }

    @Override
    public Map<Identifier, ContentType> getElements() {
        return elements;
    }

    @Override
    public Map<Integer, List<String>> getVariants() {
        return variants;
    }

    @Override
    public void addVariant(String variant, int variant_index) {
        if(!variants.containsKey(variant_index)){
            variants.put(variant_index,new ArrayList<>());
        }
        variants.get(variant_index).add(variant);
    }

    @Override
    public void runCommand(Identifier identifier) {
        if (rawCommands.containsKey(identifier)) {
            String[] strings = rawCommands.get(identifier);
            StaticCommandExecutor.executeFile(dispatcher, strings,identifier,this);

        } else {
            throw new MissingCommandException("content registration file not found!", identifier);
        }
    }


    @Override
    public BlockTemplate getBlockTemplate() {
        return blockTemplate;
    }

    @Override
    public ItemTemplate getItemTemplate() {
        return itemTemplate;
    }

    class MissingCommandException extends RuntimeException {
        MissingCommandException(String message, Identifier identifier) {
            super("Command \"" + identifier + "\" not found: " + message);
        }
    }
}
