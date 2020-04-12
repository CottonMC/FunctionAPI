package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import io.github.cottonmc.functionapi.api.commands.*;
import io.github.cottonmc.functionapi.api.script.ScriptedObject;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.*;
import net.minecraft.server.command.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.explosion.*;
import net.minecraft.world.explosion.Explosion.*;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiFunction;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World{

    protected ServerWorldMixin(LevelProperties levelProperties, DimensionType dimensionType, BiFunction<World, Dimension, ChunkManager> biFunction, Profiler profiler, boolean bl){
        super(levelProperties, dimensionType, biFunction, profiler, bl);
    }

    @Inject(
    at = @At("TAIL"),
    method = "spawnEntity"
    )
    private void spawn(Entity entity, CallbackInfoReturnable<Boolean> cir){
        GlobalEventContainer.getInstance().executeEvent((ScriptedObject)entity, "spawn", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld)(Object)this, entity));
    }

    @Inject(
    at = @At("HEAD"),
    method = "createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;",
    cancellable = true)
    private void explosion(Entity entity, DamageSource damageSource, double d, double e, double f, float g, boolean bl, DestructionType destructionType, CallbackInfoReturnable<Explosion> cir){
        ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking((ScriptedObject)entity, "before/explode", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld)(Object)this, entity));

        if(((CommandSourceExtension)serverCommandSource).isCancelled()){
            cir.cancel();
        }
        GlobalEventContainer.getInstance().executeEvent((ScriptedObject)entity, "explode", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld)(Object)this, entity));

    }
}
