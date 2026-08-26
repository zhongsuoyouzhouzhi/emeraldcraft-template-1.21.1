package com.zhouzhi.emeraldcraft.item.remined_emerald;

import com.zhouzhi.emeraldcraft.init.ModBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

public class RefinedEmeraldItem extends Item {
	public RefinedEmeraldItem() {
		super(new Item.Properties().rarity(Rarity.UNCOMMON));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
		return true;
	}

	@Override
	@MethodsReturnNonnullByDefault
	public InteractionResult useOn(@ParametersAreNonnullByDefault UseOnContext context) {
		super.useOn(context);
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();
		ItemStack stack = context.getItemInHand();
		if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == ModBlocks.REFINED_EMERALD_BLOCK.get()) {
			BlockPos _bp = BlockPos.containing(x, y, z);
			BlockState _bs = ModBlocks.REFINED_EMERALD_BLOCK_2.get().defaultBlockState();
			BlockState _bso = world.getBlockState(_bp);
			for (Property<?> _propertyOld : _bso.getProperties()) {
				Property _propertyNew = _bs.getBlock().getStateDefinition().getProperty(_propertyOld.getName());
				if (_propertyNew != null) {
					_bs.getValue(_propertyNew);
					try {
						_bs = _bs.setValue(_propertyNew, _bso.getValue(_propertyOld));
					} catch (Exception ignored) {
					}
				}
			}
			world.setBlock(_bp, _bs, 3);
			world.levelEvent(2001, BlockPos.containing(x, y, z), Block.getId(ModBlocks.REFINED_EMERALD_BLOCK.get().defaultBlockState()));
			stack.shrink(1);
		}
		return InteractionResult.SUCCESS;
	}
}