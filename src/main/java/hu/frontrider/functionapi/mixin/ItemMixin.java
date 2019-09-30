package hu.frontrider.functionapi.mixin;

import hu.frontrider.functionapi.ScriptedObject;
import hu.frontrider.functionapi.ServerCommandSourceFactory;
import hu.frontrider.functionapi.events.EventManager;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
@Implements(@Interface(iface = ScriptedObject.class, prefix = "api_scripted$"))
public abstract class ItemMixin {

    private Identifier thisId = null;
    private EventManager useOnBlock;
    private EventManager finishUsing;
    private EventManager useOnEntiy;

    @Inject(
            at = @At("TAIL"),
            method = "useOnBlock",
            cancellable = true
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

        if(useOnBlock.hasEvents()){
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }


    @Inject(
            at = @At("TAIL"),
            method = "useOnEntity",
            cancellable = true
    )
    private void useOnEntity(ItemStack itemStack_1, PlayerEntity playerEntity, LivingEntity livingEntity_1, Hand hand_1, CallbackInfoReturnable<Boolean> cir){

        World world = livingEntity_1.world;
        if(useOnEntiy == null){
            useOnEntiy = new EventManager((ScriptedObject) this, "use_on_entity");
        }

        if (world instanceof ServerWorld) {
            ServerCommandSource commandContext = ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, livingEntity_1);
            useOnEntiy.fire(commandContext);
        }

        if(useOnEntiy.hasEvents()){
            cir.setReturnValue(true);
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
