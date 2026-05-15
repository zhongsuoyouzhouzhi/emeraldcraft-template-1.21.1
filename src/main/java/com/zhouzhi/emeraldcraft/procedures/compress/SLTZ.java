package com.zhouzhi.emeraldcraft.procedures.compress;

import com.zhouzhi.emeraldcraft.procedures.net.function.Function;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SLTZ {
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

    private static void push(Level world, Entity sourceEntity, double x, double y, double z, double R, Function.Function_EntityOperation function_entityOperation) {
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
}