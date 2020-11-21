package io.github.cottonmc.functionapi.data;

public class BlockMaterialData{
    float hardness = 1.0f;
    //sets hardness and blast resistance
    boolean indestructible = false;
    //light level,capped at 15
    int luminance = 0;
    //weather ot not redstone connects to this
    boolean emits_redstone=false;

    boolean tool_Required = false;
    //false if it's transparent
    boolean opaque = true;
}
