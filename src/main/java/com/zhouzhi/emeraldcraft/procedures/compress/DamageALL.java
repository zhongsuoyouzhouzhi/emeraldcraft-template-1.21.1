package com.zhouzhi.emeraldcraft.procedures.compress;

import com.zhouzhi.emeraldcraft.procedures.net.Use;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class DamageALL {

    public static void execute(Level world, double x, double y, double z, float damageAmount, int XRadius, int YRadius, int ZRadius) {
        if (world instanceof ServerLevel serverLevel) {
            int minX = (int) (x - XRadius);
            int minY = (int) (y - YRadius);
            int minZ = (int) (z - ZRadius);
            int maxX = (int) (x + XRadius);
            int maxY = (int) (y + YRadius);
            int maxZ = (int) (z + ZRadius);

            BlockPos minPos = new BlockPos(minX, minY, minZ);
            BlockPos maxPos = new BlockPos(maxX, maxY, maxZ);

            Iterable<Entity> entities = serverLevel.getEntities().getAll();

            for (Entity entity : entities) {
                if (isEntityInRange(entity, minPos, maxPos) && entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.hurt(livingEntity.damageSources().generic(), damageAmount);
                }
            }
        }
    }

    public static void execute(Level world, Entity source, float damageAmount, int XRadius, int YRadius, int ZRadius, boolean damageSource) {
        if (world instanceof ServerLevel) {
            SimpleUse.OperateEntity(world, source, XRadius, YRadius, ZRadius, (entity)->{
                if (!damageSource && entity == source) {
                    return;
                }
                if (entity instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.hurt(livingEntity.damageSources().generic(), damageAmount);
                }
            });
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
}