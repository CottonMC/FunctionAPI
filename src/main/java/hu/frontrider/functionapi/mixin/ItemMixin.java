package hu.frontrider.functionapi.mixin;

import hu.frontrider.functionapi.events.EventManager;
import hu.frontrider.functionapi.ScriptedObject;
import hu.frontrider.functionapi.ServerCommandSourceFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
@Implements(@Interface(iface = ScriptedObject.class, prefix = "api_scripted$"))
public abstract class ItemMixin {

    private Identifier thisId = null;
    private EventManager useOnBlock;
    private EventManager finishUsing;
    private EventManager use = new EventManager((ScriptedObject) this, "use");


    @Inject(
            at = @At("TAIL"),
            method = "useOnBlock"
    )
    private void useOnBlock(ItemUsageContext itemUsageContext, CallbackInfoReturnable<ActionResult> cir){

        if(useOnBlock == null){
            useOnBlock = new EventManager((ScriptedObject) this, "use_on_block");
        }

        World world = itemUsageContext.getWorld();
        BlockPos blockPos = itemUsageContext.getBlockPos();
        if (world instanceof ServerWorld) {
            ServerCommandSource commandContext = ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, world.getBlockState(blockPos).getBlock(), blockPos,itemUsageContext.getPlayer());
            useOnBlock.fire(commandContext);
        }
    }

    @Inject(
            at = @At("TAIL"),
            method = "finishUsing"
    )
    private void finishUsing(ItemStack itemStack_1, World world, LivingEntity livingEntity_1, CallbackInfoReturnable<ItemStack> cir){
        if(finishUsing == null){
            finishUsing = new EventManager((ScriptedObject) this, "finish_using");
        }
        if (world instanceof ServerWorld) {
            ServerCommandSource commandContext = ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, livingEntity_1);
            finishUsing.fire(commandContext);
        }
    }
    @Inject(
            at = @At("TAIL"),
            method = "use"
    )
    private void use(World world, PlayerEntity playerEntity, Hand hand_1, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
        if(use == null){
            use = new EventManager((ScriptedObject) this, "use");
        }

        if (world instanceof ServerWorld) {
            ServerCommandSource commandContext = ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, playerEntity);
            use.fire(commandContext);
        }
    }

    /**
     * Dynamically gets the id of this item instance.
     */
    public Identifier api_scripted$getID() {
        if (thisId == null) {
            thisId = Registry.ITEM.getId((Item) (Object) this);
        }
        return thisId;
    }

    public String api_scripted$getType() {
        return "item";
    }
}
