package io.github.cottonmc.functionapi.documentation;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.RootCommandNode;
import io.github.cottonmc.functionapi.api.content.ContentRegistrationContext;
import io.github.cottonmc.functionapi.content.ContentRegistrationContextImpl;

import java.io.IOException;
import java.io.PrintWriter;

public class ContentCommandDocumentationGenerator {

    public void generate(CommandDispatcher<ContentRegistrationContext> dispatcher){
        RootCommandNode<ContentRegistrationContext> root = dispatcher.getRoot();
        ContentRegistrationContextImpl contentRegistrationContext = new ContentRegistrationContextImpl(dispatcher);
        String[] allUsage = dispatcher.getAllUsage(root, contentRegistrationContext, false);

        try {

            PrintWriter fileWriter = new PrintWriter("./functionapi.txt");
            fileWriter.write("Function api registration command reference: "+System.lineSeparator());
            for (String s : allUsage) {
                fileWriter.append(s).append(System.lineSeparator());
            }
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
