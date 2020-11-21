package io.github.cottonmc.functionapi.commands.inventory.util;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

/**
 * simulates a location in the world as an inventory, any floating items in that location will be considered it's contents.
 */
public class FloatingItemInventory implements Inventory {

    private List<ItemEntity> entities;
    private final World world;
    private final BlockPos blockPos;
    private final Box box;

    public FloatingItemInventory(World world, BlockPos blockPos) {
        this.world = world;
        this.blockPos = blockPos;

        box = new Box(blockPos);
        entities = world.getEntitiesByType(EntityType.ITEM, box, entity -> true).stream().map(entity -> (ItemEntity) entity).collect(Collectors.toList());
    }

    @Override
    public int size(){
        return entities.size() + 1;
    }

    @Override
    public boolean isEmpty(){
        return entities.isEmpty();
    }

    @Override
    public ItemStack getStack(int i){
        if (i > entities.size() - 1) {
            return ItemStack.EMPTY;
        }
        return entities.get(i).getStack();
    }

    @Override
    public ItemStack removeStack(int i, int j){
        if (i > entities.size()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = entities.get(i).getStack();

        ItemStack copy = stack.copy();
        if (stack.getCount() >= j) {
            stack.setCount(stack.getCount() - j);
            copy.setCount(j);
        } else {
            copy.setCount(stack.getCount());
            entities.get(i).kill();
        }

        return copy;
    }

    @Override
    public ItemStack removeStack(int i){
        if (i > entities.size()) {
            return ItemStack.EMPTY;
        }
        ItemEntity itemEntity = entities.get(i);
        ItemStack stack = itemEntity.getStack();
        itemEntity.kill();
        return stack;
    }

    @Override
    public void setStack(int i, ItemStack itemStack){
        if (i > entities.size()-1) {
            ItemEntity itemEntity = new ItemEntity(world, blockPos.getX()+.5, blockPos.getY()+.5, blockPos.getZ()+.5, itemStack);
            world.spawnEntity(itemEntity);
            entities.add(itemEntity);
        }
        ItemEntity itemEntity = entities.get(i);
        if (itemStack.isEmpty()) {
            itemEntity.kill();
        } else
            itemEntity.setStack(itemStack);
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity playerEntity){
        return false;
    }

    @Override
    public void clear() {
        for (ItemEntity entity : entities) {
            entity.kill();
        }
    }
}
