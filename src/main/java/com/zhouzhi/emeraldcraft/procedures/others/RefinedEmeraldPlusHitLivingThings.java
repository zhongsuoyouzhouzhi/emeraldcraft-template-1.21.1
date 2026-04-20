package com.zhouzhi.emeraldcraft.procedures.others;

import com.zhouzhi.emeraldcraft.init.ModMobEffects;
import com.zhouzhi.emeraldcraft.procedures.compress.DamageALL;
import com.zhouzhi.emeraldcraft.procedures.compress.MobEffectALL;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class RefinedEmeraldPlusHitLivingThings {
	public static void execute(Level world, double x, double y, double z, Entity entity, Entity source) {
		if (entity == null || world == null)
		    return;
		DamageALL.execute(world, source, 22.5f, 10, 10, 10, false);
		MobEffectInstance[] effects = new MobEffectInstance[]{
			new MobEffectInstance(net.minecraft.world.effect.MobEffects.POISON, 300, 5, false, false),
			new MobEffectInstance(net.minecraft.world.effect.MobEffects.BLINDNESS, 360, 3, false, false),
			new MobEffectInstance(ModMobEffects.SUPPRESS, 360, 5, false, false)
		};
		MobEffectALL.execute(world, x, y, z, effects, 15 ,15 ,15, source);

	}
}
