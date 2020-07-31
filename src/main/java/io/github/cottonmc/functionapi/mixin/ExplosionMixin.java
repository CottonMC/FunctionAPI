package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.*;
import io.github.cottonmc.functionapi.api.commands.*;
import io.github.cottonmc.functionapi.api.script.*;
import io.github.cottonmc.functionapi.events.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.server.command.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.*;
import net.minecraft.world.*;
import net.minecraft.world.explosion.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

@Mixin(Explosion.class)
public abstract class ExplosionMixin{

    @Shadow @Final private World world;
    @Shadow @Final private Entity entity;
    @Shadow @Final private double x;
    @Shadow @Final private double y;
    @Shadow @Final private double z;

    @Shadow public abstract List<BlockPos> getAffectedBlocks();

    @Inject(
    at = @At("HEAD"),
    method = "collectBlocksAndDamageEntities"
    )
    private void collectBlocksAndDamageEntities(CallbackInfo ci){
        if(!this.world.isClient){
            ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking((ScriptedObject)entity, "before/explode_start",ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld)world, entity));
            if(((CommandSourceExtension)serverCommandSource).isCancelled()){
                ci.cancel();
            }
            GlobalEventContainer.getInstance().executeEvent((ScriptedObject)entity, "explode_start", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld)world, entity));
        }
    }


    @Inject(
    at = @At("HEAD"),
    method = "affectWorld",
    cancellable = true)
    private void affectWorld(boolean bl, CallbackInfo ci){
        if(!this.world.isClient){
            ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking((ScriptedObject)entity, "before/explode_blocks",ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld)world, entity));
            if(((CommandSourceExtension)serverCommandSource).isCancelled()){
                ci.cancel();

                for(BlockPos affectedBlock : getAffectedBlocks()){
                    world.getServer().execute(()->{
                        world.getServer().getPlayerManager().sendToAround(null, affectedBlock.getX(), affectedBlock.getY(), affectedBlock.getZ(), 64.0D, world.getRegistryKey(), new BlockUpdateS2CPacket(world,affectedBlock));
                    });
                }
            }

            GlobalEventContainer.getInstance().executeEvent((ScriptedObject)entity, "explode_blocks", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld)world, entity));
        }
    }
}
