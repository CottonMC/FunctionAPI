package io.github.cottonmc.functionapi.content;

import com.mojang.brigadier.CommandDispatcher;
import io.github.cottonmc.functionapi.api.content.CommandFileSource;
import io.github.cottonmc.functionapi.api.content.ContentRegistrationContext;
import io.github.cottonmc.functionapi.content.commands.BlockPlacementCommand;
import io.github.cottonmc.functionapi.content.commands.BlockSettingsCommand;
import io.github.cottonmc.functionapi.content.commands.ItemSettingsCommand;
import io.github.cottonmc.functionapi.content.commands.RegistrationCommand;
import io.github.cottonmc.functionapi.content.commands.util.IncludeCommand;
import io.github.cottonmc.functionapi.content.commands.util.PrintCommand;
import io.github.cottonmc.functionapi.documentation.ContentCommandDocumentationGenerator;
import io.github.cottonmc.functionapi.script.StaticCommandExecutor;

import java.util.function.Function;

public class ContentCommandExecutor extends StaticCommandExecutor<ContentRegistrationContext, String> {

    private ContentCommandExecutor(CommandFileSource<String> commandFileSource, Function<CommandDispatcher<ContentRegistrationContext>, ContentRegistrationContext> contextFactory) {
        super(commandFileSource, contextFactory);
    }

   public static class Builder{

        CommandFileSource<String> fileSource;
        Function<CommandDispatcher<ContentRegistrationContext>, ContentRegistrationContext> contextFactory;

        public Builder setFileSource(CommandFileSource<String> commandFileSource){
            fileSource = commandFileSource;
            return this;
        }

        public Builder setContextFactory( Function<CommandDispatcher<ContentRegistrationContext>, ContentRegistrationContext> contextFactory){
            this.contextFactory = contextFactory;
            return this;
        }

        public ContentCommandExecutor build(){
            ContentCommandExecutor contentCommandExecutor = new ContentCommandExecutor(fileSource, contextFactory);

            contentCommandExecutor.register(BlockPlacementCommand::register);
            contentCommandExecutor.register(BlockSettingsCommand::register);
            contentCommandExecutor.register(ItemSettingsCommand::register);
            contentCommandExecutor.register(RegistrationCommand::register);
            contentCommandExecutor.register((dispatcher -> PrintCommand.register((CommandDispatcher) dispatcher)));
            contentCommandExecutor.register((dispatcher -> IncludeCommand.register((CommandDispatcher) dispatcher)));

            new ContentCommandDocumentationGenerator().generate(contentCommandExecutor.getCommandDispatcher());
            return contentCommandExecutor;
        }
    }
    public static ContentCommandExecutor INSTANCE=null;
}
