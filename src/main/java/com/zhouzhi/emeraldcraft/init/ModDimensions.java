package com.zhouzhi.emeraldcraft.init;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.OptionalLong;

public class ModDimensions {
    public static final ResourceKey<LevelStem> EMERALD_WORLD =
            ResourceKey.create(Registries.LEVEL_STEM,
                    ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "emerald_world"));
    public static final ResourceKey<Level> EMERALD_WORLD_LEVEL_KEY =
            ResourceKey.create(Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "emerald_world"));
    public static final ResourceKey<DimensionType> EMERALD_WORLD_DIMENSION_TYPE_KEY =
            ResourceKey.create(Registries.DIMENSION_TYPE,
                    ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "emerald_world_type"));

    public static void bootstrap(BootstrapContext<DimensionType> context) {
        context.register(EMERALD_WORLD_DIMENSION_TYPE_KEY, new DimensionType(
                OptionalLong.of(12000),               //固定时间
                true,                                       //天空光照
                false,                                      //基岩顶层
                false,                                      //放水
                true,                                       //自然维度
                16.0,                                       //坐标缩放比例
                false,                                       //床
                false,                                      //重生锚
                -64,                                        //最低高度
                256,                                        //总高度
                256,                                        //逻辑高度
                ModTags.EMERALD_BLOCKS,                     //火焰不灭的方块标签
                BuiltinDimensionTypes.END_EFFECTS,          //渲染效果
                8.0f,                                       //环境光照
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)
        ));
    }

    public static void bootstrapLevelStem(BootstrapContext<LevelStem> context) {
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);

        context.register(EMERALD_WORLD, new LevelStem(
                dimensionTypes.getOrThrow(EMERALD_WORLD_DIMENSION_TYPE_KEY),
                new NoiseBasedChunkGenerator(
                        new FixedBiomeSource(biomes.getOrThrow(ModBiomes.EMERALD_BIOME)),
                        noiseSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD)
                )
        ));
    }

}
