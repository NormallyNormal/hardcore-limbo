package com.normallynormal.hardcorelimbo;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;

import static com.normallynormal.hardcorelimbo.HardcoreLimbo.WIND_CHIMES_ENTITY;

public class HardcoreLimboClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Here we will put client-only registration code
        //BlockEntityRendererRegistry.INSTANCE.register(WIND_CHIMES_ENTITY, WindChimesTileRenderer::new);
    }
}