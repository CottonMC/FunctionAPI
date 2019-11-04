package io.github.cottonmc.functionapi.commands.inventory;

import com.mojang.brigadier.context.CommandContext;
import io.github.cottonmc.functionapi.commands.InventoryCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Direction;

public class DamageItem implements InventoryCommand.BiDirectionalCommand {

    public static final InventoryCommand.BiDirectionalCommand INSTANCE = new DamageItem();

    private DamageItem(){}

    @Override
    public int execute(CommandContext<ServerCommandSource> context, Direction source, Direction target) {
        return 1;
    }
}
