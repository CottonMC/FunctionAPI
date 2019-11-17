package io.github.cottonmc.functionapi.content.commands.arguments;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.script.FunctionAPIIdentifierImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionAPIFunctionAPIIdentifierArgumentTypeTest {

    CommandDispatcher<String> stringCommandDispatcher;
    FunctionAPIIdentifier identifier;

    @BeforeEach
    void setUp() throws CommandSyntaxException {
        stringCommandDispatcher = new CommandDispatcher<>();
        LiteralCommandNode<String> register = stringCommandDispatcher.register(
                LiteralArgumentBuilder.<String>literal("id")
                        .then(LiteralArgumentBuilder.<String>literal("id")
                                .executes(context -> {
                                    return 1;
                                }))
                        .then(RequiredArgumentBuilder.<String, FunctionAPIIdentifier>argument("identifier", FunctionAPIIdentifierArgumentType.identifier())
                                .executes(context -> {
                                    identifier = context.getArgument("identifier", FunctionAPIIdentifierImpl.class);
                                    return 1;
                                }))
        );

        String[] allUsage = stringCommandDispatcher.getAllUsage(stringCommandDispatcher.getRoot(), "", false);
        System.out.println("usages:");
        for (String s : allUsage) {
            System.out.println(s);
        }
        stringCommandDispatcher.execute("id id", "");
    }

    @Test
    @DisplayName("The argument can be read as an functionAPIIdentifier.")
    void canReadArgument() throws CommandSyntaxException {
        stringCommandDispatcher.execute("id namespace:value", "");
        assertAll(
                () -> assertEquals("namespace", identifier.getNamespace()),
                () -> assertEquals("value", identifier.getPath())
        );
    }

    @AfterEach
    void tearDown() {
    }
}