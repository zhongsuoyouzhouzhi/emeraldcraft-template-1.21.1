package com.zhouzhi.emeraldcraft.procedures.others;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import com.zhouzhi.emeraldcraft.init.ModAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class EmeraldProjectileHitEntity {
	public static void execute(LevelAccessor world, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		if (entity == null || immediatesourceentity == null)
			return;
		if ((immediatesourceentity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(ModAttributes.LAUNCHED) ? Objects.requireNonNull(_livingEntity6.getAttribute(ModAttributes.LAUNCHED)).getValue() : 0) == 1)
			if (sourceentity == null)
				return;
		entity.hurt(new DamageSource(world.holderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("emeraldcraft:emerald_radiation")))), 120);
		if (world instanceof ServerLevel _level) {
			LightningBolt entityToSpawn = EntityType.LIGHTNING_BOLT.create(_level);
            if (entityToSpawn != null) {
                entityToSpawn.moveTo(Vec3.atBottomCenterOf(BlockPos.containing(entity.getX(), entity.getY(), entity.getZ())));
                entityToSpawn.setVisualOnly(true);
                _level.addFreshEntity(entityToSpawn);
            }
        }
		if (entity instanceof Mob _entity)
			_entity.setTarget(null);
		entity.igniteForSeconds(20);
		if (entity instanceof Player _player) {
			_player.getAbilities().invulnerable = true;
			_player.onUpdateAbilities();
		}
		if (!((immediatesourceentity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(ModAttributes.LAUNCHED) ? Objects.requireNonNull(_livingEntity6.getAttribute(ModAttributes.LAUNCHED)).getValue() : 0) == 1)) {
			if (sourceentity instanceof Player _player) {
                _player.getFoodData().setSaturation((entity instanceof Player _plr ? _plr.getFoodData().getSaturationLevel() : 0) + _player.getFoodData().getSaturationLevel());
            }
			if (entity instanceof Player _player)
				_player.getFoodData().setSaturation(0);
			if (sourceentity instanceof Player _player) {
                _player.getFoodData().setFoodLevel((entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) + _player.getFoodData().getFoodLevel());
            }
			if (entity instanceof Player _player) {
                _player.causeFoodExhaustion(_player.getFoodData().getFoodLevel());
            }
		}
		for (int index0 = 0; index0 < 32; index0++) {
			{
                entity.setYRot((float) 22.5);
				entity.setXRot(0);
				entity.setYBodyRot(entity.getYRot());
				entity.setYHeadRot(entity.getYRot());
				entity.yRotO = entity.getYRot();
				entity.xRotO = entity.getXRot();
				if (entity instanceof LivingEntity _entity) {
					_entity.yBodyRotO = _entity.getYRot();
					_entity.yHeadRotO = _entity.getYRot();
				}
			}
			EmeraldCraft.queueServerWork(5, () -> entity.makeStuckInBlock(Blocks.AIR.defaultBlockState(), new Vec3(0.25, 0.05, 0.25)));
		}
	}
}