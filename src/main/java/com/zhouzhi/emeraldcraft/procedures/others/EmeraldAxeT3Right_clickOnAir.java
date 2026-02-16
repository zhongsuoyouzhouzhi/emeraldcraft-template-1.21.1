package com.zhouzhi.emeraldcraft.procedures.others;

import com.zhouzhi.emeraldcraft.procedures.compress.WXTY;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class EmeraldAxeT3Right_clickOnAir {
	public static void execute(Entity entity) {
		Level world = entity.level();
		WXTY.execute(world, entity, 50.0);
	}
}