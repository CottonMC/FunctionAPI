package io.github.cottonmc.functionapi;

import blue.endless.jankson.*;
import io.github.cottonmc.cotton.config.annotations.*;

import java.util.*;

@ConfigFile(name=FunctionAPI.MODID)
public class Config{

    @Comment(value="Insert the event ids that you want to disable.")
    public List<String> blacklistedEvents =Collections.emptyList();
}