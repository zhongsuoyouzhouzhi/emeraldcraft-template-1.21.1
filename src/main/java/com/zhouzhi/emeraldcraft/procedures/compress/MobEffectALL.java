package com.zhouzhi.emeraldcraft.procedures.compress;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class MobEffectALL {
    public static void execute(Level world, double x, double y, double z, MobEffectInstance[] effectInstances, int xRadius, int yRadius, int zRadius) {
        if (world instanceof ServerLevel serverLevel) {
            int minX = (int) (x - xRadius);
            int minY = (int) (y - yRadius);
            int minZ = (int) (z - zRadius);
            int maxX = (int) (x + xRadius);
            int maxY = (int) (y + yRadius);
            int maxZ = (int) (z + zRadius);
            
            BlockPos minPos = new BlockPos(minX, minY, minZ);
            BlockPos maxPos = new BlockPos(maxX, maxY, maxZ);
            
            for (Entity entity : serverLevel.getEntities().getAll()) {
                if (isEntityInRange(entity, minPos, maxPos) && entity instanceof LivingEntity livingEntity) {
                    if (!livingEntity.level().isClientSide()) {
                        for (MobEffectInstance effectInstance : effectInstances) {
                            if (effectInstance != null) {
                                livingEntity.addEffect(effectInstance);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void execute(Level world, double x, double y, double z, MobEffectInstance[] effectInstances, int xRadius, int yRadius, int zRadius, Entity sourceEntity) {
        if (world instanceof ServerLevel serverLevel) {
            int minX = (int) (x - xRadius);
            int minY = (int) (y - yRadius);
            int minZ = (int) (z - zRadius);
            int maxX = (int) (x + xRadius);
            int maxY = (int) (y + yRadius);
            int maxZ = (int) (z + zRadius);

            BlockPos minPos = new BlockPos(minX, minY, minZ);
            BlockPos maxPos = new BlockPos(maxX, maxY, maxZ);

            for (Entity entity : serverLevel.getEntities().getAll()) {
                if (isEntityInRange(entity, minPos, maxPos) && entity instanceof LivingEntity livingEntity && entity !=sourceEntity) {
                    if (!livingEntity.level().isClientSide()) {
                        for (MobEffectInstance effectInstance : effectInstances) {
                            if (effectInstance != null) {
                                livingEntity.addEffect(effectInstance);
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean isEntityInRange(Entity entity, BlockPos minPos, BlockPos maxPos) {
        double entityX = entity.getX();
        double entityY = entity.getY();
        double entityZ = entity.getZ();
        
        return entityX >= minPos.getX() && entityX <= maxPos.getX() &&
               entityY >= minPos.getY() && entityY <= maxPos.getY() &&
               entityZ >= minPos.getZ() && entityZ <= maxPos.getZ();
    }

    public static void execute(Level world, MobEffectInstance[] effectInstances, LivingEntity Target) {
        if (world instanceof ServerLevel) {
            if (!Target.level().isClientSide()) {
                for (MobEffectInstance effectInstance : effectInstances) {
                    if (effectInstance != null) {
                        Target.addEffect(effectInstance);
                    }
                }
            }
        }
    }
}