package io.github.cottonmc.functionapi.events.commands.inventory;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;

public class DamageItemEntity implements Command<ServerCommandSource> {

    public static final Command<ServerCommandSource>INSTANCE = new DamageItemEntity();

    private DamageItemEntity(){}


    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        return 1;
    }
}
