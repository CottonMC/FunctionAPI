execute if entity @s[y_rotation=135..-135] run setstate ~ ~ ~ facing direction north
execute if entity @s[y_rotation=-45..45] run setstate ~ ~ ~ facing direction south
execute if entity @s[y_rotation=-135..-45] run setstate ~ ~ ~ facing direction east
execute if entity @s[y_rotation=45..135] run setstate ~ ~ ~ facing direction west