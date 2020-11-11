package com.normallynormal.hardcorelimbo;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.example.registry.TileRegistry;
import software.bernie.geckolib3.GeckoLib;

public class HardcoreLimbo implements ModInitializer {
    //Create the blocks
    public static final WindChimes WIND_CHIMES = new WindChimes(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
    public static final SoulBinder SOUL_BINDER = new SoulBinder(FabricBlockSettings.of(Material.STONE).hardness(4.0f));
    public static final SoulGuidingLantern SOUL_GUIDING_LANTERN = new SoulGuidingLantern(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
    public static final GoldenSoulGuidingLantern GOLDEN_SOUL_GUIDING_LANTERN = new GoldenSoulGuidingLantern(FabricBlockSettings.of(Material.METAL).hardness(4.0f));
    //Create the soul binder's inventory
    public static BlockEntityType<SoulBinderEntity> SOUL_BINDER_ENTITY;
    public static BlockEntityType<WindChimesEntity> WIND_CHIMES_ENTITY;
    //Packets for soul lantern particles and the ghosts shader
    public static final Identifier LEADING_PARTICLE = new Identifier("hclimbo", "particle");
    public static final Identifier FORMLESS_SHADER = new Identifier("hclimbo", "shader");
    //Sounds
    public static final SoundEvent WIND_CHIMES_SOUND = Registry.register(Registry.SOUND_EVENT, "hclimbo.block.wind_chimes", new SoundEvent(new Identifier("hclimbo", "hclimbo.block.wind_chimes")));

    @Override
    public void onInitialize() {
        GeckoLib.initialize();
        Registry.register(Registry.BLOCK, new Identifier("hclimbo", "wind_chimes"), WIND_CHIMES);
        Registry.register(Registry.ITEM, new Identifier("hclimbo", "wind_chimes"), new BlockItem(WIND_CHIMES, new Item.Settings().group(ItemGroup.MISC)));
        WIND_CHIMES_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "hclimbo:wild_chimes", BlockEntityType.Builder.create(WindChimesEntity::new, WIND_CHIMES).build(null));
        BlockEntityRendererRegistry.INSTANCE.register(WIND_CHIMES_ENTITY, WindChimesTileRenderer::new);

        Registry.register(Registry.BLOCK, new Identifier("hclimbo", "soul_binder"), SOUL_BINDER);
        Registry.register(Registry.ITEM, new Identifier("hclimbo", "soul_binder"), new BlockItem(SOUL_BINDER, new Item.Settings().group(ItemGroup.MISC)));
        SOUL_BINDER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "hclimbo:soul_binder", BlockEntityType.Builder.create(SoulBinderEntity::new, SOUL_BINDER).build(null));

        Registry.register(Registry.BLOCK, new Identifier("hclimbo", "soul_guiding_lantern"), SOUL_GUIDING_LANTERN);
        Registry.register(Registry.ITEM, new Identifier("hclimbo", "soul_guiding_lantern"), new BlockItem(SOUL_GUIDING_LANTERN, new Item.Settings().group(ItemGroup.MISC)));

        Registry.register(Registry.BLOCK, new Identifier("hclimbo", "golden_soul_guiding_lantern"), GOLDEN_SOUL_GUIDING_LANTERN);
        Registry.register(Registry.ITEM, new Identifier("hclimbo", "golden_soul_guiding_lantern"), new BlockItem(GOLDEN_SOUL_GUIDING_LANTERN, new Item.Settings().group(ItemGroup.MISC)));
    }
}
