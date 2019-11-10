#Runs after the world was loaded for the first time.
event run functionapi:api/server/creation
scoreboard players add hasloaded loadedonce 1
