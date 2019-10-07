package io.github.cottonmc.functionapi.events.special;

import io.github.cottonmc.functionapi.ScriptedObject;
import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import io.github.cottonmc.functionapi.events.EventManager;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import net.minecraft.block.BlockState;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DispenserEvent implements DispenserBehavior {

    @Override
    public ItemStack dispense(BlockPointer blockPointer, ItemStack itemStack) {
        runEvent(blockPointer, itemStack);
        return itemStack;
    }

    public boolean hasEventForItem(ItemStack itemStack,MinecraftServer server) {

        EventManager dispense = new EventManager((ScriptedObject) itemStack.getItem(), "dispense", true);
        EventManager eventManager = GlobalEventContainer.getInstance().addIfMissing(dispense);

        return eventManager.hasEvents(server);
    }

    private boolean runEvent(BlockPointer blockPointer, ItemStack itemStack) {
        boolean successful = false;
        BlockPos blockPos = blockPointer.getBlockPos();
        World world = blockPointer.getWorld();
        BlockState blockState = world.getBlockState(blockPos);

        if (!blockState.isAir()) {
            return false;
        }

        MinecraftServer server = world.getServer();
        ServerCommandSource serverCommandSource = ServerCommandSourceFactory.INSTANCE.create(server, (ServerWorld) world, blockState.getBlock(), blockPos);

        EventManager dispense = new EventManager((ScriptedObject) itemStack.getItem(), "dispense", true);

        EventManager eventManager = GlobalEventContainer.getInstance().addIfMissing(dispense);
        if (eventManager.hasEvents()) {
            //if the item is damageable, then damage it.
            if (itemStack.getItem().isDamageable()) {
                itemStack.setDamage(itemStack.getDamage() + 1);
            }
            //if not, "consume" it
            else {
                itemStack.decrement(1);
            }
            successful = true;
            eventManager.fire(serverCommandSource);
        }
        return successful;
    }

}
