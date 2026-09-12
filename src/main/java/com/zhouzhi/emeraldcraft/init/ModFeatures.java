package com.zhouzhi.emeraldcraft.init;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> EMERALD_ORE_CF =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "emerald_ore_cf"));
    public static final ResourceKey<PlacedFeature> EMERALD_ORE_PF =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "emerald_ore_pf"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> EMERALD_BLOCK_CF =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "emerald_block_cf"));
    public static final ResourceKey<PlacedFeature> EMERALD_BLOCK_PF =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "emerald_block_pf"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> REFINED_EMERALD_BLOCK_CF =
            ResourceKey.create(Registries.CONFIGURED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "refined_emerald_block_cf"));
    public static final ResourceKey<PlacedFeature> REFINED_EMERALD_BLOCK_PF =
            ResourceKey.create(Registries.PLACED_FEATURE,
                    ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "refined_emerald_block_pf"));

    public static void bootstrapConfigured(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        context.register(EMERALD_ORE_CF, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(List.of(
                        OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
                                Blocks.EMERALD_ORE.defaultBlockState()),
                        OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES),
                                Blocks.DEEPSLATE_EMERALD_ORE.defaultBlockState())
                ), 8)));

        context.register(EMERALD_BLOCK_CF, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(List.of(
                        OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
                                Blocks.EMERALD_BLOCK.defaultBlockState())
                ), 4)));

        
        context.register(REFINED_EMERALD_BLOCK_CF, new ConfiguredFeature<>(Feature.ORE,
                new OreConfiguration(List.of(
                        OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES),
                                ModBlocks.REFINED_EMERALD_BLOCK.get().defaultBlockState())
                ), 3)));
    }

    public static void bootstrapPlaced(BootstrapContext<PlacedFeature> context) {
        var cf = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(EMERALD_ORE_PF, new PlacedFeature(
                cf.getOrThrow(EMERALD_ORE_CF),
                List.of(
                        CountPlacement.of(10),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(320)),
                        BiomeFilter.biome()
                )
        ));

        context.register(EMERALD_BLOCK_PF, new PlacedFeature(
                cf.getOrThrow(EMERALD_BLOCK_CF),
                List.of(
                        CountPlacement.of(2),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64)),
                        BiomeFilter.biome()
                )
        ));

        context.register(REFINED_EMERALD_BLOCK_PF, new PlacedFeature(
                cf.getOrThrow(REFINED_EMERALD_BLOCK_CF),
                List.of(
                        CountPlacement.of(1),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32)),
                        BiomeFilter.biome()
                )
        ));
    }
}
