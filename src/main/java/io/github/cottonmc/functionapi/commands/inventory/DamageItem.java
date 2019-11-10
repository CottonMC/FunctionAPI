package io.github.cottonmc.functionapi.commands.inventory;

import com.mojang.brigadier.context.CommandContext;
import io.github.cottonmc.functionapi.api.BiDirectionalCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Direction;

public class DamageItem implements BiDirectionalCommand {

    public static final BiDirectionalCommand INSTANCE = new DamageItem();

    private DamageItem(){}

    @Override
    public int execute(CommandContext<ServerCommandSource> context, Direction source, Direction target) {
        return 1;
    }
}
