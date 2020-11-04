package com.normallynormal.hardcorelimbo.mixins;

import com.normallynormal.hardcorelimbo.PlayerEntityExt;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.WorldSaveHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin{
    public PlayerManagerMixin(MinecraftServer server, DynamicRegistryManager.Impl registryManager, WorldSaveHandler saveHandler, int maxPlayers) {

    }
//    @ModifyVariable(method = "respawnPlayer",at = @At(value = "STORE"),ordinal = 0)
//    private ServerPlayerEntity inject(ServerPlayerEntity serverPlayerEntity){
//        return this.
//    }
}
