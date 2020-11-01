package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.*;
import io.github.cottonmc.functionapi.api.commands.*;
import io.github.cottonmc.functionapi.api.script.*;
import io.github.cottonmc.functionapi.events.*;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.*;
import net.minecraft.server.command.*;
import net.minecraft.server.world.*;
import net.minecraft.util.profiler.*;
import net.minecraft.util.registry.*;
import net.minecraft.world.*;
import net.minecraft.world.dimension.*;
import net.minecraft.world.explosion.*;
import net.minecraft.world.explosion.Explosion.*;
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
    method = "createExplosion(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;",
    cancellable = true)
    private void explosion(Entity entity, DamageSource damageSource, ExplosionBehavior explosionBehavior, double d, double e, double f, float g, boolean bl, DestructionType destructionType, CallbackInfoReturnable<Explosion> cir){
        ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking((ScriptedObject)entity, "before/explode", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld)(Object)this, entity));

        if(((CommandSourceExtension)serverCommandSource).isCancelled()){
            cir.cancel();
        }
        GlobalEventContainer.getInstance().executeEvent((ScriptedObject)entity, "explode", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld)(Object)this, entity));

    }
}
