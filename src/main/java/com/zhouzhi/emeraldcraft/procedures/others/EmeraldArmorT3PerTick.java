package com.zhouzhi.emeraldcraft.procedures.others;

import com.zhouzhi.emeraldcraft.init.ModMobEffects;
import com.zhouzhi.emeraldcraft.procedures.compress.MobEffectALL;
import com.zhouzhi.emeraldcraft.procedures.compress.SLTZ;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;

public class EmeraldArmorT3PerTick {
    public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
        if (entity == null)
            return;
        if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            MobEffectInstance[] effectInstances = new MobEffectInstance[]{
                    new MobEffectInstance(ModMobEffects.EMERALD_ATTACH, 40, 8, false, false),
                    new MobEffectInstance(ModMobEffects.EMERALD_BONUS, 20, 14, false, false),
                    new MobEffectInstance(net.minecraft.world.effect.MobEffects.FIRE_RESISTANCE, 40, 0, false, false)
            };
            MobEffectALL.execute(_entity.level(), effectInstances, _entity);
        }
		if (entity instanceof Player player && world instanceof ServerLevel _level) {
			if (player.getFoodData().getFoodLevel() < 20) {
				itemstack.hurtAndBreak(2, _level, player, i -> {});
				player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() + 1);
			}
			if (player.getFoodData().getSaturationLevel() < 20) {
				itemstack.hurtAndBreak(1, _level, player, i -> {});
				player.getFoodData().setSaturation(player.getFoodData().getSaturationLevel() + 1);
				player.getCooldowns().addCooldown(itemstack.getItem(), 30);
			}
            player.getCooldowns().addCooldown(itemstack.getItem(), 30);
		}
        if (entity.isOnFire()) {
            entity.clearFire();
            if (world instanceof ServerLevel _level) {
                itemstack.hurtAndBreak(1, _level, null, i -> {
                });
            }
            if (entity instanceof Player _player)
                _player.getCooldowns().addCooldown(itemstack.getItem(), 40);
        }
        if (entity.isInWater()) {
			if (entity.getAirSupply() <= 10) {
				entity.setAirSupply(300);
				if (entity instanceof Player _player && world instanceof ServerLevel _level) {
					_player.getCooldowns().addCooldown(itemstack.getItem(), 200);
					itemstack.hurtAndBreak(3, _level, _player, i -> {
					});
				}
			}
        }
        if (entity.isShiftKeyDown()) {
            if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
                _entity.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 1, 20, false, false));
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
