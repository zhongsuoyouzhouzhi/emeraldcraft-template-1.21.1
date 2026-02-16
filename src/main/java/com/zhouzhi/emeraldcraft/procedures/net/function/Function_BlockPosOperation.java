package com.zhouzhi.emeraldcraft.procedures.net.function;

import net.minecraft.world.level.block.Block;

@FunctionalInterface
public interface Function_BlockOperation {
    void run(Block block, int x, int y, int z);
}