package com.normallynormal.hardcorelimbo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class HardcoreLimboClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Here we will put client-only registration code
        //BlockEntityRendererRegistry.INSTANCE.register(HardcoreLimbo.WIND_CHIMES_ENTITY, WindChimesTileRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(HardcoreLimbo.SOUL_BINDER_ENTITY, SoulBinderTileRenderer::new);
//        ClientSidePacketRegistry.INSTANCE.register(HardcoreLimbo.UPDATE_SOUL_BINDER_INVENTORY_CONTENTS,
//                (packetContext, attachedData) -> packetContext.getTaskQueue().execute(() -> {
//                    DefaultedList<ItemStack> items = DefaultedList.ofSize(4, ItemStack.EMPTY);
//                    items.get(1).getItem().
//        }));
    }
}