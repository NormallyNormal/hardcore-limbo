package com.normallynormal.hardcorelimbo;

import net.minecraft.block.entity.BlockEntity;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class WindChimesEntity extends BlockEntity implements IAnimatable{
    private AnimationFactory factory = new AnimationFactory(this);
    public WindChimesEntity() {
        super(HardcoreLimbo.WIND_CHIMES_ENTITY);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    private <P extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
//        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wind_chimes.new", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public void play(){
        System.out.println("play");
        AnimationController controller = this.factory.getOrCreateAnimationData(this.hashCode()).getAnimationControllers().get("controller");
        System.out.println("play1");
        System.out.println("play2");
        controller.setAnimation(new AnimationBuilder().addAnimation("animation.wind_chimes.new", false));
        System.out.println("play3");
    }
}
