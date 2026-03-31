package com.zhouzhi.emeraldcraft.procedures.compress;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

public class SLTZ {
    /**
     */
    public static void execute(Level world, Entity sourceEntity, double x, double y, double z, double R, float Damage) {
        if (world instanceof ServerLevel serverLevel) {
            Vec3 center = new Vec3(x, y, z);
            
            for (Entity entity : serverLevel.getEntities().getAll()) {
                if (entity == sourceEntity) continue;
                
                Vec3 entityPos = entity.position();
                double distance = center.distanceTo(entityPos);
                
                if (distance <= R && distance > 0) {
                    double pushStrength = 1.0 - (distance / R);
                    	
                    Vec3 pushDirection = entityPos.subtract(center).normalize();
                    
                    double horizontalStrength = pushStrength * 1.5;
                    double verticalStrength = pushStrength * 0.8;
                    
                    entity.setDeltaMovement(
                        entity.getDeltaMovement().add(
                            pushDirection.x * horizontalStrength,
                            verticalStrength,
                            pushDirection.z * horizontalStrength
                        )
                    );
                    
                    entity.hasImpulse = true;
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.hurt(livingEntity.damageSources().explosion(null, sourceEntity), Damage);
                    }
                }
            }
        }
    }

    public static void execute(Level world, Entity sourceEntity, double x, double y, double z, double R) {
        if (world instanceof ServerLevel serverLevel) {
            Vec3 center = new Vec3(x, y, z);
            
            for (Entity entity : serverLevel.getEntities().getAll()) {
                if (entity == sourceEntity) continue;
                
                Vec3 entityPos = entity.position();
                double distance = center.distanceTo(entityPos);
                
                if (distance <= R && distance > 0) {
                    double pushStrength = 1.0 - (distance / R);
                    	
                    Vec3 pushDirection = entityPos.subtract(center).normalize();
                    
                    double horizontalStrength = pushStrength * 1.5;
                    double verticalStrength = pushStrength * 0.8;
                    
                    entity.setDeltaMovement(
                        entity.getDeltaMovement().add(
                            pushDirection.x * horizontalStrength,
                            verticalStrength,
                            pushDirection.z * horizontalStrength
                        )
                    );
                    
                    entity.hasImpulse = true;
                }
            }
        }
    }
}