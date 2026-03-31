package com.zhouzhi.emeraldcraft.procedures.effect;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

public class EmeraldAttachOnEffectEnded {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (!(entity instanceof Player _plr && _plr.getAbilities().instabuild)) {
			if (entity instanceof Player _player) {
				_player.getAbilities().mayfly = false;
			}
			if (entity instanceof Player _player) {
				_player.getAbilities().flying = false;
				_player.onUpdateAbilities();
			}
		}
	}
}