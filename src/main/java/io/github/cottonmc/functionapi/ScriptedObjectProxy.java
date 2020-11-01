package io.github.cottonmc.functionapi;

import io.github.cottonmc.functionapi.api.script.ScriptedObject;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public interface ScriptedObjectProxy extends ScriptedObject{
    World getWorld();

    default Optional<Entity> getEntity(){
        return Optional.empty();
    }

    default Optional<BlockPos> getBlockPos(){
        return Optional.empty();
    }
}
