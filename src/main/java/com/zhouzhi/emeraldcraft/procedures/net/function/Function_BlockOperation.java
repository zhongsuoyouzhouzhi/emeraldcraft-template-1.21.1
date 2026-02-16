package com.zhouzhi.emeraldcraft.procedures.net;

import net.minecraft.world.level.block.Block;

@FunctionalInterface
public interface Function_OperateBlock {
    void run(Block block, int x, int y, int z);
}