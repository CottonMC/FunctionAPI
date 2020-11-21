package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.*;
import io.github.cottonmc.functionapi.api.commands.*;
import io.github.cottonmc.functionapi.api.script.*;
import io.github.cottonmc.functionapi.events.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.*;
import net.minecraft.server.command.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.explosion.*;
import org.jetbrains.annotations.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(Explosion.class)
public abstract class ExplosionMixin{

    @Shadow
    @Final
    private World world;
    @Shadow
    @Final
    private Entity entity;


    @Shadow
    @Final
    private DamageSource damageSource;

    @Shadow
    @Final
    private double x;

    @Shadow
    @Final
    private double z;

    @Shadow
    @Final
    private double y;

    @Shadow public @Nullable abstract LivingEntity getCausingEntity();

    @Inject(
    at = @At("HEAD"),
    method = "collectBlocksAndDamageEntities",
    cancellable = true
    )
    private void collectBlocksAndDamageEntities(CallbackInfo ci){
        if(!this.world.isClient){
            Entity entity = getCausingEntity();
            if(entity == null){
                BlockPos blockPos = new BlockPos((int)x, (int)y, (int)z);
                Block block = world.getBlockState(blockPos).getBlock();
                ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking((ScriptedObject)block, "before/explode_start", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)world, block, blockPos));
                if(((CommandSourceExtension)serverCommandSource).isCancelled()){
                    ci.cancel();
                }
                GlobalEventContainer.getInstance().executeEvent((ScriptedObject)block, "explode_start", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)world, block, blockPos));
            }else{
                ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking((ScriptedObject)entity, "before/explode_start", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)world, entity));
                if(((CommandSourceExtension)serverCommandSource).isCancelled()){
                    ci.cancel();
                }
                GlobalEventContainer.getInstance().executeEvent((ScriptedObject)entity, "explode_start", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)world, entity));
            }
        }
    }


    @Inject(
    at = @At("HEAD"),
    method = "affectWorld",
    cancellable = true)
    private void affectWorld(boolean bl, CallbackInfo ci){
        if(!this.world.isClient){
            Entity entity = getCausingEntity();
            if(entity == null){
                BlockPos blockPos = new BlockPos((int)x, (int)y, (int)z);
                Block block = world.getBlockState(blockPos).getBlock();
                ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking((ScriptedObject)block, "before/explode_blocks", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)world, block, blockPos));
                if(((CommandSourceExtension)serverCommandSource).isCancelled()){
                    ci.cancel();
                }
                GlobalEventContainer.getInstance().executeEvent((ScriptedObject)block, "explode_blocks", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)world, block, blockPos));
            }else{
                ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking((ScriptedObject)entity, "before/explode_blocks", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)world, entity));
                if(((CommandSourceExtension)serverCommandSource).isCancelled()){
                    ci.cancel();
                }
                GlobalEventContainer.getInstance().executeEvent((ScriptedObject)entity, "explode_blocks", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)world, entity));
            }

        }
    }
}
