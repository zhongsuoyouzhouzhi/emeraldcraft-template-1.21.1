package com.zhouzhi.emeraldcraft.procedures.others;

import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

import static com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse.GameTypeGetter.isCreativeOrSpectator;


public class EmeraldShovelT3Right_clickOnAir {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack item) {
	int radius = 4;
	int dirt_num;
	if (entity == null)
		return;
	dirt_num = SimpleUse.destroyDirt(world,(int)x,(int)y,(int)z,radius,false,entity instanceof ServerPlayer serverPlayer?serverPlayer:null);
	if (!(isCreativeOrSpectator(entity))) {
		if (world instanceof ServerLevel _level && entity instanceof Player _player) {
			item.hurtAndBreak(dirt_num, _level, _player, a -> {
				});
			}
		}
	}
}