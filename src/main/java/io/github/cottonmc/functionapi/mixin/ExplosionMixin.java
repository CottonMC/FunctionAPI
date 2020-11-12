package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.*;
import io.github.cottonmc.functionapi.api.commands.*;
import io.github.cottonmc.functionapi.api.script.*;
import io.github.cottonmc.functionapi.events.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.server.command.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.*;
import net.minecraft.world.*;
import net.minecraft.world.explosion.*;
import org.apache.logging.log4j.core.jmx.*;
import org.jetbrains.annotations.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

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

    @Inject(
    at = @At("HEAD"),
    method = "collectBlocksAndDamageEntities"
    )
    private void collectBlocksAndDamageEntities(CallbackInfo ci){
        if(!this.world.isClient){
            if(entity == null){
                BlockPos blockPos = new BlockPos((int)x, (int)y, (int)z);
                Block source = world.getBlockState(blockPos).getBlock();
                ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking((ScriptedObject)source, "before/explode_start", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)world, source, blockPos));
                if(((CommandSourceExtension)serverCommandSource).isCancelled()){
                    ci.cancel();
                }
                GlobalEventContainer.getInstance().executeEvent((ScriptedObject)source, "explode_start", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)world, source, blockPos));
            }else{
                Entity source = entity;
                ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking((ScriptedObject)source, "before/explode_start", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)world, source));
                if(((CommandSourceExtension)serverCommandSource).isCancelled()){
                    ci.cancel();
                }
                GlobalEventContainer.getInstance().executeEvent((ScriptedObject)source, "explode_start", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)world, source));
            }
        }
    }


    @Inject(
    at = @At("HEAD"),
    method = "affectWorld",
    cancellable = true)
    private void affectWorld(boolean bl, CallbackInfo ci){
        if(!this.world.isClient){
            if(entity == null){
                BlockPos blockPos = new BlockPos((int)x, (int)y, (int)z);
                Block source = world.getBlockState(blockPos).getBlock();
                ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking((ScriptedObject)source, "before/explode_blocks", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)world, source, blockPos));
                if(((CommandSourceExtension)serverCommandSource).isCancelled()){
                    ci.cancel();
                }
                GlobalEventContainer.getInstance().executeEvent((ScriptedObject)source, "explode_blocks", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)world, source, blockPos));
            }else{
                Entity source = entity;
                ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking((ScriptedObject)source, "before/explode_blocks", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)world, source));
                if(((CommandSourceExtension)serverCommandSource).isCancelled()){
                    ci.cancel();
                }
                GlobalEventContainer.getInstance().executeEvent((ScriptedObject)source, "explode_blocks", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)world, source));
            }

        }
    }
}
