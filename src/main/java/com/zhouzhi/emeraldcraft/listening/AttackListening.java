package com.zhouzhi.emeraldcraft.listening;

import com.zhouzhi.emeraldcraft.item.lava_emerald.LavaEmeraldAxeItem;
import com.zhouzhi.emeraldcraft.item.lava_emerald.LavaEmeraldItem;
import com.zhouzhi.emeraldcraft.item.lava_emerald.LavaEmeraldPickaxeItem;
import com.zhouzhi.emeraldcraft.item.lava_emerald.LavaEmeraldSwordItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class AttackListening {
    @SubscribeEvent
    public void LavaEmeraldToolAttack(LivingDamageEvent.Pre event){
        LivingEntity target = event.getEntity();
        Level level = event.getEntity().getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            ItemStack weapon = attacker.getMainHandItem();
            if (weapon.getItem() instanceof LavaEmeraldItem || weapon.getItem() instanceof LavaEmeraldSwordItem || weapon.getItem() instanceof LavaEmeraldAxeItem || weapon.getItem() instanceof LavaEmeraldPickaxeItem) {
                target.lavaHurt();
            }
        }
    }
}
