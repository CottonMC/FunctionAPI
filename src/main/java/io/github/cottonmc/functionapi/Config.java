package io.github.cottonmc.functionapi;

import blue.endless.jankson.*;
import io.github.cottonmc.cotton.config.annotations.*;

import java.util.*;

@ConfigFile(name=FunctionAPI.MODID)
public class Config{
    @Comment(value="Will allow the data command to modify player information. it's not allowed in vanilla by default, so it's disabled here as well. Use at your own risk.")
    public boolean enablePlayerDataModification = false;

    @Comment(value="Insert the event ids that you want to disable.")
    public List<String> blacklistedEvents =Collections.emptyList();
}