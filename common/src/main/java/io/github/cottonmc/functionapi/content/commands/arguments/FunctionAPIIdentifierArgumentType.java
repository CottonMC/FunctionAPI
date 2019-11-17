package io.github.cottonmc.functionapi.content.commands.arguments;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.script.FunctionAPIIdentifierImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class FunctionAPIIdentifierArgumentType implements ArgumentType<FunctionAPIIdentifier> {

    public FunctionAPIIdentifierArgumentType() {
    }

    public static FunctionAPIIdentifierArgumentType identifier() {
        return new FunctionAPIIdentifierArgumentType();
    }


    @Override
    public FunctionAPIIdentifier parse(StringReader reader) throws CommandSyntaxException {
        int int_1 = reader.getCursor();

        while (reader.canRead() && FunctionAPIIdentifier.isCharValid(reader.peek())) {
            reader.skip();
        }

        String[] split = reader.getString().substring(int_1, reader.getCursor()).split(":");

        if(split.length != 2){
            LiteralMessage message = new LiteralMessage("invalid argument "+ Arrays.toString(split));
            throw new CommandSyntaxException(new SimpleCommandExceptionType(message),message);
        }
        return new FunctionAPIIdentifierImpl(split[0], split[1]);
    }
}
