# Function api

Mod that allows minectaft functions to be run on certain events of the game. Add an event tag to your function for it to run.

Tag structure: Due to the nature of the mod, tags contain the id of the
thing they are referring to. for example, to trigger a function when a
dirt block is placed down, use `minecraft:function_api/block/dirt/placed
`. `/` is a directory separator! The example tag goes into the folder
minecraft, under that functions (then)-> function_api-> block-> dirt->
then the placed.json file contains the tag


Entity events are listed, but they are disabled as of 1.2.0 until 
further notice!

## Events

| tag                                                           | action                                                                                           | included objects                                                                                                                                                                                                                  |
|:--------------------------------------------------------------|:-------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| namespace:function_api/block/name/placed                      | called after the block was placed down                                                           | called in the name of the block, in it's position, the placer is also included                                                                                                                                                    |
| namespace:function_api/block/name/broken                      | called after the block was broken or mined                                                       | called in the name of the block, in it's position                                                                                                                                                                                 |
| namespace:function_api/block/name/exploded                    | called after the block blown up                                                                  | called in the name of the block, in it's position                                                                                                                                                                                 |
| namespace:function_api/block/name/activate                    | called after the block was right clicked (used, or activated) by the player                      | called in the name of the block, in it's position, the user is also included                                                                                                                                                      |
| namespace:function_api/block/name/steppedOn                   | called after the block was stepped on by an entity                                               | called in the name of the block, in it's position, the entity is also included                                                                                                                                                    |
| namespace:function_api/block/name/entityCollided              | called after an entity has collided with the block, includes sideways collosions.                | called in the name of the block, in it's position, the entity is also included                                                                                                                                                    |
| namespace:function_api/block/name/entityLanded                | called after an entity has landed on the block                                                   | called in the name of the block, in it's position, the entity is also included                                                                                                                                                    |
| namespace:function_api/block/name/projectileHit               | called after the block was hit by a projectile (arrow, fireball)                                 | called in the name of the block, in it's position, the entity of the projectile is also included                                                                                                                                  |
| namespace:function_api/item/name/use_on_block                 | called after the item was used on a block                                                        | called in the name of the user, in it's position                                                                                                                                                                                  |
| namespace:function_api/item/name/finishUsing                  | called after the item has finished it's use. Only works if the item had a functionality already. | called in the name of the user, in it's position                                                                                                                                                                                  |
| namespace:function_api/item/name/use                          | called after the item has finished it's use.                                                     | called in the name of the user, in it's position                                                                                                                                                                                  |
| namespace:function_api/entity/name/tick                       | called when the entity updates                                                                   | called in the name of the entity, in it's position                                                                                                                                                                                |
| namespace:function_api/entity/name/damage                     | called when the entity takes damage.                                                             | called in the name of the entity, in it's position                                                                                                                                                                                |
| namespace:function_api/entity/name/killed_other               | called when the entity kills an another                                                          | called in the name of the entity, in it's position                                                                                                                                                                                |
| namespace:function_api/entity/name/struck_by_lightning        | called when the entity is struck by lightning                                                    | called in the name of the entity, in it's position                                                                                                                                                                                |
| namespace:function_api/entity/name/swimstart                  | called when the entity starts to swim                                                            | called in the name of the entity, in it's position                                                                                                                                                                                |
| namespace:function_api/livingentity/name/on_attacking         | called when the entity is attacking something                                                    | called in the name of the entity, in it's position                                                                                                                                                                                |
| namespace:function_api/livingentity/name/on_death             | called when the entity dies                                                                      | called in the entity's position. No entity is in the context, as it might not exist anymore.                                                                                                                                      |
| functionapi:function_api/api/server/creation                  | fired once after the world was created.                                                          | ran as a server command, no location or entities involved. Setting the "loadedonce" score on the fake player called "hasloaded" to 0 will cause it to repeat on the next (re)load (reload the world, or use the /reload command). |
| functionapi:function_api/entity/entity/tick                     | called when any entity updates                                                                   | called in the name of the entity, in it's position                                                                                                                                                                                |
| functionapi:function_api/entity/entity/damage                   | called when any entity takes damage.                                                             | called in the name of the entity, in it's position                                                                                                                                                                                |
| functionapi:function_api/entity/entity/killed_other             | called when any entity kills an another                                                          | called in the name of the entity, in it's position                                                                                                                                                                                |
| functionapi:function_api/entity/entity/struck_by_lightning      | called when any entity is struck by lightning                                                    | called in the name of the entity, in it's position                                                                                                                                                                                |
| functionapi:function_api/entity/entity/swimstart                | called when any entity starts to swim                                                            | called in the name of the entity, in it's position                                                                                                                                                                                |
| functionapi:function_api/livingentity/livingentity/on_attacking | called when any entity is attacking something                                                    | called in the name of the entity, in it's position                                                                                                                                                                                |
| functionapi:function_api/livingentity/livingentity/on_death     | called when any entity dies                                                                      | called in the entity's position. No entity is in the context, as it might not exist anymore.                                                                                                                                      |

Examples can be found in the examples folder.
## Entity Events

Entity events only work for vanilla entities by default.The functions
`functionapi:api/build_vanilla_entity_events.mcfunction` and
`functionapi:api/run_entity_tick_event.mcfunction` contain the template
that you need copy for modded entity support.

It is tedious, but it does allow you to subset entities in any way you'd
like. (eg, in my system horses are 1 event, but you can use entity
selectors to filter for colors)

### Internal Events 
DO NOT hook into tags that use the name "internal", unless you are fully
aware of what are you doing. Those only exist for the purposes of this
mod.

## Commands

The mod adds the `event` command that can be used to manually trigger,
or enable/disable/create events. Keep in mind, that you can not disable
an event that has not been fired yet!

If you want to disable an event (eg: something crashes your game, or
just customization), I recommend doing it at `minecraft:load`, aka
datapack (re)load.

Calling the `event` command without an id (eg: `event disable`), will
cause it to affect all currently registered events.


## Registration callbacks.

Internally, most events are created when something required it. For
example you stepped on that specific block. (the `creation` event is
static)

All events have a "create callback" event, that gets called after the
event was registered. The ID is the same as the parent event, postfixed
with `_create_callback`. Example:
`minecraft:function_api/block/dirt/placed_create_callback`.

This allows packmakers to disable events that would be coming from other
mods, right after they were registered. Currently that is the only
purpose. They are called as the server.
