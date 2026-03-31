package com.zhouzhi.emeraldcraft.block;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RefinedemeraldBlockBlock extends Block {
	public static final IntegerProperty EMERALDED = IntegerProperty.create("emeralded", 1, 5);

	public RefinedemeraldBlockBlock() {
		super(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(6f).lightLevel(s -> 3).requiresCorrectToolForDrops().hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true)
				.instrument(NoteBlockInstrument.IRON_XYLOPHONE));
		this.registerDefaultState(this.stateDefinition.any().setValue(EMERALDED, 1));
	}

	@Override
	public int getLightBlock(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
		return 15;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(EMERALDED);
	}

	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		return Objects.requireNonNull(super.getStateForPlacement(context)).setValue(EMERALDED, 1);
	}
}