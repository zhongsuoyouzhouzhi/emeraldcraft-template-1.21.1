package com.zhouzhi.emeraldcraft.listening;

import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import com.zhouzhi.emeraldcraft.procedures.net.Use;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

public class TickListening {
    @SubscribeEvent
    public void serverTick(ServerTickEvent.Post event) {
        if (!event.hasTime()){
            return;
        }
        event.getServer().getAllLevels().forEach(level -> {
            if (level.isClientSide()) return;
            Use.EntityPause.tickPausedEntities(level,10);
            for (Entity entity : level.getEntities().getAll()) {
                if (entity instanceof LivingEntity livingEntity) {
                    if (TagChange.getOrCreateComponent(livingEntity, "ShouldBeKilled", false)) {
                        if (livingEntity.getHealth() > 0) {
                            livingEntity.setHealth(0);
                            livingEntity.setInvulnerable(false);
                            livingEntity.hurt(livingEntity.damageSources().genericKill(), Float.MAX_VALUE);
                            if (livingEntity.getHealth() <= 0) {
                                TagChange.saveComponent(livingEntity, "ShouldBeKilled", false);
                            }
                        }
                    }
                }
            }
        });
    }
}
