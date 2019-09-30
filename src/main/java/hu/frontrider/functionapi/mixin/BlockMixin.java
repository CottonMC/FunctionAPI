package hu.frontrider.functionapi.mixin;

import hu.frontrider.functionapi.ServerCommandSourceFactory;
import hu.frontrider.functionapi.events.EventManager;
import hu.frontrider.functionapi.ScriptedObject;
import hu.frontrider.functionapi.events.GlobalEventContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * adds scripting functionality to the block class.
 */
@Mixin(Block.class)
@Implements(@Interface(iface = ScriptedObject.class, prefix = "api_scripted$"))
public abstract class BlockMixin {

    private Identifier thisId = null;
    private EventManager neighbourUpdate;
    private EventManager place;
    private EventManager brake;
    private EventManager exploded;
    private EventManager activate;
    private EventManager steppedOn;
    private EventManager entityCollided;
    private EventManager entityLanded;
    private EventManager projectileHit;

    @Inject(at = @At("TAIL"), method = "neighborUpdate")
    private void neighborUpdate(BlockState blockState_1, World world_1, BlockPos blockPos_1, Block block_1, BlockPos blockPos_2, boolean boolean_1, CallbackInfo ci) {

        if (world_1 instanceof ServerWorld) {
            if (neighbourUpdate == null) {
                neighbourUpdate = new EventManager((ScriptedObject) this, "neighbour_update");
                neighbourUpdate.serverInit(world_1.getServer());
            }
            ServerCommandSource commandContext = ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1);
            neighbourUpdate.fire(commandContext);
        }
    }

    @Inject(
            at = @At("TAIL"),
            method = "onPlaced"
    )
    private void place(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1, ItemStack itemStack_1, CallbackInfo ci) {
        if (world_1 instanceof ServerWorld) {
            if (place == null) {
                place = new EventManager((ScriptedObject) this, "placed");
                place.serverInit(world_1.getServer());

            }
            ServerCommandSource commandContext = ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1, livingEntity_1);
            place.fire(commandContext);
        }
    }

    @Inject(
            at = @At("TAIL"),
            method = "onBroken"
    )
    private void broken(IWorld world_1, BlockPos blockPos_1, BlockState blockState_1, CallbackInfo ci) {

        if (world_1 instanceof ServerWorld) {
            if (brake == null) {
                brake = new EventManager((ScriptedObject) this, "broken");
                entityLanded.serverInit(((ServerWorld) world_1).getServer());

            }
            ServerCommandSource commandContext = ServerCommandSourceFactory.INSTANCE.create(((ServerWorld) world_1).getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1);
            brake.fire(commandContext);
        }
    }

    @Inject(
            at = @At("TAIL"),
            method = "onDestroyedByExplosion"
    )
    private void exploded(World world_1, BlockPos blockPos_1, Explosion explosion_1, CallbackInfo ci) {

        if (world_1 instanceof ServerWorld) {
            if (exploded == null) {
                exploded = new EventManager((ScriptedObject) this, "exploded");
                entityLanded.serverInit(world_1.getServer());

            }
            ServerCommandSource commandContext = ServerCommandSourceFactory.INSTANCE.create(((ServerWorld) world_1).getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1);
            exploded.fire(commandContext);
        }
    }

    @Inject(
            at = @At("TAIL"),
            method = "activate"
    )
    private void activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1, CallbackInfoReturnable<Boolean> cir) {

        if (world_1 instanceof ServerWorld) {
            if (activate == null) {
                activate = new EventManager((ScriptedObject) this, "activate");
                activate.serverInit(world_1.getServer());
            }
            ServerCommandSource commandContext = ServerCommandSourceFactory.INSTANCE.create(((ServerWorld) world_1).getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1,playerEntity_1);
            activate.fire(commandContext);
        }
    }

    @Inject(at = @At("TAIL"), method = "onSteppedOn")
    private void onSteppedOn(World world_1, BlockPos blockPos_1, Entity entity_1, CallbackInfo ci) {

        if (world_1 instanceof ServerWorld) {
            if (steppedOn == null) {
                steppedOn = new EventManager((ScriptedObject) this, "stepped_on");
                steppedOn.serverInit(world_1.getServer());

            }
            ServerCommandSource commandContext = ServerCommandSourceFactory.INSTANCE.create(((ServerWorld) world_1).getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1);
            steppedOn.fire(commandContext);
        }
    }

    @Inject(at = @At("TAIL"), method = "onEntityCollision")
    private void onEntityCollision(BlockState blockState_1, World world_1, BlockPos blockPos_1, Entity entity_1, CallbackInfo ci) {

        if (world_1 instanceof ServerWorld) {
            if (entityCollided == null) {
                entityCollided = new EventManager((ScriptedObject) this, "entity_collided");
                entityCollided.serverInit(world_1.getServer());

            }
            ServerCommandSource commandContext = ServerCommandSourceFactory.INSTANCE.create(((ServerWorld) world_1).getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1);
            entityCollided.fire(commandContext);
        }
    }


    @Inject(
            at = @At("TAIL"),
            method = "onLandedUpon"
    )
    private void onLandedUpon(World world_1, BlockPos blockPos_1, Entity entity_1, float float_1, CallbackInfo ci) {

        if (world_1 instanceof ServerWorld) {
            if (entityLanded == null) {
                entityLanded = new EventManager((ScriptedObject) this, "entity_landed");
                entityLanded.serverInit(world_1.getServer());
            }
            ServerCommandSource commandContext = ServerCommandSourceFactory.INSTANCE.create(((ServerWorld) world_1).getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1, entity_1);
            entityLanded.fire(commandContext);
        }
    }

    @Inject(
            at = @At("TAIL"),
            method = "onProjectileHit"
    )
    private void onProjectileHit(World world_1, BlockState blockState_1, BlockHitResult blockHitResult_1, Entity entity_1, CallbackInfo ci) {

        if (world_1 instanceof ServerWorld) {
            if (projectileHit == null) {
                projectileHit = new EventManager((ScriptedObject) this, "projectile_hit");
                projectileHit.serverInit(world_1.getServer());

            }
            ServerCommandSource commandContext = ServerCommandSourceFactory.INSTANCE.create(((ServerWorld) world_1).getServer(), (ServerWorld) world_1, (Block) (Object) this, blockHitResult_1.getBlockPos(), entity_1);
            projectileHit.fire(commandContext);
        }
    }

    /**
     * Dynamically gets the id of this block instance.
     */
    public Identifier api_scripted$getID() {
        if (thisId == null) {
            thisId = Registry.BLOCK.getId((Block) (Object) this);
        }
        return thisId;
    }

    public String api_scripted$getType() {
        return "block";
    }
}
