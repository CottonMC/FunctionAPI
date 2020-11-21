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
import net.minecraft.util.profiler.*;
import net.minecraft.util.registry.*;
import net.minecraft.world.*;
import net.minecraft.world.dimension.*;
import net.minecraft.world.explosion.*;
import net.minecraft.world.explosion.Explosion.*;
import org.jetbrains.annotations.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.function.*;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World{


    protected ServerWorldMixin(MutableWorldProperties mutableWorldProperties, RegistryKey<World> registryKey, DimensionType dimensionType, Supplier<Profiler> supplier, boolean bl, boolean bl2, long l){
        super(mutableWorldProperties, registryKey, dimensionType, supplier, bl, bl2, l);
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
    method = "createExplosion",
    cancellable = true
    )
    private void createExplosion(@Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionBehavior explosionBehavior, double d, double e, double f, float g, boolean bl, DestructionType destructionType, CallbackInfoReturnable<Explosion> cir){
        if(entity == null){
            BlockPos blockPos = new BlockPos((int)d, (int)e, (int)f);
            Block block = getBlockState(blockPos).getBlock();
            ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking((ScriptedObject)block, "before/explode", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)(Object)this, block, blockPos));
            if(((CommandSourceExtension)serverCommandSource).isCancelled()){
                Explosion explosion = new Explosion((World)(Object)this, entity, damageSource, explosionBehavior, d, e, f, g, bl, DestructionType.NONE);
                explosion.clearAffectedBlocks();
                cir.setReturnValue(explosion);
            }
            GlobalEventContainer.getInstance().executeEvent((ScriptedObject)block, "explode", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)(Object)this, block, blockPos));
        }else{
            ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking((ScriptedObject)entity, "before/explode", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)(Object)this, entity));
            if(((CommandSourceExtension)serverCommandSource).isCancelled()){
                Explosion explosion = new Explosion((World)(Object)this, entity, damageSource, explosionBehavior, d, e, f, g, bl, DestructionType.NONE);
                explosion.clearAffectedBlocks();
                cir.setReturnValue(explosion);
            }
            GlobalEventContainer.getInstance().executeEvent((ScriptedObject)entity, "explode", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)(Object)this, entity));
        }
    }



}
