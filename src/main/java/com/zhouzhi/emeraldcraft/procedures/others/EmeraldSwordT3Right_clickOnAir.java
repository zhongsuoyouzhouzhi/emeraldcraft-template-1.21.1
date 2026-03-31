package com.zhouzhi.emeraldcraft.procedures.others;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.registries.Registries;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftEntities;
import com.zhouzhi.emeraldcraft.entity.EmeraldProjectileEntity;

public class EmeraldSwordT3Right_clickOnAir {
	public static void execute(LevelAccessor world, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (world instanceof ServerLevel _level) {
			itemstack.hurtAndBreak(40, _level, null, _stkprov -> {
			});
		}
		{
            Level projectileLevel = entity.level();
			if (!projectileLevel.isClientSide()) {
				Projectile _entityToSpawn = initArrowProjectile(new EmeraldProjectileEntity(EmeraldcraftEntities.EMERALD_PROJECTILE.get(), 0, 0, 0, projectileLevel, createArrowWeaponItemStack(projectileLevel)), entity
				);
				_entityToSpawn.setPos(entity.getX(), entity.getEyeY() - 0.1, entity.getZ());
				_entityToSpawn.shoot(entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z, (float) 1.5, 0);
				projectileLevel.addFreshEntity(_entityToSpawn);
			}
		}
		if (entity instanceof Player _player)
			_player.getCooldowns().addCooldown(itemstack.getItem(), 60);
	}

	private static AbstractArrow initArrowProjectile(AbstractArrow entityToSpawn, Entity shooter) {
		entityToSpawn.setOwner(shooter);
		entityToSpawn.setBaseDamage((float) 0);
        entityToSpawn.setSilent(true);
        entityToSpawn.pickup = AbstractArrow.Pickup.DISALLOWED;
		return entityToSpawn;
	}

	private static ItemStack createArrowWeaponItemStack(Level level) {
		ItemStack weapon = new ItemStack(Items.ARROW);
        weapon.enchant(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK), 1);
        weapon.enchant(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.PIERCING), (byte) 127);
		return weapon;
	}
}