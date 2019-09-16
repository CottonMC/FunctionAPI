package hu.frontrider.functionapi.mixin;

import hu.frontrider.functionapi.events.EventManager;
import hu.frontrider.functionapi.ScriptedObject;
import hu.frontrider.functionapi.ServerCommandSourceFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
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
    private EventManager useOnBlock = new EventManager((ScriptedObject) this, "use-on-block");
    private EventManager finishUsing = new EventManager((ScriptedObject) this, "finish-using");

    @Inject(
            at = @At("TAIL"),
            method = "useOnBlock"
    )
    private void useOnBlock(ItemUsageContext itemUsageContext, CallbackInfoReturnable<ActionResult> cir){
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
        if (world instanceof ServerWorld) {
            ServerCommandSource commandContext = ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, livingEntity_1);
            finishUsing.fire(commandContext);
        }
    }

    /**
     * Dynamically gets the id of this block instance.
     */
    public Identifier api_scripted$getID() {
        if (thisId == null) {
            thisId = Registry.ITEM.getId((Item) (Object) this);
        }
        return thisId;
    }

    /**
     * called when the block should be reloaded.
     */
    public void api_scripted$markDirty() {
        useOnBlock.markDirty();
        finishUsing.markDirty();
    }

    public String api_scripted$getType() {
        return "item";
    }
}
