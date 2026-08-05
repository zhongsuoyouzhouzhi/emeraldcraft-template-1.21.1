package com.zhouzhi.emeraldcraft.procedures.net;

import com.zhouzhi.emeraldcraft.init.ModEntities;
import com.zhouzhi.emeraldcraft.init.ModMobEffects;
import com.zhouzhi.emeraldcraft.procedures.compress.MobEffectALL;
import com.zhouzhi.emeraldcraft.procedures.compress.PushAway;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class Use {
    public static void EmeraldSwordHitLivingThings(LivingEntity entity){
        entity.addEffect(new MobEffectInstance(ModMobEffects.SUPPRESS, 40, 1, false, true));
    }

    public static void EmeraldSwordT2HitLivingThings(LivingEntity entity){
        entity.addEffect(new MobEffectInstance(ModMobEffects.SUPPRESS, 50, 2, false, true));
    }

    public static void EmeraldSwordT3HitLivingThings(LivingEntity entity){
        entity.addEffect(new MobEffectInstance(ModMobEffects.SUPPRESS, 60, 3, false, true));
    }

    public static void IronSwordHitLivingThings(LivingEntity entity, LivingEntity source){
        entity.addEffect(new MobEffectInstance(ModMobEffects.SUPPRESS, 20, 0, false, true));
        if (source.getHealth() < source.getMaxHealth()){
            source.setHealth(source.getHealth()+1f);
        } else {
            source.setHealth(source.getMaxHealth());
        }
    }

    public static void IronSwordT2HitLivingThings(LivingEntity entity, LivingEntity source){
        entity.addEffect(new MobEffectInstance(ModMobEffects.SUPPRESS, 40, 1, false, true));
        if (source.getHealth() < source.getMaxHealth()){
            source.setHealth(source.getHealth()+2f);
        } else {
            source.setHealth(source.getMaxHealth());
        }
    }

    public static void IronToolBeingDamagedPerTick(LevelAccessor world, Entity entity, ItemStack itemstack){
        if (entity != null) {
            if (itemstack.getDamageValue() != 0 && !world.isClientSide()) {
                if (entity instanceof Player _player && !(_player.getCooldowns().isOnCooldown(itemstack.getItem()))) {
                    int timer = TagChange.getOrCreateComponent(itemstack,"Timer",600);
                    timer--;

                    if (timer <= 0) {
                        if (itemstack.getDamageValue() <= 15) {
                            itemstack.setDamageValue(0);
                        } else {
                            itemstack.setDamageValue(itemstack.getDamageValue() - 15);
                        }
                        _player.getCooldowns().addCooldown(itemstack.getItem(), 100);

                        TagChange.saveComponent(itemstack,"Timer",600);
                    }
                    TagChange.saveComponent(itemstack,"Timer",timer);
                }
            }
        }
    }

    public static void RefinedEmeraldT3ToolIsBeingDamagedPerTick(LevelAccessor world, Entity entity, ItemStack itemstack){
        if (entity != null) {
            if (itemstack.getDamageValue() != 0 && !world.isClientSide()) {
                if (entity instanceof Player _player && !(_player.getCooldowns().isOnCooldown(itemstack.getItem()))) {
                    int timer = TagChange.getOrCreateComponent(itemstack,"Timer",100);
                    timer--;
                    if (timer <= 0) {
                        if (itemstack.getDamageValue() <= 100) {
                            itemstack.setDamageValue(0);
                        } else {
                            itemstack.setDamageValue(itemstack.getDamageValue() - 100);
                        }
                        _player.getCooldowns().addCooldown(itemstack.getItem(), 20);
                        TagChange.saveComponent(itemstack,"Timer",100);
                    }
                    TagChange.saveComponent(itemstack,"Timer",timer);
                }
            }
        }
    }

    public static void SkyFillingBladeSpecialSkill2(Entity sourceEntity, float damage, double radius) {
        if (sourceEntity == null || sourceEntity.level().isClientSide()) {
            return;
        }
        Level level = sourceEntity.level();
        AABB area = new AABB(
                sourceEntity.getX() - radius, sourceEntity.getY() - radius, sourceEntity.getZ() - radius,
                sourceEntity.getX() + radius, sourceEntity.getY() + radius, sourceEntity.getZ() + radius
        );
        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area)) {
            if (target == sourceEntity) {
                continue;
            }
            spawnLightningAtEntity(target);
            if (damage > 0) {
                target.hurt(level.damageSources().magic(), damage);
                if (sourceEntity instanceof Player) {
                    target.hurt(level.damageSources().playerAttack((Player) sourceEntity), damage);
                } else if (sourceEntity instanceof LivingEntity) {
                    target.hurt(level.damageSources().mobAttack((LivingEntity) sourceEntity), damage);
                }
            }
        }
        spawnLightningAtPosition(level, sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ());
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null,
                    sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ(),
                    net.minecraft.sounds.SoundEvents.LIGHTNING_BOLT_THUNDER,
                    net.minecraft.sounds.SoundSource.WEATHER,
                    5.0F, 1.0F
            );

            serverLevel.playSound(null,
                    sourceEntity.getX(), sourceEntity.getY(), sourceEntity.getZ(),
                    net.minecraft.sounds.SoundEvents.LIGHTNING_BOLT_IMPACT,
                    net.minecraft.sounds.SoundSource.WEATHER,
                    1.0F, 1.0F
            );
        }
    }

    private static void spawnLightningAtEntity(LivingEntity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            LightningBolt lightning = new LightningBolt(
                    net.minecraft.world.entity.EntityType.LIGHTNING_BOLT,
                    serverLevel
            );
            lightning.moveTo(entity.getX(), entity.getY(), entity.getZ());
            lightning.setVisualOnly(false);
            lightning.setCause(entity instanceof Player ? (ServerPlayer) entity : null);
            serverLevel.addFreshEntity(lightning);


        }
    }

    private static void spawnLightningAtPosition(Level level, double x, double y, double z) {
        if (level instanceof ServerLevel serverLevel) {
            LightningBolt lightning = new LightningBolt(
                    net.minecraft.world.entity.EntityType.LIGHTNING_BOLT,
                    serverLevel
            );

            lightning.moveTo(x, y, z);
            lightning.setVisualOnly(true); // 中心雷电设为仅视觉效果

            serverLevel.addFreshEntity(lightning);
        }
    }

    public static void VoidEmeraldSwordHitLivingThings(ItemStack itemstack, LivingEntity entity, LivingEntity source){
        if (source instanceof Player _player && !(_player.getCooldowns().isOnCooldown(itemstack.getItem()))){
            String[] b = {};
            for (String a : entity.getTags().toArray(b)) {
                if (a.equals("void")) {
                    return;
                }
            }
            entity.addTag("void");
        }
    }

    public static void VoidEmeraldSwordRight_clickOnAir(ItemStack stack,Player source,Level world) {
        if (TagChange.getOrCreateComponent(stack,"Void_Open",true)) {
            if (world instanceof ServerLevel serverLevel) {
                every_entity:
                for (Entity entity : serverLevel.getAllEntities()) {
                    String[] b = {};
                    for (String a : entity.getTags().toArray(b)) {
                        if (a.equals("void")) {
                            entity.setInvisible(true);
                            entity.setInvulnerable(false);
                            entity.setSilent(true);
                            killEntity(entity,source);
                            entity.removeTag("void");
                            if (entity.getType().equals(EntityType.ENDER_DRAGON))
                                continue every_entity;
                            if (!world.isClientSide())
                                serverLevel.sendParticles(
                                        ParticleTypes.END_ROD,
                                        entity.getX(), entity.getY(), entity.getZ(),
                                        64,
                                        0.5, 0.5, 0.5,
                                        0
                                );
                            entity.removeTag("void");
                            entity.moveTo(entity.getX(), -200, entity.getZ());
                            break;
                        }
                    }
                }
                if (!SimpleUse.GameTypeGetter.isCreativeOrSpectator(source)) {
                    stack.hurtAndBreak(2, serverLevel, null, _k -> {
                    });
                }
                source.getCooldowns().addCooldown(stack.getItem(), 200);
            }
        }
    }

    public static void killEntity(Entity entity,LivingEntity source) {
        float damage = Float.MAX_VALUE;
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.hurt(source.damageSources().mobAttack(source), damage);
            livingEntity.setHealth(0);
            livingEntity.die(source.damageSources().mobAttack(source));
            if (livingEntity.getHealth() > 0f)
                livingEntity.hurt(new DamageSource(entity.level().holderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("emeraldcraft:emerald_radiation")))), damage);
        } else entity.kill();
    }

    public static void VoidEmeraldArmorPerTick(Level world, Player player, ItemStack stack) {
        int van = SimpleUse.VoidArmorNumber(player);
        if (van == 4) {
            if (TagChange.getOrCreateComponent(stack, "Void", false)) {
                MobEffectInstance[] effectInstances = new MobEffectInstance[]{
                        new MobEffectInstance(ModMobEffects.VOID, 2, 5, false, false),
                };
                MobEffectALL.execute(world, effectInstances, player);

                PushAway.executeWhen(world, player, player.getX(), player.getY(), player.getZ(), 3.5, entity->
                    entity.getType().equals(EntityType.ARROW) ||
                            entity.getType().equals(EntityType.ENDER_PEARL) ||
                            entity.getType().equals(EntityType.SHULKER_BULLET) ||
                            entity.getType().equals(EntityType.POTION) ||
                            entity.getType().is(EntityTypeTags.IMPACT_PROJECTILES) ||
                            entity.getType().is(EntityTypeTags.REDIRECTABLE_PROJECTILE) ||
                            entity.getType().equals(ModEntities.EMERALD_PROJECTILE.get())
                );
            } else if (player.isInWaterRainOrBubble()) {
                EquipmentSlot[] slots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
                for (EquipmentSlot slot : slots) {
                    ItemStack itemstack = player.getItemBySlot(slot);
                    if (itemstack.getDamageValue() > 0) {
                        itemstack.setDamageValue(itemstack.getDamageValue() - 1);
                    }
                }
            }
        }
    }
    public static class EntityPause {
        /**
         * 暂停生物
         */
        public static void pauseEntities(Level level, Vec3 center, double radius, int time) {
            if (level.isClientSide()) return;
            long currentTick = level.getGameTime();
            AABB aabb = new AABB(center.x - radius, center.y - radius, center.z - radius,
                    center.x + radius, center.y + radius, center.z + radius);
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, aabb);
            for (LivingEntity entity : entities) {
                if (entity instanceof Player) continue;
                if (level instanceof ServerLevel serverLevel) {
                    Brain<?> brain = entity.getBrain();
                    ((Brain<LivingEntity>) brain).stopAll(serverLevel, entity);
                    brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
                    brain.eraseMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
                    brain.eraseMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
                    brain.eraseMemory(MemoryModuleType.ANGRY_AT);
                }

                entity.setNoActionTime(time * 20);
                entity.setDeltaMovement(Vec3.ZERO);
                entity.setNoGravity(true);
                TagChange.getOrCreateComponent(entity, "PausedByGenesisEmeraldSword", true);
                TagChange.saveComponent(entity, "PausedByGenesisEmeraldSword", true);
                TagChange.getOrCreateComponent(entity, "PausedStartTickByGenesisEmeraldSword", currentTick);
                TagChange.saveComponent(entity, "PausedStartTickByGenesisEmeraldSword", currentTick);
            }
        }
        /**
         * 自动解除暂停
         */
        public static void tickPausedEntities(ServerLevel level, int time) {
            if (level.isClientSide()) return;
            long currentTick = level.getGameTime();
            for (Entity entity : level.getEntities().getAll()) {
                if (entity instanceof LivingEntity livingEntity) {
                    if (TagChange.getOrCreateComponent(entity, "PausedByGenesisEmeraldSword", false)) {
                        long startTick = TagChange.getOrCreateComponent(entity, "PausedStartTickByGenesisEmeraldSword", currentTick);
                        if (currentTick - startTick >= (long) time * 20) {
                            livingEntity.setNoActionTime(0);
                            livingEntity.setNoGravity(false);
                            TagChange.saveComponent(livingEntity, "PausedByGenesisEmeraldSword", false);
                            TagChange.saveComponent(livingEntity, "PausedStartTickByGenesisEmeraldSword", 0);
                        } else {
                            livingEntity.setDeltaMovement(Vec3.ZERO);
                            Brain<?> brain = livingEntity.getBrain();
                            ((Brain<LivingEntity>) brain).stopAll(level, livingEntity);
                            brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
                            brain.eraseMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER);
                            brain.eraseMemory(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
                            brain.eraseMemory(MemoryModuleType.ANGRY_AT);
                        }
                    }
                }
            }
        }
        /**
         * 检查是否暂停
         */
        public static boolean isPaused(LivingEntity entity) {
            return TagChange.getOrCreateComponent(entity, "PausedByGenesisEmeraldSword", false);
        }
    }

    public static void OblivionEmeraldSwordRight_click(Player player) {
        for (LivingEntity entity:getEntitiesInCrosshair(player,32,Math.PI / 6)) {
            entity.setSilent(true);
            killEntity(entity, player);
            if (entity.getType().equals(EntityType.ENDER_DRAGON))
                return;
            if (player.level() instanceof ServerLevel serverLevel && !serverLevel.isClientSide()) {
                serverLevel.sendParticles(
                        ParticleTypes.END_ROD,
                        entity.getX(), entity.getY(), entity.getZ(),
                        128,
                        0.5, 0.5, 0.5,
                        0.25
                );
            }
            entity.moveTo(entity.getX(), -200, entity.getZ());
        }
    }

    private static List<LivingEntity> getEntitiesInCrosshair(Player player, double maxDistance, double angleThreshold) {
        List<LivingEntity> result = new ArrayList<>();
        Level level = player.level();
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);
        AABB area = new AABB(eyePos.x - maxDistance, eyePos.y - maxDistance, eyePos.z - maxDistance,
                eyePos.x + maxDistance, eyePos.y + maxDistance, eyePos.z + maxDistance);
        List<LivingEntity> candidates = level.getEntitiesOfClass(LivingEntity.class, area);
        candidates.remove(player);
        for (LivingEntity target : candidates) {
            Vec3 targetPos = target.position().add(0, target.getBbHeight() * 0.5, 0);
            Vec3 toTarget = targetPos.subtract(eyePos);
            double distance = toTarget.length();
            if (distance > maxDistance) continue;
            Vec3 toTargetNorm = toTarget.scale(1.0 / distance);
            double cosAngle = lookVec.dot(toTargetNorm);
            if (cosAngle < Math.cos(angleThreshold)) continue;
            ClipContext clipContext = new ClipContext(eyePos, targetPos,
                    ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player);
            var hitResult = level.clip(clipContext);

            if (hitResult.getType() == HitResult.Type.MISS) {
                result.add(target);
            } else {
                double hitDist = eyePos.distanceTo(hitResult.getLocation());
                if (hitDist >= distance - 0.1) {
                    result.add(target);
                }
            }
        }
        return result;
    }
}
