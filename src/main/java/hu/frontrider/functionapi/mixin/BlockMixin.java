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
    private EventManager place;
    private EventManager brake;
    private EventManager exploded;
    private EventManager activate;
    private EventManager steppedOn;
    private EventManager entityCollided;
    private EventManager entityLanded;
    private EventManager projectileHit;
    private EventManager neighbourUpdate;

    @Inject(at = @At("TAIL"), method = "neighborUpdate")
    private void neighborUpdate(BlockState blockState_1, World world_1, BlockPos blockPos_1, Block block_1, BlockPos blockPos_2, boolean boolean_1, CallbackInfo ci) {
        neighbourUpdate = EventManager.execute(neighbourUpdate, (ScriptedObject) this, "neighbour_update", world_1, () -> ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1));
    }

    @Inject(
            at = @At("TAIL"),
            method = "onPlaced"
    )
    private void place(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1, ItemStack itemStack_1, CallbackInfo ci) {
        place = EventManager.execute(place, (ScriptedObject) this, "placed", world_1, () -> ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1, livingEntity_1));
    }

    @Inject(
            at = @At("TAIL"),
            method = "onBroken"
    )
    private void broken(IWorld world_1, BlockPos blockPos_1, BlockState blockState_1, CallbackInfo ci) {
        brake = EventManager.execute(brake, (ScriptedObject) this, "broken", world_1, () -> ServerCommandSourceFactory.INSTANCE.create(((ServerWorld) world_1).getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1));
    }

    @Inject(
            at = @At("TAIL"),
            method = "onDestroyedByExplosion"
    )
    private void exploded(World world_1, BlockPos blockPos_1, Explosion explosion_1, CallbackInfo ci) {
        exploded = EventManager.execute(exploded, (ScriptedObject) this, "exploded", world_1, () -> ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1));
    }

    @Inject(
            at = @At("TAIL"),
            method = "activate"
    )
    private void activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1, CallbackInfoReturnable<Boolean> cir) {
        activate = EventManager.execute(activate, (ScriptedObject) this, "activate", world_1, () -> ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1, playerEntity_1));
    }

    @Inject(at = @At("TAIL"), method = "onSteppedOn")
    private void onSteppedOn(World world_1, BlockPos blockPos_1, Entity entity_1, CallbackInfo ci) {
        steppedOn = EventManager.execute(steppedOn, (ScriptedObject) this, "stepped_on", world_1, () -> ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1, entity_1));
    }

    @Inject(at = @At("TAIL"), method = "onEntityCollision")
    private void onEntityCollision(BlockState blockState_1, World world_1, BlockPos blockPos_1, Entity entity_1, CallbackInfo ci) {
        entityCollided = EventManager.execute(entityCollided, (ScriptedObject) this, "entity_collided", world_1, () -> ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1, entity_1));
    }


    @Inject(
            at = @At("TAIL"),
            method = "onLandedUpon"
    )
    private void onLandedUpon(World world_1, BlockPos blockPos_1, Entity entity_1, float float_1, CallbackInfo ci) {
        entityLanded = EventManager.execute(entityLanded, (ScriptedObject) this, "entity_landed", world_1, () -> ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1, entity_1));
    }

    @Inject(
            at = @At("TAIL"),
            method = "onProjectileHit"
    )
    private void onProjectileHit(World world_1, BlockState blockState_1, BlockHitResult blockHitResult_1, Entity entity_1, CallbackInfo ci) {
        projectileHit = EventManager.execute(projectileHit, (ScriptedObject) this, "projectile_hit", world_1, () -> ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockHitResult_1.getBlockPos(), entity_1));
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
