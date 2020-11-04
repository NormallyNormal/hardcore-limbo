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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity{

//    @Inject(method = "getBlockBreakingSpeed",at = @At("RETURN"),cancellable = true)
//    private void formlessEffect(BlockState block, CallbackInfoReturnable<Float> cir){
//        if (this.hasStatusEffect(HardcoreLimbo.FORMLESS))
//            cir.setReturnValue(0f);
//    }


    @Inject(method = "isBlockBreakingRestricted",at = @At("INVOKE"),cancellable = true)
    private void formlessEffect1(World world, BlockPos pos, GameMode gameMode, CallbackInfoReturnable<Boolean> cir){
        System.out.println("lastDeathLocation");
        if (this.getScoreboardTags().contains("formless"))
            cir.setReturnValue(true);
    }


    @Inject(method = "attack",at = @At("INVOKE"),cancellable = true)
    private void formlessEffect2(Entity entity, CallbackInfo ci){
        if (this.getScoreboardTags().contains("formless"))
            ci.cancel();
    }



//    @Inject(method = "canModifyBlocks",at = @At("INVOKE"),cancellable = true)
//    private void formlessEffect3(CallbackInfoReturnable<Boolean> cir){
//        if (this.getScoreboardTags().contains("formless"))
//            cir.setReturnValue(false);
//    }




    public PlayerEntityMixin(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }
}

