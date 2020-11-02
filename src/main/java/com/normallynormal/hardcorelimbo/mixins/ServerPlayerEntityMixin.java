package com.normallynormal.hardcorelimbo.mixins;

import com.normallynormal.hardcorelimbo.HardcoreLimbo;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
abstract class ServerPlayerEntityMixin extends LivingEntity {

//    @Inject(method = "getBlockBreakingSpeed",at = @At("RETURN"),cancellable = true)
//    private void formlessEffect(BlockState block, CallbackInfoReturnable<Float> cir){
//        if (this.hasStatusEffect(HardcoreLimbo.FORMLESS))
//            cir.setReturnValue(0f);
//    }

    @Inject(method = "isSpectator",at = @At("HEAD"),cancellable = true)
    private void formlessEffect1(CallbackInfoReturnable<Boolean> cir){
        if (this.getScoreboardTags().contains("formless"))
            cir.setReturnValue(true);
    }

//    @Inject(method = "canModifyBlocks",at = @At("INVOKE"),cancellable = true)
//    private void formlessEffect3(CallbackInfoReturnable<Boolean> cir){
//        if (this.getScoreboardTags().contains("formless"))
//            cir.setReturnValue(false);
//    }

    @Inject(method = "onDeath",at = @At("HEAD"),cancellable = true)
    private void addFormlessToDeadPeople(DamageSource source, CallbackInfo ci) {
        this.addScoreboardTag("formless");
        this.dropInventory();
    }

    public ServerPlayerEntityMixin(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }
}

