package com.zhouzhi.emeraldcraft.listening;

import com.zhouzhi.emeraldcraft.entity.ThrownInfernoEmeraldTrident;
import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.init.ModTags;
import com.zhouzhi.emeraldcraft.item.oblivion_emerald.OblivionEmeraldItem;
import com.zhouzhi.emeraldcraft.item.void_emerald.VoidEmeraldArmorItem;
import com.zhouzhi.emeraldcraft.item.void_emerald.VoidEmeraldItem;
import com.zhouzhi.emeraldcraft.procedures.compress.MobEffectALL;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import top.theillusivec4.curios.api.CuriosApi;

import static com.zhouzhi.emeraldcraft.listening.MiningListening.infernoChange;
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
                if (trident.special_skill) {
                    Entity attacker = source.getEntity();
                    if (level instanceof ServerLevel serverLevel) {
                        AABB aabb = new AABB(target.getX() - 5,target.getY() - 1, target.getZ() - 5,target.getX() + 5,target.getY() + 1,target.getZ() + 5);
                        serverLevel.getEntitiesOfClass(LivingEntity.class, aabb).forEach(entity -> {
                            if (entity.is(target) || (attacker != null && entity.is(attacker))) {
                                return;
                            }
                            entity.hurt(entity.damageSources().generic(),30F);
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
                    target.hurt(target.damageSources().generic(),75F);
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

        CuriosApi.getCuriosInventory(target).ifPresent(inventory -> {
            inventory.findCurios(stack ->
                    stack.getItem() instanceof OblivionEmeraldItem
            ).forEach(stack -> {
                if (damage > 10F) {
                    if (target.level() instanceof ServerLevel serverLevel) {
                        event.setCanceled(true);
                        stack.stack().hurtAndBreak(1, serverLevel, target, item -> {
                        });
                    }
                }
            });
        });

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
    public void ShieldBlock(LivingShieldBlockEvent event) {
        if (event.isCanceled()) return;
        LivingEntity livingEntity = event.getEntity();
        Level level = livingEntity.getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        } else if (!event.getBlocked()) {
            return;
        }
        ItemStack itemstack = livingEntity.getUseItem();

        if (itemstack.is(ModItems.VOID_EMERALD_SHIELD)) {
            if (itemstack.getDamageValue() > itemstack.getMaxDamage()-200) {
                if (level instanceof ServerLevel serverLevel) {
                    itemstack.hurtAndBreak(200,serverLevel,livingEntity,item -> {});
                }
                livingEntity.hurt(livingEntity.damageSources().indirectMagic(livingEntity,livingEntity),5);
                VoidEmeraldItem.explode(level,livingEntity.getOnPos().above(),livingEntity,8);
                SimpleUse.OperateEntity(level,livingEntity,8,8,8,entity->{
                    if (entity == livingEntity) return;
                    String[] b = {};
                    for (String a : entity.getTags().toArray(b)) {
                        if (a.equals("void")) {
                            return;
                        }
                    }
                    entity.addTag("void");
                });
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
                target.setHealth(target.getHealth()-damage);
            }
        }
    }

    @SubscribeEvent
    public void OblivionEmeraldSwordAttack(LivingDamageEvent.Pre event){
        Level level = event.getEntity().getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            ItemStack weapon = attacker.getMainHandItem();
            if (weapon.is(ModItems.OBLIVION_EMERALD_SWORD)) {
                event.setNewDamage(event.getNewDamage() * 3.5f);
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
            }
        }
    }
}
