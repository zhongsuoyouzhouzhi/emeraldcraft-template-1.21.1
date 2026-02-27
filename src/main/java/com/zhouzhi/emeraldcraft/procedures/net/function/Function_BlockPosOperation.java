package com.zhouzhi.emeraldcraft.procedures.net.function;

import net.minecraft.core.BlockPos;

@FunctionalInterface
public interface Function_BlockPosOperation {
    void run(BlockPos blockpos, int x, int y, int z);
}