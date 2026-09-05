package com.zhouzhi.emeraldcraft.listening;

import com.zhouzhi.emeraldcraft.entity.ThrownInfernoEmeraldTrident;
import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.init.ModTags;
import com.zhouzhi.emeraldcraft.item.oblivion_emerald.OblivionEmeraldItem;
import com.zhouzhi.emeraldcraft.item.void_emerald.VoidEmeraldArmorItem;
import com.zhouzhi.emeraldcraft.procedures.compress.MobEffectALL;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.zhouzhi.emeraldcraft.listening.MiningListening.infernoChange;
import static com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse.CuriosAPI.hasCurios;
import static com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse.Random_static.nextBoolean;
import static com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse.Random_static.nextPercent;

public class AttackListening {
    @SubscribeEvent
    public void LavaEmeraldToolAndInfernoEmeraldToolAttack(LivingDamageEvent.Pre event){
        LivingEntity target = event.getEntity();
        Level level = target.getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            ItemStack weapon = attacker.getMainHandItem();
            if (weapon.is(ModItems.LAVA_EMERALD) || weapon.is(ModTags.LAVA_EMERALD_TOOLS) || weapon.is(ModItems.INFERNO_EMERALD) || weapon.is(ModTags.INFERNO_EMERALD_TOOLS)) {
                target.lavaHurt();
                if (weapon.is(ModTags.INFERNO_EMERALD_TOOLS) && hasCurios(attacker, ModItems.INFERNO_EMERALD.get())) {
                    event.setNewDamage(event.getNewDamage() * 1.5f);
                }
            }
        }
    }

    @SubscribeEvent
    public void LavaEmeraldT2ToolAttack(LivingDamageEvent.Pre event){
        LivingEntity target = event.getEntity();
        Level level = target.getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            ItemStack weapon = attacker.getMainHandItem();
            if (weapon.is(ModItems.LAVA_EMERALD_T2) || weapon.is(ModTags.LAVA_EMERALD_T2_TOOLS)) {
                target.lavaHurt();
                target.setHealth(target.getHealth() - 1f);
                target.startSleeping(target.getOnPos());
            }
        }
    }

    @SubscribeEvent
    public void InfernoEmeraldSwordAttack(LivingDamageEvent.Post event){
        LivingEntity target = event.getEntity();
        Level level = target.getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            ItemStack weapon = attacker.getMainHandItem();
            if (weapon.is(ModItems.INFERNO_EMERALD_SWORD)) {
                int Blood_boil_Brand = TagChange.getOrCreateComponent(target,"Blood-boil Brand",0);
                TagChange.saveComponent(target,"Blood-boil Brand",++Blood_boil_Brand);
                if (Blood_boil_Brand >= 3){
                    TagChange.saveComponent(target,"Blood-boil Brand",0);
                    float k = event.getNewDamage() / event.getOriginalDamage();
                    if (k < 1F)
                        k = 1F;
                    if (hasCurios(attacker, ModItems.INFERNO_EMERALD.get())) {
                        k += 0.5F;
                    }
                    target.setHealth(target.getHealth() - 0.5F * k * (target.getMaxHealth()-target.getHealth()));
                    MobEffectInstance[] effects = new MobEffectInstance[]{
                            new MobEffectInstance(MobEffects.WEAKNESS, 80, 7, false, true),
                            new MobEffectInstance(MobEffects.BLINDNESS, 80, 4, false, true),
                    };
                    MobEffectALL.execute(target.getCommandSenderWorld(),effects,target);
                    if (level instanceof ServerLevel serverLevel && !serverLevel.isClientSide()) {
                        double x = target.getX();
                        double y = target.getY();
                        double z = target.getZ();
                        serverLevel.sendParticles(ParticleTypes.LAVA, x, y, z, 256 ,0,0.2,0,3);
                    }
                }
            }
            int attacker_Blood_boil_Brand = TagChange.getOrCreateComponent(attacker,"Blood-boil Brand",0);
            if (attacker_Blood_boil_Brand > 0) {
                TagChange.saveComponent(attacker,"Blood-boil Brand", ++attacker_Blood_boil_Brand);
                if (attacker_Blood_boil_Brand >= 3){
                    TagChange.saveComponent(attacker,"Blood-boil Brand",0);
                    float k = event.getNewDamage() / event.getOriginalDamage();
                    if (k < 1F)
                        k = 1F;
                    if (hasCurios(target, ModItems.INFERNO_EMERALD.get())) {
                        k += 0.5F;
                    }
                    attacker.setHealth(attacker.getHealth() - 0.5F * k * (attacker.getMaxHealth()-attacker.getHealth()));
                    attacker.hurt(attacker.damageSources().mobAttack(target),0);
                    MobEffectInstance[] effects = new MobEffectInstance[]{
                            new MobEffectInstance(MobEffects.WEAKNESS, 80, 7, false, true),
                            new MobEffectInstance(MobEffects.BLINDNESS, 80, 4, false, true),
                    };
                    MobEffectALL.execute(attacker.getCommandSenderWorld(),effects,attacker);
                    if (level instanceof ServerLevel serverLevel && !serverLevel.isClientSide()) {
                        double x = attacker.getX();
                        double y = attacker.getY();
                        double z = attacker.getZ();
                        serverLevel.sendParticles(ParticleTypes.LAVA, x, y, z, 256 ,0,0.2,0,3);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void ThrownInfernoEmeraldTridentAttack(LivingDamageEvent.Post event){
        LivingEntity target = event.getEntity();
        Level level = target.getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        DamageSource source = event.getSource();
        if (source.is(DamageTypes.TRIDENT)) {
            if (source.getDirectEntity() instanceof ThrownInfernoEmeraldTrident trident) {
                Entity attacker = source.getEntity();
                if (trident.special_skill) {
                    if (level instanceof ServerLevel serverLevel) {
                        AABB aabb = new AABB(target.getX() - 5,target.getY() - 1, target.getZ() - 5,target.getX() + 5,target.getY() + 1,target.getZ() + 5);
                        serverLevel.getEntitiesOfClass(LivingEntity.class, aabb).forEach(entity -> {
                            if (entity.is(target) || (attacker != null && entity.is(attacker))) {
                                return;
                            }
                            if (attacker instanceof LivingEntity living && hasCurios(living, ModItems.INFERNO_EMERALD.get())) {
                                entity.hurt(entity.damageSources().generic(), 45F);
                            } else {
                                entity.hurt(entity.damageSources().generic(), 30F);
                            }
                            entity.lavaHurt();
                        });
                        // region 特效
                        Vec3 offset = new Vec3(1,0,0);
                        SimpleUse.Effect.round_plane(
                                serverLevel,
                                ParticleTypes.FLAME,
                                target.position(),
                                1,
                                60,
                                offset,
                                0.5,
                                true);
                        SimpleUse.Effect.round_plane(
                                serverLevel,
                                ParticleTypes.FLAME,
                                target.position(),
                                0.5,
                                60,
                                offset,
                                0.5,
                                true);
                        SimpleUse.Effect.round_plane(
                                serverLevel,
                                ParticleTypes.FLAME,
                                target.position(),
                                0.25,
                                60,
                                offset,
                                0.5,
                                true);
                        SimpleUse.Effect.round_plane(
                                serverLevel,
                                ParticleTypes.FLAME,
                                target.position(),
                                0.015625,
                                60,
                                offset,
                                0.5,
                                true);
                        // endregion
                        // region 焚化方块
                        BlockPos pos = target.getOnPos().above();
                        SimpleUse.OperateBlock(
                                serverLevel,
                                pos.getX(), pos.getY(), pos.getZ(),
                                5,
                                1,
                                (block, X, Y, Z) -> {
                                    if (nextBoolean()) {
                                        if (block.isEmpty(block.defaultBlockState())) {
                                            return;
                                        }
                                        infernoChange(X,Y,Z,pos,serverLevel);
                                    }
                                });
                        // endregion
                    }
                } else {
                    if (attacker instanceof LivingEntity living && hasCurios(living, ModItems.INFERNO_EMERALD.get())) {
                        target.hurt(target.damageSources().generic(), 112.5F);
                    } else {
                        target.hurt(target.damageSources().generic(), 75F);
                    }
                    // region 特效
                    if (level instanceof ServerLevel serverLevel) {
                        Vec3 offset = new Vec3(0,-1,0);
                        SimpleUse.Effect.round_plane(
                                serverLevel,
                                ParticleTypes.FLAME,
                                target.position().add(0,2,0),
                                0.75,
                                60,
                                offset,
                                0.125,
                                false
                        );
                    }
                    // endregion
                }
            }
        }
    }

    @SubscribeEvent
    public void VoidEmeraldArmorBeAttacked(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        Level level = target.getCommandSenderWorld();
        DamageSource damageSource = event.getSource();
        if (target.isDamageSourceBlocked(damageSource)) {
            return;
        }
        float damage = event.getAmount();
        AtomicBoolean _return = new AtomicBoolean(false);

        CuriosApi.getCuriosInventory(target).flatMap(inventory -> inventory.findFirstCurio(stack ->
                stack.getItem() instanceof OblivionEmeraldItem
        )).ifPresent(stack -> {
            event.setCanceled(true);
            if (damage > 10F) {
                if (target.level() instanceof ServerLevel serverLevel) {
                    stack.stack().hurtAndBreak(1, serverLevel, target, item -> {
                    });
                }
            }
            _return.set(true);
        });
        if (_return.get()) {
            return;
        }

        ItemStack[] armor = {
                target.getItemBySlot(EquipmentSlot.HEAD),
                target.getItemBySlot(EquipmentSlot.CHEST),
                target.getItemBySlot(EquipmentSlot.LEGS),
                target.getItemBySlot(EquipmentSlot.FEET)
        };
        int van = SimpleUse.VoidArmorNumber(target);
        if (armor[0].getItem() instanceof VoidEmeraldArmorItem &&
                armor[1].getItem() instanceof VoidEmeraldArmorItem &&
                armor[2].getItem() instanceof VoidEmeraldArmorItem &&
                armor[3].getItem() instanceof VoidEmeraldArmorItem) {
            if (level instanceof ServerLevel serverLevel) {
                if (TagChange.getOrCreateComponent(armor[0], "Void", false)) {
                    event.setCanceled(true);
                    if (event.getSource().getEntity() instanceof LivingEntity attacker) {
                        for (ItemStack itemStack : armor) {
                            itemStack.hurtAndBreak((int) (2.5f * damage), serverLevel, attacker, item -> {
                            });
                        }
                    } else {
                        for (ItemStack itemStack : armor) {
                            itemStack.hurtAndBreak((int) (2.5f * damage), serverLevel, null, item -> {
                            });
                        }
                    }
                } else {
                    if (nextPercent(80)) {
                        event.setCanceled(true);
                        for (ItemStack itemStack : armor) {
                            itemStack.hurtAndBreak(1, serverLevel, null, item -> {
                            });
                        }
                    } else if (nextBoolean()) {
                        event.setAmount(event.getAmount() * 0.2f);
                        for (ItemStack itemStack : armor) {
                            itemStack.hurtAndBreak(4, serverLevel, null, item -> {
                            });
                        }
                    }
                }
            }
        } else if (van > 0) {
            if (nextPercent(van * 17.5)) {
                event.setCanceled(true);
                if (level instanceof ServerLevel serverLevel) {
                    for (ItemStack itemStack : armor) {
                        if (itemStack.getItem() instanceof VoidEmeraldArmorItem) {
                            itemStack.hurtAndBreak(van/2, serverLevel, null, item -> {
                            });
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void GenesisEmeraldSwordAttack(LivingDamageEvent.Pre event){
        LivingEntity target = event.getEntity();
        Level level = target.getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            ItemStack weapon = attacker.getMainHandItem();
            if (weapon.is(ModItems.GENESIS_EMERALD_SWORD)) {
                float damage = event.getNewDamage();
                if (damage < 150)
                    damage = 150;
                event.setNewDamage(0);
                target.setHealth(target.getHealth() - damage);
            }
        }
    }

    @SubscribeEvent
    public void OblivionEmeraldSwordAttack(LivingDamageEvent.Pre event){
        LivingEntity target = event.getEntity();
        Level level = target.getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            ItemStack weapon = attacker.getMainHandItem();
            if (weapon.is(ModItems.OBLIVION_EMERALD_SWORD)) {
                if (target.getHealth() <= target.getMaxHealth() * 0.2f) {
                    event.setNewDamage(Float.MAX_VALUE);
                    target.setSilent(true);
                    target.setInvisible(true);
                    target.setInvulnerable(false);
                    target.hurt(target.damageSources().generic(), Float.MAX_VALUE);
                    target.setHealth(0);
                    target.hurt(target.damageSources().generic(), Float.MAX_VALUE);
                    target.die(target.damageSources().genericKill());
                    if (target.getHealth() > 0f)
                        target.hurt(new DamageSource(target.getCommandSenderWorld().holderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("emeraldcraft:emerald_radiation")))), Float.MAX_VALUE);
                    if (!(TagChange.getOrCreateComponent(target,"ShouldBeKilled",false) && target instanceof Player)) {
                        TagChange.saveComponent(target,"ShouldBeKilled",true);
                    }
                } else {
                    event.setNewDamage(event.getNewDamage() * 3.5f);
                    if (hasCurios(attacker,ModItems.OBLIVION_EMERALD.get())) {
                        event.setNewDamage(event.getNewDamage() * 1.5f);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void OblivionEmeraldToolAttack(LivingDamageEvent.Pre event){
        Level level = event.getEntity().getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            ItemStack weapon = attacker.getMainHandItem();
            if (weapon.is(ModItems.OBLIVION_EMERALD_AXE) || weapon.is(ModItems.OBLIVION_EMERALD_PICKAXE) || weapon.is(ModItems.OBLIVION_EMERALD_SHOVEL)) {
                event.setNewDamage(event.getNewDamage() * 1.75f);
                if (hasCurios(attacker,ModItems.OBLIVION_EMERALD.get())) {
                    event.setNewDamage(event.getNewDamage() * 1.5f);
                }
            }
        }
    }

    @SubscribeEvent
    public void ConflictEmeraldAttack(LivingDamageEvent.Post event) {
        LivingEntity target = event.getEntity();
        Level level = target.getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        if (target.getMainHandItem().is(ModItems.CONFLICT_EMERALD)
                || target.getOffhandItem().is(ModItems.CONFLICT_EMERALD)
                || !SimpleUse.CuriosAPI.getCuriosForOne(target, ModItems.CONFLICT_EMERALD.get()).is(ItemStack.EMPTY.getItem())) {
            if (target.getHealth() <= 0) {
                target.setHealth(1);
                var stack = target.getMainHandItem();
                if (!stack.is(ModItems.CONFLICT_EMERALD)) stack = target.getOffhandItem();
                if (!stack.is(ModItems.CONFLICT_EMERALD)) stack = SimpleUse.CuriosAPI.getCuriosForOne(target, ModItems.CONFLICT_EMERALD.get());
                stack.shrink(1);
                target.removeEffectsCuredBy(net.neoforged.neoforge.common.EffectCures.PROTECTED_BY_TOTEM);
                target.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1400, 3));
                target.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 2));
                target.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 800, 6));
                target.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
                level.playSound(null, target.getOnPos().above(),SoundEvents.TOTEM_USE, target.getSoundSource(), 1.0F, 1.0F);
                var random = target.getRandom();
                for (int i = 0; i < 30; ++i) {
                    double d0 = random.nextGaussian() * 0.02D;
                    double d1 = random.nextGaussian() * 0.02D;
                    double d2 = random.nextGaussian() * 0.02D;
                    level.addParticle(
                            ParticleTypes.TOTEM_OF_UNDYING,
                            target.getX() + random.nextDouble() * 2.0D - 1.0D,
                            target.getY() + random.nextDouble() * 2.0D,
                            target.getZ() + random.nextDouble() * 2.0D - 1.0D,
                            d0, d1, d2
                    );
                }
            }
        }
    }
}
