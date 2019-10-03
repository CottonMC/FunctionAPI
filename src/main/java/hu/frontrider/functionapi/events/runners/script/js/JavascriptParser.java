package hu.frontrider.functionapi.events.runners.script.js;

import hu.frontrider.functionapi.events.runners.script.ScriptParser;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import net.minecraft.util.Identifier;

import javax.script.*;
import java.util.Optional;

public class JavascriptParser implements ScriptParser {

    private final NashornScriptEngineFactory nashornScriptEngineFactory = new NashornScriptEngineFactory();

    @Override
    public Optional<Invocable> parse(String script){
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

                Invocable invocable = (Invocable) engine;
                return Optional.of(invocable);
            }
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public boolean correctFor(Identifier scriptID) {
        return scriptID.getPath().endsWith(".js");
    }


}
