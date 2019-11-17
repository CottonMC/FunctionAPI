package io.github.cottonmc.functionapi.api.content;


import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;

import java.util.List;
import java.util.Map;

public interface CommandFileSource<S> {

    Map<FunctionAPIIdentifier, List<S>> getCommandFiles();
}
