package com.normallynormal.hardcorelimbo;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib.GeckoLib;

public class HardcoreLimbo implements ModInitializer {
    public static final WindChimes WIND_CHIMES = new WindChimes(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
    public static BlockEntityType<WindChimesEntity> WIND_CHIMES_ENTITY;

    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier("hclimbo", "wind_chimes"), WIND_CHIMES);
        Registry.register(Registry.ITEM, new Identifier("hclimbo", "wind_chimes"), new BlockItem(WIND_CHIMES, new Item.Settings().group(ItemGroup.MISC)));
        BlockRenderLayerMap.INSTANCE.putBlock(HardcoreLimbo.WIND_CHIMES, RenderLayer.getCutout());
        WIND_CHIMES_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "hclimbo:wild_chimes", BlockEntityType.Builder.create(WindChimesEntity::new, WIND_CHIMES).build(null));
        GeckoLib.initialize();
    }
}
