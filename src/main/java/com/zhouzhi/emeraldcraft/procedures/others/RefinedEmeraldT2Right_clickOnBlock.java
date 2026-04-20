package com.zhouzhi.emeraldcraft.procedures.others;

import com.zhouzhi.emeraldcraft.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class RefinedEmeraldT2Right_clickOnBlock {
	public static void execute(LevelAccessor world, double x, double y, double z, ItemStack itemstack) {
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == ModBlocks.REFINED_EMERALD_BLOCK_2.get()) {
			{
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockState _bs = ModBlocks.REFINED_EMERALD_BLOCK_3.get().defaultBlockState();
				BlockState _bso = world.getBlockState(_bp);
				for (Property<?> _propertyOld : _bso.getProperties()) {
					Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
					if (_propertyNew != null && _bs.getValue(_propertyNew) != null)
						try {
							_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
						} catch (Exception e) {
						}
				}
				world.setBlock(_bp, _bs, 3);
			}
			world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(ModBlocks.REFINED_EMERALD_BLOCK_2.get().defaultBlockState()));
			itemstack.shrink(1);
		}
	}
}