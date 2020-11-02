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

//    @Inject(method = "getBlockBreakingSpeed",at = @At("RETURN"),cancellable = true)
//    private void formlessEffect(BlockState block, CallbackInfoReturnable<Float> cir){
//        if (this.hasStatusEffect(HardcoreLimbo.FORMLESS))
//            cir.setReturnValue(0f);
//    }

    @Inject(method = "onLandedUpon",at = @At("INVOKE"),cancellable = true)
    private void formlesssEffect(World world, BlockPos pos, Entity entity, float distance, CallbackInfo callbackInfo){
        if (true)
            callbackInfo.cancel();
    }

    public FarmlandMixin(Settings settings) {
        super(settings);
    }
}