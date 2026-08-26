package com.zhouzhi.emeraldcraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

public class RefinedEmeraldBlock3Block extends Block {
	public RefinedEmeraldBlock3Block() {
		super(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(15f, 20f).lightLevel(s -> 12).requiresCorrectToolForDrops().pushReaction(PushReaction.BLOCK).hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true)
				.instrument(NoteBlockInstrument.IRON_XYLOPHONE));
	}

	@Override
	public int getLightBlock(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
		return 15;
	}

	@Override
	public void wasExploded(@NotNull Level world, @NotNull BlockPos pos, @NotNull Explosion e) {
		super.wasExploded(world, pos, e);
		double x = pos.getX();
		double y = pos.getY();
		double z = pos.getZ();
		if (!world.isClientSide()) {
			boom(world, x, y, z);
			_boom(world, x , y, z, 1);
			_boom(world, x, y, z, -1);
		}
	}

	private static void boom(Level level, double x, double y, double z) {
		level.explode(null, x, y, z, 128, Level.ExplosionInteraction.BLOCK);
	}
	private static void _boom(Level level, double x, double y, double z,int i) {
		final int a = 16;
		boom(level,x + i * a, y, z);
		boom(level, x, y + i * a, z);
		boom(level, x, y, z + i * a);
	}
}