package com.zhouzhi.emeraldcraft.block;

import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

import com.zhouzhi.emeraldcraft.procedures.others.RefinedEmeraldBlock3IsDestroyedByBoom;

public class RefinedemeraldBlock3Block extends Block {
	public RefinedemeraldBlock3Block() {
		super(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(15f, 20f).lightLevel(s -> 12).requiresCorrectToolForDrops().pushReaction(PushReaction.BLOCK).hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true)
				.instrument(NoteBlockInstrument.IRON_XYLOPHONE));
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 15;
	}

	@Override
	public void wasExploded(Level world, BlockPos pos, Explosion e) {
		super.wasExploded(world, pos, e);
		RefinedEmeraldBlock3IsDestroyedByBoom.execute(world, pos.getX(), pos.getY(), pos.getZ());
	}
}