package com.normallynormal.hardcorelimbo;

import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import software.bernie.geckolib3.renderer.geo.GeoBlockRenderer;

public class WindChimesTileRenderer extends GeoBlockRenderer<WindChimesEntity> {
    public WindChimesTileRenderer(BlockEntityRenderDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn, new WindChimesModel());
    }
}
