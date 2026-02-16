package com.zhouzhi.emeraldcraft.procedures.net;

import net.minecraft.world.entity.Entity;

@FunctionalInterface
public interface Function_OperateEntity {
    void run(Entity entity);
}
