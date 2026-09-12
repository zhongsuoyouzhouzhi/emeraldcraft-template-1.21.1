package com.zhouzhi.emeraldcraft.listening;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import com.zhouzhi.emeraldcraft.init.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkEvent;

@EventBusSubscriber(modid = EmeraldCraft.MOD_ID)
public class WorldListening {
    private static final int SCAN_DEPTH = 16;
    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        LevelAccessor levelAccessor = event.getLevel();
        if (!(levelAccessor instanceof ServerLevel level)) return;
        if (!level.dimension().equals(ModDimensions.EMERALD_WORLD_LEVEL_KEY)) return;
        ChunkAccess chunk = event.getChunk();
        ChunkPos chunkPos = chunk.getPos();
        BlockState emeraldBlock = Blocks.EMERALD_BLOCK.defaultBlockState();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        int minY = level.getMinBuildHeight();
        int maxY = level.getMaxBuildHeight();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = chunkPos.getMinBlockX() + x;
                int worldZ = chunkPos.getMinBlockZ() + z;
                for (int y = minY; y < minY + SCAN_DEPTH && y < maxY; y++) {
                    pos.set(worldX, y, worldZ);
                    if (chunk.getBlockState(pos).is(Blocks.BEDROCK)) {
                        chunk.setBlockState(pos, emeraldBlock, false);
                    }
                }
                for (int y = Math.max(minY, maxY - SCAN_DEPTH); y < maxY; y++) {
                    pos.set(worldX, y, worldZ);
                    if (chunk.getBlockState(pos).is(Blocks.BEDROCK)) {
                        chunk.setBlockState(pos, emeraldBlock, false);
                    }
                }
            }
        }
        chunk.setUnsaved(true);
    }
}