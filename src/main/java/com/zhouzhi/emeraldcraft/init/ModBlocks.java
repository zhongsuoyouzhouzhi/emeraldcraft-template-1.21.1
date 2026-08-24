package com.zhouzhi.emeraldcraft.init;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import com.zhouzhi.emeraldcraft.block.RefinedEmeraldBlock2Block;
import com.zhouzhi.emeraldcraft.block.RefinedEmeraldBlock3Block;
import com.zhouzhi.emeraldcraft.block.RefinedEmeraldBlockBlock;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
	public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(EmeraldCraft.MOD_ID);
	public static final DeferredBlock<Block> REFINED_EMERALD_BLOCK;
	public static final DeferredBlock<Block> REFINED_EMERALD_BLOCK_2;
	public static final DeferredBlock<Block> REFINED_EMERALD_BLOCK_3;
	static {
		REFINED_EMERALD_BLOCK = REGISTRY.register("refined_emerald_block", RefinedEmeraldBlockBlock::new);
		REFINED_EMERALD_BLOCK_2 = REGISTRY.register("refined_emerald_block_2", RefinedEmeraldBlock2Block::new);
		REFINED_EMERALD_BLOCK_3 = REGISTRY.register("refined_emerald_block_3", RefinedEmeraldBlock3Block::new);
	}
	// Start of user code block custom blocks
	// End of user code block custom blocks
}