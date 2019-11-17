package io.github.cottonmc.functionapi.content;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.cottonmc.functionapi.api.content.BlockTemplate;
import io.github.cottonmc.functionapi.api.content.ItemMaterial;
import io.github.cottonmc.functionapi.content.template.ItemMaterialImpl;
import io.github.cottonmc.functionapi.content.template.ItemTemplateImpl;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.content.template.BlockTemplateImpl;
import io.github.cottonmc.functionapi.api.content.ContentRegistrationContext;
import io.github.cottonmc.functionapi.api.content.ItemTemplate;

import java.util.*;

public class ContentRegistrationContextImpl implements ContentRegistrationContext{

    private final CommandDispatcher<ContentRegistrationContext> dispatcher;

    private final BlockTemplate blockTemplate = new BlockTemplateImpl();
    private final ItemTemplate itemTemplate = new ItemTemplateImpl();
    private final ItemMaterial itemMaterial= new ItemMaterialImpl();
    private final Map<Integer,List<String>> variants = new LinkedHashMap<>();



    private String postfix = "";

    public ContentRegistrationContextImpl(CommandDispatcher<ContentRegistrationContext> dispatcher) {
        this.dispatcher = dispatcher;
    }

    private Map<FunctionAPIIdentifier, ContentType> elements = new HashMap<>();

    @Override
    public void addPostfix(String postfix) {
        this.postfix += "_" + postfix;
    }

    @Override
    public String getPostfix() {
        return postfix;
    }


    @Override
    public void createElement(ContentType contentType, FunctionAPIIdentifier functionAPIIdentifier) {
        elements.put(functionAPIIdentifier, contentType);
    }

    @Override
    public Map<FunctionAPIIdentifier, ContentType> getElements() {
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
    public void runCommand(FunctionAPIIdentifier functionAPIIdentifier) {
        Map<FunctionAPIIdentifier, List<String>>  rawCommands = ContentCommandExecutor.INSTANCE.getCommandFileSource().getCommandFiles();
        if (rawCommands.containsKey(functionAPIIdentifier)) {
            try {
                List<ParseResults<ContentRegistrationContext>> parseResults = ContentCommandExecutor.INSTANCE.parseFile(rawCommands.get(functionAPIIdentifier), this);
                ContentCommandExecutor.INSTANCE.execute(parseResults,this);
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
            }
        } else {
            throw new MissingCommandException("content registration file not found!", functionAPIIdentifier);
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

    @Override
    public ItemMaterial getItemMaterial() {
        return itemMaterial;
    }

    class MissingCommandException extends RuntimeException {
        MissingCommandException(String message, FunctionAPIIdentifier functionAPIIdentifier) {
            super("Command \"" + functionAPIIdentifier + "\" not found: " + message);
        }
    }
}
