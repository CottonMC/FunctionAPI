package io.github.cottonmc.functionapi.events.runners.script.api;

/**
 * This one object should be passed into the Scripts to provide standard libraries.
 * */
public class ScriptApiObject {


    //we have an export import system, so we can import libraries from other languages.
    /**
     * An object, that your script exports. Same as nodejs's export function.
     * */
    public void Export(Object exported){

    }

    /**
     * Get the export of a script by it's identifier
     * */
    public Object require(String identifier){
           return null;
    }


    /**
     * Get the export of a script by it's identifier
     * */
    public Object require(String namespace,String path){
        return null;
    }
}
