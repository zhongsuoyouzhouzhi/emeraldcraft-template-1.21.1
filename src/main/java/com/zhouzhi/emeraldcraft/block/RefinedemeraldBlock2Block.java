package com.zhouzhi.emeraldcraft.block;

import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

public class RefinedemeraldBlock2Block extends Block {
	public RefinedemeraldBlock2Block() {
		super(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(7f, 6f).lightLevel(s -> 4).requiresCorrectToolForDrops().pushReaction(PushReaction.BLOCK).hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true)
				.instrument(NoteBlockInstrument.IRON_XYLOPHONE));
	}

	@Override
	public int getLightBlock(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
		return 15;
	}
}