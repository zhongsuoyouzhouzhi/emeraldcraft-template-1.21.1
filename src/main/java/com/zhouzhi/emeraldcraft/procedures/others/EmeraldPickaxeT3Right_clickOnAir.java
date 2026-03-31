package com.zhouzhi.emeraldcraft.procedures.others;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;

import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;

public class EmeraldPickaxeT3Right_clickOnAir {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
        int item_need_to_destroy = SimpleUse.destroyStone(world,entity.getBlockX(),entity.getBlockY(),entity.getBlockZ(),3,false);
        if (SimpleUse.getEntityGameType(entity) == GameType.CREATIVE || SimpleUse.getEntityGameType(entity) == GameType.SPECTATOR) return;
        if (entity instanceof Player _player && world instanceof ServerLevel _level) {
            itemstack.hurtAndBreak(item_need_to_destroy, _level, _player, a -> {
            });
        }
	}
}