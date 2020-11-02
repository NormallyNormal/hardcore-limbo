package com.normallynormal.hardcorelimbo;

import com.normallynormal.hardcorelimbo.HardcoreLimbo;
import com.normallynormal.hardcorelimbo.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;

public class SoulBinderEntity extends BlockEntity implements ImplementedInventory {

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

    public ItemStack getItemOnSide(int side){
        return this.getStack(side);
    }
}
