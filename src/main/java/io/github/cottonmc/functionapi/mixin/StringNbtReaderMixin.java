package io.github.cottonmc.functionapi.mixin;

import com.mojang.brigadier.exceptions.*;
import net.minecraft.entity.effect.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.util.registry.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(StringNbtReader.class)
public abstract class StringNbtReaderMixin{


    /**
     * change the nbt parser to be smarter
     * */
    @Inject(
    at = @At("RETURN"),
    method = "parseCompoundTag",
    cancellable = true)
    private void parseCompoundTag(CallbackInfoReturnable<CompoundTag> cir) throws CommandSyntaxException{
        CompoundTag returnValue = cir.getReturnValue();
        if(returnValue.getKeys().contains("ActiveEffects")){
            ListTag activeEffects = (ListTag)returnValue.get("ActiveEffects");
            for(int i = 0; i < activeEffects.size(); i++){
                Tag activeEffect = activeEffects.get(i);
                System.out.println(activeEffect);
                /**
                 * if the id was inputted as a string, parse it back to the internal raw id.
                 * */
                if(activeEffect instanceof CompoundTag){
                    Tag id = ((CompoundTag)activeEffect).get("Id");
                    //check if it's a string
                    if(id.getType() == 8){
                        String s = id.asString();
                        Identifier identifier = Identifier.tryParse(s);
                        if(identifier != null){
                            if(Registry.STATUS_EFFECT.containsId(identifier)){
                                StatusEffect statusEffect = Registry.STATUS_EFFECT.get(identifier);
                                int rawId = Registry.STATUS_EFFECT.getRawId(statusEffect);
                                ((CompoundTag)activeEffect).putByte("Id", (byte)rawId);
                            }else{
                                System.out.println(identifier + "is not in the status effect registry");
                            }
                        }else{
                            System.out.println("failed to parse id " + s);
                        }
                    }
                }
            }
            cir.setReturnValue(returnValue);
        }
    }

}
