package com.zhouzhi.emeraldcraft.listening;

import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.item.void_emerald.VoidEmeraldItem;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import com.zhouzhi.emeraldcraft.procedures.net.Use;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;

import static com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse.CuriosAPI.hasCurios;

public class ShieldBlockListening {
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
                SimpleUse.OperateEntity(level,livingEntity,8,8,8, entity->{
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
        } else if (itemstack.is(ModItems.OBLIVION_EMERALD_SHIELD)) {
            var absorbed_damage = Float.parseFloat(TagChange.getOrCreateComponent(itemstack,"AbsorbedDamage","0.0"));
            var block_damage = event.getBlockedDamage() * 0.75f;
            absorbed_damage += block_damage;
            String damage = String.format("%.1f", absorbed_damage);
            var reflect_damage = Float.parseFloat(damage);
            if (reflect_damage >= 50f || event.getBlockedDamage() >= 15f) {
                TagChange.saveComponent(itemstack,"AbsorbedDamage", "0.0");
                if (livingEntity instanceof Player player) {
                    SimpleUse.Message.send(player, Component.translatable("tooltip.emeraldcraft.oblivion_shield.desc")
                            .append("0"), true);
                }
                if (level instanceof ServerLevel serverLevel) {
                    itemstack.hurtAndBreak(10,serverLevel,livingEntity,item -> {});
                }
                if (hasCurios(livingEntity,ModItems.OBLIVION_EMERALD.get())) {
                    Use.ChooseEntity.getEntitiesInRectangle(livingEntity,3,3,12).forEach(entity ->
                            entity.hurt(entity.damageSources().indirectMagic(livingEntity,livingEntity), reflect_damage * 1.5f));
                } else {
                    Use.ChooseEntity.getEntitiesInRectangle(livingEntity,3,3,12).forEach(entity ->
                            entity.hurt(entity.damageSources().indirectMagic(livingEntity,livingEntity), reflect_damage));
                }
                for (var a = 3;a >= 0;a -= 1) {
                    SimpleUse.Effect.spawnRectangleBorder(livingEntity, ParticleTypes.OMINOUS_SPAWNING, a, a, a + 9);
                }
            } else {
                TagChange.saveComponent(itemstack, "AbsorbedDamage", damage);
                if (livingEntity instanceof Player player) {
                    SimpleUse.Message.send(player, Component.translatable("tooltip.emeraldcraft.oblivion_shield.desc")
                            .append(damage), true);
                }
            }
        }
    }
}
