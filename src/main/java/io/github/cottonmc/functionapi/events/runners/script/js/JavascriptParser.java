package io.github.cottonmc.functionapi.events.runners.script.js;

import io.github.cottonmc.functionapi.events.runners.script.api.ExecutableScript;
import io.github.cottonmc.functionapi.events.runners.script.api.InvocableScript;
import io.github.cottonmc.functionapi.events.runners.script.api.ScriptParser;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import net.minecraft.util.Identifier;

import javax.script.*;
import java.util.Optional;

public class JavascriptParser implements ScriptParser {

    private final NashornScriptEngineFactory nashornScriptEngineFactory = new NashornScriptEngineFactory();

    @Override
    public Optional<ExecutableScript> parse(String script){
        ScriptEngine engine = nashornScriptEngineFactory.getScriptEngine(new JavascriptClassFilter());

        try {
            engine.eval("exit = undefined");
            engine.eval("load = undefined");
            engine.eval("Java = undefined");

            engine.eval(script);
            if (engine instanceof Invocable) {
                Object main = engine.get("main");
                //if there is no "main" method, then we don't return the script. It is mandatory.
                if(main == null){
                    return Optional.empty();
                }

                return Optional.of(new InvocableScript((Invocable) engine));
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public boolean canParse(Identifier scriptID) {
        return scriptID.getPath().endsWith(".js");
    }


}
