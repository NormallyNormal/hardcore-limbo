package com.normallynormal.hardcorelimbo.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmlandBlock.class)
abstract class FarmlandMixin extends Block {

    //Stop ghosts from trampling crops
    @Inject(method = "onLandedUpon",at = @At("INVOKE"),cancellable = true)
    private void formlessEffect(World world, BlockPos pos, Entity entity, float distance, CallbackInfo callbackInfo){
        if (entity.getScoreboardTags().contains("formless"))
            callbackInfo.cancel();
    }

    public FarmlandMixin(Settings settings) {
        super(settings);
    }
}