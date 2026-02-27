package com.zhouzhi.emeraldcraft.procedures.others;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

public class RefinedEmeraldBlock3IsDestroyedByBoom {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof Level _level && !_level.isClientSide()) {
			final int a = 16;
			boom(_level, x, y, z);
			boom(_level, x + a, y, z);
			boom(_level, x - a, y, z);
			boom(_level, x, y + a, z);
			boom(_level, x, y - a, z);
			boom(_level, x, y, z + a);
			boom(_level, x, y, z - a);
		}
	}

	private static void boom(Level level, double x, double y, double z) {
		level.explode(null, x, y, z, 128, Level.ExplosionInteraction.BLOCK);
	}
}