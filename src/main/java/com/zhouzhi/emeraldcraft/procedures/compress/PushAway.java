package com.zhouzhi.emeraldcraft.procedures.compress;

import com.zhouzhi.emeraldcraft.procedures.net.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PushAway {
    public static void execute(Level world, Entity sourceEntity, double x, double y, double z, double R, float Damage) {
        push(world,sourceEntity,x,y,z,R,entity -> {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.hurt(livingEntity.damageSources().explosion(null, sourceEntity), Damage);
            }
        });
    }

    public static void execute(Level world, Entity sourceEntity, double x, double y, double z, double R) {
        push(world,sourceEntity,x,y,z,R,entity -> {});
    }

    public static void executeWhen(Level world, Entity sourceEntity, double x, double y, double z, double R,com.google.common.base.Function<Entity,Boolean> function_condition) {
        push(world,sourceEntity,x,y,z,R,entity -> {},function_condition);
    }

    public static void executeOnly(Level world, Entity targetEntity, double x, double y, double z, double R) {
        pushOnly(world,targetEntity,x,y,z,R,entity -> {});
    }
    /**
     * @param world 中心所在level
     * @param sourceEntity 源实体位置
     * @param x 中心x坐标
     * @param y 中心y坐标
     * @param z 中心z坐标
     * @param R 半径
     * @param function_entityOperation 推开后需要进行的操作
     */
    protected static void push(Level world, Entity sourceEntity, double x, double y, double z, double R, Function.Function_EntityOperation function_entityOperation) {
        if (world instanceof ServerLevel serverLevel) {
            if (world.isClientSide) return;
            Vec3 center = new Vec3(x, y, z);
            for (Entity entity : serverLevel.getEntities().getAll()) {
                if (entity == sourceEntity) continue;
                Vec3 pos = entity.position();
                double dist = center.distanceTo(pos);
                if (dist > R) continue;
                Vec3 normal = pos.subtract(center).normalize();
                Vec3 velocity = entity.getDeltaMovement();
                double speed = velocity.length();
                if (speed < 0.1) {
                    Vec3 outward = normal.scale(0.8);
                    entity.setDeltaMovement(outward);
                    entity.hasImpulse = true;
                    continue;
                }
                Vec3 velDir = velocity.normalize();
                double dot = velDir.dot(normal);
                if (dot < 0) {
                    entity.setDeltaMovement(velocity.scale(-1));
                    entity.hasImpulse = true;
                }
                function_entityOperation.run(entity);
            }
        }
    }
    /**
     * @param world 中心所在level
     * @param sourceEntity 源实体位置
     * @param x 中心x坐标
     * @param y 中心y坐标
     * @param z 中心z坐标
     * @param R 半径
     * @param function_entityOperation 推开后需要进行的操作
     * @param function_condition 生物需要满足的条件
     */
    protected static void push(Level world, Entity sourceEntity, double x, double y, double z, double R, Function.Function_EntityOperation function_entityOperation,com.google.common.base.Function<Entity,Boolean> function_condition) {
        if (world instanceof ServerLevel serverLevel) {
            if (world.isClientSide) return;
            Vec3 center = new Vec3(x, y, z);
            for (Entity entity : serverLevel.getEntities().getAll()) {
                if (entity == sourceEntity) continue;
                Vec3 pos = entity.position();
                double dist = center.distanceTo(pos);
                if (dist > R) continue;
                if (!function_condition.apply(entity)) continue;
                Vec3 normal = pos.subtract(center).normalize();
                Vec3 velocity = entity.getDeltaMovement();
                double speed = velocity.length();
                if (speed < 0.1) {
                    Vec3 outward = normal.scale(0.8);
                    entity.setDeltaMovement(outward);
                    entity.hasImpulse = true;
                    continue;
                }
                Vec3 velDir = velocity.normalize();
                double dot = velDir.dot(normal);
                if (dot < 0) {
                    entity.setDeltaMovement(velocity.scale(-1));
                    entity.hasImpulse = true;
                }
                function_entityOperation.run(entity);
            }
        }
    }
    /**
     * @param world 中心所在level
     * @param targetEntity 目标实体位置
     * @param x 中心x坐标
     * @param y 中心y坐标
     * @param z 中心z坐标
     * @param R 半径
     * @param function_entityOperation 推开后需要进行的操作
     */
    protected static void pushOnly(Level world, Entity targetEntity, double x, double y, double z, double R, Function.Function_EntityOperation function_entityOperation) {
        if (world.isClientSide) return;
        Vec3 center = new Vec3(x, y, z);
        Vec3 pos = targetEntity.position();
        double dist = center.distanceTo(pos);
        if (dist > R) return;
        Vec3 normal = pos.subtract(center).normalize();
        Vec3 velocity = targetEntity.getDeltaMovement();
        double speed = velocity.length();
        if (speed < 0.1) {
            Vec3 outward = normal.scale(0.8);
            targetEntity.setDeltaMovement(outward);
            targetEntity.hasImpulse = true;
            return;
        }
        Vec3 velDir = velocity.normalize();
        double dot = velDir.dot(normal);
        if (dot < 0) {
            targetEntity.setDeltaMovement(velocity.scale(-1));
            targetEntity.hasImpulse = true;
        }
        function_entityOperation.run(targetEntity);
    }
}