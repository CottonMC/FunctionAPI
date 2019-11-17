package io.github.cottonmc.functionapi.content;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;

public class ToolTagHelper {

    private Multimap<Tag<Item>,Item> toolTagmap = LinkedListMultimap.create();

    public static final ToolTagHelper INSTANCE = new ToolTagHelper();

    public void addTag(Item item, Tag<Item> tooltag) {
        toolTagmap.put(tooltag,item);
    }

    public void refresh(){

    }
}
