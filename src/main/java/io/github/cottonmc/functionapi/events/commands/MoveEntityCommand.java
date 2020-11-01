package io.github.cottonmc.functionapi.events.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Vec3d;

import java.util.Collection;

public class MoveEntityCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("moveentity")
                        .then(CommandManager.argument("target", EntityArgumentType.entities())
                                .then(CommandManager.argument("amount", Vec3ArgumentType.vec3())
                                        .executes(context -> {
                                            Collection<? extends Entity> entities = EntityArgumentType.getEntities(context, "target");
                                            Vec3d amount = Vec3ArgumentType.getVec3(context, "amount");

                                            for (Entity entity : entities) {
                                                Vec3d distanceFromTarget = entity.getPos().subtract(amount);
                                                entity.addVelocity(distanceFromTarget.getX(),-distanceFromTarget.getY(),distanceFromTarget.getZ());
                                            }

                                            return 1;
                                        })


                                ))
        );
    }

}
