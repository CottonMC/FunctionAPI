package hu.frontrider.functionapi.events.context;


import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandSource;
import net.minecraft.util.Identifier;

import java.util.Optional;

public interface EnhancedCommandSource {

    /**
     * adds/changes a new value to this context
     * */
    <T> void setValue(Class<T> type, Identifier identifier,Object object);
    /**
     * Get a value from the context with a given ID.
     * */
    <T> Optional<T> getValue(Class<T> type, Identifier identifier);

    /**
     * The extended context can store multiple entities, that we can swap between. It gives more control over the environment.
     * */
    void changeTarget(MemberType memberType);

    /**
     * Adds a new target member to the source, each type can only have 1 member.
     * */
    void setMember(MemberType memberType, Entity entity);
}
