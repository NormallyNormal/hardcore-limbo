package com.normallynormal.hardcorelimbo.mixins;

import com.normallynormal.hardcorelimbo.ClientPlayerEntityExt;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resource.ResourceManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
abstract class GameRendererMixin{

    //Stop F5 mode from removing the formless shader
    @Inject(method = "onCameraEntitySet",at = @At("HEAD"),cancellable = true)
    private void formlessShader(@Nullable Entity entity, CallbackInfo ci) {
        Entity eEntity = (((GameRendererAccessor)this).getClient().getCameraEntity());
        if(eEntity instanceof PlayerEntity && !eEntity.isSpectator() && ((ClientPlayerEntityExt)((GameRendererAccessor)this).getClient().getCameraEntity()).getFormless()){
            ci.cancel();
        }
    }

    public GameRendererMixin(MinecraftClient client, ResourceManager resourceManager, BufferBuilderStorage bufferBuilderStorage) {

    }
}
