package com.zhouzhi.emeraldcraft.procedures.others;

import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

import static com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse.GameTypeGetter.isCreativeOrSpectator;

public class EmeraldPickaxeT3Right_clickOnAir {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
        int item_need_to_destroy;
        item_need_to_destroy = SimpleUse.destroyStone(world,entity.getBlockX(),entity.getBlockY(),entity.getBlockZ(),3,false,entity instanceof ServerPlayer serverPlayer?serverPlayer:null);
        if (isCreativeOrSpectator(entity)) return;
        if (entity instanceof Player _player && world instanceof ServerLevel _level) {
            itemstack.hurtAndBreak(item_need_to_destroy, _level, _player, a -> {
            });
        }
	}
}