package com.zhouzhi.emeraldcraft.procedures.others;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

public class RefinedemeraldBlock3IsDestroyedByBoom {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world instanceof Level _level && !_level.isClientSide())
			_level.explode(null, x, y, z, 648, Level.ExplosionInteraction.BLOCK);
		if (world instanceof Level _level && !_level.isClientSide())
			_level.explode(null, (x + 32), y, z, 384, Level.ExplosionInteraction.BLOCK);
		if (world instanceof Level _level && !_level.isClientSide())
			_level.explode(null, (x - 32), y, z, 384, Level.ExplosionInteraction.BLOCK);
		if (world instanceof Level _level && !_level.isClientSide())
			_level.explode(null, x, (y + 32), z, 384, Level.ExplosionInteraction.BLOCK);
		if (world instanceof Level _level && !_level.isClientSide())
			_level.explode(null, x, (y - 32), z, 384, Level.ExplosionInteraction.BLOCK);
		if (world instanceof Level _level && !_level.isClientSide())
			_level.explode(null, x, y, (z + 32), 384, Level.ExplosionInteraction.BLOCK);
		if (world instanceof Level _level && !_level.isClientSide())
			_level.explode(null, x, y, (z - 32), 384, Level.ExplosionInteraction.BLOCK);
	}
}