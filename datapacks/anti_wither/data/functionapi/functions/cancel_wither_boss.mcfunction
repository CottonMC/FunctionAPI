
#cancel the explosion
event cancel

#summon a firework to deal explosion damage
summon firework_rocket ~ ~1 ~ {LifeTime:1,FireworksItem:{id:firework_rocket,Count:1,tag:{Fireworks:{Flight:2,Explosions:[{Type:1,Flicker:0,Trail:0,Colors:[I;15790320],FadeColors:[I;15790320]}]}}}}
#add some extra particles
particle minecraft:explosion_emitter ~ ~1 ~ ~ ~ ~1
particle minecraft:explosion_emitter ~ ~1 ~ ~ ~1 ~
particle minecraft:explosion_emitter ~ ~1 ~ ~1 ~ ~
particle minecraft:explosion_emitter ~ ~1 ~ ~1 ~ ~
particle minecraft:explosion_emitter ~ ~1 ~ ~ ~1 ~
particle minecraft:explosion_emitter ~ ~1 ~ ~ ~ ~1