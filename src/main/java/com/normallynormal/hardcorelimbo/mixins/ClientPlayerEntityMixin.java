package com.normallynormal.hardcorelimbo.mixins;

import com.normallynormal.hardcorelimbo.ClientPlayerEntityExt;
import com.normallynormal.hardcorelimbo.HardcoreLimbo;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
abstract class ClientPlayerEntityMixin extends LivingEntity implements ClientPlayerEntityExt {

//    @Inject(method = "getBlockBreakingSpeed",at = @At("RETURN"),cancellable = true)
//    private void formlessEffect(BlockState block, CallbackInfoReturnable<Float> cir){
//        if (this.hasStatusEffect(HardcoreLimbo.FORMLESS))
//            cir.setReturnValue(0f);
//    }
    boolean formless = false;

    @Inject(method = "shouldSpawnSprintingParticles",at = @At("RETURN"),cancellable = true)
    private void formlesssEffect(CallbackInfoReturnable<Boolean> cir) {
        if (this.isInvisible())
            cir.setReturnValue(false);
    }


//    @Inject(method = "isBlockBreakingRestricted",at = @At("INVOKE"),cancellable = true)
//    private void formlessEffect2(World world, BlockPos pos, GameMode gameMode, CallbackInfoReturnable<Boolean> cir){
//        if (this.getScoreboardTags().contains("formless"))
//            cir.setReturnValue(true);
//    }


    public void setFormless(boolean formless) {
        this.formless = formless;
    }

    public boolean getFormless(){
        return formless;
    }

    public ClientPlayerEntityMixin(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }
}
