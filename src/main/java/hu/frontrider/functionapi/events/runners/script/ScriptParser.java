package hu.frontrider.functionapi.events.runners.script;

import net.minecraft.util.Identifier;

import javax.script.*;
import java.util.Optional;

public interface ScriptParser {

    /**
     * Returns an Invocable from the engine that can be used to execute the script's `main` method.
     * This is a decision made to make repeated execution faster, as we only compile once.
     *
     * @param script the source of the script
     * @return an optional that may contain something that can be executed.
     *
     * */
    Optional<Invocable> parse(String script);

    /**
     * returns true if this identifier (with file extension) is valid for this engine.
     * */
    boolean correctFor(Identifier scriptID);

    default ScriptEngine getEngineByName(String name){
        final ScriptEngine engine;

        ScriptEngineManager factory = new ScriptEngineManager();
        engine = factory.getEngineByName(name);

        Bindings bindings = engine.createBindings();
        bindings.put("api",new ScriptApiObject());

        return engine;
    }
}
