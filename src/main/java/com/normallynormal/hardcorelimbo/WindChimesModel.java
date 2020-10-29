package com.normallynormal.hardcorelimbo;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.AnimatedGeoModel;

public class WindChimesModel extends AnimatedGeoModel<WindChimesEntity> {

    @Override
    public Identifier getModelLocation(WindChimesEntity object) {
        return new Identifier("hclimbo", "geo/wind_chimes.geo.json");
    }

    @Override
    public Identifier getTextureLocation(WindChimesEntity object) {
        return new Identifier("hclimbo" + "textures/block/wind_chimes.png");
    }

    @Override
    public Identifier getAnimationFileLocation(WindChimesEntity object) {
        return new Identifier("hclimbo", "animations/wind_chimes.animation.json");
    }
}
