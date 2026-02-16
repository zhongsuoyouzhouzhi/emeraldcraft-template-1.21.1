package com.zhouzhi.emeraldcraft.procedures.others;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftBlocks;

public class RefinedEmeraldRight_clickOnBlock {
	public static void execute(LevelAccessor world, double x, double y, double z, ItemStack itemstack) {
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == EmeraldcraftBlocks.REFINEDEMERALD_BLOCK.get()) {
			{
				BlockPos _bp = BlockPos.containing(x, y, z);
				BlockState _bs = EmeraldcraftBlocks.REFINEDEMERALD_BLOCK_2.get().defaultBlockState();
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
			world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(EmeraldcraftBlocks.REFINEDEMERALD_BLOCK.get().defaultBlockState()));
			itemstack.shrink(1);
		}
	}
}