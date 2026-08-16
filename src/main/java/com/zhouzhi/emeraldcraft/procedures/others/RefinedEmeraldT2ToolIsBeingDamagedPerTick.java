package com.zhouzhi.emeraldcraft.procedures.others;

import com.zhouzhi.emeraldcraft.procedures.net.Use;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

public class RefinedEmeraldT2ToolIsBeingDamagedPerTick {
    public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
        if (entity == null)
            return;
        if (itemstack.getDamageValue() != 0 && !world.isClientSide()) {
            if (entity instanceof Player _player && !(_player.getCooldowns().isOnCooldown(itemstack.getItem()))) {
                Use.subDamageValue(itemstack,30);
                _player.getCooldowns().addCooldown(itemstack.getItem(), 60);
            }
        }
    }
}