package com.normallynormal.hardcorelimbo.mixins;

import com.normallynormal.hardcorelimbo.ClientPlayerEntityExt;
import com.normallynormal.hardcorelimbo.GameRendererExt;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SynchronousResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
abstract class GameRendererMixin{

//    @Inject(method = "getBlockBreakingSpeed",at = @At("RETURN"),cancellable = true)
//    private void formlessEffect(BlockState block, CallbackInfoReturnable<Float> cir){
//        if (this.hasStatusEffect(HardcoreLimbo.FORMLESS))
//            cir.setReturnValue(0f);
//    }

    boolean hasFormlessShader = false;
//
    @Inject(method = "onCameraEntitySet",at = @At("HEAD"),cancellable = true)
    private void formlessShader(@Nullable Entity entity, CallbackInfo ci) {
//        System.out.println("1:" + (((GameRendererAccessor)this).getClient().getCameraEntity() instanceof PlayerEntity));
//        if(((GameRendererAccessor)this).getClient().getCameraEntity() != null) {
//            System.out.println("2:" + ((GameRendererAccessor)this).getClient().getCameraEntity().isInvisible());
//            System.out.println("3:" + ((GameRendererAccessor)this).getClient().getCameraEntity().getClass());
//        }
        Entity eEntity = (((GameRendererAccessor)this).getClient().getCameraEntity());
        if(eEntity instanceof PlayerEntity && !eEntity.isSpectator() && ((ClientPlayerEntityExt)((GameRendererAccessor)this).getClient().getCameraEntity()).getFormless()){
            ci.cancel();
        }
    }



//    @Inject(method = "isBlockBreakingRestricted",at = @At("INVOKE"),cancellable = true)
//    private void formlessEffect2(World world, BlockPos pos, GameMode gameMode, CallbackInfoReturnable<Boolean> cir){
//        if (this.getScoreboardTags().contains("formless"))
//            cir.setReturnValue(true);
//    }

    public GameRendererMixin(MinecraftClient client, ResourceManager resourceManager, BufferBuilderStorage bufferBuilderStorage) {

    }
}
