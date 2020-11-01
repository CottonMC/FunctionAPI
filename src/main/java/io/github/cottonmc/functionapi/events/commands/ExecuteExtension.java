package io.github.cottonmc.functionapi.events.commands;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.cottonmc.functionapi.script.commandtemplates.ExecuteExtensionTemplate;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.PosArgument;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class ExecuteExtension extends ExecuteExtensionTemplate<ServerCommandSource, PosArgument> {
    @Override
    protected ArgumentType<PosArgument> blockPosArgumentType() {
        return BlockPosArgumentType.blockPos();
    }

    @Override
    protected int execute(CommandContext<ServerCommandSource> context, String name, String value) {
        try {
            BlockPos position = BlockPosArgumentType.getBlockPos(context, "position");
            BlockState blockState = context.getSource().getWorld().getBlockState(position);
            for (Property<?> property : blockState.getProperties()) {
                if (property.getName().equals(name)) {
                    Optional<?> value1 = property.parse(value);
                    if (value1.isPresent()) {
                        Object propertyValue = value1.get();
                        Comparable<Object> knownValue = (Comparable<Object>) blockState.get(property);
                        return knownValue.compareTo(propertyValue);
                    }
                }
            }
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
