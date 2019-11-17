package io.github.cottonmc.functionapi.api.content;

import io.github.cottonmc.functionapi.api.IncludeCommandRunner;
import io.github.cottonmc.functionapi.api.script.CommandRunner;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ContentRegistrationContext extends IncludeCommandRunner {

    void addPostfix(String postfix);
    String getPostfix();

    enum ContentType{
        BLOCK,ITEM
    }

    void createElement(ContentType contentType, FunctionAPIIdentifier functionAPIIdentifier);

    Map<FunctionAPIIdentifier,ContentType> getElements();

    Map<Integer, List<String>> getVariants();
    void addVariant(String variant, int variant_index);
    BlockTemplate getBlockTemplate();
    ItemTemplate getItemTemplate();

    ItemMaterial getItemMaterial();

    void runCommand(FunctionAPIIdentifier functionAPIIdentifier);

}
