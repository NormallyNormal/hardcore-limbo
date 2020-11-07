package com.normallynormal.hardcorelimbo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.loader.util.sat4j.core.Vec;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class HardcoreLimboClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Here we will put client-only registration code
        //BlockEntityRendererRegistry.INSTANCE.register(HardcoreLimbo.WIND_CHIMES_ENTITY, WindChimesTileRenderer::new);
        BlockEntityRendererRegistry.INSTANCE.register(HardcoreLimbo.SOUL_BINDER_ENTITY, SoulBinderTileRenderer::new);
        Random randomizer = new Random(System.currentTimeMillis());
//        ClientSidePacketRegistry.INSTANCE.register(HardcoreLimbo.UPDATE_SOUL_BINDER_INVENTORY_CONTENTS,
//                (packetContext, attachedData) -> packetContext.getTaskQueue().execute(() -> {
//                    DefaultedList<ItemStack> items = DefaultedList.ofSize(4, ItemStack.EMPTY);
//                    items.get(1).getItem().
//        }));
        ClientSidePacketRegistry.INSTANCE.register(HardcoreLimbo.LEADING_PARTICLE,
                (packetContext, attachedData) -> {
                        ParticleEffect flame = attachedData.readBoolean() ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME;
                        float variance = attachedData.readFloat();
                        BlockPos blockPos = attachedData.readBlockPos();
                        BlockPos deathLocation = attachedData.readBlockPos();
                        Vec3d pos = packetContext.getPlayer().getPos();
                        double mag = Math.sqrt(Math.pow((deathLocation.getX()-pos.getX()),2) + Math.pow((deathLocation.getY()-pos.getY()),2) + Math.pow((deathLocation.getZ()-pos.getZ()),2));
                        Vec3d r = new Vec3d(deathLocation.getX()-pos.getX(),deathLocation.getY()-pos.getY(),deathLocation.getZ()-pos.getZ());
                        Vec3d rhat = new Vec3d(r.getX()/mag,r.getY()/mag,r.getZ()/mag);
                        packetContext.getTaskQueue().execute(() -> {
                        for(int i = 0; i < 10; i++)
                            MinecraftClient.getInstance().particleManager.addParticle(flame, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, rhat.getX()/6 + -variance/2 + (randomizer.nextDouble() * variance), -0.05 + (randomizer.nextDouble() * 0.1), rhat.getZ()/6 + -variance/2 + (randomizer.nextDouble() * variance)
                    );
                });
        });
    }
}