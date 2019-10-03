package hu.frontrider.functionapi.events.runners.script;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.arguments.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ServerCommandSourceWrapper implements CommandSource {


    private final ServerCommandSource delegate;
    public ServerCommandSourceWrapper(ServerCommandSource delegate) {

        this.delegate = delegate;
    }

    public Text getDisplayName() {
        return this.delegate.getDisplayName();
    }

    public String getName() {
        return this.delegate.getName();
    }

    public boolean hasPermissionLevel(int int_1) {
        return this.delegate.hasPermissionLevel(int_1);
    }

    public Vec3d getPosition() {
        return this.delegate.getPosition();
    }

    public ServerWorld getWorld() {
        return this.delegate.getWorld();
    }

    public Entity getEntity() {
        return this.delegate.getEntity();
    }

    public Entity getEntityOrThrow() throws CommandSyntaxException {
        return this.delegate.getEntityOrThrow();
    }

    public ServerPlayerEntity getPlayer() throws CommandSyntaxException {
        return this.delegate.getPlayer();
    }

    public Vec2f getRotation() {
        return this.delegate.getRotation();
    }

    public MinecraftServer getMinecraftServer() {
        return this.delegate.getMinecraftServer();
    }

    public EntityAnchorArgumentType.EntityAnchor getEntityAnchor() {
        return this.delegate.getEntityAnchor();
    }

    public void sendFeedback(String text_1, boolean boolean_1) {

        this.delegate.sendFeedback(new LiteralText(text_1),boolean_1);

    }

    public void sendError(String text_1) {

        this.delegate.sendError(new LiteralText(text_1));
    }


    public Collection<String> getPlayerNames() {
        return this.delegate.getPlayerNames();
    }

    public Collection<String> getTeamNames() {
        return this.delegate.getTeamNames();
    }

    public Collection<Identifier> getSoundIds() {
        return Registry.SOUND_EVENT.getIds();
    }

    public Stream<Identifier> getRecipeIds() {
        return this.delegate.getRecipeIds();
    }

    public CompletableFuture<Suggestions> getCompletions(CommandContext<CommandSource> commandContext_1, SuggestionsBuilder suggestionsBuilder_1) {
        return delegate.getCompletions(commandContext_1, suggestionsBuilder_1);
    }

}
