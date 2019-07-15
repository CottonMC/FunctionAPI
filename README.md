# Function api

Mod that allows minectaft functions to be run on certain events of the game. Add an event tag to your function for it to run.

Tag structure:
Due to the nature of the mod, tags contain the id of the thing they are referring to. for example, to trigger a function when a dirt block is placed down, use `api:block-placed-minecraft-dirt`.

tags:

| tag  | action |included objects|
| ------------- | ------------- | ------------- |
| api:block-placed-*namespace*-*name*  | called after the block was placed down | called in the name of the block, in it's position, the placer is also included |
| api:block-broken-*namespace*-*name*  | called after the block was broken or mined | called in the name of the block, in it's position|
| api:block-exploded-*namespace*-*name*  | called after the block blown up | called in the name of the block, in it's position|
| api:block-activate-*namespace*-*name*  | called after the block was right clicked (used, or activated) by the player | called in the name of the block, in it's position, the user is also included |
| api:block-steppedOn-*namespace*-*name*  | called after the block was stepped on by an entity | called in the name of the block, in it's position, the entity is also included |
| api:block-entityCollided-*namespace*-*name*  | called after an entity has collided with the block, includes sideways collosions. | called in the name of the block, in it's position, the entity is also included |
| api:block-entityLanded-*namespace*-*name*  | called after an entity has landed on the block | called in the name of the block, in it's position, the entity is also included |
| api:block-projectileHit-*namespace*-*name*  | called after the block was hit by a projectile (arrow, fireball)| called in the name of the block, in it's position, the entity of the projectile is also included |
| api:item-useOnBlock-*namespace*-*name*  | called after the item was used on a block | called in the name of the user, in it's position |
| api:item-finishUsing-*namespace*-*name*  | called after the item was used | called in the name of the user, in it's position |
