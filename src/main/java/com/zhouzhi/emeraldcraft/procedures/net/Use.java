package com.zhouzhi.emeraldcraft.procedures.net;

import com.zhouzhi.emeraldcraft.init.ModMobEffects;
import com.zhouzhi.emeraldcraft.procedures.compress.MobEffectALL;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;

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
            source.setHealth(source.getHealth()+1f);
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
                            float damage = 0x7FFFFFFF;
                            if (entity instanceof LivingEntity livingEntity) {
                                livingEntity.hurt(source.damageSources().playerAttack(source), damage);
                                livingEntity.die(source.damageSources().playerAttack(source));
                                if (livingEntity.getHealth() > 0f)
                                    livingEntity.hurt(new DamageSource(world.holderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("emeraldcraft:emerald_radiation")))), damage);
                            } else entity.kill();
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
                if (SimpleUse.getEntityGameType(source) != GameType.CREATIVE) {
                    stack.hurtAndBreak(2, serverLevel, null, _k -> {
                    });
                }
                source.getCooldowns().addCooldown(stack.getItem(), 200);
            }
        }
    }

    public static void VoidEmeraldArmorPerTick(Level world, Player player, ItemStack stack) {
        int van = SimpleUse.VoidArmorNumber(player);
        if (van == 4) {
            if (TagChange.getOrCreateComponent(stack, "Void", false)) {
                MobEffectInstance[] effectInstances = new MobEffectInstance[]{
                        new MobEffectInstance(ModMobEffects.VOID, 2, 5, false, false),
                };
                MobEffectALL.execute(world, effectInstances, player);
            }
        }
    }
}
