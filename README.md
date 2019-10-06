# Function api

Mod that allows minectaft functions to be run on certain events of the game. Add an event tag to your function for it to run.

Tag structure: Due to the nature of the mod, tags contain the id of the
thing they are referring to. for example, to trigger a function when a
dirt block is placed down, use `minecraft:function_api/block/dirt/placed
`. `/` is a directory separator! The example tag goes into the folder
minecraft, under that functions (then)-> function_api-> block-> dirt->
then the placed.json file contains the tag

More information on the wiki page.

Examples can be found in the examples folder.
