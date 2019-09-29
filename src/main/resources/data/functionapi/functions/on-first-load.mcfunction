#adds a function hook, like minecraft:onload, but it only fires once for the world.
scoreboard objectives add loadedonce dummy "world was loaded"
execute if score hasloaded loadedonce matches 0 run function functionapi:creationevent


