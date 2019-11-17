package io.github.cottonmc.functionapi.api;

public interface ExtendedBlockProperties {
    /**
     * used as an additional way to determine weather or not this block is stairs.
     * */
    default boolean isBlockStairs(){
        return false;
    }
}
