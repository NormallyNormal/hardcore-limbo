package com.normallynormal.hardcorelimbo.mixins;

import com.normallynormal.hardcorelimbo.ClientPlayerEntityExt;
import com.normallynormal.hardcorelimbo.HardcoreLimbo;
import com.normallynormal.hardcorelimbo.PlayerEntityExt;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
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
        if (this.getScoreboardTags().contains("formless"))
            cir.setReturnValue(true);
    }



    @Inject(method = "attack",at = @At("INVOKE"),cancellable = true)
    private void formlessEffect2(Entity entity, CallbackInfo ci){
        if (this.getScoreboardTags().contains("formless"))
            ci.cancel();
    }

    @Inject(method = "tick",at = @At("HEAD"),cancellable = true)
    private void visitDeathLocation(CallbackInfo ci) {
        if (this.getScoreboardTags().contains("formless") && !this.getScoreboardTags().contains("formless2") && this.isAlive() && ((PlayerEntityExt)this).getLastDeathLocation() != null) {
            if (((PlayerEntityExt) this).getLastDeathLocation().isInRange(this.getPos(), 5)) {
                System.out.println(this.getName().asString() + " found their death point.");
                this.addScoreboardTag("formless2");
                this.sendSystemMessage(new LiteralText("This location awakens something inside you..."), Util.NIL_UUID);
            }
        }
        if(this.isAlive()) {
            if (world.getPlayerByUuid(this.getUuid()).getHungerManager().getFoodLevel() > 6.0 && this.getScoreboardTags().contains("energy3food")) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 10, 1));
            } else if (world.getPlayerByUuid(this.getUuid()).getHungerManager().getFoodLevel() > 12.0 && this.getScoreboardTags().contains("energy6food")) {
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 10, 1));
            }
        }
    }
    @ModifyVariable(method = "addExperience(I)V", at = @At("HEAD"), ordinal = 0)
    private int injected(int y) {
        double divisor;
        if(this.getScoreboardTags().contains("mind2divide")){
            divisor = 1.5;
        } else if(this.getScoreboardTags().contains("mind4divide")){
            divisor = 3;
        } else {
            return y;
        }
        Random randomizer = new Random(System.currentTimeMillis());
        double z = ((double)y / divisor) % 1;
        if(randomizer.nextDouble() < z){
            return (int)(y / divisor) + 1;
        } else {
            return (int)(y / divisor);
        }
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

