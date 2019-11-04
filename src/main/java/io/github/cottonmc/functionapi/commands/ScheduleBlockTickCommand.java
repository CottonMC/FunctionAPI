package io.github.cottonmc.functionapi.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.block.Block;
import net.minecraft.command.arguments.BlockPosArgumentType;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.command.arguments.Vec3ArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Collection;

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
