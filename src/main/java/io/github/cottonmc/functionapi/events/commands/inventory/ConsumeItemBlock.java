package io.github.cottonmc.functionapi.events.commands.inventory;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.*;
import io.github.cottonmc.functionapi.api.commands.CommandWithArgument;
import net.minecraft.command.arguments.*;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.math.*;

public class ConsumeItemBlock implements CommandWithArgument<ServerCommandSource,Direction> {

    public static final CommandWithArgument INSTANCE = new ConsumeItemBlock();

    private ConsumeItemBlock(){}

    @Override
    public int execute(CommandContext<ServerCommandSource> context, Direction direction) {
        context.getSource().sendFeedback(new LiteralText("consuming item"),true);
        try{
            BlockPos sourceBlock = BlockPosArgumentType.getBlockPos(context, "sourceBlock");

            context.getSource().sendFeedback(new LiteralText(sourceBlock.toString()),true);

        }catch(CommandSyntaxException e){
            context.getSource().sendError(new LiteralText(e.getRawMessage().getString()));
            return 1;
        }

        return 0;
    }
}
