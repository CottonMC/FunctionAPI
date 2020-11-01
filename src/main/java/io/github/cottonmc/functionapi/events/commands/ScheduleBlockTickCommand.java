package io.github.cottonmc.functionapi.events.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.block.Block;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;

public class ScheduleBlockTickCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("scheduleblocktick")
                        .then(CommandManager.argument("target", BlockPosArgumentType.blockPos())
                                .then(CommandManager.argument("ticks", IntegerArgumentType.integer())
                                        .executes(context -> {
                                            BlockPos target = BlockPosArgumentType.getBlockPos(context, "target");
                                            int ticks = IntegerArgumentType.getInteger(context, "ticks");

                                            Block block = context.getSource().getWorld().getBlockState(target).getBlock();
                                            context.getSource().getWorld().getBlockTickScheduler().schedule(target,block,ticks);

                                            return 1;
                                        })


                                ))
        );
    }
}
