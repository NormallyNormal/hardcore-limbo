package com.normallynormal.hardcorelimbo;

import com.normallynormal.hardcorelimbo.mixins.GameRendererAccessor;
import com.normallynormal.hardcorelimbo.mixins.GameRendererInvoker;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.loader.util.sat4j.core.Vec;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class HardcoreLimboClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        //Soul binder item renderer
        BlockEntityRendererRegistry.INSTANCE.register(HardcoreLimbo.SOUL_BINDER_ENTITY, SoulBinderTileRenderer::new);
        //Randomizer
        Random randomizer = new Random(System.currentTimeMillis());

        //Blocks can have textures with transparency
        BlockRenderLayerMap.INSTANCE.putBlock(HardcoreLimbo.WIND_CHIMES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(HardcoreLimbo.SOUL_BINDER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(HardcoreLimbo.SOUL_GUIDING_LANTERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(HardcoreLimbo.GOLDEN_SOUL_GUIDING_LANTERN, RenderLayer.getCutout());

        //Packet for soul lantern particles
        ClientSidePacketRegistry.INSTANCE.register(HardcoreLimbo.LEADING_PARTICLE,
                (packetContext, attachedData) -> {
                        ParticleEffect flame = attachedData.readBoolean() ? ParticleTypes.SOUL_FIRE_FLAME : ParticleTypes.FLAME;
                        //Variance determines how concentrated the particles are
                        float variance = attachedData.readFloat();
                        BlockPos blockPos = attachedData.readBlockPos();
                        BlockPos deathLocation = attachedData.readBlockPos();
                        Vec3d pos = packetContext.getPlayer().getPos();
                        //Magnitude of vector to location
                        double mag = Math.sqrt(Math.pow((deathLocation.getX()-pos.getX()),2) + Math.pow((deathLocation.getY()-pos.getY()),2) + Math.pow((deathLocation.getZ()-pos.getZ()),2));
                        //Vector to location
                        Vec3d r = new Vec3d(deathLocation.getX()-pos.getX(),deathLocation.getY()-pos.getY(),deathLocation.getZ()-pos.getZ());
                        //Unit vector with same direction as r
                        Vec3d rhat = new Vec3d(r.getX()/mag,r.getY()/mag,r.getZ()/mag);
                        //Make 10 random velocity particles
                        packetContext.getTaskQueue().execute(() -> {
                        for(int i = 0; i < 10; i++)
                            MinecraftClient.getInstance().particleManager.addParticle(flame, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, rhat.getX()/6 + -variance/2 + (randomizer.nextDouble() * variance), -0.05 + (randomizer.nextDouble() * 0.1), rhat.getZ()/6 + -variance/2 + (randomizer.nextDouble() * variance)
                    );
                });
        });

        //Formless shader on client
        ClientSidePacketRegistry.INSTANCE.register(HardcoreLimbo.FORMLESS_SHADER,
                (packetContext, attachedData) -> {
                    boolean addOrRemove = attachedData.readBoolean();
                    packetContext.getTaskQueue().execute(() -> {
                        //Add or remove the shader effects
                        if(addOrRemove) {
                            ((GameRendererInvoker) MinecraftClient.getInstance().gameRenderer).InvokeLoadShader(new Identifier("shaders/post/desaturate.json"));
                            //Hide the HUD
                            MinecraftClient.getInstance().options.hudHidden = true;
                            //Make the client player know it's a ghost.
                            ((ClientPlayerEntityExt) MinecraftClient.getInstance().player).setFormless(true);
                        } else {
                            ((GameRendererAccessor) MinecraftClient.getInstance().gameRenderer).getShader().close();
                            MinecraftClient.getInstance().options.hudHidden = false;
                            ((ClientPlayerEntityExt) MinecraftClient.getInstance().player).setFormless(false);
                        }
                    });
                });
    }
}