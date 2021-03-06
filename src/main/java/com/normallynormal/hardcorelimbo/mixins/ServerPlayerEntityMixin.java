package com.normallynormal.hardcorelimbo.mixins;

import com.mojang.authlib.GameProfile;
import com.normallynormal.hardcorelimbo.HardcoreLimbo;
import com.normallynormal.hardcorelimbo.PlayerEntityExt;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
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
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;



@Mixin(ServerPlayerEntity.class)
abstract class ServerPlayerEntityMixin extends PlayerEntity implements PlayerEntityExt{

    //Store death and recovery locations
    private Vec3d lastDeathLocation;
    private Vec3d soulRecoveryPoint;
    private Random randomizer = new Random(System.currentTimeMillis());

    //Apply some spectator mode things to ghosts so I don't have to program all that
    @Inject(method = "isSpectator",at = @At("HEAD"),cancellable = true)
    private void formlessEffect1(CallbackInfoReturnable<Boolean> cir){
        if (this.getScoreboardTags().contains("formless"))
            cir.setReturnValue(true);
    }

    //Ensure that all ghosts have the shader, hidden HUD when created.
    @Inject(method = "onSpawn",at = @At("HEAD"),cancellable = true)
    private void formlessEffect3(CallbackInfo ci){
        System.out.println(this.getScoreboardTags().contains("formless"));
        if(!world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY) && this.getScoreboardTags().contains("formless")) {
            PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
            passedData.writeBoolean(true);
            ServerSidePacketRegistry.INSTANCE.sendToPlayer(this.world.getPlayerByUuid(this.getUuid()), HardcoreLimbo.FORMLESS_SHADER, passedData);
        }
    }

    //Pass on death location and recovery location to next instance after dying
    @Inject(method = "copyFrom",at = @At(value = "TAIL"),cancellable = true)
    private void copyDeathData(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        ((PlayerEntityExt)this).setLastDeathLocation(((PlayerEntityExt)oldPlayer).getLastDeathLocation());
        ((PlayerEntityExt)this).setSoulRecoveryPoint(((PlayerEntityExt)oldPlayer).getSoulRecoveryPoint());
    }

    //Store death location and recovery location (random) on death
    @Inject(method = "onDeath",at = @At(value = "TAIL"),cancellable = true)
    private void addFormless(DamageSource source, CallbackInfo ci) {
        if(!world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
            Vec3d lastDeathLocation = new Vec3d((int) this.getX(), (int) this.getY(), (int) this.getZ());
            Vec3d soulRecoveryPoint = new Vec3d((int) (this.getX() + (1000 * randomizer.nextDouble() - 500)), 0, (int) (this.getZ() + (1000 * randomizer.nextDouble() - 500)));
            ((PlayerEntityExt) this).setLastDeathLocation(lastDeathLocation);
            ((PlayerEntityExt) this).setSoulRecoveryPoint(soulRecoveryPoint);
            this.addScoreboardTag("formless");
            //Spectators don't set XP to zero so we do this
            this.experienceLevel = 0;
            this.experienceProgress = 0;
        }
        this.removeScoreboardTag("form3hearts");
        this.removeScoreboardTag("form6hearts");
        this.removeScoreboardTag("energy3food");
        this.removeScoreboardTag("energy6food");
        this.removeScoreboardTag("mind2divide");
        this.removeScoreboardTag("mind4divide");
    }

    //Stop server player from attacking
    @Inject(method = "attack",at = @At("INVOKE"),cancellable = true)
    private void formlessEffect2(Entity entity, CallbackInfo ci){
        if (this.getScoreboardTags().contains("formless"))
            ci.cancel();
    }

    //Store recovery and death locations to NBT data
    @Inject(method = "writeCustomDataToTag", at = @At("HEAD"),cancellable = true)
    private void writeCustomDataToTag(CompoundTag tag, CallbackInfo ci) {
        if(lastDeathLocation != null && soulRecoveryPoint != null){
            tag.putInt("lastDeathLocationX", (int)lastDeathLocation.getX());
            tag.putInt("lastDeathLocationY", (int)lastDeathLocation.getY());
            tag.putInt("lastDeathLocationZ", (int)lastDeathLocation.getZ());
            tag.putInt("soulRecoveryPointX", (int)soulRecoveryPoint.getX());
            tag.putInt("soulRecoveryPointZ", (int)soulRecoveryPoint.getZ());
        }
    }

    //Read recovery and death locations from NBT data
    @Inject(method = "readCustomDataFromTag", at = @At("TAIL"),cancellable = true)
    private void readCustomDataFromTag(CompoundTag tag, CallbackInfo ci) {
        lastDeathLocation = new Vec3d(tag.getInt("lastDeathLocationX"),tag.getInt("lastDeathLocationY"),tag.getInt("lastDeathLocationZ"));
        soulRecoveryPoint = new Vec3d(tag.getInt("soulRecoveryPointX"),0,tag.getInt("soulRecoveryPointZ"));
    }

    public Vec3d getLastDeathLocation(){
        return lastDeathLocation;
    }

    public Vec3d getSoulRecoveryPoint(){
        return soulRecoveryPoint;
    }

    public void setLastDeathLocation(Vec3d pos){
        lastDeathLocation = pos;
    }

    public void setSoulRecoveryPoint(Vec3d pos){
        soulRecoveryPoint = pos;
    }

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }
}

