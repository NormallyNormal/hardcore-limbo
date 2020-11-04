package com.normallynormal.hardcorelimbo.mixins;

import com.normallynormal.hardcorelimbo.HardcoreLimbo;
import com.normallynormal.hardcorelimbo.PlayerEntityExt;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin extends Entity implements PlayerEntityExt {

//    @Inject(method = "getBlockBreakingSpeed",at = @At("RETURN"),cancellable = true)
//    private void formlessEffect(BlockState block, CallbackInfoReturnable<Float> cir){
//        if (this.hasStatusEffect(HardcoreLimbo.FORMLESS))
//            cir.setReturnValue(0f);
//    }

//    @Inject(method = "clearStatusEffects",at = @At("INVOKE"),cancellable = true)
//    private void formlesssEffect(CallbackInfoReturnable<Boolean> cir){
//        if (this.getScoreboardTags().contains("NoEffectClearing"))
//            cir.cancel();
//    }



    public LivingEntityMixin(EntityType<? extends Entity> type, World world) {
        super(type, world);
    }
}
