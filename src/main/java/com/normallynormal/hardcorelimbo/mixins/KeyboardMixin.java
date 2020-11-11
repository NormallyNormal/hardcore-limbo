package com.normallynormal.hardcorelimbo.mixins;

import com.normallynormal.hardcorelimbo.ClientPlayerEntityExt;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Inject(method = "onKey", at = @At("TAIL"))
    private void injected(long window, int key, int scancode, int i, int j, CallbackInfo ci) {
        if(key == 290 && ((ClientPlayerEntityExt)((KeyboardAccessor)this).getClient().getCameraEntity()).getFormless()){
            ((KeyboardAccessor)this).getClient().options.hudHidden = true;
        }
    }
}
