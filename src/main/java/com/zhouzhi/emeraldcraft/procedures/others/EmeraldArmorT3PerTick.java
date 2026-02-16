package com.zhouzhi.emeraldcraft.procedures.others;

import com.zhouzhi.emeraldcraft.procedures.compress.SLTZ;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftMobEffects;

public class EmeraldArmorT3PerTick {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(EmeraldcraftMobEffects.EMERALD_ATTACH, 40, 8, false, false));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(EmeraldcraftMobEffects.EMERALD_BONUS, 20, 14, false, false));
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 40, 0, false, false));
		if ((entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) < 20) {
			if (world instanceof ServerLevel _level) {
				itemstack.hurtAndBreak(2, _level, null, _stkprov -> {
				});
			}
			if (entity instanceof Player _player)
				_player.getFoodData().setFoodLevel((entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) + 1);
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 30);
		}
		if ((entity instanceof Player _plr ? _plr.getFoodData().getSaturationLevel() : 0) < 20) {
			if (world instanceof ServerLevel _level) {
				itemstack.hurtAndBreak(1, _level, null, _stkprov -> {
				});
			}
			if (entity instanceof Player _player)
				_player.getFoodData().setSaturation((float) ((entity instanceof Player _plr ? _plr.getFoodData().getSaturationLevel() : 0) + 1));
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 30);
		}
		if (entity.isOnFire()) {
			entity.clearFire();
			if (world instanceof ServerLevel _level) {
				itemstack.hurtAndBreak(1, _level, null, _stkprov -> {
				});
			}
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 40);
		}
		if (entity.isInWater()) {
			if (entity.getAirSupply() <= 10) {
				entity.setAirSupply(300);
				if (entity instanceof Player _player)
					_player.getCooldowns().addCooldown(itemstack.getItem(), 200);
				if (world instanceof ServerLevel _level) {
					itemstack.hurtAndBreak(3, _level, null, _stkprov -> {
					});
				}
			}
		}
		if (entity.isShiftKeyDown()) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1, 20, false, false));
			if (entity.isInWall()) {
				{
					BlockPos _pos = BlockPos.containing(x, y + 1, z);
					Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y + 1, z), null);
					world.destroyBlock(_pos, false);
				}
				world.destroyBlock(BlockPos.containing(x, y + 1, z), false);
			}
			Level _world = entity.level();
			double _x = entity.getX();
			double _y = entity.getY();
			double _z = entity.getZ();
			SLTZ.execute(_world, entity, _x, _y, _z, 4);
		}
	}
}