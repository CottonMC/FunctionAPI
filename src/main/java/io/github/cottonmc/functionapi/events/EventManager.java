package io.github.cottonmc.functionapi.events;

import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.api.script.CommandRunner;
import io.github.cottonmc.functionapi.script.FunctionManager;
import io.github.cottonmc.functionapi.api.script.ScriptedObject;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.IWorld;

import java.util.function.Supplier;

/**
 * Handles an event
 */
public class EventManager extends FunctionManager<ServerCommandSource,MinecraftServer> {

    public EventManager(ScriptedObject target, String eventName, boolean isSingleton) {
        super(createID(target, eventName), isSingleton);
    }

    public EventManager(ScriptedObject target, String eventName) {
        super(createID(target, eventName), false);
    }

    public EventManager(FunctionAPIIdentifier callbackID) {
        super(callbackID);
    }

    @Override
    protected CommandRunner<ServerCommandSource,MinecraftServer> getCommandRunner(FunctionAPIIdentifier identifier) {
        if(identifier instanceof  Identifier)
        return new ServerCommandRunner((Identifier) identifier);
        else{
            return new ServerCommandRunner(new Identifier(identifier.getNamespace(),identifier.getPath()));

        }
    }

    public static FunctionAPIIdentifier createID(ScriptedObject target, String eventName) {
        return (FunctionAPIIdentifier) new Identifier(target.getEventID().getNamespace(), target.getEventType() + "/" + target.getEventID().getPath() + "/" + eventName);
    }


    public void serverInit(MinecraftServer server) {
        if (!initialized) {
            FunctionAPIIdentifier id = getID();
            Identifier identifier = new Identifier(id.getNamespace(),id.getPath()+"_create_callback");
            GlobalEventContainer.getInstance().initCallback((FunctionAPIIdentifier) identifier, server);
            initialized = true;
        }
    }

    @Override
    protected void runFunction(ServerCommandSource commandContext) {
        commandContext.getMinecraftServer().execute(()->{
            commandRunner.fire(commandContext);
        });

    }

    @Override
    protected void runFunctionBlocking(ServerCommandSource commandContext) {
        commandRunner.fire(commandContext);
    }

    public static EventManager execute(EventManager manager, ScriptedObject thiz, String name, IWorld world_1, Supplier<ServerCommandSource> serverCommandSourceSupplier) {
        if (world_1 instanceof ServerWorld) {
            if (manager == null) {
                manager = new EventManager(thiz, name);
                manager.serverInit(((ServerWorld) world_1).getServer());
            }
            manager.fire(serverCommandSourceSupplier.get());
            return manager;
        }
        return null;
    }

    public static EventManager executeBlocking(EventManager manager, ScriptedObject thiz, String name, IWorld world_1, Supplier<ServerCommandSource> serverCommandSourceSupplier) {
        if (world_1 instanceof ServerWorld) {
            if (manager == null) {
                manager = new EventManager(thiz, name);
                manager.serverInit(((ServerWorld) world_1).getServer());
            }
            manager.fireBlocking(serverCommandSourceSupplier.get());
            return manager;
        }
        return null;
    }
}
