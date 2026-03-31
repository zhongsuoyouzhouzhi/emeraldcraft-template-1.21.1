package com.zhouzhi.emeraldcraft.procedures.others;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import static com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse.getEntityGameType;


public class EmeraldShovelT3Right_clickOnAir {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack item) {
	int radius = 4;
	int dirtnum = 0;
	if (entity == null)
		return;
	for (int dx = -radius; dx <= radius; dx++) {
		for (int dy = -radius; dy <= radius; dy++) {
			for (int dz = -radius; dz <= radius; dz++) {
				int blockX = (int)x + dx;
				int blockY = (int)y + dy;
				int blockZ = (int)z + dz;
				Block blockasd = (world.getBlockState(BlockPos.containing(blockX, blockY, blockZ))).getBlock();
				if (blockasd == Blocks.DIRT || blockasd == Blocks.GRASS_BLOCK){
					BlockPos pos = BlockPos.containing(blockX, blockY, blockZ);
					Block.dropResources(world.getBlockState(pos), world, BlockPos.containing(blockX, blockY, blockZ), null);
					world.destroyBlock(pos, false);
					dirtnum++;
					}
				}
			}
		}
	if (!(getEntityGameType(entity) == GameType.CREATIVE || getEntityGameType(entity) == GameType.SPECTATOR)) {
		if (world instanceof ServerLevel _level && entity instanceof Player _player) {
			item.hurtAndBreak(dirtnum, _level, _player, _stkprov -> {
				});
			}
		}
	}
}