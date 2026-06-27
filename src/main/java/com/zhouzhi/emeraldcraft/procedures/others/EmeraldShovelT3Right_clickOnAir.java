package com.zhouzhi.emeraldcraft.procedures.others;

import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import static com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse.GameTypeGetter.isCreativeOrSpectator;


public class EmeraldShovelT3Right_clickOnAir {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack item) {
	int radius = 4;
	int dirt_num = 0;
	if (entity == null)
		return;
	if (entity instanceof ServerPlayer serverPlayer)
		dirt_num = SimpleUse.destroyDirt(world,(int)x,(int)y,(int)z,radius,true,serverPlayer);
	else {
		for (int dx = -radius; dx <= radius; dx++) {
			for (int dy = -radius; dy <= radius; dy++) {
				for (int dz = -radius; dz <= radius; dz++) {
					int blockX = (int) x + dx;
					int blockY = (int) y + dy;
					int blockZ = (int) z + dz;
					Block block = (world.getBlockState(BlockPos.containing(blockX, blockY, blockZ))).getBlock();
					if (block == Blocks.DIRT || block == Blocks.GRASS_BLOCK) {
						BlockPos pos = BlockPos.containing(blockX, blockY, blockZ);
						Block.dropResources(world.getBlockState(pos), world, BlockPos.containing(blockX, blockY, blockZ), null);
						world.destroyBlock(pos, false);
						dirt_num++;
					}
				}
			}
		}
	}
	if (!(isCreativeOrSpectator(entity))) {
		if (world instanceof ServerLevel _level && entity instanceof Player _player) {
			item.hurtAndBreak(dirt_num, _level, _player, a -> {
				});
			}
		}
	}
}