package io.github.cottonmc.functionapi.api;

public interface CommandSourceExtension {

   default boolean isCancelled(){
      return false;
   };

   void cancel();
}
