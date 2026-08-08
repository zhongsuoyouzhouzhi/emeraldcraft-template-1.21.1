package com.zhouzhi.emeraldcraft.procedures;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.procedures.compress.*;
import com.zhouzhi.emeraldcraft.procedures.net.Use;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse.sendOpen;
import static com.zhouzhi.emeraldcraft.procedures.compress.TagChange.getOrCreateComponent;
import static com.zhouzhi.emeraldcraft.procedures.compress.TagChange.saveComponent;


public class SpecialSkillPressed {
	public static void execute(Entity entity) {
		if (entity instanceof LivingEntity livingEntity) {
			ItemStack itemstack = livingEntity.getItemBySlot(EquipmentSlot.MAINHAND);
            ItemStack armor = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
            int van = SimpleUse.VoidArmorNumber(livingEntity);
            if (van == 4 && livingEntity.isShiftKeyDown()) {
                boolean tag;
                tag = TagChange.getOrCreateComponent(armor, "Void", false);
                TagChange.saveComponent(armor, "Void", !tag);
                if (entity instanceof Player player) {
                    if (!tag)
                        SimpleUse.Message.send(player, Component.translatable("message.emeraldcraft.skill_void_armor").append(Component.translatable("message.emeraldcraft.true")),true);
                    else
                        SimpleUse.Message.send(player, Component.translatable("message.emeraldcraft.skill_void_armor").append(Component.translatable("message.emeraldcraft.false")),true);
                }
            } else if (itemstack.is(ModItems.EMERALD_SWORD_T_3.get())){
                if (entity instanceof Player _player) {
                    if (!_player.getCooldowns().isOnCooldown(itemstack.getItem())) {
                        _player.getCooldowns().addCooldown(itemstack.getItem(), 40);
                        Level world = entity.level();
                        double x = entity.getX();
                        double y = entity.getY();
                        double z = entity.getZ();
                        double radius = 22.5;
                        float damage = 20.0f;
                        PushAway.execute(world, entity, x, y, z, radius, damage);
                    }
                }
			} else if (itemstack.is(ModItems.REFINED_EMERALD_PLUS)) {
                if (entity instanceof Player _player && !(SimpleUse.GameTypeGetter.isCreativeOrSpectator(_player))){
                    itemstack.setCount(itemstack.getCount() - 1);
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 20);
                }
                Level level = entity.level();
                livingEntity.moveTo(entity.getX(),entity.getY()+4,entity.getZ());
                if (entity instanceof Player _player) {
                    EmeraldCraft.queueServerWork(20, () ->
                        PushAway.execute(level, _player, _player.getX(), _player.getY(), _player.getZ(), 64, 64));
                    level.playSound(_player, _player.getX(), _player.getY(), _player.getZ(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 2.0f, 1.0f);
                }
            } else if (itemstack.is(ModItems.EMERALD_PICKAXE_T_3)) {
                Level world = livingEntity.level();
                if (world instanceof ServerLevel _level) {
                    itemstack.hurtAndBreak(1, _level, null, a -> {
                    });
                }
                if (entity instanceof Player _player)
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 100);
                MobEffectInstance[] effects = new MobEffectInstance[]{
                        new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 90, 5, false, false)
                };
                double x = entity.getX();
                double y = entity.getY();
                double z = entity.getZ();
                MobEffectALL.execute(world, x, y, z, effects, 12 ,5 ,12);
            } else if (itemstack.is(ModItems.SKYFILLING_BLADE)) {
                Level world = livingEntity.level();
                if (world instanceof ServerLevel _level) {
                    itemstack.hurtAndBreak(1, _level, null, a -> {
                    });
                }
                if (entity instanceof Player _player) {
                    if (_player.getCooldowns().isOnCooldown(itemstack.getItem()))
                        return;
                    int tag = getOrCreateComponent(itemstack,"special_skill_type",0);
                    tag++;
                    switch (tag){
                        case 1:
                            if (world instanceof ServerLevel _level) {
                                itemstack.hurtAndBreak(9, _level, null, a -> {
                                });
                            }
                            MTR.execute(entity, 64.0f, 200.0f, 320);
                            _player.getCooldowns().addCooldown(itemstack.getItem(), 150);
                            SimpleUse.Message.send(_player, Component.translatable("message.emeraldcraft.skill").append("2"),true);
                            break;
                        case 2:
                            Use.SkyFillingBladeSpecialSkill2(_player,120.0f,32);
                            _player.getCooldowns().addCooldown(itemstack.getItem(), 120);
                            SimpleUse.Message.send(_player, Component.translatable("message.emeraldcraft.skill").append("3"),true);
                            break;
                        default:
                            MobEffectInstance[] effects = new MobEffectInstance[]{
                                    new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 4),
                                    new MobEffectInstance(MobEffects.WEAKNESS, 80, 5),
                                    new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 3),
                                    new MobEffectInstance(MobEffects.REGENERATION, 180, 5),
                                    new MobEffectInstance(MobEffects.WATER_BREATHING, 180, 5)
                            };
                            MobEffectALL.execute(_player.level(), effects, _player);
                            _player.getCooldowns().addCooldown(itemstack.getItem(), 60);
                            tag = 0;
                            SimpleUse.Message.send(_player, Component.translatable("message.emeraldcraft.skill").append("1"),true);
                    }
                    saveComponent(itemstack,"special_skill_type",tag);
                }
            } else if (itemstack.is(ModItems.VOID_EMERALD_SWORD)) {
                if (livingEntity.level() instanceof ServerLevel serverLevel) {
                    if (entity.isShiftKeyDown()) {
                        for (Entity _entity : serverLevel.getAllEntities()) {
                            String[] b = {};
                            for (String a : _entity.getTags().toArray(b)) {
                                if (a.equals("void")) {
                                    _entity.removeTag("void");
                                }
                            }
                        }
                    } else {
                        if (entity instanceof Player _player) {
                            saveComponent(itemstack, "Void_Open", !getOrCreateComponent(itemstack, "Void_Open", true));
                            sendOpen(_player, getOrCreateComponent(itemstack, "Void_Open", true));
                        }
                    }
                }
            } else if (itemstack.is(ModItems.VOID_EMERALD_AXE) || itemstack.is(ModItems.VOID_EMERALD_PICKAXE) || itemstack.is(ModItems.VOID_EMERALD_SHOVEL) || itemstack.is(ModItems.VOID_EMERALD_HOE)) {
                boolean tag = getOrCreateComponent(itemstack,"Scope",false);
                saveComponent(itemstack,"Scope",!tag);
                if (entity instanceof Player _player) {
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 10);
                    sendOpen(_player, getOrCreateComponent(itemstack, "Scope", true));
                }
            } else if (itemstack.is(ModItems.INFERNO_EMERALD_SWORD)) {
                if (entity instanceof Player player) {
                    Level level = player.level();
                    if (Use.InfernoEmeraldSwordSpecialSkill(level,player)) {
                        if (!SimpleUse.GameTypeGetter.isCreativeOrSpectator(player) && level instanceof ServerLevel serverLevel) {
                            itemstack.hurtAndBreak(75, serverLevel,player,ignore->{});
                        }
                    }
                }
            }
        }
    }
}