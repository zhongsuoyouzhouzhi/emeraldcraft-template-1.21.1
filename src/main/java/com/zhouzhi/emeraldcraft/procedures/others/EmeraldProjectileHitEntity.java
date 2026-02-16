package com.zhouzhi.emeraldcraft.procedures.others;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftAttributes;
import com.zhouzhi.emeraldcraft.EmeraldCraft;

public class EmeraldProjectileHitEntity {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		if (entity == null || immediatesourceentity == null)
			return;
		if ((immediatesourceentity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(EmeraldcraftAttributes.LAUNCHED) ? _livingEntity6.getAttribute(EmeraldcraftAttributes.LAUNCHED).getValue() : 0) == 1)
			if (sourceentity == null)
				return;
		entity.hurt(new DamageSource(world.holderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("emeraldcraft:emerald_radiation")))), 120);
		if (world instanceof ServerLevel _level) {
			LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
			entityToSpawn.moveTo(Vec3.atBottomCenterOf(BlockPos.containing(x, y, z)));
			entityToSpawn.setVisualOnly(true);
			_level.addFreshEntity(entityToSpawn);
		}
		if (entity instanceof Mob _entity)
			_entity.setTarget(null);
		entity.igniteForSeconds(20);
		if (entity instanceof Player _player) {
			_player.getAbilities().invulnerable = true;
			_player.onUpdateAbilities();
		}
		if (!((immediatesourceentity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(EmeraldcraftAttributes.LAUNCHED) ? _livingEntity6.getAttribute(EmeraldcraftAttributes.LAUNCHED).getValue() : 0) == 1)) {
			if (sourceentity instanceof Player _player)
				_player.getFoodData().setSaturation((float) ((entity instanceof Player _plr ? _plr.getFoodData().getSaturationLevel() : 0) + (sourceentity instanceof Player _plr ? _plr.getFoodData().getSaturationLevel() : 0)));
			if (entity instanceof Player _player)
				_player.getFoodData().setSaturation(0);
			if (sourceentity instanceof Player _player)
				_player.getFoodData().setFoodLevel((entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) + (sourceentity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0));
			if (entity instanceof Player _player)
				_player.causeFoodExhaustion(entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0);
		}
		for (int index0 = 0; index0 < 32; index0++) {
			{
				Entity _ent = entity;
				_ent.setYRot((float) 22.5);
				_ent.setXRot(0);
				_ent.setYBodyRot(_ent.getYRot());
				_ent.setYHeadRot(_ent.getYRot());
				_ent.yRotO = _ent.getYRot();
				_ent.xRotO = _ent.getXRot();
				if (_ent instanceof LivingEntity _entity) {
					_entity.yBodyRotO = _entity.getYRot();
					_entity.yHeadRotO = _entity.getYRot();
				}
			}
			EmeraldCraft.queueServerWork(5, () -> {
				entity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25));
			});
		}
	}
}