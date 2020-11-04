package com.normallynormal.hardcorelimbo;

import com.normallynormal.hardcorelimbo.HardcoreLimbo;
import com.normallynormal.hardcorelimbo.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;

public class SoulBinderEntity extends BlockEntity implements ImplementedInventory, SidedInventory {

    private DefaultedList<ItemStack> items = DefaultedList.ofSize(4, ItemStack.EMPTY);

    public SoulBinderEntity() {
        super(HardcoreLimbo.SOUL_BINDER_ENTITY);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        Inventories.fromTag(tag,items);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        Inventories.toTag(tag,items);
        return super.toTag(tag);
    }
    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction direction) {
        return false;
    }

    @Override
    public int[] getAvailableSlots(Direction var1) {
        // Just return an array of all slots
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }

        return result;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return false;
    }

    public ItemStack getItemOnSide(int side){
        return this.getStack(side);
    }
}
