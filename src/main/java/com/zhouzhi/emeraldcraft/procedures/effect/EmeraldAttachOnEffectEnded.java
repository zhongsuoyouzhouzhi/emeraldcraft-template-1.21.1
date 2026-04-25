package com.zhouzhi.emeraldcraft.procedures.effect;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class EmeraldAttachOnEffectEnded {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (!(entity instanceof Player _plr && _plr.getAbilities().instabuild)) {
			if (entity instanceof Player _player) {
				_player.getAbilities().mayfly = false;
				_player.getAbilities().flying = false;
				_player.onUpdateAbilities();
			}
		}
	}
}