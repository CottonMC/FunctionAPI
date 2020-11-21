package io.github.cottonmc.functionapi.commands;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.cottonmc.functionapi.script.commandtemplates.BlockStateCommandTemplate;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.PosArgument;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;

public class BlockStateCommand extends BlockStateCommandTemplate<ServerCommandSource, PosArgument> {

    @Override
    protected ArgumentType<PosArgument> blockPosArgumentType() {
        return BlockPosArgumentType.blockPos();
    }

    @Override
    protected boolean permission(ServerCommandSource source) {
        return source.hasPermissionLevel(2);
    }

    @Override
    protected int handle(CommandContext<ServerCommandSource> context, String name, String value) {
        try {
            BlockPos target = BlockPosArgumentType.getBlockPos(context, "target");
            ServerWorld world = context.getSource().getWorld();
            BlockState blockState = world.getBlockState(target);
            Collection<Property<?>> properties = blockState.getProperties();
            for (Property property : properties) {
                    if (property.getName().equals(name)) {
                        property.parse(value).ifPresent(val -> {
                            world.setBlockState(target, blockState.with(property,(Comparable) val));
                        });
                    }
                }

        } catch (CommandSyntaxException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }


}
