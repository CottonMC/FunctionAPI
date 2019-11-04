package io.github.cottonmc.functionapi.api;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;

public interface CommandSourceExtension {

   boolean isCancelled();

   void cancel();
}
