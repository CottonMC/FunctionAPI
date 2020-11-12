
Function API Event reference. This document has been generated from the source code of the mod!

The event names are normal identifiers. To get the tag that you can use to bind a function file is as follows:

event name: `minecraft:block/dirt/broken`

tag name: `minecraft:function_api/block/dirt/broken`


If an event is blocking, then it will pause the game until the execution is done, so it CAN cause lagspikes! (non blocking events will run when the server has time for it)

| name  | description | includes | blocking |
|---|---|---|---|
| functionapi:api/server/creation|Runs after the world was loaded for the first time.| The server | No |
| \<namespace\>:creeper/\<name\>/before/explode||The source block, running it it's position|Yes, can be cancelled!|
| \<namespace\>:creeper/\<name\>/explode||The source block, running it it's position|No|
| \<namespace\>:block/\<name\>/broken||The source block, running it it's position, also the entity that is the source of this interaction|No|
| \<namespace\>:block/\<name\>/set||The source block, running it it's position, also the entity that is the source of this interaction|No|
| \<namespace\>:block/\<name\>/piston_move||The source block, running it it's position, also the entity that is the source of this interaction|No|
| \<namespace\>:block/\<name\>/after/placed||The source block, running it it's position, also the entity that is the source of this interaction|Yes|
| \<namespace\>:block/\<name\>/placed||The source block, running it it's position, also the entity that is the source of this interaction|No|
| \<namespace\>:block/\<name\>/before/placed||The source block, running it it's position, also the entity that is the source of this interaction|Yes, can be cancelled!|
| \<namespace\>:block/\<name\>/before/broken||The source block, running it it's position|Yes, can be cancelled!|
| \<namespace\>:block/\<name\>/broken||The source block, running it it's position, also the entity that is the source of this interaction|No|
| \<namespace\>:block/\<name\>/exploded||The source block, running it it's position, also the entity that is the source of this interaction|No|
| \<namespace\>:block/\<name\>/stepped_on||The source block, running it it's position, also the entity that is the source of this interaction|No|
| \<namespace\>:item/\<name\>/after_break||The source block, running it it's position, also the entity that is the source of this interaction|No|
| \<namespace\>:block/\<name\>/before/entity_landed||The source block, running it it's position, also the entity that is the source of this interaction|Yes, can be cancelled!|
| \<namespace\>:block/\<name\>/entity_landed||The source block, running it it's position, also the entity that is the source of this interaction|No|
| \<namespace\>:entity/\<name\>/attacking||The source entity, running it it's position|No|
| function_api:entity/\<name\>/attacking|Called when any entity calls \<namespace\>:entity/\<name\>/attacking|The source entity, running it it's position|No|
| \<namespace\>:entity/\<name\>/death||The source entity, running it it's position|No|
| function_api:entity/\<name\>/death|Called when any entity calls \<namespace\>:entity/\<name\>/death|The source entity, running it it's position|No|
| \<namespace\>:entity/\<name\>/shield_hit||The source entity, running it it's position|No|
| function_api:entity/\<name\>/shield_hit|Called when any entity calls \<namespace\>:entity/\<name\>/shield_hit|The source entity, running it it's position|No|
| \<namespace\>:block/\<name\>/activate||The source block, running it it's position, also the entity that is the source of this interaction|No|
| \<namespace\>:block/\<name\>/activate_offhand||The source block, running it it's position, also the entity that is the source of this interaction|No|
| \<namespace\>:block/\<name\>/before/activate||The source block, running it it's position, also the entity that is the source of this interaction|Yes, can be cancelled!|
| \<namespace\>:block/\<name\>/before/activate_offhand||The source block, running it it's position, also the entity that is the source of this interaction|Yes, can be cancelled!|
| \<namespace\>:block/\<name\>/neighbour_update||The source block, running it it's position, also the entity that is the source of this interaction|No|
| \<namespace\>:block/\<name\>/entity_collided||The source block, running it it's position, also the entity that is the source of this interaction|No|
| \<namespace\>:block/\<name\>/projectile_hit||The source block, running it it's position, also the entity that is the source of this interaction|No|
| \<namespace\>:block/\<name\>/tick||The source block, running it it's position, also the entity that is the source of this interaction|No|
| \<namespace\>:block/\<name\>/random_tick||The source block, running it it's position, also the entity that is the source of this interaction|No|
| \<namespace\>:item/\<name\>/use_on_block||The source block, running it it's position, also the entity that is the source of this interaction|No|
| \<namespace\>:item/\<name\>/use_on_block_offhand||The source block, running it it's position, also the entity that is the source of this interaction|No|
| \<namespace\>:item/\<name\>/before/use_on_block||The source block, running it it's position, also the entity that is the source of this interaction|Yes, can be cancelled!|
| \<namespace\>:item/\<name\>/before/use_on_block_offhand||The source block, running it it's position, also the entity that is the source of this interaction|Yes, can be cancelled!|
| \<namespace\>:item/\<name\>/before/use_on_entity||The source block, running it it's position|Yes, can be cancelled!|
| \<namespace\>:item/\<name\>/before/use_on_entity_offhand||The source block, running it it's position|Yes, can be cancelled!|
| \<namespace\>:item/\<name\>/use_on_entity||The source block, running it it's position|No|
| \<namespace\>:item/\<name\>/use_on_entity_offhand||The source block, running it it's position|No|
| \<namespace\>:entity/\<name\>/spawn||The source entity, running it it's position|No|
| function_api:entity/\<name\>/spawn|Called when any entity calls \<namespace\>:entity/\<name\>/spawn|The source entity, running it it's position|No|
| \<namespace\>:(ScriptedObject)source/\<name\>/before/explode_start||The source block, running it it's position|Yes, can be cancelled!|
| \<namespace\>:(ScriptedObject)source/\<name\>/explode_start||The source block, running it it's position|No|
| \<namespace\>:entity/\<name\>/before/explode||The source entity, running it it's position|Yes, can be cancelled!|
| function_api:entity/\<name\>/before/explode|Called when any entity calls \<namespace\>:entity/\<name\>/before/explode|The source entity, running it it's position|Yes, can be cancelled!|
| \<namespace\>:entity/\<name\>/explode||The source entity, running it it's position|No|
| function_api:entity/\<name\>/explode|Called when any entity calls \<namespace\>:entity/\<name\>/explode|The source entity, running it it's position|No|
| \<namespace\>:entity/\<name\>/tick||The source entity, running it it's position|No|
| function_api:entity/\<name\>/tick|Called when any entity calls \<namespace\>:entity/\<name\>/tick|The source entity, running it it's position|No|
| \<namespace\>:entity/\<name\>/swim_start||The source entity, running it it's position|No|
| function_api:entity/\<name\>/swim_start|Called when any entity calls \<namespace\>:entity/\<name\>/swim_start|The source entity, running it it's position|No|
| \<namespace\>:entity/\<name\>/before/damage||The source entity, running it it's position|Yes, can be cancelled!|
| function_api:entity/\<name\>/before/damage|Called when any entity calls \<namespace\>:entity/\<name\>/before/damage|The source entity, running it it's position|Yes, can be cancelled!|
| \<namespace\>:entity/\<name\>/damage||The source entity, running it it's position|No|
| function_api:entity/\<name\>/damage|Called when any entity calls \<namespace\>:entity/\<name\>/damage|The source entity, running it it's position|No|
| \<namespace\>:entity/\<name\>/struck_by_lightning||The source entity, running it it's position|No|
| function_api:entity/\<name\>/struck_by_lightning|Called when any entity calls \<namespace\>:entity/\<name\>/struck_by_lightning|The source entity, running it it's position|No|
| \<namespace\>:(ScriptedObject)source/\<name\>/before/explode_start||The source block, running it it's position|Yes, can be cancelled!|
| \<namespace\>:(ScriptedObject)source/\<name\>/explode_start||The source block, running it it's position|No|
| \<namespace\>:(ScriptedObject)source/\<name\>/before/explode_start||The source entity, running it it's position|Yes, can be cancelled!|
| \<namespace\>:(ScriptedObject)source/\<name\>/explode_start||The source entity, running it it's position|No|
| \<namespace\>:(ScriptedObject)source/\<name\>/before/explode_blocks||The source block, running it it's position|Yes, can be cancelled!|
| \<namespace\>:(ScriptedObject)source/\<name\>/explode_blocks||The source block, running it it's position|No|
| \<namespace\>:(ScriptedObject)source/\<name\>/before/explode_blocks||The source entity, running it it's position|Yes, can be cancelled!|
| \<namespace\>:(ScriptedObject)source/\<name\>/explode_blocks||The source entity, running it it's position|No|
