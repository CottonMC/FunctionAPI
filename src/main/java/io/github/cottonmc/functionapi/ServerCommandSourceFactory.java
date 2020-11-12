package io.github.cottonmc.functionapi;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Language;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;


/**
 * Builds a new command source, that can be used to execute event functions
 */
public class ServerCommandSourceFactory {

    public static ServerCommandSourceFactory INSTANCE = new ServerCommandSourceFactory();
    private ServerCommandSourceFactory(){};

    public ServerCommandSource create(MinecraftServer minecraftServer, Vec3d position, Vec2f rotation, ServerWorld serverWorld, int permissionLevel, String simpleName, Text name){
        return create(minecraftServer, position, rotation, serverWorld, permissionLevel, simpleName, name,null);
    }

    public ServerCommandSource create(MinecraftServer minecraftServer, Vec3d position, Vec2f rotation, ServerWorld serverWorld, int permissionLevel, String simpleName, Text name,  Entity entity){
        return new ServerCommandSource(StandardCommandOutput.INSTANCE,position,rotation,serverWorld,permissionLevel,simpleName,name,minecraftServer,entity);
    }

    public ServerCommandSource create(MinecraftServer minecraftServer, ServerWorld serverWorld, Block block, BlockPos blockPos) {
        String translate = Language.getInstance().get(block.getTranslationKey());
        return create(minecraftServer, new Vec3d(blockPos.getX(),blockPos.getY(),blockPos.getZ()), new Vec2f(0f, 0f), serverWorld, 2, translate, new LiteralText(translate));
    }

    public ServerCommandSource create(ServerWorld serverWorld, Block block, BlockPos blockPos) {
        String translate = Language.getInstance().get(block.getTranslationKey());
        return create(serverWorld.getServer(), new Vec3d(blockPos.getX(),blockPos.getY(),blockPos.getZ()), new Vec2f(0f, 0f), serverWorld, 2, translate, new LiteralText(translate));
    }
    public ServerCommandSource create(ServerWorld serverWorld, BlockPos blockPos) {
        Block block = serverWorld.getBlockState(blockPos).getBlock();
        String translate = Language.getInstance().get(block.getTranslationKey());
        return create(serverWorld.getServer(), new Vec3d(blockPos.getX(),blockPos.getY(),blockPos.getZ()), new Vec2f(0f, 0f), serverWorld, 2, translate, new LiteralText(translate));
    }

    public ServerCommandSource create(MinecraftServer minecraftServer, ServerWorld serverWorld, Entity entity) {
        if(entity == null){
            return minecraftServer.getCommandSource();
        }
        Text name = entity.getName();
        if(name == null){
            return create(minecraftServer, entity.getPos(), entity.getRotationClient(), serverWorld, 2, entity.getType().getName().getString(), name,entity);
        }else
        return create(minecraftServer, entity.getPos(), entity.getRotationClient(), serverWorld, 2, name.getString(), name,entity);
    }

    public ServerCommandSource create(ServerWorld serverWorld, Entity entity) {
        return create(serverWorld.getServer(), entity.getPos(), entity.getRotationClient(), serverWorld, 2, entity.getName().getString(), entity.getName(),entity);
    }
    public ServerCommandSource create(MinecraftServer minecraftServer, ServerWorld serverWorld, Block block, BlockPos blockPos,Entity entity) {
        String translate = Language.getInstance().get(block.getTranslationKey());
        return create(minecraftServer, new Vec3d(blockPos.getX(),blockPos.getY(),blockPos.getZ()), new Vec2f(0f, 0f), serverWorld, 2, translate, new LiteralText(translate),entity);
    }

}
