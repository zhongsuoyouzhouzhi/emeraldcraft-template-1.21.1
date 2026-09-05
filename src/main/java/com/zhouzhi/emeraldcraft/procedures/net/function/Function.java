package com.zhouzhi.emeraldcraft.procedures.net.function;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;

public class Function {
    @FunctionalInterface
    public interface Function_BlockOperation {
        void run(Block block, int x, int y, int z);
    }
    @FunctionalInterface
    public interface Function_BlockPosOperation {
        void run(BlockPos blockpos, int x, int y, int z);
    }
    @FunctionalInterface
    public interface Function_EntityOperation {
        void run(Entity entity);
    }
    @FunctionalInterface
    public interface Function_Operation<T,Y> {
        void run(T vars,Y vars2);
    }
}
