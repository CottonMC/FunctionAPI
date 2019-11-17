package io.github.cottonmc.functionapi.api.commands;


public interface CommandSourceExtension {

   default boolean isCancelled(){
      return false;
   };

   void cancel();
}
