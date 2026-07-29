package com.zhouzhi.emeraldcraft.listening;

import com.zhouzhi.emeraldcraft.item.lava_emerald.*;
import com.zhouzhi.emeraldcraft.item.void_emerald.VoidEmeraldArmorItem;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

import java.util.Random;

public class AttackListening {
    private static final Random random = new Random();
    @SubscribeEvent
    public void LavaEmeraldToolAttack(LivingDamageEvent.Pre event){
        LivingEntity target = event.getEntity();
        Level level = event.getEntity().getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            ItemStack weapon = attacker.getMainHandItem();
            if (weapon.getItem() instanceof LavaEmeraldItem || weapon.getItem() instanceof LavaEmeraldSwordItem || weapon.getItem() instanceof LavaEmeraldAxeItem || weapon.getItem() instanceof LavaEmeraldPickaxeItem || weapon.getItem() instanceof LavaEmeraldShovelItem || weapon.getItem() instanceof LavaEmeraldHoeItem) {
                target.lavaHurt();
            }
        }
    }

    @SubscribeEvent
    public void VoidEmeraldArmorBeAttacked(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        Level level = target.getCommandSenderWorld();
        float damage = event.getAmount();
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
                    if (random.nextInt(100) < 80) {
                        event.setCanceled(true);
                        for (ItemStack itemStack : armor) {
                            itemStack.hurtAndBreak(1, serverLevel, null, item -> {
                            });
                        }
                    } else if (random.nextInt(100) < 50){
                        event.setAmount(event.getAmount() * 0.2f);
                        for (ItemStack itemStack : armor) {
                            itemStack.hurtAndBreak(4, serverLevel, null, item -> {
                            });
                        }
                    }
                }
            }
        } else if (van > 0) {
            if (random.nextDouble(100) < van * 17.5) {
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
}
