package hu.frontrider.functionapi.events.runners.script.js;

import jdk.nashorn.api.scripting.ClassFilter;

public class JavascriptClassFilter implements ClassFilter {
    @Override
    public boolean exposeToScripts(String s) {
        return false;
    }
}
