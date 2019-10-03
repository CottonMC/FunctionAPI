package hu.frontrider.functionapi.mixin;


import hu.frontrider.functionapi.events.special.DispenserEvent;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(DispenserBlock.class)
public abstract class DispenserBlockMixin {


    private DispenserEvent event = new DispenserEvent();

    /**
     * if there
     */
    @Inject(at = @At("RETURN"), cancellable = true, method = "getBehaviorForItem")
    private void getBehaviourForItemMod(ItemStack itemStack_1, CallbackInfoReturnable<DispenserBehavior> cir) {
        if(cir.getReturnValue() == DispenserBehavior.NOOP){
            cir.setReturnValue(event);
        }
    }
}
