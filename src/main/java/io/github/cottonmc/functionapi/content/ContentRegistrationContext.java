package io.github.cottonmc.functionapi.content;

import io.github.cottonmc.functionapi.content.templates.BlockTemplate;
import io.github.cottonmc.functionapi.content.templates.ItemTemplate;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

public interface ContentRegistrationContext {


    void addPostfix(String postfix);
    String getPostfix();

    enum ContentType{
        BLOCK,ITEM
    }

    void createElement(ContentType contentType, Identifier identifier);

    Map<Identifier,ContentType> getElements();

    Map<Integer, List<String>> getVariants();
    void addVariant(String variant, int variant_index);
    BlockTemplate getBlockTemplate();
    ItemTemplate getItemTemplate();

    void runCommand(Identifier identifier);

}
