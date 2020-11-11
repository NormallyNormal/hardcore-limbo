package com.normallynormal.hardcorelimbo;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
//import software.bernie.geckolib.GeckoLib;

public class HardcoreLimbo implements ModInitializer {
    public static final WindChimes WIND_CHIMES = new WindChimes(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
    public static final SoulBinder SOUL_BINDER = new SoulBinder(FabricBlockSettings.of(Material.STONE).hardness(4.0f));
    public static final SoulGuidingLantern SOUL_GUIDING_LANTERN = new SoulGuidingLantern(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
    public static final GoldenSoulGuidingLantern GOLDEN_SOUL_GUIDING_LANTERN = new GoldenSoulGuidingLantern(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
//    public static BlockEntityType<WindChimesEntity> WIND_CHIMES_ENTITY;
    public static BlockEntityType<SoulBinderEntity> SOUL_BINDER_ENTITY;
//    public static final Identifier UPDATE_SOUL_BINDER_INVENTORY_CONTENTS = new Identifier("hclimbo", "inventory");
    public static final Identifier LEADING_PARTICLE = new Identifier("hclimbo", "particle");
    public static final Identifier FORMLESS_SHADER = new Identifier("hclimbo", "shader");

    @Override
    public void onInitialize() {
//        GeckoLib.initialize();
        Registry.register(Registry.BLOCK, new Identifier("hclimbo", "wind_chimes"), WIND_CHIMES);
        Registry.register(Registry.ITEM, new Identifier("hclimbo", "wind_chimes"), new BlockItem(WIND_CHIMES, new Item.Settings().group(ItemGroup.MISC)));

        //WIND_CHIMES_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "hclimbo:wild_chimes", BlockEntityType.Builder.create(WindChimesEntity::new, WIND_CHIMES).build(null));
        Registry.register(Registry.BLOCK, new Identifier("hclimbo", "soul_binder"), SOUL_BINDER);
        Registry.register(Registry.ITEM, new Identifier("hclimbo", "soul_binder"), new BlockItem(SOUL_BINDER, new Item.Settings().group(ItemGroup.MISC)));

        SOUL_BINDER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "hclimbo:soul_binder", BlockEntityType.Builder.create(SoulBinderEntity::new, SOUL_BINDER).build(null));
        Registry.register(Registry.BLOCK, new Identifier("hclimbo", "soul_guiding_lantern"), SOUL_GUIDING_LANTERN);
        Registry.register(Registry.ITEM, new Identifier("hclimbo", "soul_guiding_lantern"), new BlockItem(SOUL_GUIDING_LANTERN, new Item.Settings().group(ItemGroup.MISC)));

        Registry.register(Registry.BLOCK, new Identifier("hclimbo", "golden_soul_guiding_lantern"), GOLDEN_SOUL_GUIDING_LANTERN);
        Registry.register(Registry.ITEM, new Identifier("hclimbo", "golden_soul_guiding_lantern"), new BlockItem(GOLDEN_SOUL_GUIDING_LANTERN, new Item.Settings().group(ItemGroup.MISC)));

    }
}
