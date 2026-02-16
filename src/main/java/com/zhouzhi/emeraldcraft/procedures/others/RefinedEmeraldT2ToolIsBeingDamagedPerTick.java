package com.zhouzhi.emeraldcraft.procedures.others;

import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

public class RefinedEmeraldT2ToolIsBeingDamagedPerTick {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
        if (entity == null)
            return;
        if (itemstack.getDamageValue() != 0 && !world.isClientSide()) {
            if (entity instanceof Player _player && !(_player.getCooldowns().isOnCooldown(itemstack.getItem()))) {
                int timer = TagChange.getOrCreateComponent(itemstack, "Timer",300);
                timer--;
                if (timer <= 0) {
                    if (itemstack.getDamageValue() <= 30) {
                        itemstack.setDamageValue(0);
                    } else {
                        itemstack.setDamageValue(itemstack.getDamageValue() - 30);
                    }
                    _player.getCooldowns().addCooldown(itemstack.getItem(), 60);
                    TagChange.saveComponent(itemstack, "Timer", 300);
                }
                TagChange.saveComponent(itemstack, "Timer", timer);
            }
        }
    }
}