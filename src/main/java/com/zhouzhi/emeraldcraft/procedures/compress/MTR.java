package com.zhouzhi.emeraldcraft.procedures.compress;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.TickTask;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MTR {
    
    public static void execute(Entity source, float radius, float damage, int delayTicks) {
        if (!(source.level() instanceof ServerLevel serverLevel)) return;
        
        double x = source.getX();
        double y = source.getY();
        double z = source.getZ();
        double radiusSq = radius * radius;
        
        List<UUID> affectedEntities = new ArrayList<>();
        
        List<LivingEntity> entities = serverLevel.getEntitiesOfClass(
            LivingEntity.class,
            new AABB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius),
            entity -> entity != source && entity.distanceToSqr(x, y, z) <= radiusSq
        );
        
        // 施加256级效果（255级放大器）
        for (LivingEntity entity : entities) {
            // 效果持续到伤害发生的时间点
            entity.addEffect(new MobEffectInstance(
                MobEffects.MOVEMENT_SLOWDOWN, 
                delayTicks, 
                255, // 255级放大器 = 256级效果
                false,
                false
            ));
            
            entity.addEffect(new MobEffectInstance(
                MobEffects.WEAKNESS,
                delayTicks,
                255,
                false,
                false
            ));
            
            entity.addEffect(new MobEffectInstance(
                MobEffects.DIG_SLOWDOWN,
                delayTicks,
                255,
                false,
                false
            ));
            
            affectedEntities.add(entity.getUUID());
        }
        
        if (affectedEntities.isEmpty()) return;
        
        UUID sourceUUID = source.getUUID();
        
        // 在效果结束后（delayTicks后）造成伤害
        serverLevel.getServer().tell(new TickTask(
            serverLevel.getServer().getTickCount() + delayTicks,
            () -> {
                Entity currentSource = serverLevel.getEntity(sourceUUID);
                
                // 创建伤害来源（使用魔法类型）
                DamageSource damageSource = serverLevel.damageSources().source(
                    DamageTypes.MAGIC,
                    currentSource
                );
                
                for (UUID entityId : affectedEntities) {
                    Entity entity = serverLevel.getEntity(entityId);
                    if (entity instanceof LivingEntity livingEntity && livingEntity.isAlive()) {
                        livingEntity.hurt(damageSource, damage);
                    }
                }
            }
        ));
    }
}