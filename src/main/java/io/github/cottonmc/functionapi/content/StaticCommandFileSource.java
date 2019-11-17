package io.github.cottonmc.functionapi.content;

import io.github.cottonmc.functionapi.FunctionAPI;
import io.github.cottonmc.functionapi.api.content.CommandFileSource;

import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.script.FunctionAPIIdentifierImpl;
import io.github.cottonmc.functionapi.util.DirectoryManager;
import io.github.cottonmc.functionapi.util.FileTemplateHelper;
import io.github.cottonmc.staticdata.StaticData;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StaticCommandFileSource implements CommandFileSource<String> {

    private String dirName;

    public StaticCommandFileSource(String dirName) {
        this.dirName = dirName;
    }

    @Override
    public Map<FunctionAPIIdentifier, List<String>> getCommandFiles() {
        Map<FunctionAPIIdentifier, String[]> commands = new FileTemplateHelper<>(
                (file) -> new BufferedReader(new FileReader(file)).lines().collect(Collectors.toList()).toArray(new String[]{}),
                DirectoryManager.getINSTANCE().getContentFolder(),
                "mccontent"
        ).getTemplates();

        StaticData
                .getAllInDirectory(FunctionAPI.MODID + "/" + dirName)
                .stream()
                .map(staticDataItem -> {
                    try {
                        String[] lines = staticDataItem.getAsString().split("\n+");
                        for (int i = 0; i < lines.length; i++) {
                            lines[i] = lines[i].trim();
                        }
                        net.minecraft.util.Identifier identifier = staticDataItem.getIdentifier();

                        return new ImmutablePair<>(new FunctionAPIIdentifierImpl(identifier.getNamespace(), identifier.getPath().replaceAll("^" + FunctionAPI.MODID + "/" + dirName + "/", "").replaceAll("\\.mccontent$", "")), lines);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .forEach(identifierImmutablePair -> {
                    commands.put(identifierImmutablePair.getLeft(), (identifierImmutablePair.right));
                });

        Map<FunctionAPIIdentifier,List<String>> results = new HashMap<>();

        commands.forEach((key,value)->{
            List<String> strings = new ArrayList<>();
            Collections.addAll(strings,value);
            strings = strings.stream().filter(line-> !line.trim().startsWith("#") && !line.trim().isEmpty()).collect(Collectors.toList());
            results.put(key,strings);
        });

        return results;
    }
}
