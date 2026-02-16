package com.zhouzhi.emeraldcraft.procedures.compress;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

public class WXTY {
    /**
     * @param world
     * @param sourceEntity
     * @param d
     */
    public static void execute(Level world, Entity sourceEntity, double d) {
        if (world instanceof ServerLevel serverLevel && sourceEntity != null) {
            Vec3 sourcePos = sourceEntity.position();
            Vec3 lookDirection = sourceEntity.getViewVector(1.0F);
            
            Vec3 targetPos = sourcePos.add(lookDirection);
            
            for (Entity entity : serverLevel.getEntities().getAll()) {
                if (entity == sourceEntity) continue;
                
                Vec3 entityPos = entity.position();
                double distance = sourcePos.distanceTo(entityPos);
                
                if (distance <= d && distance > 0) {
                    Vec3 entityDirection = entityPos.subtract(sourcePos).normalize();
                    double dotProduct = entityDirection.dot(lookDirection);
                    
                    double angleFactor = Math.max(0, dotProduct);
                    
                    double distanceFactor = distance / d;
                    
                    double attractionStrength = distanceFactor * angleFactor;
                    
                    if (attractionStrength > 0) {
                        Vec3 attractionVector = targetPos.subtract(entityPos);
                        
                        Vec3 movement = attractionVector.scale(attractionStrength * 0.4);
                        
                        entity.setDeltaMovement(
                            entity.getDeltaMovement().add(
                                movement.x,
                                movement.y,
                                movement.z
                            )
                        );
                        
                        entity.hasImpulse = true;
                    }
                }
            }
        }
    }
}