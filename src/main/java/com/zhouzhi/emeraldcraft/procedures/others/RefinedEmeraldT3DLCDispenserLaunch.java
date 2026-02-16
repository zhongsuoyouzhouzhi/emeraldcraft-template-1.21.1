package com.zhouzhi.emeraldcraft.procedures.others;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Direction;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftEntities;
import com.zhouzhi.emeraldcraft.init.EmeraldcraftAttributes;
import com.zhouzhi.emeraldcraft.entity.EmeraldProjectileEntity;

public class RefinedEmeraldT3DLCDispenserLaunch {
	public static void execute(LevelAccessor world, double x, double y, double z, Direction direction) {
		if (direction == null)
			return;
		Entity a = null;
		if (world instanceof ServerLevel projectileLevel) {
			Projectile _entityToSpawn = initArrowProjectile(new EmeraldProjectileEntity(EmeraldcraftEntities.EMERALD_PROJECTILE.get(), 0, 0, 0, projectileLevel, createArrowWeaponItemStack(projectileLevel, 1, (byte) 127)), null, 0, true, false,
					false, AbstractArrow.Pickup.DISALLOWED);
			a = _entityToSpawn;
			_entityToSpawn.setPos((x + direction.getStepX()), (y + direction.getStepY()), (z + direction.getStepZ()));
			_entityToSpawn.shoot((direction.getStepX()), (direction.getStepY()), (direction.getStepZ()), (float) 1.5, 0);
			projectileLevel.addFreshEntity(_entityToSpawn);
		}
		if (a instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(EmeraldcraftAttributes.LAUNCHED))
			_livingEntity8.getAttribute(EmeraldcraftAttributes.LAUNCHED).setBaseValue(1);
	}

	private static AbstractArrow initArrowProjectile(AbstractArrow entityToSpawn, Entity shooter, float damage, boolean silent, boolean fire, boolean particles, AbstractArrow.Pickup pickup) {
		entityToSpawn.setOwner(shooter);
		entityToSpawn.setBaseDamage(damage);
		if (silent)
			entityToSpawn.setSilent(true);
		if (fire)
			entityToSpawn.igniteForSeconds(100);
		if (particles)
			entityToSpawn.setCritArrow(true);
		entityToSpawn.pickup = pickup;
		return entityToSpawn;
	}

	private static ItemStack createArrowWeaponItemStack(Level level, int knockback, byte piercing) {
		ItemStack weapon = new ItemStack(Items.ARROW);
		if (knockback > 0)
			weapon.enchant(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK), knockback);
		if (piercing > 0)
			weapon.enchant(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.PIERCING), piercing);
		return weapon;
	}
}