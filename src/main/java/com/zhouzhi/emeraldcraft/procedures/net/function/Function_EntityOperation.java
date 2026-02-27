package com.zhouzhi.emeraldcraft.procedures.net.function;

import net.minecraft.world.entity.Entity;

@FunctionalInterface
public interface Function_EntityOperation {
    void run(Entity entity);
}
