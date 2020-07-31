package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.api.commands.CommandSourceExtension;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerCommandSource.class)
public abstract class ServerCommandSourceMixin implements CommandSourceExtension {

    private boolean cancelled = false;
    @Override
    public boolean isCancelled() {
        return cancelled;
    }


    @Override
    public void cancel() {
        cancelled = true;
    }

    @Override
    public void setCancelled(boolean b){
        cancelled = b;
    }
}
