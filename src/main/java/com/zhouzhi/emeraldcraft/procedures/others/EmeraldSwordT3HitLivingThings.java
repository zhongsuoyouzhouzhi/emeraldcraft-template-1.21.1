package com.zhouzhi.emeraldcraft.procedures.others;

import net.minecraft.world.level.Level;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.registries.Registries;

import com.zhouzhi.emeraldcraft.EmeraldCraft;

public class EmeraldSwordT3HitLivingThings {
	public static void execute(Entity sourceentity) {
		if (sourceentity == null)
			return;
		EmeraldCraft.queueServerWork(10, () -> {
			{
                Level projectileLevel = sourceentity.level();
				if (!projectileLevel.isClientSide()) {
					Projectile _entityToSpawn = initArrowProjectile(new Arrow(projectileLevel, 0, 0, 0, new Arrow(EntityType.ARROW, projectileLevel).getPickupItemStackOrigin(), createArrowWeaponItemStack(projectileLevel)),
							sourceentity);
					_entityToSpawn.setPos(sourceentity.getX(), sourceentity.getEyeY() - 0.1, sourceentity.getZ());
					_entityToSpawn.shoot(sourceentity.getLookAngle().x, sourceentity.getLookAngle().y, sourceentity.getLookAngle().z, 2, 0);
					projectileLevel.addFreshEntity(_entityToSpawn);
				}
			}
		});
	}

	private static AbstractArrow initArrowProjectile(AbstractArrow entityToSpawn, Entity shooter) {
		entityToSpawn.setOwner(shooter);
		entityToSpawn.setBaseDamage((float) 10);
		entityToSpawn.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
		return entityToSpawn;
	}

	private static ItemStack createArrowWeaponItemStack(Level level) {
		ItemStack weapon = new ItemStack(Items.ARROW);
        weapon.enchant(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.KNOCKBACK), 1);
        weapon.enchant(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.PIERCING), (byte) 127);
		return weapon;
	}
}