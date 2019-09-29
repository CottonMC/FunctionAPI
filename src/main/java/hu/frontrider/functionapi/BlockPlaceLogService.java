package hu.frontrider.functionapi;

import hu.frontrider.functionapi.events.runners.service.EventHandler;
import hu.frontrider.functionapi.events.runners.service.ServiceRunner;
import net.minecraft.block.Block;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class BlockPlaceLogService implements EventHandler {
    @Override
    public void fire(ServerCommandSource serverCommandSource) {
        ServerWorld world = serverCommandSource.getWorld();
        BlockPos blockPos = new BlockPos(serverCommandSource.getPosition());
        Block block = world.getBlockState(blockPos).getBlock();

        System.out.println(block.getName().asString());
    }

    @Override
    public boolean matchesEvent(Identifier identifier) {
        return identifier.getPath().startsWith("block-placed-");
    }

}
